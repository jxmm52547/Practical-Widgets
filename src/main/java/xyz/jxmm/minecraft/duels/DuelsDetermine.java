package xyz.jxmm.minecraft.duels;

import com.google.gson.JsonObject;

public class DuelsDetermine {

    public static Boolean determine(JsonObject duelsJson,String str){
        return duelsJson.has(str);
    }
}
