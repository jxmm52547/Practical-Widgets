package xyz.jxmm.jrrp;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.data.MainExample;
import xyz.jxmm.perm.Determine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;

public class Main {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/data.json");
    static File dataFile = new File("./PracticalWidgets/data.json");
    static File topFile = new File("./PracticalWidgets/JrrpTop.json");


    public static void perm(String userName,Long sender,Group group) throws IOException {
        if (Determine.main(sender,group,"jrrp")){
            register(group,sender,userName);
        }
    }

    public static void register(Group group,Long sender,String userName) throws IOException {
        JsonObject json;
        json = new Gson().fromJson(new InputStreamReader(Files.newInputStream(dataFile.toPath()), StandardCharsets.UTF_8), JsonObject.class);
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        if (Determine.register(group)){
            if (!json.has(sender.toString())){
                MessageChain chain = new MessageChainBuilder()
                        .append(at)
                        .append(new PlainText("\n未注册,请使用[ /注册 ]先注册哦"))
                        .build();
                group.sendMessage(chain);
            } else {
                jrrp(sender,group,userName);
            }
        } else if (!json.has(sender.toString())){
            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText("已为您自动注册"))
                    .build();
            group.sendMessage(chain);

            JsonObject jsobj = MainExample.main();
            json.add(sender.toString(), jsobj);
            fileWriter(file.getPath(), gson.toJson(json));
            jrrp(sender,group,userName);
        } else {
            jrrp(sender,group,userName);
        }
    }

    public static void jrrp(Long sender,Group group,String userName) throws IOException {
        JsonObject json;
        JsonObject top;
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        json = new Gson().fromJson(new InputStreamReader(Files.newInputStream(dataFile.toPath()), StandardCharsets.UTF_8), JsonObject.class);
        top = new Gson().fromJson(new InputStreamReader(Files.newInputStream(topFile.toPath()), StandardCharsets.UTF_8), JsonObject.class);

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

            writJrrpTop(sender, userName, group, top, jrrp);
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

            writJrrpTop(sender, userName, group, top, jrrp);

            fileWriter("./PracticalWidgets/data.json", gson.toJson(json));
        }
    }

    public static void writJrrpTop(Long sender, String userName, Group group, JsonObject top, int jrrp) {
        if (!top.has(String.valueOf(group.getId()))){
            top.add(String.valueOf(group.getId()), xyz.jxmm.data.JrrpTop.userArray(sender, userName,jrrp));
        } else {
            JsonArray groupValue = top.get(String.valueOf(group.getId())).getAsJsonArray();
            for (int i = 0; i < groupValue.size(); i++) {
                if (groupValue.get(i).getAsJsonObject().get("user").getAsLong() != sender){
                    groupValue.add(xyz.jxmm.data.JrrpTop.userExample(sender,userName,jrrp));
                }
            }

        }
        fileWriter("./PracticalWidgets/JrrpTop.json", gson.toJson(top));
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
