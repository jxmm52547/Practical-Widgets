package xyz.jxmm.jrrp;

import com.sun.tools.javac.jvm.Code;
import net.mamoe.mirai.contact.Group;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.*;

public class jrrpTop {
    public static void jrrpTop(Group group, Long sender) {

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

        Map<String,String> groupNum = new TreeMap<>(new MyComparatorKey());
        int s;
        for (s=0;s<json.size();s++){
            groupNum.put(json.get(s).getAsJsonObject().get("jrrp").getAsString(),json.get(s).getAsJsonObject().get("group").getAsString());
        }
        List<String> userGroup = new ArrayList<>();
        Set<String> keySet=groupNum.keySet();
        Iterator it=keySet.iterator();
        while (it.hasNext()) {
            String next = (String)it.next();
            userGroup.add(groupNum.get(next));
        }

        //1：把map转换成entryset，再转换成保存Entry对象的list。
        List<Map.Entry<Long,Integer>> jrrpNum= new ArrayList<>(top.entrySet());
        List<Map.Entry<String,Integer>> nickStr = new ArrayList<>(nick.entrySet());
        //2：调用Collections.sort(list,comparator)方法把Entry-list排序
        jrrpNum.sort(new MyComparatorValue());
        nickStr.sort(new MyComparatorValue());
        //3：遍历排好序的Entry-list，可得到按顺序输出的结果
        LocalDateTime time = LocalDateTime.now();
        String msg
                = "排行时间: "
                + time.getYear() + "年"
                + time.getMonthValue() + "月"
                + time.getDayOfMonth() + "日  "
                + time.getHour() + "时"
                + time.getMinute() + "分"
                + time.getSecond() + "秒";
       List<String> username = new ArrayList<>();
        for (Map.Entry<String, Integer> entry1:nickStr){
            username.add(entry1.getKey());
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
                    +(userGroup.get(u))
                    +("\n");
            u++;
            k++;
        }

        ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
        builder.add(2931519915L,"末酱",new PlainText(msg));

        group.sendMessage(builder.build());


    }
}

class MyComparatorValue implements Comparator<Map.Entry>{//按值排序
    public int compare(Map.Entry o1, Map.Entry o2) {
        return ((Integer)o2.getValue()).compareTo((Integer) o1.getValue());
    }
}

class MyComparatorKey implements Comparator<String>{
    public int compare(String o2, String o1) {
        return o1.compareTo(o2);
    }
}