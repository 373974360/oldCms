package com.yinhai.webservice.client;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileNotFoundException;
import java.util.Map;
import com.yinhai.model.JiSuanQiVo;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/21
 * Time: 15:06
 * Description: 重庆公积金调查问卷webservice客户端
 * Version: v1.0
 */


public class JISuanQiAServiceClient {

    private static String limit = "";//贷款额度(元)
    private static String loanYear = "";//贷款年限(年)
    private static String loanType = "";//还款方式：1：等额本息2:等额本金

    public static JiSuanQiVo getResult(Map<String, String> map) {
        limit = map.get("limit");
        loanYear = map.get("loanYear");
        loanType = map.get("loanType");
        String s = WebServiceClientUtil.doHttpPost("trader", "NLC110", getparamValue());
        return getResult(s);
    }
    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        if (limit != null && !"".equals(limit)) {
            _xmlstr.append("<limit>").append(limit).append("</limit>");
        }
        if (loanYear != null && !"".equals(loanYear)) {
            _xmlstr.append("<loanYear>").append(loanYear).append("</loanYear>");
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
            if (rootElement.element("calculateType") != null) {
                jisuanqiVo.setCalculateType(rootElement.element("calculateType") .getTextTrim());
            } else {
                jisuanqiVo.setCalculateType("");
            }
            if (rootElement.element("syRate") != null) {
                jisuanqiVo.setSyRate(rootElement.element("syRate") .getTextTrim());
            } else {
                jisuanqiVo.setSyRate("");
            }
            if (rootElement.element("gjjTate") != null) {
                jisuanqiVo.setGjjTate(rootElement.element("gjjTate") .getTextTrim());
            } else {
                jisuanqiVo.setGjjTate("");
            }
            if (rootElement.element("syInterestAvg") != null) {
                jisuanqiVo.setSyInterestAvg(rootElement.element("syInterestAvg") .getTextTrim());
            } else {
                jisuanqiVo.setSyInterestAvg("");
            }
            if (rootElement.element("syInterestSum") != null) {
                jisuanqiVo.setSyInterestSum(rootElement.element("syInterestSum") .getTextTrim());
            } else {
                jisuanqiVo.setSyInterestSum("");
            }
            if (rootElement.element("gjjInterestAvg") != null) {
                jisuanqiVo.setGjjInterestAvg(rootElement.element("gjjInterestAvg") .getTextTrim());
            } else {
                jisuanqiVo.setGjjInterestAvg("");
            }
            if (rootElement.element("gjjInterestSum") != null) {
                jisuanqiVo.setGjjInterestSum(rootElement.element("gjjInterestSum") .getTextTrim());
            } else {
                jisuanqiVo.setGjjInterestSum("");
            }
            if (rootElement.element("avgDiff") != null) {
                jisuanqiVo.setAvgDiff(rootElement.element("avgDiff") .getTextTrim());
            } else {
                jisuanqiVo.setAvgDiff("");
            }
            if (rootElement.element("sumDiff") != null) {
                jisuanqiVo.setSumDiff(rootElement.element("sumDiff") .getTextTrim());
            } else {
                jisuanqiVo.setSumDiff("");
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
