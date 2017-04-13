<%@ page contentType="text/html; charset=utf-8"%>
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
<title>信息修改</title>
<jsp:include page="../include/include_info_tools.jsp"/>
<script type="text/javascript" src="js/m_gk_bszn.js?v=201610091545"></script>
<script type="text/javascript">
<!--
var topnum = "<%=topnum%>";
var site_id = "<%=siteid%>";
var cid = "<%=cid%>";
var app_id = "<%=app_id%>";
var infoid = "<%=infoid%>";
var linksInfo = "";
var focusInfo = "";
var mFlag = false;

var gk_sxyj ="gk_sxyj";
var gk_bltj ="gk_bltj";
var gk_blsx ="gk_blsx";
// var gk_sfbz ="gk_sfbz";
var gk_bllc ="gk_bllc";
var gk_cclx ="gk_cclx";
var gk_sfyj ="gk_sfyj";


	$(document).ready(function(){

        initUeditor(gk_sxyj);
        initUeditor(gk_bltj);
        initUeditor(gk_blsx);
      //  initUeditor(gk_sfbz);
        initUeditor(gk_bllc);
//        initUeditor(gk_cclx);
        initUeditor(gk_sfyj);

		reloadPublicGKInfo();
		
		getDataList("gk_fwlb","gk_fwlb");

		if(infoid != "" && infoid != "null" && infoid != null){		
			defaultBean = ModelUtilRPC.select(infoid,site_id,"gkfbszn");
			if(defaultBean){
				$("#gk_f_bszn_table").autoFill(defaultBean);
                setV(gk_sxyj,defaultBean.gk_sxyj);
                setV(gk_bltj,defaultBean.gk_bltj);
                setV(gk_blsx,defaultBean.gk_blsx);
//                setV(gk_sfbz,defaultBean.gk_sfbz);
                setV(gk_bllc,defaultBean.gk_bllc);
//                setV(gk_cclx,defaultBean.gk_cclx);
                setV(gk_sfyj,defaultBean.gk_sfyj);
				publicReloadUpdateGKInfos();
			}
			$("#addButton").click(updateInfoData);		
			mFlag = true;		
		}
		else
		{
			$("#addButton").click(addInfoData);
			mFlag = false;
		}
	});

//-->
</script>
</head>

<body>
<span class="blank12"></span>
<form action="#" name="form1">
<div id="gk_f_bszn_table">
<jsp:include page="../include/include_public_gk.jsp"/>

<!-- 内容主体不同部分　开始 -->

<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<!--
    	<tr>
			<th>服务类别：</th>
			<td>
				<select id="gk_fwlb" class="input_select" style="width:150px;">
					
				</select>
			</td>
		</tr>
		-->
		<input id="gk_fwlb" type="hidden" value="行政许可项目" class="input_select" style="width:150px;">
    	<tr>
			<th>办理部门：</th>
			<td>
				<input id="gk_bsjg" name="gk_bsjg" type="text" class="width350" maxlength="80" value="" />
			</td>
		</tr>
        <tr>
			<th>办理依据：</th>
			<td>				
				<script id="gk_sxyj" type="text/plain" style="width:95%;height:160px;"></script>
			</td>
		</tr>
		<input id="gk_bldx" name="gk_bldx" type="hidden" class="width350" maxlength="80" value="" />
        <tr>
			<th>申请条件：</th>
			<td>
				<script id="gk_bltj" type="text/plain" style="width:95%;height:160px;"></script>
			</td>
		</tr>
        <tr>
			<th>办理材料：</th>
			<td>
				<script id="gk_blsx" type="text/plain" style="width:95%;height:160px;"></script>
			</td>
		</tr>
		<tr>
			<th>办理流程：</th>
			<td>
				<script id="gk_bllc" type="text/plain" style="width:95%;height:160px;"></script>
			</td>
		</tr>
		<tr>
			<th>办理地点：</th>
			<td>
				<input id="gk_gsfs" name="gk_gsfs" type="text" class="width350" maxlength="80" value="" />
			</td>
		</tr>
        <tr>
			<th>办理时间：</th>
			<td>
				<input id="gk_bgsj" name="gk_bgsj" type="text" class="width350" maxlength="80" value="" />
			</td>
		</tr>
		<tr>
			<th>联系电话：</th>
			<td>
				<input id="gk_zxqd" name="gk_zxqd" type="text" class="width350" maxlength="80" value="" />
			</td>
		</tr>
		 <tr>
			<th>法定办理时限：</th>
			<td>
				<input id="gk_blshixian" name="gk_blshixian" type="text" class="width350" maxlength="80" value="" />
			</td>
		</tr>
<!--		<tr>
			<th>收费标准：</th>
			<td>				
				<script id="gk_sfbz" type="text/plain" style="width:95%;height:160px;"></script>
			</td>
		</tr>-->
		<tr>
			<th>收费依据及标准：</th>
			<td>				
				<script id="gk_sfyj" type="text/plain" style="width:95%;height:160px;"></script>
			</td>
		</tr>



<!--        <tr>
			<th>乘车路线：</th>
			<td>
				<script id="gk_cclx" type="text/plain" style="width:95%;height:160px;"></script>
			</td>
		</tr>-->
		<!--
        <tr>
			<th>机构网址：</th>
			<td>
				<input id="gk_jgwz" name="gk_jgwz" type="text" class="width350" maxlength="80" value="" />
			</td>
		</tr>
    
    	<tr>
			<th style="vertical-align:top;">监督检查：</th>
			<td style="">
				<textarea id="gk_jdjc" name="gk_jdjc" style="width:95%;height:160px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			</td>
		</tr>
        <tr>
			<th style="vertical-align:top;">责任追究：</th>
			<td style="">
				<textarea id="gk_zrzj" name="gk_zrzj" style="width:95%;height:160px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			</td>
		</tr>
		-->
        <!--<tr>
			<th style="vertical-align:top;">网上咨询：</th>
			<td style="">
				<input id="gk_wszx" name="gk_wszx" type="text" class="width350" maxlength="80" value="" />
			<span class="blank9"></span>
			</td>
		</tr>
    
    
		<tr>
			<th style="vertical-align:top;">网上办理：</th>
			<td style="">
				<input id="gk_wsbl" name="gk_wsbl" type="text" class="width350" maxlength="80" value="" />
			<span class="blank9"></span>
			</td>
		</tr>-->
        <!--
        <tr>
			<th style="vertical-align:top;">状态查询：</th>
			<td style="">
				<textarea id="gk_ztcx" name="gk_ztcx" style="width:95%;height:160px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			</td>
		</tr>
        
        <tr>
			<th style="vertical-align:top;">网上投诉：</th>
			<td style="">
				<textarea id="gk_wsts" name="gk_wsts" style="width:95%;height:160px;visibility:hidden;"></textarea>
			<span class="blank9"></span>
			</td>
		</tr>
        -->
        <tr>
			<th style="vertical-align:top;">备注：</th>
			<td style="">
				<textarea id="gk_memo" name="gk_memo" style="width:64%;height:70px;"></textarea>
			<span class="blank9"></span>
			</td>
		</tr>
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
