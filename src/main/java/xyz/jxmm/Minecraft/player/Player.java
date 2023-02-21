package xyz.jxmm.Minecraft.player;

import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Player {
    public static void player(JsonObject json, Long sender, Group group){
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);
        JsonObject playerJson = json.get("player").getAsJsonObject();
        JsonObject bwJson = playerJson.get("stats").getAsJsonObject().get("Bedwars").getAsJsonObject();
        JsonObject swJson = playerJson.get("stats").getAsJsonObject().get("SkyWars").getAsJsonObject();
        JsonObject arcade = playerJson.get("stats").getAsJsonObject().get("Arcade").getAsJsonObject();
        JsonObject TNTGames = playerJson.get("stats").getAsJsonObject().get("TNTGames").getAsJsonObject();
        JsonObject achievements = playerJson.get("achievements").getAsJsonObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年-MM月-dd日-hh时-mm分-ss秒");

        chain.append(new PlainText("\n玩家名: "));
        chain.append(new PlainText(playerJson.get("playername").getAsString()));
        chain.append(new PlainText("\n首次登录时间: "));
        chain.append(new PlainText(simpleDateFormat.format(new Date(playerJson.get("firstLogin").getAsLong()))));
        chain.append(new PlainText("\n上次登录时间: "));
        chain.append(new PlainText(simpleDateFormat.format(new Date(playerJson.get("lastLogin").getAsLong()))));
        chain.append(new PlainText("\n上次注销时间: "));
        chain.append(new PlainText(simpleDateFormat.format(new Date(playerJson.get("lastLogout").getAsLong()))));
        chain.append(new PlainText("\n玩家使用的语言: "));
        chain.append(new PlainText(playerJson.get("userLanguage").getAsString()));
        chain.append(new PlainText("\n成就点数: "));
        chain.append(new PlainText(String.valueOf(playerJson.get("achievementPoints").getAsInt())));
        chain.append(new PlainText(" | 人品值: "));
        chain.append(new PlainText(String.valueOf(playerJson.get("karma").getAsInt())));

        chain.append(new PlainText("\n街机金币: "));
        chain.append(new PlainText(String.valueOf(arcade.get("coins").getAsInt())));
        chain.append(new PlainText(" | 街机总胜利数: "));
        chain.append(new PlainText(String.valueOf(achievements.get("arcade_arcade_winner").getAsInt())));

        chain.append(new PlainText("\n起床战争等级: "));
        chain.append(new PlainText(String.valueOf(achievements.get("bedwars_level").getAsInt())));
        chain.append(new PlainText(" | 起床战争金币: "));
        chain.append(new PlainText(String.valueOf(bwJson.get("coins").getAsInt())));
        chain.append(new PlainText(" | 起床战争连胜: "));
        chain.append(new PlainText(String.valueOf(bwJson.get("winstreak").getAsInt())));

        chain.append(new PlainText("\n空岛战争等级: "));
        chain.append(new PlainText(swJson.get("levelFormatted").getAsString().replace("§7", "")));
        chain.append(new PlainText(" | 空岛战争金币: "));
        chain.append(new PlainText(String.valueOf(swJson.get("coins").getAsInt())));
        chain.append(new PlainText(" | 空岛战争连胜: "));
        chain.append(new PlainText(String.valueOf(swJson.get("win_streak").getAsInt())));

        chain.append(new PlainText("\nTNT游戏金币: "));
        chain.append(new PlainText(String.valueOf(TNTGames.get("coins").getAsInt())));
        chain.append(new PlainText(" | TNT游戏胜场: "));
        chain.append(new PlainText(String.valueOf(TNTGames.get("wins").getAsInt())));
        chain.append(new PlainText(" | TNT游戏连胜: "));
        chain.append(new PlainText(String.valueOf(TNTGames.get("winstreak").getAsInt())));

        group.sendMessage(chain.build());
    }
}
