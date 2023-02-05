package xyz.jxmm.music;

import xyz.jxmm.tools.*;

import net.mamoe.mirai.contact.Group;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String msg, Group group) {
        String type = musicMap.musicMap(msg.substring(4,6));
        String name = msg.substring(7).replaceAll(" ","%20");


        URL url;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        {
            try {
                url = new URL("http://www.xmsj.org/?name=" + name + "&type=" + type);
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
            catch (IOException e) {
                group.sendMessage("IO异常:\n" + e);
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
        group.sendMessage(String.valueOf(result));

    }

}
