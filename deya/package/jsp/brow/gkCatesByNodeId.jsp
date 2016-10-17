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
if(jsoncallback==null){
	jsoncallback = "";
}
    String node_id = request.getParameter("node_id"); //公开节点分类Id
	if(node_id==null)
	{
		node_id="";
	} 
	String cate_id = request.getParameter("cate_id"); //公开节点分类Id
	if(cate_id==null)
	{
		cate_id="";
	} 
    if(cate_id==""){ //通过公开节点Id得到 公开节点一级栏目
    	List<CategoryBean> list = CategoryManager.getCategoryListBySiteID(node_id,0);
    	JSONArray listResultJo = JSONArray.fromObject(list);
        String listResultStr = listResultJo.toString();
        if(jsoncallback.equals("")){
    		out.println(listResultStr);
    	}else{
    		out.println(jsoncallback+"("+listResultStr+")");
    	}
    }else{
    	List<CategoryBean> list = CategoryManager.getChildCategoryList(Integer.valueOf(cate_id),node_id);
    	JSONArray listResultJo = JSONArray.fromObject(list);
        String listResultStr = listResultJo.toString();
        if(jsoncallback.equals("")){
    		out.println(listResultStr);
    	}else{
    		out.println(jsoncallback+"("+listResultStr+")");
    	}
    }
	
	/**
	通过公开节点Id得到 公开节点一级栏目
	http://www.nxszs.gov.cn/jsp/brow/gkCatesByNodeId.jsp?node_id=GKjljcwyh
		 node_id  公开节点Id
		返回数据格式：
		[{"app_id":"zwgk","cat_class_id":10783,"cat_cname":"组织机构","cat_code":"01","cat_description":"","cat_ename":"zzjg","cat_id":11246,"cat_keywords":"",
			"cat_level":1,"cat_memo":"","cat_position":"$0$11246$","cat_sort":9999,"cat_source_id":0,"cat_type":0,"class_id":1008,"hj_sql":"",
			"id":11246,"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,
			"is_mutilpage":0,"is_show":1,"is_show_tree":1,"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":0,"site_id":"GKjljcwyh",
			"template_index":0,"template_list":79,"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,"zt_cat_id":0},
			{"app_id":"zwgk","cat_class_id":10784,"cat_cname":"法规文件","cat_code":"02","cat_description":"","cat_ename":"fgwj","cat_id":11251,
				"cat_keywords":"","cat_level":1,"cat_memo":"","cat_position":"$0$11251$","cat_sort":9999,"cat_source_id":0,"cat_type":0,"class_id":1008,
				"hj_sql":"","id":11251,"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,
				"is_mutilpage":0,"is_show":1,"is_show_tree":1,"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":0,"site_id":"GKjljcwyh",
				"template_index":0,"template_list":79,"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,"zt_cat_id":0},{"app_id":"zwgk",
					"cat_class_id":10785,"cat_cname":"规划计划","cat_code":"03","cat_description":"","cat_ename":"ghjh","cat_id":11255,"cat_keywords":"",
					"cat_level":1,"cat_memo":"","cat_position":"$0$11255$","cat_sort":9999,"cat_source_id":0,"cat_type":0,"class_id":1008,"hj_sql":"",
					"id":11255,"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,
					"is_mutilpage":0,"is_show":1,"is_show_tree":1,"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":0,"site_id":"GKjljcwyh",
					"template_index":0,"template_list":79,"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,"zt_cat_id":0},{"app_id":"zwgk",
						"cat_class_id":10786,"cat_cname":"工作动态","cat_code":"04","cat_description":"","cat_ename":"gzdt","cat_id":11256,"cat_keywords":"",
						"cat_level":1,"cat_memo":"","cat_position":"$0$11256$","cat_sort":9999,"cat_source_id":0,"cat_type":0,"class_id":1008,"hj_sql":"",
						"id":11256,"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,
						"is_mutilpage":0,"is_show":1,"is_show_tree":1,"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":0,
						"site_id":"GKjljcwyh","template_index":0,"template_list":79,"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,
						"zt_cat_id":0},{"app_id":"zwgk","cat_class_id":10787,"cat_cname":"财政信息","cat_code":"05","cat_description":"","cat_ename":"czxx",
							"cat_id":11259,"cat_keywords":"","cat_level":1,"cat_memo":"","cat_position":"$0$11259$","cat_sort":9999,"cat_source_id":0,"cat_type":0,
							"class_id":1008,"hj_sql":"","id":11259,"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,
							"is_generate_index":0,"is_mutilpage":0,"is_show":1,"is_show_tree":1,"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"",
							"parent_id":0,"site_id":"GKjljcwyh","template_index":0,"template_list":79,"urlrule_content":"","urlrule_index":"","urlrule_list":"",
							"wf_id":0,"zt_cat_id":0},{"app_id":"zwgk","cat_class_id":10788,"cat_cname":"人事信息","cat_code":"06","cat_description":"",
								"cat_ename":"rsxx","cat_id":11262,"cat_keywords":"","cat_level":1,"cat_memo":"","cat_position":"$0$11262$","cat_sort":9999,
								"cat_source_id":0,"cat_type":0,"class_id":1008,"hj_sql":"","id":11262,"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,
								"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,"is_mutilpage":0,"is_show":1,"is_show_tree":1,"is_sub":false,
								"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":0,"site_id":"GKjljcwyh","template_index":0,"template_list":79,
								"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,"zt_cat_id":0}] 
	*/
	
	
	/**
	通过公开节点Id和栏目id 得到 公开节点下级栏目
	http://www.nxszs.gov.cn/jsp/brow/gkCatesByNodeId.jsp?node_id=GKjljcwyh&cate_id=11246
		 node_id  公开节点Id
		 cate_id  栏目Id
		返回数据格式：
		[{"app_id":"zwgk","cat_class_id":10790,"cat_cname":"部门职能","cat_code":"01","cat_description":"","cat_ename":"bmzn","cat_id":11247,"cat_keywords":"",
			"cat_level":2,"cat_memo":"","cat_position":"$0$11246$11247$","cat_sort":9999,"cat_source_id":0,"cat_type":0,"class_id":10783,"hj_sql":"","id":11247,
			"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,"is_mutilpage":0,"is_show":1,
			"is_show_tree":1,"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":11246,"site_id":"GKjljcwyh","template_index":0,"template_list":79,
			"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,"zt_cat_id":0},{"app_id":"zwgk","cat_class_id":10791,"cat_cname":"部门领导及业务分工",
				"cat_code":"02","cat_description":"","cat_ename":"bmldjywfg","cat_id":11248,"cat_keywords":"","cat_level":2,"cat_memo":"",
				"cat_position":"$0$11246$11248$","cat_sort":9999,"cat_source_id":0,"cat_type":0,"class_id":10783,"hj_sql":"","id":11248,"is_allow_comment":0,
				"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,"is_mutilpage":0,"is_show":1,"is_show_tree":1,
				"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":11246,"site_id":"GKjljcwyh","template_index":0,"template_list":79,
				"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,"zt_cat_id":0},{"app_id":"zwgk","cat_class_id":10792,"cat_cname":"内设机构",
					"cat_code":"03","cat_description":"","cat_ename":"nsjg","cat_id":11249,"cat_keywords":"","cat_level":2,"cat_memo":"",
					"cat_position":"$0$11246$11249$","cat_sort":9999,"cat_source_id":0,"cat_type":0,"class_id":10783,"hj_sql":"","id":11249,"is_allow_comment":0,
					"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,"is_mutilpage":0,"is_show":1,"is_show_tree":1,
					"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":11246,"site_id":"GKjljcwyh","template_index":0,"template_list":79,
					"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,"zt_cat_id":0},{"app_id":"zwgk","cat_class_id":10793,"cat_cname":"直属机构",
						"cat_code":"04","cat_description":"","cat_ename":"zsjg","cat_id":11250,"cat_keywords":"","cat_level":2,"cat_memo":"",
						"cat_position":"$0$11246$11250$","cat_sort":9999,"cat_source_id":0,"cat_type":0,"class_id":10783,"hj_sql":"","id":11250,
						"is_allow_comment":0,"is_allow_submit":0,"is_archive":0,"is_comment_checked":0,"is_disabled":1,"is_generate_index":0,"is_mutilpage":0,
						"is_show":1,"is_show_tree":1,"is_sub":false,"is_sync":0,"is_wf_publish":0,"jump_url":"","parent_id":11246,"site_id":"GKjljcwyh",
						"template_index":0,"template_list":79,"urlrule_content":"","urlrule_index":"","urlrule_list":"","wf_id":0,"zt_cat_id":0}]
	*/

%>
