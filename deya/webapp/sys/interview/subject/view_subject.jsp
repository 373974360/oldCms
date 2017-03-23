<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>访谈主题分类添加</title> 
	<jsp:include page="../../include/include_tools.jsp"/>
	<script src="js/subjectList.js"></script>
	
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var defaultBean = subjectBean;
		$(document).ready(function () {	

			if(request.getParameter("tn") != "main")
				$("#subject").find("#main_data").hide();			
			
			var s_id = request.getParameter("id");
			
			if(s_id != null && s_id.trim() != "")
			{
			
				defaultBean = SubjectRPC.getSubjectObj(s_id);
								
				if(defaultBean)			
				{
					$("#subject").autoFill(defaultBean);
				}

				var cBean = SubjectRPC.getSubjectCategoryBeanByCID(defaultBean.category_id);
				$("#category_name").val(cBean.category_name);
			}
			initButtomStyle();
			init_input();
			init_FromTabsStyle();

			disabledWidget();
		}); 	

		function fnPreview(obj,url)
		{
			$("#video_td").html('<div style="clear:both"><embed id="live_video_embed" height="150" width="200" src="'+url+'" mediatype="video" autostart="true" loop="true" menu="true" sck_id="0"></embed></div>');			
		}

		var ifgetVideoInfo = false;//只读取一次
		var ifgetActorInfo = false;
		var ifgetReleInfo = false;
		function changeContentLable2(obj)
		{
			$("#lable_tr").find("TD").attr("class","content_lab");
			$(obj).attr("class","content_lab_check");
			var c_id = $(obj).attr("id")+"_tab";
			
			$("#form_div > table").hide();


			$("#"+c_id).show();

			if($(obj).attr("id") == "splj")
			{
				if(!ifgetVideoInfo)
					getVideoInfo();
			}
			if($(obj).attr("id") == "jbzl")
			{
				if(!ifgetActorInfo)
					getActorInfo();
			}
			if($(obj).attr("id") == "xgxx")
			{
				if(!ifgetReleInfo)
					getReleInfo();
			}
		}
		
		function getReleInfo()
		{
			var SubjectRPC = jsonrpc.SubjectRPC;
			var subjectReleInfo = new Bean("com.deya.wcm.bean.interview.SubReleInfo",true);			
			var beanList = null;
			var table = null;

			table = new Table();	
			var colsMap = new Map();	
			var colsList = new List();	
			
			colsList.add(setTitleClos("info_name","信息标题","","25px","",""));	
			colsList.add(setTitleClos("publish_flag","发布状态","120px","","",""));		
			colsList.add(setTitleClos("add_user","创建人","80px","","",""));			
			
			table.setColsList(colsList);
			table.setAllColsList(colsList);				
			table.enableSort=false;
			table.checkBox=false;
			table.onSortChange = showList;
			table.show("table_xgxx");

			beanList = SubjectRPC.getReleInfoListBySub_id("",0,SubjectRPC.getReleInfoCountBySub_id(defaultBean.sub_id),defaultBean.sub_id);		
			beanList = List.toJSList(beanList);
			curr_bean = null;		
			table.setBeanList(beanList,"td_list");
			table.show();	

			table.getCol("info_name").each(function(i){
				if(i>0)
				{
					$(this).css({"text-align":"left"});		
					$(this).css({"padding-left":"10px"});
				}
			});

			table.getCol("publish_flag").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
				if(i>0)
				{
					if(beanList.get(i-1).publish_flag == 0)
						$(this).html("未发布&#160;");
					if(beanList.get(i-1).publish_flag == 1)
						$(this).html("已发布&#160;");
					if(beanList.get(i-1).publish_flag == -1)
						$(this).html("已撤消&#160;");
				}
			});

			$("#table_xgxx").find("#contents_list").css("height","340px");
			ifgetReleInfo = true;
		}

		function getVideoInfo()
		{			
			var resouseList = SubjectRPC.getResouseListByManager(defaultBean.sub_id);
			$("#subject").find("input").each(function(){
				if($(this).attr("type") == "radio")
				{
					$(this).attr("disabled",true);
				}
			});
			
			if(resouseList != null)
			{
				resouseList = List.toJSList(resouseList);
				for(var i=0;i<resouseList.size();i++)
				{
					if(resouseList.get(i).affix_path != "")
					{
						if(resouseList.get(i).affix_type == "pic")
						{
							$("#s_for_pic").html('<div><div style="float:left;margin:auto;padding-top:3px;width:400px">'+resouseList.get(i).affix_path+'</div><div style="float:left;margin-left:12px"><a class="button"   hidefocus="true" id="preview_pic_button"><span id="" onclick="fnPreview(\'pic\',\''+resouseList.get(i).affix_path+'\')">&nbsp;预览</span></a><div></div>');	
						}
						else
						{	
							var str = '<div><div style="float:left;margin:auto;padding-top:3px;width:400px;border:">'+resouseList.get(i).affix_path+'</div><div style="float:left;margin-left:12px"><a class="button"  hidefocus="true" id="preview_pic_button"><span id="" onclick="fnPreview(\'video\',\''+resouseList.get(i).affix_path+'\')">&nbsp;预览</span></a><div></div>';

							if(resouseList.get(i).affix_status == "forecast")
							{
								$("#s_for_video").html(str);
							}
							if(resouseList.get(i).affix_status == "live")
							{
								$("#s_live_video").html(str);
							}
							if(resouseList.get(i).affix_status == "history")
							{
								$("#s_history_video").html(str);
							}
						}
					}
				}
			}
			ifgetVideoInfo = true;
		}

		function getActorInfo()
		{
			var SubjectRPC = jsonrpc.SubjectRPC;
			var subjectActor = new Bean("com.deya.wcm.bean.interview.SubjectActor",true);	
			var beanList = null;
			var table = null;
			
			table = new Table();	
			var colsMap = new Map();	
			var colsList = new List();	
			
			colsList.add(setTitleClos("actor_name","嘉宾名称","","25px","",""));
			colsList.add(setTitleClos("sex","性别","60px","","",""));	
			colsList.add(setTitleClos("a_post","职务","100px","","",""));
			colsList.add(setTitleClos("add_user","创建人","80px","","",""));					
			
			table.setColsList(colsList);
			table.setAllColsList(colsList);				
			table.enableSort=false;
			table.checkBox=false;
			table.onSortChange = showList;
			table.show("table_jbzl");

			beanList = SubjectRPC.getActorList("",defaultBean.sub_id);	
			beanList = List.toJSList(beanList);	
			curr_bean = null;		
			table.setBeanList(beanList,"td_list");
			table.show();

			table.getCol("actor_name").each(function(i){//为某一列的每个单元格增加点击事件(常用于给按钮增加事件)
				if(i>0)
				{
					$(this).css({"text-align":"left"});
					$(this).css({"padding-left":"10px"});
				}
			});
			
			$("#table_jbzl").find("#contents_list").css("height","340px");
			ifgetActorInfo = true;
		}

		function fnPreview(a_type,path)
		{
			if(a_type == "pic")
			{
				$("#preview_td").html('<img src="'+path+'" width="200px" height="150px">');
			}
			else
			{
				$("#preview_td").html('<embed id="live_video_embed" height="150" width="200" src="'+path+'" mediatype="video" autostart="true" loop="true" menu="true" sck_id="0"></embed>');
			}

		}
	//-->
	</SCRIPT>		
</head> 
<body> 	
<table class="table_option" border="0" cellpadding="0" cellspacing="0" align="left">
	<tr>
		<td align="left" class="fromTabs width10" style="">	
			<span class="blank3"></span>
		</td>
		<td align="left" width="100%">
			<ul class="fromTabs">
				<li class="list_tab list_tab_cur">
					<div class="tab_left">
						<div class="tab_right" >基础信息</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >视频链接</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >新闻稿</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >嘉宾资料</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >相关信息</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >图文区文字记录</div>
					</div>
				</li>
				<li class="list_tab">
					<div class="tab_left">
						<div class="tab_right" >互动区文字记录</div>
					</div>
				</li>
			</ul>
		</td> 	
		
	</tr>
</table>
<span class="blank12"></span>
<div id="subject">
<div class="infoListTable" id="listTable_0">
<table id="jcxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>访谈主题ID：</th>
			<td id=""><input type="text" id="sub_id" name="sub_id" class="width300" ></td>
			 </tr>
			 <tr>
				<th>访谈主题：</th>
			<td id=""><input type="text" id="sub_name" name="sub_name" class="width300" ></td>
			 </tr>
			 <tr>			
				<th>访谈模型：</th>
		      <td id=""><input type="text" id="category_name" name="category_name" class="width300" ></td>
			 </tr>			 
			 <tr>
				<th>开始时间：</th>
		      <td class="content_td"><input type="text" id="start_time" name="start_time" class="width150" >&nbsp;&nbsp;结束时间：<input type="text" id="end_time" name="end_time" class="width150" >
				</td>
			 </tr>
			 <tr>
				<th style="vertical-align:top;">访谈简介：</th>
		      <td class="content_td"><textarea id="intro" name="intro" style="width:585px;height:50px;" onblur="checkInputValue('intro',true,1000,'访谈简介','')"></textarea></td>
			 </tr>
			 <tr id="main_data">
				<th>审核状态：</th>
		      <td class="content_td"><input type="radio" id="audit_status" name="audit_status" value="0" checked="checked">待审核&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="audit_status" name="audit_status" value="1">审核通过&nbsp;&nbsp;<input type="radio" id="audit_status" name="audit_status" value="-1">审核不通过</td>
			 </tr>
			 <tr id="main_data">			 
				<th>发布状态：</th>
		      <td class="content_td"><input type="radio" id="publish_status" name="publish_status" value="0" checked="checked">未发布&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="publish_status" name="publish_status" value="1"   >已发布&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="publish_status" name="publish_status" value="2" >已归档&nbsp;&nbsp;<input type="radio" id="publish_status" name="publish_status" value="-1" >已撤消</td>
			 </tr>
			 <tr id="main_data">
				<th>访谈状态：</th>
		      <td class="content_td"><input type="radio" id="status" name="status" value="0"  checked="checked">预告状态&nbsp;&nbsp;<input type="radio" id="status" name="status" value="1" >直播状态&nbsp;&nbsp;<input type="radio" id="status" name="status" value="2" >历史状态</td>
			 </tr>
			 <tr>
				<th>参与人数：</th>
		      <td id="count_user"></td>
			 </tr>
	</tbody>
</table>
</div>

<div class="infoListTable hidden" id="listTable_1">
<table id="splj_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>			
			<th>访谈图片：</th>
			<td id="s_for_pic">&nbsp;</td>
		</tr>
		<tr>			
			<th>预告视频：</th>
			<td id="s_for_video">&nbsp;</td>
		</tr>
		<tr>
			<th>直播视频：</th>
			<td id="s_live_video">&nbsp;</td>
		</tr>
		<tr>
			<th>历史视频：</th>
			<td id="s_history_video">&nbsp;</td>
		</tr>
		<tr>
		   <th></th>
		   <td  id="preview_td"></td>
		</tr>
	</tbody>
</table>
</div>
<div class="infoListTable hidden" id="listTable_2">
<table id="xwg_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th></th>
			<td width="98%" ><div id="info" style="width:98%;height:340px;overflow:auto;line-height:18px"></div></td>
		</tr>
	</tbody>
</table>
</div>
<div class="infoListTable hidden" id="listTable_3">
<table id="jbzl_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th></th>
			<td width="98%" ><div id="table_jbzl"></div></td>
		</tr>
	</tbody>
</table>
</div>
<div class="infoListTable hidden" id="listTable_4">
<table id="xgxx_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
					<th></th>
					<td width="98%" ><div id="table_xgxx"></div></td>
				</tr>	
	</tbody>
</table>
</div>
<div class="infoListTable hidden" id="listTable_5">
<table id="twq_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
					<th></th>
					<td width="98%" ><div id="history_record_pic" style="width:98%;height:340px;overflow:auto;line-height:18px"></div></td>
				</tr>	
	</tbody>
</table>
</div>
<div class="infoListTable hidden" id="listTable_6">
<table id="hdq_tab" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th></th>
			<td width="98%" ><div id="history_record_text" style="width:98%;height:340px;overflow:auto;line-height:18px"></div></td>
		</tr>
	</tbody>
</table>
</div>
</div>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="userAddButton" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</body> 

</html> 

