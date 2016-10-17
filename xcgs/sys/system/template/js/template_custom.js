var ModelRPC = jsonrpc.ModelRPC;

var modelCustomList ;
$(document).ready(function(){
	modelCustomList = ModelRPC.getCANModelList();
	modelCustomList = List.toJSList(modelCustomList);//把list转成JS的List对象	
	for(var i=0;i<modelCustomList.size();i++){
		var modelCustom = modelCustomList.get(i);
		var model_type = modelCustom.model_type;
		if(model_type.indexOf('0')<0){//不是系统默认
			$("#model_select").addOption(modelCustom.model_name,"custom_"+modelCustom.model_id);
		}
	}
});