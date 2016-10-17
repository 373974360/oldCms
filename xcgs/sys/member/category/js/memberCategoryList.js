var MemberManRPC = jsonrpc.MemberManRPC;

var MemberCategoryBean = new Bean("com.deya.wcm.bean.member.MemberCategoryBean",true);

var tp = new TurnPage();
var beanList = null;
var table = new Table();	
table.table_name = "member_category_table";
var con_m = new Map();

function initTable(){
	var colsMap = new Map();	
	var colsList = new List();	
	
	colsList.add(setTitleClos("mcat_name","会员分类名称","","","",""));//英文名，显示名，宽，高，样式名，点击事件　
	//colsList.add(setTitleClos("mcat_id","会员分类ID","","","",""));
	colsList.add(setTitleClos("mcat_memo","会员分类描述","300px","","",""));
	colsList.add(setTitleClos("sort_col","排序","100px","","",""));
	
	table.setColsList(colsList);
	table.setAllColsList(colsList);				
	table.enableSort=false;//禁用表头排序
	table.onSortChange = showList;
	table.show("table");//里面参数为外层div的id
}

function reloadCategoryList()
{
	showList();
	showTurnPage();
}

function showList(){
			
	var sortCol = table.sortCol;
	var sortType = table.sortType;		
	if(sortCol == "" || sortCol == null)
	{
		sortCol = "id";
		sortType = "desc";
	}

	beanList = MemberManRPC.getMemberCategoryList();
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	tp.total = beanList.size();
	table.setBeanList(beanList,"td_list");//设置列表内容的样式
	table.show("table");
	
	table.getCol("mcat_name").each(function(i){	
		if(i>0)
		{
			var id = beanList.get(i-1).mcat_id;
			$(this).html('<a href="javascript:top.OpenModalWindow(\'会员分类信息\',\'/sys/member/category/memberCategory_add.jsp?type=update&mcat_id='+id +'\',450,245)">'+beanList.get(i-1).mcat_name+'</a>');	
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

function showTurnPage(){			
	tp.show("turn","simple");	
	tp.onPageChange = showList;
}

// 保存会员分类排序
function sortMemberCategorySort()
{
	var selectIDS = table.getAllCheckboxValue("mcat_id");
	if(MemberManRPC.saveMemberCategorySort(selectIDS))
	{
		top.msgAlert(WCMLang.Sort_success);
	}else
	{
		top.msgWargin(WCMLang.Sort_fail);
	}
}

// 添加会员分类信息
function addMemberCategory()
{
	top.OpenModalWindow("新建会员分类信息","/sys/member/category/memberCategory_add.jsp?type=add&mcat_id=",460,245);
}

// 修改会员分类信息
function updateMemberCategory()
{
	var id = table.getSelecteCheckboxValue("mcat_id");
	top.OpenModalWindow("修改会员分类信息","/sys/member/category/memberCategory_add.jsp?type=update&mcat_id="+id,460,245);
}

// 删除会员分类信息
function deleteMemberCategory()
{
	var ids = table.getSelecteCheckboxValue("mcat_id");
	var mp = new Map();
	mp.put("mcat_ids", ids);
	if(MemberManRPC.deleteMemberCategory(mp))
	{
		top.msgAlert("会员分类信息"+WCMLang.Delete_success);
		reloadCategoryList();
	}
	else
	{
		top.msgWargin("会员分类信息"+WCMLang.Delete_fail);
	}
}