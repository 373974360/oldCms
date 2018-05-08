package com.yinhai.pdf;

import com.deya.util.DateUtil;
import com.deya.util.UploadManager;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.cms.info.PicBean;
import com.deya.wcm.bean.cms.info.VideoBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.org.user.SessionManager;
import com.deya.wcm.template.velocity.data.InfoUtilData;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.yinhai.model.GuiDangVo;
import com.yinhai.sftp.UpLoadFile;
import com.yinhai.webservice.client.GuiDangServiceClient;
import freemarker.template.Configuration;
import freemarker.template.Template;

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


public class ArticleToPdf {

    private static final String FONT = "font/wryh.ttf";
    private static final String HTML = "article_template.html";
    private static String dest = "";
    private static Configuration freemarkerCfg = null;
    private static String ip = "";
    private static int port = 0;
    private static String user = "";
    private static String pwd = "";
    private static String dpath = "";
    private static final String files[] = {".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".rar", ".zip", ".pdf", ".txt"};

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

    public static boolean backInfo(HttpServletRequest request, String info_ids) {
        String domain = request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");
        boolean status = true;
        if (info_ids != null && info_ids.length() > 0) {
            String[] split = info_ids.split(",");
            for (String s : split) {
                Map<String, Object> data = new HashMap();
                InfoBean ib = InfoBaseManager.getInfoById(s);
                data.put("top_title", ib.getTop_title());
                data.put("title", ib.getTitle());
                data.put("sub_title", ib.getSubtitle());
                data.put("author", ib.getAuthor());
                data.put("publish_time", ib.getReleased_dtime());
                data.put("hits", ib.getHits());
                GuiDangVo guiDangVo = new GuiDangVo();
                LoginUserBean loginUserBean = (LoginUserBean) SessionManager.get(request, "cicro_wcm_user");
                guiDangVo.setBuscode(String.valueOf(loginUserBean.getDept_id()));
                guiDangVo.setCurcode(String.valueOf(loginUserBean.getDept_id()));
                guiDangVo.setUsercode(String.valueOf(loginUserBean.getUser_id()));
                guiDangVo.setYwbh("MH_" + ib.getInfo_id());
                guiDangVo.setYwbt(ib.getTitle());
                guiDangVo.setLcmc("信息发布");
                guiDangVo.setCurdate(DateUtil.getString(new Date(), "yyyyMMdd"));
                try {
                    if (ib.getModel_id() == 11) {
                        ArticleBean articleBean = InfoUtilData.getArticleObject(s);
                        data.put("content", completeHtmlTag(articleBean.getInfo_content(), domain));
                    } else if (ib.getModel_id() == 10) {
                        PicBean articleBean = InfoUtilData.getPicObject(s);
                        data.put("content", completeHtmlTag(articleBean.getPic_content(), domain));
                    } else if (ib.getModel_id() == 13) {
                        VideoBean articleBean = InfoUtilData.getVideoObject(s);
                        data.put("content", completeHtmlTag(articleBean.getInfo_content(), domain));
                    } else if (ib.getModel_id() == 12) {
                        data.put("content", ib.getContent_url());
                    } else {
                        Map articleCustomObject = InfoUtilData.getArticleCustomObject(s);
                    }
                    String content = freeMarkerRender(data, HTML);
                    String pdfName = ib.getInfo_id() + ".pdf";
                    String pdfPath = dest + pdfName;
                    createPdf(content, pdfPath);
                    List<String> fileUrl = findFileUrl(content, ib.getSite_id());
                    String attrFiles = pdfName;
                    if (fileUrl.size() > 0) {
                        for (String s1 : fileUrl) {
                            String fileName = s1.substring(s1.lastIndexOf("/") + 1, s1.length());
                            UpLoadFile.sshSftpUpload(ip, user, pwd, port, s1, dpath);
                            attrFiles += "|" + fileName;
                        }
                    }
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

    public static List<String> findFileUrl(String html, String site_id) {
        List<String> urls = new ArrayList<>();
        //匹配img标签的正则表达式
        String regxpForHref = "href=\"([^\"]*)\"";
        Pattern pattern = Pattern.compile(regxpForHref);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group(1);
            String suffix = temp.substring(temp.lastIndexOf("."), temp.length());
            for (String file : files) {
                if (suffix.toLowerCase().equals(file)) {
                    String uploadPath = UploadManager.getUploadSavePath(site_id) + temp;
                    urls.add(uploadPath);
                }
            }
        }
        return urls;
    }

    public static String completeHtmlTag(String html, String domain) {
//        System.out.println(html);
//        System.out.println("-----------------------------");
        html = html.replaceAll("<img(.*?)src=\"((?!http|hppts).*?)", "<img$1src=\"" + domain);
        html = html.replaceAll("<img(.*?)src=\'((?!http|hppts).*?)", "<img$1src=\'" + domain);
        String regxpForImgTag = "<img\\s[^>]+/>";
        Pattern pattern = Pattern.compile(regxpForImgTag);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replaceAll(temp, temp.replaceAll("/>", "></img>"));
        }
        html = html.replaceAll("<br/>", "<br></br>");
//        System.out.println(html);
        return html;
    }


}
