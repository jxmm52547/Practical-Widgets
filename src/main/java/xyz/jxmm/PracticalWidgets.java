package xyz.jxmm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.QuoteReply;
import xyz.jxmm.config.Main;
import xyz.jxmm.data.Object;
import xyz.jxmm.data.*;
import xyz.jxmm.kokomi.Kokomi;
import xyz.jxmm.member_leave.MemberLeave;
import xyz.jxmm.perm.Determine;
import xyz.jxmm.perm.Perm;
import xyz.jxmm.tools.FileWriterMethod;
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
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private PracticalWidgets() {
        super(new JvmPluginDescriptionBuilder("xyz.jxmm.Practical_Widgets", version())
                .name("Practical-Widgets")
                .author("靖暄")
                .build());
    }

    public static String version(){
        return "0.6.1";  //每次更新修改
    }

    public static String perfix(){
        Properties properties = Main.properties;
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
            Kokomi.fileExists();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String prefix = perfix();

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

            if (msg.startsWith(prefix)){
                msg = msg.replace(prefix,"");

                try {
                    if(msg.equals("update")) {
                        MainExample.perm(group, sender);
                    } else if (msg.equals("清理全部聊天记录")) {
                        Kokomi.clearAll(group, sender);
                    } else if (msg.equals("清理聊天记录")) {
                        Kokomi.clear(group,null, sender);
                    } else if (msg.equals("保存聊天记录")) {
                        Kokomi.save();
                        group.sendMessage("已保存");
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
                    } else if (msg.startsWith("prem ")) {
                        xyz.jxmm.perm.PermissionGenerator.permissionGenerator(msg.replaceAll("prem ",""),sender,group);
                    } else if (msg.startsWith("system")) {
                        Kokomi.setSystem(sender,msg.replaceAll("system ",""), group, null);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else if (msg.startsWith("@" + g.getBot().getId())) {
                xyz.jxmm.kokomi.Kokomi.main(msg.replaceAll("@" + g.getBot().getId() + " ",""),sender,group,null,source);

            }


                //给点歌用的
            QuoteReply music = source.getOriginalMessage().get(QuoteReply.Key);
            if(music != null && msg.length() <= 2){
                String musicName = music.getSource().getOriginalMessage().contentToString();
                if (musicName.startsWith(prefix + "点歌")){
                    musicName = musicName.replaceAll(prefix +  "点歌 ","");
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

        eventChannel.subscribeAlways(BotJoinGroupEvent.class, bj ->{
            ContactList<Group> groupContactList = bj.getBot().getGroups();
            Perm.creatNew(groupContactList);
        });

        eventChannel.subscribeAlways(FriendMessageEvent.class, f ->{
            Long sender = f.getSender().getId();
            String msg = f.getMessage().contentToString();
            Friend friend = f.getSender();

            if (msg.startsWith(prefix)){
                msg = msg.replace(prefix,"");

                if (msg.equals("清理聊天记录")) {
                    Kokomi.clear(null,friend,sender);
                }
                else if (msg.equals("保存聊天记录")) {
                    Kokomi.save();
                    friend.sendMessage("末酱会一直记住你的");
                }
                else if (msg.startsWith("system")) {
                    Kokomi.setSystem(sender,msg.replaceAll("system ",""),null,friend);
                }
            }else {
                Kokomi.main(msg,sender,null,friend,null);
            }

        });
    }

    @Override
    public void onDisable() {
        FileWriterMethod.fileWriter(Data.TidewhisperScrolls.getPath(), gson.toJson(Kokomi.TidewhisperScrolls));
        getLogger().info("聊天记录已保存");
        getLogger().info("一点小功能  已卸载!");
    }
}