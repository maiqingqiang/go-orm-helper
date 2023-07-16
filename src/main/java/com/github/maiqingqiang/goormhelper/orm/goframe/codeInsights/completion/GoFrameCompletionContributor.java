package com.github.maiqingqiang.goormhelper.orm.goframe.codeInsights.completion;

import com.goide.GoParserDefinition;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;

public class GoFrameCompletionContributor extends CompletionContributor {
    public GoFrameCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement()
                        .withElementType(GoParserDefinition.Lazy.STRING_LITERALS),
                new GoFrameColumnCompletionProvider());
    }
}
