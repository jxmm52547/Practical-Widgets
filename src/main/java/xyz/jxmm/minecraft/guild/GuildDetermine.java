package xyz.jxmm.minecraft.guild;

import com.google.gson.JsonObject;

public class GuildDetermine {
    public static Boolean determine(JsonObject json,String str){
        return json.has(str);
    }
}
