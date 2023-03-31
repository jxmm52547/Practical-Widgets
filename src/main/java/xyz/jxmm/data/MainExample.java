package xyz.jxmm.data;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

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
        JsonObject signExample = new JsonObject();

        jrrpExample.addProperty("jrrpValue", 0);
        jrrpExample.addProperty("week", "这是模板");

        dogExample.addProperty("dogValue","String");
        dogExample.addProperty("week", "请勿删除");

        signExample.addProperty("签到次数",0);
        signExample.addProperty("week","签到判断");

        example.add("jrrp",jrrpExample);
        example.add("dog", dogExample);
        example.add("sign",signExample);
        example.addProperty("lastTime", LocalDateTime.now().toString());
        example.addProperty("version","0.4.2 - Alpha - 2"); //每次版本更新修改

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
        if (json.get("123456").getAsJsonObject().has("version")){
            if (!json.get("123456").getAsJsonObject().get("version").getAsString().equals("0.4.2 - Alpha - 2")){ //每次版本更新修改
                json.add("123456", main());
                fileWriter(file.getPath(), gson.toJson(json));
            }
        } else {
            json.add("123456", main());
            fileWriter(file.getPath(), gson.toJson(json));
        }



        if (group != null) {
            group.sendMessage("数据库手动更新成功");
        }
    }

    static void write() {
        JsonObject exampleGen = gen();
        file.getParentFile().mkdirs();
        fileWriter(file.getPath(), gson.toJson(exampleGen));
    }
}
