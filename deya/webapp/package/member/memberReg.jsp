<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.member.*,com.deya.wcm.services.member.*,com.deya.util.*,com.deya.wcm.bean.member.*,com.deya.wcm.services.control.domain.*"%>
<%
	String me_id = FormatUtil.formatNullString(request.getParameter("me_id"));
	String me_account = FormatUtil.formatNullString(request.getParameter("me_account"));
	String me_password = FormatUtil.formatNullString(request.getParameter("me_password"));
	String me_realname = FormatUtil.formatNullString(request.getParameter("me_realname"));
	String me_card_id = FormatUtil.formatNullString(request.getParameter("me_card_id"));
	String me_phone = FormatUtil.formatNullString(request.getParameter("me_phone"));
	String me_email = FormatUtil.formatNullString(request.getParameter("me_email"));
	String me_nickname = FormatUtil.formatNullString(request.getParameter("me_nickname"));
	String me_sex = FormatUtil.formatNullString(request.getParameter("me_sex"));
	String me_age = FormatUtil.formatNullString(request.getParameter("me_age"));
	String me_vocation = FormatUtil.formatNullString(request.getParameter("me_vocation"));
	String me_tel = FormatUtil.formatNullString(request.getParameter("me_tel"));
	String me_address = FormatUtil.formatNullString(request.getParameter("me_address"));
	String mcat_id = request.getParameter("mcat_id");
	if(mcat_id == null || "".equals(mcat_id))
		mcat_id = "0";

	String auth_code = FormatUtil.formatNullString(request.getParameter("auth_code"));

	String action_type = FormatUtil.formatNullString(request.getParameter("action_type"));
	if("password".equals(action_type))
	{		
		MemberBean mb = MemberLogin.getMemberBySession(request);
		
		if(mb == null)
		{
			out.println("<script>");
			out.println("top.alert(\"您还没有登录，请先登录\")");
			out.println("</script>");
			return;
		}
		me_id = mb.getMe_id();
		if(MemberManager.updateMemberPassword(me_id,me_password))
		{
			//再登录一次，更新下session中的对象
			MemberRegisterBean mrb = MemberManager.getMemberRegisterBeanByID(me_id);
			MemberLogin.memberLogin(mrb.getMe_account(),mrb.getMe_password(),request);
			out.println("<script>");
			out.println("top.alert(\"密码更新成功，请使用新密码登录\")");
			out.println("top.location.reload();");
			out.println("</script>");
		}else
		{
			out.println("<script>");
			out.println("top.alert(\"密码更新失败\")");
			out.println("</script>");
		}
	}


	if("update".equals(action_type))
	{
		if(MemberLogin.getMemberBySession(request) == null)
		{
			out.println("<script>");
			out.println("top.alert(\"您还没有登录，请先登录\")");
			out.println("</script>");
			return;
		}
	
		MemberBean mb = new MemberBean();
		mb.setMe_realname(me_realname);
		mb.setMe_card_id(me_card_id);
		mb.setMe_phone(me_phone);
		mb.setMe_email(me_email);
		mb.setMe_nickname(me_nickname);
		mb.setMe_sex(Integer.parseInt(me_sex));
		mb.setMe_age(me_age);
		mb.setMe_vocation(me_vocation);
		mb.setMe_tel(me_tel);
		mb.setMe_address(me_address);	
		mb.setMe_id(me_id);
		
		if(MemberManager.updateMemberBrowser(mb))
		{
			//再登录一次，更新下session中的对象
			MemberRegisterBean mrb = MemberManager.getMemberRegisterBeanByID(me_id);
			MemberLogin.memberLogin(mrb.getMe_account(),mrb.getMe_password(),request);
			out.println("<script>");
			out.println("top.showMemberName();");
			out.println("top.alert(\"资料更新成功\")");
			out.println("top.location.reload();");
			out.println("</script>");
		}
		else
		{
			out.println("<script>");
			out.println("top.alert(\"资料更新失败\")");
			out.println("</script>");
		}
		return;
	}

	if("insert".equals(action_type))
	{
		String codeSession = (String)request.getSession().getAttribute("valiCode");
		if(!codeSession.equals(auth_code))
		{
			return;
		}

		MemberBean mb = new MemberBean();
		mb.setApp_id("cms");
		mb.setSite_id(SiteDomainManager.getSiteIDByDomain(request.getLocalName()));
		mb.setMe_realname(me_realname);
		mb.setMe_card_id(me_card_id);
		mb.setMe_phone(me_phone);
		mb.setMe_email(me_email);
		mb.setMe_nickname(me_nickname);
		mb.setMe_sex(Integer.parseInt(me_sex));
		mb.setMe_age(me_age);
		mb.setMe_vocation(me_vocation);
		mb.setMe_tel(me_tel);
		mb.setMe_address(me_address);
		mb.setMcat_id(mcat_id);
		
		MemberConfigBean config_bean = MemberConfigManager.getMemberConfigBean();
		//判断是否需要审核　0为不审核
		if(config_bean.getIs_check() == 0)
			mb.setMe_status(1);
		
		MemberRegisterBean mrb = new MemberRegisterBean();
		mrb.setMe_account(me_account);
		mrb.setMe_password(me_password);
		if(MemberManager.RegisterMember(mb,mrb))
		{
			out.println("<script>");
			if(config_bean.getIs_check() == 0)
			{
				MemberLogin.memberLogin(me_account,me_password,request);
				out.println("top.alert('注册成功')");
				out.println("top.location.href = '/member/memberInfo.htm'");
			}
			if(config_bean.getIs_check() == 1)
			{
				out.println("top.alert(\"注册成功,请等待管理员审核通过\")");
				out.println("top.location.href = '/'");
			}
			out.println("</script>");
		}else
		{
			out.println("<script>");
			out.println("top.alert('注册失败')");
			out.println("</script>");
		}
		return;
	}
%>