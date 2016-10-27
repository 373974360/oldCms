var setting = {      
	//fontCss : {color:"red"},
	nameCol : "text",
	nodesCol : "children",
	expandSpeed : "slow", //fast slow ""
	isSimpleData : false,    //是否为简单数组数据 这里还可以用array数据
	showIcon : true,//不显示文件夹图标	
	showLine: true, //是否带有虚线      
	checkable: false, //是否带有选择框
	callback : {
		 beforeExpand: beforeExpand,
		expand: onExpand,
		click: zTreeClick
	}
};  
var zTreeObj;

$(document).ready(function()
{	
	if(isShared)
	{
		zTreeObj = $("#tree").zTree(setting, zTreeNodes[0].children);  
	}
	else
	{
		zTreeObj = $("#tree").zTree(setting, zTreeNodes);  
	}
	
	if(cat_id != "" && cat_id != null)
	{
		initRightFrameForCategory(cat_id);
	}
});

//点击树所触发的事件
function zTreeClick()
{
	var selectedNode = zTreeObj.getSelectedNode();
	//if(selectedNode.children == null)
	var isopen = 0;
	if(selectedNode.attributes.url != null && selectedNode.attributes.url != "")
	{
		var url = selectedNode.attributes.url.split("?");
		if(url[1] != null && url[1] != "")
		{
			var param = url[1].split("&");
			if(param != null && param != "")
			for(var i= 0 ; i < param.length; i++)
			{
				var value = param[i].split("=")[1];
				if(value == "_blank")
				{
					isopen = 1;
				}
			}
			if(isopen == 1)
			{
				window.open(selectedNode.attributes.url);
			}
			else
			{
				$("#rightFrame").attr("src",selectedNode.attributes.url);
			}
		}
		else
		{
			$("#rightFrame").attr("src",selectedNode.attributes.url);
		}
		//window.open(selectedNode.attributes.url,"rightFrame");
	}
	else
		changeRightFrame(selectedNode.id);
}

//点击树改变右窗体链接地址 iframe的名称必须为rightFrame
function changeRightFrame(id)
{
	var is_sh = false;
	if(isShared)
	{
		is_sh = isShared;
	}
	if(is_sh == true || node_id != "")
		$("#rightFrame").attr("src","/info/iList.jsp?isSd="+is_sh+"&node_id="+node_id+"&cat_id="+id);
	else
		$("#rightFrame").attr("src","/info/iList.jsp?isSd="+is_sh+"&node_id="+node_id+"&cat_id="+id+"&catalog_id="+id);
}

//初始展示栏目数据,需要栏目ID
function initRightFrameForCategory(cat_id)
{
	var node = zTreeObj.getNodeByParam("id",cat_id);
	changeRightFrame(cat_id);
	zTreeObj.selectNode(node);
	zTreeObj.expandNode(node, true, false);
}

var lastNode = null;

function beforeExpand(treeId, treeNode){ 
	if(treeNode.parentNode == null || treeNode.parentNode == undefined)
	{
		zTreeObj.expandAll(false);
	}
	else if(lastNode != null)
	{
		if(lastNode.parentNode != null && treeNode.parentNode != undefined )
		{
			zTreeObj.expandNode(lastNode,false,false);
		}
	}
	lastNode = treeNode;
}

function onExpand(event, treeId, treeNode){}