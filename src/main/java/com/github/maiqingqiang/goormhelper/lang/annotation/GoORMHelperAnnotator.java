package com.github.maiqingqiang.goormhelper.lang.annotation;

import com.github.maiqingqiang.goormhelper.Types;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoORMHelperAnnotator implements Annotator {
    final List<String> annotations = List.of(Types.MODEL_ANNOTATION, Types.TABLE_ANNOTATION);

    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof PsiComment comment)) return;

        String text = comment.getText();
        if (text == null || (!text.contains(Types.MODEL_ANNOTATION) && !text.contains(Types.TABLE_ANNOTATION))) return;

        for (String annotation : annotations) {
            int index = comment.getText().indexOf(annotation);
            if (index == -1) {
                continue;
            }
            Matcher matcher;
            if (!(matcher = Pattern.compile(annotation + "\\((.*?)\\)").matcher(comment.getText())).find()) {
                continue;
            }
            int startOffset = element.getTextRange().getStartOffset() + index;
            int annotationLength = annotation.length();

            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(DefaultLanguageHighlighterColors.METADATA)
                    .range(TextRange.from(startOffset, annotationLength))
                    .create();

            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(DefaultLanguageHighlighterColors.IDENTIFIER)
                    .range(TextRange.from(startOffset + annotationLength, matcher.group(0).length() - annotationLength))
                    .create();
        }

    }
}
