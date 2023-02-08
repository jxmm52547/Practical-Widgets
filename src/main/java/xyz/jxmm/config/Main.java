package xyz.jxmm.config;

import xyz.jxmm.tools.*;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Main {
    static Properties properties = new Properties();
    static File file = new File("./PracticalWidgets/config.properties");

    public static String example(){
        String cfg = "#这里是配置文件,请谨慎填写内容\n\n"
                + "#token相关\n"
                + "#ALAPI  请前往www.alapi.cn注册账号后在个人中心找到自己的token密钥\n"
                + "ALAPIToken = 123456";
        return cfg;
    }

    public static void main() throws IOException {
        if (!file.exists()){
            FileWriter.fileWriter(file.getPath(), example());
        }
    }
}
