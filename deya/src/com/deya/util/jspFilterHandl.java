package com.deya.util;

import com.deya.util.jconfig.JconfigUtilContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

public class jspFilterHandl {
    private static String[] filter_str = {"%22", "%27", "%3E", "%3e", "%3C", "%3c", "/*", "\\", "union", "--", "1=1", "and ", "concat", "acustart", "application", "script", "location", "limit ", "alert", "iframe", "set-cookie", "+", "or ", "drop table", "asc\\(", "mid\\(", "char\\(", "net user", "exists", "alter",
            "+acu+", "onmouseover", "header", "exec ", "insert ", "select ", "delete ", "trancate", "update ", "updatexml", "extractvalue", "href=", "data:text", "declare", "master", "execute", "xp_cmdshell", "netlocalgroup", "count\\(", "restore", "floor", "ExtractValue", "UpdateXml",
            "injected", "ACUstart", "ACUend", "():;", "acu:Expre", "window.location.href", "document", "parameter: ", "<OBJECT", "javascript", "confirm", "<script>", "</script>", "..", "<img>", "</img>", "cat ", "click", "function", "prompt(", "src", "url","http"};
    private static String no_filter_jsp;

    static {
        String[] jspArr = JconfigUtilContainer.bashConfig().getPropertyNamesByCategory("filter_jsp_page");
        if (jspArr != null && jspArr.length > 0) {
            for (int i = jspArr.length; i > 0; --i) {
                no_filter_jsp = no_filter_jsp + "," + JconfigUtilContainer.bashConfig().getProperty(jspArr[i - 1], "", "filter_jsp_page");
            }
        }
    }

    public jspFilterHandl() {

    }

    //李苏培加
    public static boolean isTureKey(String content) {
        String contentold = content;
        boolean result = false;//不包含
        try {
            String str[] = filter_str;
            for (int i = 0; i < str.length; i++) {
                String s = str[i];
                if (s != null && !"".equals(s)) {
                    s = s.toString();
                    try {
                        content = URLDecoder.decode(contentold.replaceAll("%20", " ").replaceAll("&lt;", "<"), "utf-8").toLowerCase();
                        content = content + URLDecoder.decode(contentold, "utf-8");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    result = content.toLowerCase().contains(s);
                    if (result) {
                        break;
                    }
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return true;//包含
        }
    }

    //李苏培加
    public static boolean isTure(HttpServletRequest request) { //是否包含过滤关键字
        String path = ((HttpServletRequest) request).getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + path + "/";
        // HTTP 头设置 Referer过滤
        /*
        String referer = ((HttpServletRequest) request).getHeader("Referer"); // REFRESH
        if(referer != null){
            if(isTureKey(referer)){
                return true;  //包含要过滤的关键字
            }
        }
        */
        try {
            String servletPath = request.getServletPath();
            String queryString = request.getQueryString();
            //System.out.println("servletPath	====	" + servletPath);
            //System.out.println("queryString	====	" + queryString);

            if (queryString == null) {
                queryString = "";
            }
            for (Enumeration e = request.getParameterNames(); e.hasMoreElements(); ) {
                String arr = (String) e.nextElement();
                String value = request.getParameter(arr);
                if ("ware_content".equals(arr) || "t_content".equals(arr)) {
                    return false;
                }
                if ("cat_id".equals(arr) || "model_id".equals(arr) || "sq_id".equals(arr) || "tm_id".equals(arr) || "info_id".equals(arr)) {
                    try {
                        if (value != null && !"".equals(value)) {
                            int i = Integer.parseInt(value);
                        }
                    } catch (Exception ex) {
                        return true;
                    }
                }

                if (isTureKey(value)) {
                    return true;  //包含要过滤的关键字
                }
            }

            if ((queryString != null) && (!("".equals(queryString)))) {
                if (isTureKey(queryString)) {
                    return true;  //包含要过滤的关键字
                }
            }
            return false;//不包含要过滤的关键字
        } catch (Exception e) {
            e.printStackTrace();
            return true;//包含要过滤的关键字
        }
    }

    public static boolean isRightParam(HttpServletRequest request, String url) {
        /*
        if(no_filter_jsp.contains(url))
            return true;//如果此页面不需要进行过滤，直接返回true
        */
        if (isTure(request)) {//验证不通过
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String content = "http://www.ylwsw.gov.cn/appeal/list.jsp?model_id=(SELECT%20(CASE%20WHEN%20(3627=3627)))&tm_id=373&tab=3";
        String contentold = "http://www.ylwsw.gov.cn/appeal/list.jsp?model_id=(SELECT%20(CASE%20WHEN%20(3627=3627)))&tm_id=373&tab=3";
        try {
            content = URLDecoder.decode(contentold.replaceAll("%20", " ").replaceAll("&lt;", "<"), "utf-8").toLowerCase();
            content = content + URLDecoder.decode(contentold, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String s = "aa=123";
        System.out.println(s.substring(s.indexOf("=") + 1));
    }
}
