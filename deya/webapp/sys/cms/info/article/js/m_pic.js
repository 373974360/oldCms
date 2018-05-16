var PicBean = new Bean("com.deya.wcm.bean.cms.info.PicBean",true);
var PicItemBean = new Bean("com.deya.wcm.bean.cms.info.PicItemBean",true);

function addPicInfo()
{
	var bean = BeanUtil.getCopy(PicBean);	
	$("#info_article_table").autoBind(bean);
	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}
	if($("#add_pic_table img").length == 0)
	{
		msgAlert("图片不能为空，请选择图片");
		return;
	}
	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);

    var info_level = $(":radio[name='info_level'][checked]").val();
    bean.info_level = (info_level == null ? "B" : info_level);
    var isIpLimit = $(":radio[name='isIpLimit'][checked]").val();
    bean.isIpLimit = (isIpLimit == null ? "0" : isIpLimit);

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
	infoNextId = 0;
	if(infoIdGoble == 0){
		infoNextId = InfoBaseRPC.getInfoId();
	}else{
		infoNextId = infoIdGoble;
	}

	no1 = infoNextId;
	bean.id = infoNextId;
	bean.info_id = infoNextId;
	bean.item_list = getItemList(bean.info_id);
	
	setCateClassToBean(bean);
	if(setGKNumHandlForInsert(bean) == false)
	{
		return;
	}

	publicSaveInfoEvent(bean,"pic","insert");
}

function updatePicInfo()
{
	var bean = BeanUtil.getCopy(PicBean);	
	$("#info_article_table").autoBind(bean);
	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}
	if($("#add_pic_table img").length == 0)
	{
		msgAlert("图片不能为空，请选择图片");
		return;
	}
	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);
	if(bean.info_status == "8" || bean.info_status == 8){
		bean.step_id = 100;
	}else{
        bean.step_id = $("#step_id").val();
	}
	bean.info_id = infoid;
	bean.item_list = getItemList(bean.info_id);
    var info_level = $(":radio[name='info_level'][checked]").val();
    bean.info_level = (info_level == null ? "B" : info_level);
    var isIpLimit = $(":radio[name='isIpLimit'][checked]").val();
    bean.isIpLimit = (isIpLimit == null ? "0" : isIpLimit);

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
	publicSaveInfoEvent(bean,"pic","update");	
}

function getItemList(info_id)
{
	var item_list = new List();
	$("#add_pic_table tr").each(function(i){
		var ib = BeanUtil.getCopy(PicItemBean);
		ib.info_id = info_id;
		ib.pic_title = $(this).find("input").val();
		ib.pic_path = $(this).find("img").attr("src");		
		ib.pic_note = $(this).find("textarea").val();
		ib.pic_sort = i+1;
		if(i == 0)
		{
			//把详细内容写入到第一个对象中
			//ib.pic_content = KE.html("pic_content");
			ib.pic_content = getV(contentId);
		}
		item_list.add(ib);
	});
	return item_list;
}