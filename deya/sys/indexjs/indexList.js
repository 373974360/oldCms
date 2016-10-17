var UserLogin = jsonrpc.UserLoginRPC;
var MenuBean = new Bean("com.deya.wcm.bean.org.operate.MenuBean",true);	


var DeptRPC = jsonrpc.DeptRPC;
var DeptBean = new Bean("com.deya.wcm.bean.org.dept.DeptBean",true);

function showList()
{
	var menu_list =  UserLogin.getMenuListByUserID("1");
	menu_list = List.toJSList(menu_list);

	alert(menu_list.size())
}

