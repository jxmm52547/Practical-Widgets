package xyz.jxmm.minecraft.mm;

import com.google.gson.JsonObject;

public class MurderMysteryDetermine {
    public static boolean coins(JsonObject jsonObject){
        return jsonObject.has("coins");
    }

    public static boolean wins(JsonObject jsonObject){
        return jsonObject.has("wins");
    }

    public static boolean games(JsonObject jsonObject){
        return jsonObject.has("games");
    }

    public static boolean kills(JsonObject jsonObject){
        return jsonObject.has("kills");
    }

    public static boolean deaths(JsonObject jsonObject){return jsonObject.has("deaths");}

    public static boolean bow_kills(JsonObject jsonObject){
        return jsonObject.has("bow_kills");
    }

    public static boolean knife_kills(JsonObject jsonObject){
        return jsonObject.has("knife_kills");
    }

    public static boolean thrown_knife_kills(JsonObject jsonObject){
        return jsonObject.has("thrown_knife_kills");
    }

    public static boolean trap_kills(JsonObject jsonObject){
        return jsonObject.has("trap_kills");
    }

    public static boolean detective_wins(JsonObject jsonObject){
        return jsonObject.has("detective_wins");
    }

    public static boolean murderer_wins(JsonObject jsonObject){
        return jsonObject.has("murderer_wins");
    }

    public static boolean was_hero(JsonObject jsonObject) { return jsonObject.has("was_hero"); }

    public static boolean coins_pickedup(JsonObject jsonObject) { return  jsonObject.has("coins_pickedup");}

    //周胜场算法
    public static boolean quests(JsonObject jsonObject){
        return jsonObject.has("quests");
    }
    public static boolean mm_weekly_wins(JsonObject jsonObject){
        return jsonObject.has("mm_weekly_wins");
    }
    public static boolean active(JsonObject jsonObject){
        return jsonObject.has("active");
    }
    public static boolean objectives(JsonObject jsonObject){
        return jsonObject.has("objectives");
    }
    public static boolean weekly_win(JsonObject jsonObject){
        return jsonObject.has("mm_weekly_win");
    }
    public static boolean mm_weekly_murderer_kills(JsonObject jsonObject){
        return jsonObject.has("mm_weekly_murderer_kills");
    }
    public static boolean weekly_kill(JsonObject jsonObject){
        return jsonObject.has("mm_weekly_kills_as_murderer");
    }

    //经典模式
    public static boolean games_MURDER_CLASSIC(JsonObject jsonObject){return jsonObject.has("games_MURDER_CLASSIC");}
    public static boolean wins_MURDER_CLASSIC(JsonObject jsonObject){return jsonObject.has("wins_MURDER_CLASSIC");}
    public static boolean bow_kills_MURDER_CLASSIC(JsonObject jsonObject){return jsonObject.has("bow_kills_MURDER_CLASSIC");}
    public static boolean knife_kills_MURDER_CLASSIC(JsonObject jsonObject){
        return jsonObject.has("knife_kills_MURDER_CLASSIC");
    }
    public static boolean thrown_knife_kills_MURDER_CLASSIC(JsonObject jsonObject){
        return jsonObject.has("thrown_knife_kills_MURDER_CLASSIC");
    }
    public static boolean trap_kills_MURDER_CLASSIC(JsonObject jsonObject){
        return jsonObject.has("trap_kills_MURDER_CLASSIC");
    }
    public static boolean detective_wins_MURDER_CLASSIC(JsonObject jsonObject){
        return jsonObject.has("detective_wins_MURDER_CLASSIC");
    }
    public static boolean murderer_wins_MURDER_CLASSIC(JsonObject jsonObject){
        return jsonObject.has("murderer_wins_MURDER_CLASSIC");
    }
    public static boolean kills_MURDER_CLASSIC(JsonObject jsonObject){
        return jsonObject.has("kills_MURDER_CLASSIC");
    }
    public static boolean deaths_MURDER_CLASSIC(JsonObject jsonObject){
        return jsonObject.has("deaths_MURDER_CLASSIC");
    }
    public static boolean was_hero_MURDER_CLASSIC(JsonObject jsonObject) { return  jsonObject.has("was_hero_MURDER_CLASSIC");}


    //双倍模式
    public static boolean games_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("games_MURDER_DOUBLE_UP");
    }
    public static boolean wins_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("wins_MURDER_DOUBLE_UP");
    }
    public static boolean bow_kills_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("bow_kills_MURDER_DOUBLE_UP");
    }
    public static boolean knife_kills_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("knife_kills_MURDER_DOUBLE_UP");
    }
    public static boolean thrown_knife_kills_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("thrown_knife_kills_MURDER_DOUBLE_UP");
    }
    public static boolean trap_kills_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("trap_kills_MURDER_DOUBLE_UP");
    }
    public static boolean detective_wins_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("detective_wins_MURDER_DOUBLE_UP");
    }
    public static boolean murderer_wins_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("murderer_wins_MURDER_DOUBLE_UP");
    }
    public static boolean kills_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("kills_MURDER_DOUBLE_UP");
    }
    public static boolean deaths_MURDER_DOUBLE_UP(JsonObject jsonObject){
        return jsonObject.has("deaths_MURDER_DOUBLE_UP");
    }
    public static boolean was_hero_MURDER_DOUBLE_UP(JsonObject jsonObject) {
        return jsonObject.has("was_hero_MURDER_DOUBLE_UP");
    }

    //刺客模式
    public static boolean games_MURDER_ASSASSINS(JsonObject jsonObject){
        return jsonObject.has("games_MURDER_ASSASSINS");
    }
    public static boolean wins_MURDER_ASSASSINS(JsonObject jsonObject){
        return jsonObject.has("wins_MURDER_ASSASSINS");
    }
    public static boolean bow_kills_MURDER_ASSASSINS(JsonObject jsonObject){
        return jsonObject.has("bow_kills_MURDER_ASSASSINS");
    }
    public static boolean knife_kills_MURDER_ASSASSINS(JsonObject jsonObject){
        return jsonObject.has("knife_kills_MURDER_ASSASSINS");
    }
    public static boolean thrown_knife_kills_MURDER_ASSASSINS(JsonObject jsonObject){
        return jsonObject.has("thrown_knife_kills_MURDER_ASSASSINS");
    }
    public static boolean trap_kills_MURDER_ASSASSINS(JsonObject jsonObject){
        return jsonObject.has("trap_kills_MURDER_ASSASSINS");
    }
    public static boolean kills_MURDER_ASSASSINS(JsonObject jsonObject){
        return jsonObject.has("kills_MURDER_ASSASSINS");
    }
    public static boolean deaths_MURDER_ASSASSINS(JsonObject jsonObject){
        return jsonObject.has("deaths_MURDER_ASSASSINS");
    }

    //感染模式
    public static boolean games_MURDER_INFECTION(JsonObject jsonObject){
        return jsonObject.has("games_MURDER_INFECTION");
    }
    public static boolean wins_MURDER_INFECTION(JsonObject jsonObject){
        return jsonObject.has("wins_MURDER_INFECTION");
    }
    public static boolean kills_MURDER_INFECTION(JsonObject jsonObject){
        return jsonObject.has("kills_MURDER_INFECTION");
    }
    public static boolean deaths_MURDER_INFECTION(JsonObject jsonObject){
        return jsonObject.has("deaths_MURDER_INFECTION");
    }
    public static boolean kills_as_survivor_MURDER_INFECTION(JsonObject jsonObject){
        return jsonObject.has("kills_as_survivor_MURDER_INFECTION");
    }
    public static boolean bow_kills_MURDER_INFECTION(JsonObject jsonObject){
        return jsonObject.has("bow_kills_MURDER_INFECTION");
    }
    public static boolean kills_as_infected_MURDER_INFECTION(JsonObject jsonObject){
        return jsonObject.has("kills_as_infected_MURDER_INFECTION");
    }
}
