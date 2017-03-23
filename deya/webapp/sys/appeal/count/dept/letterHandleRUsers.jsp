<%@page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.*"%>
<%@page import="com.deya.wcm.bean.appeal.count.CountBean" %>
<%@page import="com.deya.util.jconfig.*"%>
<%@page import="com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.appeal.count.CountUtil"%>
<%@page import="java.io.File"%>
<%@page import="com.deya.wcm.services.appeal.count.OutExcel"%>
<%@page import="com.deya.wcm.services.appeal.count.CountServices"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   String now = DateUtil.getCurrentDateTime();
          pageContext.setAttribute("now",now);
   
   String s = (String)request.getParameter("s");
   String e = (String)request.getParameter("e");
   String b_ids = (String)request.getParameter("b_ids");
   pageContext.setAttribute("s",s);
   pageContext.setAttribute("e",e);
   pageContext.setAttribute("b_ids",b_ids);
   
    //删除今天以前的文件夹
	String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
	String path = FormatUtil.formatPath(root_path + "/appeal/count/dept/");  
	CountUtil.deleteFile(path);
	
	//创建今天的文件夹和xls文件
	String nowDate = CountUtil.getNowDayDate();
	String fileTemp2 = FormatUtil.formatPath(path+File.separator+nowDate);
	File file2 = new File(fileTemp2);
	if(!file2.exists()){
		file2.mkdir();
	}
	String nowTime = CountUtil.getNowDayDateTime();
	String xls = nowTime + CountUtil.getEnglish(1)+".xls";
	String xlsFile = fileTemp2+File.separator+xls;
	String urlFile = "/sys/appeal/count/dept/"+nowDate+File.separator+xls;
	 
   //List<CountBean> list = CountServices.getCountDeptHandle(s,e,b_ids);
   List<CountBean> list = CountServices.getCountUsersHandle(s,e,b_ids);
   pageContext.setAttribute("list",list);
   
   String[] head = {"处理人","总件数","待处理","处理中","待审核","已办结","未办结","办结率"};
   String[][] data = new String[list.size()][8];
   for(int i=0;i<list.size();i++){   
	   CountBean countBean = list.get(i);
	   data[i][0] = countBean.getUser_realname();
	   data[i][1] = countBean.getCountall(); //总件数
	   data[i][2] = countBean.getCountdai(); //待处理
	   data[i][3] = countBean.getCountchu(); //处理中
	   data[i][4] = countBean.getCountshen();//待审核
	   data[i][5] = countBean.getCountend(); //已办结
	   data[i][6] = countBean.getCountwei();
	   data[i][7] = countBean.getCountendp();
   } 
   
   OutExcel oe= new OutExcel("处理人统计表");
   oe.doOut(xlsFile,head,data);
   
   pageContext.setAttribute("url",urlFile);  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>按处理人-办理情况</title>  
<meta http-equiv="X-UA-Compatible" content="IE=8" />


<link type="text/css" rel="stylesheet" href="/sys/styles/main.css" />
<link type="text/css" rel="stylesheet" href="/sys/styles/sub.css" />
<script type="text/javascript" src="/sys/js/jquery.js"></script>
<script type="text/javascript" src="/sys/js/java.js"></script>
<script type="text/javascript" src="/sys/js/extend.js"></script>
<script type="text/javascript" src="/sys/js/jsonrpc.js"></script>
<script type="text/javascript" src="/sys/js/jquery.c.js"></script>
<script type="text/javascript" src="/sys/js/common.js"></script>
<script type="text/javascript" src="/sys/js/lang/zh-cn.js"></script>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="/sys/js/sysUI.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
    window.setExcelOutUrl('${url}');
}); 
</script>
<script type="text/javascript">
        var list_s = "";
        var list_e = "";
        var list_b_ids = "";
        
        var CountServicesRPC = jsonrpc.CountServicesRPC;
      
		$(document).ready(function () {	
			list_s = "${s}" +" 00:00:01";
	        list_e = "${e}" +" 59:59:59";
	        list_b_ids ="${b_ids}";
	        
			var list = CountServicesRPC.getCountUsersHandle('${s}','${e}','${b_ids}');
			    list = List.toJSList(list);

			$("#treeTable_tbody").empty();
			var treeHtmls = "<thead>"+
					"<tr style=\"height:30px;background-color:#f7f6f2;\">"+
					"   <th width=\"25%\">处理人</th> " +
					"   <th width=\"10%\">全部  </th> " +
					"   <th width=\"10%\">待处理</th> " +
					"	<th width=\"10%\">处理中</th> " +
					"	<th width=\"10%\">待审核</th> " +
					"	<th width=\"10%\">已办结</th> " +
					"	<th width=\"10%\">未办结</th> " +
					"	<th width=\"15%\">办结率</th> " +
					"</tr>"+
			        "</thead>";
		        if(list.size()!=0) 
		        {
					for(var i=0;i<list.size();i++){
						treeHtmls += setTreeNodeUser(list.get(i));
					}
					    treeHtmls +="<tr><td colspan=\"8\">&nbsp;&nbsp;</td></tr>";
				}else{
					treeHtmls += "<tr><td colspan=\"8\">暂无数据...</td></tr>";
					treeHtmls += "<tr><td colspan=\"8\">&nbsp;&nbsp; </td></tr>";
				}
			$("#treeTable_tbody").append(treeHtmls);
		});

	function setTreeNodeUser(bean)
	{			
		var treeHtml = "";
	
			treeHtml +="<tr><td>"+bean.user_realname+"</td>";
			treeHtml +="<td><a href='javascript:openCateInfo(\""+bean.user_id+"\",\"all\")'>"+bean.countall +"</a></td>";
			treeHtml +="<td><a href='javascript:openCateInfo(\""+bean.user_id+"\",\"0\")'>"+bean.countdai +"</a></td>";
			treeHtml +="<td><a href='javascript:openCateInfo(\""+bean.user_id+"\",\"1\")'>"+bean.countchu +"</a></td>";
			treeHtml +="<td><a href='javascript:openCateInfo(\""+bean.user_id+"\",\"2\")'>"+bean.countshen +"</a></td>";
			treeHtml +="<td><a href='javascript:openCateInfo(\""+bean.user_id+"\",\"3\")'>"+bean.countend +"</a></td>";
			treeHtml +="<td><a href='javascript:openCateInfo(\""+bean.user_id+"\",\"wei\")'>"+bean.countwei +"</a></td>";
			treeHtml +="<td>"+bean.countendp+"</td>";
			treeHtml+="</tr>";
		return treeHtml;
	}

 function openCateInfo(user_id,type)
 { 
OpenModalWindow("查看详细信息","/sys/appeal/count/dept/letter_list.jsp?user_id="+user_id+"&type="+type+"&b_ids="+list_b_ids+"&s="+list_s+"&e="+list_e,1000,600);
 }		
</script>
</head>
<body>
<table id="treeTable_tbody" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0"
style="border-bottom:1px solid #bfbfbf;">
</table>
<span class="blank9"></span>
</body>
</html>