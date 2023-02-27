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

import static xyz.jxmm.minecraft.HypURLConnect.hypixelURLConnect;

public class RecentGames {
    public static String main(String ID , Group group, MessageChainBuilder chain){
        String uuid = MJURLConnect.moJangURLConnect(ID);

        if (uuid.equals("Connection timed out")){
            chain.append(new PlainText("连接超时, 这可能是因为玩家不存在, 请检查您输入的玩家ID是否正确\n或者请检查您的网络状况"));
            group.sendMessage(chain.build());
            return "";
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            if (recent(uuid).equals("noHypixelAPI")){
                chain.append("请前往配置文件填写hypixelAPI后重试");

                System.out.println("以下出现的报错如果是 NullPointerException, 这是正常现象，因为您未填写HypixelAPI, 如果不是请联系作者");
                System.out.println("如果您在配置文件未找到HypixelAPI填写位置,请重启mirai后检查配置文件, 如果还是没有请联系作者");
                group.sendMessage(chain.build());
                return "";
            } else {
                stringBuilder.append(recent(uuid));
                return stringBuilder.toString();
            }
        }
    }

    public static String recent(String uuid){
        Properties properties = new Properties();
        File cfg = new File("./PracticalWidgets/config.properties");

        try {
            properties.load(new InputStreamReader(Files.newInputStream(cfg.toPath()), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String HypixelAPI = properties.getProperty("HypixelAPI");
        String connectURL = "https://api.hypixel.net/recentgames?key=" + HypixelAPI + "&uuid=" + uuid;
        return URLConnect.URLConnect(connectURL);
    }
}
