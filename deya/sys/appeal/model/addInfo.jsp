<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>信件录入</title>
<meta name="generator" content="cicro-Builder"/>
<meta name="author" content="cicro"/>
<link type="text/css" rel="stylesheet" href="../../styles/sq_modle.css"/>
<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link type="text/css" rel="stylesheet" href="/sys/styles/uploadify.css"/>
<jsp:include page="../../include/include_tools.jsp"/>
<script language="javascript" src="/cms.files/js/jquery-plugin/jquery.uploadify.js"></script>
<script language="javascript" src="/cms.files/js/jquery-plugin/uploadFile/swfobject.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="/cms.files/js/appeal.js"></script>
<script type="text/javascript" src="js/modelList.js"></script>
<script type="text/javascript">
var AppealModelRPC = jsonrpc.AppealModelRPC;
var ModelRPC = jsonrpc.ModelRPC
var SQRPC = jsonrpc.SQRPC;
var SQBean = new Bean("com.deya.wcm.bean.appeal.sq.SQBean",true);
var SQAttachment = new Bean("com.deya.wcm.bean.appeal.sq.SQAttachment",true);
var local_IP ="";
		
var sq_custom_list;//用于存储扩展字段的列表
var model_id = "<%=request.getParameter("model_id")%>";
var site_id = jsonrpc.SiteRPC.getSiteIDByAppID("appeal");
$(document).ready(function(){
	initButtomStyle();
	init_input();
	iniSQbox();
	getDealDeptListByMID();
    
	getCPFormListByModel();//得到扩展字段及其内容

	//loadSQFormUpload();
	//上传文档
	//publicUploadDOC("uploadify",true,false,"",0,'',site_id,"");
	
	//得到诉求目的列表
	getPurposeList();
	//得到特征标记列表
	getTagList();

	showSelectDiv("cat_name","cat_tree_div",300,"leftMenu");
	showCategoryTree();  //填充树状数据
	showSelectDiv("area_name","area_tree_div",300,"areaMenu");
	showAreaTree();

    local_IP = "<%=request.getRemoteAddr()%>";

});

init_editer("sq_content");

function getCPFormListByModel()
{
	var from_list = jsonrpc.SQModelRPC.getCPFormListByModel(model_id);
	    from_list = List.toJSList(from_list);
	if(from_list != null && from_list.size() > 0)
	{
		var str = "";
		for(var i=0;i<from_list.size();i++)
		{			
			str += '<tr><th>'+from_list.get(i).field_cname+'：</th><td><input id="'+from_list.get(i).field_ename+'" name="'+from_list.get(i).field_ename+'" type="text" class="width200"/></td></tr>';
		}
		$("#sq_title_info_tr").after(str);
	}
}

function getDealDeptListByMID()
{
	var dept_list = AppealModelRPC.getAppealDeptList(model_id);
        dept_list = List.toJSList(dept_list);
	var str = "";
	if(dept_list != null && dept_list.size()>0)
	{		
		for(var i=0;i<dept_list.size();i++)
		{
			if(i==0){
				str += '<li style="width:120px;overflow:hidden;display:block;height:20px;padding-right:5px" title="'+dept_list.get(i).dept_name+'"><input type="radio" id="do_dept" name="do_dept" value="'+dept_list.get(i).dept_id+'" checked><label>'+dept_list.get(i).dept_name+'</label></li>';
			}else{
				str += '<li style="width:120px;overflow:hidden;display:block;height:20px;padding-right:5px" title="'+dept_list.get(i).dept_name+'"><input type="radio" id="do_dept" name="do_dept" value="'+dept_list.get(i).dept_id+'"><label>'+dept_list.get(i).dept_name+'</label></li>';
			}
		}		
	}
	$("#dept_list").html(str);
}

function getPurposeList()
{
	var p_list = jsonrpc.PurposeRPC.getPurposeList();
	    p_list = List.toJSList(p_list);
	var str = "";
	if(p_list != null && p_list.size()>0)
	{		
		for(var i=0;i<p_list.size();i++)
		{
			if(i==0){
				str += '<li style="width:100px;overflow:hidden;display:block;height:20px;padding-right:5px" title="'+p_list.get(i).pur_name+'"><input type="radio" id="pur_id" name="pur_id" value="'+p_list.get(i).pur_id+'" checked><label>'+p_list.get(i).pur_name+'</label></li>';
			}else{
				str += '<li style="width:100px;overflow:hidden;display:block;height:20px;padding-right:5px" title="'+p_list.get(i).pur_name+'"><input type="radio" id="pur_id" name="pur_id" value="'+p_list.get(i).pur_id+'"><label>'+p_list.get(i).pur_name+'</label></li>';
			}
		}		
	}
	$("#purpose_list").html(str);

}
//显示地区分类树
function showAreaTree()
{
	json_data = eval(jsonrpc.AreaRPC.getAreaTreeJsonStr());
	setAppointTreeJsonData("areaTree",json_data);
	initAreaTree();
}
//初始化地区分类树点击事件 
function initAreaTree()
{
	$('#areaTree').tree({		
		onClick:function(node){			
				selectedArea(node.id,node.text);            
		}
	});
}
//点击树节点,修改菜单列表数据  (发生地区)
function selectedArea(id,text){
	$("#area_name").val(text);
	$("#area_id").val(id);
	$("#area_tree_div").hide();
}
//显示内容分类树
function showCategoryTree()
{
	json_data = eval(jsonrpc.AppealCategoryRPC.getCategoryTreeJsonStr());
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
}
//初始化内容分类树点击事件 
function initMenuTree()
{
	$('#leftMenuTree').tree({		
		onClick:function(node){	
				selectedCat(node.id,node.text);            
		}
	});
}
//点击树节点,修改菜单列表数据 (内容分类)
function selectedCat(id,text){
	$("#cat_name").val(text);
	$("#cat_id").val(id);
	$("#cat_tree_div").hide();
}
//得到特征标记列表
function getTagList()
{
	var t_list = jsonrpc.AssistRPC.getTagListByAPPSite("appeal","");
	t_list = List.toJSList(t_list); 
	if(t_list != null && t_list.size() > 0)
	{
		for(var i=0;i<t_list.size();i++)
		{
			$("#tag_list").append('<li><input type="checkbox" id="tag_id" name="tag_id" value="'+t_list.get(i).tag_id+'"><label>'+t_list.get(i).tag_name+'</label></li>');
		}
	}
}
function getV(input_id){
	return KE.html(input_id);
}
function SubmitLetterInfo()
{
	var SQBean = BeanUtil.getCopy(SQBean);
	$("#sq_info_table").autoBind(SQBean);

    SQBean.model_id = model_id;
	var pur_id = $(":radio[id='pur_id']:checked").val();
	if(pur_id == ""){
       SQBean.pur_id = "0";
	}else{
	   SQBean.pur_id = pur_id;
	}
	if($("#area_id").val() ==""){
		SQBean.area_id = "0";
	}else{
		SQBean.area_id = $("#area_id").val();
	}

	if($("#cat_id").val() ==""){
		SQBean.cat_id = "0";
	}else{
		SQBean.cat_id = $("#cat_id").val();
	}

	if($("#sq_realname").val() ==""){
		top.msgAlert("姓名不能为空!");
		return;
	}else{
		SQBean.sq_realname = $("#sq_realname").val();
	}
	SQBean.sq_sex = $(":radio[id='sq_sex']:checked").val()+"";
	if($("#sq_age").val() == ""){
		SQBean.sq_age = 0;
	}else{
		SQBean.sq_age = $("#sq_age").val()
	}
	SQBean.sq_tel = $("#sq_tel").val();
	SQBean.sq_phone = $("#sq_phone").val();
	SQBean.sq_card_id = $("#sq_card_id").val()+"";
	SQBean.sq_email = $("#sq_email").val();
	SQBean.sq_vocation = $('#sq_vocation').val();
	SQBean.sq_address = $('#sq_address').val();
	SQBean.sq_ip = local_IP+"";

	if(getV("sq_content") == ""){
		top.msgAlert("信件内容不能为空!");
		return;
	}else{
		SQBean.sq_content = getV("sq_content");
	    SQBean.sq_content2= getV("sq_content");
	}
    if($("#sq_title").val() == ""){
        top.msgAlert("信件标题不能为空!");
		return;
	}else{
		SQBean.sq_title = $("#sq_title").val();
	    SQBean.sq_title2 = $("#sq_title").val();
	}
	if($(":radio[id='do_dept']:checked").val() == ""){
		top.msgAlert("请选择收件单位或人!");
		return;
	}else{
		SQBean.do_dept = $(":radio[id='do_dept']:checked").val()+"";
	}
	var tag_ids = $(":checkbox[id='tag_id']").getCheckValues()+"";

	/*
	if(!standard_checkInputInfo("ysq_info"))
	{
		return;
	}*/

    var sq_atta_path = "";
	var sq_atta_name = "";
    var SQAttBean = BeanUtil.getCopy(SQAttachment);
    
	if(sq_atta_path !="")
	{
		alert(sq_atta_path);
		SQAttBean.att_name(sq_atta_path);
		SQAttBean.att_path(sq_atta_name);
	}
	if(SQRPC.insertSQ(SQBean,SQAttBean))
	{
	   /*
		    var l = ModelRPC.getCPFormListByModel(model_id);
			    l = List.toJSList(l);
			    alert("l ===="+l.size());
			var sc_list = null;
			if(l != null && l.size() > 0)
			{
				for(var i=0;i<l.size();i++)
				{
					var  SQCustom = new Bean("com.deya.wcm.bean.appeal.sq.SQCustom",true);

					var filed_ename = l.get(i).getField_ename();
					var cu_value = $("#"+filed_ename).val();
						alert("cu_value===="+cu_value);
					if(cu_value != null && !"".equals(cu_value.trim()))
					{
						SQCustom.cu_key(filed_ename);
						SQCustom.cu_value(cu_value);
						SQCustom.model_id(model_id);
						SQCustom.sq_id(SQBean.getSq_id());

						sc_list.add(SQCustom);
					}
				}
				if(sc_list != null && sc_list.size() > 0)
					SQManager.insertSQCursom(sc_list);//插入扩展字段
		   }
	    */
		top.msgAlert("操作成功!");
		window.location.href = "/sys/appeal/model/modelList.jsp";
	}else
	{
		top.msgWargin("操作失败!");
	}
}

function iniSQbox()
{
	$(".sq_title_box").bind('click',function(){	
		if($(this).find(".sq_title_right").text()=="点击闭合")
		{
			$(this).find(".sq_title").removeClass("sq_title_minus").addClass("sq_title_plus");
			$(this).find(".sq_title_right").text("点击展开");
			$(this).parent().find(".sq_box_content").hide(300);
		}
		else
		{
			$(this).find(".sq_title").removeClass("sq_title_plus").addClass("sq_title_minus");
			$(this).find(".sq_title_right").text("点击闭合");
			$(this).parent().find(".sq_box_content").show(300);
		}
	})
}

</script>
</head>
<body>
<div id="sq_info_table">
	<span class="blank3"></span>
    <div class="sq_box">
	    <div class="sq_title_box">
			<div class="sq_title sq_title_minus">处理单位</div>
			<div class="sq_title_right">点击闭合</div>
	    </div>
        <div class="sq_box_content">
          <table class="table_view" border="0" cellpadding="0" cellspacing="0">
            <tr>
               <th><span class="f_red">*</span>收件单位/人：</th>
               <td>
                  <ul id="dept_list" width="100%"></ul>
               </td>
           </tr>
          </table>
        </div>
    </div>
	<span class="blank6"></span>
	<div class="sq_box">
	    <div class="sq_title_box">
			<div class="sq_title sq_title_minus">信件特征</div>
			<div class="sq_title_right">点击闭合</div>
		</div>
        <div class="sq_box_content">
             <table class="table_view" border="0" cellpadding="0" cellspacing="0">
            <tr>
               <th>诉求目的：</th>
               <td>
                  <ul id="purpose_list" width="100%" ></ul>
               </td>
           </tr>
           <tr>
                <th>内容分类：</th>
                <td>
                    <input type="text" id="cat_name" value="" class="width200" readOnly="readOnly"/>
                    <div id="cat_tree_div" class="select_div tip hidden border_color">
                        <div id="leftMenuBox">
                            <div id="leftMenu" class="contentBox6 textLeft" style="overflow:auto">
                                <ul id="leftMenuTree" class="easyui-tree tree_list_none" animate="true">
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th>发生地区：</th>
                <td>
                    <input type="text" id="area_name" value="" class="width200" readOnly="readOnly"/><input type="hidden" id="area_id" value=""/>
                    <div id="area_tree_div" class="select_div tip hidden border_color">
                        <div id="areaBox">
                            <div id="areaMenu" class="contentBox6 textLeft" style="overflow:auto">
                                <ul id="areaTree" class="easyui-tree tree_list_none" animate="true">
                                </ul>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <th>特征标记：</th>
                <td>
                    <ul id="tag_list">
                    </ul>
                </td>
            </tr>
          </table>
        </div>
    </div>
    <span class="blank6"></span>
    <div class="sq_box">
	    <div class="sq_title_box">
			<div class="sq_title sq_title_minus">写信人信息</div>
			<div class="sq_title_right">点击闭合</div>
		</div>
        <div class="sq_box_content">
             <table class="table_view" border="0" cellpadding="0" cellspacing="0" >
           <tr>
                <th><span class="f_red">*</span>姓名：</th>
                <td><input id="sq_realname" name="sq_realname" type="text" value=""/><div class="cError"></div></td>
                <th>性别：</th>
                <td>
                    <ul>
                        <li><input id="sq_sex" name="sq_sex" type="radio" value="1" checked="checked"/>
                            <label for="d">男</label>
                        </li>
                        <li><input id="sq_sex" name="sq_sex" type="radio" value="0"/>
                            <label for="e">女</label>
                        </li>
                    </ul>
                </td>
           </tr>
           <tr>
                <th>年龄：</th>
                <td><input id="sq_age" name="sq_age" type="text"  value=""/></td>
                <th>固定电话：</th>
                <td><input id="sq_tel" name="sq_tel" type="text"  value=""/></td>
           </tr>
           <tr>
                <th>手机：</th>
                <td><input id="sq_phone" name="sq_phone" type="text"  value=""/><div class="cError"></div></td>
                <th>身份证：</th>
                <td><input id="sq_card_id" name="sq_card_id" type="text"  value=""/></td>
           </tr>
           <tr>
                <th>Email：</th>
                <td><input id="sq_email" name="sq_email" type="text"  value=""/><div class="cError"></div></td>
                <th>职业：</th>
                <td>
                    <select id="sq_vocation" name="sq_vocation">
                        <option value="工人" >工人</option> 
                        <option value="教师" >教师</option> 
                        <option value="农民" >农民</option> 
                        <option value="医生" >医生</option> 
                        <option value="现役军人" >现役军人</option> 
                        <option value="公务员" >公务员</option> 
                        <option value="学生" >学生</option> 
                        <option value="个体经营者" >个体经营者</option> 
                        <option value="企业管理人员" >企业管理人员</option> 
                        <option value="专业技术人员" >专业技术人员</option> 
                        <option value="事业单位工作人员" >事业单位工作人员</option> 
                        <option value="律师" >律师</option> 
                        <option value="文体人员" >文体人员</option> 
                        <option value="自由职业者" >自由职业者</option> 
                        <option value="无业人员" >无业人员</option> 
                        <option value="退（离）休人员" >退（离）休人员</option> 
                        <option value="其它" >其它</option> 
                    </select>
                </td>
            </tr>
        </table>
        <table class="table_view" border="0" cellpadding="0" cellspacing="0">
            <tr id="sq_title_info_tr">
                  <th>住址：</th>
                  <td><input id="sq_address" name="sq_address" type="text" style="width:500px;"/></td>
            </tr>
       </table>
        </div>
    </div>
    <span class="blank6"></span>
    <div class="sq_box">
		<div class="sq_title_box">
			<div class="sq_title sq_title_minus">信件信息</div>
			<div class="sq_title_right">点击闭合</div>
		</div>
        <div class="sq_box_content">
             <table class="table_form" border="0" cellpadding="0" cellspacing="0" >		
                <tr>
                      <th><span class="f_red">*</span>信件标题：</th>
                      <td><input id="sq_title" name="sq_title" type="text"  style="width:500px;" value=""/><div  class="cError"></div></td>
                </tr>
                <tr>
                      <th><span class="f_red">*</span>信件内容：</th>
                      <td>
                           <textarea id="sq_content" name="sq_content" style="width:600px;height:200px"></textarea>
                           <div class="cError"></div>
                      </td>
                 </tr>
                 <!--tr>
                      <th>上传附件：</th>
                      <td>
                            <input type="hidden" name="sq_atta_name" id="sq_atta_name" value="">
                            <input type="hidden" name="file_url" id="file_url" value="">
                            <span class="blank3"></span>
                            <div id="fileQueue"></div>
                            <input id="uploadify" name="uploadify" type="file" style="width:250px;"/>
                            <p>允许上传格式为:jpg,bmp,png,gif,doc,xls,txt,rar,zip；附件小于5M。</p>
                      </td>
                 </tr-->
                 <tr>
                      <th><span class="f_red">*</span>公开意愿：</th>
                      <td>
                          <ul>
                            <li><input id="d4" name="is_open" type="radio" value="1" checked="checked"/>
                            <label for="d4">同意</label>
                            </li>
                            <li><input id="e4" name="is_open" type="radio" value="0"/><label for="e4">不同意</label></li>
                          </ul>
                           如果您选择"不同意",我们可能将对您的写信内容及办理结果不进行公示!
                      </td>
                  </tr>
                </table>
        </div>
	</div>
	<span class="blank12"></span>
	<div style="text-indent:40px;">
         <input id="btnOK" name="btnOK" type="submit" onclick="SubmitLetterInfo()" value="提交"/>
         <input id="btnReSet" name="btnReSet" type="button"  onclick="window.history.go(-1)" value="取消"/>
    </div>
    <span class="blank12"></span>
    <div class="line2h"></div>
    <span class="blank3"></span>
</div>
</body>
</html>
