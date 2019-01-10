<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.util.FormatUtil,com.deya.project.zjzf.*"%>
<%@ page import="com.deya.util.DateUtil" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%



	String name  = FormatUtil.formatNullString(request.getParameter("name"));
	String xb  = FormatUtil.formatNullString(request.getParameter("xb"));
	String csny  = FormatUtil.formatNullString(request.getParameter("csny"));
	String mz  = FormatUtil.formatNullString(request.getParameter("mz"));
	String zzmm  = FormatUtil.formatNullString(request.getParameter("zzmm"));
	String card  = FormatUtil.formatNullString(request.getParameter("card"));
	String gzdw  = FormatUtil.formatNullString(request.getParameter("gzdw"));
	String zhiwu  = FormatUtil.formatNullString(request.getParameter("zhiwu"));
	String zhicheng  = FormatUtil.formatNullString(request.getParameter("zhicheng"));
	String hkszd  = FormatUtil.formatNullString(request.getParameter("hkszd"));
	String phone  = FormatUtil.formatNullString(request.getParameter("phone"));
	String tel  = FormatUtil.formatNullString(request.getParameter("tel"));
	String address  = FormatUtil.formatNullString(request.getParameter("address"));
	String txdz  = FormatUtil.formatNullString(request.getParameter("txdz"));
	String postcode  = FormatUtil.formatNullString(request.getParameter("postcode"));
	String email  = FormatUtil.formatNullString(request.getParameter("email"));
	String type  = FormatUtil.formatNullString(request.getParameter("type"));
		
	String codeSession = (String)request.getSession().getAttribute("valiCode");



	ZjzfTypeBean bean = ZjzfTypeManager.getZjzfTypeBean(Integer.parseInt(type));

	String startTime = bean.getStart_time();
	String endTime = bean.getEnd_time();
	String currDate = DateUtil.getCurrentDateTime("yyyy-MM-dd");

	if(StringUtils.isNotEmpty(startTime)){
		if(!DateUtil.compare_date(currDate,startTime)){
			out.println("<script>");
			out.println("top.alert('报名还未开始请稍后提交')");
			out.println("</script>");
			return;
		}
	}


	if(StringUtils.isNotEmpty(endTime)) {
		if (!DateUtil.compare_date(currDate, endTime)) {
			out.println("<script>");
			out.println("top.alert('报名已结束')");
			out.println("</script>");
			return;
		}
	}



	
	String auth_code = request.getParameter("auth_code");
	System.out.println("******************************************************"+codeSession + "------------------------------------------------" +auth_code);
	if(!auth_code.equals(codeSession))
	{
		out.println("<script>");
		out.println("top.alert('验证码不正确')");
		out.println("top.changeCreateImage()");
		out.println("</script>");
		return;
	}
	
	ZJZFBean zjzf = new ZJZFBean();
	zjzf.setAddress(address);
	zjzf.setCard(card);
	zjzf.setCsny(csny);
	zjzf.setEmail(email);
	zjzf.setGzdw(gzdw);
	zjzf.setHkszd(hkszd);
	zjzf.setMz(mz);
	zjzf.setName(name);
	zjzf.setPhone(phone);
	zjzf.setPostcode(postcode);
	zjzf.setTel(tel);
	zjzf.setTxdz(txdz);
	zjzf.setXb(xb);
	zjzf.setZhicheng(zhicheng);
	zjzf.setZhiwu(zhiwu);
	zjzf.setZzmm(zzmm);
	if(type != null && !"".equals(type))
	{
		zjzf.setType(Integer.parseInt(type));
	}
	else
	{
		zjzf.setType(1);
	}
	
	if(ZJZFManager.insertZJZF(zjzf))
	{
		out.println("<script>");
		out.println("top.alert('报名成功，感谢您的参与')");		
		out.println("top.location.reload()");		
		out.println("</script>");
	}else
	{
		out.println("<script>");
		out.println("top.alert('报名成功，请重新提交')");				
		out.println("</script>");
	}
%>
