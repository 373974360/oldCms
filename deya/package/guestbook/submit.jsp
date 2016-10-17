<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.bean.appCom.guestbook.*,com.deya.wcm.services.appCom.guestbook.*"%>
<%@page import="com.deya.util.FormatUtil,com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String gbs_id = FormatUtil.formatNullString(request.getParameter("gbs_id"));
	String title = FormatUtil.formatNullString(request.getParameter("title"));
	String nickname = FormatUtil.formatNullString(request.getParameter("nickname"));	
	String address = FormatUtil.formatNullString(request.getParameter("address"));
	String content = FormatUtil.formatNullString(request.getParameter("content"));
	String class_id = FormatUtil.formatNullString(request.getParameter("class_id"));	
	String realname = FormatUtil.formatNullString(request.getParameter("realname"));
	String phone = FormatUtil.formatNullString(request.getParameter("phone"));
	String tel = FormatUtil.formatNullString(request.getParameter("tel"));
	String post_code = FormatUtil.formatNullString(request.getParameter("post_code"));
	String idcard = FormatUtil.formatNullString(request.getParameter("idcard"));
	String vocation = FormatUtil.formatNullString(request.getParameter("vocation"));
	String age = FormatUtil.formatNullString(request.getParameter("age"));
	String sex = FormatUtil.formatNullString(request.getParameter("sex"));
	String member_id = FormatUtil.formatNullString(request.getParameter("member_id"));
	
	String auth_code = FormatUtil.formatNullString(request.getParameter("auth_code"));
	if(gbs_id == null || "".equals(gbs_id))
		return;
	
	//判断是否需要验证码
	GuestBookSub gbs = GuestBookSubManager.getGuestBookSub(Integer.parseInt(gbs_id));
	if(gbs.getIs_auth_code() == 1)
	{
		String codeSession = (String)request.getSession().getAttribute("valiCode");		
		if(!auth_code.equals(codeSession))
		{
			out.println("<script>");
			out.println("top.alert('验证码不正确')");
			out.println("top.changeCreateImage()");
			out.println("</script>");
			return;
		}
	}
	
	GuestBookBean bean = new GuestBookBean();
	if(gbs.getDirect_publish() == 1)//主题中设置是否直接发布
		bean.setPublish_status(1);
	bean.setSite_id(site_id);
	bean.setGbs_id(Integer.parseInt(gbs_id));
	bean.setTitle(title);
	bean.setNickname(nickname);
	bean.setAddress(address);
	bean.setContent(content);
	bean.setClass_id(class_id);
	bean.setIp(request.getRemoteAddr());	
	bean.setRealname(realname);
	bean.setPhone(phone);
	bean.setTel(tel);
	bean.setPost_code(post_code);
	bean.setIdcard(idcard);
	bean.setVocation(vocation);
	bean.setAge(age);
	try{
		if(sex != null && !"".equals(sex))
		{
			bean.setSex(Integer.parseInt(sex));
		}
		if(member_id != null && !"".equals(member_id))
		{
			bean.setSex(Integer.parseInt(member_id));
		}
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	
	if(GuestBookManager.insertGuestBook(bean))
	{
		out.println("<script>");
		out.println("top.alert('提交成功，感谢您的参与')");
		out.println("</script>");
		return;
	}else
	{
		out.println("<script>");
		out.println("top.alert('提交失败，请重新提交')");
		out.println("</script>");
	}
%>