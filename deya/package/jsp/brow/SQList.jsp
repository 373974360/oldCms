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
    String model_id = request.getParameter("model_id"); //业务ID  多个业务用逗号隔开
	if(model_id==null)
	{
		model_id="";
	} 
	String size = request.getParameter("size"); //页面列表条数
	if(size==null)
	{
		size="";
	} 
	String cur_page = request.getParameter("cur_page"); //当前页数
	if(cur_page==null)
	{
		cur_page="";
	}
	String dept_id = request.getParameter("dept_id"); // 处理部门
	if(dept_id==null)
	{
		dept_id="";
	}
	String related_dept = request.getParameter("related_dept"); // 处理部门
	if(related_dept==null)
	{ 
		related_dept="";
	}
	String str = "model_id="+model_id+";publish_status=1;is_open=1;size="+size+";cur_page="+cur_page+";related_dept="+related_dept;
	List<SQBean> list = AppealData.getAppealList(str);
    	JSONArray listResultJo = JSONArray.fromObject(list);
        String listResultStr = listResultJo.toString();
        if(jsoncallback.equals("")){
    		out.println(listResultStr);
    	}else{
    		out.println(jsoncallback+"("+listResultStr+")");
    	}

	
	/**
	通过参数 得到 信件列表
	http://www.nxszs.gov.cn/jsp/brow/SQList.jsp?model_id=64,65,66,67,68,69,70,71,72,73&related_dept=130&cur_page=1&size=10
		model_id      //业务ID  多个业务用逗号隔开
		related_dept  //  收件部门ID
		dept_id       //  处理部门ID
		cur_page      //  当前第几页
		size          //  列表条数
		返回数据格式：
		    sq_title2   是标题
	 */

%>
