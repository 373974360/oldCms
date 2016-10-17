<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%@page import="com.deya.wcm.bean.cms.category.CategoryBean"%>
<%@page import="com.deya.wcm.services.cms.category.CategoryManager"%>
<%
   
response.setContentType("application/json");//这个一定要加

    String jsoncallback = (String)request.getParameter("callback");
	String cat_id = request.getParameter("cat_id"); //新闻栏目  如果是多个栏目 可以用逗号分隔 例如“123,123,4”
	if(cat_id==null)
	{
		cat_id="";
	}
	
	/** 通过上级节点id得到下级节点列表
	http://www.nxszs.gov.cn/jsp/brow/infoCategory.jsp?cat_id=10043
		cat_id 新闻栏目
	返回数据格式：
	[{"app_id":"cms","cat_class_id":0,"cat_cname":"国内要闻","cat_code":"","cat_description":"","cat_ename":"guonayaowen",
		"cat_id":10745,"cat_keywords":"","cat_level":3,"cat_memo":"","cat_position":"$0$10001$10043$10745$","cat_sort":9999,
		"cat_source_id":0,"cat_type":0,"class_id":0,"hj_sql":"","id":10745,"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,
		"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,"is_mutilpage":0,"is_show":1,"is_show_tree":1,"is_sub":false,"is_sync":0,
		"is_wf_publish":0,"jump_url":"","parent_id":10043,"site_id":"CMSCMSszs","template_index":0,"template_list":1,"urlrule_content":"",
		"urlrule_index":"","urlrule_list":"","wf_id":0,"zt_cat_id":0},{"app_id":"cms","cat_class_id":0,"cat_cname":"区内要闻","cat_code":"",
			"cat_description":"","cat_ename":"gounayaowen","cat_id":10746,"cat_keywords":"","cat_level":3,"cat_memo":"",
			"cat_position":"$0$10001$10043$10746$","cat_sort":9999,"cat_source_id":0,"cat_type":0,"class_id":0,"hj_sql":"","id":10746,
			"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,
			"is_mutilpage":0,"is_show":1,"is_show_tree":1,"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":10043,
			"site_id":"CMSCMSszs","template_index":0,"template_list":1,"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,
			"zt_cat_id":0},{"app_id":"cms","cat_class_id":0,"cat_cname":"本市要闻","cat_code":"","cat_description":"","cat_ename":"shinayaowen",
				"cat_id":10747,"cat_keywords":"","cat_level":3,"cat_memo":"","cat_position":"$0$10001$10043$10747$","cat_sort":9999,"cat_source_id":0,
				"cat_type":0,"class_id":0,"hj_sql":"","id":10747,"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,
				"is_disabled":1,"is_generate_index":0,"is_mutilpage":0,"is_show":1,"is_show_tree":1,"is_sub":false,"is_sync":0,"is_wf_publish":0,
				"jump_url":"","parent_id":10043,"site_id":"CMSCMSszs","template_index":0,"template_list":1,"urlrule_content":"","urlrule_index":"",
				"urlrule_list":"","wf_id":0,"zt_cat_id":0}] 
	*/
	String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
    
	//List<CategoryBean> listResult = InfoUtilData.getChildCategoryList(cat_id,site_id);
	 List<CategoryBean> listResult = CategoryManager.getChildCategoryList(Integer.parseInt(cat_id),site_id);
	JSONArray listResultJo = JSONArray.fromObject(listResult);
    String listResultStr = listResultJo.toString();
    out.println(jsoncallback+"("+listResultStr+")");
%>
