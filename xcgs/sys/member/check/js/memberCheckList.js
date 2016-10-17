var MemberManRPC = jsonrpc.MemberManRPC;

var MemberBean = new Bean("com.deya.wcm.bean.member.MemberBean",true);
var MemberRegisterBean = new Bean("com.deya.wcm.bean.member.MemberRegisterBean",true);

var val=new Validator();
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "register_table_list";
var con_m = new Map();
var account_m = new Map();// key=会员ID，values=会员登录名

function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("me_realname","真实姓名","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("me_account","登录名","150px","","",""));
	colsList.add(setTitleClos("me_nickname","昵称","150px","","",""));
	colsList.add(setTitleClos("me_status","状态","150px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function reloadMemberCheckList()
{
	showList();
	showTurnPage();
}

function showList(){
			
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}
		
	con_m.put("start_num", tp.getStart());	
	con_m.put("page_size", tp.pageSize);

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
			$(this).html('<a href="javascript:top.addTab(true,\'/sys/member/manager/member_view.jsp?type=view&member_id='+id+'\',\'会员信息\')">'+beanList.get(i-1).me_realname+'</a>');
		}
	});
	
	table.getCol("me_status").each(function(i){	
		if(i>0)
		{
			var status = beanList.get(i-1).me_status;
			if(status == -1)
			{
				$(this).text("停用");
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
	var search_value = $(obj).parent().find("#searchkey").val();
	if(search_value.trim() == "" ||  search_value == null)
	{
		top.msgAlert(WCMLang.Search_empty);
		return;
	}
	search_name = $(obj).parent().find("#searchFields").val(); 

	con_m.put("search_value", search_value);
	con_m.put("search_name", search_name);
	reloadMemberCheckList();
}

// 单独审核
function singleCheck()
{
	var ids = table.getSelecteCheckboxValue("me_id");
	top.OpenModalWindow("会员审核","/sys/member/check/member_check.jsp?member_id="+ids+"&type=single",350,190);
	
}

// 批量审核
function batchCheck()
{
	var ids = table.getSelecteCheckboxValue("me_id");
	top.OpenModalWindow("会员批量审核","/sys/member/check/member_check.jsp?member_id="+ids+"&type=batch",350,140);
}
	
// 审核通过
function saveStatus()
{
	var mp = new Map();
	$(":radio").each(
		function(i)
		{
			if($(this).is(':checked'))
			{
				mp.put("me_status", $(this).val());
				return;
			}
		});
	mp.put("ids", me_id);
	if(!MemberManRPC.checkMemberByIDS(mp))
	{
		top.msgWargin("会员审核" + WCMLang.Set_fail);
		return;
	}
	top.msgAlert("会员审核" + WCMLang.Set_success);
	top.getCurrentFrameObj().reloadMemberCheckList();
	top.CloseModalWindow();
}

function deleteMember()
{
	var selectIDS = table.getSelecteCheckboxValue("me_id");

	if(MemberManRPC.deleteMember(selectIDS))
	{
		top.msgAlert("会员信息"+WCMLang.Delete_success);
		reloadMemberCheckList();
	}else
	{
		top.msgWargin("会员信息"+WCMLang.Delete_fail);
	}
}