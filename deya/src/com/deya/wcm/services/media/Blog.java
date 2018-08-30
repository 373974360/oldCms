package com.deya.wcm.services.media;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Blog {

    private static String token = "";
    private static String domain = "";

    public static void updateStatus(String text,String content_url) {
        initParams("media.properties");
        if (!content_url.substring(0, 4).equals("http")) {
            content_url = domain + content_url;
        }
        String url = "https://api.weibo.com/2/statuses/share.json";
        String parameters = "status=" + text+" "+content_url+ "&access_token=" + token;
        postUrl(url, parameters);
    }

    public static void postUrl(String url, String parameters) {
        try {
            URLConnection conn = (new URL(url)).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:11.0) Gecko/20100101 Firefox/11.0");
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(parameters);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initParams(String fileName) {
        token = GetValueByKey(fileName, "token");
        domain = GetValueByKey(fileName, "domain");
    }
    public static String GetValueByKey(String fileName, String key) {
        Properties pps = new Properties();
        try {
            FileInputStream in = new FileInputStream(Weixin.class.getResource("/" + fileName).toString().substring(5));
            pps.load(in);
            String value = pps.getProperty(key);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
