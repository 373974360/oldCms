<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加元数据</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/jquery_selecter.js"></script>
<script type="text/javascript" src="js/fields_add.js"></script>
<script type="text/javascript" src="js/fieldsAddUtil.js"></script>
<script type="text/javascript">
var tab_index = "${param.tab_index}";
var val=new Validator();

var type = request.getParameter("type");
var id = request.getParameter("id");

$(document).ready(function(){
	initButtomStyle();
	init_input();	

	$("label").unbind("click");
	
	//var model_id = request.getParameter("model_id");
	if(type){
		if(type=='mod'){
			//$("#titleId").html('修改字段')
			//给确定按钮添加点击事件
	        $("#subButton").click(function(){
	                          	doAdd("mod")
	                       });
			fullFields(id);//填充表单
		}else if(type=='view'){
			$("#subButtonA").hide();//隐藏确定按钮
			fullFields(fields_id);//填充表单
		}
	}else{
		 //给确定按钮添加点击事件
	         $("#subButton").click(function(){
	                          	doAdd("add")
	                       });
	}
	
	
	//init_page();
});

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="form_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
	   <tr>
			<th><span class="f_red">*</span>字段名称：</th>
			<td >
				c_<input id="field_enname" name="field_enname" type="text" class="width200" style="width: 186px" value="" onblur="checkInputValue('field_enname',false,20,'字段名称','checkLowerLetter')"/>
			    注：字段名只能是小写字母
			</td>	
		</tr>
		<tr>
			<th><span class="f_red">*</span>字段别名：</th>
			<td >
				<input id="field_cnname" name="field_cnname" type="text" class="width200" value="" onblur="checkInputValue('field_cnname',false,60,'字段别名','')"/>
			    注：显示在表单中的字段别名
			</td>			
		</tr>
		<tr>
			<th>字段提示：</th>
			<td >
				<input id="field_text" name="field_text" type="text" class="width200" value="" />
			    注：显示在字段别名后方作为重要提示的文字 
			</td>			
		</tr>
		<tr>
			<th>是否必填：</th>
			<td >
			<input type="radio" id="is_null_1" name="is_null" value="1"/><label>是</label> 
			 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="is_null_2" name="is_null" value="0"  checked="checked"/><label>否</label>
			   <label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：生成的表单中该字段是否为必填 <label>
			</td>			
		</tr>
		<tr>
			<th>是否显示：</th>
			<td >
			<input type="radio" id="is_display_1" name="is_display" value="1" checked="true"/><label>是</label> 
			 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="is_display_2" name="is_display" value="0" /><label>否</label>
			   <label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：不显示的字段将不会出现在页面，其值是默认值 <label>
			</td>			
		</tr>
		<tr>
			<th>字段类型：</th>
			<td id="ttt">
			<input type="radio" id="field_type0" name="field_type" value="0" checked="true" onclick="selectType0()"/><label>单行文本</label> 
			 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="field_type1" name="field_type" value="1" onclick="selectType1()"/><label>多行文本</label>
			 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="field_type2" name="field_type" value="2" onclick="selectType2()"/><label>多行文本（支持HTML）</label>  
			 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="field_type3" name="field_type" value="3" onclick="selectType3()"/><label>选项</label>  
			 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="field_type4" name="field_type" value="4" onclick="selectType4()"/><label>数字</label>
			 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="field_type5" name="field_type" value="5" onclick="selectType5()"/><label>日期和时间</label>  
			 &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="field_type6" name="field_type" value="6" onclick="selectType6()"/><label>文件</label>  
			</td>			
		</tr>
	 </tbody>
	</table>
	<div id="textDiv" style="display">
		<table class="table_form" id="form_table1" border="0" cellpadding="0" cellspacing="0">
		    <tbody>
			   <tr>
					<th><span class="f_red">*</span>最大字符数：</th>
					<td >
						<input id="field_maxnum" name="field_maxnum" type="text" style="width: 100px" value="3000" onblur="checkInputValue('field_maxnum',false,20,'最大字符数','checkNumber')"/>
					    &nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr>
					<th>文本框长度：</th>
					<td >
						<input id="field_maxlength" name="field_maxlength" type="text" style="width: 80px" value="300" onblur="checkInputValue('field_maxlength',true,60,'文本框长度','checkNumber')"/>px
					    &nbsp;&nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr style="display:none">
					<th>验证规则：</th>
					<td >
						<select name="validator" id="validator">
                        		<option value="0" selected="true">无</option>
								<option value="1">Email地址</option>
								<option value="2">电话号码</option>
								<option value="3">纯数字</option>
								<option value="4">纯中文</option>
								<option value="5">纯英文字母</option>
                        </select>
					</td>	
				</tr>
				<tr id="default_valueText_tr_id">
					<th>字段默认值：</th>
					<td >
						<input id="default_valueText" name="default_valueText" type="text" style="width: 500px" value=""/>
					    &nbsp;&nbsp;注：内容长度不能大于最大字符数
					</td>	
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="areaNoHtmlDiv" style="display:none">
		<table class="table_form" border="0" id="form_table2" cellpadding="0" cellspacing="0">
		    <tbody>
			   <tr>
					<th><span class="f_red">*</span>显示宽度：</th>
					<td >
						<input id="width" name="width" type="text" style="width: 80px" value="400" onblur="checkInputValue('width',false,20,'显示宽度','checkNumber')"/>px
					    &nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr>
					<th><span class="f_red">*</span>显示高度：</th>
					<td >
						<input id="height" name="height" type="text" style="width: 80px" value="100" onblur="checkInputValue('height',false,60,'显示高度','checkNumber')"/>px
					    &nbsp;&nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr>
					<th>最大字符数：</th>
					<td >
						<input id="field_maxnumArea" name="field_maxnumArea" type="text" style="width: 100px" value="" onblur="checkInputValue('field_maxnumArea',true,60,'最大字符数','checkNumber')"/>
					    &nbsp;&nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr>
					<th>字段默认值：</th>
					<td >
					   <textarea class="input_border" id="default_valueArea" name="default_valueArea" rows="4" cols="50"></textarea>
					    &nbsp;&nbsp;注：内容长度不能大于最大字符数
					</td>	
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="areaHtmlDiv" style="display:none">
		<table class="table_form" border="0" id="form_table3" cellpadding="0" cellspacing="0">
		    <tbody>
			   <tr>
					<th><span class="f_red">*</span>显示宽度：</th>
					<td >
						<input id="widthHtml" name="widthHtml" type="text" style="width: 80px" value="620" onblur="checkInputValue('widthHtml',false,20,'显示宽度','checkNumber')"/>px
					    &nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr>
					<th><span class="f_red">*</span>显示高度：</th>
					<td >
						<input id="heightHtml" name="heightHtml" type="text" style="width: 80px" value="300" onblur="checkInputValue('heightHtml',false,60,'显示高度','checkNumber')"/>px
					    &nbsp;&nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr class="hidden">
					<th>最大字符数：</th>
					<td >
						<input id="field_maxnumAreaHtml" name="field_maxnumAreaHtml" type="text" style="width: 100px" value="" onblur="checkInputValue('field_maxnumAreaHtml',true,60,'最大字符数','checkNumber')"/>
					    &nbsp;&nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr>
					<th>字段默认值：</th>
					<td >
						<textarea class="input_border" id="default_valueAreaHtml" name="default_valueAreaHtml" rows="4" cols="50"></textarea>
					    &nbsp;&nbsp;注：内容长度不能大于最大字符数
					</td>	
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="selectDiv" style="display:none">
		<table class="table_form" border="0" id="form_table4" cellpadding="0" cellspacing="0">
		    <tbody>
			   <tr style="display:none">
					<th>数据类型：</th>
					<td >
					   <input type="radio" name="data_type" id="date_type1" value="1" checked="true" onclick="data_type_select1()"><label> 自定义</label>	
								&nbsp;&nbsp;<input type="radio" name="data_type" id="date_type1" checked="true"  value="2" onclick="data_type_select2()"> <label>数据字典</label>
					</td>	
				</tr>
				<tr style="display:" id="data_type_selectTR">
					<th><span class="f_red">*</span>选择数据分类：</th>
					<td >
						<select name="data_type_select" id="data_type_select" onchange="setDateList()">
							<option value="-1">--请选择--</option>
						</select>
					</td>	
				</tr>
				<tr id="select_itemTR" style="display:none">  
					<th>每个选项1：</th>
					<td >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
                           <tr>
                                <td align="left" style="width: 400px;">
                                  <input type="hidden" name="select_item_hidden" id="select_item_hidden" />
                                      <select name="select_item" id="select_item" style="width: 350px; height: 100px" size="2" ondblclick="return ModifyUrl();">
                                      </select>
                                </td>
                               <td align="left">
		                         <input type="button" name="addurl" id="addurl" value="添加选项    " onclick="AddUrl();" /><br />
		                         <input type="button" name="modifyurl" id="modifyurl" value="修改当前选项" onclick="return ModifyUrl();" /><br />
		                         <input type="button" name="delurl" id="delurl" value="删除当前选项" onclick="DelUrl();" />
		                     </td>
                           </tr>
                           <tr>
                             <td colspan="2">
                                <span style="color:6847d8">&nbsp;&nbsp;注：显示数据|保存数据，如果添加空数据可写 “无|”</span>
                              </td>
                           </tr>
                         </table>
					</td>	
				</tr>
				<tr>
					<th>显示选项使用：</th>
					<td >
						<table width="100%" border="0">
                           	  <tr>
                           	  	<td width="20%"><input type="radio" name="select_view" id="select_view0" value="0" checked="true" onclick="showSelecetTr()"><label>单选下拉列表框</label></td>
								<td width="30%"><input type="radio" name="select_view" id="select_view1" value="1" onclick="showSelecetTr()"> <label>多选列表框</label></td>
		                        <td width="50%"></td>
		                      </tr>
							  <tr>
                           	  	<td width="20%"><input type="radio" name="select_view" id="select_view2" value="2" onclick="showSelecetTr()"><label> 单选按钮</label></td>
								<td width="30%"><input type="radio" name="select_view" id="select_view3" value="3" onclick="showSelecetTr()"> <label>复选框</label></td>
                                <td width="50%"></td>
                           	  </tr>
                        </table>
					</td>	
				</tr>
				<tr id="default_valueTextSelectTR0" style="display:">
					<th>选项默认值：</th>
					<td >
						<select name="select0" id="select0" style="width:380px" onchange="setSelectValue()"></select>
					</td>	
				</tr>
				<tr id="default_valueTextSelectTR1" style="display:none">
					<th>选项默认值：</th>
					<td >
						<select multiple="multiple" size="5" name="select1" id="select1" style="width:380px;height:90px" onchange="setSelectValue()"></select>
					</td>	
				</tr>
				<tr id="default_valueTextSelectTR2" style="display:none">
					<th>选项默认值：</th>
					<td >
						<select name="select2" id="select2" style="width:380px" onchange="setSelectValue()"></select>
					</td>	
				</tr>
				<tr id="default_valueTextSelectTR3" style="display:none">
					<th>选项默认值：</th>
					<td >
						<select multiple="multiple" size="5" name="select3" id="select3" style="width:380px;height:90px" onchange="setSelectValue()"></select>
					</td>	
				</tr>
			</tbody>
		</table>
		<input type="hidden" name="default_valueTextSelect" id="default_valueTextSelect" maxlength="500" size="70"/>
	</div>
	
	<div id="numDiv" style="display:none">
		<table class="table_form" border="0" id="form_table5" cellpadding="0" cellspacing="0">
		    <tbody>
			   <tr>
					<th>最小值：</th>
					<td >
						<input id="min_num" name="min_num" type="text" style="width: 80px" value="" onblur="checkInputValue('min_num',true,20,'最小值','checkNumber')"/>
					    &nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr>
					<th>最大值：</th>
					<td >
						<input id="max_num" name="max_num" type="text" style="width: 80px" value="" onblur="checkInputValue('max_num',true,60,'最大值','checkNumber')"/>
					    &nbsp;&nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
				<tr>
					<th>字段默认值：</th>
					<td >
						<input id="default_valueNum" name="default_valueNum" type="text" style="width: 80px" value="" onblur="checkInputValue('default_valueNum',true,60,'字段默认值','checkNumber')"/>
					    &nbsp;&nbsp;&nbsp;注：只能是数字
					</td>	
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="timeDiv" style="display:none">
		<table class="table_form" border="0" id="form_table6" cellpadding="0" cellspacing="0">
		    <tbody>
				<tr>
					<th>默认值：</th>
					<td >
						<input type="radio" name="timeSelectType" id="timeSelectType" value="0" checked="true"/> <label>无</label>
						<br/>
						<input type="radio" name="timeSelectType" id="timeSelectType" value="2"/> <label>当前时间</label>
						<br/>
		                <input type="radio" name="timeSelectType" id="timeSelectType" value="1"/> <label>指定时间</label>
						<input class="Wdate" type="text" name="time" id="time" size="25" style="height:16px;line-height:16px;" 
						 onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="true"/>
					</td>	
				</tr>
			</tbody>
		</table>
	</div>
	
	<div id="fileDiv" style="display:none">
		<table class="table_form" border="0" id="form_table7" cellpadding="0" cellspacing="0">
		    <tbody>
				<tr>
					<th>允许文件类型：</th>
					<td >
						<input type="text" name="file_type" id="file_type" maxlength="500" size="30" value=""/>
						&nbsp;&nbsp;注：允许多个类型请用“|”号分割，如：jpg|mp3|gif|rm|rmvb
					</td>	
				</tr>
			</tbody>
		</table>
	</div>
	
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<div id ="viewType" style="display:none">  
			<input id="AddCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />	
			</div>
			<div id="addType" style="display:" >
			<input id="subButton" name="btn1" type="button" value="保存" />	
			<!-- 
			<input id="memAddReset" name="btn2" type="button" onclick="formReSet('member_table','')" value="重置" />
			-->
			<input id="memAddCancel" name="btn3" type="button" onclick="javascritp:top.tab_colseOnclick(top.curTabIndex);" value="取消" />	
			</div>
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
