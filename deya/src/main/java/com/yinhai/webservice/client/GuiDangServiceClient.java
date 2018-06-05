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


public class GuiDangServiceClient {
    private static String ywbh = "";
    private static String ywbt = "";
    private static String lcmc = "";
    private static String files = "";
    private static String curcode = "";
    private static String buscode = "";
    private static String curdate = "";
    private static String usercode = "";
    private static String filepath = "";
    private static String xxly = "";

    public static int doService(GuiDangVo guiDangVo) {
        ywbh = guiDangVo.getYwbh();
        ywbt = guiDangVo.getYwbt();
        lcmc = guiDangVo.getLcmc();
        files = guiDangVo.getFiles();
        curcode = guiDangVo.getCurcode();
        buscode = guiDangVo.getBuscode();
        curdate = guiDangVo.getCurdate();
        usercode = guiDangVo.getUsercode();
        filepath = guiDangVo.getFilepath();
        xxly = guiDangVo.getXxly();
        String s = WebServiceClientUtil.doHttpPost("trader", "NLC065", getparamValue());
        int result = getResult(s);
        return result;
    }


    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
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
        if (xxly != null && !"".equals(xxly)) {
            _xmlstr.append("<xxly>").append(xxly).append("</xxly>");
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
