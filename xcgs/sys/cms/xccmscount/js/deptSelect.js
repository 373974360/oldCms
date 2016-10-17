
// 取得选中的部门
function getSelectedIDS(){
	return getLeftMenuChecked();
}

// 取得已选中的部门名称
function getDeptNames(ids){
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
	
	var dept_names = "";
	if(tem_ids !=""){
		var tempA = tem_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			dept_names += "," + $("div[node-id='"+tempA[i]+"']").text();
		}
		if(dept_names != ""){
			dept_names = dept_names.substr(1);
		} 
	}
	top.getCurrentFrameObj().setDeptNames(dept_names);
}

// 设置已经选中的部门
function setSelectedID(){
	var opt_ids = dept_str;
	if(opt_ids != "" && opt_ids != null)
	{
		var tempA = opt_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$("div[node-id='"+tempA[i]+"'] .tree-checkbox").removeClass("tree-checkbox0").addClass("tree-checkbox1");			
		}		
	}
}

// 保存选中的部门id
function saveDeptIDS(){
	var ids = getSelectedIDS();
	top.getCurrentFrameObj().dept_str = ids;
	
	getDeptNames(ids); // 取得选中的部门名称
	top.CloseModalWindow();
}

function cancle(){
	top.CloseModalWindow();
}