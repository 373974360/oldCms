<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.DateUtil"%>
<%@page import="java.util.*"%>
<%@ page import="com.deya.wcm.bean.appeal.count.CountBean" %>
<%@page import="com.deya.util.jconfig.*"%>
<%@page import="com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.appeal.count.CountUtil"%>
<%@page import="java.io.File"%>
<%@page import="com.deya.wcm.services.appeal.count.OutExcel"%>
<%@page import="com.deya.wcm.services.appeal.count.CountServices"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
   String now = DateUtil.getCurrentDateTime();
   //now = now.substring(0,7);
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
	 
   List<CountBean> list = CountServices.getCountDeptType(s,e,b_ids);
   pageContext.setAttribute("list",list);
   
   String[] head = {"信件类型","全部信件","正常信件","无效信件","重复信件","不予受理信件"};
   String[][] data = new String[list.size()][6];
   for(int i=0;i<list.size();i++){   
	   CountBean countBean = list.get(i);
	   data[i][0] = countBean.getDept_name();
	   data[i][1] = countBean.getCount(); 
	   data[i][2] = countBean.getCount_normal();
	   data[i][3] = countBean.getCount_invalid();
	   data[i][4] = countBean.getCount_repeat();
	   data[i][5] = countBean.getCount_nohandle();
   } 
    
   OutExcel oe=new OutExcel("部门统计表");
   oe.doOut(xlsFile,head,data);
   
   pageContext.setAttribute("url",urlFile);
   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>按处理部门-信件类型</title>  
<meta http-equiv="X-UA-Compatible" content="IE=8" />


<!--  
<link type="text/css" rel="stylesheet" href="css/main.css" />
<link type="text/css" rel="stylesheet" href="css/sub.css" />
-->
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
	//alert($("body").width());
}); 

</script>
<script type="text/javascript">	
		var CountServicesRPC = jsonrpc.CountServicesRPC;

		$(document).ready(function () {	
			var beanList = CountServicesRPC.getListAllType('${s}','${e}','${b_ids}');	
			beanList = List.toJSList(beanList);
			var treeHtmls = "";
			for(var i=0;i<beanList.size();i++){
			     treeHtmls += setTreeNode(beanList.get(i));
			}
			$("#treeTable_tbody").append(treeHtmls);
			iniTreeTable("treeTableCount");
		});


		//组织树列表选项字符串
		function setTreeNode(bean,d_width)
		{			
			var treeHtml = "";
			
			if(bean.category_type == "leaf")
			{		
				if(bean.p_id!='0'){  
				      type_calss = "class='child-of-node-"+bean.p_id+"'"
				}
				treeHtml+="<tr id='node-"+bean.category_id+"' "+type_calss+" > <td><span class='file'>"+bean.category_name+"</span></td>";
                treeHtml += setHandlList(bean.handle_list);
				treeHtml+="</tr>";
			}
			else
			{
				var type_calss = "";
				if(bean.p_id!='0'){  
				      type_calss = "class='child-of-node-"+bean.p_id+"'"
				}  
				treeHtml+="<tr id='node-"+bean.category_id+"' "+type_calss+" > <td><span class='folder'>"+bean.category_name+"</span></td>";
				treeHtml += setHandlList(bean.handle_list);
				treeHtml+="</tr>";
				var child_list = bean.child_list;
				  child_list = List.toJSList(child_list);
				  if(child_list.size() > 0)
				  { 
					 for(var i=0;i<child_list.size();i++)
					 {						
						treeHtml += setTreeNode(child_list.get(i));
					 }
				  }
			}
			
			return treeHtml;
		}
		//组织操作选项字符串
		function setHandlList(l)
		{
			var str = "";
			if(l)
			{
				l = List.toJSList(l); 			
				for(var i=0;i<l.size();i++)
				{					
					str+="<td>"+l.get(i).handle_name+"</td>";
				} 
			}
			return str;
		}

		function ChangeStatus(o)
		{
			if (o.parentNode)
			{
				if (o.parentNode.parentNode.parentNode.className == "Opened")
				{
					o.parentNode.parentNode.parentNode.className = "Closed";
				}
				else
				{
					o.parentNode.parentNode.parentNode.className = "Opened";
				}
			}
		}

		
</script>
</head>

<body>
<table id="treeTableCount" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">
  <thead>
    <tr>
      <th>信件类型</th>
      <th>全部信件</th>
      <th>正常信件</th>
	  <th>无效信件</th>
	  <th>重复信件</th>
	  <th>不予受理信件</th>
    </tr>
  </thead>
  <tbody id="treeTable_tbody">

  </tbody>
  <tfoot>
  	<td colspan="6"></td>
  </tfoot>
</table>

<span class="blank9"></span>
</body>
</html> 