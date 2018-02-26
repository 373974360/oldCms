function getAllowReceiveSite()
{
	var site_list = CategoryRPC.getAllowReceiveSite(site_id);
	site_list = List.toJSList(site_list);
	$("#tsArea").addOptions(site_list,"site_id","site_name");
}
//根据目标站点得到它能推送的站点
function initCatTree(s_id){
	var cat_jdata = "";
	if(app_id == "cms")
	{
		if(s_id == site_id)
		{
			$("#leftMenuTree").empty();
			$('#leftMenuTree').tree({   
				 checkbox: true,   
				 onlyLeafCheck: true,   
				 url: '/servlet/Category?site_id=' + site_id + '&user_id='+LoginUserBean.user_id+'&pid=0',
				 onBeforeExpand:function(node,param){
					 $('#leftMenuTree').tree('options').url = '/servlet/Category?site_id=' + site_id + '&user_id='+LoginUserBean.user_id+'&pid=' + node.id;	// change the url
					 //param.myattr = 'test';    // or change request parameter
				 },               
				 onClick:function(node){
					 if(node.state == "open")
				     {
						 $(this).tree('collapse',node.target);
				     }
					 else
					 {
						 $(this).tree('expand',node.target);
					 }
				 }
			}); 
			
			/*
			cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(site_id));
			var zt_jdata = eval(CategoryRPC.getZTCategoryTreeJsonStr(site_id));//如果是本站,加入所有专题的目录
			for(var i=0;i<zt_jdata.length;i++)
			{
				cat_jdata[0].children[cat_jdata[0].children.length] = zt_jdata[i];
			}
			*/

		}else
		{
			cat_jdata = eval(CategoryRPC.getReceiveCategoryTreeBySiteID(s_id,site_id));
			setLeftMenuTreeJsonData(cat_jdata);	
			initTree();
		}
	}else
	{
		cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(s_id));
		setLeftMenuTreeJsonData(cat_jdata);	
		initTree();
	}
	
}

//得到节点关联站点的所有栏目
function getNodeRealSiteCategory(s_site_id)
{
	cat_jdata = eval(CategoryRPC.getCategoryTreeBySiteID(s_site_id));
	setLeftMenuTreeJsonData(cat_jdata);	
	initTree();
}
	
function related_ok(){
	var c_ids = getSelectIds();
	if(c_ids != "")
	{
		if(app_id == "zwgk")
		{
			doTSForGK(c_ids);	
		}
		else
			doTS(c_ids);			
	}else
		top.msgWargin("请选择要推送的节点");
	
}

function related_cancel(){
	top.CloseModalWindow();
}

function doTS(catIds){
	//var i_list = new List();
	var info_list = top.top.getCurrentFrameObj().table.getSelecteBeans();
	info_list = List.toJSList(info_list);
	/******** 判断要推送的信息是否含有链接或引用的信息，链接或引用是不能再次被推送的 ***********   改掉了，可以再次被推送
	for(var j=0;j<info_list.size();j++)
	{
		if(info_list.get(j).is_host != 0)
		{
			top.msgWargin("推送的信息中含有链接或引用的数据，该类型数据不能被再次推送，请重新选择");
			return;
		}
		i_list.add(info_list.get(j));
	}
	*******************/

	var idsTmp = $(":checked[name='infostatus']").val();
	var isPublish = $("#isPublish").is(':checked');
	if(jsonrpc.InfoBaseRPC.infoTo(info_list,s_site_id,app_id,catIds,idsTmp,isPublish))
	{
		top.msgAlert("推送成功");
		top.CloseModalWindow();
		top.top.getCurrentFrameObj().table.unChekcbox();
	}
	else
	{
		top.msgWargin("推送失败,请重新操作");
	}
}

function doTSForGK(catIds){
	var info_list = top.top.getCurrentFrameObj().table.getSelecteBeans();
	/*
	for(var j=0;j<info_list.size();j++)
	{
		if(info_list.get(j).is_host != 0)
		{
			top.msgWargin("推送的信息中含有链接或引用的数据，该类型数据不能被再次推送，请重新选择");
			return;
		}
	}*/
	

	var idsTmp = $(":checked[name='infostatus']").val();
	var cIds = catIds.split(",");
	for(var i=0; i<cIds.length; i++){
		if(cIds[i] != null && cIds[i] != ""){
			for(var j=0;j<info_list.size();j++)
			{
				var ib;
				var model_ename = ModelRPC.getModelEName(info_list.get(j).model_id);
				if(model_ename == "link")
				{//如果是链接内容模型，直接使用Bean，不需要再取
					ib = info_list.get(j);
				}
				else
					ib = ModelUtilRPC.select(info_list.get(j).info_id,site_id,model_ename);
								
				if(idsTmp == "2"){
					//如果选择引用类型，直接用链接内容模型
					ib.from_id = ib.info_id;
					ib.model_id = ModelRPC.getModelBeanByEName("link").model_id;
					model_ename == "link";
					//判断获取的信息是否是同一站点的				
					if(s_site_id != site_id)
					{
						//判断内容页地址是否以/（相对路径）开始，如果是，需要加上原站点ID的域名
						if(ib.content_url.indexOf("/") == 0)
						{
							var new_site_id = top.top.getCurrentFrameObj().getRealSiteIDByApp(app_id,site_id);
							ib.content_url = SiteRPC.getSiteDomain(new_site_id)+ib.content_url;
						}
					}
				}else
				{//这里付成空，需要重新生成
					ib.content_url = "";
				}

				if(idsTmp == "1")
				{//如果是引用的信息
					ib.from_id = ib.info_id;					
				}
				var id = InfoBaseRPC.getInfoId();
				ib.id = id;
				ib.info_id = id;
				ib.site_id = s_site_id;				
				ib.is_host = idsTmp;	
				var categoryBean = CategoryRPC.getCategoryBeanCatID(cIds[i],s_site_id);
				ib.app_id = categoryBean.app_id;
				ib.wf_id = categoryBean.wf_id;
				if(ib.wf_id == 0)
				{
					ib.info_status = 8;
					ib.step_id = 100;
				}
				else
				{
					ib.info_status = 2;
					ib.step_id = 0;
				}
				ib.cat_id = cIds[i];

				ModelUtilRPC.insert(ib,model_ename);
			}			
		}
	}
	top.msgAlert("推送成功");
	top.CloseModalWindow();
}

function getSelectIds(){
	var s = "";	
	$("#leftMenuTree .tree-checkbox1").each(function(i){
		var o = $(this).parent();		
		s += "," + o.attr("node-id");
	});
	if(s != null && s != "")
		s = s.substring(1);
	return s;
}

function changeSiteId(val){
	if(val != "")
	{
		s_site_id = val;
		initCatTree(val);
	}else
	{
		s_site_id = site_id;
		initCatTree(site_id);
	}
}

function initTree(){
	$("#leftMenuTree .tree-file").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');
	$("#leftMenuTree .tree-checkbox").click(function(){		
		if($(this).attr("class").indexOf("tree-checkbox0") > -1)
		{
			$(this).removeClass("tree-checkbox0");
			$(this).addClass("tree-checkbox1");
		}else
		{
			$(this).removeClass("tree-checkbox1");
			$(this).addClass("tree-checkbox0");			
		}
	});	
	$('#leftMenuTree').tree({   
		 onClick:function(node){      
			 if(node.state == "open")
		     {
				 $(this).tree('collapse',node.target);
		     }
			 else
			 {
				 $(this).tree('expand',node.target);
			 }
		 }   
	 });   
}