package xyz.jxmm.dog;

import xyz.jxmm.tools.*;

import net.mamoe.mirai.contact.Group;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class GetValue {

    static Properties properties = new Properties();
    static File file = new File("./PracticalWidgets/config.properties");

    public static String main(Group group) {
        try {
            properties.load(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String ALAPIToken = properties.getProperty("ALAPIToken");
        String TGRJ = "https://v2.alapi.cn/api/dog?format=json&token=" + ALAPIToken;
        return URLConnect.URLConnect(TGRJ);
    }
}
