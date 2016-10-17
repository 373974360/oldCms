package com.deya.wcm.dataCollection.util;

import java.io.PrintStream;

public class URLUtil {
    public static String formatLink(String aLink, String domain) {
        if (aLink.startsWith("http://"))
            return aLink;
        if (aLink.startsWith("./")) {
            return "";
        }
        String link = domain + aLink;
        return link;
    }

    public static String getDomainUrl(String url) {
        String domain = "";
        if (url.length() > 0) {
            String reg = ".*\\/\\/([^\\/\\:]*).*";
            domain = "http://" + url.replaceAll(reg, "$1") + "/";
        }
        return domain;
    }

    public static void main(String[] args) {
        String url = "<a href=\"/c/2014-07-21/114730553620.shtml\" target=\"_blank\">太原市城镇医保补助标准提高至人均320元</a><span>(07月21日 11:47)</span>\"";

        url = "http://china.huanqiu.com/politics/index.html$http://china.huanqiu.com/politics/<0,2,1,1>.html";
        System.out.println(getDomainUrl(url));
    }
}