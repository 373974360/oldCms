<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<title>网友留言</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script type="text/javascript" src="js/liveManager.js"></script>
	<style>
	
	TD,DIV{font-size:12px;color:355C86}	

	.blankH5{height:5px;clear:both;overflow:hidden;}
	.blankH90{height:90px;clear:both;overflow:hidden;}

	.l_div_body{width:100%;float:left;margin:auto;100%;height:28px;background-image:url('../../images/live_label_back.gif');}	
	.l_div_body .l_div_left{float:left;margin:auto;width:5px;height:28px;background-image:url('../../images/gpps_sprites.gif');background-position:-561px -72px;}	
	.l_div_body .l_div_right{float:right;margin:auto;width:5px;height:28px;background-image:url('../../images/gpps_sprites.gif');background-position:-569px -72px;}


	.lable_text{float:left;margin:auto;margin-top:8px;margin-left:8px;font-weight:bold;width:100px}
	.lable_button{float:right;margin:auto;margin-top:8px;margin-left:8px;font-weight:bold;width:150px}
	
	#text_area_editor{height:520px;text-align:center;border-left:1px solid #A0B2C8;border-right:1px solid #A0B2C8;border-bottom:1px solid #A0B2C8;padding-top:2px;text-align:left}
		
	#c_user_count{float:left;margin:auto;margin-top:8px;margin-left:8px;width:100px;}
	</style>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var id = parent.id;
		var sub_id = parent.sub_id;
		var text_start_num = 0;
		var pic_start_num = 0;
		$(document).ready(function () {	
			getAllMessage();
			
		});

		function getAllMessage()
		{
			var m = ChatRPC.getAllMessageList(sub_id,pic_start_num,text_start_num);			
			if(m != null)
			{
				m = Map.toJSMap(m);				
				setTextAreaInfo(m);				
			}

			//得到在线人数
			try{
			$("#c_user_count").html(parent.zbgl_frame.getCurrentUserCount());
			}catch(e)
			{}
		}

		setInterval("getAllMessage()",3000);

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
					var content = textList.get(i).content;
					if(content.indexOf("xheditor_emot") > -1)
					{
						content = content.replace(/..\/..\/js\/xheditor\/xheditor_emot/ig,"js/xheditor/xheditor_emot");
						
					}
					$("#text_area_editor").append("<div style='padding:8px;'><strong>"+textList.get(i).chat_user+"：</strong>"+content+"</div>");					
				}					

				if(document.getElementById("text_area_editor").scrollHeight>document.getElementById("text_area_editor").offsetHeight)
				{								
					document.getElementById("text_area_editor").scrollTop=document.getElementById("text_area_editor").scrollHeight-document.getElementById("text_area_editor").offsetHeight+5;
				}
			}
		}

		
	//-->
	</SCRIPT>	
</head> 
<body topmargin="0" leftmargin="0" style="background: #ECF4FC;"> 
	<input type="hidden" id="handleId" name="handleId" value="H23001">
	<table id="tables" border="0" cellpadding="0" cellspacing="0" width="98%" class="content_lab_table" align="center">
	  <tr>
	   <td>	    
		<div class="l_div_body" style="float:left"><div class="l_div_left"></div><div id="video_lable_text" class="lable_text">网友留言</div><div id="c_user_count"></div><div class="l_div_right"></div></div>	   
	     <div id="text_area_editor" style="float:left;width:100%"></div>
	   </td>
	  </tr>
	</table>
	
</body>  
</html>