package com.deya.wcm.services.media;

import com.deya.wcm.bean.cms.info.ArticleBean;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Weixin {
    public static String appid = "";
    private static String secret = "";
    private static String accessTockenUrl = "";
    private static String perUploadUrl = "";
    private static String uploadUrl = "";
    private static String sendAllUrl = "";
    private static String domain = "";
    private static String defautImag="";
    private static String tocken="";
    private static String rootpath="";
    private static String previewUrl="";
    private static String testwxname="";
    private static String sendtype="";
    private static String jsapiTicketUrl="";
    private static Map<String,String> tokenMap = new HashMap<>();
    private static Map<String,String> ticketMap = new HashMap<>();

    public static String AccessTockenOk(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(accessTockenUrl);
        JSONObject jsonObject;
        String tokenStr = "";
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                System.out.println(result);
                jsonObject = JSONObject.fromObject(result);
                boolean isExit = jsonObject.containsKey("access_token");
                tokenStr = isExit ? jsonObject.getString("access_token") : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenStr;
    }
    public static String getToken(){
        String lifeStr = tokenMap.get("tokenLife") == null ? null : tokenMap.get("tokenLife").toString();
        Long life = 0L;
        if (lifeStr != null && !"".equals(lifeStr)) {
            life = Long.parseLong(lifeStr);
        }

        String tokenValue = tokenMap.get("tokenValue") == null ? null : tokenMap.get("tokenValue").toString();
        System.out.println("@@@@@@@@@@tokenValue" + tokenValue);
        if (!"".equals(tokenValue) && tokenValue != null && (new Date()).getTime() <= life + 7200000L) {
            return tokenValue;
        } else {
            String token = AccessTockenOk();
            System.out.println("@@@@@@@@@@token" + token);
            saveToken(token, String.valueOf((new Date()).getTime()));
            return token;
        }
    }

    public static void saveToken(String value, String life){
        tokenMap.put("tokenLife", life);
        tokenMap.put("tokenValue", value);
    }

    public static String getJSApiTicket(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = jsapiTicketUrl.replace("##tocken##", tocken);
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject;
        String ticket = "";
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                System.out.println(result);
                jsonObject = JSONObject.fromObject(result);
                boolean isExit = jsonObject.containsKey("ticket");
                ticket = isExit ? jsonObject.getString("ticket") : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }
    public static String getTicket(){
        String lifeStr = ticketMap.get("ticketLife") == null ? null : ticketMap.get("ticketLife").toString();
        Long life = 0L;
        if (lifeStr != null && !"".equals(lifeStr)) {
            life = Long.parseLong(lifeStr);
        }

        String ticketValue = ticketMap.get("ticketValue") == null ? null : ticketMap.get("ticketValue").toString();
        System.out.println("@@@@@@@@@@ticketValue" + ticketValue);
        if (!"".equals(ticketValue) && ticketValue != null && (new Date()).getTime() <= life + 7200000L) {
            return ticketValue;
        } else {
            String ticket = getJSApiTicket();
            System.out.println("@@@@@@@@@@ticket" + ticket);
            saveTicket(ticket, String.valueOf((new Date()).getTime()));
            return ticket;
        }
    }

    public static void saveTicket(String value, String life){
        ticketMap.put("ticketLife", life);
        ticketMap.put("ticketValue", value);
    }

    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static String uploadImage(String url, String filePath) throws IOException {
        String result = null;
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)urlObj.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            String BOUNDARY = "---------------------------" + System.currentTimeMillis();
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            OutputStream out = new DataOutputStream(con.getOutputStream());
            out.write(head);
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[1024];

            int bytes;
            while((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }

            in.close();
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
            out.write(foot);
            out.flush();
            out.close();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = null;

                while((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                if (result == null) {
                    result = buffer.toString();
                }
            } catch (IOException e) {
                System.out.println("发送POST请求出现异常！" + e);
                e.printStackTrace();
                throw new IOException("数据读取异常");
            } finally {
                if (reader != null) {
                    reader.close();
                }

            }
            System.out.println("--临时图片素材--" + result);
            JSONObject object = JSONObject.fromObject(result);
            return object.getString("media_id");
        } else {
            throw new IOException("文件不存在");
        }
    }

    public static String upload(String url, List<ArticleBean> articleList) throws Exception {
        String data = "{\"articles\": [";
        String articleStr = "";
        String imgpath = "";
        String tempUrl = perUploadUrl.replace("##tocken##", tocken);
        for(int i = 0; i < articleList.size(); ++i) {
            imgpath = defautImag;
            ArticleBean ab = articleList.get(i);
            if(StringUtils.isNotEmpty(ab.getThumb_url())&&!ab.getThumb_url().substring(0,4).equals("http")){
                imgpath = rootpath+ab.getThumb_url();
            }
            String temp_media_id = uploadImage(tempUrl, imgpath);
            articleStr = articleStr + ",{\"thumb_media_id\":\"" + temp_media_id + "\",\"author\":\"" + ab.getAuthor() + "\",\"title\":\"" + ab.getTitle() + "\",\"content_source_url\":\"" + domain + ab.getContent_url() + "\",\"content\":\"" + ab.getInfo_content().replace("\"","'") + "\",\"digest\":\"" + ab.getDescription() + "\",\"show_cover_pic\":\"1\"}";
        }
        data = data + articleStr.substring(1) + "]}";
        JSONObject jsonObject = CommUtil.httpRequest(url, "POST", data);
        System.out.println("--图文素材--" + jsonObject.toString());
        return jsonObject.getString("media_id");
    }

    //正式群发 -- 已关注的所有用户
    public static boolean sendGroupMessage(String mediaId) {
        String sendUrl = sendAllUrl.replace("##tocken##", tocken);
        String gdata = "{\"filter\":{\"is_to_all\":true},\"mpnews\":{\"media_id\":\"" + mediaId + "\"},\"msgtype\":\"mpnews\", \"send_ignore_reprint\":0}";
        JSONObject json = CommUtil.httpRequest(sendUrl, "POST", gdata);
        System.out.println("--群发--" + json.toString());
        if(json.get("errcode").toString().equals("0")){
            return true;
        }
        return false;
    }


    //群发预览  -- testwxname  指定的接收微信号
    public static boolean previewMessage(String mediaId) {
        String sendUrl = previewUrl.replace("##tocken##", tocken);
        String gdata = "{\"towxname\":\""+testwxname+"\",\"mpnews\":{\"media_id\":\"" + mediaId + "\"},\"msgtype\":\"mpnews\"}";
        JSONObject json = CommUtil.httpRequest(sendUrl, "POST", gdata);
        System.out.println("--预览--" + json.toString());
        if(json.get("errcode").toString().equals("0")){
            return true;
        }
        return false;
    }

    public static boolean doPush(List<ArticleBean> articleList) throws Exception {
        initParams("media.properties");
        String upUrl = uploadUrl.replace("##tocken##", tocken);
        String temp_id = upload(upUrl, articleList);
        boolean flag = false;
        if(sendtype.equals("0")){
            flag = sendGroupMessage(temp_id);//正式群发
        }else{
            flag = previewMessage(temp_id);//测试预览
        }
        return flag;
    }

    public static void initParams(String fileName) {
        tocken = getToken();
        appid = GetValueByKey(fileName, "appid");
        secret = GetValueByKey(fileName, "secret");
        accessTockenUrl = GetValueByKey(fileName, "accessTockenUrl") + appid + "&secret=" + secret;
        perUploadUrl = GetValueByKey(fileName, "perUploadUrl");
        uploadUrl = GetValueByKey(fileName, "uploadUrl");
        sendAllUrl = GetValueByKey(fileName, "sendAllUrl");
        domain = GetValueByKey(fileName, "domain");
        defautImag = GetValueByKey(fileName, "defautImag");
        rootpath = GetValueByKey(fileName, "rootpath");
        previewUrl = GetValueByKey(fileName, "previewUrl");
        testwxname = GetValueByKey(fileName, "testwxname");
        sendtype = GetValueByKey(fileName, "sendtype");
        jsapiTicketUrl = GetValueByKey(fileName, "jsapiTicketUrl");
    }

    public static String GetValueByKey(String fileName, String key) {
        Properties pps = new Properties();
        try {
            FileInputStream in = new FileInputStream(Weixin.class.getResource("/" + fileName).toString().substring(5));
            pps.load(in);
            String value = pps.getProperty(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
