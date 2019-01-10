var ZjzfTypeRPC = jsonrpc.ZjzfTypeRPC;

var ZjzfTypeBean = new Bean("com.deya.project.zjzf.ZjzfTypeBean",true);
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

function reloadList()
{
	showList();
}

function initTable(){

	var colsList = new List();	
	
	colsList.add(setTitleClos("name","分类名称","200px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("start_time","开始时间","150px","","",""));
	colsList.add(setTitleClos("end_time","结束时间","150px","","",""));
	colsList.add(setTitleClos("remark","备注","","","",""));

	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){

	
	beanList = ZjzfTypeRPC.getZjzfTypeList();//第一个参数为站点ID，暂时默认为空
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("name").each(function(i){
		if(i>0)
		{
			$(this).html('<a href="javascript:openUpdatePage('+beanList.get(i-1).id+')">'+beanList.get(i-1).name+'</a>');
		}
	});		

	Init_InfoTable(table.table_name);
}


function openAddPage()
{
    window.location.href = "add_zjzftype.jsp";
}

function fuopenUpdatePage()
{
    var selectIDS = table.getSelecteCheckboxValue("id");
    window.location.href = "add_zjzftype.jsp?id="+selectIDS;
}

function openUpdatePage(id)
{
    window.location.href = "add_zjzftype.jsp?id="+id;
}

function saveZjzfBean()
{
    var bean = BeanUtil.getCopy(ZjzfTypeBean);
    $("#zjzfType").autoBind(bean);

    if(bean.id.trim() == "0")
    {
        if(ZjzfTypeRPC.insertZjzfType(bean))
        {
            top.msgAlert("报名分类"+WCMLang.Add_success);
            window.location.href='zjzfType.jsp'
        }else
        {
            top.msgWargin("报名分类"+WCMLang.Add_fail);
        }
    }
    else
    {
        if(ZjzfTypeRPC.updateZjzfType(bean))
        {
            top.msgAlert("报名分类"+WCMLang.Add_success);
            window.location.href='zjzfType.jsp'
        }else
        {
            top.msgWargin("报名分类"+WCMLang.Add_fail);
        }
    }
}


//删除投稿
function deleteZjzftype()
{
    var selectIDS = table.getSelecteCheckboxValue("id");
    var m = new Map();
    m.put("id",selectIDS);
    if(ZjzfTypeRPC.deleteZjzfType(m))
    {
        top.msgAlert("报名分类"+WCMLang.Delete_success);
        reloadList();
    }
    else
    {
        top.msgWargin("报名分类"+WCMLang.Delete_success);
    }
}





