package xyz.jxmm.jrrp;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;
import xyz.jxmm.data.JrrpTop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;

public class Main {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File dataFile = new File("./PracticalWidgets/data.json");
    static File topFile = new File("./PracticalWidgets/jrrpTop.json");

    public static void main(Long sender, String userName, Group group) throws IOException {
        JsonObject json;
        JsonObject top;
        json = new Gson().fromJson(new InputStreamReader(Files.newInputStream(dataFile.toPath()), StandardCharsets.UTF_8), JsonObject.class);
        top = new Gson().fromJson(new InputStreamReader(Files.newInputStream(topFile.toPath()), StandardCharsets.UTF_8), JsonObject.class);

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
                int jrrp = user.get("jrrpValue").getAsInt();
                String express = express(jrrp);

                MessageChain chain = new MessageChainBuilder()
                        .append(at)
                        .append(new PlainText("\n今日已查询,您的今日人品为: " + jrrp + "\n"))
                        .append(new PlainText(express))
                        .build();
                group.sendMessage(chain);
            } else {
                int jrrp = new Random().nextInt(101);
                String express = express(jrrp);

                MessageChain chain = new MessageChainBuilder()
                        .append(at)
                        .append(new PlainText("\n您的今日人品为:  " + jrrp + "\n"))
                        .append(new PlainText(express))
                        .build();
                group.sendMessage(chain);

                user.addProperty("jrrpValue", jrrp);
                user.addProperty("week", toDayWeek);

                json.get(sender.toString()).getAsJsonObject().add("jrrp", user);
                json.get(sender.toString()).getAsJsonObject().addProperty("lastTime", LocalDateTime.now().toString());

                if (!top.has(String.valueOf(group.getId()))){
                    top.add(String.valueOf(group.getId()),JrrpTop.userArray(sender, userName,jrrp));
                } else {
                    JsonArray groupValue = top.get(String.valueOf(group.getId())).getAsJsonArray();
                    groupValue.add(JrrpTop.userExample(sender,userName,jrrp));
                }

                fileWriter("./PracticalWidgets/data.json", gson.toJson(json));
                fileWriter("./PracticalWidgets/jrrpTop.json", gson.toJson(top));
            }

        }

    }

    public static String express(int jrrp) throws IOException {
        String express = "";
        Properties properties = new Properties();
        File cfg = new File("./PracticalWidgets/config.properties");
        properties.load(new InputStreamReader(Files.newInputStream(cfg.toPath()), StandardCharsets.UTF_8));

        if (jrrp==0){
            express = properties.getProperty("0");
        } else if (jrrp<=20 && jrrp>0){
            express = properties.getProperty("1到20");
        }
        else if (jrrp<=40 && jrrp>20){
            express = properties.getProperty("21到40");
        }
        else if (jrrp<=60 && jrrp>40){
            express = properties.getProperty("41到60");
        }
        else if (jrrp<=80 && jrrp>60){
            express = properties.getProperty("61到80");
        }
        else if (jrrp<=99 && jrrp>80){
            express = properties.getProperty("81到99");
        }
        else if (jrrp == 100){
            express = properties.getProperty("100");
        }

        return express;
    }

}
