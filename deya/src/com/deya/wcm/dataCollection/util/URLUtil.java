package com.deya.wcm.dataCollection.util;

public class URLUtil {
    public static String formatLink(String parentUrl, String aLink, String domain) {
        if (aLink.startsWith("http://")) {
            return aLink;
        } else {

            String link = "";
            if (aLink.startsWith("/")) {
                link = domain + aLink;
            } else {
                link = parentUrl.substring(0, parentUrl.lastIndexOf("/")) + "/" + aLink;

            }
//            String link = domain + "/" + aLink;
            return link;
        }
    }

    public static String getDomainUrl(String url) {
        String domain = "";
        if (url.length() > 0) {
            if(url.startsWith("http://")){
                String reg = ".*\\/\\/([^\\/\\:]*).*";
                domain = "http://" + url.replaceAll(reg, "$1");
            }else{
                String reg = ".*\\/\\/([^\\/\\:]*).*";
                domain = "https://" + url.replaceAll(reg, "$1");
            }
        }
        System.out.println(domain);
        return domain;
    }

    public static void main(String[] args) {
        String url = "<a href=\"/c/2014-07-21/114730553620.shtml\" target=\"_blank\">太原市城镇医保补助标准提高至人均320元</a><span>(07月21日 11:47)</span>\"";

        url = "http://china.huanqiu.com/politics/index.html$http://china.huanqiu.com/politics/<0,2,1,1>.html";
        System.out.println(getDomainUrl(url));
    }
}