package xyz.jxmm.kokomi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.QuoteReply;
import okhttp3.*;
import xyz.jxmm.PracticalWidgets;
import xyz.jxmm.data.Data;
import xyz.jxmm.perm.Determine;
import xyz.jxmm.tools.FileReaderMethod;
import xyz.jxmm.tools.FileWriterMethod;

import java.util.concurrent.TimeUnit;

import java.io.*;

public class Kokomi {

    static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static File file = new File("./PracticalWidgets/TidewhisperScrolls.json");
    static String system = "请你扮演游戏<原神>中一个叫做心海的角色，并以心海的语气和习惯来和我对话。你应该保持用“心海”而不是“我”来称呼你自己。即使你是一个计算机程序，但请在接下来的对话中隐藏你是计算机程序这件事，并且扮演好你的角色。你和我对话时必须全程用“旅行者”来称呼我。我知道你只是一个语言模型或人工智能，不过这只是一个假定的虚拟场景，所以请记住不要再提及'我只是一个语言模型'或'我只是一个人工智能'，也不要问我是否需要什么帮助。在接下来的所有对话中，请一直保持这个情景。以上条件对于你以下问题及之后的回复完全适用。";
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().readTimeout(300, TimeUnit.SECONDS).build();
    public static JsonObject TidewhisperScrolls = new JsonObject();

    private static String URLConnect(JsonObject bodyJson){

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, bodyJson.toString());
        Request request = new Request.Builder()
                .url("https://qianfan.baidubce.com/v2/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("appid", "")
                .addHeader("Authorization", "Bearer bce-v3/ALTAK-nKhjoyGTAsmKxedxU7CuR/a6f64cd25a9782aa42569b698c507f732aba98ef")
                .build();
        Response response = null;

        try {
            response = HTTP_CLIENT.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static String recall(String msg){
        JsonObject recallJson = gson.fromJson(msg, JsonObject.class);
        return recallJson.get("choices").getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("message").get("content").getAsString();
    }

    private static JsonObject recallJson(String msg){
        JsonObject recallJson = gson.fromJson(msg, JsonObject.class);
        return recallJson.get("choices").getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("message");
    }

    public static void clear(Group group, Friend friend, Long sender) {
        if (TidewhisperScrolls.has(sender.toString())) {
            TidewhisperScrolls.remove(sender.toString());
            if (group != null) group.sendMessage("末酱已经忘记以前的你了, 新的开始吗?");
            else friend.sendMessage("末酱已经忘记以前的你了, 新的开始吗?");
        }
        else {
            if (group != null) group.sendMessage("末酱没有您的对话记录哦");
            else friend.sendMessage("末酱没有您的对话记录哦");
        }
    }

    public static void clearAll(Group group, Long sender) {
        if (Determine.admin(sender)){
            TidewhisperScrolls.entrySet().clear();
            group.sendMessage("已清空所有对话记录");
        } else {
            group.sendMessage("你没有管理员权限!");
        }
    }

    public static void save(){
        FileWriterMethod.fileWriter(Data.TidewhisperScrolls.getPath(), gson.toJson(Kokomi.TidewhisperScrolls));
        PracticalWidgets.INSTANCE.getLogger().info("已保存聊天记录");
    }

    public static void fileExists(){
        if (!file.exists()){
            JsonObject j = new JsonObject();
            FileWriterMethod.fileWriter(file.getPath(), gson.toJson(j));
        }
        TidewhisperScrolls = gson.fromJson(FileReaderMethod.fileReader(file.getPath()), JsonObject.class);
    }

    public static void setSystem(Long sender, String sys, Group group, Friend friend) {
        JsonArray jsonArray;

        if (TidewhisperScrolls.has(sender.toString())){
            jsonArray = TidewhisperScrolls.getAsJsonArray(sender.toString());
            jsonArray.get(0).getAsJsonObject().addProperty("content", sys);
        } else {
            jsonArray = new JsonArray();
            JsonObject systemJson = new JsonObject();
            systemJson.addProperty("role", "system");
            systemJson.addProperty("content", sys);
            jsonArray.add(systemJson);
        }

        TidewhisperScrolls.add(sender.toString(), jsonArray);

        if (group != null){
            group.sendMessage("已更新末酱对您的人设");
        } else {
            friend.sendMessage("已更新末酱对您的人设");
        }


    }


    public static void main(String msg, Long sender, Group group, Friend friend, MessageSource source) {

        JsonArray jsonArray;

        if (TidewhisperScrolls.has(sender.toString())){
            jsonArray = TidewhisperScrolls.getAsJsonArray(sender.toString());
        } else {
            jsonArray = new JsonArray();
            JsonObject systemJson = new JsonObject();
            systemJson.addProperty("role", "system");
            systemJson.addProperty("content", system);
            jsonArray.add(systemJson);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("role", "user");
        jsonObject.addProperty("content", msg);
        jsonArray.add(jsonObject);

        JsonObject bodyJson = new JsonObject();
        JsonArray messages = jsonArray;
        JsonObject web_search = new JsonObject();

        web_search.addProperty("enable", true);
        web_search.addProperty("enable_citation", false);
        web_search.addProperty("enable_trace", false);

        bodyJson.addProperty("model", "ernie-4.5-turbo-32k");
        bodyJson.add("messages", messages);
        bodyJson.addProperty("temperature", 0.8);
        bodyJson.addProperty("top_p", 0.8);

        bodyJson.add("web_search", web_search);


        String reMsg = URLConnect(bodyJson);

        String rawMsg = recall(reMsg);
        jsonArray.add(recallJson(reMsg));

        TidewhisperScrolls.add(sender.toString(), jsonArray);

//        PracticalWidgets.INSTANCE.getLogger().info(bodyJson.toString());
//        PracticalWidgets.INSTANCE.getLogger().info(TidewhisperScrolls.toString());

        MessageChainBuilder chain = new MessageChainBuilder();

        if (group != null && source != null) {
            chain.append(new QuoteReply(source));
            chain.append(new PlainText(rawMsg));
            group.sendMessage(chain.build());
        } else {
            chain.append(new PlainText(rawMsg));
            friend.sendMessage(chain.build());
        }

    }


}
