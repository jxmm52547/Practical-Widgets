package xyz.jxmm.minecraft.fish;

import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.minecraft.Nick;

import java.text.DecimalFormat;

public class Fish {
    public static void fish(JsonObject json, Long sender, Group group){
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);
        MessageChainBuilder all = new MessageChainBuilder();
        MessageChainBuilder water = new MessageChainBuilder();
        MessageChainBuilder lava = new MessageChainBuilder();
        MessageChainBuilder ice = new MessageChainBuilder();

        JsonObject playerJson = new JsonObject();
        JsonObject statsJson;
        JsonObject mainLobbyJson;
        JsonObject fishJson;
        boolean tf = false;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        if (!json.get("player").isJsonObject()) {
            chain.append(new PlainText("无法得到 玩家相关信息"));
        } else {
            playerJson = json.get("player").getAsJsonObject();
            if (playerJson.has("stats")){
                statsJson = playerJson.get("stats").getAsJsonObject();
                if (statsJson.has("MainLobby")){
                    mainLobbyJson = statsJson.get("MainLobby").getAsJsonObject();
                    if (mainLobbyJson.has("fishing")){
                        fishJson = mainLobbyJson.get("fishing").getAsJsonObject();
                        tf = true;
                    } else chain.append(new PlainText("无法得到 钓鱼 数据!"));
                } else chain.append(new PlainText("无法得到 主大厅 数据"));
            }
        }

        if (!tf) {
            group.sendMessage(chain.build());
        } else {
            chain.append(new PlainText(Nick.nick(playerJson))); //玩家名称前缀
            chain.append(new PlainText(playerJson.get("displayname").getAsString()));
            chain.append(new PlainText(" | 钓鱼 数据如下: "));


        }
    }
}
