package xyz.jxmm.minecraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import xyz.jxmm.tools.URLConnect;

public class MJURLConnect {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static String moJangURLConnect(String playerName){
        String connectURL = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
        String result = URLConnect.URLConnect(connectURL);

        if (result.equals("")){
            return "Connection timed out";
        } else if (result.equals("java.net.ConnectException: Connection timed out: connect")) {
            return "Connection timed out";
        } else {
            JsonObject json = gson.fromJson(result, JsonObject.class);
            return json.get("id").getAsString();
        }
    }
}
