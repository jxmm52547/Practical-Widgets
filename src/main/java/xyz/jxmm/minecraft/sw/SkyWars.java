package xyz.jxmm.minecraft.sw;

import static xyz.jxmm.minecraft.sw.SkyWarsDetermine.*;

import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.minecraft.player.PlayerDetermine;

import java.text.DecimalFormat;

public class SkyWars {
    public static void sw(JsonObject json, Long sender, Group group){
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);
        JsonObject swJson;
        JsonObject playerJson;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if (json.get("player").isJsonObject()){
            playerJson = json.get("player").getAsJsonObject();

            chain.append(new PlainText("\n玩家名:\n"));
            if (PlayerDetermine.rank(playerJson)){
                String rank = playerJson.get("newPackageRank").getAsString();
                boolean rankPlus = playerJson.has("monthlyPackageRank");
                switch (rank){
                    case "MVP_PLUS":
                        if (rankPlus) chain.append(new PlainText("【MVP++】"));
                        else chain.append(new PlainText("【MVP+】"));
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
            chain.append(new PlainText(playerJson.get("displayname").getAsString()));
            chain.append(new PlainText(" | 空岛战争 数据如下:"));

            if (playerJson.get("stats").getAsJsonObject().has("SkyWars")){
                swJson = playerJson.get("stats").getAsJsonObject().get("SkyWars").getAsJsonObject();

                if (games_played(swJson)){
                    chain.append(new PlainText("\n总游戏场次: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("games_played_skywars").getAsInt())));
                    chain.append(new PlainText(" | 当前等级: "));
                    chain.append(new PlainText(swJson.get("levelFormatted").getAsString().replace(swJson.get("levelFormatted").getAsString().substring(0,1),"").replace("⋆","✨")));
                }

                if (skywars_experience(swJson)){
                    chain.append(new PlainText("\n当前经验值: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("skywars_experience").getAsDouble())));
                    chain.append(new PlainText(" | 金币: "));
                    chain.append(new PlainText(swJson.get("coins").getAsInt() + "💰"));
                }

                if (win_streak(swJson)){
                    chain.append(new PlainText("\n胜场数: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("win_streak").getAsInt())));
                    chain.append(new PlainText(" | 败场数: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("losses").getAsInt())));
                    chain.append(new PlainText(" | 胜率: "));
                    chain.append(new PlainText(String.valueOf(decimalFormat.format(
                            (float) swJson.get("win_streak").getAsInt() /
                                    (float) swJson.get("losses").getAsInt()))));
                }

                if (kills(swJson)){
                    chain.append(new PlainText("\n总击杀数: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("kills").getAsInt())));
                    chain.append(new PlainText(" | 助攻: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("assists").getAsInt())));
                    chain.append(new PlainText(" | 死亡数: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("deaths").getAsInt())));
                    chain.append(new PlainText(" | KD: "));
                    chain.append(new PlainText(String.valueOf(decimalFormat.format(
                            (float) swJson.get("kills").getAsInt() /
                                    (float) swJson.get("deaths").getAsInt()))));
                }

                if (melee_kills(swJson)){
                    chain.append(new PlainText("\n近战杀敌数: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("melee_kills").getAsInt())));
                    chain.append(new PlainText(" | 弓箭击杀: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("bow_kills").getAsInt())));
                    chain.append(new PlainText(" | 推入虚空: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("void_kills").getAsInt())));
                }

                if (arrows_shot(swJson)){
                    chain.append(new PlainText("\n箭矢射击数: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("arrows_shot").getAsInt())));
                    chain.append(new PlainText(" | 箭矢命中数: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("arrows_hit").getAsInt())));
                    chain.append(new PlainText(" | 箭矢命中率: "));
                    chain.append(new PlainText(decimalFormat.format(
                            (float) swJson.get("arrows_hit").getAsInt() /
                                    (float) swJson.get("arrows_shot").getAsInt())));
                }

                if (chests_opened(swJson)){
                    chain.append(new PlainText("\n打开箱子个数: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("chests_opened").getAsInt())));
                }

                if (enderpearls_thrown(swJson)){
                    chain.append(new PlainText(" | 扔出末影珍珠数: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("enderpearls_thrown").getAsInt())));
                }

                if (souls_gathered(swJson)){
                    chain.append(new PlainText(" | 获得的灵魂: "));
                    chain.append(new PlainText(String.valueOf(swJson.get("souls_gathered").getAsInt())));
                }
            } else {
                chain.append(new PlainText("无法得到 空岛战争 数据"));
            }

        }


        group.sendMessage(chain.build());
    }
}
