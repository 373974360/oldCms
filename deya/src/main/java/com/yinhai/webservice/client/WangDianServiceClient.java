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

    private static String depcode = "";//机构代码
    private static String deptype1 = "";//机构类型
    private static String bktype = "";//银行行别
    private static String noparentnode    = "";//是否显示根节点
    private static String bustype = "";//业务类型
    private static String centercode = "";//中心编号
    private static String keyword = "";//搜索关键字 机构地址  机构名称  服务范围

    public static List<WangDianVo> getWangdianResultList(Map<String, String> map) {
        depcode = map.get("depcode");
        deptype1 = map.get("deptype1");
        bktype = map.get("bktype");
        noparentnode = map.get("noparentnode");
        bustype = map.get("bustype");
        centercode = map.get("centercode");
        keyword = map.get("keyword");
        String s = WebServiceClientUtil.doHttpPost("trader", "NLC009", getparamValue());
        return getWangdianResultList(s);
    }
    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        if (depcode != null && !"".equals(depcode)) {
            _xmlstr.append("<depcode>").append(depcode).append("</depcode>");
        }
        if (deptype1 != null && !"".equals(deptype1)) {
            _xmlstr.append("<deptype1>").append(deptype1).append("</deptype1>");
        }else{
            _xmlstr.append("<deptype1>").append(0).append("</deptype1>");
        }
        if (bktype != null && !"".equals(bktype)) {
            _xmlstr.append("<bktype>").append(bktype).append("</bktype>");
        }
        if (noparentnode != null && !"".equals(noparentnode)) {
            _xmlstr.append("<noparentnode>").append(noparentnode).append("</noparentnode>");
        }
        if (bustype != null && !"".equals(bustype)) {
            _xmlstr.append("<bustype>").append(bustype).append("</bustype>");
        }else{
            _xmlstr.append("<bustype>").append(0).append("</bustype>");
        }
        if (centercode != null && !"".equals(centercode)) {
            _xmlstr.append("<centercode>").append(centercode).append("</centercode>");
        }
        if (keyword != null && !"".equals(keyword)) {
            _xmlstr.append("<keyword>").append(keyword).append("</keyword>");
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
                Iterator personInfos = rootElement.elementIterator("list");
                if (personInfos != null) {
                    while (personInfos.hasNext()) {
                        Element questionnaire = (Element) personInfos.next();
                        WangDianVo wangDianVo = new WangDianVo();
                        if (questionnaire.element("id") != null) {
                            wangDianVo.setId(questionnaire.element("id").getTextTrim());
                        } else {
                            wangDianVo.setId("");
                        }
                        if (questionnaire.element("pid") != null) {
                            wangDianVo.setPid(questionnaire.element("pid").getTextTrim());
                        } else {
                            wangDianVo.setPid("");
                        }
                        if (questionnaire.element("depcode") != null) {
                            wangDianVo.setDepcode(questionnaire.element("depcode").getTextTrim());
                        } else {
                            wangDianVo.setDepcode("");
                        }
                        if (questionnaire.element("name") != null) {
                            wangDianVo.setName(questionnaire.element("name").getTextTrim());
                        } else {
                            wangDianVo.setName("");
                        }
                        if (questionnaire.element("depaddr") != null) {
                            wangDianVo.setDepaddr(questionnaire.element("depaddr").getTextTrim());
                        } else {
                            wangDianVo.setDepaddr("");
                        }
                        if (questionnaire.element("depphone") != null) {
                            wangDianVo.setDepphone(questionnaire.element("depphone").getTextTrim());
                        } else {
                            wangDianVo.setDepphone("");
                        }
                        if (questionnaire.element("longitude") != null) {
                            wangDianVo.setLongitude(questionnaire.element("longitude").getTextTrim());
                        } else {
                            wangDianVo.setLongitude("");
                        }
                        if (questionnaire.element("latitude") != null) {
                            wangDianVo.setLatitude(questionnaire.element("latitude").getTextTrim());
                        } else {
                            wangDianVo.setLatitude("");
                        }
                        if (questionnaire.element("sertime") != null) {
                            wangDianVo.setSertime(questionnaire.element("sertime").getTextTrim());
                        } else {
                            wangDianVo.setSertime("");
                        }
                        if (questionnaire.element("serarea") != null) {
                            wangDianVo.setSerarea(questionnaire.element("serarea").getTextTrim());
                        } else {
                            wangDianVo.setSerarea("");
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
