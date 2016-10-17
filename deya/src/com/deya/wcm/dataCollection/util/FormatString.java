package com.deya.wcm.dataCollection.util;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.services.control.domain.SiteDomainManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.tags.MetaTag;

public class FormatString {
    public static String filterHtmlTag(String str) {
        String newStr = "";
        newStr = str.replaceAll("</?[^<]+>", "");
        return newStr;
    }

    public static String filterStrForKeyword(String value) {
        String str = value;

        str = str.replaceAll("文章关键词：", "");

        str = str.replaceAll("&nbsp;", "");

        return str;
    }

    public static String getPageEncode(String html) {
        String encode = "";
        MetaTag metaTag = (MetaTag) ParserUtils.parseTag(html, MetaTag.class, "http-equiv", "Content-type");
        if (metaTag != null) {
            encode = metaTag.getMetaContent();
            if (encode.length() > 0) {
                encode = formatPageEncode(encode);
            }
        }
        return encode;
    }

    public static String formatPageEncode(String str) {
        String reg = "^[a-zA-Z/;]*?\\s*charset=([a-zA-Z_-0-9]+)";
        String newEncode = str.replaceAll(reg, "$1");
        return newEncode;
    }

    public static int urlmarkNum(String urlbymark) {
        int num = 0;
        String reg = "[<](.*?)[>]+";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(urlbymark);
        while (m.find()) {
            num++;
        }
        return num;
    }

    public static String getManagerPath() {
        String path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
        path = path + File.separator + "dataCollection" + File.separator + "log";
        return path;
    }

    public static String getArtPicUploadPath(String site_id) {
        String path = JconfigUtilContainer.bashConfig().getProperty("path", "", "hostRoot_path");
        String domain = getHostSiteDomainBySiteID(site_id);
        path = path + File.separator + domain + File.separator + "ROOT" + File.separator + "collArtPic";
        return path;
    }

    public static String getHostSiteDomainBySiteID(String site_id) {
        String domain_name = "";
        List<SiteDomainBean> l = SiteDomainManager.getDomainListBySiteID(site_id);
        if ((l != null) && (l.size() > 0)) {
            for (SiteDomainBean sdb : l) {
                if ((site_id.equals(sdb.getSite_id())) && (sdb.getIs_host() == 1))
                    domain_name = sdb.getSite_domain();
            }
        }
        return domain_name;
    }

    public static String getSiteDomain(String site_id) {
        return SiteDomainManager.getDefaultSiteDomainBySiteID(site_id);
    }

    public static boolean strIsNull(String str) {
        if (str == null) {
            str = "";
        }
        str = str.trim();
        if ((!str.equals("")) && (!str.equals("null"))) {
            return true;
        }
        return false;
    }

    public static List<String> getImage(String content) {
        List list = new ArrayList();

        String imageReg = "src.*?=[\"](.*?)[\"]";

        Pattern p = Pattern.compile(imageReg);
        Matcher m = p.matcher(content);
        while (m.find()) {
            list.add(m.group().replace("src=", "").replaceAll("\"", ""));
        }
        return list;
    }

    public static String getPicName(String str) {
        String pic_name = "";
        String[] s = str.split("/");
        for (int i = 0; i < s.length; i++) {
            pic_name = s[(s.length - 2)];
        }
        pic_name = str.substring(str.indexOf(pic_name) - 1, str.length());
        return pic_name;
    }

    public static boolean contentIsHaveImage(String str) {
        boolean b = false;
        String s = "";
        String imageReg = "<img|IMG.*?src.*?=.*?(.*?)>";
        Pattern p = Pattern.compile(imageReg);
        Matcher m = p.matcher(str);
        while (m.find()) {
            s = m.group();
        }
        if (strIsNull(s)) {
            b = true;
        }
        return b;
    }

    public static String filterTitleKeyWord(String title) {
        String[] s = {"|", "_", "-"};
        for (int i = 0; i < s.length; i++) {
            if (title.contains(s[i])) {
                title = title.substring(0, title.indexOf(s[i]));
            }
        }
        return title;
    }

    public static String filterURL(String str) {
        String newStr = "";
        if (strIsNull(str)) {
            newStr = str.replaceAll(" ", "%20");
            newStr = newStr.replaceAll("\\(", "%28");
            newStr = newStr.replaceAll("\\)", "%29");
        }
        return newStr;
    }

    public static void main(String[] args) {
        String str = "asdfasdhfaoisdhfpoiahsd[oifhasodihfasdhflkahsdfha<IMG border=0 src=\"/c/news/20140810/skdfashdak.jsp\">hdiofhaiosdhfadfhaiosdhfiahsdhfaihsdiof";

        str = "/uploads/allimg/140110/1-1401101K3393J.JPG";

        System.out.println(getPicName(str));
    }
}
