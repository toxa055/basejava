package com.urise.webapp;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        showAllFiles(new File("/Users/toxa/basejava"));
    }

    public static void showAllFiles(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                showAllFiles(file);
            } else {
                System.out.println(file.getName());
            }
        }
    }
}
