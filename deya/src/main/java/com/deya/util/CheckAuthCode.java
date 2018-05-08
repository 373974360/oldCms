package com.deya.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>验证码验证</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Cicro</p>
 */

public class CheckAuthCode {
	/**
	 * 验证用户输入的验证码是否正确.(验证码输入框名称必须是　authcode)
	 * 
	 * @param HttpServletRequest           
	 * @return true or false	
	 */
	public static boolean isMatch(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String inputAuthCode = request.getParameter("authcode");
		// 检查输入框和session里的验证码是否为空
		if (inputAuthCode == null || session.getAttribute("AUTHCODE") == null) {
			return false;
		}

		String sessionAuthCode = (String)session.getAttribute("AUTHCODE");

		if (!inputAuthCode.equals(sessionAuthCode)) {
			return false;
		}
		return true;
	}
}
