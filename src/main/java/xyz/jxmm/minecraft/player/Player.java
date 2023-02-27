package xyz.jxmm.minecraft.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Player {
    public static void player(JsonObject json,JsonObject recentGames, Long sender, Group group){
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);
        JsonObject playerJson;
        JsonObject bwJson;
        JsonObject swJson;
        JsonObject arcade;
        JsonObject TNTGames;
        JsonObject achievements;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年-MM月-dd日-HH时-mm分-ss秒", Locale.CHINA);

        if (json.get("player").isJsonObject()){
            playerJson = json.get("player").getAsJsonObject();
            achievements = playerJson.get("achievements").getAsJsonObject();

            chain.append(new PlainText("\n玩家名: "));
            chain.append(new PlainText(playerJson.get("playername").getAsString()));

            chain.append(new PlainText("\n首次登录时间: "));
            if (PlayerDetermine.firstLogin(playerJson)){
                chain.append(new PlainText(simpleDateFormat.format(new Date(playerJson.get("firstLogin").getAsLong()))));
            } else {
                chain.append(new PlainText("null"));
            }

            chain.append(new PlainText("\n上次登录时间: "));
            if (PlayerDetermine.lastLogin(playerJson)){
                chain.append(new PlainText(simpleDateFormat.format(new Date(playerJson.get("lastLogin").getAsLong()))));
            } else {chain.append(new PlainText("null"));}

            chain.append(new PlainText("\n上次注销时间: "));
            if (PlayerDetermine.lastLogout(playerJson)){
                chain.append(new PlainText(simpleDateFormat.format(new Date(playerJson.get("lastLogout").getAsLong()))));
            } else {chain.append(new PlainText("null"));}

            chain.append(new PlainText("\n玩家使用的语言: "));
            if (PlayerDetermine.userLanguage(playerJson)){
                chain.append(new PlainText(playerJson.get("userLanguage").getAsString()));
            } else {chain.append("null");}

            chain.append(new PlainText("\n玩家最近游玩的模式: "));
            if (PlayerDetermine.recentGames(recentGames)){
                chain.append(new PlainText(recentGames.get("games").getAsJsonArray().get(0).getAsJsonObject().get("gameType").getAsString()));
            } else {chain.append(new PlainText("null"));}

            chain.append(new PlainText("\n成就点数: "));
            if (PlayerDetermine.achievementPoints(playerJson)){
                chain.append(new PlainText(String.valueOf(playerJson.get("achievementPoints").getAsInt())));
            } else {chain.append(new PlainText("null"));}

            chain.append(new PlainText(" | 人品值: "));
            if (PlayerDetermine.karma(playerJson)){
                chain.append(new PlainText(String.valueOf(playerJson.get("karma").getAsInt())));
            } else {chain.append(new PlainText("null"));}

            if (playerJson.get("stats").getAsJsonObject().has("Arcade")){
                arcade = playerJson.get("stats").getAsJsonObject().get("Arcade").getAsJsonObject();

                if (PlayerDetermine.arcade_arcade_winner(arcade, achievements)){
                    chain.append(new PlainText("\n街机金币: "));
                    chain.append(new PlainText(String.valueOf(arcade.get("coins").getAsInt())));
                    chain.append(new PlainText(" | 街机总胜利数: "));
                    chain.append(new PlainText(String.valueOf(achievements.get("arcade_arcade_winner").getAsInt())));

                } else {chain.append(new PlainText("\n街机游戏数据无法得到"));}
            }

            if(playerJson.get("stats").getAsJsonObject().has("Bedwars")){
                bwJson = playerJson.get("stats").getAsJsonObject().get("Bedwars").getAsJsonObject();
                if (PlayerDetermine.bedWars(achievements,bwJson)){
                    chain.append(new PlainText("\n起床战争等级: "));
                    chain.append(new PlainText(String.valueOf(achievements.get("bedwars_level").getAsInt())));
                    chain.append(new PlainText(" | 起床战争金币: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("coins").getAsInt())));
                    chain.append(new PlainText(" | 起床战争连胜: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("winstreak").getAsInt())));

                } else {chain.append(new PlainText("\n起床战争数据无法得到"));}
            }

            if (playerJson.get("stats").getAsJsonObject().has("SkyWars")){
                swJson = playerJson.get("stats").getAsJsonObject().get("SkyWars").getAsJsonObject();
                if (PlayerDetermine.skyWars(swJson)){
                    chain.append(new PlainText("\n空岛战争等级: "));
                    chain.append(new PlainText(swJson.get("levelFormatted").getAsString().replace("§7", "")));
                    chain.append(new PlainText(" | 空岛战争金币: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("coins").getAsInt())));
                    chain.append(new PlainText(" | 空岛战争连胜: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("win_streak").getAsInt())));
                } else {
                    chain.append(new PlainText("\n空岛战争数据无法得到"));
                }
            }

            if (playerJson.get("stats").getAsJsonObject().has("TNTGames")){
                TNTGames = playerJson.get("stats").getAsJsonObject().get("TNTGames").getAsJsonObject();
                if (PlayerDetermine.TNTGames(TNTGames)){
                    chain.append(new PlainText("\nTNT游戏金币: "));
                    chain.append(new PlainText(String.valueOf(TNTGames.get("coins").getAsInt())));
                    chain.append(new PlainText(" | TNT游戏胜场: "));
                    chain.append(new PlainText(String.valueOf(TNTGames.get("wins").getAsInt())));
                    chain.append(new PlainText(" | TNT游戏连胜: "));
                    chain.append(new PlainText(String.valueOf(TNTGames.get("winstreak").getAsInt())));
                } else {
                    chain.append(new PlainText("\nTNT游戏数据无法得到"));
                }
            }
        } else {
            chain.append(new PlainText("该玩家存在, 但是玩家数据不存在, 可能是因为玩家没有进入过hyp服务器, 也可能是数据丢失"));
        }



        group.sendMessage(chain.build());
    }
}
