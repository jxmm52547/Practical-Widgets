package xyz.jxmm.music;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.QuoteReply;


public class Songs {
    public static void main(Group group, JsonArray songs, MessageSource source){
        QuoteReply quoteReply = new QuoteReply(source);
        MessageChainBuilder chain = new MessageChainBuilder().append(quoteReply);

        String name = "";

        for (int i = 0; i < songs.size(); i++) {
            StringBuilder artistsName = new StringBuilder();
            JsonObject song = songs.get(i).getAsJsonObject();  //单曲

            name = song.get("name").getAsString();
            JsonArray artistsArray = song.get("artists").getAsJsonArray();
            for (int j = 0; j < artistsArray.size() ; j++) {
                artistsName.append(artistsArray.get(j).getAsJsonObject().get("name").getAsString()).append(" / ");
            }

            chain.append(new PlainText("\n"));
            chain.append(new PlainText(String.valueOf(i+1))).append(new PlainText(":  "));  //序号
            chain.append(new PlainText(name));  //歌名
            chain.append(new PlainText(" - "));  //连接符
            chain.append(new PlainText(artistsName));
        }
        group.sendMessage(chain.build());

    }
}
