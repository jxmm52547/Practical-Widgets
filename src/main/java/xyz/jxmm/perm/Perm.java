package xyz.jxmm.perm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import xyz.jxmm.PracticalWidgets;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static xyz.jxmm.tools.FileReaderMethod.fileReader;


public class Perm {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File admin = new File("./PracticalWidgets/perm/admin.json");
    static File blackList = new File("./PracticalWidgets/perm/blackList.json");
    static File enableGroup = new File("./PracticalWidgets/perm/EnableGroup.json");
    static File groupMemberPerm = new File("./PracticalWidgets/perm/GroupMemberPerm.json");

    public static void example(){
        if (!admin.exists()){
            Admin.gen(123456L);
        } else {
            update(admin);
        }

        if (!blackList.exists()){
            BlackList.gen("123456");
        } else {
            update(blackList);
        }

        if (!enableGroup.exists()){
            EnableGroup.gen("g123456");
        } else {
            update(enableGroup);
        }

        if (!groupMemberPerm.exists()){
            GroupMemberPerm.gen("g123456",123456L);
        } else {
            update(groupMemberPerm);
        }
    }

    public static void update(File file){
        JsonObject jsonObject = gson.fromJson(fileReader(file.getPath()),JsonObject.class);

        if (!jsonObject.get("version").getAsString().equals(PracticalWidgets.version())){
            // V0.5.0 预留更新入口
        }

    }

    //得到bot所在的所有群
    public static List<Long> group(ContactList<Group> groupContactList){
        JsonArray jsonArray = new Gson().fromJson(Arrays.toString(groupContactList.toArray()), JsonArray.class);
        List<Long> groupID = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            groupID.add(Long.valueOf(jsonArray.get(i).getAsString()
                    .replaceAll("Group\\(","")
                    .replace(")","")));
        }
        return groupID;
    }

    public static void creatNew(ContactList<Group> groupContactList){
        EnableGroup.creatNew(group(groupContactList));

        List<Long> groupMembers = new ArrayList<>();
        for (int i = 0; i < groupContactList.getSize(); i++) {
            Group group = groupContactList.get(group(groupContactList).get(i));
            JsonArray jsonArray = new JsonArray();
            if (group != null) {
                jsonArray = new Gson().fromJson(Arrays.toString(group.getMembers().toArray()), JsonArray.class);
            }

            for (int j = 0; j < jsonArray.size(); j++) {
                groupMembers.add(Long.valueOf(jsonArray.get(j).getAsString()
                        .replaceAll("NormalMember\\(","")
                        .replace(")","")));
            }
            GroupMemberPerm.creatNew(group.getId(),groupMembers);
            groupMembers = new ArrayList<>();
        }


    }

}
