package xyz.jxmm.perm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.jxmm.PracticalWidgets;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static xyz.jxmm.tools.FileReaderMethod.fileReader;

public class GroupMemberPerm {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File groupMemberPerm = new File("./PracticalWidgets/perm/GroupMemberPerm.json");

    public static JsonObject group(Long ID){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(ID.toString(),member(ID));
        return jsonObject;
    }

    public static JsonObject member(Long ID){
        JsonObject member = new JsonObject();

        member.addProperty("jrrp",true);
        member.addProperty("jrrp-top",true);
        member.addProperty("dog",true);
        member.addProperty("song",true);
        member.addProperty("new-object",true);
        member.addProperty("hyp",true);
        member.addProperty("sign",true);

        return member;
    }

    public static void gen(String groupID,Long ID){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("version", PracticalWidgets.version());
        jsonObject.add(groupID,group(ID));
        WriteJson.main(groupMemberPerm,jsonObject);
    }

    public static void creatNew(Long groupID, List<Long> memberID){
        JsonObject group = gson.fromJson(fileReader(groupMemberPerm.getPath()),JsonObject.class);

            if (!group.has("g" + groupID)) {
                group.add("g" + groupID,new JsonObject());
                for (int i = 0;i < memberID.size();i++) {
                    group.get("g"+groupID).getAsJsonObject().add(memberID.get(i).toString(),member(memberID.get(i)));
                }
            } else {
                Set<String> existID = new HashSet<>();
                for (int i = 0; i < group.get("g" + groupID).getAsJsonObject().size(); i++) {
                    existID = group.get("g" + groupID).getAsJsonObject().keySet();
                }
                for (int i = 0; i < memberID.size(); i++) {
                    if (!existID.contains(memberID.get(i).toString())){
                        group.get("g" + groupID).getAsJsonObject().add(memberID.get(i).toString(),member(memberID.get(i)));
                    }
                }
            }

        WriteJson.main(groupMemberPerm,group);
    }

}
