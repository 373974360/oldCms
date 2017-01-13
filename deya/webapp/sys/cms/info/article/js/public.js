var InfoBaseRPC = jsonrpc.InfoBaseRPC;
var ArticleRPC = jsonrpc.ArticleRPC;
var ModelUtilRPC = jsonrpc.ModelUtilRPC;
var SiteRPC = jsonrpc.SiteRPC;
var InfoBean = new Bean("com.deya.wcm.bean.cms.info.InfoBean",true);
var RelatedBean = new Bean("com.deya.wcm.bean.cms.info.RelatedInfoBean",true);
var AssistRPC = jsonrpc.AssistRPC;
var ModelRPC = jsonrpc.ModelRPC;
var CategoryRPC = jsonrpc.CategoryRPC;
var imgDomain = "";
var wf_id = 0;
var step_id = 0;
var val=new Validator();
var infoIdGoble = 0;
var defaultBean;
var no1 = 0;
var infoNextId = 0;
var cur_sort_tablename = "";//上移下移操作中操作的当前table的ID名
var infoIdGoble = 0;
var rela_site_id = "";//该节点所关联的站点

var fontSize=12;
var fontspacesize="";
var fontSpaceType="";
var fontBoldType="";
var fontObliqueType="";
var fontSizeType="";
var titleStyle="";
var titleColor = "";

/*后来添加*/
var selectIds = "";
var userOpts = "";

function setInfoGoble(n){
	infoIdGoble = n;
}
function getInfoGoble(n){
	return infoIdGoble;
}

//公用页面加载事件
function reloadPublicInfo()
{
	imgDomain = jsonrpc.MateInfoRPC.getImgDomain(site_id);
	$("#theUser").val(LoginUserBean.user_realname);
	showStringLength('title','wordnum');
	setSource();
	setAuthor();
	setPreTitle();
	iniSQbox();
	iniOpt();
    setPublishSource();
	
	getFirstChileCateID();//如果点击的是枝节点，默认找到第一个叶节点
	
	titleColorKey();
	showSelectDiv2("source","select",300);
	showSelectDiv2("author","select2",300);
	showSelectDiv2("editor","select3",300);
	showSelectDiv2("pre_title","select4",120);
	//showOtherCateTree();
	
	showSelectDiv2("cat_tree","cat_tree_div1",300);
	showSelectDiv2("zt_tree","cat_tree_div2",300);	
	
	//设置信息状态按钮
	
	if(mFlag == false)
	{	
		//添加时设置权限
		setInfoStatusButton();
		//根据栏目对象得到栏目中设置的评论状态来默认信息的评论
		setCommitStatus();
		$("#cat_id").val(cid);
		$("#site_id").val(site_id);		
		$("#input_user").val(LoginUserBean.user_id);
		var temp_site_id = site_id;
		if(app_id == "ggfw")
		{
			temp_site_id = jsonrpc.SiteRPC.getSiteIDByAppID(app_id)
		}
		$("#source").val(SiteRPC.getSiteBeanBySiteID(temp_site_id).site_name)
		$("#author").val(LoginUserBean.user_realname);
		$("#editor").val(LoginUserBean.user_realname);
	}
	setUserOperate();
	showCatId(cid);
	
	$("#title").blur(getTagsForTitle);
}

function titleColorKey()
{
	$("#title_color").keydown(function(e){
		e = e ? e : window.event; 
		var keyCode = e.which ? e.which : e.keyCode;
		if(keyCode == "8" || keyCode == 46)
		{
			$(this).val("");
			$(this).css("background","");
		}
	});
}

function setPublishSource(){
    if(defaultBean.publish_source != "")
    {
		if(defaultBean.publish_source.indexOf('pc') < 0 ){
            $("input[name='publish_source']").each(function(){
                if($(this).val() == "pc"){
                	$(this).removeAttr("checked");
				}
            });
		}
        if(defaultBean.publish_source.indexOf('wx') < 0 ){
            $("input[name='publish_source']").each(function(){
                if($(this).val() == "wx"){
                    $(this).removeAttr("checked");
                }
            });
        }
        if(defaultBean.publish_source.indexOf('app') < 0 ){
            $("input[name='publish_source']").each(function(){
                if($(this).val() == "app"){
                    $(this).removeAttr("checked");
                }
            });
        }
    }
}

function getTagsForTitle()
{
	var tags = InfoBaseRPC.getTagsForTitle($("#title").val());
	if(tags != "" && tags != null)
	{
		$("#tags").val(tags);
	}
}

//根据栏目对象得到栏目中设置的评论状态来默认信息的评论
function setCommitStatus()
{
	var categoryBean = CategoryRPC.getCategoryBeanCatID(cid,site_id);
	if(categoryBean.is_allow_comment == 1)
	{
		$("#is_allow_comment").attr("checked",true);
	}
}

//得到叶节点
function getFirstChileCateID()
{
	//判断选中的节点是否是叶节点	
	if(top.isSubNode(cid))
	{
		//如果是，得到选中节点下的第一个叶子节点ID
		cid = top.getFirstChildNodeID();
		
		$("#cat_id").val(cid);
		//$("#showCatId").text(top.getFirstChildNodeValue());
	}		
}

function showStringLength(inId, showId){
	$("#"+showId).text($("#"+inId).val().length);
}

function setSource(){
	beanList = AssistRPC.getAllSourceList();//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	var str = "";
	for(var i=0; i<beanList.size(); i++){
		str += '<li onclick="showValue(\'source\',this)" style="cursor:default;">'+beanList.get(i).source_name+'</li>';
	}
	$("#selectList").html(str);
}

function setPreTitle(){
	beanList = jsonrpc.DataDictRPC.getDataDictList("pre_title");//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	var str = "";
	for(var i=0; i<beanList.size(); i++){
		str += '<li onclick="showValue(\'pre_title\',this)" style="cursor:default;">'+beanList.get(i).dict_name+'</li>'
	}
	$("#selectList_pre").html(str);
}

function showValue(id,o){
	$("#"+id).val($(o).html());
}

function setAuthor(){
	beanList = AssistRPC.getAllAuthorList();//第一个参数为站点ID，暂时默认为空	
	beanList = List.toJSList(beanList);//把list转成JS的List对象	
	var str = str2 = "";
	for(var i=0; i<beanList.size(); i++){
		str += '<li onclick="showValue(\'author\',this)" style="cursor:default;">'+beanList.get(i).author_name+'</li>';
		str2 += '<li onclick="showValue(\'editor\',this)" style="cursor:default;">'+beanList.get(i).author_name+'</li>';
	}
	$("#selectList2").html(str);
	$("#selectList3").html(str2);
}

function showSelectDiv2(input_name,div_name,height,tree_div_name)
{
	$("input[id='"+input_name+"']").addClass("select_input_default");
	$("input[id='"+input_name+"']").hover(function(){			
		$(this).removeClass("select_input_default");
		$(this).addClass("select_input_selected");			
	},function(){
		$(this).removeClass("select_input_selected");
		$(this).addClass("select_input_default");			
	});

	$("input[id='"+input_name+"']").click(function(event){
		$("div.select_div").hide();//先关闭其它的div

		var thePos = $(this).position();
		$("#"+div_name).show();
		$("#"+div_name).css("background","#FFFFFF");
		$("#"+div_name).css("left",thePos.left);
		$("#"+div_name).css("top",thePos.top+19);
		$("#"+div_name).css("width",$(this).width()+3);
		$("#"+div_name).css("height",height+"px");
		if(tree_div_name != null)
			$("#"+tree_div_name).css("height",(height-12)+"px");
		else
			$("#leftMenu").css("height",(height-12)+"px");
		
		event.stopPropagation();
	});
	
	
	$("html").click(function(event){
		try{
			if($($(event.target)[0]).attr("class").indexOf("tree-") == -1)
			{
				$("div.select_div").hide();		
				setAllIds();
			}else
			{
				setAllIds();
			}
		}catch(e){
			$("div.select_div").hide();	
			setAllIds();
		}
	});
}

function setAllIds(){
	var catIds = showSelectIds1("s");
	var ztIds = showSelectIds2("s");
	$("#cat_tree").val(catIds);
	$("#zt_tree").val(ztIds);
}

function showSelectIds1(t){
	/*
	var s = "";
	var names = "";
	$("#leftMenuTree1 li").each(function(i){
		if(i>0){
			var o = $(this).find(".tree-checkbox1").parent();
			var id = o.attr("node-id");
			if(id != null && id != undefined && id != "undefined"){
				if(s.indexOf(id) == -1){
					s += "," + id;
					names += "," + $(o).find(".tree-title").html();
				}
			}
		}
	});
	if(t != null){
		if(names.length > 1){
			return names.substring(1);
		}
		return names;
	}
	return s;
	*/
	var nodes = $("#leftMenuTree1").tree("getChecked");
	var ids = "";
	var names = "";
	if (nodes.length > 0) {
		for (var i = 0; i < nodes.length; i++) 
		{
			if (ids != "") 
			{ 
				ids += ","; 
			}
			if (names != "") 
			{ 
				names += ","; 
			}
			ids += nodes[i].id;
			names += nodes[i].text;
		} 
	}
	selectIds = ids;
	if(t != null){
		return names;
	}
	return ids;
}

function showSelectIds2(t){
	var s = "";
	var names = "";
	$("#leftMenuTree2 li").each(function(i){
		if(i>0){
			var o = $(this).find(".tree-checkbox1").parent();
			var id = o.attr("node-id");
			if(id != null && id != undefined && id != "undefined"){
				if(s.indexOf(id) == -1){
					s += "," + id;
					names += "," + $(o).find(".tree-title").html();
				}
			}
		}
	});
	if(t != null){
		if(names.length > 1){
			return names.substring(1);
		}
		return names;
	}
	return s;
}

var zt_jdata;
var cat_jdata;
//点击显示同时发布到里面栏目树
function showCategoryTree()
{
	
	$('#leftMenuTree1').tree({   
		 checkbox: true,   
		 onlyLeafCheck: true,   
		 url: '/sys/cms/category/category.jsp?site_id=' + site_id + '&user_id=' + LoginUserBean.user_id + '&pid=0',
		 onBeforeExpand:function(node,param){
			 $('#leftMenuTree1').tree('options').url = '/sys/cms/category/category.jsp?site_id=' + site_id + '&user_id=' + LoginUserBean.user_id + '&pid=' + node.id;	// change the url
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
		 },
		 onLoadSuccess:function(node,data){
			if(selectIds != "")
			{
				var temp = selectIds.split(",");
				for(var i = 0; i < temp.length; i++)
				{
					var n = $("#leftMenuTree1").tree('find',temp[i]);
			        if(n){
			            $("#leftMenuTree1").tree('check',n.target);
			        }
				}
				
			}
		 }
	}); 
	
	/*
	if(cat_jdata == null)
	{
		cat_jdata = eval(jsonrpc.CategoryRPC.getInfoCategoryTreeByUserID(site_id,LoginUserBean.user_id));
		setMenuTreeJsonData("leftMenuTree1",cat_jdata);
		initTree("leftMenuTree1");
	}
	*/
}
//点击显示同时发布到里面的专题栏目树
function showZTCategoryTree()
{
	if(zt_jdata == null)
	{
		zt_jdata = eval(jsonrpc.CategoryRPC.getZTCategoryTreeJsonStr(site_id,LoginUserBean.user_id));	
		setMenuTreeJsonData("leftMenuTree2",zt_jdata);
		initTree("leftMenuTree2");
	}
}

function showOtherCateTree(){
	var zt_jdata = eval(jsonrpc.CategoryRPC.getZTCategoryTreeJsonStr(site_id,LoginUserBean.user_id));	
	var cat_jdata = eval(jsonrpc.CategoryRPC.getInfoCategoryTreeByUserID(site_id,LoginUserBean.user_id));
	setMenuTreeJsonData("leftMenuTree1",cat_jdata);
	setMenuTreeJsonData("leftMenuTree2",zt_jdata);

	
}

//左边栏目树对像赋值json格式数据
function setMenuTreeJsonData(id,jsonData){
	isChangeTreeStatus = true;
	if(jsonData != undefined && jsonData.length>0)
	{
		$('#'+id).empty();
		$('#'+id).tree('append',{
			parent: (null),
			data:jsonData
		});	
	}
}

function initTree(div_name){
	$("#"+div_name+" .tree-file").after('<SPAN class="tree-checkbox tree-checkbox0"></SPAN>');
	$("#"+div_name+" .tree-checkbox").click(function(){		
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
}

function showCatId(id){
	$("#showCatId").text(CategoryRPC.getCategoryCName(id,site_id));
}

function showSubTitle(){
	var st = $("#stt").is(':checked');
	if(st){
		$("#subTitleTr").css("display","");
	}else{
		$("#subTitleTr").css("display","none");
	}
}

function showTopTitle(){
	var st = $("#sttop").is(':checked');
	if(st){
		$("#topTitleTr").css("display","");
	}else{
		$("#topTitleTr").css("display","none");
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
	});
}

function changeTitleColor(){
	$("#title").css("color",$("#title_color").val());
}

function setLinkInfoIds(info_ids,type){	
	cur_sort_tablename = "relateInfos";
	var rinfoIds = info_ids.split(",");
	for(var i=0; i<rinfoIds.length; i++){
		var relateBean = InfoBaseRPC.getInfoById(rinfoIds[i],site_id);		
		if(relateBean != null){
			var tmp = $("#relateInfos").find("span[id='"+relateBean.info_id+"']").html();
			if(tmp == null || tmp == "null" || tmp == ""){	
				var relateBeanT = BeanUtil.getCopy(RelatedBean);
				relateBeanT.related_info_id = InfoBaseRPC.getReleInfoID();
				relateBeanT.title = relateBean.title;
				relateBeanT.thumb_url = relateBean.thumb_url;
				relateBeanT.content_url = relateBean.content_url;
				if(type == "a"){
					if(infoIdGoble == 0){
						infoIdGoble = InfoBaseRPC.getInfoId();
					}
					
					relateBeanT.info_id = infoIdGoble;
				}else{
					relateBeanT.info_id = infoid;
				}
				
				relateBeanT.sort_id = i;
				if(InfoBaseRPC.addRelatedInfo(relateBeanT))
				{
					$("#relateInfos").append("<tr><td style=\"width:280px;\"><span id=\""+relateBean.related_info_id+"\" style='color:#000;'>"+relateBean.title+"</span></td><td style=\"width:150px;\"><ul class=\"optUL\"><li class=\"opt_up ico_up\" title=\"上移\"></li><li class=\"opt_down ico_down\" title=\"下移\"></li><li class=\"opt_delete ico_delete\" title=\"删除\"></li></ul></td></tr>");
				}
			}
		}
	}
	
	resetNum();
}
/////////////////////
function updateRelatedInfoAsSort(infoId, siteId){
	$("#relateInfos td").each(function(i){
		var reid = $(this).find("span").attr("id");
		if(reid != null && reid != undefined && reid != "undefined"){		
			var relateBean = InfoBaseRPC.getRelatedInfoBean(infoId, reid);
			if(relateBean != null)
			{
				relateBean.sort_id = i;
				InfoBaseRPC.updateRelatedInfo(relateBean);
			}
		}
	});
}

//将选择的标签写入到页面中
function setFocusInfoIds(v){
	$("#focusWare").empty();
	focusInfo = v;
	if(focusInfo != ""){
		var ids = focusInfo.split(",");		
		for(var i=0; i<ids.length; i++){
			if(ids[i] != ""){
				var wareBean = jsonrpc.WareRPC.getWareByID(ids[i]);
				$("#focusWare").append("<tr><td style=\"width:600px;\"><span id=\""+wareBean.ware_id+"\" style='color:#000;'>"+wareBean.ware_name+"</span></td></tr>");
			}
		}
	}
}

//得到已选中的手动标签
function getSelctWareInfo()
{
	var ids = "";	
	$("#focusWare span").each(function(i){
		if(i > 0)
			ids += ",";
		ids += $(this).attr("id");
	});
	return ids;
}

function addFocusWares(id){
	if(focusInfo == null || focusInfo == "" || focusInfo.length < 1){
		return;
	}else{
		
		var temp_site_id = site_id;//因为公开节点是关联的站点，所以这里需要判断一下
		if(rela_site_id != "")
			temp_site_id = rela_site_id;

		jsonrpc.WareRPC.deleteWareInfoRef(id,"",app_id,temp_site_id);
		
		var wares = focusInfo.split(",");
		for(var i=0; i<wares.length; i++){
			jsonrpc.WareRPC.insertWareInfoRef(id,wares[i],LoginUserBean.user_id,app_id,temp_site_id);
		}
	}
}

function showRelatedInfos(infoid){
	cur_sort_tablename = "relateInfos";
	$("#relateInfos").empty();
	var rm = new Map();
	rm.put("info_id", infoid);
	rm.put("sort_name", "sort_id");
	rm.put("sort_type", "asc");
	
	var relatedInfoList = InfoBaseRPC.getRelatedInfoList(rm);
	relatedInfoList = List.toJSList(relatedInfoList);
	
	for(var i=0; i<relatedInfoList.size(); i++){		
		$("#relateInfos").append("<tr><td style=\"width:280px;\"><span id=\""+relatedInfoList.get(i).related_info_id+"\" style='color:#000;'>"+relatedInfoList.get(i).title+"</span></td><td style=\"width:150px;\"><ul class=\"optUL\"><li class=\"opt_up ico_up\" title=\"上移\"></li><li class=\"opt_down ico_down\" title=\"下移\"></li><li class=\"opt_delete ico_delete\" title=\"删除\"></li></ul></td></tr>");
	}
	
	resetNum();
}

//删除
function deleteTr(obj)
{
	if(cur_sort_tablename == "relateInfos")
	{		
		var m = new Map();
		m.put("related_info_ids",$(obj).find("span").attr("id"));
		InfoBaseRPC.deleteRelatedInfo(m);
	}
	$(obj).remove();
	resetNum();
}

//上移
function moveUpTr(obj)
{
	if($(obj).index==0) return;
	$($(obj).prev()).before($(obj));
	resetNum();
}

//下移
function moveDownTr(obj)
{	
	$($(obj).next()).after($(obj));
	resetNum();
}

//上、下移、删除事件绑定

function iniOpt()
{	
	$(".opt_up").live("click",function(){
		//alert("上移"+$(this).parent().parent().parent().index());
		var tmpObj = $(this).parent().parent().parent();
		cur_sort_tablename = tmpObj.parent().parent().attr("id");
		moveUpTr(tmpObj);
	});
	
	$(".opt_down").live("click",function(){
		//alert("下移"+$(this).parent().parent().parent().index());
		var tmpObj = $(this).parent().parent().parent();
		cur_sort_tablename = tmpObj.parent().parent().attr("id");
		moveDownTr(tmpObj);
	});
	
	$(".opt_delete").live("click",function(){
		//alert("删除"+$(this).parent().parent().parent().index());
		var tmpObj = $(this).parent().parent().parent();
		cur_sort_tablename = tmpObj.parent().parent().attr("id");
		deleteTr(tmpObj);
	});
	
}

//重排
function resetNum(){
	$("#"+cur_sort_tablename+" tr").find(".opt_up").addClass("ico_up");
	$("#"+cur_sort_tablename+" tr").find(".opt_down").addClass("ico_down");
	
	//首行、未行去除移动图标
	$("#"+cur_sort_tablename+" tr").first().find(".opt_up").removeClass("ico_up");
	$("#"+cur_sort_tablename+" tr").last().find(".opt_down").removeClass("ico_down");
}

function addRelatedHand(){
	//alert("在模态窗口里边再次打开新的模态窗口，会把第一个模态窗口冲掉滴");
	top.OpenModalWindow("手动添加","/sys/cms/info/article/addRelatedInfo.jsp?info_id="+infoid+"&site_id="+site_id,400,200);	
}


//打开相关信息选择窗口
function openLinkInfoDataPage(Info_id,t_site_id)
{	
	var temp_site = "";
	if(t_site_id != "" && t_site_id != null)
	{
		temp_site = t_site_id;
	}
	else
		temp_site = site_id;
	top.OpenModalWindow("选择相关信息","/sys/cms/info/article/chooseInfoPage.jsp?info_id="+Info_id+"&site_id="+temp_site,825,500);	
}

//打开相关信息选择窗口
function openFocusPage(Info_id,t_site_id)
{	
	var tmp = "m";
	if(mFlag){
		tmp = "m";
	}else{
		tmp = "a";
	}

	var temp_site = "";
	if(app_id == "zwgk")
	{
		temp_site = SiteRPC.getSiteIDByAppID(app_id);//政务公开的推荐标签使用政务公开应用所关联的站点　
	}
	else
		temp_site = site_id;
	top.OpenModalWindow("选择手动标签","/sys/cms/info/article/focusInfo.jsp?info_id="+Info_id+"&site_id="+temp_site+"&t="+tmp,660,500);	
}

function showFocusWares(infoId){
	var temp_site_id = site_id;//因为公开节点是关联的站点，所以这里需要判断一下
	if(rela_site_id != "")
		temp_site_id = rela_site_id;

	var wareList = jsonrpc.WareRPC.getWareListByRefInfo(infoId,app_id,temp_site_id);
	wareList = List.toJSList(wareList);
	for(var i=0; i<wareList.size(); i++){
		$("#focusWare").append("<tr><td style=\"width:600px;\"><span id=\""+wareList.get(i).ware_id+"\" style='color:#000;'>"+wareList.get(i).ware_name+"</span></td></tr>");
		//$("#focusWare").append("<tr><td>"+wareList.get(i).ware_name+"</td></tr>");
	}
}

//插入其它的信息
function insertOtherInfos(bean,model_ename,info_id)
{	
	var catIds = showSelectIds1();
	//var ztIds = showSelectIds2();
	if(catIds != ""){
		var ids = (catIds).split(",");
		//InfoBaseRPC.insertInfoToOtherCat(bean,ids);
		for(var i=0;i<ids.length;i++)
		{//都以引用方式过去
			bean.from_id = info_id;
			bean.id = InfoBaseRPC.getInfoId();
			bean.info_id = bean.id;
			bean.is_host = 1;
			bean.wf_id = CategoryRPC.getWFIDByCatID(ids[i],site_id);
			if(bean.wf_id == 0)
			{
				if(userOpts.indexOf(",302,"))
				{
					bean.info_status = 8;
				}
				else
				{
					bean.info_status = 4;
				}
				bean.step_id = 100;
			}
			else
			{
				var temp_step_id = getMaxStepIDByUserID(bean.wf_id,app_id,site_id);	
				//首先判断该栏目是否需要审核,且用户有审核权限
				if(userOpts.indexOf(",303,") > -1)
				{
					//得到管理员审核流程中最大的步骤ID, 只有最终审核的人才有终审通过权限			
					if(temp_step_id == 100)
					{
						bean.info_status = 8;
						bean.step_id = 100;
					}
					else
					{
						bean.info_status = 2;
						bean.step_id = 0;
					}
				}
				else
				{
					bean.info_status = 2;
					bean.step_id = 0;
				}
			}
			bean.cat_id = ids[i];
			ModelUtilRPC.insert(bean,model_ename);
		}
	}
	
	addFocusWares(info_id);
	updateRelatedInfoAsSort(info_id,bean.site_id);
}

//修改其它信息
function updateOtherInfos(bean)
{
	addFocusWares(bean);
	updateRelatedInfoAsSort(bean.info_id,bean.site_id);
}

//修改信息时页面加载方法
function publicReloadUpdateInfos()
{
	$("#t1").hide();
	$("#t2").hide();
	$("#t3").hide();
	$("#t4").hide();
	infoIdGoble = infoid;
	showInfoStatusText();	
	showRelatedInfos(infoid);
	showStringLength('title','wordnum');
	showStringLength('subtitle','wordnum2');
	
	if(defaultBean.top_title != "")
	{
		$("#sttop").attr("checked",true);
		showTopTitle();
	}
	
	if(defaultBean.subtitle != "")
	{
		$("#stt").attr("checked",true);
		showSubTitle();
	}

	$("#modify_user").val(LoginUserBean.user_id);			
	$("#app_id").val(app_id);
	showFocusWares(infoid);
	showCatId(defaultBean.cat_id);

}

//添加操作时，判断上线时间和下线时间是否正确
function Add_dtTimeIsCorrect(bean)
{
	if(bean.up_dtime != null && bean.up_dtime != "" && bean.up_dtime != "0"){
		bean.is_auto_up = 1;
	}
	if(bean.down_dtime != null && bean.down_dtime != "" && bean.down_dtime != "0"){
		if(bean.is_auto_up == 1)
		{
			if(judgeAllDateTime(bean.down_dtime,bean.up_dtime))
			{
				jQuery.simpleTips("down_dtime","下线时间不能早于上线时间",2000);
				return false;
			}
		}
		bean.is_auto_down = 1;
	}
	return true;
}

//修改操作时，判断上线时间和下线时间是否正确
function update_dtTimeIsCorrect(bean)
{
	if(bean.up_dtime != null && bean.up_dtime != "" && bean.up_dtime != "0"){
		bean.is_auto_up = 1;
	}
	else
	{
		bean.is_auto_up = 0;
	}
	if(bean.down_dtime != null && bean.down_dtime != "" && bean.down_dtime != "0"){
		if(bean.is_auto_up == 1)
		{
			if(judgeAllDateTime(bean.down_dtime,bean.up_dtime))
			{
				jQuery.simpleTips("down_dtime","下线时间不能早于上线时间",2000);
				return false;
			}
		}
		bean.is_auto_down = 1;
	}
	else
	{
		bean.is_auto_down = 0;
	}
	return true;
}

function gotoListPage(bean)
{
	//window.location.href = "/sys/cms/info/article/articleDataList.jsp?cat_id="+bean.cat_id+"&app="+bean.app_id+"&site_id="+bean.site_id+"&snum="+snum;
	top.getCurrentFrameObj(topnum).reloadInfoDataList();
	top.tab_colseOnclick(top.curTabIndex);
}

//公用保存处理事件
function publicSaveInfoEvent(bean,model_ename,save_type)
{
	var info_id = bean.info_id;
	var bool = false;
	var font_Size = fontSize+"px";  //标题大小
	titleColor = $("#title_color").val(); //标题颜色
	var fontSpace = fontspacesize+"px";
	/*
	if(titleStyle != "") //清空title样式
	{
		bean.title = bean.title.replace(/\"/g,"＂");
	}else
	{
		bean.title = bean.title.replace(/\"/g,"＂");
		
		//斜体
		if(titleColor == "" && fontObliqueType == "isOblique" && fontBoldType != "isBold" && fontSpaceType != "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='font-style:oblique;'>"+bean.title+"</span>";
		}

		//粗体
		if(titleColor == "" && fontObliqueType != "isOblique" && fontBoldType == "isBold" && fontSpaceType != "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='font-weight:bold;'>"+bean.title+"</span>";
		}

		//间距
		if(titleColor == "" && fontObliqueType != "isOblique" && fontBoldType != "isBold" && fontSpaceType == "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='letter-spacing:"+fontSpace+"'>"+bean.title+"</span>";
		}

		//大小
		if(titleColor == "" && fontObliqueType != "isOblique" && fontBoldType != "isBold" && fontSpaceType != "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//颜色
		if(titleColor != "" && fontObliqueType != "isOblique" && fontBoldType != "isBold" && fontSpaceType != "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='color:"+titleColor+"'>"+bean.title+"</span>";
		}
		
		//斜体 粗体
		if(titleColor == "" && fontObliqueType == "isOblique" && fontBoldType == "isBold" && fontSpaceType != "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='font-style:oblique;font-weight:bold;'>"+bean.title+"</span>";
		}
		
		//斜体 大小
		if(titleColor == "" && fontObliqueType == "isOblique" && fontBoldType != "isBold" && fontSpaceType != "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='font-style:oblique;font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//斜体  间距
		if(titleColor == "" && fontObliqueType == "isOblique" && fontBoldType != "isBold" && fontSpaceType == "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='font-style:oblique;letter-spacing:"+fontSpace+";'>"+bean.title+"</span>";
		}
		
		//斜体 颜色
		if(titleColor != "" && fontObliqueType == "isOblique" && fontBoldType != "isBold" && fontSpaceType != "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='color:"+titleColor+";font-style:oblique;'>"+bean.title+"</span>";
		}
		
		//粗体 大小
		if(titleColor == "" && fontObliqueType != "isOblique" && fontBoldType == "isBold" && fontSpaceType != "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='font-weight:bold;font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//粗体 间距
		if(titleColor == "" && fontObliqueType != "isOblique" && fontBoldType == "isBold" && fontSpaceType == "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='font-weight:bold;letter-spacing:"+fontSpace+";'>"+bean.title+"</span>";
		}
		
		//粗体 颜色
		if(titleColor != "" && fontObliqueType != "isOblique" && fontBoldType == "isBold" && fontSpaceType != "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='color:"+titleColor+";font-weight:bold;'>"+bean.title+"</span>";
		}
		//大小 间距 
		if(titleColor == "" && fontObliqueType != "isOblique" && fontBoldType != "isBold" && fontSpaceType == "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='letter-spacing:"+fontSpace+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//大小 颜色 
		if(titleColor != "" && fontObliqueType != "isOblique" && fontBoldType != "isBold" && fontSpaceType != "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='color:"+titleColor+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//间距 颜色
		if(titleColor != "" && fontObliqueType != "isOblique" && fontBoldType != "isBold" && fontSpaceType == "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='color:"+titleColor+";letter-spacing:"+fontSpace+"'>"+bean.title+"</span>";
		}
		
		//斜体 粗体 大小
		if(titleColor == "" && fontObliqueType == "isOblique" && fontBoldType == "isBold" && fontSpaceType != "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='color:"+titleColor+";font-style:oblique;font-weight:bold;letter-spacing:"+fontSpace+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//斜体 粗体 间距
		if(titleColor == "" && fontObliqueType == "isOblique" && fontBoldType == "isBold" && fontSpaceType == "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='font-style:oblique;font-weight:bold;letter-spacing:"+fontSpace+";'>"+bean.title+"</span>";
		}
		
		//斜体 粗体 颜色 
		if(titleColor != "" && fontObliqueType == "isOblique" && fontBoldType == "isBold" && fontSpaceType != "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='color:"+titleColor+";font-style:oblique;font-weight:bold;'>"+bean.title+"</span>";
		}
		
		//斜体 大小 间距
		if(titleColor == "" && fontObliqueType == "isOblique" && fontBoldType == "isBold" && fontSpaceType == "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='font-style:oblique;letter-spacing:"+fontSpace+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//斜体  大小 颜色 
		if(titleColor != "" && fontObliqueType == "isOblique" && fontBoldType != "isBold" && fontSpaceType != "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='color:"+titleColor+";font-style:oblique;font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//斜体 间距 颜色 
		if(titleColor != "" && fontObliqueType == "isOblique" && fontBoldType != "isBold" && fontSpaceType == "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='color:"+titleColor+";font-style:oblique;letter-spacing:"+fontSpace+";'>"+bean.title+"</span>";
		}
		
		//粗体 大小 间距 
		if(titleColor == "" && fontObliqueType != "isOblique" && fontBoldType == "isBold" && fontSpaceType == "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='font-weight:bold;letter-spacing:"+fontSpace+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//粗体 大小 颜色 
		if(titleColor != "" && fontObliqueType != "isOblique" && fontBoldType == "isBold" && fontSpaceType != "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='color:"+titleColor+";font-weight:bold;font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//粗体 间距 颜色 
		if(titleColor != "" && fontObliqueType != "isOblique" && fontBoldType == "isBold" && fontSpaceType == "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='color:"+titleColor+";font-weight:bold;letter-spacing:"+fontSpace+";'>"+bean.title+"</span>";
		}
		
		//大小 间距   颜色 
		if(titleColor != "" && fontObliqueType != "isOblique" && fontBoldType != "isBold" && fontSpaceType == "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='color:"+titleColor+";letter-spacing:"+fontSpace+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//斜体 粗体  大小 间距
		if(titleColor == "" && fontObliqueType == "isOblique" && fontBoldType == "isBold" && fontSpaceType == "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='font-style:oblique;font-weight:bold;letter-spacing:"+fontSpace+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//斜体 粗体 大小 颜色 
		if(titleColor != "" && fontObliqueType == "isOblique" && fontBoldType == "isBold" && fontSpaceType != "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='color:"+titleColor+";font-style:oblique;font-weight:bold;font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//斜体 粗体 间距 颜色 
		if(titleColor != "" && fontObliqueType == "isOblique" && fontBoldType == "isBold" && fontSpaceType == "isSpace" && fontSizeType == "normal")
		{
			bean.title = "<span style='color:"+titleColor+";font-style:oblique;font-weight:bold;letter-spacing:"+fontSpace+";'>"+bean.title+"</span>";
		}
		//斜体 大小 间距 颜色
		if(titleColor != "" && fontObliqueType == "isOblique" && fontBoldType != "isBold" && fontSpaceType == "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='color:"+titleColor+";font-style:oblique;letter-spacing:"+fontSpace+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//粗体 大小 间距 颜色
		if(titleColor != "" && fontObliqueType != "isOblique" && fontBoldType == "isBold" && fontSpaceType == "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='color:"+titleColor+";font-weight:bold;letter-spacing:"+fontSpace+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
		
		//斜体 粗体 大小 间距  颜色 
		if(titleColor != "" && fontObliqueType == "isOblique" && fontBoldType == "isBold" && fontSpaceType == "isSpace" && fontSizeType == "change")
		{
			bean.title = "<span style='color:"+titleColor+";font-style:oblique;font-weight:bold;letter-spacing:"+fontSpace+";font-size:"+font_Size+"'>"+bean.title+"</span>";
		}
	}
	*/
//	bean.title = bean.title.replace(/\"/g,"＂");
	bean.title = $("#title").val();
	if(app_id == "zwgk")
	{
		/*
		if(bean.author == "")
		{
			bean.author = LoginUserBean.user_realname;
			bean.editor = LoginUserBean.user_realname;
			bean.source = GKNodeRPC.getNodeNameByNodeID(site_id);
		}*/
		bean.tags = bean.topic_key;
	}
	if(save_type == "update")
	{
		if(update_dtTimeIsCorrect(bean) == false)
		{
			return;
		}

        if(bean.info_status == "8")
        {
            //得到该栏目所使用的流程ID
            var temp_wf_id = CategoryRPC.getWFIDByCatID(bean.cat_id,site_id);

            if(temp_wf_id != 0) {
                var temp_step_id = getMaxStepIDByUserID(temp_wf_id, bean.app_id, site_id);
                if (temp_step_id != 100) {//如果登录人是终审人，不要待审按钮 不然，后台不好更改状态逻辑，（如步骤ID为100，却又是待审状态）
                    bean.info_status = "2";
		    bean.step_id = temp_step_id;
                }
            }
        }

		bool = ModelUtilRPC.update(bean,model_ename);
		if(bool == true)
		{//修改引用此信息的信息
			if(model_ename != "link")
				updateQuoteInfo(bean,model_ename);
		}
	}
	else
	{
		if(Add_dtTimeIsCorrect(bean) == false)
		{
			return;
		}
		bool = ModelUtilRPC.insert(bean,model_ename);
	}

	if(bool)
	{	//草稿状态下不发布到其它栏目
		if(bean.info_status != 0)
		{
			insertOtherInfos(bean,model_ename,info_id);
		}
		top.msgAlert("信息"+WCMLang.Add_success);			
		gotoListPage(bean);
	}
	else
	{
		top.msgWargin("信息"+WCMLang.Add_fail);
	}
}

//修改引用此信息的信息
function updateQuoteInfo(bean,model_ename)
{
	var quote_list = InfoBaseRPC.getQuoteInfoList(bean.info_id);	
	quote_list = List.toJSList(quote_list);
	if(quote_list != null && quote_list.size() > 0)
	{
		for(var i=0;i<quote_list.size();i++)
		{
			var new_info_bean = ModelUtilRPC.select(quote_list.get(i).info_id,quote_list.get(i).site_id,model_ename);
			
			//将引用信息中不能修改的值放入到原Bean中，组成新Bean，并保存
			bean.id = new_info_bean.id;
			bean.info_id = new_info_bean.info_id;
			bean.cat_id = new_info_bean.cat_id;
			bean.from_id = new_info_bean.from_id;
			bean.content_url = new_info_bean.content_url;
			bean.wf_id = new_info_bean.wf_id;
			bean.step_id = new_info_bean.step_id;
			bean.info_status = new_info_bean.info_status;
			bean.final_status = new_info_bean.final_status;			
			bean.released_dtime = new_info_bean.released_dtime;
			bean.is_auto_up = new_info_bean.is_auto_up;
			bean.up_dtime = new_info_bean.up_dtime;
			bean.is_auto_down = new_info_bean.is_auto_down;
			bean.down_dtime = new_info_bean.down_dtime;
			bean.site_id = new_info_bean.site_id;
			ModelUtilRPC.update(bean,model_ename);
		}
	}
}

/************权限设置　开始********************/
var auditHtml = "";
function setInfoStatusButton()
{
	//得到该栏目所使用的流程ID
	wf_id = CategoryRPC.getWFIDByCatID(cid,site_id);

	if(wf_id == 0)
	{
		//没有审核流程
		$("#li_ds").remove();//删除待审
		$("#opt_303 :radio").attr("checked",true);
		$("#opt_303").show();
	}else
	{
        auditHtml = "";
        $("#wf_id").val(wf_id);
        step_id = getMaxStepIDByUserID(wf_id,app_id,site_id);
        var workFlowBean = jsonrpc.WorkFlowRPC.getWorkFlowBean(wf_id);
        var workStepList = workFlowBean.workFlowStep_list;
        workStepList = List.toJSList(workStepList);
        for(var i=0; i<workStepList.size(); i++){
            if(workStepList.get(i).step_id > step_id)
            {
                if(workStepList.get(i).required == 1)
                {
                    $("#audit_tr").removeClass("hidden");
                    var html = '<input id="auditChecked" name="auditStep" type="radio" checked="checked" onclick="setStepId(' + (workStepList.get(i).step_id - 1) + ')"/><label for="e">' + workStepList.get(i).step_name + '</label>&nbsp;&nbsp;';
                    auditHtml = auditHtml + html;
                    $("#audit_list").append(html);
                    $("#auditChecked").click();
                    break;
                }
                else
                {
                    $("#audit_tr").removeClass("hidden");
                    var html = '<input name="auditStep" type="radio" onclick="setStepId(' + (workStepList.get(i).step_id - 1) + ')"/><label for="e">' + workStepList.get(i).step_name + '</label>&nbsp;&nbsp;';
                    auditHtml = auditHtml + html;
                    $("#audit_list").append(html);
                }
            }
        }
        /*
		step_id = getMaxStepIDByUserID(wf_id,app_id,site_id);
         $("#wf_id").val(wf_id);
		$("#step_id").val(step_id);
		if(step_id == 100)
		{//如果登录人是终审人，不要待审按钮 不然，后台不好更改状态逻辑，（如步骤ID为100，却又是待审状态）
			$("#li_ds").remove();//删除待审
			$("#opt_303 :radio").attr("checked",true);//将终审通过选中
		}
		*/
	}
}


//设置是否显示选择审核人一栏
function isShowAudit(isShow)
{
    if(isShow)
    {
        $("#audit_tr").removeClass("hidden");
    }
    else
    {
        $("#audit_tr").addClass("hidden");
    }
}


function setStepId(stepId)
{
    $("#step_id").val(stepId);
}

function setUserOperate()
{
	var opt_ids = ","+top.getOptIDSByUser(app_id,site_id)+",";//登录人所拥有管理权限ID
	userOpts = opt_ids;
	//判断是否是站点管理员或超级管理员
	if(top.isSiteManager(app_id,site_id))
	{
		$("#opt_302").show().find("input").click();
        $("#audit_tr").addClass("hidden");
		$("#opt_303").show();
		$("#timer_publish").show();//显示出定时发布区域
	}else
	{
        if(wf_id == 0)
        {
            //是否有发布权限
            if(opt_ids.indexOf(",302,") > -1)
            {
                $("#opt_302").show().find("input").click();
                $("#audit_tr").addClass("hidden");
                $("#timer_publish").show(); //显示出定时发布区域
            }
        }
        else if(opt_ids.indexOf(",303,") > -1){
            //得到管理员审核流程中最大的步骤ID, 只有最终审核的人才有终审通过权限
            if(step_id == 100)
            {
                $("#opt_303").show();
                //是否有发布权限
                if(opt_ids.indexOf(",302,") > -1)
                {
                    $("#opt_302").show().find("input").click();
                    $("#audit_tr").addClass("hidden");
                    $("#timer_publish").show(); //显示出定时发布区域
                }
            }
        }else{

        }
	}

    if(opt_ids.indexOf(",531,") > -1)
    {
        $("#isChangeTime").css("display","inline-block");
        $(".releaseTime").attr("style","");
    }
}

function showInfoStatusText()
{
	/*
	var status_text = "";
	if(defaultBean.final_status == -1)
	{
		status_text = "删除";
	}else
	{
		switch(defaultBean.info_status)
		{
			case 0:status_text = "草稿";break
			case 1:status_text = "退稿";break
			case 2:status_text = "待审";break
			case 3:status_text = "撤稿";break
			case 4:status_text = "待发";break
			case 8:status_text = "已发";break
		}
	}
	$("#info_staus_tr th").text("信息状态：");
	$("#info_staus_tr td").empty().text(status_text);
	*/
}
/************权限设置　结束********************/

//替换url中的变量
function replaceURL(url)
{
	if(url.indexOf("InfoUtilData") > -1)
	{
		var site_handl = url.replace(/\$\{(.*)\}.*/ig,"$1").replace("InfoUtilData","SiteRPC");		
		return eval(site_handl)+url.substring(url.indexOf("/"));
	}
	else
	{		
		return url;
	}	
}

//
function getContentThumb()
{
	$("#thumburlList").empty();
	var c = getV(contentId); //内容
	var re = new RegExp(/<img.*?src.*?=.*?(.*?)>/ig);
	var r = c.match(re);
	if(r != null && r.length > 0)
	{
		if(r.length>1)
		{
			var strUrl="";
			for(var i=0;i<r.length;i++)
			{
				var src = r[i].replace(/<img[\s]*.*?src=\"(.*?)[\"](.*)/ig,"$1");
				strUrl+="<li style='float:left;margin-right:5px;'><div style='width:110px;height:130px;float:center;'><table>";
				strUrl+="<tr><td><img src="+src+" height='100' width='100'></td></tr>";
				strUrl+="<tr><td style='padding-left:50px;'><input type='radio' name='radio' onchange='addthumb_url(this.value)' value='"+src+"'></td></tr>";
				if(i>0 && i%4==0)
				{
					strUrl+="</table></div></li></br>";
				}else{
					strUrl+="</table></div></li>";
				}
			}
			$("#thumburlList").append(strUrl);
		}else{
			var src = r[0].replace(/<img[\s]*.*?src=\"(.*?)[\"](.*)/ig,"$1");
			$("#thumb_url").val(src);
		}
	}
}
//把选中的图片的thumb_url添加进input框中
function addthumb_url(thumb_url)
{
	$("#thumb_url").empty();
	$("#thumb_url").val(thumb_url);
}

//得到内容的翻页个数
function getContentPage(contents)
{
	var re = new RegExp(/<p class="ke-pageturning">.*?<\/p>/ig);
	var r = contents.match(re);
	if( r != null)
		return r.length+1;
	else
		return 1;
}

function getReleSiteID()
{
	var temp_site_id = site_id;
	if(app_id != "cms")
		temp_site_id = jsonrpc.SiteRPC.getSiteIDByAppID(app_id);

	return temp_site_id;
}

function selectPageHandle()
{	
	openSelectMaterialPage('savePicUrl',getReleSiteID(),'radio');	
}
