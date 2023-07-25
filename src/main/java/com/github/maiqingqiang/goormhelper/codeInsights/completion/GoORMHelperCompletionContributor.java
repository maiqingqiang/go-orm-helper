package com.github.maiqingqiang.goormhelper.codeInsights.completion;

import com.github.maiqingqiang.goormhelper.orm.ORMCompletionProvider;
import com.github.maiqingqiang.goormhelper.orm.goframe.codeInsights.completion.GoFrameColumnCompletionProvider;
import com.github.maiqingqiang.goormhelper.orm.gorm.codeInsights.completion.GormColumnCompletionProvider;
import com.github.maiqingqiang.goormhelper.orm.xorm.codeInsights.completion.XormColumnCompletionProvider;
import com.goide.GoParserDefinition;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;

import java.util.List;

public class GoORMHelperCompletionContributor extends CompletionContributor {

    public GoORMHelperCompletionContributor() {
        List<ORMCompletionProvider> providers = List.of(
                new GormColumnCompletionProvider(),
                new XormColumnCompletionProvider(),
                new GoFrameColumnCompletionProvider()
        );

        for (ORMCompletionProvider provider : providers) {
            extend(CompletionType.BASIC,
                    PlatformPatterns.psiElement().withElementType(GoParserDefinition.Lazy.STRING_LITERALS),
                    provider);
        }
    }
}
