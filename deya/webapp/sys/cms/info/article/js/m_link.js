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
	infoNextId = 0;
	if(infoIdGoble == 0){
		infoNextId = InfoBaseRPC.getInfoId();
	}else{
		infoNextId = infoIdGoble;
	}
	no1 = infoNextId;
	bean.id = infoNextId;
	bean.info_id = infoNextId;

    var publish_source = "";
    $("input[name='publish_source']:checked").each(function(){
        publish_source += "," + $(this).val() ;
    });
    if(publish_source.length > 1){
        publish_source = publish_source.substr(1);
    }else{
        alert("发布渠道不能为空！");
        return;
    }
    bean.publish_source = publish_source;
	
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
		bean.step_id = 0;
	}

    var publish_source = "";
    $("input[name='publish_source']:checked").each(function(){
        publish_source += "," + $(this).val() ;
    });
    if(publish_source.length > 1){
        publish_source = publish_source.substr(1);
    }else{
        alert("发布渠道不能为空！");
        return;
    }
    bean.publish_source = publish_source;
	setCateClassToBean(bean);
	
	publicSaveInfoEvent(bean,"link","update");	
	
}

function closePage(){
	CloseModalWindow();
}