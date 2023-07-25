package com.github.maiqingqiang.goormhelper.codeInsights.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiComment;

public class AnnotationCompletionContributor extends CompletionContributor {
    public AnnotationCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().inside(PsiComment.class),
                new AnnotationCompletionProvider());
    }
}
