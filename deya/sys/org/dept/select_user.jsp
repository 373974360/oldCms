<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String handl_name = request.getParameter("handl_name");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择用户</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css"/>
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css"/>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/deptManager.js"></script>
<script type="text/javascript">
	var handl_name = "<%=handl_name%>";
	var json_data;
	var selectedUserArray;
	var user_map = new Map();
	var selected_dept_map = new Map();
    var contentSelected = false;
$(document).ready(function(){
	initButtomStyle();	
	getAllUserMap();
	json_data = eval(DeptRPC.getAllDeptTreeJsonStr());		
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
	setAllUserListByDept(1);
	// 写入已选择过的人员
	setSelectedUserInSelect();
	});
	
    // 初始化CheckBox按钮 
	function initSelectAll()
	{
	    // 全选
	    $("#checkAll").click(doSelectAll);
	    // label全选
	    $("label:first").unbind("click");
	    $("#query").bind("propertychange",function(){  
          query();
        });
	}
	//判断不是全选
	function isContentSelectedAll(){
		$("[name='user_id']").each(function(){
	     if(!$(this).is(':checked'))
	      {  
	      contentSelected = false;
		   return contentSelected;
	      }
	    })
	    return contentSelected;
	}
	// Check未全选取消 全选
	 function setAllState()
	 {
	 contentSelected = true;
	     if(!isContentSelectedAll()){
		 	$('[name="checkAll"]').removeAttr("checked");
		 }else{
		 	$('[name="checkAll"]').attr("checked",true);
		 }
     }
	 
	// 全选
	function doSelectAll()
	{ 
	      $('input[name="user_id"]').attr("checked",this.checked);
	      var checkedObj = $("input[name='user_id'][@checked]");  
	      var Check = '';
	      checkedObj.each(function() {  
	      var isCheck = this.value;
	      Check += isCheck+",";   
	      });
	      // 添加右侧 sel_user_list
	      optSelectedAllUserList($(this).is(':checked'),Check)
		 
	}
	// 全选 添加右侧
	function optSelectedAllUserList(isChecked,uid)
	{
	    $("#sel_user_list li").remove();
	    var str = uid.split(",");
		if(isChecked)
		{	
		     $.each( str, function(){
	             var u_id=this;
	             //选中后，往左侧框添加
			     $("#sel_user_list").append('<li style="float:none;height:20px;display:block;width:100%;both:clear" user_id="'+u_id+'"><span style="float:left">'+user_map.get(u_id).user_realname+'</span><img onclick="deleteUser(this,\''+u_id+'\')" src="../../images/delete.png" width="15" height="15" alt="" style="float:right;padding-bottom:3px"/></li>');	
		     });
		} 
	}
	 
	// 查询
	function query(){
		 
		//$('[name="user_id"]').removeAttr("checked");
        //取得查询值
		var q = $.trim($("#query").val());	
	    if(q==""){
	        $("label").css("color","#000000");
		    return;
		}
	    var c ="";
		// 取得 check 各values值
		$("[name='user_id']").each(function(){
		  c +=  this.value+",";
		});
		c= c.substring(0, c.lastIndexOf(","));
		// 取得 check 各名称
		var b=""; 
		//$("all_user_list label:gt(0)").each(function(i){
        $("#all_user_list li").each(function(i){
	        if(i>0){
	            b+=$.trim($(this).text())+",";
	        }
         });
		 b= b.substring(0, b.lastIndexOf(","));
         var str = b.split(",");//名称
         var str1 = c.split(",");//值 
         
         var res="";
         $.each(str, function(i,n){// 循环名称
             if(n.indexOf(q)>-1){
                  res+=str1[i]+",";
                } 
	     });
          res= res.substring(0, res.lastIndexOf(","));//对比后的值
          
          var value = res.split(",");
            $("label").css("color","#000000");
          $.each(value, function(i,n){
            // $(":checkbox[value='"+n+"']").attr("checked","true");
             $("label[id='"+n+"']").css("color","red");
          });
          setAllState();
     }  
 
	//得到所有的用户
	function getAllUserMap()
	{
		user_map = UserManRPC.getSimpleUserMap();
		user_map = Map.toJSMap(user_map);
	}
	
	//将用户写对就对应的部门节点中
	function setUserInDeptTree(d_id,open_status)
	{
		treeNodeSelected(d_id);	
	
		var values = user_map.values();
		for(var i=0;i<values.length;i++){
			if(values[i].dept_id == d_id)
			{
				insertTreeNode(eval('[{"id":'+values[i].user_id+',"iconCls":"icon-user","text":\"'+values[i].user_realname+'\","attributes":{"handls":"setUserInSelect()"}}]'));
			}
		}
	
		var node = $('#leftMenuTree').tree('getSelected');
		//第一级不关闭
		if(open_status == "close")
			$('#leftMenuTree').tree('collapse',node.target);	
	}
	
	
	//初始化人员节点的双击事件
	function initMenuTree()
	{
		$('#leftMenuTree').tree({
			onClick:function(node){
				setAllUserListByDept(node.id);
			}
		});
	}
	
	//根据部门ID写入人员列表
	function setAllUserListByDept(dept_id)
	{
		var u_ids = ","+getSelectUserIDS()+",";	
		var checked_str = '';
		$("#all_user_list").empty();
		var values = user_map.values();
		
		var show_checkAll='';// 显示【全选】标志 
		
		for(var i=0;i<values.length;i++)
		{
			if(values[i].dept_treeposition.indexOf("$"+dept_id+"$") > -1)
			{
				checked_str = '';
				if(u_ids.indexOf(","+values[i].user_id+",") > -1)
				{
					checked_str = 'checked = "true"';
				}
				
				// 显示【全选】
			    if(show_checkAll==""){
			       $("#all_user_list").append('<li style="float:none;height:20px;border-bottom:1px solid #9DBFDD;"><input type="checkbox" id="checkAll" name="checkAll" onfocus="this.blur()"><label id="checkAll" >全选</label> | 查询  <input type="text" name="query" id="query" value="" style="width: 80px;height: 15px"  /></li>');
				   show_checkAll="none"; 
				}
				$("#all_user_list").append('<li style="float:none;height:20px;"><input type="checkbox" '+checked_str+' id="user_id" name="user_id"  value="'+values[i].user_id+'" onfocus="this.blur()"/><label id="'+values[i].user_id+'" >'+values[i].user_realname+'</label></li>');
			}
		}
		init_input();
	
		$("label").unbind("click").click(function(){
			var isChecked = $(this).prev().is(':checked') ? false:true;
			$(this).prev().attr("checked",isChecked);
			optSelectedUserList(isChecked,$(this).prev().val());
		});
	
		/**  $("#all_user_list :checkbox").click(function(){
		 	 optSelectedUserList($(this).is(':checked'),$(this).val());
		  });
	    **/
	    
		// 给各用户添加click事件  （除全选）
		$("input[name='user_id']").click(function(){
			 optSelectedUserList($(this).is(':checked'),$(this).val());
		});
		//初始化全选按钮 
		initSelectAll();
	}
	
	//根据是否选中处理选列表中的数据  
	function optSelectedUserList(isChecked,u_id)
	{ 
		if(isChecked)
		{		
			$("#sel_user_list").append('<li style="float:none;height:20px;display:block;width:100%;both:clear" user_id="'+u_id+'"><span style="float:left">'+user_map.get(u_id).user_realname+'</span><img onclick="deleteUser(this,\''+u_id+'\')" src="../../images/delete.png" width="15" height="15" alt="" style="float:right;padding-bottom:3px"/></li>');	
		     
		}else
		{
			$("#sel_user_list li[user_id='"+u_id+"']").remove();
			 
		}
		setAllState();
	}
	
	//删除已选用户
	function deleteUser(obj,ids)
	{
		$(obj).parent().remove();
		$("#all_user_list input[value='"+ids+"']").removeAttr("checked");
		//取消全选按钮 
		setAllState();
	}
	
	
	//将已有的管理员写入到select框中,从父页面方法中获取值
	function setSelectedUserInSelect()
	{
		try{
			var user_ids = top.getCurrentFrameObj().getSelectedUserIDS();
			if(user_ids != "" && user_ids != null)
			{
				var tempA = user_ids.split(",");
				for(var i=0;i<tempA.length;i++)
				{
					$("#all_user_list label[id='"+tempA[i]+"']").click();
				}
			}
		}catch(e)
		{}
	}
	
	function getSelectUserIDS()
	{
		var ids = "";
		$("#sel_user_list li").each(function(){
			ids += ","+$(this).attr("user_id");
		});
		if(ids != "" && ids != null)
			ids = ids.substring(1);	
		return ids;
	}
	
	function getSelectUserNames()
	{
		var names = "";
		$("#sel_user_list li").each(function(){
			names += ","+$(this).text();
		});
		if(names != "" && names != null)
			names = names.substring(1);	
		return names;
	}
	
	function returnUserID()
	{
		eval("top.getCurrentFrameObj()."+handl_name+"('"+getSelectUserIDS()+"','"+getSelectUserNames()+"')");	
		top.CloseModalWindow();
	}

</script>
</head>

<body>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		 <th class="table_form_th">选择部门</th>
		 <th class="table_form_th">选择用户</th>
		 <th class="table_form_th">已选用户列表</th>
		</tr><!-- 
		<tr>
		 <td>
		  值：<input type="text" name="cvalue" id="cvalue"  value="" /><br /> 
		名称 ：<input type="text" name="nvalue" id="nvalue" value="" /><br />
		查询值：<input type="text" name="qvalue" id="qvalue" value="" />
		 </td>
		</tr>	
		 -->
		<tr>			
			<td class="width230" valign="top">
				<div id="leftMenuBox">
					<div id="leftMenu" class="contentBox6 textLeft border_color" style="width:210px;height:390px;overflow:auto;background:#ffffff">
						<ul id="leftMenuTree" class="easyui-tree" animate="true" >
						</ul>
					</div>
				</div>
			</td>
			<td class="width230" align="top" >
				<div style="width:220px;height:402px;overflow:auto;" class="border_color">
					<ul id="all_user_list" style="margin:10px">
					</ul>
				 </div>
			</td>
			<td class="width210" valign="top">
				<div style="width:220px;height:402px;overflow:auto;" class="border_color">
					<ul id="sel_user_list" style="margin:10px">
					</ul>
				 </div>
			</td>
		</tr>
		 		
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="returnUserID()" value="保存" />				
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
