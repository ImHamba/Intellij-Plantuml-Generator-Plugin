package com.hamba.intellijplantumlgeneratorplugin;

import com.intellij.psi.PsiClass;

import java.util.ArrayList;
import java.util.List;

public class ClassRelation {
    private PsiClass dependent;
    private List<PsiClass> dependencies;

    public ClassRelation(PsiClass dependent, List<PsiClass> dependencies) {
        this.dependent = dependent;
        this.dependencies = dependencies;
    }

    public ClassRelation(PsiClass dependent) {
        this.dependent = dependent;
        this.dependencies = new ArrayList<>();
    }

}
