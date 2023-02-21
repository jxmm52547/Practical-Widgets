package xyz.jxmm.data;

import xyz.jxmm.tools.FileWriterMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;

public class Object {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/object.json");

    public static JsonObject userObject(Long sender, Long yourObject){
        JsonObject userObject = new JsonObject();

        userObject.addProperty(sender.toString(), yourObject);

        return userObject;
    }

    public static void write(Long sender, Long yourObject){
        JsonObject gen = userObject(sender, yourObject);

        FileWriterMethod.fileWriter(file.getPath(),gson.toJson(gen));
    }

    public static void main(){
        if (!file.exists()){
            write(123456L, 456789L);
            System.out.println("对象数据库不存在, 正在创建");
        }
    }
}
