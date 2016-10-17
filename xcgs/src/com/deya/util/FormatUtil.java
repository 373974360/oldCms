package com.deya.util;



import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.server.ServerManager;
import com.deya.wcm.services.control.site.SiteManager;



/**

 * <p>格式化字符串类</p>

 * <p>Copyright: Copyright (c) 2003</p>

 * <p>Company: Cicro</p>

 * @author Sunyi

 * @version 1.1

 */

/*

 * @version 1.1, 2004-03-09  Modified by Sunyi

 */



public class FormatUtil {

    /**

     * 格式化文件大小

     * 形式如：2.22 K, 2.22 M, 2222 Bytes，长度保留4位

     *

     * @param size 文件大小

     * @return 格式化后的字串

     */

    public static String formatSize(long size) {

        String sizeStr = "";

        //小于1024字节

        if (size < 1024) {

            sizeStr = size + " Bytes";

        }

        //大于1M

        else if (size > 1024000) {

            sizeStr = Float.toString( (float) size / 1024000);

            if (sizeStr.length() > 4) {

                sizeStr = sizeStr.substring(0, 4);

            }

            if (sizeStr.endsWith(".")) {

                sizeStr = sizeStr.substring(0, sizeStr.length() - 1);

            }

            sizeStr += " M";

        }

        else {

            sizeStr = Float.toString( (float) size / 1024);

            if (sizeStr.length() > 4) {

                sizeStr = sizeStr.substring(0, 4);

            }

            if (sizeStr.endsWith(".")) {

                sizeStr = sizeStr.substring(0, sizeStr.length() - 1);

            }

            sizeStr += " K";

        }



        return sizeStr;

    }



    /**

     * 格式化路径，分隔符用系统分隔符，如果是目录以分隔符结尾

     *

     * @param pathStr 被格式化的路径

     * @param isEndWithSeparator 是否以分隔符结尾

     * @return 格式化后的路径

     * */

    public static String formatPath(String pathStr, boolean isEndWithSeparator) {

        pathStr = formatPath(pathStr);

        pathStr += File.separator;



        return pathStr;

    }



    /**

     * 格式化路径，分隔符用系统分隔符，结尾不带分隔符

     *

     * @param pathStr 被格式化的路径

     * @return 格式化后的路径

     * */

    public static String formatPath(String pathStr) {
    	   	
    	pathStr = pathStr.replace('/', File.separatorChar);    		
	    pathStr = pathStr.replace('\\', File.separatorChar);
    	
        if (pathStr.endsWith(File.separator)) {

            pathStr = pathStr.substring(0, pathStr.length() - 1);

            pathStr = formatPath(pathStr);

        }
        return pathStr;

    }



    /**

     * 对字串进行处理，防止空字串产生错误

     * @param String 

     * @return String 格式化后的字串

     * */

    public static String formatNullString(String str) {

        if (str == null || str.trim().equals("")) {

            str = "";

        }

        return str;

    }



    /**

     * 如果字符串为空，设成默认值

     * @param String  字符串

     * @param String  默认字符串

     * @return String 格式化后的字串

     * */

    public static String formatNullString(String str, String defaultStr) {

        if (str == null || str.trim().equals("")) {

            str = defaultStr;

        }



        return str;

    }    

    

    /**

     * 判断是否是数字

     * @param String  字符串

     * @return boolean 

     * */

    public static boolean isNumeric(String str)

    {

    	if(str != null && !"".equals(str) && !str.startsWith("0"))

    	{

    		for(int i=str.length();--i>=0;)

    		{

    			int chr=str.charAt(i);

    			if(chr<48||chr>57)

    				return false;

    		}

    		return true;

    	}else

    		return false;

    }

    

    /**

     * 判断查询的字符串是否是正确的，不包含特效字符

     * @param String  字符串

     * @return boolean true正确的，false有问题的

     * */

    public static boolean isValiditySQL(String str)

    {

        String inj_str ="'| and | exec | insert | select | delete | update | count |* | % | chr | mid | master | truncate | char | declare | or |+";//这里的东西还可以自己添加

        String[] inj_stra=inj_str.split("\\|");

        for (int i=0 ; i < inj_stra.length ; i++ )

        {

            if (str.indexOf(inj_stra[i])>=0)

            {

            	System.out.println("sql str is error --> "+str);

                return false;

            }

        }

        return true;

    }



    /**

     * 从request中获取参数，并拼成url形式的字符串 xxx=aa&bbb=11

     * @param String  字符串

     * @return boolean true正确的，false有问题的

     * */

    @SuppressWarnings("unchecked")

	public static String getParameterStrInRequest(HttpServletRequest request)

    {

    	String parms = "";

    	Enumeration enumOne = request.getParameterNames();

    	while(enumOne.hasMoreElements()){ 

          		String p_name=(String)enumOne.nextElement();

       			parms += "&"+p_name+"="+request.getParameter(p_name);

        	}

    	if(parms != null && parms.length() > 0)

    		parms = parms.substring(1);

    	

    	return parms;

    }

    

    /**

     * 格式化日期时间字串为指定的格式字串

     * @param String 时间字串

     * @param String 时间字串的日期格式，如yyyy-MM-dd

     * @return String　格式化后的字串，格式如：2003-12-02 12:12:10

     * */

    public static String formatDate(String dateStr, String pattern) throws

        ParseException {  
    	if(dateStr == null || "".equals(dateStr))
    		return "";
    	else
    		return DateUtil.formatToString(dateStr,pattern);

    }

    

    /**

     * 截取字符串

     * @param String int 字串

     * @param int length 显示长度

     * @param String paddingStr 补充替换的字符串

     * @return String

     * */

    public static String cutString(String str,int length,String paddingStr)

    {

    	if(str != null && str.length() > 0)

    	{

    		String new_str = "";

    		char[] c = str.toCharArray();

            int len = 0;

            for (int i = 0; i < c.length; i++) {            	

            	if(len >= length*2-1)

            	{

            		return new_str + paddingStr;

            	}

                len++;

                if (!isLetter(c[i])) {                	

                    len++;

                }  

                new_str += c[i];

            }

            return new_str;

    	}else

    		return "";

    }

    

    /**

     * 得到字符串真实长度

     * @param String 字串

     * @return int　长度，中文字符X2

     * */

    public static int length(String s) {

        if (s == null)

            return 0;

        char[] c = s.toCharArray();

        int len = 0;

        for (int i = 0; i < c.length; i++) {

        	

            len++;

            if (!isLetter(c[i])) {

                len++;

            }

            

        }

        return len;

    }



    /**

     * 判断是否是中文

     * @param char c

     * @return boolean

     * */

    public static boolean isLetter(char c) {

        int k = 0x80;

        return c / k == 0 ? true : false;

    }

    

    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) 

                      || "null".equalsIgnoreCase(ip))    {

            ip = request.getHeader("Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) 

                      || "null".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) 

                      || "null".equalsIgnoreCase(ip)) {

            ip = request.getRemoteAddr();

        }

        return ip;

    }

   

    public static String getAddress(String ip){

    	return com.deya.util.ip.Utils.getAddress(ip);

    }
    
    /**
     * 将数字转换成制定位数的字符串
     * 如果位数不足又指定的字符补齐，如果位数过多，则取前几位
     * @param num	需要转换的数字
     * @param digit 指定转换后的位数
     * @param synbol 指定的填充字符，多余一个时取首位
     * @return
     */
    public static String intToString(int num, int digit, String symbol)
    {
    	if(symbol.length() != 1)
    	{
    		symbol = symbol.substring(0, 1);
    	}
    	String strNum = String.valueOf(num);
    	if(strNum.length() < digit)
    	{
    		String ret = "";
    		for(int i=0; i< digit-strNum.length(); i++)
    		{
    			ret += symbol;
    		}
    		ret += strNum;
    		return ret;
    	}
    	else if(strNum.length() > digit)
    	{
    		return strNum.substring(0, digit);
    	}
    	else
    	{
    		return strNum;
    	}
    }
    
    public static String getCurrentDate(String pattern)
    {
    	if(pattern == null || "".equals(pattern))
    		return DateUtil.getCurrentDateTime();
    	else
    		return DateUtil.getCurrentDateTime(pattern);
    }

    public static String getCurrentDateBefore(int day)
    {
        try {
            String before = DateUtil.getDateBefore(DateUtil.getCurrentDate(),day);
            return before;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 根据站点ID获得站点模板路径
     * @param String site_id
     * @return String
     */
    public static String getTemplatePathForSiteID(String site_id)
    {
    	String path = formatPath(SiteManager.getSitePath(site_id)+"/WEB-INF/template");
    	if(ServerManager.isWindows())
			return path.substring(path.indexOf("\\vhost")+8);
		else
			return path.substring(path.indexOf("/vhost")+8);
    }
    
    /**
     * 过滤所有以"<"开头以">"结尾的标签
     * @param String content
     * @return String
     */
    public static String filterHtmlTag(String content)
    {
        String result= HtmlRegexpUtil.filterHtml(content);
        result = HtmlRegexpUtil.filterSpace(result);
        return result.replaceAll("　","").replaceAll("&nbsp;"," ").replaceAll(" ","");
    }
    
    /**
     * 判断传过来的时间与当前时间的小时差,并判断
     * 对比当前时间是否过期(一般用于使用new图片 参数一:信息发布时间,参数二:过期时间(小时),这里放的是24小时)
		#if($FormatUtil.contrastCurrentTime("$r.released_dtime",24) == false)
			<img src="">
		#end
     * @param String old_time
     * @param int hour
     * @return boolean
     */
    public static boolean contrastCurrentTime(String o_time,int h)
    {
    	try{
    		
    		return DateUtil.compareDatetime(o_time,DateUtil.getCurrentDateTime()) > h;
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    		return false;
    	}
    }
    
    //判断字符串是否为空
    public static boolean isNull(String str)
    {
    	str = str.trim();
    	if(str == null || "".equals(str) || "null".equals(str.toLowerCase()))
    		return true;
    	else
    		return false;
    }
    
    public static String formatJsonStr(String str)
    {
    	return str.replaceAll("\"", "\\\\\"");
    }
    
    /**
     * 根据url截取域名
     * @param String url
     * @return String
     */
    public static String getDomainForUrl(String url)
    {
    	return url.replaceAll("http://([^/|:]+)[/|:].*", "$1");
    }
    
    
  //在volecity中分割字符串  --  李苏培加 
    public static ArrayList<String> split(String str ,String sp){
    	System.out.println("-------------------------split--------------------------");
    	ArrayList<String> list = new ArrayList<String>();
    	try{
    		String[] list_temp = str.split(sp);
    		for(int i=0;i<list_temp.length;i++){
    			if(list_temp[i]!=null && !list_temp[i].equals("")){
    				list.add(list_temp[i]);
    			} 
    		}
    		System.out.println(list);
    		return list;
    	}catch (Exception e) {
			e.printStackTrace();
			return list;
		}
    }
    public static String fiterHtmlTag(String str) {
    	return HtmlRegexpUtil.filterHtml(str);
    }

    public static String subString(String str,int start, int end)
    {
        return str.substring(start,end);
    }


    public static void main(String[] args) throws ParseException {

       	//System.out.print(FormatUtil.formatPath("D:\\cicro\\cms\\as\\tomcat6\\shared\\classes/AllConfigDescrion_WCM.xml"));
//    	System.out.println(path.substring(path.indexOf("\\vhost")+8));
    	//System.out.println(DateUtil.timestampToDate(1445560463,"yyyy-MM-dd HH:mm:ss"));
    	System.out.println("2015-12-25".substring(5,7));
    }



}