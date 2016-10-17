<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>禁言管理</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
	<script language="javascript" src="../../js/jquery.uploadify.js"></script>
	<script language="javascript" src="../../js/uploadFile/swfobject.js"></script>
	<script type="text/javascript" src="js/liveManager.js"></script>
	<style>
	BODY{background:#E6F0FA;font-size:12px;margin:10 0 0 0}
	TD,DIV{font-size:12px;color:355C86}	

	.blankH5{height:5px;clear:both;overflow:hidden;}
	.blankH90{height:90px;clear:both;overflow:hidden;}

	
	</style>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var id = parent.id;
		var sub_id = parent.sub_id;		
		var site_id = parent.site_id;
		$(document).ready(function () {	
			subjectBean = parent.subjectBean;
			subjectCategory = parent.subjectCategory;
			initUPLoadMedia("s_history_video_file","history_video");
			//主题状态为直播状态
			if(subjectBean.status == 1)
			{ 
				//得到设置参数
				getOtherParamSet();
			}
			else
			{
				//主题状态为历史或预告状态，从数据库中取数据
				getOtherParam();
			}
		});
		//得到其它参数,在历史状态中，主要是得到视频的地址
		function getOtherParam()
		{
			 
			var l = ChatRPC.getResouseList(sub_id,"video","");
			if(l != null)
			{
				l = List.toJSList(l);		
				for(var i=0;i<l.size();i++)
				{
					if(l.get(i).affix_status == "live")
					{
						 
						$("#live_video").val(l.get(i).affix_path)
					}
					if(l.get(i).affix_status == "history")
					{
						 
						$("#history_video").val(l.get(i).affix_path);
					}
				}
			}
		}
		
		//得到其它参数
		function getOtherParamSet()
		{
			 
			var m = ChatRPC.getOtherParamSet(sub_id);
			 
			if(m != null)
			{
				m = Map.toJSMap(m);
				 
				 
				if(m.get(sub_id+"_live_video") != "" && m.get(sub_id+"_live_video") != null)
				{
					 
					$("#live_video").val(m.get(sub_id+"_live_video"));
				}
               
				if(m.get(sub_id+"_history_video") != "" && m.get(sub_id+"_history_video") != null)
				{
					 
					$("#history_video").val(m.get(sub_id+"_history_video"));
				}

/////////////-2013-1-22-//////////////
           var l = ChatRPC.getResouseList(sub_id,"video","");
			if(l != null)
			{
				l = List.toJSList(l);		
				for(var i=0;i<l.size();i++)
				{
					if(l.get(i).affix_status == "live")
					{
						 
						$("#live_video").val(l.get(i).affix_path)
					}
					if(l.get(i).affix_status == "history")
					{
						 
						$("#history_video").val(l.get(i).affix_path);
					}
				}
			}
//////////////-2013-1-22-//////////////////

				//如果关键词过滤设置为空，读取该分类的过滤设置
				if(m.get(sub_id+"_is_t_keyw") == null)
				{					
					setRadioChecked("is_t_keyw",subjectCategory.is_t_keyw);
				}
				else
				{
					setRadioChecked("is_t_keyw",m.get(sub_id+"_is_t_keyw"));
				}
					
				if(m.get(sub_id+"_is_permit_speak") == null)	
				{
					setRadioChecked("is_permit_speak",subjectCategory.is_show_text);
				}
				else
				{
					setRadioChecked("is_permit_speak",m.get(sub_id+"_is_permit_speak"));
				}
				
				if(m.get(sub_id+"_filter_type") != "" && m.get(sub_id+"_filter_type") != null)
				{
					setRadioChecked("filter_type",m.get(sub_id+"_filter_type"));
				}

				if(m.get(sub_id+"_is_t_flink") == null)
				{					
					setRadioChecked("is_t_flink",subjectCategory.is_t_flink);
				}
				else				
				{
					setRadioChecked("is_t_flink",m.get(sub_id+"_is_t_flink"));
				}
			}
		}

		function setRadioChecked(inputName,value)
		{
			$("input[id="+inputName+"]").each(function(){		
			
				if($(this).attr("value") == value)
				{
					$(this).click();
				}
			})
		}

		//保存直播视频
		function saveLiveVideo()
		{
			if($("#live_video").val().trim() == "")
			{
				alert("请输入直播视频地址");
				return;
			}
			else
			{
				var bean = BeanUtil.getCopy(SubResouse);
				
				bean.sub_id = sub_id;
				bean.affix_type = "video";
				bean.affix_status = "live";
				bean.affix_path = $("#live_video").val();				
				bean.add_user = user_id;
				
				if(!ChatRPC.insertLiveVideo(bean))
				{
					alert("保存失败，请重新保存");
				}
			}
		}

		//上传历史视频
		function uploadHistroyVideo()
		{
			var str = $("#fileName").val();
			if(str.trim() == "")
			{
				alert("请选择上传文件");
				return;
			}

			if(!checkVideoFile(str))
			{ 
				parent.alertWar("<span style='color:red'>上传的视频文件格式不对，只允许上传 asf，avi，mpg，mpeg，mpe，mov，wmv，wav，mid，midi，mp3，mpa，mp2，ra，wma 等格式的文件，请重新上传！</span>"); 
				return; 
			}

			$("#form1").submit();
		}

		//判断图片格式
		function checkVideoFile(files) 
		{
			var ext = "asf,avi,mpg,mpeg,mpe,mov,wmv,wav,mid,midi,mp3,mpa,mp2,ra,wma";
			if(files.replace(/(^\s*)|(\s*$)/g,"") != "")
			{    		  
				if(files.indexOf(".") == -1) 
				{ 					
					return false; 
				}
				var strtype = files.match(/\.([^\.]+)(\?|$)/)[1];
				if(ext.indexOf(strtype.toLowerCase()+",") > -1)				
				{ 
					return true; 
				} 
				else
				{ 					
					return false;
				}
			}
			return true;
		}

		//得到上传图片地址后插入信息
		function returnUploadValue(furl)
		{
			if(furl != "" && furl != null)
			{
				$("#history_video").val(furl);
				var bean = BeanUtil.getCopy(SubResouse);
				
				bean.sub_id = sub_id;
				bean.affix_type = "video";
				bean.affix_status = "history";
				bean.affix_path = $("#history_video").val();				
				bean.add_user = user_id;
				
				if(!ChatRPC.insertLiveVideo(bean))
				{
					alert("上传失败，请重新上传");
				}
			}
		}
		
		function saveHistroyVideo()
		{
			if($("#history_video").val() != "" && $("#history_video").val() != null)
			{				
				var bean = BeanUtil.getCopy(SubResouse);
				
				bean.sub_id = sub_id;
				bean.affix_type = "video";
				bean.affix_status = "history";
				bean.affix_path = $("#history_video").val();				
				bean.add_user = user_id;
				
				if(!ChatRPC.insertLiveVideo(bean))
				{
					alert("添加失败，请重新添加");
				}
			}
		}

		//设置控制参数
		function setOtherParam(paramName,value)
		{
			if(!ChatRPC.setOtherParam(sub_id,paramName,value))
			{
				alert("设置参数失败，请重新设置");
			}
		}
	//-->
	</SCRIPT>	
</head> 
<body topmargin="0" leftmargin="0" style="background: #ECF4FC;"> 
	<input type="hidden" id="handleId" name="handleId" value="H23001">
   <form id="form1" name="form1" action="/InterviewLivePicSubmit" target="targetFrame" method="post" enctype="multipart/form-data">
	<table id="tables" border="0" cellpadding="0" cellspacing="0" width="98%" class="content_lab_table" align="center">
	  <tr>
	   <td width="100px" height="35px" align="right">直播视频：</td>
	   <td width="410px">
	    <input type="text" class="input_border" id="live_video" name="live_video" style="width:405px"/></td>
	   <td >
		<input id="addButton" name="btn1" type="button" onclick="saveLiveVideo()" value="保存" />
	   </td>
	  </tr>
	  <tr>
	   <td height="35px" align="right">历史视频：</td>
	   <td><div style="float:left;margin:auto;"><input type="text" class="input_border" id="history_video" name="history_video" style="width:320px" ></div><div style="float:left"><input type="file"  id="s_history_video_file" name="s_history_video_file"></div></td>
	   <td>
		<input id="addButton" name="btn1" type="button" onclick="saveHistroyVideo()" value="保存" />
	   </td>
	  </tr>
	  <tr>
	   <td height="35px" align="right">互动留言：</td>
	   <td >
	    <input type="radio" id="is_permit_speak" name="is_permit_speak" value="1" onclick="setOtherParam('is_permit_speak',this.value)" checked="true"/>允许&nbsp;&nbsp;<input type="radio" id="is_permit_speak" name="is_permit_speak" value="0" onclick="setOtherParam('is_permit_speak',this.value)"/>不允许</td>
	  </tr>
	  <tr>
	   <td height="35px" align="right">关键词过滤：</td>
	   <td >
	    <input type="radio" id="is_t_keyw" name="is_t_keyw" value="0" onclick="setOtherParam('is_t_keyw',this.value)"  checked="true"/>不启用&nbsp;&nbsp;<input type="radio" id="is_t_keyw" name="is_t_keyw" value="1" onclick="setOtherParam('is_t_keyw',this.value)"/>启用</td>
	  </tr>
	  <tr>
	   <td height="35px" align="right">过滤方式：</td>
	   <td >
	    <input type="radio" id="filter_type" name="filter_type" value="0" onclick="setOtherParam('filter_type',this.value)"  checked="true"/>不允许提交&nbsp;&nbsp;<input type="radio" id="filter_type" name="filter_type" value="1" onclick="setOtherParam('filter_type',this.value)"/>替换成***</td>
	  </tr>
	  <tr>
	   <td height="35px" align="right">昵称过滤：</td>
	   <td >
	    <input type="radio" id="is_t_flink" name="is_t_flink" value="0" onclick="setOtherParam('is_t_flink',this.value)"  checked="true"/>不启用&nbsp;&nbsp;<input type="radio" id="is_t_flink" name="is_t_flink" value="1" onclick="setOtherParam('is_t_flink',this.value)"/>启用</td>
	  </tr>
	  <tr>
		   <td height="32px" >&nbsp;</td>
		   <td><div id="fileQueue"></div></td>
		</tr>
	</table>
 </form>	
 <iframe name="targetFrame" width="0" height="0" frameborder="0" ></iframe>
</body>  
</html>