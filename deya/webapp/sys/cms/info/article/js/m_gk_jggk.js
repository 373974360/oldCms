var GKFjggkBean = new Bean("com.deya.wcm.bean.cms.info.GKFjggkBean",true);

function getV(contetnid){
	return KE.html(contetnid);
}

function setV(contetnid,v){
	KE.html(contetnid, v);
}

//添加Info
var old_remote_img = "";//需要替换的
var replace_img_content = "";//替换图片后的内容变量
var is_upload_remote_img = false;//是否上传成功标识
var img_total = 0;//需要上传的总数
var already_remote_count = 0;//已上传的总数
function addInfoData()
{	
	var bean = BeanUtil.getCopy(GKFjggkBean);	
	$("#gk_f_jggk_table").autoBind(bean);
	if(!standard_checkInputInfo("gk_f_jggk_table"))
	{
		return;
	}

	bean.gk_nsjg = getV("gk_nsjg");
	bean.gk_gzzz = getV("gk_gzzz");
	bean.gk_xmzw = getV("gk_xmzw");
		
	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);
	infoNextId = 0;
	if(infoIdGoble == 0){
		infoNextId = InfoBaseRPC.getInfoId();
	}else{
		infoNextId = infoIdGoble;
	}
	no1 = infoNextId;
	bean.id = infoNextId;
	bean.info_id = infoNextId;

	setCateClassToBean(bean);
	if(setGKNumHandlForInsert(bean) == false)
	{
		return;
	}
	publicSaveInfoEvent(bean,"gkfjggk","insert");
	
}
//修改
function updateInfoData()
{
	var bean = BeanUtil.getCopy(GKFjggkBean);	
	$("#gk_f_jggk_table").autoBind(bean);
	

	if(!standard_checkInputInfo("gk_f_jggk_table"))
	{
		return;
	}  
	
	bean.gk_nsjg = getV("gk_nsjg");
	bean.gk_gzzz = getV("gk_gzzz");
	bean.gk_xmzw = getV("gk_xmzw");
	
	bean.info_status = defaultBean.info_status;

	setCateClassToBean(bean);
	//修改的时候不用判断索引码，重新生成的话，如果有重复的不会修改原索引码
	publicSaveInfoEvent(bean,"gkfjggk","update");
}

