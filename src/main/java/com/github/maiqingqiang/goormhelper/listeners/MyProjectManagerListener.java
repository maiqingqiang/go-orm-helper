package com.github.maiqingqiang.goormhelper.listeners;

import com.github.maiqingqiang.goormhelper.GoORMHelperBundle;
import com.github.maiqingqiang.goormhelper.services.GoORMHelperCacheManager;
import com.goide.completion.imports.GoSettingsImportsFilter;
import com.goide.sdk.GoSdkUtil;
import com.goide.vendor.GoVendorExcludePolicy;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.progress.BackgroundTaskQueue;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MyProjectManagerListener implements ProjectManagerListener {
    private static final Logger LOG = Logger.getInstance(MyProjectManagerListener.class);

    @Override
    public void projectOpened(@NotNull Project project) {
        intializing(project);
    }

    private void intializing(@NotNull Project project) {
        GoORMHelperCacheManager.getInstance(project).scan();
    }
}
