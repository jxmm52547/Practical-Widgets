package xyz.jxmm.jrrp;

import xyz.jxmm.data.JrrpTop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.mamoe.mirai.contact.Group;

import java.io.File;
import java.util.*;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

public class ResetJrrpTop extends Timer {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/jrrpTop.json");

    public static void reWrite(Group group) {
        file.delete();
        fileWriter(file.getPath(), gson.toJson(JrrpTop.gen(123456L,123L,"example",0)));
        if(group!=null){
            group.sendMessage("手动重置今日人品排行榜完成");
        }
    }

}
