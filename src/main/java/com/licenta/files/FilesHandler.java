package com.licenta.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesHandler {

    public static boolean isPvUploaded(int studentId) {
        return getPV(studentId) != null;
    }

    public static boolean isAppendixUploaded(int studentId) {
        return getAppendix(studentId) != null;
    }

    public static File getPV(int studentId) {
        File pv = null;

        File[] allFiles = new File("files/" + studentId).listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if(file.getName().toLowerCase().startsWith("pv")) {
                    pv = file;
                }
            }
        }
        return pv;
    }

    public static File getAppendix(int studentId) {
        File anexa4 = null;

        File[] allFiles = new File("files/" + studentId).listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if(file.getName().toLowerCase().startsWith("anexa")) {
                    anexa4 = file;
                }
            }
        }
        return anexa4;
    }

    public static StudentFilesData getStudentProject(int userId) {
        List<String> fileNames = new ArrayList<>();

        File[] allFiles = new File("files/" + userId).listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if(file.getName().toLowerCase().startsWith("documentatie")) {
                    fileNames.add(file.getName());
                }
            }
        }
        return new StudentFilesData(fileNames);
    }

    public static StudentFilesData getStudentPresentation(int userId) {
        List<String> fileNames = new ArrayList<>();

        File[] allFiles = new File("files/" + userId).listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if(file.getName().toLowerCase().startsWith("prezentare")) {
                    fileNames.add(file.getName());
                }
            }
        }
        return new StudentFilesData(fileNames);
    }
}
