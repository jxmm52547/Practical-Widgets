package xyz.jxmm.music;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.perm.Determine;
import xyz.jxmm.tools.URLConnect;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class Main {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static Properties properties = new Properties();
    static File file = new File("./PracticalWidgets/config.properties");

    public static void main(String msg, Group group, Long sender,MessageSource source,boolean tf,int id) {
        String name = msg;
        if (msg.startsWith("点歌 ")){
            name = msg.replace("点歌 ","");
        }

        try {
            properties.load(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            name = URLEncoder.encode(name, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String ALAPIToken = properties.getProperty("ALAPIToken");
        StringBuilder result = new StringBuilder();

        if (ALAPIToken.equals("123456")){
            group.sendMessage("请联系 BOT拥有者 前往配置文件填写Token后重试(无需重启)");
        } else {
            String music = "https://v2.alapi.cn/api/music/search?limit=10&keyword=" + name + "&token=" + ALAPIToken;
            result.append(URLConnect.URLConnect(music));
        }

        JsonObject json = new Gson().fromJson(result.toString(), JsonObject.class);
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        if (json.get("code").getAsInt() != 200){
            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText("请求错误"))
                    .append(new PlainText("\n错误代码: ")).append(json.get("code").getAsString())
                    .append(new PlainText("\n错误信息: ")).append(json.get("msg").getAsString())
                    .build();
            group.sendMessage(chain);
        } else {
            if (!tf){
                Songs.main(group,json.get("data").getAsJsonObject().get("songs").getAsJsonArray(),source);
            } else {
                Music.main(sender,group,json,id);
            }


        }
    }

    public static void perm(String msg,Long sender,Group group,MessageSource source, boolean tf,int id){
        if (Determine.main(sender,group,"song")){
            main(msg, group, sender, source, tf,id);
        }
    }


}
