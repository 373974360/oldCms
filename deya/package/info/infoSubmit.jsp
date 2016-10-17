<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.util.FormatUtil,com.deya.wcm.bean.cms.info.ArticleBean,com.deya.wcm.services.cms.info.*"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%
	String cat_id = request.getParameter("cat_id");
	String title = FormatUtil.formatNullString(request.getParameter("title"));
	String sub_title = FormatUtil.formatNullString(request.getParameter("sub_title"));
    String input_user = FormatUtil.formatNullString(request.getParameter("input_user"));
	String description = FormatUtil.formatNullString(request.getParameter("description"));
	String tags = FormatUtil.formatNullString(request.getParameter("tags"));
	String editor = FormatUtil.formatNullString(request.getParameter("editor"));
	String source = FormatUtil.formatNullString(request.getParameter("source"));
	String author = FormatUtil.formatNullString(request.getParameter("author"));
	String info_content = FormatUtil.formatNullString(request.getParameter("info_content"));
		
	String codeSession = (String)request.getSession().getAttribute("valiCode");
	String auth_code = request.getParameter("auth_code");
	if(!auth_code.equals(codeSession))
	{
		out.println("<script>");
		out.println("top.alert('验证码不正确')");
		out.println("top.changeCreateImage()");
		out.println("</script>");
		return;
	}
	/*if(title != null && title.length() <= 10)
    {
    out.println("<script>");
    out.println("top.alert('标题为空或者标题太短，请重新填写！')");
            out.println("top.changeCreateImage()");
            out.println("</script>");
            return;
    }*/
	ArticleBean article = new ArticleBean();
    if(!"".equals(input_user))
    {
        article.setInput_user(Integer.parseInt(input_user));
    }
    else {
        article.setInput_user(1);
    }
	article.setInfo_status(2);
    article.setStep_id(0);
	article.setModel_id(11);
	article.setApp_id("cms");
	article.setSite_id(SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString()));
	
	int id = InfoBaseManager.getNextInfoId();
	article.setId(id);
	article.setInfo_id(id);
	article.setCat_id(Integer.parseInt(cat_id));
	article.setTitle(title);
	article.setSubtitle(sub_title);
	article.setTags(tags);
	article.setAuthor(author);
	article.setSource(source);
	article.setEditor(author);
	article.setInfo_content(info_content);
	article.setDescription(description);
	
	if(ModelUtil.insert(article,"article",null))
	{
		out.println("<script>");
		out.println("top.alert('信息添加成功，请等待管理员审核')");		
		out.println("top.document.form1.reset()");	
		out.println("top.location.reload()");		
		out.println("</script>");
	}else
	{
		out.println("<script>");
		out.println("top.alert('信息添加失败，请重新提交')");	
		out.println("</script>");
	}
%>
