package xyz.jxmm.data;

import static xyz.jxmm.tools.FileWriter.fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;

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

        if (json.has(sender.toString())){
            group.sendMessage("用户名: " + userName + "\n你已经注册过了,不要重复注册喔");
        } else {
            JsonObject jsobj = MainExample.main();

            json.add(sender.toString(), jsobj);

            try {
                fileWriter("./PracticalWidgets/data.json", gson.toJson(json));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            group.sendMessage("用户名: " + userName  + "\nQQ号: " + sender + "\n注册成功,欢迎使用!");
        }
    }
}
