package com.urise.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        File files = new File("./src/com/urise/webapp");
        output(files);
    }

    public static void output(File folder) {
        File[] list = folder.listFiles();
        assert list != null;
        for (File fileName : list) {
            if (fileName.isFile()) {
                System.out.println("    File: " + fileName.getName());
            } else if (fileName.isDirectory()) {
                System.out.println("Directory: " + fileName.getName());
                output(fileName);
            }
        }
    }
}