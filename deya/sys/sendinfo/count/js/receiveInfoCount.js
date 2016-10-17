var selectBean = null;//当前选中项对象
var serarch_con = "";//查询条件
var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "table";

var is_button_click = true;//是否是点击的按钮触发事件
var m = new Map();

function initTable(){
	
	var colsMap = new Map();	
	var colsList = new List();	
	colsList.add(setTitleClos("site_name","报送站点","","","",""));
	colsList.add(setTitleClos("send_count","报送总数","120px","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	colsList.add(setTitleClos("adopt_count","采用数","120px","","",""));
	colsList.add(setTitleClos("not_count","未采用数","80px","","",""));
	colsList.add(setTitleClos("adopt_rate","采用率","","","",""));

	table.checkbox = false;
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function showList(){

	beanList = SendInfoRPC.getSendSiteCountForReceive(m);//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	tp.total = beanList.size();
	
	curr_bean = null;		
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	
	Init_InfoTable(table.table_name);
}

function getSendSiteList()
{
	var l = SendInfoRPC.getSendSiteList(site_id);
	l = List.toJSList(l);
	var tableTr = "";
	if(l != null && l.size() > 0)
	{
		for (var i = 0; i < l.size(); i++) {
			tableTr += '<li><input style="vertical-align:middle" type="checkbox" id="b_id" name="b_id" onclick="setAllState()" value="'+l.get(i).s_site_id+'"><label  >'+l.get(i).s_site_name+'</label></li>';
		}
		$("#b_tr").append(tableTr); 
	}
}
