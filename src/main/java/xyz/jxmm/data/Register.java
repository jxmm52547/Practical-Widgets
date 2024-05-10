package xyz.jxmm.data;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.perm.Determine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Register {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/data.json");

    public static void perm(Long sender,Group group) throws IOException {
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        if (Determine.register(group)){
            main(sender,group);
        } else {
            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText(" 此群已开启自动注册, 无需手动注册"))
                    .build();
            group.sendMessage(chain);
        }
    }

    public static void main(Long sender, Group group) throws IOException {
        JsonObject json = new Gson().fromJson(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8), JsonObject.class);
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        if (json.has(sender.toString())){

            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText("\n你已经注册过了,不要重复注册喔"))
                    .build();
            group.sendMessage(chain);
        } else {
            JsonObject jsobj = MainExample.main();

            json.add(sender.toString(), jsobj);

            fileWriter(file.getPath(), gson.toJson(json));

            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText("\n注册成功,欢迎使用!"))
                    .build();
            group.sendMessage(chain);
        }
    }
}
