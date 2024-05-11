package xyz.jxmm.minecraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import xyz.jxmm.tools.URLConnect;

public class MJURLConnect {
    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static String moJangURLConnect(String playerName,String type){
        String connectURL = "";
        if (type.equals("name")){
            connectURL = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
        } else if (type.equals("uuid")) {
            connectURL = "https://sessionserver.mojang.com/session/minecraft/profile/" + playerName;
        }
        String result = URLConnect.URLConnect(connectURL);

        if (result.isEmpty()){
            return "";
        } else if (result.startsWith("java.net.ConnectException:")) {
            return "Connection timed out";
        } else if (result.startsWith("java.io.FileNotFoundException:")){
            return "FileNotFound";
        } else if (result.startsWith("java.io.IOException:")){
            return "IO";
        } else if (result.startsWith("java.net.SocketException:")){
            return "reset";
        } else if (result.startsWith("javax.net.ssl.SSLHandshakeException:")) {
            return "PKIX";
        } else if (type.equals("name")){
            JsonObject json = gson.fromJson(result, JsonObject.class);
            return json.get("id").getAsString();
        } else if (type.equals("uuid")) {
            JsonObject json = gson.fromJson(result, JsonObject.class);
            return json.get("name").getAsString();
        }
        return "";
    }
}
