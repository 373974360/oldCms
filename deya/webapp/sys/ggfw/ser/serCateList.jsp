<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息标签分类</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">

<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/serCateList.js"></script>
<script type="text/javascript" src="js/serResouceList.js"></script>
<script type="text/javascript">
	var ser_id = "${param.ser_id}"
	var top_index = "${param.top_index}"
	
	$(document).ready(function(){
		initButtomStyle();
		init_input();
		if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
		initTable();

		//设置左侧Tree
		setLeftTreeHeight();
		showSerCateTree();
		loadSerCategoryTable();		
	});
	
	function showSerCateTree()
	{
		json_data = eval(SerRPC.getSerCateJSONTree(ser_id));
		setLeftMenuTreeJsonData(json_data);
		initMenuTree();
		treeNodeSelected(ser_id);
	}

	function reloadSerCateTree()
	{
		json_data = eval(SerRPC.getSerCateJSONTree(request.getParameter("ser_id")));
		setLeftMenuTreeJsonData(json_data);
	}
	
	function initMenuTree()
	{
		$('#leftMenuTree').tree({
			onClick:function(node)
			{
				if(node.attributes == null || node.attributes.cat_type != "leaf")
					changeOperateListTable(node.id);  
				else{
					showSerResouceList(node.id);
				}
			}
		});
	}
	
	// 点击左侧树事件
	function changeOperateListTable(o_id)
	{
		$("#div_cate").show();
		$("#div_resouce").hide();
		ser_id = o_id;
		initTable();
		loadSerCategoryTable();
	}

	function showSerResouceList(o_id)
	{
		$("#div_cate").hide();
		$("#div_resouce").show();
		ser_id = o_id;
		initResouceTable();
		loadSerResouceTable();		
	}

	function openSerTree(title,handl_name)
	{
		top.OpenModalWindow(title,"/sys/ggfw/ser/select_ser_tree.jsp?&handl_name="+handl_name,450,510);
	}

	//移动目录
	function moveSerCategory(parent_id)
	{
		if(parent_id != "" && parent_id != null)
		{
			var selectIDS = table.getSelecteCheckboxValue("ser_id");			
			if(SerRPC.moveSerCategory(parent_id,selectIDS)){
				top.msgAlert("目录"+WCMLang.Move_success);
				reloadSerCateTree();
				treeNodeSelected(ser_id);
				loadSerCategoryTable();
			}else
			{
				top.msgWargin("目录"+WCMLang.Move_fail);
				return;
			}
		}
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
				<div id="div_cate">
					<input id="btn1" name="btn1" type="button" onclick="openAddPage();" value="新建节点" />
					<input id="btn2" name="btn2" type="button" onclick="updateRecord(table,'ser_id','openUpdatePage()');" value="修改" />	
					<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'ser_id','openSerTree(\'选择节点\',\'moveSerCategory\')')" value="移动节点" />
					<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'ser_id','sortSerCategory()');" value="保存排序" />
					<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'ser_id','deleteSerCategory()');" value="删除" />
				</div>
				<div id="div_resouce" class="hidden">
					<input id="btn1" name="btn1" type="button" onclick="openAddResoucePage();" value="新建" />
					<input id="btn2" name="btn2" type="button" onclick="updateRecord(table,'ser_id','openUpdateResoucePage()');" value="修改" />
					<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'ser_id','batchPublishSerResouce(1)')" value="发布" />	
					<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'ser_id','batchPublishSerResouce(0)')" value="撤销" />
					<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'ser_id','sortSerResouce()');" value="保存排序" />
					<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'ser_id','deleteSerResouce()');" value="删除" />
				</div>
			</td>
		</tr>
	</table>
	</div>
	 </td>
	</tr>
</table>


</body>
</html>
