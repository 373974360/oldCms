var MemberManRPC = jsonrpc.MemberManRPC;
var defaultBean;

var MemberConfigBean = new Bean("com.deya.wcm.bean.member.MemberConfigBean",true);

function init()
{
	defaultBean = MemberManRPC.getMemberConfigBean();
	if(defaultBean)
	{
		$("#config_table").autoFill(defaultBean);
	}
}

function savaConfig()
{
	var saveBean = BeanUtil.getCopy(MemberConfigBean);
	$("#config_table").autoBind(saveBean);
	saveBean.config_id = defaultBean.config_id;
	
	saveBean.reg_pro = KE.html("reg_pro");
	saveBean.close_pro = KE.html("close_pro");

	if(MemberManRPC.updateMemberConfigBean(saveBean))
	{
		init();
		top.msgAlert("注册配置"+WCMLang.Add_success);
	}
	else
	{
		top.msgWargin("注册配置"+WCMLang.Add_fail);
	}
}