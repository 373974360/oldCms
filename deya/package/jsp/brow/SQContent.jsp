<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%@page import="com.deya.wcm.bean.cms.category.CategoryBean"%>
<%@page import="com.deya.wcm.services.cms.category.CategoryManager"%>
<%@page import="com.deya.wcm.template.velocity.data.AppealData"%>
<%@page import="com.deya.wcm.bean.appeal.sq.SQBean"%>
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
	SQBean o = AppealData.getAppealContent(sq_id,request);
	JSONObject listResultJo = JSONObject.fromObject(o);
    String listResultStr = listResultJo.toString();
    if(jsoncallback.equals("")){
		out.println("["+listResultStr+"]");
	}else{
		out.println(jsoncallback+"(["+listResultStr+"])");
	}
	
	/**
	通过信件Id得到 信件内容
	http://www.nxszs.gov.cn/jsp/brow/SQContent.jsp?sq_id=39065
		sq_id      //信件ID
		返回数据格式：
		   sq_code     信件编号
		   sq_title2      信件标题
		   sq_content2  信件内容
		   sq_code     信件编号
		   sq_dtime    写信时间
		   sq_status   处理状态       3是已办结  负责是办理中
		   sq_reply    回复内容       
		   over_dtime  回复时间
	 */

%>
