package xyz.jxmm.Minecraft;

import com.google.gson.JsonObject;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

public class Error {
    public static Boolean err(JsonObject json, MessageChainBuilder chain, Group group){
        if (!json.get("success").getAsBoolean()){//当返回的 "success" 字段不为 真 时进行解析原因
            String err = json.get("cause").getAsString();
            switch (err) {
                case "Missing one or more fields [...]":
                    chain.append(new PlainText("返回错误 - 400\n"));
                    chain.append(new PlainText("原因: \n"));
                    chain.append(new PlainText("Missing one or more fields [...]"));
                    chain.append(new PlainText("翻译: \n"));
                    chain.append(new PlainText("缺少一个或多个字段[…]"));

                    group.sendMessage(chain.build());
                    break;
                case "Invalid API key":
                    chain.append(new PlainText("返回错误 - 403\n"));
                    chain.append(new PlainText("原因: \n"));
                    chain.append(new PlainText("Invalid API key"));
                    chain.append(new PlainText("翻译: \n"));
                    chain.append(new PlainText("API密钥无效, 请检查配置文件核对您的API"));

                    group.sendMessage(chain.build());
                    break;
                case "Key throttle":
                    chain.append(new PlainText("返回错误 - 429\n"));
                    chain.append(new PlainText("原因: \n"));
                    chain.append(new PlainText("Key throttle"));
                    chain.append(new PlainText("翻译: \n"));
                    chain.append(new PlainText("全局限制: " + json.get("global").getAsBoolean() + "\n"));
                    chain.append(new PlainText("获取此类型信息达到限制(bw,sw)或达到全局限制"));

                    group.sendMessage(chain.build());
                    break;
            }
            return false;
        } else {
            return true;
        }
    }
}
