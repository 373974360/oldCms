var VideoBean = new Bean("com.deya.wcm.bean.cms.info.VideoBean",true);

// 视频格式类型
var video_form = "asf,avi,mpg,mpeg,mpe,mov,rmvb,wmv,wav,mid,midi,mp3,mpa,mp2,ra,ram,rm,wma,flv,mp4";
// 添加视频新闻操作
function addInfoData()
{
	var bean = BeanUtil.getCopy(VideoBean);	
	$("#info_article_table").autoBind(bean);
	bean.info_content = getV(contentId);
	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}

	// bean.info_content = replace_img_content;	
	// 审核状态设置
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
	
	if(check_videoPath()==0)
	{
		msgAlert("视频不能为空，请上传视频!");
		return;
	}
	if(check_videoPath()==1)
	{
		msgAlert("视频格式不正确，请重新上传!");
		return;
	}
	
	setCateClassToBean(bean);
	if(setGKNumHandlForInsert(bean) == false)
	{
		return;
	}
	publicSaveInfoEvent(bean,"video","insert");	
}

// 修改视频新闻操作
function updateInfoData()
{
	var bean = BeanUtil.getCopy(VideoBean);	
	$("#info_article_table").autoBind(bean);
	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}
	bean.info_content = getV(contentId);
	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);
	if(bean.info_status == "8" || bean.info_status == 8){
		bean.step_id = 100;
	}else{
        bean.step_id = $("#step_id").val();
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
	
	// 视频的格式和非空检查
	if(check_videoPath()==0)
	{
		msgAlert("视频不能为空，请上传视频!");
		return;
	}
	if(check_videoPath()==1)
	{
		msgAlert("视频格式不正确，请重新上传!");
		return;
	}
    var info_level = $(":radio[name='info_level'][checked]").val();
    bean.info_level = (info_level == null ? "B" : info_level);
    var isIpLimit = $(":radio[name='isIpLimit'][checked]").val();
    bean.isIpLimit = (isIpLimit == null ? "0" : isIpLimit);
	setCateClassToBean(bean);
	publicSaveInfoEvent(bean,"video","update");		
}

// 检查视频是否为空
function check_videoPath()
{
	if($("#video_path").val().length == 0)
	{
		return 0; // 视频为空，没有上传视频
	}
	if(!check_form())
	{
		return 1; // 视频格式不正确
	}
	return 9;
}

// 检查视频格式
function check_form()
{
	var path = $("#video_path").val();
	var lt = path.split(".");
	
	if(lt.length == 0)
	{
		return false;
	}
	var suffix = lt[lt.length-1];
	if( video_form.indexOf(suffix) == -1 )
	{
		return false;
	}
	return true;
}

