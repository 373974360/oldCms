var SQRPC = jsonrpc.SQRPC;
var SQModelRPC = jsonrpc.SQModelRPC;
var SQBean = new Bean("com.deya.wcm.bean.appeal.sq.SQBean",true);
var SQAttachment = new Bean("com.deya.wcm.bean.appeal.sq.SQAttachment",true);

//得到诉求目地列表
function getPurposeList(){
	var p_list = jsonrpc.PurposeRPC.getPurposeList();
	p_list = List.toJSList(p_list);
	$("#pur_id").addOptions(p_list,"pur_id","pur_name");
}

//根据业务ID得到关联部门
function getModelDeptMapByMID(model_id)
{
	var map = jsonrpc.SQModelRPC.getModelDeptMapByMID(model_id);
	map = Map.toJSMap(map);
	return map;
}

//根据业务ID得到关联领导人列表
function getModelLeadListByMID(model_id)
{
	var ll = SQModelRPC.getModelLeadListByMID(model_id);
	ll = List.toJSList(ll);
	return ll;
}

//获取查询密码
function getQueryCode(model_id)
{
	$("#query_code").val(SQRPC.getQueryCode(model_id));
}

//保存提交
function fnOK()
{
	//判断是否有上传文件
	if(fileCount > 0)
	{
		jQuery('#uploadify').uploadifyUpload();
	}else
	{
		insertSQ();
	}
	
}

function insertSQ()
{
	var bean = BeanUtil.getCopy(SQBean);	
	$("#sq").autoBind(bean);	
	bean.submit_name = $("#do_dept :selected").text();
	
	if(SQRPC.insertSQ(bean,attBean))
	{
		alert(WCMLang.Add_success);
	}
	else
	{
		alert(WCMLang.Add_fail);
	}
}
