<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.template.velocity.data.InfoUtilData,net.sf.json.*"%>
<%@page import="com.deya.wcm.services.cms.info.InfoBaseManager"%>
<%@page import="com.deya.wcm.services.cms.info.ModelUtil,com.deya.wcm.bean.cms.info.*,java.util.regex.*"%>
<%!
public static String replaceImg(String content){
	Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
	Matcher m = p.matcher(content);
	//System.out.println(m.find());
	//System.out.println(m.groupCount());
	while(m.find()){
		String str = m.group();
		String str2 = str.replaceAll("<img", "<img height='180px' width='250px' ");
		content = content.replaceAll(str,str2);
		//System.out.println(m.group(1));
	}
	return content;
}
%>
<%
    String jsoncallback = (String)request.getParameter("callback");
if(jsoncallback==null){
	jsoncallback = "";
}
	String info_id = request.getParameter("info_id"); //信息id
	if(info_id==null)
	{
		info_id="";
	}
	/**
	通过id得到信息内容
	http://www.nxszs.gov.cn/jsp/brow/infoContent.jsp?info_id=35375
		info_id 信息Id
	返回数据格式：
	
	*/
	String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
	InfoBean infoBean = InfoBaseManager.getInfoById(info_id);
	String model_ename = com.deya.wcm.services.system.formodel.ModelManager.getModelEName(infoBean.getModel_id());
	
	Object o = null;
	String model_id = ""+infoBean.getModel_id();
	//System.out.println("model_id ---- " + model_id);
	if(model_id.equals("12")){
		String content_url = ""+infoBean.getContent_url();
		info_id = content_url.substring(content_url.lastIndexOf("/")+1,content_url.lastIndexOf("."));
		//System.out.println("info_id ---- " + info_id);
		infoBean = InfoBaseManager.getInfoById(info_id);
		model_ename = com.deya.wcm.services.system.formodel.ModelManager.getModelEName(infoBean.getModel_id());
		o = ModelUtil.select(info_id,site_id,model_ename);  
	}else{
		o = ModelUtil.select(info_id,site_id,model_ename);  
	}
	if(model_id.equals("11")){//文章
		ArticleBean articleBean = (ArticleBean)o;
		articleBean.setInfo_content(replaceImg(articleBean.getInfo_content()));
		o = (Object)articleBean;
	}
	JSONObject listResultJo = JSONObject.fromObject(o);
    String listResultStr = listResultJo.toString();
   
    //listResultStr = "{\"result\":\"success\"}";
    
    if(jsoncallback.equals("")){
		out.println(""+listResultStr+"");
	}else{
		out.println(jsoncallback+"("+listResultStr+")");
	}
%>
