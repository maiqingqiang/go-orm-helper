package com.github.maiqingqiang.goormhelper.orm.goframe.codeInsights.completion;

import com.github.maiqingqiang.goormhelper.inspections.GoTypeSpecDescriptor;
import com.github.maiqingqiang.goormhelper.orm.ORMCompletionProvider;
import com.github.maiqingqiang.goormhelper.orm.goframe.GoFrameTypes;
import com.github.maiqingqiang.goormhelper.ui.Icons;
import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.inspections.core.GoCallableDescriptorSet;
import com.goide.psi.*;
import com.goide.psi.impl.GoPsiUtil;
import com.intellij.psi.ResolveState;
import com.intellij.util.ObjectUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GoFrameColumnCompletionProvider extends ORMCompletionProvider {

    @Contract(pure = true)
    private static String @NotNull [] parseTag(@NotNull String tagStr) {
        return tagStr.split(",");
    }

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
    public @NotNull Set<GoCallableDescriptor> allowTypes() {
        return GoFrameTypes.ALLOW_TYPES;
    }

    @Override
    protected Icon getIcon() {
        return Icons.GoFrame18x12;
    }

    @Override
    protected boolean checkGoFieldDeclaration(GoFieldDeclaration field) {
        if (field.getAnonymousFieldDefinition() == null) return true;

        GoType goType = field.getAnonymousFieldDefinition().getType();

        GoTypeSpec goTypeSpec = (GoTypeSpec) goType.resolve(ResolveState.initial());

        if (goTypeSpec == null) return true;

        GoTypeSpecDescriptor descriptor = GoTypeSpecDescriptor.of(goTypeSpec, goType, true);

        if (descriptor == null) return true;

        return !descriptor.equals(GoFrameTypes.G_META) && !descriptor.equals(GoFrameTypes.GMETA_META);
    }

    @Override
    protected GoCompositeElement customFindGoCompositeElementByGoCallExpr(GoCallExpr goCallExpr) {
        return findGoTypeByReceiver(goCallExpr, allowTypes());
    }

    private @Nullable GoType findGoTypeByReceiver(GoCallExpr goCallExpr, Set<GoCallableDescriptor> allowTypes) {
        GoReferenceExpression callRef = GoPsiUtil.getCallReference(goCallExpr);
        String refName = callRef != null ? callRef.getIdentifier().getText() : null;

        if (callRef != null && GoFrameTypes.OTHER_SCHEMA_CALLABLES.contains(refName)) {
            GoMethodDeclaration declaration = ObjectUtils.tryCast(callRef.resolve(), GoMethodDeclaration.class);
            if (declaration != null) {
                GoType resultType = declaration.getResultType();

                if (resultType instanceof GoPointerType goPointerType) {
                    resultType = goPointerType.getType();
                }

                if (resultType != null && resultType.resolve(ResolveState.initial()) instanceof GoTypeSpec goTypeSpec) {
                    GoTypeSpecDescriptor descriptor = GoTypeSpecDescriptor.of(goTypeSpec, resultType, true);

                    if (allowTypes != null && allowTypes.contains(descriptor) && declaration.getReceiver() != null && declaration.getReceiver().getType() instanceof GoPointerType goPointerType) {
                        GoType goType = goPointerType.getType();
                        if (goType != null && goType.resolve(ResolveState.initial()) instanceof GoTypeSpec spec &&
                                spec.getSpecType().getType() instanceof GoStructType goStructType) {

                            for (GoFieldDeclaration field : goStructType.getFieldDeclarationList()) {
                                if (GoFrameTypes.ALLOW_FIELDS.contains(field.getFieldDefinitionList().get(0).getName()) && field.getType() != null) {
                                    return field.getType();
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected GoCompositeElement findAgainArgument(GoCompositeElement argument) {
        if (argument instanceof GoCallExpr goCallExpr) {
            GoType newArgument = findGoTypeByReceiver(goCallExpr, Set.of(GoTypeSpecDescriptor.of("builtin.string")));
            if (newArgument != null) return newArgument;
        }
        return argument;
    }

}
