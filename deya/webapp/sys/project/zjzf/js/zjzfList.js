var ZJZFRPC = top.jsonrpc.ZJZFRPC;

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "gongqidiv";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var m = new Map();

function reloadPicViewList()
{
	showList();	
	showTurnPage();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("name","姓名","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("xb","性别","50px","","",""));
	colsList.add(setTitleClos("csny","出生年月","100px","","",""));
	colsList.add(setTitleClos("mz","民族","100px","","",""));
	colsList.add(setTitleClos("zzmm","政治面貌","60px","","",""));
	colsList.add(setTitleClos("gzdw","工作单位","130px","","",""));
	colsList.add(setTitleClos("zhiwu","职务","100px","","",""));
	colsList.add(setTitleClos("phone","手机号码","100px","","",""));
	colsList.add(setTitleClos("add_time","提交时间","","","",""));

	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){
	
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("type", "1");	
	m.put("orderby", "id desc");
	
	beanList = ZJZFRPC.getGongMinList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdatePicViewPage('+beanList.get(i-1).id+')">'+beanList.get(i-1).name+'</a>');
		}
	});		

	Init_InfoTable(table.table_name);
}

function showTurnPage(){	
	
	tp.total = ZJZFRPC.getGongMinListCount(m);
			
	tp.show("turn","");	
	tp.onPageChange = showList;
}


//打开修改窗口
function openUpdatePicViewPage(id)
{
	var c_id;
	if(id != null)
	{
		c_id = id;
	}else
	{
		c_id = table.getSelecteCheckboxValue("id");
	}
	
	top.addTab(true,"/manager/project/wnzjzf/view_zjzf.jsp?id="+c_id+"&topnum="+top.curTabIndex,"报名信息");
	
}

function openUpdatePicUpdatePage(id)
{
	top.addTab(true,"/manager/project/picview/update_pic.jsp?id="+id+"&topnum="+top.curTabIndex+"&audit_type="+audit_type+"&publish_type="+publish_type,"投稿信息");
}

//删除投稿
function deletePicView()
{	
	var selectIDS = table.getSelecteCheckboxValue("id");
	var m = new Map();
	m.put("id",selectIDS);
	if(ZJZFRPC.deleteZJZF(m))
	{
		top.msgAlert("报名信息"+WCMLang.Delete_success);		
		reloadPicViewList();
	}
	else
	{
		top.msgWargin("报名信息"+WCMLang.Delete_success);
	}
}

//搜索
function SearchHandl(obj)
{
	var con_value = $(obj).parent().find("#searchkey").val();
	if(con_value.trim() == "" ||  con_value == null)
	{
		top.msgAlert(WCMLang.Search_empty);
		return;
	}
	m.put("keyword", con_value);
	reloadPicViewList();
}




