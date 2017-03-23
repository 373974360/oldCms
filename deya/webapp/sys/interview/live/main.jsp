<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>直播管理</title>	
	<link rel="stylesheet" type="text/css" href="../../js/jquery-ui/themes/base/jquery.ui.all.css"  />
	<!--<link rel="stylesheet" type="text/css" href="../../style/themes/icon.css">-->
	<jsp:include page="../../include/include_tools.jsp"/>
	<!--<script type="text/javascript" src="../../js/xheditor/xheditor.min.js"></script>-->

	<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.button.js"></script>
	<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.position.js"></script>
	<script type="text/javascript" src="../../js/jquery-ui/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="js/liveManager.js"></script>
	<script type="text/javascript" src="/cms.files/js/jwplayer/jwplayer.js"></script> 
	<style>
	
	TD,DIV,SPAN{font-size:12px;color:355C86}

	.blankH5{height:5px;clear:both;overflow:hidden;}
	.blankH90{height:90px;clear:both;overflow:hidden;}

	.l_div_body{float:left;margin:auto;width:326px;height:28px;background-image:url('../../images/live_label_back.gif');}	
	.l_div_body .l_div_left{float:left;margin:auto;width:5px;height:28px;background-image:url('../../images/gpps_sprites.gif');background-position:-561px -72px;}	
	.l_div_body .l_div_right{float:right;margin:auto;width:5px;height:28px;background-image:url('../../images/gpps_sprites.gif');background-position:-569px -72px;}

	.r_div_body{float:left;margin:auto;width:100%;height:28px;background-image:url('../../images/live_label_back.gif');}
	.r_div_body .r_div_left{float:left;margin:auto;width:5px;height:28px;background-image:url('../../images/gpps_sprites.gif');background-position:-561px -72px;}	
	.r_div_body .r_div_right{float:right;margin:auto;width:5px;height:28px;background-image:url('../../images/gpps_sprites.gif');background-position:-569px -72px;}

	.lable_text{float:left;margin:auto;margin-top:8px;margin-left:8px;font-weight:bold;width:100px}
	.lable_button{float:right;margin:auto;margin-top:8px;margin-left:8px;font-weight:bold;width:150px}
	
	.control_img_1{float:right;margin:auto;width:17px;height:14px;background-image:url('../../images/gpps_sprites.gif');background-position:-574px -72px;margin-top:8px;margin-right:3px;cursor:pointer;}
	.control_img_2{float:right;margin:auto;width:17px;height:14px;background-image:url('../../images/gpps_sprites.gif');background-position:-590px -72px;margin-top:8px;margin-right:1px;cursor:pointer;}
	.control_img_3{float:right;margin:auto;width:17px;height:14px;background-image:url('../../images/gpps_sprites.gif');background-position:-605px -72px;margin-top:8px;margin-right:6px;cursor:pointer;}

	#live_video_div{width:324px;text-align:center;border-left:1px solid #A0B2C8;border-right:1px solid #A0B2C8;border-bottom:1px solid #A0B2C8;padding-top:2px;float:left}

	.text_div{height:150px;border-left:1px solid #A0B2C8;border-right:1px solid #A0B2C8;border-bottom:1px solid #A0B2C8;overflow:auto;background:#F5FAFE;padding:3px;color:#486282}
	.editor_div{border-left:1px solid #A0B2C8;border-right:1px solid #A0B2C8;border-bottom:1px solid #A0B2C8;background:#F5FAFE;padding:3px;color:#486282;margin-top:28px;}
	.send_user_div{float:left;margin:auto;margin-top:3px;width:300px;}

	.user_list_div{width:318px;height:1px;border-left:1px solid #A0B2C8;border-right:1px solid #A0B2C8;border-bottom:1px solid #A0B2C8;overflow:auto;background:#F5FAFE;padding:3px;color:#486282}
	
	#c_user_count{float:left;margin:auto;margin-top:8px;margin-left:8px;width:100px;}
	.wargin_span{color:#A94B3B;cursor:pointer}
	.span_css{display:block; float:left;font-size:12px;}
	</style>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var site_id = site_id;
		var id = id;
		var sub_id = sub_id;
		
		var current_sysDialog = "";//需要修改记录的编辑器名称
		var current_index_num = 0;//需要修改记录的集合下标
		var current_chat_area = "";//当前修改记录的所属区域 图文区或文字互动区
		var current_isUpdateCatch;//当前修改记录是否需要更新缓存
		var current_chat_id;//当前修改记录的ID
		var current_textarea_height = 150;

		var pic_start_num = 0;
		var text_start_num = 0;
		$(document).ready(function () {		
			fnDialog();
			setDivHeight();

			setActorSelect();
			subjectBean = subjectBean;
			subjectCategory = subjectCategory;

			isShowText();
			getMessage();
            initUeditor("elem2");
            initUeditor("elem1");
			initButtomStyle();
			init_input();
		}); 
		//根据页面高度自动设置DIV高度
		function setDivHeight()
		{
			//首先得到网页可见高度
			var page_height = window_width-320;
			
			//如果高度太小，设置内容区域高最小为15px			
			//if(page_height < 150)
				//page_height = 150;			
			
			current_textarea_height = page_height/2;
			$("#text_area_editor").css("height",current_textarea_height);
			$("#pic_text_editor").css("height",current_textarea_height);
			
			//加载完后，再得到内容区的高度，并计算出在线人员列表的高度
			page_height = window_width-418;
			
			$("#c_user_list").css("height",page_height);			
			setTimeout("setParentFrameHeight()",300);			
		}
		//更改IFRAME高度
		function setParentFrameHeight()
		{
			if(document.body.scrollHeight > document.body.clientHeight-55)
			{
				//高度太小，必须改变外层Iframe的高度，让IE出现滚动条
				setFrameHeight(window.name,window_width-60);
			}
		}
		//修改文字显示窗口样式
		function changeTextAreaHeight(flag)
		{
			if(flag == 1)
			{	
				$("#text_area_editor").css("height",current_textarea_height);
				$("#pic_text_editor").css("height",current_textarea_height);

				$("#text_area_editor").show();
				$("#pic_text_editor").show();
			}
			if(flag == 2)
			{
				$("#text_area_editor").hide();
				$("#pic_text_editor").show();

				$("#pic_text_editor").css("height",current_textarea_height*2);
			}
			if(flag == 3)
			{
				$("#pic_text_editor").hide();
				$("#text_area_editor").show();

				$("#text_area_editor").css("height",current_textarea_height*2);
			}
		}

		//得到消息
		function getMessage()
		{
			//预告状态下，按发送按钮提示
			/*
			if(subjectBean.status == 0 || subjectBean.status == 2)
			{
				$("#sendMessage").click(pointWargin);
			}*/

			//预告状态下取出预告视频和图片,精彩图片　
			if(subjectBean.status == 0)
			{
				getForecastVideo('Forecast');
			}

			//主题状态为直播状态
			if(subjectBean.status == 1 || subjectBean.status == 0)
			{		
				getAllMessage();
				setInterval("getAllMessage()",5000);
				//得到当前在线用户
				getCurrentLiveUser();
				setInterval("getCurrentLiveUser()",6000);
			}
			//主题状态为历史状态，从数据库中取数据
			if(subjectBean.status == 2)
			{
				$("#video_lable_text").html("历史视频窗口");
				getChatListByDB();
				getHistoryVideo();
			}
			$("#sendMessage").click(addMessage);
		}		
		//预告状态下，按发送按钮提示信息
		function pointWargin()
		{
			alertN("只有直播状态下的访谈主题才能执行发送操作");
		}

		//得到历史视频

		function getForecastVideo(sta)
		{
			var forecastL = ChatRPC.getResouseList(sub_id,"","forecast");
			forecastL = List.toJSList(forecastL);	
			if(forecastL != null && forecastL.size() > 0)
			{	
				for(var i=0;i<forecastL.size();i++)
				{
					if(forecastL.get(i).affix_path != "")
					{//历史状态中，没有历史视频，显示访谈图片，预告图片就不显示了
						if(sta != "history")
						{
							if(forecastL.get(i).affix_type == "video")
							{
								setVideoPath(forecastL.get(i).affix_path);
								break;
							}
						}
						if(forecastL.get(i).affix_type == "pic")
						{
							$("#live_video_div").html('<img height="280" width="320" src="'+forecastL.get(i).affix_path+'" aling="center"/>');
						}
					}
					
				}
			}else{
				$("#live_video_div").html('<img height="280" width="320" src="../../images/default_live.jpg" aling="center"/>');
			}
		}

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

		//得到当前在线用户
		function getCurrentLiveUser()
		{	
			var m = ChatRPC.getLiveUserInfo(sub_id);
			if(m != null)
			{
				m = Map.toJSMap(m);
				$("#c_user_count").html("共 "+m.size()+" 人");	
				var str  = "";
				var bcolor = "#F5FAFE";
				for(var i=0;i<m.keySet().length;i++)
				{	
					var getstBean = m.get(m.keySet()[i]);	
					var bcolor = "#F5FAFE";
					if(i % 2 != 0)					
						bcolor = "#FFFFFF";					
					else
						bcolor = "#F5FAFE";
					str += "<div style='height:25px;background:"+bcolor+";padding-top:6px'><span style='width:20px'></span><span style='width:80px;display:block; float:left;'><strong>"+getstBean.nick_name+"</strong></span><span style='width:140px' class='span_css'>IP："+getstBean.ip+"</span><span onclick='setProhibitUsers(\""+getstBean.user_num+"\")' class='wargin_span span_css'>[禁言]</span></div></div>";
				}
				$("#c_user_list").html(str);
			}
			
		}
		//为网友留言页面提供的获取在线人数的接口
		function getCurrentUserCount()
		{
			return $("#c_user_count").html();
		}

		//设置禁言用户
		function setProhibitUsers(user_num)
		{
			ChatRPC.setProhibitUsers(sub_id,user_num);
		}

		
		//从数据库中取出所有信息列表
		function getChatListByDB()
		{
			var l = ChatRPC.getChatListByDB(sub_id);
			
			if(l != null)
			{
				l = List.toJSList(l);
				for(var i=0;i<l.size();i++)
				{
					if(l.get(i).chat_area == "pic")
					{
						$("#pic_text_editor").append("<div style='padding:3px;'><span style='width:150px'><strong>"+l.get(i).chat_user+"：</strong></span><span onclick='fnModify("+i+",\"pic\",\""+l.get(i).chat_id+"\",false)' style='cursor:pointer'>[编辑]</span><span onclick='fnDelete(this,"+i+",\"pic\",\""+l.get(i).chat_id+"\",false)' class='wargin_span'>[删除]</span></div><div id='pic_editor_"+i+"' style='padding:3px;border-bottom:1px dotted #8EB0D3'>"+l.get(i).content+"</div>");
					}
					if(l.get(i).chat_area == "text")
					{
						$("#text_area_editor").append("<div style='padding:3px;'><span style='width:150px'><strong>"+l.get(i).chat_user+"：</strong></span><span onclick='fnModify("+i+",\"text\",\""+l.get(i).chat_id+"\",false)' style='cursor:pointer'>[编辑]</span><span onclick='fnDelete(this,"+i+",\"text\",\""+l.get(i).chat_id+"\",false)' class='wargin_span'>[删除]</span></div><div id='text_editor_"+i+"' style='padding:3px;border-bottom:1px dotted #8EB0D3'>"+l.get(i).content+"</div>");
					}
				}
			}
		}
		


		//得到所有消息
		function getAllMessage()
		{
			var m = ChatRPC.getAllMessageListByAdmin(sub_id,pic_start_num,text_start_num,subjectCategory.is_p_audit,subjectCategory.is_t_audit,subjectCategory.is_show_text);
			
			if(m != null)
			{
				m = Map.toJSMap(m);
				setPicTextInfo(m);
				setTextAreaInfo(m);
				setOtherParam(m);//得到其它设置参数，如直播视频地址
			}
		}

		function setVideoPath(live_video)
		{
			if(live_video.indexOf(".flv")>0)
			{
				jwplayer("live_video_div").setup({
					flashplayer: "/cms.files/js/jwplayer/player.swf",
					file: live_video,
					autostart:true,
					width:"320",
					height:"280px",
					image: ""
				});
			}
			else
			{
				$("#live_video_div").html('<embed id="live_video_embed" width="320px" height="280px" src="'+live_video+'" mediatype="video" autostart="true" loop="true" menu="true" sck_id="0" type="application/x-mplayer2" ></embed>');
				
			}
			$("#live_video_div").attr("video_path",live_video);
		}
// 修改了 访谈 》管理 后视频框无展示 所以修改此处
		//得到其它设置参数，如直播视频地址
		function setOtherParam(m)
		{ 
			if(m.get("other_set") != null)
			{
			 
                           	var other_m = Map.toJSMap(m.get("other_set"));
				var live_video = other_m.get(sub_id+"_live_video");
//////////////-2013-1-23-添加此处/////////////////

 var l = ChatRPC.getResouseList(sub_id,"video","");
			if(l != null)
			{
				l = List.toJSList(l);		
				for(var i=0;i<l.size();i++)
				{
					if(l.get(i).affix_status == "live")
					{
							live_video = l.get(i).affix_path;
					}
					 
				}
			}

//////////////-2013-1-23-添加此处/////////////////

				//如果已经有视频了，判断视频地址有没有变更，有变更修改，没变更保持不变
				if(live_video != null && $("#live_video_div").attr("video_path") != null && $("#live_video_div").attr("video_path") != "")
				{
					if($("#live_video_div").attr("video_path") != live_video)
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
					{ //没有直播视频，查看是否有预告视频
						var forecast_video = other_m.get(sub_id+"_forecast_video");

						if(forecast_video != "" && forecast_video != null)
						{
							if($("#live_video_embed").attr("src") != forecast_video)
							{
                                                         setVideoPath(forecast_video);
					         	}
                                                }
						else
						{
							//没有预告视频，查看是否有预告图片
							var forecast_pic = other_m.get(sub_id+"_forecast_pic");
							if(forecast_pic != "" && forecast_pic != null)
							{
								$("#live_video_div").html('<img height="280" width="320" src="'+forecast_pic+'" aling="center"/>');
							}//else 
								//$("#live_video_div").html('<img height="280" width="320" src="../../images/default_live.jpg" aling="center"/>');
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
					if(textList.get(i) != null)
					{
						var pass_str = "";
						if(subjectCategory.is_t_audit ==1 && textList.get(i).audit_status == 0)
						{
							pass_str = "<span onclick='fnPass(this,"+textList.get(i).index_num+",\"text\",\""+textList.get(i).chat_id+"\")' style='cursor:pointer;width:40px' class='span_css'>[允许]</span>";
						}
						else
							pass_str = "&nbsp;　　&nbsp;";

						$("#text_area_editor").append("<div style='padding:3px;'><span style='width:150px' class='span_css'><strong>"+textList.get(i).chat_user+"：</strong></span><span class='span_css' style='width:130px;'>IP："+textList.get(i).ip+"</span>"+pass_str+"&nbsp;<span onclick='fnModify("+textList.get(i).index_num+",\"text\",\""+textList.get(i).chat_id+"\",true)' style='cursor:pointer;width:40px' class='span_css'>[编辑]</span>&nbsp;<span onclick='fnQuote("+textList.get(i).index_num+")' style='cursor:pointer;width:40px' class='span_css'>[引用]</span>&nbsp;<span onclick='fnDelete(this,"+textList.get(i).index_num+",\"text\",\""+textList.get(i).chat_id+"\",true)' class='wargin_span span_css' style='width:40px'>[删除]</span>&nbsp;<span onclick='setProhibitUsers(\""+textList.get(i).user_num+"\")' class='wargin_span class='span_css''>[禁言]</span></div><div id='text_editor_"+textList.get(i).index_num+"' style='padding:3px;;border-bottom:1px dotted #8EB0D3;word-break:break-all'>"+textList.get(i).content+"</div>");
					}
				}					
				/*
				if(document.getElementById("text_area_editor").scrollHeight>document.getElementById("text_area_editor").offsetHeight)
				{								
					document.getElementById("text_area_editor").scrollTop=document.getElementById("text_area_editor").scrollHeight-document.getElementById("text_area_editor").offsetHeight+5;
				}*/
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
						var pass_str = "";
						if(subjectCategory.is_p_audit ==1 && picTextList.get(i).audit_status == 0)
						{
							pass_str = "<span onclick='fnPass(this,"+picTextList.get(i).index_num+",\"pic\",\""+picTextList.get(i).chat_id+"\")' style='cursor:pointer;width:40px' class='span_css'>[允许]</span>";
						}
						else
							pass_str = "&nbsp;　　&nbsp;";
						
						$("#pic_text_editor").append("<div style='padding:3px;'><span style='width:150px' class='span_css'><strong>"+picTextList.get(i).chat_user+"：</strong></span><span style='width:130px;'></span>"+pass_str+"&nbsp;<span onclick='fnModify("+picTextList.get(i).index_num+",\"pic\",\""+picTextList.get(i).chat_id+"\",true)' style='cursor:pointer;width:40px' class='span_css'>[编辑]</span>&nbsp;<span onclick='fnDelete(this,"+picTextList.get(i).index_num+",\"pic\",\""+picTextList.get(i).chat_id+"\",true)' class='wargin_span span_css'>[删除]</span></div><div id='pic_editor_"+picTextList.get(i).index_num+"' style='padding:3px;;border-bottom:1px dotted #8EB0D3;word-break:break-all'>"+picTextList.get(i).content+"</div>");
					}
					
				}					
				/*
				if(document.getElementById("pic_text_editor").scrollHeight>document.getElementById("pic_text_editor").offsetHeight)
				{								
					document.getElementById("pic_text_editor").scrollTop=document.getElementById("pic_text_editor").scrollHeight-document.getElementById("pic_text_editor").offsetHeight+5;
				}*/
			}
		}

		//根据主题所属分类，判断是否隐藏文字互动区
		function isShowText()
		{
			//隐藏文字互动区			
			if(subjectCategory.is_show_text == 0)
			{
				$("#text_area").hide();
				$("#pic_text_editor").css("height",current_textarea_height*2+28);
			}
		}

		//得到嘉宾信息
		function setActorSelect()
		{
			var actorList = SubjectRPC.getAllActorName(sub_id);
			actorList = List.toJSList(actorList);
			if(actorList != null)
			{
				$("#actor_select").addOptions(actorList,"actor_name","actor_name");
			}
		}
		//管理员发言
		function addMessage()
		{
			if(subjectBean.status == 2)
			{
				alert("该访谈为历史状态，不能再发言");
				return;
			}

			if($("#actor_input").val().trim() == "")
			{
				alert("请选择发言人");
				return;
			}

			if(getV("elem1").trim() == "")
			{
				alert("发言内容不能为空");
				return;
			}

			if(getV("elem1").LenDB() > 3000)
			{
				alert("输入的内容太多，只能输入1000个汉字或3000个字符！");
				return;
			}

			var cbena = BeanUtil.getCopy(ChatBean);
			cbena.chat_user = $("#actor_input").val();
			cbena.content = getV("elem1").replace(/src=\"\.\.\/\.\.\/js\/xheditor/ig,'src="/sys/js/xheditor');
			cbena.chat_type = "admin";

			if($("#actor_select").val() == "网友")
			{//模拟游客说话，插入到文字互动区
				cbena.audit_status = 1;
				cbena.chat_area = "text";
				ChatRPC.setTextInfo(sub_id,cbena,subjectCategory.is_t_audit);
			}
			else		
			{
				cbena.chat_area = "pic";
				ChatRPC.setPicTextInfo(sub_id,cbena,subjectCategory.is_p_audit);
			}
 
			setV("elem1","");
		}

		
		//初始编辑窗口
		function fnDialog()
		{
			/*
			$('#sysDialog').dialog({			
				
			});
			$('#sysDialog').dialog('close');*/

			$("#sysDialog").dialog({
				resizable: false,
				width:530,
				height:290,
				modal: true,
				title:'编辑'
			});

			$('#sysDialog').dialog('close');
		}
		
		//引用
		function fnQuote(index_num)
		{
			//$("#elem1").val($("#"+"text_editor_"+index_num).html());
			setV("elem1",$("#"+"text_editor_"+index_num).html());
		}

		//打开修改窗口
		function fnModify(index_num,flag,chat_id,isUpdateCatch){
			current_index_num = index_num;
			current_chat_area = flag;
			current_isUpdateCatch = isUpdateCatch;
			current_chat_id = chat_id;
			
			current_sysDialog = flag+"_editor_"+index_num;

			//$("#elem2").val($("#"+current_sysDialog).html());
			setV("elem2",$("#"+current_sysDialog).html());
			//KE.util("elem2").fullscreen();
			//setTimeout('KE.util("elem2").fullscreen()','1');
			$('#sysDialog').dialog('open');
		}
		//执行修改操作
		function modifyMessage()
		{
			var content_val = getV("elem2");
			$("#"+current_sysDialog).html(content_val);
			$('#sysDialog').dialog('close');

			var cbena = BeanUtil.getCopy(ChatBean);
			cbena.content = content_val;
			cbena.index_num = current_index_num;
			cbena.chat_area = current_chat_area;
			cbena.sub_id = sub_id;
			cbena.chat_id = current_chat_id;
	
			ChatRPC.updateChat(cbena,subjectCategory.is_p_audit,subjectCategory.is_t_audit,current_isUpdateCatch);
		}
		//删除操作
		function fnDelete(obj,index_num,flag,chat_id,isUpdateCatch)
		{
			$(obj).parent().remove();
			$("#"+flag+"_editor_"+index_num).remove();

			var cbena = BeanUtil.getCopy(ChatBean);
			cbena.index_num = index_num;
			cbena.chat_area = flag;
			cbena.sub_id = sub_id;
			cbena.chat_id = chat_id;
			cbena.audit_status = 1;
			ChatRPC.deleteChat(cbena,subjectCategory.is_p_audit,subjectCategory.is_t_audit,isUpdateCatch);
		}

		//审核操作
		function fnPass(obj,index_num,flag,chat_id)
		{			
			var cbena = BeanUtil.getCopy(ChatBean);
			cbena.index_num = index_num;
			cbena.chat_area = flag;
			cbena.sub_id = sub_id;
			cbena.chat_id = chat_id;
			
			ChatRPC.isPassChat(cbena);
			$(obj).html("&nbsp;　　&nbsp;");
		}
	//-->
	</SCRIPT>	

	
</head> 
<body> 
	<input type="hidden" id="handleId" name="handleId" value="H23001">
	<table id="tables" border="0" cellpadding="0" cellspacing="0" width="98%" align="center">
	 <tr>
	  <td width="336px" valign="top" height="300px">  
		 <div class="l_div_body"><div class="l_div_left"></div><div id="video_lable_text" class="lable_text">直播视频窗口</div><div class="l_div_right"></div></div>	   
	     <div id="live_video_div" ></div>
	  </td>
	  <td rowspan="2" valign="top">	        
		 <div class="r_div_body">
		  <div class="r_div_left"></div>
		  <div class="lable_text">图文直播区</div>
		  <div class="r_div_right"></div>
		  <div class="control_img_3" onclick="changeTextAreaHeight(3)"></div>
		  <div class="control_img_2" onclick="changeTextAreaHeight(2)"></div>
		  <div class="control_img_1" onclick="changeTextAreaHeight(1)"></div>
		 </div>
	   <div id="pic_text_editor" class="text_div"></div>
	   <div class="blankH5"></div>
		<!-- 文字互动区 开始 -->
	   <div id="text_area">	   
	     <div class="r_div_body">
		  <div class="r_div_left"></div>
		  <div class="lable_text">文字互动区</div>
		  <div class="r_div_right"></div>
		  <div class="control_img_3" onclick="changeTextAreaHeight(3)"></div>
		  <div class="control_img_2" onclick="changeTextAreaHeight(2)"></div>
		  <div class="control_img_1" onclick="changeTextAreaHeight(1)"></div>		 
	   	 </div><div style="clear:both"></div>
	   <div id="text_area_editor" class="text_div"></div>
	   <div class="blankH5"></div>
	   </div>
		<!-- 文字互动区 结束 -->
		<div class="r_div_body">
		  <div class="r_div_left"></div>
		  <div class="send_user_div">
			<select id="actor_select" name="actor_select" onchange="$('#actor_input').val(this.value)">
			  <option value=""></option>
			   <option value="主持人">主持人</option>
			   <option value="嘉宾">嘉宾</option>
			   <option value="网友">网友</option>
			  </select>
			  <input type="text" id="actor_input" name="actor_input" maxlength="30">			
		  </div>
		  <div class="lable_text"></div>
		  <div class="r_div_right"></div>		  
	   	 </div>
	   
	   <div class="editor_div" >
	     <!-- <textarea id="elem1" name="elem1" class="xheditor {upImgUrl:'/HtmleditorUploadFile',upImgExt:'jpg,jpeg,gif,png',upFlashUrl:'/HtmleditorUploadFile',upFlashExt:'swf|flv',upLinkUrl:'/HtmleditorUploadFile',upLinkExt:'txt,zip,doc,docx,xlsx,ppt,pptx,tar,jar,rar,html,jpg,jpeg,gif,png,swf,flv,asf,avi,mpg,mpeg,mpe,mov,rmvb,wmv,wav,mid,midi,mp3,mpa,mp2,ra,ram,rm,wma',upMediaUrl:'/HtmleditorUploadFile',upMediaExt:'wmv,avi,wma,mp3,mid',tools:'FontSize,Bold,Italic,Underline,FontColor,BackColor,SelectAll,Removeformat,Align,Separator,Link,Unlink,Img,Emot,Separator',skin:'o2007blue',width:'100%',height:'100',forcePtag:false,modalWidth:'showModal'}"></textarea> -->
           <script id="elem1" type="text/plain" style="width:100%;height:80px;"></script>
		 <span class="blank3"></span>
		 <input id="sendMessage" name="btn1" type="button" onclick="" value="发　送" />	
	   </div>
	  </td>
	 </tr>
	 <tr>
	  <td width="336px" valign="top">
	   <div class="blankH5"></div>
	   <div class="l_div_body"><div class="l_div_left"></div><div class="lable_text" >在线用户</div><div id="c_user_count"></div><div class="l_div_right"></div></div>   
	   <div class="user_list_div" id="c_user_list"></div>
	  </td>	  
	 </tr>
	</table>		
	<div class="blankH5"></div>	

	<div id="sysDialog" icon="icon-save" style="width:510px;height:250px;text-align:center;display:none;">
	<span class="blank3"></span>
		<!-- <textarea id="elem2" name="elem2"  class="xheditor {upImgUrl:'/HtmleditorUploadFile',upImgExt:'jpg,jpeg,gif,png',upFlashUrl:'/HtmleditorUploadFile',upFlashExt:'swf|flv',upLinkUrl:'/HtmleditorUploadFile',upLinkExt:'txt,zip,doc,docx,xlsx,ppt,pptx,tar,jar,rar,html,jpg,jpeg,gif,png,swf,flv,asf,avi,mpg,mpeg,mpe,mov,rmvb,wmv,wav,mid,midi,mp3,mpa,mp2,ra,ram,rm,wma',upMediaUrl:'/HtmleditorUploadFile',upMediaExt:'wmv,avi,wma,mp3,mid',tools:'FontSize,Bold,Italic,Underline,FontColor,BackColor,SelectAll,Removeformat,Align,Separator,Link,Unlink,Img,Emot,Separator',skin:'o2007blue',width:'496',height:'210',forcePtag:false}"></textarea> -->	
        <script id="elem2" type="text/plain" style="width:100%;height:210px;"></script>
		<span class="blank3"></span>
		<div style="text-align:left;margin-left:50px">
		 <input id="addButton" name="btn1" type="button" onclick="modifyMessage()" value="保存" />
		 <input id="addButton" name="btn1" type="button" onclick="$('#sysDialog').dialog('close')" value="取消" />	
		</div>
	</div>
</body> 
  
</html> 

