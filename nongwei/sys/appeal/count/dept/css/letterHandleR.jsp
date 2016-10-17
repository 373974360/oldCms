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
	String path = FormatUtil.formatPath(root_path + "/appeal/count/aim/");  
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
	String urlFile = "/sys/appeal/count/aim/"+nowDate+File.separator+xls;
	 

   List<CountBean> list = CountServices.getCountDeptHandle(s,e,b_ids);
   pageContext.setAttribute("list",list);
   
   String[] head = {"处理部门","总件数","待处理","处理中","待审核","已办结","办结率"};
   String[][] data = new String[list.size()][7];
   for(int i=0;i<list.size();i++){   
	   CountBean countBean = list.get(i);
	   data[i][0] = countBean.getDept_name();
	   data[i][1] = countBean.getCountall(); 
	   data[i][2] = countBean.getCountdai();
	   data[i][3] = countBean.getCountchu();
	   data[i][4] = countBean.getCountshen();
	   data[i][5] = countBean.getCountend();
	   data[i][6] = countBean.getCountendp();
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



<script type="text/javascript" src="/sys/js/jquery.js"></script>
<link type="text/css" rel="stylesheet" href="css/main.css" />
<link type="text/css" rel="stylesheet" href="css/sub.css" />
<script type="text/javascript" src="/sys/js/java.js"></script>
<script type="text/javascript" src="/sys/js/extend.js"></script>
<script type="text/javascript" src="/sys/js/jsonrpc.js"></script>
<script type="text/javascript" src="/sys/js/jquery.c.js"></script>
<script type="text/javascript" src="/sys/js/common.js"></script>
<script type="text/javascript" src="/sys/js/validator.js"></script>
<script type="text/javascript" src="/sys/js/lang/zh-cn.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.treeTable.js"></script>
<script type="text/javascript" src="js/sysUI.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	initButtomStyle();
	init_input();
	iniTreeTable("treeTableCount");
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	Init_InfoTable("infoList");
 
    window.parent.setExcelOutUrl('${url}');
	//alert($("body").width());
}); 

</script>
<script type="text/javascript">	
		var CountServicesRPC = jsonrpc.CountServicesRPC;

		$(document).ready(function () {	
			var beanList = CountServicesRPC.getListAll('${s}','${e}','${b_ids}');	
			beanList = List.toJSList(beanList);
			var treeHtmls = "";
			var default_div_width = $("body").width();

			treeHtmls += showCountTitle(default_div_width);
			treeHtmls += setTreeNode(beanList.get(0),default_div_width);
			//$("#CategoryTree").append(treeHtmls);

			
			$(".checkbox_div").css("width",$("body").width()-220);
			$("#CategoryTree").css("width",$("body").width()-17);
			
		});

		function showCountTitle(default_div_width)
		{
			var treeHtmls = "";
			treeHtmls += '<li class="Opened">';
			 treeHtmls += '<div class="main_div" style="width:'+default_div_width+'px">';
			  treeHtmls += '<div class="left_div">';
			   treeHtmls += '';
			  treeHtmls += '</div>';
				treeHtmls +='<div class="checkbox_div">';
			    treeHtmls += '<span class="checkbox_span">总件数</span>';
				treeHtmls += '<span class="checkbox_span">待处理</span>';
				treeHtmls += '<span class="checkbox_span">处理中</span>';
				treeHtmls += '<span class="checkbox_span">待审核</span>';
				treeHtmls += '<span class="checkbox_span">已办结</span>';
				treeHtmls += '<span class="checkbox_span">办结率</span>';
				treeHtmls += '</div>';
			treeHtmls += '</div>';
			treeHtmls += '</li>';
			return treeHtmls;
		}

		//组织树列表选项字符串
		function setTreeNode(bean,d_width)
		{			
			var treeHtml = "";
			
			if(bean.category_type == "leaf")
			{								
				treeHtml += '<li class="Child"><div class="main_div"  style="width:'+d_width+'px;"><div class="left_div"><img class=s src="css/s.gif">'+bean.category_name+'</div>   ';
				treeHtml += setHandlList(bean.handle_list);
				treeHtml += '</div></li>';
			}
			else
			{
				treeHtml += '<li class="Opened">';
				 treeHtml += '<div class="main_div" style="width:'+d_width+'px">';
			 	  treeHtml += '<div class="left_div">';
				   treeHtml += '<img class=s src="css/s.gif" onclick="javascript:ChangeStatus(this);">'+bean.category_name+'';
				  treeHtml += '</div>';
				  
				  treeHtml += setHandlList(bean.handle_list);
				 
				treeHtml += '</div>';
				var child_list = bean.child_list;
				  child_list = List.toJSList(child_list);
				  if(child_list.size() > 0)
				  {
					 d_width = d_width - 17;
					 
					 treeHtml += '<ul>';
					 for(var i=0;i<child_list.size();i++)
					 {						
						treeHtml += setTreeNode(child_list.get(i),d_width);
					 }
					 treeHtml += '</ul>';
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
				str += '<div class="checkbox_div">';
				
				for(var i=0;i<l.size();i++)
				{					
					
					str += '<span class="checkbox_span"><input style="display:none" type="checkbox" id="'+l.get(i).handle_id+'" '
					if(l.get(i).handle_selected == true)
						str += 'checked="true"';
					str += ';/>'+l.get(i).handle_name+'</span>';
				} 
				str += '</div>';
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
      <th>统计项目</th>
      <th>办结率</th>
      <th>超期件数</th>
    </tr>
  </thead>
  <tbody>
    <tr id="node-20">
      <td><span class="file">CHANGELOG</span></td>
      <td>4 KB</td>
      <td>Plain text</td>
    </tr>

    <tr id="node-1">
      <td><span class="folder">doc</span></td>
      <td>--</td>
      <td>Folder</td>
    </tr>

    <tr id="node-2" class="child-of-node-1">
      <td><span class="folder">images</span></td>
      <td>--</td>
      <td>Folder</td>
    </tr>

    <tr id="node-8" class="child-of-node-2">
      <td><span class="file">bg-table-thead.png</span></td>
      <td>52 KB</td>
      <td>Portable Network Graphics image</td>
    </tr>

    <tr id="node-9" class="child-of-node-2">
      <td><span class="file">folder.png</span></td>
      <td>111114 KB</td>
      <td>Portable Network Graphics image</td>
    </tr>

    <tr id="node-10" class="child-of-node-2">
      <td><span class="file">page_white_text.png</span></td>
      <td>4 KB</td>
      <td>Portable Network Graphics image</td>
    </tr>

    <tr id="node-3" class="child-of-node-1">
      <td><span class="file">index.html</span></td>
      <td>4 KB</td>
      <td>HTML document</td>
    </tr>

    <tr id="node-4" class="child-of-node-1">
      <td><span class="folder">javascripts</span></td>
      <td>--</td>
      <td>Folder</td>
    </tr>

    <tr id="node-5" class="child-of-node-4">
      <td><span class="file">jquery.js</span></td>
      <td>56 KB</td>
      <td>JavaScript source</td>
    </tr>

    <tr id="node-6" class="child-of-node-1">
      <td><span class="folder">stylesheets</span></td>
      <td>--</td>
      <td>Folder</td>
    </tr>

    <tr id="node-7" class="child-of-node-6">
      <td><span class="file">master.css</span></td>
      <td>4 KB</td>
      <td>CSS style sheet</td>
    </tr>

    <tr id="node-14">
      <td><span class="file">MIT-LICENSE</span></td>
      <td>4 KB</td>
      <td>Plain text</td>
    </tr>

    <tr id="node-18">
      <td><span class="file">README.markdown</span></td>
      <td>4 KB</td>
      <td>Markdown document</td>
    </tr>

    <tr id="node-11">
      <td><span class="folder">src</span></td>
      <td>--</td>
      <td>Folder</td>
    </tr>

    <tr id="node-12" class="child-of-node-11">
      <td><span class="folder">images</span></td>
      <td>--</td>
      <td>Folder</td>
    </tr>

    <tr id="node-15" class="child-of-node-12">
      <td><span class="file">bullet_toggle_minus.png</span></td>
      <td>4 KB</td>
      <td>Portable Network Graphics image</td>
    </tr>

    <tr id="node-16" class="child-of-node-12">
      <td><span class="file">bullet_toggle_plus.png</span></td>
      <td>4 KB</td>
      <td>Portable Network Graphics image</td>
    </tr>

    <tr id="node-13" class="child-of-node-11">
      <td><span class="folder">stylesheets</span></td>
      <td>--</td>
      <td>Folder</td>
    </tr>

    <tr id="node-17" class="child-of-node-13">
      <td><span class="file">jquery.treeTable.css</span></td>
      <td>4 KB</td>
      <td>CSS style sheet</td>
    </tr>

    <tr id="node-19" class="child-of-node-11">
      <td><span class="file">jquery.treeTable.js</span></td>
      <td>8 KB</td>
      <td>JavaScript source</td>
    </tr>

  </tbody>
  <tfoot>
  	<td colspan="3"></td>
  </tfoot>
</table>

<span class="blank9"></span>
</body>
</html> 


