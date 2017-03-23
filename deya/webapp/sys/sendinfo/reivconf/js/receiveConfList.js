var SendInfoRPC = jsonrpc.SendInfoRPC;
var ReceiveConfigBean = new Bean("com.deya.wcm.bean.sendInfo.ReceiveConfigBean",true);
var ReceiveCatConf = new Bean("com.deya.wcm.bean.sendInfo.ReceiveCatConf",true);

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
	colsList.add(setTitleClos("site_id","站点ID","50px","","",""));
	colsList.add(setTitleClos("site_name","站点名称","300px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("receive_status","接收状态","100px","","",""));
	colsList.add(setTitleClos("space_col"," ","","","",""));

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

	m.put("start_num", tp.getStart());	
	m.put("page_size", tp.pageSize);
	m.put("sort_name", sortCol);
	m.put("sort_type", sortType);
	if(table.con_value.trim() != "" && table.con_value != null)
	{
		m.put("con_name", table.con_name);
		m.put("con_value", table.con_value);
	}

	beanList = SendInfoRPC.getReceiveConfigList();//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("site_name").each(function(i){
		if(i>0)
		{		
			$(this).css("text-align","left");
			site_ids += beanList.get(i-1).site_id+",";
			$(this).html('<a href="javascript:openUpdateReceiveConfigPage(\''+beanList.get(i-1).site_id+'\')">'+beanList.get(i-1).site_name+'</a>');
		}
	});	

	table.getCol("receive_status").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).receive_status == 0)
				$(this).html('启用');
			else
				$(this).html('停用');
		}
	});	

	
	Init_InfoTable(table.table_name);
}

function showTurnPage(){					
	
	tp.show("turn","simple");
	tp.onPageChange = showList;
}

//打开添加窗口
function openAddReceiveConfigPage()
{
	addTab(true,"/sys/sendinfo/reivconf/add_reivconf.jsp?topnum="+curTabIndex,"报送配置");
}

//打开修改窗口
function openUpdateReceiveConfigPage(s_id)
{
	var id = "";
	if(s_id != null)
		id = s_id;
	else
		id = table.getSelecteCheckboxValue("site_id");
	addTab(true,"/sys/sendinfo/reivconf/add_reivconf.jsp?site_id="+id+"&topnum="+curTabIndex,"报送配置");
}

//得到栏目列表
function getCatList(site_id)
{
	var l = new List();
	$("#cat_list li").each(function(i){
		var sb = BeanUtil.getCopy(ReceiveCatConf);
		sb.site_id = site_id;
		sb.cat_id = $(this).attr("cat_id");
		sb.sort_id = i;
		l.add(sb);
	});
	return l;
}

function insertReceiveConfig()
{
	var bean = BeanUtil.getCopy(ReceiveConfigBean);	
	$("#receiveConfig_table").autoBind(bean);

	if(bean.site_id == "")
	{
		msgWargin("请选择站点");
		return;
	}
	
	if(SendInfoRPC.insertReceiveConfig(bean,getCatList(bean.site_id)))
	{
		msgAlert("接收站点配置"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).reloadList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("接收站点配置"+WCMLang.Add_fail);
	}
}

function updateReceiveConfig()
{
	var bean = BeanUtil.getCopy(ReceiveConfigBean);	
	$("#receiveConfig_table").autoBind(bean);

	if(!standard_checkInputInfo("receiveConfig_table"))
	{
		return;
	}	
	bean.id = defaultBean.id;
	bean.site_id = site_id;
	if(SendInfoRPC.updateReceiveConfig(bean,getCatList(site_id)))
	{
		msgAlert("接收站点配置"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).reloadList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("接收站点配置"+WCMLang.Add_fail);
	}
}

function deleteReceiveConfig()
{
	var selectIDS = table.getSelecteCheckboxValue("site_id");
	
	
	if(SendInfoRPC.deleteReceiveConfig(selectIDS))
	{
		msgAlert("接收站点配置"+WCMLang.Delete_success);
		reloadList();
	}else
	{
		msgWargin("接收站点配置"+WCMLang.Delete_fail);
	}
}


function publishReceiveConfig(receive_status)
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	var m = new Map();
	m.put("receive_status",receive_status+"");
	m.put("ids",selectIDS);
	if(SendInfoRPC.updateReceiveConfigStatus(m))
	{
		msgAlert("接收站点配置"+WCMLang.Set_success);
		reloadList();
	}else
	{
		msgWargin("接收站点配置"+WCMLang.Set_fail);
	}
}
