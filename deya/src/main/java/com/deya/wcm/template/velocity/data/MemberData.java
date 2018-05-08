package com.deya.wcm.template.velocity.data;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.member.MemberBean;
import com.deya.wcm.bean.member.MemberConfigBean;
import com.deya.wcm.services.member.MemberConfigManager;
import com.deya.wcm.services.member.MemberLogin;

public class MemberData {
	
	/**得到会员配置信息
	 * */
	public static MemberConfigBean getMemberConfig()
	{
		return MemberConfigManager.getMemberConfigBean();
	}
	
	/**得到会员配置信息
	 * */
	public static String getRegSerTerms()
	{
		return getMemberConfig().getReg_pro();
	}
	
	public static MemberBean getMemberObject(HttpServletRequest request)
	{
		return MemberLogin.getMemberBySession(request);
	}
}
