package com.urise.webapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class MainFile {

    public static void main(String[] args) {
        showAllFiles(new File("../basejava/src/com/urise/webapp"));
    }

    public static void showAllFiles(File directory) {
        Path path = Paths.get(directory.getName());
        File[] files = directory.listFiles();
        int tab = 0;
        if (files != null) {
            for (File file : files) {
                tab = file.toPath().getParent().getNameCount() - path.getNameCount();
                for (int i = 0; i < tab; i++) {
                    System.out.print("\t");
                }
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
