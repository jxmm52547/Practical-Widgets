package xyz.jxmm.perm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.io.File;

import static xyz.jxmm.tools.FileReaderMethod.fileReader;
import static xyz.jxmm.tools.FileWriterMethod.fileWriter;

public class PermissionGenerator {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File enableGroup = new File("./PracticalWidgets/perm/EnableGroup.json");
    static File groupMemberPerm = new File("./PracticalWidgets/perm/GroupMemberPerm.json");
    public static void permissionGenerator(String msg, Long sender, Group group){
        JsonObject enableGroupJson = gson.fromJson(fileReader(enableGroup.getPath()),JsonObject.class);
        JsonObject groupMemberPermJson = gson.fromJson(fileReader(groupMemberPerm.getPath()),JsonObject.class);
        MessageChainBuilder chain = new MessageChainBuilder().append(MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]"));

        if (msg.substring(0,msg.indexOf(" ")).matches("^\\d+$")){
            //不指定群号  默认当前群聊
            if (groupMemberPermJson.get("g" + group.getId()).getAsJsonObject().has(msg.substring(0,msg.indexOf(" ")))){
                groupMemberPermJson.add("g" + group.getId(), memberPermControl(msg,groupMemberPermJson, group,group.getId(),null,chain));
                fileWriter(groupMemberPerm.getPath(), gson.toJson(groupMemberPermJson));
            } else {
                chain.append(new PlainText("[memberID] 字段有误, 不存在此群成员!"));
                group.sendMessage(chain.build());
            }

        } else if (msg.startsWith("g") && msg.matches("^g\\d+$")){
            //g开头代表指定群号
            String groupID = msg.substring(0,msg.indexOf(" "));
            msg = msg.replaceAll(groupID + " ","");

            if (msg.substring(0,msg.indexOf(" ")).matches("^\\d+$")){
                //指定群成员  判断是否具有此群及此成员
                String member = msg.substring(0,msg.indexOf(" "));
                msg = msg.replaceAll(member + " ","");
                if (groupMemberPermJson.has(groupID)
                        && groupMemberPermJson.get(groupID).getAsJsonObject().has(member)
                        && groupMemberPermJson.get(groupID).getAsJsonObject().get(member).getAsJsonObject().keySet().contains(msg.substring(0,msg.indexOf(" ")))){
                    groupMemberPermJson.add(groupID, memberPermControl(msg,groupMemberPermJson, group, Long.parseLong(groupID.replaceAll("g","")),member,chain));
                    fileWriter(groupMemberPerm.getPath(), gson.toJson(groupMemberPermJson));
                } else {
                    chain.append(new PlainText("[groupID] 或 [memberID] 或 <type> 字段有误, 请检查指令输入!"));
                    group.sendMessage(chain.build());
                }

            } else if (msg.substring(0,msg.indexOf(" ")).matches("^[A-Za-z-?]+$")
                    && enableGroupJson.has(groupID) && enableGroupJson.get(groupID).getAsJsonObject().keySet().contains(msg.substring(0,msg.indexOf(" ")))) {
                //如果正则匹配为 全字母包含-  并且功能列表存在此字符串  则控制指定群聊功能权限
                enableGroupJson.add(groupID,groupPermControl(msg,enableGroupJson,group,Long.parseLong(groupID.replaceAll("g","")),chain));
                fileWriter(enableGroup.getPath(), gson.toJson(enableGroupJson));
            } else {
                chain.append(new PlainText("[groupID] 或 <type> 字段有误, 请检查指令输入!"));
                group.sendMessage(chain.build());
            }

        } else if (msg.substring(0,msg.indexOf(" ")).matches("^[A-Za-z-?]+$")
                && enableGroupJson.get("g" + group.getId()).getAsJsonObject().keySet().contains(msg.substring(0,msg.indexOf(" ")))){
                //正则匹配, 不包含群号, 默认控制当前群聊功能权限
            enableGroupJson.add("g" + group.getId(),groupPermControl(msg,enableGroupJson,group,null,chain));
            fileWriter(enableGroup.getPath(), gson.toJson(enableGroupJson));
        } else {
            chain.append(new PlainText("<type> 字段有误, 请检查指令输入!"));
            group.sendMessage(chain.build());
        }
    }

    public static JsonObject memberPermControl(String msg, JsonObject groupMemberPermJson, Group group,Long groupID,String member, MessageChainBuilder chain){
        if (member == null){
            member = msg.substring(0, msg.indexOf(" "));
            msg = msg.replaceAll(member + " ","");
        }
        JsonObject groupObject = groupMemberPermJson.get("g" + groupID).getAsJsonObject();
        JsonObject memberPermJson =groupObject.get(member).getAsJsonObject();
        String type = msg.substring(0,msg.indexOf(" "));
        msg = msg.replaceAll(type + " ","");

        groupObject.add(member, memberTypeControl(msg,type,memberPermJson,Long.parseLong(member),group,chain,groupID));
        return groupObject;
    }

    public static JsonObject groupPermControl(String msg, JsonObject enableGroupJson, Group group, Long groupID, MessageChainBuilder chain){
        if (groupID == null){
            groupID = group.getId();
        }
        JsonObject groupObject = enableGroupJson.get("g" + groupID).getAsJsonObject();
        String type = msg.substring(0,msg.indexOf(" "));
        msg = msg.replaceAll(type + " ","");

        switch (msg){
            case "true":
                groupObject.addProperty(type,true);
                chain.append(new PlainText("已开启群 " + groupID + " " + type + " 功能!"));
                group.sendMessage(chain.build());

                return groupObject;
            case "false":
                groupObject.addProperty(type,false);
                chain.append(new PlainText("已关闭群 " + groupID + " " + type + " 功能!"));
                group.sendMessage(chain.build());

                return groupObject;
            default:
                chain.append(new PlainText("控制语句有误, 请输入 true 或 false !"));
                group.sendMessage(chain.build());

                return groupObject;
        }
    }

    public static JsonObject memberTypeControl(String msg, String type, JsonObject memberPermJson, Long member, Group group, MessageChainBuilder chain, Long groupID){
        switch (msg){
            case "true":
                memberPermJson.addProperty(type,true);
                chain.append(new PlainText("已将群 " + groupID + " 中\n"));
                chain.append(MiraiCode.deserializeMiraiCode("[mirai:at:" + member + "]"));
                chain.append(new PlainText(" 的 " + type + " 权限开启!"));
                group.sendMessage(chain.build());

                return memberPermJson;
            case "false":
                memberPermJson.addProperty(type,false);
                chain.append(new PlainText("已将群 " + groupID + " 中\n"));
                chain.append(MiraiCode.deserializeMiraiCode("[mirai:at:" + member + "]"));
                chain.append(new PlainText(" 的 " + type + " 权限关闭!"));
                group.sendMessage(chain.build());

                return memberPermJson;
            default:
                chain.append(new PlainText("控制语句有误, 请输入 true 或 false !"));
                group.sendMessage(chain.build());

                return memberPermJson;
        }

    }
}
