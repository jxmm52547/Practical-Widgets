package xyz.jxmm.perm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

public class WriteJson {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void main(File file, JsonObject jsonObject){
        fileWriter(file.getPath(), gson.toJson(jsonObject));
    }
}
