package com.github.maiqingqiang.goormhelper.services;

import com.github.maiqingqiang.goormhelper.GoORMHelperBundle;
import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.bean.ScannedPath;
import com.github.maiqingqiang.goormhelper.inspections.GoTypeSpecDescriptor;
import com.github.maiqingqiang.goormhelper.orm.goframe.GoFrameTypes;
import com.github.maiqingqiang.goormhelper.utils.Strings;
import com.goide.GoFileType;
import com.goide.psi.*;
import com.goide.util.Value;
import com.google.common.base.CaseFormat;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.progress.BackgroundTaskQueue;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiManager;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.serviceContainer.NonInjectable;
import com.intellij.util.Time;
import org.atteo.evo.inflector.English;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Service(Service.Level.PROJECT)
@State(name = "GoORMHelperCache", storages = @Storage(value = "goORMHelperCache.xml"))
public class GoORMHelperCacheManager implements PersistentStateComponent<GoORMHelperCacheManager.State> {
    private static final Logger LOG = Logger.getInstance(GoORMHelperCacheManager.class);

    public final Project project;
    private State state = new State();

    @NonInjectable
    private GoORMHelperCacheManager(@NotNull Project project) {
        this.project = project;
    }

    public static GoORMHelperCacheManager getInstance(@NotNull Project project) {
        return project.getService(GoORMHelperCacheManager.class);
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

    public void scan() {
        BackgroundTaskQueue taskQueue = new BackgroundTaskQueue(project, GoORMHelperBundle.message("name"));
        taskQueue.run(new Task.Backgroundable(project, GoORMHelperBundle.message("initializing.title")) {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                if ((state.lastTimeChecked + Time.DAY) <= System.currentTimeMillis()) {
                    state.lastTimeChecked = System.currentTimeMillis();

                    state.schemaMapping.clear();
                    state.scannedPathMapping.clear();
                    state.tableStructMapping.clear();

                    LOG.info("Clear GoORMHelperCache lastTimeChecked: " + state.lastTimeChecked);
                }

                GoORMHelperProjectSettings.State state = Objects.requireNonNull(GoORMHelperProjectSettings.getInstance(project).getState());

                if (state.enableGlobalScan) {
                    scanProject(project.getBaseDir(), Types.EXCLUDED_SCAN_LIST);
                } else {
                    for (String path : state.scanPathList) {
                        VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(path);
                        if (file == null) continue;
                        scanProject(file);
                    }
                }
            }
        });
    }

    public void scanProject(@NotNull VirtualFile virtualFile) {
        scanProject(virtualFile, null);
    }

    public void scanProject(@NotNull VirtualFile virtualFile, List<String> excluded) {
        for (VirtualFile file : virtualFile.getChildren()) {
            if (!file.isValid() || (excluded != null && excluded.contains(file.getName()))) continue;

            if (file.isDirectory()) {
                scanProject(file, excluded);
            } else {
                ScannedPath scanned = this.state.scannedPathMapping.get(file.getUrl());
                if (scanned != null && scanned.getLastModified() == file.getTimeStamp()) continue;
                LOG.info("GoORMHelperCache update: " + file.getUrl());
                parseGoFile(file);
            }
        }
    }

    public void parseGoFile(@NotNull VirtualFile file) {
        if (!(file.isValid() && file.getName().endsWith('.' + GoFileType.DEFAULT_EXTENSION))) return;

        DumbService.getInstance(project).runWhenSmart(() -> {
            Document document = FileDocumentManager.getInstance().getDocument(file);

            if (document != null && PsiManager.getInstance(project).findFile(file) instanceof GoFile goFile) {

                List<String> structList = new ArrayList<>();

                for (GoTypeSpec typeSpec : goFile.getTypes()) {
                    String structName = typeSpec.getName();
                    if (!(typeSpec.getSpecType().getType() instanceof GoStructType && structName != null))
                        continue;

                    addSchemaMapping(structName, file);
                    structList.add(structName);

                    String tableName = findTableName(typeSpec);

                    if (tableName.isEmpty()) {
                        String structInitialisms = structName;
                        for (String word : Strings.COMMON_INITIALISMS.keySet()) {
                            structInitialisms = structInitialisms.replace(word, word.charAt(0) + word.substring(1).toLowerCase());
                        }

                        tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, structInitialisms);

                        String plural = English.plural(tableName);
                        if (!plural.equals(tableName)) {
                            this.state.tableStructMapping.put(plural, structName);
                        }
                    }

                    this.state.tableStructMapping.put(tableName, structName);
                }

                addScannedPathMapping(file, structList);
            }
        });
    }

    public void addSchemaMapping(String key, @NotNull VirtualFile file) {
        String fileUrl = file.getUrl();

        if (this.state.schemaMapping.containsKey(key)) {
            this.state.schemaMapping.get(key).add(fileUrl);
        } else {
            Set<String> list = new HashSet<>();
            list.add(fileUrl);
            this.state.schemaMapping.put(key, list);
        }
    }

    private String findTableName(@NotNull GoTypeSpec typeSpec) {
        for (GoNamedSignatureOwner method : typeSpec.getAllMethods()) {
            if (method.getName() != null && method.getName().equals(Types.TABLE_NAME_FUNC) && method instanceof GoMethodDeclaration goMethodDeclaration) {
                GoReturnStatement goReturnStatement = PsiTreeUtil.findChildOfType(goMethodDeclaration, GoReturnStatement.class);
                if (goReturnStatement == null) continue;

                Value value = goReturnStatement.getExpressionList().get(0).getValue();
                if (value != null && value.getString() != null && !value.getString().isEmpty()) {
                    return value.getString();
                }
            }
        }

        if (typeSpec.getSpecType().getType() instanceof GoStructType goStructType) {
            for (GoFieldDeclaration goFieldDeclaration : goStructType.getFieldDeclarationList()) {
                if (goFieldDeclaration.getAnonymousFieldDefinition() == null) continue;

                GoType goType = goFieldDeclaration.getAnonymousFieldDefinition().getType();

                GoTypeSpec goTypeSpec = (GoTypeSpec) goType.resolve(ResolveState.initial());

                if (goTypeSpec == null) continue;

                GoTypeSpecDescriptor descriptor = GoTypeSpecDescriptor.of(goTypeSpec, goType, true);

                if (descriptor == null) continue;

                if (descriptor.equals(GoFrameTypes.G_META) || descriptor.equals(GoFrameTypes.GMETA_META)) {
                    System.out.println("666 " + goTypeSpec.getName());
                }
            }
        }

        return "";
    }

    public void addScannedPathMapping(@NotNull VirtualFile file, List<String> structList) {
        ScannedPath scanned = new ScannedPath();
        scanned.setSchema(structList);
        scanned.setLastModified(file.getTimeStamp());
        this.state.scannedPathMapping.put(file.getUrl(), scanned);
    }

    public Map<String, ScannedPath> getScannedPathMapping() {
        return this.state.scannedPathMapping;
    }

    public Map<String, Set<String>> getSchemaMapping() {
        return this.state.schemaMapping;
    }

    public Map<String, String> getTableStructMapping() {
        return this.state.tableStructMapping;
    }

    public void reset() {
        this.state.lastTimeChecked = 0L;
    }

    static class State {
        public final Map<String, Set<String>> schemaMapping = new HashMap<>();
        public final Map<String, ScannedPath> scannedPathMapping = new HashMap<>();
        public final Map<String, String> tableStructMapping = new HashMap<>();
        public long lastTimeChecked = 0L;
    }
}
