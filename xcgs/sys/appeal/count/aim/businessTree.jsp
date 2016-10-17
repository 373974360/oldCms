<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设置业务</title>


<jsp:include page="../../../include/include_tools.jsp"/>

<script type="text/javascript">
var CountServicesUtilRPC = jsonrpc.CountServicesUtilRPC;

$(document).ready(function(){
	setBusiness();//得到所有的业务
});

//得到所有的业务
function setBusiness(){
	 	var tableTr = "";
		var beanList = null;

		beanList = CountServicesUtilRPC.getBusinessList();
		beanList = List.toJSList(beanList);

		for (var i = 0; i < beanList.size(); i++) {
			var map = Map.toJSMap(beanList.get(i));
			var id = map.get("id");
			var name = map.get("name");
			//$("#s_b_id").addOption(businessBean.bu_name,businessBean.b_id);
			tableTr += '<tr><td>&nbsp;&nbsp;<input style="vertical-align:middle" type="checkbox" name="b_id" onclick="setAllState()" value="'+id+'"><span id="'+id+'">'+name+'</span></td></tr>';
		}
		$("#addTable").html(tableTr); 

}

//自定义对象
function BusinessBean(id,name){
	this.id = id;
	this.name = name;
}


function fnAll(){
	if($("[name='all']").is(':checked')){
  	  fnSelectAll();//全选
    }else{
  	  fnCancelAll();//取消全选
    }
}

function fnSelectAll(){
	$("[name='b_id']").attr("checked",'true');
}

function fnCancelAll(){
	$("[name='b_id']").removeAttr("checked");
}


function setAllState(){
	 contentSelected = true;
     if(!isContentSelectedAll()){
	 	$("[name='all']").removeAttr("checked");
	 }else{
	 	$("[name='all']").attr("checked",'true');
	 }
}

//判断不是全选
function isContentSelectedAll(){
	$("[name='b_id']").each(function(){
     if(!$(this).is(':checked'))
      {
        contentSelected = false;
		return contentSelected;
      }
    })
	//alert(contentSelected);
	return contentSelected;
}

function fnOK(){
	var ids="";
	var names=""; 
    $(":checkbox[name='b_id'][checked]").each(function(){
       ids+=$(this).val()+";";
	   names+=$("#"+$(this).val()).html()+";";
    }) 
	//alert(ids); 
	//alert(names);
	//parent.document.right_frame.setBIds(ids);
	//parent.document.right_frame.setBNames(names);
	top.CloseModalWindow();
	top.getCurrentFrameObj().setBIdsNames(ids,names);
}

</script>
</head>

<body>

<form name="form1" id="form1" action="" method="post">
<table id="config_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
	    <tr>
			<td id="content_area" colspan="4" class="content_form_td" valign="top">
			<table border="0" cellpadding="0" cellspacing="0" width="100%" >
			 <tr>
			  <td>
				&nbsp;&nbsp;<input type="checkbox" name="all" id="all" value="1" onclick="fnAll()" style="vertical-align:middle"><b>全选</b>
			  </td>
			 </tr> 
			 <tr>
			   <td><div class="line2h"></div><span class="blank12"></span></td>
			 </tr> 
			<table>
		    <table id="addTable" border="0" cellpadding="0" cellspacing="0" width="100%" >
		    <!-- 
			 <tr> 
			  <td> 
				<input type="checkbox" name="b_id" value="1" style="vertical-align:middle">11
			  </td>
			 </tr>
			  -->
			<table>
		   </td>			
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<table border="0" cellpadding="0" cellspacing="0" width="100%" >
			 <tr>
			  <td>
<div class="line2h"></div>
             </td>
             </tr>
</table>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="fnOK()" value="确定" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
