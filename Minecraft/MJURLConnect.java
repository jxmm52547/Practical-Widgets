package xyz.jxmm.Minecraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import xyz.jxmm.tools.URLConnect;

public class MJURLConnect {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static String moJangURLConnect(String playerName){
        String connectURL = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
        String result = URLConnect.URLConnect(connectURL);

        JsonObject json = gson.fromJson(result, JsonObject.class);

        return json.get("id").getAsString();
    }
}
