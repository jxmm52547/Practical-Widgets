package xyz.jxmm.minecraft.bw;

import static xyz.jxmm.minecraft.bw.BedWarsDetermine.*;

import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.minecraft.player.PlayerDetermine;

import java.text.DecimalFormat;

public class BedWars {
    //起床战争解析
    public static void bw(JsonObject json, Long sender, Group group){
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);
        JsonObject bwJson;
        JsonObject playerJson;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if (json.get("player").isJsonObject()){
            playerJson = json.get("player").getAsJsonObject();

            chain.append(new PlainText("\n玩家名: "));
            if (PlayerDetermine.rank(playerJson)){
                String rank = playerJson.get("newPackageRank").getAsString();
                boolean rankPlus = playerJson.has("monthlyPackageRank");
                switch (rank){
                    case "MVP_PLUS":
                        if (rankPlus && playerJson.get("monthlyPackageRank").getAsString().equals("SUPERSTAR")){
                            chain.append(new PlainText("【MVP++】"));
                        }
                        else{
                            chain.append(new PlainText("【MVP+】"));
                        }
                        break;
                    case "MVP":
                        chain.append(new PlainText("【MVP】"));
                        break;
                    case "VIP_PLUS":
                        chain.append(new PlainText("【VIP+】"));
                        break;
                    case "VIP":
                        chain.append(new PlainText("【VIP】"));
                        break;
                }
            }
            chain.append(new PlainText(json.get("player").getAsJsonObject().get("displayname").getAsString()));
            chain.append(new PlainText(" | 起床战争 数据如下:"));

            if (json.get("player").getAsJsonObject().get("stats").getAsJsonObject().has("Bedwars")){
                bwJson = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Bedwars").getAsJsonObject();

                if (games_played_bedwars(bwJson)){
                    chain.append(new PlainText("\n游戏场次: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("games_played_bedwars").getAsInt())));
                    chain.append(new PlainText(" | 当前等级: "));
                    chain.append(new PlainText(String.valueOf(json.get("player").getAsJsonObject().get("achievements").getAsJsonObject().get("bedwars_level").getAsInt())));
                    /*部分玩家的连胜获取不成功
                    chain.append(new PlainText(" | 当前连胜: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("winstreak").getAsInt())));*/
                }

                if (kills_bedwars(bwJson)){
                    chain.append(new PlainText("\n击杀数: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("kills_bedwars").getAsInt())));
                    chain.append(new PlainText(" | 死亡数: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("deaths_bedwars").getAsInt())));
                    chain.append(new PlainText(" | KD: "));
                    chain.append(new PlainText(decimalFormat.format(
                            (float)bwJson.get("kills_bedwars").getAsInt() /
                                    (float)bwJson.get("deaths_bedwars").getAsInt())));
                }

                if (final_kills_bedwars(bwJson)){
                    chain.append(new PlainText("\n最终击杀: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("final_kills_bedwars").getAsInt())));
                    chain.append(new PlainText(" | 最终死亡: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("final_deaths_bedwars").getAsInt())));
                    chain.append(new PlainText(" | FKD："));
                    chain.append(new PlainText(decimalFormat.format(
                            (float)bwJson.get("final_kills_bedwars").getAsInt() /
                                    (float)bwJson.get("final_deaths_bedwars").getAsInt())));
                }

                if (kills_bedwars(bwJson) && final_kills_bedwars(bwJson)){
                    chain.append(new PlainText("\n总击杀: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("kills_bedwars").getAsInt() + bwJson.get("final_kills_bedwars").getAsInt())));
                    chain.append(new PlainText(" | 总死亡: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("deaths_bedwars").getAsInt() + bwJson.get("final_deaths_bedwars").getAsInt())));
                    chain.append(new PlainText(" | 总KD: "));
                    chain.append(new PlainText(decimalFormat.format(
                            (float) (bwJson.get("kills_bedwars").getAsInt() + bwJson.get("final_kills_bedwars").getAsInt()) /
                                    (float) (bwJson.get("deaths_bedwars").getAsInt() + bwJson.get("final_deaths_bedwars").getAsInt())
                    )));
                }

                if (beds_broken_bedwars(bwJson)){
                    chain.append(new PlainText("\n摧毁床: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("beds_broken_bedwars").getAsInt())));
                    chain.append(new PlainText(" | 被摧毁床: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("beds_lost_bedwars").getAsInt())));
                    chain.append(new PlainText(" | B/L: "));
                    chain.append(new PlainText(decimalFormat.format(
                            (float)bwJson.get("beds_broken_bedwars").getAsInt() /
                                    (float)bwJson.get("beds_lost_bedwars").getAsInt())));
                }

                if (wins_bedwars(bwJson)){
                    chain.append(new PlainText("\n胜利数: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("wins_bedwars").getAsInt())));
                    chain.append(new PlainText(" | 失败数: "));
                    chain.append(new PlainText(String.valueOf(bwJson.get("losses_bedwars").getAsInt())));
                    chain.append(new PlainText(" | W/L: "));
                    chain.append(new PlainText(decimalFormat.format(
                            (float)bwJson.get("wins_bedwars").getAsInt() /
                                    (float)bwJson.get("losses_bedwars").getAsInt())));
                }
            } else {
                chain.append(new PlainText("无法得到 起床战争 数据"));
            }
        } else if (json.get("player").isJsonNull()){
            chain.append(new PlainText("<playerID> 不存在"));
        }

        group.sendMessage(chain.build());

    }
}
