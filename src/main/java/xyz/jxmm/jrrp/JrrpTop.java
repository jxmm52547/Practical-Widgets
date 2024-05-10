package xyz.jxmm.jrrp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import xyz.jxmm.perm.Determine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

public class JrrpTop {
    public static void jrrpTop(Group group, Long sender) {

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        File file = new File("./PracticalWidgets/JrrpTop.json");

        JsonObject json;

        MessageChain at = MiraiCode.deserializeMiraiCode("[mirai:at:" + sender + "]");

        try {
            json = new Gson().fromJson(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8), JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonArray groupValue;
        if (json.has(String.valueOf(group.getId()))){
            groupValue = json.get(String.valueOf(group.getId())).getAsJsonArray();

            HashMap<String, Integer> nick = new HashMap<>();
            int i;
            for (i=0;i<groupValue.size();i++){
                nick.put(groupValue.get(i).getAsJsonObject().get("nick").getAsString(),groupValue.get(i).getAsJsonObject().get("jrrp").getAsInt());
            }

            HashMap<Long, Integer> top = new HashMap<>();
            int n;
            for (n=0;n<groupValue.size();n++){
                top.put(groupValue.get(n).getAsJsonObject().get("user").getAsLong(),groupValue.get(n).getAsJsonObject().get("jrrp").getAsInt());
            }

//        Map<String,String> groupNum = new TreeMap<>(new MyComparatorKey());
//        int s;
//        for (s=0;s<json.size();s++){
//            groupNum.put(json.get(s).getAsJsonObject().get("jrrp").getAsString(),json.get(s).getAsJsonObject().get("group").getAsString());
//        }
//        List<String> userGroup = new ArrayList<>();
//        Set<String> keySet=groupNum.keySet();
//        Iterator it=keySet.iterator();
//        while (it.hasNext()) {
//            String next = (String)it.next();
//            userGroup.add(groupNum.get(next));
//        }
//        这是上个版本的显示群号相关 (来自群: groupID)

            //1：把map转换成entryset，再转换成保存Entry对象的list。
            List<Map.Entry<Long,Integer>> jrrpNum= new ArrayList<>(top.entrySet());
            List<Map.Entry<String,Integer>> nickStr = new ArrayList<>(nick.entrySet());
            //2：调用Collections.sort(list,comparator)方法把Entry-list排序
            jrrpNum.sort(new MyComparatorValue());
            nickStr.sort(new MyComparatorValue());
            //3：遍历排好序的Entry-list，可得到按顺序输出的结果
            LocalDateTime time = LocalDateTime.now();
            String msg
                    = "排行群聊号: \n"
                    + "    "
                    + group.getId()
                    + "\n排行时间: \n"
                    + "    "
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
                        +("\n");
                u++;
                k++;
            }

            ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
            builder.add(group.getBot().getId(),group.getBot().getNick(),new PlainText(msg));

            group.sendMessage(builder.build());
        } else {
            MessageChain chain = new MessageChainBuilder()
                    .append(at)
                    .append(new PlainText("此群没有排行榜信息喔\n"))
                    .append(new PlainText("有可能你在其他群查看的今日人品,这样将不会记录为此群，将会记录为查看今日人品的群\n"))
                    .append(new PlainText("也有可能今天还没有人查看人品值"))
                    .build();
            group.sendMessage(chain);
        }




    }

    public static void perm(Long sender,Group group){
        if (Determine.main(sender,group,"jrrp-top")){
            jrrpTop(group, sender);
        }
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