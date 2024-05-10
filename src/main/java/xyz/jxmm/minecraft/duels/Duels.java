package xyz.jxmm.minecraft.duels;

import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.minecraft.Nick;

import static xyz.jxmm.minecraft.duels.DuelsDetermine.determine;

import java.text.DecimalFormat;

public class Duels {
    public static void duels(JsonObject json, Long sender, Group group){
        MessageChainBuilder main = new MessageChainBuilder().append(MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]"));
        MessageChainBuilder solo = new MessageChainBuilder();
        MessageChainBuilder team = new MessageChainBuilder();
        MessageChainBuilder other = new MessageChainBuilder();


        JsonObject playerJson;
        JsonObject duelsJson;
        JsonObject achievements;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if (json.get("player").isJsonObject()){
            playerJson = json.get("player").getAsJsonObject();
            achievements = playerJson.get("achievements").getAsJsonObject();

            main.append(new PlainText(Nick.nick(playerJson)));
            main.append(new PlainText(json.get("player").getAsJsonObject().get("displayname").getAsString()));
            main.append(new PlainText(" | 单挑模式 数据如下:"));

            if (playerJson.get("stats").getAsJsonObject().has("Duels")){
                duelsJson = playerJson.get("stats").getAsJsonObject().get("Duels").getAsJsonObject();

                //硬币
                main.append(new PlainText("\n硬币: "));
                if (determine(duelsJson,"coins")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("coins").getAsInt())));
                } else main.append(new PlainText("null"));

                //游玩回合数
                main.append(new PlainText(" | 游玩回合数: "));
                if (determine(duelsJson,"rounds_played")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("rounds_played").getAsInt())));
                } else main.append(new PlainText("null"));

                //总胜场
                main.append(new PlainText("\n总胜场: "));
                if (determine(duelsJson,"wins")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("wins").getAsInt())));
                } else main.append(new PlainText("null"));

                //总败场
                main.append(new PlainText(" | 总败场"));
                if (determine(duelsJson,"losses")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("losses").getAsInt())));
                } else main.append(new PlainText("null"));

                //总胜率
                main.append(new PlainText(" | 总胜率"));
                if (determine(duelsJson,"wins") && determine(duelsJson,"rounds_played")){
                    main.append(new PlainText(decimalFormat.format(
                            (float)duelsJson.get("wins").getAsInt() /
                                    (float) duelsJson.get("rounds_played").getAsInt() * 100
                    )));
                    main.append(new PlainText("%"));
                } else main.append(new PlainText("null"));

                //总击杀
                main.append(new PlainText("\n总击杀: "));
                if (determine(duelsJson,"kills")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("kills").getAsInt())));
                } else main.append(new PlainText("null"));

                //总死亡
                main.append(new PlainText(" | 总死亡: "));
                if (determine(duelsJson, "deaths")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("deaths").getAsInt())));
                } else main.append(new PlainText("null"));

                //总KD
                main.append(new PlainText(" | 总KD: "));
                if (determine(duelsJson,"kills") && determine(duelsJson,"deaths")){
                    main.append(new PlainText(decimalFormat.format(
                            (float)duelsJson.get("kills").getAsInt() /
                                    (float) duelsJson.get("deaths").getAsInt()
                    )));
                } else main.append(new PlainText("null"));

                //战桥击杀数
                main.append(new PlainText("\n战桥击杀数: "));
                if (determine(duelsJson, "bridge_kills")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("bridge_kills").getAsInt())));
                } else main.append(new PlainText("null"));

                //战桥死亡数
                main.append(new PlainText(" | 战桥死亡数: "));
                if (determine(duelsJson, "bridge_deaths")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("bridge_deaths").getAsInt())));
                } else main.append(new PlainText("null"));

                //Goals Scored
                main.append(new PlainText("\nGoals Scored: "));
                if (determine(achievements,"duels_goals")){
                    main.append(new PlainText(String.valueOf(achievements.get("duels_goals").getAsInt())));
                } else main.append(new PlainText("null"));

                //flags captured


                //近战攻击
                main.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson, "melee_swings")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("melee_swings").getAsInt())));
                } else main.append(new PlainText("null"));

                //近战命中
                main.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson,"melee_hits")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("melee_hits").getAsInt())));
                } else main.append(new PlainText("null"));

                //近战命中率
                main.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "melee_swings") && determine(duelsJson,"melee_hits")){
                    main.append(new PlainText(decimalFormat.format(
                            (float)duelsJson.get("melee_hits").getAsInt() /
                                    (float) duelsJson.get("melee_swings").getAsInt() * 100
                    )));
                    main.append(new PlainText("%"));
                } else main.append(new PlainText("null"));

                //弓箭射击
                main.append(new PlainText("\n弓箭射击: "));
                if (determine(duelsJson,"bow_shots")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("bow_shots").getAsInt())));
                } else main.append(new PlainText("null"));

                //弓箭命中
                main.append(new PlainText(" | 弓箭命中: "));
                if (determine(duelsJson,"bow_hits")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("bow_hits").getAsInt())));
                } else main.append(new PlainText("null"));

                //命中率
                main.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"bow_shots") && determine(duelsJson,"bow_hits")){
                    main.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("bow_hits").getAsInt() /
                                    (float) duelsJson.get("bow_shots").getAsInt() * 100
                    )));
                    main.append(new PlainText("%"));
                } else main.append(new PlainText("null"));

                //方块放置数
                main.append(new PlainText("\n方块放置数: "));
                if (determine(duelsJson,"blocks_placed")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("blocks_placed").getAsInt())));
                } else main.append(new PlainText("null"));

                //soup eaten
                main.append(new PlainText(" | soup eaten: "));
                if (determine(duelsJson,"soup_eaten")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("soup_eaten").getAsInt())));
                } else main.append(new PlainText("null"));

                //使用治疗药水
                main.append(new PlainText("\n治疗药水使用: "));
                if (determine(duelsJson,"heal_pots_used")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("heal_pots_used").getAsInt())));
                } else main.append(new PlainText("null"));

                //金苹果食用
                main.append(new PlainText(" | 金苹果食用: "));
                if (determine(duelsJson,"golden_apples_eaten")){
                    main.append(new PlainText(String.valueOf(duelsJson.get("golden_apples_eaten").getAsInt())));
                } else main.append(new PlainText("null"));


                //单挑详细信息
                solo.append(new PlainText("单挑详细信息如下: "));
                //弓箭决斗
                solo.append(new PlainText("\n弓箭决斗: "));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"bow_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bow_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"bow_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bow_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"bow_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bow_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"bow_duel_wins") && determine(duelsJson,"bow_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("bow_duel_wins").getAsInt() /
                                    (float) duelsJson.get("bow_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"bow_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bow_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "bow_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bow_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"bow_duel_kills") && determine(duelsJson,"bow_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("bow_duel_kills").getAsInt() /
                                    (float) duelsJson.get("bow_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //弓箭命中
                solo.append(new PlainText("\n弓箭命中: "));
                if (determine(duelsJson, "bow_duel_bow_hits")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bow_duel_bow_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //弓箭射出
                solo.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"bow_duel_bow_shots")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bow_duel_bow_shots").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "bow_duel_bow_hits") && determine(duelsJson,"bow_duel_bow_shots")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("bow_duel_bow_hits").getAsInt() /
                                    (float) duelsJson.get("bow_duel_bow_shots").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //经典决斗
                solo.append(new PlainText("\n经典决斗: "));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"classic_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("classic_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"classic_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("classic_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"classic_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("classic_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"classic_duel_wins") && determine(duelsJson,"classic_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("classic_duel_wins").getAsInt() /
                                    (float) duelsJson.get("classic_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"classic_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("classic_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "classic_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("classic_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"classic_duel_kills") && determine(duelsJson,"classic_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("classic_duel_kills").getAsInt() /
                                    (float) duelsJson.get("classic_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"classic_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("classic_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "classic_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("classic_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"classic_duel_melee_swings") && determine(duelsJson, "classic_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("classic_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("classic_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //弓箭命中
                solo.append(new PlainText("\n弓箭命中: "));
                if (determine(duelsJson, "classic_duel_bow_hits")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("classic_duel_bow_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //弓箭射出
                solo.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"classic_duel_bow_shots")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("classic_duel_bow_shots").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "classic_duel_bow_hits") && determine(duelsJson,"classic_duel_bow_shots")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("classic_duel_bow_hits").getAsInt() /
                                    (float) duelsJson.get("classic_duel_bow_shots").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //高手
                solo.append(new PlainText("\n高手决斗: "));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"op_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("op_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"op_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("op_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"op_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("op_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"op_duel_wins") && determine(duelsJson,"op_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("op_duel_wins").getAsInt() /
                                    (float) duelsJson.get("op_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"op_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("op_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "op_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("op_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"op_duel_kills") && determine(duelsJson,"op_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("op_duel_kills").getAsInt() /
                                    (float) duelsJson.get("op_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"op_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("op_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "op_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("op_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"op_duel_melee_swings") && determine(duelsJson, "op_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("op_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("op_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //UHC
                solo.append(new PlainText("\nUHC对决: "));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"uhc_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"uhc_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"uhc_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"uhc_duel_wins") && determine(duelsJson,"uhc_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("uhc_duel_wins").getAsInt() /
                                    (float) duelsJson.get("uhc_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"uhc_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "uhc_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"uhc_duel_kills") && determine(duelsJson,"uhc_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("uhc_duel_kills").getAsInt() /
                                    (float) duelsJson.get("uhc_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"uhc_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "uhc_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"uhc_duel_melee_swings") && determine(duelsJson, "uhc_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("uhc_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("uhc_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //弓箭命中
                solo.append(new PlainText("\n弓箭命中: "));
                if (determine(duelsJson, "uhc_duel_bow_hits")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_bow_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //弓箭射出
                solo.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"uhc_duel_bow_shots")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_bow_shots").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "uhc_duel_bow_hits") && determine(duelsJson,"uhc_duel_bow_shots")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("uhc_duel_bow_hits").getAsInt() /
                                    (float) duelsJson.get("uhc_duel_bow_shots").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //方块放置
                solo.append(new PlainText("\n方块放置: "));
                if (determine(duelsJson,"uhc_duel_blocks_placed")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_blocks_placed").getAsInt())));
                } else solo.append(new PlainText("null"));

                //金苹果食用
                solo.append(new PlainText(" | 金苹果食用: "));
                if (determine(duelsJson,"uhc_duel_golden_apples_eaten")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("uhc_duel_golden_apples_eaten").getAsInt())));
                } else solo.append(new PlainText("null"));


                //药水对决
                solo.append(new PlainText("\n药水对决: "));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"potion_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("potion_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"potion_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("potion_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"potion_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("potion_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"potion_duel_wins") && determine(duelsJson,"potion_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("potion_duel_wins").getAsInt() /
                                    (float) duelsJson.get("potion_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"potion_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("potion_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "potion_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("potion_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"potion_duel_kills") && determine(duelsJson,"potion_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("potion_duel_kills").getAsInt() /
                                    (float) duelsJson.get("potion_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"potion_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("potion_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "potion_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("potion_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"potion_duel_melee_swings") && determine(duelsJson, "potion_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("potion_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("potion_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //治疗药水使用
                solo.append(new PlainText("\n治疗药水使用: "));
                if (determine(duelsJson,"potion_duel_heal_pots_used")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("potion_duel_heal_pots_used").getAsInt())));
                } else solo.append(new PlainText("null"));

                //超级战墙
                solo.append(new PlainText("\n超级战墙:"));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"mw_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"mw_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"mw_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"mw_duel_wins") && determine(duelsJson,"mw_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("mw_duel_wins").getAsInt() /
                                    (float) duelsJson.get("mw_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"mw_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "mw_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"mw_duel_kills") && determine(duelsJson,"mw_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("mw_duel_kills").getAsInt() /
                                    (float) duelsJson.get("mw_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"mw_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "mw_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"mw_duel_melee_swings") && determine(duelsJson, "mw_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("mw_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("mw_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //弓箭命中
                solo.append(new PlainText("\n弓箭命中: "));
                if (determine(duelsJson, "mw_duel_bow_hits")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_bow_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //弓箭射出
                solo.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"mw_duel_bow_shots")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_bow_shots").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "mw_duel_bow_hits") && determine(duelsJson,"mw_duel_bow_shots")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("mw_duel_bow_hits").getAsInt() /
                                    (float) duelsJson.get("mw_duel_bow_shots").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //方块放置数
                solo.append(new PlainText("\n方块放置数: "));
                if (determine(duelsJson,"mw_duel_blocks_placed")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("mw_duel_blocks_placed").getAsInt())));
                } else solo.append(new PlainText("null"));


                //闪电饥饿游戏
                solo.append(new PlainText("\n闪电饥饿游戏:"));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"blitz_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"blitz_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"blitz_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"blitz_duel_wins") && determine(duelsJson,"blitz_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("blitz_duel_wins").getAsInt() /
                                    (float) duelsJson.get("blitz_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"blitz_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "blitz_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"blitz_duel_kills") && determine(duelsJson,"blitz_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("blitz_duel_kills").getAsInt() /
                                    (float) duelsJson.get("blitz_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"blitz_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "blitz_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"blitz_duel_melee_swings") && determine(duelsJson, "blitz_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("blitz_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("blitz_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //弓箭命中
                solo.append(new PlainText("\n弓箭命中: "));
                if (determine(duelsJson, "blitz_duel_bow_hits")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_bow_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //弓箭射出
                solo.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"blitz_duel_bow_shots")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_bow_shots").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "blitz_duel_bow_hits") && determine(duelsJson,"blitz_duel_bow_shots")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("blitz_duel_bow_hits").getAsInt() /
                                    (float) duelsJson.get("blitz_duel_bow_shots").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //方块放置数
                solo.append(new PlainText("\n方块放置数: "));
                if (determine(duelsJson,"blitz_duel_blocks_placed")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("blitz_duel_blocks_placed").getAsInt())));
                } else solo.append(new PlainText("null"));


                //空岛战争
                solo.append(new PlainText("\n空岛战争:"));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"sw_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"sw_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"sw_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"sw_duel_wins") && determine(duelsJson,"sw_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("sw_duel_wins").getAsInt() /
                                    (float) duelsJson.get("sw_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"sw_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "sw_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"sw_duel_kills") && determine(duelsJson,"sw_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("sw_duel_kills").getAsInt() /
                                    (float) duelsJson.get("sw_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"sw_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "sw_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"sw_duel_melee_swings") && determine(duelsJson, "sw_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("sw_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("sw_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //弓箭命中
                solo.append(new PlainText("\n弓箭命中: "));
                if (determine(duelsJson, "sw_duel_bow_hits")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_bow_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //弓箭射出
                solo.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"sw_duel_bow_shots")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_bow_shots").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "sw_duel_bow_hits") && determine(duelsJson,"sw_duel_bow_shots")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("sw_duel_bow_hits").getAsInt() /
                                    (float) duelsJson.get("sw_duel_bow_shots").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //方块放置数
                solo.append(new PlainText("\n方块放置数: "));
                if (determine(duelsJson,"sw_duel_blocks_placed")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sw_duel_blocks_placed").getAsInt())));
                } else solo.append(new PlainText("null"));


                //连击决斗
                solo.append(new PlainText("\n连击决斗:"));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"combo_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("combo_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"combo_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("combo_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"combo_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("combo_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"combo_duel_wins") && determine(duelsJson,"combo_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("combo_duel_wins").getAsInt() /
                                    (float) duelsJson.get("combo_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"combo_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("combo_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "combo_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("combo_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"combo_duel_kills") && determine(duelsJson,"combo_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("combo_duel_kills").getAsInt() /
                                    (float) duelsJson.get("combo_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"combo_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("combo_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "combo_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("combo_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"combo_duel_melee_swings") && determine(duelsJson, "combo_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("combo_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("combo_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //金苹果食用
                solo.append(new PlainText("\n金苹果食用: "));
                if (determine(duelsJson,"combo_duel_golden_apples_eaten")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("combo_duel_golden_apples_eaten").getAsInt())));
                } else solo.append(new PlainText("null"));


                //决一死箭
                solo.append(new PlainText("\n决一死箭: "));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"bowspleef_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bowspleef_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"bowspleef_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bowspleef_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"bowspleef_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bowspleef_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"bowspleef_duel_wins") && determine(duelsJson,"bowspleef_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("bowspleef_duel_wins").getAsInt() /
                                    (float) duelsJson.get("bowspleef_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

//                //击杀
//                solo.append(new PlainText("\n击杀数: "));
//                if (determine(duelsJson,"bowspleef_duel_kills")){
//                    solo.append(new PlainText(String.valueOf(duelsJson.get("bowspleef_duel_kills").getAsInt())));
//                } else solo.append(new PlainText("null"));
//
//                //死亡
//                solo.append(new PlainText(" | 死亡数: "));
//                if (determine(duelsJson, "bowspleef_duel_deaths")) {
//                    solo.append(new PlainText(String.valueOf(duelsJson.get("bowspleef_duel_deaths").getAsInt())));
//                } else solo.append(new PlainText("null"));
//
//                //KD
//                solo.append(new PlainText(" | KD: "));
//                if (determine(duelsJson,"bowspleef_duel_kills") && determine(duelsJson,"bowspleef_duel_deaths")){
//                    solo.append(decimalFormat.format(
//                            (float) duelsJson.get("bowspleef_duel_kills").getAsInt() /
//                                    (float) duelsJson.get("bowspleef_duel_deaths").getAsInt()
//                    ));
//                } else solo.append(new PlainText("null"));

                //弓箭射出
                solo.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"bowspleef_duel_bow_shots")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bowspleef_duel_bow_shots").getAsInt())));
                } else solo.append(new PlainText("null"));


                //相扑对决
                solo.append(new PlainText("\n相扑对决: "));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"sumo_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sumo_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"sumo_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sumo_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"sumo_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sumo_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"sumo_duel_wins") && determine(duelsJson,"sumo_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("sumo_duel_wins").getAsInt() /
                                    (float) duelsJson.get("sumo_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"sumo_duel_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sumo_duel_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "sumo_duel_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sumo_duel_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"sumo_duel_kills") && determine(duelsJson,"sumo_duel_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("sumo_duel_kills").getAsInt() /
                                    (float) duelsJson.get("sumo_duel_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"sumo_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sumo_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "sumo_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("sumo_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"sumo_duel_melee_swings") && determine(duelsJson, "sumo_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("sumo_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("sumo_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));


                //Boxing Duel
                solo.append(new PlainText("\nBoxing Duel: "));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"boxing_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("boxing_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"boxing_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("boxing_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"boxing_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("boxing_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"boxing_duel_wins") && determine(duelsJson,"boxing_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("boxing_duel_wins").getAsInt() /
                                    (float) duelsJson.get("boxing_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"boxing_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("boxing_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "boxing_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("boxing_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"boxing_duel_melee_swings") && determine(duelsJson, "boxing_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("boxing_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("boxing_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));


                //战桥决斗
                solo.append(new PlainText("\n战桥决斗: "));

                //胜场数
                solo.append(new PlainText("\n胜场数: "));
                if (determine(duelsJson,"bridge_duel_wins")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_wins").getAsInt())));
                } else solo.append(new PlainText("null"));

                //败场数
                solo.append(new PlainText(" | 败场数: "));
                if (determine(duelsJson,"bridge_duel_losses")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_losses").getAsInt())));
                } else solo.append(new PlainText("null"));

                //游玩回合数
                solo.append(new PlainText("\n游玩回合数: "));
                if (determine(duelsJson,"bridge_duel_rounds_played")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_rounds_played").getAsInt())));
                } else solo.append(new PlainText("null"));

                //胜率
                solo.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"bridge_duel_wins") && determine(duelsJson,"bridge_duel_rounds_played")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("bridge_duel_wins").getAsInt() /
                                    (float) duelsJson.get("bridge_duel_rounds_played").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //击杀
                solo.append(new PlainText("\n击杀数: "));
                if (determine(duelsJson,"bridge_duel_bridge_kills")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_bridge_kills").getAsInt())));
                } else solo.append(new PlainText("null"));

                //死亡
                solo.append(new PlainText(" | 死亡数: "));
                if (determine(duelsJson, "bridge_duel_bridge_deaths")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_bridge_deaths").getAsInt())));
                } else solo.append(new PlainText("null"));

                //KD
                solo.append(new PlainText("\nKD: "));
                if (determine(duelsJson,"bridge_duel_bridge_kills") && determine(duelsJson,"bridge_duel_bridge_deaths")){
                    solo.append(decimalFormat.format(
                            (float) duelsJson.get("bridge_duel_bridge_kills").getAsInt() /
                                    (float) duelsJson.get("bridge_duel_bridge_deaths").getAsInt()
                    ));
                } else solo.append(new PlainText("null"));

                //Goals Scored
                solo.append(new PlainText(" | Goals Scored: "));
                if (determine(duelsJson, "bridge_duel_goals")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_goals").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战攻击
                solo.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"bridge_duel_melee_swings")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_melee_swings").getAsInt())));
                } else solo.append(new PlainText("null"));

                //近战命中
                solo.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "bridge_duel_melee_hits")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_melee_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"bridge_duel_melee_swings") && determine(duelsJson, "bridge_duel_melee_hits")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("bridge_duel_melee_hits").getAsInt() /
                                    (float) duelsJson.get("bridge_duel_melee_swings").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //弓箭命中
                solo.append(new PlainText("\n弓箭命中: "));
                if (determine(duelsJson, "bridge_duel_bow_hits")) {
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_bow_hits").getAsInt())));
                } else solo.append(new PlainText("null"));

                //弓箭射出
                solo.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"bridge_duel_bow_shots")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_bow_shots").getAsInt())));
                } else solo.append(new PlainText("null"));

                //命中率
                solo.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "bridge_duel_bow_hits") && determine(duelsJson,"bridge_duel_bow_shots")){
                    solo.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("bridge_duel_bow_hits").getAsInt() /
                                    (float) duelsJson.get("bridge_duel_bow_shots").getAsInt() * 100
                    )));
                    solo.append(new PlainText("%"));
                } else solo.append(new PlainText("null"));

                //方块放置
                solo.append(new PlainText("\n方块放置: "));
                if (determine(duelsJson,"bridge_duel_blocks_placed")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_blocks_placed").getAsInt())));
                } else solo.append(new PlainText("null"));

                //金苹果食用
                solo.append(new PlainText(" | 金苹果食用: "));
                if (determine(duelsJson,"bridge_duel_golden_apples_eaten")){
                    solo.append(new PlainText(String.valueOf(duelsJson.get("bridge_duel_golden_apples_eaten").getAsInt())));
                } else solo.append(new PlainText("null"));





            } else {
                main.append(new PlainText("无法得到 单挑模式 数据"));
            }
        } else {
            main.append(new PlainText("无法得到 玩家相关信息!"));
        }


        //Team Statistics
        if (json.get("player").isJsonObject()) {
            playerJson = json.get("player").getAsJsonObject();
            achievements = playerJson.get("achievements").getAsJsonObject();

            team.append(new PlainText("Team Statistics 数据如下:"));

            if (playerJson.get("stats").getAsJsonObject().has("Duels")) {
                duelsJson = playerJson.get("stats").getAsJsonObject().get("Duels").getAsJsonObject();

                //极限生存双人模式
                team.append(new PlainText("\n极限生存双人模式: "));

                //胜场
                team.append(new PlainText("\n胜场: "));
                if (determine(duelsJson,"uhc_doubles_wins")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_wins").getAsInt())));
                } else team.append(new PlainText("null"));

                //败场
                team.append(new PlainText(" | 败场: "));
                if (determine(duelsJson,"uhc_doubles_losses")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_losses").getAsInt())));
                } else team.append(new PlainText("null"));

                //游玩场次
                team.append(new PlainText("\n游玩场次: "));
                if (determine(duelsJson,"uhc_doubles_rounds_played")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_rounds_played").getAsInt())));
                } else team.append(new PlainText("null"));

                //胜率
                team.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"uhc_doubles_wins") && determine(duelsJson,"uhc_doubles_rounds_played")){
                    team.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("uhc_doubles_wins").getAsInt() /
                            (float) duelsJson.get("uhc_doubles_rounds_played").getAsInt() * 100
                    )));
                    team.append(new PlainText("%"));
                }

                //击杀
                team.append(new PlainText("\n击杀: "));
                if (determine(duelsJson,"uhc_doubles_kills")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_kills").getAsInt())));
                } else team.append(new PlainText("null"));

                //死亡
                team.append(new PlainText(" | 死亡: "));
                if (determine(duelsJson, "uhc_doubles_deaths")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_deaths").getAsInt())));
                } else team.append(new PlainText("null"));

                //KD
                team.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"uhc_doubles_kills") && determine(duelsJson,"uhc_doubles_deaths")){
                    team.append(new PlainText(decimalFormat.format(
                            (float)duelsJson.get("uhc_doubles_kills").getAsInt() /
                                    (float) duelsJson.get("uhc_doubles_deaths").getAsInt()
                    )));
                } else team.append(new PlainText("null"));

                //近战攻击
                team.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"uhc_doubles_melee_swings")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_melee_swings").getAsInt())));
                } else team.append(new PlainText("null"));

                //近战命中
                team.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "uhc_doubles_melee_hits")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_melee_hits").getAsInt())));
                } else team.append(new PlainText("null"));

                //命中率
                team.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"uhc_doubles_melee_swings") && determine(duelsJson, "uhc_doubles_melee_hits")){
                    team.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("uhc_doubles_melee_hits").getAsInt() /
                                    (float) duelsJson.get("uhc_doubles_melee_swings").getAsInt() * 100
                    )));
                    team.append(new PlainText("%"));
                } else team.append(new PlainText("null"));

                //弓箭命中
                team.append(new PlainText("\n弓箭命中: "));
                if (determine(duelsJson, "uhc_doubles_bow_hits")) {
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_bow_hits").getAsInt())));
                } else team.append(new PlainText("null"));

                //弓箭射出
                team.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"uhc_doubles_bow_shots")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_bow_shots").getAsInt())));
                } else team.append(new PlainText("null"));

                //命中率
                team.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "uhc_doubles_bow_hits") && determine(duelsJson,"uhc_doubles_bow_shots")){
                    team.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("uhc_doubles_bow_hits").getAsInt() /
                                    (float) duelsJson.get("uhc_doubles_bow_shots").getAsInt() * 100
                    )));
                    team.append(new PlainText("%"));
                } else team.append(new PlainText("null"));

                //方块放置
                team.append(new PlainText("\n方块放置: "));
                if (determine(duelsJson,"uhc_doubles_blocks_placed")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_blocks_placed").getAsInt())));
                } else team.append(new PlainText("null"));

                //金苹果食用
                team.append(new PlainText(" | 金苹果食用: "));
                if (determine(duelsJson,"uhc_doubles_golden_apples_eaten")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_doubles_golden_apples_eaten").getAsInt())));
                } else team.append(new PlainText("null"));


                //极限生存组队模式
                team.append(new PlainText("极限生存组队模式: "));

                //胜场
                team.append(new PlainText("\n胜场: "));
                if (determine(duelsJson,"uhc_four_wins")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_wins").getAsInt())));
                } else team.append(new PlainText("null"));

                //败场
                team.append(new PlainText(" | 败场: "));
                if (determine(duelsJson,"uhc_four_losses")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_losses").getAsInt())));
                } else team.append(new PlainText("null"));

                //游玩场次
                team.append(new PlainText("\n游玩场次: "));
                if (determine(duelsJson,"uhc_four_rounds_played")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_rounds_played").getAsInt())));
                } else team.append(new PlainText("null"));

                //胜率
                team.append(new PlainText(" | 胜率: "));
                if (determine(duelsJson,"uhc_four_wins") && determine(duelsJson,"uhc_four_rounds_played")){
                    team.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("uhc_four_wins").getAsInt() /
                                    (float) duelsJson.get("uhc_four_rounds_played").getAsInt() * 100
                    )));
                    team.append(new PlainText("%"));
                }

                //击杀
                team.append(new PlainText("\n击杀: "));
                if (determine(duelsJson,"uhc_four_kills")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_kills").getAsInt())));
                } else team.append(new PlainText("null"));

                //死亡
                team.append(new PlainText(" | 死亡: "));
                if (determine(duelsJson, "uhc_four_deaths")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_deaths").getAsInt())));
                } else team.append(new PlainText("null"));

                //KD
                team.append(new PlainText(" | KD: "));
                if (determine(duelsJson,"uhc_four_kills") && determine(duelsJson,"uhc_four_deaths")){
                    team.append(new PlainText(decimalFormat.format(
                            (float)duelsJson.get("uhc_four_kills").getAsInt() /
                                    (float) duelsJson.get("uhc_four_deaths").getAsInt()
                    )));
                } else team.append(new PlainText("null"));

                //近战攻击
                team.append(new PlainText("\n近战攻击: "));
                if (determine(duelsJson,"uhc_four_melee_swings")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_melee_swings").getAsInt())));
                } else team.append(new PlainText("null"));

                //近战命中
                team.append(new PlainText(" | 近战命中: "));
                if (determine(duelsJson, "uhc_four_melee_hits")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_melee_hits").getAsInt())));
                } else team.append(new PlainText("null"));

                //命中率
                team.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson,"uhc_four_melee_swings") && determine(duelsJson, "uhc_four_melee_hits")){
                    team.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("uhc_four_melee_hits").getAsInt() /
                                    (float) duelsJson.get("uhc_four_melee_swings").getAsInt() * 100
                    )));
                    team.append(new PlainText("%"));
                } else team.append(new PlainText("null"));

                //弓箭命中
                team.append(new PlainText("\n弓箭命中: "));
                if (determine(duelsJson, "uhc_four_bow_hits")) {
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_bow_hits").getAsInt())));
                } else team.append(new PlainText("null"));

                //弓箭射出
                team.append(new PlainText(" | 弓箭射出: "));
                if (determine(duelsJson,"uhc_four_bow_shots")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_bow_shots").getAsInt())));
                } else team.append(new PlainText("null"));

                //命中率
                team.append(new PlainText(" | 命中率: "));
                if (determine(duelsJson, "uhc_four_bow_hits") && determine(duelsJson,"uhc_four_bow_shots")){
                    team.append(new PlainText(decimalFormat.format(
                            (float) duelsJson.get("uhc_four_bow_hits").getAsInt() /
                                    (float) duelsJson.get("uhc_four_bow_shots").getAsInt() * 100
                    )));
                    team.append(new PlainText("%"));
                } else team.append(new PlainText("null"));

                //方块放置
                team.append(new PlainText("\n方块放置: "));
                if (determine(duelsJson,"uhc_four_blocks_placed")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_blocks_placed").getAsInt())));
                } else team.append(new PlainText("null"));

                //金苹果食用
                team.append(new PlainText(" | 金苹果食用: "));
                if (determine(duelsJson,"uhc_four_golden_apples_eaten")){
                    team.append(new PlainText(String.valueOf(duelsJson.get("uhc_four_golden_apples_eaten").getAsInt())));
                } else team.append(new PlainText("null"));




            } else {
                team.append(new PlainText("无法得到 Team Statistics 数据"));
            }

            //Other Statistics
            if (json.get("player").isJsonObject()) {
                playerJson = json.get("player").getAsJsonObject();
                achievements = playerJson.get("achievements").getAsJsonObject();

                other.append(new PlainText("Other Statistics 数据如下:"));

                if (playerJson.get("stats").getAsJsonObject().has("Duels")) {
                    duelsJson = playerJson.get("stats").getAsJsonObject().get("Duels").getAsJsonObject();

                    //极限生存死亡竞赛
                    other.append(new PlainText("\n极限生存死亡竞赛: "));

                    //胜场
                    other.append(new PlainText("\n胜场："));
                    if (determine(duelsJson,"uhc_meetup_wins")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_losses").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //败场
                    other.append(new PlainText(" | 败场: "));
                    if (determine(duelsJson,"uhc_meetup_losses")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_losses").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //游玩场次
                    other.append(new PlainText("\n游玩场次: "));
                    if (determine(duelsJson,"uhc_meetup_rounds_played")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_rounds_played").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //胜率
                    other.append(new PlainText(" | 胜率: "));
                    if (determine(duelsJson,"uhc_meetup_wins") && determine(duelsJson,"uhc_meetup_rounds_played")){
                        other.append(new PlainText(decimalFormat.format(
                                (float) duelsJson.get("uhc_meetup_losses").getAsInt() /
                                        (float) duelsJson.get("uhc_meetup_rounds_played").getAsInt() * 100
                        )));
                        other.append(new PlainText("%"));
                    } else other.append(new PlainText("null"));

                    //击杀数
                    other.append(new PlainText("\n击杀数: "));
                    if (determine(duelsJson,"uhc_meetup_kills")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_kills").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //死亡数
                    other.append(new PlainText(" | 死亡数: "));
                    if (determine(duelsJson,"uhc_meetup_deaths")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_deaths").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //KD
                    other.append(new PlainText(" | KD: "));
                    if (determine(duelsJson,"uhc_meetup_kills") && determine(duelsJson,"uhc_meetup_deaths")){
                        other.append(new PlainText(decimalFormat.format(
                                (float) duelsJson.get("uhc_meetup_kills").getAsInt() /
                                        (float) duelsJson.get("uhc_meetup_deaths").getAsInt()
                        )));
                    } else other.append(new PlainText("null"));

                    //近战攻击
                    other.append(new PlainText("\n近战攻击: "));
                    if (determine(duelsJson,"uhc_meetup_melee_swings")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_melee_swings").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //近战命中
                    other.append(new PlainText(" | 近战命中: "));
                    if (determine(duelsJson,"uhc_meetup_melee_hits")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_melee_hits").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //近战命中率
                    other.append(new PlainText(" | 近战命中率: "));
                    if (determine(duelsJson,"uhc_meetup_melee_hits") && determine(duelsJson,"uhc_meetup_melee_swings")){
                        other.append(new PlainText(decimalFormat.format(
                                (float) duelsJson.get("uhc_meetup_melee_hits").getAsInt() /
                                        (float) duelsJson.get("uhc_meetup_melee_swings").getAsInt() * 100
                        )));
                        other.append(new PlainText("%"));
                    } else other.append(new PlainText("null"));

                    //弓箭射击
                    other.append(new PlainText("\n弓箭射击: "));
                    if (determine(duelsJson,"uhc_meetup_bow_shots")) {
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_bow_shots").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //弓箭命中
                    other.append(new PlainText(" | 弓箭命中: "));
                    if (determine(duelsJson,"uhc_meetup_bow_hits")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_bow_hits").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //弓箭命中率
                    other.append(new PlainText(" | 弓箭命中率: "));
                    if (determine(duelsJson,"uhc_meetup_bow_hits") && determine(duelsJson,"uhc_meetup_bow_shots")){
                        other.append(new PlainText(decimalFormat.format(
                                (float) duelsJson.get("uhc_meetup_bow_hits").getAsInt() /
                                        (float) duelsJson.get("uhc_meetup_bow_shots").getAsInt() * 100
                        )));
                        other.append(new PlainText("%"));
                    } else other.append(new PlainText("null"));

                    //方块放置数
                    other.append(new PlainText("\n方块放置数: "));
                    if (determine(duelsJson,"uhc_meetup_blocks_placed")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("uhc_meetup_blocks_placed").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //跑酷
                    other.append(new PlainText("\n跑酷: "));

                    //胜场
                    other.append(new PlainText("\n胜场: "));
                    if(determine(duelsJson,"parkour_eight_wins")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("parkour_eight_wins").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //败场
                    other.append(new PlainText(" | 败场: "));
                    if (determine(duelsJson,"parkour_eight_losses")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("parkour_eight_losses").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //游玩场次
                    other.append(new PlainText("\n游玩场次"));
                    if (determine(duelsJson,"parkour_eight_rounds_played")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("parkour_eight_rounds_played").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //胜率
                    other.append(new PlainText(" | 胜率: "));
                    if (determine(duelsJson,"parkour_eight_wins") && determine(duelsJson,"parkour_eight_rounds_played")){
                        other.append(new PlainText(decimalFormat.format(
                                (float) duelsJson.get("parkour_eight_wins").getAsInt() /
                                        (float) duelsJson.get("parkour_eight_rounds_played").getAsInt() * 100
                        )));
                        other.append(new PlainText("%"));
                    } else other.append(new PlainText("null"));


                    //Duel Arena
                    other.append(new PlainText("\nDuel Arena: "));

                    //胜场
                    other.append(new PlainText("\n胜场: "));
                    if (determine(duelsJson,"duel_arena_wins")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("duel_arena_wins").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //败场
                    other.append(new PlainText(" | 败场: "));
                    if (determine(duelsJson,"duel_arena_losses")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("duel_arena_losses").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //游玩场次
                    other.append(new PlainText("\n游玩场次: "));
                    if (determine(duelsJson,"duel_arena_rounds_played")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("duel_arena_rounds_played").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //胜率
                    other.append(new PlainText(" | 胜率: "));
                    if (determine(duelsJson,"duel_arena_wins") && determine(duelsJson,"duel_arena_rounds_played")){
                        other.append(new PlainText(decimalFormat.format(
                                (float) duelsJson.get("duel_arena_wins").getAsInt() /
                                        (float) duelsJson.get("duel_arena_rounds_played").getAsInt() * 100
                        )));
                        other.append(new PlainText("%"));
                    } else other.append(new PlainText("null"));

                    //近战攻击
                    other.append(new PlainText("\n近战攻击: "));
                    if (determine(duelsJson,"duel_arena_melee_swings")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("duel_arena_melee_swings").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //近战命中
                    other.append(new PlainText(" | 近战命中: "));
                    if (determine(duelsJson,"duel_arena_melee_hits")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("duel_arena_melee_hits").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //近战命中率
                    other.append(new PlainText(" | 近战命中率: "));
                    if (determine(duelsJson,"duel_arena_melee_hits") && determine(duelsJson,"duel_arena_melee_swings")){
                        other.append(new PlainText(decimalFormat.format(
                                (float) duelsJson.get("duel_arena_melee_hits").getAsInt() /
                                        (float) duelsJson.get("duel_arena_melee_swings").getAsInt() * 100
                        )));
                        other.append(new PlainText("%"));
                    } else other.append(new PlainText("null"));

                    //弓箭射击
                    other.append(new PlainText("\n弓箭射击: "));
                    if (determine(duelsJson,"duel_arena_bow_shots")) {
                        other.append(new PlainText(String.valueOf(duelsJson.get("duel_arena_bow_shots").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //弓箭命中
                    other.append(new PlainText(" | 弓箭命中: "));
                    if (determine(duelsJson,"duel_arena_bow_hits")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("duel_arena_bow_hits").getAsInt())));
                    } else other.append(new PlainText("null"));

                    //弓箭命中率
                    other.append(new PlainText(" | 弓箭命中率: "));
                    if (determine(duelsJson,"duel_arena_bow_hits") && determine(duelsJson,"duel_arena_bow_shots")){
                        other.append(new PlainText(decimalFormat.format(
                                (float) duelsJson.get("duel_arena_bow_hits").getAsInt() /
                                        (float) duelsJson.get("duel_arena_bow_shots").getAsInt() * 100
                        )));
                        other.append(new PlainText("%"));
                    } else other.append(new PlainText("null"));

                    //方块放置数
                    other.append(new PlainText("\n方块放置数: "));
                    if (determine(duelsJson,"duel_arena_blocks_placed")){
                        other.append(new PlainText(String.valueOf(duelsJson.get("duel_arena_blocks_placed").getAsInt())));
                    } else other.append(new PlainText("null"));


                }

            }


        } else {
            team.append(new PlainText("无法得到 玩家相关信息!"));
        }

        ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
        builder.add(group.getBot().getId(),group.getBot().getNick(),main.build());
        builder.add(group.getBot().getId(),group.getBot().getNick(),solo.build());
        builder.add(group.getBot().getId(),group.getBot().getNick(),team.build());
        builder.add(group.getBot().getId(),group.getBot().getNick(),other.build());
        group.sendMessage(builder.build());
    }

}
