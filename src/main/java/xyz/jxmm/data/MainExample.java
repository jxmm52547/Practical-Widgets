package xyz.jxmm.data;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import kotlinx.serialization.json.Json;
import net.mamoe.mirai.contact.Group;

import org.jetbrains.annotations.NotNull;
import xyz.jxmm.PracticalWidgets;
import xyz.jxmm.perm.Determine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class MainExample {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/data.json");

    @NotNull
    public static JsonObject main(){
        JsonObject example = new JsonObject();

        example.add("jrrp",jrrp());
        example.add("dog", dog());
        example.add("sign",sign());

        example.addProperty("lastTime", LocalDateTime.now().toString());
        example.addProperty("version",PracticalWidgets.version()); //每次版本更新修改

        return example;
    }

    public static JsonObject jrrp(){
        JsonObject jrrpExample = new JsonObject();
        jrrpExample.addProperty("jrrpValue", 0);
        jrrpExample.addProperty("week", "这是模板");
        return jrrpExample;
    }

    public static JsonObject dog(){
        JsonObject dogExample = new JsonObject();
        dogExample.addProperty("dogValue","String");
        dogExample.addProperty("week", "请勿删除");
        return dogExample;
    }
    public static JsonObject sign(){
        JsonObject signExample = new JsonObject();
        signExample.addProperty("签到次数",0);
        signExample.addProperty("week","签到判断");

        return signExample;
    }

    public static JsonObject gen(){
        JsonObject exampleGen = new JsonObject();
        exampleGen.add("123456", main());

        return exampleGen;
    }

    public static void perm(Group group,Long sender){
        if (Determine.admin(group,sender)){
            update(group);
        } else {
            group.sendMessage("你没有管理员权限!");
        }
    }

    public static void update(Group group){
        JsonObject json;

        try {
            json = new Gson().fromJson(new FileReader(file,StandardCharsets.UTF_8), JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (json.get("123456").getAsJsonObject().has("version")){
            if (!json.get("123456").getAsJsonObject().get("version").getAsString().equals(PracticalWidgets.version())){ //每次版本更新修改
                json.add("123456", main());
                fileWriter(file.getPath(), gson.toJson(json));
            }
        } else {
            json.add("123456", main());
            fileWriter(file.getPath(), gson.toJson(json));
        }

        for (String key : json.keySet()){
            JsonObject user = json.get(key).getAsJsonObject();

            if (!user.has("sign")){
                user.add("sign",sign());
                json.add(key,user);
            }
            user.addProperty("lastTime", LocalDateTime.now().toString());
            user.addProperty("version",PracticalWidgets.version());
            json.add(key,user);

        }
        fileWriter(file.getPath(),gson.toJson(json));

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
