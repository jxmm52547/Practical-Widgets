package xyz.jxmm.minecraft;

import static xyz.jxmm.minecraft.HypURLConnect.hypixelURLConnect;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.minecraft.bw.BedWars;
import xyz.jxmm.minecraft.player.Player;
import xyz.jxmm.minecraft.sw.SkyWars;

import java.util.Objects;

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
        JsonObject json = new JsonObject();

        if (handle.startsWith("bw")){
            ID.append(handle.replaceAll("bw ",""));//得到玩家ID
            if (value(ID.toString(),group,chain)){
                stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
                json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
                type.append("bw");
                err(json,chain,group,type,sender);
            }


        } else if (handle.startsWith("sw")) {
            ID.append(handle.replaceAll("sw ",""));
            if (value(ID.toString(),group,chain)){
                stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
                json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
                type.append("sw");
                err(json,chain,group,type,sender);
            }


        } else if (handle.startsWith("player")){
            ID.append(handle.replaceAll("player ",""));
            if (value(ID.toString(),group,chain)){
                stringBuilder.append(analysis(ID.toString(),group,chain));//将玩家信息写入stringBuilder
                json = new Gson().fromJson(stringBuilder.toString(), JsonObject.class);
                type.append("player");
                err(json,chain,group,type,sender);
            }

        } else {
            chain.append(new PlainText("指令不完整, 缺少关键字或关键字错误"));
            chain.append(new PlainText("\n/hyp <type> <playerID>"));
            group.sendMessage(chain.build());
        }




    }

    public static void err(JsonObject json, MessageChainBuilder chain, Group group, StringBuilder type, Long sender){
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

        if (uuid.equals("Connection timed out")){
            chain.append(new PlainText("连接超时, 这可能是因为玩家不存在, 请检查您输入的玩家ID是否正确\n或者请检查您的网络状况"));
            group.sendMessage(chain.build());
            return "";
        } else {
            //从hypixel官方API得到用户数据
            StringBuilder stringBuilder = new StringBuilder();

            if (hypixelURLConnect(uuid).equals("noHypixelAPI")){
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
