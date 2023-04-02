package com.hamba.intellijplantumlgeneratorplugin.utils;

import com.intellij.lang.jvm.JvmModifier;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.util.containers.ContainerUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void createNewFile(String savePath, String fileName, String fileExtension, String fileContent) {
        String filePath = savePath + "/" + fileName + "." + fileExtension;
        try {
            Files.write(Paths.get(filePath), fileContent.getBytes());
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
            System.out.println("File not created.");
        }
    }

    public static DirectoryTreeNode generateDirectoryTree(Project project) {
        // assumes single source directory
        PsiDirectory sourceDirectory = getSourceRootDirectories(project).get(0);

        // initialise package tree
        DirectoryTreeNode directoryTree = new DirectoryTreeNode(sourceDirectory, null);

        // fill out the tree with all directories and classes in the structure
        directoryTree.generateTreeFromDirectoryData();

        return directoryTree;
    }

    public static List<PsiDirectory> getSourceRootDirectories(Project project) {
        final List<VirtualFile> sourceRoots = new ArrayList<>();
        final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(project);

        // get all source roots of the project
        ContainerUtil.addAll(sourceRoots, projectRootManager.getContentSourceRoots());

        final PsiManager psiManager = PsiManager.getInstance(project);

        // get source root directories
        List<PsiDirectory> sourceDirectories = new ArrayList<>();
        for (VirtualFile sourceRoot : sourceRoots) {
            sourceDirectories.add(psiManager.findDirectory(sourceRoot));
        }

        return sourceDirectories;
    }

    public static String getContentRootDirectories(Project project) {
//        final VirtualFile contentRoot;
//        final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(project);
//        ProjectManager projectManager = ProjectManager.getInstance();

        return project.getBasePath();
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
