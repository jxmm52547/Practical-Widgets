package xyz.jxmm.data;

import static xyz.jxmm.tools.FileWriter.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.mamoe.mirai.contact.Group;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class MainExample {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/data.json");

    @NotNull
    public static JsonObject main(){
        JsonObject example = new JsonObject();
        JsonObject jrrpExample = new JsonObject();
        JsonObject dogExample = new JsonObject();

        jrrpExample.addProperty("jrrpValue", 0);
        jrrpExample.addProperty("week", "这是模板");

        dogExample.addProperty("dogValue","String");
        dogExample.addProperty("week", "请勿删除");

        example.add("jrrp",jrrpExample);
        example.add("dog", dogExample);
        example.addProperty("lastTime", LocalDateTime.now().toString());

        return example;
    }

    public static JsonObject gen(){
        JsonObject exampleGen = new JsonObject();
        exampleGen.add("123456", main());

        return exampleGen;
    };

    public static void update(Group group){
        JsonObject json;
        try {
            json = new Gson().fromJson(new FileReader(file), JsonObject.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        json.add("123456", main());
        try {
            fileWriter("./PracticalWidgets/data.json", gson.toJson(json));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (group != null) {
            group.sendMessage("数据库手动更新成功");
        }
    }

    static void write() throws IOException {
        JsonObject exampleGen = gen();
        file.getParentFile().mkdirs();
        fileWriter("./PracticalWidgets/data.json", gson.toJson(exampleGen));
    }
}
