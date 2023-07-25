package com.github.maiqingqiang.goormhelper.listeners;

import com.github.maiqingqiang.goormhelper.bean.ScannedPath;
import com.github.maiqingqiang.goormhelper.services.GoORMHelperCacheManager;
import com.goide.GoFileType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SchemaFileListener implements BulkFileListener {
    private static final Logger LOG = Logger.getInstance(SchemaFileListener.class);

    private final Project project;

    public SchemaFileListener(Project project) {
        this.project = project;
    }

    @Override
    public void after(@NotNull List<? extends @NotNull VFileEvent> events) {
        for (VFileEvent event : events) {
            if (event.isFromSave() && event.getFile() != null && event.getFile().isValid() && event.getPath().endsWith('.' + GoFileType.DEFAULT_EXTENSION)) {
                GoORMHelperCacheManager manager = GoORMHelperCacheManager.getInstance(this.project);

                VirtualFile file = event.getFile();

                ScannedPath scanned = manager.getScannedPathMapping().get(file.getUrl());

                if (scanned != null) {
                    scanned.getSchema().forEach(s -> manager.getTableStructMapping().remove(s));
                }

                GoORMHelperCacheManager.getInstance(this.project).parseGoFile(event.getFile());
            }
        }
    }
}
