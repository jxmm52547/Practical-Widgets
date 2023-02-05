package xyz.jxmm.jrrp;

import net.mamoe.mirai.contact.Group;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import xyz.jxmm.data.JrrpTop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

public class jrrpTop {
    public static void jrrpTop(Group group) {

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        File file = new File("./PracticalWidgets/jrrpTop.json");

        JsonArray json;

        try {
            json = new Gson().fromJson(new FileReader(file), JsonArray.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, Integer> nick = new HashMap<>();
        int i;
        for (i=0;i<json.size();i++){
            nick.put(json.get(i).getAsJsonObject().get("nick").getAsString(),json.get(i).getAsJsonObject().get("jrrp").getAsInt());
        }

        HashMap<Long, Integer> top = new HashMap<>();
        int n;
        for (n=0;n<json.size();n++){
            top.put(json.get(n).getAsJsonObject().get("user").getAsLong(),json.get(n).getAsJsonObject().get("jrrp").getAsInt());
        }

        HashMap<Long,Integer> groupNum = new HashMap<>();
        int s;
        for (s=0;s<json.size();s++){
            groupNum.put(json.get(s).getAsJsonObject().get("group").getAsLong(),json.get(s).getAsJsonObject().get("jrrp").getAsInt());
        }

        //1：把map转换成entryset，再转换成保存Entry对象的list。
        List<Map.Entry<Long,Integer>> jrrpNum= new ArrayList<>(top.entrySet());
        List<Map.Entry<String,Integer>> nickStr = new ArrayList<>(nick.entrySet());
        List<Map.Entry<Long,Integer>> groupList = new ArrayList<>(groupNum.entrySet());
        //2：调用Collections.sort(list,comparator)方法把Entry-list排序
        jrrpNum.sort(new MyComparator());
        nickStr.sort(new MyComparator());
        groupList.sort(new MyComparator());
        //3：遍历排好序的Entry-list，可得到按顺序输出的结果
        LocalDateTime time = LocalDateTime.now();
        String msg
                = "比对时间: "
                + time.getYear() + "年"
                + time.getMonthValue() + "月"
                + time.getDayOfMonth() + "日  "
                + time.getHour() + "时"
                + time.getMinute() + "分"
                + time.getSecond() + "秒";
       List<String> username = new ArrayList<>();
       List<Long> sendGroupList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry1:nickStr){
            username.add(entry1.getKey());
        }
        for (Map.Entry<Long, Integer> entry1:groupList){
            sendGroupList.add(entry1.getKey());
        }
        int u = 0;
        int k = 1;
        for(Map.Entry<Long,Integer> entry:jrrpNum){
            msg += ("\n第")
                    +(k)
                    +("名:\n    ")
                    +("用户名: ")
                    +(username.get(u))
                    +("\n    QQ号: ")
                    +(entry.getKey())
                    +("\n    人品值: ")
                    +(entry.getValue())
                    +("\n    来自群: ")
                    +(sendGroupList.get(u))
                    +("\n");
            u++;
            k++;
        }
        group.sendMessage(msg);


    }
}

class MyComparator implements Comparator<Map.Entry>{
    public int compare(Map.Entry o1, Map.Entry o2) {
        return ((Integer)o2.getValue()).compareTo((Integer) o1.getValue());
    }
}