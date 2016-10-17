package com.deya.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.jconfig.JconfigUtilContainer;


public class jspFilterHandl {
	private static String[] filter_str = {" and ","acustart","application","<script"," limit ","alert","iframe","set-cookie","+"," or ",
		                                 "+acu+","onmouseover","header","exec"," insert "," select "," delete "," update ","href=","data:text",
		                                 "injected","ACUstart","ACUend","():;","acu:Expre",";window.location.href","parameter: ","<OBJECT","javascript:","..", "cat ", "click", "function","*"};
	private static String no_filter_jsp;
	 
	static{ 
		String[] jspArr = JconfigUtilContainer.bashConfig().getPropertyNamesByCategory("filter_jsp_page");
		if(jspArr != null && jspArr.length > 0)
    	{
    		for(int i=jspArr.length;i > 0;i--)
    		{
    			no_filter_jsp += ","+JconfigUtilContainer.bashConfig().getProperty(jspArr[i-1], "", "filter_jsp_page");
    		}    		
    	}
	}
	
	  //李苏培加
	  public static boolean isTureKey(String content){
		  String contentold = content;
	  	  boolean result = false;//不包含
		  try{
			  String str[] = filter_str;
			  for(int i=0;i<str.length;i++){
				  String s = str[i];
				  if(s!=null && !"".equals(s)){
					  s = s.toString();
					  try{
						  content = URLDecoder.decode(contentold.replaceAll("%", "%25").replaceAll("&lt;", "<"), "utf-8").toLowerCase();
						  content = content + URLDecoder.decode(contentold, "utf-8");
				        }catch (Exception e1) {
				        	e1.printStackTrace();
						}
					  result = content.toLowerCase().contains(s);
					  if(result){
						  break;
					  }
				  }
			  }
			  return result;
		  }catch (Exception e) {
			e.printStackTrace();
			return true;//包含
		  }  
	}
	
	//李苏培加
	public static boolean isTure(HttpServletRequest request){ //是否包含过滤关键字
		try{
			String servletPath = request.getServletPath(); 
		    String queryString = request.getQueryString(); 
		    System.out.println("servletPath	====	" + servletPath);
		    System.out.println("queryString	====	" + queryString);
		    
		    if(queryString == null) 
		    {
		      queryString = "";
		    }
		    for (Enumeration e = request.getParameterNames(); e.hasMoreElements(); )
		    {
		      String arr = (String)e.nextElement();
		      String value = request.getParameter(arr);
		        if(isTureKey(value)){
			    	 return true;  //包含要过滤的关键字
			    }
		    }
		    
		    if ((queryString != null) && (!("".equals(queryString)))) {
		    	if(isTureKey(queryString)){
			    	 return true;  //包含要过滤的关键字
			    }  
		    }	
			return false;//不包含要过滤的关键字
		}catch (Exception e) {
			e.printStackTrace();
			return true;//包含要过滤的关键字
		}
	}
	
	public static boolean isRightParam(HttpServletRequest request,String url)
	{
		if(no_filter_jsp.contains(url))
			return true;//如果此页面不需要进行过滤，直接返回true
		
		if(isTure(request)){//验证不通过
			return false;
		}
		
		String params = FormatUtil.getParameterStrInRequest(request);
		try {
			params = java.net.URLDecoder.decode(params.replaceAll("%", "%25"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(params != null && !"".equals(params))
		{
			String[] tempA = params.split("&");
			for (int i=0;i<tempA.length;i++)
			{
				for(int j=0;j<filter_str.length;j++)
				{
					String s = tempA[i].substring(tempA[i].indexOf("=")+1);
					if(s.toLowerCase().contains(filter_str[j]))
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static void main(String args[])
	{
		String s = "aa=123";
		System.out.println(s.substring(s.indexOf("=")+1));
	}
}
