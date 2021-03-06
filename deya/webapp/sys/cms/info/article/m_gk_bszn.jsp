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
<title>信息维护</title>
<jsp:include page="../include/include_info_tools.jsp"/>
<script type="text/javascript" src="js/m_gk_bszn.js?v=20150915"></script>
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
var gk_sfbz ="gk_sfbz";
var gk_bllc ="gk_bllc";
//var gk_cclx ="gk_cclx";
//var gk_sfyj ="gk_sfyj";
var gk_bljg ="gk_bljg";
var gk_bgxz ="gk_bgxz";


	$(document).ready(function(){

        initUeditor(gk_sxyj);
        initUeditor(gk_bltj);
        initUeditor(gk_blsx);
        initUeditor(gk_sfbz);
        initUeditor(gk_bllc);
        //initUeditor(gk_cclx);
        //initUeditor(gk_sfyj);
        initUeditor(gk_bljg);
        initUeditor(gk_bgxz);

		reloadPublicGKInfo();
		
		getDataList("gk_fwlb","gk_fwlb");

		if(infoid != "" && infoid != "null" && infoid != null){		
			defaultBean = ModelUtilRPC.select(infoid,site_id,"gkfbszn");
			if(defaultBean){
				$("#gk_f_bszn_table").autoFill(defaultBean);
                setV(gk_sxyj,defaultBean.gk_sxyj);
                setV(gk_bltj,defaultBean.gk_bltj);
                setV(gk_blsx,defaultBean.gk_blsx);
                setV(gk_sfbz,defaultBean.gk_sfbz);
                setV(gk_bllc,defaultBean.gk_bllc);
                //setV(gk_cclx,defaultBean.gk_cclx);
                //setV(gk_sfyj,defaultBean.gk_sfyj);
                setV(gk_bljg,defaultBean.gk_bljg);
                setV(gk_bgxz,defaultBean.gk_bgxz);
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
<input id="gk_cclx" name="gk_cclx" type="hidden" value=""/>
<input id="gk_sfyj" name="gk_sfyj" type="hidden" value=""/>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
    	<tr>
			<th>事项类型：</th>
			<td>
				<select id="gk_fwlb" class="input_select" style="width:150px;"></select>
			</td>
		</tr>
        <tr>
            <th>办理依据：</th>
            <td>
                <script id="gk_sxyj" type="text/plain" style="width:95%;height:160px;"></script>
            </td>
        </tr>
        <tr>
            <th>行使层级：</th>
            <td>
                <input id="gk_xzcj" name="gk_xzcj" type="text" class="width350" maxlength="80" value=""/>
            </td>
        </tr>
        <tr>
            <th>通办范围：</th>
            <td>
                <select id="gk_tbfw" name="gk_tbfw" class="input_select" style="width:150px;">
                    <option value="无">请选择</option>
                    <option value="跨省">跨省</option>
                    <option value="跨市">跨市</option>
                    <option value="跨县">跨县</option>
                </select>
            </td>
        </tr>
    	<tr>
			<th>受理机构：</th>
			<td>
				<input id="gk_bsjg" name="gk_bsjg" type="text" class="width350" maxlength="80" value="" />
			</td>
		</tr>
        <tr>
            <th>实施主体性质：</th>
            <td>
                <input id="gk_ssztxz" name="gk_ssztxz" type="text" class="width350" maxlength="80" value=""/>
            </td>
        </tr>
        <tr>
            <th>服务对象：</th>
            <td>
                <input id="gk_bldx" name="gk_bldx" type="text" class="width350" maxlength="80" value=""/>
            </td>
        </tr>
        <tr>
			<th>受理条件：</th>
			<td>
				<script id="gk_bltj" type="text/plain" style="width:95%;height:160px;"></script>
			</td>
		</tr>
        <tr>
            <th>联办机构：</th>
            <td>
                <input id="gk_lbjg" name="gk_lbjg" type="text" class="width350" maxlength="80" value=""/>
            </td>
        </tr>
        <tr>
            <th>承诺办结时限：</th>
            <td>
                <input id="gk_blshixian" name="gk_blshixian" type="text" class="width350" maxlength="80" value="" />
            </td>
        </tr>
        <tr>
            <th>法定办结时限：</th>
            <td>
                <input id="gk_fdbjsx" name="gk_fdbjsx" type="text" class="width350" maxlength="80" value="" />
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
            <th>监督电话：</th>
            <td>
                <input id="gk_jddh" name="gk_jddh" type="text" class="width350" maxlength="80" value="" />
            </td>
        </tr>
        <tr>
            <th>办理结果：</th>
            <td>
                <script id="gk_bljg" type="text/plain" style="width:95%;height:160px;"></script>
            </td>
        </tr>
        <tr>
			<th>申请材料：</th>
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
			<th>收费标准：</th>
			<td>				
				<script id="gk_sfbz" type="text/plain" style="width:95%;height:160px;"></script>
			</td>
		</tr>
        <tr>
            <th>表格及样表下载：</th>
            <td>
                <script id="gk_bgxz" type="text/plain" style="width:95%;height:160px;"></script>
            </td>
        </tr>
		<!--
        <tr>
            <th>收费依据：</th>
            <td>
            <script id="gk_sfyj" type="text/plain" style="width:95%;height:160px;"></script>
            </td>
        </tr>
        <tr>
            <th>乘车路线：</th>
            <td>
                <script id="gk_cclx" type="text/plain" style="width:95%;height:160px;"></script>
            </td>
        </tr>
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
        <tr>
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
		</tr>
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
			<input id="addCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</div>
</form>
</body>
</html>
