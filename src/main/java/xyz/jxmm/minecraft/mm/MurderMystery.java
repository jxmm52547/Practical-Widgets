package xyz.jxmm.minecraft.mm;

import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.minecraft.Nick;

import java.text.DecimalFormat;

import static xyz.jxmm.minecraft.mm.MurderMysteryDetermine.*;

public class MurderMystery {

    public static void mm(JsonObject json, Long sender, Group group){
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);
        JsonObject playerJson;
        JsonObject mmJson;
        JsonObject quests;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if (json.get("player").isJsonObject()){
            playerJson = json.get("player").getAsJsonObject();


            chain.append(new PlainText(Nick.nick(playerJson)));
            chain.append(new PlainText(json.get("player").getAsJsonObject().get("displayname").getAsString()));
            chain.append(new PlainText(" | 密室杀手 数据如下:"));

            if (playerJson.get("stats").getAsJsonObject().has("MurderMystery")){
                mmJson = playerJson.get("stats").getAsJsonObject().get("MurderMystery").getAsJsonObject();

                //硬币
                chain.append(new PlainText("\n硬币: "));
                if (coins(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("coins").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总胜场
                chain.append(new PlainText(" | 总胜场: "));
                if (wins(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("wins").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总场次
                chain.append(new PlainText("\n总场次: "));
                if (games(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("games").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总胜率
                chain.append(new PlainText(" | 总胜率: "));
                if (wins(mmJson) && games(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float)mmJson.get("wins").getAsInt() /
                                    (float) mmJson.get("games").getAsInt() *
                                    100
                    )));
                    chain.append(new PlainText("%"));
                } else chain.append(new PlainText("null"));

                //总击杀
                chain.append(new PlainText("\n总击杀数: "));
                if (kills(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("kills").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总死亡
                chain.append(new PlainText("| 总死亡数: "));
                if (deaths(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("deaths").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总KD
                chain.append(new PlainText(" | 总KD: "));
                if (games(mmJson) && deaths(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float)mmJson.get("kills").getAsInt() /
                                    (float)mmJson.get("deaths").getAsInt()
                    )));
                } else chain.append(new PlainText("null"));

                //总弓箭击杀
                chain.append(new PlainText("\n总弓箭击杀: "));
                if (bow_kills(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("bow_kills").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总匕首击杀
                chain.append(new PlainText(" | 总匕首击杀: "));
                if (knife_kills(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("knife_kills").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总飞刀击杀
                chain.append(new PlainText("\n总飞刀击杀: "));
                if (thrown_knife_kills(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("thrown_knife_kills").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总陷阱击杀
                chain.append(new PlainText(" | 总陷阱击杀: "));
                if (trap_kills(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("trap_kills").getAsInt())));
                } else chain.append(new PlainText("null"));

                //侦探总胜场
                chain.append(new PlainText("\n总侦探胜场: "));
                if (detective_wins(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("detective_wins").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总杀手胜场
                chain.append(new PlainText(" | 总杀手胜场: "));
                if (murderer_wins(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("murderer_wins").getAsInt())));
                } else chain.append(new PlainText("null"));

                //周胜场
                chain.append(new PlainText("\n周胜场: "));
                if (quests(playerJson)){
                    quests = playerJson.get("quests").getAsJsonObject();
                    if (mm_weekly_wins(quests)){
                        JsonObject mm_weekly_wins = quests.get("mm_weekly_wins").getAsJsonObject();
                        if (active(mm_weekly_wins)){
                            JsonObject active = mm_weekly_wins.get("active").getAsJsonObject();
                            if (objectives(active)){
                                JsonObject objectives = active.get("objectives").getAsJsonObject();
                                if (weekly_win(objectives)){
                                    chain.append(new PlainText(String.valueOf(objectives.get("mm_weekly_win").getAsInt())));
                                } else chain.append(new PlainText("null"));
                            } else chain.append(new PlainText("null"));
                        } else chain.append(new PlainText("null"));
                    } else chain.append(new PlainText("null"));
                } else chain.append(new PlainText("null"));

                //周击杀
                chain.append(new PlainText(" | 周击杀: "));
                if (quests(playerJson)){
                    quests = playerJson.get("quests").getAsJsonObject();
                    if (mm_weekly_murderer_kills(quests)){
                        JsonObject mm_weekly_murderer_kills = quests.get("mm_weekly_murderer_kills").getAsJsonObject();
                        if (active(mm_weekly_murderer_kills)){
                            JsonObject active = mm_weekly_murderer_kills.get("active").getAsJsonObject();
                            if (objectives(active)){
                                JsonObject objectives = active.get("objectives").getAsJsonObject();
                                if (weekly_kill(objectives)){
                                    chain.append(new PlainText(String.valueOf(objectives.get("mm_weekly_kills_as_murderer").getAsInt())));
                                } else chain.append(new PlainText("null"));
                            } else chain.append(new PlainText("null"));
                        } else chain.append(new PlainText("null"));
                    } else chain.append(new PlainText("null"));
                } else chain.append(new PlainText("null"));

                //经典模式
                chain.append(new PlainText("\n经典模式数据如下: "));

                //场次
                chain.append(new PlainText("\n场次: "));
                if (games_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("games_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //胜场
                chain.append(new PlainText(" | 胜场"));
                if (wins_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("wins_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //胜率
                chain.append(new PlainText(" | 胜率: "));
                if (games_MURDER_CLASSIC(mmJson) && wins_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float)mmJson.get("wins_MURDER_CLASSIC").getAsInt() /
                                    (float) mmJson.get("games_MURDER_CLASSIC").getAsInt() *
                                    100
                    )));
                    chain.append(new PlainText("%"));
                } else chain.append(new PlainText("null"));

                //弓箭击杀
                chain.append(new PlainText("\n弓箭击杀: "));
                if (bow_kills_MURDER_CLASSIC(mmJson)) {
                    chain.append(new PlainText(String.valueOf(mmJson.get("bow_kills_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //匕首击杀
                chain.append(new PlainText(" | 匕首击杀: "));
                if (knife_kills_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("knife_kills_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //飞刀击杀
                chain.append(new PlainText("\n飞刀击杀: "));
                if (thrown_knife_kills_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("thrown_knife_kills_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //陷阱击杀
                chain.append(new PlainText(" | 陷阱击杀: "));
                if (trap_kills_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("trap_kills_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //侦探胜场
                chain.append(new PlainText("\n侦探胜场: "));
                if (detective_wins_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("detective_wins_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //杀手胜场
                chain.append(new PlainText(" | 杀手胜场: "));
                if (murderer_wins_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("murderer_wins_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //经典模式总击杀
                chain.append(new PlainText("\n经典模式总击杀: "));
                if (kills_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("kills_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //经典模式总死亡
                chain.append(new PlainText(" | 死亡: "));
                if (deaths_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("deaths_MURDER_CLASSIC").getAsInt())));
                } else chain.append(new PlainText("null"));

                //经典模式KD
                chain.append(new PlainText(" | KD: "));
                if (kills_MURDER_CLASSIC(mmJson) && deaths_MURDER_CLASSIC(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float) mmJson.get("kills_MURDER_CLASSIC").getAsInt() /
                                    (float) mmJson.get("deaths_MURDER_CLASSIC").getAsInt()
                    )));
                }

                //双倍模式
                chain.append(new PlainText("\n双倍模式数据如下: "));

                //场次
                chain.append(new PlainText("\n场次: "));
                if (games_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("games_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //胜场
                chain.append(new PlainText(" | 胜场: "));
                if (wins_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("wins_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //胜率
                chain.append(new PlainText(" | 胜率: "));
                if (games_MURDER_DOUBLE_UP(mmJson) && wins_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float) mmJson.get("wins_MURDER_DOUBLE_UP").getAsInt() /
                                    (float) mmJson.get("games_MURDER_DOUBLE_UP").getAsInt() *
                                    100
                    )));
                    chain.append(new PlainText("%"));
                } else chain.append(new PlainText("null"));

                //弓箭击杀
                chain.append(new PlainText("\n弓箭击杀: "));
                if (bow_kills_MURDER_DOUBLE_UP(mmJson)) {
                    chain.append(new PlainText(String.valueOf(mmJson.get("bow_kills_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //匕首击杀
                chain.append(new PlainText(" | 匕首击杀: "));
                if (knife_kills_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("knife_kills_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //飞刀击杀
                chain.append(new PlainText("\n飞刀击杀: "));
                if (thrown_knife_kills_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("thrown_knife_kills_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //陷阱击杀
                chain.append(new PlainText(" | 陷阱击杀: "));
                if (trap_kills_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("trap_kills_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //侦探胜场
                chain.append(new PlainText("\n侦探胜场: "));
                if (detective_wins_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("detective_wins_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //杀手胜场
                chain.append(new PlainText(" | 杀手胜场: "));
                if (murderer_wins_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("murderer_wins_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //双倍模式总击杀
                chain.append(new PlainText("\n今典模式总击杀: "));
                if (kills_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("kills_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //双倍模式总死亡
                chain.append(new PlainText(" | 死亡: "));
                if (deaths_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("deaths_MURDER_DOUBLE_UP").getAsInt())));
                } else chain.append(new PlainText("null"));

                //双倍模式KD
                chain.append(new PlainText(" | KD: "));
                if (kills_MURDER_DOUBLE_UP(mmJson) && deaths_MURDER_DOUBLE_UP(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float) mmJson.get("kills_MURDER_DOUBLE_UP").getAsInt() /
                                    (float) mmJson.get("deaths_MURDER_DOUBLE_UP").getAsInt()
                    )));
                } else chain.append(new PlainText("null"));

                //刺客模式
                chain.append(new PlainText("\n刺客模式数据如下: "));

                //场次
                chain.append(new PlainText("\n场次: "));
                if (games_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("games_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //胜场
                chain.append(new PlainText(" | 胜场: "));
                if (wins_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("wins_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //胜率
                chain.append(new PlainText(" | 胜率: "));
                if (games_MURDER_ASSASSINS(mmJson) && wins_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float) mmJson.get("wins_MURDER_ASSASSINS").getAsInt() /
                                    (float) mmJson.get("games_MURDER_ASSASSINS").getAsInt() *
                                    100
                    )));
                    chain.append(new PlainText("%"));
                } else chain.append(new PlainText("null"));

                //弓箭击杀
                chain.append(new PlainText("\n弓箭击杀: "));
                if (bow_kills_MURDER_ASSASSINS(mmJson)) {
                    chain.append(new PlainText(String.valueOf(mmJson.get("bow_kills_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //匕首击杀
                chain.append(new PlainText(" | 匕首击杀: "));
                if (knife_kills_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("knife_kills_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //飞刀击杀
                chain.append(new PlainText("\n飞刀击杀: "));
                if (thrown_knife_kills_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("thrown_knife_kills_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //陷阱击杀
                chain.append(new PlainText(" | 陷阱击杀: "));
                if (trap_kills_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("trap_kills_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //侦探胜场
                chain.append(new PlainText("\n侦探胜场: "));
                if (detective_wins_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("detective_wins_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //杀手胜场
                chain.append(new PlainText(" | 杀手胜场: "));
                if (murderer_wins_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("murderer_wins_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //双倍模式总击杀
                chain.append(new PlainText("\n今典模式总击杀: "));
                if (kills_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("kills_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //双倍模式总死亡
                chain.append(new PlainText(" | 死亡: "));
                if (deaths_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("deaths_MURDER_ASSASSINS").getAsInt())));
                } else chain.append(new PlainText("null"));

                //双倍模式KD
                chain.append(new PlainText(" | KD: "));
                if (kills_MURDER_ASSASSINS(mmJson) && deaths_MURDER_ASSASSINS(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float) mmJson.get("kills_MURDER_ASSASSINS").getAsInt() /
                                    (float) mmJson.get("deaths_MURDER_ASSASSINS").getAsInt()
                    )));
                } else chain.append(new PlainText("null"));

                //感染模式
                chain.append(new PlainText("\n感染模式数据如下: "));

                //总场次
                chain.append(new PlainText("\n场次: "));
                if (games_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("games_MURDER_INFECTION").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总胜场
                chain.append(new PlainText(" | 胜场: "));
                if (wins_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("wins_MURDER_INFECTION").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总胜率
                chain.append(new PlainText(" | 胜率: "));
                if (games_MURDER_INFECTION(mmJson) && wins_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float) mmJson.get("wins_MURDER_INFECTION").getAsInt() /
                                    (float) mmJson.get("games_MURDER_INFECTION").getAsInt() *
                                    100
                    )));
                    chain.append(new PlainText("%"));
                } else chain.append(new PlainText("null"));

                //总击杀
                chain.append(new PlainText("\n击杀: "));
                if (kills_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("wins_MURDER_INFECTION").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总死亡
                chain.append(new PlainText(" | 死亡: "));
                if (deaths_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("deaths_MURDER_INFECTION").getAsInt())));
                } else chain.append(new PlainText("null"));

                //总KD
                chain.append(new PlainText(" | KD: "));
                if (kills_MURDER_INFECTION(mmJson) && deaths_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float) mmJson.get("kills_MURDER_INFECTION").getAsInt() /
                                    (float) mmJson.get("deaths_MURDER_INFECTION").getAsInt()
                    )));
                } else chain.append(new PlainText("null"));

                //幸存者击杀
                chain.append(new PlainText("\n幸存者击杀: "));
                if (kills_as_survivor_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("kills_as_survivor_MURDER_INFECTION").getAsInt())));
                } else chain.append(new PlainText("null"));

                //弓箭击杀
                chain.append(new PlainText(" | 弓箭击杀: "));
                if (bow_kills_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("bow_kills_MURDER_INFECTION").getAsInt())));
                } else chain.append(new PlainText("null"));

                //弓箭击杀率
                chain.append(new PlainText(" | 弓箭击杀率: "));
                if(kills_as_survivor_MURDER_INFECTION(mmJson) && bow_kills_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(decimalFormat.format(
                            (float) mmJson.get("bow_kills_MURDER_INFECTION").getAsInt() /
                                    (float) mmJson.get("kills_as_survivor_MURDER_INFECTION").getAsInt() *
                                    100
                    )));
                    chain.append(new PlainText("%"));
                } else chain.append(new PlainText("null"));

                //感染者击杀
                chain.append(new PlainText("\n感染者击杀: "));
                if (kills_as_infected_MURDER_INFECTION(mmJson)){
                    chain.append(new PlainText(String.valueOf(mmJson.get("kills_as_infected_MURDER_INFECTION").getAsInt())));
                } else chain.append(new PlainText("null"));



            } else {
                chain.append(new PlainText("无法得到 密室杀手 数据"));

            }

            ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
            builder.add(group.getBot().getId(),group.getBot().getNick(),chain.build());
            group.sendMessage(builder.build());
        }
    }
}
