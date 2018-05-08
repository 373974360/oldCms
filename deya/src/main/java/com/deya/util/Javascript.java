package com.deya.util;

/**
 * <p>Html工具类,输出js</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Cicro</p>
 * @author not Sunyi
 * @version 1.0
 */

public class Javascript {
	/**
     * 输出alert
     * @param str 要打印的字符串
     * @return String
     * */
    public static String alert(String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script language='javascript'>");
        sb.append("alert('" + msg + "');");
        sb.append("</script>");

        return sb.toString();
    }

    /**
     * 当前路径跳转
     * @param str 要跳转的地址
     * @return String
     * */
    public static String location(String url) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script language='javascript'>");
        sb.append("location='" + url + "';");
        sb.append("</script>");

        return sb.toString();
    }

    /**
     * 指定窗口跳转
     * @param url 跳转地址
     * @param obj 跳转对象　父窗口还是本窗口 parent window
     * @return String
     * */
    public static String location(String url, String obj) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script language='javascript'>");
        sb.append(obj + ".location='" + url + "';");
        sb.append("</script>");

        return sb.toString();
    }

    /**
     * 当前页面刷新
     * @return String
     * */
    public static String reload() {
        StringBuffer sb = new StringBuffer();
        sb.append("<script language='javascript'>");
        sb.append("location.reload();");
        sb.append("</script>");

        return sb.toString();
    }

    /**
     * 指定窗口刷新
     * @param String 刷新对象　父窗口还是本窗口 parent window
     * @return String
     * */
    public static String reload(String obj) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script language='javascript'>");
        sb.append(obj + ".location.reload();");
        sb.append("</script>");

        return sb.toString();
    }

    /**
     * 返回跳转
     * @param int　跳转参数，要跳到之前的第几个页面
     * @return String
     * */
    public static String history(int num) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script language='javascript'>");
        sb.append("history.go(" + num + ");");
        sb.append("</script>");

        return sb.toString();
    }

    /**
     * 关闭当前窗口
     * @return String
     * */
    public static String close() {
        StringBuffer sb = new StringBuffer();
        sb.append("<script language='javascript'>");
        sb.append("window.close();");
        sb.append("</script>");

        return sb.toString();
    }

    /**
     * 关闭指定窗口
     * @param String 关闭对象　父窗口还是本窗口 parent window
     * @return String
     * */
    public static String close(String obj) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script language='javascript'>");
        sb.append(obj + ".close();");
        sb.append("</script>");

        return sb.toString();
    }

}