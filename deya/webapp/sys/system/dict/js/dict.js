var DataDictRPC = jsonrpc.DataDictRPC;
var DataDictBean = new Bean("com.deya.wcm.bean.system.dict.DataDictBean",true);
var DataDictCategoryBean = new Bean("com.deya.wcm.bean.system.dict.DataDictCategoryBean",true);

var val=new Validator();

function selectKZ(){
	treeNodeSelected("-1");
	showMessage("-1","扩展数据字典","kz");
}

//初始化左边栏树对像
function iniLeftMenuTree(){
	$('#leftMenuTree').tree({
		url: 'jsonData.jsp',
		onClick:function(node){
			if(node.attributes!=undefined){   
				showMessage(node.id,node.text,node.attributes.nt); 
            }  
		}		
	});
}

function showMessage(id, name, tp){
	dc = chooseNode(id,tp);
	var cmap = new Map();
	cmap.put("dict_cat_id",id);
	cmap.put("dict_sort","asc");
	var dictList = DataDictRPC.getDictList(cmap);
	setDictList(dictList);
	var dcBean = DataDictRPC.getOneDCBean(id);
	showDictCategory(dcBean);
	resetClass();
}

function chooseNode(id,tp){
	if(tp == "df"){
		$("#is_sys").val("1");
		$("#dict_cat_name").attr("disabled","disabled");
		$("#dict_cat_id").attr("disabled","disabled");
		return "-2";
	}
	$("#is_sys").val("0");
	$("#dict_cat_name").attr("disabled","");
	$("#dict_cat_id").attr("disabled","");
	return "-1";
}

function setDictList(dictList){
	dictList = List.toJSList(dictList);//把list转成JS的List对象
	
	if(dictList != null && dictList.size() > 0){
		var str = "";
		num = dictList.size();
		for(var i=1; i<=dictList.size(); i++){
			var d_name = "";
			var d_id = "";
			if(dictList.get(i-1).dict_name != null)
			{
				d_name = dictList.get(i-1).dict_name;
				d_id = dictList.get(i-1).dict_id;
			}

			var isdf = "";
			if(dictList.get(i-1).is_defult == 1){
				isdf = " checked ";
				selectFlag = true;
			}
			str += '<tr>'
				+'<td class="width40">'
				+'<span class="c_index">'+(i)+'：</span>'
				+'</td>'
				+'<td class="width200">'
				+'<input id="dict_name" name="dict_name" class="width200" value="'+d_name+'"/>'
				+'</td>'
				+'<td class="width100">'
				+'<input id="dict_num" name="dict_num" class="width100"  onblur="checkSame(this)" value="'+d_id+'"/>'
				+'</td>'
				+'<td align="center" class="width40">'
				+'<input name="isdefault" type="checkbox" '+isdf+' onclick="selectDefault(this)"/>'
				+'</td>'
				+'<td>'
				+'<ul class="optUL">'
				+'<li class="ico_up" title="上移" onclick="moveUp(this)"></li>'
				+'<li class="ico_down" title="下移" onclick="moveDown(this)"></li>'
				+'<li class="ico_delete" title="删除" onclick="deleteTr(this)"></li>'
				+'</ul>'
				+'</td>'
				+'</tr>';
		}
		$("#dictData").html(str);
	}else{
		$("#dictData").html("");
		num = 0;
		addDict();
	}
	init_input2();
}

function showDictCategory(cbean){
	if(cbean != null){
		$("#dict_cat_name").val(cbean.dict_cat_name);
		var memo = cbean.dict_cat_memo;
		if(memo == null || memo == "NULL" || memo == "null"){
			memo = "";
		}
		$("#dict_cat_memo").val(memo);
		$("#dict_cat_id").val(cbean.dict_cat_id);
		dcFlag = true;
	}else{
		clearDictCategory();
	}
}

//重排序号
function resetNum(){
	var ic = 1;
	$("#dictData tr").each(function(){
		$("#dictData tr li[title='上移']").addClass("ico_up");
		$("#dictData tr li[title='下移']").addClass("ico_down");
		$(this).find(".c_index").text((ic++) + "：");
	});
	$("#dictData tr .ico_up").first().removeClass("ico_up");
	$("#dictData tr .ico_down").last().removeClass("ico_down");
}

function resetClass(){
	$("#dictData tr .ico_up").first().removeClass("ico_up");
	$("#dictData tr .ico_down").last().removeClass("ico_down");
}

function selectDefault(o){
	$(":checkbox[name='isdefault'][checked]").attr("checked","");
	$(o).attr("checked","checked");
}

//删除
function deleteTr(obj){
	$(obj).remove();
	resetNum();
}

function moveUp(o){
	$(o).parent().parent().parent().insertBefore($(o).parent().parent().parent().prev());
	resetNum();
}

function moveDown(o){
	$(o).parent().parent().parent().insertAfter($(o).parent().parent().parent().next());
	resetNum();
}

function deleteTr(o){
	$(o).parent().parent().parent().remove();
	resetNum();
}

function addDict(){
	var htmlStr = '<tr>'
	+'<td class="width40">'
	+'<span class="c_index">'+(++num)+'：</span>'
	+'</td>'
	+'<td class="width200">'
	+'<input id="dict_name" name="dict_name" class="width200" />'
	+'</td>'
	+'<td class="width100">'
	+'<input id="dict_num" name="dict_num" class="width100" onblur="checkSame(this)"/>'
	+'</td>'
	+'<td align="center" class="width40">'
	+'<input name="isdefault" type="checkbox"  onclick="selectDefault(this)"/>'
	+'</td>'
	+'<td>'
	+'<ul class="optUL">'
	+'<li class="ico_up" title="上移" onclick="moveUp(this)"></li>'
	+'<li class="ico_down" title="下移" onclick="moveDown(this)"></li>'
	+'<li class="ico_delete" title="删除" onclick="deleteTr(this)"></li>'
	+'</ul>'
	+'</td>'
	+'</tr>';
	$("#dictData").append(htmlStr);
	init_input2();
	resetNum();
}

function addDictionaryList(){
	resetNum();
	if(dc == "" || dc < 0){
		dc = $("#dict_cat_id").val();
	}
	DataDictRPC.delDicts(dc);
	$("#dictData tr").each(function(){
		var dbean = BeanUtil.getCopy(DataDictBean);
		dbean.dict_cat_id=dc;
		dbean.dict_id=$(this).find("input[name='dict_num']").val();
		dbean.dict_name=$(this).find("input[name='dict_name']").val();
		dbean.dict_sort=$(this).find(".c_index").html().replace("：","");
		dbean.is_defult=($(this).find(":checkbox[name='isdefault'][checked]").is(':checked') ? "1" : "0");
		dbean.app_id=app;
		dbean.site_id=site_id;
		DataDictRPC.addDict(dbean);
	});
}

function clearDictCategory(){
	$("#dict_cat_name").val("");
	$("#dict_cat_id").val("");
	$("#dict_cat_memo").val("");
	$("#dictData").html("");
	num = 0;
	addDict();
	dcFlag = false;
}

function addNodeKZ(){
	treeNodeSelected("-1");
	clearDictCategory();
	$("#is_sys").val("0");
	$("#dict_cat_name").attr("disabled","");
	$("#dict_cat_id").attr("disabled","");
	dc = "-1";
}

function insertNode(id,name){
	if(!dcFlag){
		insertTreeNode(eval('[{"id":"'+id+'", "text":"'+name+'","attributes":{"site_id":"'+site_id+'","nt":"kz"}}]'));
	}
}

function deleteNode(ids){
	$("div[node-id='"+ids+"']").parent().remove();		
}

function updateNode(id,name){
	updateTreeNode(id,name);
}

function deleteDC(){
	msgConfirm(WCMLang.Delete_confirm,"deleteDCHandl()");
}

function deleteDCHandl()
{
	var node = $('#leftMenuTree').tree('getSelected');
	if(node.id == "-1" || node.id == "-2" || node.nt == "df"){
		msgAlert("只能删除扩展数据字典");
		return;
	}else{
		DataDictRPC.delDC(node.id);
		DataDictRPC.delDicts(node.id);
		deleteNode(node.id);
		dc = "-1";
		selectKZ();
		msgAlert("数据字典删除成功");
	}
}

//添加
function fnOK(){
	var bean = BeanUtil.getCopy(DataDictCategoryBean);	
	$("#dc_table").autoBind(bean);
	if(!standard_checkInputInfo("dc_table")){
		return;
	}
	if(isExistFlag){
		msgAlert("字典代码已经存在，请重新输入");
		$("#dict_cat_id").focus();
		return;
	}
	
	if(!submitFlag){
		return;
	}
	if(DataDictRPC.addDC(bean)){
		if(!dcFlag){
			insertNode(bean.dict_cat_id,bean.dict_cat_name);
		}else{
			updateNode(bean.dict_cat_id,bean.dict_cat_name);
		}
		addDictionaryList();
		msgAlert("数据字典"+WCMLang.Add_success);
	}
}

function checkSame(o){
	var tmp = 0;
	$("#dictData tr").each(function(){
		if($(this).find("input[name='dict_num']").val() == $(o).val()){
			tmp++;
		}
	});
	if(tmp > 1){
		jQuery.simpleTips(o,"数据项编码不能重复",2000);
		//$(o).focus();
		$(o).val("");
		submitFlag = false;
		return;
	}
	submitFlag = true;
}

function checkExist(input_name,o){
	var isExist = DataDictRPC.getOneDCBean($(o).val());
	if(!dcFlag && isExist != null && isExist.dict_cat_id != null && isExist.dict_cat_id != ""){
		jQuery.simpleTips(input_name,"字典代码为"+$(o).val()+"的数据已经存在",2000);
		$(o).focus();
		isExistFlag = true;
		return;
	}else{
		isExistFlag = false;
	}
}

function init_input2(){
	$(":text").addClass("input_text");
	$(":text").blur( function () { $(this).removeClass("input_text_focus"); } );
	$(":text").focus( function () { $(this).addClass("input_text_focus"); } );
}