package xyz.jxmm.data;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Data {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File data = new File("./PracticalWidgets/data.json");

    static File jrrpTop = new File("./PracticalWidgets/jrrpTop.json");

    static JsonObject json;

    {
        try {
            json = new Gson().fromJson(new FileReader("./PracticalWidgets/data.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
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
        } else {
//            JrrpTop.update(null);
        }
    }

}
