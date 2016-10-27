var SendInfoRPC = jsonrpc.SendInfoRPC;
var ReceiveInfoBean = new Bean("com.deya.wcm.bean.sendInfo.ReceiveInfoBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "table";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var m = new Map();
var site_ids = "";//所有可以报送的站点ＩＤ字符串，用于添加窗口进行重

function reloadList()
{
	site_ids = "";
	showList();
	showTurnPage();		
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	colsList.add(setTitleClos("title","标题","","","",""));
	colsList.add(setTitleClos("cat_cname","接收栏目","120px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("s_site_name","报送站点","120px","","",""));
	colsList.add(setTitleClos("s_user_name","报送人","80px","","",""));
	colsList.add(setTitleClos("s_send_dtime","报送时间","120px","","",""));
	colsList.add(setTitleClos("adopt_status","采用状态","120px","","",""));

	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){	
	
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}
	m.put("site_id", site_id);
	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = SendInfoRPC.getReceiveInfoList(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("title").each(function(i){
		if(i>0)
		{		
			$(this).css("text-align","left");
			var url = beanList.get(i-1).s_content_url;
			if(url !="")
			{
				if(url.indexOf("/") == 0)
				{
					url = 'http://'+beanList.get(i-1).s_site_domain+url;
				}
				$(this).html('<a target="_blank" href="'+url+'">'+beanList.get(i-1).title+'</a>');
			}else{
			    $(this).html('<a href="javascript:openViewPage('+beanList.get(i-1).id+')">'+beanList.get(i-1).title+'</a>');
			}
		}
	});	

	table.getCol("adopt_status").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).adopt_status == 0)
				$(this).html('未采用');
			if(beanList.get(i-1).adopt_status == 1)
				$(this).html('已采用');
			if(beanList.get(i-1).adopt_status == -1)
				$(this).html('<a href="javascript:openAdoptDescPage(\''+beanList.get(i-1).adopt_desc+'\')">不采用</a>');
		}
	});	

	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){					
	tp.total = SendInfoRPC.getReceiveInfoCount(m);	
	tp.show("turn","");
	tp.onPageChange = showList;
}

function openViewPage(id)
{
	top.addTab(true,"/sys/sendinfo/reivinfo/sendinfoView.jsp?id="+id,"查看信息");
}

//打开意见窗口
var adopt_desc = "";
function openAdoptDescPage(desc)
{
	adopt_desc = desc;
	top.OpenModalWindow("不采用意见","/sys/sendinfo/reivinfo/adopt_desc.jsp?type=-1",520,235);
}

//采用操作
function adoptReceiveInfo(flag,info_status)
{
	if(flag == 1)
	{
		var adopt_map = new Map();
		adopt_map.put("ids",table.getSelecteCheckboxValue("id"));
		adopt_map.put("is_record","1");
		adopt_map.put("adopt_status",flag+"");
		adopt_map.put("adopt_desc","");
		adopt_map.put("info_status",info_status);
		

		if(SendInfoRPC.adoptReceiveInfo(adopt_map))
		{
			top.msgAlert("采用设置操作成功");
			reloadList();
		}else
		{
			top.msgAlert("采用操作失败，请重新操作");
		}
	}else
	{
		top.OpenModalWindow("不采用意见","/sys/sendinfo/reivinfo/adopt_desc.jsp",520,235);
	}
}

//不采用操作
function dontAdoptRInfoHandl(desc)
{
	var adopt_map = new Map();
	adopt_map.put("ids",table.getSelecteCheckboxValue("id"));
	adopt_map.put("is_record","1");
	adopt_map.put("adopt_status","-1");
	adopt_map.put("adopt_desc",desc);
	if(SendInfoRPC.adoptReceiveInfo(adopt_map))
	{
		top.msgAlert("不采用设置操作成功");
		reloadList();
	}else
	{
		top.msgAlert("不采用操作失败，请重新操作");
	}
}

//删除
function deleteReceiveInfo()
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	var map = new Map();
	map.put("ids",selectIDS);
	if(SendInfoRPC.deleteReceiveInfo(map))
	{
		top.msgAlert("信息"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		top.msgWargin("信息"+WCMLang.Delete_fail);
	}
}

function searchHandl(con)
{
	m.put("conn", con);
	reloadList();
}
