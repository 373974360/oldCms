<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 

	<title>访谈主题分类添加</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="../../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
	<script src="js/subjectList.js"></script>

	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var defaultBean = subjectBean;
		$(document).ready(function () {	
			setContentAreaHeight();

			var categoryList = SubjectRPC.getSubCategoryAllName();
			categoryList = List.toJSList(categoryList);
			if(categoryList != null)
			{
				$("#category_id").addOptions(categoryList,"category_id","category_name");
			}			
			
			var s_id = request.getParameter("id");
			
			if(s_id != null && s_id.trim() != "")
			{
			
				defaultBean = SubjectRPC.getSubjectObj(s_id);
								
				if(defaultBean)			
				{
					$("#subject").autoFill(defaultBean);
				
					var resouseList = SubjectRPC.getResouseList(defaultBean.sub_id,"forecast","");
					
					if(resouseList != null)
					{
						resouseList = List.toJSList(resouseList);
						for(var i=0;i<resouseList.size();i++)
						{
							if(resouseList.get(i).affix_path != "")
							{
								if(resouseList.get(i).affix_type == "pic")
								{
									$("#s_for_pic").val(resouseList.get(i).affix_path);
									$("#pic_div").show();				
									$("#pic_div").find("img").attr("src",resouseList.get(i).affix_path);
									
								}
								else
								{
									$("#s_for_video").val(resouseList.get(i).affix_path);
									$("#video_div").show();				
									$("#video_div").html('<embed height="300" width="300" src="'+resouseList.get(i).affix_path+'" mediatype="video" autostart="true" loop="true" menu="true" sck_id="0"></embed>');
								}
							}
						}
					}
				}					
				$("#subButton").click(updateSubject);
			
			}
			else
			{
				$("#subButton").click(saveSubject);
			}
		}); 

		function setContentAreaHeight()
		{
			setDivHeightAndContentNum();			
			$("#content_area").css("height",div_height-29);
			$("#form_div").css("height",div_height-30);
		}

		function showForPic(obj,divName)
		{			
				if($(obj).val() != "")
				{
					$("#"+divName).show();	
					if(divName == "pic_div")
						$("#"+divName).find("img").attr("src",$(obj).val());
					if(divName == "video_div")
						$("#"+divName).html('<embed height="300" width="300" src="'+$(obj).val()+'" mediatype="video" autostart="true" loop="true" menu="true" sck_id="0"></embed>')
				}
				else
					$("#"+divName).hide();				
		}
		
	//-->
	</SCRIPT>	
</head> 
<body> 
	<input type="hidden" id="handleId" name="handleId" value="H22001,H23001">	
	<table border="0" cellpadding="0" cellspacing="0" width="99%" class="right_body">
	  <tr>
	   <td class="b_top_l"></td>
	   <td class="b_top_c">
	    <!-- 当前位置区域　开始 -->
		<div class="blankW3"></div>
		<div class="cur_position_img"></div>
		<div class="cur_position_text">当前位置：访谈直播 >> 创建访谈主题</div>
		<!-- 当前位置区域　结束 -->
	   </td>
	   <td class="b_top_r"></td>
	  </tr>
	  <tr>
	   <td class="b_center_l"></td>
	   <td class="b_center_c" align="left"  valign="top">	
	     <table id="tables" border="0" cellpadding="0" cellspacing="0" width="99%" class="content_lab_table">
		  <tr id="lable_tr">
		   <td width="80px" height="26px" align="center" class="content_lab_check" id="jcxx" onclick="changeContentLable(this)">基础信息</td>
		   <td width="80px" height="26px" align="center" class="content_lab" id="splj" onclick="changeContentLable(this)">视频链接</td>
		   <td width="80px" height="26px" align="center" class="content_lab" id="xwg" onclick="changeContentLable(this)">新闻稿</td>
		   <td class="content_lab">&nbsp;</td>
		  </tr>
		 </table>		 
		 <!-- 表单区域 开始-->
		 <table border="0" cellpadding="0" cellspacing="0" width="99%" style="margin-left:10px" id="subject">	
		  <tr>
		   <td id="content_area" colspan="4" class="content_form_td" valign="top">
		     <!-- 隐含字段区域　开始 -->
			 <input type="hidden" id="id" name="id" value="0"/>
			 <input type="hidden" id="sub_id" name="sub_id"/>
			 <input type="hidden" id="add_user" name="apply_user"/> <!-- 登录人ID -->
			 <input type="hidden" id="add_user" name="apply_dept"/> <!-- 部门ID -->
			 <input type="hidden" id="user_name" name="user_name"/>	 <!-- 登录人名 -->	 
			 <!-- 隐含字段区域　结束 -->
		    <div id="form_div" style="overflow:auto">
		    <table id="jcxx_tab" border="0" cellpadding="0" cellspacing="0" width="100%" >
			 <tr>
			  <td height="32px" width="28px" class="content_td">&nbsp;</td>
			  <td align="right" width="90px" class="content_td">访谈主题：</td>
			　<td class="content_td"><input type="text" id="sub_name" name="sub_name" style="width:374px">&nbsp;<span style="color:red">*</span></td>
			 </tr>
			 <tr>
			  <td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" class="content_td">访谈分类：</td>
		      <td class="content_td"><select id="category_id" name="category_id" style="width:150px"></select></td>
			 </tr>
			 <tr>
			  <td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" class="content_td">访谈简介：</td>
		      <td class="content_td" style="padding-top:3px;padding-bottom:3px;"><textarea id="intro" name="intro" style="width:372px;height:84px"></textarea></td>
			 </tr>
			 <tr>
			  <td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" class="content_td">开始时间：</td>
		      <td class="content_td"><input class="Wdate" type="text" name="start_time" id="start_time" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="true" style="width:150">&nbsp;&nbsp;结束时间：<input class="Wdate" type="text" name="end_time" id="end_time" size="11" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="true" style="width:150">
			  </td>
			 </tr>
			 <tr id="main_data">
			  <td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" class="content_td">审核状态：</td>
		      <td class="content_td"><input type="radio" id="audit_status" name="audit_status" value="0" checked="checked">待审核&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="audit_status" name="audit_status" value="1">审核通过&nbsp;&nbsp;<input type="radio" id="audit_status" name="audit_status" value="-1">审核不通过</td>
			 </tr>
			 <tr id="main_data">
			  <td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" class="content_td">发布状态：</td>
		      <td class="content_td"><input type="radio" id="publish_status" name="publish_status" value="0" checked="checked">未发布&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="publish_status" name="publish_status" value="1"   >已发布&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="publish_status" name="publish_status" value="2" >已归档&nbsp;&nbsp;<input type="radio" id="publish_status" name="publish_status" value="-1" >已撤消</td>
			 </tr>
			 <tr id="main_data">
			  <td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" class="content_td">访谈状态：</td>
		      <td class="content_td"><input type="radio" id="status" name="status" value="0"  checked="checked">预告状态&nbsp;&nbsp;<input type="radio" id="status" name="status" value="1" >直播状态&nbsp;&nbsp;<input type="radio" id="status" name="status" value="2" >历史状态</td>
			 </tr>
			</table>
			<table id="splj_tab" border="0" cellpadding="0" cellspacing="0" width="100%" style="display:none">
			    <tr><td height="10px" colspan="3">&nbsp;</td></tr>
				<tr>
					<td height="32px" width="28px" class="content_td">&nbsp;</td>
					<td align="right" width="90px" class="content_td" valign="top" style="padding-top:7px">预告图片：</td>
					<td class="content_td" ><input type="text" id="s_for_pic" name="s_for_pic" style="width:374px" onblur="showForPic(this,'pic_div')"><div id="pic_div" style="width:100%;height:150px;display:none"><img src=""></img></div></td>
				</tr>
				<tr>
					<td height="32px" class="content_td">&nbsp;</td>
					<td align="right" class="content_td" valign="top" style="padding-top:7px">预告视频：</td>
					<td class="content_td"><input type="text" id="s_for_video" name="s_for_video" style="width:374px" onblur="showForPic(this,'video_div')"><div id="video_div" style="width:100%;height:150px;display:none"></div></td>
				</tr>				
			</table>
			<table id="xwg_tab" border="0" cellpadding="0" cellspacing="0" width="100%" style="display:none">
			    <tr><td height="10px" colspan="2">&nbsp;</td></tr>
				<tr>
					<td height="32px" width="28px" class=""></td>
					<td width="98%" ><textarea id="info" name="intro" style="width:98%;height:400px"></textarea></td>
				</tr>
				
			   </table>
			</div>
	   <!-- 表单区域 结束-->
		</td>
		  </tr>
		 </table>
	   </td>
	   <td class="b_center_r"></td>
	  </tr>
	  <tr>
	   <td class="b_bottom_l"></td>
	   <td class="b_bottom_c" align="center">
	    <!-- 按钮区域 开始-->
	     <div style="width:200px;height:24px">
		 <a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span id="subButton">&nbsp;保存</span></a>
		 <a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="fnReload()">&nbsp;重置</span></a>
		 <a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="window.location.href = 'subjectList.html'">&nbsp;取消</span></a>
		 </div>
       <!-- 按钮区域 结束-->
	   </td>
	   <td class="b_bottom_r"></td>
	  </tr>
	 </table> 

</body> 

</html> 

