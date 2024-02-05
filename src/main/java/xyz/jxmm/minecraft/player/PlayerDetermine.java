package xyz.jxmm.minecraft.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PlayerDetermine {
    public static Boolean online(JsonObject online){return online.has("session");}
    public static Boolean mode(JsonObject session){return  session.has("mode");}
    public static Boolean map(JsonObject session){return  session.has("map");}
    public static Boolean rank(JsonObject playerJson){return playerJson.has("newPackageRank");}

    public static Boolean giftingMeta(JsonObject playerJson){
        return playerJson.has("giftingMeta");
    }
    public static Boolean ranksGiven(JsonObject giftingMeta){
        return giftingMeta.has("ranksGiven");
    }
    public static Boolean firstLogin(JsonObject playerJson){
        return playerJson.has("firstLogin");
    }

    public static Boolean lastLogin(JsonObject playJson){
        return playJson.has("lastLogin");
    }

    public static Boolean lastLogout(JsonObject playerJson){
        return playerJson.has("lastLogout");
    }

    public static Boolean userLanguage(JsonObject playerJson){
        return playerJson.has("userLanguage");
    }

    public static Boolean guild(JsonObject guild){
        return !guild.get("guild").isJsonNull();
    }

    public static Boolean networkExp(JsonObject playerJson){
        return playerJson.has("networkExp");
    }

    /*
    public static Boolean arcade_arcade_winner(JsonObject arcade,JsonObject achievements){
        return arcade.has("coins") && achievements.has("arcade_arcade_winner");
    }

    public static Boolean bedWars(JsonObject achievements, JsonObject bwJson){
        return achievements.has("bedwars_level") && bwJson.has("coins") && bwJson.has("winstreak");
    }

    public static Boolean skyWars(JsonObject swJson){
        return swJson.has("levelFormatted") && swJson.has("coins") && swJson.has("win_streak");
    }

    public static Boolean TNTGames(JsonObject TNTGames){
        return TNTGames.has("coins") && TNTGames.has("wins") && TNTGames.has("winstreak");
    }
    V0.4.2版本更新注释
     */

    public static Boolean recentGames(JsonObject recentGames){
        return recentGames.get("games").getAsJsonArray().size() > 0;
    }

    public static Boolean achievementPoints(JsonObject playerJson){
        return playerJson.has("achievementPoints");
    }

    public static Boolean karma(JsonObject playerJson){
        return playerJson.has("karma");
    }

    /*
    public static Boolean bwJson(JsonObject playerJson){
        return playerJson.get("stats").getAsJsonObject().has("Bedwars");
    }
    V0.4.2版本更新注释
     */
}
