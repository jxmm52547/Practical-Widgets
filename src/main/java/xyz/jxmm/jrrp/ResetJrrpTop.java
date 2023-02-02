package xyz.jxmm.jrrp;

import xyz.jxmm.data.JrrpTop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.mamoe.mirai.contact.Group;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static xyz.jxmm.tools.FileWriter.fileWriter;

public class ResetJrrpTop {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/jrrpTop.json");

    public static long getToday0TimeStamp() {
        //当前时间的时间戳j
        long time = System.currentTimeMillis();
        long zero = time / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        Timestamp timestamp = new Timestamp(zero);
        //今天零点零分零秒
        return timestamp.getTime();
    }

    public static void reWrite(Group group) {
        file.delete();
        try {
            fileWriter("./PracticalWidgets/jrrpTop.json", gson.toJson(JrrpTop.gen()));
            if(group!=null){
                group.sendMessage("手动重置今日人品排行榜完成");
            }
        } catch (IOException e) {
            if (group!=null){
                group.sendMessage("手动重置今日人品排行榜出错\n原因: " + e);
            }
            throw new RuntimeException(e);
        }
    }

    public static void timerTask() throws InterruptedException {
        AtomicReference<Long> now = new AtomicReference<>(System.currentTimeMillis());
        Long tomorrow = getToday0TimeStamp() + 86400000;
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            now.set(getToday0TimeStamp());
            ResetJrrpTop.reWrite(null);
        }, 0, tomorrow - now.get(), TimeUnit.MILLISECONDS);
    }

}
