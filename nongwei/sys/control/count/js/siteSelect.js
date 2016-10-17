
// 取得选中的栏目
function getSelectedIDS(){
	return getLeftMenuChecked();
}

// 取得已选中的栏目名称
function getCateNames(ids){
	var tem_ids = ids;
	var i_s = (root_id+"").length;
	if(ids.substr(0,i_s) == root_id){
		
		var i_e = ids.length;
		if(i_s < i_e){
			tem_ids = ids.substr(i_s+1, i_e);
		}else{
			tem_ids = "";
		}
	}
	
	var cat_names = "";
	if(tem_ids !=""){
		var tempA = tem_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			cat_names += "," + $("div[node-id='"+tempA[i]+"']").text();
		}
		if(cat_names != ""){
			cat_names = cat_names.substr(1);
		} 
	}
	top.getCurrentFrameObj().setCatNames(cat_names);
}

// 设置已经选中的栏目
function setSelectedID(){
	var opt_ids = cat_str;
	if(opt_ids != "" && opt_ids != null)
	{
		var tempA = opt_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$("div[node-id='"+tempA[i]+"'] .tree-checkbox").removeClass("tree-checkbox0").addClass("tree-checkbox1");			
		}		
	}
}

// 保存选中的栏目id
function saveCateIDS(){
	var ids = getSelectedIDS();
	top.getCurrentFrameObj().cat_str = ids;
	
	getCateNames(ids); // 取得选中的栏目名称
	top.CloseModalWindow();
}

function cancle(){
	top.CloseModalWindow();
}