function addInfoDataCustom()
{	
	var bean = BeanUtil.getCopy(GKFtygsBean);	
	$("#info_article_table").autoBind(bean);
	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}

	var st = $(":radio[name='info_status'][checked]").val();
	bean.info_status = (st == null ? "2" : st);
	infoNextId = 0;
	if(infoIdGoble == 0){
		infoNextId = InfoBaseRPC.getInfoId();
	}else{
		infoNextId = infoIdGoble;
	}
	no1 = infoNextId;
	bean.id = infoNextId;
	bean.info_id = infoNextId;

	
	setCateClassToBean(bean);
	if(setGKNumHandlForInsert(bean) == false)
	{
		return;
	}
	
	//添加自定义表信息-----开始
	var con_m_cus = new Map();
	var cusBean = new Map();
	con_m_cus.put("model_id", model);
	var beanList_cus = jsonrpc.FormRPC.getFormFieldsListByModelIdN(con_m_cus);
	beanList_cus = List.toJSList(beanList_cus);//把list转成JS的List对象
	for(var i=0;i<beanList_cus.size();i++){
		var field_enname = beanList_cus.get(i).field_enname;
		var field_type = beanList_cus.get(i).field_type;
		if(field_type=="0"){//单行文本
			cusBean.put(field_enname, $("#"+field_enname).val());
		}else if(field_type=='1'){//多行文本
			cusBean.put(field_enname,$("#"+field_enname).val());
		}else if(field_type=='2'){//多行文本带html
			cusBean.put(field_enname,KE.html(field_enname));
		}else if(field_type=='3'){//选项
			var select_view = beanList_cus.get(i).select_view;
			var value_f = '';
			if(select_view=='0'){//选项是单选下拉框内容
				value_f = $("#"+field_enname).val();
			}else if(select_view=='1'){//选项是多选下拉框
				value_f = $("#"+field_enname).val();
			}else if(select_view=='2'){//选项是单选按钮
				value_f = $(":radio[name='"+field_enname+"'][checked]").val();
			}else if(select_view=='3'){//选项是复选框
				  var n = 0;
				  value_f = "";
				  var len = $(":checkbox[name='"+field_enname+"'][checked]").length;
				  $(":checkbox[name='"+field_enname+"'][checked]").each(function(){ 
				  	   n++;
				  	   if(n==len){
				  		 value_f += $(this).val();
					   }else{
						   value_f += $(this).val() + ",";
					   }
			       });
			}
			cusBean.put(field_enname,value_f);
		}else if(field_type=='4'){//数字
			cusBean.put(field_enname, $("#"+field_enname).val());
		}else if(field_type=='5'){//时间和日期
			cusBean.put(field_enname, $("#"+field_enname).val());
		}else if(field_type=='6'){//文件
			cusBean.put(field_enname, $("#"+field_enname).val());
		}
	}
	//alert(cusBean); 
	cusBean.put("id",infoNextId);
	jsonrpc.InfoCustomRPC.addInfoCuston(model,cusBean);
	//添加自定义表信息-----结束
	
	publicSaveInfoEventCustom(bean,cusBean,"gk_article_custom","insert");
	
}


//修改
function updateInfoDataCustom()
{

	var bean = BeanUtil.getCopy(GKFtygsBean);	
	$("#info_article_table").autoBind(bean);

	if(!standard_checkInputInfo("info_article_table"))
	{
		return;
	}
  
	bean.info_status = defaultBean.info_status;
	
	//修改自定义表信息-----开始
	var con_m_cus = new Map();
	var cusBean = new Map();
	con_m_cus.put("model_id", model);
	var beanList_cus = jsonrpc.FormRPC.getFormFieldsListByModelIdN(con_m_cus);
	beanList_cus = List.toJSList(beanList_cus);//把list转成JS的List对象
	for(var i=0;i<beanList_cus.size();i++){
		var field_enname = beanList_cus.get(i).field_enname;
		var field_type = beanList_cus.get(i).field_type;
		if(field_type=="0"){//单行文本
			cusBean.put(field_enname, $("#"+field_enname).val());
		}else if(field_type=='1'){//多行文本
			cusBean.put(field_enname,$("#"+field_enname).val());
		}else if(field_type=='2'){//多行文本带html
			cusBean.put(field_enname,KE.html(field_enname));
		}else if(field_type=='3'){//选项
			var select_view = beanList_cus.get(i).select_view;
			var value_f = '';
			if(select_view=='0'){//选项是单选下拉框内容
				value_f = $("#"+field_enname).val();
			}else if(select_view=='1'){//选项是多选下拉框
				value_f = $("#"+field_enname).val();
			}else if(select_view=='2'){//选项是单选按钮
				value_f = $(":radio[name='"+field_enname+"'][checked]").val();
			}else if(select_view=='3'){//选项是复选框
				  var n = 0;
				  value_f = "";
				  var len = $(":checkbox[name='"+field_enname+"'][checked]").length;
				  $(":checkbox[name='"+field_enname+"'][checked]").each(function(){ 
				  	   n++;
				  	   if(n==len){
				  		 value_f += $(this).val();
					   }else{
						   value_f += $(this).val() + ",";
					   }
			       });
			}
			cusBean.put(field_enname,value_f);
		}else if(field_type=='4'){//数字
			cusBean.put(field_enname, $("#"+field_enname).val());
		}else if(field_type=='5'){//时间和日期
			cusBean.put(field_enname, $("#"+field_enname).val());
		}else if(field_type=='6'){//文件
			cusBean.put(field_enname, $("#"+field_enname).val());
		}
	} 
	//alert(cusBean);
	cusBean.put("id",defaultBean.info_id);
	//alert(defaultBean.info_id);
	jsonrpc.InfoCustomRPC.updateInfoCuston(model,cusBean);
	//修改自定义表信息-----结束
	
	
	
	setCateClassToBean(bean);
	//修改的时候不用判断索引码，重新生成的话，如果有重复的不会修改原索引码
	
	publicSaveInfoEventCustom(bean,cusBean,"gk_article_custom","update");	
	
	
}

//公用保存处理事件 --- 自定义数据专用
function publicSaveInfoEventCustom(bean,cusBean,model_ename,save_type)
{
	var bool = false;
	bean.title = bean.title.replace(/\"/g,"＂");
	if(app_id == "zwgk")
	{
		bean.tags = bean.topic_key;
	}
	if(save_type == "update")
	{
		if(update_dtTimeIsCorrect(bean) == false)
		{
			return;
		}	
		//alert(111);
		bool = ModelUtilRPC.update(bean,model_ename);
		//alert(222);
		if(bool == true)
		{//修改引用此信息的信息
			if(model_ename != "link") 
				jsonrpc.InfoCustomRPC.updateQuoteInfoCustomGk(bean,cusBean,model_ename);
		}
	}
	else
	{
		if(Add_dtTimeIsCorrect(bean) == false)
		{
			return;
		}
		//alert("111");
		bool = ModelUtilRPC.insert(bean,model_ename);
		//alert("222");
	}

	if(bool)
	{	//草稿状态下不发布到其它栏目
		if(bean.info_status != 0)
		{
			insertOtherInfos(bean,model_ename);
		}
		top.msgAlert("信息"+WCMLang.Add_success);			
		gotoListPage(bean);
	}
	else
	{
		top.msgWargin("信息"+WCMLang.Add_fail);
	}
}
