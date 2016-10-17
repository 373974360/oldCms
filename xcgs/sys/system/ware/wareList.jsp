<%@ page contentType="text/html; charset=utf-8"%>
<%
	String site_id = request.getParameter("site_id");
	site_id = (site_id==null) ? "" : site_id;
	
	String app_id = request.getParameter("app_id");
	String id = request.getParameter("id");
	if(id == null)
	{
		id = "0";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息标签分类</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css" />
<jsp:include page="../../include/include_tools.jsp"/>
<style type="text/css">
	.static{background:url(../images/ware_html.png) left no-repeat; }/* 静态 */
	.auto{background:url(../images/ware_clock.png) left no-repeat; }/* 自动 */
	.opt{background:url(../images/ware_cog.png) left no-repeat; }/* 手动 */
	.rss{background:url(../images/ware_rss.png) left no-repeat; }/* RSS */
</style>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/wareList.js"></script>
<script type="text/javascript">
	var app_id = "<%=app_id%>";
	var site_id = "<%=site_id%>";
	var id = "<%=id%>";
	
	con_m.put("app_id",app_id);
	con_m.put("site_id",site_id);
	
	$(document).ready(function(){
		initButtomStyle();
		init_FromTabsStyle();
		if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
		//设置左侧Tree
		setLeftTreeHeight();
		showWareCateTree();
		
		initTable();
		loadWareTable();
		
	});
	
	function showWareCateTree()
	{
		json_data = eval(WareRPC.getJSONTreeStr(con_m));
		setLeftMenuTreeJsonData(json_data);
		initMenuTree();
		treeNodeSelected(id);
	}
	
	function initMenuTree()
	{
		$('#leftMenuTree').tree({
			onClick:function(node)
			{			
				changeOperateListTable(node.id);            
			}
		});
	}
	
	// 点击左侧树事件
	function changeOperateListTable(o_id)
	{
		id = o_id;
		loadWareTable();
	}
</script>
</head>
<body>
<table style="width:100%" border="0" cellpadding="0" cellspacing="0" >
	<tr>
	 <td width="200px" valign="top">
		<div id="leftMenuBox">
			<div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
				<ul id="leftMenuTree" class="easyui-tree" animate="true">
				</ul>
			</div>
		</div>
	 </td>
	 <td class="width10"></td>
	 <td valign="top">
	   <div>		
		<div id="table"></div>
		<div id="turn"></div>
		<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddPage();" value="新建" />
				<input id="btn2" name="btn2" type="button" onclick="updateRecord(table,'id','openUpdatePage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="movWares()" value="转移标签" />
				<input id="btn4" name="btn4" type="button" onclick="saveSort()" value="保存排序" />
				<input id="btn" name="btn6" type="button" onclick="publicSelectCheckbox(table,'ware_id','createWareContentHtml()');" value="生成静态页" />
				<input id="btn5" name="btn5" type="button" onclick="deleteRecord(table,'id','deleteWareByIDS()');" value="删除" />
			</td>
		</tr>
	</table>
	</div>
	 </td>
	</tr>
</table>


</body>
</html>
