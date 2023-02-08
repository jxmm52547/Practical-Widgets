package xyz.jxmm.tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriter {
    public static void fileWriter(String filepath, String content) throws IOException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                Files.newOutputStream(Paths.get(filepath)), "UTF-8"));
        writer.println(content);
        writer.close();
    }
}
