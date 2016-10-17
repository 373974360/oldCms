var SubjectRPC = jsonrpc.SubjectRPC;
var ChatRPC = jsonrpc.ChatRPC;

var subjectActor = new Bean("com.deya.gpps.bean.interview.SubjectActor",true);
var subjectBean = new Bean("com.deya.gpps.bean.interview.SubjectBean",true);
var subjectCategory = new Bean("com.deya.gpps.bean.interview.SubjectCategory",true);
var ChatBean = new Bean("com.deya.gpps.bean.interview.ChatBean",true);
var GuestBean = new Bean("com.deya.gpps.bean.interview.GuestBean",true);

var sub_id = request.getParameter("id");

var pic_start_num = 0;
var text_start_num = 0;
var jctp_num = 0;

var user_num = "";
var user_name = "";

$(document).ready(function () {	
	subjectBean = SubjectRPC.getSubjectObjBySubID(request.getParameter("sub_id"));
	subjectCategory = SubjectRPC.getSubjectCategoryBeanByCID(subjectBean.category_id);
			
	$("#subject_intro").html(subjectBean.intro);
	//如果是历史状态,不显示发言区域
	if(subjectBean.status == 2)
	{
		$("#text_area_div").hide();				
		$("#pic_text_editor").css("height",$("#pic_text_editor").height()+$("#text_area_div").height());
		//得到所有消息记录
		setHistoryMessage();
		//得到精彩图片列表
		getLivePicList("history");
		//得到历史视频
		getHistoryVideo();
	}
	else
	{
		setUserInfo();//设置用户信息	
		//得到消息
		getAllMessage();
		setInterval("getAllMessage()",3000);	

		//得到精彩图片列表
		getLivePicList("");
		setInterval("getLivePicList()",20000);
	}

	if(subjectBean.status == 0)
	{//显示预告图片
		getForecastVideo();
	}

	isShowText();	//根据主题所属分类，判断是否隐藏文字互动区	
}); 

//得到历史视频
function getHistoryVideo()
{
	var history_video = ChatRPC.getHistoryVideo(sub_id);
	
	if(history_video != "" && history_video !=null)
	{
		setVideoPath(history_video);
	}else
	{//如果没有历史视频，显示访谈图片
		getForecastVideo('history');
	}
}

//得到访谈图片
function getForecastVideo(sta)
{
	var video_path = "";
	var img_path = "";
	var forecastL = ChatRPC.getResouseList(sub_id,"","forecast");
	forecastL = List.toJSList(forecastL);	
	if(forecastL != null && forecastL.size() > 0)
	{	
		for(var i=0;i<forecastL.size();i++)
		{
			if(forecastL.get(i).affix_path != "" && forecastL.get(i).affix_type == "pic")
			{						
				img_path = forecastL.get(i).affix_path;				
			}	
			if(forecastL.get(i).affix_path != "" && forecastL.get(i).affix_type == "video")
			{						
				video_path = forecastL.get(i).affix_path;				
			}	
		}
	}
	if(video_path != "")
	{
		setVideoPath(video_path);
	}
	else
	{
		if(img_path.trim() == ""){
			img_path = "/manager/images/default_live.jpg";
		}
		$("#live_video_div").html('<img  src="'+img_path+'" aling="center"/>');
	}
}

//在历史状态下读出信息
function setHistoryMessage()
{
	if(subjectBean.history_record_pic.trim() != "" && subjectBean.history_record_text.trim() != "")
	{
		$("#pic_text_editor > ul").append(subjectBean.history_record_pic);
		$("#text_area_editor > ul").append(subjectBean.history_record_text);
	}
	else
	{
		var l = ChatRPC.getChatListByDB(sub_id);			
		if(l != null)
		{
			l = List.toJSList(l);
			for(var i=0;i<l.size();i++)
			{
				if(l.get(i).chat_area == "pic" && subjectBean.history_record_pic.trim() == "")
				{
					$("#pic_text_editor > ul").append("<li><span>"+l.get(i).chat_user+"：</span>"+l.get(i).content+"</li>");
				}
				if(l.get(i).chat_area == "text" && subjectBean.history_record_text.trim() == "")
				{
					$("#text_area_editor > ul").append("<li><span>"+l.get(i).chat_user+"：</span>"+l.get(i).content+"</li>");
				}
			}
		}

		if(subjectBean.history_record_pic.trim() != "")
			$("#pic_text_editor > ul").append(subjectBean.history_record_pic);
		if(subjectBean.history_record_text.trim() != "")
			$("#text_area_editor > ul").append(subjectBean.history_record_text);

	}
}

//设置用户信息
function setUserInfo()
{
	/********** 初始获取用户序号 开始 ************/
	user_num = request.getCookie("gpps_user_num");
	
	if(user_num == "" || user_num == null)
	{
		user_num = ChatRPC.getUserMark(sub_id);		
		request.setCookie("gpps_user_num",user_num,"h4");//h4 cookie有效时间4小时
	}
	user_name = "网友"+user_num;
	
	/********** 初始获取用户序号 结束 ************/

	/********** 如果用户有登录，从登录信息中取数据 开始 ************/
	//Member = MemberRPC.getMemberBySession();
	if(memberA != null)
	{
		if(memberA.nick_name.trim() != "")
		{
			user_name = "网友"+memberA.nick_name;
		}
		else
			user_name = "网友"+memberA.user_name;
	}
	//在在线用户集合中写入当前的用户信息 初始没有昵称，使用网码+序号
	//$("#nickname").val(user_name);
	recordUserInfo(user_num,user_name);
	//判断该访谈模型是否只允许会员发言		
	
	if(subjectCategory.is_register == 0 && memberA == null)
	{//登录用户才可以发言	
		$("#sandMess").click(promptMemberLogin);
	}else
	{//所有用户都可以发言
		$("#sandMess").click(addMessage);
	}
	/********** 如果用户有登录，从登录信息中取数据 结束 ************/
}

function promptMemberLogin()
{
	alert("该主题需要登录才能发言，请登录");
}

//得到精彩图片列表
function getLivePicList(subject_status)
{
	if(subject_status == "history")
	{
		var srList = ChatRPC.getResouseList(sub_id,"pic","live");
	}else
		var srList = ChatRPC.getLivePicList(sub_id,jctp_num);
	if(srList != null)
	{
		srList = List.toJSList(srList);
		var p_name = "";
		
		if(srList.size() > 0)
		{
			for(var i=0;i<srList.size();i++)
			{
				if(srList.get([i]) != null)
				{
					p_name = srList.get([i]).affix_name;
					if(p_name.trim() == "" || p_name == null)
					{
						p_name = "图片直播";
					}
					var str = '<li><div><img src="'+srList.get([i]).affix_path+'"></div><span>'+p_name+'</span></li>';
					$("#pic_list_div > ul").prepend(str);
				}
			}

			jctp_num = srList.size();
		}
	}
}

//在在线用户集合中写入当前的用户信息
function recordUserInfo(u_num,u_name)
{
	GuestBean.user_num = u_num;
	GuestBean.nick_name = u_name;
	ChatRPC.recordUserInfo(sub_id,GuestBean);
}

	  

//根据主题所属分类，判断是否隐藏文字互动区
function isShowText()
{
	//隐藏文字互动区
	
	if(subjectCategory.is_show_text == 0)
	{	
		$("#text_area_div").hide();
		$("#edit_div").hide();			
		$("#pic_text_editor").css("height",$("#pic_text_editor").height()+$("#edit_div").height()+$("#text_area_div").height());
	}
}
	

//得到所有消息
function getAllMessage()
{
	var m = ChatRPC.getAllMessageList(sub_id,pic_start_num,text_start_num);
	
	if(m != null)
	{
		m = Map.toJSMap(m);
		setPicTextInfo(m);
		setTextAreaInfo(m);
		if(subjectBean.status == 1)
			setOtherParam(m);//得到其它设置参数，如直播视频地址  直播状态下才启用 
	}
}

function setVideoPath(live_video)
{
	$("#live_video_div").html('<embed id="live_video_embed" width="'+$("#live_video_div").attr("width")+'" height="'+$("#live_video_div").attr("height")+'" src="'+live_video+'" mediatype="video" autostart="true" loop="true" menu="true" sck_id="0"></embed>');
}

//得到其它设置参数，如直播视频地址
function setOtherParam(m)
{
	if(m.get("other_set") != null)
	{
		var other_m = Map.toJSMap(m.get("other_set"));
		var live_video = other_m.get(sub_id+"_live_video");
		//如果已经有视频了，判断视频地址有没有变更，有变更修改，没变更保持不变

		
		if($("#live_video_embed").attr("src") != null)
		{
			if($("#live_video_embed").attr("src") != live_video)
			{
				setVideoPath(live_video);
			}
		}
		else
		{			
			//如果有直播视频，直接插入
			if(live_video != "" && live_video != null)
			{
				setVideoPath(live_video);
			}
			else
			{//没有直播视频，查看是否有预告视频
				var forecast_video = other_m.get(sub_id+"_forecast_video");				
				if(forecast_video != "" && forecast_video != null)
				{
					setVideoPath(live_video);
				}
				else
				{
					//没有预告视频，查看是否有预告图片
					var forecast_pic = other_m.get(sub_id+"_forecast_pic");
					if(forecast_pic == "" || forecast_pic == null)
					{
						forecast_pic = "/manager/images/default_live.jpg";
					}
					
					$("#live_video_div").html('<img  src="'+forecast_pic+'" aling="center"/>');
				}
				
			}
		}
	}
}

//显示文字互动区域消息
function setTextAreaInfo(m)
{
	if(m.get("text_max_num") != null)
		text_start_num = m.get("text_max_num");

	var textList = m.get("text_mList");
	
	if(textList != null)
	{
		textList = List.toJSList(textList);
		for(var i=0;i<textList.size();i++)
		{
			if(textList.get(i) != null && textList.get(i).user_num != user_num)
			{
				var content = textList.get(i).content;
				$("#text_area_editor > ul").append("<li><span>"+textList.get(i).chat_user+"：</span>"+content+"</li>");
			}
		}					

		if(document.getElementById("text_area_editor").scrollHeight>document.getElementById("text_area_editor").offsetHeight)
		{								
			document.getElementById("text_area_editor").scrollTop=document.getElementById("text_area_editor").scrollHeight-document.getElementById("text_area_editor").offsetHeight+5;
		}
	}
}
//显示图文直播区域消息
function setPicTextInfo(m)
{
	if(m.get("pic_max_num") != null)
		pic_start_num = m.get("pic_max_num");

	var picTextList = m.get("pic_mList");
	if(picTextList != null)
	{
		picTextList = List.toJSList(picTextList);
		for(var i=0;i<picTextList.size();i++)
		{
			if(picTextList.get(i) != null)
			{
				var content = picTextList.get(i).content;
				
				$("#pic_text_editor > ul").append("<li><span>"+picTextList.get(i).chat_user+"：</span>"+content+"</li>");
			}
		}					

		if(document.getElementById("pic_text_editor").scrollHeight>document.getElementById("pic_text_editor").offsetHeight)
		{								
			document.getElementById("pic_text_editor").scrollTop=document.getElementById("pic_text_editor").scrollHeight-document.getElementById("pic_text_editor").offsetHeight+15;
		}
	}
}

//发言
function addMessage()
{	
	if(subjectCategory.is_register == 0 && memberA == null)
	{
		promptMemberLogin();
		return;
	}

	if($("#elem1").val().trim() == "")
	{
		alert("发言内容不能为空");
		return;
	}

	if($("#elem1").val().LenDB() > 3000)
	{
		alert("输入的内容太多，只能输入1000个汉字或3000个字符！");
		return;
	}	
		
	var cbena = BeanUtil.getCopy(ChatBean);
	cbena.user_num = user_num;
	cbena.chat_user = user_name;
	cbena.content = $("#elem1").val();
	cbena.chat_type = "guest";
	cbena.chat_area = "text";
	ChatRPC.setTextInfo(sub_id,cbena,subjectCategory.is_t_audit);
	
	$("#text_area_editor > ul").append("<li><span>"+cbena.chat_user+"：</span>"+cbena.content+"</li>");
	if(document.getElementById("text_area_editor").scrollHeight>document.getElementById("text_area_editor").offsetHeight)
	{								
		document.getElementById("text_area_editor").scrollTop=document.getElementById("text_area_editor").scrollHeight-document.getElementById("text_area_editor").offsetHeight+15;
	}

	$("#elem1").val("");
}

//修改文字显示窗口样式
function changeTextAreaHeight(flag)
{

	if(flag == 1)
	{	
		$("#text_area_editor").css("height","150px");
		$("#pic_text_editor").css("height","150px");

		$("#text_area_editor").show();
		$("#pic_text_editor").show();
	}
	if(flag == 2)
	{
		$("#text_area_editor").hide();
		$("#pic_text_editor").show();

		$("#pic_text_editor").css("height","300px");
	}
	if(flag == 3)
	{
		$("#text_area_editor").css("height","300px");
		$("#pic_text_editor").hide();
		$("#text_area_editor").show();
	}
}