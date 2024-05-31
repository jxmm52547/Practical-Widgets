package xyz.jxmm.minecraft.guild;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.tools.MyComparatorValue;
import xyz.jxmm.minecraft.MJURLConnect;
import xyz.jxmm.minecraft.Nick;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static xyz.jxmm.minecraft.guild.GuildDetermine.determine;

public class Guild {

    static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public static void common(String msg,Long sender,Group group,MessageChainBuilder chain){
        JsonObject json = new JsonObject();
        String id = null;
        if (msg.startsWith("player")){ //玩家ID
            msg = msg.replaceAll("player ","");
            id = msg;
            json = new Gson().fromJson(Tool.main(msg,group,chain,"player"), JsonObject.class);
        } else if (msg.startsWith("name")) { //公会name
            msg = msg.replaceAll("name ","");
            json = new Gson().fromJson(Tool.guild(msg,"name"), JsonObject.class);
        } else { //默认 玩家ID
            id = msg;
            json = new Gson().fromJson(Tool.main(msg,group,chain,"player"), JsonObject.class);
        }

        guild(json,sender,group,id);

    }
    public static void guild(JsonObject json, Long sender, Group group, String id){
        MessageChainBuilder chain = new MessageChainBuilder().append(MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]"));
        MessageChainBuilder achievementChain = new MessageChainBuilder();
        MessageChainBuilder membersChain = new MessageChainBuilder();



        JsonArray members = new JsonArray();
        JsonArray ranks = new JsonArray();
        JsonObject achievements = new JsonObject();
        JsonArray preferredGames = new JsonArray();
        JsonObject guildExpByGameType = new JsonObject();

        if (json.get("guild").isJsonNull()){
            chain.append(new PlainText("\n玩家未加入公会(player) 或 不存在此公会(name)"));
            group.sendMessage(chain.build());
        } else {
            json = json.get("guild").getAsJsonObject();
            ranks = json.get("ranks").getAsJsonArray();
            members = json.get("members").getAsJsonArray();
            if (json.has("achievements")) achievements = json.get("achievements").getAsJsonObject();
            if (json.has("preferredGames")) preferredGames = json.get("preferredGames").getAsJsonArray();
            if (json.has("guildExpByGameType")) guildExpByGameType = json.get("guildExpByGameType").getAsJsonObject();

            chain.append(new PlainText("\n公会名称: "));
            chain.append(new PlainText(json.get("name").getAsString()));

            chain.append(new PlainText("\n会长: "));
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getAsJsonObject().get("rank").getAsString().equals("Guild Master")){
                    chain.append(new PlainText(MJURLConnect.moJangURLConnect(members.get(i).getAsJsonObject().get("uuid").getAsString(),"uuid")));
                }
            }
            chain.append(new PlainText(MJURLConnect.moJangURLConnect(members.get(0).getAsJsonObject().get("uuid").getAsString(),"uuid")));

            chain.append(new PlainText("\n成员数量: "));
            chain.append(new PlainText(String.valueOf(members.size())));

            chain.append(new PlainText("\n公会创建时间: "));
            long created = json.get("created").getAsLong();
            Instant instant = Instant.ofEpochMilli(created);
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            chain.append(new PlainText(localDateTime.toString()));

            //描述
            if (json.has("description")){
                chain.append(new PlainText("\n描述: "));
                chain.append(new PlainText(json.get("description").getAsString()));
            }

            //经验 & 等级
            int exp = json.get("exp").getAsInt();
            int[] expNeeded = {100000, 150000, 250000, 500000, 750000, 1000000, 1250000, 1500000, 2000000, 2500000, 2500000, 2500000, 2500000, 2500000, 3000000};
            
            chain.append(new PlainText("\n等级: "));
            int level = 0;
            for (int j : expNeeded) {
                if (exp >= j) {
                    exp -= j;
                    level++;
                } else break;
            }
            while (exp>=3000000){
                level++;
                exp -= 3000000;
            }
            chain.append(new PlainText(String.valueOf(level)));

            chain.append(new PlainText("\n总经验: "));
            int ex = json.get("exp").getAsInt();
            chain.append(new PlainText(formatExp(ex)));

            String target = "100K";
            exp = json.get("exp").getAsInt();
            int target1 = 100000;
            int index = 0;
            for (int j : expNeeded) {
                if (exp >= j) {
                    exp -= j;
                    target = Tool.exp(index);
                    target1 = expNeeded[index];
                    index++;
                } else break;
            }
            while (exp>=3000000){
                target = "3M";
                target1 = 3000000;
                exp -= 3000000;
            }
            chain.append(new PlainText(
                    "("+ decimalFormat.format((float) exp / (float)1000000) +
                            "/" + target + " " +
                            decimalFormat.format((float) exp / (float) target1 * 100) +
                            "%)"));

            //标签 & 颜色
            if (json.has("tag")) {
                chain.append(new PlainText("\n标签: "));
                chain.append(new PlainText(json.get("tag").getAsString()));
            }
            if (json.has("tagColor")) {
                chain.append(new PlainText(" " + Nick.color(json.get("tagColor").getAsString())));
            }

            //公会成就
            achievementChain.append(new PlainText("公会成就:\n"));

            achievementChain.append(new PlainText("最高同时在线: "));
            achievementChain.append(new PlainText(String.valueOf(achievements.get("ONLINE_PLAYERS").getAsInt())));

            achievementChain.append(new PlainText("\n每日最高经验: "));
            if (determine(achievements,"EXPERIENCE_KINGS")){
                ex = achievements.get("EXPERIENCE_KINGS").getAsInt();
                achievementChain.append(new PlainText(formatExp(ex)));
            } else achievementChain.append(new PlainText("null"));

            achievementChain.append(new PlainText("\n公会胜场数: "));
            if (determine(achievements, "WINNERS")) {
                achievementChain.append(new PlainText(String.valueOf(achievements.get("WINNERS").getAsInt())));
            } else achievementChain.append(new PlainText("null"));


            achievementChain.append(new PlainText("\n公会每周经验: "));
            Set<String> set = new HashSet<>();
            int sum = 0;

            //总计
            for (int i = 0; i < members.size(); i++) {
                JsonObject expHistory = new JsonObject();
                if (determine(members.get(i).getAsJsonObject(),"expHistory")){
                    expHistory = members.get(i).getAsJsonObject().get("expHistory").getAsJsonObject();
                }
                set = expHistory.keySet();
                for (String j : set) {
                    sum += expHistory.get(j).getAsInt();
                }
            }
            achievementChain.append(new PlainText(formatExp(sum)));
            int weekExp = sum; //周 平均每位成员经验
            boolean activation = sum == 0;

            //周每日
            for (String s : set) {
                achievementChain.append(new PlainText("\n    " + s + ": "));
                sum = 0;
                for (int i = 0; i < members.size(); i++) {
                    JsonObject expHistory = new JsonObject();
                    if (determine(members.get(i).getAsJsonObject(),"expHistory")){
                        expHistory = members.get(i).getAsJsonObject().get("expHistory").getAsJsonObject();
                    } else continue;
                    sum += expHistory.get(s).getAsInt();
                }
                achievementChain.append(new PlainText(formatExp(sum)));
            }


            achievementChain.append(new PlainText("\n平均每位成员经验:"));
            //今日
            achievementChain.append(new PlainText("\n今日: "));
            if (determine(achievements,"EXPERIENCE_KINGS")){
                achievementChain.append(new PlainText(decimalFormat.format(
                        (float) achievements.get("EXPERIENCE_KINGS").getAsInt()  /
                        (float) members.size())
                ));
            }
            //一周
            achievementChain.append(new PlainText(" | 一周: "));
            achievementChain.append(new PlainText(decimalFormat.format(
                    (float) weekExp  /
                            (float) members.size())
            ));

            if (id == null) {
                membersChain.append(new PlainText("name 查询无玩家信息!"));
            } else {
                membersChain.append(new PlainText("成员: " + id));

                String uuid = MJURLConnect.moJangURLConnect(id,"name");

                for (int i = 0; i < members.size(); i++) {
                    JsonObject member = members.get(i).getAsJsonObject();
                    if (member.get("uuid").getAsString().equals(uuid)){
                        //rank
                        membersChain.append(new PlainText("\n成员rank: "));
                        membersChain.append(member.get("rank").getAsString());

                        //加入时间
                        membersChain.append(new PlainText("\n加入时间: "));
                        instant = Instant.ofEpochMilli(member.get("joined").getAsLong());
                        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                        membersChain.append(new PlainText(String.valueOf(localDate)));

                        //任务
                        if (determine(member,"questParticipation")){
                            membersChain.append(new PlainText("\n完成公会任务: "));
                            membersChain.append(new PlainText(String.valueOf(member.get("questParticipation").getAsInt())));
                        }

                        //经验
                        if (determine(member,"expHistory")){
                            membersChain.append(new PlainText("\n个人每周经验: "));
                            sum = 0;
                            JsonObject expHistory = member.get("expHistory").getAsJsonObject();
                            for (String s : set) {
                                sum += expHistory.get(s).getAsInt();
                            }
                            membersChain.append(new PlainText(formatExp(sum)));

                            for (String s : set) {
                                membersChain.append(new PlainText("\n    " + s + ": "));
                                membersChain.append(new PlainText(formatExp(expHistory.get(s).getAsInt())));
                                //排名
                                membersChain.append(new PlainText(" #"));
                                Map<String,Integer> name = new HashMap<>();
//                                Map<String,Integer> vue = new HashMap<>();
                                for (int j = 0; j < members.size(); j++) {
                                    name.put(members.get(j).getAsJsonObject().get("uuid").getAsString(),
                                            members.get(j).getAsJsonObject().get("expHistory").getAsJsonObject().get(s).getAsInt());
//                                    vue = name;
                                }
                                List<Map.Entry<String,Integer>> strName= new ArrayList<>(name.entrySet());
//                                List<Map.Entry<String,Integer>> vueValue = new ArrayList<>(vue.entrySet());
                                strName.sort(new MyComparatorValue());
//                                vueValue.sort(new MyComparatorValue());

                                List<String> uuidList = new ArrayList<>();
                                for (Map.Entry<String, Integer> entry1:strName){
                                    uuidList.add(entry1.getKey());
                                }
                                int x = 1;
                                for (String string : uuidList){
                                    if (string.equals(uuid)){
                                        if (activation){
                                            membersChain.append(new PlainText("1"));
                                        } else {
                                            membersChain.append(new PlainText(String.valueOf(x)));
                                        }
                                        membersChain.append(new PlainText("/"));
                                        membersChain.append(new PlainText(String.valueOf(members.size())));
                                        break;
                                    } else x++;
                                }


                            }


                        }


                        break;
                    }
                }



            }


            /*
            //ranks  职位列表
            achievementChain.append(new PlainText(" 职位列表: "));
            StringBuilder stringBuilderDefaultRank = new StringBuilder().append("\n默认职位: ");
            StringBuilder sbRank = new StringBuilder();
            for (int i = 0; i < ranks.size(); i++) {
                JsonObject rank = ranks.get(i).getAsJsonObject();
                String name = rank.get("name").getAsString();
                boolean df = rank.get("default").getAsBoolean();
                String tag = rank.get("tag").isJsonNull() ? "null" : rank.get("tag").getAsString();
                //创建时间
                long rankCreated = rank.get("created").getAsLong();
                Instant instant1 = Instant.ofEpochMilli(rankCreated);
                LocalDateTime localDateTime1 = instant1.atZone(ZoneId.systemDefault()).toLocalDateTime();
                //权重
                int priority = rank.get("priority").getAsInt();
                //如果 df = true 即为默认职位, 提前写入;
                stringBuilderDefaultRank.append(df ? name : "") ;

                sbRank.append("\n(").append(i).append(")");
                sbRank.append("\n  职位名称: ").append(name);
                sbRank.append("\n  标签: ").append(tag);
                sbRank.append("\n  创建时间: ").append(localDateTime1);
                sbRank.append("\n  权重: ").append(priority);
            }
            achievementChain.append(new PlainText(stringBuilderDefaultRank.toString()));
            achievementChain.append(new PlainText(sbRank.toString()));

             */

            /*
            //成员列表
            membersChain.append(new PlainText(" 成员列表: "));
            membersChain.append(new PlainText("总人数: "));
            int totalMembers = members.size(); //总人数
            StringBuilder sbMember = new StringBuilder();
            for (int i = 0; i < members.size(); i++) {
                JsonObject member = members.get(i).getAsJsonObject();
                String uuid = member.get("uuid").getAsString();
                String rank = member.get("rank").getAsString();
                //加入时间
                long joined = member.get("joined").getAsLong();
                Instant instant2 = Instant.ofEpochMilli(joined);
                LocalDateTime localDateTime2 = instant2.atZone(ZoneId.systemDefault()).toLocalDateTime();
                int questParticipation = member.has("questParticipation") ? member.get("questParticipation").getAsInt() : 0;
                //7天内为公会获取的经验
                long totalExp = 0;
                JsonObject expHistoryJson = member.get("expHistory").getAsJsonObject();
                List<Map.Entry<String,JsonElement>> keyList = new ArrayList<>(expHistoryJson.entrySet());
                for (Map.Entry<String, JsonElement> entry:keyList){
                    totalExp += entry.getValue().getAsLong();
                }

                sbMember.append("\n(").append(i).append(")");
                sbMember.append("\n  uuid: ").append(uuid);
                sbMember.append("\n  职位: ").append(rank);
                sbMember.append("\n  加入时间: ").append(localDateTime2);
                sbMember.append("\n  已完成任务数: ").append(questParticipation);
                sbMember.append("\n  7天内为公会获取经验值: ").append(totalExp);
            }
            membersChain.append(new PlainText(String.valueOf(totalMembers)));
            membersChain.append(new PlainText(sbMember.toString()));

             */

            ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
            builder.add(group.getBot().getId(),group.getBot().getNick(),chain.build());
            builder.add(group.getBot().getId(),group.getBot().getNick(),achievementChain.build());
            builder.add(group.getBot().getId(),group.getBot().getNick(),membersChain.build());
            group.sendMessage(builder.build());
        }

    }

    //格式化经验值
    public static String formatExp(int ex){
        if (ex >= 100000 & ex < 1000000){
            return decimalFormat.format((float)ex/1000) + "K";
        } else if (ex > 1000000){
            return decimalFormat.format((float)ex/1000000) + "M";
        } else {
            return String.valueOf(ex);
        }
    }
}
