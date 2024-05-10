package xyz.jxmm.tools;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class FileWriterMethod {
    public static void fileWriter(String filepath, String content) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            fos = new FileOutputStream(filepath);
            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            osw.write(content);
            osw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
