<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择用户</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/nodeSelected.js"></script>
<script type="text/javascript">
// 调用这个节点选择jsp时,调用页面要有一个setNodeName(str_node_name)方法和一个node_ids属性
// setNodeName(str_node_name)方法用来保存选取的节点名称.
// node_ids属性属性放置选中的节点id
var str_sel_node = top.getCurrentFrameObj().node_ids; // 选中的节点ID
var str_node_name = ""; // 选中的节点名称

var json_node_cat; // 节点分类json对象

$(document).ready(function(){
	initButtomStyle();	
	json_node_cat = eval(GKNodeRPC.getGKNodeCategroyJSONROOTTreeStr());
	
	initNodeCat(json_node_cat); // 初始化节点分类
	setNodeList(0); // 设置节点列表
	initSelectedList();
});

// 调用这个节点选择jsp时,调用页面要有一个setNodeName(str_node_name)方法和一个node_ids属性
// setNodeName(str_node_name)方法用来保存选取的节点名称.
// node_ids属性属性放置选中的节点id
function saveNode(){
	top.getCurrentFrameObj().node_ids = str_sel_node;
	top.getCurrentFrameObj().setNodeName(str_node_name);
	top.CloseModalWindow();
}

</script>
</head>

<body>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		 <th class="table_form_th">选择公开节点分类</th>
		 <th class="table_form_th">选择公开节点</th>
		 <th class="table_form_th">已选公开节点列表</th>
		</tr>	
		<tr>			
			<td class="width230" valign="top">
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft border_color" style="width:210px;height:390px;overflow:auto;background:#ffffff">
						<ul id="leftMenuTree" class="easyui-tree" animate="true" >
						</ul>
					</div>
				</div>
			</td>
			<td class="width230" align="top" >
				<div style="width:220px;height:402px;overflow:auto;" class="border_color">
					<ul id="node_list" style="margin:10px">
					</ul>
				 </div>
			</td>
			<td class="width210" valign="top">
				<div style="width:220px;height:402px;overflow:auto;" class="border_color">
					<ul id="sel_node_list" style="margin:10px">
					</ul>
				 </div>
			</td>
		</tr>		
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="saveNode()" value="保存" />				
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>	