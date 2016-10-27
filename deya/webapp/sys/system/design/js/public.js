/**
*　公用站点上传缩略图方法
* @param input_name 上传按钮名称
* @param uploadAfterHandl 上传完后处理的事件，一般返回图片的真实地址
*/
function publicUploadDesignThumbButtom(input_name,uploadAfterHandl)
{
	$("#"+input_name).uploadify({//初始化函数
	    'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :'/servlet/DesignFileUpload',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :1,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :'jpg,jpeg,gif,png',//上传文件类型说明
		'fileExt' :'*.jpg;*.jpeg;*.gif;*.png;', //控制可上传文件的扩展名,启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':31457280,//文件上传的大小限制，单位是字节
	    'auto' :true,//是否自动上传
	    'multi' :false,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件			
			$("#"+input_name).uploadifySettings('scriptData',{'up_type':'thumb','sid':MateInfoRPC.getUploadSecretKey()});
			
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
			var objPath =  jQuery.parseJSON(response);
			if(uploadAfterHandl != "" && uploadAfterHandl != null)
			{				
				eval(""+uploadAfterHandl+"('"+objPath.att_path+"')");
			}
 	    },
 	   	'onAllComplete': function(event,data){
 	    	
 	    }
   });
}

/**
*　公用站点上传缩略图方法
* @param input_name 上传按钮名称
* @param ename 风格英文名称(保存目录)
* @param uploadAfterHandl 上传完后处理的事件，一般返回图片的真实地址
*/
function publicUploadDesignFileButtom(input_name,ename,uploadAfterHandl)
{
	$("#"+input_name).uploadify({//初始化函数
	    'uploader' :'/sys/js/uploadFile/uploadify.swf',//flash文件位置，注意路径
	    'script' :'/servlet/DesignFileUpload',//后台处理的请求
		'cancelImg' :'/sys/js/uploadFile/cancel.png',//取消按钮图片
		'buttonImg': '/sys/js/uploadFile/upst.gif',
	    'folder' :'folder',//您想将文件保存到的路径
	    'queueID' :'fileQueue',//与下面的上传文件列表id对应
	    'queueSizeLimit' :50,//上传文件的数量
	    //'scriptData' :{'is_press':0,'press_osition':5,'site_id':site_id},//向后台传的数据
	    'fileDesc' :'jpg,jpeg,gif,png,css',//上传文件类型说明
		'fileExt' :'*.jpg;*.jpeg;*.gif;*.png;*.css', //控制可上传文件的扩展名,启用本项时需同时声明fileDesc
	    'method':'get',//如果向后台传输数据，必须是get
	    'sizeLimit':31457280,//文件上传的大小限制，单位是字节
	    'auto' :true,//是否自动上传
	    'multi' :false,
	    'simUploadLimit' :1,//同时上传文件的数量
	    'buttonText' :'BROWSE',//浏览按钮图片
		'onSelect':function(event,queueID,fileObj){//选择完后触发的事件			
			$("#"+input_name).uploadifySettings('scriptData',{'up_type':'file','css_ename':ename,'sid':MateInfoRPC.getUploadSecretKey()});
			
		},
		'onError':function(event,queueID,fileObj,errorObj){
			alert("文件:" +fileObj.name + "上传失败！");	
		},
	    'onComplete': function(event,queueID,fileObj,response,data){//当上传完成后的回调函数，ajax方式哦~~
			if(uploadAfterHandl != "" && uploadAfterHandl != null)
			{				
				eval(""+uploadAfterHandl+"('"+pic_url+"')");
			}
 	    },
 	   	'onAllComplete': function(event,data){
 	    	
 	    }
   });
}