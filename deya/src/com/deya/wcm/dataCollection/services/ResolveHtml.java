package com.deya.wcm.dataCollection.services;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.info.ArticleBean;
import com.deya.wcm.dataCollection.bean.ArticleInfoBean;
import com.deya.wcm.dataCollection.bean.CollRuleBean;
import com.deya.wcm.dataCollection.dao.CollectionDataDAO;
import com.deya.wcm.dataCollection.util.DownHtmlUtil;
import com.deya.wcm.dataCollection.util.FormatDate;
import com.deya.wcm.dataCollection.util.FormatString;
import com.deya.wcm.dataCollection.util.URLUtil;
import com.deya.wcm.services.cms.category.CategoryRPC;
import com.deya.wcm.services.cms.info.InfoBaseRPC;
import com.deya.wcm.services.cms.info.ModelUtil;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolveHtml {
    static Logger logger = LoggerFactory.getLogger(ResolveHtml.class);

    public static LinkedHashSet<String> ResolveHtmlForLink(String listPageStr, String domain, String containerSelector, String linkSelector, String parentUrl) {
        LinkedHashSet waitResolveInfoSet = new LinkedHashSet();

        containerSelector = containerSelector.trim();
        linkSelector = linkSelector.trim();
        Document doc = Jsoup.parse(listPageStr);
        Elements list = doc.select(containerSelector);
        Elements links = list.select(linkSelector);
        for (Element link : links) {
            String linkHref = link.attr("href");
            if (FormatString.strIsNull(linkHref)) {
                String waitLink = URLUtil.formatLink(parentUrl, linkHref, domain);
                if (FormatString.strIsNull(waitLink)) {
                    waitResolveInfoSet.add(waitLink);
                }
            }
        }
        return waitResolveInfoSet;
    }

    public static void getArticleInfoHtml(CollRuleBean collBean, LinkedHashSet<String> waitgetArtInfoSet) {
        if ((waitgetArtInfoSet != null) && (waitgetArtInfoSet.size() > 0)) {
            for (int i = waitgetArtInfoSet.size(); i > 0; i--) {
                boolean isSleep = false;
                boolean isStop = false;
                String r = "start";

                r = CaijiBean.getMapString(String.valueOf(collBean.getRule_id()));
                if (r == null) {
                    r = "start";
                }
                if (r.equals("start")) {
                    isSleep = false;
                } else if (r.equals("pause")) {
                    isSleep = true;
                } else if (r.equals("end")) {
                    isStop = true;
                }
                if (isStop) {
                    break;
                }
                if (isSleep) {
                    try {
                        do {
                            r = CaijiBean.getMapString(String.valueOf(collBean.getRule_id()));
                            if (r == null) {
                                r = "start";
                            }
                            if (r.equals("start")) {
                                isSleep = false;
                            } else if (r.equals("pause")) {
                                isSleep = true;
                            } else if (r.equals("end")) {
                                isStop = true;
                            }
                            Thread.sleep(1000L);
                            if (isStop) {
                                break;
                            }
                        } while (isSleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i--;
                } else {
                    String url = (String) waitgetArtInfoSet.iterator().next();
                    String contentHtml = DownHtmlUtil.downLoadHtml(url, collBean.getPageEncoding());

                    waitgetArtInfoSet.remove(url);
                    if (FormatString.strIsNull(contentHtml)) {
                        getArticleInfo(collBean, contentHtml, url);
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static ArticleInfoBean getArticleInfo(CollRuleBean collBean, String contentHtmlStr, String url) {
        if (contentHtmlStr.length() > 0) {
            Document parse = Jsoup.parse(contentHtmlStr);

            ArticleInfoBean articleBean = new ArticleInfoBean();
            articleBean.setCat_id(collBean.getCat_id());
            articleBean.setRule_id(collBean.getRule_id());
            articleBean.setColl_time(DateUtil.getCurrentDateTime());
            articleBean.setUrl(url);
            articleBean.setModel_id(collBean.getModel_id());

            String atrTitle = "";
            if ((FormatString.strIsNull(collBean.getInfotitle_start())) && (FormatString.strIsNull(collBean.getInfotitle_end()))) {
                Element select = parse.select(collBean.getInfotitle_start()).first();
                if (select != null) {
                    atrTitle = select.text();
                }
                if (FormatString.strIsNull(atrTitle)) {
                    atrTitle = FormatString.filterTitleKeyWord(atrTitle);
                    articleBean.setArt_title(atrTitle.trim());
                }
            }
            String atrFrom = "";
            if ((FormatString.strIsNull(collBean.getSource_start())) && (FormatString.strIsNull(collBean.getSource_end()))) {
                Element select = parse.select(collBean.getSource_start()).first();
                if (select != null) {
                    atrFrom = select.text();
                }
                if (FormatString.strIsNull(atrFrom)) {
                    atrFrom = FormatString.filterHtmlTag(atrFrom);
                    atrFrom = FormatString.filterStrForKeyword(atrFrom);
                    articleBean.setArt_source(atrFrom.trim());
                }
            }
            String pubTime = "";
            if ((FormatString.strIsNull(collBean.getAddTime_start())) && (FormatString.strIsNull(collBean.getAddTime_end()))) {
                Element select = parse.select(collBean.getAddTime_start()).first();
                if (select != null) {
                    pubTime = select.text();
                }
                if (FormatString.strIsNull(pubTime)) {
                    pubTime = getTimeStr(pubTime);

                    pubTime = pubTime.replaceAll("-", "/");
                    pubTime = pubTime.replaceAll("年", "/");
                    pubTime = pubTime.replaceAll("月", "/");
                    pubTime = pubTime.replaceAll("日", "/");

                    pubTime = parseDateFromString(pubTime);


                    articleBean.setArt_pubTime(FormatDate.formatTime(pubTime, "yyyy/MM/dd"));
                }
            }
            String author = "";
            if ((FormatString.strIsNull(collBean.getAuthor_start())) && (FormatString.strIsNull(collBean.getAuthor_end()))) {
                Element select = parse.select(collBean.getAuthor_start()).first();
                if (select != null) {
                    author = select.text();
                }
                if (FormatString.strIsNull(author)) {
                    author = FormatString.filterHtmlTag(author);
                    author = FormatString.filterStrForKeyword(author);
                    articleBean.setArt_author(author.trim());
                }
            }
            String keyWord = "";
            if ((FormatString.strIsNull(collBean.getKeywords_start())) && (FormatString.strIsNull(collBean.getKeywords_end()))) {
                Element select = parse.select(collBean.getKeywords_start()).first();
                if (select != null) {
                    keyWord = select.text();
                }
                if (FormatString.strIsNull(keyWord)) {
                    keyWord = FormatString.filterHtmlTag(keyWord);
                    keyWord = FormatString.filterStrForKeyword(keyWord);
                    articleBean.setArt_keyWords(keyWord.trim());
                }
            }
            String docNo = "";
            if ((FormatString.strIsNull(collBean.getDocNo_start())) && (FormatString.strIsNull(collBean.getDocNo_end()))) {
                Element select = parse.select(collBean.getDocNo_start()).first();
                if (select != null) {
                    docNo = select.text();
                }
                if (FormatString.strIsNull(docNo)) {
                    docNo = FormatString.filterHtmlTag(docNo);
                    docNo = FormatString.filterStrForKeyword(docNo);
                    articleBean.setArt_docNo(docNo.trim());
                }
            }
            String gk_signer_dtime = "";
            if ((FormatString.strIsNull(collBean.getSigner_dtime_start())) && (FormatString.strIsNull(collBean.getSigner_dtime_end()))) {
                Element select = parse.select(collBean.getSigner_dtime_start()).first();
                if (select != null) {
                    gk_signer_dtime = select.text();
                }
                if (FormatString.strIsNull(gk_signer_dtime)) {
                    gk_signer_dtime = FormatString.filterHtmlTag(gk_signer_dtime);
                    gk_signer_dtime = FormatString.filterStrForKeyword(gk_signer_dtime);
                    articleBean.setGk_signer_dtime(gk_signer_dtime.trim());
                }
            }
            String gk_input_dept = "";
            if ((FormatString.strIsNull(collBean.getInput_dept_start())) && (FormatString.strIsNull(collBean.getInput_dept_end()))) {
                Element select = parse.select(collBean.getInput_dept_start()).first();
                if (select != null) {
                    gk_input_dept = select.text();
                }
                if (FormatString.strIsNull(gk_input_dept)) {
                    gk_input_dept = FormatString.filterHtmlTag(gk_input_dept);
                    gk_input_dept = FormatString.filterStrForKeyword(gk_input_dept);
                    articleBean.setGk_input_dept(gk_input_dept.trim());
                }
            }
            String artContent = "";
            if ((FormatString.strIsNull(collBean.getContent_start())) && (FormatString.strIsNull(collBean.getContent_end()))) {
                Element contentDom = parse.select(collBean.getContent_start()).first();
                if (contentDom != null) {
                    artContent = contentDom.html();
                }
                if (FormatString.strIsNull(artContent)) {
                    artContent = artContent.trim();
                    if (collBean.getPic_isGet() == 1) {
                        Elements imgList = contentDom.select("img");
                        if (imgList.size() > 0) {
                            Iterator<Element> iterator = imgList.iterator();
                            while (iterator.hasNext()) {
                                Element img = (Element) iterator.next();
                                String imgLink = img.attr("src");
                                if ((imgLink.startsWith("http://10.")) || (imgLink.startsWith("http://192.168"))) {
                                    logger.warn("跳过局域网图片抓取:" + imgLink);
                                } else {
                                    String imageFullUrl = getAbsUrl(url, imgLink);
                                    System.out.println("图片路径--------------------------" + imageFullUrl);
                                    try {
                                        URL imageURL = new URL(imageFullUrl);
                                        String relativeRootUrlPath = imageURL.getPath();
                                        if (DownHtmlUtil.writePicToLocal(imageFullUrl, collBean.getSite_id(), relativeRootUrlPath)) {
                                            String replacement = "/collArtPic" + relativeRootUrlPath;

                                            System.out.println("内容图片 url(" + imgLink + ") 替换为：" + replacement);
                                            artContent = artContent.replaceAll(imgLink, replacement);
                                        }
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            articleBean.setArt_content(artContent);
                        } else {
                            articleBean.setArt_content(artContent);
                        }
                    } else {
                        articleBean.setArt_content(artContent);
                    }
                }
            }
            if (FormatString.strIsNull(articleBean.getArt_title())) {
                addCollDataInfo(articleBean, url, collBean.getRule_id() + "");
            }
            return articleBean;
        }
        return null;
    }

    public static String getAbsUrl(String absolutePath, String relativePath) {
        try {
            URL absoluteUrl = new URL(absolutePath);
            URL parseUrl = new URL(absoluteUrl, relativePath);
            String s = parseUrl.toString();
            List<String> urlPart = new ArrayList();
            String rootUrl = s.substring(0, s.indexOf("/", s.indexOf("//")));
            urlPart.add(rootUrl);
            for (String part : s.substring(s.indexOf("/", s.indexOf("//") + 1)).split("/")) {
                try {
                    urlPart.add(URLEncoder.encode(part, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return StringUtils.join(urlPart, "/");
        } catch (MalformedURLException e) {
        }
        return "";
    }

    public static void addCollDataInfo(ArticleInfoBean articleBean, String url, String rule_id) {
        String filePath = FormatString.getManagerPath();
        ArticleInfoBean artList = CollectionDataDAO.getCollDataInfobyUrl(url);
        if ((artList != null) && (artList.getUrl().equals(url))) {
            logger.info("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息已经采集过，不再重复采集!");
        } else if (articleBean.getModel_id().equals("12")) {
            articleBean.setArtis_user(1);
            if (!CollectionDataManager.addCollDataInfo(articleBean)) {
                logger.info("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息入库失败!");
            } else {
                logger.info("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息入库成功!");
                ArticleBean bean = new ArticleBean();
                bean.setId(InfoBaseRPC.getInfoId());
                bean.setInfo_id(InfoBaseRPC.getInfoId());
                bean.setInfo_status(8);
                bean.setInfo_content(articleBean.getArt_content());
                bean.setPage_count(1);
                bean.setCat_id(Integer.parseInt(articleBean.getCat_id()));
                String site_id = CategoryRPC.getCategoryBean(Integer.parseInt(articleBean.getCat_id())).getSite_id();
                bean.setTitle(articleBean.getArt_title().replace("/g", "＂"));
                bean.setApp_id("cms");
                bean.setSite_id(site_id);
                bean.setModel_id(12);
                bean.setReleased_dtime(articleBean.getArt_pubTime());
                bean.setIs_am_tupage(0);
                bean.setWeight(60);
                bean.setContent_url(articleBean.getUrl());
                ModelUtil.insert(bean, "article", null);
            }
        } else if (!CollectionDataManager.addCollDataInfo(articleBean)) {
            logger.info("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息入库失败!");
        } else {
            logger.info("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息入库成功!");
        }
    }

    private static String parseDateFromString(String string) {
        if (StringUtils.isBlank(string)) {
            return "";
        }
        try {
            Pattern compile = Pattern.compile("20\\d{2}/\\d{1,2}/\\d{1,2}");
            Matcher matcher = compile.matcher(string);
            if (matcher.find()) {
                return matcher.group();
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public static String getTimeStr(String str){
        str = str.replaceAll("[^x00-xff]*", "");
        if(str.length()>=8){
            str = str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8)+" "+str.substring(8,str.length());
        }
        return str;
    }


    public static void main(String[] args) {
        String str = "发布时间：2017-07-06 22:00:00:333 来源：渭南日报浏览次数：";
        //String gbStr = toLength(str, 8);
        str = getTimeStr(str);
        System.out.print(str);
    }
}
