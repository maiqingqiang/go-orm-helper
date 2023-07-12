package com.github.maiqingqiang.goormhelper.orm;

import com.github.maiqingqiang.goormhelper.Types;
import com.github.maiqingqiang.goormhelper.services.GoORMHelperManager;
import com.github.maiqingqiang.goormhelper.ui.Icons;
import com.github.maiqingqiang.goormhelper.utils.ORMPsiTreeUtil;
import com.github.maiqingqiang.goormhelper.utils.Strings;
import com.goide.documentation.GoDocumentationProvider;
import com.goide.inspections.core.GoCallableDescriptor;
import com.goide.inspections.core.GoCallableDescriptorSet;
import com.goide.psi.*;
import com.google.common.base.CaseFormat;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;

public abstract class ORMCompletionProvider extends CompletionProvider<CompletionParameters> {
    private static final Logger LOG = Logger.getInstance(ORMCompletionProvider.class);

    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

        PsiElement currentElement = parameters.getPosition();

        Project project = currentElement.getProject();

        GoCallExpr goCallExpr = (GoCallExpr) PsiTreeUtil.findFirstParent(currentElement, e -> e instanceof GoCallExpr);

        if (goCallExpr == null) return;

        GoCallableDescriptor descriptor = callablesSet().find(goCallExpr, false);
        if (descriptor == null) return;

        Integer argumentIndex = callables().get(descriptor);

        if (!ORMPsiTreeUtil.callHasArgumentAtIndex(goCallExpr, argumentIndex, currentElement.getParent()) && !(currentElement.getParent().getParent() instanceof GoKey))
            return;

        String schema = findSchema(currentElement);

        if (schema == null || schema.isEmpty()) return;

        List<String> pathList = Objects.requireNonNull(GoORMHelperManager.getInstance(project).getState()).schemaMapping.get(schema);

        if (pathList == null) return;

        for (String path : pathList) {

            VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(path);
            if (file == null) continue;

            GoFile goFile = (GoFile) PsiManager.getInstance(project).findFile(file);
            if (goFile == null) continue;

            for (GoTypeDeclaration goTypeDeclaration : goFile.findChildrenByClass(GoTypeDeclaration.class)) {
                GoTypeSpec goTypeSpec = goTypeDeclaration.getTypeSpecList().get(0);

                if (!Objects.equals(goTypeSpec.getName(), schema)) continue;

                scanFields(descriptor, result, goTypeSpec);
            }
        }
    }

    private String findSchema(PsiElement currentElement) {
        GoCompositeElement argument = findTargetGoCompositeElement(currentElement);
        return parseArgument(argument);
    }

    private GoCompositeElement findTargetGoCompositeElement(@NotNull PsiElement currentElement) {
        GoCompositeElement element = findGoCompositeElement(currentElement, schemaCallablesSet(), schemaCallables());

        if (element == null) element = findGoCompositeElement(currentElement, otherSchemaCallablesSet(), otherSchemaCallables());

        if (element == null) {
            GoCallExpr goCallExpr = (GoCallExpr) PsiTreeUtil.findFirstParent(currentElement, e -> e instanceof GoCallExpr);
            if (goCallExpr != null) {
                GoReferenceExpression goReferenceExpression = ORMPsiTreeUtil.findLastChildOfType(goCallExpr, GoReferenceExpression.class);
                if (goReferenceExpression != null && goReferenceExpression.resolve() != null) {
                    GoType goType = goReferenceExpression.getGoType(ResolveState.initial());
                    if (goType != null && goType.getPresentationText().equals(allowType())) {
                        PsiElement resolved = goReferenceExpression.resolve();
                        if (resolved != null) {
                            element = findTargetGoCompositeElement(resolved);
                        }
                    }
                }
            }
        }

        return element;
    }

    private @Nullable String parseArgument(GoCompositeElement argument) {
        LOG.debug("argument: " + argument);

        if (argument instanceof GoStatement) {
            String comment = GoDocumentationProvider.getCommentText(GoDocumentationProvider.getCommentsForElement(argument), false);
            Matcher matcher;

            if ((matcher = Types.MODEL_ANNOTATION_PATTERN.matcher(comment)).find()) {
                return matcher.group(1);
            }

            if ((matcher = Types.TABLE_ANNOTATION_PATTERN.matcher(comment)).find()) {
                String table = matcher.group(1);
                GoORMHelperManager.State state = Objects.requireNonNull(GoORMHelperManager.getInstance(argument.getProject()).getState());
                return state.tableStructMapping.get(table);
            }
        } else if (argument instanceof GoExpression) {
            if (argument instanceof GoUnaryExpr goUnaryExpr) {
                if (goUnaryExpr.getExpression() instanceof GoCompositeLit goCompositeLit) {
                    return ORMPsiTreeUtil.getText(goCompositeLit);
                } else if (goUnaryExpr.getExpression() instanceof GoReferenceExpression goReferenceExpression) {
                    if (goReferenceExpression.resolve() instanceof GoVarDefinition goVarDefinition) {
                        return ORMPsiTreeUtil.getText(goVarDefinition.getGoType(ResolveState.initial()));
                    } else if (goReferenceExpression.resolve() instanceof GoParamDefinition goParamDefinition) {
                        GoPointerType goPointerType = PsiTreeUtil.findChildOfType(goParamDefinition.getParent(), GoPointerType.class);
                        return ORMPsiTreeUtil.getText(PsiTreeUtil.findChildOfType(goPointerType, GoType.class));
                    }
                }
            } else if (argument instanceof GoBuiltinCallExpr goBuiltinCallExpr) {
                return ORMPsiTreeUtil.getText(PsiTreeUtil.findChildOfType(goBuiltinCallExpr, GoType.class));
            } else if (argument instanceof GoReferenceExpression goReferenceExpression && goReferenceExpression.resolve() instanceof GoVarDefinition goVarDefinition) {
                GoType goType = PsiTreeUtil.findChildOfAnyType(goVarDefinition.getParent(), GoType.class);
                if (goType != null) {
                    return ORMPsiTreeUtil.getText(goType);
                } else {
                    return ORMPsiTreeUtil.getText(PsiTreeUtil.findChildOfType(goVarDefinition.getParent(), GoCompositeLit.class));
                }
            } else if (argument instanceof GoStringLiteral goStringLiteral) {
                GoORMHelperManager.State state = Objects.requireNonNull(GoORMHelperManager.getInstance(argument.getProject()).getState());
                return state.tableStructMapping.get(goStringLiteral.getDecodedText());
            }
        }

        return null;
    }

    private @Nullable GoCompositeElement findGoCompositeElementByStatement(GoStatement statement, GoCallableDescriptorSet callablesSet, Map<GoCallableDescriptor, Integer> callables) {

        String comment = GoDocumentationProvider.getCommentText(GoDocumentationProvider.getCommentsForElement(statement), false);

        if ((Types.MODEL_ANNOTATION_PATTERN.matcher(comment)).find()) {
            return statement;
        }

        if ((Types.TABLE_ANNOTATION_PATTERN.matcher(comment)).find()) {
            return statement;
        }

        for (GoCallExpr goCallExpr : PsiTreeUtil.findChildrenOfType(statement, GoCallExpr.class)) {
            GoCallableDescriptor descriptor = callablesSet.find(goCallExpr, false);
            if (descriptor == null) continue;

            Integer argumentIndex = callables.get(descriptor);

            List<GoExpression> expressionList = goCallExpr.getArgumentList().getExpressionList();
            if (expressionList.size() >= argumentIndex + 1) return expressionList.get(argumentIndex);
        }

        return null;
    }

    private @Nullable GoCompositeElement findGoCompositeElement(@NotNull PsiElement currentElement, GoCallableDescriptorSet callablesSet, Map<GoCallableDescriptor, Integer> callables) {
        if (callables == null || callablesSet == null) return null;

        GoStatement currentStatement = (GoStatement) PsiTreeUtil.findFirstParent(currentElement, e -> e instanceof GoStatement);

        GoCompositeElement argument = findGoCompositeElementByStatement(currentStatement, callablesSet, callables);
        if (argument != null) return argument;

        if (currentStatement instanceof GoAssignmentStatement goAssignmentStatement) {
            for (GoExpression goExpression : goAssignmentStatement.getLeftHandExprList().getExpressionList()) {
                GoType goType = goExpression.getGoType(ResolveState.initial());
                if (goType != null && goType.getPresentationText().equals(allowType()) && goExpression.getReference() != null) {
                    PsiElement resolved = goExpression.getReference().resolve();
                    if (resolved != null) {
                        argument = findGoCompositeElement(resolved, callablesSet, callables);
                        if (argument != null) return argument;
                    }
                }
            }
        } else if (currentStatement instanceof GoSimpleStatement goSimpleStatement) {
            if (goSimpleStatement.getShortVarDeclaration() != null) {
                GoShortVarDeclaration shortVarDeclaration = goSimpleStatement.getShortVarDeclaration();
                for (PsiReference search : GoReferencesSearch.search(shortVarDeclaration.getDefinitionList().get(0))) {
                    GoStatement statement = (GoStatement) PsiTreeUtil.findFirstParent(search.getElement(), e -> e instanceof GoStatement);

                    argument = findGoCompositeElementByStatement(statement, callablesSet, callables);
                    if (argument != null) return argument;
                }
            }
        }

        return null;
    }

    private void scanFields(GoCallableDescriptor descriptor, @NotNull CompletionResultSet result, @NotNull GoTypeSpec goTypeSpec) {
        if (goTypeSpec.getSpecType().getType() instanceof GoStructType goStructType) {
            for (GoFieldDeclaration field : goStructType.getFieldDeclarationList()) {
                String column = getColumn(field);
                String comment = getComment(field);
                String type = "";

                if (field.getType() != null) {
                    type = field.getType().getPresentationText();
                }

                if (column != null && column.isEmpty()) {
                    if (field.getFieldDefinitionList().size() == 0 && field.getAnonymousFieldDefinition() != null) {
                        GoType goType = field.getAnonymousFieldDefinition().getGoType(ResolveState.initial());
                        if (goType == null) continue;

                        GoTypeSpec spec = (GoTypeSpec) goType.resolve(ResolveState.initial());
                        if (spec == null) continue;

                        scanFields(descriptor, result, spec);
                        continue;
                    }

                    String name = field.getFieldDefinitionList().get(0).getName();

                    if (name != null) {
                        column = Strings.replaceCommonInitialisms(name);
                        column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column);
                    }
                }


                if (comment != null && comment.isEmpty()) {
                    comment = GoDocumentationProvider.getCommentText(GoDocumentationProvider.getCommentsForElement(field), false);
                }

                addElement(result, column, comment, type);

                Map<GoCallableDescriptor, List<String>> queryExpr = queryExpr();
                if (queryExpr != null) {
                    List<String> whereExpr = queryExpr.get(descriptor);
                    if (whereExpr != null) {
                        for (String s : whereExpr) {
                            addElement(result, String.format(s, column), comment, type);
                        }
                    }
                }
            }
        }
    }

    private void addElement(@NotNull CompletionResultSet result, String column, String comment, String type) {
        result.addElement(LookupElementBuilder.create(column)
                .withTypeText(type)
                .withIcon(getIcon())
                .withTailText(" " + comment, true));
    }

    protected Icon getIcon() {
        return Icons.Icon12x12;
    }

    public abstract Map<GoCallableDescriptor, Integer> callables();

    public abstract GoCallableDescriptorSet callablesSet();

    public abstract @Nullable Map<GoCallableDescriptor, Integer> schemaCallables();

    public abstract @Nullable GoCallableDescriptorSet schemaCallablesSet();

    public abstract @Nullable Map<GoCallableDescriptor, Integer> otherSchemaCallables();

    public abstract @Nullable GoCallableDescriptorSet otherSchemaCallablesSet();

    public abstract @Nullable Map<GoCallableDescriptor, List<String>> queryExpr();

    public abstract @Nullable String getColumn(@NotNull GoFieldDeclaration field);

    public abstract @Nullable String getComment(@NotNull GoFieldDeclaration field);

    public abstract @NotNull String allowType();
}
