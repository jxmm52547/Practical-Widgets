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
import xyz.jxmm.minecraft.Nick;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Guild {

    public static void common(String msg,Long sender,Group group,MessageChainBuilder chain){
        JsonObject json = new JsonObject();
        if (msg.startsWith("player")){ //玩家ID
            msg = msg.replaceAll("player ","");
            json = new Gson().fromJson(Tool.main(msg,group,chain,"player"), JsonObject.class);
        } else if (msg.startsWith("name")) { //公会name
            msg = msg.replaceAll("name ","");
            json = new Gson().fromJson(Tool.guild(msg,"name"), JsonObject.class);
        } else { //默认 玩家ID
            json = new Gson().fromJson(Tool.main(msg,group,chain,"player"), JsonObject.class);
        }

        guild(json,sender,group);

    }
    public static void guild(JsonObject json, Long sender, Group group){
        MessageChainBuilder chain = new MessageChainBuilder().append(MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]"));
        MessageChainBuilder ranksChain = new MessageChainBuilder().append(MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]"));
        MessageChainBuilder membersChain = new MessageChainBuilder().append(MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]"));

        DecimalFormat decimalFormat = new DecimalFormat("0.000");

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

            chain.append(new PlainText(" | 公会创建时间: "));
            long created = json.get("created").getAsLong();
            Instant instant = Instant.ofEpochMilli(created);
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            chain.append(new PlainText(localDateTime.toString()));

            //描述
            if (json.has("description")){
                chain.append(new PlainText("描述: "));
                chain.append(new PlainText("\n" + json.get("description").getAsString()));
            }

            //经验 & 等级
            chain.append(new PlainText("\n总经验: "));
            chain.append(new PlainText(String.valueOf(json.get("exp").getAsLong())));

            chain.append(new PlainText(" | 等级: "));
            String ex = json.get("exp").toString();
            long l = (long) Double.parseDouble(ex);
            double xp = Math.sqrt((0.0008 * l) + 12.25) - 2.5;

            chain.append(new PlainText(decimalFormat.format(xp)));

            //标签 & 颜色
            if (json.has("tag")) {
                chain.append(new PlainText("\n标签: "));
                chain.append(new PlainText(json.get("tag").getAsString()));
            }
            if (json.has("tagColor")) {
                chain.append(new PlainText(" " + Nick.color(json.get("tagColor").getAsString())));
            }

            //ranks  职位列表
            ranksChain.append(new PlainText(" 职位列表: "));
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
            ranksChain.append(new PlainText(stringBuilderDefaultRank.toString()));
            ranksChain.append(new PlainText(sbRank.toString()));

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

            ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
            builder.add(group.getBot().getId(),group.getBot().getNick(),chain.build());
            builder.add(group.getBot().getId(),group.getBot().getNick(),ranksChain.build());
            builder.add(group.getBot().getId(),group.getBot().getNick(),membersChain.build());
            group.sendMessage(builder.build());
        }





    }
}
