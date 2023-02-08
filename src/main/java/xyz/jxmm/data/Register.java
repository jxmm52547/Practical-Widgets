package xyz.jxmm.data;

import static xyz.jxmm.tools.FileWriter.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Register {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/data.json");
    public static void main(Long sender, String userName, Group group){
        JsonObject json;
        try {
            json = new Gson().fromJson(new FileReader("./PracticalWidgets/data.json"), JsonObject.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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

            try {
                fileWriter("./PracticalWidgets/data.json", gson.toJson(json));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText("\n注册成功,欢迎使用!"))
                    .build();
            group.sendMessage(chain);
        }
    }
}
