package com.github.maiqingqiang.goormhelper.orm.xorm.codeInsights.completion;

import com.goide.GoParserDefinition;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;

public class XormCompletionContributor extends CompletionContributor {
    public XormCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement()
                        .withElementType(GoParserDefinition.Lazy.STRING_LITERALS),
                new XormColumnCompletionProvider());
    }
}
