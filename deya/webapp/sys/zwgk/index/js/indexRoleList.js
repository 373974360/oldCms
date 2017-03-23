var IndexRPC = jsonrpc.IndexRPC;
var indexRoleBean = new Bean("com.deya.wcm.bean.zwgk.index.IndexRoleBean",true);

var id = "id";
var value = "ir_value";
var space = "ir_space";
var is_valid = "is_valid";

function initTable()
{
	beanList = IndexRPC.getRoleList();// 取得索引生成规则列表
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	
	for(var i=0; i<beanList.size(); i++)
	{
		var roleBean = beanList.get(i);
		var str_pre = "cell_" + i +"_";
		$("#"+str_pre+id).val(roleBean.id);
		$("#"+str_pre+value).val(roleBean.ir_value);
		$("#"+str_pre+space).val(roleBean.ir_space);
		
		var box_id  = str_pre+is_valid;
		setCheckBox(box_id, roleBean.is_valid, 1);
	}
}

function updateRoleList()
{
	var list = new List();
	for(var i=0; i<7; i++)
	{
		var str_pre = "cell_" + i +"_";
		var roleBean = BeanUtil.getCopy(indexRoleBean);
	 	roleBean.id = $("#"+str_pre+id).val();
		roleBean.ir_value = $("#"+str_pre+value).val();
		roleBean.ir_space = $("#"+str_pre+space).val();
		var box_id  = str_pre+is_valid;
		roleBean.is_valid = getCheckBox(box_id,1,0);
		list.add(roleBean);
	}

	if(IndexRPC.updateIndexRole(list))
	{
		msgWargin("索引规则"+WCMLang.Set_success);
	}
	else
	{
		msgWargin("索引规则"+WCMLang.Set_fail);
	}
	initTable();
}

function reLoad()
{
	initTable();
}

/** 设置checkbox的选中与否
  * box_id  checkbox框的ID
  * value	checkbox的当前值
  * checked_val	选中checkbox时的值
***/
function setCheckBox(box_id, value, checked_val)
{
	var checked_value = checked_val
	if(value == checked_value)
	{
		$("#"+box_id).attr("checked","checked");
	}
	else
	{
		$("#"+box_id).removeAttr("checked");
	}
}

/** 取得checkbox的值
  * box_id  checkbox框的ID
  * checked_val	checkbox的选中时的值
  * unchecked_val	checkbox的未选中时的值
***/
function getCheckBox(box_id, checked_val, unchecked_val)
{
	if($("#"+box_id).is(':checked'))
	{
		return checked_val;
	}
	else
	{
		return unchecked_val;
	}
}
