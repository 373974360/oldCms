var CountServicesUtilRPC = jsonrpc.CountServicesUtilRPC;

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

//得到所有的业务
function setBusiness(){
	 	var tableTr = "";
		var beanList = null;

		beanList = CountServicesUtilRPC.getBusinessList();
		beanList = List.toJSList(beanList);

		for (var i = 0; i < beanList.size(); i++) {
			var map = Map.toJSMap(beanList.get(i));
			var id = map.get("id");
			var name = map.get("name");
			//$("#s_b_id").addOption(businessBean.bu_name,businessBean.b_id);
			tableTr += '&nbsp;&nbsp;<input style="vertical-align:middle" type="checkbox" name="b_id" onclick="setAllState()" value="'+id+'"><span id="'+id+'">'+name+'</span>';
		}
		$("#b_tr").html(tableTr); 

}

function fnAll(){
	if($("[name='all']").is(':checked')){
  	  fnSelectAll();//全选
    }else{
  	  fnCancelAll();//取消全选
    }
}

function fnSelectAll(){
	$("[name='b_id']").attr("checked",'true');
}

function fnCancelAll(){
	$("[name='b_id']").removeAttr("checked");
}


function setAllState(){
	 contentSelected = true;
     if(!isContentSelectedAll()){
	 	$("[name='all']").removeAttr("checked");
	 }else{
	 	$("[name='all']").attr("checked",'true');
	 }
}

//判断不是全选
function isContentSelectedAll(){
	$("[name='b_id']").each(function(){
     if(!$(this).is(':checked'))
      {
        contentSelected = false;
		return contentSelected;
      }
    })
	//alert(contentSelected);
	return contentSelected;
}