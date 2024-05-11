package xyz.jxmm;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.QuoteReply;
import xyz.jxmm.config.Main;
import xyz.jxmm.data.Object;
import xyz.jxmm.data.*;
import xyz.jxmm.member_leave.MemberLeave;
import xyz.jxmm.perm.Perm;
import xyz.jxmm.tools.JrrpMap;
import xyz.jxmm.tools.TimerTask;

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
        return "0.6.0";  //每次更新修改
    }

    public static String perfix(){
        Properties properties = new Properties();
        File file = new File("./PracticalWidgets/config.properties");
        try {
            properties.load(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties.getProperty("prefix");
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
            MessageSource source = g.getSource();
            String msg = g.getMessage().contentToString();
            Long sender = g.getSender().getId();
            String userName = g.getSenderName();
            Group group = g.getGroup();

            String prefix = perfix();
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
                        xyz.jxmm.music.Main.perm(msg, sender, group, source, false,0);
                    } else if(msg.startsWith("new对象")){
                        xyz.jxmm.new_object.NewObject.perm(sender, group);
                    } else if (msg.startsWith("hyp")){
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

            //给点歌用的
            QuoteReply music = source.getOriginalMessage().get(QuoteReply.Key);
            if(music != null && msg.length() <= 2){
                String musicName = music.getSource().getOriginalMessage().contentToString();
                if (musicName.startsWith("/点歌 ")){
                    musicName = musicName.replaceAll("/点歌 ","");
                    int id = Integer.parseInt(msg);
                    if (id > 0 && id <= 10){
                        xyz.jxmm.music.Main.perm(musicName, sender, group, source, true,id);
                    } else {
                        group.sendMessage(new QuoteReply(source).plus("请回复 1~10 点歌"));
                    }

                }

            }

        });



        //群成员离开通知
        eventChannel.subscribeAlways(MemberLeaveEvent.class, ml ->{

            Group group = ml.getGroup();
            Member member = ml.getMember();
            String nick = ml.getMember().getNick();
            MemberLeave.perm(group,member,nick);


        });
    }

    @Override
    public void onDisable() {
        getLogger().info("一点小功能  已卸载!");
    }
}