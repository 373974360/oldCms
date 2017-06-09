package com.deya.util;

import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class jspFilterHandl
{
    private static String[] filter_str = {"%22","%27","%3E","%3e","%3C","%3c","/*","\\","union","--","1=1","and ","concat","acustart","application","script","location","limit ","alert","iframe","set-cookie","+","or ","drop table","asc\\(","mid\\(","char\\(","net user","exists","alter",
            "+acu+","onmouseover","header","exec ","insert ","select ","delete ","trancate","update ","updatexml","extractvalue","href=","data:text","declare","master","execute","xp_cmdshell","netlocalgroup","count\\(","restore","floor","ExtractValue","UpdateXml",
            "injected","ACUstart","ACUend","():;","acu:Expre","window.location.href","document","parameter: ","<OBJECT","javascript","confirm","<script>","</script>","..","<img>" ,"</img>","cat ", "click", "function","prompt(","<a",";","body","title","head"};
    private static String no_filter_jsp;

    static
    {
        String[] jspArr = JconfigUtilContainer.bashConfig().getPropertyNamesByCategory("filter_jsp_page");
        if ((jspArr != null) && (jspArr.length > 0)) {
            for (int i = jspArr.length; i > 0; i--) {
                no_filter_jsp = no_filter_jsp + "," + JconfigUtilContainer.bashConfig().getProperty(jspArr[(i - 1)], "", "filter_jsp_page");
            }
        }
    }

    public static boolean isTureKey(String content)
    {
        String contentold = content;
        boolean result = false;
        try
        {
            String[] str = filter_str;
            for (int i = 0; i < str.length; i++)
            {
                String s = str[i];
                if ((s != null) && (!"".equals(s)))
                {
                    s = s.toString();
                    try
                    {
                        content = URLDecoder.decode(contentold.replaceAll("%20", " ").replaceAll("&lt;", "<"), "utf-8").toLowerCase();
                        content = content + URLDecoder.decode(contentold, "utf-8");
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                    result = content.toLowerCase().contains(s);
                    if (result) {
                        break;
                    }
                }
            }
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isTure(HttpServletRequest request)
    {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";


        String referer = request.getHeader("Referer");
        if ((referer != null) &&
                (isTureKey(referer))) {
            return true;
        }
        try
        {
            String servletPath = request.getServletPath();
            String queryString = request.getQueryString();
            if (queryString == null) {
                queryString = "";
            }
            for (Enumeration e = request.getParameterNames(); e.hasMoreElements();)
            {
                String arr = (String)e.nextElement();
                String value = request.getParameter(arr);
                if (("model_id".equals(arr))) {
                    try
                    {
                        int i = Integer.parseInt(value);
                    }
                    catch (Exception ex)
                    {
                        return true;
                    }
                }
                if (isTureKey(value)) {
                    return true;
                }
            }
            if ((queryString != null) && (!"".equals(queryString)) &&
                    (isTureKey(queryString))) {
                return true;
            }
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isRightParam(HttpServletRequest request, String url)
    {
        if (isTure(request)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args)
    {
        String content = "http://www.ylwsw.gov.cn/appeal/list.jsp?model_id=(SELECT%20(CASE%20WHEN%20(3627=3627)))&tm_id=373&tab=3";
        String contentold = "http://www.ylwsw.gov.cn/appeal/list.jsp?model_id=(SELECT%20(CASE%20WHEN%20(3627=3627)))&tm_id=373&tab=3";
        try
        {
            content = URLDecoder.decode(contentold.replaceAll("%20", " ").replaceAll("&lt;", "<"), "utf-8").toLowerCase();
            content = content + URLDecoder.decode(contentold, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        String s = "aa=123";
        System.out.println(s.substring(s.indexOf("=") + 1));
    }
}
