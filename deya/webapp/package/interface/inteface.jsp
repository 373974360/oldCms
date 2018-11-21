<%@ page contentType="application/json; charset=utf-8"%>
<%@ page import="com.deya.util.FormatUtil"%>
<%@ page import="com.deya.wcm.bean.cms.info.ArticleBean"%>
<%@ page import="com.deya.wcm.services.cms.info.InfoBaseManager"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.deya.wcm.services.zwgk.index.IndexManager"%>
<%@ page import="com.deya.util.DateUtil"%>
<%@ page import="com.deya.wcm.services.cms.info.ModelUtil"%>
<%
    String callback = request.getParameter("callback");
    if(callback != null && !"".equals(callback)){
        out.println(callback + "(" + setNews(request) + ")");
    }else{
        out.println(setNews(request));
    }
%>
<%!
    public String setNews(HttpServletRequest request){
        String json = "";
        String site_id = "CMSxxxq";
        int cat_id = 10013;
        String title = FormatUtil.formatNullString(request.getParameter("title"));//标题
        String doc_no = FormatUtil.formatNullString(request.getParameter("doc_no"));//发文字号
        String publish_dept = FormatUtil.formatNullString(request.getParameter("publish_dept"));//发布机构
        String keywords = FormatUtil.formatNullString(request.getParameter("keywords"));//关键字
        String publish_date = FormatUtil.formatNullString(request.getParameter("publish_date"));//发布日期
        String info_content = FormatUtil.formatNullString(request.getParameter("info_content"));//正文内容
        ArticleBean article = new ArticleBean();
        article.setCat_id(cat_id);
        article.setInput_user(1);//默认录入人是系统管理员
        article.setInfo_status(0);//默认状态为草稿
        article.setModel_id(11);
        article.setApp_id("cms");
        article.setSite_id(site_id);
        int id = InfoBaseManager.getNextInfoId();
        article.setId(id);
        article.setInfo_id(id);
        article.setHits(0);
        article.setSubtitle("");
        article.setTags("");
        article.setAuthor("");
        article.setSource("");
        article.setEditor("");
        article.setDescription("");
        article.setThumb_url("");
        Map<String,String> imap = IndexManager.getIndex(site_id,article.getCat_id()+"","","","");
        if(!imap.isEmpty()&&imap.containsKey("gk_index")){
            article.setGk_index(imap.get("gk_index"));
        }
        if(title != null && !"".equals(title) && !"null".equals(title)){
            article.setTitle(title);
        }else{
            article.setTitle("");
        }
        if(doc_no != null && !"".equals(doc_no) && !"null".equals(doc_no)){
            article.setDoc_no(doc_no);
        }else{
            article.setDoc_no("");
        }
        if(publish_dept != null && !"".equals(publish_dept) && !"null".equals(publish_dept)){
            article.setGk_input_dept(publish_dept);
        }else{
            article.setGk_input_dept("");
        }
        if(keywords != null && !"".equals(keywords) && !"null".equals(keywords)){
            article.setTopic_key(keywords);
        }else{
            article.setTopic_key("");
        }
        if(info_content != null && !"".equals(info_content) && !"null".equals(info_content)){
            article.setInfo_content(info_content);
        }else{
            article.setInfo_content("");
        }
        if(publish_date != null && !"".equals(publish_date) && !"null".equals(publish_date)){
            article.setInput_dtime(publish_date);
            article.setReleased_dtime(publish_date);
        }else{
            article.setInput_dtime(DateUtil.getCurrentDateTime());
            article.setReleased_dtime(DateUtil.getCurrentDateTime());
        }
        if(ModelUtil.insert(article,"article",null)){
            json = "{\"code\":1,\"msg\":\"success\"}";
        }else{
            json = "{\"code\":0,\"msg\":\"error\"}";
        }
        return "["+json+"]";
    }
%>
