<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%
   
response.setContentType("application/json");//这个一定要加

    String jsoncallback = (String)request.getParameter("callback");
    String listResultStr = com.deya.wcm.services.zwgk.node.GKNodeRPC.getGKNodeCategroyJSONTreeStr();
    out.println(jsoncallback+"("+listResultStr+")");
	  
	/**
	得到公开节点分类
	http://www.nxszs.gov.cn/jsp/brow/gkNodeType.jsp
		返回数据格式：
		null([{"id":7,"text":"信息公开","attributes":{"url":"","handl":""},"children":[{"id":3,"text":"中共石嘴山市委员会",
			"attributes":{"url":"","handl":""}},{"id":4,"text":"石嘴山市人民政府","attributes":{"url":"","handl":""}},
			{"id":5,"text":"石嘴山市法、检机构","attributes":{"url":"","handl":""}},{"id":6,"text":"石嘴山市群众团体",
				"attributes":{"url":"","handl":""}},{"id":8,"text":"石嘴山市直属事业单位","attributes":{"url":"","handl":""}},
				{"id":11,"text":"区县","attributes":{"url":"","handl":""}}]}]) 
	*/

%>
