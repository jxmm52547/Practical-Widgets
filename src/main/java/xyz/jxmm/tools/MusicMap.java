package xyz.jxmm.tools;

import java.util.HashMap;

public class MusicMap {
    public static String musicMap(String msg){
        HashMap<String, String> map = new HashMap<>();
        map.put("网易", "netease");
        map.put("QQ", "qq");
        map.put("qq","qq");

        return map.get(msg);
    }
}
