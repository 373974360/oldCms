<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%
   
response.setContentType("application/json");//这个一定要加

    String jsoncallback = (String)request.getParameter("callback");
    String nodcat_id = request.getParameter("nodcat_id"); //公开节点分类Id
	if(nodcat_id==null)
	{
		nodcat_id="";
	}
    
	List<com.deya.wcm.bean.zwgk.node.GKNodeBean> listResult = com.deya.wcm.services.zwgk.node.GKNodeRPC.getGKNodeListByCateID(Integer.parseInt(nodcat_id));
	JSONArray listResultJo = JSONArray.fromObject(listResult);
    String listResultStr = listResultJo.toString();
    out.println(jsoncallback+"("+listResultStr+")");
	  
	/**
	通过公开节点分类Id得到 公开节点列表
	http://www.nxszs.gov.cn/jsp/brow/gkNodeListByType.jsp?nodcat_id=3
		   nodcat_id  公开节点分类Id
		返回数据格式：
		[{"address":"","apply_name":"6-大武口区","dept_code":"6402200","dept_id":6,"email":"","fax":"","id":66,
			"index_template_id":"55","is_apply":0,"nodcat_id":11,"node_demo":"","node_fullname":"大武口区",
			"node_id":"GKdwkq","node_name":"大武口区","node_status":0,"office_dtime":"","postcode":"",
			"rela_site_id":"CMSCMSszs","sort_id":1,"tel":""},{"address":"","apply_name":"6-惠农区",
				"dept_code":"6402300","dept_id":6,"email":"","fax":"","id":67,"index_template_id":"55","is_apply":0,
				"nodcat_id":11,"node_demo":"","node_fullname":"惠农区","node_id":"GKhnq","node_name":"惠农区",
				"node_status":0,"office_dtime":"","postcode":"","rela_site_id":"CMSCMSszs","sort_id":2,"tel":""},
				{"address":"","apply_name":"6402300","dept_code":"6402100","dept_id":6,"email":"","fax":"","id":68,
					"index_template_id":"55","is_apply":0,"nodcat_id":11,"node_demo":"","node_fullname":"平罗县",
					"node_id":"GKplx","node_name":"平罗县","node_status":0,"office_dtime":"","postcode":"",
					"rela_site_id":"CMSCMSszs","sort_id":3,"tel":""}]
	*/

%>
