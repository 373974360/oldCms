package com.deya.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void main(String arr[]){
    	System.out.println(Integer.parseInt("0x100F",16));
    }

	// 十进制转化为十六进制，结果为C8。 
	//Integer.toHexString(200); 
	// 十六进制转化为十进制，结果140。 
	//Integer.parseInt("8C",16); 

  public static Cookie getCookie(HttpServletRequest request, String name) {
    Cookie cookies[] = request.getCookies();
    if (cookies == null || name == null || name.length() == 0) {
      return null;
    }
    for (int i = 0; i < cookies.length; i++) {
    	System.out.println("cookies[i].getName()---" + cookies[i].getName()+"--"+cookies[i].getDomain()+"--"+request.getServerName());
      if (name.equals(cookies[i].getName())
          //&& request.getServerName().equals(cookies[i].getDomain())
          ) {
        return cookies[i];
      }
    }
    return null;
  }
  
  
  public static String getCookieValue(HttpServletRequest request, String name) {
	    Cookie cookie = getCookie(request,name);
	    if(cookie==null){
	    	return "";
	    }
	    return cookie.getValue();
	  }

  public static void deleteCookie(HttpServletRequest request,
      HttpServletResponse response, Cookie cookie) {
    if (cookie != null) {
      cookie.setPath(getPath(request));
      cookie.setValue("");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }
  }

  public static void setCookie(HttpServletRequest request,
      HttpServletResponse response, String name, String value) {
    setCookie(request, response, name, value, 0x278d00);
  }

  public static void setCookie(HttpServletRequest request,
      HttpServletResponse response, String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value == null ? "" : value);
    cookie.setMaxAge(maxAge);
    cookie.setPath(getPath(request));
    response.addCookie(cookie);
  }

  private static String getPath(HttpServletRequest request) {
    String path = request.getContextPath();
    return (path == null || path.length()==0) ? "/" : path;
  }

}