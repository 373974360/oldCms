<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.util.*,com.deya.project.dz_siteError.*"%>
<%@ page import="org.apache.commons.httpclient.HttpClient" %>
<%@ page import="org.apache.commons.httpclient.methods.PostMethod" %>
<%@ page import="org.apache.commons.httpclient.params.HttpMethodParams" %>
<%
	String typeId = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("typeId")));
	String submitSiteName = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("submitSiteName")));
	String submitUser = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("submitUser")));
	String submitUserPhone = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("submitUserPhone")));
	String submitUserEmail = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("submitUserEmail")));
	String submitDescription = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("submitDescription")));
	String errorUrl = Encode.iso_8859_1ToUtf8(FormatUtil.formatNullString(request.getParameter("errorUrl")));
	String codeSession = (String)request.getSession().getAttribute("valiCode");
	String auth_code = request.getParameter("auth_code");
	if(!auth_code.equals(codeSession))
	{
		response.getWriter().println("<script>");
		response.getWriter().println("top.alert('验证码不正确')");
		response.getWriter().println("top.changeCreateImage()");
		response.getWriter().println("</script>");
		return;
	}
	SiteErrorBean seb = new SiteErrorBean();
	seb.setTypeId(Integer.parseInt(typeId));
	seb.setSubmitSiteName(submitSiteName);
	seb.setSubmitUser(submitUser);
	seb.setSubmitUserPhone(submitUserPhone);
	seb.setSubmitUserEmail(submitUserEmail);
	seb.setSubmitDescription(submitDescription);
	seb.setErrorUrl(errorUrl);
	seb.setStatus("0");
	if(SiteErrorManager.insertSiteError(seb,null))
	{
		if(errorUrl != null && (errorUrl.contains("www.weinan") || errorUrl.contains("top.weinan") || errorUrl.contains("njsz.weinan") || errorUrl.contains("jjzb.weinan") || errorUrl.contains("ms.weinan") || errorUrl.contains("en.weinan")))
		{
			Map<String,String> m = new HashMap<String, String>();
			List<ErrorHandleUserBean> allErrorHandleUserList = ErrorHandleUserManager.getAllErrorHandleUserList(m);
			if(allErrorHandleUserList != null && allErrorHandleUserList.size() > 0)
			{
				for (ErrorHandleUserBean errorHandleUserBean : allErrorHandleUserList) {
					if("1".equals(errorHandleUserBean.getIsSendMsg()))
					{
						HttpClient httpclient = new HttpClient();
						PostMethod post = new PostMethod("http://sx.ums86.com:8899/sms/Api/Send.do");
						post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
						post.addParameter("SpCode", "218968");
						post.addParameter("LoginName", "wn_xxh");
						post.addParameter("Password", "wn1234");
						post.addParameter("MessageContent", "市政府网站信息有误，请及时更正。链接地址：" + errorUrl);
						post.addParameter("UserNumber", errorHandleUserBean.getPhone());
						post.addParameter("SerialNumber", "");
						post.addParameter("ScheduleTime", "");
						post.addParameter("whitevalid", "1");
						post.addParameter("f", "1");
						httpclient.executeMethod(post);
						String info = new String(post.getResponseBody(),"gbk");
						System.out.println(info);
					}
				}
			}
		}
	}
%>
