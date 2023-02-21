package xyz.jxmm.Minecraft.sw;

import com.google.gson.JsonObject;

public class SkyWarsDetermine {

    public static Boolean games_played(JsonObject swJson){
        return swJson.has("games_played_skywars") && swJson.has("levelFormatted");
    }
    public static Boolean skywars_experience(JsonObject swJson){
        return swJson.has("skywars_experience") && swJson.has("coins");
    }
    public static Boolean win_streak(JsonObject swJson){
        return swJson.has("win_streak") && swJson.has("losses");
    }
    public static Boolean kills(JsonObject swJson){
        return swJson.has("kills") && swJson.has("assists") && swJson.has("deaths");
    }
    public static Boolean melee_kills(JsonObject swJson){
        return swJson.has("melee_kills") && swJson.has("bow_kills") && swJson.has("void_kills");
    }
    public static Boolean arrows_shot(JsonObject swJson){
        return swJson.has("arrows_shot") && swJson.has("arrows_hit");
    }
    public static Boolean chests_opened(JsonObject swJson){
        return swJson.has("chests_opened");
    }
    public static Boolean enderpearls_thrown(JsonObject swJson){
        return swJson.has("enderpearls_thrown");
    }
    public static Boolean souls_gathered(JsonObject swJson){
        return swJson.has("souls_gathered");
    }
}
