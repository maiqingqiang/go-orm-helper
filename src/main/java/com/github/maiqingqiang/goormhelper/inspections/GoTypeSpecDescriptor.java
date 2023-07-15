package com.github.maiqingqiang.goormhelper.inspections;

import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.psi.GoTypeSpec;
import com.goide.psi.impl.imports.GoImportPathUtil;
import com.goide.util.GoUtil;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GoTypeSpecDescriptor implements GoCallableDescriptor {

    private final String importPath;
    private final String name;

    public GoTypeSpecDescriptor(@NotNull String importPath, @NotNull String name) {
        this.importPath = importPath;
        this.name = name;
    }

    public static @NotNull GoTypeSpecDescriptor of(@NotNull String descriptorText) {
        int lastDot = descriptorText.lastIndexOf(".");
        if (lastDot != -1 && lastDot != 0 && lastDot != descriptorText.length() - 1) {
            return new GoTypeSpecDescriptor(descriptorText.substring(0, lastDot), descriptorText.substring(lastDot + 1));
        } else {
            throw new IllegalArgumentException("Wrong type spec name: " + descriptorText);
        }
    }

    public static @Nullable GoTypeSpecDescriptor of(@NotNull GoTypeSpec goTypeSpec, @NotNull PsiElement context, boolean ignoreVersion) {
        String name = goTypeSpec.getName();
        if (name == null) {
            return null;
        } else {
            String importPath = GoUtil.getImportPath(goTypeSpec, context);
            if (importPath == null) {
                return null;
            } else {
                importPath = ignoreVersion ? GoImportPathUtil.removeVersion(importPath) : importPath;
                return new GoTypeSpecDescriptor(importPath, name);
            }
        }
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getImportPath() {
        return importPath;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            GoTypeSpecDescriptor that = (GoTypeSpecDescriptor) o;
            return this.importPath.equals(that.importPath) && this.name.equals(that.name);
        } else {
            return false;
        }
    }
}
