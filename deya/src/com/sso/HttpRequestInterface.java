package com.sso;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.util.Map;

/**
 * @Description: 调用http请求
 * @User: like
 * @Date: 2016/12/13 11:51
 * @Version: 1.0
 * @Created with IntelliJ IDEA.
 */
public class HttpRequestInterface {

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return String 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        HttpClient httpclient = new HttpClient();
        GetMethod get = new GetMethod(url + "?" + param);
        get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        String response = "";
        try {
            httpclient.executeMethod(get);
            response = new String(get.getResponseBody(), "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该Map<String,Object>键值对的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String,Object> param) {
        HttpClient httpclient = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        post.addParameter("param", param.get("param").toString());
        String response = "";
        try {
            httpclient.executeMethod(post);
            response = new String(post.getResponseBody(), "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
