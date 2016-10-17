var YsqgkRPC = jsonrpc.YsqgkRPC;
var YsqgkPhrasalBean = new Bean("com.deya.wcm.bean.zwgk.ysqgk.YsqgkPhrasalBean",true);

var val= new Validator();
var beanList = null;

var list_table = new Table();
    list_table.table_name = "list_table";

var table = new Table();	
	table.table_name = "Phrasal_table";

function initTable()
{
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("gph_title","常用语短名","","","",""));//英文名，显示名，宽，高，样式名，点击事件
	colsList.add(setTitleClos("gph_type","类型","100px","","",""));　
	colsList.add(setTitleClos("sort_id","排序","100px","","",""));
	
	list_table.setColsList(colsList);
	list_table.setAllColsList(colsList);				
	list_table.enableSort=false;//禁用表头排序
	list_table.onSortChange = showList;
	list_table.show("list_table");//里面参数为外层div的id
}
function showList(gph_type)
{
	var sortCol = list_table.sortCol;
	var sortType = list_table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "gph_id";
		sortType = "desc";
	}
	if(gph_type != "")
	{
		if( gph_type == "all"){
			beanList = YsqgkRPC.getYsqgkPhrasalLists();
		}else{
			beanList = YsqgkRPC.getYsqgkPhrasaListByType(gph_type);
		}
	}
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	list_table.setBeanList(beanList,"td_list");//设置列表内容的样式
	list_table.show("list_table");
	
	list_table.getCol("gph_title").each(function(i){	
		if(i>0)
		{
			$(this).html('<a href="javascript:openPhrasaPage2('+beanList.get(i-1).gph_id+')">'+beanList.get(i-1).gph_title+'</a>');	
		}
	});
	list_table.getCol("gph_type").each(function(i){		
		if(i>0)
		{			
			var ysq_type = beanList.get(i-1).gph_type;
			var viewType = "";
			switch(ysq_type)
			{
				case 0	:viewType="登记回执";break;
				case 1	:viewType="全部公开";break;
				case 2	:viewType="部分公开";break;
				case 3	:viewType="不予公开";break;
				case 4	:viewType="非本单位信息";break;
				case 5	:viewType="信息不存在";break;
			}
			$(this).html(viewType);			
		}
	});
	list_table.getCol("sort_id").each(function(i){	
		if(i>0)
		{			
			$(this).html(getSortColStr());			
		}
	});
	Init_InfoTable(list_table.table_name);
}
// 添加常用语信息
function addPhrasalRecord()
{
	window.location.href  = "/sys/zwgk/ysqgk/operate/phrasal_add.jsp?type=add&gph_type="+gph_type;
}
function openPhrasaPage2(gph_id)
{
	window.location.href  = "/sys/zwgk/ysqgk/operate/phrasal_add.jsp?type=update&gph_id="+gph_id+"&ggph_type="+gph_type;
}
// 修改常用语信息
function updatePhrasalRecord()
{
	var gph_id = list_table.getSelecteCheckboxValue("gph_id");
	window.location.href  = "/sys/zwgk/ysqgk/operate/phrasal_add.jsp?type=update&gph_id="+gph_id+"&ggph_type="+gph_type;
}
//添加常用语信息-保存事件
function addPhrasal()
{
	var addBean = BeanUtil.getCopy(YsqgkPhrasalBean);
		$("#Phrasal_table").autoBind(addBean);
	if(!standard_checkInputInfo("Phrasal_table"))
	{
		return;
	}
	addBean.gph_id = YsqgkRPC.getGph_id();
	//addBean.gph_content = getV("gph_content");
	addBean.gph_type = $('#gph_type').val();
	addBean.gph_content = getV("gph_content");
	if(YsqgkRPC.insertYsqgkPhrasalBean(addBean))
	{
		top.msgAlert("常用语信息"+WCMLang.Add_success);
		goBack();
	}
	else
	{
		top.msgWargin("常用语信息"+WCMLang.Add_fail);
		return;
	}
}
// 修改常用语信息-保存事件
function updatePhrasal(gph_type)
{
	var updateBean = BeanUtil.getCopy(YsqgkPhrasalBean);
	$("#Phrasal_table").autoBind(updateBean);
	if(!standard_checkInputInfo("Phrasal_table"))
	{
		return;
	}
	updateBean.gph_id = $("#gph_id").val();
	updateBean.gph_type = $('#gph_type').val();
	updateBean.gph_content = getV("gph_content");
	if(YsqgkRPC.updateYsqgkPhrasalBean(updateBean))
	{
		top.msgAlert("常用语信息"+WCMLang.Set_success);
		//top.getCurrentFrameObj().showList();
		//top.CloseModalWindow();
		goBack();
		//window.location.href  = "/sys/zwgk/ysqgk/operate/ysqgk_phrasal.jsp?gph_type="+gph_type;
	}
	else
	{
		top.msgWargin("常用语信息"+WCMLang.Set_fail);
		return;
	}
}
//删除常用语信息
function deletePhrasal(gph_type)
{
	var gph_id = list_table.getSelecteCheckboxValue("gph_id");
	if(gph_id =="")
	{
		top.msgAlert("请选择要操作的记录");
		return;
	}else{
		var mp = new Map();
			mp.put("gph_id", gph_id);
		if(YsqgkRPC.deleteYsqgkPhrasal(mp))
		{
			top.msgAlert("常用语信息"+WCMLang.Delete_success);
			showList(gph_type);
		}
		else
		{
			top.msgWargin("常用语信息"+WCMLang.Delete_fail);
		}
	}
}
//节点排序
function sortPhrasal(gph_type)
{
	var selectIDS = list_table.getAllCheckboxValue("gph_id");
	if(YsqgkRPC.saveYsqgkPhrasalSort(selectIDS))
	{
		top.msgAlert(WCMLang.Sort_success);
		showList(gph_type);	
	}else
	{
		top.msgWargin(WCMLang.Sort_fail);
	}
}

function goBack()
{
	window.location.href  = "/sys/zwgk/ysqgk/operate/ysqgk_phrasal.jsp?gph_type="+gph_type;
}