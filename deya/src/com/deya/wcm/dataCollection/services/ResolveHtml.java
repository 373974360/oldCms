package com.deya.wcm.dataCollection.services;

import com.deya.util.DateUtil;
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
import com.deya.wcm.services.zwgk.index.IndexManager;
import com.deya.wcm.services.zwgk.info.GKInfoManager;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ResolveHtml {
    static Logger logger = LoggerFactory.getLogger(ResolveHtml.class);


    /**
     * @param listPageStr       整个页面的 html
     * @param domain
     * @param containerSelector 容器的 selector
     * @param linkSelector      列表中链接的 selector
     * @param parentUrl
     * @return
     */
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

                if (r.equals("start"))
                    isSleep = false;
                else if (r.equals("pause"))
                    isSleep = true;
                else if (r.equals("end")) {
                    isStop = true;
                }

                if (isStop)
                    break;
                if (isSleep) {
                    try {
                        do {
                            r = CaijiBean.getMapString(String.valueOf(collBean.getRule_id()));
                            if (r == null) {
                                r = "start";
                            }

                            if (r.equals("start"))
                                isSleep = false;
                            else if (r.equals("pause"))
                                isSleep = true;
                            else if (r.equals("end")) {
                                isStop = true;
                            }

                            Thread.sleep(1000L);
                            if (isStop) break;
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
            if ((FormatString.strIsNull(collBean.getInfotitle_start())) &&
                    (FormatString.strIsNull(collBean.getInfotitle_end()))) {

//                atrTitle =
//                        StringUtils.substringBetween(contentHtmlStr, collBean.getInfotitle_start().trim(),
//                                collBean.getInfotitle_end().trim());

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
//                atrFrom = StringUtils.substringBetween(contentHtmlStr, collBean.getSource_start().trim(),
//                        collBean.getSource_end().trim());

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
//                pubTime =
//                        StringUtils.substringBetween(contentHtmlStr, collBean.getAddTime_start().trim(),
//                                collBean.getAddTime_end().trim());
                Element select = parse.select(collBean.getAddTime_start()).first();
                if (select != null) {
                    pubTime = select.text();
                }
                if (FormatString.strIsNull(pubTime)) {
                    pubTime = pubTime.replaceAll("年", "-");
                    pubTime = pubTime.replaceAll("月", "-");
                    pubTime = pubTime.replaceAll("日", "");
                    if (pubTime.length() > 16) {
                        pubTime = pubTime.substring(0, 16);
                    }
                    articleBean.setArt_pubTime(pubTime);
                }

            }

            String author = "";
            if ((FormatString.strIsNull(collBean.getAuthor_start())) && (FormatString.strIsNull(collBean.getAuthor_end()))) {
//                author = StringUtils.substringBetween(contentHtmlStr, collBean.getAuthor_start().trim(),
//                        collBean.getAuthor_end().trim());
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
//                keyWord = StringUtils.substringBetween(contentHtmlStr, collBean.getKeywords_start().trim(),
//                        collBean.getKeywords_end().trim());
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

            String artContent = "";
            if ((FormatString.strIsNull(collBean.getContent_start())) && (FormatString.strIsNull(collBean.getContent_end()))) {
//                artContent = StringUtils.substringBetween(contentHtmlStr, collBean.getContent_start().trim(),
//                        collBean.getContent_end().trim());
                Element contentDom = parse.select(collBean.getContent_start()).first();
                if (contentDom != null) {
                    artContent = contentDom.html();
                }
                if (FormatString.strIsNull(artContent)) {
                    artContent = artContent.trim();

                    if (collBean.getPic_isGet() == 1) { //是否抓取图片


                        Elements imgList = contentDom.select("img");

                        if (imgList.size() > 0) {
//                            String domain = FormatString.getSiteDomain(collBean.getSite_id());

                            Iterator<Element> iterator = imgList.iterator();

                            while (iterator.hasNext()) {
                                Element img = iterator.next();
                                String imgLink = img.attr("src");
                                if (imgLink.startsWith("http://10.")
                                        || imgLink.startsWith("http://192.168")) {
                                    logger.warn("跳过局域网图片抓取:" + imgLink);
                                    continue;
                                }
                                String imageFullUrl = getAbsUrl(url, imgLink);
                                System.out.println("图片路径--------------------------" + imageFullUrl);
//                                if (!imageFullUrl.startsWith("http://")) {
//                                    imageFullUrl = URLUtil.getDomainUrl(collBean.getColl_url()) + imageFullUrl;
//                                }
//                                imageFullUrl = FormatString.filterURL(imageFullUrl);


//                                String imageFullUrl = "http://www.shaanxizhongyan.com.cn/uploadimg/20120329104318763.gif";

                                try {
                                    URL imageURL = new URL(imageFullUrl);
                                    String relativeRootUrlPath = imageURL.getPath();
                                    if (DownHtmlUtil.writePicToLocal(imageFullUrl, collBean.getSite_id(), relativeRootUrlPath)) {
                                        String replacement = "/collArtPic" + relativeRootUrlPath;

                                        System.out.println("内容图片 url(" + imgLink + ") 替换为：" + replacement);
                                        artContent = artContent.replaceAll(imgLink, replacement);

//                                        //获取文件所在的 url path，不包括文件名
//                                        String resoue = imageFullUrl.substring(0, imageFullUrl.lastIndexOf("/"));
//                                        System.out.println("resoue:------------------------------" + resoue);
//                                        //取得排除域名后的 path
//                                        resoue = resoue.replace(URLUtil.getDomainUrl(resoue), "");
//
//                                        resoue = resoue.substring(0, resoue.lastIndexOf("/"));
//                                        System.out.println("resoue:------------------------------" + resoue);
//                                        if (StringUtils.isNotBlank(resoue)) {
//                                        }
                                    }
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
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

            if(articleBean.getModel_id().equals("12")){
                System.out.println("Art_title:"+articleBean.getArt_title());
                if ((FormatString.strIsNull(articleBean.getArt_title()))) {
                    addCollDataInfo(articleBean, url, collBean.getRule_id() + "");
                }
            }else{
                if ((FormatString.strIsNull(articleBean.getArt_title())) && (FormatString.strIsNull(articleBean.getArt_content()))) {
                    addCollDataInfo(articleBean, url, collBean.getRule_id() + "");
                }
            }
//            System.out.println(ToStringBuilder.reflectionToString(articleBean));
            return articleBean;
        }
        return null;
    }


    public static String getAbsUrl(String absolutePath, String relativePath) {
        try {
            URL absoluteUrl = new URL(absolutePath);
            URL parseUrl = new URL(absoluteUrl, relativePath);
            String s = parseUrl.toString();
            List<String> urlPart = new ArrayList<String>();
            String rootUrl = s.substring(0, s.indexOf("/", s.indexOf("//")));
            urlPart.add(rootUrl);
            for (String part : s.substring(s.indexOf("/", s.indexOf("//") + 1)).split("/")) {
                try {
                    urlPart.add(URLEncoder.encode(part, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            String resultUrl = StringUtils.join(urlPart, "/");
            return resultUrl;
        } catch (MalformedURLException e) {
            return "";
        }
    }

    public static void addCollDataInfo(ArticleInfoBean articleBean, String url, String rule_id) {
        String filePath = FormatString.getManagerPath();
        ArticleInfoBean artList = CollectionDataDAO.getCollDataInfobyUrl(url);
        if (artList != null && artList.getUrl().equals(url)) {
            logger.info("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息已经采集过，不再重复采集!");

        } else {
            if(articleBean.getModel_id().equals("12")){
                //默认写入已采用库
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
                    bean.setTitle(articleBean.getArt_title().replace("/g","＂"));
                    bean.setApp_id("cms");
                    bean.setSite_id(site_id);
                    bean.setModel_id(12);
                    bean.setReleased_dtime(articleBean.getArt_pubTime());
                    bean.setIs_am_tupage(0);
                    bean.setWeight(60);
                    bean.setContent_url(articleBean.getUrl());
                    Map<String,String> imap = IndexManager.getIndex(site_id,articleBean.getCat_id(),"","");
                    if(!imap.isEmpty()&&imap.containsKey("gk_index"))
                    {
                        bean.setGk_index(imap.get("gk_index"));
                    }
                    ModelUtil.insert(bean,"article",null);
                }
            }else{
                if (!CollectionDataManager.addCollDataInfo(articleBean)) {
                    logger.info("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息入库失败!");
                } else {
                    logger.info("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息入库成功!");
                }
            }
        }
    }

//    public static void main(String[] args) throws IOException {
//        System.out.println(getAbsUrl("http://zfgjj.hanzhong.gov.cn/Website/newsshow.jsp?id=304","../upload/images/1464081505266QQ图片20160524155323.jpg"));
//         String janPattern = "(0?[13578]|1[02])-(0?[1-9]|[12][0-9]|3[01])";
//         String febPattern = "0?2-(0?[1-9]|[12][0-9])";
//         String aprPattern = "(0?[469]|11)-(0?[1-9]|[12][0-9]|30)";
//         String dayPattern = String.format("^2[0-9]{3}-(%s|%s|%s)$", janPattern, febPattern, aprPattern);


//        System.out.println(a.);

        /*String urlStr = "http://www.lygjj.org.cn/files/2016files/2015年财务收支决算/4财政拨款收入支出决算总表.jpg";
        HttpURLConnection con;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            String savePath = "/Users/yangyan/1111.jpg";
            int code = con.getResponseCode();

            if (code == 200) {
                FileUtils.copyInputStreamToFile(con.getInputStream(), new File(savePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

//        System.out.println(parseDateFromString("2022/05/05"));


//        try {
//
//
//            URL url = new URL("http://www.shaanxizhongyan.com.cn/9572.html");
//
//            Document parse = Jsoup.parse(url, 50000);
//            Elements select = parse.select("#table55 > tbody > tr:nth-child(5) > td");
//            System.out.println(select);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }


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


    public static void main(String[] args) throws MalformedURLException {
        String urlstr = "http://www.shaanxizhongyan.com.cn/aaa/uploadimg/20120329104318763.gif";


        URL imageURL = new URL(urlstr);
        String path = imageURL.getPath();


        System.out.println(path);
        String artContent = "<td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"news\"></p><p style=\"TEXT-INDENT: 26px; LINE-HEIGHT: 1.5\" align=\"center\"><img src=\"/aaa/uploadimg/20120329104318763.gif\" border=\"0\"></p>\n" +
                "<p style=\"TEXT-INDENT: 26px; LINE-HEIGHT: 1.5\" align=\"center\"><img src=\"/uploadimg/20120329170949274.gif\" border=\"0\"></p>\n" +
                "<p style=\"TEXT-INDENT: 26px; LINE-HEIGHT: 1.5\">3月27至28日，陕西中烟在西安召开2012年纪检监察暨整顿规范工作会议，传达学习了全国烟草行业纪检监察及整顿规范工作会议精神，回顾总结全系统2011年党风廉政建设、反腐败及整顿规范工作，安排部署2012年反腐倡廉及整顿规范工作任务。公司党组书记、总经理陈晖主持会议并讲话，党组成员、纪检组长陈建利作工作报告。</p>\n" +
                "<p style=\"TEXT-INDENT: 26px; LINE-HEIGHT: 1.5\">陈晖指出，一要认真贯彻落实国家局纪检监察会议精神，严格按照姜成康局长讲话要求及潘家华组长六项重点工作部署，努力做到“三个始终”，牢固树立“五种意识”，突出重点，狠抓落实，切实保证政令畅通，坚决做到姜局长提出的“四个严禁”和潘组长提出的“六个严禁”。二是要进一步加强党员领导干部作风建设，深刻领会党的纯洁性的重大意义和总体要求，增强党员领导干部的纯洁性意识，加强源头治理，切实堵塞管理漏洞，规范权力运行，把制度建设作为保持党的纯洁性的重要保证。三是加强对党风廉政建设的领导，健全完善机制，提高反腐倡廉建设科学化水平。</p>\n" +
                "<p style=\"TEXT-INDENT: 26px; LINE-HEIGHT: 1.5\">针对当前和今后一个时期的整顿规范工作，陈晖提出明确要求，要统一思想，提高认识，高度重视整顿规范工作，真正把思想和行动统一到国家局党组的决策部署上来，真正理解整顿规范是行业“生命线”的重要意义，做到严格规范不动摇，主动规范不松懈，自觉规范保发展，坚持规范上水平。要突出公开招标，抓住重点环节，改进工作方法，切实抓好各项措施的落实。 </p>\n" +
                "<p style=\"TEXT-INDENT: 26px; LINE-HEIGHT: 1.5\">陈建利在工作报告中对今年纪检监察工作作了安排部署：要加强党的政治纪律监督检查，确保公司重大决策部署贯彻落实；加强领导干部作风建设，巩固作风建设年成果；落实党风廉政建设责任制，巩固反腐倡廉工作合力；强化宣传教育，铸牢反腐倡廉基础工作；加强源头治理，保障权力规范运行；抓好信访和查办案工作，发挥案件查办治本功效；加强队伍建设，不断提高履职能力。针对整顿规范工作，陈建利提出，要认真传达行业整顿规范现场会精神，提高对开展“两项工作”重要性的认识；要持续推进公开招标，认真落实应招尽招、能招尽招，提高招标实效；持续推进办事公开民主管理，认真落实干部职工“四权”；持续开展专项规范，点面结合抓好专项清理；持续贯彻落实工作规范，提高内部专卖管理监督工作水平；加强组织领导，严明工作纪律，合力推动各项工作措施有效落实。</p>\n" +
                "<p style=\"TEXT-INDENT: 26px; LINE-HEIGHT: 1.5\">会议传达学习了姜成康局长在全国烟草行业2012年纪检监察工作会议上的重要讲话、潘家华组长所作的工作报告及赵洪顺副局长在行业整顿规范工作现场会上的讲话。与会人员围绕讲话精神，结合系统改革发展和单位部门工作实际及《陕西中烟工业有限责任公司关于2012年加强内部管理监督和继续深入推进“两项工作”的安排意见》进行了分组讨论，理清了思路，明确了任务，坚定了信心。</p>\n" +
                "<p style=\"TEXT-INDENT: 26px; LINE-HEIGHT: 1.5\">会上，公司党组与各烟厂、材料厂、公司机关各部门主要负责人签订了2012年度党风廉政责任书。</p>\n" +
                "<p style=\"TEXT-INDENT: 26px; LINE-HEIGHT: 1.5\">公司党组成员、机关副处以上干部，各烟厂厂长、党委书记、纪检书记、工会主席、主管副厂长及有关部门负责人共130人参加了会议。</p></td>";


        System.out.println("Filename: " + FormatString.getPicName(urlstr));

        String resoue = urlstr.substring(0, urlstr.lastIndexOf("/"));
        System.out.println("resoue:------------------------------" + resoue);
        String relativePath = resoue = resoue.replaceAll(URLUtil.getDomainUrl(resoue), "");
        resoue = resoue.substring(0, resoue.lastIndexOf("/"));
        System.out.println("resoue:------------------------------" + resoue);


        if (StringUtils.isNotBlank(resoue)) {
            artContent = artContent.replaceAll(resoue, "/collArtPic");
        } else {
            artContent = artContent.replaceAll(resoue, "/collArtPic" + relativePath);
        }
        System.out.println(artContent);
    }
}
