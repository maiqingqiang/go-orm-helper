package com.github.maiqingqiang.goormhelper.services;

import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.bean.ScannedPath;
import com.goide.GoFileType;
import com.goide.psi.*;
import com.goide.util.Value;
import com.google.common.base.CaseFormat;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SimpleModificationTracker;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.serviceContainer.NonInjectable;
import org.atteo.evo.inflector.English;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Service(Service.Level.PROJECT)
@State(name = "GoORMHelper", storages = @Storage(value = "goORMHelper.xml"))
public class GoORMHelperManager implements PersistentStateComponent<GoORMHelperManager.State> {

    private State state;

    private final Project project;


    public GoORMHelperManager(@NotNull Project project) {
        this(project, new State());
    }

    @NonInjectable
    private GoORMHelperManager(@NotNull Project project, State state) {
        this.state = state;
        this.project = project;
    }

    public static GoORMHelperManager getInstance(@NotNull Project project) {
        return project.getService(GoORMHelperManager.class);
    }

    public void addSchemaMapping(String key, @NotNull VirtualFile file) {
        String fileUrl = file.getUrl();

        if (this.state.schemaMapping.containsKey(key)) {
            this.state.schemaMapping.get(key).add(fileUrl);
        } else {
            List<String> list = new ArrayList<>();
            list.add(fileUrl);
            this.state.schemaMapping.put(key, list);
        }
    }

    public void addScannedPathMapping(@NotNull VirtualFile file, List<String> structList) {
        ScannedPath scanned = new ScannedPath();
        scanned.setSchema(structList);
        scanned.setLastModified(file.getTimeStamp());
        this.state.scannedPathMapping.put(file.getUrl(), scanned);
    }

    public ScannedPath getScannedPath(@NotNull VirtualFile file) {
        return this.state.scannedPathMapping.get(file.getUrl());
    }

    public void removeScannedPath(@NotNull VirtualFile file) {
        this.state.scannedPathMapping.remove(file.getUrl());
    }

    public void clearScanned(@NotNull VirtualFile file) {
        ScannedPath scannedPath = getScannedPath(file);
        for (String s : scannedPath.getSchema()) {
            this.state.schemaMapping.remove(s);
        }
        removeScannedPath(file);
    }

    public void clear() {
        this.state.scannedPathMapping.clear();
        this.state.schemaMapping.clear();
    }

    @Override
    public @Nullable State getState() {
        return this.state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
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

                    String asTableName = null;
                    for (GoNamedSignatureOwner method : typeSpec.getAllMethods()) {
                        if (method.getName() != null && method.getName().equals(Types.TABLE_NAME_FUNC) && method instanceof GoMethodDeclaration goMethodDeclaration) {
                            GoReturnStatement goReturnStatement = PsiTreeUtil.findChildOfType(goMethodDeclaration, GoReturnStatement.class);
                            if (goReturnStatement == null) continue;

                            Value value = goReturnStatement.getExpressionList().get(0).getValue();
                            if (value != null) {
                                asTableName = value.getString();
                            }
                        }
                    }

                    if (asTableName == null || asTableName.isEmpty()) {
                        asTableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, structName);

                        String plural = English.plural(asTableName);
                        if (!plural.equals(asTableName)) {
                            this.state.tableStructMapping.put(plural, structName);
                        }
                    }

                    this.state.tableStructMapping.put(asTableName, structName);
                }

                addScannedPathMapping(file, structList);
            }
        });
    }

    public void scanProject(VirtualFile virtualFile) {
        for (VirtualFile file : virtualFile.getChildren()) {
            if (!file.isValid()) continue;

            if (file.isDirectory()) {
                scanProject(file);
            } else {
                parseGoFile(file);
            }
        }
    }

    public void scan() {
        GoORMHelperProjectSettings.State state = Objects.requireNonNull(GoORMHelperProjectSettings.getInstance(project).getState());

        if (state.enableGlobalScan) {
            scanProject(project.getBaseDir());
        } else {
            for (String path : state.scanPathList) {
                VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(path);
                if (file == null) continue;
                scanProject(file);
            }
        }
    }

    public static class State extends SimpleModificationTracker {
        public final Map<String, List<String>> schemaMapping = new HashMap<>();

        public final Map<String, ScannedPath> scannedPathMapping = new HashMap<>();

        public final Map<String, String> tableStructMapping = new HashMap<>();
    }
}
