package xyz.jxmm.newobject;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class NewObject {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/object.json");

    public static void newObject(Long sender, Group group){
        JsonObject main;
        try {
            main = new Gson().fromJson(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8), JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        MessageChain atSender = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(atSender);


        if (main.has(sender.toString())){
            Long object = main.get(sender.toString()).getAsLong();
            MessageChain atObject = MiraiCode.deserializeMiraiCode("[mirai:at:" + object + "]");

            chain.append(new PlainText("\n你今天都已经 new 一个对象了, 还不够?"));
            chain.append(new PlainText("\n你今天 new的对象是"));
            chain.append(atObject);

            group.sendMessage(chain.build());
        } else {
            //随机一位群成员
            ContactList<NormalMember> members = group.getMembers();
            int membersSize = members.getSize();
            int randomMember = new Random().nextInt(membersSize);
            JsonArray member = new Gson().fromJson(Arrays.toString(members.toArray()),JsonArray.class);
            List<Long> memberID = new ArrayList<>();

            for (int i = 0; i < member.size(); i++) {
                //遍历所有 NormalMember(123456), 将其转换为 123456 并写入List
                memberID.add(Long.valueOf(member.get(i).getAsString().replaceAll("NormalMember","").replace("(","").replace(")","")));
            }
            //得到随机群员ID
            Long object = memberID.get(randomMember);
            //@这个群员
            MessageChain atObject = MiraiCode.deserializeMiraiCode("[mirai:at:" + object + "]");

            //组成chain
            chain.append(new PlainText("\n你今天new的一个对象是"));
            chain.append(atObject);

            //输出
            group.sendMessage(chain.build());
            main.addProperty(sender.toString(),object);
            main.addProperty(object.toString(),sender);

            fileWriter("./PracticalWidgets/object.json", gson.toJson(main));
        }
    }

}
