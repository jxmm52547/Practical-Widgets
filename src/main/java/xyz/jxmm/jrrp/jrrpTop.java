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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

public class jrrpTop {
    public static void jrrpTop(Group group, Long sender) {

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        File file = new File("./PracticalWidgets/jrrpTop.json");

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
//        ??????????????????????????????????????? (?????????: groupID)

            //1??????map?????????entryset?????????????????????Entry?????????list???
            List<Map.Entry<Long,Integer>> jrrpNum= new ArrayList<>(top.entrySet());
            List<Map.Entry<String,Integer>> nickStr = new ArrayList<>(nick.entrySet());
            //2?????????Collections.sort(list,comparator)?????????Entry-list??????
            jrrpNum.sort(new MyComparatorValue());
            nickStr.sort(new MyComparatorValue());
            //3?????????????????????Entry-list????????????????????????????????????
            LocalDateTime time = LocalDateTime.now();
            String msg
                    = "???????????????: \n"
                    + "    "
                    + group.getId()
                    + "\n????????????: \n"
                    + "    "
                    + time.getYear() + "???"
                    + time.getMonthValue() + "???"
                    + time.getDayOfMonth() + "???  "
                    + time.getHour() + "???"
                    + time.getMinute() + "???"
                    + time.getSecond() + "???";
            List<String> username = new ArrayList<>();
            for (Map.Entry<String, Integer> entry1:nickStr){
                username.add(entry1.getKey());
            }

            int u = 0;
            int k = 1;
            for(Map.Entry<Long,Integer> entry:jrrpNum){
                msg += ("\n???")
                        +(k)
                        +("???:\n    ")
                        +("?????????: ")
                        +(username.get(u))
                        +("\n    QQ???: ")
                        +(entry.getKey())
                        +("\n    ?????????: ")
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
                    .append(new PlainText("??????????????????????????????\n"))
                    .append(new PlainText("?????????????????????????????????????????????,????????????????????????????????????????????????????????????????????????\n"))
                    .append(new PlainText("?????????????????????????????????????????????"))
                    .build();
            group.sendMessage(chain);
        }




    }
}

class MyComparatorValue implements Comparator<Map.Entry>{//????????????
    public int compare(Map.Entry o1, Map.Entry o2) {
        return ((Integer)o2.getValue()).compareTo((Integer) o1.getValue());
    }
}

class MyComparatorKey implements Comparator<String>{
    public int compare(String o2, String o1) {
        return o1.compareTo(o2);
    }
}