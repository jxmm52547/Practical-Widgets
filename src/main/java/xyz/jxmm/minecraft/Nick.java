package xyz.jxmm.minecraft;

import com.google.gson.JsonObject;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import xyz.jxmm.minecraft.player.PlayerDetermine;

public class Nick {
    @NotNull
    public static String nick(JsonObject json){
        if (PlayerDetermine.rank(json)){
            String rank = json.get("newPackageRank").getAsString();
            boolean rankPlus = json.has("monthlyPackageRank");
            switch (rank){
                case "MVP_PLUS":
                    if (rankPlus && json.get("monthlyPackageRank").getAsString().equals("SUPERSTAR")){
                       return "【MVP++】";
                    }
                    else{
                        return "【MVP+】";
                    }
                case "MVP":
                    return "【MVP】";
                case "VIP_PLUS":
                    return "【VIP+】";
                case "VIP":
                    return "【VIP】";
            }
        }
        return "";
    }
}
