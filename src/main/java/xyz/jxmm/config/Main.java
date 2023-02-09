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
        String cfg = "#这里是配置文件,请谨慎填写内容\n\n"
                + "#token相关\n"
                + "#ALAPI  请前往www.alapi.cn注册/登录账号后在个人中心找到自己的token密钥\n"
                + "ALAPIToken = 123456\n\n"
                + "#今日人品相关\n"
                + "#自定义 今日人品 形容词\n"
                + "0 = 解锁成就顶级非酋\n"
                + "1to20 = \n"
                + "21to40 = \n"
                + "41to60 = \n"
                + "61to80 = \n"
                + "81to99 = \n"
                + "100 = 解锁成就顶级欧皇";
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

//            String $0 = properties.getProperty("0");
//            String $1$20 = properties.getProperty("1到20");
//            String $21$40 = properties.getProperty("21到40");
//            String $41$60 = properties.getProperty("41到60");
//            String $61$80 = properties.getProperty("61到80");
//            String $81$99 = properties.getProperty("81到99");
//            String $100 = properties.getProperty("100");
            //这一段写多了, 就当给给下个版本的配置文件做准备吧 (想着删了再写一次太麻烦了就懒得删了注释掉算了

            //以上内容为储存用户填写的value

            String cfg = "#这里是配置文件,请谨慎填写内容\n\n"
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
                    + "100 = 解锁成就顶级欧皇";

            //重新组建 配置文件
            FileWriterMethod.fileWriter(file.getPath(), cfg);
        }
    }
}
