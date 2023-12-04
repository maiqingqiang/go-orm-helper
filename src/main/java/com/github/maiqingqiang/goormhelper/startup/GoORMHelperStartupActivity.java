package com.github.maiqingqiang.goormhelper.startup;

import com.github.maiqingqiang.goormhelper.services.GoORMHelperCacheManager;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

public class GoORMHelperStartupActivity implements StartupActivity, DumbAware {
    @Override
    public void runActivity(@NotNull Project project) {
        ReadAction.run(() -> GoORMHelperCacheManager.getInstance(project).scan());
    }
}
