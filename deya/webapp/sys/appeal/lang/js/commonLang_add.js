var CommonLangRPC = jsonrpc.CommonLangRPC;
var CommonLangBean = new Bean("com.deya.wcm.bean.appeal.lang.CommonLangBean",true);

var val = new Validator();
var defaultBean;

function iniPage()
{
	$(".customLable li").click(function(){
        insertV("ph_content",$(this).attr("title"));
	});
	if(type == "update")
	{
		fillBean();
		$("#btn1").click(saveUpdate);
	}
	else
	{
		$("#ph_type").val(ph_type);
		$("#btn1").click(saveAdd);
	}
}
// 初始化填充数据
function fillBean()
{
	defaultBean =  CommonLangRPC.getCommonLangByID(ph_id);
	$("#add_table").autoFill(defaultBean);
    setV("ph_content",defaultBean.ph_content);
}
// 添加常用语保存
function saveAdd()
{
	if(!check_commonLang())
	{
		return;
	}
	
	var addCommomLang = BeanUtil.getCopy(CommonLangBean);
	$("#add_table").autoBind(addCommomLang);
	addCommomLang.ph_content = getV("ph_content");
	if(CommonLangRPC.insertCommonLang(addCommomLang))
	{	
		msgAlert("常用语"+WCMLang.Add_success);
		backCommonLang();
	}
	else
	{
		msgWargin("常用语"+WCMLang.Add_fail);
	}
		
}
// 修改常用语保存
function saveUpdate()
{
	if(!check_commonLang())
	{
		return;
	}
	var updateCommomLang = BeanUtil.getCopy(CommonLangBean);
	$("#add_table").autoBind(updateCommomLang);
	updateCommomLang.ph_id = defaultBean.ph_id;
	updateCommomLang.sort_id = defaultBean.sort_id;
	updateCommomLang.ph_content = getV("ph_content");
	if(CommonLangRPC.updateCommonLang(updateCommomLang))
	{
		msgAlert("常用语"+WCMLang.Set_success);
		backCommonLang();
	}
	else
	{
		msgWargin("常用语"+WCMLang.Set_fail);
	}
}

// 返回常用语列表页
function backCommonLang()
{
	window.location.href  = "/sys/appeal/lang/commonLangList.jsp?ph_type="+ph_type;
}

// 输入验证常用语内容
function check_content()
{
	var str = getV("ph_content");
	if( str.trim() == "")
	{
		val.check_result = false;
		return ;
	}
}

// 页面验证
function check_commonLang()
{
	isFocus = true;
	val.check_result = true;
	
	$("#ph_title").blur();
	
	if(!val.getResult())
	{
		return false;
	}
	
	if( getV("ph_content").trim() == "")
	{
		msgAlert("【详细内容】项不能为空");
		return ;
	}
	return true;
}