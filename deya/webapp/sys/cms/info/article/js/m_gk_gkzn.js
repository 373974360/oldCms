var ArticleBean = new Bean("com.deya.wcm.bean.cms.info.ArticleBean",true);

function getV(){
	return KE.html(contetnid);
}

function setV(v){
	KE.html(contetnid, v);
}

//判断文章内容中的图片是否有别的网站的图片
function checkLocalImages(content)
{
	replace_img_content = content;
	var re = new RegExp(/<img.*?src.*?=.*?(.*?)>/ig);
	var r = replace_img_content.match(re);
	if(r != null && r.length > 0)
	{		
		var check_id = MateInfoRPC.getUploadSecretKey();
		check_id = check_id.replace(/#/ig,"cicro");
		for(var i=0;i<r.length;i++)
		{
			var src = r[i].replace(/<img[\s]*.*?src=\"(.*?)[\"](.*)/ig,"$1");	
			old_remote_img = src;
			//判断是否是本地的图片			
			if(src.indexOf("/") != 0 && src.indexOf(jsonrpc.SiteDomainRPC.getSiteDomainBySiteID(rela_site_id)) == -1 && src.indexOf(imgDomain) == -1)
			{		
				img_total += 1;
				$.getJSON(imgDomain+"/servlet/RemoteImgSer?src="+src+"&user_id="+user_id+"&site_id="+rela_site_id+"&sid="+check_id+"&jsonp=?", function(data){
				 			 
				});
			}
		}
	}else
	{
		is_upload_remote_img = true;
		$("#addButton").click();
	}
}

//添加Info
var old_remote_img = "";//需要替换的
var replace_img_content = "";//替换图片后的内容变量
var is_upload_remote_img = false;//是否上传成功标识
var img_total = 0;//需要上传的总数
var already_remote_count = 0;//已上传的总数
function addInfoData()
{	
	//判断是否需要上传远程图片
	if($("#is_remote").is(':checked'))
	{//判断是否已上传，没有话执行上传函数
		if(is_upload_remote_img == false)
		{
			checkLocalImages(getV());		
			return;
		}
	}else
	{
		replace_img_content = getV();
	}

	var bean = BeanUtil.getCopy(ArticleBean);	
	$("#info_article_table").autoBind(bean);
	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}

	bean.info_content = replace_img_content;	
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
	
	publicSaveInfoEvent_GKZN(bean,"article","insert");	
}
//修改
function updateInfoData()
{
	//判断是否需要上传远程图片
	if($("#is_remote").is(':checked'))
	{//判断是否已上传，没有话执行上传函数
		if(is_upload_remote_img == false)
		{
			checkLocalImages(getV());		
			return;
		}
	}else
	{
		replace_img_content = getV();
	}
	var bean = BeanUtil.getCopy(ArticleBean);	
	$("#info_article_table").autoBind(bean);

	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}
	bean.info_content = replace_img_content;
	
	if(cid == 10 || cid == 11)
	{//公开指南的时候，需要修改发布状态
		var st = $(":radio[name='info_status'][checked]").val();
		bean.info_status = (st == null ? "2" : st);
	}else
	{
		bean.info_status = defaultBean.info_status;
	}
	publicSaveInfoEvent_GKZN(bean,"article","update");	
}

//公用保存处理事件 特殊的，用于指定栏目
function publicSaveInfoEvent_GKZN(bean,model_ename,save_type)
{
	var bool = false;
	if(save_type == "update")
	{
		if(update_dtTimeIsCorrect(bean) == false)
		{
			return;
		}	
		bool = ModelUtilRPC.update(bean,model_ename);
	}
	else
	{
		if(Add_dtTimeIsCorrect(bean) == false)
		{
			return;
		}
		bool = ModelUtilRPC.insert(bean,model_ename);
	}

	if(bool)
	{		
		msgAlert("信息"+WCMLang.Add_success);
		if(topnum != 0)//表示是从列表页点过来的
		{
			gotoListPage(bean);
		}else
			window.location.reload();
	}
	else
	{
		msgWargin("信息"+WCMLang.Add_fail);
	}
}