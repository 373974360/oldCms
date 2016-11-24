package com.deya.wcm.services.org.user;

import com.deya.wcm.jsonlistener.MySessionContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {
	public static Object get(HttpServletRequest request, String strKey) {
        Object result = null;
        HttpSession session = null;
        MySessionContext myc = MySessionContext.getInstance();
        Cookie[] cookies = request.getCookies();
        if(null != cookies){
            for (Cookie cookie : cookies) {
                if(result == null && "JSESSIONID".equals(cookie.getName())){
                    String cookieValue = cookie.getValue();
                    session = myc.getSession(cookieValue);
                    if(session != null){
                        result = session.getAttribute(strKey);
                    }
                }
            }
            if(result != null){
                for (Cookie cookie : cookies) {
                    if("JSESSIONID".equals(cookie.getName())){
                        String cookieValue = cookie.getValue();
                        session = myc.getSession(cookieValue);
                        if(session != null){
                            session.setAttribute(strKey, result);
                        }
                    }
                }
                HttpSession mycSession = myc.getSession(request.getSession(true).getId());
                if(null != mycSession){
                    mycSession.setAttribute(strKey, result);
                }
            }
        }
		return result;
	}

	public static void set(HttpServletRequest request, String strKey,
			Object objValue) {
        HttpSession session = null;
        MySessionContext myc = MySessionContext.getInstance();
        Cookie[] cookies = request.getCookies();
        if(null != cookies){
            for (Cookie cookie : cookies) {
                if("JSESSIONID".equals(cookie.getName())){
                    String cookieValue = cookie.getValue();
                    session = myc.getSession(cookieValue);
                    if(session != null){
                        session.setAttribute(strKey, objValue);
                    }
                }
            }
        }
        HttpSession mycSession = myc.getSession(request.getSession(true).getId());
        if(null != mycSession){
            mycSession.setAttribute(strKey, objValue);
        }
	}
	
	public static void remove(HttpServletRequest request, String strKey) {
        HttpSession session = null;
        MySessionContext myc = MySessionContext.getInstance();
        Cookie[] cookies = request.getCookies();
        if(null != cookies){
            for (Cookie cookie : cookies) {
                if("JSESSIONID".equals(cookie.getName())){
                    String cookieValue = cookie.getValue();
                    session = myc.getSession(cookieValue);
                    if(session != null){
                        session.removeAttribute(strKey);
                    }
                }
            }
        }
        HttpSession mycSession = myc.getSession(request.getSession(true).getId());
        if(null != mycSession){
            mycSession.removeAttribute(strKey);
        }
	}
}
