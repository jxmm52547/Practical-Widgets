package xyz.jxmm.config;

import xyz.jxmm.tools.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class Main {
    static Properties properties = new Properties();
    static File file = new File("./PracticalWidgets/config.properties");

    public static String example(){
        String cfg = "#这里是配置文件,请谨慎填写内容, 所有Value都不需要引号\n\n"

                + "#指令前缀 默认 / , 可以填写 '#' 但是可能因编辑器不同导致此字段显示为注释, 但是不影响, 您任然可以使用 '#' 不要添加引号\n"
                + "prefix = /\n\n"

                + "#token相关\n"
                + "#请前往 https://developer.hypixel.net/dashboard/ 获取自己的API填写  (以后有望取消这个key, 用靖暄的APP API)\n"
                + "ALAPIToken = 123456\n\n"

                + "#今日人品相关\n"
                + "#自定义 今日人品 形容词\n"
                + "0 = 解锁成就顶级非酋\n"
                + "1到20 = \n"
                + "21到40 = \n"
                + "41到60 = \n"
                + "61到80 = \n"
                + "81到99 = \n"
                + "100 = 解锁成就顶级欧皇\n\n"
                + "#HypixelAPI\n"
                + "#请连接至hypixel服务器并输入指令 '/api' 获取您的Key, 如果忘记了您的api 可输入指令 '/api new' 获取新的API\n"
                + "HypixelAPI = \n\n"

                + "#当群成员退出群聊时自定义回复\n"
                + "#格式: $memberNick + $quit + , QQ号: + $memberID, 群昵称: + $memberNick , $quitExpress\n"
                + "#例子: 悠然见南山 退出了群聊, QQ号: 123456, 群昵称: 插件测试BOT, 坏欸\n"
                + "#退出了群聊\n"
                + "quit = 退出了群聊\n"
                + "#自定义形容\n"
                + "quitExpress = \n\n"

                + "#签到提示    采用半自定义语句, 预计未来使用全自定义语句\n"
                + "#这里 & 后接固定结构, 两个 $ 中包含的是自定义语句    可研究下列例子\n"
                + "#已签到\n"
                + "hasBeenSigned = &@sender &hasBeenSignedExpress&\n"
                + "hasBeenSignedExpress = 今日已签到, 不可以重复签到哦~\n"
                + "#解析: @123456 今日已签到, 不可以重复签到哦~\n"
                + "#未签到\n"
                + "sign = &@sender $signExpress$\n"
                + "signExpress = 签到成功~不知道送你什么, 就给你记录一次签到次数吧\n"
                + "#解析: @123456 签到成功~不知道送你什么, 就给你记录一次签到次数吧";
        return cfg;
    }

    public static Properties properties() throws IOException {
        properties.load(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
        return properties;
    }


    public static void main() throws IOException {


        if (!file.exists()){  //如果文件不存在则执行 example() 方法生成配置文件
            FileWriterMethod.fileWriter(file.getPath(), example());
        } else if (!properties().containsKey("100")){    //如果没有100这个key就进行更新
            properties = properties();
            String ALAPIToken = properties.getProperty("ALAPIToken");

            String cfg = "#这里是配置文件,请谨慎填写内容, 所有Value都不需要引号\n\n"

                    + "#指令前缀 默认 / , 可以填写 '#' 但是可能因编辑器不同导致此字段显示为注释, 但是不影响, 您任然可以使用 '#' 不要添加引号\n"
                    + "prefix = /\n\n"

                    + "#token相关\n"
                    + "#ALAPI  请前往www.alapi.cn注册/登录账号后在个人中心找到自己的token密钥\n"
                    + "ALAPIToken = " + ALAPIToken + "\n\n"

                    + "#今日人品相关\n"
                    + "#自定义 今日人品 形容词\n"
                    + "0 = 解锁成就顶级非酋\n"
                    + "1到20 = \n"
                    + "21到40 = \n"
                    + "41到60 = \n"
                    + "61到80 = \n"
                    + "81到99 = \n"
                    + "100 = 解锁成就顶级欧皇\n\n"
                    + "#HypixelAPI\n"
                    + "#请连接至hypixel服务器并输入指令 '/api' 获取您的Key, 如果忘记了您的api 可输入指令 '/api new' 获取新的API\n"
                    + "HypixelAPI = \n\n"

                    + "#当群成员退出群聊时自定义回复\n"
                    + "#格式: $memberNick + $quit + , QQ号: + $memberID + , $quitExpress\n"
                    + "#例子: 終末牽挂 退出了群聊, QQ号: 123456\n"
                    + "#退出了群聊\n"
                    + "quit = 退出了群聊\n"
                    + "#自定义形容\n"
                    + "quitExpress = \n\n"

                    + "#签到提示    采用半自定义语句, 预计未来使用全自定义语句\n"
                    + "#这里 & 后接固定结构, 两个 $ 中包含的是自定义语句    可研究下列例子\n"
                    + "#已签到\n"
                    + "hasBeenSigned = &@sender &hasBeenSignedExpress&\n"
                    + "hasBeenSignedExpress = 今日已签到, 不可以重复签到哦~\n"
                    + "#解析: @123456 今日已签到, 不可以重复签到哦~\n"
                    + "#未签到\n"
                    + "sign = &@sender $signExpress$\n"
                    + "signExpress = 签到成功~不知道送你什么, 就给你记录一次签到次数吧\n"
                    + "#解析: @123456 签到成功~不知道送你什么, 就给你记录一次签到次数吧";

            //重新组建 配置文件
            FileWriterMethod.fileWriter(file.getPath(), cfg);

        } else if (!properties().containsKey("HypixelAPI")){ //如果没有 HypixelApi 这个key就进行更新
            String ALAPIToken = properties.getProperty("ALAPIToken");

            String $0 = properties.getProperty("0");
            String $1$20 = properties.getProperty("1到20");
            String $21$40 = properties.getProperty("21到40");
            String $41$60 = properties.getProperty("41到60");
            String $61$80 = properties.getProperty("61到80");
            String $81$99 = properties.getProperty("81到99");
            String $100 = properties.getProperty("100");
            //这一段写多了, 就当给给下个版本的配置文件做准备吧 (想着删了再写一次太麻烦了就懒得删了注释掉算了
            //V0.3.0版本终于用上了, 载入史册

            String cfg = "#这里是配置文件,请谨慎填写内容\n\n"

                    + "#指令前缀 默认 /\n"
                    + "prefix = /\n\n"

                    + "#token相关\n"
                    + "#ALAPI  请前往www.alapi.cn注册/登录账号后在个人中心找到自己的token密钥\n"
                    + "ALAPIToken = " + ALAPIToken + "\n\n"

                    + "#今日人品相关\n"
                    + "#自定义 今日人品 形容词\n"
                    + "0 = " + $0 + "\n"
                    + "1到20 = " + $1$20 + "\n"
                    + "21到40 = " + $21$40 +  "\n"
                    + "41到60 = " + $41$60 + "\n"
                    + "61到80 = " + $61$80 + "\n"
                    + "81到99 = " + $81$99 + "\n"
                    + "100 = " + $100 + "\n\n"

                    + "#HypixelAPI\n"
                    + "#请连接至hypixel服务器并输入指令 '/api' 获取您的Key, 如果忘记了您的api 可输入指令 '/api new' 获取新的API\n"
                    + "HypixelAPI = \n\n"

                    + "#当群成员退出群聊时自定义回复\n"
                    + "#格式: $memberNick + $quit + , QQ号: + $memberID + , $quitExpress\n"
                    + "#例子: 終末牽挂 退出了群聊, QQ号: 123456\n"
                    + "#退出了群聊\n"
                    + "quit = 退出了群聊\n"
                    + "#自定义形容\n"
                    + "quitExpress = \n\n"

                    + "#签到提示    采用半自定义语句, 预计未来使用全自定义语句\n"
                    + "#这里 & 后接固定结构, 两个 $ 中包含的是自定义语句    可研究下列例子\n"
                    + "#已签到\n"
                    + "hasBeenSigned = &@sender &hasBeenSignedExpress&\n"
                    + "hasBeenSignedExpress = 今日已签到, 不可以重复签到哦~\n"
                    + "#解析: @123456 今日已签到, 不可以重复签到哦~\n"
                    + "#未签到\n"
                    + "sign = &@sender $signExpress$\n"
                    + "signExpress = 签到成功~不知道送你什么, 就给你记录一次签到次数吧\n"
                    + "#解析: @123456 签到成功~不知道送你什么, 就给你记录一次签到次数吧";
            FileWriterMethod.fileWriter(file.getPath(),cfg);
        } else if (!properties().containsKey("prefix")){ //如果没有 prefix 这个key就进行更新
            String ALAPIToken = properties.getProperty("ALAPIToken");

            String $0 = properties.getProperty("0");
            String $1$20 = properties.getProperty("1到20");
            String $21$40 = properties.getProperty("21到40");
            String $41$60 = properties.getProperty("41到60");
            String $61$80 = properties.getProperty("61到80");
            String $81$99 = properties.getProperty("81到99");
            String $100 = properties.getProperty("100");

            String HypixelAPI = properties.getProperty("HypixelAPI");

            String cfg = "#这里是配置文件,请谨慎填写内容\n\n"

                    + "#指令前缀 默认 /\n"
                    + "prefix = /\n\n"

                    + "#token相关\n"
                    + "#ALAPI  请前往www.alapi.cn注册/登录账号后在个人中心找到自己的token密钥\n"
                    + "ALAPIToken = " + ALAPIToken + "\n\n"

                    + "#今日人品相关\n"
                    + "#自定义 今日人品 形容词\n"
                    + "0 = " + $0 + "\n"
                    + "1到20 = " + $1$20 + "\n"
                    + "21到40 = " + $21$40 +  "\n"
                    + "41到60 = " + $41$60 + "\n"
                    + "61到80 = " + $61$80 + "\n"
                    + "81到99 = " + $81$99 + "\n"
                    + "100 = " + $100 + "\n\n"

                    + "#HypixelAPI\n"
                    + "#请连接至hypixel服务器并输入指令 '/api' 获取您的Key, 如果忘记了您的api 可输入指令 '/api new' 获取新的API\n"
                    + "HypixelAPI = " + HypixelAPI + "\n\n"

                    + "#当群成员退出群聊时自定义回复\n"
                    + "#格式: $memberNick + $quit + , QQ号: + $memberID + , $quitExpress\n"
                    + "#例子: 終末牽挂 退出了群聊, QQ号: 123456\n"
                    + "#退出了群聊\n"
                    + "quit = 退出了群聊\n"
                    + "#自定义形容\n"
                    + "quitExpress = \n\n"

                    + "#签到提示    采用半自定义语句, 预计未来使用全自定义语句\n"
                    + "#这里 & 后接固定结构, 两个 $ 中包含的是自定义语句    可研究下列例子\n"
                    + "#已签到\n"
                    + "hasBeenSigned = &@sender &hasBeenSignedExpress&\n"
                    + "hasBeenSignedExpress = 今日已签到, 不可以重复签到哦~\n"
                    + "#解析: @123456 今日已签到, 不可以重复签到哦~\n"
                    + "#未签到\n"
                    + "sign = &@sender $signExpress$\n"
                    + "signExpress = 签到成功~不知道送你什么, 就给你记录一次签到次数吧\n"
                    + "#解析: @123456 签到成功~不知道送你什么, 就给你记录一次签到次数吧";
            FileWriterMethod.fileWriter(file.getPath(),cfg);
        } else if (!properties().containsKey("quit")){ //如果没有 quit 这个key就进行更新
            String prefix = properties.getProperty("prefix");

            String ALAPIToken = properties.getProperty("ALAPIToken");

            String $0 = properties.getProperty("0");
            String $1$20 = properties.getProperty("1到20");
            String $21$40 = properties.getProperty("21到40");
            String $41$60 = properties.getProperty("41到60");
            String $61$80 = properties.getProperty("61到80");
            String $81$99 = properties.getProperty("81到99");
            String $100 = properties.getProperty("100");

            String HypixelAPI = properties.getProperty("HypixelAPI");

            String cfg = "#这里是配置文件,请谨慎填写内容\n\n"

                    + "#指令前缀 默认 /\n"
                    + "prefix = " + prefix + "\n\n"

                    + "#token相关\n"
                    + "#ALAPI  请前往www.alapi.cn注册/登录账号后在个人中心找到自己的token密钥\n"
                    + "ALAPIToken = " + ALAPIToken + "\n\n"

                    + "#今日人品相关\n"
                    + "#自定义 今日人品 形容词\n"
                    + "0 = " + $0 + "\n"
                    + "1到20 = " + $1$20 + "\n"
                    + "21到40 = " + $21$40 +  "\n"
                    + "41到60 = " + $41$60 + "\n"
                    + "61到80 = " + $61$80 + "\n"
                    + "81到99 = " + $81$99 + "\n"
                    + "100 = " + $100 + "\n\n"

                    + "#HypixelAPI\n"
                    + "#请连接至hypixel服务器并输入指令 '/api' 获取您的Key, 如果忘记了您的api 可输入指令 '/api new' 获取新的API\n"
                    + "HypixelAPI = " + HypixelAPI + "\n\n"

                    + "#当群成员退出群聊时自定义回复\n"
                    + "#格式: $memberNick + $quit + , QQ号: + $memberID + , $quitExpress\n"
                    + "#例子: 終末牽挂 退出了群聊, QQ号: 123456\n"
                    + "#退出了群聊\n"
                    + "quit = 退出了群聊\n"
                    + "#自定义形容\n"
                    + "quitExpress = \n\n"

                    + "#签到提示    采用半自定义语句, 预计未来使用全自定义语句\n"
                    + "#这里 & 后接固定结构, 两个 $ 中包含的是自定义语句    可研究下列例子\n"
                    + "#已签到\n"
                    + "hasBeenSigned = &@sender &hasBeenSignedExpress&\n"
                    + "hasBeenSignedExpress = 今日已签到, 不可以重复签到哦~\n"
                    + "#解析: @123456 今日已签到, 不可以重复签到哦~\n"
                    + "#未签到\n"
                    + "sign = &@sender $signExpress$\n"
                    + "signExpress = 签到成功~不知道送你什么, 就给你记录一次签到次数吧\n"
                    + "#解析: @123456 签到成功~不知道送你什么, 就给你记录一次签到次数吧";
            FileWriterMethod.fileWriter(file.getPath(),cfg);
        } else if (!properties().containsKey("sign")){ //如果没有 sign 这个 key 就进行更新
            String prefix = properties.getProperty("prefix");

            String ALAPIToken = properties.getProperty("ALAPIToken");

            String $0 = properties.getProperty("0");
            String $1$20 = properties.getProperty("1到20");
            String $21$40 = properties.getProperty("21到40");
            String $41$60 = properties.getProperty("41到60");
            String $61$80 = properties.getProperty("61到80");
            String $81$99 = properties.getProperty("81到99");
            String $100 = properties.getProperty("100");

            String HypixelAPI = properties.getProperty("HypixelAPI");

            String quit = properties.getProperty("quit");
            String quitExpress = properties.getProperty("quitExpress");

            String cfg = "#这里是配置文件,请谨慎填写内容\n\n"

                    + "#指令前缀 默认 /\n"
                    + "prefix = " + prefix + "\n\n"

                    + "#token相关\n"
                    + "#ALAPI  请前往www.alapi.cn注册/登录账号后在个人中心找到自己的token密钥\n"
                    + "ALAPIToken = " + ALAPIToken + "\n\n"

                    + "#今日人品相关\n"
                    + "#自定义 今日人品 形容词\n"
                    + "0 = " + $0 + "\n"
                    + "1到20 = " + $1$20 + "\n"
                    + "21到40 = " + $21$40 +  "\n"
                    + "41到60 = " + $41$60 + "\n"
                    + "61到80 = " + $61$80 + "\n"
                    + "81到99 = " + $81$99 + "\n"
                    + "100 = " + $100 + "\n\n"

                    + "#HypixelAPI\n"
                    + "#请连接至hypixel服务器并输入指令 '/api' 获取您的Key, 如果忘记了您的api 可输入指令 '/api new' 获取新的API\n"
                    + "HypixelAPI = " + HypixelAPI + "\n\n"

                    + "#当群成员退出群聊时自定义回复\n"
                    + "#格式: $memberNick + $quit + , QQ号: + $memberID + , $quitExpress\n"
                    + "#例子: 終末牽挂 退出了群聊, QQ号: 123456\n"
                    + "#退出了群聊\n"
                    + "quit = " + quit + "\n"
                    + "#自定义形容\n"
                    + "quitExpress = " + quitExpress + "\n\n"

                    + "#签到提示    采用半自定义语句, 预计未来使用全自定义语句\n"
                    + "#这里 & 后接固定结构, 两个 $ 中包含的是自定义语句    可研究下列例子\n"
                    + "#已签到\n"
                    + "hasBeenSigned = &@sender $hasBeenSignedExpress$\n"
                    + "hasBeenSignedExpress = 今日已签到, 不可以重复签到哦~\n"
                    + "#解析: @123456 今日已签到, 不可以重复签到哦~\n"
                    + "#未签到\n"
                    + "sign = &@sender 签到成功~ $signExpress$\n"
                    + "signExpress = 不知道送你什么, 就给你记录一次签到次数吧\n"
                    + "#解析: @123456 签到成功~ 不知道送你什么, 就给你记录一次签到次数吧";
            FileWriterMethod.fileWriter(file.getPath(),cfg);

        } else if (!properties().containsKey("memberQuitSwitch")){//如果没有 memberQuitSwitch 这个 key 就进行更新
            properties = properties();

            String prefix = properties.getProperty("prefix");

            String ALAPIToken = properties.getProperty("ALAPIToken");

            String $0 = properties.getProperty("0");
            String $1$20 = properties.getProperty("1到20");
            String $21$40 = properties.getProperty("21到40");
            String $41$60 = properties.getProperty("41到60");
            String $61$80 = properties.getProperty("61到80");
            String $81$99 = properties.getProperty("81到99");
            String $100 = properties.getProperty("100");

            String HypixelAPI = properties.getProperty("HypixelAPI");

            String quit = properties.getProperty("quit");
            String quitExpress = properties.getProperty("quitExpress");

            String hasBeenSigned = properties.getProperty("hasBeenSigned");
            String hasBeenSignedExpress = properties.getProperty("hasBeenSignedExpress");
            String sign = properties.getProperty("sign");
            String signExpress = properties.getProperty("signExpress");

            String cfg = "#这里是配置文件,请谨慎填写内容\n\n"

                    + "#指令前缀 默认 /\n"
                    + "prefix = " + prefix + "\n\n"

                    + "#token相关\n"
                    + "#ALAPI  请前往www.alapi.cn注册/登录账号后在个人中心找到自己的token密钥\n"
                    + "ALAPIToken = " + ALAPIToken + "\n\n"

                    + "#今日人品相关\n"
                    + "#自定义 今日人品 形容词\n"
                    + "0 = " + $0 + "\n"
                    + "1到20 = " + $1$20 + "\n"
                    + "21到40 = " + $21$40 +  "\n"
                    + "41到60 = " + $41$60 + "\n"
                    + "61到80 = " + $61$80 + "\n"
                    + "81到99 = " + $81$99 + "\n"
                    + "100 = " + $100 + "\n\n"

                    + "#HypixelAPI\n"
                    + "#请前往 https://developer.hypixel.net/dashboard/ 获取自己的API填写  (以后有望取消这个key, 用靖暄的APP API)\n"
                    + "HypixelAPI = " + HypixelAPI + "\n\n"

                    //0.4.3更新内容
                    + "#退群提醒开关  默认开启\n"
                    + "memberQuitSwitch = true\n\n"
                    //结束

                    + "#当群成员退出群聊时自定义回复\n"
                    + "#格式: $memberNick + $quit + , QQ号: + $memberID, 群昵称: + $memberNick , $quitExpress\n"
                    + "#例子: 悠然见南山 退出了群聊, QQ号: 123456, 群昵称: 插件测试BOT, 坏欸\n"
                    + "#退出了群聊\n"
                    + "quit = " + quit + "\n"
                    + "#自定义形容\n"
                    + "quitExpress = " + quitExpress + "\n\n"

                    + "#签到提示    采用半自定义语句, 预计未来使用全自定义语句\n"
                    + "#这里 & 后接固定结构, 两个 $ 中包含的是自定义语句    可研究下列例子\n"
                    + "#已签到\n"
                    + "hasBeenSigned = " + hasBeenSigned + "\n"
                    + "hasBeenSignedExpress = " + hasBeenSignedExpress + "\n"
                    + "#解析: @123456 今日已签到, 不可以重复签到哦~\n"
                    + "#未签到\n"
                    + "sign = " + sign + "\n"
                    + "signExpress = " + signExpress + "\n"
                    + "#解析: @123456 签到成功~ 不知道送你什么, 就给你记录一次签到次数吧";

            FileWriterMethod.fileWriter(file.getPath(),cfg);

        }

        FileWriterMethod.fileWriter(file.getPath(),latest());
        //以上内容为储存用户填写的value
    }

    //保持最新版
    public static String latest() throws IOException {
        properties = properties();

        String prefix = null;
        String ALAPIToken = null;
        String $0 = null;
        String $1$20 = null;
        String $21$40 = null;
        String $41$60 = null;
        String $61$80 = null;
        String $81$99 = null;
        String $100 = null;
        String HypixelAPI = null;
        String quit = null;
        String quitExpress = null;
        String hasBeenSigned = null;
        String hasBeenSignedExpress = null;
        String sign = null;
        String signExpress = null;
        if (properties.containsKey("prefix")){prefix = properties.getProperty("prefix");}

        if (properties.containsKey("ALAPIToken")){ALAPIToken = properties.getProperty("ALAPIToken");}

        if (properties.containsKey("0")){$0 = properties.getProperty("0");}
        if (properties.containsKey("1到20")){$1$20 = properties.getProperty("1到20");}
        if (properties.containsKey("21到40")){$21$40 = properties.getProperty("21到40");}
        if (properties.containsKey("41到60")){$41$60 = properties.getProperty("41到60");}
        if (properties.containsKey("61到80")){$61$80 = properties.getProperty("61到80");}
        if (properties.containsKey("81到99")){$81$99 = properties.getProperty("81到99");}
        if (properties.containsKey("100")){$100 = properties.getProperty("100");}

        if (properties.containsKey("HypixelAPI")) {HypixelAPI = properties.getProperty("HypixelAPI");}

        if (properties.containsKey("quit")){quit = properties.getProperty("quit");}
        if (properties.containsKey("quitExpress")){quitExpress =properties.getProperty("quitExpress");}

        if (properties.containsKey("hasBeenSigned")){hasBeenSigned = properties.getProperty("hasBeenSigned");}
        if (properties.containsKey("hasBeenSignedExpress")){hasBeenSignedExpress = properties.getProperty("hasBeenSignedExpress");}
        if (properties.containsKey("sign")){sign = properties.getProperty("sign");}
        if (properties.containsKey("signExpress")){signExpress = properties.getProperty("signExpress");}

        String cfg = "#这里是配置文件,请谨慎填写内容\n\n"

                + "#指令前缀 默认 /\n"
                + "prefix = " + prefix + "\n\n"

                + "#token相关\n"
                + "#ALAPI  请前往www.alapi.cn注册/登录账号后在个人中心找到自己的token密钥\n"
                + "ALAPIToken = " + ALAPIToken + "\n\n"

                + "#今日人品相关\n"
                + "#自定义 今日人品 形容词\n"
                + "0 = " + $0 + "\n"
                + "1到20 = " + $1$20 + "\n"
                + "21到40 = " + $21$40 +  "\n"
                + "41到60 = " + $41$60 + "\n"
                + "61到80 = " + $61$80 + "\n"
                + "81到99 = " + $81$99 + "\n"
                + "100 = " + $100 + "\n\n"

                + "#HypixelAPI\n"
                + "#请前往 https://developer.hypixel.net/dashboard/ 获取自己的API填写  (以后有望取消这个key, 用靖暄的APP API)\n"
                + "HypixelAPI = " + HypixelAPI + "\n\n"

                //0.4.3更新内容
                + "#退群提醒开关  默认开启\n"
                + "memberQuitSwitch = true\n\n"
                //结束

                + "#当群成员退出群聊时自定义回复\n"
                + "#格式: $memberNick + $quit + , QQ号: + $memberID, 群昵称: + $memberNick , $quitExpress\n"
                + "#例子: 悠然见南山 退出了群聊, QQ号: 123456, 群昵称: 插件测试BOT, 坏欸\n"
                + "#退出了群聊\n"
                + "quit = " + quit + "\n"
                + "#自定义形容\n"
                + "quitExpress = " + quitExpress + "\n\n"

                + "#签到提示    采用半自定义语句, 预计未来使用全自定义语句\n"
                + "#这里 & 后接固定结构, 两个 $ 中包含的是自定义语句    可研究下列例子\n"
                + "#已签到\n"
                + "hasBeenSigned = " + hasBeenSigned + "\n"
                + "hasBeenSignedExpress = " + hasBeenSignedExpress + "\n"
                + "#解析: @123456 今日已签到, 不可以重复签到哦~\n"
                + "#未签到\n"
                + "sign = " + sign + "\n"
                + "signExpress = " + signExpress + "\n"
                + "#解析: @123456 签到成功~ 不知道送你什么, 就给你记录一次签到次数吧";

        return cfg;
    }
}
