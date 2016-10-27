 <%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app_id");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>页面列表面</title>  
<meta http-equiv="X-UA-Compatible" content="IE=8" />

 
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="js/pageList.js"></script>
<script type="text/javascript"> 
var SiteRPC = jsonrpc.SiteRPC;
var app_id = "<%=app_id%>";
var site_id = "<%=site_id%>";
if(site_id == null || site_id == "null")
	site_id = "";

var domain_url = SiteRPC.getDefaultSiteDomainBySiteID(site_id);
$(document).ready(function(){
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");  

}); 
 
</script>
<script type="text/javascript">	
		
 
		$(document).ready(function () {	
			initPageTree();
		});

		function initPageTree()
		{
			var beanList = PageRPC.getPageList(app_id,site_id,0);	
			beanList = List.toJSList(beanList);
			var treeHtmls = "";
			for(var i=0;i<beanList.size();i++){
			     treeHtmls += setTreeNode(beanList.get(i));
			}
			$("#treeTable_tbody").html(treeHtmls);
			iniTreeTable("treeTableCount");
		}
 
 
		//组织树列表选项字符串
		function setTreeNode(bean)
		{			
			var treeHtml = "";
			var child_list = bean.child_list;
			child_list = List.toJSList(child_list);
			
			if(child_list == null || child_list.size() == 0)
			{
				var type_calss= "";
				if(bean.parent_id!='0'){  
				      type_calss = "class='child-of-node-"+bean.parent_id+"'"
				}
				treeHtml+="<tr id='node-"+bean.id+"' "+type_calss+" > <td><span class='file'><a href='javascript:openUpdatePage("+bean.id+")'>"+bean.page_cname+"</a></span></td><td>"+bean.page_ename+"</span><td>"+bean.page_interval+"</td><td>"+bean.last_dtime+"</td>";
                treeHtml += setHandlList(bean,"file");
				treeHtml+="</tr>";
			}
			else 
			{
				var type_calss = "";
								
				treeHtml+="<tr id='node-"+bean.id+"' "+type_calss+" > <td><span class='folder'><a href='javascript:openUpdatePage("+bean.id+")'>"+bean.page_cname+"</a></span></td><td>"+bean.page_ename+"</span><td>"+bean.page_interval+"</td><td>"+bean.last_dtime+"</td>";
				treeHtml += setHandlList(bean,"folder");
				treeHtml+="</tr>";
				
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
		function setHandlList(bean,file_type)
		{								
			var str = '<td ><a href="javascript:createHtmlPage('+bean.id+')">生成页面</a></span>';
			if(/\/$/.test(bean.page_path))
				str += '&nbsp;&nbsp;<a target="_blank" href="'+domain_url+bean.page_path+bean.page_ename+'.htm">浏览</a></span>';
			else
				str += '&nbsp;&nbsp;<a target="_blank" href="'+domain_url+bean.page_path+"/"+bean.page_ename+'.htm">浏览</a></span>';
			str += '&nbsp;&nbsp;<a href="javascript:openAddPage('+bean.page_id+')">添加页面</a></span>';
			if(file_type == "file")
				str += '&nbsp;&nbsp;<a href="javascript:deletePage('+bean.id+')">删除</a></span>';
			str += '</td>';
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
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="openAddPage(0);" value="添加页面" />				
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
<table id="treeTableCount" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">
  <thead>
    <tr>
      <th>页面名称</th>
      <th>英文名</th>
      <th>更新频率</th>
	  <th>更新时间</th>
	  <th style="text-align:center">操作</th>
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
 
 

