var MateFolderRPC = jsonrpc.MateFolderRPC;
var MateInfoRPC = jsonrpc.MateInfoRPC;
var MateInfoBean = new Bean("com.deya.wcm.bean.material.MateInfoBean",true);
var user_id = jsonrpc.UserLoginRPC.getUserBySession().user_id;
var pic_url = "";
var is_press_bak;
var press_osition_bak;

/**
*　公用站点上传素材方法
* @param input_name 上传按钮名称
* @param is_auto 是否自动上传　true or false　如果为false,需要手动设置上传的参数
* @param is_multi 是否可
* @param selectAfterHandl　选择图片后触发的事件
* @param is_press 是否生成水印　0 or 1 0不生成　1生成
* @param press_osition 生成水印位置　1～9
* @param site_id 站点ID
* @param uploadAfterHandl 上传完后处理的事件，一般返回图片的真实地址
*/
function publicUploadButtomLoad(input_name,is_auto,is_multi,selectAfterHandl,is_press,press_osition,site_id,uploadAfterHandl)
{
	var imgDomain = MateInfoRPC.getImgDomain(site_id);
	$("#"+input_name).uploadify({//初始化函数
	    'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :''+imgDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :50,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :'jpg,jpeg,gif,png,bmp',//上传文件类型说明
		'fileExt' :'*.jpg;*.jpeg;*.gif;*.png;*.bmp;', //控制可上传文件的扩展名,启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':31457280,//文件上传的大小限制，单位是字节
	    'auto' :is_auto,//是否自动上传
	    'multi' :is_multi,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			if(fileObj.size > 31457280){
				alert("附件过大，不允许上传！");
				return;
			}
			else
			{
				if(is_auto)
				{
					if(is_press_bak != null)
					{
						is_press = is_press_bak;
					}
					if(press_osition_bak != null)
						press_osition = press_osition_bak;

					$("#"+input_name).uploadifySettings('scriptData',{'is_press':is_press,'press_osition':press_osition,'site_id':site_id,'sid':MateInfoRPC.getUploadSecretKey()});
				}else
				{
					if(selectAfterHandl != "" && selectAfterHandl != null)
						eval(selectAfterHandl+"()");
				}	
			}
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
			var arr_id = MateInfoRPC.getArrIdFromTable();
			var att_path = "";  
			var att_ename = "";
			var hd_url ="";
			var thumb_url =""; 
			var tt_att_ename = "";
			var tt_thumb_url = "";
			var tmpPicLi  = "";
			var objPath =  jQuery.parseJSON(response);
			var oldname = fileObj.name;
			att_path = objPath.att_path;
		    att_ename = objPath.att_ename;
		    hd_url = objPath.hd_url;
		    thumb_url = objPath.thum_url;
		   
			pic_url = imgDomain + att_path + att_ename;
			addMateInfo(arr_id,0,site_id,att_path,hd_url,thumb_url,att_ename,oldname,fileObj.size);	
			
			if(uploadAfterHandl != "" && uploadAfterHandl != null)
			{
				if(uploadAfterHandl.indexOf("(") == -1)
					eval(""+uploadAfterHandl+"('"+pic_url+"','"+oldname+"')");
				else
				{
					uploadAfterHandl = uploadAfterHandl.replace("pic_url",pic_url);
					
					eval(uploadAfterHandl);
				}
			}
 	    },
 	   	'onAllComplete': function(event,data){
 	    	
 	    }
   });
}

function publicUploadButtomAllFile(input_name,is_auto,is_multi,selectAfterHandl,is_press,press_osition,site_id,fileDesc,fileExt,uploadAfterHandl)
{
	var imgDomain = MateInfoRPC.getImgDomain(site_id);
	$("#"+input_name).uploadify({//初始化函数
	    'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :''+imgDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :50,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :fileDesc,//上传文件类型说明
		'fileExt' :fileExt, //控制可上传文件的扩展名,启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':31457280,//文件上传的大小限制，单位是字节
	    'auto' :is_auto,//是否自动上传
	    'multi' :is_multi,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			if(fileObj.size > 31457280){
				alert("附件过大，不允许上传！");
				return;
			}
			else
			{
				if(is_auto)
				{
					if(is_press_bak != null)
					{
						is_press = is_press_bak;
					}
					if(press_osition_bak != null)
						press_osition = press_osition_bak;

					$("#"+input_name).uploadifySettings('scriptData',{'is_press':is_press,'press_osition':press_osition,'site_id':site_id,'sid':MateInfoRPC.getUploadSecretKey()});
				}else
				{
					if(selectAfterHandl != "" && selectAfterHandl != null)
						eval(selectAfterHandl+"()");
				}		
			}
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
			var arr_id = MateInfoRPC.getArrIdFromTable();
			var att_path = "";  
			var att_ename = "";
			var hd_url ="";
			var thumb_url =""; 
			var tt_att_ename = "";
			var tt_thumb_url = "";
			var tmpPicLi  = "";
			var objPath =  jQuery.parseJSON(response);
			var oldname = fileObj.name;
			att_path = objPath.att_path;
		    att_ename = objPath.att_ename;
		    hd_url = objPath.hd_url;
		    thumb_url = objPath.thum_url;
		   
			pic_url = imgDomain + att_path + att_ename;
			addMateInfo(arr_id,0,site_id,att_path,hd_url,thumb_url,att_ename,oldname,fileObj.size);	
			
			if(uploadAfterHandl != "" && uploadAfterHandl != null)
			{
				if(uploadAfterHandl.indexOf("(") == -1)
					eval(""+uploadAfterHandl+"('"+pic_url+"','"+oldname+"')");
				else
				{
					uploadAfterHandl = uploadAfterHandl.replace("pic_url",pic_url);
					
					eval(uploadAfterHandl);
				}
			}
 	    },
 	   	'onAllComplete': function(event,data){
 	    	
 	    }
   });
}


function publicUploadMediaButtomLoad(input_name,is_auto,selectAfterHandl,site_id,uploadAfterHandl)
{
	var imgDomain = MateInfoRPC.getImgDomain(site_id);
	var sizeLimit = 524288000;
	$("#"+input_name).uploadify({//初始化函数
	    'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :''+imgDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :50,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :'asf,avi,mpg,mpeg,mpe,mov,rmvb,wmv,wav,mid,midi,mp3,mpa,mp2,mp4,flv,ra,ram,rm,wma',//上传文件类型说明
		'fileExt' :'*.asf;*.avi;*.mpg;*.mpeg;*.mpe;*.mov;*.rmvb;*.wmv;*.wav;*.mid;*.midi;*.mp3;*.mpa;*.mp2;*.ra;*.ram;*.rm;*.wma;*.mp4;*.flv', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':sizeLimit,//文件上传的大小限制，单位是字节
	    'auto' :is_auto,//是否自动上传
	    'multi' :false,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			if(fileObj.size > sizeLimit){
				alert("附件过大，不允许上传！");
				return;
			}
			else
			{
				if(is_auto)
				{
					$("#"+input_name).uploadifySettings('scriptData',{'is_press':0,'press_osition':5,'site_id':site_id,'sid':MateInfoRPC.getUploadSecretKey()});
				}else
				{
					if(selectAfterHandl != "" && selectAfterHandl != null)
						eval(selectAfterHandl+"()");
				}	
			}
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
			var arr_id = MateInfoRPC.getArrIdFromTable();
			var att_path = "";  
			var att_ename = "";
			var hd_url ="";
			var thumb_url =""; 
			var tt_att_ename = "";
			var tt_thumb_url = "";
			var tmpPicLi  = "";
			var objPath =  jQuery.parseJSON(response);
			var oldname = fileObj.name;
			att_path = objPath.att_path;
		    att_ename = objPath.att_ename;
		    hd_url = objPath.hd_url;
		    thumb_url = objPath.thum_url;
		   
			pic_url = imgDomain + att_path + att_ename;
			addMateInfo(arr_id,0,site_id,att_path,hd_url,thumb_url,att_ename,oldname,fileObj.size);						
 	    },
 	   	'onAllComplete': function(event,data){
 	    	if(uploadAfterHandl != "" && uploadAfterHandl != null)
			{				
				eval(""+uploadAfterHandl+"('"+pic_url+"')");
			}
 	    }
   });
}

function publicUploadFlashButtomLoad(input_name,is_auto,selectAfterHandl,site_id,uploadAfterHandl)
{
	var imgDomain = MateInfoRPC.getImgDomain(site_id);
	var sizeLimit = 31457280;
	$("#"+input_name).uploadify({//初始化函数
	    'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :''+imgDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :50,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :'swf,flv',//上传文件类型说明
		'fileExt' :'*.swf;*.flv', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':sizeLimit,//文件上传的大小限制，单位是字节
	    'auto' :is_auto,//是否自动上传
	    'multi' :false,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			if(fileObj.size > sizeLimit){
				alert("附件过大，不允许上传！");
				return;
			}
			else
			{
				if(is_auto)
				{
					$("#"+input_name).uploadifySettings('scriptData',{'is_press':0,'press_osition':5,'site_id':site_id,'sid':MateInfoRPC.getUploadSecretKey()});
				}else
				{
					if(selectAfterHandl != "" && selectAfterHandl != null)
						eval(selectAfterHandl+"()");
				}	
			}
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
			var arr_id = MateInfoRPC.getArrIdFromTable();
			var att_path = "";  
			var att_ename = "";
			var hd_url ="";
			var thumb_url =""; 
			var tt_att_ename = "";
			var tt_thumb_url = "";
			var tmpPicLi  = "";
			var objPath =  jQuery.parseJSON(response);
			var oldname = fileObj.name;
			att_path = objPath.att_path;
		    att_ename = objPath.att_ename;
		    hd_url = objPath.hd_url;
		    thumb_url = objPath.thum_url;
		   
			pic_url = imgDomain + att_path + att_ename;
			addMateInfo(arr_id,0,site_id,att_path,hd_url,thumb_url,att_ename,oldname,fileObj.size);						
 	    },
 	   	'onAllComplete': function(event,data){
 	    	if(uploadAfterHandl != "" && uploadAfterHandl != null)
			{				
				eval(""+uploadAfterHandl+"('"+pic_url+"','"+pic_url+"')");
			}
 	    }
   });
}

function publicUploadFileButtomLoad(input_name,is_auto,is_multi,selectAfterHandl,is_press,press_osition,site_id,uploadAfterHandl)
{
	var imgDomain = MateInfoRPC.getImgDomain(site_id);
	var K_V = new Map();//区取站点素材配置信息
	K_V.put("group", "attachment");
	var temp_site = "";
	if(app_id == "zwgk")
	{
		temp_site = jsonrpc.SiteRPC.getSiteIDByAppID(app_id);//政务公开使用政务公开应用所关联的站点　
		K_V.put("site_id", site_id);
	}
	else
		temp_site = site_id;	
	K_V.put("app_id", "cms");
	var resultMap = MateFolderRPC.getValues(K_V);
	resultMap = Map.toJSMap(resultMap);
	var file_desc = resultMap.get("upload_allow");
	var file_ext = "*."+file_desc.replace(/,/g,";*.")+";";
	var sizeLimit = 31457280;
	$("#"+input_name).uploadify({//初始化函数
	    'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :''+imgDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :50,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :file_desc,//上传文件类型说明
		'fileExt' :file_ext, //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':sizeLimit,//文件上传的大小限制，单位是字节
	    'auto' :is_auto,//是否自动上传
	    'multi' :is_multi,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			if(fileObj.size > sizeLimit){
				alert("附件过大，不允许上传！");
				return;
			}
			else
			{
				if(is_auto)
				{
					$("#"+input_name).uploadifySettings('scriptData',{'is_press':0,'press_osition':5,'site_id':site_id,'sid':MateInfoRPC.getUploadSecretKey()});
				}else
				{
					if(selectAfterHandl != "" && selectAfterHandl != null)
						eval(selectAfterHandl+"()");
				}	
			}	
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
			var arr_id = MateInfoRPC.getArrIdFromTable();
			var att_path = "";  
			var att_ename = "";
			var hd_url ="";
			var thumb_url =""; 
			var tt_att_ename = "";
			var tt_thumb_url = "";
			var tmpPicLi  = "";
			var objPath =  jQuery.parseJSON(response);
			var oldname = fileObj.name;
			att_path = objPath.att_path;
		    att_ename = objPath.att_ename;
		    hd_url = objPath.hd_url;
		    thumb_url = objPath.thum_url;
		   
			pic_url = imgDomain + att_path + att_ename;
			addMateInfo(arr_id,0,site_id,att_path,hd_url,thumb_url,att_ename,oldname,fileObj.size);		
			if(uploadAfterHandl != "" && uploadAfterHandl != null)
			{				
				eval(""+uploadAfterHandl+"('"+pic_url+"','"+oldname+"')");
			}
 	    },
 	   	'onAllComplete': function(event,data){
 	    	
 	    }
   });
}

//添加素材
function addMateInfo(att_id,f_id,site_id,serverUrl,hd_url,thum_url,att_ename,oldName,filesize)
{
	var bean = BeanUtil.getCopy(MateInfoBean);
	bean.att_id = att_id;
	bean.f_id = f_id;
	bean.site_id = site_id;
	bean.user_id = user_id;
	bean.att_path = serverUrl;
	bean.att_cname = oldName;
	bean.hd_url = hd_url;;
	bean.thumb_url = thum_url;
	bean.att_ename = att_ename;
	bean.alias_name = oldName;
	bean.filesize = filesize;
	
	if(MateInfoRPC.insertMateInfoBean(bean))
	{	
		//top.msgAlert("素材信息"+WCMLang.Add_success);	
		//top.CloseModalWindow();
	}
	else
	{
		top.msgWargin("素材信息"+WCMLang.Add_fail);
	}
}


//只上传word和exl文件
function publicUploadDOC(input_name,is_auto,is_multi,selectAfterHandl,is_press,press_osition,site_id,uploadAfterHandl)
{
	var imgDomain = MateInfoRPC.getImgDomain(site_id);
	$("#"+input_name).uploadify({//初始化函数
	    'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :''+imgDomain+'/servlet/UploadFileIfy',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :50,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :'txt,doc,docx,xlsx,xsl',//上传文件类型说明
		'fileExt' :'*.txt;*.doc;*.docx;*.xlsx;*.xsl', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':31457280,//文件上传的大小限制，单位是字节
	    'auto' :is_auto,//是否自动上传
	    'multi' :is_multi,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件
			if(fileObj.size > 31457280){
				alert("附件过大，不允许上传！");
				return;
			}
			else
			{
				if(is_auto)
				{
					$("#"+input_name).uploadifySettings('scriptData',{'is_press':is_press,'press_osition':press_osition,'site_id':site_id,'sid':MateInfoRPC.getUploadSecretKey()});
				}else
				{
					if(selectAfterHandl != "" && selectAfterHandl != null)
						eval(selectAfterHandl+"()");
				}	
			}
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
			var arr_id = MateInfoRPC.getArrIdFromTable();
			var att_path = "";  
			var att_ename = "";
			var hd_url ="";
			var thumb_url =""; 
			var tt_att_ename = "";
			var tt_thumb_url = "";
			var tmpPicLi  = "";
			var objPath =  jQuery.parseJSON(response);
			var oldname = fileObj.name;
			att_path = objPath.att_path;
		    att_ename = objPath.att_ename;
		    hd_url = objPath.hd_url;
		    thumb_url = objPath.thum_url;
		   
			pic_url = imgDomain + att_path + att_ename;
			addMateInfo(arr_id,0,site_id,att_path,hd_url,thumb_url,att_ename,oldname,fileObj.size);	
			
			if(uploadAfterHandl != "" && uploadAfterHandl != null)
			{				
				eval(""+uploadAfterHandl+"('"+pic_url+"')");
			}
 	    },
 	   	'onAllComplete': function(event,data){   	
 	    }
   });

}
var current_folder = "";//保存目录，如果不为空，保存在此目录下,且于模板管理处上传资源
function initTemplateUpLoad()
{
	var sizeLimit = 104857600;
	
	 $("#uploadify").uploadify( {//初始化函数
		'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
		'script' :jsonrpc.SiteRPC.getDefaultSiteDomainBySiteID(site_id)+'/sys/servlet/TemplateResourcesUpload',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
		'folder' :'uploads',//您想将文件保存到的路径
		'queueID' :'fileQueue',//与下面的上传文件列表id对应
		'queueSizeLimit' :100,//上传文件的数量
		'scriptData':{'site_id':site_id},//向后台传的数据
		'fileDesc' :'js,css,gif,jpg,png,jpeg,swf,zip,doc,docx,xls,ppt,pdf',//上传文件类型说明
		'fileExt' :'*.js;*.css;*.gif;*.jpg;*.png;*.jpeg;*.swf;*.zip;*.doc;*.docx;*.xls;*.ppt;*.pdf', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
		'method':'get',//如果向后台传输数据，必须是get
		'sizeLimit':sizeLimit,//文件上传的大小限制，单位是字节
		'auto' :true,//是否自动上传
		'multi' :true,
		'simUploadLimit' :1,//同时上传文件的数量
		'buttonText' :'BROWSE',//浏览按钮图片
		'auto' : true,//点击选择直接上传图片
		'onSelect':function(event, queueID, fileObj){//选择完后触发的事件
			if(fileObj.size > sizeLimit)
			{
				alert("附件太大");
				return;
			}	
			else
			{
				$("#uploadify").uploadifySettings('scriptData',{'custom_folder':current_folder,'site_id':site_id,'sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
			}
		},
		'onSelectOnce':function(event,data){
			fileCount = data.fileCount;			
		},
		'onCancel':function(event,queueId,fileObj,data){
			fileCount = data.fileCount;
		},
		'onComplete': function(event, queueID, fileObj,serverData,response,data) {//当上传完成后的回调函数			
			var objPath =  jQuery.parseJSON(serverData);			
			uploadReturnHandl(objPath.att_path);
		}
   });  
}

function isChina(s){
	var patrn=/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
	if(!patrn.exec(s)){
		return false;
	}
	else{
		return true;
	}
}

function initTemplateUpLoadSingl(input_name,uploadAfterHandl)
{
	var sizeLimit = 31457280;
	
	 $("#"+input_name).uploadify( {//初始化函数
		'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
		'script' :jsonrpc.SiteRPC.getDefaultSiteDomainBySiteID(site_id)+'/sys/servlet/TemplateResourcesUpload',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
		'folder' :'uploads',//您想将文件保存到的路径
		'queueID' :'fileQueue',//与下面的上传文件列表id对应
		'queueSizeLimit' :100,//上传文件的数量
		//'scriptData':{'site_id':site_id},//向后台传的数据
		'fileDesc' :'js,css,gif,jpg,png,jpeg,swf',//上传文件类型说明
		'fileExt' :'*.js;*.css;*.gif;*.jpg;*.png;*.jpeg;*.swf', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
		'method':'get',//如果向后台传输数据，必须是get
		'sizeLimit':sizeLimit,//文件上传的大小限制，单位是字节
		'auto' :true,//是否自动上传
		'multi' :1,
		'simUploadLimit' :1,//同时上传文件的数量
		'buttonText' :'BROWSE',//浏览按钮图片
		'auto' : true,//点击选择直接上传图片
		'onSelect':function(event, queueID, fileObj){//选择完后触发的事件		
			if(fileObj.size > sizeLimit)
			{
				alert("附件太大");
				return;
			}
			else
			{
				$("#"+input_name).uploadifySettings('scriptData',{'site_id':site_id,'sid':jsonrpc.MateInfoRPC.getUploadSecretKey()});
				if(isChina(fileObj.name))
				{
					alert("请不要上传名称带有中文字符的图片");
					return;
				}
			}
		},
		'onSelectOnce':function(event,data){
			fileCount = data.fileCount;			
		},
		'onCancel':function(event,queueId,fileObj,data){
			fileCount = data.fileCount;
		},
		'onComplete': function(event, queueID, fileObj,serverData,response,data) {//当上传完成后的回调函数			
			var objPath =  jQuery.parseJSON(serverData);
			if(uploadAfterHandl != "" && uploadAfterHandl != null)
			{				
				eval(""+uploadAfterHandl+"('"+objPath.att_path+"')");
			}
		}
   });  
}

function obj2str(o){

  var r = [];

  if(typeof o == "string" || o == null) {

    return o;

  }

  if(typeof o == "object"){

    if(!o.sort){

      r[0]="{"

      for(var i in o){

        r[r.length]=i;

        r[r.length]=":";

        r[r.length]=obj2str(o[i]);

        r[r.length]=",";

      }

      r[r.length-1]="}"

    }else{

      r[0]="["

      for(var i =0;i<o.length;i++){

        r[r.length]=obj2str(o[i]);

        r[r.length]=",";

      }

      r[r.length-1]="]"

    }

    return r.join("");

  }

  return o.toString();

}
