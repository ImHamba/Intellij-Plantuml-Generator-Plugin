package com.hamba.intellijplantumlgeneratorplugin.main;

import com.intellij.psi.PsiClass;

import java.util.ArrayList;
import java.util.List;

public class ClassRelation {
    private PsiClass dependentClass;
    private List<PsiClass> dependencyClasses;

    public ClassRelation(PsiClass dependentClass, List<PsiClass> dependencyClasses) {
        this.dependentClass = dependentClass;
        this.dependencyClasses = dependencyClasses;
    }

    public ClassRelation(PsiClass dependentClass) {
        this.dependentClass = dependentClass;
        this.dependencyClasses = new ArrayList<>();
    }

    /**
     * checks if this class relation contains a relationship between the dependent class and a specified dependency
     * class
     *
     * @param checkDependentClass
     * @param checkDependencyClass
     *
     * @return
     */
    public boolean hasRelation(PsiClass checkDependentClass, PsiClass checkDependencyClass) {
        return dependentClass == checkDependentClass && dependencyClasses.contains(checkDependencyClass);
    }

    public PsiClass getDependentClass() {
        return dependentClass;
    }

    public List<PsiClass> getDependencyClasses() {
        return dependencyClasses;
    }
}
