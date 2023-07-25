package com.github.maiqingqiang.goormhelper.codeInsights.completion;

import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.ui.Icons;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class AnnotationCompletionProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        for (String annotation : Types.ANNOTATION_LIST) {
            if (annotation.contains(result.getPrefixMatcher().getPrefix())) {
                result.addElement(LookupElementBuilder
                        .create(annotation)
                        .withLookupString(annotation)
                        .withPresentableText(annotation)
                        .withIcon(Icons.Icon12x12));
            }
        }
    }


}
