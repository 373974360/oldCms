package com.yinhai.webservice.client;

import com.yinhai.model.JiSuanQiVo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/21
 * Time: 15:06
 * Description: 重庆公积金调查问卷webservice客户端
 * Version: v1.0
 */


public class JISuanQiCServiceClient {

    private static String age = "";//借款人年龄
    private static String income = "";//家庭月收入(元)
    private static String price = "";//购房价款(元)
    private static String sex = "";//借款人性别 1:男 2女
    private static String houseType = "";//房屋类型 1:商品房  2:二手房
    private static String loanType = "";//贷款类型:1:公积金贷款  2:组合贷款

    public static JiSuanQiVo getResult(Map<String, String> map) {
        age = map.get("age");
        income = map.get("income");
        price = map.get("price");
        sex = map.get("sex");
        houseType = map.get("houseType");
        loanType = map.get("loanType");
        String s = WebServiceClientUtil.doHttpPost("trader", "NLC112", getparamValue());
        return getResult(s);
    }
    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        if (age != null && !"".equals(age)) {
            _xmlstr.append("<age>").append(age).append("</age>");
        }
        if (income != null && !"".equals(income)) {
            _xmlstr.append("<income>").append(income).append("</income>");
        }
        if (price != null && !"".equals(price)) {
            _xmlstr.append("<price>").append(price).append("</price>");
        }
        if (sex != null && !"".equals(sex)) {
            _xmlstr.append("<sex>").append(sex).append("</sex>");
        }
        if (houseType != null && !"".equals(houseType)) {
            _xmlstr.append("<houseType>").append(houseType).append("</houseType>");
        }
        if (loanType != null && !"".equals(loanType)) {
            _xmlstr.append("<loanType>").append(loanType).append("</loanType>");
        }
        return _xmlstr.toString();
    }

    public static JiSuanQiVo getResult(String s) {
        JiSuanQiVo jisuanqiVo = new JiSuanQiVo();
        try {
            s = s.substring(s.indexOf("<data>"), s.indexOf("</return>"));
            Document xmlDoc = DocumentHelper.parseText(s);
            Element rootElement = xmlDoc.getRootElement();
            if (rootElement.element("maxYear") != null) {
                jisuanqiVo.setMaxYear(rootElement.element("maxYear") .getTextTrim());
            } else {
                jisuanqiVo.setMaxYear("");
            }
            if (rootElement.element("gjjLoan") != null) {
                jisuanqiVo.setGjjLoan(rootElement.element("gjjLoan") .getTextTrim());
            } else {
                jisuanqiVo.setGjjLoan("");
            }
            if (rootElement.element("syLoan") != null) {
                jisuanqiVo.setSyLoan(rootElement.element("syLoan") .getTextTrim());
            } else {
                jisuanqiVo.setSyLoan("");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return jisuanqiVo;
    }

    public static void main(String[] args) throws FileNotFoundException, com.itextpdf.text.DocumentException {
        String ss = "|20180428115025_801.zip|/deya/cms/aas.pdf";
        String[] split = ss.split("\\|");
        for (String s1 : split) {
            if (s1 != null && s1.length() > 0) {
                String fileName = s1.substring(s1.lastIndexOf("/") + 1, s1.length());
                System.out.println(fileName);
            }
        }
    }
}
