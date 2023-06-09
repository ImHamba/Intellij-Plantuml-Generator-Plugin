package com.hamba.intellijplantumlgeneratorplugin.main;

import com.hamba.intellijplantumlgeneratorplugin.utils.DirectoryTreeNode;
import com.intellij.codeInsight.completion.AllClassesGetter;
import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.hamba.intellijplantumlgeneratorplugin.main.UmlGenerator.generateUmlClassDiagramSyntax;
import static com.hamba.intellijplantumlgeneratorplugin.main.UmlGenerator.generateUmlRelationshipSyntax;
import static com.hamba.intellijplantumlgeneratorplugin.utils.Utils.*;

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

        List<ClassRelation> allAssociations = processor.generateAssociations();
        List<ClassRelation> allInheritances = processor.generateInheritances();
        List<ClassRelation> allInterfaces = processor.generateInterfaces();
        List<ClassRelation> nonDependecyRelations = concatenateLists(allAssociations, allInterfaces, allInheritances);
        List<ClassRelation> allDependencies = processor.generateDependencies(nonDependecyRelations);

        String dependenciesUml = generateUmlRelationshipSyntax(allDependencies, "..>");
        String associationsUml = generateUmlRelationshipSyntax(allAssociations, "-[#red]->");
        String inheritancesUml = generateUmlRelationshipSyntax(allInheritances, "-[#blue]-|>");
        String interfacesUml = generateUmlRelationshipSyntax(allInterfaces, ".[#green].|>");

        String allRelationsUml = String.join("\n\n", Arrays.asList(dependenciesUml, associationsUml, inheritancesUml, interfacesUml));

        DirectoryTreeNode directoryTree = generateDirectoryTree(project);

        String classAndPackageUml = generateUmlClassDiagramSyntax(directoryTree);

        // add starting syntax
        String fullDiagramUml = "@startuml\nskinparam linetype ortho\n" + classAndPackageUml + "\n" + allRelationsUml + "\n@enduml";

        createNewFile(project.getBasePath(), "umlDiagram", "puml", fullDiagramUml);

        int a = 1;
    }

    @Override
    public void update(AnActionEvent e) {

    }
}