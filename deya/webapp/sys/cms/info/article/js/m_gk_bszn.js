var GKFbsznBean = new Bean("com.deya.wcm.bean.cms.info.GKFbsznBean",true);

//添加Info
var old_remote_img = "";//需要替换的
var replace_img_content = "";//替换图片后的内容变量
var is_upload_remote_img = false;//是否上传成功标识
var img_total = 0;//需要上传的总数
var already_remote_count = 0;//已上传的总数
function addInfoData()
{	
	var bean = BeanUtil.getCopy(GKFbsznBean);	
	$("#gk_f_bszn_table").autoBind(bean);
	if(!standard_checkInputInfo("gk_f_bszn_table"))
	{
		return;
	}

	bean.gk_sxyj = getV(gk_sxyj);
	bean.gk_bltj = getV(gk_bltj);
	bean.gk_blsx = getV(gk_blsx);
	bean.gk_sfbz = getV(gk_sfbz);
	bean.gk_bllc = getV(gk_bllc);
	//bean.gk_cclx = getV(gk_cclx);
    //bean.gk_sfyj = getV(gk_sfyj);
    bean.gk_bljg = getV(gk_bljg);
    bean.gk_bgxz = getV(gk_bgxz);


	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);

    var info_level = $(":radio[name='info_level'][checked]").val();
    bean.info_level = (info_level == null ? "B" : info_level);
    var isIpLimit = $(":radio[name='isIpLimit'][checked]").val();
    bean.isIpLimit = (isIpLimit == null ? "0" : isIpLimit);
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
	publicSaveInfoEvent(bean,"gkfbszn","insert");
	
}
//修改
function updateInfoData()
{
	var bean = BeanUtil.getCopy(GKFbsznBean);	
	$("#gk_f_bszn_table").autoBind(bean);
	

	if(!standard_checkInputInfo("gk_f_bszn_table"))
	{
		return;
	}

    bean.gk_sxyj = getV(gk_sxyj);
    bean.gk_bltj = getV(gk_bltj);
    bean.gk_blsx = getV(gk_blsx);
    bean.gk_sfbz = getV(gk_sfbz);
    bean.gk_bllc = getV(gk_bllc);
    //bean.gk_cclx = getV(gk_cclx);
    //bean.gk_sfyj = getV(gk_sfyj);
    bean.gk_bljg = getV(gk_bljg);
    bean.gk_bgxz = getV(gk_bgxz);
	
	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);
	if(bean.info_status == "8" || bean.info_status == 8){
		bean.step_id = 100;
	}else{
		bean.step_id = 0;
	}
    var info_level = $(":radio[name='info_level'][checked]").val();
    bean.info_level = (info_level == null ? "B" : info_level);
    var isIpLimit = $(":radio[name='isIpLimit'][checked]").val();
    bean.isIpLimit = (isIpLimit == null ? "0" : isIpLimit);
	setCateClassToBean(bean);
	//修改的时候不用判断索引码，重新生成的话，如果有重复的不会修改原索引码
	publicSaveInfoEvent(bean,"gkfbszn","update");
}

