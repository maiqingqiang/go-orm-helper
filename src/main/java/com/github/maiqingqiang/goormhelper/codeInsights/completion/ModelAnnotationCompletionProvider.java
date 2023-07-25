package com.github.maiqingqiang.goormhelper.codeInsights.completion;

import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.ui.Icons;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class ModelAnnotationCompletionProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        System.out.println(result.getPrefixMatcher().getPrefix());

    }


}
