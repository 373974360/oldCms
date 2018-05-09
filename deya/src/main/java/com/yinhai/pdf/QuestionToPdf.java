package com.yinhai.pdf;

import com.deya.util.DateUtil;
import com.deya.util.UploadManager;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.cms.info.PicBean;
import com.deya.wcm.bean.cms.info.VideoBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.bean.survey.SurveyBean;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.org.user.SessionManager;
import com.deya.wcm.services.survey.SurveyService;
import com.deya.wcm.template.velocity.data.InfoUtilData;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.yinhai.model.GuiDangVo;
import com.yinhai.sftp.UpLoadFile;
import com.yinhai.webservice.client.GuiDangServiceClient;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
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


public class QuestionToPdf {

    private static final String FONT = "font/wryh.ttf";
    private static final String HTML = "question_template.html";
    private static String dest = "";
    private static Configuration freemarkerCfg = null;
    private static String ip = "";
    private static int port = 0;
    private static String user = "";
    private static String pwd = "";
    private static String dpath = "";

    static {
        freemarkerCfg = new Configuration();
        dest = JconfigUtilContainer.bashConfig().getProperty("pdfdir", "", "sftp");
        ip = JconfigUtilContainer.bashConfig().getProperty("ip", "", "sftp");
        port = Integer.parseInt(JconfigUtilContainer.bashConfig().getProperty("port", "", "sftp"));
        user = JconfigUtilContainer.bashConfig().getProperty("user", "", "sftp");
        pwd = JconfigUtilContainer.bashConfig().getProperty("password", "", "sftp");
        dpath = JconfigUtilContainer.bashConfig().getProperty("dpath", "", "sftp");
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath() + "/template"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    guiDangVo.setCurdate(DateUtil.getString(new Date(), "yyyyMMdd"));
                    try {
                        data.put("content", completeHtmlTag(surveyBean.getSurvey_content(), domain));
                        String content = freeMarkerRender(data, HTML);
                        String pdfName = surveyBean.getS_id() + ".pdf";
                        String pdfPath = dest + pdfName;
                        createPdf(content, pdfPath);
                        String attrFiles = pdfName;
                        guiDangVo.setFiles(attrFiles);
                        //上传文章生成的pdf到sftp服务器
                        UpLoadFile.sshSftpUpload(ip, user, pwd, port, pdfPath, dpath);
                        guiDangVo.setFilepath(dpath);
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

    public static void createPdf(String content, String dest) throws IOException, com.itextpdf.text.DocumentException {
        // step 1
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(content.getBytes()), null, Charset.forName("UTF-8"), fontImp);
        // step 5
        document.close();

    }

    /**
     * freemarker渲染html
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTmp);
            template.setEncoding("UTF-8");
            // 合并数据模型与模板
            template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
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


