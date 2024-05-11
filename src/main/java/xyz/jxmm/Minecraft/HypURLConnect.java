package xyz.jxmm.minecraft;

import xyz.jxmm.tools.URLConnect;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class HypURLConnect {
    static Properties properties = new Properties();
    static File cfg = new File("./PracticalWidgets/config.properties");
    public static String hypixelURLConnect(String uuid){
        try {
            properties.load(new InputStreamReader(Files.newInputStream(cfg.toPath()), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String HypixelAPI = properties.getProperty("HypixelAPI");
        if (HypixelAPI.equals("")){
            return "noHypixelAPI";
        } else {
            String connectURL = "https://api.hypixel.net/player?key=" + HypixelAPI + "&uuid=" + uuid;

            return URLConnect.URLConnect(connectURL);
        }
    }
}
