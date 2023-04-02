package com.hamba.intellijplantumlgeneratorplugin.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;

public class Utils {

    public static void createNewFile(String fileName, String fileExtension, String fileContent, Project project) {
        final PsiFileFactory factory = PsiFileFactory.getInstance(project);
        final PsiFile file = factory.createFileFromText(fileName + "." + fileExtension, fileContent);

        project.getBasePath();
    }
}
