package xyz.jxmm;

import xyz.jxmm.data.*;
import xyz.jxmm.tools.*;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.io.IOException;


public final class PracticalWidgets extends JavaPlugin {
    public static final PracticalWidgets INSTANCE = new PracticalWidgets();

    private PracticalWidgets() {
        super(new JvmPluginDescriptionBuilder("xyz.jxmm.Practical_Widgets", "0.1.1")
                .name("实用小组件")
                .author("靖暄")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("实用小组件  已加载!");//插件加载提示
        getLogger().info("当前功能: 舔狗日记, 今日人品, 今日人品排行榜");

        try {//数据库相关
            Data.main();
            JrrpTop.main(123L,"example", 0);
            xyz.jxmm.jrrp.ResetJrrpTop.timerTask();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
        eventChannel.subscribeAlways(GroupMessageEvent.class, g -> {
            String msg = g.getMessage().contentToString();
            Long sender = g.getSender().getId();
            String userName = g.getSenderName();
            Group group = g.getGroup();

            if(msg.equals("/update")){
                MainExample.update(group);
            } else if (msg.equals("/update jrrpTop")) {
//                JrrpTop.update(group);
            } else if (JrrpMap.JrrpMap(msg)){
                xyz.jxmm.jrrp.Main.main(sender, userName, group);
            } else if (msg.equals("/注册")) {
                Register.main(sender, userName, group);
            } else if (msg.equals("/舔狗日记")) {
                xyz.jxmm.dog.Main.main(sender,userName, group);
            } else if (JrrpMap.JrrpTopMap(msg)) {
                xyz.jxmm.jrrp.jrrpTop.jrrpTop(group);
            } else if (msg.equals("/reset")) {
                xyz.jxmm.jrrp.ResetJrrpTop.reWrite(group);
            }
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("实用小组件  已卸载!");
    }
}