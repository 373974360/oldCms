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
import com.yinhai.model.GuiDangVo;
import com.yinhai.sftp.SFTPUtils;
import com.yinhai.webservice.client.GuiDangServiceClient;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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

    private static final String HTML = "article_template.html";
    private static String localPath = "";
    private static String remotePath = "";
    private static final String files[] = {".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".rar", ".zip", ".pdf", ".txt"};

    static {
        localPath = JconfigUtilContainer.bashConfig().getProperty("localPath", "", "sftp");
        remotePath = JconfigUtilContainer.bashConfig().getProperty("remotePath", "", "sftp");
    }

    public static boolean toPdf(HttpServletRequest request, String info_ids) {
        String domain = request.getRequestURL().toString().replaceAll(request.getRequestURI(), "");
        boolean status = true;
        if (info_ids != null && info_ids.length() > 0) {
            String[] split = info_ids.split(",");
            for (String s : split) {
                Map<String, Object> data = new HashMap();
                InfoBean ib = InfoBaseManager.getInfoById(s);
                if (ib != null) {
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
                    guiDangVo.setXxly("4");
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
                        String content = PdfUtil.freeMarkerRender(data, HTML);
                        String pdfName = ib.getInfo_id() + ".pdf";
                        String pdfPath = localPath + pdfName;
                        PdfUtil.createPdf(content, pdfPath);
                        //上传文章生成的pdf到sftp服务器
                        SFTPUtils sftpUtils = new SFTPUtils();
                        sftpUtils.uploadFile(pdfName, pdfName);
                        List<String> fileUrl = findFileUrl(content, ib.getSite_id());
                        String attrFiles = pdfName;
                        if (fileUrl.size() > 0) {
                            for (String s1 : fileUrl) {
                                String fileName = s1.substring(s1.lastIndexOf("/") + 1, s1.length());
                                sftpUtils.uploadFile(fileName, fileName);
                                attrFiles += "|" + fileName;
                            }
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
