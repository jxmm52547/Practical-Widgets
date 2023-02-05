package xyz.jxmm.tools;

import java.util.HashMap;

public class JrrpMap {
    public static boolean JrrpMap(String msg) {
        HashMap<String, String> map = new HashMap<>();
        map.put("/jrrp", "jrrp");
        map.put(".jrrp", "jrrp");
        map.put("。jrrp", "jrrp");
        map.put("/JRRP", "jrrp");
        map.put(".JRRP", "jrrp");
        map.put("。JRRP", "jrrp");
        map.put("今日人品", "jrrp");
        map.put("jrrp", "jrrp");

        return map.containsKey(msg);
    }

    public  static boolean JrrpTopMap(String msg) {
        HashMap<String, String> map = new HashMap<>();
        map.put("/jrrptop", "jrrptop");
        map.put(".jrrptop", "jrrptop");
        map.put("。jrrptop", "jrrptop");
        map.put("今日人品排行榜", "jrrptop");
        map.put("jrrptop", "jrrptop");

        return  map.containsKey(msg);
    }
}
