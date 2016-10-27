var GuestBookRPC = jsonrpc.GuestBookRPC;

var selectBean = null;//当前选中项对象
var val=new Validator();
var serarch_con = "";//查询条件
var beanList = null;
var table = new Table();	
table.table_name = "table";
var current_role_bean;
var is_button_click = true;//是否是点击的按钮触发事件
var m = new Map();

function reloadList()
{
	initTable();
	showList();	
}

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();		
	colsList.add(setTitleClos("title","留言主题","300px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("count","留言总数","70px","","",""));
	colsList.add(setTitleClos("publish_count","已发布总数","70px","","",""));
	colsList.add(setTitleClos("reply_count","已回复总数","70px","","",""));
	colsList.add(setTitleClos("space_col"," ","","","",""));

	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function funOK()
{
	m.remove("start_day");
	m.remove("end_day");
	m.put("site_id", site_id);
	var cat_ids = "";
	$(":checked[name='b_id'] ").each(function(){
		if($(this).val() != "")
			cat_ids += ","+$(this).val();
	});
	if(cat_ids != "")
	{
		m.put("cat_ids", cat_ids.substring(1));
		var start_day = $("#s").val();
	    var end_day = $("#e").val();
		if(start_day != "" && start_day != null)
			m.put("start_day", start_day+" 00:00:00");
		if(end_day != "" && end_day != null)
			m.put("end_day", end_day+" 23:59:59");

		if(start_day != "" && start_day != null && end_day != "" && end_day != null)
		{
			if(!judgeDate(start_day,end_day))
			{
				top.msgWargin("结束时间不能在开始时间前");
				return;
			}
		}

		reloadList();
	}else
	{
		top.msgWargin("请选择留言分类");
	}
	
}

function showList(){			

	beanList = GuestBookRPC.getGBCategoryStatistics(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");	
	
	Init_InfoTable(table.table_name);
}

//得到所有分类列表
function getAllGBCatList()
{
	var tableTr = "";
	var beanList = null;

	beanList = GuestBookRPC.getGuestBookCategoryList(site_id);
	beanList = List.toJSList(beanList);

	for (var i = 0; i < beanList.size(); i++) {
		tableTr += '<li><input style="vertical-align:middle" type="checkbox" id="cat_id" name="b_id" onclick="setAllState(this)" value="'+beanList.get(i).cat_id+'"><label>'+beanList.get(i).title+'</label></li>';
	}
	$("#b_tr").append(tableTr);
}

function setAllState(){
     if(isContentSelectedAll()){
		 $("[name='all']").attr("checked",'checked');
	 }else{
	 	$("[name='all']").removeAttr("checked");
	 }
}

//判断不是全选  全选：true  不是全选：false
function isContentSelectedAll(){
	var n = 0;
	var k = 0;
	$("[name='b_id']").each(function(){
		n++;
     if($(this).is(':checked'))
      {
         k++;
      }
    })
	if(k==n){//全选
         return true;
	}
	return false;
}

function fnAll(){
	if($("[name='all']").is(':checked')){
  	  fnSelectAll();//全选
    }else{
  	  fnCancelAll();//取消全选
    }
}

function fnSelectAll(){
	$("[name='b_id']").attr("checked",'checked');
}

function fnCancelAll(){
	$("[name='b_id']").removeAttr("checked");
}
