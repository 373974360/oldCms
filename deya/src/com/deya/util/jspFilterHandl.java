//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.deya.util;

import com.deya.util.jconfig.JconfigUtilContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class jspFilterHandl {
    private static String[] filter_str = {"%df", "%5c", "%27", "%20", "%22", "%27", "%3E", "%3e", "%3C", "%3c", "\\", "union", "--", "1=1", "and ", "concat", "acustart", "application", "script", "location", "limit ", "alert", "iframe", "set-cookie", "+", "or ", "drop table", "asc\\(", "mid\\(", "char\\(", "net user", "exists", "alter",
            "+acu+", "onmouseover", "header", "exec ", "insert ", "select ", "delete ", "trancate", "update ", "updatexml", "extractvalue", "href=", "data:text", "declare", "master", "execute", "xp_cmdshell", "netlocalgroup", "count\\(", "restore", "floor", "ExtractValue", "UpdateXml",
            "injected", "ACUstart", "ACUend", "():;", "acu:Expre", "window.location.href", "document", "parameter: ", "<OBJECT", "javascript", "confirm", "<script>", "</script>", "..", "cat ", "click", "function", "prompt(", "<", ">", "'", "“", "”", "‘", "’"};
    private static String no_filter_jsp;

    private static String[] sqlFilterStr = {"exec ", "insert ", "select ", "delete ", "trancate", "update ", "drop table"};

    static {
        Set<String> jspArr = JconfigUtilContainer.bashConfig().getPropertyNamesByCategory("filter_jsp_page");

        String j;
        for (Iterator var1 = jspArr.iterator(); var1.hasNext(); no_filter_jsp = no_filter_jsp + "," + JconfigUtilContainer.bashConfig().getProperty(j, "", "filter_jsp_page")) {
            j = (String) var1.next();
        }
    }

    //李苏培加
    public static boolean isTureKey(String content, String[] filterStr) {
        String contentold = content;
        boolean result = false;//不包含
        try {
            String str[] = filterStr;
            for (int i = 0; i < str.length; i++) {
                String s = str[i];
                if (s != null && !"".equals(s)) {
                    s = s.toString();
                    try {
                        content = URLDecoder.decode(contentold.replaceAll("%20", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">"), "utf-8").toLowerCase();
                        content = (content + URLDecoder.decode(contentold, "utf-8")).replaceAll("<select", "");
                    } catch (Exception e1) {
                        content = contentold.replaceAll("%20", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">").toLowerCase();
                        content = (content + contentold).replaceAll("<select", "");
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
    public static boolean isTure(ServletRequest request) { //是否包含过滤关键字
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
            String servletPath = ((HttpServletRequest) request).getServletPath();
            String queryString = ((HttpServletRequest) request).getQueryString();
            if (queryString == null) {
                queryString = "";
            }
            if (servletPath.indexOf("JSON-RPC") >= 0) {
                String params = getRequestPayload(request);
                if (isTureKey(params, sqlFilterStr)) {
                    return true;  //包含要过滤的关键字
                }
            }
            for (Enumeration e = request.getParameterNames(); e.hasMoreElements(); ) {
                Object o = e.nextElement();
                String arr = (String) o;
                String value = request.getParameter(arr);
                if ("ware_content".equals(arr) || "t_content".equals(arr) || "sq_content".equals(arr) || "ExcelContent".equals(arr) || "info_content".equals(arr)) {
                    continue;
                }
                if ("cat_id".equals(arr) || "model_id".equals(arr) || "sq_id".equals(arr) || "tm_id".equals(arr) || "info_id".equals(arr) || "id".equals(arr))              {
                    try {
                        if (value != null && !"".equals(value) && !"null".equals(value)) {
                            int i = Integer.parseInt(value);
                        }
                    } catch (Exception ex) {
                        return true;
                    }
                }
                if (isTureKey(value, filter_str)) {
                    return true;  //包含要过滤的关键字
                }
            }
            if ((queryString != null) && (!("".equals(queryString)))) {
                if (isTureKey(queryString, filter_str)) {
                    return true;  //包含要过滤的关键字
                }
            }
            return false;//不包含要过滤的关键字
        } catch (Exception e) {
            e.printStackTrace();
            return true;//包含要过滤的关键字
        }
    }

    public static boolean isRightParam(ServletRequest request, String url) {
        if (isTure(request)) {//验证不通过
            return false;
        }
        return true;
    }

    private static String getRequestPayload(ServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
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
