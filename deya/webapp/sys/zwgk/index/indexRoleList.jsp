<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>索引码生成规则编辑</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/indexRoleList.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initTable();
});
</script>
<style>

</style>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
  <table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0">
    <tbody>
      <tr>
        <th>索引码构成：</th>
        <td colspan="2" valign="top" >
		<table  class="table_option" style="width:100%; margin:0 0;" border="0" cellpadding="0" cellspacing="0" >
			<thead>
            <tr>
              <td class="width40" >选择</td>
              <td class="width100">代码构成</td>
              <td style="width:130px;">取值</td>
              <td class="width80">分隔符</td>
              <td ></td>
            </tr>
			</thead>
			<tbody>
            <tr id="row_0">
              <td class=""><input id="cell_0_is_valid" type="checkbox" value="1"/></td>
              <td class=""><strong>前段码</strong></td>
              <td class=""><input id="cell_0_ir_value" type="text" class="width100" value="" /></td>
              <td class=""><select id="cell_0_ir_space" style="width:80px;">
                  <option value="">无分隔</option>
                  <option selected="selected" value="/">反斜杠(/)</option>
                  <option value="_">下划线(_)</option>
                  <option value="-">横杠(-)</option>
                </select>
              </td>
              <td ><input id="cell_0_id" type="hidden" value="" /></td>
            </tr>
            <tr id="row_1">
              
              <td class=""><input id="cell_1_is_valid" type="checkbox" checked="checked" disabled="disabled" /></td>
              <td class=""><strong>区域及部门编号</strong></td>
              <td class="">公开节点的节点编码</td>
              <td class=""><select id="cell_1_ir_space" style="width:80px;">
                  <option value="">无分隔</option>
                  <option selected="selected" value="/">反斜杠(/)</option>
                  <option value="_">下划线(_)</option>
                  <option value="-">横杠(-)</option>
                </select>
              </td>
              <td ><input id="cell_1_id" type="hidden" value="" />
			  <input id="cell_1_ir_value" type="hidden" value="" />
			  </td>
            </tr>
            <tr id="row_2">
              
              <td class=""><input id="cell_2_is_valid" type="checkbox" /></td>
              <td class=""><strong>信息分类号</strong></td>
              <td class=""><select id="cell_2_ir_value" style="width:100px;">
                  <option value="0">一级类目编码</option>
                  <option value="1">二级类目编码</option>
                  <option value="2">三级类目编码</option>
                  <option selected="selected" value="3">末级类目编码</option>
                </select></td>
              <td class=""><select id="cell_2_ir_space" style="width:80px;">
                  <option value="">无分隔</option>
                  <option selected="selected" value="/">反斜杠(/)</option>
                  <option value="_">下划线(_)</option>
                  <option value="-">横杠(-)</option>
                </select>
              </td>
              <td ><input id="cell_2_id" type="hidden" value="" /></td>
            </tr>
            <tr id="row_3">
              
              <td class=""><input id="cell_3_is_valid" type="checkbox"  checked="checked" disabled="disabled" /></td>
              <td class=""><strong>信息编制年份</strong></td>
              <td class=""><select id="cell_3_ir_value" style="width:100px;">
                  <option selected="selected" value="yyyy">YYYY</option>
                  <option selected="selected" value="yyyyMM">YYYYMM</option>
                </select>
              </td>
              <td class=""><select id="cell_3_ir_space" style="width:80px;">
                  <option value="">无分隔</option>
                  <option selected="selected" value="/">反斜杠(/)</option>
                  <option value="_">下划线(_)</option>
                  <option value="-">横杠(-)</option>
                </select>
              </td>
              <td ><input id="cell_3_id" type="hidden" value="" /></td>
            </tr>
            <tr id="row_4">
             
              <td class=""><input id="cell_4_is_valid" type="checkbox"  checked="checked" disabled="disabled"/></td>
              <td class=""><strong>信息流水号</strong></td>
              <td class=""><select id="cell_4_ir_value" style="width:100px;">
                  <option value="3">3位</option>
                  <option value="4">4位</option>
                  <option selected="selected" value="5">5位</option>
                  <option value="6">6位</option>
                  <option value="7">7位</option>
                  <option value="8">8位</option>
                </select>
              </td>
              <td> <input id="cell_4_id" type="hidden" value="" /><input id="cell_4_ir_space" type="hidden" value="" />
              </td>
            </tr>
			</tbody>
          </table>
		 </td>
      </tr>
      <tr id="row_5">
        <th>流水号规则：</th>
        <td><select id="cell_5_ir_value" style="width:100px;">
            <option  selected="selected"  value="100">公开节点</option>
            <option value="0">末级分类</option>
          </select>
          <input id="cell_5_id" type="hidden" value="" />
          <input id="cell_5_ir_space" type="hidden" value="" />
          <input id="cell_5_is_valid" class="hidden" type="checkbox" value="" />
        </td>
      </tr>
      <tr id="row_6">
        <th>流水号循环周期：</th>
        <td class=""><select id="cell_6_ir_value" style="width:100px;">
            <option selected="selected" value="Y">按年份</option>
            <option value="M">按月循环</option>
          </select>
          <input id="cell_6_id" type="hidden" value="" />
          <input id="cell_6_ir_space" type="hidden" value="" />
          <input id="cell_6_is_valid" class="hidden" type="checkbox" value="" />
        </td>
      </tr>      
    </tbody>
  </table>
  <!--隔线开始-->
  <span class="blank12"></span>
  <div class="line2h"></div>
  <span class="blank3"></span>
  <!--隔线结束-->
  <table class="table_option" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td align="left" valign="middle" style="text-indent:100px;"><input id="btn1" name="btn1" type="button" onclick="updateRoleList()" value="保存" />
        <input id="btn2" name="btn2" type="button" onclick="reLoad()" value="重置" />
      </td>
    </tr>
  </table>
  <span class="blank3"></span>
</form>
</body>
</html>
