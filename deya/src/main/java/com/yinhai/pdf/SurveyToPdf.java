package com.yinhai.pdf;

import com.deya.util.DateUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.bean.survey.SurveyBean;
import com.deya.wcm.services.org.user.SessionManager;
import com.deya.wcm.services.survey.SurveyService;
import com.yinhai.model.GuiDangVo;
import com.yinhai.sftp.SFTPUtils;
import com.yinhai.webservice.client.GuiDangServiceClient;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/1/21
 * Time: 15:06
 * Description: 重庆公积金调查问卷webservice客户端
 * Version: v1.0
 */


public class SurveyToPdf {

    private static final String HTML = "question_template.html";
    private static String localPath;
    private static String remotePath;
    static {
        localPath = JconfigUtilContainer.bashConfig().getProperty("localPath", "", "sftp");
        remotePath = JconfigUtilContainer.bashConfig().getProperty("remotePath", "", "sftp");
    }

    public static boolean toPdf(String sids, HttpServletRequest request) {
        String domain = request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");
        boolean status = true;
        if (sids != null && sids.length() > 0) {
            String[] split = sids.split(",");
            for (String s : split) {
                Map<String, Object> data = new HashMap();
                SurveyBean surveyBean = SurveyService.getSurveyBean(s);
                if (surveyBean != null) {
                    data.put("s_name", surveyBean.getS_name());
                    data.put("description", surveyBean.getDescription());
                    data.put("add_time", surveyBean.getAdd_time());
                    data.put("publish_time", surveyBean.getPublish_time());
                    data.put("start_time", surveyBean.getStart_time());
                    data.put("end_time", surveyBean.getEnd_time());
                    GuiDangVo guiDangVo = new GuiDangVo();
                    LoginUserBean loginUserBean = (LoginUserBean) SessionManager.get(request, "cicro_wcm_user");
                    guiDangVo.setBuscode(String.valueOf(loginUserBean.getDept_id()));
                    guiDangVo.setCurcode(String.valueOf(loginUserBean.getDept_id()));
                    guiDangVo.setUsercode(String.valueOf(loginUserBean.getUser_id()));
                    guiDangVo.setYwbh("MH_" + surveyBean.getS_id());
                    guiDangVo.setYwbt(surveyBean.getS_name());
                    guiDangVo.setLcmc("满意度问卷");
                    guiDangVo.setXxly("3");
                    guiDangVo.setCurdate(DateUtil.getString(new Date(), "yyyyMMdd"));
                    try {
                        data.put("content", completeHtmlTag(surveyBean.getSurvey_content(), domain));
                        String content = PdfUtil.freeMarkerRender(data, HTML);
                        String pdfName = surveyBean.getS_id() + ".pdf";
                        String pdfPath = localPath + pdfName;
                        PdfUtil.createPdf(content, pdfPath);
                        String attrFiles = pdfName;
                        guiDangVo.setFiles(attrFiles);
                        //上传文章生成的pdf到sftp服务器
                        SFTPUtils sftpUtils = new SFTPUtils();
                        sftpUtils.uploadFile(pdfName,pdfName);
                        guiDangVo.setFilepath(remotePath);
                        int i = GuiDangServiceClient.doService(guiDangVo);
                        if (i == 0) {
                            status = false;
                            break;
                        }
                        File file = new File(pdfPath);
                        file.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                        status = false;
                        break;
                    }
                }
            }
        }
        return status;
    }


    public static String completeHtmlTag(String html, String domain) {
//        System.out.println(html);
//        System.out.println("-----------------------------");
        html = html.replaceAll("\\.\\./images", "/sys/images");
        html = html.replaceAll("<img(.*?)src=\"/((?!http|hppts).*?)", "<img$1src=\"" + domain + "/");
        html = html.replaceAll("<img(.*?)src=\'/((?!http|hppts).*?)", "<img$1src=\'" + domain + "/");
        String regxpForImgTag = "<img\\s*(.*?)[^>]*?>";
        Pattern pattern = Pattern.compile(regxpForImgTag);
        Matcher matcher = pattern.matcher(html);
        boolean isFirst = true;
        while (matcher.find()) {
            String temp = matcher.group();
            if (temp.indexOf("sys/images/pro_fore.png") >= 0) {
                if (isFirst) {
                    html = html.replaceAll(temp, "");
                    isFirst = false;
                }
            } else {
                html = html.replace(temp, temp.replace(">", "></img>"));
            }
        }

        //去掉调查中的特殊符号，因为不能转为pdf
        String regxpForInputTag = "<input\\s*(.*?)[^>]*?>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, "");
        }
        html = html.replaceAll("<br/>", "<br></br>");
        html = html.replaceAll("5票", "");   //投票单选、多选中去掉票数
        html = html.replaceAll("\\(20%\\)", "");    //投票单选、多选中去掉图片
        html = html.replaceAll("</div><div id=\"title_span\">", "");    //每个题目的标题折行
        regxpForInputTag = "<div id=\"s_name_show\"\\s*(.*?)>(.*?)</div>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, "");
        }

        regxpForInputTag = "<div id=\"s_description_show\"\\s*(.*?)>(.*?)</div>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, "");
        }

        regxpForInputTag = "<ul id=\"item_ul\">(.*?)</ul>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, temp.replaceAll("<ul id=\"item_ul\">", "").replaceAll("</ul>", ""));
        }

        regxpForInputTag = "<li class=\"li_css1\">(.*?)</li>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, temp.replaceAll("<li class=\"li_css1\">", "").replaceAll("</li>", ""));
        }
        regxpForInputTag = "<select id=\"(.*?)\" name=\"(.*?)\" class=\"input_select\">(.*?)</select>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, temp.replaceAll("<select id=\"(.*?)\" name=\"(.*?)\" class=\"input_select\">", "").replaceAll("</select>", ""));
        }
        regxpForInputTag = "<option value=\"(.*?)\" is_update=\"true\">(.*?)</option>";
        pattern = Pattern.compile(regxpForInputTag);
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, temp.replaceAll("<option value=\"(.*?)\" is_update=\"true\">", "").replaceAll("</option>", " "));
        }
//        System.out.println(html);
//        System.out.println("-------------------------------");
        return html;
    }


}


