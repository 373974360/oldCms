package com.yinhai.pdf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deya.util.DateUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.bean.survey.SurveyBean;
import com.deya.wcm.services.org.user.SessionManager;
import com.deya.wcm.services.survey.SurveyService;
import com.yinhai.model.GuiDangVo;
import com.yinhai.sftp.SFTPUtils;
import com.yinhai.webservice.client.GuiDangServiceClient;
import org.apache.commons.lang3.StringUtils;

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
    private static final String STEP_HTML = "question_step_template.html";
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
                    data.put("depname", surveyBean.getDepname());//发起部门
                    data.put("creator", surveyBean.getCreator());//发起人
                    data.put("create_date", surveyBean.getCreate_date());//发起时间
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

                        //问卷信息生成PDF
                        data.put("content", completeHtmlTag(surveyBean.getSurvey_content(), domain));
                        String content = PdfUtil.freeMarkerRender(data, HTML);
                        String pdfName = surveyBean.getS_id() + ".pdf";
                        String pdfPath = localPath + pdfName;
                        PdfUtil.createPdf(content, pdfPath);


                        //问卷流程生成PDF
                        String stepPdfName="";
                        String stepPdfPath="";
                        String curInfo = surveyBean.getCurinfo();
                        if(StringUtils.isNotEmpty(curInfo)){
                            curInfo = curInfo.replaceAll(" ","");
                            curInfo = curInfo.replaceAll("=",":\"");
                            curInfo = curInfo.replaceAll("result","\"result\"");
                            curInfo = curInfo.replaceAll(",curname","\",\"curname\"");
                            curInfo = curInfo.replaceAll(",curdepname","\",\"curdepname\"");
                            curInfo = curInfo.replaceAll(",nextstepname","\",\"nextbumc\"");
                            curInfo = curInfo.replaceAll(",stepname","\",\"stepname\"");
                            curInfo = curInfo.replaceAll(",remark","\",\"remark\"");
                            curInfo = curInfo.replaceAll(",curdate","\",\"curdate\"");
                            curInfo = curInfo.replaceAll(",curloginid","\",\"curloginid\"");
                            curInfo = curInfo.replaceAll("}","\"}");

                            String stepHtml = "";
                            JSONArray array = JSONArray.parseArray(curInfo);
                            if(array!=null&&array.size()>0){
                                for (int i = 0; i < array.size(); i++) {
                                    JSONObject jo = array.getJSONObject(i);
                                    stepHtml+="<tr><td>"+jo.getString("stepname")+"</td><td>"+jo.getString("result")+"</td><td colspan='3'>"+jo.getString("remark")+"</td><td>"+jo.getString("curname")+" "+jo.getString("curdate")+"</td></tr>";
                                }
                                data.put("stepHtml",stepHtml);
                                String stepContent = PdfUtil.freeMarkerRender(data, STEP_HTML);
                                stepPdfName = surveyBean.getS_id() + "_step.pdf";
                                stepPdfPath = localPath + stepPdfName;
                                PdfUtil.createPdf(stepContent, stepPdfPath);
                            }
                        }



                        String attrFiles = pdfName;
                        if(StringUtils.isNotEmpty(stepPdfName)){
                            attrFiles+="|"+stepPdfName;
                        }
                        if(StringUtils.isNotEmpty(surveyBean.getFile_path())){
                            attrFiles += "|"+surveyBean.getFile_path();
                        }

                        SFTPUtils sftpUtils = new SFTPUtils();

                        //上传线下分析报告
                        if(StringUtils.isNotEmpty(surveyBean.getFile_path_fxbg())){
                            String filepath = surveyBean.getFile_path_fxbg();
                            String fileext = filepath.substring(filepath.lastIndexOf("."),filepath.length());
                            String remoteName = surveyBean.getS_id() + "_xxfxbg"+fileext;
                            sftpUtils.uploadFile2(remoteName,filepath);
                            attrFiles+="|"+remoteName;
                        }

                        //上传文章生成的pdf到sftp服务器
                        sftpUtils.uploadFile(pdfName,pdfName);

                        //上传审批流程
                        if(StringUtils.isNotEmpty(stepPdfName)){
                            sftpUtils.uploadFile(stepPdfName,stepPdfName);
                        }
                        guiDangVo.setFiles(attrFiles);
                        guiDangVo.setFilepath(remotePath);
                        int i = GuiDangServiceClient.doService(guiDangVo);
                        if (i == 0) {
                            status = false;
                            break;
                        }
                        File file = new File(pdfPath);
                        file.delete();

                        if(StringUtils.isNotEmpty(stepPdfPath)){
                            File stepFile = new File(stepPdfPath);
                            stepFile.delete();
                        }
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


