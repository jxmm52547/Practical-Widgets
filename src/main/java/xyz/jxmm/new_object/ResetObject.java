package xyz.jxmm.new_object;

import xyz.jxmm.data.Object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

public class ResetObject {

    static File file = new File("./PracticalWidgets/object.json");
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void reset(){
        file.delete();
        Object.write(123456L,456789L);
    }
}
