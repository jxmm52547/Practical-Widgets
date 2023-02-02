package xyz.jxmm.data;

import static xyz.jxmm.tools.FileWriter.fileWriter;

import net.mamoe.mirai.contact.Group;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JrrpTop {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/jrrpTop.json");

    @NotNull
    public static JsonObject main(Long sender,String username, int jrrp) {
        JsonObject userExample = new JsonObject();

        userExample.addProperty("user", sender);
        userExample.addProperty("nick", username);
        userExample.addProperty("jrrp", jrrp);

        return userExample;
    }

    @NotNull
    public static JsonArray gen(){
        JsonArray exampleGen = new JsonArray();
        exampleGen.add(main(123L,"example", 0));

        return exampleGen;
    }

//    public static void update(Group group){
//        JsonArray json;
//        try {
//            json = new Gson().fromJson(new FileReader(file), JsonArray.class);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        json.add(main(123L,"example", 0));
//        try {
//            fileWriter("./PracticalWidgets/jrrpTop.json", gson.toJson(json));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (group != null) {
//            group.sendMessage("今日人品排行榜数据库手动更新成功");
//        }
//    }

    static void write() throws IOException {
        JsonArray exampleGen = gen();
        file.getParentFile().mkdirs();
        fileWriter("./PracticalWidgets/jrrpTop.json", gson.toJson(exampleGen));
    }


}
