package com.yinhai.webservice.client;

import com.deya.util.DateUtil;
import com.deya.util.jconfig.JconfigUtilContainer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Description:
 * @User: Administrator
 * @Date: 2018/5/10 0010
 */
public class WebServiceClientUtil {
    private static String wsdlUrl;
    private static String targetNamespace;
    private static String methodName;
    private static String paramName;
    private static String reqident;
    private static String forgcode;
    private static String certcode;
    private static String torgcode;

    private static void init(String serviceName){
        wsdlUrl = JconfigUtilContainer.bashConfig().getProperty("wsdlUrl", "", serviceName);
        targetNamespace = JconfigUtilContainer.bashConfig().getProperty("targetNamespace", "", serviceName);
        methodName = JconfigUtilContainer.bashConfig().getProperty("methodName", "", serviceName);
        paramName = JconfigUtilContainer.bashConfig().getProperty("paramName", "", serviceName);
        reqident = JconfigUtilContainer.bashConfig().getProperty("reqident", "", serviceName);
        forgcode = JconfigUtilContainer.bashConfig().getProperty("forgcode", "", serviceName);
        certcode = JconfigUtilContainer.bashConfig().getProperty("certcode", "", serviceName);
        torgcode = JconfigUtilContainer.bashConfig().getProperty("torgcode", "", serviceName);
    }

    private static String getSoapStr(String txcode,String paramValue) {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ser=\"");
        _xmlstr.append(targetNamespace).append("\">");
        _xmlstr.append("<soapenv:Header/><soapenv:Body>");
        _xmlstr.append("<ser:").append(methodName).append(">");
        _xmlstr.append("<").append(paramName).append(">");
        _xmlstr.append("<![CDATA[<data><txcode>" + txcode + "</txcode>");
        _xmlstr.append("<torgcode>" + torgcode + "</torgcode>");
        _xmlstr.append("<forgcode>" + forgcode + "</forgcode>");
        _xmlstr.append("<certcode>" + certcode + "</certcode>");
        _xmlstr.append("<txchannel>1</txchannel>");
        _xmlstr.append("<reqident>mh_" + DateUtil.getCurrentDateTime("yyyyMMddhhmmssSSS") + "</reqident>");
        _xmlstr.append(paramValue);
        _xmlstr.append("</data>]]>");
        _xmlstr.append("</").append(paramName).append(">");
        _xmlstr.append("</ser:").append(methodName).append(">");
        _xmlstr.append("</soapenv:Body> </soapenv:Envelope>");
        return _xmlstr.toString();
    }

    public static String doHttpPost(String serviceName,String txcode,String paramValue) {
        init(serviceName);
        //服务的地址
        URL wsUrl = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            wsUrl = new URL(wsdlUrl);
            conn = (HttpURLConnection) wsUrl.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
                os = conn.getOutputStream();
                //请求体
                String soap = getSoapStr(txcode,paramValue);
                os.write(soap.getBytes());
                is = conn.getInputStream();
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String s = sb.toString();
                s = s.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
                closeConnect(conn, is, os);
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeConnect(conn, is, os);
        } finally {
            closeConnect(conn, is, os);
        }
        return null;
    }

    private static void closeConnect(HttpURLConnection conn, InputStream is, OutputStream os) {
        try {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
