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
		 click: zTreeClick
	}
};  
var zTreeObj;

$(document).ready(function()
{	
	zTreeObj = $("#tree").zTree(setting, zTreeNodes);  
	if(cat_id != "" && cat_id != null)
	{
		initRightFrameForCategory(cat_id);
	}
});

//点击树所触发的事件
function zTreeClick()
{
	var selectedNode = zTreeObj.getSelectedNode();
	if(selectedNode.children == null)
	{//alert(selectedNode.attributes.url)
		if(selectedNode.attributes.is_mutilpage == 0)
			changeRightFrame(selectedNode.id);
		else
		{
			if(selectedNode.attributes.url != "")
			{
				window.open(selectedNode.attributes.url);
			}
		}
	}
}

//点击树改变右窗体链接地址 iframe的名称必须为rightFrame
function changeRightFrame(id)
{
	$("#rightFrame").attr("src","/ggfw/fwList.jsp?cat_id="+id);
}

//初始展示栏目数据,需要栏目ID
function initRightFrameForCategory(cat_id)
{
	var node = zTreeObj.getNodeByParam("id",cat_id);
	changeRightFrame(cat_id);
	zTreeObj.selectNode(node);
	zTreeObj.expandNode(node, true, false);
}
