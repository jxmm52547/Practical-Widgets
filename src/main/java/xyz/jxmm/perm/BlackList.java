package xyz.jxmm.perm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.PracticalWidgets;

import java.io.File;

import static xyz.jxmm.tools.FileReaderMethod.fileReader;
import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

public class BlackList {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File blackList = new File("./PracticalWidgets/perm/blackList.json");

    public static JsonArray value(String id){
        JsonArray jsonArray = new JsonArray();

        jsonArray.add(id);

        return jsonArray;
    }

    public static MessageChainBuilder at(Long sender){
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        return new MessageChainBuilder().append(at);
    }

    public static void gen(String id){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("version",PracticalWidgets.version());
        jsonObject.add("value",value(id));
        WriteJson.main(blackList,jsonObject);
    }

    public static void blackLIst(String msg, Long sender, Group group){
        if (Determine.admin(group,sender)){
            String type = msg.replaceAll("bl ","");
            if (type.startsWith("add")){
                String value = type.replaceAll("add ","");

                if (value.startsWith("@")){
                    if (value.replaceAll("@","").equals(sender.toString())){
                        group.sendMessage(at(sender).append(new PlainText("无法将自己加入黑名单!")).build());
                    } else if (Long.parseLong(value.replaceAll("@","")) == group.getBot().getId()){
                        group.sendMessage(at(sender).append(new PlainText("无法将bot加入黑名单!")).build());
                    } else {
                        add(value.replaceAll("@",""),sender,group);
                    }

                } else if (value.startsWith("g") && value.replaceAll("g","").matches("^\\d+$")){
                    add(value,sender,group);
                } else if (value.matches("^\\d+$")){
                    if (value.equals(sender.toString())){
                        group.sendMessage(at(sender).append(new PlainText("无法将自己加入黑名单!")).build());
                    } else if (Long.parseLong(value) == group.getBot().getId()){
                        group.sendMessage(at(sender).append(new PlainText("无法将bot加入黑名单!")).build());
                    } else {
                        add(value,sender,group);
                    }

                } else {
                    group.sendMessage(at(sender).append(new PlainText("未知 用户/群 请检查输入!")).build());
                }
            }

            else if (type.startsWith("rm")){
                String value = type.replaceAll("rm ","");

                if (value.startsWith("@")){
                    if (value.replaceAll("@","").equals(sender.toString())){
                        group.sendMessage(at(sender).append(new PlainText("无法将自己移出黑名单!")).build());
                    } else if (Long.parseLong(value.replaceAll("@","")) == group.getBot().getId()){
                        group.sendMessage(at(sender).append(new PlainText("无法将bot移出黑名单!")).build());
                    } else {
                        remove(value.replaceAll("@",""),sender,group);
                    }

                } else if (value.startsWith("g") && value.replaceAll("g","").matches("^\\d+$")){
                    remove(value,sender,group);
                } else if (value.matches("^\\d+$")){
                    if (value.equals(sender.toString())){
                        group.sendMessage(at(sender).append(new PlainText("无法将自己移出黑名单!")).build());
                    } else if (Long.parseLong(value) == group.getBot().getId()){
                        group.sendMessage(at(sender).append(new PlainText("无法将bot移出黑名单!")).build());
                    } else {
                        remove(value,sender,group);
                    }

                } else {
                    group.sendMessage(at(sender).append(new PlainText("未知 用户/群 请检查输入!")).build());
                }
            } else {
                group.sendMessage(at(sender).append(new PlainText("未知指令, 请检查语法或指令完整性!")).build());
            }

        } else {
            group.sendMessage(at(sender).append(new PlainText("你没有管理员权限!")).build());
        }

    }

    public static void add(String value,Long sender,Group group){
        JsonObject jsonObject = gson.fromJson(fileReader(blackList.getPath()), JsonObject.class);
        JsonArray jsonArray = jsonObject.get("value").getAsJsonArray();
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);
        boolean TF = false;

        for (int i = 0; i < jsonArray.size(); i++) {
            if (value.equals(jsonArray.get(i).getAsString())){
                TF = true;
                break;
            }
        }
        if (TF){
            chain.append(new PlainText("该 用户/群 已被列入黑名单!"));
            group.sendMessage(chain.build());
        } else {
            jsonArray.add(value);
            jsonObject.add("value",jsonArray);
            fileWriter(blackList.getPath(), gson.toJson(jsonObject));
            chain.append(new PlainText("已将 " + value + " 列入黑名单"));
            group.sendMessage(chain.build());
        }

    }

    public static void remove(String value,Long sender,Group group){
        JsonObject jsonObject = gson.fromJson(fileReader(blackList.getPath()), JsonObject.class);
        JsonArray jsonArray = jsonObject.get("value").getAsJsonArray();
        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);
        int index = -1;

        for (int i = 0; i < jsonArray.size(); i++) {
            if (value.equals(jsonArray.get(i).getAsString())){
                index = i;
                break;
            }
        }
        if (index == -1){
            chain.append(new PlainText("不存在该 用户/群!"));
            group.sendMessage(chain.build());
        } else {
            jsonArray.remove(index);
            jsonObject.add("value",jsonArray);
            fileWriter(blackList.getPath(), gson.toJson(jsonObject));
            chain.append(new PlainText("已将 " + value + " 移出黑名单"));
            group.sendMessage(chain.build());
        }
    }
}
