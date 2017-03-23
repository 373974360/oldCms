/**********************添加操作　开始*************************************/
function fnAddSubject(main_type)
{
	//window.location.href = "add_subject.jsp?site_id="+site_id+"&tn="+main_type;
	addTab(true,"/sys/interview/subject/add_subject.jsp?site_id="+site_id+"&tn="+main_type+"&topnum="+curTabIndex,"维护主题");
}

function getFckeditorValue()
{
	/*
	var oEditor = FCKeditorAPI.GetInstance('info'); 
	var c = oEditor.GetXHTML(true);*/

}

function saveSubject()
{
	var bean = BeanUtil.getCopy(subjectBean);
	$("#subject").autoBind(bean);
    bean.info = getV(contentId);
	
	if(!standard_checkInputInfo("jcxx_tab") || !standard_checkInputInfo("splj_tab"))
	{
		return;
	}

	val.checkEmpty(bean.start_time,"访谈开始时间","start_time");
	val.checkDataTimeAfter(bean.start_time,bean.end_time,"访谈时间","end_time");
	if(!val.getResult()){		
		return;
	}
	
	if(bean.audit_status != 1 && (bean.publish_status != 0 || bean.status != 0 ))
	{
		msgWargin("该主题未通过审核，不能设置发布状态和访谈状态");
		return;
	}
	bean.site_id = site_id;
	bean.apply_user = LoginUserBean.user_id;
	bean.apply_dept = LoginUserBean.dept_id;
	if(SubjectRPC.insertSubject(bean))
	{
		msgAlert("访谈主题"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).showTurnPage();
		getCurrentFrameObj(topnum).showList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("访谈主题"+WCMLang.Add_fail);
	}
}
/**********************添加操作　结束*************************************/


/**********************修改操作　开始*************************************/
function showUpdatePate(main_type,id,audit_status)
{
	if(main_type == "main" || audit_status != 1)
	{
		//window.location.href = "add_subject.jsp?site_id="+site_id+"&tn="+main_type+"&id="+id;
		addTab(true,"/sys/interview/subject/add_subject.jsp?site_id="+site_id+"&tn="+main_type+"&id="+id+"&topnum="+curTabIndex,"维护主题");
	}
	else
		msgAlert("该主题已经通过审核，不能再修改操作");
}

function fnUpdateSubject(main_type)
{
	var selectIDS = table.getSelecteCheckboxValue("id");
	if(selectIDS == "")
	{
		alertN("请选择要修改的主题");
		return;
	}
	else
	{
		if(selectIDS.split(",").length > 1)
		{
			alertN("只能选择一条主题进行修改操作");
			return;
		}
		else
		{			
			if(main_type == "main" || table.getSelecteBeans().get(0).audit_status != 1)
			{
				//window.location.href = "add_subject.jsp?site_id="+site_id+"&tn="+main_type+"&id="+selectIDS;
				addTab(true,"/sys/interview/subject/add_subject.jsp?site_id="+site_id+"&tn="+main_type+"&id="+selectIDS+"&topnum="+curTabIndex,"维护主题");
			}
			else
			{
				msgAlert("该主题已经通过审核，不能再修改操作");
			}
		}
	}
}

function updateSubject()
{
	var bean = BeanUtil.getCopy(subjectBean);
	$("#subject").autoBind(bean);
    bean.info = getV(contentId);
	
	if(!standard_checkInputInfo("jcxx_tab") || !standard_checkInputInfo("splj_tab"))
	{
		return;
	}

	if(bean.audit_status != 1 && (bean.publish_status > 0 || bean.status > 0 ))
	{
		msgWargin("该主题未通过审核，不能设置发布状态和访谈状态");
		return;
	}
	bean.update_user = LoginUserBean.user_id;
	if(SubjectRPC.updateSubject(bean))
	{
		msgAlert("访谈主题"+WCMLang.Add_success);
		getCurrentFrameObj(topnum).showTurnPage();
		getCurrentFrameObj(topnum).showList();
		tab_colseOnclick(curTabIndex);
	}
	else
	{
		msgWargin("访谈主题"+WCMLang.Add_fail);
	}
}


/**********************修改操作　结束*************************************/

//查看分类
function view_subject(main_type,id)
{	
	//fnModelWin('查看访谈主题','interview/subject/view_subject.jsp?tn='+main_type+'&id='+id,800,500)
	addTab(true,'/sys/interview/subject/view_subject.jsp?tn='+main_type+'&id='+id,'查看访谈主题');
}


//打开参与者页面
function fnActorManager(main_type)
{	
	selectBean = table.getSelecteBeans();
	addTab(true,'/sys/interview/subject/actorList.jsp?&tn='+main_type+'&sub_id='+selectBean.get(0).sub_id+'&ss='+selectBean.get(0).audit_status,'嘉宾资料管理');
}

function actorManager(main_type)
{	
	window.location.href = "actorList.jsp?&tn="+main_type+"&sub_id="+beanList.get(carent_cell_num).sub_id+"&ss="+beanList.get(carent_cell_num).audit_status;
}
//打开相关主题页面
function fnReleInfoManager(main_type)
{
	var ls = table.getSelecteBeans();
	if(ls == null || ls.size() == 0)
	{
		alertN("请选择要操作的主题");
		return;
	}
	else
	{
		if(ls.size() > 1)
		{
			alertN("只能选择一条主题进行管理操作");
			return;
		}
		addTab(true,"/sys/interview/subject/releInfoList.jsp?&tn="+main_type+"&sub_id="+ls.get(0).sub_id+"&ss="+ls.get(0).audit_status,"相关信息管理");
	}
}

//判断图片格式
function checkImgFile(files) 
{ 
	if(files.replace(/(^\s*)|(\s*$)/g,"") != "")
	{    		  
		if(files.indexOf(".") == -1) 
		{ 					
			return false; 
		}
		var strtype = files.match(/\.([^\.]+)(\?|$)/)[1];
		if (strtype.toLowerCase()=="jpg"||strtype.toLowerCase()=="gif"||strtype.toLowerCase()=="bmp"||strtype.toLowerCase()=="png") 
		{ 
			return true; 
		} 
		else
		{ 			
			alertWar("<span style='color:red'>上传的文档图片格式不对，只允许上传 jpg，jpeg，gif，png 等格式的文件，请重新上传！</span>");
			return false;
		}
	}
	return true;
}

//判断视频格式
function checkVideoFile(files) 
{
	var ext = "asf,avi,mpg,mpeg,mpe,mov,wmv,wav,mid,midi,mp3,mpa,mp2,ra,wma";
	if(files.replace(/(^\s*)|(\s*$)/g,"") != "")
	{    		  
		if(files.indexOf(".") == -1) 
		{ 					
			return false; 
		}
		var strtype = files.match(/\.([^\.]+)(\?|$)/)[1];
		if(ext.indexOf(strtype.toLowerCase()+",") > -1)				
		{ 
			return true; 
		} 
		else
		{ 					
			alertWar("<span style='color:red'>上传的视频文件格式不对，只允许上传 asf，avi，mpg，mpeg，mpe，mov，wmv，wav，mid，midi，mp3，mpa，mp2，ra，wma 等格式的文件，请重新上传！</span>");
			return false;
		}
	}
	return true;
}

function fnCancel()
{
	if(request.getParameter("tn") != "main")
		window.location.href = "subjectList.html";
	else
		window.location.href = "subjectManager.html";
}

function openSearchWin(main_type)
{
	addTab(true,"/sys/interview/subject/search_subject.jsp?tn="+main_type+"&ti="+curTabIndex,"访谈主题查询");
}

function searchHandl(str)
{
	con = str;
	showTurnPage();
	showList();
}