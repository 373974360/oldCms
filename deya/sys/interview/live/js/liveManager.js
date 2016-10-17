var SubjectRPC = jsonrpc.SubjectRPC;
var ChatRPC = jsonrpc.ChatRPC;
var subjectActor = new Bean("com.deya.wcm.bean.interview.SubjectActor",true);
var subjectBean = new Bean("com.deya.wcm.bean.interview.SubjectBean",true);
var subjectCategory = new Bean("com.deya.wcm.bean.interview.SubjectCategory",true);
var ChatBean = new Bean("com.deya.wcm.bean.interview.ChatBean",true);
var GuestBean = new Bean("com.deya.wcm.bean.interview.GuestBean",true);
var SubResouse = new Bean("com.deya.wcm.bean.interview.SubjectResouse",true);

var user_name = "";
var user_id = "";
var beanList = null;
var table = new Table();	
table.table_name = "pro_table";	
/********** 禁言列表 开始 ************/
function initTable(){		
		var colsMap = new Map();
		var colsList = new List();	
		
		colsList.add(setTitleClos("nick_name","禁言用户","40%","20px","",""));//英文名，显示名，宽，高，样式名，点击事件　
		colsList.add(setTitleClos("ip","IP","60%","","",""));	
		
		table.setColsList(colsList);
		table.setAllColsList(colsList);		
		table.checkBox = false;
		table.enableSort=false;//禁用表头排序
		table.onSortChange = showList;
		table.show("table");//里面参数为外层div的id
	}

	function showList(){		

		beanList = ChatRPC.getProhibitUsers(sub_id);//第一个参数为站点ID，暂时默认为空		
		beanList = List.toJSList(beanList);//把list转成JS的List对象		
		
		//alert(toJSON(beanList.get(0)));//然后就可以调用get(),add()等方法了
		//alert(beanList);
		curr_bean = null;		
		table.setBeanList(beanList,"td_list");//设置列表内容的样式
		table.show();	

		Init_InfoTable(table.table_name);
	}
/********** 禁言列表 结束 ************/