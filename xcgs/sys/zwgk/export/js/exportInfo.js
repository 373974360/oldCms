var CategoryRPC = jsonrpc.CategoryRPC;
var OperateRPC = jsonrpc.OperateRPC;
var CategoryBean = new Bean("com.deya.wcm.bean.cms.category.CategoryBean",true);
var CategorySharedBean = new Bean("com.deya.wcm.bean.cms.category.CategorySharedBean",true);
var SyncBean = new Bean("com.deya.wcm.bean.cms.category.SyncBean",true);
var CategoryReleBean = new Bean("com.deya.wcm.bean.cms.category.CategoryReleBean",true);
var CategoryModel = new Bean("com.deya.wcm.bean.cms.category.CategoryModel",true);

//获取规则，切换目录类型
function choose_regu()
{
	var node_ids = "";
	var node_names = "";
	var cat_ids = "";
	var cat_names = "";
	var mp = new Map();
	if(choose_type == "zwgk")
	{
		var temp_ids = getAllTreeNode("leftMenuTree2_regu");
		if(temp_ids == "|")
		{
			top.msgWargin("请选择信息公开目录");
			return;
		}
		cat_ids = temp_ids.split("|")[0];
		cat_names = temp_ids.split("|")[1];
		if(site_id==''){//不是 公开具体节点 时用
			node_ids = $('#leftMenuTree_regu').tree('getSelected').attributes.t_node_id;
			node_names = $('#leftMenuTree_regu').tree('getSelected').text;
		}else{//选择 具体公开节点 时用
			node_ids = site_id;
		}
		if(node_ids=='' || node_ids=='undefined'  || node_ids==undefined){
			top.msgWargin("请选择公开节点");
			return;
		}

		$("#node_id").val(node_ids);
		$("#cat_ids").val(cat_ids);
        
		var extype = $(":radio[name='extype'][checked]").val();
		if(extype=='1'){
			$('#form1').attr('action', 'exportInfoWord.jsp');
		}else{
			$('#form1').attr('action', 'exportInfoWordCard.jsp');
		}
		$("#form1").submit();
		
		//alert(titlename);
		//alert(node_ids);
		//alert(cat_ids);
	}else
	{
		var temp_ids = getAllTreeNode("leftMenuTree_regu");
		if(temp_ids == "|")
		{
			top.msgWargin("请选择共享目录");
			return;
		}
		cat_ids = temp_ids.split("|")[0];
		cat_names = temp_ids.split("|")[1];

		var temp_nodes = getLeafTreeNode("leftMenuTree2_regu");
		if(temp_ids != "|")
		{
			node_ids = temp_nodes.split("|")[0];
			node_names = temp_nodes.split("|")[1];
		}
		
		$("#node_id").val(node_ids);
		$("#cat_ids").val(cat_ids);
		
		var extype = $(":radio[name='extype'][checked]").val();
		if(extype=='1'){
			$('#form1').attr('action', 'exportInfoWord.jsp');
		}else{
			$('#form1').attr('action', 'exportInfoWordCard.jsp');
		}
		$("#form1").submit();
		
	}
}

//得到共享信息的站点
function getAllowSharedSite()
{		
	//$("#tsArea").addOptionsSingl("zwgk","信息公开系统");
	//共享目录的树
	var sharedCategoryList = CategoryRPC.getCateClassListByApp(app_id);
	sharedCategoryList =List.toJSList(sharedCategoryList);
	for(var i=0;i<sharedCategoryList.size();i++)
	{
		if(sharedCategoryList.get(i).class_type == 1)
		{
			$("#tsArea").addOptionsSingl(sharedCategoryList.get(i).class_id,sharedCategoryList.get(i).class_cname);
		}
	}	
}

function changeCategoryId(val)
{
	if(val == "") return;
	choose_type = val;
	if(val == "zwgk")
	{
		$("#leftMenuTree2_regu").empty();
		initMenuTree("leftMenuTree_regu",false);
		setAppointTreeJsonData("leftMenuTree_regu",node_json_data);
		initZWGKTree();
	}
	else
	{
		initMenuTree("leftMenuTree_regu",true);		
		setAppointTreeJsonData("leftMenuTree_regu",eval(CategoryRPC.getCategoryTreeByClassID(val)));
		setAppointTreeJsonData("leftMenuTree2_regu",node_json_data);
		$("#titlename").val($("#tsArea").find("option:selected").text());
	}	
}

function initZWGKTree()
{
	$('#leftMenuTree_regu').tree({
		onClick:function(node){
			if(node.iconCls == "icon-gknode")
			{	
				s_site_id = node.attributes.t_node_id;
				//alert(node.text);
				$("#titlename").val(node.text);
				var jdata = eval(CategoryRPC.getCategoryTreeBySiteID(node.attributes.t_node_id));
				//jdata = getTreeObjFirstNode(jdata);
				$("#leftMenuTree2_regu").empty();
				
				setAppointTreeJsonData("leftMenuTree2_regu",jdata);

			}else{
				$("#titlename").val('');
			}
		}
	});	
}

function getAllTreeNode(div_name){
	var ids = "";
	var texts = "";
	//alert($('#'+div_name).html());
	$('#'+div_name+' span.tree-checkbox2').each(function(i){
		ids +=",";
		texts +=",";
		if(div_name == "leftMenuTree2_regu" && choose_type != "zwgk")
		{
			var node = $('#'+div_name).tree('find',$(this).parent().attr("node-id"));
			ids += node.attributes.t_node_id;
		}
		else{
			ids += $(this).parent().attr("node-id");
		}
		texts += $(this).parent().text();
	});
	$('#'+div_name+' span.tree-checkbox1').each(function(i){
		ids +=",";
		texts +=",";
		if(div_name == "leftMenuTree2_regu" && choose_type != "zwgk")
		{
			var node = $('#'+div_name).tree('find',$(this).parent().attr("node-id"));
			ids += node.attributes.t_node_id;
		}
		else{
			ids += $(this).parent().attr("node-id");
		}
		texts += $(this).parent().text();
	});
	
	ids = ids.substring(1);
	texts = texts.substring(1);
	
	return ids+"|"+texts;
}

function getLeafTreeNode(div_name){
	var ids = "";
	var texts = "";
	$('#'+div_name+' span.tree-file+span.tree-checkbox1').each(function(i){
		if(i>0)
		{
			ids +=",";
			texts +=",";
		}
		if(div_name == "leftMenuTree2_regu" && choose_type != "zwgk")
		{
			var node = $('#'+div_name).tree('find',$(this).parent().attr("node-id"));
			ids += node.attributes.t_node_id;
		}
		else{
			ids += $(this).parent().attr("node-id");
		}
		texts += $(this).parent().text();
	});
	return ids+"|"+texts;
}

//得到树中的第一个节点的所有子节点,为了不显示根节点
function getTreeObjFirstNode(cat_jdata)
{
	if(cat_jdata != null)
	{
		return cat_jdata[0].children;
	}
}

function initMenuTree(div_name,is_checkbox)
{
	$('#'+div_name).tree({	
		checkbox: is_checkbox		
	});
}




