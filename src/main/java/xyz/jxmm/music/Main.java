package xyz.jxmm.music;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.*;
import xyz.jxmm.tools.*;

import net.mamoe.mirai.contact.Group;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class Main {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static Properties properties = new Properties();
    static File file = new File("./PracticalWidgets/config.properties");

    public static void main(String msg, Group group, Long sender) {
        String name = msg.replace("/点歌 ","");

        URL url;
        BufferedReader in;

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
            String music = "https://v2.alapi.cn/api/music/search?limit=1&keyword=" + name + "&token=" + ALAPIToken;
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
            JsonObject song = json.get("data").getAsJsonObject().get("songs").getAsJsonArray().get(0).getAsJsonObject();
            String musicTitle = song.get("name").getAsString();
            Long musicID = song.get("id").getAsLong();

            JsonArray artists = song.get("artists").getAsJsonArray();
            StringBuilder artistsName = new StringBuilder();
            for (int i = 0;i < artists.size();i++){
                artistsName.append(artists.get(i).getAsJsonObject().get("name").getAsString());
                artistsName.append("/");
            }

            String jumpURL ="https://y.music.163.com/m/song?id=" + musicID;
            String imgURL = getImgURL(group,musicID,properties,at);
            String musicURL = "http://music.163.com/song/media/outer/url?id=" + musicID;

            MusicShare musicShare = new MusicShare(
                MusicKind.NeteaseCloudMusic,
                musicTitle,
                    artistsName.toString(),
                    jumpURL,
                    imgURL,
                    musicURL
            );
            group.sendMessage(musicShare);
        }
    }

    public static String getImgURL(Group group,Long musicID,Properties properties,MessageChain at){
        URL url;
        BufferedReader in;

        String ALAPIToken = properties.getProperty("ALAPIToken");
        StringBuilder result = new StringBuilder();
        StringBuilder imgURL = new StringBuilder();

        if (ALAPIToken.equals("123456")){
            group.sendMessage("请联系 BOT拥有者 前往配置文件填写Token后重试(无需重启)");
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String music = "https://v2.alapi.cn/api/music/detail?id=" + musicID + "&token=" + ALAPIToken;
            result.append(URLConnect.URLConnect(music));

            JsonObject json = new Gson().fromJson(result.toString(), JsonObject.class);
            if (json.get("code").getAsInt() != 200){
                MessageChain chain = new MessageChainBuilder()
                        .append(at)
                        .append(new PlainText("图片请求错误"))
                        .append(new PlainText("\n错误代码: ")).append(json.get("code").getAsString())
                        .append(new PlainText("\n错误信息: ")).append(json.get("msg").getAsString())
                        .build();
                group.sendMessage(chain);
            } else {
                imgURL.append(json.get("data").getAsJsonObject().get("songs").getAsJsonArray()
                        .get(0).getAsJsonObject().get("al").getAsJsonObject()
                        .get("picUrl").getAsString());

            }
        }
        return imgURL.toString();
    }
}
