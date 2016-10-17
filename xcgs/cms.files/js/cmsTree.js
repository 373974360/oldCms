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
		 beforeExpand: zTreeOnBeforeExpand,
		 onExpand: zTreeOnExpand,
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
	var url="";
	var selectedNode = zTreeObj.getSelectedNode();
	if(selectedNode.attributes.url != null && selectedNode.attributes.url != "")
	{

		window.open(selectedNode.attributes.url);
	}else{
		var tm_param = "";
		if(template_id != "" && template_id != null)
			tm_param = "&tm_id="+template_id;
        if(selectedNode.children == null)
		{
           url = "/info/iList.jsp?site_id="+site_id+"&cat_id="+selectedNode.id+tm_param;
		}else{
		   url = "/info/iIndex.jsp?site_id="+site_id+"&cat_id="+selectedNode.id+tm_param;
		}
		window.open(url);
    }
}


//点击树改变右窗体链接地址 iframe的名称必须为rightFrame
function changeRightFrame(id)
{
	var tm_param = "";
	if(template_id != "" && template_id != null)
		tm_param = "&tm_id="+template_id;
	//var is_sh = false;
	//if(isShared)
	//{
		//is_sh = isShared;
	//}
	//if(is_sh == true || node_id != "")
		//$("#rightFrame").attr("src","/info/iList.jsp?isSd="+is_sh+"&node_id="+node_id+"&cat_id="+id+tm_param);
	//else
		$("#rightFrame").attr("src","/info/iList.jsp?site_id="+site_id+"&cat_id="+id+tm_param);
}


//初始展示栏目数据,需要栏目ID
function initRightFrameForCategory(cat_id)
{
	var node = zTreeObj.getNodeByParam("id",cat_id);
	changeRightFrame(cat_id);
	zTreeObj.selectNode(node);
	zTreeObj.expandNode(node, true, false);
}


//如何实现展开一个节点时关闭其他节点
////////////////////////////////////////----------------start
var curExpandNode = null;
//需要 beforeExpand 事件回调函数
function zTreeOnBeforeExpand(treeId, treeNode) {
  singlePath(treeNode);
}
function singlePath(newNode) {
  /*
  如果即将展开的节点与需要展开的节点相同，那么返回.
  这个逻辑看上去很可笑，已经展开了为何还会再展开？但对于更复杂的操作中不一定只有 beforeExpand 的时候
  才会执行这个方法，所以不一定能完全避免此类事件）
  */
  if (newNode === curExpandNode) return;

  //存在已展开节点，并且是展开状态的
  if (curExpandNode && curExpandNode.open==true) {
	//这部分是专门用于多级节点的
	if (newNode.parentNode === curExpandNode.parentNode) {
	  //如果新展开的节点和已展开节点具有同一个父节点，则只需要将已展开节点折叠即可
	  zTreeObj.expandNode(curExpandNode, false, true);
	} else {
	  //如果不是共同父节点，则需要找到最上方不同的父节点，将其包括子节点全部进行折叠
	  var newParents = [];
	  while (newNode) {
		newNode = newNode.parentNode;
		if (newNode === curExpandNode) {
		  newParents = null;
		  break;
		} else if (newNode) {
		  newParents.push(newNode);
		}
	  }
	  if (newParents!=null) {
		var oldNode = curExpandNode;
		var oldParents = [];
		while (oldNode) {
		  oldNode = oldNode.parentNode;
		  if (oldNode) {
			oldParents.push(oldNode);
		  }
		}
		for (var i = (newParents.length<oldParents.length?newParents.length:oldParents.length); i>=0; i--) {
		  if (newParents[i] !== oldParents[i]) {
			zTreeObj.expandNode(oldParents[i], false, true);
			break;
		  }
		}
	  }
	}
  }
  //这行代码是否一定需要，就要看你的实际情况了，毕竟这个方法里面并没有将 newNode 进行展开
  curExpandNode = newNode;
}
//需要 expand 事件回调函数
function zTreeOnExpand(event, treeId, treeNode) {
  curExpandNode = treeNode;
}
////////////////////////////////////////----------------end