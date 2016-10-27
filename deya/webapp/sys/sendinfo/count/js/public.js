var SendInfoRPC = jsonrpc.SendInfoRPC;
var UserManRPC = jsonrpc.UserManRPC;

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