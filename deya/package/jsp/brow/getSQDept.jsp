<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.bean.appeal.cpDept.*"%>
<%@page import="com.deya.wcm.template.velocity.data.AppealData"%>
<%
   
response.setContentType("application/json");//这个一定要加

    String jsoncallback = (String)request.getParameter("callback");
    String model_id = (String)request.getParameter("model_id");
    List<CpDeptBean> list = AppealData.getAppealDeptList(model_id);
    String json = "";
    if(list != null && list.size() > 0)
    {
    	for(CpDeptBean cd : list)
    	{
    		json += ",{\"id\":"+cd.getDept_id()+",\"text\":\""+cd.getDept_name()+"\"}";
    	}
    	if(json != null && !"".equals(json))
    		json = "["+json.substring(1)+"]";
    }

out.println(jsoncallback+"("+json+")");
%>
