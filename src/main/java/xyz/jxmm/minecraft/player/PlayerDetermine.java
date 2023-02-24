package xyz.jxmm.minecraft.player;

import com.google.gson.JsonObject;

public class PlayerDetermine {
    public static Boolean firstLogin(JsonObject playerJson){
        return playerJson.has("firstLogin");
    }

    public static Boolean lastLogin(JsonObject playJson){
        return playJson.has("lastLogin");
    }

    public static Boolean lastLogout(JsonObject playerJson){
        return playerJson.has("lastLogout");
    }

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
}
