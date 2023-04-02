package com.hamba.intellijplantumlgeneratorplugin.utils;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryTreeNode extends TreeNode<PsiDirectory> {

    private List<PsiClass> directoryClasses = new ArrayList<>();

    public DirectoryTreeNode(PsiDirectory directory, TreeNode parent) {
        super(directory, parent);

        // iterate through each file in this node's directory
        for (PsiFile file : directory.getFiles()) {
            // cast file to PsiJavaFile, get its classes and add them to classes
            directoryClasses.addAll(Arrays.asList(((PsiJavaFile) file).getClasses()));
        }
    }

    public void generateTreeFromDirectoryData() {
        // access directory stored in current tree node and get its subdirectories
        PsiDirectory[] subdirectories = this.data.getSubdirectories();

        // iterate through the subdirectories and create new nodes to add as children
        for (PsiDirectory subdirectory : subdirectories) {
            DirectoryTreeNode newChild = new DirectoryTreeNode(subdirectory, this);
            this.addChild(newChild);

            // generate the tree for the child in a depth first manner
            newChild.generateTreeFromDirectoryData();
        }
    }

    public String getDirectoryName() {
        return data.getName();
    }

    public List<String> getClassNames() {
        List<String> classNames = new ArrayList<>();
        for (PsiClass directoryClass : directoryClasses) {
            classNames.add(directoryClass.getName());
        }

        return classNames;
    }
}
