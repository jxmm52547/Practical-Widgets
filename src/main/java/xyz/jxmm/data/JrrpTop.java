package xyz.jxmm.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

public class JrrpTop {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/jrrpTop.json");

    @NotNull
//    public static JsonObject main(Long sender,String username, int jrrp, Long group) {
//        JsonObject userExample = new JsonObject();
//
//        userExample.addProperty("user", sender);
//        userExample.addProperty("nick", username);
//        userExample.addProperty("jrrp", jrrp);
//        userExample.addProperty("group", group);
//
//        return userExample;
//    }

    public static JsonObject userExample(Long sender, String username, int jrrp){
        JsonObject userExample = new JsonObject();

        userExample.addProperty("user",sender);
        userExample.addProperty("jrrp", jrrp);
        userExample.addProperty("nick", username);


        return userExample;
    }

    public static JsonArray userArray(Long sender,String username,int jrrp){
        JsonArray userArray = new JsonArray();
        userArray.add(userExample(sender, username, jrrp));
        return userArray;
    }

    public static JsonObject gen(Long groupID,Long sender,String username,int jrrp){
        JsonObject groupObject = new JsonObject();
        groupObject.add(groupID.toString(),userArray(sender,username,jrrp));

        return groupObject;
    }

    static void write() throws IOException {
        JsonObject exampleGen = gen(123456L,123L,"example",0);
        file.getParentFile().mkdirs();
        fileWriter(file.getPath(), gson.toJson(exampleGen));
    }

//    public static void update(Group group) throws IOException {
//        file.delete();
//        FileWriterMethod.fileWriter(file.getPath(), gson.toJson(gen("123456")));
//        if (group != null){
//            group.sendMessage("更新且重置排行榜成功");
//        }
//    }

    public static void main() throws IOException {
        if (!file.exists()){
            write();
            System.out.println("今日人品排行榜不存在，正在创建");
        }
    }

}
