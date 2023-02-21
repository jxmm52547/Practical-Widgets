package xyz.jxmm.Minecraft;

import static xyz.jxmm.Minecraft.HypURLConnect.hypixelURLConnect;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import xyz.jxmm.Minecraft.bw.BedWars;
import xyz.jxmm.Minecraft.player.Player;
import xyz.jxmm.Minecraft.sw.SkyWars;

public class Hypixel {
    public static void hypixel(String msg, Long sender, Group group){
        //信息部分
        String handle = msg.replaceAll("/hyp ","");
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);

        //数据部分
        StringBuilder ID = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder type = new StringBuilder();

        if (handle.startsWith("bw")){
            ID.append(handle.replaceAll("bw ",""));//得到玩家ID
            stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
            type.append("bw");

        } else if (handle.startsWith("sw")) {
            ID.append(handle.replaceAll("sw ",""));
            stringBuilder.append(analysis(ID.toString(),group,chain));
            type.append("sw");

        } else if (handle.startsWith("player")){
            ID.append(handle.replaceAll("player ",""));
            stringBuilder.append(analysis(ID.toString(),group,chain));
            type.append("player");
        }

        JsonObject json = new Gson().fromJson(stringBuilder.toString(),JsonObject.class);
        if (Error.err(json,chain,group)){
            switch (type.toString()){
                case "bw":
                    BedWars.bw(json,sender,group);
                    break;
                case "sw":
                    SkyWars.sw(json,sender,group);
                    break;
                case "player":
                    Player.player(json,sender,group);
                    break;
            }
        }
    }

    public static String analysis(String ID , Group group,MessageChainBuilder chain){
        String uuid = MJURLConnect.moJangURLConnect(ID);

        //从hypixel官方API得到用户数据
        StringBuilder stringBuilder = new StringBuilder();

        if (hypixelURLConnect(uuid).equals("noHypixelAPI")){
            chain.append("请前往配置文件填写hypixelAPI后重试");

            System.out.println("以下出现的报错如果是 NullPointerException, 这是正常现象，因为您未填写HypixelAPI, 如果不是请联系作者");
            System.out.println("如果您在配置文件未找到HypixelAPI填写位置,请重启mirai后检查配置文件, 如果还是没有请联系作者");
            group.sendMessage(chain.build());
            return null;
        } else {
            stringBuilder.append(hypixelURLConnect(uuid));
            return stringBuilder.toString();
        }

    }
}
