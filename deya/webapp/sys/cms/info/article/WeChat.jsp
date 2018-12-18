<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.deya.wcm.bean.cms.info.ArticleBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.deya.wcm.services.cms.info.article.ArticleManager" %>
<%@ page import="com.deya.wcm.services.media.Weixin" %>
<%
    String infoId = request.getParameter("id");
    String site_id = request.getParameter("siteid");
    boolean flag = true;
    List<ArticleBean> list = new ArrayList<>();
    String arr[] = null;
    if (!"".equals(infoId)){
        arr = infoId.split(",");
        for (int i = 0; i < arr.length; i++) {
            ArticleBean article = ArticleManager.getArticle(arr[i], site_id);
            list.add(article);
        }
    }
    try {
        flag = Weixin.doPush(list,site_id);
    } catch (Exception e) {
        e.printStackTrace();
    }
    PrintWriter outs = response.getWriter();
    if (flag == true) {
        outs.println("{\"data\":\"ok\"}");
    } else {
        outs.println("{\"data\":\"bok\"}");
    }


%>
