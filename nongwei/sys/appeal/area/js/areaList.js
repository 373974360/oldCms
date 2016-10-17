var AreaRPC = jsonrpc.AreaRPC;
var AreaBean = new Bean("com.deya.wcm.bean.appeal.area.AreaBean",true);

var val=new Validator();
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "Area_table_list";;
var con_m = new Map();

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("area_cname","地区名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　	
	colsList.add(setTitleClos("sort_id","排序","120px","","",""));
	 
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){			

	beanList = AreaRPC.getChildAreaList(area_id);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	//名称加链接
	table.getCol("area_cname").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openNameUpdateAreaPage('+beanList.get(i-1).area_id+','+beanList.get(i-1).parent_id+')">'+beanList.get(i-1).area_cname+'</a>');
		}
	});	
	table.getCol("sort_id").each(function(i){	
		if(i>0)
		{			
			$(this).html(getSortColStr());		
		}
	});
}
//修改页面
function openNameUpdateAreaPage(area_id,parent_id)
{
	  top.OpenModalWindow("维护地区","/sys/appeal/area/area_add.jsp?parentID="+parent_id+"&area_id="+area_id,450,130);
}
function showTurnPage(){					
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}
//新增页面
function openAddAreaPage()
{
	top.OpenModalWindow("维护地区","/sys/appeal/area/area_add.jsp?parentID="+area_id,450,130);
}

//执行新增
function addArea()
{     
	var bean = BeanUtil.getCopy(AreaBean);	
	var parentbean = BeanUtil.getCopy(AreaBean);
	$("#area_table").autoBind(bean);
	if(!standard_checkInputInfo("area_table"))//检测input不能为空
	{
		return;
	}
	bean.area_id = AreaRPC.getAreaID();
	bean.parent_id = parent_id;
	parentbean = AreaRPC.getAreaBean(parent_id);//得到父类数据
	 
	var child_arealevel = parentbean.area_level;
	child_arealevel +=1;
	bean.area_level = child_arealevel;
	if(AreaRPC.insertArea(bean))
	{  
		top.msgAlert("地区信息"+WCMLang.Add_success);	
		top.getCurrentFrameObj().changeAreaListTable(parent_id);
		top.getCurrentFrameObj().insertAreaTree(bean.area_id,bean.area_cname);
		top.CloseModalWindow();
	}
	else
	{  
		top.msgWargin("地区信息"+WCMLang.Add_fail);
	}
}

//添加树节点

function insertAreaTree(id,dept_name)
{
	insertTreeNode(eval('[{"id":'+id+',"text":\"'+dept_name+'\","attributes":{}}]'));	
}

//修改页面
function openUpdateAreaPage()
{
	var selectIDS = table.getSelecteCheckboxValue("area_id");
	  top.OpenModalWindow("维护地区","/sys/appeal/area/area_add.jsp?parentID="+area_id+"&area_id="+selectIDS,450,130);
}


//执行修改
 
function updateArea()
{    
	var bean = BeanUtil.getCopy(AreaBean);	
	$("#area_table").autoBind(bean);
	 
	if(!standard_checkInputInfo("area_table"))
	{ 
		return;
	}
	bean.area_id = area_id;
	 
	if(AreaRPC.updateArea(bean))
	{  
		top.msgAlert("地区信息"+WCMLang.Add_success);	
		 
		 top.getCurrentFrameObj().changeAreaListTable(parent_id);
		 
		 top.getCurrentFrameObj().updateAreaTree(bean.area_id,bean.area_cname);
		 
	 
		top.CloseModalWindow();
	}else{
		top.msgWargin("地区信息"+WCMLang.Add_fail);
	}
}

//修改树节点
function updateAreaTree(id,area_cname)
{
	updateTreeNode(id,area_cname);
}
//删除

function deleteArea()
{
	var selectIDS = table.getSelecteCheckboxValue("area_id");
	if(AreaRPC.deleteArea(selectIDS))
	{
		top.msgAlert("地区信息"+WCMLang.Delete_success);
		top.getCurrentFrameObj().changeAreaListTable(area_id);
		top.getCurrentFrameObj().deleteTreeNode(selectIDS);
	}else
	{
		top.msgWargin("地区信息"+WCMLang.Delete_fail);
	}
}
//节点排序
function saveAreaSort()
{
	var selectIDS = table.getAllCheckboxValue("area_id");
	if(AreaRPC.saveAreaSort(selectIDS))
	{
		top.msgAlert(WCMLang.Sort_success);
		showAreaTree();	 
	}else
	{  
		top.msgWargin(WCMLang.Sort_fail);
	}
}
//打开权限选择窗口
/*function openSelectSinglAreaPage(title,handl_name)
{
	top.OpenModalWindow(title,"/sys/org/Area/select_singl_Area.jsp?handl_name="+handl_name,450,510);
}*/
