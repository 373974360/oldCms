package com.yinhai.webservice.client;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.yinhai.model.SurveySettingVo;
import com.yinhai.sftp.SFTPUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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


public class SurveySettingServiceClient {

    private static String ywlsh = "";
    private static String title = "";
    private static String stime = "";
    private static String etime = "";
    private static String state = "";

    public static List<SurveySettingVo> getSurveySettingList(Map<String, String> map) {
        title = map.get("title");
        stime = map.get("stime");
        etime = map.get("etime");
        state = map.get("state");
        ywlsh = "";
        String s = WebServiceClientUtil.doHttpPost("trader", "NLC067", getparamValue());
        return getSurveySettingListResult(s);
    }

    public static boolean updateSurveySettingState(Map<String, String> map) {
        ywlsh = map.get("ywlsh");
        state = map.get("state");
        String s = WebServiceClientUtil.doHttpPost("trader", "NLC068", getparamValue());
        return updateSurveySettingResult(s);

    }

    public static String getparamValue() {
        StringBuilder _xmlstr = new StringBuilder();
        if (ywlsh != null && !"".equals(ywlsh)) {
            _xmlstr.append("<ywlsh>").append(ywlsh).append("</ywlsh>");
        }
        if (title != null && !"".equals(title)) {
            _xmlstr.append("<title>").append(title).append("</title>");
        }
        if (stime != null && !"".equals(stime)) {
            _xmlstr.append("<stime>").append(stime).append("</stime>");
        }
        if (etime != null && !"".equals(etime)) {
            _xmlstr.append("<etime>").append(etime).append("</etime>");
        }
        if (state != null && !"".equals(state)) {
            _xmlstr.append("<state>").append(state).append("</state>");
        }
        return _xmlstr.toString();
    }

    public static List<SurveySettingVo> getSurveySettingListResult(String s) {
        List<SurveySettingVo> surveySettingVoList = new ArrayList<>();
        try {
            s = s.substring(s.indexOf("<data>"), s.indexOf("</return>"));
            Document xmlDoc = DocumentHelper.parseText(s);
            Element rootElement = xmlDoc.getRootElement();
            Iterator personInfos = rootElement.elementIterator("list");
            if (personInfos != null) {
                while (personInfos.hasNext()) {
                    Element questionnaire = (Element) personInfos.next();
                    SurveySettingVo surveySettingVo = new SurveySettingVo();
                    if (questionnaire.element("ywlsh") != null) {
                        surveySettingVo.setYwlsh(questionnaire.element("ywlsh").getTextTrim());
                    } else {
                        surveySettingVo.setYwlsh("");
                    }
                    if (questionnaire.element("question_title") != null) {
                        surveySettingVo.setQuestion_title(questionnaire.element("question_title").getTextTrim());
                    } else {
                        surveySettingVo.setQuestion_title("");
                    }
                    if (questionnaire.element("question_desc") != null) {
                        surveySettingVo.setQuestion_desc(questionnaire.element("question_desc").getTextTrim());
                    } else {
                        surveySettingVo.setQuestion_desc("");
                    }
                    if (questionnaire.element("create_date") != null) {
                        surveySettingVo.setCreate_date(questionnaire.element("create_date").getTextTrim());
                    } else {
                        surveySettingVo.setCreate_date("");
                    }
                    if (questionnaire.element("creator") != null) {
                        surveySettingVo.setCreator(questionnaire.element("creator").getTextTrim());
                    } else {
                        surveySettingVo.setCreator("");
                    }
                    if (questionnaire.element("start_date") != null) {
                        surveySettingVo.setStart_date(questionnaire.element("start_date").getTextTrim());
                    } else {
                        surveySettingVo.setStart_date("");
                    }
                    if (questionnaire.element("end_date") != null) {
                        surveySettingVo.setEnd_date(questionnaire.element("end_date").getTextTrim());
                    } else {
                        surveySettingVo.setEnd_date("");
                    }
                    if (questionnaire.element("question_file_name") != null) {
                        String fileNames = "";
                        String question_file_name = questionnaire.element("question_file_name").getTextTrim();
                        if (question_file_name != null) {
                            String[] split = question_file_name.split("\\|");
                            for (String s1 : split) {
                                if (s1 != null && s1.length() > 0) {
                                    String fileName = s1.substring(s1.lastIndexOf("/") + 1, s1.length());
                                    SFTPUtils sftpUtils = new SFTPUtils();
                                    sftpUtils.downloadFile(s1, fileName);
                                    fileNames += "|" + fileName;
                                }
                            }
                        }
                        if (fileNames.length() > 0) {
                            fileNames = fileNames.substring(1);
                        }
                        surveySettingVo.setQuestion_file_name(fileNames);
                    } else {
                        surveySettingVo.setQuestion_file_name("");
                    }
                    if (questionnaire.element("state") != null) {
                        surveySettingVo.setState(questionnaire.element("state").getTextTrim());
                    } else {
                        surveySettingVo.setState("");
                    }
                    if (questionnaire.element("question_code") != null) {
                        surveySettingVo.setQuestion_code(questionnaire.element("question_code").getTextTrim());
                    } else {
                        surveySettingVo.setQuestion_code("");
                    }
                    if (questionnaire.element("source") != null) {
                        surveySettingVo.setSource(questionnaire.element("source").getTextTrim());
                    } else {
                        surveySettingVo.setSource("");
                    }
                    if (questionnaire.element("depname") != null) {
                        surveySettingVo.setDepname(questionnaire.element("depname").getTextTrim());
                    } else {
                        surveySettingVo.setDepname("");
                    }
                    if (questionnaire.element("curinfo") != null) {
                        surveySettingVo.setCurinfo(questionnaire.element("curinfo").getTextTrim());
                    } else {
                        surveySettingVo.setCurinfo("");
                    }
                    surveySettingVoList.add(surveySettingVo);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return surveySettingVoList;
    }

    public static boolean updateSurveySettingResult(String s) {
        try {
            s = s.substring(s.indexOf("<data>"), s.indexOf("</return>"));
            Document xmlDoc = DocumentHelper.parseText(s);
            Element rootElement = xmlDoc.getRootElement();
            Iterator personInfos = rootElement.elementIterator("list");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return true;
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
