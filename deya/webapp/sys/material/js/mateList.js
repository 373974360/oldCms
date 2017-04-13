var MateFolderRPC = jsonrpc.MateFolderRPC;
var MateFolderBean = new Bean("com.deya.wcm.bean.material.MateFolderBean",true);
var MateInfoRPC = jsonrpc.MateInfoRPC;
var MateInfoBean = new Bean("com.deya.wcm.bean.material.MateInfoBean",true);

var con_m_mateinfo = new Map();
var val = new Validator();
var is_detailList = false;//是否显示的是详细列表
var beanList = null;
var input_type = "checkbox";//页面类型，默认为空，如果是选择素材页，值为　select
var tempid ="";//分类ID
var sucaiFolderCount = 0;//总数
var start_num = 0;//翻页起始数
var show_current_num = 0;//当前显示的总条数
var file_path_map = new Map();//用于存储附件的ID及路径等信息，用于删除文件，因为文件可能存放在素材库服务器上，如果要删除，需要使用rmi方法，太麻烦，所以这里把文件的地址都存储起来，用servlet提交来删除

//初始化函数开始======================================
var K_V = new Map();//取站点素材配置信息
var file_desc = "";
var file_ext = "";
function getMaterialConfig()
{
	K_V.put("group", "attachment");
	K_V.put("site_id", site_id);
	K_V.put("app_id", app_id);
	var resultMap = MateFolderRPC.getValues(K_V);
	resultMap = Map.toJSMap(resultMap);
	file_desc = resultMap.get("upload_allow");
	file_ext = "*."+file_desc.replace(/,/g,";*.")+";";
}

//初始化lightbox
function iniFancybox()
{
	$(".fancybox").fancybox();
}

//初始化列表显示个数
function initPageSize()
{
	var window_height = $(window).height();
	if(window_height >= 750 )
	{
		 pageSize = 90;
		 list_pageSize = 120;
	}
	else if(window_height >= 550)
	{
		pageSize = 75;
		list_pageSize = 100;
	}
	else 
	{
		pageSize = 60;
		list_pageSize = 80;
	}
}

//重设素材显示区域高度
function iniSuCaiPage()
{
	var window_height = $(window).height();
	var ucaiListArea_height = window_height - 75;
	//$("#SucaiListArea").css("height",ucaiListArea_height+"px");
}
//初始化左侧树
function iniLeftMenuTree()
{
	json_data = eval(MateFolderRPC.getMateFolderTreeJsonStr(f_id,site_id,user_id));
	setLeftMenuTreeJsonData(json_data);
	initMenuTree();
}
var imgtype ="";
var searchTime="";
function initMenuTree()
{
	$('#leftMenuTree').tree({		
		onClick:function(node)
		{
			$("#sucaiList").empty();
			if(node.id == -1 && node.attributes.ctype=="fixed")//点击素材类别
		 	{
		 		if(node.attributes.state == "open")
		 		{
		 			return
		 		}
		 	}
			if(node.id !=-1 && node.attributes.ctype=="fixed")     //点解树图片、flash 等
			{ 	
				optionIsDisabled();                        //置灰 隐藏div
				getFolderListshowRight(node);              //点击图片 把  '年' 显示在右边页面
			}
			if(node.attributes.ctype=="y")                 //点击树的年
			{   
				optionIsDisabled();                        //置灰 隐藏div
				var uploadyearForY = node.attributes.year; //得到年份
				getFolderListshowRight(node);              //点击树的年把月显示在右边页面
				$("#folderLists").empty();
				var type = node.id.replace("type_","");
				getuploadMonth(uploadyearForY,type);
			}
			if(node.attributes.ctype=="m")                 //点击树的月
			{   
				var uploadyearForM = node.attributes.year; //点击 月 时获取它所属的年
				var uploadmonth = node.text;               //所点击的 月 的值
				var type = node.id.replace("type_","");
				getSuCaiInfoListbyTree(type,"fixed",site_id,imgDomain,uploadyearForM,uploadmonth);
			}
			if(node.attributes.ctype=="custom")
			{
				var time="";
				$("#SucaiListArea").css("display","block");
				$("#folderListDiv").css("display","none");
				$("#tableoption").css("display","block");
				getMateInfoList(node.id,"custom",site_id,imgDomain,time); 
			}    
		}
	});
}

//置灰 并 隐藏 div
function optionIsDisabled()
{
	$("#searchkey").attr("disabled","true");  
	$("#beignTime").attr("disabled","true");
	$("#endTime").attr("disabled","true");
	$("#btnSearch").attr("disabled","true");
	$("#ctype").attr("disabled","true");
	
	$("#SucaiListArea").css("display","none");
	$("#tableoption").css("display","none");
	$("#folderListDiv").css("display","block");
}
//置灰的放开
function openDisabled() 
{
	$("#searchkey").removeAttr("disabled"); 
	$("#beignTime").removeAttr("disabled");
	$("#endTime").removeAttr("disabled");
	$("#btnSearch").removeAttr("disabled");
	$("#ctype").removeAttr("disabled");
}

//鼠标移入
function changefolderDivColor(id)
{
	if(id<10){
		$("#0"+id+"").css("background","#ddeeff");
	}else{
		$("#"+id+"").css("background","#ddeeff");
	}
}

//鼠标移出
function restorefolderDivColor(id)
{
	if(id<10){
		$("#0"+id+"").css("background","");
	}else{
		$("#"+id+"").css("background","");
	}
}

//点击树的图片时    '年' 显示在右边页面
var Node ="";
function getFolderListshowRight(node)
{
	var strLi = "";
	$("#folderLists").empty();
	    Node = $('#leftMenuTree').tree('getChildren',node.target);	
	for(var i=0;i<Node.length;i++)
	{
		if(Node[i].attributes.ctype == "y")
		{
		   var tmp = Node[i].id.replace("type_","");
		   strLi += "<li id='"+Node[i].text+"' style='width:160px;height:170px;float:left;border:1px solid #ccc;margin:0px 5px 5px 0px;'><div style='width:145px;height:130px;float:center;' onmousemove='changefolderDivColor("+Node[i].text+")' onmouseout='restorefolderDivColor("+Node[i].text+")'><table>";
		   var n  = $('#leftMenuTree').tree('getChildren',Node[i].target).length;
		   strLi += "<tr><td align='center'><img src='../images/folderimage.gif' onclick='getuploadMonth("+Node[i].text+","+tmp+")' title='"+Node[i].text+"'></td></tr>"+
		   		    "<tr><td align='center'>"+Node[i].text+"("+n+")"+"</td></tr>";
		   strLi += "</table></div></li>";
		}
	}
	$("#folderLists").append(strLi);
}

//点击   '年'  获取相应的     '月'
function getuploadMonth(year,type)
{
	var monthLi = "";
	var node = Node;
	var uploadYear = year;
	$("#folderLists").empty();
	for(var i=0;i<node.length;i++)
	{
		if(node[i].attributes.ctype == "m" && node[i].attributes.year == uploadYear)
		{
			monthLi += "<li id='"+node[i].text+"' style='width:160px;height:170px;float:left;border:1px solid #ccc;margin-right:5px;'><div style='width:145px;height:130px;float:center;' onmousemove='changefolderDivColor("+node[i].text+")' onmouseout='restorefolderDivColor("+node[i].text+")'><table>";
			monthLi += "<tr><td align='center'><img src='../images/folderimage.gif' onclick='getSuCaiInfoList("+uploadYear+","+node[i].text+","+type+")' title='"+node[i].text+"'></td></tr>"+
			   		  "<tr><td align='center'>"+node[i].text+"</td></tr>";
			monthLi += "</table></div></li>";
		}
	}
	$("#folderLists").append(monthLi);
}

//点击  右边页面   的  "月"  获取素材数据
function getSuCaiInfoList(year,month,p_tt)
{  
	var uploadmonth = "";
	if(month<10)
	{
		uploadmonth = "0"+month;
	}else{
		uploadmonth = month+"";
	}
	var uploadtime = year+uploadmonth;
	var ctype = "fixed";
	$("#sucaiList").empty();
	$("#folderListDiv").attr("style","");
	$("#folderListDiv").css("display","none");
	$("#tableoption").css("display","block");
	$("#SucaiListArea").css("display","block");
	openDisabled();  //置灰放开
	searchTime=uploadtime;  //用于搜素
	getMateInfoList(p_tt,ctype,site_id,imgDomain,uploadtime);
	
}

//点击    树    的  "月" 获取相应的素材数据
function getSuCaiInfoListbyTree(tmp,ctype,site_id,imgDomain,upload_year,upload_month)
{
	var upload_Time = upload_year+""+upload_month;
	$("#sucaiList").empty();
	$("#folderListDiv").attr("style","");
	$("#folderListDiv").css("display","none");
	$("#tableoption").css("display","block");
	$("#SucaiListArea").css("display","block");
	openDisabled();  //置灰放开
	searchTime=upload_Time; //搜素时间
	getMateInfoList(tmp,ctype,site_id,imgDomain,upload_Time);
}

//素材点击事件
function iniSuCaiList()
{
	$("#sucaiList li").live('click', function(event) {
		if((("INPUT,IMG").indexOf($(event.target)[0].tagName.toUpperCase())>-1))
		{
			return;
		}
		var checkboxObj = $(this).find(":checkbox")[0];
		$(checkboxObj).attr("checked",!($(checkboxObj).is(':checked')));
	});	
}
//全选|反选
function selectSuCai(type)
{
	if(type=="all")
	{
		$("#sucaiList :checkbox").attr("checked",true);
	}
	if(type=="other")
	{
		$("#sucaiList :checkbox").each(function(i){
		   	$(this).attr("checked",!($(this).is(':checked')));
		 });
	}
}
//缩略图，列表切换
function showSuCai(type,obj)
{
	initPageSize();
	$(".showList").css("font-weight","normal");
	$(obj).css("font-weight","bold");
	if(type=="list")
	{		
		if(is_detailList)
		{
			$("#sucaiList li").each(function(){
				var all_name = $(this).find("label[dtitle]").attr("dtitle");
				
				var s_name = $(this).find("label[dtitle]").text();
				$(this).find("label[dtitle]").text(all_name);
				$(this).find("label[dtitle]").attr("dtitle",s_name);			
			});
		}
		$("#sucaiList").removeClass("detailList");
		$("#sucaiList").addClass("imgList");
		$("#sucaiList").addClass("imgListNoImg");
		if(start_num < list_pageSize)
		{
			list_pageSize = list_pageSize - start_num;
			showMateInfosList(tempid,site_id,"custom",imgDomain,list_pageSize);
		}
		is_detailList = false;
	}
	
	if(type=="piclist")
	{
		if(is_detailList)
		{
			$("#sucaiList li").each(function(){
				var all_name = $(this).find("label[dtitle]").attr("dtitle");
				var s_name = $(this).find("label[dtitle]").text();
				$(this).find("label[dtitle]").text(all_name);
				$(this).find("label[dtitle]").attr("dtitle",s_name);			
			});
		}
		$("#sucaiList").removeClass("detailList");
		$("#sucaiList").removeClass("imgListNoImg");
		$("#sucaiList").addClass("imgList");
		is_detailList = false;
	}
	if(type=="detail_list")
	{			
		if(!is_detailList)
		{
			$("#sucaiList").addClass("imgListNoImg");
			$("#sucaiList").removeClass("imgList");
			$("#sucaiList").addClass("detailList");
			
			$("#sucaiList li").each(function(){
				var all_name = $(this).find("label[dtitle]").attr("dtitle");
				var s_name = $(this).find("label[dtitle]").text();
				$(this).find("label[dtitle]").text(all_name);
				$(this).find("label[dtitle]").attr("dtitle",s_name);			
			});
		}
		is_detailList = true;
	}
}
function openUploadSpan()
{
	$("#fileUploadSpan").dialog({
			resizable: false,
			width:450,
			height:350,
			modal: true,
			title:"上传素材"
	});
}
function CloseUploadSpan()
{
	$("#fileUploadSpan").dialog('close');
}
	
function getMateInfoList(o_id,ctype,site_id,imgDomain,upload_time)
{
	tempid ="";
	o_id += "";
	if(o_id.indexOf("type_") > -1){
		tempid = o_id.substring(5);
	}else{
		tempid = o_id;
	}
	start_num = 0;
	show_current_num = 0;
	file_path_map.clear();

	con_m_mateinfo.remove("beignTime","");
	con_m_mateinfo.remove("endTime","");
	con_m_mateinfo.remove("alias_name","");
	con_m_mateinfo.remove("att_type","");
	con_m_mateinfo.remove("f_id","");
	
	showMateInfosList(tempid,site_id,ctype,imgDomain,pageSize,upload_time);
}

function getMateInfoListSearch(o_id,ctype,site_id,imgDomain)
{
	tempid ="";
	o_id += "";
	if(o_id.indexOf("type_") > -1){
		tempid = o_id.substring(5);
	}else{
		tempid = o_id;
	}
	start_num = 0;
	show_current_num = 0;
	file_path_map.clear();	

	showMateInfosList(tempid,site_id,ctype,imgDomain,pageSize);
}


//点击左侧树节点后获取素材列表
function showMateInfosList(f_id,site_id,ctype,imgDomain,page_size,time)
{
	if(ctype =="fixed" && f_id != "-1")
	{	
		if(time == "")
		{
			con_m_mateinfo.remove("time","");
		}else{
			con_m_mateinfo.put("time",time);
		}
		
		con_m_mateinfo.put("att_type",f_id);
	}
	else if(ctype =="custom")
	{   	
		if(time == "")
		{
			con_m_mateinfo.remove("time","");
		}
		if(f_id == "")
		{
			con_m_mateinfo.remove("f_id","");
		}else{
			con_m_mateinfo.put("f_id",f_id);
		}
		//con_m_mateinfo.put("f_id",f_id);
	}
	else if(ctype =="")
	{
		con_m_mateinfo.remove("f_id","");
	}
	con_m_mateinfo.put("start_num",start_num);
	con_m_mateinfo.put("page_size",page_size);
	con_m_mateinfo.put("site_id",site_id);
	con_m_mateinfo.put("user_id",user_id);
	
	start_num += pageSize;	

	try{
		beanList = MateInfoRPC.getMateInfoList(con_m_mateinfo);//第一个参数为站点ID，暂时默认为空
	}catch(e){alert(e)}
	
	beanList = List.toJSList(beanList);//把list转成JS的List对象
	sucaiFolderCount = MateInfoRPC.getMateInfoListCounts(con_m_mateinfo);
	
	show_current_num += beanList.size();
	if(show_current_num > sucaiFolderCount)
		show_current_num = sucaiFolderCount;
	$("#sucaiPageCount").text(show_current_num);
	$("#sucaiFolderCount").text(sucaiFolderCount);

	var emptymsgFlay="";
	if(beanList.size()== 0 && $("#sucaiList li").size() == 0)
	{
		var tmpstr = "<li style='margin-left:50px; width：300px;'><div align='center' style='color:red; font-size:20px;'>暂无数据</div></li>";
		$("#sucaiList").empty();
		emptymsgFlay="is";
		$("#sucaiList").append(tmpstr);
	}
	var tmpLi="";
	for(var i=0;i<beanList.size();i++)
	{
		var t_att_ename = imgDomain+beanList.get(i).att_path + beanList.get(i).att_ename;
		var t_thumb_url = imgDomain+beanList.get(i).att_path + beanList.get(i).att_ename;
		var temp_name = beanList.get(i).alias_name.subString2(5);
		var full_name = beanList.get(i).alias_name;
		if(is_detailList)  //是否显示的是详细列表
		{
			full_name = beanList.get(i).alias_name.subString2(5);
			temp_name = beanList.get(i).alias_name;
		}else
		{			
			temp_name = beanList.get(i).alias_name.subString2(5);
			full_name = beanList.get(i).alias_name;
		}

		t_thumb_url = showPreviewBySuffix(t_thumb_url);
		
		var fan_class = "";
		if(".gif,.jpg,.png,.jpeg,.swf,.bmp".indexOf(beanList.get(i).fileext) > -1)
		{
			fan_class = 'class=\"fancybox\"';
		}
		/***** 文件路径存储*****/
		saveFilePathInMap(beanList.get(i));

		tmpLi += "<li id='"+beanList.get(i).att_id+"'><a href='"+t_att_ename+"' title='"+beanList.get(i).alias_name+"' "+fan_class+"><img src='"+t_thumb_url+"' /></a>";
		if(input_type == "radio")
			tmpLi += "<div><input id='"+beanList.get(i).att_id+"' name='"+beanList.get(i).att_id+"' type='radio' onclick='selectPicUrlHandl(\""+imgDomain+beanList.get(i).att_path+beanList.get(i).att_ename+"\",\""+beanList.get(i).alias_name+"\")'/><label onclick='selectPicUrlHandl(\""+imgDomain+beanList.get(i).att_path+beanList.get(i).att_ename+"\",\""+beanList.get(i).alias_name+"\")'  dtitle='"+full_name+"'>"+temp_name+"</label></div></li>";
		else
			tmpLi += "<div><input id='"+beanList.get(i).att_id+"' name='"+beanList.get(i).att_id+"' type='checkbox' /><label dtitle='"+full_name+"'>"+temp_name+"</label></div></li>";
		
	}
	if(emptymsgFlay == "")
	{
		//$("#sucaiList").empty();
		$("#sucaiList").append(tmpLi);
	}
	
	$(window).scroll(function(){
        var o = $(window);
        if (o.scrollTop() + o.height() + 50 >= $(document).height() && o.scrollTop() > 50) {
            //当前要加载的页码
            if (start_num >= sucaiFolderCount) return;
            showMateInfosList(f_id,site_id,ctype,imgDomain,page_size,time);
        }
        /*
        if (o.scrollTop()+o.height() > o.get(0).scrollHeight - 90)
        {
            if (window.loading ) return;
            if (window.show_more_lock) return;
            if (start_num >= sucaiFolderCount) return;
            //showMateInfosList(tempid,site_id,ctype,imgDomain,pageSize);
            showMateInfosList(f_id,site_id,ctype,imgDomain,page_size,time);
        }
        */
	});
	
	init_input();
	iniFancybox();
}

//将附件路径信息存入到map中
function saveFilePathInMap(attBean)
{
	var path = "";
	var att_ename = attBean.att_ename;
	var att_path = attBean.att_path;
	var thumb_url = attBean.thumb_url;
	var hd_url = attBean.hd_url;
	
	path = att_path + att_ename;
	if(thumb_url != "" && thumb_url != null)
	{
		path += ","+att_path + thumb_url;
	}
	if(hd_url != "" && hd_url != null)
	{
		path += ","+att_path + hd_url;
	}
	file_path_map.put(attBean.att_id,path);
}

function saveFilePathInMap2(att_id,att_ename,att_path,thumb_url,hd_url)
{
	var path = "";	
	path = att_path + att_ename;
	if(thumb_url != "" && thumb_url != null)
	{
		path += ","+att_path + thumb_url;
	}
	if(hd_url != "" && hd_url != null)
	{
		path += ","+att_path + hd_url;
	}
	file_path_map.put(att_id,path);
}

//根据文件后缀名显示不同的图标
function showPreviewBySuffix(f_url)
{
	var extName = "";
	if (f_url.lastIndexOf(".") >= 0) {
		extName = f_url.substring(f_url.lastIndexOf(".")+1).toLowerCase();
	}
	var img_ext = "jpg,jpeg,gif,png,bmp";
	var other_ext = "avi,bmp,doc,docx,flv,gif,html,jpg,mp3,mp4,mpeg,pdf,png,ppt,pptx,psd,rar,txt,wma,wmv,xls,xlsx,zip";

	if(img_ext.indexOf(extName) > -1)
	{
		return f_url;
	}
	if(other_ext.indexOf(extName) > -1)
	{
		return "../../images/ext/" + extName + ".png";
	}
	return "../../images/ext/unknown.png";
}

//对素材信息操作：删,改
function MateInfoClick(type,site_id){
	var cnode = $('#leftMenuTree').tree('getSelected');
	var cnode_id = cnode.id;
	var cnode_type = cnode.attributes.ctype;
	var str ="";
	var j=0;
    $("#sucaiList :checkbox").each(function(i){
    	if($(this).is(':checked')){
    		str += $(this).attr("id")+",";
    	}
	});
    if(str ==""){
		msgAlert(WCMLang.Select_empty+"!");
		return;
	}
    str = str.substring(0,str.length -1);
    var Temparr = str.split(","); 
	if(type == "1"){
		if(Temparr.length > 1){
			msgAlert(WCMLang.Select_singl+"!");
			return;	
		}else{
			OpenModalWindow("素材信息","/sys/material/operate/mateInfo_modify.jsp?att_id="+str+"&cnode_id="+cnode_id+"&cnode_type="+cnode_type+"&site_id="+site_id,500,210);
		}
	}else if(type == "2")
	{
		if(MateInfoRPC.deleteMateInfo(str))
		{
			for(j=0;j<Temparr.length;j++)
			{
				deleteFileForMate(Temparr[j]);
				$("#sucaiList li[id='"+Temparr[j]+"']").remove();
			}
			msgAlert("素材信息"+WCMLang.Delete_success);
			CloseModalWindow();
		}else
		{
			msgWargin("素材信息"+WCMLang.Delete_fail);
		}
	}
}

//远程调用删除附件
function deleteFileForMate(att_id)
{
	var url = imgDomain+"/servlet/UploadFileIfy?delete_file="+file_path_map.get(att_id)+"&sid="+encodeURIComponent(MateInfoRPC.getUploadSecretKey());	
	$("#delete_frame").attr("src",url);
}

//修改素材信息
function updateMateInfo()
{   
	var bean = BeanUtil.getCopy(MateInfoBean);
	$("#MateInfo_table").autoBind(bean);
	if(!standard_checkInputInfo("MateInfo_table"))
	{
		return;
	}
	var cnode_id = $("#cnode_id").val();
	var cnode_type = $("#cnode_type").val();
	var site_id = $("#site_id").val();

	bean.att_id = att_id;
	bean.alias_name = $("#alias_name").val();
	bean.att_description = $("#att_description").val();	
	
	if(MateInfoRPC.updateMateInfoBean(bean))
	{
		msgAlert("素材信息"+WCMLang.Add_success);
		getCurrentFrameObj().updateMateInfoTitle(bean.alias_name);
		CloseModalWindow();
	}
	else
	{
		msgWargin("素材信息"+WCMLang.Add_fail);
	}
}

function updateMateInfoTitle(title)
{
	$("#sucaiList :checked").next().text(title);	
	$("#sucaiList :checked").parent().parent().find("a").attr("title",title);
}

function removeHTML(){
	$("#sucaiList").html("");
}
//查询数据
function getSearchInfos(site_id)
{
	$("#sucaiList").empty();
	var searchNode;
	var ctype = $(":radio[checked]").val();
	var beignTime = $("#beignTime").val();
	var endTime = $("#endTime").val();
	var searchkey = $("#searchkey").val().trim();
	
	
	if(!searchTime=="")
	{
		uploadY = searchTime.substring(0, 4);  
		uploadM = searchTime.substring(4, 6);  
		
		var beginY = beignTime.toString().substring(0,4); 
		var endY = endTime.toString().substring(0,4);   
		
		var beginM = beignTime.toString().substring(5,7); 
		var endM = endTime.toString().substring(5,7);  
		
		/*
		if(uploadY != beginY || uploadY != endY)
		{
			msgAlert("只能查询"+uploadY+"年"+uploadM+"月时间的素材!");
		}
		if(uploadY==beginY && uploadY==endY)
		{
			if(uploadM != beginM || uploadM != endM)
			{
				msgAlert("只能查询"+uploadY+"年"+uploadM+"月时间的素材!");
			}
		}
		*/
	}
	
	if(ctype == 1){
		searchNode = $('#leftMenuTree').tree('getSelected');
	}else{
		searchNode = "";
	}
	if(searchkey !="")
	{	
		con_m_mateinfo.put("alias_name",searchkey);
	}
	if(beignTime !="" && beignTime != null && endTime !="" && endTime !=null){
		if(!judgeDate(beignTime,endTime)){
			msgAlert("结束时间不能小于起始时间!");
			return;
		}
	}
	if(beignTime !=""){
		con_m_mateinfo.put("beignTime",beignTime);
	}
	if(endTime !=""){
		con_m_mateinfo.put("endTime",endTime);
	}
	if(searchNode !=""){
		if(searchNode.attributes.ctype=="fixed")
		{   
			getMateInfoListSearch(searchNode.id,"fixed",site_id,imgDomain); 
		}else if(searchNode.attributes.ctype=="custom")
		{
			getMateInfoListSearch(searchNode.id,"custom",site_id,imgDomain); 
		}      
	}else{
			getMateInfoListSearch("","",site_id,imgDomain); 
	} 
	
}
//====================================以下是对素材目录的操作==============================
//点击左侧目录树操作:添加,修改,删除
function MateFolderClick(site_id,type){	
	var node = $('#leftMenuTree').tree('getSelected');
	if(type == "1"){
		if(node.attributes.ctype=="fixed")
		{ 
			msgAlert(WCMLang.Update_fixedFolderForNot);
			return;
		}else{
			OpenModalWindow("修改目录","/sys/material/operate/matefolder_add.jsp?f_id="+f_id+"&site_id="+site_id,470,195);
		}
	}else if(type == "2"){
		if(node.attributes.ctype=="fixed" || node.id == 0)
		{ 
			msgAlert(WCMLang.Update_fixedFolderForNot);
			return;
		}else{
			OpenModalWindow("修改目录","/sys/material/operate/matefolder_modify.jsp?f_id="+tempid,470,130);
		}
	}else if(type == "3"){
		deleteCustomFollder();
	}
}
//添加目录
function addMateFolder()
{
	var bean = BeanUtil.getCopy(MateFolderBean);	
	$("#MateFolder_table").autoBind(bean);

	if(!standard_checkInputInfo("MateFolder_table"))
	{
		return;
	}	
	bean.f_id = MateFolderRPC.getMateFolderID();
	bean.parent_id = $("#p_id").val();
	bean.cname = $("#subcname").val();	
	bean.site_id = site_id;
	bean.user_id = user_id;
	if(MateFolderRPC.insertMateFolder(bean))
	{	
		msgAlert("目录信息"+WCMLang.Add_success);
		getCurrentFrameObj().insertMateFolderTree(bean.f_id,bean.cname);
		CloseModalWindow();
	}
	else
	{
		msgWargin("目录信息"+WCMLang.Add_fail);
	}
}
//更新目录节点
function insertMateFolderTree(id,cname)
{
	insertFolderTreeNode(eval('[{"id":'+id+',"text":"'+cname+'","attributes":{"ctype":"custom"}}]'));	
}
function insertFolderTreeNode(nodeData){
	insertTreeNode(nodeData);
}
//修改目录
function updateMateFolder()
{
	var bean = BeanUtil.getCopy(MateFolderBean);
	$("#MateFolder_table").autoBind(bean);
	if(!standard_checkInputInfo("MateFolder_table"))
	{
		return;
	}
	bean.f_id = f_id;
	if(MateFolderRPC.updateMateFolder(bean))
	{
		msgAlert("目录信息"+WCMLang.Add_success);
		getCurrentFrameObj().updateMateFolderTree(bean.f_id,bean.cname);
		CloseModalWindow();
	}
	else
	{
		msgWargin("目录信息"+WCMLang.Add_fail);
	}
}
//更新目录节点
function updateMateFolderTree(id,cname)
{
	updateTreeNode(id,cname);
}
function deleteCustomFollder()
{
	//得到选中的树节点
	var node = $('#leftMenuTree').tree('getSelected');
	if(node == ""){
		msgAlert(""+WCMLang.Select_empty);
	    return;
	}
	if(node.attributes.ctype=="fixed" || node.id == 0)
	{ 
		msgAlert(WCMLang.Delete_fixedFolderForNot+"!");
		return;
	}
	msgConfirm(WCMLang.Delete_confirm,"deleteCustomFollderHandl()");
}

function deleteCustomFollderHandl()
{
	var node = $('#leftMenuTree').tree('getSelected');
	if(MateFolderRPC.deleteMateFolder(node.id))
	{
		msgAlert("目录信息"+WCMLang.Delete_success);
		$("div[node-id='"+node.id+"']").parent().remove();		
	}else
	{
		msgWargin("目录信息"+WCMLang.Delete_fail);
	}
}


//删除左侧树节点
//function deleteTreeNode(id)
//{
	//deleteTreeNode(id);
//}

//添加素材
function addMateInfo(att_id,f_id,site_id,serverUrl,hd_url,thum_url,att_ename,oldName,filesize)
{
	var bean = BeanUtil.getCopy(MateInfoBean);
	bean.att_id = att_id;
	bean.f_id = f_id;
	bean.site_id = site_id;
	bean.user_id = user_id;
	bean.att_path = serverUrl;
	bean.att_cname = oldName;
	bean.hd_url = hd_url;;
	bean.thumb_url = thum_url;
	bean.att_ename = att_ename;
	bean.alias_name = oldName;
	bean.filesize = filesize;
	
	if(MateInfoRPC.insertMateInfoBean(bean))
	{	
		//msgAlert("素材信息"+WCMLang.Add_success);
		//CloseModalWindow();
	}
	else
	{
		msgWargin("素材信息"+WCMLang.Add_fail);
	}
}

//移动图片
var move_selected_id = "";
function MoveClick()
{	
	move_selected_id = "";
	$("#sucaiList :checked").each(function(i){		
		if(i > 0)
			move_selected_id += ",";
		move_selected_id += ($(this).attr("id"))
	});
	if(move_selected_id == "" || move_selected_id  == null)
	{
		msgWargin("请选择要转移的素材");
	}
	else
		OpenModalWindow("选择目录","/sys/material/operate/cat_tree.jsp?site_id="+site_id,350,420);
}

function MoveMateHandl(cat_id)
{
	if(MateInfoRPC.moveMateInfo(cat_id,move_selected_id))
	{
		msgAlert("素材"+WCMLang.Move_success);
		$("#sucaiList").empty();
		getMateInfoList(tempid,"custom",site_id,imgDomain);
	}else
	{
		msgWargin("素材"+WCMLang.Move_fail);
		return;
	}
}