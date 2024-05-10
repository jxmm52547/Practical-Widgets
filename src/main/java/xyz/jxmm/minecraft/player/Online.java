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

public class Online {
    public static String main(String ID, Group group, MessageChainBuilder chain){
        String uuid;
        if (ID.length() < 32){
            uuid = MJURLConnect.moJangURLConnect(ID);
            return err(uuid,group,chain);
        } else if (ID.length() == 32){
            return err(ID,group,chain);
        } else if (ID.length() == 36){
            return err(ID,group,chain);
        } else {
            chain.append(new PlainText("<playerID> 错误"));
            group.sendMessage(chain.build());
            return "";
        }
    }

    public static String err(String uuid,Group group,MessageChainBuilder chain){
        if (uuid.equals("Connection timed out")){
            chain.append(new PlainText("连接超时, 这可能是因为玩家不存在, 请检查您输入的玩家ID是否正确\n或者请检查您的网络状况"));
            group.sendMessage(chain.build());
            return "";
        } else {
            return online(uuid);
        }
    }

    public static String online(String uuid){
        Properties properties = new Properties();
        File cfg = new File("./PracticalWidgets/config.properties");

        try {
            properties.load(new InputStreamReader(Files.newInputStream(cfg.toPath()), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String HypixelAPI = properties.getProperty("HypixelAPI");
        String connectURL = "https://api.hypixel.net/v2/status?key=" + HypixelAPI + "&uuid=" + uuid;
        return URLConnect.URLConnect(connectURL);
    }
}
