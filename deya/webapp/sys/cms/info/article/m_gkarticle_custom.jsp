<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.deya.wcm.services.model.services.*,com.deya.wcm.services.model.*" %>
<%@page import="com.deya.wcm.services.system.dict.DataDictRPC"%>
<%@page import="com.deya.wcm.bean.system.dict.DataDictBean"%>
<%@page import="com.deya.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String cid = request.getParameter("cid");
String siteid = request.getParameter("site_id");
String infoid = request.getParameter("info_id");
String app_id = request.getParameter("app_id");
String model = request.getParameter("model");
if(cid == null || cid.equals("null")){
	cid = "0";
}
if(app_id == null || app_id.equals("null")){
	app_id = "0";
}
if(model == null || !model.matches("[0-9]*")){
	model = "0";
}
String topnum = request.getParameter("topnum");
if(topnum == null || topnum.trim().equals("") || topnum.trim().equals("null") ){
	topnum = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息维护</title>
<jsp:include page="../include/include_info_tools.jsp"/>
<script type="text/javascript" src="js/m_gk_tygs.js"></script>
<script type="text/javascript" src="js/m_gkarticle_custom.js"></script>
<script type="text/javascript" src="js/gkPublic.js"></script>
<script type="text/javascript">

var model = "<%=model%>";
var topnum = "<%=topnum%>";
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";
var contetnid = "info_content";
var linksInfo = "";
var focusInfo = "";
var mFlag = false;

$(document).ready(function(){

	$("#uploadify_file_table").hide();
	init_input();	
	reloadPublicGKInfo();
	publicUploadButtomLoad("uploadify",true,false,"",0,5,getReleSiteID(),"savePicUrl");

	if(infoid != "" && infoid != "null" && infoid != null){	
		//得到主信息cs_info	
		defaultBean = jsonrpc.InfoCustomRPC.getArticle(infoid);
		//defaultBean = ModelUtilRPC.select(infoid,site_id,"gkftygs");
		if(defaultBean){
			$("#info_article_table").autoFill(defaultBean);	
			//setV(defaultBean.info_content);
			//publicReloadUpdateInfos();	
			publicReloadUpdateGKInfos();	

			//得到公开主信息cs_gk_info
			var gkInfoBean = jsonrpc.InfoCustomRPC.getGKInfo(infoid);
			gkInfoBean = Map.toJSMap(gkInfoBean);
			//alert(customBean.get("id"));
			$("#info_article_table").autoFillMap(gkInfoBean);

			//设置自定义表信息
			var customBean = jsonrpc.InfoCustomRPC.getCustomInfoMap(model,infoid);
			customBean = Map.toJSMap(customBean);
			//alert(customBean.get("id"));
			$("#info_article_table").autoFillMap(customBean);
		}
		$("#addButton").click(updateInfoDataCustom);		
		mFlag = true;		
	}
	else
	{			
		$("#addButton").click(addInfoDataCustom);		
		mFlag = false;
	}
	initButtomStyle();
});
//init_editer_turnpage(contetnid);	

function savePicUrl(url)
{
	$("#thumb_url").val(url);
}
</script>
</head>

<body>
<span class="blank12"></span>
<form action="#" name="form1">
<div id="info_article_table">
<input id="model_id" type="hidden" class="width200" value="<%=model%>" />
<input id="app_id" type="hidden" class="width200" value="<%=app_id%>" />
<jsp:include page="../include/include_public_gk.jsp"/>

<%
Map map = new HashMap();
map.put("model_id", model);
List<Fields> fieldsList = FormRPC.getFormFieldsListByModelIdN(map);
pageContext.setAttribute("fieldsList",fieldsList);

//存放html编辑器字段
List<String> htmlList = new ArrayList<String>();
//存放文件字段
List<String> fileList = new ArrayList<String>();
//存放文件字段的上传文件类型
List<String> fileTypeList = new ArrayList<String>();
%>

<!-- 内容主体不同部分　开始 -->
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
	<%
	   for(int i=0;i<fieldsList.size();i++){
		   Fields field = fieldsList.get(i);
		   //字段名称
		   String field_cnname = field.getField_cnname();
		   //字段名称
		   String field_enname = field.getField_enname().toLowerCase();
		   //字段默认值
		   String default_value = field.getDefault_value();
		   //字段是否显示
		   String display = "";
		   if(field.getIs_display().equals("0")){
			   display = "hidden";//隐藏
		   }
		   //是否必填
		   boolean is_null = false;
		   if(field.getIs_null().equals("1")){
			   is_null = true;//必填
		   }
		   //字段提示
		   String field_text = FieldsUtil.formatQuote(field.getField_text());
		   //字段类型
		   String field_type = field.getField_type();
		   if(field_type.equals("0")){//单行文本
			   //最大字符数
	    	   String field_maxnum = field.getField_maxnum();
	    	   if(field_maxnum==null || "".equals(field_maxnum)){
	    		   field_maxnum = "500";
	    	   }
	    	   //是否必填
			   String onblur = "";
		       if(is_null){//必填
		    	   onblur = "checkInputValue('"+field_enname+"',false,"+field_maxnum+",'"+field_cnname+"','')";
		       }else{
		    	   onblur = "checkInputValue('"+field_enname+"',true,"+field_maxnum+",'"+field_cnname+"','')";
		       }
		       //文本框长度
		       String field_maxlength = field.getField_maxlength();
		   %>
			  <tr class="<%=display%>">
				<th><%if(is_null){%><span class="f_red">*</span><%}%><%=field_cnname%>：</th>
				<td style="">
					<input type="text" name="<%=field_enname%>" id="<%=field_enname%>" style="width:<%=field_maxlength%>px" value="<%=default_value%>" onblur="<%=onblur%>"/>
					<span><%if(!field_text.equals("")){%>注：<%=field_text%><%}%></span>
				</td>
			  </tr>
		   <% 
		   }else if(field_type.equals("1")){//多行文本
			   //宽度
		       String width = field.getWidth();
		       //高度
		       String height = field.getHeight();
		       
		       //最大字符数
	    	   String field_maxnum = field.getField_maxnum();
	    	   if(field_maxnum==null || "".equals(field_maxnum)){
	    		   field_maxnum = "999999999";
	    	   }
	    	   //是否必填
			   String onblur = "";
		       if(is_null){//必填
		    	   onblur = "checkInputValue('"+field_enname+"',false,"+field_maxnum+",'"+field_cnname+"','')";
		       }else{
		    	   if(!field_maxnum.equals("")){
		    		   onblur = "checkInputValue('"+field_enname+"',true,"+field_maxnum+",'"+field_cnname+"','')";
		    	   }
		       }
		       
			 %>
			   <tr class="<%=display%>">
				<th><%if(is_null){%><span class="f_red">*</span><%}%><%=field_cnname%>：</th>
				<td style="">
					<textarea name="<%=field_enname%>" id="<%=field_enname%>" style="width:<%=width%>px;height:<%=height%>px" onblur="<%=onblur%>"><%=default_value%></textarea>
					<span><%if(!field_text.equals("")){%>注：<%=field_text%><%}%></span>
				</td>
			  </tr>
			   <%
		   }else if(field_type.equals("2")){//多行文本 带html编辑器
			   htmlList.add(field_enname);
			   //宽度
		       String width = field.getWidth();
		       //高度
		       String height = field.getHeight();
		       %>
		       <tr class="<%=display%>">
				<th style="vertical-align:top;"><%if(is_null){%><span class="f_red">*</span><%}%><%=field_cnname%>：</th>
				<td style="">
					 <textarea id="<%=field_enname%>" name="<%=field_enname%>" style="width:<%=width%>px;;height:<%=height%>px;visibility:hidden;"><%=default_value%></textarea>
					<span><%if(!field_text.equals("")){%>注：<%=field_text%><%}%></span>
				</td>
			  </tr>
		       <%
		   }else if(field_type.equals("3")){//选项
			   String select_view = field.getSelect_view();
		       //System.out.println("select_view=====" + select_view);
		       String data_type_id = field.getData_type_id();//数据字典分类ID
		       List<DataDictBean> dataDictBeans = DataDictRPC.getDataDictList(data_type_id);
		       //String default_value = field.getDefault_value();//默认值
			   %>
			   <tr class="<%=display%>">
				<th><%if(is_null){%><span class="f_red">*</span><%}%><%=field_cnname%>：</th>
				<td style="">
					<%
					if(select_view.equals("0")){//选项是单选下拉框内容
						%>
						<select name='<%=field_enname%>' id='<%=field_enname%>'>
						 <%
						    if(default_value.equals("")){
						    	if(!is_null){
						    	%>
						    	<option value='' selected='selected'>--请选择--</option>
						    	<%}
						    }
						    DataDictBean dataDictBean = null;
						    for(int j=0;j<dataDictBeans.size();j++){
						    	dataDictBean = dataDictBeans.get(j);
						    	if(dataDictBean.getDict_id().equals(default_value)){
						    		%>
						    		<option value='<%=dataDictBean.getDict_id()%>' selected='selected'><%=dataDictBean.getDict_name()%></option>
						    		<%
						    	}else{
						    	%>
						    	<option value='<%=dataDictBean.getDict_id()%>'><%=dataDictBean.getDict_name()%></option>
						    	<%}
						    }
						 %>
						</select>
						<%
					}else if(select_view.equals("1")){//选项是多选下拉框
					   	%>
					   	<select name='<%=field_enname%>' id='<%=field_enname%>' style='height:<%=dataDictBeans.size()*18%>px' size='<%=dataDictBeans.size()%>' multiple='multiple'>
					   	    <%
						   	    DataDictBean dataDictBean = null;
							    for(int j=0;j<dataDictBeans.size();j++){
							    	dataDictBean = dataDictBeans.get(j);
							    	%>
							    	<option value='<%=dataDictBean.getDict_id()%>'><%=dataDictBean.getDict_name()%></option>
							    	<%
							    }
					   	    %>  
					   	</select>
					   	<%
					}else if(select_view.equals("2")){//选项是单选按钮
						DataDictBean dataDictBean = null;
					    for(int j=0;j<dataDictBeans.size();j++){
					    	dataDictBean = dataDictBeans.get(j);
					    	if(!default_value.equals("")){
					    		List<String> listDefault = Arrays.asList(default_value.split(","));
						    	if(listDefault.contains(dataDictBean.getDict_id())){//默认值
						    		%>
						    		<input type='radio' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'  checked='true'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
						    		<%
						    	}else{
						    		%>
						    		<input type='radio' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
						    		<%
						    	}
					    	}else{//如果没有默认值 并且也为必填 就设置第一个值为默认值
					    		if(is_null){
					    			if(j==0){
					    			%>
					    			  <input type='radio' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'  checked='true'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
					    			<%
					    			}else{
							    		%>
								    	<input type='radio' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
								    	<%	
					    			}
					    		}else{
					    			%>
							    	<input type='radio' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
							    	<%
					    		}
					    	}
					    	
					    }
					}else if(select_view.equals("3")){//选项是复选框
						DataDictBean dataDictBean = null;
					    for(int j=0;j<dataDictBeans.size();j++){
					    	dataDictBean = dataDictBeans.get(j);
					    	if(!default_value.equals("")){
					    		List<String> listDefault = Arrays.asList(default_value.split(","));
						    	if(listDefault.contains(dataDictBean.getDict_id())){//默认值
						    		%>
						    		<input type='checkbox' id='<%=field_enname%>' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'  checked='true'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
						    		<%
						    	}else{
						    		%>
						    		<input type='checkbox' id='<%=field_enname%>' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
						    		<%
						    	}
					    	}else{//如果没有默认值 并且也为必填 就设置第一个值为默认值
					    		if(is_null){
					    			if(j==0){
					    			%>
					    			  <input type='checkbox' id='<%=field_enname%>' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'  checked='true'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
					    			<%
					    			}else{
							    		%>
								    	<input type='checkbox' id='<%=field_enname%>' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
								    	<%	
					    			}
					    		}else{
					    			%>
							    	<input type='checkbox' id='<%=field_enname%>' name='<%=field_enname%>' value='<%=dataDictBean.getDict_id()%>'><%=dataDictBean.getDict_name()%></input>&nbsp;&nbsp;&nbsp;&nbsp;
							    	<%
					    		}
					    	}
					    	
					    }
					}
					%>
					<span><%if(!field_text.equals("")){%>注：<%=field_text%><%}%></span>
				</td>
			  </tr>
			   
			   <%
		   }else if(field_type.equals("4")){//数字
			   //最小数
	    	   String min_num = field.getMin_num();
	    	   if(min_num==null || "".equals(min_num)){
	    		   min_num = "";
	    	   }
	    	   //最大数
	    	   String max_num = field.getMax_num();
	    	   if(max_num==null || "".equals(max_num)){
	    		   max_num = "";
	    	   }
	    	   //是否必填
			   String onblur = "";
		       if(is_null){//必填
		    	   onblur = "checkNumber('"+field_enname+"',"+min_num+",'"+max_num+"',false)";
		       }else{
		    	   onblur = "checkNumber('"+field_enname+"',"+min_num+",'"+max_num+"',true)";
		       }
			   %>
			   <tr class="<%=display%>">
				<th><%if(is_null){%><span class="f_red">*</span><%}%><%=field_cnname%>：</th>
				<td style="">
					<input type="text" name="<%=field_enname%>" id="<%=field_enname%>" style="width:100px" value="<%=default_value%>" onblur="<%=onblur%>"/>
					<span><%if(!field_text.equals("")){%>注：<%=field_text%><%}%></span>
				</td>
			  </tr>
			   <%
			   
		   }else if(field_type.equals("5")){//时间和日期
			   String onblur = "";
			   String field_maxnum = "100";
		       if(is_null){//必填
		    	   onblur = "checkInputValue('"+field_enname+"',false,"+field_maxnum+",'"+field_cnname+"','')";
		       }else{
		    	   onblur = "checkInputValue('"+field_enname+"',true,"+field_maxnum+",'"+field_cnname+"','')";
		       }
			   %>
			   <tr class="<%=display%>">
				<th><%if(is_null){%><span class="f_red">*</span><%}%><%=field_cnname%>：</th>
				<td style="">
				<%
				   if(default_value.equals("nowTime")){//现在时间
					   String nowTime = DateUtil.getCurrentDateTime();
					%>
					  <input id="<%=field_enname%>" name="<%=field_enname%>" type="text" value="<%=nowTime%>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" onblur="<%=onblur%>"/>
					<%   
				   }else if(default_value.equals("")){
					   %>
						  <input id="<%=field_enname%>" name="<%=field_enname%>" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" onblur="<%=onblur%>" />
					   <%  
				   }else{
					   %>
						  <input id="<%=field_enname%>" name="<%=field_enname%>" type="text" value="<%=default_value%>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" readonly="readonly" onblur="<%=onblur%>" />
					   <% 
				   }
				%>
					<span><%if(!field_text.equals("")){%>注：<%=field_text%><%}%></span>
				</td>
			  </tr>
			   
			   <%
		   }else if(field_type.equals("6")){//文件
			   fileList.add(field_enname);
			   fileTypeList.add(default_value);
			   %>
			   <tr class="<%=display%>">
				<th><%if(is_null){%><span class="f_red">*</span><%}%><%=field_cnname%>：</th>
				<td style=""><div style="float:left;margin:auto;">
				<input id="<%=field_enname%>" name="<%=field_enname%>" type="text" style="width:250px;" value="" /></div>
				<div style="float:left">&#160;<input type="file" name="<%=field_enname%>_uploadify" id="<%=field_enname%>_uploadify"/></div>
				<div style="float:left;margin-top:5px">&#160;&#160;&#160;<%if(!field_text.equals("")){%>注：<%=field_text%><%}%></div>
				<span></span>
				</td>
			  </tr>
			  <%
		   }

	   }
	%>
	  </tbody>
	</table>
<!-- 内容主体不同部分　结束 -->

<jsp:include page="../include/include_public_high_gk.jsp"/>

</div>

<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />
			<input id="addReset" name="btn1" type="button" onclick="window.location.reload()" value="重置" />
			<input id="addCancel" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</div>
</form>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){

	//设置html编辑器
	<%
	  for(int i=0;i<htmlList.size();i++){
		  %>
		  init_editer('<%=htmlList.get(i)%>');
		  <%
	  }
	%>
	<%
	  for(int i=0;i<fileList.size();i++){
		  String fid = fileList.get(i);
		  String fTypes = fileTypeList.get(i);
		  StringBuffer sbFtypes1 = new StringBuffer();
		  StringBuffer sbFtypes2 = new StringBuffer();
		  List<String> listF = Arrays.asList(fTypes.split("\\|"));
		  for(int j=0;j<listF.size();j++){
			  String f = listF.get(j);
			  if(!"".equals(f)){
				  sbFtypes1.append(f+",");
				  sbFtypes2.append("*."+f+";");
			  }
		  }
		  %>
		  publicUploadButtomAllFile("<%=fid%>_uploadify",true,false,"",0,5,getReleSiteID(),'<%=sbFtypes1%>','<%=sbFtypes2%>',"savePicUrCustom('pic_url','<%=fid%>')");
		  <%
	  }
	%>
});


function savePicUrCustom(url,input_name)
{
	$("#"+input_name).val(url);
}


//isNull 是否可以为空
function checkNumber(id,min_num,max_mun,isNull){
	var v = $("#"+id).val();
	if(!isNull){
       if($.trim(v)==''){
    	 val.showError(id,"该字段不能为空！");
      	 return; 
       }
       if(min_num!=''){
	         if(v<min_num){
	        	 val.showError(id,"输入值要大于"+min_num);
	        	 return;
	         }
		}
		if(max_mun!=''){
	        if(v>max_mun){
	       	 val.showError(id,"输入值要小于"+max_mun);
	       	 return;
	        }
		}
	}else{
		if($.trim(v)!=''){
			if(min_num!=''){
		         if(v<min_num){
		        	 val.showError(id,"输入值要大于"+min_num);
		        	 return;
		         }
			}
			if(max_mun!=''){
		        if(v>max_mun){
		       	 val.showError(id,"输入值要小于"+max_mun);
		       	 return;
		        }
			}
		}
    }
	
}
</script>