var SerResouceBean = new Bean("com.deya.wcm.bean.zwgk.ser.SerResouceBean",true);

function loadSerResouceTable()
{
	showResouceList();	
	showResouceTurnPage();	
}

function initResouceTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("res_id","ID","20px","","",""));
	colsList.add(setTitleClos("title","标题","300px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("publish_status","发布状态","100px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	colsList.add(setTitleClos("spanCol"," ","","","",""));
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showResouceList(){	
	
	beanList = SerRPC.getSerResouceList(ser_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size()
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("title").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openAddResoucePage('+beanList.get(i-1).res_id+')">'+beanList.get(i-1).title+'</a>');
		}
	});	

	table.getCol("publish_status").each(function(i){
		if(i>0)
		{
			if(beanList.get(i-1).publish_status == 0)
				$(this).html('未发布');
			else
				$(this).html('已发布');
		}
	});	

	table.getCol("sort_col").each(function(i){	
		if(i>0)
		{
			$(this).html(getSortColStr());
		}
	});	
	
	Init_InfoTable(table.table_name);
}

function showResouceTurnPage(){				
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

function openAddResoucePage(res_id)
{
	if(res_id != null)
		addTab(true,"/sys/ggfw/ser/serResouce_add.jsp?top_index="+curTabIndex+"&ser_id="+ser_id+"&res_id="+res_id,"服务资源修改");
	else
		addTab(true,"/sys/ggfw/ser/serResouce_add.jsp?top_index="+curTabIndex+"&ser_id="+ser_id,"服务资源修改");
}

function openUpdateResoucePage()
{
	openAddResoucePage(table.getSelecteCheckboxValue("res_id"));
}

function addSerResouce()
{
	var bean = BeanUtil.getCopy(SerResouceBean);
	$("#add_table").autoBind(bean);
	
	if(!standard_checkInputInfo("add_table"))
	{
		return;
	}
	bean.content = getV(contentId);
	bean.ser_id = ser_id;
	if(SerRPC.insertSerResouce(bean))
	{
		msgAlert("服务资源"+WCMLang.Add_success);
		getCurrentFrameObj(top_index).loadSerResouceTable();
		tab_colseOnclick(curTabIndex);
	}else
	{
		msgWargin("服务资源"+WCMLang.Add_fail);
	}
}

function updateSerResouce()
{
	var bean = BeanUtil.getCopy(SerResouceBean);
	$("#add_table").autoBind(bean);
	
	if(!standard_checkInputInfo("add_table"))
	{
		return;
	}
	bean.content = getV(contentId);
	bean.res_id = res_id;
	if(SerRPC.updateSerResouce(bean))
	{
		msgAlert("服务资源"+WCMLang.Add_success);
		getCurrentFrameObj(top_index).loadSerResouceTable();
		tab_colseOnclick(curTabIndex);
	}else
	{
		msgWargin("服务资源"+WCMLang.Add_fail);
	}
}

function deleteSerResouce()
{
	var selectIDS = table.getSelecteCheckboxValue("res_id");

	if(SerRPC.deleteSerResouce(selectIDS))
	{
		msgAlert("服务资源"+WCMLang.Delete_success);
		loadSerResouceTable();
	}else
	{
		msgWargin("服务资源"+WCMLang.Delete_fail);
	}
}

function sortSerResouce()
{
	var selectIDS = table.getAllCheckboxValue("res_id");
	if(SerRPC.sortSerResouce(selectIDS))
	{
		msgAlert(WCMLang.Sort_success);
	}else
	{
		msgWargin(WCMLang.Sort_fail);
	}
}

function batchPublishSerResouce(publish_status)
{
	var selectIDS = table.getSelecteCheckboxValue("res_id");
	if(SerRPC.updateSerResouceStatus(selectIDS,publish_status))
	{
		msgAlert(WCMLang.Publish_success);
		loadSerResouceTable();
	}
	else
		msgWargin(WCMLang.Publish_fail);
}