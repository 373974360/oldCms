var WareRPC = jsonrpc.WareRPC;
var WareBean = new Bean("com.deya.wcm.bean.system.ware.WareBean", true);

var con_m = new Map();
var beanList = null;
var table = new Table();
table.table_name = "Ware_table_list";
var tp = new TurnPage();

function loadWareTable()
{
	showList();
	showTurnPage();
}

function initTable()
{
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("ware_id","ID","20px","","",""));
	colsList.add(setTitleClos("ware_name","标签名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　	
	colsList.add(setTitleClos("ware_ver","版本号","","","",""));　	
	colsList.add(setTitleClos("ware_type","类型","50px","","",""));
	colsList.add(setTitleClos("sort_col","操作","180px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);	
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList()
{
	beanList = WareRPC.getWareList(id, con_m);
	beanList =  List.toJSList(beanList);
	tp.total = beanList.size();		
	
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	table.getCol("ware_name").each(function(i){	
		if(i>0)
		{
			$(this).css("text-align","left");
			var img = "";
			switch(beanList.get(i-1).ware_type)
			{
				case 0 :	img = '<img src="../../images/ware_html.png"/>';
					break;
				case 1 :	img = '<img src="../../images/ware_clock.png"/>';
					break;
				case 2 :	img = '<img src="../../images/ware_cog.png"/>';
					break;
				case 3 :	img = '<img src="../../images/ware_rss.png"/>';
					break;
				default:	img = "";
					
			}
			$(this).html('<a href="javascript:openUpdatePageByIndex('+(i-1)+', '+beanList.get(i-1).wcat_id+')">'+img+'&nbsp;'+beanList.get(i-1).ware_name+'</a>');
		}
	});
	
	table.getCol("ware_type").each(function(i){	
		if(i>0)
		{
			var type_name = "";
			switch(beanList.get(i-1).ware_type)
			{
				case 0 :	type_name = "静态";;
					break;
				case 1 :	type_name = "自动";
					break;
				case 2 :	type_name = "手动";
					break;
				case 3 :	type_name = "RSS";	
					break;
				default:	type_name = "";
					
			}
			$(this).html(type_name);
		}
	});
	
	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{
			var str = "";
			str += '<span onclick="javascript:top.msgConfirm(WCMLang.Delete_confirm, \'deleteWareByIndex('+(i-1)+')\')" style="cursor : pointer"> 删除</span>&#160;&#160;';
			str += '<span onclick="openHistoryWareVer(\''+beanList.get(i-1).ware_id+'\',\''+beanList.get(i-1).site_id+'\')" style="cursor:pointer;">历史版本</span>&#160;&#160;&#160;';
			$(this).html(getSortColStr()+str);
		}
	});
	
	table.getCol("ware_ver").each(function(i){	
		if(i>0)
		{
			$(this).html(beanList.get(i-1).ware_ver);
		}
	});

	
	Init_InfoTable(table.table_name);
}

function showTurnPage()
{
	tp.show("turn","simple");
	tp.onPageChange = showList;
}

function openHistoryWareVer(ware_id,site_id)
{
	top.OpenModalWindow("标签历史版本","/sys/system/ware/wareDataVerList.jsp?wareid="+ware_id+"&siteid="+site_id+"&appid="+app_id,1000,530);
}

// 保存排序button事件
function saveSort()
{
	var ids = table.getAllCheckboxValue("id");
	if(WareRPC.savaWareSort(ids))
	{
		top.msgAlert(WCMLang.Sort_success);
	}
	else
	{
		top.msgWargin(WCMLang.Sort_fail);
	}
}

// table中的点击删除事件
function deleteWareByIndex(index)
{
	var singleID = beanList.get(index).ware_id;
	con_m.put("ware_id", singleID);
	con_m.put("site_id", site_id);
	if(WareRPC.deleteWare(con_m))
	{
		top.msgAlert("信息标签"+WCMLang.Delete_success);
		loadWareTable();
	}
	else
	{
		top.msgWargin("信息标签"+WCMLang.Delete_fail);
	}
	con_m.remove("id");
}

// table下方删除按钮引发的事件
function deleteWareByIDS()
{
	var arrIDS = table.getSelecteCheckboxValue("ware_id");
	con_m.put("ware_id", arrIDS);
	con_m.put("site_id", site_id);
	if(WareRPC.deleteWare(con_m))
	{
		top.msgAlert("信息标签"+WCMLang.Delete_success);
		loadWareTable();
	}
	else
	{
		top.msgWargin("信息标签"+WCMLang.Delete_fail);
	}
	con_m.remove("id");
}

// 打开添加信息标签页面
function openAddPage()
{
	if(id == 0)
	{
		top.msgWargin("请先选择标签分类");
		return;
	}
	window.location.href  = "/sys/system/ware/wareAdd.jsp?site_id="+site_id+"&type=add&app_id="+app_id+"&wcat_id="+id;
}

// 打开信息标签修改页面
function openUpdatePage()
{
	var selectediID = table.getSelecteCheckboxValue("id");
	window.location.href  = "/sys/system/ware/wareAdd.jsp?site_id="+site_id+"&type=update&app_id="+app_id+"&wcat_id="+id+"&id="+selectediID;
}

function openUpdatePageByIndex(index, wcat_id)
{
	var bean = beanList.get(index);
	window.location.href = "/sys/system/ware/wareAdd.jsp?site_id="+site_id+"&type=update&app_id="+app_id+"&wcat_id="+wcat_id+"&id="+bean.id;
}

/************************************信息标签 添加修改 操作**************************************/


// 返回信息标签列表页
function backWareList()
{
	window.location.href  = "/sys/system/ware/wareList.jsp?site_id="+site_id+"&app_id="+app_id+"&id="+wcat_id;
}

function saveAddWare()
{	
	if(!standard_checkInputInfo("ware_table"))
	{
		return;
	}
	var addBean = BeanUtil.getCopy(WareBean);
	$("#ware_table").autoBind(addBean);
	var twareType = $(":radio[name='tware_type'][checked]").val();
	addBean.ware_type = twareType;
	addBean.wcat_id = wcat_id;//临时用，因为分类信息不能正常使用
	addBean.ware_content = editAreaLoader.getValue();
	if(WareRPC.insertWare(addBean))
	{
		top.msgAlert("信息标签"+WCMLang.Add_success);
		backWareList();
	}
	else
	{
		top.msgWargin("信息标签"+WCMLang.Add_fail);
	}
}

function saveUpdateWare()
{
	if(!standard_checkInputInfo("ware_table"))
	{
		return;
	}
    var updateBean = BeanUtil.getCopy(defaultBean);
    $("#ware_table").autoBind(updateBean);
	var twareType = $(":radio[name='tware_type'][checked]").val();
	updateBean.ware_type = twareType;
	updateBean.wcat_id = wcat_id;//临时用，因为分类信息不能正常使用
	updateBean.ware_content = editAreaLoader.getValue();
	//var recoveryType="update";
	if(WareRPC.updateWare(updateBean))
	{
		top.msgAlert("信息标签"+WCMLang.Set_success);
		backWareList();
	}
	else
	{
		top.msgWargin("信息标签"+WCMLang.Set_fail);
	}
}

function movWares(){
	var arrIDS = table.getSelecteCheckboxValue("id");
	
	top.OpenModalWindow("新建标签分类","/sys/system/ware/mov_wares.jsp?ids="+arrIDS+"&site_id="+site_id,500,508);
}

function updateMove(ids,cid){
	var tmap = new Map();
	tmap.put("ware_ids",ids);
	tmap.put("wcat_id",cid);
	
	if(WareRPC.moveWareToOtherCategory(tmap))
	{
		top.msgAlert("标签转移成功");
	}
	else
	{
		top.msgWargin("标签转移失败");
	}
}

function createWareContentHtml()
{
	var arrIDS = table.getSelecteCheckboxValue("ware_id");
	if(WareRPC.createHtmlPageMutil(arrIDS,site_id))
	{
		top.msgAlert("静态页生成成功");		
	}else{
		top.msgWargin("静态页生成失败,请重新生成");
	}
}