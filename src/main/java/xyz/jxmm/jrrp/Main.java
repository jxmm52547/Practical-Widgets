package xyz.jxmm.jrrp;

import static xyz.jxmm.tools.FileWriter.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

public class Main {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File dataFile = new File("./PracticalWidgets/data.json");
    static File topFile = new File("./PracticalWidgets/jrrpTop.json");

    public static void main(Long sender, String userName, Group group) {
        JsonObject json;
        JsonArray top;
        try {
            json = new Gson().fromJson(new FileReader(dataFile), JsonObject.class);
            top = new Gson().fromJson(new FileReader(topFile), JsonArray.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        if (!json.has(sender.toString())){
            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText("\n未注册,请使用[ /注册 ]先注册哦"))
                    .build();
            group.sendMessage(chain);
        } else {
            JsonObject user = json.get(sender.toString()).getAsJsonObject().get("jrrp").getAsJsonObject();

            String toDayWeek = LocalDateTime.now().getDayOfWeek().toString();
            String oldDayWeek = user.get("week").getAsString();

            if (toDayWeek.equals(oldDayWeek)){
                MessageChain chain = new MessageChainBuilder()
                        .append(at)
                        .append(new PlainText("\n今日已查询,您的今日人品为: " + user.get("jrrpValue")))
                        .build();
                group.sendMessage(chain);
            } else {
                int jrrp = new Random().nextInt(101);

                MessageChain chain = new MessageChainBuilder()
                        .append(at)
                        .append(new PlainText("\n您的今日人品为:  " + jrrp))
                        .build();
                group.sendMessage(chain);

                user.addProperty("jrrpValue", jrrp);
                user.addProperty("week", toDayWeek);

                json.get(sender.toString()).getAsJsonObject().add("jrrp", user);
                json.get(sender.toString()).getAsJsonObject().addProperty("lastTime", LocalDateTime.now().toString());

                top.add(xyz.jxmm.data.JrrpTop.main(sender,userName,jrrp,group.getId()));

                try {
                    fileWriter("./PracticalWidgets/data.json", gson.toJson(json));
                    fileWriter("./PracticalWidgets/jrrpTop.json", gson.toJson(top));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

    }

}
