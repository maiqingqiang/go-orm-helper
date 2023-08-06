package com.github.maiqingqiang.goormhelper.orm.gorm.codeInsights.completion;

import com.github.maiqingqiang.goormhelper.orm.ORMCompletionProvider;
import com.github.maiqingqiang.goormhelper.orm.gorm.GormTypes;
import com.github.maiqingqiang.goormhelper.ui.Icons;
import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.inspections.core.GoCallableDescriptorSet;
import com.goide.psi.GoFieldDeclaration;
import com.goide.psi.GoTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;

public class GormColumnCompletionProvider extends ORMCompletionProvider {

    public static @NotNull Map<String, String> parseTag(@NotNull String str) {
        Map<String, String> settings = new HashMap<>();
        String[] names = str.split(";");

        for (int i = 0; i < names.length; i++) {
            int j = i;
            if (!names[j].isEmpty()) {
                while (names[j].charAt(names[j].length() - 1) == '\\') {
                    i++;
                    names[j] = names[j].substring(0, names[j].length() - 1) + ";" + names[i];
                    names[i] = "";
                }
            }

            String[] values = names[j].split(":");
            String k = values[0].trim().toUpperCase();

            if (values.length >= 2) {
                settings.put(k, String.join(":", Arrays.copyOfRange(values, 1, values.length)));
            } else if (!k.isEmpty()) {
                settings.put(k, k);
            }
        }

        return settings;
    }

    @Override
    public Map<GoCallableDescriptor, Integer> callables() {
        return GormTypes.CALLABLES;
    }

    @Override
    public GoCallableDescriptorSet callablesSet() {
        return GormTypes.CALLABLES_SET;
    }

    @Override
    public Map<GoCallableDescriptor, Integer> schemaCallables() {
        return GormTypes.SCHEMA_CALLABLES;
    }

    @Override
    public GoCallableDescriptorSet schemaCallablesSet() {
        return GormTypes.SCHEMA_CALLABLES_SET;
    }

    @Override
    public @Nullable Map<GoCallableDescriptor, Integer> otherSchemaCallables() {
        return GormTypes.OTHER_SCHEMA_CALLABLES;
    }

    @Override
    public @Nullable GoCallableDescriptorSet otherSchemaCallablesSet() {
        return GormTypes.OTHER_SCHEMA_CALLABLES_SET;
    }

    @Override
    public Map<GoCallableDescriptor, List<String>> queryExpr() {
        return GormTypes.QUERY_EXPR;
    }

    @Override
    protected Icon getIcon() {
        return Icons.Gorm35x12;
    }

    @Override
    public String getColumn(@NotNull GoFieldDeclaration field) {
        String column = "";
        GoTag tag = field.getTag();
        if (tag != null && tag.getValue("gorm") != null) {
            Map<String, String> tagMap = parseTag(Objects.requireNonNull(tag.getValue("gorm")));

            if (tagMap.containsKey("COLUMN")) {
                column = tagMap.get("COLUMN");
            }
        }
        return column;
    }

    @Override
    public String getComment(@NotNull GoFieldDeclaration field) {
        String comment = "";
        GoTag tag = field.getTag();
        if (tag != null && tag.getValue("gorm") != null) {
            Map<String, String> tagMap = parseTag(Objects.requireNonNull(tag.getValue("gorm")));

            if (tagMap.containsKey("COMMENT")) {
                comment = tagMap.get("COMMENT");
            }
        }
        return comment;
    }

    @Override
    public @NotNull Set<GoCallableDescriptor> allowTypes() {
        return GormTypes.ALLOW_TYPES;
    }
}
