package com.deya.wcm.services.member;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.member.MemberBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.services.org.user.SessionManager;

public class MemberLogin {
	/**
	 * 会员登录
	 */
	public static String memberLogin(String me_account,String me_password,HttpServletRequest request)
	{
		String result = MemberManager.memberLogin(me_account,me_password);
		if("true".equals(result))
		{
			SessionManager.set(request, "cicro_wcm_member", MemberManager.getMemberBenaByAccount(me_account));
		}
		return result;
	}
	
	
	/**
	 * 从session得到会员对象
	 * 
	 * @param HttpServletRequest request           
	 * @return boolean
	 */
	public static MemberBean getMemberBySession(HttpServletRequest request)
	{
		return (MemberBean)SessionManager.get(request, "cicro_wcm_member");
	}
	
	/**
	 * 注销登录
	 * 
     * @param HttpServletRequest request     
	 * @return boolean
	 */
	public static boolean logout(HttpServletRequest request){
		SessionManager.remove(request, "cicro_wcm_member");
		return true;
	}
}
