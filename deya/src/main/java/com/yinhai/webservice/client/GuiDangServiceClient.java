package com.yinhai.webservice.client;

import com.deya.util.DateUtil;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.yinhai.model.GuiDangVo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/21
 * Time: 15:06
 * Description: 重庆公积金调查问卷webservice客户端
 * Version: v1.0
 */


public class GuiDangServiceClient {

    private static String wsdlUrl = "http://35.10.28.126:8007/trader/services/TraderService?wsdl";
    private static String targetNamespace = "http://service.core.trader.yinhai.com/";
    private static String methodName = "doTrader";
    private static String paramName = "paramXml";
    private static String forgcode = "19ceb94366931d8e2017";
    private static String certcode = "3b0184454f8cb24147d7";
    private static String torgcode = "5510gjj";
    private static String txcode = "";
    private static String ywbh = "";
    private static String ywbt = "";
    private static String lcmc = "";
    private static String files = "";
    private static String curcode = "";
    private static String buscode = "";
    private static String curdate = "";
    private static String usercode = "";
    private static String filepath = "";

    public static int doService(GuiDangVo guiDangVo) {
        txcode = "NLC065";
        ywbh = guiDangVo.getYwbh();
        ywbt = guiDangVo.getYwbt();
        lcmc = guiDangVo.getLcmc();
        files = guiDangVo.getFiles();
        curcode = guiDangVo.getCurcode();
        buscode = guiDangVo.getBuscode();
        curdate = guiDangVo.getCurdate();
        usercode = guiDangVo.getUsercode();
        filepath = guiDangVo.getFilepath();
        int result = getResult();
        return result;
    }

    public static String getSoapStr() {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ser=\"");
        _xmlstr.append(targetNamespace).append("\">");
        _xmlstr.append("<soapenv:Header/><soapenv:Body>");
        _xmlstr.append("<ser:").append(methodName).append(">");
        _xmlstr.append("<").append(paramName).append(">");
        _xmlstr.append(getparamValue());
        _xmlstr.append("</").append(paramName).append(">");
        _xmlstr.append("</ser:").append(methodName).append(">");
        _xmlstr.append("</soapenv:Body> </soapenv:Envelope>");
        return _xmlstr.toString();
    }

    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        _xmlstr.append("<![CDATA[<data><txcode>" + txcode + "</txcode>");
        _xmlstr.append("<torgcode>" + torgcode + "</torgcode>");
        _xmlstr.append("<forgcode>" + forgcode + "</forgcode>");
        _xmlstr.append("<certcode>" + certcode + "</certcode>");
        _xmlstr.append("<txchannel>1</txchannel>");
        _xmlstr.append("<reqident>mh_" + DateUtil.getCurrentDateTime("yyyyMMddhhmmssSSS") + "</reqident>");
        if (ywbh != null && !"".equals(ywbh)) {
            _xmlstr.append("<ywbh>").append(ywbh).append("</ywbh>");
        }
        if (ywbt != null && !"".equals(ywbt)) {
            _xmlstr.append("<ywbt>").append(ywbt).append("</ywbt>");
        }
        if (lcmc != null && !"".equals(lcmc)) {
            _xmlstr.append("<lcmc>").append(lcmc).append("</lcmc>");
        }
        if (files != null && !"".equals(files)) {
            _xmlstr.append("<files>").append(files).append("</files>");
        }
        if (curcode != null && !"".equals(curcode)) {
            _xmlstr.append("<curcode>").append(curcode).append("</curcode>");
        }
        if (buscode != null && !"".equals(buscode)) {
            _xmlstr.append("<buscode>").append(buscode).append("</buscode>");
        }
        if (curdate != null && !"".equals(curdate)) {
            _xmlstr.append("<curdate>").append(curdate).append("</curdate>");
        }
        if (usercode != null && !"".equals(usercode)) {
            _xmlstr.append("<usercode>").append(usercode).append("</usercode>");
        }
        if (filepath != null && !"".equals(filepath)) {
            _xmlstr.append("<filepath>").append(filepath).append("</filepath>");
        }
        _xmlstr.append("</data>]]>");
        return _xmlstr.toString();
    }

    public static int getResult() {
        String textTrim = "0";
        try {
            String s = doHttpPost();
            s = s.substring(s.indexOf("<data>"), s.indexOf("</return>"));
            Document xmlDoc = DocumentHelper.parseText(s);
            System.out.println(s + "----------------------------------");
            Element rootElement = xmlDoc.getRootElement();
            Element rescode = rootElement.element("rescode");
            textTrim = rescode.getTextTrim();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(textTrim);
    }

    public static String doHttpPost() {
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
                String soap = getSoapStr();
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

    public static void closeConnect(HttpURLConnection conn, InputStream is, OutputStream os) {
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
