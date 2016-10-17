package com.deya.wcm.services.search.util.autokeys;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.sun.org.apache.regexp.internal.recompile;

public class FileDown {
    public final static boolean DEBUG = true; // 调试用
    private static int BUFFER_SIZE = 8096; // 缓冲区大小

    public static void saveToFile(String destUrl, String fileName) throws IOException {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;

        // 建立链接
        url = new URL(destUrl);
        httpUrl = (HttpURLConnection) url.openConnection();
        // 连接指定的资源
        httpUrl.connect();
        // 获取网络输入流
        bis = new BufferedInputStream(httpUrl.getInputStream());


        int iLength = bis.available();
        byte[] b = new byte[iLength];
        bis.read(b);
        bis.close();
        String strR = new String(b, "utf-8");
        //System.out.println("strR===" + strR);

        // 建立文件
        fos = new FileOutputStream(fileName);
        if (FileDown.DEBUG)
            //System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" +fileName + "]");
        // 保存文件
        while ((size = bis.read(buf)) != -1)
            fos.write(buf, 0, size);


        fos.close();
        bis.close();
        httpUrl.disconnect();
    }


    public static String getGoogleResult(String destUrl) throws IOException {
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;

        // 建立链接
        url = new URL(destUrl);
        httpUrl = (HttpURLConnection) url.openConnection();
        // 连接指定的资源
        httpUrl.connect();
        // 获取网络输入流
        bis = new BufferedInputStream(httpUrl.getInputStream());

        int iLength = bis.available();
        byte[] b = new byte[iLength];
        bis.read(b);
        bis.close();
        String strR = new String(b);
        //String strR = new String(b, "utf-8");

        bis.close();
        httpUrl.disconnect();

        return strR;
    }

    public static String getBaiduResult(String destUrl) {
        try {

            // 得到指定的页面内容
            InputStream in = null;
            BufferedReader reader = null;
            String htmlContent = "";
            in = new URL(destUrl).openStream();
            reader = new BufferedReader(new InputStreamReader(in, "GBK"));
            StringBuffer htmlContentBu = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                htmlContentBu.append(line).append("\n");
            }
            in.close();
            htmlContent = htmlContentBu.toString();

            return htmlContent;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
