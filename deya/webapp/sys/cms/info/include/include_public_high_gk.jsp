<%@ page contentType="text/html; charset=utf-8"%>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th style="vertical-align:top;">内容概述：</th>
			<td>
				<textarea id="description" name="description" style="width:90%;height:70px" onblur="checkInputValue('description',true,900,'内容概述','')"></textarea>
			</td>
		</tr>
	</tbody>
</table>

<table id="" class="table_form hidden" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th style="vertical-align:top;">附件：<br />
			<td style="" align="left">
				<table id="add_file_table" class="" style="width:100%" border="0" cellpadding="0" cellspacing="0">
                	
                </table>
			</td>
		</tr>
        <tr>
			<th></th>
			<td>
				<div class="left"><input type="file" name="uploadify_file" id="uploadify_file"/></div>
				<div class="left">&#160;<input id="i005" name="i005" type="button" onclick="javascript:selectFileHandle('saveFileUrl','checkbox');" value="附件库" /></div>
                <!-- <div class="left">&#160;<input id="i005" name="i005" type="button" onclick="javascript:void(0);" value="远程采集" /></div> -->
			</td>
		</tr>				     
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
        <!--
        <tr>
			<th style="vertical-align:top;">审核程序：</th>
			<td>
            	<input id="gk_proc" name="gk_proc" type="text" class="width350" maxlength="80" value="" onblur="checkInputValue('gk_proc',true,900,'审核程序','')"/>
			</td>
		</tr>
        <tr>
			<th>责任部门/科室：</th>
			<td>
				<input id="gk_duty_dept" name="gk_duty_dept" type="text" style="width:350px;" value="" onblur="checkInputValue('gk_duty_dept',true,240,'责任部门/科室','')"/>
			</td>
		</tr>
		-->
        <tr>
			<th>发布机构：</th>
			<td>
				<input id="gk_input_dept" name="gk_input_dept" type="text" style="width:350px;" value="" onblur="checkInputValue('gk_input_dept',true,240,'发布机构','')"/>
			</td>
		</tr>
		<%--<tr>
			<th>定时发布：</th>
			<td style="width:100px;">
				<input id="up_dtime" name="up_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
			</td>
			&lt;%&ndash;<th>定时撤销：</th>
			<td style="width:100px;">
				<input id="down_dtime" name="down_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
			</td>
			<td></td>&ndash;%&gt;
		</tr>--%>
    </tbody>
</table>
<table id="" class="table_form hidden" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		<th>公开时限：</th>
			<td width="135">
				<select id="gk_time_limit" class="input_select" style="width:150px;">
					
				</select>
			</td>
			<th style="width:40px;">公开范围：</th>
			<td width="135">
				<select id="gk_range" class="input_select" style="width:150px;">
					
				</select>
			</td>
            <th style="width:40px;">公开形式：</th>
			<td width="135">
				<select id="gk_way" class="input_select" style="width:150px;">
					
				</select>
			</td>
            <td></td>
        </tr>
	</tbody>
</table>
<table id="timer_publish" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
	<tr>
		<th>定时发布：</th>
		<td style="width:100px;">
			<input id="up_dtime" name="up_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
		</td>
		<!--<th>定时撤销：</th>
		<td style="width:100px;">
			<input id="down_dtime" name="down_dtime" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" />
		</td>-->
		<td></td>
	</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
    	<tr class="hidden">
			<th>索引号：</th>
			<td>
				<input type="text" id="gk_index" name="gk_index" readonly="readonly" style="background-color:#F0F0EE;"  class="width200" value="" />
				<!--<input id="gk_index_handl" name="gk_index_handl" type="checkbox" onclick="showHandlNum(this)"/> 手动编号-->
			</td>
		</tr>
        <tr id="shoudong_num" class="hidden">
			<th>生成年份：</th>
			<td>
				 <input type="text" id="gk_year" name="gk_year" class="width100" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true,readOnly:true})" readonly="readonly"/>&#160;
				 顺序号：<input type="text" id="gk_num" name="gk_num" class="width70"  value="0" maxlength="8" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="ime-mode:Disabled"/>&#160;<input type="button" value="重新生成" onclick="getNewGKIndex()"/>
			</td>
		</tr>
		<tr>
			<th>权重：</th>
			<td>
				<input id="weight" name="weight" type="text" style="width:50px;" value="60"  maxlength="2" onblur="checkInputValue('weight',true,2,'权重','checkNumber')"/>默认值：60，取值范围（0-99）
			</td>
		</tr>
		<tr id="info_staus_tr">
			<th>发布状态：</th>
			<td >
				<ul class="flagClass">
					<li><input id="d" name="info_status" type="radio"  value="0" onclick="isShowAudit(false)" /><label for="d">草稿</label></li>
					<li id="li_ds"><input id="e" name="info_status" type="radio" value="2" checked="checked" onclick="isShowAudit(true)"/><label for="e">待审</label></li>
					<!--<li id="opt_303" class="hidden"><input id="f" name="info_status" type="radio" value="4" /><label for="f">待发</label></li>-->
					<li id="opt_302" class="hidden"><input id="3" name="info_status" type="radio" value="8" onclick="isShowAudit(false)"/><label for="g">发布</label></li>
					<li id="opt_bmsc" class="hidden"><input id="4" name="info_status" type="radio" value="9" onclick="isShowAudit(false)"/><label for="g">保密审查</label></li>
				</ul>
			</td>
		</tr>
        <tr id="audit_tr" class="hidden">
            <th>选择审核步骤：</th>
            <td id="audit_list">

            </td>
        </tr>
	</tbody>
</table>
<div class="sq_box">
<div class="sq_title_box hidden" >
	<div class="sq_title sq_title_plus">高级属性</div>
	<div class="sq_title_right">点击展开</div>
</div>
<div class="sq_box_content  hidden">
<table id="" class="table_form hidden" border="0" cellpadding="0" cellspacing="0">
	<tbody>
    	<tr>
			<th>主题分类：</th>
			<td id="topic_id_td">
				<input type="hidden" id="topic_id" name="topic_id" value="0"><input type="text" id="topic_name" name="topic_name" value="" style="width:80px" readOnly="readOnly">
				<select class="input_select" style="width:150px;" onchange="setChileListToSelect(0,this.value,'topic_id_td')">
					<option value="0"></option>
				</select>               
			</td>
		</tr>
        <tr>
			<th>体裁分类：</th>
			<td id="theme_id_td">
				<input type="hidden" id="theme_id" name="theme_id" value="0"><input type="text" id="theme_name" name="theme_name" value="" style="width:80px" readOnly="readOnly">
				<select class="input_select" style="width:150px;" onchange="setChileListToSelect(0,this.value,'theme_id_td')">
					<option value="0"></option>
				</select>
			</td>
		</tr>
        <tr>
			<th>服务对象分类：</th>
			<td id="serve_id_td">
				<input type="hidden" id="serve_id" name="serve_id" value="0"><input type="text" id="serve_name" name="serve_name" value="" style="width:80px" readOnly="readOnly">
				<select class="input_select" style="width:150px;" onchange="setChileListToSelect(0,this.value,'serve_id_td')">
					<option value="0"></option>
				</select>
               
			</td>
		</tr>
 	</tbody>
</table>
        
<table id="" class="table_form hidden" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
        	 <th>信息有效性：</th>
			 <td class="width150">
				<select id="gk_validity" class="input_select" style="width:150px;">
					
				</select>
			</td>
			<th style="width:40px;">生效日期：</th>
			<td class="width150">
				<input id="effect_dtime" name="effect_dtime" type="text" class="width150" maxlength="80" value=""  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"/>
			</td>
			<th style="width:40px;">废止日期：</th>
			<td class="width150">
				<input id="aboli_dtime" name="aboli_dtime" type="text" class="width150" maxlength="80" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true})" readonly="readonly"/>
			</td>           
			<td></td>
		</tr>
		<tr>			
			<th>信息格式：</th>
			<td class="width150">
				<select id="gk_format" class="input_select" style="width:150px;">
					
				</select>
			</td>
			<th style="width:40px;">载体类型：</th>
			<td class="width150">
				<select id="carrier_type" class="input_select" style="width:150px;">
					
				</select>
			</td>
            <th  style="width:40px;">语种：</th>
			<td class="width150">
				<select id="language" class="input_select" style="width:150px;">
				
				</select>
			</td>
			<td></td>
		</tr>
	</tbody>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
    	 <tr>
			<th valign="top">推荐：</th>
			<td>
				<input id="btn1" name="btn1" type="button" onclick="openFocusPage(infoid,rela_site_id);" value="选择" />
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
		<tr>
			<th>相关文章：</th>
			<td>
				<input id="btn1" name="btn1" type="button" onclick="openLinkInfoDataPage(infoid,rela_site_id)" value="选择文章" />
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
         <!--
         <tr id="info_level_tr">
             <th>稿件评级：</th>
             <td >
                 <ul class="flagClass">
                     <li><input name="info_level" type="radio"  value="A"/><label>A</label></li>
                     <li><input name="info_level" type="radio" value="B" checked="checked" /><label>B</label></li>
                     <li><input name="info_level" type="radio" value="C" /><label>C</label></li>
                     <li><input name="info_level" type="radio" value="D" /><label>D</label></li>
                 </ul>
             </td>
         </tr>
         <tr id="info_level_tr">
             <th>是否IP限制：</th>
             <td >
                 <ul class="flagClass">
                     <li><input name="isIpLimit" type="radio"  value="1"/><label>是</label></li>
                     <li><input name="isIpLimit" type="radio" value="0" checked="checked" /><label>否</label></li>
                 </ul>
             </td>
         </tr>
         -->
	</tbody>
</table>
</div>
</div>
