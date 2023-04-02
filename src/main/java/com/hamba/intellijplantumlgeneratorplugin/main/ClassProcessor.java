package com.hamba.intellijplantumlgeneratorplugin.main;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.Processor;

import java.util.*;

public class ClassProcessor implements Processor<PsiClass> {

    private List<PsiClass> allClasses = new ArrayList<>();

    // processor that is used below on each class PsiClass object
    @Override
    public boolean process(PsiClass currentClass) {
        allClasses.add(currentClass);
        return true;
    }

    public List<ClassRelation> generateAssociations() {
        // stores the set of relationships between each class and other classes
        List<ClassRelation> allAssociations = new ArrayList<>();

        // iterate through each class in the project
        for (PsiClass currentClass : allClasses) {
            // stores the set of relationships between the current class being processed and other classes
            List<PsiClass> associations = processAssociations(currentClass);

            // add current class relationships to maps holding all relationships
            if (associations.size() > 0) {
                allAssociations.add(new ClassRelation(currentClass, associations));
            }
        }

        return allAssociations;
    }

    public List<ClassRelation> generateDependencies() {
        // stores the set of relationships between each class and other classes
        List<ClassRelation> allDependencies = new ArrayList<>();

        // iterate through each class in the project
        for (PsiClass currentClass : allClasses) {
            // stores the set of relationships between the current class being processed and other classes
            List<PsiClass> dependencies = processDependencies(currentClass);

            if (dependencies.size() > 0) {
                allDependencies.add(new ClassRelation(currentClass, dependencies));
            }
        }

        return allDependencies;
    }

    public List<ClassRelation> generateInterfaces() {
        // stores the set of relationships between each class and other classes
        List<ClassRelation> allInterfaces = new ArrayList<>();

        // iterate through each class in the project
        for (PsiClass currentClass : allClasses) {
            // stores the set of relationships between the current class being processed and other classes
            List<PsiClass> interfaces = processInterfaces(currentClass);

            if (interfaces.size() > 0) {
                allInterfaces.add(new ClassRelation(currentClass, interfaces));
            }
        }

        return allInterfaces;
    }

    public List<ClassRelation> generateInheritances() {
        // stores the set of relationships between each class and other classes
        List<ClassRelation> allInheritances = new ArrayList<>();

        // iterate through each class in the project
        for (PsiClass currentClass : allClasses) {
            // stores the set of relationships between the current class being processed and other classes
            PsiClass inheritance = processInheritances(currentClass);

            if (inheritance != null) {
                List<PsiClass> inheritances = new ArrayList<>();
                inheritances.add(inheritance);
                allInheritances.add(new ClassRelation(currentClass, inheritances));
            }
        }

        return allInheritances;
    }

    public List<PsiClass> processAssociations(PsiClass currentClass) {
        List<PsiClass> associations = new ArrayList<>();

        // get all attributes of the class
        PsiField[] allFields = currentClass.getAllFields();

        // iterate through each attribute in this class
        for (PsiField field : allFields) {
            PsiClass fieldClass = PsiUtil.resolveClassInClassTypeOnly(field.getType());
            associations.add(fieldClass);
        }

        return associations;
    }

    public List<PsiClass> processInterfaces(PsiClass currentClass) {
        // get array of all interfaces of current class
        PsiClass[] interfaceArray = currentClass.getInterfaces();

        // convert array to list
        return Arrays.asList(interfaceArray);
    }

    public List<PsiClass> processDependencies(PsiClass currentClass) {
        // TODO: implement this
        return new ArrayList<>();
    }

    public PsiClass processInheritances(PsiClass currentClass) {
        // get class this class extends from
        // returns null if current class does not extend anything
        PsiClass parent = currentClass.getSuperClass();

        if (allClasses.contains(parent)) {
            return parent;
        }

        return null;
    }
}
