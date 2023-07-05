package com.github.maiqingqiang.goormhelper.orm.xorm.codeInsights.completion;

import com.github.maiqingqiang.goormhelper.orm.ORMCompletionProvider;
import com.github.maiqingqiang.goormhelper.orm.xorm.XormTypes;
import com.github.maiqingqiang.goormhelper.ui.Icons;
import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.inspections.core.GoCallableDescriptorSet;
import com.goide.psi.GoFieldDeclaration;
import com.goide.psi.GoTag;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XormColumnCompletionProvider extends ORMCompletionProvider {

    @lombok.Data
    static class Tag {
        private String name;
        private List<String> params;

        public Tag(String name, List<String> params) {
            this.name = name;
            this.params = params;
        }
    }

    @Override
    public Map<GoCallableDescriptor, Integer> callables() {
        return XormTypes.CALLABLES;
    }

    @Override
    public GoCallableDescriptorSet callablesSet() {
        return XormTypes.CALLABLES_SET;
    }

    @Override
    public Map<GoCallableDescriptor, Integer> schemaCallables() {
        return XormTypes.SCHEMA_CALLABLES;
    }

    @Override
    public GoCallableDescriptorSet schemaCallablesSet() {
        return XormTypes.SCHEMA_CALLABLES_SET;
    }

    @Override
    public Map<GoCallableDescriptor, Integer> tableCallables() {
        return XormTypes.TABLE_CALLABLES;
    }

    @Override
    public GoCallableDescriptorSet tableCallablesSet() {
        return XormTypes.TABLE_CALLABLES_SET;
    }

    @Override
    public Map<GoCallableDescriptor, List<String>> queryExpr() {
        return XormTypes.QUERY_EXPR;
    }

    @Override
    public String getColumn(@NotNull GoFieldDeclaration field) {
        String column = "";
        GoTag tag = field.getTag();
        if (tag != null && tag.getValue("xorm") != null) {
            @NotNull List<Tag> tags = parseTag(tag.getValue("xorm"));

            for (Tag t : tags) {
                if (t.getName().startsWith("'") && t.getParams().size() == 0) {
                    column = t.getName().replaceAll("'", "");
                }
            }
        }
        return column;
    }

    @Override
    public String getComment(@NotNull GoFieldDeclaration field) {
        String comment = "";

        GoTag tag = field.getTag();
        if (tag != null && tag.getValue("xorm") != null) {
            @NotNull List<Tag> tags = parseTag(tag.getValue("xorm"));

            for (Tag t : tags) {
                if (t.getName().equals("comment") && t.getParams().size() > 0) {
                    comment = t.getParams().get(0).replaceAll("'", "");
                }
            }
        }

        return comment;
    }

    @Override
    protected Icon getIcon() {
        return Icons.Xorm19x12;
    }

    private static @NotNull List<Tag> parseTag(String tagStr) {
        tagStr = tagStr.trim();
        boolean inQuote = false;
        boolean inBigQuote = false;
        int lastIdx = 0;
        Tag curTag = null;
        int paramStart = 0;
        List<Tag> tags = new ArrayList<>();

        for (int i = 0; i < tagStr.length(); i++) {
            char t = tagStr.charAt(i);
            switch (t) {
                case '\'' -> inQuote = !inQuote;
                case ' ' -> {
                    if (!inQuote && !inBigQuote) {
                        if (lastIdx < i) {
                            if (curTag == null || curTag.getName().isEmpty()) {
                                curTag = new Tag(tagStr.substring(lastIdx, i), new ArrayList<>());
                            }
                            tags.add(curTag);
                            lastIdx = i + 1;
                            curTag = null;
                        } else if (lastIdx == i) {
                            lastIdx = i + 1;
                        }
                    } else if (inBigQuote && !inQuote) {
                        paramStart = i + 1;
                    }
                }
                case ',' -> {
                    if (!inQuote && !inBigQuote) {
                        throw new IllegalArgumentException("Comma[" + i + "] of " + tagStr + " should be in quote or big quote");
                    }
                    if (!inQuote) {
                        curTag.getParams().add(tagStr.substring(paramStart, i).trim());
                        paramStart = i + 1;
                    }
                }
                case '(' -> {
                    inBigQuote = true;
                    if (!inQuote) {
                        curTag = new Tag(tagStr.substring(lastIdx, i), new ArrayList<>());
                        paramStart = i + 1;
                    }
                }
                case ')' -> {
                    inBigQuote = false;
                    if (!inQuote && curTag != null) {
                        curTag.getParams().add(tagStr.substring(paramStart, i));
                    }
                }
            }
        }

        if (lastIdx < tagStr.length()) {
            if (curTag == null || curTag.getName().isEmpty()) {
                curTag = new Tag(tagStr.substring(lastIdx), new ArrayList<>());
            }
            tags.add(curTag);
        }

        return tags;
    }
}
