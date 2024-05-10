package xyz.jxmm.member_leave;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

import static xyz.jxmm.tools.FileReaderMethod.fileReader;

public class MemberLeave {

    static File cfg = new File("./PracticalWidgets/config.properties");
    static File enableGroup = new File("./PracticalWidgets/perm/EnableGroup.json");
    public static void memberLeave(Group group, Member member,String nick) throws IOException {
        String quit = "";
        String quitExpress = "";
        Properties properties = new Properties();
        properties.load(new InputStreamReader(Files.newInputStream(cfg.toPath()), StandardCharsets.UTF_8));
        quit = properties.getProperty("quit");
        quitExpress = properties.getProperty("quitExpress");

        MessageChainBuilder chain = new MessageChainBuilder();
        long memberID = member.getId();
        String memberNick = member.getNick();

        chain.append(new PlainText(memberNick));
        chain.append(new PlainText(" "));
        chain.append(new PlainText(quit));
        chain.append(new PlainText(", QQ号: "));
        chain.append(new PlainText(Long.toString(memberID)));
        chain.append(new PlainText(", 群昵称: "));
        chain.append(new PlainText(nick));

        if (quitExpress != null){
            chain.append(new PlainText(", "));
            chain.append(new PlainText(quitExpress));
        }

        group.sendMessage(chain.build());
    }

    public static void perm(Group group, Member member,String nick){
        JsonObject json = new Gson().fromJson(fileReader(enableGroup.getPath()), JsonObject.class);
        if (json.get("g" + group.getId()).getAsJsonObject().get("groupMemberQuit").getAsBoolean()){
            try {
                memberLeave(group,member,nick);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("群成员退出,未开启退出提醒,仅控制台提醒用于提示, 请查看上方控制台事件信息");
        }
    }
}
