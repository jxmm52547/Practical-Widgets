package xyz.jxmm.minecraft.player;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.minecraft.MJURLConnect;
import xyz.jxmm.tools.URLConnect;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class Guild {
    public static String main(String ID, Group group, MessageChainBuilder chain){
        String uuid = MJURLConnect.moJangURLConnect(ID,"name");
        StringBuilder stringBuilder = new StringBuilder();

        if (uuid.equals("Connection timed out")){
            chain.append(new PlainText("连接超时, 这可能是因为玩家不存在, 请检查您输入的玩家ID是否正确\n或者请检查您的网络状况"));
            group.sendMessage(chain.build());
            return "";
        } else {
            stringBuilder.append(guild(uuid));
            return stringBuilder.toString();
        }
    }

    public static String guild(String uuid){
        Properties properties = new Properties();
        File cfg = new File("./PracticalWidgets/config.properties");

        try {
            properties.load(new InputStreamReader(Files.newInputStream(cfg.toPath()), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String HypixelAPI = properties.getProperty("HypixelAPI");
        String connectURL = "https://api.hypixel.net/guild?key=" + HypixelAPI + "&player=" + uuid;
        return URLConnect.URLConnect(connectURL);
    }
}
