<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%@page import="com.deya.wcm.bean.cms.category.CategoryBean"%>
<%@page import="com.deya.wcm.services.cms.category.CategoryManager"%>
<%@page import="com.deya.wcm.template.velocity.data.*"%>
<%@page import="com.deya.wcm.bean.appeal.sq.SQBean"%>
<%@page import="com.deya.wcm.bean.template.TurnPageBean"%>
<%@page import="com.deya.wcm.bean.system.comment.CommentBean"%>
<%
   
response.setContentType("application/json");//这个一定要加

String jsoncallback = (String)request.getParameter("callback");
if(jsoncallback==null){
	jsoncallback = "";
}
    String sq_id = request.getParameter("sq_id"); //信件ID
	if(sq_id==null)
	{
		sq_id="";
	}
	String size = request.getParameter("size"); //页面列表条数
	if(size==null)
	{
		size="";
	} 
	String cur_page = request.getParameter("cur_page"); //当前页数
	if(cur_page==null)
	{
		cur_page="1";
	}
    String str = "item_id="+sq_id+";app_id=appeal;size="+size+";cur_page="+cur_page;
	List<CommentBean> list = CommentData.getCommentList(str);
	JSONArray listResultJo = JSONArray.fromObject(list);
    String listResultStr = listResultJo.toString();
        if(jsoncallback.equals("")){
    		out.println(listResultStr);
    	}else{
    		out.println(jsoncallback+"("+listResultStr+")");
    	}

	
	/**
	通过信件Id得到 信件 评论条数
	http://www.nxszs.gov.cn/jsp/brow/SQCommentCount.jsp?sq_id=39065
		sq_id      //信件ID
		返回数据格式： {"count":2,"cur_page":1,"next_num":1,"page_count":1,"page_size":10,"prev_num":1} 
    		count     评论条数
	 */

%>
