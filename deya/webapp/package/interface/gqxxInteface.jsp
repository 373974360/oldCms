<%@ page import="java.util.List" %>
<%@ page import="com.deya.wcm.bean.cms.info.InfoBean" %>
<%@ page import="com.deya.wcm.services.cms.info.InfoBaseManager" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.deya.util.FormatUtil" %>
<%@ page import="com.deya.wcm.template.velocity.data.InfoUtilData" %>
<%@ page import="com.deya.wcm.bean.template.TurnPageBean" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%
    String action_type = request.getParameter("action_type");
    String callback = request.getParameter("callback");
    String result = "";

    if("news_list".equals(action_type))
    {
        result = getNewsList(request);
    }
    if("news_count".equals(action_type))
    {
        result = getNewsListCount(request);
    }
    if(callback!=null){
        out.println(callback+"("+result+")");
    }else{
        out.println(result);
    }
%>
<%!
    String title = "阎良";
    String site_id="CMSxaagri";

    public String getNewsList(HttpServletRequest request)
    {
        String json = "";
        String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
        String page = FormatUtil.formatNullString(request.getParameter("page"));
        String size = FormatUtil.formatNullString(request.getParameter("size"));
        String params = "site_id="+site_id+";title="+title+";cat_id="+cat_id+";size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
        List<InfoBean> info_list = InfoUtilData.getInfoList(params);
        if(info_list != null && info_list.size() > 0)
        {
            for(InfoBean info : info_list)
            {
                json += ",{\"info_id\":\""+info.getInfo_id()+"\",\"title\":\""+replaceFont(info.getTitle())+"\",\"model_id\":\""+info.getModel_id()+"\",\"content_url\":\""+info.getContent_url()+"\",\"description\":\""+replaceStr(info.getDescription())+"\",\"source\":\""+info.getSource()+"\",\"released_dtime\":\""+info.getReleased_dtime()+"\"}";
            }
            json = json.substring(1);
        }
        return "["+json+"]";
    }

    public String getNewsListCount(HttpServletRequest request)
    {
        String json = "";
        String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
        String page = FormatUtil.formatNullString(request.getParameter("page"));
        String size = FormatUtil.formatNullString(request.getParameter("size"));
        String params = "site_id="+site_id+";title="+title+";cat_id="+cat_id+";size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
        TurnPageBean tpb = InfoUtilData.getInfoCount(params);
        if(tpb != null)
        {
            json += "{\"count\":\"" + tpb.getCount() + "\",\"page_count\":\""+tpb.getPage_count()+"\",\"prev_num\":\""+tpb.getPrev_num()+"\",\"next_num\":\""+tpb.getNext_num()+"\"}";
        }
        return json;
    }

    public static String replaceStr(String str)
    {
        //return str.replaceAll("\"","'").replaceAll("\r|\n|\r\n","").replaceAll("<p.*?[^>]>|</p.*?[^>]>", "<p>");
        return str.replaceAll("\"","'").replaceAll("\r|\n|\r\n","").replaceAll("<p\\+.*?[^>]>|</p\\+.*?[^>]>", "<p>");
    }
    //替换标题里面的font标签
    public static String replaceFont(String str){
        return str.replaceAll("<font([^>]*)>", "").replaceAll("</font([^>]*)>", "");
    }
%>

