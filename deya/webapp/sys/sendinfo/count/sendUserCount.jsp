<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报送信息工作量统计</title> 


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="/sys/js/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="js/sendUserCount.js"></script>
<script type="text/javascript">
var contentSelected = false;
var site_id = "${param.site_id}";
var type = "${param.type}";

$(document).ready(function(){
	//得到所有报送过的站点
	reloadBefore();
	getReceiveSiteListForRecord();
	initButtomStyle();
	init_input();
	
	$("label").unbind("click").click(function(){
		
		var b_id = $(this).prev("input").attr("id");
		if($("[id='"+b_id+"']").is(':checked')){
			 $("[id='"+b_id+"']").removeAttr("checked");
		 }else{
			$("[id='"+b_id+"']").attr("checked",'true');
		 }
		 setAllState();
	});

	if(type == "user")
	{
		$("#addButton").click(funOKUser);
		$("#table").show();
	}else
	{
		$("#addButton").click(funOKCate);
	}

	reloadAfter();
});

function setCountCon()
{
	var t_site_ids = "";
	$(":checked[id='b_id']").each(function(){
		t_site_ids += ",'"+$(this).val()+"'";
	});
	if(t_site_ids != "" && t_site_ids != null)
	{
		m.put("t_site_id",t_site_ids.substring(1));
	}else
	{
		msgWargin("请选择接收站点");
	    return false;
	}

	var start_time = $("#s").val();
	var end_time = $("#e").val();
	if(start_time != "" && start_time != null)
	{
		if(end_time != "" && end_time != null)
		{
			if(judgeDate(end_time,start_time))
			{
				msgWargin("结束时间不能小于开始时间");
				return false;
			}
		}
		m.put("start_time",start_time+" 00:00:00");
	}	
	if(end_time != "" && end_time != null)
	{
		m.put("end_time",end_time+" 23:59:59");
	}
	m.put("send_site_id",site_id);
	return true;
}

function funOKUser()
{
	if(setCountCon())
	{
		initTable();
		showList();
	}
}

function funOKCate()
{
	getSendCateListForRecord();
}

function getSendCateListForRecord()
{
	if(setCountCon())
	{
		$("#treeTableCount").show();
		var l = SendInfoRPC.getSendCateListForRecord(m);
		l = List.toJSList(l);
		var treeHtmls = "";
		for(var i=0;i<l.size();i++){
			 treeHtmls += setTreeNode(l.get(i));
		}
		$("#treeTable_tbody").html(treeHtmls);
		iniTreeTable("treeTableCount");
	}

}

//组织树列表选项字符串
		function setTreeNode(bean)
		{			
			var treeHtml = "";
			var child_cate_list = bean.child_cate_list;
			child_cate_list = List.toJSList(child_cate_list);
			
			if(child_cate_list == null || child_cate_list.size() == 0)
			{
				var type_calss= "";
				if(bean.parent_id!='0'){  
				      type_calss = "class='child-of-node-"+bean.cat_parent_id+"'"
				}
				treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='file'>"+bean.cat_cname+"</span></td><td>"+bean.send_count+"</td><td>"+bean.adopt_count+"</td><td>"+bean.not_count+"</td><td>"+bean.adopt_rate+"</td>";
               
				treeHtml+="</tr>";
			}
			else 
			{
				var type_calss = "";
								
				treeHtml+="<tr id='node-"+bean.cat_id+"' "+type_calss+" > <td><span class='folder'>"+bean.cat_cname+"</span></td><td></td><td></td><td></td><td></td>";
				treeHtml+="</tr>";
				
				  if(child_cate_list.size() > 0)
				  { 
					 for(var i=0;i<child_cate_list.size();i++)
					 {						
						treeHtml += setTreeNode(child_cate_list.get(i));
					 }
				  }
			}			
			return treeHtml;
		}
		
		function ChangeStatus(o)
		{
			if (o.parentNode)
			{
				if (o.parentNode.parentNode.parentNode.className == "Opened")
				{
					o.parentNode.parentNode.parentNode.className = "Closed";
				}
				else
				{
					o.parentNode.parentNode.parentNode.className = "Opened";
				}
			}
		}
</script>
</head>

<body>
<form id="form1" name="form1" action="" method="get">
<table class="table_option fromTabs" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" >
			<span class="f_red">*</span>时间范围：
				<input class="Wdate" type="text" name="s" id="s" size="11" style="height:16px;line-height:16px;" value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
		         --
			    <input class="Wdate" type="text" name="e" id="e" size="11" style="height:16px;line-height:16px;
"  value="" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="true">
			<span class="blank3"></span>
		</td>
			<td align="right" valign="middle">
				 <input id="addButton" name="btn1" type="button" onclick="" value="统计" />	
				 <input id="userAddReset" name="btn1" type="button" onclick="reset()" value="重置" />
				 <span class="blank3"></span>
			</td>
		</tr>
		<tr>
			<td align="left" valign="middle" colspan="2">
				<ul class="inputUL" id="b_tr"> 
					<li><label class="f_red">*</label>接收站点：&nbsp;&nbsp;<input type="checkbox" name="all" id="all" value="1" onclick="fnAll()" style="vertical-align:middle"><b><label onclick="fnAllSet()">全选</label></b></li>
				</ul>
				<span class="blank3"></span>
			</td>
	</tr>
</table>

<span class="blank3"></span>

<div id="table" class="hidden"></div><!-- 列表DIV -->
<table id="treeTableCount" class="treeTableCSS table_border hidden"  border="0" cellpadding="0" cellspacing="0">
  <thead>
    <tr>
      <th>栏目名称</th>
      <th>报送总数</th>
      <th>采用数</th>
	  <th>未采用数</th>
	  <th>采用率</th>
    </tr>
  </thead>
  <tbody id="treeTable_tbody">
 
  </tbody>
  <tfoot>
  	<td colspan="7"></td>
  </tfoot>
</table>

<span class="blank3"></span>
<span class="blank3"></span>
<div id="line2h" class="line2h" style="display:none"></div>
<span class="blank3"></span>
<table id="buttonTable" class="table_option" border="0" cellpadding="0" cellspacing="0" style="display:none">
	<tr>	
		<td>
		    <!--
			<input id="addButton" name="btn1" type="button" onclick="window.location.href='banliqingkuang.jsp'" value="统计条件" />	
			<input id="excel_out" name="btn1" type="button"  onclick="" value="导出" />
			-->
			
			<span class="blank3"></span>
		</td>  	
	</tr>
</table>
</form>
</body>
</html>
