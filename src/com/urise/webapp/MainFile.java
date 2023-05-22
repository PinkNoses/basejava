package com.urise.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        File files = new File("..\\basejava");
        output(files);
    }

    public static void output(File folder) {
        File[] list = folder.listFiles();
        assert list != null;
        for (File fileName : list) {
            if (fileName.isDirectory()) {
                output(fileName);
            } else {
                try {
                    System.out.println(fileName.getCanonicalPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}