package xyz.jxmm.minecraft.bw;

import com.google.gson.JsonObject;

public class BedWarsDetermine {
    public static Boolean games_played_bedwars(JsonObject bwJson){
        return bwJson.has("games_played_bedwars") && bwJson.has("Experience");
    }
    public static Boolean beds_broken_bedwars(JsonObject bwJson){
        return bwJson.has("beds_broken_bedwars") && bwJson.has("beds_lost_bedwars");
    }
    public static Boolean kills_bedwars(JsonObject bwJson){
        return bwJson.has("kills_bedwars") && bwJson.has("deaths_bedwars");
    }
    public static Boolean final_kills_bedwars(JsonObject bwJson){
        return bwJson.has("final_kills_bedwars") && bwJson.has("final_deaths_bedwars");
    }
    public static Boolean wins_bedwars(JsonObject bwJson){
        return bwJson.has("wins_bedwars") && bwJson.has("losses_bedwars");
    }
}
