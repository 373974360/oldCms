var UserLogin = jsonrpc.UserLoginRPC;
var MenuBean = new Bean("com.deya.wcm.bean.org.operate.MenuBean",true);

var DeptRPC = jsonrpc.DeptRPC;
var DeptBean = new Bean("com.deya.wcm.bean.org.dept.DeptBean",true);


var LoginUserBean = UserLogin.getUserBySession();
var sql_click_count = 0;//点击人员名称次数统计，用于触发特定事件

function showList()
{
	var menu_list =  UserLogin.getMenuListByUserID("1");
	menu_list = List.toJSList(menu_list);
}

$(document).ready(function(){	
	$("#user_name").click(function(){
		
	if(sql_click_count == 0) {
		setTimeout("sql_click_count=0",3000);
	}
		sql_click_count += 1;		
		if(sql_click_count > 4)
		{
			addTab(true,"/sys/tools/sql.jsp","sql执行器");
			sql_click_count = 0;
		}
	});			
});
