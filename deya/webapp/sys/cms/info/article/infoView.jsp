<%@ page contentType="text/html; charset=utf-8"%>
<%
String site_id = request.getParameter("site_id");
String info_id = request.getParameter("info_id");
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
<script type="text/javascript">
<!--

var info_id = "<%=info_id%>";
var site_id = "<%=site_id%>";
var topnum = "<%=topnum%>";
var SiteRPC = jsonrpc.SiteRPC; 

$(document).ready(function(){	
	initButtomStyle();
	init_input();	
	defaultBean = InfoBaseRPC.getInfoById(info_id,site_id);

	if(defaultBean != null)
	{
		if(defaultBean.is_host == 1)
		{//引用的信息显示编辑源信息按钮
			$("#from_info_button").show();
		}

		var model_ename = ModelRPC.getModelEName(defaultBean.model_id);
		if(model_ename == "link")
		{//链接型的			
			$("#contentFrame").attr("src",replaceURL(defaultBean.content_url));
		}else
		{
			if(defaultBean.app_id == "cms")
				//$("#contentFrame").attr("src",SiteRPC.getDefaultSiteDomainBySiteID(defaultBean.site_id)+SiteRPC.getSitePort()+"/info/contentView.jsp?info_id=<%=info_id%>&site_id=<%=site_id%>");
				$("#contentFrame").attr("src","/info/contentView.jsp?info_id=<%=info_id%>&site_id=<%=site_id%>");
			else
				//$("#contentFrame").attr("src",SiteRPC.getDefaultSiteDomainBySiteID(jsonrpc.SiteRPC.getSiteIDByAppID(defaultBean.app_id))+SiteRPC.getSitePort()+"/info/contentView.jsp?info_id=<%=info_id%>&site_id=<%=site_id%>");
				$("#contentFrame").attr("src","/info/contentView.jsp?info_id=<%=info_id%>&site_id=<%=site_id%>");
		}

		var opt_ids = ","+top.getOptIDSByUser(defaultBean.app_id,site_id)+",";//登录人所拥有管理权限ID
		var is_admin = top.isSiteManager(defaultBean.app_id,site_id);//是否是站点管理员或超级管理员
		//根据信息状态值显示不同的操作		
		if(defaultBean.final_status == -1)
		{
			//删除状态
			$("#opt_308").show();
			$("#operate_table").show();
			$("#addButton").show();
		}else
		{
			if(defaultBean.info_status == 8)
			{
				//发布状态只显示撤销
				if(is_admin == true || opt_ids.indexOf(",307,") > -1)
				{
					$("#opt_307").show();
					$("#operate_table").show();
					$("#addButton").show();
				}			
			}
			if(defaultBean.info_status == 4 || defaultBean.info_status == 3)
			{			
				//待发或撤稿状态显示发布
				if(is_admin == true || opt_ids.indexOf(",302,") > -1)
				{
					$("#opt_302").show();
					$("#operate_table").show();
					$("#addButton").show();
				}
			}
			if(defaultBean.info_status == 2)
			{
                //待审状态显示通过，退稿
                if(is_admin == true || opt_ids.indexOf(",303,") > -1)
                {
                    $("li[id='opt_303']").show().find("input").click();
                    $("li[id='opt_304']").show();
                    $("#operate_table").show();
                    $("#addButton").show();
                }

                //得到该栏目所使用的流程ID
                var wf_id = CategoryRPC.getWFIDByCatID(defaultBean.cat_id,defaultBean.site_id);
                if(wf_id != 0)
                {
                    var auditHtml = "";
                    var step_id = getMaxStepIDByUserID(wf_id, defaultBean.app_id, site_id);
                    var workFlowBean = jsonrpc.WorkFlowRPC.getWorkFlowBean(wf_id);
                    var workStepList = workFlowBean.workFlowStep_list;
                    workStepList = List.toJSList(workStepList);
                    for (var i = 0; i < workStepList.size(); i++) {
                        if (workStepList.get(i).step_id > step_id) {
                            if (workStepList.get(i).required == 1) {
                                $("#audit_tr").removeClass("hidden");
                                var html = '<input id="auditChecked" name="auditStep" type="radio" checked="checked" onclick="changeStepId(' + (workStepList.get(i).step_id - 1) + ',2)"/><label for="e">' + workStepList.get(i).step_name + '</label>&nbsp;&nbsp;';
                                auditHtml = auditHtml + html;
                                $("#audit_list").append(html);
                                $("#auditChecked").click();
                                break;
                            }
                            else {
                                $("#audit_tr").removeClass("hidden");
                                var html = '<input name="auditStep" type="radio" onclick="changeStepId(' + (workStepList.get(i).step_id - 1) + ',2)"/><label for="e">' + workStepList.get(i).step_name + '</label>&nbsp;&nbsp;';
                                auditHtml = auditHtml + html;
                                $("#audit_list").append(html);
                            }
                        }
                    }
                }

                //显示发布
                if(is_admin == true || opt_ids.indexOf(",302,") > -1)
                {
                    $("#opt_302").show().find("input").click();
                    defaultBean.step_id = 100;
                    defaultBean.info_status = 8;
                    //$("#opt_999").show();
                }
			}

			if(defaultBean.info_status == 1 || defaultBean.info_status == 0)
			{
				//草,退稿状态下显示待审
				$("#li_ds").show();
				$("#btn300").show();  //草稿和退稿情况是可以修改信息的
				$("#operate_table").show();
				$("#addButton").show();
			}
		}		

		//显示编辑按钮
		if(is_admin == true)
		{			
			$("#btn300").show();
		}else
		{
			if(opt_ids.indexOf(",300,") > -1)//有修改权限
			{
                $("#btn300").show();
			}
		}
	}

	showAutoDescTr();
});

//打开修改窗口
function openUpdatePage()
{
	//判断是不是引用信息
	if(defaultBean.is_host == 1)
	{		
		window.location.href = "/sys/cms/info/article/update_info.jsp?cid="+defaultBean.cat_id+"&info_id="+info_id+"&site_id="+defaultBean.site_id+"&app_id="+defaultBean.app_id+"&model="+defaultBean.model_id+"&topnum="+topnum;
	}else
	{
		switch(defaultBean.cat_id)
		{
			case 10:window.location.href = "/sys/cms/info/article/m_gk_gkzn.jsp?cid="+defaultBean.cat_id+"&site_id="+defaultBean.site_id+"&app_id="+defaultBean.app_id+"&topnum="+topnum;
					break;
			case 11:window.location.href = "/sys/cms/info/article/m_gk_gkzn.jsp?cid="+defaultBean.cat_id+"&site_id="+defaultBean.site_id+"&app_id="+defaultBean.app_id+"&topnum="+topnum;
					break;
			case 12:window.location.href = "/sys/cms/info/article/m_gk_gkzn.jsp?cid="+defaultBean.cat_id+"&info_id="+defaultBean.info_id+"&site_id="+defaultBean.site_id+"&app_id="+defaultBean.app_id+"&model="+defaultBean.model_id+"&topnum="+topnum;
					break;
			default:window.location.href = "/sys/cms/info/article/"+getAddPagebyModel(defaultBean.model_id)+"?cid="+defaultBean.cat_id+"&info_id="+info_id+"&site_id="+site_id+"&app_id="+defaultBean.app_id+"&topnum="+topnum;
		}	
	}
}

function getAddPagebyModel(model_id)
{
	var add_page = jsonrpc.ModelRPC.getModelAddPage(model_id);
	if(add_page == "" || add_page == null)
		add_page = "m_article.jsp";
	return add_page;
}

function changeStepId(step_id,status)
{
    defaultBean.step_id = step_id;
    defaultBean.info_status = status;
}

//保存操作
function saveInfoStatus()
{
	var user_id = LoginUserBean.user_id;
	var info_status = $(":checked").val();
	var operate_boolean = false;
	if(info_status == null || info_status == "")
	{
		top.msgWargin("请选择信息状态");
		return;
	}
	var info_list = new List();
	info_list.add(defaultBean);
	
	switch(info_status)
	{
		case "-1":operate_boolean = InfoBaseRPC.goBackInfo(info_list);//还原
				break;
		case "1":operate_boolean = InfoBaseRPC.noPassInfoStatus(info_id,$("#auto_desc").val());//退稿
				break;
		case "2":operate_boolean = InfoBaseRPC.passInfoStatus(info_list,user_id);//待审
				break;
		case "3":operate_boolean = InfoBaseRPC.updateInfoStatus(info_list,"3");//撤销
				break;
        case "4":operate_boolean = InfoBaseRPC.updateInfoStatus(info_list,"4");//待发
            break;
		case "8":operate_boolean = InfoBaseRPC.updateInfoStatus(info_list,"8");//发布
				break;
		case "99":operate_boolean = InfoBaseRPC.passInfoStatus(info_list,user_id);//通过
				break;
	}

	if(operate_boolean)
	{		
		top.msgAlert("信息状态保存成功");
		try{
			top.getCurrentFrameObj(topnum).reloadInfoDataList();
			top.tab_colseOnclick(top.curTabIndex);
		}catch(e){
            top.tab_colseOnclick(top.curTabIndex);
        }
	}else{
		top.msgWargin("信息状态保存失败");
	};
}

function showAutoDescTr()
{
	$(":radio").click(function(){
		if($(this).val() == 1)
		{
			$("#auto_desc_tr").show()
		}else
			$("#auto_desc_tr").hide()
	});
}

function openFromInfoPage()
{
	var from_info_bean = InfoBaseRPC.getInfoById(defaultBean.from_id);	
	if(from_info_bean != null)
	{
		window.location.href = "/sys/cms/info/article/"+getAddPagebyModel(from_info_bean.model_id)+"?cid="+from_info_bean.cat_id+"&info_id="+defaultBean.from_id+"&site_id="+from_info_bean.site_id+"&app_id="+from_info_bean.app_id+"&topnum="+topnum;
	}
}

//-->
</script>
</head>

<body>
<span class="blank12"></span>
<form action="#" name="form1">
<div id="info_article_table">

<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>			
			<td style="text-align:center">
				<iframe id="contentFrame" width="98%" height="450px" frameborder="0" class="border_color" src=""></iframe>
			</td>
		</tr>		
	</tbody>
</table>
<table id="operate_table" class="table_form hidden" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th>信息状态：</th>
			<td>
				<ul class="flagClass">									
					<li id="li_ds" class="hidden"><input id="a" name="info_status" type="radio" value="2" onclick="isShowAudit(true)" /><label for="a">待审</label></li>
					<li id="opt_308" class="hidden"><input id="b" name="info_status" type="radio" value="-1" onclick="isShowAudit(false)" /><label for="b">还原</label></li>
					<li id="opt_303" class="hidden"><input id="c" name="info_status" type="radio" value="99" onclick="isShowAudit(true)" /><label for="c">通过</label></li>
					<li id="opt_304" class="hidden"><input id="d" name="info_status" type="radio" value="1" onclick="isShowAudit(false)" /><label for="d">退稿</label></li>
					<li id="opt_307" class="hidden"><input id="e" name="info_status" type="radio" value="3" onclick="isShowAudit(false)" /><label for="e">撤销</label></li>
                    <!--<li id="opt_999" class="hidden"><input id="g" name="info_status" type="radio" value="4" onclick="isShowAudit(false)" /><label for="g">待发</label></li>-->
					<li id="opt_302" class="hidden"><input id="f" name="info_status" type="radio" value="8" onclick="isShowAudit(false)" /><label for="f">发布</label></li>
				</ul>
			</td>
		</tr>
        <tr id="audit_tr" class="hidden">
            <th>选择审核人：</th>
            <td id="audit_list">

            </td>
        </tr>
		<tr class="hidden" id="auto_desc_tr">
			<th style="vertical-align:top;">退稿意见：</th>
			<td>
				<textarea id="auto_desc" name="auto_desc" style="width:620px;;height:100px;"></textarea>
			</td>
		</tr>
	</tbody>
</table>
</div>

<!--隔线开始-->
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<!--隔线结束-->
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="addButton" class="hidden" name="btn1" type="button" onclick="saveInfoStatus()" value="保存" />
			<input id="btn300" class="hidden" name="btn1" type="button" onclick="openUpdatePage()" value="编辑" />
			<input id="addCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex);" value="取消" />&nbsp;&nbsp;&nbsp;
			<input id="from_info_button" class="hidden" name="btn1" type="button" onclick="openFromInfoPage()" value="编辑源信息" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</div>
</form>
</body>
</html>
