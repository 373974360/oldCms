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
	
	var b_ids = getCheckedBoxId();
	if(b_ids==""){
		top.msgWargin("请选择递交渠道");
	    return ;
	}

     //document.countList.location.href = "letterPurR.jsp?s="+timeS+"&e="+timeE+"&b_ids="+b_ids;
     var href_str = "letterPurR.jsp?s="+timeS+"&e="+timeE+"&b_ids="+b_ids;
	 $("#countList").attr("src",href_str);
	 $("#iframeTable").show();
	 $("#line2h").show();
	 $("#buttonTable").show();

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
			tableTr += '<li><input style="vertical-align:middle" type="checkbox" id="b_id_'+id+'" name="b_id" onclick="setAllState()" value="'+id+'"><label id="'+id+'" >'+name+'</label></li>';
		}
		$("#b_tr").append(tableTr); 
}

function fnAllSet(){
     if($("[name='all']").is(':checked')){
			$("[name='all']").removeAttr("checked");
	 }else{
	        $("[name='all']").attr("checked",'true');
	 }
	 fnAll();
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
     if(isContentSelectedAll()){
		 $("[name='all']").attr("checked",'true');
	 }else{
	 	$("[name='all']").removeAttr("checked");
	 }
}

//判断不是全选  全选：true  不是全选：false
function isContentSelectedAll(){
	var n = 0;
	var k = 0;
	$("[name='b_id']").each(function(){
		n++;
     if($(this).is(':checked'))
      {
         k++;
      }
    })
	if(k==n){//全选
         return true;
	}
	return false;
}

function getCheckedBoxId(){

	  var n = 0;
	  var value = ""; 
	  var len = $(":checkbox[name='b_id'][checked]").length;
	  $(":checkbox[name='b_id'][checked]").each(function(){ 
		   n++;
		   if(n==len){
			  value += $(this).val();
		   }else{
			  value += $(this).val() + ",";
		   }
	   });  
	   //alert(value);
	   return value; 
}


var urlFile="";
function setExcelOutUrl(url){
	 urlFile = url;
     $("#excel_out").click(downFile);
}

function downFile(){
     window.open(urlFile);
}
