package xyz.jxmm.tools;

import java.io.*;

public class FileWriter {
    public static void fileWriter(String filepath, String content) throws IOException {
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(filepath)) {
            fileWriter.append(content);
        }
    }
}
