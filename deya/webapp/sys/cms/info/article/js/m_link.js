var InfoBean = new Bean("com.deya.wcm.bean.cms.info.GKInfoBean",true);



function addInfoData()
{	
	var bean = BeanUtil.getCopy(InfoBean);	
	$("#info_article_table").autoBind(bean);
	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}	
	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);
    var up_dtime = $("#up_dtime").val();
    if (up_dtime != null && up_dtime != '') {
        bean.info_status = 4;
    }

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

	publicSaveInfoEvent(bean,"link","insert");		
}
//修改
function updateInfoData()
{	
	var bean = BeanUtil.getCopy(InfoBean);	
	$("#info_article_table").autoBind(bean);

	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}	

	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);
	if(bean.info_status == "8" || bean.info_status == 8){
		bean.step_id = 100;
	}else{
		bean.step_id = 1;
	}
	setCateClassToBean(bean);
	
	publicSaveInfoEvent(bean,"link","update");	
	
}

function closePage(){
	top.CloseModalWindow();
}