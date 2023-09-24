package xyz.jxmm.sign;

import static xyz.jxmm.tools.FileWriterMethod.fileWriter;
import static xyz.jxmm.tools.HasString.hasString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Properties;

public class Sign {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File data = new File("./PracticalWidgets/data.json");
    static File cfg = new File("./PracticalWidgets/config.properties");
    static Properties properties = new Properties();

    public static void sign(Long sender, Group group) throws IOException {
        MessageChainBuilder chain = new MessageChainBuilder();

        JsonObject json;
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        properties.load(new InputStreamReader(Files.newInputStream(cfg.toPath()), StandardCharsets.UTF_8));
        json = new Gson().fromJson(new InputStreamReader(Files.newInputStream(data.toPath()), StandardCharsets.UTF_8), JsonObject.class);

        if (!json.has(sender.toString())) {
            chain.append(at);
            chain.append(new PlainText("\n未注册,请使用[ /注册 ]先注册哦"));
            group.sendMessage(chain.build());
        } else {
            JsonObject signJson = json.get(sender.toString()).getAsJsonObject().get("sign").getAsJsonObject();

            String toDayWeek = LocalDateTime.now().getDayOfWeek().toString();
            String oldDayWeek = signJson.get("week").getAsString();

            if (toDayWeek.equals(oldDayWeek)) {  //今日已签到
                String firstHBS = properties.getProperty("hasBeenSigned");

                String express = properties.getProperty("hasBeenSignedExpress");

                String format = "";

                if (hasString(firstHBS,"$hasBeenSignedExpress$")){
                    System.out.println("in");
                    format += firstHBS.replaceAll("\\$hasBeenSignedExpress\\$",express);

//                    for (int i = 0; i < firstHBS.length(); i++) {
//                        char c = firstHBS.charAt(i);
//                        if (c == '$'){
//                            for (int m = i; m < firstHBS.length(); m++){
//                                char c1 = firstHBS.charAt(m);
//                                if (c1 == '$'){
//                                    firstHBS.replace(i,m,express);
//                                    break;
//                                }
//                            }
//                            break;
//                        }
//                    }
                }

                System.out.println(format);
                sendMessage(at,group,chain,format);



            } else {  //未签到
                JsonObject user = json.get(sender.toString()).getAsJsonObject().get("sign").getAsJsonObject();

                int signTimes = user.get("签到次数").getAsInt();

                signTimes++;

                user.addProperty("签到次数", signTimes);
                user.addProperty("week", toDayWeek);

                json.get(sender.toString()).getAsJsonObject().add("sign", user);

                String firstSign = properties.getProperty("sign");
                String express = properties.getProperty("signExpress");

                String format = "";

                if (hasString(firstSign,"$signExpress$")){
                    format = firstSign.replaceAll("\\$signExpress\\$",express);

//                    for (int i = 0; i < firstSign.length(); i++) {
//                        char c = firstSign.charAt(i);
//                        if (c == '$'){
//                            firstSign.replace(i,i+22,express);
//                        }
//                    }
                }

                sendMessage(at,group,chain,format);
                fileWriter("./PracticalWidgets/data.json", gson.toJson(json));

            }
        }
    }

    public static void sendMessage(MessageChain at,Group group,MessageChainBuilder chain,String format){
        if (hasString(format,"&@sender")){  //需要 @sender 时



            if (format.startsWith("&@sender")) {  //首先 @sender
                chain.append(at);
                String hbs = format.replaceAll("&@sender", "");

                chain.append(new PlainText(hbs));
                group.sendMessage(chain.build());

            } else {
                for (int i = 0; i < format.length(); i++) {
                    char c = format.charAt(i);
                    if (c == '&') {
                        if (hasString(format, "&@sender")) {
                            chain.append(new PlainText(format.substring(0, i)));
                            chain.append(at);

                            String hbs = format.replaceAll(format.substring(0,i+8), "");
                            chain.append(new PlainText(hbs));
                            group.sendMessage(chain.build());
                            break;
                        } else {
                            System.out.println(" 配置文件信息格式填写错误");
                        }
                    }
                }

            }
        } else {  //没有 &@sender 即不@发送者
            chain.append(new PlainText(format));
            group.sendMessage(chain.build());
        }
    }

    /*  //曾使用代码，已迭代
    public static MessageChainBuilder nonAt(MessageChainBuilder chain, String hbs, String hasBeenSignedExpress, Properties properties) {
        if (hbs.startsWith("$")) {
            if (hasString(hbs, "$hasBeenSignedExpress$")) {
                chain.append(new PlainText(hasBeenSignedExpress));
                chain.append(new PlainText(hbs.replaceAll("$hasBeenSignedExpress$", "")));
            } else {
                chain.append(new PlainText(" 配置文件信息格式填写错误"));
            }

        } else {
            if (hasString(hbs, "$hasBeenSignedExpress$")) {
                for (int i = 0; i < hbs.length(); i++) {
                    char c = hbs.charAt(i);
                    if (c == '$') {
                        chain.append(new PlainText(hbs.substring(0, i)));
                        chain.append(new PlainText(properties.getProperty("hasBeenSignedExpress")));
                        break;
                    }
                }
            }
        }
        return chain;
    }

     */

}
