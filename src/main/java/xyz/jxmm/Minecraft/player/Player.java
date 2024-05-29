package xyz.jxmm.minecraft.player;

import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import xyz.jxmm.minecraft.Nick;
import xyz.jxmm.minecraft.player.PlayerDetermine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Player {
    public static void player(JsonObject json,JsonObject recentGames,JsonObject guild,JsonObject online, Long sender, Group group){
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);
        JsonObject playerJson;
        JsonObject giftingMeta;
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日-HH时mm分ss秒", Locale.CHINA);

        if (json.get("player").isJsonObject()){
            playerJson = json.get("player").getAsJsonObject();
//            achievements = playerJson.get("achievements").getAsJsonObject();  V0.4.2版本更新注释

            chain.append(new PlainText("\n" + Nick.nick(playerJson) + " ")); //玩家名称前缀
            chain.append(new PlainText(playerJson.get("displayname").getAsString()));

            chain.append(new PlainText("\n在线状态: "));
            if (PlayerDetermine.online(online)){
                JsonObject session = online.get("session").getAsJsonObject();
                boolean onlineStatus = session.get("online").getAsBoolean();
                if (onlineStatus) {
                    chain.append(new PlainText("ONLINE\uD83D\uDFE2"));
                    chain.append(new PlainText("\n" + session.get("gameType").getAsString()));
                     {
                        if (PlayerDetermine.mode(session)){
                            chain.append(new PlainText(" | "));
                            chain.append(new PlainText(session.get("mode").getAsString()));
                        }
                        if (PlayerDetermine.map(session)){
                            chain.append(new PlainText(" | "));
                            chain.append(new PlainText(session.get("map").getAsString()));
                        }

                    }
                }
                else {
                    if (PlayerDetermine.lastLogin(playerJson)){
                        chain.append(new PlainText("OFFLINE\uD83D\uDD34"));
                    } else {chain.append(new PlainText("未开启在线状态API"));}

                }

            } else {
                chain.append(new PlainText("ERROR"));
            }

            chain.append(new PlainText("\nRANK赠送数: "));
            if (PlayerDetermine.giftingMeta(playerJson)){
                giftingMeta = playerJson.get("giftingMeta").getAsJsonObject();
                if (PlayerDetermine.ranksGiven(giftingMeta)){
                    chain.append(new PlainText(String.valueOf(giftingMeta.get("ranksGiven").getAsInt())));
                } else chain.append(new PlainText("0"));
            } else chain.append(new PlainText("0"));

            chain.append(new PlainText("\n首次登录时间: "));
            if (PlayerDetermine.firstLogin(playerJson)){
                chain.append(new PlainText(simpleDateFormat.format(new Date(playerJson.get("firstLogin").getAsLong()))));
            } else {
                chain.append(new PlainText("null"));
            }

            if (PlayerDetermine.lastLogin(playerJson)){
                chain.append(new PlainText("\n最后登录时间: "));
                chain.append(new PlainText(simpleDateFormat.format(new Date(playerJson.get("lastLogin").getAsLong()))));
            }

            if (PlayerDetermine.lastLogout(playerJson)){
                chain.append(new PlainText("\n最后退出时间: "));
                chain.append(new PlainText(simpleDateFormat.format(new Date(playerJson.get("lastLogout").getAsLong()))));
            }

            chain.append(new PlainText("\n使用的语言: "));
            if (PlayerDetermine.userLanguage(playerJson)){
                chain.append(new PlainText(playerJson.get("userLanguage").getAsString()));
            } else {chain.append(new PlainText("null"));}

            chain.append(new PlainText("\n所属公会: "));
            if (PlayerDetermine.guild(guild)){
                chain.append(new PlainText(guild.get("guild").getAsJsonObject().get("name").getAsString()));
            } else {chain.append(new PlainText("无"));}

            chain.append(new PlainText("\nHypixel等级: "));
            if (PlayerDetermine.networkExp(playerJson)){
                String ex = playerJson.get("networkExp").toString();
                long l = (long) Double.parseDouble(ex);
                double xp = Math.sqrt((0.0008 * l) + 12.25) - 2.5;

                chain.append(new PlainText(decimalFormat.format(xp)));
            } else {chain.append(new PlainText("null"));}

            if (PlayerDetermine.recentGames(recentGames)){
                chain.append(new PlainText("\n最近游玩的模式: "));
                chain.append(new PlainText(recentGames.get("games").getAsJsonArray().get(0).getAsJsonObject().get("gameType").getAsString()));
            }

            chain.append(new PlainText("\n成就点数: "));
            if (PlayerDetermine.achievementPoints(playerJson)){
                chain.append(new PlainText(String.valueOf(playerJson.get("achievementPoints").getAsInt())));
            } else {chain.append(new PlainText("null"));}

            chain.append(new PlainText(" | 人品值: "));
            if (PlayerDetermine.karma(playerJson)){
                chain.append(new PlainText(String.valueOf(playerJson.get("karma").getAsInt())));
            } else {chain.append(new PlainText("null"));}


            URL url;
            byte[] data;
            try {
                url = new URL("https://crafatar.com/renders/body/" + playerJson.get("uuid").getAsString() + "?scale=10" + "&overlay");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                InputStream is = conn.getInputStream();

                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[6024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                is.close();

                data = outStream.toByteArray();

            } catch (IOException e) {
                chain.append(new PlainText("\n\n皮肤图片加载失败, 可能是API出现问题"));
                chain.append(new PlainText("\n可查看 crafatar.com 确认是否无法加载"));
                group.sendMessage(chain.build());
                System.out.println("以对下方报错进行处理, 若 crafatar.com 正常访问请联系作者");
                throw new RuntimeException(e);
            }

            Image img = group.uploadImage(ExternalResource.create(data));
            chain.append(img);

        } else {
            chain.append(new PlainText("该玩家存在, 但是玩家数据不存在, 可能是因为玩家没有进入过hyp服务器, 也可能是数据丢失"));
        }

        group.sendMessage(chain.build());
    }
}
