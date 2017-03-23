var MemberManRPC = jsonrpc.MemberManRPC;

var MemberCategoryBean = new Bean("com.deya.wcm.bean.member.MemberCategoryBean",true);
var defaultBean;
var val=new Validator();

function initCategoryBean()
{
	if(type == "update")
	{
		defaultBean = MemberManRPC.getMemberCategoryByID(mcat_id);
		$("#cate_table").autoFill(defaultBean);
		
		$("#saveBtn").click(updateMemberCategory);
	}
	else
	{
		$("#saveBtn").click(addMemberCategory);
	}
}

// 添加会员分类信息-保存事件
function addMemberCategory()
{
	if(!checkInfo())
	{
		return;
	}
	var addBean = BeanUtil.getCopy(MemberCategoryBean);
	addBean.mcat_name = $("#mcat_name").val();
	addBean.mcat_memo = $("#mcat_memo").val();
	if(MemberManRPC.insertMemberCategory(addBean))
	{
		msgAlert("会员分类信息"+WCMLang.Add_success);
		getCurrentFrameObj().reloadCategoryList();
		CloseModalWindow();
	}
	else
	{
		msgWargin("会员分类信息"+WCMLang.Add_fail);
		return;
	}
}

// 修改会员分类信息-保存事件
function updateMemberCategory()
{
	if(!checkInfo())
	{
		return;
	}
	var updateBean = BeanUtil.getCopy(defaultBean);
	updateBean.mcat_name = $("#mcat_name").val();
	updateBean.mcat_memo = $("#mcat_memo").val();
	if(MemberManRPC.updateMemberCategory(updateBean))
	{
		msgAlert("会员分类信息"+WCMLang.Set_success);
		getCurrentFrameObj().reloadCategoryList();
		CloseModalWindow();
	}
	else
	{
		msgWargin("会员分类信息"+WCMLang.Set_fail);
		return;
	}
}

function checkInfo()
{
	isFocus = true;
	val.check_result = true;
	
	$("#mcat_name").blur();
	$("#mcat_memo").blur();
	
	/*
	if($("#mcat_name").val().length < 4)
	{
		val.showError("mcat_name","会员分类名称不能少于4个字符");
		return false;
	}
	*/
	
	if(!val.getResult())
	{
		return false;
	}
	return true;
}