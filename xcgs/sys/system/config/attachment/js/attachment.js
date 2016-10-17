var configRPC = jsonrpc.ConfigRPC;


function saveAttachConfig(){
	var K_V = new Map();
	K_V.put("group", $("#group").val());
	K_V.put("site_id", site_id);
	K_V.put("app_id", app_id);
	K_V.put("key_upload_allow", $("#upload_allow").val());
	K_V.put("key_thumb_width", $("#thumb_width").val());
	K_V.put("key_thumb_height", $("#thumb_height").val());
	K_V.put("key_normal_width", $("#normal_width").val());
	K_V.put("key_normal_height", $("#normal_height").val());
	K_V.put("key_thumb_quality", $("#thumb_quality").val());
	
	var isAuth = $(":radio[name='watermark'][checked]").val();
	if(isAuth == null){
		isAuth = "";
	}
	K_V.put("key_watermark", isAuth);
	var lct = $(":radio[name='water_location'][checked]").val();
	if(lct == null){
		lct = "";
	}
	K_V.put("key_water_location", lct);
	K_V.put("key_water_width", $("#water_width").val());
	K_V.put("key_water_height", $("#water_height").val());
	K_V.put("key_water_transparent", $("#water_transparent").val());
	K_V.put("key_water_pic", $("#water_pic").val());

	var is_compress = $(":radio[name='is_compress'][checked]").val();
	if(is_compress == null){
		is_compress = "";
	}
	K_V.put("key_is_compress", is_compress);
	//alert(K_V);
	if(configRPC.add(K_V)){
		top.msgAlert(WCMLang.Add_success);
	}else{
		top.msgAlert(WCMLang.Add_fail);
	}
}

function initAttachConfig(){
	var K_V = new Map();
	K_V.put("group", $("#group").val());
	K_V.put("site_id", site_id);
	K_V.put("app_id", app_id);
	var resultMap = configRPC.getValues(K_V);
	resultMap = Map.toJSMap(resultMap);
	//alert("resultMap==="+resultMap);
	if(resultMap != null && resultMap.size() > 0){
		$("#upload_allow").val(resultMap.get("upload_allow"));
		$("#thumb_width").val(resultMap.get("thumb_width"));
		$("#thumb_height").val(resultMap.get("thumb_height"));
		$("#normal_width").val(resultMap.get("normal_width"));
		$("#normal_height").val(resultMap.get("normal_height"));
		$("#thumb_quality").val(resultMap.get("thumb_quality"));
		$(":radio[name='watermark'][value='"+resultMap.get("watermark")+"']").attr("checked","checked");
		$(":radio[name='water_location'][value='"+resultMap.get("water_location")+"']").attr("checked","checked");
		//$("#water_location").val(resultMap.get("water_location"));
		$("#water_width").val(resultMap.get("water_width"));
		$("#water_height").val(resultMap.get("water_height"));
		$("#water_transparent").val(resultMap.get("water_transparent"));
		$("#water_pic").val(resultMap.get("water_pic"));
		$(":radio[name='is_compress'][value='"+resultMap.get("is_compress")+"']").attr("checked","checked");
		return true;
	}else
		return false;
}