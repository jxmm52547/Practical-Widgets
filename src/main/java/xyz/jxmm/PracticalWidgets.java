package xyz.jxmm;

import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import xyz.jxmm.data.*;
import xyz.jxmm.data.Object;
import xyz.jxmm.perm.Perm;
import xyz.jxmm.tools.*;
import xyz.jxmm.config.Main;
import xyz.jxmm.member_leave.MemberLeave;

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
        super(new JvmPluginDescriptionBuilder("xyz.jxmm.Practical_Widgets", version())
                .name("Practical-Widgets")
                .author("靖暄")
                .build());
    }

    public static String version(){
        return "0.5.1";  //每次更新修改
    }

    @Override
    public void onEnable() {
        getLogger().info("Practical-Widgets  已加载!");//插件加载提示

        try {//数据库相关
            Perm.example();
            Data.main();
            JrrpTop.main();
            TimerTask.timerTask();
            Main.main();
            Object.main();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EventChannel<Event> botOnline = GlobalEventChannel.INSTANCE.parentScope(this);
        botOnline.subscribeAlways(BotOnlineEvent.class, b -> {
            ContactList<Group> groupContactList = b.getBot().getGroups();
            Perm.creatNew(groupContactList);
        });

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
            if (msg.startsWith(prefix)){
                msg = msg.replace(prefix,"");

                try {
                    if(msg.equals("update")){
                        MainExample.perm(group,sender);
                    } else if (msg.equals("reset jrrptop")) {
                        xyz.jxmm.jrrp.ResetJrrpTop.perm(group,sender);
                    } else if (JrrpMap.jrrpMap(msg)){
                        xyz.jxmm.jrrp.Main.perm(userName,sender, group);
                    } else if (msg.equals("注册")) {
                        Register.perm(sender, group);
                    } else if (msg.equals("舔狗日记")) {
                        xyz.jxmm.dog.Main.perm(userName, sender, group);
                    } else if (JrrpMap.JrrpTopMap(msg)) {
                        xyz.jxmm.jrrp.JrrpTop.perm(sender, group);
                    } else if(msg.startsWith("点歌")){
                        xyz.jxmm.music.Main.perm(msg, sender, group);
                    } else if(msg.startsWith("new对象")){
                        xyz.jxmm.new_object.NewObject.perm(sender, group);
                    } else if (msg.startsWith("hyp ")){
                        xyz.jxmm.minecraft.Hypixel.perm(msg,sender,group);
                    } else if (msg.equals("签到") || msg.equals("sign")){
                        xyz.jxmm.sign.Sign.perm(sender,group);
                    } else if (msg.startsWith("bl ")){
                        xyz.jxmm.perm.BlackList.blackLIst(msg,sender,group);
                    } else if (msg.startsWith("perm ")) {
                        xyz.jxmm.perm.PermissionGenerator.permissionGenerator(msg.replaceAll("perm ",""),sender,group);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

        });

        //群成员离开通知
        eventChannel.subscribeAlways(MemberLeaveEvent.class, ml ->{

            Properties properties = new Properties();
            File file = new File("./PracticalWidgets/config.properties");
            try {
                properties.load(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (properties.getProperty("memberQuitSwitch").equals("true")){
                Group group = ml.getGroup();
                Member member = ml.getMember();
                String nick = ml.getMember().getNick();
                MemberLeave.perm(group,member,nick);
            } else {
                System.out.println("群成员退出,未开启退出提醒,仅控制台提醒用于提示, 请查看上方控制台事件信息");
            }


        });
    }

    @Override
    public void onDisable() {
        getLogger().info("一点小功能  已卸载!");
    }
}