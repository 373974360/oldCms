var GKNodeRPC = jsonrpc.GKNodeRPC;

var mp_node = new Map(); // 节点Map,key=分类ID,values=list<bean>

// 初始化节点分类信息
function initNodeCat(json_node_cat){
	setLeftMenuTreeJsonData(json_node_cat);
	
	$('#leftMenuTree').tree({
		onClick:function(node){
			setNodeList(node.id);
		}
	});
}

// 设置节点列表
function setNodeList(cat_id) {
	var lt_node;
	if(cat_id == 0){
		lt_node = GKNodeRPC.getAllGKNodeList(); // 取得所有的公开节点列表;
		lt_node = List.toJSList(lt_node);
		initMap(lt_node);
	}else {
		lt_node = new List();
		
		var values = mp_node.values();
		for(var i=0;i<values.length;i++) {
			if(values[i].nodcat_id == cat_id) {
				lt_node.add(values[i]);
			}
		}
	}
	$("#node_list").empty();
	$("#node_list").append('<li style="float:none;height:20px"><input type="checkbox" id="user_id_tt"/><label id="all_check" style="font-weight: 800">全选</label></li>');
	
	for(var i=0;i<lt_node.size();i++)
	{
		var bean = lt_node.get(i);
		var checked_str = '';
		if(str_sel_node.indexOf("'"+bean.node_id+"'") > -1)
		{
			checked_str = 'checked = "true"';
		}
		$("#node_list").append('<li style="float:none;height:20px"><input type="checkbox" '+checked_str+' id="user_id" value="'+bean.node_id+'" /><label id="'+bean.node_id+'" >'+bean.node_name+'</label></li>');
		
	}
	init_input();
	
	// 添加点击事件
	$("#node_list label").unbind("click").click(function(){
		var isChecked = $(this).prev().is(':checked') ? false : true;
		$(this).prev().attr("checked",isChecked);
		setSelectedList(isChecked,$(this).prev().val(),$(this).text());
		isAllChecked();
	});
	
	$("#node_list :checkbox").unbind("click").click(function(){
		var isChecked = $(this).is(':checked');
		setSelectedList(isChecked,$(this).val(),$(this).next().text());
		isAllChecked();
	});
	
	// 全选checkbox按钮的操作
	$("#all_check").unbind("click").click(function(){ // 全选label点击事件

		var isChecked = $(this).prev().is(':checked') ? false : true;
		if(isChecked) {
			$(this).prev().attr("checked","checked");
		} else {
			$(this).prev().removeAttr("checked");
		}
		
		$("#node_list :checkbox").each(function(i){
			if(i!=0){
				if(isChecked != $(this).is(':checked'))
					$(this).next().click();
			}
		});
	});
	
	$("#all_check").prev().unbind("click").click(function(){ // 全选checkbox点击事件
		var isChecked = $(this).is(':checked');
		
		$("#node_list :checkbox").each(function(i){
			if(i!=0){
				if(isChecked != $(this).is(':checked'))
					$(this).next().click();
			}
		});
	});
}

// 选中的节点列表
function setSelectedList(isChecked,node_id,node_name)
{
	if(isChecked)
	{		
		$("#sel_node_list").append('<li style="float:none;height:20px;display:block;width:100%;both:clear" node_id="'+node_id+'"><span style="float:left">'+node_name+'</span><img onclick="deleteNode(this,\''+node_id+'\')" src="../../images/delete.png" width="15" height="15" alt="" style="float:right;padding-bottom:3px"/></li>');	
	}else
	{
		$("#sel_node_list li[node_id='"+node_id+"']").remove();
	}
	resetSelectedInfo();
}

//删除已选用户
function deleteNode(obj,ids)
{
	$(obj).parent().remove();
	$("#node_list input[value='"+ids+"']").removeAttr("checked");
	resetSelectedInfo();
}

function resetSelectedInfo() {
	var node_ids = "";
	str_node_name = "";
	$("#sel_node_list li").each(function(i){
		node_ids += ",'"+ $(this).attr("node_id") + "'";
		str_node_name += "," +  $(this).children("span").html();
	});
	if(node_ids.length != 0) {
		node_ids = node_ids.substring(1);
	}
	if(str_node_name.length !=0){
		str_node_name = str_node_name.substring(1);
	}
	
	str_sel_node = node_ids;
}

// 填充节点缓存map,key为分类ID,value为Array<nodeBean>
function initMap(lt){
	for(var i=0; i< lt.size(); i++) {
		var bean = lt.get(i);
		mp_node.put(bean.node_id,bean);
	}
}

// 初始化选中的节点列表
function initSelectedList()
{
	var values = mp_node.values();
	for(var i=0;i<values.length;i++) {
		if( str_sel_node.indexOf("'"+values[i].node_id+"'") > -1) {
				$("#sel_node_list").append('<li style="float:none;height:20px;display:block;width:100%;both:clear" node_id="'+values[i].node_id+'"><span style="float:left">'+values[i].node_name+'</span><img onclick="deleteNode(this,\''+values[i].node_id+'\')" src="../../images/delete.png" width="15" height="15" alt="" style="float:right;padding-bottom:3px"/></li>');
		}
	}
	resetSelectedInfo();
}

// 全选checkbox的控制
function isAllChecked()
{   
	var flg = true;
	$("#node_list :checkbox").each(function(i){
		if(i!=0)
		{
			if($(this).is(':checked') == false) {
				flg = false;
				return false;
			}
		}
		
	});
	if(flg) {
		$("#all_check").prev().attr("checked","checked");
	} else {
		$("#all_check").prev().removeAttr("checked");
	}
}