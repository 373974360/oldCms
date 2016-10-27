<%@page import="com.deya.wcm.dao.system.ware.WareDAO"%>
<%@page import="com.deya.util.Encode"%>
<%@page import="com.deya.wcm.services.system.ware.*"%>
<%@page import="com.deya.wcm.bean.system.ware.*"%>
<%@page import="java.util.*"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%
	String cid = request.getParameter("cat_id");
	String words = request.getParameter("words");
	String siteid = request.getParameter("site_id");
	
	Map<String, String> m = new HashMap<String, String>();
	m.put("site_id", siteid);
	m.put("app_id", "0");	
	if(cid.startsWith(",")){
		cid = cid.trim().substring(1);
	}
	if(cid == null || cid.trim().equals("")){
		List<WareBean> list = WareDAO.getAllWareList();
		for(int i=0; i<list.size(); i++){
			WareBean ware = list.get(i);
			if(ware.getWare_name() == null || ware.getWare_name().trim().equals("")){
				continue;
			}
			out.println("<li><input type='checkbox' name='infoList' value='"+ware.getWare_id()+"' onclick='chooseInfo(this)'/><span id='"+ware.getWare_id()+"'>&nbsp;&nbsp;"+ware.getWare_name()+"&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='r_s'>"+ware.getUpdate_dtime()+"</span></li>");
		}
	}else{
		String cids[] = cid.split(",");
		for(int j=0; j<cids.length; j++){
			//System.out.println(cids[j]+"========="+m);
			List<WareBean> list = WareManager.getWareList(cids[j],m);
			for(int i=0; i<list.size(); i++){
				WareBean ware = list.get(i);
				if(ware.getWare_name() == null || ware.getWare_name().trim().equals("")){
					continue;
				}
				out.println("<li><input type='checkbox' name='infoList' value='"+ware.getWare_id()+"' onclick='chooseInfo(this)'/><span id='"+ware.getWare_id()+"'>&nbsp;&nbsp;"+ware.getWare_name()+"&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='r_s'>"+ware.getUpdate_dtime()+"</span></li>");
			}
		}
	}
	
		
%>
