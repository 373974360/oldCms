

//设置统计业务
function setBIdsHtml(){
		//parent.fnModelWin('设置业务','appeal/count/businessTree.html',330,450); 
		top.OpenModalWindow("设置业务","/sys/appeal/count/aim/businessTree.jsp",330,450);
}



function setBIdsNames(ids,names){
    setBIds(ids);
	setBNames(names);
}

//设置统计业务
function setBNames(names){
	if(names==""){
		names = "----请选择统计业务----";
	}
	$("#b_name").val(names);
}

//设置统计业务id
function setBIds(ids){
	$("#b_ids").val(ids);
}
	
//得到统计业务id
function getBIds(){
	return $("#b_ids").val();
}


function funOK(){
		
	var timeS = $("#s").val();
	var timeE = $("#e").val();
	if(timeS=='' || timeE==''){
		top.msgWargin("请填写时间范围");
		return ;
	}
	if(timeS>timeE){
	   top.msgWargin("结束时间不能在开始时间前");
	   return ;
	}
	
	if("----请选择统计业务----"==$("#b_name").val()){
		top.msgWargin("请选择统计业务");
		return ;
	}
	
	$("#b_name").val("");
	
	$("#form1").submit();
}