var ModelRPC = jsonrpc.ModelRPC;
var MetaDataRPC = jsonrpc.MetaDataRPC;
var ModelFieldBean = new Bean("com.deya.wcm.bean.system.formodel.ModelFieldBean",true);

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "field_table";

var meta_map = MetaDataRPC.getMetaDataMap();
meta_map = Map.toJSMap(meta_map);

function reloadFieldList()
{
	showList();	
	showTurnPage();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();		
	
	colsList.add(setTitleClos("field_cname","中文名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("field_ename","英文名称","100px","","",""));
	colsList.add(setTitleClos("table_name","对应表名","100px","","",""));
	colsList.add(setTitleClos("is_show","是否展示","80px","","",""));
	colsList.add(setTitleClos("meta_id","对应元数据","150px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){		

	beanList = ModelRPC.getFieldListByModelID(model_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("field_cname").each(function(i){
		if(i>0)
		{			
			$(this).html('<a href="javascript:openViewFieldPage('+beanList.get(i-1).field_id+')">'+beanList.get(i-1).field_cname+'</a>');
		}
	});		

	table.getCol("is_show").each(function(i){
		if(i>0)
		{			
			if(beanList.get(i-1).is_show == 0)
				$(this).text("展示");
			else
				$(this).text("不展示");
		}
	});	
	table.getCol("meta_id").each(function(i){
		if(i>0)
		{			
			if(beanList.get(i-1).meta_id != 0)
				$(this).text(meta_map.get(beanList.get(i-1).meta_id).meta_cname);
			else
				$(this).text("");
		}
	});	

	Init_InfoTable(table.table_name);
}

function showTurnPage(){					
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

//打开查看窗口
function openViewFieldPage(r_id)
{	
	OpenModalWindow("查看内容模型字段","/sys/system/formodel/field_view.jsp?field_id="+r_id,450,285);
}

//打开添加窗口
function openAddFieldPage()
{
	OpenModalWindow("修改内容模型字段","/sys/system/formodel/field_add.jsp?model_id="+model_id,450,285);
}

//打开修改窗口
function openUpdateFieldPage()
{
	var selectList = table.getSelecteBeans();	
	if(selectList.get(0).table_name != "cs_info_udefined")
	{
		msgWargin("系统字段不允许修改");
	}
	else
		OpenModalWindow("修改内容模型字段","/sys/system/formodel/field_add.jsp?field_id="+selectList.get(0).field_id,450,285);
}

//添加内容模型
function addModelField()
{
	var bean = BeanUtil.getCopy(ModelFieldBean);	
	$("#field_table").autoBind(bean);

	if(!standard_checkInputInfo("field_table"))
	{
		return;
	}
	
	bean.model_id = model_id;
	bean.table_name = "cs_info_udefined";
	if(ModelRPC.insertModelField(bean))
	{
		msgAlert("内容模型字段"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadFieldList();
	}
	else
	{
		msgWargin("内容模型字段"+WCMLang.Add_fail);
	}
}
//修改内容模型
function updateModelField()
{
	var bean = BeanUtil.getCopy(ModelFieldBean);	
	$("#field_table").autoBind(bean);

	if(!standard_checkInputInfo("field_table"))
	{
		return;
	}
  
	if(ModelRPC.updateModelField(bean))
	{
		msgAlert("内容模型字段"+WCMLang.Add_success);
		CloseModalWindow();
		getCurrentFrameObj().reloadFieldList();
	}
	else
	{
		msgWargin("内容模型字段"+WCMLang.Add_fail);
	}
}

function deleteModelField()
{
	var selectList = table.getSelecteBeans();	
	var c_name = "";
	for(var i=0;i<selectList.size();i++)
	{
		if(selectList.get(i).table_name != "cs_info_udefined")
		{
			c_name += ","+selectList.get(i).field_cname;
		}
	}
	if(c_name != "")
	{
		c_name = c_name.substring(1);
		msgWargin(c_name+" 这些系统字段不允许删除");
		return;
	}

	var selectIDS = table.getSelecteCheckboxValue("field_id");
	if(ModelRPC.deleteModelField(selectIDS))
	{
		msgAlert("内容模型字段"+WCMLang.Delete_success);
		reloadFieldList();
	}else
	{
		msgWargin("内容模型字段"+WCMLang.Delete_fail);
	}
}