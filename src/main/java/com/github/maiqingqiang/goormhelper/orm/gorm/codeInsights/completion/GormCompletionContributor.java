package com.github.maiqingqiang.goormhelper.orm.gorm.codeInsights.completion;

import com.goide.GoParserDefinition;
import com.goide.psi.GoFile;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;

public class GormCompletionContributor extends CompletionContributor {
    public GormCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement()
                        .withElementType(GoParserDefinition.Lazy.STRING_LITERALS)
                        .inFile(StandardPatterns.instanceOf(GoFile.class)),
                new GormColumnCompletionProvider());
    }
}
