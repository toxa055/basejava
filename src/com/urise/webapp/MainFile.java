package com.urise.webapp;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        showAllFiles(new File("../basejava/src/com/urise/webapp"));
    }

    public static void showAllFiles(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    showAllFiles(file);
                } else {
                    System.out.println("File: " + file.getName());
                }
            }
        }
    }
}
