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

//        TreeSet<Long> treeSet=new TreeSet<>(top.keySet());   //借助treeset生成有序的key
//        Iterator iterator=treeSet.iterator();
//        HashMap<Long,Integer> helpmap=new LinkedHashMap<>();  //再利用linkedhashmap生成有序map
//        while (iterator.hasNext()){
//            int key=(int) iterator.next();
//            Integer value=top.get(key);
//            helpmap.put((long) key,value);
//        }

        //1：把map转换成entryset，再转换成保存Entry对象的list。
        List<Map.Entry<Long,Integer>> entrys= new ArrayList<>(top.entrySet());
        List<Map.Entry<String,Integer>> entries = new ArrayList<>(nick.entrySet());
        //2：调用Collections.sort(list,comparator)方法把Entry-list排序
        entrys.sort(new MyComparator());
        entries.sort(new MyComparator());
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
        for (Map.Entry<String, Integer> entry1:entries){
            username.add(entry1.getKey());
        }
        int u = 0;
        int k = 1;
        for(Map.Entry<Long,Integer> entry:entrys){
//            group.sendMessage(entry.getKey().toString() + ',' + entry.getValue().toString());
            msg += ("\n第")
                    +(k)
                    +("名:\n    ")
                    +("用户名: ")
                    +(username.get(u))
                    +("\n    QQ号: ")
                    +(entry.getKey())
                    +("\n    人品值: ")
                    +(entry.getValue())
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