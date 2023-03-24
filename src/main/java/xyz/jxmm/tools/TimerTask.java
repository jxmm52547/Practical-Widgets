package xyz.jxmm.tools;

import xyz.jxmm.new_object.ResetObject;
import xyz.jxmm.jrrp.ResetJrrpTop;

import java.util.Calendar;
import java.util.Timer;

public class TimerTask {

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
        t.schedule(new java.util.TimerTask() {
            public void run() {
                //run中填写定时器主要执行的代码块
                ResetJrrpTop.reWrite(null);
                ResetObject.reset();
                System.out.println("今日人品排行榜已重置");
                System.out.println("new对象已重置");
            }
        }, date.getTime(), daySpan); //daySpan是一天的毫秒数，也是执行间隔

    }
}
