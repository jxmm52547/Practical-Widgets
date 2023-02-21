package xyz.jxmm.data;

import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Data {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File data = new File("./PracticalWidgets/data.json");

    static File jrrpTop = new File("./PracticalWidgets/jrrpTop.json");

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
            JrrpTop.write();
        }

    }

}
