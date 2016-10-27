var SiteGroupRPC = jsonrpc.SiteGroupRPC;
var SiteGroupBean = new Bean("com.deya.wcm.bean.control.SiteGroupBean",true);

var val=new Validator();
var defaultBean;

//填充数据
function initData(){
	SiteGroupBean = SiteGroupRPC.getSGroupRoot("0");
	defaultBean = BeanUtil.getCopy(SiteGroupBean);
	//$("#sgroup_table").autoBind(bean);
	$("#sgroup_table").autoFill(defaultBean);
}

//提交
function funOK(){
	if(!standard_checkInputInfo("sgroup_table"))
	{
		return;
	}
	$("#sgroup_table").autoBind(defaultBean);
	
	if(SiteGroupRPC.updateSiteGroup(defaultBean))
	{
		top.msgAlert("站群信息"+WCMLang.Add_success);
	}
	else
	{
		top.msgWargin("站群信息"+WCMLang.Add_fail);
	}
}
