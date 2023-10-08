package xyz.jxmm.perm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.jxmm.PracticalWidgets;

import java.io.File;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

public class Admin {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File admin = new File("./PracticalWidgets/perm/admin.json");

    public static JsonArray value(Long id) {
        JsonArray jsonArray = new JsonArray();

        jsonArray.add(id);

        return jsonArray;
    }

    public static void gen(Long id){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("version",PracticalWidgets.version());
        jsonObject.add("value",value(id));
        WriteJson.main(admin,jsonObject);
    }
}
