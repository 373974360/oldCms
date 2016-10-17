<%@page import="com.deya.util.Encode"%>
<%@page import="com.deya.wcm.services.cms.info.InfoBaseManager"%>
<%@page import="com.deya.wcm.bean.cms.info.*"%>
<%@page import="java.util.*"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%
	String cid = request.getParameter("cat_id");
	String words = request.getParameter("words");
	String siteid = request.getParameter("site_id");
	String p = request.getParameter("p");
	String t = request.getParameter("t");
	if(p == null){
		p = "1";
	}
	if(t == null){
		t = "";
	}
	int pageSize = Integer.parseInt(p);
	Map<String, String> m = new HashMap<String, String>();
	m.put("site_id", siteid);
	m.put("start_num", 50*(pageSize-1)+"");	
	m.put("page_size", "50");
	
	m.put("info_status", "8");
	m.put("final_status", "0");
	m.put("is_host", "0");
	
	m.put("sort_name", "released_dtime");
	m.put("sort_type", "desc");
	if(cid.startsWith(",")){
		cid = cid.trim().substring(1);
	}
	m.put("cat_ids", cid);
	
	if(words != null && !words.trim().equals("")){
		m.put("con_name", "title");
		m.put("con_value", Encode.iso_8859_1ToUtf8(words));
	}
	
	if(t.equals("count")){
		out.println(InfoBaseManager.getInfoCount(m));
		return;
	}
	
	List<InfoBean> list = InfoBaseManager.getInfoList(m);
	for(int i=0; i<list.size(); i++){
		InfoBean info = list.get(i);
		String tm = info.getReleased_dtime();
		if(tm != null && tm.length() > 4){
			tm = tm.substring(0,tm.lastIndexOf(":"));
		}
		out.println("<li><input type='checkbox' name='infoList' value='"+info.getInfo_id()+"' onclick='chooseInfo(this)'/><span id='"+info.getInfo_id()+"'>&nbsp;&nbsp;"+info.getTitle()+"&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='r_s'>"+tm+"</span></li>");
	}
		
%>
