package com.deya.project.dz_jyhgl;

import com.deya.wcm.dataCollection.util.DownHtmlUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckErrorUrlRPC {

    private static String noCheckExt = "jpg,png,gif,bmp,jpeg,asf,avi,mpg,mpeg,mpe,mov,rmvb,wmv,wav,mid,midi,mp3,mpa,mp2,mp4,flv,ra,ram,rm,wma,mkv,txt,doc,docx,xlsx,xsl,ppt,pdf,rtf,rar,zip,tar,gz";

    public static List<ErrorUrlBean> startCheckSite(String site_domain, String encode) {
        List<Map<String,Integer>> result = new ArrayList<>();//错链收集集合
        Map<String,Integer> urlMap = new HashMap<>();//已检测URL集合
        result = checkUrl(site_domain,site_domain,encode,result,urlMap);
        return toBeanList(result);
    }

    public static List<Map<String,Integer>> checkUrl(String site_domain,String url, String encode,List<Map<String,Integer>> result,Map<String,Integer> urlMap){
        Map<String,Integer> map = new HashMap<>();
        if(url.substring(0,1).equals("/")){//本站相对地址加上域名前缀
            url = site_domain+url;
        }
        if(url.substring(0,1).equals("?")) {//本站相对地址加上域名前缀
            url = site_domain+"/info/iList.jsp"+url;
        }
        if(!urlMap.containsKey(url)){//判断改地址是否验证
            urlMap.put(url,1);//标记已验证地址
            int httpCode = downLoadHtmlByCode(url,encode);//链接是否可以正常打开
            if(httpCode==200){
                String ext = url.substring(url.lastIndexOf(".")+1,url.length());
                if(url.length()>=site_domain.length()&&url.substring(0,site_domain.length()).equals(site_domain)&&!noCheckExt.contains(ext)){//判断该链接是否为本站地址
                    System.out.println("网址："+url);
                    String htmlStr = downLoadHtml(url,encode);
                    if(htmlStr.equals("error")){
                        map.put(url,500);
                        result.add(map);
                    }else{
                        List<String> hrefArray = getAHref(htmlStr);
                        if(!hrefArray.isEmpty()){
                            for(String str:hrefArray){
                                checkUrl(site_domain,str, encode,result,urlMap);
                            }
                        }
                    }
                }
            }else{
                map.put(url,httpCode);
                result.add(map);
            }
        }
        return result;
    }


    public static List<String> getAHref(String strs){
        List<String>  al=new ArrayList<>();
        String regex="<a.*?/a>";
        Pattern pt=Pattern.compile(regex);
        Matcher mt=pt.matcher(strs);
        while(mt.find()){
            String s3 = "href=\"(.*?)\"";
            Pattern pt3=Pattern.compile(s3);
            Matcher mt3=pt3.matcher(mt.group());
            while(mt3.find()){
                String u=mt3.group().replaceAll("href=|>","");
                u = u.replaceAll("\"","");
                if(!u.equals("/")&&!u.equals("#")&&!u.contains("javascript:")){
                    al.add(u);
                }
            }
        }
        return al;
    }

    public static String downLoadHtml(String StrUrl, String Encode) {
        String htmlStr = "";
        String str = "";
        HttpURLConnection con = null;
        String cookie = "";
        label160:
        try {
            if (StrUrl.length() > 0) {
                URL url = new URL(StrUrl);
                int temp = Integer.parseInt(Math.round(Math.random() * 7) + "");
                con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setRequestProperty("Pragma", "no-cache");
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("User-Agent", DownHtmlUtil.UserAgent[temp]); // 模拟手机系统
                con.setRequestProperty("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");// 只接受text/html类型，当然也可以接受图片,pdf,*/*任意，就是tomcat/conf/web里面定义那些
                con.setConnectTimeout(50000);

                if (cookie.length() != 0)
                    con.setRequestProperty("Cookie", cookie);
                con.setInstanceFollowRedirects(false);
                int httpCode = con.getResponseCode();
                if (httpCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                    cookie += con.getHeaderField("Set-Cookie") + ";";
                }
                if (httpCode != 200)
                    break label160;
                InputStream in = con.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        in, Encode));
                while ((str = rd.readLine()) != null) {
                    htmlStr = htmlStr + str;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            htmlStr = "error";
        } finally {
            con.disconnect();
        }
        return htmlStr;
    }

    public static int downLoadHtmlByCode(String StrUrl, String Encode) {
        int httpCode = 200;
        HttpURLConnection con = null;
        String cookie = "";
        label160:
        try {
            if (StrUrl.length() > 0) {
                URL url = new URL(StrUrl);
                int temp = Integer.parseInt(Math.round(Math.random() * 7) + "");
                con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setRequestProperty("Pragma", "no-cache");
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("User-Agent", DownHtmlUtil.UserAgent[temp]); // 模拟手机系统
                con.setRequestProperty("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");// 只接受text/html类型，当然也可以接受图片,pdf,*/*任意，就是tomcat/conf/web里面定义那些
                con.setConnectTimeout(50000);
                if (cookie.length() != 0)
                    con.setRequestProperty("Cookie", cookie);
                con.setInstanceFollowRedirects(false);
                httpCode = con.getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return httpCode;
    }

    public static List<ErrorUrlBean> toBeanList(List<Map<String,Integer>> list){
        List<ErrorUrlBean> resultList = new ArrayList<>();
        if(!list.isEmpty()){
            for(Map<String,Integer> map:list){
                for(String key:map.keySet()){
                    ErrorUrlBean bean = new ErrorUrlBean();
                    bean.setUrl(key);
                    bean.setCode(map.get(key));
                    resultList.add(bean);
                }
            }
        }
        return resultList;
    }
}
