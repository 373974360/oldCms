package com.deya.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args){
        setNews();
    }

    public static void setNews() {
        String url = "http://39.106.141.135/interface/inteface.jsp";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new NameValuePair("title", "门户网站接口文档111"));
        data.add(new NameValuePair("doc_no", "文号"));
        data.add(new NameValuePair("publish_dept", "发布机构"));
        data.add(new NameValuePair("gk_signer", "签发人"));
        data.add(new NameValuePair("keywords", "关键字"));
        data.add(new NameValuePair("publish_date", "2018-11-02 14:35:00"));
        data.add(new NameValuePair("info_content", "纯文字推送的 正文内容"));
        data.add(new NameValuePair("info_content_file", "/upload/CMSsxaj/201812/201812190936012.docx"));
        data.add(new NameValuePair("file_count", "1"));
        data.add(new NameValuePair("file_1", "/upload/CMSsxaj/201812/201812190936012.docx"));
        data.add(new NameValuePair("file_name_1", "2018版-信息协同接口设计-外部协作的信息协同接口1.0"));
        doPost(url, data);
    }

    public static String doPost(String url, List<NameValuePair> data) {
        String response = "";

        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
        if(!data.isEmpty()){
            for(NameValuePair nameValuePair:data){
                postMethod.addParameter(nameValuePair);
            }
        }
        //postMethod.setRequestBody((NameValuePair[]) data.toArray());
        int statusCode = 0;

        try {
            statusCode = httpclient.executeMethod(postMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //301、302重定向
        if(statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY){
            Header locationHeader = postMethod.getResponseHeader("location");
            String location = "";
            if (locationHeader != null) {
                location = locationHeader.getValue();
                System.out.println("重定向跳转页面:" + location);
                response= doPost(location,data);//用跳转后的页面重新请求。
            } else {
                System.err.println("location is null.");
            }
        }else {
            System.out.println(postMethod.getStatusLine());
            try {
                byte[] b=postMethod.getResponseBody();
                response = new String(b,"UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            postMethod.releaseConnection();
        }
        System.out.println("POST响应参数："+response);
        return response;
    }
}

