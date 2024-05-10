package xyz.jxmm.dog;

import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import xyz.jxmm.data.MainExample;
import xyz.jxmm.perm.Determine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Properties;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

public class Main {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    static File file = new File("./PracticalWidgets/data.json");

    public static void main(Long sender,String userName, Group group) {
        JsonObject json;
        try {
            json = new Gson().fromJson(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8), JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        if (Determine.register(group)){
            if (!json.has(sender.toString())){
                MessageChain chain = new MessageChainBuilder()
                        .append(at)
                        .append(new PlainText(" 未注册,请使用[ /注册 ]先注册哦"))
                        .build();
                group.sendMessage(chain);
            } else {
                dog(json,sender,group);
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
            dog(json,sender,group);
        } else {
            dog(json,sender,group);
        }
    }

    public static void dog(JsonObject json, Long sender, Group group){
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        JsonObject user = json.get(sender.toString()).getAsJsonObject().get("dog").getAsJsonObject();

        String toDayWeek = LocalDateTime.now().getDayOfWeek().toString();
        String oldDayWeek = user.get("week").getAsString();
        Properties properties;

        try {
            properties = xyz.jxmm.config.Main.properties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (properties.getProperty("ALAPIToken").equals("123456")){
            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText("请联系 BOT拥有者 前往配置文件填写Token后重试(无需重启)"))
                    .build();
            group.sendMessage(chain);
        } else if (toDayWeek.equals(oldDayWeek)){
            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText("\n已经看过了喔,今日属于您的日记为: \n"))
                    .append(new PlainText(user.get("dogValue").getAsString()))
                    .build();
            group.sendMessage(chain);
        } else {
            String value =  GetValue.main(group);
            JsonObject dog;
            dog = new Gson().fromJson(value, JsonObject.class);
            if (!dog.get("msg").getAsString().equals("success")){
                MessageChain chain = new MessageChainBuilder()
                        .append(at)
                        .append(new PlainText("请求失败"))
                        .append(new PlainText("\n错误代码: " + dog.get("code").getAsNumber()))
                        .append(new PlainText("\n错误信息: " + dog.get("msg").getAsString()))
                        .build();
                group.sendMessage(chain);
            } else {
                value = dog.get("data").getAsJsonObject().get("content").getAsString();
                MessageChain chain = new MessageChainBuilder()
                        .append(at)
                        .append(new PlainText("\n今日属于您的日记为: \n"))
                        .append(new PlainText(value))
                        .build();
                group.sendMessage(chain);

                user.addProperty("dogValue", value);
                user.addProperty("week", toDayWeek);

                json.get(sender.toString()).getAsJsonObject().add("dog", user);
                json.get(sender.toString()).getAsJsonObject().addProperty("lastTime", LocalDateTime.now().toString());
                fileWriter(file.getPath(), gson.toJson(json));
            }

        }
    }

    public static void perm(String userName,Long sender,Group group) throws IOException {
        if (Determine.main(sender,group,"dog")){
            main(sender, userName, group);
        }
    }
}
