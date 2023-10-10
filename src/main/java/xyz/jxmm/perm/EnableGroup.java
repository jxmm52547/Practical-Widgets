package xyz.jxmm.perm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import xyz.jxmm.PracticalWidgets;

import java.io.File;
import java.util.List;

import static xyz.jxmm.tools.FileReaderMethod.fileReader;

public class EnableGroup {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File enableGroup = new File("./PracticalWidgets/perm/EnableGroup.json");

    public static JsonObject group() {
        JsonObject group = new JsonObject();

        group.addProperty("enable-register",true);
        group.addProperty("jrrp",true);
        group.addProperty("jrrp-top",true);
        group.addProperty("dog",true);
        group.addProperty("song",true);
        group.addProperty("new-object",true);
        group.addProperty("hyp",true);
        group.addProperty("sign",true);
        group.addProperty("groupMemberQuit",true);

        return group;
    }

    public static void gen(String groupID){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("version",PracticalWidgets.version());
        jsonObject.add(groupID,group());

        WriteJson.main(enableGroup,jsonObject);
    }

    public static void creatNew(List<Long> groupID){
        JsonObject group = gson.fromJson(fileReader(enableGroup.getPath()), JsonObject.class);

        for (Long l : groupID) {
            if (!group.has("g" + l)) {
                group.add("g" + l, group());
            }
        }

        WriteJson.main(enableGroup,group);

    }

}
