package com.github.maiqingqiang.goormhelper.listeners;

import com.github.maiqingqiang.goormhelper.services.GoORMHelperCacheManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.startup.StartupManager;
import org.jetbrains.annotations.NotNull;

public class MyProjectManagerListener implements ProjectManagerListener {
    @Override
    public void projectOpened(@NotNull Project project) {
        intializing(project);
    }

    private void intializing(@NotNull Project project) {
        StartupManager.getInstance(project).runWhenProjectIsInitialized(() -> GoORMHelperCacheManager.getInstance(project).scan());
    }
}
