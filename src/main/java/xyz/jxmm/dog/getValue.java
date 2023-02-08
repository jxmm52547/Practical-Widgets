package xyz.jxmm.dog;

import net.mamoe.mirai.contact.Group;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class getValue {

    static Properties properties = new Properties();
    static File file = new File("./PracticalWidgets/config.properties");

    public static String main(Group group) {
        URL url;
        BufferedReader in = null;
        try {
            properties.load(Files.newInputStream(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String ALAPIToken = properties.getProperty("ALAPIToken");
        String TGRJ = "https://v2.alapi.cn/api/dog?format=text&token=" + ALAPIToken;
        StringBuilder result = new StringBuilder();
        {
            try {
                if (ALAPIToken.equals("123456")) {
                    result.append("请联系 BOT拥有者 前往配置文件填写Token后重试(无需重启)");
                } else {
                    url = new URL(TGRJ);
                    URLConnection connection = url.openConnection();

                    connection.setRequestProperty("accept", "*/*");
                    connection.setRequestProperty("connection", "Keep-Alive");
                    connection.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

                    connection.connect();

                    in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

                    String line;
                    while ((line = in.readLine()) != null) {
                        result.append(line);
                    }
                }


            }
            catch (IOException e) {
                return String.valueOf(e);
            }
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

        }
        return result.toString();
    }
}
