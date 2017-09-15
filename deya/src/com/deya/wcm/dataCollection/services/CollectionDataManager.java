package com.deya.wcm.dataCollection.services;

import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dataCollection.bean.ArticleInfoBean;
import com.deya.wcm.dataCollection.bean.CollRuleBean;
import com.deya.wcm.dataCollection.dao.CollectionDataDAO;
import com.deya.wcm.dataCollection.util.DownHtmlUtil;
import com.deya.wcm.dataCollection.util.FormatString;
import com.deya.wcm.dataCollection.util.URLUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectionDataManager {
    private static String DATA_COLL_TABLENAME = "cs_collection";
    private static String COLL_ARTICLE_TABLENAME = "cs_artInfo";

    public static boolean addCollectionRule(CollRuleBean collBean) {
        int id = PublicTableDAO.getIDByTableName(DATA_COLL_TABLENAME);
        collBean.setId(id);
        collBean.setRule_id(id);
        if ((collBean.getColl_url().contains("&lt;")) || (collBean.getColl_url().contains("&gt;"))) {
            collBean.setColl_url(collBean.getColl_url().replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
        }
        return CollectionDataDAO.addCollectionRule(collBean);
    }

    public static List<CollRuleBean> getRuleList(Map<String, String> m) {
        return CollectionDataDAO.getRuleList(m);
    }

    public static List<CollRuleBean> getAllCollRuleBeanList(Map<String, String> m) {
        return CollectionDataDAO.getAllCollRuleBeanList(m);
    }

    public static String getRuleListCount() {
        return CollectionDataDAO.getRuleListCount();
    }

    public static boolean deleteRuleById(String ids) {
        Map m = new HashMap();
        m.put("ids", ids);
        return CollectionDataDAO.deleteRuleById(m);
    }

    public static CollRuleBean getCollRuleBeanByID(String id) {
        return CollectionDataDAO.getCollRuleBeanByID(id);
    }

    public static boolean updateRuleInfo(CollRuleBean collBean) {
        if ((collBean.getColl_url().contains("&lt;")) && (collBean.getColl_url().contains("&gt;"))) {
            collBean.setColl_url(collBean.getColl_url().replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
        }
        return CollectionDataDAO.updateRuleInfo(collBean);
    }

    public static String getCollUrlJsonStr(String urlStr, String containerSelector, String linkSelector, String Encode) {
        String domain = URLUtil.getDomainUrl(urlStr);
        LinkedHashSet set = initPageUrl(urlStr);
        String json_str = "[";
        while (set.size() > 0) {
            String url = (String) set.iterator().next();
            json_str = json_str + "{";
            json_str = json_str + "\"text\":\"" + url + "\",\"state\":\"closed\"";
            String strHtml = DownHtmlUtil.downLoadHtml(url, Encode);
            set.remove(url);

            if (FormatString.strIsNull(strHtml)) {
                LinkedHashSet SetURL = ResolveHtml.ResolveHtmlForLink(strHtml, domain, containerSelector, linkSelector, url);
                if ((SetURL.size() > 0) && (SetURL != null)) {
                    String child_str = getchildUrlStr(SetURL);
                    if ((child_str != null) && (!"".equals(child_str))) {
                        json_str = json_str + ",\"children\":[" + child_str + "]";
                    }
                    json_str = json_str + "},";
                } else {
                    json_str = "[{\"text\":\"" + urlStr + "采集规则有误,不能提取链接地址!\"},";
                }
            } else {
                json_str = "[{\"text\":\"" + url + "链接有误,不能获取列表链接!\"},";
            }
        }
        json_str = json_str.substring(0, json_str.length() - 1);
        json_str = json_str + "]";
        return json_str;
    }

    public static String getchildUrlStr(LinkedHashSet<String> SetUrl) {
        String json_str = "";
        if ((SetUrl.size() > 0) && (SetUrl != null)) {
            for (int i = SetUrl.size(); i > 0; i--) {
                String url = (String) SetUrl.iterator().next();
                json_str = json_str + "{\"text\":\"" + url + "\"},";
                SetUrl.remove(url);
            }
        } else json_str = json_str + "{\"text\":\"链接有误,不能获取列表链接\"},";

        json_str = json_str.substring(0, json_str.length() - 1);
        return json_str;
    }

    public static void startCollectionData(String ruleID) {
        CollectionThread search1 = new CollectionThread(ruleID);
        search1.start();
    }

    public static void CollectionData(String rule_id) {
//        String filePath = FormatString.getManagerPath();
//        File file = new File(filePath + File.separator + rule_id + ".txt");
//        if (file.exists())
//            file.delete();
//        try {
//            file.createNewFile();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
        CollRuleBean collBean = getCollRuleBeanByID(rule_id);

        LinkedHashSet urlSet = initCollRule(collBean);

        String domain = URLUtil.getDomainUrl(collBean.getColl_url());

        while (urlSet.size() > 0) {
            String url = (String) urlSet.iterator().next();
            //                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
//                bw.write("开始采集链接地址为: " + url + " 的列表");
//                bw.newLine();
//                bw.flush();
//                bw.close();

            System.out.println("开始采集链接地址为: " + url + " 的列表");

                String listPagehStr = DownHtmlUtil.downLoadHtml(url, collBean.getPageEncoding());
                urlSet.remove(url);

                if (FormatString.strIsNull(listPagehStr))
                    try {
                        LinkedHashSet waitgetArtInfoSet =
                                ResolveHtml.ResolveHtmlForLink(listPagehStr, domain,collBean.getListUrl_start(), collBean.getListUrl_end(),url);

                        ResolveHtml.getArticleInfoHtml(collBean, waitgetArtInfoSet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
        }
    }

    public static LinkedHashSet<String> initCollRule(CollRuleBean collBean) {
        String url = collBean.getColl_url();
        LinkedHashSet startURLSet = initPageUrl(url);
        return startURLSet;
    }

    public static LinkedHashSet<String> initPageUrl(String url) {
        LinkedHashSet startCollURLSet = new LinkedHashSet();
        if (url.contains("$")) {
            String[] arrayurl = url.split("\\u0024");
            for (int i = 0; i < arrayurl.length; i++) {
                if (!geturlIsHaveMark(arrayurl[i])) {
                    startCollURLSet.add(arrayurl[i]);
                } else {
                    String urlInfo = geturmMarkInfo(arrayurl[i]);
                    String[] array = urlInfo.split(",");
                    String type = array[0].toString();
                    int a1 = Integer.parseInt(array[1].toString());
                    int n = Integer.parseInt(array[2].toString());
                    int dorq = Integer.parseInt(array[3].toString());
                    if (type.equals("0"))
                        for (int k = 1; k <= n; k++) {
                            String tempurl = arrayurl[i];
                            int an = a1 + (k - 1) * dorq;
                            tempurl = tempurl.replaceAll("[<](.*?)[>]+", an + "");
                            startCollURLSet.add(tempurl);
                        }
                    else {
                        for (int j = 1; j <= n; j++) {
                            String tempurl = arrayurl[i];
                            int an = (int) (a1 * Math.pow(dorq, j - 1));
                            tempurl = tempurl.replaceAll("[<](.*?)[>]+", an + "");
                            startCollURLSet.add(tempurl);
                        }
                    }
                }
            }
        } else if (!geturlIsHaveMark(url)) {
            startCollURLSet.add(url);
        } else {
            String urlInfo = geturmMarkInfo(url);
            String[] array = urlInfo.split(",");
            String type = array[0].toString();
            int a1 = Integer.parseInt(array[1].toString());
            int n = Integer.parseInt(array[2].toString());
            int dorq = Integer.parseInt(array[3].toString());
            if (type.equals("0"))
                for (int k = 1; k <= n; k++) {
                    String tempurl = url;
                    int an = a1 + (k - 1) * dorq;
                    tempurl = tempurl.replaceAll("[<](.*?)[>]+", an + "");
                    startCollURLSet.add(tempurl);
                }
            else {
                for (int j = 1; j <= n; j++) {
                    String tempurl = url;
                    int an = (int) (a1 * Math.pow(dorq, j - 1));
                    tempurl = tempurl.replaceAll("[<](.*?)[>]+", an + "");
                    startCollURLSet.add(tempurl);
                }
            }

        }

        return startCollURLSet;
    }

    public static String geturmMarkInfo(String urlMark) {
        String info = "";
        String reg = "[<](.*?)[>]+";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(urlMark);
        while (m.find()) {
            info = m.group();
        }
        info = info.replaceAll("[<](.*?)[>]+", "$1");
        return info;
    }

    public static boolean geturlIsHaveMark(String url) {
        String reg = "(.*?)[<](.*?)[>](.*?)+";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(url);
        boolean b = m.matches();
        return b;
    }

    public static boolean addCollDataInfo(ArticleInfoBean articleBean) {
        int id = PublicTableDAO.getIDByTableName(COLL_ARTICLE_TABLENAME);
        articleBean.setId(id);
        return CollectionDataDAO.addCollDataInfo(articleBean);
    }

    public static String getCollInfoListCount(Map<String, String> m) {
        return CollectionDataDAO.getCollInfoListCount(m);
    }

    public static List<ArticleInfoBean> getCollDataList(Map<String, String> m) {
        return CollectionDataDAO.getCollDataList(m);
    }

    public static boolean deleteCollInfoById(String ids) {
        Map m = new HashMap();
        m.put("ids", ids);
        return CollectionDataDAO.deleteCollInfoById(m);
    }

    public static List<ArticleInfoBean> getcollBeanListByIds(String ids) {
        Map m = new HashMap();
        m.put("ids", ids);
        return CollectionDataDAO.getcollBeanListByIds(m);
    }

    public static boolean changeCollInfoStatus(String id) {
        return CollectionDataDAO.changeCollInfoStatus(id);
    }

    public static ArticleInfoBean getCollDataInfobyid(String infoid) {
        return CollectionDataDAO.getCollDataInfobyid(infoid);
    }

    public static boolean updateCollDataInfo(ArticleInfoBean artBean) {
        return CollectionDataDAO.updateCollDataInfo(artBean);
    }

    public static void updateCollTime(Map<String, String> m) {
        CollectionDataDAO.updateCollTime(m);
    }

    public static String readCollLogInfo(String rule_name, int rowLine) {
        String Logpath = FormatString.getManagerPath();
        File file = new File(Logpath + File.separator + rule_name + ".txt");
        String str = "";
        try {
            BufferedReader lb = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            int lines = 0;
            while (str != null) {
                lines++;
                str = lb.readLine();
                if (lines - rowLine == 0)
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String readCollLogByRuleID(String rule_id, int lineNum) {
        String str = readCollLogInfo(rule_id, lineNum);

        return str;
    }

    public static boolean deleteRuleByRuleCatId(Map m) {
        return CollectionDataDAO.deleteRuleByRuleCatId(m);
    }


    public static void main(String[] args) {
    }
}
