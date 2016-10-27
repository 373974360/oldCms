<%@page language="java" import="java.util.*,java.io.File" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.cms.info.*,com.deya.wcm.services.cms.info.InfoBaseManager.*,com.deya.wcm.services.cms.category.*"%>
<%@page import="com.deya.wcm.bean.cms.info.ArticleBean.*,com.deya.wcm.bean.cms.info.InfoBean.*,com.deya.wcm.bean.logs.SettingLogsBean.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil,com.deya.wcm.bean.cms.info.*" %>
<%
    PicBean pb  = null;    
 	String info_id = request.getParameter("info_id");
    if(info_id !="" || info_id != null){
      pb = (PicBean) com.deya.wcm.template.velocity.data.InfoUtilData.getInfoObject(info_id); 
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title><%=pb.getTitle()%></title>
<meta name="keywords" content=""/>
<meta name="description" content="" />
<meta name="robots" content="index, follow" />
<meta name="googlebot" content="index, follow" />
<link rel="stylesheet" type="text/css" href="/wcm.files/styles/group.css" />
<link rel="stylesheet" type="text/css" href="/styles/site.css" />
<link rel="stylesheet" type="text/css" href="/styles/hd.skin/hd.css" />
<link rel="stylesheet" type="text/css" href="/styles/news.skin/news.css" />
<script type="text/javascript" src="/wcm.files/js/jquery.js"></script>
<script type="text/javascript" src="/wcm.files/js/java.js"></script>
<script type="text/javascript" src="/wcm.files/js/jsonrpc.js"></script>
<script type="text/javascript" src="/wcm.files/js/comment.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
});
</script>
</head>
<body>
<div style="text-algin:center;">
      <!--组图原图显示 开始-->
      <%
           if(pb != null)
           {
        	   List<PicItemBean> item_list =  pb.getItem_list() ;
    	       if(item_list != null)
    	       {
    	           for(int i=0;i<item_list.size();i++)
    	           {
    	              String fistname = "";
    	              String extName = "";
    	              String big_url = "";
    	              String url = item_list.get(i).getPic_path();
    	              if(url.lastIndexOf(".") >= 0) {
    	                extName = url.substring(url.lastIndexOf(".")).toLowerCase();
    	                fistname = url.replaceAll(extName, "");
    	                big_url = fistname+"_b"+extName;
    	              }
    	              File fileTemp = new File(big_url);// 判断文件是否存在
    	              boolean falg = false;
    	              falg = fileTemp.exists();
    	              if(falg){
    	 %>			
    	               <img src="<%=url%>" style="margin-buttom:10px;">
    	 <%
    	              }else
    	 %>
    	               <img src="<%=big_url%>" style="margin-buttom:10px;">
    	 <%
    	           }
    	      } 
           }
      %>
    <!--组图原图显示结束--> 
</div>
</body>
</html>