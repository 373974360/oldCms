var emptyBean = new Bean("com.deya.wcm.services.model.Fields",true);

//加载文件后执行
$(function(){
			setSelectDataType();
		}	
)

//选择单行文本
function selectType0(){
	$("#areaHtmlDiv").hide();
	$("#areaNoHtmlDiv").hide();
	$("#selectDiv").hide();
	$("#numDiv").hide();
	$("#timeDiv").hide();
	$("#fileDiv").hide();
	$("#textDiv").show();
}
//选择多行文本(不带html编辑器)
function selectType1(){
	$("#textDiv").hide();
	$("#selectDiv").hide();
	$("#numDiv").hide();
	$("#timeDiv").hide();
	$("#areaHtmlDiv").hide();
	$("#fileDiv").hide();
	$("#areaNoHtmlDiv").show();
}
//选择多行文本（带html编辑器）
function selectType2(){
	$("#textDiv").hide();
	$("#selectDiv").hide();
	$("#numDiv").hide();
	$("#timeDiv").hide();
	$("#areaNoHtmlDiv").hide();
	$("#fileDiv").hide();
	$("#areaHtmlDiv").show();
}
//选择选项
function selectType3(){
	$("#textDiv").hide();
	$("#areaHtmlDiv").hide();
	$("#areaNoHtmlDiv").hide();
	$("#numDiv").hide();
	$("#timeDiv").hide();
	$("#fileDiv").hide();
	$("#selectDiv").show();
}
//选择数字
function selectType4(){
	$("#textDiv").hide();
	$("#areaHtmlDiv").hide();
	$("#areaNoHtmlDiv").hide();
	$("#selectDiv").hide();
	$("#timeDiv").hide();
	$("#fileDiv").hide();
	$("#numDiv").show();
}
//选择日期
function selectType5(){
	$("#textDiv").hide();
	$("#areaHtmlDiv").hide();
	$("#areaNoHtmlDiv").hide();
	$("#selectDiv").hide();
	$("#numDiv").hide();
	$("#fileDiv").hide();
	$("#timeDiv").show();
}
//选择文件
function selectType6(){
	$("#textDiv").hide();
	$("#areaHtmlDiv").hide();
	$("#areaNoHtmlDiv").hide();
	$("#selectDiv").hide();
	$("#numDiv").hide();
	$("#timeDiv").hide();
	$("#fileDiv").show();
}

function setSelectValue(){
	var type = $(":radio[name='select_view'][checked='true']").val();
	if(type=='0'){
	    $("#default_valueTextSelect").val($("#select0").getSelectedValue());	
	}else if(type=='1'){
		var select1 = $("#select1").getSelectedValue();
		var valueAll = "";
		if(select1==null){
			$("#default_valueTextSelect").val("");
		}else{
			for(var i=0;i<select1.length;i++){
				if(i!=select1.length-1){
					valueAll = valueAll + select1[i] + ",";
				}else{
					valueAll = valueAll + select1[i];
				}
			}
			$("#default_valueTextSelect").val(valueAll);
		}
		
	}else if(type=='2'){
	    $("#default_valueTextSelect").val($("#select2").getSelectedValue());	
	}else if(type=='3'){
		var select3 = $("#select3").getSelectedValue();
		var valueAll = "";
		if(select3==null){
			$("#default_valueTextSelect").val("");
		}else{
			for(var i=0;i<select3.length;i++){
				if(i!=select3.length-1){
					valueAll = valueAll + select3[i] + ",";
				}else{
					valueAll = valueAll + select3[i];
				}
			}
			$("#default_valueTextSelect").val(valueAll);
		}
		
	}
	//alert($("#default_valueTextSelect").val());
}


function showSelecetTr(){
	var type = $(":radio[name='select_view'][checked='true']").val();
	if (type == '0') {
	    $("#default_valueTextSelectTR0").show();
		$("#default_valueTextSelectTR1").hide();
		$("#default_valueTextSelectTR2").hide();
		$("#default_valueTextSelectTR3").hide();
	}else if (type == '1') {
	    $("#default_valueTextSelectTR1").show();
		$("#default_valueTextSelectTR0").hide();
		$("#default_valueTextSelectTR2").hide();
		$("#default_valueTextSelectTR3").hide();
	}else if (type == '2') {
	    $("#default_valueTextSelectTR2").show();
		$("#default_valueTextSelectTR0").hide();
		$("#default_valueTextSelectTR1").hide();
		$("#default_valueTextSelectTR3").hide();
	}else if (type == '3') {
	    $("#default_valueTextSelectTR3").show();
		$("#default_valueTextSelectTR0").hide();
		$("#default_valueTextSelectTR1").hide();
		$("#default_valueTextSelectTR2").hide();
	}
}

//选择 选项 的 数据来源类型（自定义）
function data_type_select1(){
	$("#select_itemTR").show();
	$("#default_valueTextSelectTR").show();
	$("#data_type_selectTR").hide();
	setSelectDefault();
}
//选择 选项 的 数据来源类型（数据字典）
function data_type_select2(){  
	$("#select_itemTR").hide();
	$("#default_valueTextSelectTR").hide();
	$("#data_type_selectTR").show();
	//setDateList();
}

//读取数据字典
function setDateList(){
	var value = $("#data_type_select").getSelectedValue();
	var dictionaryList = jsonrpc.DataDictRPC.getDataDictList(value);
	dictionaryList = List.toJSList(dictionaryList);//把list转成JS的List对象
	if (value != '-1') {
		$("#select0").clearAll();
		$("#select0").addOption("--请选择--", "");
		for (var i = 0; i < dictionaryList.size(); i++) {
			var dictionaryBean = dictionaryList.get(i);
			$("#select0").addOption(dictionaryBean.dict_name, dictionaryBean.dict_id);
		}
		
		$("#select1").clearAll();
		for (var i = 0; i < dictionaryList.size(); i++) {
			var dictionaryBean = dictionaryList.get(i);
			$("#select1").addOption(dictionaryBean.dict_name, dictionaryBean.dict_id);
		}
		
		$("#select2").clearAll();
		$("#select2").addOption("--请选择--", "");
		for (var i = 0; i < dictionaryList.size(); i++) {
			var dictionaryBean = dictionaryList.get(i);
			$("#select2").addOption(dictionaryBean.dict_name, dictionaryBean.dict_id);
		}
		
		$("#select3").clearAll();
		for (var i = 0; i < dictionaryList.size(); i++) {
			var dictionaryBean = dictionaryList.get(i);
			$("#select3").addOption(dictionaryBean.dict_name, dictionaryBean.dict_id);
		}
	}
	
}

//设置选项数据来源分类
function setSelectDataType(){
	//得到系统默认的数据字典
	dictionaryList = jsonrpc.DataDictRPC.getDictCategoryForSyS(1);
	dictionaryList = List.toJSList(dictionaryList);//把list转成JS的List对象
	for (var i = 0; i < dictionaryList.size(); i++) {
			var dataDictCategoryBean = dictionaryList.get(i);  
			$("#data_type_select").addOption(dataDictCategoryBean.dict_cat_name,dataDictCategoryBean.dict_cat_id);
	}
	//得到扩展的数据字典
	var dictionaryList = jsonrpc.DataDictRPC.getDictCategoryForSyS(0);
	dictionaryList = List.toJSList(dictionaryList);//把list转成JS的List对象
	for (var i = 0; i < dictionaryList.size(); i++) {
			var dataDictCategoryBean = dictionaryList.get(i);  
			$("#data_type_select").addOption(dataDictCategoryBean.dict_cat_name,dataDictCategoryBean.dict_cat_id);
	}
}


//点击确定按钮事件
function doAdd(type){
	
	if(!standard_checkInputInfo("form_table"))
	{
		return;
	}
	if(!standard_checkInputInfo("form_table1"))
	{
		return;
	}
	if(!standard_checkInputInfo("form_table2"))
	{
		return;
	}
	if(!standard_checkInputInfo("form_table3"))
	{
		return;
	}
	if(!standard_checkInputInfo("form_table4"))
	{
		return;
	}
	if(!standard_checkInputInfo("form_table5"))
	{
		return;
	}
	if(!standard_checkInputInfo("form_table6"))
	{
		return;
	}
	if(!standard_checkInputInfo("form_table7"))
	{
		return;
	}
	
	var bean = BeanUtil.getCopy(emptyBean);
	
    //判断字段名字的格式
	var field_enname = $("#field_enname").val();
	field_enname = "c_"+field_enname;
	
    //判断字段提示
	var field_text = $("#field_text").val();
	field_text = null2String(field_text);

    //判断是否必填
    var is_null = $(":radio[name='is_null'][checked]").val();

    //判断是否显示在添加页面
    var is_display = $(":radio[name='is_display'][checked]").val();
   
    //判断此字段类型
    var field_type = $(":radio[name='field_type'][checked]").val();
    
    //选择是的单行文本的情况
    if(field_type==0){
   	   
	   //判断验证规则
       var validator = $('#validator').val();
	   
	   //判断字段默认值
	   var default_valueText = $("#default_valueText").val();
	   default_valueText = null2String(default_valueText);
	   
	   var field_maxnum = $("#field_maxnum").val();
	   if(field_maxnum!='' && default_valueText!=''){
		   if(field_maxnum*1<default_valueText.length){
			        var htmlError = "默认值超出最大范围！"
	   		        htmlError = "<li><font color='red'>"+htmlError+"</font>";
			        msgWargin(htmlError);
	   				return;
		   }
	   }
	   
	   var field_maxlength = $("#field_maxlength").val();
	   //填充bean特殊属性
	   bean.field_maxnum = field_maxnum;
	   bean.field_maxlength = field_maxlength;
	   bean.validator = validator;
	   bean.default_value = default_valueText;
   }  
   //选择的是多行文本（不支持HTML）的情况
   if(field_type==1){
   	
   	   //判断显示宽度
       var width = $("#width").val();
	   
	   //判断显示高度
       var height = $("#height").val();
	   
	   //判断最大字符数
       var field_maxnumArea = $.trim($("#field_maxnumArea").val());
	   //alert(field_maxnumArea);
	   
	   //判断字段默认值
       var default_valueArea = $("#default_valueArea").val();
	   default_valueArea = null2String(default_valueArea);

	   if(field_maxnumArea!='' && default_valueArea!=''){
		   if(field_maxnumArea*1<default_valueArea.length){
			        var htmlError = "默认值超出最大范围！"
	   		        htmlError = "<li><font color='red'>"+htmlError+"</font>";
			        msgWargin(htmlError);
	   				return;
		   }
	   }
	   
	   //填充bean特殊属性
	   bean.width = width;
	   bean.height = height;
	   bean.field_maxnum = field_maxnumArea;
	   bean.default_value = default_valueArea;
   }

   //选择的是多行文本（支持HTML）的情况
   if (field_type == 2) {
       
	   
       //判断显示宽度
       var widthHtml = $("#widthHtml").val();

	   //alert(widthHtml);
	   
	   //判断显示高度
       var heightHtml = $("#heightHtml").val();
	   //alert(heightHtml);
	   
	   //判断最大字符数
       var field_maxnumAreaHtml = $.trim($("#field_maxnumAreaHtml").val());
	  // alert(field_maxnumAreaHtml);
	   
	   //判断字段默认值
       var default_valueAreaHtml = $("#default_valueAreaHtml").val();
	   default_valueAreaHtml = null2String(default_valueAreaHtml);

	   if(field_maxnumAreaHtml!='' && default_valueAreaHtml!=''){
		   if(field_maxnumAreaHtml*1<default_valueAreaHtml.length){
			        var htmlError = "默认值超出最大范围！"
	   		        htmlError = "<li><font color='red'>"+htmlError+"</font>";
			        msgWargin(htmlError);
	   				return;
		   }
	   }
	   
	   //填充bean特殊属性
	   bean.width = widthHtml;
	   bean.height = heightHtml;
	   bean.field_maxnum = field_maxnumAreaHtml;
	   bean.default_value = default_valueAreaHtml;
   }	
   
   //选择的是选项的情况
   if (field_type == 3) {

       //判断每个选项
       var select_item = "";
	   var len = $("select[name=select_item] option").length;
	   var i=0;
	   $("select[name=select_item] option").each(function(){
	  	   i++;
	  	   if(i==len){
		   	  select_item += $(this).val();
		   }else{
		      select_item += $(this).val() + ",";
		   }
        });
	    //alert(select_item);
		
		//判断 选项数据来源 的类型
		var data_type = $(":radio[name='data_type'][checked]").val();
		var data_type_id = '';
		if(data_type=='1'){//自定义类型
			data_type_id = '';
		}else if(data_type=='2'){//数据字典类型
			data_type_id = $("#data_type_select").getSelectedValue();
		}
		//alert(data_type);
		//alert(data_type_id);
		
		if(data_type=='1'){//自定义类型
			//判断是否添加选项和默认值是否填写正确
			if(!ChangeHiddenFieldValue()){
				return;
			}
		}else if(data_type=='2'){//数据字典类型
		    if(data_type_id=='-1'){
		    	msgWargin("请选择数据分类");
                return false;
			}
			
		}

		//判断显示选项使用
		var select_view = $(":radio[name='select_view'][checked]").val();
		//alert(select_view);
		
		//判断字段默认值
        var default_valueTextSelect = $("#default_valueTextSelect").val();
	    default_valueTextSelect = null2String(default_valueTextSelect);
	    
	    //填充bean特殊属性
	    bean.select_item = select_item;
	    bean.select_view = select_view;
	    bean.default_value = default_valueTextSelect;
		bean.data_type = data_type;
		bean.data_type_id = data_type_id;
		//alert(bean.data_type_id);		
   }
   
   //选择的是数字的情况
   if (field_type == 4) {
       //判断最小值
       var min_num = $.trim($("#min_num").val());
	   //alert(min_num);
	   
	   //判断最大值
       var max_num = $.trim($("#max_num").val());
	  //alert(max_num);
	  
	   //判断字段默认值
       var default_valueNum = $.trim($("#default_valueNum").val());
       if(max_num!="" && min_num!=""){
    	   if(max_num*1<min_num*1){
    		    var htmlError = "最大值不能小于最小值！"
   		        htmlError = "<li><font color='red'>"+htmlError+"</font>";
    		    msgWargin(htmlError);
   				return; 
    	   }
       }  
	   if(default_valueNum!=''){
		   if(default_valueNum*1<min_num*1 || default_valueNum*1>max_num*1){
		   	    var htmlError = "数字默认值超出范围！"
		        htmlError = "<li><font color='red'>"+htmlError+"</font>";
		   	    msgWargin(htmlError);
				return; 
		   }
	   }
	   //alert(default_valueNum);
	   
	   //填充bean特殊属性
	   bean.min_num = min_num;
	   bean.max_num = max_num;
	   bean.default_value = default_valueNum;
	   
   }
   
   //选择的是日期的情况
   if (field_type == 5) {
       
	   var default_value = "";
	   var timeSelectType = $(":radio[name='timeSelectType'][checked]").val();
	   if(timeSelectType==1){
	   	  if($("#emit_tiem").val()==''){
		  	default_value = "";
		  }else{
		  	default_value = $("#time").val()
		  } 
	   }else if(timeSelectType==2){
	   	  default_value = "nowTime";
	   }
	   //alert(default_value);
	   
	   //填充bean特殊属性
	   bean.default_value = default_value;
   }
   
   //选择的是文件的情况
   if (field_type == 6) {
       //判断字段默认值
       var default_value = $.trim($("#file_type").val());
	   if(default_value == null || (default_value = $.trim(default_value)).length == 0){
		  default_value = '';
	   }
	   //填充bean特殊属性
	   bean.default_value = default_value;
   }
   
   //填充bean公用属性
   //bean.model_id = model_id;
   bean.field_enname = field_enname;
   var field_cnname = $("#field_cnname").val();
   bean.field_cnname = field_cnname;
   bean.field_text = field_text;
   bean.is_null = is_null;   
   bean.is_display = is_display;  
   bean.field_type = field_type;
   bean.is_sys = '1';

   //alert("添加");
   //alert(toJSON(bean));
   //return false; 

   if(type=='add'){
/*		
		if(field_type=='6'){
			//校验字段中是否已经有了文件类型
		    var isExistFile = fieldsRPC.getFileByModelId(bean.model_id);
			if(isExistFile){
				var htmlError = "一个内容模型中只能存在一个文件类型的字段！"
		        htmlError = "<li><font color='red'>"+htmlError+"</font>";
		        //$("#errorSpan").html(htmlError);
				alertWar(htmlError);
				return;   
			}
		}
*/		
   	    //校验字段名称是否重复 false:不存在，true：存在
		var m = new Map();
		m.put("field_enname", field_enname);
		var isExist = jsonrpc.FieldsRPC.getFieldsListCount(m);
		if(isExist==1){
			var htmlError = "【字段名称】已经存在！"
	        htmlError = "<li><font color='red'>"+htmlError+"</font>";
	        //$("#errorSpan").html(htmlError);
			msgWargin(htmlError);
			return; 
		}else{
			
			//msgWargin("新增成功");
			//return;
			 if(jsonrpc.FieldsRPC.addFields(bean)){
				 msgAlert("新增成功");
				 getCurrentFrameObj(tab_index).reloadList();
				 tab_colseOnclick(curTabIndex);
			 }else{
				 msgAlert("新增失败");
			 }
		}
   }else if(type=='mod'){
		
	    bean.id = id;   
	    //alert(id);
   	    //alert(bean.id);
   	    if(jsonrpc.FieldsRPC.updateFieldsById(bean)){
   	    		 msgAlert("修改成功");
   	    		 getCurrentFrameObj(tab_index).reloadList();
   	    		 tab_colseOnclick(curTabIndex);
			 }else{
				 msgAlert("修改失败");
		}
   }
}

//填充表单
function fullFields(id){
	var bean = BeanUtil.getCopy(emptyBean);
	bean = jsonrpc.FieldsRPC.getFieldById(id);
	//alert(toJSON(bean));
	//alert(id);
	//得到字段id
	//id = bean.id;
	//alert(bean.field_enname);
	var c_ename = bean.field_enname.substring(2);
	//alert(c_ename);
	$("#field_enname").val(c_ename).attr("disabled","true");
	$("#field_cnname").val(bean.field_cnname);
	$("#field_text").val(bean.field_text);
	$(":radio[name='is_null'][value='"+bean.is_null+"']").attr("checked",true);
	$(":radio[name='is_display'][value='"+bean.is_display+"']").attr("checked",true);
	$(":radio[name='field_type'][value='"+bean.field_type+"']").attr("checked",true);
	
	//修改的是单行文本
	if(bean.field_type=='0'){
		
		//如果不是系统字段
		if(bean.is_sys=='1'){
			/*
			//不可用用的字段类型
			$("#field_type1").attr("disabled","true");
			$("#field_type2").attr("disabled","true");
			$("#field_type3").attr("disabled","true");
			$("#field_type4").attr("disabled","true");
			$("#field_type5").attr("disabled","true");
			$("#field_type6").attr("disabled", "true");
			
		}else{	
		*/				
			//不可用用的字段类型
			$("#field_type3").attr("disabled","true");
			$("#field_type4").attr("disabled","true");
			$("#field_type5").attr("disabled","true");
			$("#field_type6").attr("disabled", "true");
		}
		
		//填充表单
		//alert(bean.field_maxnum);
		//alert(bean.field_maxlength);
		$("#field_maxnum").val(bean.field_maxnum);
		$("#field_maxlength").val(bean.field_maxlength);
		if(bean.validator==null){	
			bean.validator = "0";
		} 
		$("#validator").attr("value",bean.validator);
		if(bean.default_value==null){	
			bean.default_value = "";
		}                   
		$("#default_valueText").val(bean.default_value);
		
	}else //修改的是多行文本（不支持HTML）
		if(bean.field_type=='1'){
			
			//如果不是系统字段
			if (bean.is_sys == '1') {
			/*
				//不可用用的字段类型
				$("#field_type0").attr("disabled", "true");
				$("#field_type2").attr("disabled", "true");
				$("#field_type3").attr("disabled", "true");
				$("#field_type4").attr("disabled", "true");
				$("#field_type5").attr("disabled", "true");	
				$("#field_type6").attr("disabled", "true");	
			}
			else {
			*/
			    //不可用用的字段类型
				$("#field_type0").attr("disabled","true");
				$("#field_type3").attr("disabled","true");
				$("#field_type4").attr("disabled","true");
				$("#field_type5").attr("disabled","true");
				$("#field_type6").attr("disabled", "true");
			}		
			
			//根据类型表单的变化
			$("#textDiv").hide();
			$("#areaNoHtmlDiv").show();
			
			//填充表单
			$("#width").val(bean.width);
			$("#height").val(bean.height);
			if(bean.field_maxnum==null){
				bean.field_maxnum="";
			}
			$("#field_maxnumArea").val(bean.field_maxnum);
			if(bean.default_value==null){
				bean.default_value="";
			}
			$("#default_valueArea").val(bean.default_value);
			
		}else 
			//修改的是多行文本（支持HTML）
			if(bean.field_type=='2'){
				
				//如果不是系统字段
				if (bean.is_sys == '1') {
					/*
					//不可用用的字段类型
					$("#field_type0").attr("disabled", "true");
					$("#field_type1").attr("disabled", "true");
					$("#field_type3").attr("disabled", "true");
					$("#field_type4").attr("disabled", "true");
					$("#field_type5").attr("disabled", "true");
					$("#field_type6").attr("disabled", "true");		
				}
				else {
				*/
					//不可用用的字段类型
					$("#field_type0").attr("disabled","true");
					$("#field_type1").attr("disabled","true");
					$("#field_type3").attr("disabled","true");
					$("#field_type4").attr("disabled","true");
					$("#field_type5").attr("disabled","true");
					$("#field_type6").attr("disabled", "true");
				}
				
				//根据类型表单的变化
				$("#textDiv").hide();
				$("#areaHtmlDiv").show();
				
				//填充表单
				$("#widthHtml").val(bean.width);
				$("#heightHtml").val(bean.height);
				if(bean.field_maxnum==null){
					bean.field_maxnum="";
				}
				$("#field_maxnumAreaHtml").val(bean.field_maxnum);
				if(bean.default_value==null){
					bean.default_value="";
				}
				$("#default_valueAreaHtml").val(bean.default_value);
				
			}else //修改的是选项
				if(bean.field_type=='3'){
					
					
					//如果不是系统字段
					if (bean.is_sys == '1') {
						/*
						//不可用用的字段类型
						$("#field_type0").attr("disabled", "true");
						$("#field_type1").attr("disabled", "true");
						$("#field_type2").attr("disabled", "true");
						$("#field_type4").attr("disabled", "true");
						$("#field_type5").attr("disabled", "true");
						$("#field_type6").attr("disabled", "true");	
						
						//选项内容和操作不可用
						$("#select_item").attr("disabled", "true");		
						$("#addurl").attr("disabled", "true");	
						$("#modifyurl").attr("disabled", "true");	
						$("#delurl").attr("disabled", "true");	
						
						//选项类型不可用
						$("#select_view0").attr("disabled", "true");
						$("#select_view1").attr("disabled", "true");
						$("#select_view3").attr("disabled", "true");
					}
					else {
					*/
						//不可用用的字段类型
						$("#field_type0").attr("disabled","true");
						$("#field_type1").attr("disabled","true");
						$("#field_type2").attr("disabled","true");
						$("#field_type4").attr("disabled","true");
						$("#field_type5").attr("disabled","true");
						$("#field_type6").attr("disabled", "true");
					}
									
					//根据类型表单的变化
					//alert("sss");
					$("#textDiv").hide();
					$("#selectDiv").show();
					
					//填充表单
					if(bean.select_item==null){
						bean.select_item="";
					}
					var intem = bean.select_item.split(",");
					if(intem!=null && intem!=''){
						for(var i=0;i<intem.length;i++){
						   $("<option value='"+intem[i]+"'>"+intem[i]+"</option>").appendTo("#select_item")
					    }
					}
					
					
					$(":radio[name='select_view'][value='"+bean.select_view+"']").attr("checked",true);
					if(bean.default_value==null){
						bean.default_value="";
					}
					$("#default_valueTextSelect").val(bean.default_value);
					
					//判断 选项数据来源 类型
					var data_type = bean.data_type;  
					if(data_type=='2'){//自定义数据
					     $(":radio[name='data_type'][value='"+data_type+"']").attr("checked",true);
						 $("#select_itemTR").hide();
						 $("#default_valueTextSelectTR").hide();
						 $("#data_type_selectTR").show();
						 $("#data_type_select").setSelectedValue(bean.data_type_id);
					}
					
					//lisp
					showSelecetTr();
					if(bean.data_type!='2'){
						setSelectDefault();
					}else{
						setDateList();
					}
				    if(bean.select_view=='0'){
						 $("#select0").setSelectedValue(bean.default_value);
					}else if(bean.select_view=='1'){
						 //alert(bean.default_value);
						 $("#select1").val(bean.default_value.split(","));
					}else if(bean.select_view=='2'){
						 //alert(bean.default_value);
						 $("#select2").setSelectedValue(bean.default_value);
					}else if(bean.select_view=='3'){
						 //alert(bean.default_value);
						 $("#select3").val(bean.default_value.split(","));
					}
				}else //修改的是数字
					if (bean.field_type == '4') {
						
						//如果不是系统字段
						if (bean.is_sys == '1') {
							/*
							//不可用用的字段类型
							$("#field_type0").attr("disabled", "true");
							$("#field_type1").attr("disabled", "true");
							$("#field_type2").attr("disabled", "true");
							$("#field_type3").attr("disabled", "true");
							$("#field_type5").attr("disabled", "true");	
							$("#field_type6").attr("disabled", "true");	
						}
						else {
						*/
							//不可用用的字段类型
							$("#field_type0").attr("disabled", "true");
							$("#field_type1").attr("disabled", "true");
							$("#field_type2").attr("disabled", "true");
							$("#field_type3").attr("disabled", "true");
							$("#field_type5").attr("disabled", "true");
							$("#field_type6").attr("disabled", "true");
						}		  
						
						//根据类型表单的变化
						$("#textDiv").hide();
						$("#numDiv").show()
						
						//填充表单
						$("#min_num").val(bean.min_num);
						$("#max_num").val(bean.max_num);
						
						if(bean.default_value==null){
							bean.default_value="";
						}
						$("#default_valueNum").val(bean.default_value);
					}else //修改的是日期
						if (bean.field_type == '5') {
							
					        //不可用用的字段类型
							$("#field_type0").attr("disabled", "true");
							$("#field_type1").attr("disabled", "true");
							$("#field_type2").attr("disabled", "true");
							$("#field_type3").attr("disabled", "true");
							$("#field_type4").attr("disabled", "true");
							$("#field_type6").attr("disabled", "true");
							
							//根据类型表单的变化
							$("#textDiv").hide();
							$("#timeDiv").show()
							
							//填充表单
							if(bean.default_value==null){
								bean.default_value="";
							}
							if(bean.default_value!=""){
								if(bean.default_value == 'nowTime'){
									$(":radio[name='timeSelectType'][value=2]").attr("checked",true);
								}else{
									$(":radio[name='timeSelectType'][value=1]").attr("checked",true);
									$("#time").val(bean.default_value);
						            /*  
									var dateTime = bean.default_value.split(" ");
									var date = dateTime[0];
									$("#emit_tiem").val(date);
									var HTime = dateTime[1].split(":");
									$("#emit_tiem_Hour").attr("value",HTime[0]);
									$("#emit_tiem_Minute").attr("value",HTime[1]);	
									*/
								}
								
							}
							
						}else//修改的是文件
							if (bean.field_type == '6') {
								
						        //不可用用的字段类型
								$("#field_type0").attr("disabled", "true");
								$("#field_type1").attr("disabled", "true");
								$("#field_type2").attr("disabled", "true");
								$("#field_type3").attr("disabled", "true");
								$("#field_type4").attr("disabled", "true");
								$("#field_type5").attr("disabled", "true");
								
								//根据类型表单的变化
								$("#textDiv").hide();
								$("#fileDiv").show()
								
								//填充表单
								if(bean.default_value==null){
									bean.default_value="";
								}
								if(bean.default_value!=""){
								    $("#file_type").val(bean.default_value);
							    }
								
							}
	
}

function showSelecetTr(){
	var type = $(":radio[name='select_view'][checked='true']").val();
	if (type == '0') {
	    $("#default_valueTextSelectTR0").show();
		$("#default_valueTextSelectTR1").hide();
		$("#default_valueTextSelectTR2").hide();
		$("#default_valueTextSelectTR3").hide();
	}else if (type == '1') {
	    $("#default_valueTextSelectTR1").show();
		$("#default_valueTextSelectTR0").hide();
		$("#default_valueTextSelectTR2").hide();
		$("#default_valueTextSelectTR3").hide();
	}else if (type == '2') {
	    $("#default_valueTextSelectTR2").show();
		$("#default_valueTextSelectTR0").hide();
		$("#default_valueTextSelectTR1").hide();
		$("#default_valueTextSelectTR3").hide();
	}else if (type == '3') {
	    $("#default_valueTextSelectTR3").show();
		$("#default_valueTextSelectTR0").hide();
		$("#default_valueTextSelectTR1").hide();
		$("#default_valueTextSelectTR2").hide();
	}
}
