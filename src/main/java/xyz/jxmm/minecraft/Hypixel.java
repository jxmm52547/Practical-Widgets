package xyz.jxmm.minecraft;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import xyz.jxmm.minecraft.arcade.Arcade;
import xyz.jxmm.minecraft.bw.BedWars;
import xyz.jxmm.minecraft.duels.Duels;
import xyz.jxmm.minecraft.fish.Fish;
import xyz.jxmm.minecraft.guild.Guild;
import xyz.jxmm.minecraft.mm.MurderMystery;
import xyz.jxmm.minecraft.guild.Tool;
import xyz.jxmm.minecraft.player.Online;
import xyz.jxmm.minecraft.player.Player;
import xyz.jxmm.minecraft.player.RecentGames;
import xyz.jxmm.minecraft.sw.SkyWars;
import xyz.jxmm.perm.Determine;

import static xyz.jxmm.minecraft.HypURLConnect.hypixelURLConnect;


public class Hypixel {

    public static void hypixel(String msg, Long sender, Group group){
        //信息部分
        String handle = msg.replaceAll("hyp","");
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);

        //数据部分
        StringBuilder ID = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder type = new StringBuilder();
        JsonObject json;

        if (handle.startsWith(" bw")){
            ID.append(handle.replaceAll(" bw ",""));//得到玩家ID
            type.append("bw");

            stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
            json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);

            if (!stringBuilder.toString().equals("")){
                run(type.toString(),json,chain,sender,group);
            }

        } else if (handle.startsWith(" sw")) {
            ID.append(handle.replaceAll(" sw ",""));
            type.append("sw");

            stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
            json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);

            if (!stringBuilder.toString().equals("")){
                run(type.toString(),json,chain,sender,group);
            }


        } else if (handle.startsWith(" player")){
            ID.append(handle.replaceAll(" player ",""));
            type.append("player");

            stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
            json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);

            if (!stringBuilder.toString().equals("")){
                JsonObject recentGames = new Gson().fromJson(RecentGames.main(ID.toString(),group,chain), JsonObject.class);
                JsonObject guild = new Gson().fromJson(Tool.main(ID.toString(),group,chain,"player"), JsonObject.class);
                JsonObject online = new Gson().fromJson(Online.main(ID.toString(),group,chain), JsonObject.class);

                if (error(json,chain,group) && error(recentGames,chain,group) && error(guild,chain,group) && error(online,chain,group)){
                    Player.player(json,recentGames,guild,online,sender,group);
                }
            }



        } else if (handle.startsWith(" arc")) {
            ID.append(handle.replaceAll(" arc ", ""));
            type.append("arc");

            stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
            json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);

            if (!stringBuilder.toString().equals("")){
                run(type.toString(),json,chain,sender,group);
            }

        } else if (handle.startsWith(" mm")) {
            ID.append(handle.replaceAll(" mm ", ""));
            type.append("mm");

            stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
            json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);

            if (!stringBuilder.toString().equals("")){
                run(type.toString(),json,chain,sender,group);
            }

        } else if (handle.startsWith(" duels")) {
            ID.append(handle.replaceAll(" duels ", ""));
            type.append("duels");

            stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
            json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);

            if (!stringBuilder.toString().equals("")){
                run(type.toString(),json,chain,sender,group);
            }

        } else if (handle.startsWith(" fish")){
            ID.append(handle.replaceAll(" fish ", ""));
            type.append("fish");

            stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
            json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);

            if (!stringBuilder.toString().equals("")){
                run(type.toString(),json,chain,sender,group);
            }
        } else if (handle.startsWith(" guild")) {
            ID.append(handle.replaceAll(" guild ", ""));
            Guild.common(ID.toString(),sender,group,chain);

        } else {
            chain.append(new PlainText("指令不完整, 缺少关键字或关键字错误"));
            chain.append(new PlainText("\n" + xyz.jxmm.PracticalWidgets.perfix()));
            chain.append(new PlainText("hyp <type> <playerID>"));
            chain.append(new PlainText("\n<type>包含如下关键字:" +
                    "\nplayer 玩家系列数据" +
                    "\narc 街机游戏\nbw 起床战争" +
                    "\nsw 空岛战争" +
                    "\nmm 密室杀手" +
                    "\nduels 决斗游戏" +
                    "\nfish 钓鱼数据" +
                    "\nguild 公会数据"));
            chain.append(new PlainText("\n<playerID> ID & 16进制uuid & 带连接符16进制uuid"));
            /*
                <tnt>掘战游戏
                <ww>羊毛战争
             */
            group.sendMessage(chain.build());
        }




    }

    public static void run(String type,JsonObject json,MessageChainBuilder chain,Long sender,Group group){
        if (error(json,chain,group)) {
            switch (type){
                case "bw":
                    BedWars.bw(json,sender,group);
                    break;

                case "sw":
                    SkyWars.sw(json,sender,group);
                    break;

                case "arc":
                    Arcade.arc(json,sender,group);
                    break;

                case "mm":
                    MurderMystery.mm(json,sender,group);
                    break;

                case "duels":
                    Duels.duels(json,sender,group);
                    break;

                case "fish":
                    Fish.fish(json,sender,group);
                    break;

            }
        }
    }

    public static Boolean error(JsonObject json, MessageChainBuilder chain, Group group){
        return Error.err(json, chain, group);
    }

    public static String analysis(String ID , Group group,MessageChainBuilder chain){
        StringBuilder uuid = new StringBuilder();
        if (ID.length() < 32){
            uuid.append(MJURLConnect.moJangURLConnect(ID));
            return mojangErr(uuid.toString(),group,chain);
        } else if (ID.length() == 32){
            return mojangErr(ID,group,chain);
        } else if (ID.length() == 36){
            return mojangErr(ID,group,chain);
        } else {
            chain.append(new PlainText("<playerID> 错误"));
            group.sendMessage(chain.build());
            return "";
        }


    }

    public static String mojangErr(String uuid,Group group,MessageChainBuilder chain){
        switch (uuid) {
            case "":
                chain.append(new PlainText("NULL\n未知错误, 从MJ官方无法得到任何信息"));
                group.sendMessage(chain.build());
                return "";
            case "Connection timed out":
                chain.append(new PlainText("连接超时, 这可能是因为玩家不存在, 请检查您输入的玩家ID是否正确\n或者请检查您的网络状况"));
                group.sendMessage(chain.build());
                return "";
            case "FileNotFound":
                chain.append(new PlainText("玩家不存在, 请检查输入的玩家ID是否正确"));
                group.sendMessage(chain.build());
                return "";
            case "IO":
                chain.append(new PlainText("<playerID> 格式错误, 请输入正确的玩家ID"));
                group.sendMessage(chain.build());
                return "";
            case "reset":
                chain.append(new PlainText("链接已重置, 请稍后再试"));
                group.sendMessage(chain.build());
                return "";
            case "PKIX":
                chain.append(new PlainText("DNS劫持, 通常这是运营商问题, 请稍后再试"));
                group.sendMessage(chain.build());
                return "";
            default:
                //从hypixel官方API得到用户数据
                StringBuilder stringBuilder = new StringBuilder();

                if (hypixelURLConnect(uuid).equals("noHypixelAPI")) {
                    chain.append("请前往配置文件填写hypixelAPI后重试");

                    System.out.println("以下出现的报错如果是 NullPointerException, 这是正常现象，因为您未填写HypixelAPI, 如果不是请联系作者");
                    System.out.println("如果您在配置文件未找到HypixelAPI填写位置,请重启mirai后检查配置文件, 如果还是没有请联系作者");
                    group.sendMessage(chain.build());
                    return "";
                } else {
                    stringBuilder.append(hypixelURLConnect(uuid));
                    return stringBuilder.toString();
                }
        }
    }

    public static void perm(String msg,Long sender,Group group){
        if (Determine.main(sender,group,"hyp")){
            hypixel(msg,sender,group);
        }
    }

    public static Boolean value(String ID, Group group, MessageChainBuilder chain){
        return !analysis(ID, group, chain).equals("");
    }
}
