<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.bean.appeal.model.*,com.deya.wcm.services.appeal.model.*"%>
<%@page import="com.deya.wcm.bean.appeal.sq.*,com.deya.wcm.services.appeal.sq.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.system.comment.*,com.deya.wcm.bean.system.comment.*"%>
<%		
		String codeSession = (String)request.getSession().getAttribute("valiCode");
		String item_id = request.getParameter("item_id");
		String model_id = request.getParameter("model_id");
		String c_content = request.getParameter("comment_content");

        if("".equals(c_content)){
			out.println("<script>");
			out.println("top.alert('内容不能为空！')");
			out.println("</script>");
			return;
		}

		String auth_code = request.getParameter("auth_code");
		if("".equals(auth_code)){
			out.println("<script>");
			out.println("top.alert('验证码不能为空！')");
			out.println("</script>");
			return;
		}
		if(!auth_code.equals(codeSession))
		{
			out.println("<script>");
			out.println("top.alert('验证码不正确')");
			out.println("top.changeCreateImage()");
			out.println("</script>");
			return;
		}
		
        CommentBean cb = new CommentBean();
        cb.setCmt_content(c_content);
        cb.setApp_id("appeal");
        cb.setItem_id(item_id);
        cb.setSite_id("");
        cb.setCmt_ip(FormatUtil.getIpAddr(request));
        
		if(CommentManager.insertComment(cb))
		{
			out.println("<script>");
			out.println("top.alert('评论成功，欢迎您的参与')");
            out.println("top.location.reload()");
			out.println("</script>");
			return;
		}else
		{
			out.println("<script>");
			out.println("top.alert('评论失败')");
			out.println("</script>");
			return;
		}
%>