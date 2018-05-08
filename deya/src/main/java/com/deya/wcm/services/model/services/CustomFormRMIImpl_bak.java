package com.deya.wcm.services.model.services;
//一般常用的

import com.cicro.iresource.service.resourceService.dto.DataElementCollectionDTO;
import com.cicro.iresource.service.resourceService.dto.DataElementCollectionDataElementDTO;
import com.cicro.iresource.service.resourceService.dto.DataElementDTO;
import com.deya.util.DateUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.info.*;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.dao.cms.info.ModelUtilDAO;
import com.deya.wcm.dao.zwgk.info.GKInfoDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.rmi.ICustomFormRMI;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.CategoryUtil;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.InfoBaseRPC;
import com.deya.wcm.services.cms.info.ModelConfig;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.model.Fields;
import com.deya.wcm.services.org.user.UserManager;
import com.deya.wcm.services.system.formodel.ModelManager;
import com.deya.wcm.services.system.formodel.ModelRPC;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class CustomFormRMIImpl_bak extends UnicastRemoteObject implements ICustomFormRMI {

	static String info_state = JconfigUtilContainer.bashConfig().getProperty("info_state", "8", "wcm_zyk");
	
	public CustomFormRMIImpl_bak() throws RemoteException {
		super();
	}
	
	
	//是否开启了资源库接口
	public boolean isState(){
		String str = JconfigUtilContainer.bashConfig().getProperty("state", "off", "wcm_zyk");
		System.out.println("wcm_zyk--state--" + str);
		if(str.equals("off")){
			return false;
		}else{
			return true;
		}
	}

	//添加自定义表单
	public boolean addCustomForm(DataElementCollectionDTO dataElementCollection)
			throws RemoteException {
		try{
			
			if(!isState()){
				return true;
			}
			
			//插入内容模型 并 创建表
			ModelBean mb = getModelBeanByDataElementCollection(dataElementCollection);
			
			//向自定义表中添加字段
			List<DataElementCollectionDataElementDTO> listFields = dataElementCollection.getDataElements(); 
			for(DataElementCollectionDataElementDTO dataElementCollectionDataElement : listFields){
				addCustomForm(dataElementCollectionDataElement,mb);
			}
			
			
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	//通过dataElementCollection得到要添加的表的信息  并 创建表
	public static ModelBean getModelBeanByDataElementCollection(DataElementCollectionDTO dataElementCollection){
		try{
			//插入内容模型 并 创建表
			String model_ename = dataElementCollection.getDecEnName().toLowerCase();
			System.out.println(" getModelBeanByDataElementCollection model_ename : " + model_ename);
			String model_name = dataElementCollection.getDecName();
			System.out.println(" getModelBeanByDataElementCollection model_name : " + model_name);
			String table_name = dataElementCollection.getDecEnName().toLowerCase();
			System.out.println(" getModelBeanByDataElementCollection table_name : " + table_name);
			String template_list = "";
			String template_show = "";
			String app_id = "cms";
			String model_memo = dataElementCollection.getNote();
			if(model_memo==null){
				model_memo = "";
			}
			System.out.println(" getModelBeanByDataElementCollection model_memo : " + model_memo);
			int disabled = 0;
			String model_icon = "ico_custom";
			String add_page = "m_article_custom.jsp";
			String view_page = "";
			String model_type = "2";
			ModelBean mb = new ModelBean();
			mb.setModel_ename(model_ename);
			mb.setModel_name(model_name);
			mb.setTable_name(table_name);
			mb.setTemplate_list(template_list);
			mb.setTemplate_show(template_show);
			mb.setApp_id(app_id);
			mb.setModel_memo(model_memo);
			mb.setDisabled(disabled);
			mb.setModel_icon(model_icon);
			mb.setAdd_page(add_page); 
			mb.setView_page(view_page);
			mb.setModel_type(model_type);
			ModelManager.insertModel(mb, null);
			//得到添加信息的内容模型bean
			mb = ModelManager.getModelBeanByEName(model_ename);
			
			return mb;
		}catch (Exception e) {
			e.printStackTrace();
			return new ModelBean();
		}
	}
	
	
	//通过dataElementCollectionDataElement和ModelBean添加字段
	public static boolean addCustomForm(DataElementCollectionDataElementDTO dataElementCollectionDataElement,ModelBean mb){
		try{
			
			DataElementDTO dataElement = dataElementCollectionDataElement.getDataElement();
			String field_cnname = dataElement.getDeName();
			String field_enname = dataElement.getDeShortName().toLowerCase();
			String field_type = dataElementCollectionDataElement.getDeDataType();//数据类型
			System.out.println(field_cnname + " -- " + field_enname + "--- field_type --- " + field_type);
			String select_view = dataElementCollectionDataElement.getDeShow();//选项类型
			if(field_type.equals("string")){//单行文本
				field_type = "0";
				if(select_view.equals("selectElement")){//下拉框
					select_view = "0";
					field_type = "3";
				}else if(select_view.equals("radioElement")){
					select_view = "2";
					field_type = "3";
				}
			}else if(field_type.equals("text")){//多行文本（支持HTML）
				field_type = "2";
			}else if(field_type.equals("int") || field_type.equals("float")){//数字
				field_type = "4";
			}else if(field_type.equals("date")){//日期和时间
				field_type = "5";
			}else if(field_type.equals("file")){//附件
				field_type = "6";
			}
			String is_sys = "1";
			String is_null = "0";
			String is_display = "1";
			String field_text = "";
			String field_info = "";
			if(field_type.equals("0")){//单行文本
				field_info = "<fieldInfo><field_maxnum><![CDATA[]]></field_maxnum><field_maxlength><![CDATA[300]]></field_maxlength><validator><![CDATA[0]]></validator><default_value><![CDATA[]]></default_value></fieldInfo>";
			}else if(field_type.equals("2")){//多行文本（支持HTML）
				field_info = "<fieldInfo><width><![CDATA[620]]></width><height><![CDATA[300]]></height><field_maxnum><![CDATA[]]></field_maxnum><default_value><![CDATA[]]></default_value></fieldInfo>";
			}else if(field_type.equals("3")){//选项
				field_info = "<fieldInfo><data_type><![CDATA[]]></data_type><data_type_id><![CDATA[]]></data_type_id><select_item><![CDATA[]]></select_item><select_view><![CDATA["+select_view+"]]></select_view><default_value><![CDATA[]]></default_value></fieldInfo>";
			}else if(field_type.equals("4")){//数字
				field_info = "<fieldInfo><min_num><![CDATA[]]></min_num><max_num><![CDATA[]]></max_num><default_value><![CDATA[]]></default_value></fieldInfo>";
			}else if(field_type.equals("5")){//日期和时间
				field_info = "<fieldInfo><default_value><![CDATA[]]></default_value></fieldInfo>";
			}else if(field_type.equals("6")){//附件
				field_info = "<fieldInfo><default_value><![CDATA[]]></default_value></fieldInfo>";
			}
			
			//设置字段bean
			Fields fields = new Fields();
			fields.setFrom_id(0);
			fields.setModel_id(mb.getModel_id());
			fields.setField_sort(9999);
			fields.setAdd_time(DateUtil.getCurrentDateTime());
			fields.setField_cnname(field_cnname);
			fields.setField_enname(field_enname);
			fields.setField_type(field_type);
			fields.setIs_sys(is_sys);
			fields.setIs_null(is_null);
			fields.setIs_display(is_display);
			fields.setField_text(field_text);
			fields.setField_info(field_info);
			FormDao.addFields(fields);
			
			//在表中添加字段 --- start
			// 更新表结构
			Map hmParam = new HashMap();
			hmParam.put("tableName", mb.getTable_name());
			hmParam.put("fieldName", fields.getField_enname().toLowerCase());
			if(fields.getField_type().equals("0")){
				hmParam.put("fieldSize","4000"); 
				FormUtil.updateFieldDesc("addFieldVarchar", hmParam);
			}else if(fields.getField_type().equals("1") || fields.getField_type().equals("2")){
				FormUtil.updateFieldDesc("addFieldLongtext", hmParam);
			}else if(fields.getField_type().equals("3")){
				hmParam.put("fieldSize", "3000");
				FormUtil.updateFieldDesc("addFieldVarchar", hmParam);
			}else if(fields.getField_type().equals("4")){
				hmParam.put("fieldSize", "20");
				FormUtil.updateFieldDesc("addFieldBigint", hmParam);
			}else if(fields.getField_type().equals("5")){
				hmParam.put("fieldSize", "255");
				FormUtil.updateFieldDesc("addFieldVarchar", hmParam);
			}else if(fields.getField_type().equals("6")){
				hmParam.put("fieldSize", "3000"); 
				FormUtil.updateFieldDesc("addFieldVarchar", hmParam);
			}
			
			FormService.clearMapLsit();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			FormService.clearMapLsit();
			return false;
		}
	}
	
	//修改自定义表单
	public boolean updateCustomForm(DataElementCollectionDTO dataElementCollection)throws RemoteException{
		try{
			
			if(!isState()){
				return true;
			}
			
			String model_ename = dataElementCollection.getDecEnName().toLowerCase();
			String model_name = dataElementCollection.getDecName();
			String table_name = dataElementCollection.getDecEnName().toLowerCase();
			
			//得到原来的字段列表
			List<String> listFieldsOld = new ArrayList<String>();
			Map<String,String> mapFieldsOld = new HashMap<String,String>();
			ModelBean mb = ModelManager.getModelBeanByEName(model_ename);
			
			//如果为空则说明还没有创建该表信息  那么就添加
			if(mb==null){
				return addCustomForm(dataElementCollection);
			}
			
			Map map = new HashMap();
			map.put("model_id", mb.getModel_id());
			List<Fields> fieldsList= FormRPC.getFormFieldsListByModelIdN(map);
			for(Fields fields : fieldsList){
				listFieldsOld.add(fields.getField_enname());
				mapFieldsOld.put(fields.getField_enname(), String.valueOf(fields.getId()));
			}
			
			//得到修改后的字段列表
			List<String> listFieldsNew = new ArrayList<String>();
			List<DataElementCollectionDataElementDTO> listFields = dataElementCollection.getDataElements(); 
			for(DataElementCollectionDataElementDTO dataElementCollectionDataElement : listFields){
				DataElementDTO dataElement = dataElementCollectionDataElement.getDataElement();
				String field_enname = dataElement.getDeShortName();
				listFieldsNew.add(field_enname);
			}
			
			
			List<String> addList = new ArrayList(listFieldsNew);//要添加的字段ID
			List<String> deleteListTemp = new ArrayList<String>();//要删除的字段ID
			List<String> deleteList = new ArrayList(deleteListTemp);
			
			List<String> idsList = new ArrayList(listFieldsOld);
			List<String> cidsList = new ArrayList(listFieldsNew);
			for(String idsStr : idsList){
				if(idsStr==null || "".equals(idsStr)){
					continue;
				}
				if(!cidsList.contains(idsStr)){
					deleteList.add(idsStr);
				}
			} 
			addList.removeAll(idsList);
			
			System.out.println("CustomFormRMIImpl.java updateCustomForm addList :: " + addList);
			System.out.println("CustomFormRMIImpl.java updateCustomForm deleteList :: " + deleteList);
			
			
			//操作要删除或者要添加的字段
			for(DataElementCollectionDataElementDTO dataElementCollectionDataElement : listFields){
				DataElementDTO dataElement = dataElementCollectionDataElement.getDataElement();
				String field_enname = dataElement.getDeShortName();
				if(addList.contains(field_enname)){//添加字段
					addCustomForm(dataElementCollectionDataElement,mb);
				}
				//System.out.println("field_enname == " + field_enname);
			}
			
			for(String del_name : deleteList){//删除字段
				 String d_id = mapFieldsOld.get(del_name);//得到要删除字段的ID
				 //System.out.println("d_id == " + d_id);
				 //System.out.println("mb.getModel_id() == " + mb.getModel_id());
				 FormService.deleteFormFields(d_id, ""+mb.getModel_id());
			}
			
			FormService.clearMapLsit();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			FormService.clearMapLsit();
			return false;
		}
	}

	
	
	//删除自定义表
	public boolean deleteCustomForm(String ids,String enames){
		try{
			
			if(!isState()){
				return true;
			}
			
			//得到内容模型
			List<String> listEnames = Arrays.asList(enames.split(","));
			for(String ename : listEnames){
				ename = ename.toLowerCase();
				if(ename!=null && !ename.equals("")){
					ModelBean mb = ModelManager.getModelBeanByEName(ename);
					ModelManager.deleteModel(mb.getModel_id()+"", null);
				}
			}
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//添加自定义表信息
	public boolean addCustomInfo(String info)throws RemoteException{
		try{
			
			if(!isState()){
				return true;
			}
			
			 Map mapResult = getInfoBeanByJson(info);
			 //System.out.println(mapResult);
			 String dept_position = (String)mapResult.get("deptTreePath"); //部门的tree路径
			 System.out.println("deptTreePath === " + dept_position);
			 //通过 dept_potion 得到该信息要插入的站点列表 以,分割
			 //List<String> list = CmsIresourceCategoryUtil.getSiteIdByDeptTreePosition(dept_position);
			 List<String> list = new ArrayList<String>();
			 System.out.println("list.size() ---- " + list.size());
			 if(list.size()>0){
				 for(String site_id : list){
					 addPublicInfo(mapResult,site_id);
				 }
			 }else{
				 List<SiteBean> listSite = SiteManager.getSiteList();
				 System.out.println("listSite.size() ---- " + listSite.size());
				 for(SiteBean site : listSite){
					 String parent_id = site.getParent_id();
					 if(!parent_id.equals("0")){
						 String category_id = (String)mapResult.get("category_id");//栏目id
						 System.out.println(site.getSite_id() + " ---- " + category_id);
						 CategoryBean cb = CategoryManager.getCategoryBean(Integer.valueOf(category_id));
						 if(cb.getSite_id().equals(site.getSite_id())){
							 addPublicInfo(mapResult,site.getSite_id());
						 }
					 }
				 }
			 }
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将MAP中key值去掉dec
	 * @param Map
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static Map mapKeyValueDeleDesc(Map m)
	{
		if(m != null && m.size() > 0)
		{
			Map<String, Object> new_m = new HashMap<String, Object>();
			Iterator iter = m.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String)entry.getKey();
			    Object val = entry.getValue();
			    if(key.startsWith("dec")){
			    	key = key.substring(3, key.length());
			    }
			    new_m.put(key, val);
			} 
			return new_m;
		}
		else
			return m;
	}
	
	//公用添加信息和附件以及自定义信息的方法
	public static boolean addPublicInfo(Map mapResult,String site_id){
		try{
			 System.out.println("----addPublicInfo---- == " + mapResult);
			 String category_id = (String)mapResult.get("category_id");//栏目id
			 String table_name = (String)mapResult.get("table_name");//表名
			 String from_id = (String)mapResult.get("from_id");//资源库中的信息id
			 List<Map> listData = (List<Map>)mapResult.get("mapResult");//信息dataList
			 //信息Map Map里面是key：字段名字  value：字段值
	         Map listDataMap = (Map)mapResult.get("listDataMap");      //信息dataList 转换为 的 Map
	         
	         //去掉map中所有的dec
	         listDataMap = mapKeyValueDeleDesc(listDataMap);
	         
	         //信息的附件list
	         List<Map> listFiles = (List<Map>)mapResult.get("listFiles");      //信息的附件list
			 
	         //得到内容模型bean
	         ModelBean modelBean = ModelRPC.getModelBeanByEName(table_name);
	         //得到该内容模型下面的字段列表
	         Map map = new HashMap();
	         map.put("model_id", modelBean.getModel_id());
	         List<Fields> fieldsList = FormRPC.getFormFieldsListByModelIdN(map);
	         String title_str = "";
	         String time_str = "";
	         for(Fields fields : fieldsList){
	        	 if("title".equals(fields.getField_flag())){
	        		 title_str = fields.getField_enname();
	        	 }
	        	 if("publish_time".equals(fields.getField_flag2())){
	        		 time_str = fields.getField_enname();
	        	 }
	         }
	         if(title_str.equals("") || time_str.equals("")){
	        	 System.out.println(" Not set 'title' or 'publish_time' hearder , please set it!");
	        	 return true;
	         }
	         
	         if(modelBean==null){
	        	 return true;
	         }
	         
	         System.out.println("modelBean.getModel_id()========" + modelBean.getModel_id());
	         
	        
	         //设置信息主表内容
	         ArticleBean articleBean = new ArticleBean();
	         int id = InfoBaseRPC.getInfoId();
	         articleBean.setId(id);
	         articleBean.setInfo_id(id);
	         articleBean.setCat_id(Integer.valueOf(category_id));
			 articleBean.setModel_id(modelBean.getModel_id());
	         //articleBean.setModel_id(0);
	         articleBean.setTop_title("");
	         articleBean.setPre_title("");
	         articleBean.setTitle((String)listDataMap.get(title_str.toString()));
	         articleBean.setSubtitle("");
	         articleBean.setTitle_color("");
	         articleBean.setThumb_url("");
	         articleBean.setDescription("");
	         articleBean.setAuthor("");
	         articleBean.setEditor("");
	         articleBean.setSource("");
	         articleBean.setInfo_keywords("");
	         articleBean.setInfo_description("");
	         articleBean.setTags("");
	         articleBean.setContent_url("");
	         articleBean.setWf_id(0);
	         articleBean.setStep_id(100);
	         articleBean.setInfo_status(Integer.valueOf(info_state));
	         articleBean.setFinal_status(0);
	         articleBean.setWeight(60);
	         articleBean.setHits(0);
	         articleBean.setDay_hits(0);
	         articleBean.setWeek_hits(0);
	         articleBean.setMonth_hits(0);
	         articleBean.setLasthit_dtime("");
	         articleBean.setIs_allow_comment(0);
	         articleBean.setComment_num(0);
	         String time_str2 = (String)listDataMap.get(time_str.toString())+" 00:00:00";
	         articleBean.setReleased_dtime(time_str2);
	         articleBean.setInput_user(0);
	         articleBean.setInput_dtime(articleBean.getReleased_dtime());
	         articleBean.setModify_user(0);
	         articleBean.setModify_dtime("");
	         articleBean.setOpt_dtime(articleBean.getReleased_dtime());
	         articleBean.setUp_dtime("");
	         articleBean.setIs_auto_down(0);
	         articleBean.setDown_dtime("");
	         articleBean.setIs_host(0);
	         articleBean.setTitle_hashkey(0);
	         articleBean.setApp_id("cms");
//	         CategoryBean categoryBean = CategoryManager.getCategoryBean(articleBean.getCat_id());
//	         articleBean.setSite_id(categoryBean.getSite_id());
	         articleBean.setSite_id(site_id);
	         articleBean.setPage_count(0);
	         articleBean.setI_ver(0);
  	         articleBean.setIs_pic(0);
	         articleBean.setIs_am_tupage(0);
	         articleBean.setTupage_num(1000);
	         
	         
	         //添加自定义信息
	         listDataMap.put("id",articleBean.getInfo_id());
	         InfoCustomRPC.addInfoCuston(""+modelBean.getModel_id(),listDataMap);
	         
	         //删除关联表数据
			 WcmZykInfoDao.deleteWcminfo_zykinfoById(from_id,articleBean.getSite_id()); 
	         //添加信息关联表
	         WcmZykInfoDao.addWcminfo_zykinfo(from_id, ""+articleBean.getInfo_id(),articleBean.getSite_id());
	         
	         
	         //删除附件表
			 WcmZykInfoDao.deleteZykinfoFileById(""+articleBean.getInfo_id());
				
	         //添加该信息的附件表
	         for(Map mapF : listFiles){
	        	 mapF.put("info_id", articleBean.getInfo_id());
	        	 mapF.put("file_id", String.valueOf((Object)mapF.get("id")));
	        	 WcmZykInfoDao.addZykinfoFile(mapF);
	         }
	         
	         //添加主信息
	         insert(articleBean,"article_custom");
	         
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//公用修改信息和附件以及自定义信息的方法
	public static boolean updatePublicInfo(Map mapResult,String site_id){
		try{
			 String category_id = (String)mapResult.get("category_id");//栏目id
			 String table_name = (String)mapResult.get("table_name");//表名
			 String from_id = (String)mapResult.get("from_id");//资源库中的信息id
			 List<Map> listData = (List<Map>)mapResult.get("mapResult");//信息dataList
			 //信息Map Map里面是key：字段名字  value：字段值
	         Map listDataMap = (Map)mapResult.get("listDataMap");      //信息dataList 转换为 的 Map
	         
	         //去掉map中所有的dec
	         listDataMap = mapKeyValueDeleDesc(listDataMap);
	         
	         //信息的附件list
	         List<Map> listFiles = (List<Map>)mapResult.get("listFiles");      //信息的附件list
			 
	         
	         //得到内容模型bean
	         ModelBean modelBean = ModelRPC.getModelBeanByEName(table_name);
	         //得到该内容模型下面的字段列表
	         Map map = new HashMap();
	         map.put("model_id", modelBean.getModel_id());
	         List<Fields> fieldsList = FormRPC.getFormFieldsListByModelIdN(map);
	         String title_str = "";
	         String time_str = "";
	         for(Fields fields : fieldsList){
	        	 if("title".equals(fields.getField_flag())){
	        		 title_str = fields.getField_enname();
	        	 }
	        	 if("publish_time".equals(fields.getField_flag2())){
	        		 time_str = fields.getField_enname();
	        	 }
	         }
	         if(title_str.equals("") || time_str.equals("")){
	        	 System.out.println(" Not set 'title' or 'publish_time' hearder , please set it!");
	        	 return true;
	         }
	         
	         if(modelBean==null){
	        	 return true;
	         }
	         
	         //修改数据时 先读关联表  如果关联表不存在 则添加该信息 
	         String info_id = WcmZykInfoService.getWcminfo_zykinfoById(from_id,site_id);
	         if(info_id==null || "".equals(info_id)){ //如果关联表不存在 则添加该信息 
	        	return addPublicInfo(mapResult,site_id);
	         }else{//如果关联表存在
	        	 //如果关联表存在  读取主表信息  如果不存在 则添加 该信息
	        	 System.out.println("---updatePublicInfo----info_id::"+info_id);
	             InfoBean infoBean = InfoBaseManager.getInfoById(info_id);
	             if(infoBean==null || "".equals(infoBean.getInfo_id())){
	            	 return addPublicInfo(mapResult,site_id);
	             }
	         }
	         
	         //设置信息主表内容
	         ArticleBean articleBean = new ArticleBean();
	         int id = Integer.valueOf(info_id);
	         articleBean.setId(id);
	         articleBean.setInfo_id(id);
	         articleBean.setCat_id(Integer.valueOf(category_id));
			 articleBean.setModel_id(modelBean.getModel_id());
	         articleBean.setTop_title("");
	         articleBean.setPre_title("");
	         articleBean.setTitle((String)listDataMap.get(title_str.toString()));
	         articleBean.setSubtitle("");
	         articleBean.setTitle_color("");
	         articleBean.setThumb_url("");
	         articleBean.setDescription("");
	         articleBean.setAuthor("");
	         articleBean.setEditor("");
	         articleBean.setSource("");
	         articleBean.setInfo_keywords("");
	         articleBean.setInfo_description("");
	         articleBean.setTags("");
	         articleBean.setContent_url("");
	         articleBean.setWf_id(0);
	         articleBean.setStep_id(100);
	         articleBean.setInfo_status(Integer.valueOf(info_state));
	         articleBean.setFinal_status(0);
	         articleBean.setWeight(60);
	         articleBean.setHits(0);
	         articleBean.setDay_hits(0);
	         articleBean.setWeek_hits(0);
	         articleBean.setMonth_hits(0);
	         articleBean.setLasthit_dtime("");
	         articleBean.setIs_allow_comment(0);
	         articleBean.setComment_num(0);
	         String time_str2 = (String)listDataMap.get(time_str.toString())+" 00:00:00";
	         articleBean.setReleased_dtime(time_str2);
	         articleBean.setInput_user(0);
	         articleBean.setInput_dtime(articleBean.getReleased_dtime());
	         articleBean.setModify_user(0);
	         articleBean.setModify_dtime("");
	         articleBean.setOpt_dtime(articleBean.getReleased_dtime());
	         articleBean.setUp_dtime("");
	         articleBean.setIs_auto_down(0);
	         articleBean.setDown_dtime("");
	         articleBean.setIs_host(0);
	         articleBean.setTitle_hashkey(0);
	         articleBean.setApp_id("cms");
//	         CategoryBean categoryBean = CategoryManager.getCategoryBean(articleBean.getCat_id());
//	         articleBean.setSite_id(categoryBean.getSite_id());
	         articleBean.setSite_id(site_id);
	         articleBean.setPage_count(0);
	         articleBean.setI_ver(0);
  	         articleBean.setIs_pic(0);
	         articleBean.setIs_am_tupage(0);
	         articleBean.setTupage_num(1000);
	         
	         //判断自定义表中的信息是否存在 如果不存在就 添加自定义表信息  如果存在就修改自定义表信息
	         listDataMap.put("id",articleBean.getInfo_id());
	         Map mapTemp = InfoCustomRPC.getCustomInfoMap(modelBean.getModel_id()+"", articleBean.getInfo_id()+"");
	         if(mapTemp==null){//自定义表中不存在该信息
	        	 System.out.println("mapTemp-----add---");
	        	 InfoCustomRPC.addInfoCuston(""+modelBean.getModel_id(),listDataMap);
	         }else{
	        	 if(mapTemp.keySet().size()<=0){//自定义表中不存在该信息
	        		 System.out.println("mapTemp-----add-2--");
	        		 InfoCustomRPC.addInfoCuston(""+modelBean.getModel_id(),listDataMap);
	        	 }else{//自定义表中存在该信息 
	        		 //修改自定义信息
	        		 System.out.println("mapTemp-----update-2--");
	    	         //InfoCustomRPC.addInfoCuston(""+modelBean.getModel_id(),listDataMap);
	    	         InfoCustomRPC.updateInfoCuston(""+modelBean.getModel_id(),listDataMap);
	        	 }
	         } 

	         //添加信息关联表
	         //WcmZykInfoDao.addWcminfo_zykinfo(from_id, ""+articleBean.getInfo_id());
	         
	         //先删除该信息的附件表
	         WcmZykInfoDao.deleteZykinfoFileById(""+articleBean.getInfo_id());
	         //添加该信息的附件表
	         for(Map mapF : listFiles){
	        	 mapF.put("info_id", articleBean.getInfo_id());
	        	 mapF.put("file_id", (String)mapF.get("id"));
	        	 WcmZykInfoDao.addZykinfoFile(mapF);
	         }
	         
	         //修改主信息
	         //insert(articleBean,"article_custom");
	         update(articleBean,"article_custom");
	         
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public static boolean insert(Object ob, String model_name)
	{
		//System.out.println("ob---cat_id = " + ((Map)ob).get("cat_id"));
		SettingLogsBean stl = null;
		try{ 
				String info_status = "0";
				String site_id = "";
				String app_id = "";
				try {
					info_status = BeanUtils.getProperty(ob, "info_status");
					site_id = BeanUtils.getProperty(ob, "site_id");
					app_id = BeanUtils.getProperty(ob, "app_id");
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if("8".equals(info_status))
				{
					//如果需要发布，使用rmi调用
					return FileRmiFactory.insertInfo(InfoBaseManager.getInfoReleSiteID(site_id,app_id), ob, model_name, stl);
				}else				
					return ModelUtil.insert(ob, model_name, stl);
				
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			
	}
	
	/**
	 * 修改模型信息
	 * @param ob	模型信息对象
	 * @param model_name	模型信息名称
	 * @param request
	 * @return
	 */
	public static boolean update(Object ob, String model_name)
	{
		try{
			    System.out.println("ModelUtilRPC :: update :: start");
				String info_status = "0";
				String site_id = "";
				String app_id = "";
				SettingLogsBean stl = null;
			  try {
					info_status = BeanUtils.getProperty(ob, "info_status");
					site_id = BeanUtils.getProperty(ob, "site_id");
					app_id = BeanUtils.getProperty(ob, "app_id");
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if("8".equals(info_status))
				{
					//如果需要发布，使用rmi调用
					return FileRmiFactory.updateInfo(InfoBaseManager.getInfoReleSiteID(site_id,app_id), ob, model_name, stl);
				}else
					return ModelUtil.update(ob, model_name, stl);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
}
	
	//修改自定义表信息
	public boolean updateCustomInfo(String info)throws RemoteException{
		try{
			
			if(!isState()){
				return true;
			}
			
			 Map mapResult = getInfoBeanByJson(info);

			 //通过 dept_potion 得到该信息要插入的站点列表 以,分割
			 String dept_position = (String)mapResult.get("deptTreePath"); //部门的tree路径
			 //List<String> list = CmsIresourceCategoryUtil.getSiteIdByDeptTreePosition(dept_position);
			 List<String> list = new ArrayList<String>();
			 System.out.println("list.size() ---- " + list.size());
			 if(list.size()>0){
				 for(String site_id : list){
					 updatePublicInfo(mapResult,site_id);
				 }
			 }else{
				 List<SiteBean> listSite = SiteManager.getSiteList();
				 System.out.println("listSite.size() ---- " + listSite.size());
				 for(SiteBean site : listSite){
					 String parent_id = site.getParent_id();
					 if(!parent_id.equals("0")){
						 String category_id = (String)mapResult.get("category_id");//栏目id
						 System.out.println(site.getSite_id() + " ---- " + category_id);
						 CategoryBean cb = CategoryManager.getCategoryBean(Integer.valueOf(category_id));
						 if(cb.getSite_id().equals(site.getSite_id())){
							 updatePublicInfo(mapResult,site.getSite_id());
						 }
					 }
				 }
			 }
			 
			 return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	 
	//删除自定义表信息
	public boolean deleteCustomInfo(String ids)throws RemoteException{
		try{
			
			if(!isState()){
				return true;
			}
			
			//通过关联表得到该信息关联的站点
			List<String> list = Arrays.asList(ids.split(","));
			for(String id : list){
				if(id==null || "".equals(id)){
					continue;
				}
				System.out.println("id ---- " + id);
				List<Map> listSite = WcmZykInfoService.getWcminfo_zykinfosById(id+"");
				System.out.println("listSite.size() ---- " + listSite.size());
				for(Map map : listSite){
					String site_id = (String)MapManager.getValue(map, "site_id");
					String info_id = "";
					Object object = MapManager.getValue(map, "info_id");
					if(object instanceof java.math.BigDecimal){
						info_id = object.toString();
					}else{
						info_id = object.toString();
					}
					System.out.println("info_id:" + info_id + " --- site_id:" + site_id);
					deleteCustionInfoPublic(info_id,site_id);
				}
				
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	//info_id：wcm信息id
	public boolean deleteCustionInfoPublic(String info_id,String site_id){
		try{
			List<String> list = Arrays.asList(info_id.split(","));
			for(String id : list){
				if(id==null || "".equals(id)){
					continue;
				}  
				//得到主表中的信息info_id
				//String info_id = WcmZykInfoDao.getWcminfo_zykinfoById(id,site_id);
				//InfoBean infoBean = InfoBaseManager.getInfoById(info_id);
				
				//先执行 撤销信息的操作  之后再删除信息   --- 为了删除静态内容页和删除搜索的索引
				InfoBean infoBean = InfoBaseManager.getInfoById(id);
				List<InfoBean> listInfoBean = new ArrayList<InfoBean>();
				listInfoBean.add(infoBean);
				InfoBaseManager.updateInfoStatus(listInfoBean, "3",null);
				
				//删除自定义表
				//暂时不会删除自定义表中的信息 
				
				//删除主表
				Map map = new HashMap();
				map.put("info_ids", info_id);
				InfoDAO.deleteInfo(map);
				//删除附件表
				WcmZykInfoDao.deleteZykinfoFileById(info_id);
				
				//删除关联表数据
				WcmZykInfoDao.deleteWcminfo_zykinfoById(id,site_id);
				
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	//得到信息的bean 文件 以及一些添加信息时需要的信息
	public static Map getInfoBeanByJson(String info){
		try{
			
			Map mapResult = new HashMap();
			System.out.println("info==" + info);
			JSONObject joAll = JSONObject.fromObject(info); 
	        Iterator itAll = joAll.keys();
	        Map mapAll = new HashMap();
	        while(itAll.hasNext()){
	       	 String key = itAll.next().toString();
	       	 String value = joAll.getString(key);
	       	 mapAll.put(key, value);
	        }
	        //System.out.println(mapAll);
	        //栏目id
	        String category_id = (String)mapAll.get("treeId");   //栏目id
	        category_id = CmsIresourceCategoryUtil.getCmscatidByResourceid(category_id.trim());
	        
	        if(category_id.equals("")){
	        	System.out.println("--------cms_iresource_cat.properties is not set----------------");
	        	return mapResult;
	        }   
	                 
	        mapResult.put("category_id", category_id);
	        //表名
	        String table_name = (String)mapAll.get("mdcEnName").toString(); //表名
	        mapResult.put("table_name", table_name);
	        //部门treePosition
	        String deptTreePath = (String)mapAll.get("deptTreePath"); //部门treePosition
	        mapResult.put("deptTreePath", deptTreePath);
	        //资源库中的信息id
	        String from_id = (String)mapAll.get("id");           //资源库中的信息id
	        mapResult.put("from_id", from_id);
	        String jsData = (String)mapAll.get("data");
	        //System.out.println("jsData==" + jsData);
	        JSONArray joData = JSONArray.fromObject(jsData); 
	        Iterator itData = joData.iterator();
	        
	        //信息dataList
	        List<Map> listData = new ArrayList<Map>();           //信息dataList
	        mapResult.put("listData", listData);
	        while(itData.hasNext()){
	        	JSONObject jsonObject = (JSONObject)itData.next();
	        	Iterator itField = jsonObject.keys();
	           Map mapField  = new HashMap();
	           while(itField.hasNext()){
	          	    String key = itField.next().toString();
	          	    String value = jsonObject.getString(key);
	           	mapField.put(key, value);	
	           }
	           listData.add(mapField);
	        }
	        //信息Map Map里面是key：字段名字  value：字段值
	        Map listDataMap = new HashMap();                 //信息dataList 转换为 的 Map
	        mapResult.put("listDataMap", listDataMap);
	        for(Map map2 : listData){
	       	String key = (String)map2.get("enName");
	       	String value = (String)map2.get("value");
	       	listDataMap.put(key, value);
	        }
	        
	        
	        String jsFile = (String)mapAll.get("file");
	        //信息的附件list
	        List<Map> listFiles = new ArrayList<Map>();           //信息的附件list
	        mapResult.put("listFiles", listFiles);
	        JSONObject joFile = JSONObject.fromObject(jsFile); 
	        Iterator itFile = joFile.keys(); 
	        while(itFile.hasNext()){
	       	 String key = itFile.next().toString();
	       	 String jsonFile = joFile.getString(key);
	       	 JSONArray jsonFileAy = JSONArray.fromObject(jsonFile); 
	            Iterator itjsonFile = jsonFileAy.iterator();
	            while(itjsonFile.hasNext()){
	            	JSONObject jsonObject = (JSONObject)itjsonFile.next();
	            	Iterator itField = jsonObject.keys();
	               Map mapField  = new HashMap();
	               while(itField.hasNext()){
	              	    String key_1 = itField.next().toString();
	              	    String value = jsonObject.getString(key_1);
	               	mapField.put(key_1, value);
	               }
	               listFiles.add(mapField);
	            }
	        }
			
	        return mapResult;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}


	@Override
	public boolean addCustomForm(String info) throws RemoteException {
		try{
			
			//转换 json 为 Bean
			//DataElementCollectionDTO dataElementCollection = (DataElementCollectionDTO)JSONObject.toBean(JSONObject.fromObject(info),DataElementCollectionDTO.class);
			DataElementCollectionDTO dataElementCollection = new ObjectMapper().readValue(info, DataElementCollectionDTO.class);
			
			if(!isState()){
				return true;
			}
			
			//插入内容模型 并 创建表
			ModelBean mb = getModelBeanByDataElementCollection(dataElementCollection);
			
			//向自定义表中添加字段
			List<DataElementCollectionDataElementDTO> listFields = dataElementCollection.getDataElements(); 
			for(DataElementCollectionDataElementDTO dataElementCollectionDataElement : listFields){
				addCustomForm(dataElementCollectionDataElement,mb);
			}
			
			
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean updateCustomForm(String info) throws RemoteException {
    try{
		
    	//转换 json 为 Bean
		//DataElementCollectionDTO dataElementCollection = (DataElementCollectionDTO)JSONObject.toBean(JSONObject.fromObject(info),DataElementCollectionDTO.class);
		DataElementCollectionDTO dataElementCollection = new ObjectMapper().readValue(info, DataElementCollectionDTO.class);
    	
			if(!isState()){
				return true;
			}
			
			String model_ename = dataElementCollection.getDecEnName().toLowerCase();
			String model_name = dataElementCollection.getDecName();
			String table_name = dataElementCollection.getDecEnName().toLowerCase();
			
			//得到原来的字段列表
			List<String> listFieldsOld = new ArrayList<String>();
			Map<String,String> mapFieldsOld = new HashMap<String,String>();
			ModelBean mb = ModelManager.getModelBeanByEName(model_ename);
			
			//如果为空则说明还没有创建该表信息  那么就添加
			if(mb==null){
				return addCustomForm(dataElementCollection);
			}
			
			Map map = new HashMap();
			map.put("model_id", mb.getModel_id());
			List<Fields> fieldsList= FormRPC.getFormFieldsListByModelIdN(map);
			for(Fields fields : fieldsList){
				listFieldsOld.add(fields.getField_enname());
				mapFieldsOld.put(fields.getField_enname(), String.valueOf(fields.getId()));
			}
			
			//得到修改后的字段列表
			List<String> listFieldsNew = new ArrayList<String>();
			List<DataElementCollectionDataElementDTO> listFields = dataElementCollection.getDataElements(); 
			for(DataElementCollectionDataElementDTO dataElementCollectionDataElement : listFields){
				DataElementDTO dataElement = dataElementCollectionDataElement.getDataElement();
				String field_enname = dataElement.getDeShortName();
				listFieldsNew.add(field_enname);
			}
			
			
			List<String> addList = new ArrayList(listFieldsNew);//要添加的字段ID
			List<String> deleteListTemp = new ArrayList<String>();//要删除的字段ID
			List<String> deleteList = new ArrayList(deleteListTemp);
			
			List<String> idsList = new ArrayList(listFieldsOld);
			List<String> cidsList = new ArrayList(listFieldsNew);
			for(String idsStr : idsList){
				if(idsStr==null || "".equals(idsStr)){
					continue;
				}
				if(!cidsList.contains(idsStr)){
					deleteList.add(idsStr);
				}
			} 
			addList.removeAll(idsList);
			
			System.out.println("CustomFormRMIImpl.java updateCustomForm addList :: " + addList);
			System.out.println("CustomFormRMIImpl.java updateCustomForm deleteList :: " + deleteList);
			
			
			//操作要删除或者要添加的字段
			for(DataElementCollectionDataElementDTO dataElementCollectionDataElement : listFields){
				DataElementDTO dataElement = dataElementCollectionDataElement.getDataElement();
				String field_enname = dataElement.getDeShortName();
				if(addList.contains(field_enname)){//添加字段
					addCustomForm(dataElementCollectionDataElement,mb);
				}
				//System.out.println("field_enname == " + field_enname);
			}
			
			for(String del_name : deleteList){//删除字段
				 String d_id = mapFieldsOld.get(del_name);//得到要删除字段的ID
				 //System.out.println("d_id == " + d_id);
				 //System.out.println("mb.getModel_id() == " + mb.getModel_id());
				 FormService.deleteFormFields(d_id, ""+mb.getModel_id());
			}
			
			FormService.clearMapLsit();
			
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			FormService.clearMapLsit();
			return false;
		}
	}


	//在资源库中添加信息直接到 wcm 中的对应的数据模型中   from_id是资源库中的ID
	public boolean insertForm(Object ob, String model_name,String from_id) throws RemoteException {
		//传入的对象 ob  model_name  
		try{
			if(!isState()){
				return true;
			}
			String site_id = BeanUtils.getProperty(ob, "site_id");
			//先读关联表
			String info_id = WcmZykInfoService.getWcminfo_zykinfoById(from_id,site_id);
	        if(info_id!=null && !"".equals(info_id)){ //关联表存在 则不添加该信息
	        	System.out.println("WcmZykInfo --- id is exist");
	        	return false;
	        }
	        
			//通过 Input_user 得到 用户所在部门，  再通过部门ID得到 该部门关联的站点ID --- 李苏培加
			System.out.println("method--------------------insertForm" );
			String input_user = BeanUtils.getProperty(ob, "input_user");
			System.out.println("input_user-----" + input_user);
			String dept_id = UserManager.getDeptIDByUserID(input_user);
			System.out.println("dept_id-----" + dept_id);
			List<SiteBean> site_list = SiteManager.getSiteList();
			for(SiteBean siteBean : site_list){
				if((siteBean.getDept_id()+"").equals(dept_id)){
					System.out.println("siteBean.getSite_id()-----" + siteBean.getSite_id());
					if(!"HIWCMcgroup".equals(siteBean.getSite_id())){
						System.out.println("siteBean.getSite_id()---22--" + siteBean.getSite_id());
						BeanUtils.setProperty(ob, "site_id",siteBean.getSite_id());
						break;
					}
				}
			}
	        
			//1: ob = com.deya.wcm.bean.cms.info.ArticleBean    model_name = article    model_id=11  //文章类型
//			 ArticleBean articleBean = new ArticleBean();
//			 articleBean.setApp_id("cms"); //应用Id
//			 articleBean.setAuthor("系统管理员");//作者
//			 articleBean.setCat_id(1020); //栏目Id
//			 articleBean.setEditor("");//编辑
//			 articleBean.setInfo_content("信息内容");//信息内容
//			 articleBean.setDescription("内容摘要");//内容摘要	 
//			 articleBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 articleBean.setSite_id("");//站点id
//			 articleBean.setSource("政务门户演示网站");//来源
//			 articleBean.setThumb_url("");//缩略图
//			 articleBean.setTitle("信息标题");//标题 
//		     articleBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 articleBean.setTop_title("");//上标题
//			 articleBean.setSubtitle("");//副标题
			 if(model_name.endsWith("article")){
				 ArticleBean articleBean = (ArticleBean)ob;
				 articleBean.setPage_count(1);//默认的
				 articleBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 articleBean.setInfo_id(articleBean.getId());//信息ID
				 articleBean.setStep_id(100);
				 articleBean.setInfo_status(4);//0是草稿 4是待发
				 articleBean.setWeight(60);//默认权重是60
				 articleBean.setModel_id(11); 
				 String tags = InfoBaseRPC.getTagsForTitle(articleBean.getTitle());
				 articleBean.setTags(tags);
				 articleBean.setApp_id("cms"); //应用Id
				 articleBean.setInfo_content(CustomFormRMIImplUtil.replaceAllStr(articleBean.getInfo_content()));//转换内容中的路径
				 articleBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(articleBean.getThumb_url()));//转换缩略图中的路径
				 ob = articleBean;
			 } 
		//2: ob = com.deya.wcm.bean.cms.info.PicBean    model_name = pic    model_id=10  //组图类型
//			 PicBean picBean = new PicBean();
//			 picBean.setApp_id("cms"); //应用Id
//			 picBean.setAuthor("系统管理员");//作者
//			 picBean.setCat_id(1020); //栏目Id
//			 picBean.setDescription("内容摘要");//内容摘要	 
//			 picBean.setEditor("");//编辑
//			 picBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 picBean.setPic_content("信息内容");//信息内容
//			 picBean.setSite_id("");//站点id
//			 picBean.setSource("政务门户演示网站");//来源
//			 picBean.setThumb_url("");//缩略图
//			 picBean.setTitle("信息标题");//标题
//			 picBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 picBean.setTop_title("");//上标题
//			 picBean.setSubtitle("");//副标题
//			 //图片列表
//			 List<PicItemBean> item_list = new ArrayList<PicItemBean>();
//			 PicItemBean picItemBean1 = new PicItemBean();
//			 picItemBean1.setPic_title("aaa");//图片标题
//			 picItemBean1.setPic_note("aaaaaaaaaa");//图片描述
//			 picItemBean1.setPic_path("http://www.lspcs.com/cms.files/upload/CMSlisp/201302/201302280852033.jpg");//图片路径
//			 item_list.add(picItemBean1);
//			 PicItemBean picItemBean2 = new PicItemBean();
//			 picItemBean2.setPic_title("bbb");//图片标题
//			 picItemBean2.setPic_note("bbbbbbbbbbb");//图片描述
//			 picItemBean2.setPic_path("http://www.lspcs.com/cms.files/upload/CMSlisp/201302/201302280852033.jpg");//图片路径
//			 item_list.add(picItemBean2);
//			 picBean.setItem_list(item_list);
			 if(model_name.endsWith("pic")){
				 PicBean picBean = (PicBean)ob;
				 picBean.setPage_count(1);//默认的
				 picBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 picBean.setInfo_id(picBean.getId());//信息ID
				 picBean.setInfo_status(4);//0是草稿 4是待发
				 picBean.setWeight(60);//默认权重是60
				 picBean.setStep_id(100); 
				 picBean.setModel_id(10);
				 picBean.setIs_pic(1);//是否图片
				 String tags = InfoBaseRPC.getTagsForTitle(picBean.getTitle());
				 picBean.setTags(tags);
				 List<PicItemBean> item_list_temp = new ArrayList<PicItemBean>();
				 int i=1;
				 for(PicItemBean bean : picBean.getItem_list()){
					 bean.setInfo_id(picBean.getInfo_id());
					 if(i==1){
						 bean.setPic_content(CustomFormRMIImplUtil.replaceAllStr(picBean.getPic_content()));
					 }
					 bean.setPic_sort(i);
					 bean.setPic_path(CustomFormRMIImplUtil.replaceAllStr(bean.getPic_path()));//转换缩略图中的路径
					 i++;
					 item_list_temp.add(bean);
				 }
				 picBean.setItem_list(item_list_temp);
				 picBean.setApp_id("cms"); //应用Id
				 //picBean.setInfo_content(CustomFormRMIImplUtil.replaceAllStr(picBean.getInfo_content()));//转换内容中的路径
				 picBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(picBean.getThumb_url()));//转换缩略图中的路径
				 ob = picBean;
			 }
			 
			//3: ob = com.deya.wcm.bean.cms.info.InfoBean    model_name = link    model_id=12  //链接类型
//			 InfoBean infoBean = new InfoBean();
//			 infoBean.setApp_id("cms"); //应用Id
//			 infoBean.setAuthor("系统管理员");//作者
//			 infoBean.setCat_id(1020); //栏目Id
//			 infoBean.setContent_url("");//链接地址
//			 infoBean.setDescription("内容摘要");//内容摘要	
//			 infoBean.setEditor("");//编辑
//			 infoBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 infoBean.setSite_id("");//站点id
//			 infoBean.setSource("政务门户演示网站");//来源
//			 infoBean.setThumb_url("");//缩略图
//			 infoBean.setTitle("信息标题");//标题
//			 infoBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 infoBean.setTop_title("");//上标题
//			 infoBean.setSubtitle("");//副标题
			 if(model_name.endsWith("link")){
				 InfoBean infoBean = (InfoBean)ob;
				 infoBean.setPage_count(1);//默认的
				 infoBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 infoBean.setInfo_id(infoBean.getId());//信息ID
				 infoBean.setInfo_status(4);//0是草稿 4是待发
				 infoBean.setWeight(60);//默认权重是60
				 infoBean.setStep_id(100); 
				 infoBean.setModel_id(12);
				 String tags = InfoBaseRPC.getTagsForTitle(infoBean.getTitle());
				 infoBean.setTags(tags);
				 infoBean.setApp_id("cms"); //应用Id
				 infoBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(infoBean.getThumb_url()));//转换缩略图中的路径
				 ob = infoBean;
			 }
			 
			//4: ob = com.deya.wcm.bean.cms.info.VideoBean    model_name = video    model_id=13  //视频类型
//			 VideoBean videoBean = new VideoBean();
//			 videoBean.setApp_id("cms"); //应用Id
//			 videoBean.setAuthor("系统管理员");//作者
//			 videoBean.setCat_id(1020); //栏目Id
//			 videoBean.setContent_url("");//链接地址
//			 videoBean.setDescription("内容摘要");//内容摘要	
//			 videoBean.setEditor("");//编辑
//			 videoBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 videoBean.setSite_id("");//站点id
//			 videoBean.setSource("政务门户演示网站");//来源
//			 videoBean.setThumb_url("");//缩略图
//			 videoBean.setTitle("信息标题");//标题
//			 videoBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 videoBean.setTop_title("");//上标题
//			 videoBean.setSubtitle("");//副标题
//			 videoBean.setVideo_path("");//视频地址
//			 videoBean.setInfo_content("视频内容视频内容视频内容");//视频内容
			 if(model_name.endsWith("video")){
				 VideoBean videoBean = (VideoBean)ob;
				 videoBean.setPage_count(1);//默认的
				 videoBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 videoBean.setInfo_id(videoBean.getId());//信息ID
				 videoBean.setInfo_status(4);//0是草稿 4是待发
				 videoBean.setWeight(60);//默认权重是60
				 videoBean.setStep_id(100); 
				 videoBean.setModel_id(13);
				 String tags = InfoBaseRPC.getTagsForTitle(videoBean.getTitle());
				 videoBean.setTags(tags);
				 videoBean.setApp_id("cms"); //应用Id
				 videoBean.setInfo_content(CustomFormRMIImplUtil.replaceAllStr(videoBean.getInfo_content()));//转换内容中的路径
				 videoBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(videoBean.getThumb_url()));//转换缩略图中的路径
				 ob = videoBean;
			 }
			 
			//5: ob = com.deya.wcm.bean.cms.info.GKFtygsBean    model_name = gkftygs    model_id=14  //通用格式类型
//			 GKFtygsBean gkFtygsBean = new GKFtygsBean();
//			 gkFtygsBean.setEffect_dtime("2013-02-28");//生效日期
//			 gkFtygsBean.setAboli_dtime("2013-02-28");//废止日期
//			 gkFtygsBean.setAuthor("系统管理员");//作者
//			 gkFtygsBean.setCarrier_type("载体类型");//载体类型
//			 gkFtygsBean.setCat_id(1020); //栏目Id
//			 gkFtygsBean.setDescription("内容摘要");//内容摘要
//			 gkFtygsBean.setDoc_no("文号");//文号
//			 gkFtygsBean.setEditor("");//编辑
//			 gkFtygsBean.setGenerate_dtime("2013-02-28");//生成日期
//			 gkFtygsBean.setGk_content("信息内容");//信息内容
//			 gkFtygsBean.setGk_duty_dept("责任部门/科室");//责任部门/科室
//			 gkFtygsBean.setGk_format("html");//信息格式
//			 gkFtygsBean.setGk_index("A0300-010000-2013-000072");//索引号
//			 gkFtygsBean.setGk_input_dept("发布机构");//发布机构
//			 gkFtygsBean.setGk_proc("审核程序");//审核程序
//			 gkFtygsBean.setGk_range("面向社会");//公开范围
//			 gkFtygsBean.setGk_signer("签发人");//签发人
//			 gkFtygsBean.setGk_time_limit("长期公开");//公开时限
//			 gkFtygsBean.setGk_validity("有效");//信息有效性
//			 gkFtygsBean.setGk_way("政府网站");//公开形式
//			 gkFtygsBean.setGk_time_limit("长期公开");//公开时限
//			 gkFtygsBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 gkFtygsBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 gkFtygsBean.setLanguage("汉语");//语种
//			 gkFtygsBean.setPlace_key("位置关键词");//位置关键词
//			 gkFtygsBean.setServe_id(384);//服务对象分类ID
//			 gkFtygsBean.setServe_name("行政机关");//服务对象分类名称
//			 gkFtygsBean.setSite_id("GKfaw");//公开节点ID  ------ 要根据实际项目填写
//			 gkFtygsBean.setSource("政务门户演示网站");//来源
//			 gkFtygsBean.setTheme_id(370);//体裁分类ID
//			 gkFtygsBean.setTheme_name("命令");//体裁分类名称
//			 gkFtygsBean.setThumb_url("");//缩略图
//			 gkFtygsBean.setTitle("信息标题");//标题
//			 gkFtygsBean.setTop_title("");//上标题
//			 gkFtygsBean.setSubtitle("");//副标题
//			 gkFtygsBean.setTopic_id(404);//主题分类ID
//			 gkFtygsBean.setTopic_name("省政府");//主题分类名称
//			 //附件列表
//			 List<GKResFileBean> file_list = new ArrayList<GKResFileBean>();
//			 GKResFileBean gkResFileBean1 = new GKResFileBean();
//			 gkResFileBean1.setRes_name("aaa");//附件标题
//			 gkResFileBean1.setRes_url("http://www.lspcs.com/cms.files/upload/CMSlisp/201302/201302280852033.jpg");//图片路径
//			 file_list.add(gkResFileBean1);
//			 gkFtygsBean.setFile_list(file_list);
			 
			 if(model_name.endsWith("gkftygs")){
				 GKFtygsBean gkFtygsBean = (GKFtygsBean)ob;
				 gkFtygsBean.setPage_count(1);//默认的
				 gkFtygsBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 gkFtygsBean.setInfo_id(gkFtygsBean.getId());//信息ID
				 gkFtygsBean.setInfo_status(4);//0是草稿 4是待发
				 gkFtygsBean.setModel_id(14);
				 
				 gkFtygsBean.setWeight(60);//默认权重是60
				 gkFtygsBean.setStep_id(100); 
				 String tags = InfoBaseRPC.getTagsForTitle(gkFtygsBean.getTitle());
				 gkFtygsBean.setTags(tags);
				 gkFtygsBean.setTopic_key(tags);
				 gkFtygsBean.setApp_id("zwgk"); //应用Id
				 
				 List<GKResFileBean> file_list_temp = new ArrayList<GKResFileBean>();
				 int i=1;
				 for(GKResFileBean bean : gkFtygsBean.getFile_list()){
					 bean.setInfo_id(gkFtygsBean.getInfo_id());
					 bean.setSort_id(i);
					 bean.setRes_url(CustomFormRMIImplUtil.replaceAllStr(bean.getRes_url()));//转换附件中的路径
					 i++;
					 file_list_temp.add(bean);
				 }
				 gkFtygsBean.setFile_list(file_list_temp);
				 gkFtygsBean.setGk_content(CustomFormRMIImplUtil.replaceAllStr(gkFtygsBean.getGk_content()));//转换内容中的路径
				 gkFtygsBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(gkFtygsBean.getThumb_url()));//转换缩略图中的路径
				 ob = gkFtygsBean;
			 }
			 
			//6: ob = com.deya.wcm.bean.cms.info.GKFldcyBean    model_name = gkfldcy    model_id=16  //通用格式类型
//			 GKFldcyBean gkFldcyBean = new GKFldcyBean();
//			 gkFldcyBean.setEffect_dtime("2013-02-28");//生效日期
//			 gkFldcyBean.setAboli_dtime("2013-02-28");//废止日期
//			 gkFldcyBean.setAuthor("系统管理员");//作者
//			 gkFldcyBean.setCarrier_type("载体类型");//载体类型
//			 gkFldcyBean.setCat_id(1020); //栏目Id
//			 gkFldcyBean.setDescription("内容摘要");//内容摘要
//			 gkFldcyBean.setDoc_no("文号");//文号
//			 gkFldcyBean.setEditor("");//编辑
//			 gkFldcyBean.setGenerate_dtime("2013-02-28");//生成日期
//			 gkFldcyBean.setGk_content("信息内容");//信息内容
//			 gkFldcyBean.setGk_duty_dept("责任部门/科室");//责任部门/科室
//			 
//			 gkFldcyBean.setGk_dz("");//地址
//			 gkFldcyBean.setGk_email("");//EMAIl
//			 gkFldcyBean.setGk_grjl("");//个人简历
//			 gkFldcyBean.setGk_gzfg("");//工作分工
//			 gkFldcyBean.setGk_ldzw("");//领导职务
//			 gkFldcyBean.setGk_pic("http://www.st.gov.cn/rrr.jpg");//照片
//			 gkFldcyBean.setGk_tel("");//电话
//			 
//			 gkFldcyBean.setGk_format("html");//信息格式
//			 gkFldcyBean.setGk_index("A0300-010000-2013-000072");//索引号
//			 gkFldcyBean.setGk_input_dept("发布机构");//发布机构
//			 gkFldcyBean.setGk_proc("审核程序");//审核程序
//			 gkFldcyBean.setGk_range("面向社会");//公开范围
//			 gkFldcyBean.setGk_signer("签发人");//签发人
//			 gkFldcyBean.setGk_time_limit("长期公开");//公开时限
//			 gkFldcyBean.setGk_validity("有效");//信息有效性
//			 gkFldcyBean.setGk_way("政府网站");//公开形式
//			 gkFldcyBean.setGk_time_limit("长期公开");//公开时限
//			 gkFldcyBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 gkFldcyBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 gkFldcyBean.setLanguage("汉语");//语种
//			 gkFldcyBean.setPlace_key("位置关键词");//位置关键词
//			 gkFldcyBean.setServe_id(384);//服务对象分类ID
//			 gkFldcyBean.setServe_name("行政机关");//服务对象分类名称
//			 gkFldcyBean.setSite_id("GKfaw");//公开节点ID  ------ 要根据实际项目填写
//			 gkFldcyBean.setSource("政务门户演示网站");//来源
//			 gkFldcyBean.setTheme_id(370);//体裁分类ID
//			 gkFldcyBean.setTheme_name("命令");//体裁分类名称
//			 gkFldcyBean.setThumb_url("");//缩略图
//			 gkFldcyBean.setTitle("信息标题");//标题
//			 gkFldcyBean.setTop_title("");//上标题
//			 gkFldcyBean.setSubtitle("");//副标题
//			 gkFldcyBean.setTopic_id(404);//主题分类ID
//			 gkFldcyBean.setTopic_name("省政府");//主题分类名称
//			 //附件列表
//			 List<GKResFileBean> file_list = new ArrayList<GKResFileBean>();
//			 GKResFileBean gkResFileBean1 = new GKResFileBean();
//			 gkResFileBean1.setRes_name("aaa");//附件标题
//			 gkResFileBean1.setRes_url("http://www.lspcs.com/cms.files/upload/CMSlisp/201302/201302280852033.jpg");//图片路径
//			 file_list.add(gkResFileBean1);
//			 gkFldcyBean.setFile_list(file_list);
			 if(model_name.endsWith("gkfldcy")){
				 GKFldcyBean gkFldcyBean = (GKFldcyBean)ob;
				 gkFldcyBean.setPage_count(1);//默认的
				 gkFldcyBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 gkFldcyBean.setInfo_id(gkFldcyBean.getId());//信息ID
				 gkFldcyBean.setInfo_status(4);//0是草稿 4是待发
				 gkFldcyBean.setModel_id(16);
				 
				 gkFldcyBean.setWeight(60);//默认权重是60
				 gkFldcyBean.setStep_id(100); 
				 String tags = InfoBaseRPC.getTagsForTitle(gkFldcyBean.getTitle());
				 gkFldcyBean.setTags(tags);
				 gkFldcyBean.setTopic_key(tags);
				 gkFldcyBean.setApp_id("zwgk"); //应用Id
				 
				 List<GKResFileBean> file_list_temp = new ArrayList<GKResFileBean>();
				 int i=1;
				 for(GKResFileBean bean : gkFldcyBean.getFile_list()){
					 bean.setInfo_id(gkFldcyBean.getInfo_id());
					 bean.setSort_id(i);
					 bean.setRes_url(CustomFormRMIImplUtil.replaceAllStr(bean.getRes_url()));//转换附件中的路径
					 i++;
					 file_list_temp.add(bean);
				 }
				 gkFldcyBean.setFile_list(file_list_temp);
				 gkFldcyBean.setGk_content(CustomFormRMIImplUtil.replaceAllStr(gkFldcyBean.getGk_content()));//转换内容中的路径
				 gkFldcyBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(gkFldcyBean.getThumb_url()));//转换缩略图中的路径
				 ob = gkFldcyBean;
			 }
			 
				//7: ob = com.deya.wcm.bean.cms.info.GKFbsznBean    model_name = gkfbszn    model_id=20  //通用格式类型
//			 GKFbsznBean gkFbsznBean = new GKFbsznBean();
//			 gkFbsznBean.setEffect_dtime("2013-02-28");//生效日期
//			 gkFbsznBean.setAboli_dtime("2013-02-28");//废止日期
//			 gkFbsznBean.setAuthor("系统管理员");//作者
//			 gkFbsznBean.setCarrier_type("载体类型");//载体类型
//			 gkFbsznBean.setCat_id(1020); //栏目Id
//			 gkFbsznBean.setDescription("内容摘要");//内容摘要
//			 gkFbsznBean.setDoc_no("文号");//文号
//			 gkFbsznBean.setEditor("");//编辑
//			 gkFbsznBean.setGenerate_dtime("2013-02-28");//生成日期
//			 gkFbsznBean.setGk_duty_dept("责任部门/科室");//责任部门/科室
//			 
//			 gkFbsznBean.setGk_bgsj("");//办公时间及地址
//			 gkFbsznBean.setGk_bldx("");//办理对象及范围
//			 gkFbsznBean.setGk_bllc("");//办理流程
//			 gkFbsznBean.setGk_blshixian("");//办理时限
//			 gkFbsznBean.setGk_blsx("");//办理手续
//			 gkFbsznBean.setGk_bsjg("");//办事机构
//			 gkFbsznBean.setGk_cclx("");//乘车路线
//			 gkFbsznBean.setGk_fwlb("公共服务项目");//服务类别
//			 gkFbsznBean.setGk_gsfs("公示方式内容");//公示方式
//			 gkFbsznBean.setGk_jdjc("");//监督检查
//			 gkFbsznBean.setGk_jgwz("http://www.cicro.com");//机构网址
//			 gkFbsznBean.setGk_memo("");//备注
//			 gkFbsznBean.setGk_sfbz("");//收费标准
//			 gkFbsznBean.setGk_sfyj("");//收费依据
//			 gkFbsznBean.setGk_sxyj("");//事项依据
//			 gkFbsznBean.setGk_wsbl("");//网上办理
//			 gkFbsznBean.setGk_wsts("");//网上投诉
//			 gkFbsznBean.setGk_wszx("");//网上咨询
//			 gkFbsznBean.setGk_zrzj("");//责任追究
//			 gkFbsznBean.setGk_ztcx("");//状态查询
//			 gkFbsznBean.setGk_zxqd("");//咨询渠道
//			 
//			 gkFbsznBean.setGk_format("html");//信息格式
//			 gkFbsznBean.setGk_index("A0300-010000-2013-000072");//索引号
//			 gkFbsznBean.setGk_input_dept("发布机构");//发布机构
//			 gkFbsznBean.setGk_proc("审核程序");//审核程序
//			 gkFbsznBean.setGk_range("面向社会");//公开范围
//			 gkFbsznBean.setGk_signer("签发人");//签发人
//			 gkFbsznBean.setGk_time_limit("长期公开");//公开时限
//			 gkFbsznBean.setGk_validity("有效");//信息有效性
//			 gkFbsznBean.setGk_way("政府网站");//公开形式
//			 gkFbsznBean.setGk_time_limit("长期公开");//公开时限
//			 gkFbsznBean.setInput_dtime("2013-02-20 22:22:22");//添加时间
//			 gkFbsznBean.setInput_user(1);//添加用户ID -- 组织机构中的用户id
//			 gkFbsznBean.setLanguage("汉语");//语种
//			 gkFbsznBean.setPlace_key("位置关键词");//位置关键词
//			 gkFbsznBean.setServe_id(384);//服务对象分类ID
//			 gkFbsznBean.setServe_name("行政机关");//服务对象分类名称
//			 gkFbsznBean.setSite_id("GKfaw");//公开节点ID  ------ 要根据实际项目填写
//			 gkFbsznBean.setSource("政务门户演示网站");//来源
//			 gkFbsznBean.setTheme_id(370);//体裁分类ID
//			 gkFbsznBean.setTheme_name("命令");//体裁分类名称
//			 gkFbsznBean.setThumb_url("");//缩略图
//			 gkFbsznBean.setTitle("信息标题");//标题
//			 gkFbsznBean.setTop_title("");//上标题
//			 gkFbsznBean.setSubtitle("");//副标题
//			 gkFbsznBean.setTopic_id(404);//主题分类ID
//			 gkFbsznBean.setTopic_name("省政府");//主题分类名称
//			 //附件列表
//			 List<GKResFileBean> file_list = new ArrayList<GKResFileBean>();
//			 GKResFileBean gkResFileBean1 = new GKResFileBean();
//			 gkResFileBean1.setRes_name("aaa");//附件标题
//			 gkResFileBean1.setRes_url("http://www.lspcs.com/cms.files/upload/CMSlisp/201302/201302280852033.jpg");//图片路径
//			 file_list.add(gkResFileBean1);
//			 gkFbsznBean.setFile_list(file_list);
			 if(model_name.endsWith("gkfbszn")){
				 GKFbsznBean gkFbsznBean = (GKFbsznBean)ob;
				 gkFbsznBean.setPage_count(1);//默认的
				 gkFbsznBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 gkFbsznBean.setInfo_id(gkFbsznBean.getId());//信息ID
				 gkFbsznBean.setInfo_status(4);//0是草稿 4是待发
				 gkFbsznBean.setModel_id(20);
				 
				 gkFbsznBean.setWeight(60);//默认权重是60
				 gkFbsznBean.setStep_id(100); 
				 String tags = InfoBaseRPC.getTagsForTitle(gkFbsznBean.getTitle());
				 gkFbsznBean.setTags(tags);
				 gkFbsznBean.setTopic_key(tags);
				 gkFbsznBean.setApp_id("zwgk"); //应用Id
				 
				 List<GKResFileBean> file_list_temp = new ArrayList<GKResFileBean>();
				 int i=1;
				 for(GKResFileBean bean : gkFbsznBean.getFile_list()){
					 bean.setInfo_id(gkFbsznBean.getInfo_id());
					 bean.setSort_id(i);
					 bean.setRes_url(CustomFormRMIImplUtil.replaceAllStr(bean.getRes_url()));//转换附件中的路径
					 i++;
					 file_list_temp.add(bean);
				 }
				 gkFbsznBean.setFile_list(file_list_temp);
				 //gkFbsznBean.setGk_content(CustomFormRMIImplUtil.replaceAllStr(gkFbsznBean.getGk_content()));//转换内容中的路径
				 gkFbsznBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(gkFbsznBean.getThumb_url()));//转换缩略图中的路径
				 ob = gkFbsznBean;
			 }
			
			String temp_info_id = BeanUtils.getProperty(ob, "info_id");//得到信息ID
			
			Map<String, String> mp = ModelConfig.getModelMap(model_name);
			System.out.println("insert ---- model_name :: " + model_name);
			ModelUtil.setPageCountInObject(ob,model_name);
			String addSql = "";
			if(mp!=null){
				addSql = mp.get("AddSQL");
			}
			if(addModel(ob,addSql,model_name, null)){
				 //添加信息关联表
		         WcmZykInfoDao.addWcminfo_zykinfo(from_id, ""+temp_info_id,site_id);
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 添加相关模型，模型可以是文章模型，通用图组新闻，通用视频新闻，用户自定义字段
	 * 中的任何一个
	 * @param o	具体模型的Bean对象
	 * @param sqlName	模型对应的MyBatis配置文件中的ID
	 * @param String model_name
	 * @param stl
	 * @return	
	 */
	public static boolean addModel(Object o, String sqlName,String model_name, SettingLogsBean stl)
	{
		if(addInfo(o, stl))
		{
			if(model_name.toLowerCase().contains("gk"))
			{//如果英文名含有gk,表示是公开的内容模型，需要添加内容模型的公用表
				GKInfoDAO.insertGKInfo(o);
			}
			if(!"".equals(sqlName))
			{
				if(sqlName.equals("insert_info_pic"))
				{
					return ModelUtilDAO.insertPicInfo(o,model_name);
				}
				else
				{
					return DBManager.insert(sqlName, o);
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * add BaseInfomation Operate
	 * @param info
	 * @param stl
	 * @return boolean
	 */
	public static boolean addInfo(Object ob, SettingLogsBean stl){
		InfoBean info = (InfoBean)ob;
		if(info.getId() == 0){
			int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_TABLE_NAME);
			info.setId(id);
			info.setInfo_id(id);
		}
		if(info.getInput_dtime().equals("")){
			info.setInput_dtime(DateUtil.getCurrentDateTime());
		} 
		info.setReleased_dtime(info.getInput_dtime());
		changeInfoStatus(info);
		
		return InfoDAO.addInfo(info, stl);		
	}
	
	
	/**
	 * 信息添加时设置信息状态
	 * @param InfoBean info
	 * @return 
	 */
	public static void changeInfoStatus(InfoBean info)
	{
		if(info.getInfo_status() == 4){//终审通过(待发)状态也要更新步骤ID	
			info.setStep_id(100);
			//判断栏目是否允许审核通过后直接发布
			
			CategoryBean cb = CategoryManager.getCategoryBeanCatID(info.getCat_id(), info.getSite_id());
			
			if(cb!=null && cb.getIs_wf_publish() == 1)
			{//可以直接发布，状态设置为8
				info.setInfo_status(8);
				if(info.getReleased_dtime() == null || "".equals(info.getReleased_dtime()))
					info.setReleased_dtime(DateUtil.getCurrentDateTime());
			}
		}
		if(info.getInfo_status() == 8){
			if(info.getReleased_dtime() == null || "".equals(info.getReleased_dtime()))
				info.setReleased_dtime(DateUtil.getCurrentDateTime());
			info.setStep_id(100);
		}		
		if(info.getContent_url() == null || "".equals(info.getContent_url()))
			info.setContent_url(CategoryUtil.getFoldePathByCategoryID(info.getCat_id(),info.getApp_id(),info.getSite_id())+info.getInfo_id()+".htm");
	}

	
	//在资源库中修改信息直接到 wcm 中的对应的数据模型中 
	public boolean updateForm(Object ob, String model_name,String from_id) throws RemoteException {
		try{
			if(!isState()){
				return true;
			}
			//先读关联表
			String site_id = BeanUtils.getProperty(ob, "site_id");
			String info_id = WcmZykInfoService.getWcminfo_zykinfoById(from_id,site_id);
	        if(info_id==null || "".equals(info_id)){ //如果关联表不存在 则添加该信息
	        	 System.out.println("WcmZykInfo -- id is not esist!");
	             return insertForm(ob, model_name,from_id); 
	        }
			String app_id = "cms";
			//String site_id = "";
			//得到要修改的信息ID
			//String info_id = "0";//---------------信息ID
			InfoBean infoBean = InfoBaseManager.getInfoById(info_id);
			if(model_name.endsWith("article")){
				 ArticleBean articleBean = (ArticleBean)ob;
				 //articleBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 //articleBean.setInfo_id(articleBean.getId());//信息ID
				 String tags = InfoBaseRPC.getTagsForTitle(articleBean.getTitle());
				 articleBean.setTags(tags);
				 app_id = articleBean.getApp_id(); //应用Id
				 site_id = articleBean.getSite_id();//站点id
				 if(infoBean==null){
					 info_id = articleBean.getInfo_id()+""; 
					 infoBean = InfoBaseManager.getInfoById(info_id);
				 } 
				 articleBean.setInfo_id(Integer.parseInt(info_id));
				 articleBean.setSite_id(site_id);
				 articleBean.setPage_count(1);//默认的
				 articleBean.setStep_id(100);
				 articleBean.setWeight(60);//默认权重是60
				 articleBean.setModel_id(11);
				 articleBean.setCat_id(infoBean.getCat_id());
				 articleBean.setApp_id(infoBean.getApp_id());
				 if("8".equals(infoBean.getInfo_status()+"")){//已发布
					 articleBean.setInfo_status(8);//0是草稿 4是待发
				 }else{//待发
					 articleBean.setInfo_status(4);//0是草稿 4是待发
				 }
				 System.out.println("articleBean.getInfo_status()--" + articleBean.getInfo_status());
				 articleBean.setInfo_content(CustomFormRMIImplUtil.replaceAllStr(articleBean.getInfo_content()));//转换内容中的路径
				 articleBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(articleBean.getThumb_url()));//转换缩略图中的路径
				 ob = articleBean;
			}  
			if(model_name.endsWith("pic")){
				PicBean picBean = (PicBean)ob;
				 //articleBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 //articleBean.setInfo_id(articleBean.getId());//信息ID
				 String tags = InfoBaseRPC.getTagsForTitle(picBean.getTitle());
				 picBean.setTags(tags);
				 app_id = picBean.getApp_id(); //应用Id
				 site_id = picBean.getSite_id();//站点id
				 if(infoBean==null){
					 info_id = picBean.getInfo_id()+""; 
					 infoBean = InfoBaseManager.getInfoById(info_id);
				 }
				 picBean.setInfo_id(Integer.parseInt(info_id));
				 picBean.setSite_id(site_id);
				 picBean.setPage_count(1);//默认的
				 picBean.setStep_id(100);
				 picBean.setWeight(60);//默认权重是60
				 picBean.setModel_id(10); 
				 picBean.setCat_id(infoBean.getCat_id());
				 picBean.setApp_id(infoBean.getApp_id());
				 if("8".equals(infoBean.getInfo_status())){//已发布
					 picBean.setInfo_status(8);//0是草稿 4是待发
				 }else{//待发
					 picBean.setInfo_status(4);//0是草稿 4是待发
				 }
				 System.out.println("articleBean.getInfo_status()--" + picBean.getInfo_status());
				 List<PicItemBean> item_list_temp = new ArrayList<PicItemBean>();
				 int i=1;
				 for(PicItemBean bean : picBean.getItem_list()){
					 bean.setInfo_id(picBean.getInfo_id());
					 if(i==1){
						 bean.setPic_content(CustomFormRMIImplUtil.replaceAllStr(picBean.getPic_content()));
					 }
					 bean.setPic_sort(i);
					 picBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(picBean.getThumb_url()));//转换缩略图中的路径
					 i++;
					 item_list_temp.add(bean);
				 }
				 picBean.setItem_list(item_list_temp);
				 ob = picBean;
			}
			if(model_name.endsWith("link")){
				InfoBean infoBean2 = (InfoBean)ob;
				 //articleBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 //articleBean.setInfo_id(articleBean.getId());//信息ID
				 String tags = InfoBaseRPC.getTagsForTitle(infoBean2.getTitle());
				 infoBean2.setTags(tags);
				 app_id = infoBean2.getApp_id(); //应用Id
				 site_id = infoBean2.getSite_id();//站点id
				 if(infoBean==null){
					 info_id = infoBean2.getInfo_id()+""; 
					 infoBean = InfoBaseManager.getInfoById(info_id);
				 } 
				 infoBean2.setInfo_id(Integer.parseInt(info_id));
				 infoBean2.setSite_id(site_id);
				 infoBean2.setPage_count(1);//默认的
				 infoBean2.setStep_id(100);
				 infoBean2.setWeight(60);//默认权重是60
				 infoBean2.setModel_id(12);
				 infoBean2.setCat_id(infoBean.getCat_id());
				 infoBean2.setApp_id(infoBean.getApp_id());
				 if("8".equals(infoBean.getInfo_status()+"")){//已发布
					 infoBean2.setInfo_status(8);//0是草稿 4是待发
				 }else{//待发
					 infoBean2.setInfo_status(4);//0是草稿 4是待发
				 }
				 System.out.println("articleBean.getInfo_status()--" + infoBean2.getInfo_status());
				 infoBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(infoBean.getThumb_url()));//转换缩略图中的路径
				 ob = infoBean2;
			} 
			if(model_name.endsWith("video")){ 
				 VideoBean videoBean = (VideoBean)ob;
				 //articleBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 //articleBean.setInfo_id(articleBean.getId());//信息ID
				 String tags = InfoBaseRPC.getTagsForTitle(videoBean.getTitle());
				 videoBean.setTags(tags);
				 app_id = videoBean.getApp_id(); //应用Id
				 site_id = videoBean.getSite_id();//站点id
				 if(infoBean==null){
					 info_id = videoBean.getInfo_id()+""; 
					 infoBean = InfoBaseManager.getInfoById(info_id);
				 } 
				 videoBean.setInfo_id(Integer.parseInt(info_id));
				 videoBean.setSite_id(site_id);
				 videoBean.setPage_count(1);//默认的
				 videoBean.setStep_id(100);
				 videoBean.setWeight(60);//默认权重是60
				 videoBean.setModel_id(13);
				 videoBean.setCat_id(infoBean.getCat_id());
				 videoBean.setApp_id(infoBean.getApp_id());
				 if("8".equals(infoBean.getInfo_status()+"")){//已发布
					 videoBean.setInfo_status(8);//0是草稿 4是待发
				 }else{//待发
					 videoBean.setInfo_status(4);//0是草稿 4是待发
				 }
				 System.out.println("articleBean.getInfo_status()--" + videoBean.getInfo_status());
				 videoBean.setInfo_content(CustomFormRMIImplUtil.replaceAllStr(videoBean.getInfo_content()));//转换内容中的路径
				 videoBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(videoBean.getThumb_url()));//转换缩略图中的路径
				 ob = videoBean;
			} 
			if(model_name.endsWith("gkftygs")){ 
				GKFtygsBean gkFtygsBean = (GKFtygsBean)ob;
				 //articleBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 //articleBean.setInfo_id(articleBean.getId());//信息ID
				 String tags = InfoBaseRPC.getTagsForTitle(gkFtygsBean.getTitle());
				 gkFtygsBean.setTags(tags);
				 gkFtygsBean.setTopic_key(tags);
				 app_id = gkFtygsBean.getApp_id(); //应用Id
				 site_id = gkFtygsBean.getSite_id();//站点id
				 if(infoBean==null){
					 info_id = gkFtygsBean.getInfo_id()+""; 
					 infoBean = InfoBaseManager.getInfoById(info_id);
				 } 
				 gkFtygsBean.setInfo_id(Integer.parseInt(info_id));
				 gkFtygsBean.setSite_id(site_id);
				 gkFtygsBean.setPage_count(1);//默认的
				 gkFtygsBean.setStep_id(100);
				 gkFtygsBean.setWeight(60);//默认权重是60
				 gkFtygsBean.setModel_id(14);
				 gkFtygsBean.setCat_id(infoBean.getCat_id());
				 gkFtygsBean.setApp_id(infoBean.getApp_id());
				 if("8".equals(infoBean.getInfo_status()+"")){//已发布
					 gkFtygsBean.setInfo_status(8);//0是草稿 4是待发
				 }else{//待发
					 gkFtygsBean.setInfo_status(4);//0是草稿 4是待发
				 }
				 //附件---开始
				 List<GKResFileBean> file_list_temp = new ArrayList<GKResFileBean>();
				 int i=1;
				 for(GKResFileBean bean : gkFtygsBean.getFile_list()){
					 bean.setInfo_id(gkFtygsBean.getInfo_id());
					 bean.setSort_id(i);
					 bean.setRes_url(CustomFormRMIImplUtil.replaceAllStr(bean.getRes_url()));//转换附件中的路径
					 i++;
					 file_list_temp.add(bean);
				 }
				 gkFtygsBean.setFile_list(file_list_temp);
				 //附件---结束
				 System.out.println("articleBean.getInfo_status()--" + gkFtygsBean.getInfo_status());
				 gkFtygsBean.setGk_content(CustomFormRMIImplUtil.replaceAllStr(gkFtygsBean.getGk_content()));//转换内容中的路径
				 gkFtygsBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(gkFtygsBean.getThumb_url()));//转换缩略图中的路径
				 ob = gkFtygsBean;
			}  
			if(model_name.endsWith("gkfldcy")){ 
				 GKFldcyBean gkFldcyBean = (GKFldcyBean)ob;
				 //articleBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 //articleBean.setInfo_id(articleBean.getId());//信息ID
				 String tags = InfoBaseRPC.getTagsForTitle(gkFldcyBean.getTitle());
				 gkFldcyBean.setTags(tags);
				 gkFldcyBean.setTopic_key(tags);
				 app_id = gkFldcyBean.getApp_id(); //应用Id
				 site_id = gkFldcyBean.getSite_id();//站点id
				 if(infoBean==null){
					 info_id = gkFldcyBean.getInfo_id()+""; 
					 infoBean = InfoBaseManager.getInfoById(info_id);
				 } 
				 gkFldcyBean.setInfo_id(Integer.parseInt(info_id));
				 gkFldcyBean.setSite_id(site_id);
				 gkFldcyBean.setPage_count(1);//默认的
				 gkFldcyBean.setStep_id(100);
				 gkFldcyBean.setWeight(60);//默认权重是60
				 gkFldcyBean.setModel_id(16);
				 gkFldcyBean.setCat_id(infoBean.getCat_id());
				 gkFldcyBean.setApp_id(infoBean.getApp_id());
				 if("8".equals(infoBean.getInfo_status()+"")){//已发布
					 gkFldcyBean.setInfo_status(8);//0是草稿 4是待发
				 }else{//待发
					 gkFldcyBean.setInfo_status(4);//0是草稿 4是待发
				 }
				 //附件---开始
				 List<GKResFileBean> file_list_temp = new ArrayList<GKResFileBean>();
				 int i=1;
				 for(GKResFileBean bean : gkFldcyBean.getFile_list()){
					 bean.setInfo_id(gkFldcyBean.getInfo_id());
					 bean.setSort_id(i);
					 bean.setRes_url(CustomFormRMIImplUtil.replaceAllStr(bean.getRes_url()));//转换附件中的路径
					 i++;
					 file_list_temp.add(bean);
				 }
				 gkFldcyBean.setFile_list(file_list_temp);
				 //附件---结束
				 System.out.println("articleBean.getInfo_status()--" + gkFldcyBean.getInfo_status());
				 gkFldcyBean.setGk_content(CustomFormRMIImplUtil.replaceAllStr(gkFldcyBean.getGk_content()));//转换内容中的路径
				 gkFldcyBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(gkFldcyBean.getThumb_url()));//转换缩略图中的路径
				 ob = gkFldcyBean;
			}
			if(model_name.endsWith("gkfbszn")){ 
				  GKFbsznBean gkFbsznBean = (GKFbsznBean)ob;
				 //articleBean.setId(InfoBaseRPC.getInfoId());//信息ID
				 //articleBean.setInfo_id(articleBean.getId());//信息ID
				 String tags = InfoBaseRPC.getTagsForTitle(gkFbsznBean.getTitle());
				 gkFbsznBean.setTags(tags);
				 gkFbsznBean.setTopic_key(tags);
				 app_id = gkFbsznBean.getApp_id(); //应用Id
				 site_id = gkFbsznBean.getSite_id();//站点id
				 if(infoBean==null){
					 info_id = gkFbsznBean.getInfo_id()+""; 
					 infoBean = InfoBaseManager.getInfoById(info_id);
				 } 
				 gkFbsznBean.setInfo_id(Integer.parseInt(info_id));
				 gkFbsznBean.setSite_id(site_id);
				 gkFbsznBean.setPage_count(1);//默认的
				 gkFbsznBean.setStep_id(100);
				 gkFbsznBean.setWeight(60);//默认权重是60
				 gkFbsznBean.setModel_id(20);
				 gkFbsznBean.setCat_id(infoBean.getCat_id());
				 gkFbsznBean.setApp_id(infoBean.getApp_id());
				 if("8".equals(infoBean.getInfo_status()+"")){//已发布
					 gkFbsznBean.setInfo_status(8);//0是草稿 4是待发
				 }else{//待发
					 gkFbsznBean.setInfo_status(4);//0是草稿 4是待发
				 }
				 //附件---开始
				 List<GKResFileBean> file_list_temp = new ArrayList<GKResFileBean>();
				 int i=1;
				 for(GKResFileBean bean : gkFbsznBean.getFile_list()){
					 bean.setInfo_id(gkFbsznBean.getInfo_id());
					 bean.setSort_id(i);
					 bean.setRes_url(CustomFormRMIImplUtil.replaceAllStr(bean.getRes_url()));//转换附件中的路径
					 i++;
					 file_list_temp.add(bean);
				 }
				 gkFbsznBean.setFile_list(file_list_temp);
				 //附件---结束
				 System.out.println("articleBean.getInfo_status()--" + gkFbsznBean.getInfo_status());
				 gkFbsznBean.setThumb_url(CustomFormRMIImplUtil.replaceAllStr(gkFbsznBean.getThumb_url()));//转换缩略图中的路径
				 ob = gkFbsznBean;
			}
			Map<String, String> mp = ModelConfig.getModelMap(model_name);
			System.out.println("ModelUtilRPC :: update :: start---" + infoBean.getInfo_status());
			if("8".equals((infoBean.getInfo_status()+"").trim()))
			{   
				System.out.println("ModelUtilRPC :: info_status :: start3---" + BeanUtils.getProperty(ob, "info_status"));
				System.out.println("ModelUtilRPC :: site_id :: start3---" + BeanUtils.getProperty(ob, "site_id"));
				//如果需要发布，使用rmi调用
				return CustomFormRMIImplUtil.updateInfo(InfoBaseManager.getInfoReleSiteID(site_id,app_id), ob, model_name, null);
			}else
				return CustomFormRMIImplUtil.update(ob, model_name, null);
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//在资源库中删除信息直接到 wcm 中的对应的数据模型中  list 中是要删除的信息id
	public boolean deleteForm(List<String> list,String site_id) throws RemoteException{
		try{
			if(!isState()){
				return true;
			}
			if(list.size()<0){
				return true;
			}
			
			for(String id : list){
				if(id!=null && !"".equals(id)){
					
					String info_id = WcmZykInfoService.getWcminfo_zykinfoById(id,site_id);
			        if(info_id==null || "".equals(info_id)){ //如果关联表不存在
			            continue;
			        }
					InfoBean infoBean = InfoBaseManager.getInfoById(info_id);
					if(infoBean==null){
						continue;
					}
					List<InfoBean> infoList = new ArrayList<InfoBean>();
					infoList.add(infoBean);
					FileRmiFactory.deleteInfo(InfoBaseManager.getInfoReleSiteID(infoList.get(0).getSite_id(),infoList.get(0).getApp_id()), infoList, null);
					//删除关联表数据 
					WcmZykInfoDao.deleteWcminfo_zykinfoById(id,site_id);
				}
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}
