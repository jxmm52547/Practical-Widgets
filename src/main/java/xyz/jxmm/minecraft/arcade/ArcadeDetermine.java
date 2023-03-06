package xyz.jxmm.minecraft.arcade;

import com.google.gson.JsonObject;

public class ArcadeDetermine {
//硬币
    public static Boolean coins(JsonObject acdJson){
        return acdJson.has("coins");
    }

//总胜场
    public static Boolean arcade_arcade_winner(JsonObject achievements){
        return achievements.has("arcade_arcade_winner");
    }

//派对游戏
    public static Boolean wins_party(JsonObject acdJson){
        return acdJson.has("wins_party");
    }
    public static Boolean round_wins_party(JsonObject acdJson){
        return acdJson.has("round_wins_party");
    }
    public static Boolean total_stars_party(JsonObject acdJson){
        return acdJson.has("total_stars_party");
    }
    public static Boolean wins_zombies_deadend(JsonObject acdJson){
        return acdJson.has("wins_zombies_deadend");
    }
    public static Boolean headshots_dayone(JsonObject acdJson){
        return acdJson.has("headshots_dayone");
    }

//进击的苦力怕
    public static Boolean crreper_Attack(JsonObject acdJson){
        return acdJson.has("max_wave");
    }

//龙之战


//末影掘战


//农场躲猫猫
    public static Boolean wins_farm_hunt(JsonObject acdJson){
        return acdJson.has("wins_farm_hunt");
    }
    public static Boolean animal_wins_farm_hunt(JsonObject acdJson){
        return acdJson.has("animal_wins_farm_hunt");
    }
    public static Boolean hunter_wins_farm_hunt(JsonObject acdJson){
        return acdJson.has("hunter_wins_farm_hunt");
    }
    public static Boolean kills_farm_hunt(JsonObject acdJson){
        return acdJson.has("kills_farm_hunt");
    }
    public static Boolean animal_kills_farm_hunt(JsonObject acdJson){
        return acdJson.has("animal_kills_farm_hunt");
    }
    public static Boolean hunter_kills_farm_hunt(JsonObject acdJson){
        return acdJson.has("hunter_kills_farm_hunt");
    }
    public static Boolean poop_collected_farm_hunt(JsonObject acdJson){
        return acdJson.has("poop_collected_farm_hunt");
    }

//足球
    public static Boolean wins_soccer(JsonObject acdJson){
        return acdJson.has("wins_soccer");
    }
    public static Boolean goals_soccer(JsonObject acdJson){
        return acdJson.has("goals_soccer");
    }
    public static Boolean powerkicks_soccer(JsonObject acdJson){
        return acdJson.has("powerkicks_soccer");
    }
    public static Boolean kicks_soccer(JsonObject acdJson){
        return acdJson.has("kicks_soccer");
    }

//星际战争


//躲猫猫
    public static Boolean hider_wins_hide_and_seek(JsonObject acdJson){
        return acdJson.has("hider_wins_hide_and_seek");
    }
    public static Boolean seeker_wins_hide_and_seek(JsonObject acdJson){
        return acdJson.has("seeker_wins_hide_and_seek");
    }
    public static Boolean party_pooper_hider_wins_hide_and_seek(JsonObject acdJson){
        return acdJson.has("party_pooper_hider_wins_hide_and_seek");
    }
    public static Boolean party_pooper_seeker_wins_hide_and_seek(JsonObject acdJson){
        return acdJson.has("party_pooper_seeker_wins_hide_and_seek");
    }

//人体打印机
    public static Boolean wins_hole_in_the_wall(JsonObject acdJson){
        return acdJson.has("wins_hole_in_the_wall");
    }
    public static Boolean rounds_hole_in_the_wall(JsonObject acdJson){
        return acdJson.has("rounds_hole_in_the_wall");
    }
    public static Boolean hitw_record_q(JsonObject acdJson){
        return acdJson.has("hitw_record_q");
    }
    public static Boolean hitw_record_f(JsonObject acdJson){
        return acdJson.has("hitw_record_f");
    }

//我说你做
    public static Boolean wins_simon_says(JsonObject acdJson){
        return acdJson.has("wins_simon_says");
    }
    public static Boolean rounds_simon_says(JsonObject acdJson){
        return acdJson.has("rounds_simon_says");
    }
    public static Boolean top_score_simon_says(JsonObject acdJson){
        return acdJson.has("top_score_simon_says");
    }
    public static Boolean round_wins_simon_says(JsonObject acdJson){
        return acdJson.has("round_wins_simon_says");
    }

//迷你战墙
    public static Boolean wins_mini_walls(JsonObject acdJson){
        return acdJson.has("wins_mini_walls");
    }
    public static Boolean final_kills_mini_walls(JsonObject acdJson){
        return acdJson.has("final_kills_mini_walls");
    }
    public static Boolean kills_mini_walls(JsonObject acdJson){
        return acdJson.has("kills_mini_walls");
    }
    public static Boolean deaths_mini_walls(JsonObject acdJson){
        return acdJson.has("deaths_mini_walls");
    }
    public static Boolean arrows_hit_mini_walls(JsonObject acdJson){
        return acdJson.has("arrows_hit_mini_walls");
    }
    public static Boolean arrows_shot_mini_walls(JsonObject acdJson){
        return acdJson.has("arrows_shot_mini_walls");
    }
    public static Boolean wither_kills_mini_walls(JsonObject acdJson){
        return acdJson.has("wither_kills_mini_walls");
    }
    public static Boolean wither_damage_mini_walls(JsonObject acdJson){
        return acdJson.has("wither_damage_mini_walls");
    }


//像素画家
    public static Boolean wins_pixel_painters(JsonObject acdJson){
        return acdJson.has("wins_pixel_painters");
    }


//Pixel party
    public static Boolean pixel_party(JsonObject acdJson){
        return acdJson.has("pixel_party");
    }
    public static Boolean games_played(JsonObject pixel_party){
        return pixel_party.has("games_played");
    }
    public static Boolean rounds_completed(JsonObject pixel_party){
        return pixel_party.has("rounds_completed");
    }
    public static Boolean wins(JsonObject pixel_party){
        return pixel_party.has("wins");
    }
    public static Boolean wins_normal(JsonObject pixel_party){
        return pixel_party.has("wins_normal");
    }
    public static Boolean rounds_completed_normal(JsonObject pixel_party){
        return pixel_party.has("rounds_completed_normal");
    }
    public static Boolean wins_hyper(JsonObject pixel_party){
        return pixel_party.has("wins_hyper");
    }
    public static Boolean rounds_completed_hyper(JsonObject pixel_party){
        return pixel_party.has("rounds_completed_hyper");
    }
    public static Boolean power_ups_collected(JsonObject pixel_party){
        return pixel_party.has("power_ups_collected");
    }
    public static Boolean power_ups_collected_normal(JsonObject pixel_party){
        return pixel_party.has("power_ups_collected_normal");
    }
    public static Boolean power_ups_collected_hyper(JsonObject pixel_party){
        return pixel_party.has("power_ups_collected_hyper");
    }


//乱棍之战
    public static Boolean wins_throw_out(JsonObject acdJson){
        return acdJson.has("wins_throw_out");
    }
    public static Boolean kills_throw_out(JsonObject acdJson){
        return acdJson.has("kills_throw_out");
    }

//僵尸末日
    public static Boolean wins_zombies(JsonObject acdJson){
        return acdJson.has("wins_zombies");
    }
    public static Boolean total_rounds_survived_zombies(JsonObject acdJson){
        return acdJson.has("total_rounds_survived_zombies");
    }
    public static Boolean best_round_zombies(JsonObject acdJson){
        return acdJson.has("best_round_zombies");
    }
    public static Boolean zombie_kills_zombies(JsonObject acdJson){
        return acdJson.has("zombie_kills_zombies");
    }
    public static Boolean players_revived_zombies(JsonObject acdJson){
        return acdJson.has("players_revived_zombies");
    }
    public static Boolean times_knocked_down_zombies(JsonObject acdJson){
        return acdJson.has("times_knocked_down_zombies");
    }
    public static Boolean deaths_zombies(JsonObject acdJson){
        return acdJson.has("deaths_zombies");
    }
    public static Boolean doors_opened_zombies(JsonObject acdJson){
        return acdJson.has("doors_opened_zombies");
    }
    public static Boolean windows_repaired_zombies(JsonObject acdJson){
        return acdJson.has("windows_repaired_zombies");
    }
}
