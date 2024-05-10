package xyz.jxmm.tools;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileReaderMethod {
    public static String fileReader(String filepath) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        StringBuilder fileContent = new StringBuilder();

        try {
            fis = new FileInputStream(filepath);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
                fileContent.append("\r\n"); // 补上换行符
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContent.toString();
    }
}
