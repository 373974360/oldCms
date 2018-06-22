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


public class JISuanQiBServiceClient {

    private static String gjjLoan = "";//公积金贷款
    private static String syLoan = "";//商业性贷款
    private static String maxYear = "";//最高贷款年限
    private static String loanType = "";//还款方式

    public static JiSuanQiVo getResult(Map<String, String> map) {
        gjjLoan = map.get("gjjLoan");
        syLoan = map.get("syLoan");
        maxYear = map.get("maxYear");
        loanType = map.get("loanType");
        String s = WebServiceClientUtil.doHttpPost("trader", "NLC111", getparamValue());
        return getResult(s);
    }
    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        if (gjjLoan != null && !"".equals(gjjLoan)) {
            _xmlstr.append("<gjjLoan>").append(gjjLoan).append("</gjjLoan>");
        }
        if (syLoan != null && !"".equals(syLoan)) {
            _xmlstr.append("<syLoan>").append(syLoan).append("</syLoan>");
        }
        if (maxYear != null && !"".equals(maxYear)) {
            _xmlstr.append("<maxYear>").append(maxYear).append("</maxYear>");
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
            if (rootElement.element("gjjRate") != null) {
                jisuanqiVo.setGjjTate(rootElement.element("gjjRate") .getTextTrim());
            } else {
                jisuanqiVo.setGjjTate("");
            }
            if (rootElement.element("gjjRepayMonth") != null) {
                jisuanqiVo.setGjjRepayMonth(rootElement.element("gjjRepayMonth") .getTextTrim());
            } else {
                jisuanqiVo.setGjjRepayMonth("");
            }
            if (rootElement.element("syRepayMonth") != null) {
                jisuanqiVo.setSyRepayMonth(rootElement.element("syRepayMonth") .getTextTrim());
            } else {
                jisuanqiVo.setSyRepayMonth("");
            }
            if (rootElement.element("syLoanInterestSum") != null) {
                jisuanqiVo.setSyLoanInterestSum(rootElement.element("syLoanInterestSum") .getTextTrim());
            } else {
                jisuanqiVo.setSyLoanInterestSum("");
            }
            if (rootElement.element("gjjLoanInterestSum") != null) {
                jisuanqiVo.setGjjLoanInterestSum(rootElement.element("gjjLoanInterestSum") .getTextTrim());
            } else {
                jisuanqiVo.setGjjLoanInterestSum("");
            }
            if (rootElement.element("syPrincipalInterestSum") != null) {
                jisuanqiVo.setSyPrincipalInterestSum(rootElement.element("syPrincipalInterestSum") .getTextTrim());
            } else {
                jisuanqiVo.setSyPrincipalInterestSum("");
            }
            if (rootElement.element("gjjPrincipalInterestSum") != null) {
                jisuanqiVo.setGjjPrincipalInterestSum(rootElement.element("gjjPrincipalInterestSum") .getTextTrim());
            } else {
                jisuanqiVo.setGjjPrincipalInterestSum("");
            }
            if (rootElement.element("repaySumMonth") != null) {
                jisuanqiVo.setRepaySumMonth(rootElement.element("repaySumMonth") .getTextTrim());
            } else {
                jisuanqiVo.setRepaySumMonth("");
            }
            if (rootElement.element("IntersetSum") != null) {
                jisuanqiVo.setIntersetSum(rootElement.element("IntersetSum") .getTextTrim());
            } else {
                jisuanqiVo.setIntersetSum("");
            }
            if (rootElement.element("principalInterestSum") != null) {
                jisuanqiVo.setPrincipalInterestSum(rootElement.element("principalInterestSum") .getTextTrim());
            } else {
                jisuanqiVo.setPrincipalInterestSum("");
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
