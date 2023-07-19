package com.github.maiqingqiang.goormhelper.utils;

import com.goide.psi.GoArrayOrSliceType;
import com.goide.psi.GoCallExpr;
import com.goide.psi.GoPointerType;
import com.goide.psi.GoType;
import com.goide.psi.impl.GoPsiUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ORMPsiTreeUtil {
    public static @Nullable <T extends PsiElement> T findLastChildOfType(@Nullable PsiElement element, @NotNull Class<T> aClass) {
        T result = null;
        for (T child : PsiTreeUtil.findChildrenOfType(element, aClass)) {
            result = child;
        }

        return result;
    }

    public static boolean callHasArgumentAtIndex(@NotNull GoCallExpr call, int argumentIndex, @NotNull PsiElement argument) {
        if (argumentIndex == -1) return true;
        argument = GoPsiUtil.skipParens(argument);
        return argument == ContainerUtil.getOrElse(call.getArgumentList().getExpressionList(), argumentIndex, (Object) null);
    }

    public static GoType getReallyGoType(GoType goType) {
        if (goType instanceof GoArrayOrSliceType goArrayOrSliceType) {
            goType = goArrayOrSliceType.getType();
        }

        if (goType instanceof GoPointerType goPointerType) {
            goType = goPointerType.getType();
        }

        return goType;
    }
}
