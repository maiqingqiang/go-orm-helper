package com.github.maiqingqiang.goormhelper.codeInsights.completion;

import com.goide.GoLanguage;
import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class AnnotationTypedHandler extends TypedHandlerDelegate {
    @Override
    public @NotNull Result checkAutoPopup(char charTyped, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        System.out.println("checkAutoPopup");

        if (LookupManager.getActiveLookup(editor) != null) return Result.CONTINUE;
        if (file.getLanguage() != GoLanguage.INSTANCE) return Result.CONTINUE;

        PsiElement element = file.findElementAt(editor.getCaretModel().getOffset()-1);

        if (element instanceof PsiComment && charTyped == '@') {
            AutoPopupController.getInstance(project).scheduleAutoPopup(editor);
            return Result.STOP;
        }

        return super.checkAutoPopup(charTyped, project, editor, file);
    }

//    @Override
//    public @NotNull Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
//        System.out.println("charTyped");
//
//        if (LookupManager.getActiveLookup(editor) != null) return Result.CONTINUE;
//        if (file.getLanguage() != GoLanguage.INSTANCE) return Result.CONTINUE;
//        PsiElement element = file.findElementAt(editor.getCaretModel().getOffset()-1);
//
//        if (element instanceof PsiComment && c == '@') {
//            AutoPopupController.getInstance(project).scheduleAutoPopup(editor);
//            return Result.STOP;
//        }
//
//        return super.charTyped(c, project, editor, file);
//    }
//
//    @Override
//    public @NotNull Result beforeCharTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file, @NotNull FileType fileType) {
//        System.out.println("beforeCharTyped");
//        return super.beforeCharTyped(c, project, editor, file, fileType);
//    }
}
