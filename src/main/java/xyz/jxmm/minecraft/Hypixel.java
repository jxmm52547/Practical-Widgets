package xyz.jxmm.minecraft;

import static xyz.jxmm.minecraft.HypURLConnect.hypixelURLConnect;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import tax.cute.minecraftinfoapi.CommonException;

import xyz.jxmm.minecraft.arcade.Arcade;
import xyz.jxmm.minecraft.bw.BedWars;
import xyz.jxmm.minecraft.mm.MurderMystery;
import xyz.jxmm.minecraft.player.Guild;
import xyz.jxmm.minecraft.player.Player;
import xyz.jxmm.minecraft.player.RecentGames;
import xyz.jxmm.minecraft.sw.SkyWars;

import java.io.IOException;


public class Hypixel {
    public static void hypixel(String msg, Long sender, Group group){
        //信息部分
        String handle = msg.replaceAll("hyp ","");
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);

        //数据部分
        StringBuilder ID = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder type = new StringBuilder();
        JsonObject json;

        if (handle.startsWith("bw")){
            ID.append(handle.replaceAll("bw ",""));//得到玩家ID
            if (value(ID.toString(),group,chain)){
                stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
                json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
                type.append("bw");

                if (error(json,chain,group)){
                    BedWars.bw(json,sender,group);
                }
            }


        } else if (handle.startsWith("sw")) {
            ID.append(handle.replaceAll("sw ",""));
            if (value(ID.toString(),group,chain)){
                stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
                json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
                type.append("sw");

                if (error(json,chain,group)){
                    SkyWars.sw(json,sender,group);
                }
            }


        } else if (handle.startsWith("player")){
            ID.append(handle.replaceAll("player ",""));
            if (value(ID.toString(),group,chain)){
                stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
                json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
                type.append("player");

                JsonObject recentGames = new Gson().fromJson(RecentGames.main(ID.toString(),group,chain), JsonObject.class);
                JsonObject guild = new Gson().fromJson(Guild.main(ID.toString(),group,chain), JsonObject.class);

                if (error(json,chain,group) && error(recentGames,chain,group) && error(guild,chain,group)){
                    Player.player(json,recentGames,guild,sender,group);
                }
            }

        } else if (handle.startsWith("acd")) {
            ID.append(handle.replaceAll("acd ", ""));
            if (value(ID.toString(),group,chain)){
                stringBuilder.append(analysis(ID.toString(),group,chain));
                json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
                type.append("acd");

                if (error(json,chain,group)) {
                    Arcade.acd(json,sender,group);
                }
            }
        } else if (handle.startsWith("mm")) {
            ID.append(handle.replaceAll("mm ", ""));
            if (value(ID.toString(),group,chain)){
                stringBuilder.append(analysis(ID.toString(),group,chain));
                json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
                type.append("mm");

                if (error(json,chain,group)) {
                    MurderMystery.mm(json,sender,group);
                }
            }

        } else {
            chain.append(new PlainText("指令不完整, 缺少关键字或关键字错误"));
            chain.append(new PlainText("\n/hyp <type> <playerID>"));
            chain.append(new PlainText("\n<type>包含如下关键字:\nplayer 玩家系列数据\nacd 街机游戏\nbw 起床战争\n" +
                    "sw 空岛战争\nmm 密室杀手"));
            chain.append(new PlainText("\n<playerID> 玩家ID(非UUID, 会通过ID转换至UUID, 暂不支持直接通过UUID查询)"));
            /*

<mm>密室杀手
<tnt>掘战游戏
<du>决斗游戏
<ww>羊毛战争
             */
            group.sendMessage(chain.build());
        }




    }

    public static Boolean error(JsonObject json, MessageChainBuilder chain, Group group){
        return Error.err(json, chain, group);
    }

    public static String analysis(String ID , Group group,MessageChainBuilder chain){
        String uuid = MJURLConnect.moJangURLConnect(ID);

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
                chain.append(new PlainText("玩家ID格式错误, 请输入正确的玩家ID"));
                group.sendMessage(chain.build());
                return "";
            case "reset":
                chain.append(new PlainText("链接已重置, 请稍后再试"));
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

    public static Boolean value(String ID, Group group, MessageChainBuilder chain){
        return !analysis(ID, group, chain).equals("");
    }
}
