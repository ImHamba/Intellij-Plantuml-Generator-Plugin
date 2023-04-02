package com.hamba.intellijplantumlgeneratorplugin.main;

import com.hamba.intellijplantumlgeneratorplugin.utils.DirectoryTreeNode;
import com.hamba.intellijplantumlgeneratorplugin.utils.TreeNode;
import com.hamba.intellijplantumlgeneratorplugin.utils.UmlClassType;
import com.intellij.psi.PsiClass;

import java.util.ArrayList;
import java.util.List;

import static com.hamba.intellijplantumlgeneratorplugin.utils.Utils.getPsiClassUmlType;

public class UmlGenerator {

    /**
     * Generates UML syntax from a map of class relationships
     *
     * @return
     */
    public static String generateUmlRelationshipSyntax(List<ClassRelation> classRelationships, String umlRelationSymbol) {
        List<String> relationsList = new ArrayList<>();

        // iterate through class relations
        for (ClassRelation classRelation : classRelationships) {
            // iterate through the classes the current one is dependent on
            for (PsiClass dependencyClass : classRelation.getDependencyClasses()) {
                String relationString = String.format("%s %s %s", classRelation.getDependentClass().getName(), umlRelationSymbol, dependencyClass.getName());
                relationsList.add(relationString);
            }
        }

        // combine the list of strings into a single string separated by newlines
        return String.join("\n", relationsList);
    }

    public static String generateUmlClassDiagramSyntax(DirectoryTreeNode directoryTree) {
        List<String> classDiagramStringList = generateUmlClassDiagramSyntaxRecursion(directoryTree, 0);
        return String.join("\n", classDiagramStringList);
    }

    private static List<String> generateUmlClassDiagramSyntaxRecursion(DirectoryTreeNode directoryTree, int indentLevel) {
        List<String> classDiagramStringList = new ArrayList<>();

        String packageLine = String.format("%spackage %s {", "\t".repeat(indentLevel), directoryTree.getDirectoryName());
        classDiagramStringList.add(packageLine);

        // add each class/uml object (class, interface, enum etc) to the UML syntax
        for (PsiClass directoryClass : directoryTree.getDirectoryClasses()) {
            UmlClassType classUmlType = getPsiClassUmlType(directoryClass);
            String className = directoryClass.getName();

            String classLine = String.format("%s%s %s", "\t".repeat(indentLevel + 1), classUmlType, className);
            classDiagramStringList.add(classLine);
        }

        // step through tree in a depth first order and generate the corresponding lines of UML syntax
        for (TreeNode childDirectoryTree : directoryTree.getChildren()) {
            List<String> childStrings = generateUmlClassDiagramSyntaxRecursion((DirectoryTreeNode) childDirectoryTree, indentLevel + 1);
            classDiagramStringList.addAll(childStrings);
        }

        classDiagramStringList.add("\t".repeat(indentLevel) + "}");

        return classDiagramStringList;
    }
}
