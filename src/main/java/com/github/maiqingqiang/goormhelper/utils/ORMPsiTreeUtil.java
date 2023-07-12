package com.github.maiqingqiang.goormhelper.utils;

import com.goide.psi.*;
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

    public static String getText(GoTypeReferenceExpression expression) {
        if (expression != null) {
            return expression.getIdentifier().getText();
        }
        return "";
    }

    public static String getText(GoType type) {
        if (type != null) {
            return getText(type.getTypeReferenceExpression());
        }
        return "";
    }

    public static String getText(GoCompositeLit compositeLit) {
        if (compositeLit != null) {
            return getText(compositeLit.getTypeReferenceExpression());
        }
        return "";
    }

    public static boolean callHasArgumentAtIndex(@NotNull GoCallExpr call, int argumentIndex, @NotNull PsiElement argument) {
        if (argumentIndex == -1) return true;
        argument = GoPsiUtil.skipParens(argument);
        return argument == ContainerUtil.getOrElse(call.getArgumentList().getExpressionList(), argumentIndex, (Object) null);
    }
}
