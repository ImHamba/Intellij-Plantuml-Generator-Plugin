package com.hamba.intellijplantumlgeneratorplugin.main;

import com.intellij.psi.PsiClass;

import java.util.ArrayList;
import java.util.List;

public class UmlGenerator {

    /**
     * Generates UML syntax from a map of class relationships
     *
     * @return
     */
    static String generateUmlRelationshipSyntax(List<ClassRelation> classRelationships, String umlRelationSymbol) {
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
}
