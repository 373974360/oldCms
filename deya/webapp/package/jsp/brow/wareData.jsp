<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%@page import="com.deya.wcm.bean.system.ware.*"%>
<%@page import="com.deya.wcm.services.system.ware.WareInfoManager"%>
<%
	response.setContentType("application/json");//这个一定要加

    String jsoncallback = (String)request.getParameter("callback");
    if(jsoncallback==null){
    	jsoncallback = "";
    }
	String ware_id = request.getParameter("ware_id"); //标签ID
	if(ware_id==null || ware_id.equals(""))
	{
		ware_id="";
	}
	String size_str = request.getParameter("size");//当前页数
	int size = 4;
	if(size_str!=null)
	{
		size=Integer.parseInt(size_str);
	} 
	
	/**
	http://www.democms.com/jsp/brow/wareData.jsp?callback=?&ware_id=4&size=3
		ware_id  标签ID
		size 每页条数  默认值为4
		
	返回数据格式：
	?([{"app_id":"cms","content_url":"/xinwendongtai/tpxw/37823.htm","description":"","id":1978,"pre_title":"",
		"publish_dtime":"","site_id":"CMSCMSszs","sort_id":1,"subtitle":"","thumb_url":"http://www.nxszs.gov.cn/CMSCMSszs/201210/201210250610026.jpg",
		"title":"市领导古尔邦节前慰问穆斯林群众","title_color":"","ware_id":4,"winfo_id":1673},{"app_id":"cms","content_url":"/hx/tpxw/37788.htm",
			"description":"","id":1970,"pre_title":"","publish_dtime":"","site_id":"CMSCMSszs","sort_id":1,"subtitle":"",
			"thumb_url":"http://www.nxszs.gov.cn/CMSCMSszs/201210/201210250241031.jpg","title":"原煤机厂棚户区改造项目开工奠基",
			"title_color":"","ware_id":4,"winfo_id":1669},{"app_id":"cms","content_url":"/xinwendongtai/tpxw/37426.htm",
				"description":"","id":1966,"pre_title":"","publish_dtime":"","site_id":"CMSCMSszs","sort_id":1,
				"subtitle":"","thumb_url":"http://www.nxszs.gov.cn/CMSCMSszs/201210/201210231053002.jpg",
				"title":"2012中国东西部开发区合作发展论坛在我市隆重开幕","title_color":"","ware_id":4,"winfo_id":1665},
				{"app_id":"cms","content_url":"/xinwendongtai/tpxw/37500.htm","description":"","id":1977,"pre_title":"",
					"publish_dtime":"","site_id":"CMSCMSszs","sort_id":1,"subtitle":"","thumb_url":"http://www.nxszs.gov.cn/CMSCMSszs/201210/201210230911027.jpg",
					"title":"我市举行招商引资项目推介会","title_color":"","ware_id":4,"winfo_id":1672}])
	*/
	String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
	Map<String,String> m = new HashMap<String,String>();
	m.put("ware_id",ware_id);
	m.put("app_id","cms");
	m.put("site_id",site_id);
	
	List<WareInfos> listResult = new ArrayList<WareInfos>();
	List<WareInfoBean> l = WareInfoManager.getWareInfoList(m);
	int i=0;
	if(l != null && l.size() > 0)
	{
		for(WareInfoBean wib : l)
		{
			List<WareInfos> info_list = wib.getInfos_list();
			if(info_list != null && info_list.size() > 0)
			{
				for(WareInfos info : info_list)
				{
					i++;
					info.setTitle(info.getTitle().replaceAll("<[Bb][Rr]/?>", ""));
					if(i<=size){
						listResult.add(info);
					}
				}
			}
		}
	}
	JSONArray listResultJo = JSONArray.fromObject(listResult);
    String listResultStr = listResultJo.toString();
    if(jsoncallback.equals("")){
		out.println(listResultStr);
	}else{
		out.println(jsoncallback+"("+listResultStr+")");
	}
%>
