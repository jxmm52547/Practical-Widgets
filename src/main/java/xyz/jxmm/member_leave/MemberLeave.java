package xyz.jxmm.member_leave;

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

public class MemberLeave {

    static File cfg = new File("./PracticalWidgets/config.properties");
    public static void memberLeave(Group group, Member member) throws IOException {
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

        if (quitExpress != null){
            chain.append(new PlainText(", "));
            chain.append(new PlainText(quitExpress));
        }

        group.sendMessage(chain.build());
    }
}
