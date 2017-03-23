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
	 

   List<CountBean> list = CountServices.getCountDeptHandle(s,e,b_ids);
   pageContext.setAttribute("list",list);
   
   String[] head = {"处理部门","总件数","待处理","处理中","待审核","已办结","未办结","按时办结数","超期办结数","按时办结率","办结率"};
   String[][] data = new String[list.size()][11];
   for(int i=0;i<list.size();i++){   
	   CountBean countBean = list.get(i);
	   data[i][0] = countBean.getDept_name();
	   data[i][1] = countBean.getCountall(); 
	   data[i][2] = countBean.getCountdai();
	   data[i][3] = countBean.getCountchu();
	   data[i][4] = countBean.getCountshen();
	   data[i][5] = countBean.getCountend();
	   data[i][6] = countBean.getCountwei();
	   
	   data[i][7] = countBean.getCount_over();
	   data[i][8] = countBean.getCount_warn();
	   data[i][9] = countBean.getCount_yellow();
	   
	   data[i][10] = countBean.getCountendp();
   } 
   
   OutExcel oe=new OutExcel("部门统计表");
   oe.doOut(xlsFile,head,data);
   
   pageContext.setAttribute("url",urlFile);
   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>按处理部门-办理情况</title>  
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

        var list_s = "";
        var list_e = "";
        var list_b_ids = "";
        
        var CountServicesRPC = jsonrpc.CountServicesRPC;
      
		$(document).ready(function () {	
			list_s = "${s}" + " 00:00:01";
	        list_e = "${e}" + " 59:59:59";
	        list_b_ids = "${b_ids}";
	        
			var beanList = CountServicesRPC.getListAll('${s}','${e}','${b_ids}');	
			beanList = List.toJSList(beanList);
			var treeHtmls = "";
			for(var i=0;i<beanList.size();i++){
			     treeHtmls += setTreeNode(beanList.get(i));
			}
			$("#treeTable_tbody").append(treeHtmls);
			iniTreeTable("treeTableCount");
		});


		//组织树列表选项字符串
		function setTreeNode(bean)
		{			
			var treeHtml = "";
			
			if(bean.category_type == "leaf")
			{		
				var type_calss= "";
				if(bean.p_id!='0'){  
				      type_calss = "class='child-of-node-"+bean.p_id+"'"
				}
				treeHtml+="<tr id='node-"+bean.category_id+"' "+type_calss+" > <td><span class='file'>"+bean.category_name+"</span></td>";
                treeHtml += setHandlList(bean.handle_list,bean.category_id);
				treeHtml+="</tr>";
			}
			else 
			{
				var type_calss = "";
				if(bean.p_id!='0'){  
				      type_calss = "class='child-of-node-"+bean.p_id+"'"
				}   
				treeHtml+="<tr id='node-"+bean.category_id+"' "+type_calss+" > <td><span class='folder'>"+bean.category_name+"</span></td>";
				treeHtml += setHandlList(bean.handle_list,bean.category_id);
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
		function setHandlList(l,dep_id)
		{
			var str = "";
			if(l)
			{
				l = List.toJSList(l); 			
				for(var i=0;i<l.size();i++)
				{			
					if(i==0){//总数
						str+="<td><a href='javascript:openCateInfo(\""+dep_id+"\",\"all\")'>"+l.get(i).handle_name+"</a></td>";
					}else if(i==1){//待处理
						str+="<td><a href='javascript:openCateInfo(\""+dep_id+"\",\"0\")'>"+l.get(i).handle_name+"</a></td>";
					}else if(i==2){//处理中
						str+="<td><a href='javascript:openCateInfo(\""+dep_id+"\",\"1\")'>"+l.get(i).handle_name+"</a></td>";
					}else if(i==3){//待审核
						str+="<td><a href='javascript:openCateInfo(\""+dep_id+"\",\"2\")'>"+l.get(i).handle_name+"</a></td>";
					}else if(i==4){//已办
						str+="<td><a href='javascript:openCateInfo(\""+dep_id+"\",\"3\")'>"+l.get(i).handle_name+"</a></td>";
					}else if(i==5){//未办结
						str+="<td><a href='javascript:openCateInfo(\""+dep_id+"\",\"wei\")'>"+l.get(i).handle_name+"</a></td>";
					}else if(i==6){//按时办结数
						str+="<td><a href='javascript:openCateInfo(\""+dep_id+"\",\"wei\")'>"+l.get(i).handle_name+"</a></td>";
					}else if(i==7){//超期办结数
						str+="<td><a href='javascript:openCateInfo(\""+dep_id+"\",\"wei\")'>"+l.get(i).handle_name+"</a></td>";
					}else if(i==8){//按时办结率
						str+="<td><a href='javascript:openCateInfo(\""+dep_id+"\",\"wei\")'>"+l.get(i).handle_name+"</a></td>";
					}else{
						str+="<td>"+l.get(i).handle_name+"</td>";
					}
					//str+="<td>"+l.get(i).handle_name+"</td>";
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

		function openCateInfo(dep_id,type) { 
			//getCurrentFrameObj().pieJsonData = 	pieChartDataList.get(index);
			OpenModalWindow("查看详细信息","/sys/appeal/count/dept/letter_list.jsp?dep_id="+dep_id+"&type="+type+"&b_ids="+list_b_ids+"&s="+list_s+"&e="+list_e,1000,600);
		} 
		
</script>
</head>

<body>
<table id="treeTableCount" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">
  <thead>
    <tr>
      <th>处理部门</th>
      <th>总件数</th>
      <th>待处理</th>
	  <th>处理中</th>
	  <th>待审核</th>
	  <th>已办结</th>
	  <th>未办结</th>
	  <th>按时办结数</th>
	  <th>超期办结数</th>
	  <th>按时办结率</th>
	  <th>办结率</th>
    </tr>
  </thead>
  <tbody id="treeTable_tbody">

  </tbody>
  <tfoot>
  	<td colspan="7"></td>
  </tfoot>
</table>

<span class="blank9"></span>
</body>
</html> 


