package com.hamba.intellijplantumlgeneratorplugin.main;

import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.List;
import java.util.Map;

public class GenerateUmlAction extends AnAction {
    Map<PsiType, List<PsiType>> associations;
    Map<PsiType, List<PsiType>> dependencies;
    Map<PsiType, List<PsiType>> interfaces;
    Map<PsiType, List<PsiType>> extensions;

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        ClassProcessor processor = new ClassProcessor();

        // searches for classes with '' prefix (i.e. all classes) in the project and runs class processor on them
        AllClassesGetter.processJavaClasses(
                new PlainPrefixMatcher(""),
                project,
                GlobalSearchScope.projectScope(project),
                processor);

        List<ClassRelation> allInheritances = processor.generateInheritances();
        List<ClassRelation> allInterfaces = processor.generateInterfaces();
        List<ClassRelation> allAssociations = processor.generateAssociations();

        int a = 1;
    }

    @Override
    public void update(AnActionEvent e) {

    }
}