package xyz.jxmm.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class URLConnect {
    public static String URLConnect(String connectURL){
        URL url;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        try {
                url = new URL(connectURL);
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
        return result.toString();
    }
}
