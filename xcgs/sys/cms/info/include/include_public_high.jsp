<%@ page contentType="text/html; charset=utf-8"%>
<%
	String infoid = request.getParameter("info_id");
%>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">内容摘要：</th>
			<td>
				<textarea id="description" name="description" style="width:90%;height:70px" onblur="checkInputValue('description',true,900,'内容摘要','')"></textarea>
			</td>
		</tr>
		
		<tr id="info_staus_tr">
			<th>发布状态：</th>
			<td >
				<ul class="flagClass">
					<li><input id="d" name="info_status" type="radio"  value="0"/><label for="d">草稿</label></li>
					<li id="li_ds"><input id="e" name="info_status" type="radio" value="2" checked="checked" /><label for="e">待审</label></li>					
					<li id="opt_303" class="hidden"><input id="f" name="info_status" type="radio" value="4" /><label for="f">待发</label></li>
					<li id="opt_302" class="hidden"><input id="3" name="info_status" type="radio" value="8" /><label for="g">发布</label></li>
				</ul>
			</td>
		</tr>
        <tr>
			<th valign="top">推荐：</th>
			<td>
				<input id="btn1" name="btn1" type="button" onclick="openFocusPage('<%=infoid%>');" value="选择" />
			</td>
		</tr>
		<tr>
			<th valign="top"> </th>
			<td>
				<div>
					<table id="focusWare" class="" style="width:100%;" border="0" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</td>
		</tr>
	</tbody>
</table>
<div class="sq_box">
	<div class="sq_title_box" >
		<div class="sq_title sq_title_plus">高级属性</div>
		<div class="sq_title_right">点击展开</div>
	</div>
	<div class="sq_box_content hidden ">
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">META关键词：</th>
			<td>
				<textarea id="info_keywords" name="info_keywords" style="width:90%;height:70px"></textarea>
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">META网页描述：</th>
			<td>
				<textarea id="info_description" name="info_description" style="width:90%;height:70px"></textarea>
			</td>
		</tr>
		<tr>
			<th>评论：</th>
			<td>
				<ul>
					<li><input id="is_allow_comment" name="is_allow_comment" type="checkbox"  value="1"/><label for="is_allow_comment">允许</label></li>
				</ul>
			</td>
		</tr>
		<tr>
			<th>权重：</th>
			<td>
				<input id="weight" name="weight" type="text" style="width:50px;" value="60" maxlength="2" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/>默认值：60，取值范围（0-99）
			</td>
		</tr>		
	</tbody>
</table>
<table id="timer_publish" class="table_form hidden" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>定时发布：</th>
			<td style="width:100px;">
				<input id="up_dtime" name="up_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
			</td>
			<th>定时撤销：</th>
			<td style="width:100px;">
				<input id="down_dtime" name="down_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
			</td>
			<td></td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>相关文章：</th>
			<td>
				<input id="btn1" name="btn1" type="button" onclick="openLinkInfoDataPage('<%=infoid%>')" value="选择文章" />
				<input id="btn1" name="btn1" type="button" onclick="addRelatedHand()" value="手动添加" />
			</td>
		</tr>
		<tr>
			<th></th>
			<td align="left">
				<table align="left" id="relateInfos" class="width:300px" border="0" cellpadding="0" cellspacing="0">
				</table>
			</td>
		</tr>
	</tbody>
</table>
	</div>
</div>
