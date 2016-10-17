package com.deya.wcm.dataCollection.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.deya.util.DateUtil;
import com.deya.wcm.dataCollection.bean.ArticleInfoBean;
import com.deya.wcm.dataCollection.bean.CollRuleBean;
import com.deya.wcm.dataCollection.dao.CollectionDataDAO;
import com.deya.wcm.dataCollection.util.DownHtmlUtil;
import com.deya.wcm.dataCollection.util.FormatDate;
import com.deya.wcm.dataCollection.util.FormatString;
import com.deya.wcm.dataCollection.util.URLUtil;


public class ResolveHtml {
    public static LinkedHashSet<String> ResolveHtmlForLink(String listPageStr, String domain, String beginlistTag, String endlistTag) {
        LinkedHashSet waitResolveInfoSet = new LinkedHashSet();

        beginlistTag = beginlistTag.trim();
        endlistTag = endlistTag.trim();
        String tempStr = StringUtils.substringBetween(listPageStr, beginlistTag, endlistTag);
        String newHtml = DownHtmlUtil.buildNewHtml(tempStr);

        Document doc = Jsoup.parse(newHtml);
        Elements links = doc.getElementsByTag("A");
        for (Element link : links) {
            String linkHref = link.attr("href");
            if (FormatString.strIsNull(linkHref)) {
                String waitLink = URLUtil.formatLink(linkHref, domain);
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
                    String contentHtml =
                            DownHtmlUtil.downLoadHtml(url, collBean.getPageEncoding());
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

    public static void getArticleInfo(CollRuleBean collBean, String contentHtmlStr, String url) {
        if (contentHtmlStr.length() > 0) {
            ArticleInfoBean articleBean = new ArticleInfoBean();
            articleBean.setCat_id(collBean.getCat_id());
            articleBean.setRule_id(collBean.getRule_id());
            articleBean.setColl_time(DateUtil.getCurrentDateTime());
            articleBean.setUrl(url);
            articleBean.setModel_id(collBean.getModel_id());

            String atrTitle = "";
            if ((FormatString.strIsNull(collBean.getInfotitle_start())) &&
                    (FormatString.strIsNull(collBean.getInfotitle_end()))) {
                atrTitle =
                        StringUtils.substringBetween(contentHtmlStr, collBean.getInfotitle_start().trim(),
                                collBean.getInfotitle_end().trim());
                if (FormatString.strIsNull(atrTitle)) {
                    atrTitle = FormatString.filterTitleKeyWord(atrTitle);

                    articleBean.setArt_title(atrTitle.trim());
                }

            }

            String atrFrom = "";
            if ((FormatString.strIsNull(collBean.getSource_start())) && (FormatString.strIsNull(collBean.getSource_end()))) {
                atrFrom = StringUtils.substringBetween(contentHtmlStr, collBean.getSource_start().trim(),
                        collBean.getSource_end().trim());
                if (FormatString.strIsNull(atrFrom)) {
                    atrFrom = FormatString.filterHtmlTag(atrFrom);
                    atrFrom = FormatString.filterStrForKeyword(atrFrom);
                    articleBean.setArt_source(atrFrom.trim());
                }

            }

            String pubTime = "";
            if ((FormatString.strIsNull(collBean.getAddTime_start())) && (FormatString.strIsNull(collBean.getAddTime_end()))) {
                pubTime =
                        StringUtils.substringBetween(contentHtmlStr, collBean.getAddTime_start().trim(),
                                collBean.getAddTime_end().trim());
                if (FormatString.strIsNull(pubTime)) {
                    pubTime = FormatString.filterHtmlTag(pubTime);
                    pubTime = FormatString.filterStrForKeyword(pubTime);
                    articleBean.setArt_pubTime(FormatDate.formatTime(pubTime).trim());
                }

            }

            String author = "";
            if ((FormatString.strIsNull(collBean.getAuthor_start())) && (FormatString.strIsNull(collBean.getAuthor_end()))) {
                author = StringUtils.substringBetween(contentHtmlStr, collBean.getAuthor_start().trim(),
                        collBean.getAuthor_end().trim());
                if (FormatString.strIsNull(author)) {
                    author = FormatString.filterHtmlTag(author);
                    author = FormatString.filterStrForKeyword(author);
                    articleBean.setArt_author(author.trim());
                }

            }

            String keyWord = "";
            if ((FormatString.strIsNull(collBean.getKeywords_start())) && (FormatString.strIsNull(collBean.getKeywords_end()))) {
                keyWord = StringUtils.substringBetween(contentHtmlStr, collBean.getKeywords_start().trim(),
                        collBean.getKeywords_end().trim());
                if (FormatString.strIsNull(keyWord)) {
                    keyWord = FormatString.filterHtmlTag(keyWord);
                    keyWord = FormatString.filterStrForKeyword(keyWord);
                    articleBean.setArt_keyWords(keyWord.trim());
                }

            }

            String artContent = "";
            if ((FormatString.strIsNull(collBean.getContent_start())) && (FormatString.strIsNull(collBean.getContent_end()))) {
                artContent = StringUtils.substringBetween(contentHtmlStr, collBean.getContent_start().trim(),
                        collBean.getContent_end().trim());

                if (FormatString.strIsNull(artContent)) {
                    artContent = artContent.trim();

                    if (collBean.getPic_isGet() == 1) {
                        if (FormatString.contentIsHaveImage(artContent)) {
                            String domain = FormatString.getSiteDomain(collBean.getSite_id());

                            List picURLList = FormatString.getImage(artContent);
                            for (int i = 0; i < picURLList.size(); i++) {
                                String urlstr = (String) picURLList.get(i);
                                //System.out.println("图片路径--------------------------" + urlstr);
                                String picName = FormatString.getPicName(urlstr);
                                if (!urlstr.startsWith("http://")) {
                                    urlstr = URLUtil.getDomainUrl(collBean.getColl_url()) + urlstr;
                                }
                                urlstr = FormatString.filterURL(urlstr);

                                if (DownHtmlUtil.writePicToLocal(urlstr, collBean.getSite_id(), picName)) {
                                    String resoue = urlstr.substring(0, urlstr.lastIndexOf("/"));
                                    //System.out.println("resoue:------------------------------" + resoue);
                                    resoue = resoue.replaceAll(URLUtil.getDomainUrl(resoue), "");
                                    resoue = resoue.substring(0, resoue.lastIndexOf("/"));
                                    //System.out.println("resoue:------------------------------" + resoue);
                                    artContent = artContent.replaceAll(resoue, "/collArtPic");
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

            if ((FormatString.strIsNull(articleBean.getArt_title())) && (FormatString.strIsNull(articleBean.getArt_content()))) {
                addCollDataInfo(articleBean, url, collBean.getRule_id() + "");
            }
        }
    }

    public static void addCollDataInfo(ArticleInfoBean articleBean, String url, String rule_id) {
        String filePath = FormatString.getManagerPath();
        File file = new File(filePath + File.separator + rule_id + ".txt");
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            ArticleInfoBean artList = CollectionDataDAO.getCollDataInfobyUrl(url);
            if (artList != null && artList.getUrl().equals(url)) {
                bw.write("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息已经采集过，不再重复采集!");
                bw.newLine();
                bw.flush();
                bw.close();
            } else {
                if (!CollectionDataManager.addCollDataInfo(articleBean)) {
                    bw.write("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息入库失败!");
                    bw.newLine();
                    bw.flush();
                    bw.close();
                } else {
                    bw.write("【链接为】:" + url + "   【标题】:" + articleBean.getArt_title() + "  的信息入库成功!");
                    bw.newLine();
                    bw.flush();
                    bw.close();
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}