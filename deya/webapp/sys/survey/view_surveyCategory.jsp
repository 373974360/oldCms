<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 

	<title>问卷调查分类查看</title> 
	<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="../js/java.js"></script>
	<script type="text/javascript" src="../js/jsonrpc.js"></script>
	<script type="text/javascript" src="../js/jquery.c.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/validator.js"></script>
	<script type="text/javascript" src="../js/util.js"></script>
	<script type="text/javascript" src="../js/loginCheck.js"></script>
	<script type="text/javascript"  src="../js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
	<script type="text/javascript" src="js/surveyList.js"></script>
	<script type="text/javascript" src="../js/extend.js"></script>

	<script type="text/javascript" src="/GPPS2/gpps.files/component/common/htmledit/editor/js/cicroUtil.js"></script>
	<script type="text/javascript" src="/GPPS2/gpps.files/component/common/htmledit/fckeditor.js"></script>

	<SCRIPT LANGUAGE="JavaScript">
	<!--
		var c_id = request.getParameter("c_id");
		$(document).ready(function () {								
			if(c_id != null && c_id.trim() != "")
			{
				defaultBean = SurveyCategoryRPC.getSurveyCategoryBean(c_id);
		
				if(defaultBean)
				{
					$("#surveyCategory").autoFill(defaultBean);					
				}		
				
			}
			
		}); 	


		
	//-->
	</SCRIPT>	
</head> 
<body topmargin="0" leftmargin="0" style="background: #ECF4FC;"> 
    <input type="hidden" id="handleId" name="handleId" value="H32001">
	<table id="tables" border="0" cellpadding="0" cellspacing="0" width="98%" class="content_lab_table_view" align="center">
		  <tr id="lable_tr">
		   <td width="80px" height="26px" align="center" class="content_lab_check" id="jcxx" onclick="changeContentLable(this)">基础信息</td>
		   <td class="content_lab">&nbsp;</td>
		  </tr>
		 </table>		 
		 <!-- 表单区域 开始-->
		 <table border="0" cellpadding="0" cellspacing="0" width="98%" id="surveyCategory" align="center">	
		  <tr>
		   <td id="content_area" colspan="4" class="content_form_td" valign="top">		    
		    <div id="form_div" style="overflow:auto;height:220px;padding-top:8px">
		    <table id="jcxx_tab" border="0" cellpadding="0" cellspacing="0" width="100%" >
			 <tr height="30">
			<td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" width="100" class="content_td">问卷分类ID：</td>
			  <td class="content_td" id="category_id"></td>			 
			 </tr>	
			 <tr height="30">
			<td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" width="100" class="content_td">问卷分类名称：</td>
			  <td class="content_td" id="c_name"></td>			 
			 </tr>	
			 <tr>
			  <td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" class="content_td" valign="top" style="padding-top:10px">问卷分类说明：</td>
		      <td class="content_td" ><div id="description" style="width:98%;height:80px;overflow:auto;margin-top:7px;line-height:18px"></div></td>
			 </tr>
			 <tr height="30">
			<td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" class="content_td">前台展示模板：</td>
			  <td class="content_td" id="model_path"></td>			 
			 </tr>	
			 <tr>
			  <td height="32px"  class="content_td">&nbsp;</td>
			  <td align="right" class="content_td">发布状态：</td>
		      <td class="content_td"><input type="radio" id="publish_status" name="publish_status" value="1" disabled>已发布&nbsp;&nbsp;<input type="radio" id="publish_status" name="publish_status" value="0" checked="checked" disabled>未发布&nbsp;&nbsp;<input type="radio" id="publish_status" name="publish_status" value="-1" disabled>已撤消</td>
			 </tr>			 
			</table>				
			</div>
		   </td>
		  </tr>
		 </table>
	   <!-- 表单区域 结束-->
		</td>
		  </tr>
		 </table><BR>
		<table border="0" cellpadding="0" cellspacing="0" width="98%" class="content_lab_table" align="center">
		  <tr>
		   <td class="b_bottom_l"></td>
		   <td class="b_bottom_c" align="center">
			<!-- 按钮区域 开始-->
			 <div style="width:80px;height:24px">				
				 <a class="button" href="#" onclick="this.blur(); return false;" hidefocus="true"><span onclick="closeModelWin()">&nbsp;关闭</span></a>
			 </div>
		   <!-- 按钮区域 结束-->
		   </td>
		   <td class="b_bottom_r"></td>
		  </tr>
		</table>
</body> 

</html> 

