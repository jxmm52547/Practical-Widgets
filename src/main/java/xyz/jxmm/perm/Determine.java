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

import java.io.File;

import static xyz.jxmm.tools.FileReaderMethod.fileReader;

public class Determine {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File enableGroup = new File("./PracticalWidgets/perm/EnableGroup.json");
    static File groupMemberPerm = new File("./PracticalWidgets/perm/GroupMemberPerm.json");
    static File admin = new File("./PracticalWidgets/perm/admin.json");
    static File blackList = new File("./PracticalWidgets/perm/blackList.json");

    public static Boolean main(Long sender, Group group,String type){
        JsonObject enableGroupJson = gson.fromJson(fileReader(enableGroup.getPath()), JsonObject.class);
        JsonObject groupMemberPermJson = gson.fromJson(fileReader(groupMemberPerm.getPath()), JsonObject.class);

        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");
        MessageChainBuilder chain = new MessageChainBuilder().append(at);

        if (!blackListUserTf(sender)){
            chain.append(new PlainText("您已被管理员列入黑名单!"));
            group.sendMessage(chain.build());
            return false;
        } else if (!blackListGroupTf(group)){
            chain.append(new PlainText("此群已被管理员列入黑名单!"));
            group.sendMessage(chain.build());
            return false;
        } else {
            if (enableGroupJson.get("g" + group.getId()).getAsJsonObject().get(type).getAsBoolean()){
                if (groupMemberPermJson.get("g" + group.getId()).getAsJsonObject().get(sender.toString()).getAsJsonObject().get(type).getAsBoolean()){
                    return true;
                } else {
                    chain.append(new PlainText("您没有使用此指令的权限!"));
                    group.sendMessage(chain.build());
                    return false;
                }

            } else {
                chain.append(new PlainText("群未启用此功能!"));
                group.sendMessage(chain.build());
                return false;
            }
        }

    }

    public static Boolean blackListUserTf(Long sender){
        JsonArray blackListJson = gson.fromJson(fileReader(blackList.getPath()), JsonObject.class).get("value").getAsJsonArray();
        for (int i = 0; i < blackListJson.size(); i++) {
            if (sender.toString().equals(blackListJson.get(i).getAsString())){
                return false;
            }
        }
        return true;
    }
    public static Boolean blackListGroupTf(Group group){
        JsonArray blackListJson = gson.fromJson(fileReader(blackList.getPath()), JsonObject.class).get("value").getAsJsonArray();
        for (int i = 0; i < blackListJson.size(); i++) {
            if (blackListJson.get(i).getAsString().startsWith("g")){
                if (blackListJson.get(i).getAsString().replaceAll("g","").equals(String.valueOf(group.getId()))){
                    return false;
                }
            }
        }
        return true;
    }

    public static Boolean admin(Group group,Long sender){
        JsonArray adminJson = gson.fromJson(fileReader(admin.getPath()), JsonObject.class).get("value").getAsJsonArray();

        boolean TF = false;
        for (int i = 0; i < adminJson.size(); i++) {
            if (sender == adminJson.get(i).getAsLong()){
                TF = true;
            }
        }
        return TF;
    }

    public static Boolean register(Group group){
        return gson.fromJson(fileReader(enableGroup.getPath()), JsonObject.class).get("g" + group.getId())
                .getAsJsonObject().get("enable-register").getAsBoolean();
    }
}
