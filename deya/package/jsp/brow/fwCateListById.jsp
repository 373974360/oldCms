<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%@page import="com.deya.wcm.bean.cms.category.CategoryBean"%>
<%
   
response.setContentType("application/json");//这个一定要加

    String jsoncallback = (String)request.getParameter("callback");
    String nodcat_id = request.getParameter("cat_id"); //公开节点分类Id
	if(nodcat_id==null)
	{
		nodcat_id="";
	}
    
	List<CategoryBean> listResult = InfoUtilData.getChildCategoryList(nodcat_id,"ggfw");
	JSONArray listResultJo = JSONArray.fromObject(listResult);
    String listResultStr = listResultJo.toString();
    out.println(jsoncallback+"("+listResultStr+")");
	  
	/**
	通过公开节点分类Id得到 公开节点列表
	http://www.nxszs.gov.cn/jsp/brow/fwCateListById.jsp?cat_id=10187  个人办事
	http://www.nxszs.gov.cn/jsp/brow/fwCateListById.jsp?cat_id=10212  企业办事
		返回数据格式：
	*/

%>
