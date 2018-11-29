package com.yinhai.webservice.client;

import com.yinhai.model.WangDianVo;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/21
 * Time: 15:06
 * Description: 重庆公积金调查问卷webservice客户端
 * Version: v1.0
 */


public class WangDianServiceClient {

    private static String query_type = "";//查询标识
    private static String bantype = "";//银行行别
    private static String networktype = "";//网点类型1：一手房贷款银行网点 2：二手房贷款银行网点
    private static String networkarea = "";//所在区域
    private static String orgcode = "";//机构代码
    private static String keyword = "";//关键字
    private static String limit = "";//每页条数
    private static String start = "";//起始页

    public static List<WangDianVo> getWangdianResultList(Map<String, String> map) {
        query_type = map.get("query_type");
        bantype = map.get("bantype");
        networktype = map.get("networktype");
        networkarea = map.get("networkarea");
        orgcode = map.get("orgcode");
        keyword = map.get("keyword");
        limit = map.get("limit");
        start = map.get("start");
        String s = WebServiceClientUtil.doHttpPost("trader", "NLC130", getparamValue());
        return getWangdianResultList(s);
    }
    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        if (query_type != null && !"".equals(query_type)) {
            _xmlstr.append("<query_type>").append(query_type).append("</query_type>");
        }
        if (bantype != null && !"".equals(bantype)) {
            _xmlstr.append("<bantype>").append(bantype).append("</bantype>");
        }
        if (networktype != null && !"".equals(networktype)) {
            _xmlstr.append("<networktype>").append(networktype).append("</networktype>");
        }
        if (networkarea != null && !"".equals(networkarea)) {
            _xmlstr.append("<networkarea>").append(networkarea).append("</networkarea>");
        }
        if (orgcode != null && !"".equals(orgcode)) {
            _xmlstr.append("<orgcode>").append(orgcode).append("</orgcode>");
        }
        if (keyword != null && !"".equals(keyword)) {
            _xmlstr.append("<keyword>").append(keyword).append("</keyword>");
        }
        if (limit != null && !"".equals(limit)) {
            _xmlstr.append("<limit>").append(limit).append("</limit>");
        }
        if (start != null && !"".equals(start)) {
            _xmlstr.append("<start>").append(start).append("</start>");
        }
        return _xmlstr.toString();
    }

    public static List<WangDianVo> getWangdianResultList(String s) {
        List<WangDianVo> wangDianVoArrayList = new ArrayList<>();
        if(StringUtils.isNotEmpty(s)){
            try {
                s = s.substring(s.indexOf("<data>"), s.indexOf("</return>"));
                Document xmlDoc = DocumentHelper.parseText(s);
                Element rootElement = xmlDoc.getRootElement();
                Iterator personInfos = rootElement.elementIterator("reslist");
                if (personInfos != null) {
                    while (personInfos.hasNext()) {
                        Element questionnaire = (Element) personInfos.next();
                        WangDianVo wangDianVo = new WangDianVo();
                        if (questionnaire.element("networkid") != null) {
                            wangDianVo.setNetworkid(questionnaire.element("networkid").getTextTrim());
                        } else {
                            wangDianVo.setNetworkid("");
                        }
                        if (questionnaire.element("networkname") != null) {
                            wangDianVo.setNetworkname(questionnaire.element("networkname").getTextTrim());
                        } else {
                            wangDianVo.setNetworkname("");
                        }
                        if (questionnaire.element("networkarea") != null) {
                            wangDianVo.setNetworkarea(questionnaire.element("networkarea").getTextTrim());
                        } else {
                            wangDianVo.setNetworkarea("");
                        }
                        if (questionnaire.element("networkaddress") != null) {
                            wangDianVo.setNetworkaddress(questionnaire.element("networkaddress").getTextTrim());
                        } else {
                            wangDianVo.setNetworkaddress("");
                        }
                        if (questionnaire.element("contactnumber") != null) {
                            wangDianVo.setContactnumber(questionnaire.element("contactnumber").getTextTrim());
                        } else {
                            wangDianVo.setContactnumber("");
                        }
                        if (questionnaire.element("networklongitude") != null) {
                            wangDianVo.setNetworklongitude(questionnaire.element("networklongitude").getTextTrim());
                        } else {
                            wangDianVo.setNetworklongitude("");
                        }
                        if (questionnaire.element("networklatitude") != null) {
                            wangDianVo.setNetworklatitude(questionnaire.element("networklatitude").getTextTrim());
                        } else {
                            wangDianVo.setNetworklatitude("");
                        }
                        if (questionnaire.element("projectname") != null) {
                            wangDianVo.setProjectname(questionnaire.element("projectname").getTextTrim());
                        } else {
                            wangDianVo.setProjectname("");
                        }
                        if (questionnaire.element("projectaddress") != null) {
                            wangDianVo.setProjectaddress(questionnaire.element("projectaddress").getTextTrim());
                        } else {
                            wangDianVo.setProjectaddress("");
                        }
                        if (questionnaire.element("takebank") != null) {
                            wangDianVo.setTakebank(questionnaire.element("takebank").getTextTrim());
                        } else {
                            wangDianVo.setTakebank("");
                        }
                        if (questionnaire.element("receiptcorp") != null) {
                            wangDianVo.setReceiptcorp(questionnaire.element("receiptcorp").getTextTrim());
                        } else {
                            wangDianVo.setReceiptcorp("");
                        }
                        if (questionnaire.element("orgname") != null) {
                            wangDianVo.setOrgname(questionnaire.element("orgname").getTextTrim());
                        } else {
                            wangDianVo.setOrgname("");
                        }
                        if (questionnaire.element("orgaddress") != null) {
                            wangDianVo.setOrgaddress(questionnaire.element("orgaddress").getTextTrim());
                        } else {
                            wangDianVo.setOrgaddress("");
                        }
                        if (questionnaire.element("contacttime") != null) {
                            wangDianVo.setContacttime(questionnaire.element("contacttime").getTextTrim());
                        } else {
                            wangDianVo.setContacttime("");
                        }
                        wangDianVoArrayList.add(wangDianVo);
                    }
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return wangDianVoArrayList;
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
