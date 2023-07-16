package com.github.maiqingqiang.goormhelper.orm.goframe.codeInsights.completion;

import com.github.maiqingqiang.goormhelper.orm.goframe.GoFrameTypes;
import com.goide.GoParserDefinition;
import com.goide.psi.GoCallExpr;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.Nullable;

public class GoFrameCompletionContributor extends CompletionContributor {
    public GoFrameCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement()
                        .withElementType(GoParserDefinition.Lazy.STRING_LITERALS),
                new GoFrameColumnCompletionProvider());
    }
}
