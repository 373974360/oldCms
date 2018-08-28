package com.deya.wcm.services.media;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Blog {
    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }
        }};
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init((KeyManager[])null, trustAllCerts, (SecureRandom)null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
    public static void updateStatus(String text, String accessToken) {
        String url = "https://api.weibo.com/2/statuses/share.json";
        String parameters = "status=" + text + "&access_token=" + accessToken;
        postUrl(url, parameters);
        System.out.println("发布微博成功！");
    }

    public static void postUrl(String url, String parameters) {
        try {
            trustAllHttpsCertificates();
            URLConnection conn = (new URL(url)).openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:11.0) Gecko/20100101 Firefox/11.0");
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(parameters);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;

            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
}
