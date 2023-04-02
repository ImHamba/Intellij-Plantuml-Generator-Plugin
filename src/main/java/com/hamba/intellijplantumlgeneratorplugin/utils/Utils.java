package com.hamba.intellijplantumlgeneratorplugin.utils;

import com.intellij.lang.jvm.JvmModifier;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.containers.ContainerUtil;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void createNewFile(String fileName, String fileExtension, String fileContent, Project project) {
        final PsiFileFactory factory = PsiFileFactory.getInstance(project);
        final PsiFile file = factory.createFileFromText(fileName + "." + fileExtension, fileContent);

        project.getBasePath();
    }

    public static DirectoryTreeNode generateDirectoryTree(Project project) {
        final List<VirtualFile> sourceRoots = new ArrayList<>();
        final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(project);

        // get all source roots of the project
        ContainerUtil.addAll(sourceRoots, projectRootManager.getContentSourceRoots());

        final PsiManager psiManager = PsiManager.getInstance(project);

        // get source root directory
        // assumes a single source root in the project
        final PsiDirectory sourceDirectory = psiManager.findDirectory(sourceRoots.get(0));

        // initialise package tree
        DirectoryTreeNode directoryTree = new DirectoryTreeNode(sourceDirectory, null);

        // fill out the tree with all directories and classes in the structure
        directoryTree.generateTreeFromDirectoryData();

        return directoryTree;
    }

    /**
     * Gets the UML signature of a PsiClass
     *
     * @param psiClass
     *
     * @return
     */
    public static UmlClassType getPsiClassUmlType(PsiClass psiClass) {
        if (psiClass.isEnum()) {
            return UmlClassType.ENUM;
        }
        else if (psiClass.isInterface()) {
            return UmlClassType.INTERFACE;
        }
        else if (psiClass.hasModifier(JvmModifier.ABSTRACT)) {
            return UmlClassType.ABSTRACT_CLASS;
        }
        else {
            return UmlClassType.BASIC_CLASS;
        }
    }
}
