package xyz.jxmm.minecraft.fish;

import com.google.gson.JsonObject;

public class FishDetermine {
    public static boolean determine(JsonObject duelsJson, String str){
        return duelsJson.has(str);

    }
}
