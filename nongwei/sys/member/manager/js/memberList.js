var MemberManRPC = jsonrpc.MemberManRPC;

var MemberBean = new Bean("com.deya.wcm.bean.member.MemberBean",true);
var MemberRegisterBean = new Bean("com.deya.wcm.bean.member.MemberRegisterBean",true);

var val=new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "member_table_list";
var con_m = new Map();
var account_m = new Map();// key=会员ID，values=会员登录名

function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("me_realname","真实姓名","150px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("me_account","登录名","150px","","",""));
	colsList.add(setTitleClos("me_nickname","昵称","150px","","",""));
    colsList.add(setTitleClos("add_time","添加时间","150px","","",""));
    colsList.add(setTitleClos("update_time","更新时间","150px","","",""));
	colsList.add(setTitleClos("me_status","状态","150px","","",""));
	colsList.add(setTitleClos("blank"," ","","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function reloadMemberList()
{
	showList();
	showTurnPage();
}

function showList(){
			
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
        sortCol = "update_time";
        sortType = "desc";
	}

    var mcat_id = MemberManRPC.getMCatIDByUser(top.LoginUserBean.user_id,top.current_site_id);
    if(mcat_id != null && mcat_id != ""){
        con_m.put("mcat_id", mcat_id);
    }
		
	con_m.put("start_num", tp.getStart());	
	con_m.put("page_size", tp.pageSize);
    con_m.put("sort_name", "update_time");
    con_m.put("sort_type", "desc");

	beanList = MemberManRPC.getMemberList(con_m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	

	account_m = MemberManRPC.getMemberAccountList(beanList);// 取得登录帐号名称
	account_m = Map.toJSMap(account_m);
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("me_account").each(function(i){	
		if(i>0)
		{
			var key = beanList.get(i-1).me_id;
			var values = account_m.get(key);
			$(this).text(values);
		}
	});
	
	table.getCol("me_realname").each(function(i){	
		if(i>0)
		{
			var id = beanList.get(i-1).me_id;
			$(this).html('<a href="/sys/member/manager/member_view.jsp?type=update&member_id='+id+'">'+beanList.get(i-1).me_realname+'</a>');
		}
	});
	
	table.getCol("me_status").each(function(i){	
		if(i>0)
		{
			var status = beanList.get(i-1).me_status;
			if(status == -1)
			{
				$(this).text("禁用");
			}
			else if(status == 0)
			{
				$(this).text("待审");	
			}
			else if(status == 1)
			{
				$(this).text("正常");	
			}else
			{
				$(this).text("");	
			}
		}
	});

	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	
	tp.total = MemberManRPC.getMemberCnt(con_m);			
	tp.show("turn","");	
	tp.onPageChange = showList;
}

// 搜索函数
function memberSearch(obj)
{
	con_m.remove("me_status");
	var search_value = $(obj).parent().find("#searchkey").val();
	if(search_value.trim() == "" ||  search_value == null)
	{
		top.msgAlert(WCMLang.Search_empty);
		return;
	}
	search_name = $(obj).parent().find("#searchFields").val(); 

	con_m.put("search_value", search_value);
	con_m.put("search_name", search_name);
	reloadMemberList();
}

function getMemberCategoryList()
{
	var cate_list = MemberManRPC.getMemberCategoryList();
	cate_list = List.toJSList(cate_list);
	$("#mcat_id").addOptions(cate_list,"mcat_id","mcat_name");
}

// 添加会员
function addMember()
{
	window.location.href  = "/sys/member/manager/member_view.jsp?type=add&member_id=";
}

function saveMember()
{
	if(!check_member("add"))
	{
		return;
	}
	
	var addRegisterBean = BeanUtil.getCopy(MemberRegisterBean);
	var addMemberBeab = BeanUtil.getCopy(MemberBean);
	addRegisterBean.me_account = $("#me_account").val();
	addRegisterBean.me_password = $("#me_password").val();
	
	$("#member_table").autoBind(addMemberBeab);
	if(MemberManRPC.RegisterMember(addMemberBeab, addRegisterBean))
	{
		top.msgAlert("会员信息"+WCMLang.Add_success);
		window.history.go(-1);
	}
	else
	{
		top.msgWargin("会员信息"+WCMLang.Add_fail);
	}
}

// 维护会员
function updateStatus()
{
	var id = table.getSelecteCheckboxValue("me_id");
	top.OpenModalWindow("会员维护","/sys/member/manager/member_status.jsp?member_id="+id,350,210);
}

// 修改会员
function updateMember()
{
	var id = table.getSelecteCheckboxValue("me_id");
	window.location.href  = "/sys/member/manager/member_view.jsp?type=update&member_id="+id;
}

// 修改会员保存按钮
function saveUpdate()
{
	if(!check_member("update"))
	{
		return;
	}
	
	var addMemberBean = BeanUtil.getCopy(MemberBean);
	addMemberBean.me_id = me_id;

	oldregisterBean.me_password = $("#me_password").val();
	
	$("#member_table").autoBind(addMemberBean);
	if(MemberManRPC.updateMemberInfo(addMemberBean, oldregisterBean))
	{
		top.msgAlert("会员信息"+WCMLang.Add_success);
		window.history.go(-1);
	}
	else
	{
		top.msgWargin("会员信息"+WCMLang.Add_fail);
	}
}

// 验证表单 密码验证除外
function check_member(type)
{
	isFocus = true;
	val.check_result = true;
	
	$("input[type=text]").each(function(){
		$(this).blur();
	});
	$("input[type=password]").each(function(){
		$(this).blur();
	});
	
	// 验证邮箱
	val.checkEmail($("#me_email").val(), "Email", "true" ,"me_email");
	
	// 验证身份证号码
	val.checkIDCard($("#me_card_id").val(), "身份证号", "true" ,"me_card_id");
	
	// 验证手机号码
	val.checkMobile($("#me_phone").val(), "移动电话", "true" ,"me_phone");
	
	// 验证电话号码
	val.checkTelephone($("#me_tel").val(), "固定电话", "true" ,"me_tel");
	
	if(!val.getResult())
	{
		return false;
	}
	
	if(MemberManRPC.isAccountExisted($("#me_account").val()) && type=="add")
	{
		val.showError("me_account","帐号名称重复");
		return false;
	}
	
	if($("#me_password").val().length < 4)
	{
		val.showError("me_password","密码不能少于4位");
		return false;
	}
	
	return true;
}

// 禁止非数字输入
function checkNumberKey()
{  
   var keyCode = event.keyCode;   
   if ((keyCode >= 48 && keyCode <= 57))   
   {   
       event.returnValue = true;
   } else {   
       event.returnValue = false;
   }   
}

function deleteMember()
{
	var selectIDS = table.getSelecteCheckboxValue("me_id");

	if(MemberManRPC.deleteMember(selectIDS))
	{
		top.msgAlert("会员信息"+WCMLang.Delete_success);
		reloadMemberList();
	}else
	{
		top.msgWargin("会员信息"+WCMLang.Delete_fail);
	}
}