package com.deya.util;

import com.deya.util.jconfig.JconfigUtilContainer;
import org.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;

public class jspFilterHandl {
    private static String[] filter_str = {"%df", "%5c", "%27", "%20", "%22", "%27", "%28", "%29", "%3E", "%3e", "%3C", "%3c", "\\", "union", "--", "1=1", "and ", "concat", "acustart", "application", "script", "location", "limit ", "alert", "iframe", "set-cookie", "or ", "drop table", "asc\\(", "mid\\(", "char\\(", "net user", "exists", "alter",
            "+acu+", "onmouseover", "header", "exec ", "insert ", "select ", "select+1", "delete ", "trancate", "update ", "updatexml", "extractvalue", "href=", "data:text", "declare", "master", "execute", "xp_cmdshell", "netlocalgroup", "count\\(", "restore", "floor", "ExtractValue", "UpdateXml",
            "injected", "ACUstart", "ACUend", "():;", "acu:Expre", "window.location.href", "document", "parameter: ", "<OBJECT", "javascript", "confirm", "<script>", "</script>", "..", "cat ", "click", "function", "prompt(", "<", ">", "'", "‘", "’", "�", "ndhlmt:expre", "ssion", "ndhlmt"};
    private static String no_filter_jsp;

    private static String[] sqlFilterStr = {"exec ", "insert ", "delete ", "trancate", "update ", "drop table"};

    private static String[] integerParamStr = {"cat_id", "model_id", "sq_id", "tm_id", "info_id", "info_status", "dept_id", "final_status", "f_id"};

    private static String[] editorParams = {"ware_content","t_content","sq_content","correct_content","c_spyj","c_sqtj","c_jzxyq","c_sqclml","c_sfyj","c_fulu"};

    static {
        String[] jspArr = JconfigUtilContainer.bashConfig().getPropertyNamesByCategory("filter_jsp_page");
        if (jspArr != null && jspArr.length > 0) {
            for (int i = jspArr.length; i > 0; --i) {
                no_filter_jsp = no_filter_jsp + "," + JconfigUtilContainer.bashConfig().getProperty(jspArr[i - 1], "", "filter_jsp_page");
            }
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
                        content = contentold.replaceAll("%20", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">").toLowerCase();
                        content = (content + contentold).replaceAll("<select", "");
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
                        content = contentold.replaceAll("%20", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">").toLowerCase();
                        content = (content + contentold).replaceAll("<select", "");
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

    public static boolean isRPCParames(String params){
        try{
            params = params.substring(params.indexOf("[")+9,params.indexOf("}")+1);
            JSONObject jsonObject = new JSONObject(params);
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String json_key = iterator.next().toString();
                String json_value = jsonObject.get(json_key).toString();
                for (String str : integerParamStr) {
                    if (str.equals(json_key)) {
                        try {
                            if (json_value != null && !"".equals(json_value) && !"null".equals(json_value)) {
                                int i = Integer.parseInt(json_value);
                            }
                        } catch (Exception ex) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
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
            if (queryString.indexOf("collURL") == -1) {
                if ((path.equals("/sys") && servletPath.indexOf("/JSON-RPC") >= 0) || (path.equals("/manager") && servletPath.indexOf("/JSON-RPC") >= 0)) {
                    String params = getRequestPayload(request);
                    System.out.println("params：" + params);
                    if (isTureKey(params, sqlFilterStr)) {
                        return true;  //包含要过滤的关键字
                    }
                    if(params.indexOf("map") >= 0){//jsonRPC携带的参数集合
                        if(isRPCParames(params)){
                            return true;
                        }
                    }
                }
                for (Enumeration e = request.getParameterNames(); e.hasMoreElements(); ) {
                    Object o = e.nextElement();
                    String arr = (String) o;
                    String value = request.getParameter(arr);
                    boolean isEditorParam = false;
                    for (String editorParam : editorParams) {
                        if(editorParam.equals(arr)){
                            isEditorParam = true;
                            break;
                        }
                    }
                    if(isEditorParam){
                        continue;
                    }
                    for (String str : integerParamStr) {
                        if (str.equals(arr)) {
                            try {
                                if (value != null && !"".equals(value) && !"null".equals(value)) {
                                    int i = Integer.parseInt(value);
                                }
                            } catch (Exception ex) {
                                return true;
                            }
                        }
                    }
                    if ("sq_flag".equals(arr)) {
                        try {
                            if (value != null && !"".equals(value) && !"null".equals(value)) {
                                String[] sqFlag = value.split(",");
                                for (String s : sqFlag) {
                                    int i = Integer.parseInt(s);
                                }
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
