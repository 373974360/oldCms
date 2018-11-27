package com.deya.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Test {

    public static void main(String[] args){
        setNews();
    }

    public static void setNews() {
        String url = "http://111.20.241.184:12345/interface/inteface_s.jsp";
        String file_txt = Base64Utiles.fileToBase64("/Users/chaoweima/Downloads/重庆市住房公积金信息化工程应用软件系统建设项目_附件2.1_新增申请表20181108_（综合服务平台需求新增）.docx");
        NameValuePair[] data = {
                new NameValuePair("title", "信息标题"),
                new NameValuePair("doc_no", "文号"),
                new NameValuePair("publish_dept", "发布机构"),
                new NameValuePair("gk_signer", "签发人"),
                new NameValuePair("keywords", "关键字"),
                new NameValuePair("publish_date", "2018-11-02 14:35:00"),
                new NameValuePair("info_content", "纯文字推送的 正文内容"),
                new NameValuePair("info_content_file", file_txt),
                new NameValuePair("file_arry", file_txt),
                new NameValuePair("file_arry_name", "2018版-信息协同接口设计-外部协作的信息协同接口1.0")
        };
        doPost(url, data);
    }

    public static String doPost(String url, NameValuePair[] data) {
        String response = "";

        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
        postMethod.setRequestBody(data);
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
