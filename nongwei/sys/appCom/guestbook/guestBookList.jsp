<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>待审页面</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/guestBookList.js"></script>
<script type="text/javascript">
	
var gbs_id = "${param.gbs_id}";
var site_id = "${param.site_id}";
var cat_id = "${param.cat_id}";
$(document).ready(function(){
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	reloadList();
});


//取消了Init_InfoTable的鼠标移动行时样式变化
function Init_InfoTable_page(tableName)
{
	//奇偶行不同颜色
	$("#"+tableName+".odd_even_list tbody tr:odd").removeClass().addClass("tr_odd");
    $("#"+tableName+".odd_even_list tbody tr:even").removeClass().addClass("tr_even");
	
	$("#"+tableName+" tbody tr").each(function(){
		var $_tr=$(this);
		//alterRowColor($_tr);
		
		if($(this).find(".inputHeadCol:checkbox").is(':checked'))
		{
			$(this).addClass("tr_selected");
		}
		
		//行单击事件
		$(this).click( 
			function (event) {
				if(!(("INPUT,A,SPAN,IMG,TEXTAREA").indexOf($(event.target)[0].tagName.toUpperCase())>-1))
				{
					$(this).find(".inputHeadCol:checkbox").attr("checked",!($(this).find(".inputHeadCol:checkbox").is(':checked')));
					
				}
				
				if($(this).find(".inputHeadCol:checkbox").is(':checked'))
				{
					$(this).addClass("tr_selected");
				}
				else
				{
					$(this).removeClass("tr_selected");
				}
				clickInputCheckBox(tableName);
			}
		);

	})
	
	//行头checkbox单击事件
	$("#"+tableName+" tbody .inputHeadCol:checkbox").each(function(){	
		$(this).click( 
			function () { 
				clickInputCheckBox(tableName);
			}
		);
	})
	
	//全选checkbox事件
	$("#"+tableName+" thead :checkbox").click(
		function () { 
			if($(this).is(':checked')){
				//alert($("#"+tableName+" tbody .inputHeadCol").txt());
				$("#"+tableName+" tbody .inputHeadCol:checkbox").attr("checked",true);
				$("#"+tableName+" tbody tr").addClass("tr_selected");
			}
			else
			{
				$("#"+tableName+" tbody .inputHeadCol:checkbox").attr("checked",false);
				$("#"+tableName+" tbody tr").removeClass("tr_selected");
			}
		}				  
	);
}

</script>
<style>
.comment_opt{}
.comment_opt li{ height:22px; display:block;}
</style>
</head>
<body>

<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="left" class="fromTabs width230" style="">
			<input id="btn1" name="btn1" type="button" onclick="multiSelect('publishGuestBook(1)')" value="发布" />
			<input id="btn1" name="btn1" type="button" onclick="multiSelect('publishGuestBook(0)')" value="撤消" />
			<input id="btn2" name="btn2" type="button" onclick="deleteSelect('batchDeleteGuestBook()')" value="删除" />
			
		<span class="blank3"></span>
		</td>
		<td align="right" valign="middle" class="fromTabs">
			检索
			<input id="start_day" type="text" style="height:18px" class="Wdate" size="11" value="" 
				onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />至
			<input id="end_day" type="text" style="height:18px" class="Wdate" size="11" value="" 
				onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
			<select id="search_publish" class="input_select">
				<option selected="selected" value="">发布状态</option>
				<option value="1">已发布</option>
				<option value="0">未发布</option>
			</select>
			<select id="search_reply" class="input_select">
				<option selected="selected" value="">回复状态</option>
				<option value="1">已回复</option>
				<option value="0">未回复</option>
			</select>
			<input id="searchkey" type="text" class="input_text" value=""  />
			<input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="searchList()"/>
			<span class="blank3"></span>
		</td>
	</tr>
</table>
</div>
<span class="blank3"></span>
<div>
<table id="gb_table" class="table_border odd_even_list"  border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<td width="25" align="center"><input id="selectAll" name="selectAll" type="checkbox" onclick="" /></td>
			<td width="585" align="left"></td>
			<td width="50"></td>
			<td width="25"></td>
			<td></td>
		</tr>
	</thead>
	<tbody id="gb_tbody">
	</tbody>
	<tfoot>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</tfoot>
</table>
</div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="multiSelect('publishGuestBook(1)')" value="发布" />
			<input id="btn1" name="btn1" type="button" onclick="multiSelect('publishGuestBook(0)')" value="撤消" />
			<input id="btn2" name="btn2" type="button" onclick="deleteSelect('batchDeleteGuestBook()')" value="删除" />
			
		</td>
	</tr>
</table>
</body>
</html>
