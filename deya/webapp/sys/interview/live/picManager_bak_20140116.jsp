<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>精彩图片管理</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css" />
	<script language="javascript" src="../../js/jquery.uploadify.js"></script>
	<script language="javascript" src="../../js/uploadFile/swfobject.js"></script>
	<script type="text/javascript" src="js/liveManager.js"></script>
	
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var id = parent.id;
		var sub_id = parent.sub_id;
		var site_id = parent.site_id;
		$(document).ready(function () {	
			subjectBean = parent.subjectBean;
			setDivHeight();
			initUPLoadImg("for_pic_file","fileName");
			//主题状态为直播状态
			if(subjectBean.status == 1)
			{
				getLivePicList();
			}else
			{
				//主题状态为历史或预告状态，从数据库中取数据
				getLivePicListByDB();
			}
		});

		function setDivHeight()
		{
			var page_height = document.body.clientHeight-20;
			
			$("#pic_list").css("height",page_height);
			$("#SubResouse").css("height",page_height);
			
		}

		//从数据库中得到精彩视频
		function getLivePicListByDB()
		{
			var l = ChatRPC.getResouseList(sub_id,"pic","live");
			if(l != null)
			{
				setPicToDiv(l);
			}
		}

		//得到精彩图片列表
		function getLivePicList()
		{
			var srList = ChatRPC.getLivePicList(sub_id,0);
			if(srList != null)
			{
				setPicToDiv(srList);
			}
		}

		//加入图片到div
		function setPicToDiv(list)
		{
			list = List.toJSList(list);				
			for(var i=0;i<list.size();i++)
			{
				if(list.get([i]) != null)
					setPicToDIVList(list.get([i]));
			}
		}


		function uploadImg()
		{
			var str = $("#fileName").val();				

			var val=new Validator();	
			val.checkEmpty(str,"上传图片");
			val.checkStrLength($("#affix_name").val(),"图片名称",240);
			val.checkStrLength($("#description").val(),"图片说明",3000);

			if(!val.getResult()){
				val.showError("error_div");
				return;
			}

			if(!checkImgFile(str))
			{
				parent.alertWar("<span style='color:red'>上传的文档图片格式不对，只允许上传 jpg，jpeg，gif，png 等格式的文件，请重新上传！</span>"); 
				return; 
			}

			$("#form1").submit();
			
		}
		

		//判断图片格式
		function checkImgFile(files) 
		{ 
			if(files.replace(/(^\s*)|(\s*$)/g,"") != "")
			{    		  
				if(files.indexOf(".") == -1) 
				{ 					
					return false; 
				}
				var strtype = files.match(/\.([^\.]+)(\?|$)/)[1];
				if (strtype.toLowerCase()=="jpg"||strtype.toLowerCase()=="gif"||strtype.toLowerCase()=="bmp"||strtype.toLowerCase()=="png") 
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
				var bean = BeanUtil.getCopy(SubResouse);
				
				bean.sub_id = sub_id;
				bean.affix_type = "pic";
				bean.affix_status = "live";
				bean.affix_path = furl;
				bean.user_name = user_name;
				bean.add_user = user_id;
				bean.affix_name = $("#affix_name").val();
				bean.description = $("#description").val();
				
				if(ChatRPC.insertLivePic(bean))
				{
					setPicToDIVList(bean);
					$("#fileName").val("");
					$("#affix_name").val("");
					$("#description").val("");

				}else
				{
					top.msgWargin("上传失败，请重新上传");
				}
			}			
		}

		function saveImg()
		{
			if($("#fileName").val() == "")
			{
				top.msgWargin("请添加图片");
			}
			else
			{
				var bean = BeanUtil.getCopy(SubResouse);
				
				bean.sub_id = sub_id;
				bean.affix_type = "pic";
				bean.affix_status = "live";
				bean.affix_path = $("#fileName").val();
				bean.user_name = user_name;
				bean.add_user = user_id;
				bean.affix_name = $("#affix_name").val();
				bean.description = $("#description").val();

				if(ChatRPC.insertLivePic(bean))
				{
					setPicToDIVList(bean);
					$("#fileName").val("");
					$("#affix_name").val("");
					$("#description").val("");

				}else
				{
					top.msgWargin("添加失败，请重新添加");
				}
			}
		}

		//向图片列表中插入图片
		function setPicToDIVList(bean)
		{
			var str = '<div style="height:200px;text-align:center"><img src="'+bean.affix_path+'" width="230px" height="180px" ><div class="blankH3"></div><span class="wargin_span" onclick="deletePic(this,'+bean.id+')">[删除]</span></div>';
			$("#pic_list").prepend(str);			
		}

		//删除图片信息
		function deletePic(obj,id,index_num)
		{
			if(ChatRPC.deleteLivePic(sub_id,id))
			{
				$(obj).parent().remove();
			}
			else
			{
				top.msgWargin("删除失败，请重新删除");
			}
		}
	//-->
	</SCRIPT>	
</head> 
<body topmargin="0" leftmargin="0" style="background: #ECF4FC;"> 
	<input type="hidden" id="handleId" name="handleId" value="H23001">
	<table id="tables" border="0" cellpadding="0" cellspacing="0" width="98%" class="content_lab_table" align="center">
	  <tr>
	   <td width="310px">
	    <div id="pic_list" style="background:#ffffff;border:1px solid #9FB2C7;width:300px;height:550px;overflow:auto;padding:5px">
		</div>
	   </td>
		<td>		 
	    <div style="background:#ffffff;border:1px solid #9FB2C7;width:350px;height:550px;overflow:auto;padding:5px" id="SubResouse">
		 
		 <input type="hidden" id="actionType" name="actionType" value="jctp">
		 <div style="height:20px">请选择要上传的图片：</div>
		 <div ><div style="float:left"><input type="text" name="fileName" id="fileName" style="width:240px" >&nbsp;</div><div style="float:left"><input type="file"  id="for_pic_file" name="for_pic_file"></div></div>
		 <span class="blank3"></span>
		 <div style="height:20px">图片名称：</div>
		 <div style="height:35px"><input type="text" class="input_border" name="affix_name" id="affix_name" style="width:330px" maxlength="80"></div>
		 <div style="height:20px">图片说明：</div>
		 <textarea class="input_border" style="width:330px;height:100px" id="description" name="description"></textarea>
		 <span class="blank12"></span>
		 <div style="text-align:center">
			<input id="addButton" name="btn1" type="button" onclick="saveImg()" value="保存" />	
		 </div>
		</div>
	   </td>
	  </tr>
	</table>
	<iframe name="targetFrame" width="0" height="0" frameborder="0" ></iframe>
</body>  
</html>