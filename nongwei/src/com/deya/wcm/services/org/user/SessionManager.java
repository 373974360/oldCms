package com.deya.wcm.services.org.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {
	public static Object get(HttpServletRequest request, String strKey) {
		HttpSession hs = request.getSession(false);
		if (hs == null) {			
			return null;
		}		
		return hs.getAttribute(strKey);
	}

	public static void set(HttpServletRequest request, String strKey,
			Object objValue) {
		HttpSession hs = request.getSession(true);
		hs.setAttribute(strKey, objValue);	
		
	}
	
	public static void remove(HttpServletRequest request, String strKey) {
		HttpSession hs = request.getSession(true);
		hs.removeAttribute(strKey);
	}
}
