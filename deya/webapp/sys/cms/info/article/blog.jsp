<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.deya.wcm.bean.cms.info.InfoBean" %>
<%@ page import="com.deya.wcm.services.cms.info.InfoBaseRPC" %>
<%@ page import="com.deya.wcm.services.media.Blog" %>
<%@ page import="com.deya.wcm.bean.cms.info.ArticleBean" %>
<%@ page import="com.deya.wcm.services.cms.info.article.ArticleManager" %>
<%@ page import="com.deya.util.FormatUtil" %>
<%
    String infoId = request.getParameter("id");
    InfoBean bean = InfoBaseRPC.getInfoById(infoId);
    String txt = bean.getTitle()+"  ";
    ArticleBean articleBean = ArticleManager.getArticle(bean.getInfo_id()+"",bean.getSite_id());
    if(articleBean!=null){
        String content = FormatUtil.fiterHtmlTag(articleBean.getInfo_content());
        if(content.length()>100){
            content = content.substring(0,100);
        }
        txt+=content;
    }
    Blog.updateStatus(txt,bean.getContent_url());
    PrintWriter outs = response.getWriter();
    outs.println("{\"data\":\"ok\"}");
%>
