var configRPC = jsonrpc.ConfigRPC;

function saveMailConfig(){
	var K_V = new Map();
	K_V.put("group", $("#group").val());
	K_V.put("site_id", site_id);
	K_V.put("app_id", app_id);
	K_V.put("key_smtp_server", $("#smtp_server").val());
	K_V.put("key_smtp_port", $("#smtp_port").val());
	var isAuth = $(":radio[name='smtp_is_auth'][checked]").val();
	if(isAuth == null){
		isAuth = "";
	}
	K_V.put("key_smtp_is_auth", isAuth);
	K_V.put("key_smtp_loginname", $("#smtp_loginname").val());
	K_V.put("key_smtp_password", $("#smtp_password").val());
	K_V.put("key_smtp_send_mail", $("#smtp_send_mail").val());
	//alert(K_V);
	if(configRPC.add(K_V)){
		top.msgAlert("添加成功");
	}else{
		top.msgAlert("添加失败");
	}
}

function updateMailConfig(){
	var K_V = new Map();
	K_V.put("group", $("#group").val());
	K_V.put("site_id", site_id);
	K_V.put("app_id", app_id);
	K_V.put("key_smtp_server", $("#smtp_server").val());
	K_V.put("key_smtp_port", $("#smtp_port").val());
	K_V.put("key_smtp_is_auth", $("#smtp_is_auth").val());
	K_V.put("key_smtp_loginname", $("#smtp_loginname").val());
	K_V.put("key_smtp_password", $("#smtp_password").val());
	K_V.put("key_smtp_send_mail", $("#smtp_send_mail").val());
	if(configRPC.update(K_V)){
		top.msgAlert("修改成功");
	}else{
		top.msgAlert("修改失败");
	}
}

function initMailConfig(){
	var K_V = new Map();
	K_V.put("group", $("#group").val());
	K_V.put("site_id", site_id);
	K_V.put("app_id", app_id);
	var resultMap = configRPC.getValues(K_V);
	resultMap = Map.toJSMap(resultMap);
	//alert("resultMap==="+resultMap);
	if(resultMap != null && resultMap.size() > 0){
		$("#smtp_server").val(resultMap.get("smtp_server"));
		$("#smtp_port").val(resultMap.get("smtp_port"));
		$(":radio[name='smtp_is_auth'][value='"+resultMap.get("smtp_is_auth")+"']").attr("checked","checked");
		$("#smtp_loginname").val(resultMap.get("smtp_loginname"));
		$("#smtp_password").val(resultMap.get("smtp_password"));
		$("#smtp_send_mail").val(resultMap.get("smtp_send_mail"));
		return true;
	}else
		return false;
}