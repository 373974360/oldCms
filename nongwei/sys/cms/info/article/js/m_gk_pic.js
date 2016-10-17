var GKPicBean = new Bean("com.deya.wcm.bean.cms.info.GKPicBean",true);
var PicItemBean = new Bean("com.deya.wcm.bean.cms.info.PicItemBean",true);


// 视频格式类型
var pic_form = "asf,avi,mpg,mpeg,mpe,mov,rmvb,wmv,wav,mid,midi,mp3,mpa,mp2,ra,ram,rm,wma,flv,mp4";
function addInfoData()
{	
	var bean = BeanUtil.getCopy(GKPicBean);	
	$("#info_article_table").autoBind(bean);
	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}
	if($("#add_pic_table img").length == 0)
	{
		top.msgAlert("图片不能为空，请选择图片");
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
	bean.item_list = getItemList(bean.info_id);	
	
	setCateClassToBean(bean);
	if(setGKNumHandlForInsert(bean) == false)
	{
		return;
	}
	
	publicSaveInfoEvent(bean,"gkpic","insert");
	
}
//修改
function updateInfoData()
{	
	var bean = BeanUtil.getCopy(GKPicBean);	
	$("#info_article_table").autoBind(bean);

	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	} 
	if($("#add_pic_table img").length == 0)
	{
		top.msgAlert("图片不能为空，请选择图片");
		return;
	}

	bean.info_status = defaultBean.info_status;
	bean.item_list = getItemList(bean.info_id);
	
	setCateClassToBean(bean);
	//修改的时候不用判断索引码，重新生成的话，如果有重复的不会修改原索引码
	publicSaveInfoEvent(bean,"gkpic","update");
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
			ib.pic_content = KE.html("pic_content");
		}
		item_list.add(ib);
	});
	return item_list;
}
