package xyz.jxmm;

import xyz.jxmm.data.*;
import xyz.jxmm.data.Object;
import xyz.jxmm.tools.*;
import xyz.jxmm.config.Main;
import xyz.jxmm.member_leave.memberLeave;

import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public final class PracticalWidgets extends JavaPlugin {
    public static final PracticalWidgets INSTANCE = new PracticalWidgets();

    private PracticalWidgets() {
        super(new JvmPluginDescriptionBuilder("xyz.jxmm.Practical_Widgets", "0.4.2-Alpha")
                .name("一点小功能")
                .author("靖暄")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("一点小功能  已加载!");//插件加载提示
        getLogger().info("当前功能: 舔狗日记, 今日人品, 今日人品排行榜, 点歌, new对象, Hyp信息查询, 退群提醒");

        try {//数据库相关
            Data.main();
            JrrpTop.main();
            TimerTask.timerTask();
            Main.main();
            Object.main();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
        eventChannel.subscribeAlways(GroupMessageEvent.class, g -> {
            String msg = g.getMessage().contentToString();
            Long sender = g.getSender().getId();
            String userName = g.getSenderName();
            Group group = g.getGroup();

            Properties properties = new Properties();
            File file = new File("./PracticalWidgets/config.properties");
            try {
                properties.load(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String prefix = properties.getProperty("prefix");
            if (msg.startsWith(prefix)){;
                msg = msg.replace(prefix,"");

                if(msg.equals("update")){
                    MainExample.update(group);
                } else if (msg.equals("reset jrrptop")) {
                    xyz.jxmm.jrrp.ResetJrrpTop.reWrite(group);
                } else if (JrrpMap.jrrpMap(msg)){
                    try {
                        xyz.jxmm.jrrp.Main.main(sender, userName, group);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (msg.equals("注册")) {
                    Register.main(sender, userName, group);
                } else if (msg.equals("舔狗日记")) {
                    xyz.jxmm.dog.Main.main(sender,userName, group);
                } else if (JrrpMap.JrrpTopMap(msg)) {
                    xyz.jxmm.jrrp.JrrpTop.jrrpTop(group,sender);
                } else if(msg.startsWith("点歌")){
                    xyz.jxmm.music.Main.main(msg,group,sender);
                } else if(msg.startsWith("new对象")){
                    xyz.jxmm.new_object.NewObject.newObject(sender, group);
                } else if (msg.startsWith("hyp ")){
                    xyz.jxmm.minecraft.Hypixel.hypixel(msg,sender,group);
                }
            }

        });

        eventChannel.subscribeAlways(MemberLeaveEvent.class, ml ->{
            Group group = ml.getGroup();
            Member member = ml.getMember();

            try {
                memberLeave.memberLeave(group,member);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("一点小功能  已卸载!");
    }
}