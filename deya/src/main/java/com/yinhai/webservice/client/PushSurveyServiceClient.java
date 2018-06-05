package com.yinhai.webservice.client;

import com.yinhai.model.GuiDangVo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/21
 * Time: 15:06
 * Description: 重庆公积金调查问卷webservice客户端
 * Version: v1.0
 */


public class PushSurveyServiceClient {
    private static String ywlsh = "";
    private static String url = "";

    public static int doService(String lsh,String pathUrl) {
        ywlsh = lsh;
        url = pathUrl;
        String s = WebServiceClientUtil.doHttpPost("trader", "NLC107", getparamValue());
        int result = getResult(s);
        return result;
    }


    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        if (ywlsh != null && !"".equals(ywlsh)) {
            _xmlstr.append("<ywlsh>").append(ywlsh).append("</ywlsh>");
        }
        if (url != null && !"".equals(url)) {
            _xmlstr.append("<url>").append(url).append("</url>");
        }
        return _xmlstr.toString();
    }

    public static int getResult(String s) {
        String textTrim = "0";
        try {
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
}
