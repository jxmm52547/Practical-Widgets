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

    public static void timerTask(){
        //得到时间类
        Calendar date = Calendar.getInstance();
        //设置时间为 xx-xx-xx 00:00:00
        date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), 23, 59, 59);
        //一天的毫秒
        long daySpan = 24 * 60 * 60 * 1000;
        //得到定时器实例
        Timer t = new Timer();
        //使用匿名内方式进行方法覆盖
        t.schedule(new TimerTask() {
            public void run() {
                //run中填写定时器主要执行的代码块
                ResetJrrpTop.reWrite(null);
                System.out.println("今日人品排行榜已重置");
            }
        }, date.getTime(), daySpan); //daySpan是一天的毫秒数，也是执行间隔

    }

}
