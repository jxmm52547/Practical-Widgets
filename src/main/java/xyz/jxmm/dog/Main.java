package xyz.jxmm.dog;

import xyz.jxmm.dog.getValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import static xyz.jxmm.tools.FileWriter.fileWriter;

public class Main {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    static File file = new File("./PracticalWidgets/data.json");
    public static void main(Long sender,String userName, Group group) {
        JsonObject json;
        try {
            json = new Gson().fromJson(new FileReader("./PracticalWidgets/data.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!json.has(sender.toString())){
            group.sendMessage("QQ号:  " + sender + "\n未注册,请使用[ /注册 ]先注册哦");
        } else {
            JsonObject user = json.get(sender.toString()).getAsJsonObject().get("dog").getAsJsonObject();

            String toDayWeek = LocalDateTime.now().getDayOfWeek().toString();
            String oldDayWeek = user.get("week").getAsString();

            if (toDayWeek.equals(oldDayWeek)){
                group.sendMessage(userName + "\n已经看过了喔,今日属于您的日记为: \n" + user.get("dogValue"));
            } else {
                String value =  getValue.main();
                group.sendMessage(userName + " 今日属于您的日记为: \n" + value);

                user.addProperty("dogValue", value);
                user.addProperty("week", toDayWeek);

                json.get(sender.toString()).getAsJsonObject().add("dog", user);
                json.get(sender.toString()).getAsJsonObject().addProperty("lastTime", LocalDateTime.now().toString());
                try {
                    fileWriter("./PracticalWidgets/data.json", gson.toJson(json));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
