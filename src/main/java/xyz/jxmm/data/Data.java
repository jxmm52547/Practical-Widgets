package xyz.jxmm.data;

import com.google.gson.*;
import xyz.jxmm.kokomi.Kokomi;
import xyz.jxmm.tools.FileReaderMethod;
import xyz.jxmm.tools.FileWriterMethod;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Data {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File data = new File("./PracticalWidgets/data.json");

    static File jrrpTop = new File("./PracticalWidgets/JrrpTop.json");
    public static File TidewhisperScrolls = new File("./PracticalWidgets/TidewhisperScrolls.json");
    static JsonObject json;

    {
        try {
            json = new Gson().fromJson(new InputStreamReader(Files.newInputStream(data.toPath()), StandardCharsets.UTF_8), JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main() throws IOException {
        if (!data.exists()) {
            MainExample.write();
        } else {
            MainExample.update(null);
        }

        if (!jrrpTop.exists()){
            xyz.jxmm.data.JrrpTop.write();
        }

        if (!TidewhisperScrolls.exists()) {
            TidewhisperScrolls.createNewFile();
            FileWriterMethod.fileWriter(TidewhisperScrolls.getPath(), gson.toJson(Kokomi.TidewhisperScrolls));
        }

    }

}
