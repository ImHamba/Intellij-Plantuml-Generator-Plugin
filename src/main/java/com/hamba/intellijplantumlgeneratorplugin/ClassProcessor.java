package com.hamba.intellijplantumlgeneratorplugin;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.Processor;

import java.util.*;

public class ClassProcessor implements Processor<PsiClass> {
    // stores the set of relationships between each class and other classes
    private Map<PsiClass, List<PsiClass>> allAssociations = new HashMap<>();
    private Map<PsiClass, List<PsiClass>> allDependencies = new HashMap<>();
    private Map<PsiClass, List<PsiClass>> allInterfaces = new HashMap<>();
    private Map<PsiClass, PsiClass> allExtensions = new HashMap<>();

    // processor that is used below on each class PsiClass object
    @Override
    public boolean process(PsiClass currentClass) {
        // stores the set of relationships between the current class being processed and other classes
        List<PsiClass> associations = processAssociations(currentClass);
        List<PsiClass> dependencies = processDependencies(currentClass);
        List<PsiClass> interfaces = processInterfaces(currentClass);
        PsiClass extension = processExtensions(currentClass);

        // add current class relationships to maps holding all relationships
        if (associations.size() > 0) {
            allAssociations.put(currentClass, associations);
        }

        if (interfaces.size() > 0) {
            allInterfaces.put(currentClass, interfaces);
        }

        if (dependencies.size() > 0) {
            allDependencies.put(currentClass, dependencies);
        }

        if (extension != null) {
            allExtensions.put(currentClass, extension);
        }

        return true;
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
        return new ArrayList<>();
    }

    public PsiClass processExtensions(PsiClass currentClass) {
        // get class this class extends from
        // returns null if current class does not extend anything
        return currentClass.getSuperClass();
    }
}
