package com.github.maiqingqiang.goormhelper.orm.goframe.codeInsights.completion;

import com.github.maiqingqiang.goormhelper.orm.ORMCompletionProvider;
import com.github.maiqingqiang.goormhelper.orm.goframe.GoFrameTypes;
import com.github.maiqingqiang.goormhelper.ui.Icons;
import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.inspections.core.GoCallableDescriptorSet;
import com.goide.psi.GoFieldDeclaration;
import com.goide.psi.GoTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GoFrameColumnCompletionProvider extends ORMCompletionProvider {

    @Override
    public Map<GoCallableDescriptor, Integer> callables() {
        return GoFrameTypes.CALLABLES;
    }

    @Override
    public GoCallableDescriptorSet callablesSet() {
        return GoFrameTypes.CALLABLES_SET;
    }

    @Override
    public Map<GoCallableDescriptor, Integer> schemaCallables() {
        return GoFrameTypes.SCHEMA_CALLABLES;
    }

    @Override
    public GoCallableDescriptorSet schemaCallablesSet() {
        return GoFrameTypes.SCHEMA_CALLABLES_SET;
    }

    @Override
    public @Nullable Map<GoCallableDescriptor, Integer> otherSchemaCallables() {
        return null;
    }

    @Override
    public @Nullable GoCallableDescriptorSet otherSchemaCallablesSet() {
        return null;
    }

    @Override
    public Map<GoCallableDescriptor, List<String>> queryExpr() {
        return GoFrameTypes.QUERY_EXPR;
    }

    @Override
    public String getColumn(@NotNull GoFieldDeclaration field) {
        String column = "";
        GoTag tag = field.getTag();
        if (tag != null && tag.getValue("orm") != null) {
            String[] vals = parseTag(Objects.requireNonNull(tag.getValue("orm")));
            if (vals.length > 0) {
                column = vals[0];
            }
        }
        return column;
    }

    @Override
    public String getComment(@NotNull GoFieldDeclaration field) {
        return "";
    }

    @Override
    public @NotNull String allowType() {
        return GoFrameTypes.ALLOW_TYPE;
    }

    @Override
    protected Icon getIcon() {
        return Icons.GoFrame18x12;
    }

    private static String[] parseTag(String tagStr) {
        return tagStr.split(",");
    }
}
