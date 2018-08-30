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
import java.util.List;
import java.util.Properties;

public class Weixin {
    private static String appid = "";
    private static String secret = "";
    private static String accessTockenUrl = "";
    private static String perUploadUrl = "";
    private static String uploadUrl = "";
    private static String sendAllUrl = "";
    private static String domain = "";
    private static String defautImag="";
    private static String tocken="";
    private static String rootpath="";

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
            } catch (IOException var20) {
                System.out.println("发送POST请求出现异常！" + var20);
                var20.printStackTrace();
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
        String imgpath = defautImag;
        String tempUrl = perUploadUrl.replace("##tocken##", tocken);
        for(int i = 0; i < articleList.size(); ++i) {
            ArticleBean ab = articleList.get(i);
            if(StringUtils.isNotEmpty(ab.getThumb_url())&&!ab.getThumb_url().substring(0,4).equals("http")){
                imgpath = rootpath+ab.getThumb_url();
            }
            System.out.println(imgpath);
            String temp_media_id = uploadImage(tempUrl, imgpath);
            articleStr = articleStr + ",{\"thumb_media_id\":\"" + temp_media_id + "\",\"author\":\"" + ab.getAuthor() + "\",\"title\":\"" + ab.getTitle() + "\",\"content_source_url\":\"" + domain + ab.getContent_url() + "\",\"content\":\"" + ab.getInfo_content() + "\",\"digest\":\"" + ab.getDescription() + "\",\"show_cover_pic\":\"0\"}";
        }
        data = data + articleStr.substring(1) + "]}";
        JSONObject jsonObject = CommUtil.httpRequest(url, "POST", data);
        System.out.print(data);
        System.out.println("--图文素材--" + jsonObject.toString());
        return jsonObject.getString("media_id");
    }

    public static boolean sendGroupMessage(String url, String mediaId) {
        String gdata = "{\"filter\":{\"is_to_all\":true},\"mpnews\":{\"media_id\":\"" + mediaId + "\"},\"msgtype\":\"mpnews\", \"send_ignore_reprint\":0}";
        JSONObject json = CommUtil.httpRequest(url, "POST", gdata);
        System.out.println("--群发--" + json.toString());
        return json.containsKey("msg_id");
    }

    public static boolean doPush(List<ArticleBean> articleList) throws Exception {
        initParams("media.properties");
        String upUrl = uploadUrl.replace("##tocken##", tocken);
        String sendUrl = sendAllUrl.replace("##tocken##", tocken);
        String temp_id = upload(upUrl, articleList);
        boolean flag = sendGroupMessage(sendUrl, temp_id);
        return flag;
    }

    public static void initParams(String fileName) {
        tocken = AccessTockenOk();
        appid = GetValueByKey(fileName, "appid");
        secret = GetValueByKey(fileName, "secret");
        accessTockenUrl = GetValueByKey(fileName, "accessTockenUrl") + appid + "&secret=" + secret;
        perUploadUrl = GetValueByKey(fileName, "perUploadUrl");
        uploadUrl = GetValueByKey(fileName, "uploadUrl");
        sendAllUrl = GetValueByKey(fileName, "sendAllUrl");
        domain = GetValueByKey(fileName, "domain");
        defautImag = GetValueByKey(fileName, "defautImag");
        rootpath = GetValueByKey(fileName, "rootpath");
    }

    public static String GetValueByKey(String fileName, String key) {
        Properties pps = new Properties();
        try {
            FileInputStream in = new FileInputStream(Weixin.class.getResource("/" + fileName).toString().substring(5));
            pps.load(in);
            String value = pps.getProperty(key);
            return value;
        } catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }
}
