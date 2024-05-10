package xyz.jxmm.jrrp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.perm.Determine;

import java.io.File;
import java.util.*;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

public class ResetJrrpTop extends Timer {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/JrrpTop.json");

    public static void reWrite(Group group) {
        file.delete();
        fileWriter(file.getPath(), gson.toJson(xyz.jxmm.data.JrrpTop.gen(123456L,123L,"example",0)));
        if(group!=null){
            group.sendMessage("手动重置今日人品排行榜完成");
        }
    }

    public static void perm(Group group,Long sender){
        if (Determine.admin(group,sender)){
            reWrite(group);
        } else {
            group.sendMessage("你没有管理员权限!");
        }
    }

}
