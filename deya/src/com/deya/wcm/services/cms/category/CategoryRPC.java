package com.deya.wcm.services.cms.category;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.cms.category.CateClassBean;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.CategoryGetRegu;
import com.deya.wcm.bean.cms.category.CategoryModel;
import com.deya.wcm.bean.cms.category.CategoryReleBean;
import com.deya.wcm.bean.cms.category.CategorySharedBean;
import com.deya.wcm.bean.cms.category.SMCategoryBean;
import com.deya.wcm.bean.cms.category.SyncBean;
import com.deya.wcm.bean.cms.category.ZTCategoryBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.org.app.AppRPC;
import com.deya.wcm.services.page.PageManager;

public class CategoryRPC {
	/********************* 目录管理 开始 **********************************/
	/**
     * 根据分类方式ID得目录对象
     * @param int class_id
     * @return CategoryBean
     * */
	public static CategoryBean getCategoryBeanByClassID(int class_id)
	{
		return CategoryManager.getCategoryBeanByClassID(class_id);
	}
	
	/**
     * 根据cat_ID返回目录名称
     * @param int cat_id
     * @param String site_id
     * @return String
     * */
	public static String getCategoryCName(int cat_id,String site_id)
	{
		return CategoryUtil.getCategoryCName(cat_id, site_id);
	}
	
	/**
     * 得到服务的所有目录树
     * @param 
     * @return String
     * */
	public static String getAllFWTreeJSONStr()
	{
		return CategoryTreeUtil.getAllFWTreeJSONStr();
	}
	
	public static int getNewCategoryID()
	{
		return CategoryManager.getNewCategoryID();
	}	
	
	/**
     * 根据ID返回目录对象
     * @param int cat_id
     * @return CategoryBean
     * */
	public static CategoryBean getCategoryBean(int cat_id)
	{
		return CategoryManager.getCategoryBean(cat_id);
	}
	
	/**
     * 根据cat_id,site_id返回目录对象
     * @param int cat_id
     * @param String site_id
     * @return CategoryBean
     * */
	public static CategoryBean getCategoryBeanCatID(int cat_id,String site_id)
	{
		return CategoryManager.getCategoryBeanCatID(cat_id,site_id);
	}
	
	/**
     * 根据cat_ID,站点id得到该栏目的工作流程ID
     * @param int cat_id
     * @param String site_id
     * @return int
     * */
	public static int getWFIDByCatID(int cat_id,String site_id)
	{
		return CategoryManager.getWFIDByCatID(cat_id,site_id);
	}
	
	/**
     * 根据站点或节点ID,节点类型 获得根目录节总数,如专题的
     * @param String Map<String,String> m
     * @return String
     * */
	public static String getCategoryCountBySiteAndType(Map<String,String> m)
	{
		return CategoryManager.getCategoryCountBySiteAndType(m);
	}
	
	/**
     * 根据站点或节点ID,节点类型 获得根目录节点列表
     * @param String Map<String,String> m
     * @return List<CategoryBean>
     * */
	public static List<CategoryBean> getCategoryListBySiteAndType(Map<String,String> m)
	{
		return CategoryManager.getCategoryListBySiteAndType(m);
	}
	
	/**
     * 根据站点或节点ID获得根目录对象
     * @param String site_id
     * @return CategoryBean
     * */
	public static List<CategoryBean> getCategoryListBySiteID(String site_id)
	{
		return CategoryManager.getCategoryListBySiteID(site_id,0);
	}
	
	/**
     * 根据站点ID,人员ID得到有权限管理的目录树
     * @param String site_id
     * @param int user_id
     * @return String
     * */
	public static String getInfoCategoryTreeByUserID(String site_id,int uesr_id)
	{
		return CategoryTreeUtil.getInfoCategoryTreeByUserID(site_id,uesr_id,0);
	}
	
	/**
     * 根据站点ID,人员ID得到有权限管理的服务目录树
     * @param String site_id
     * @param int user_id
     * @return String
     * */
	public static String getFWCategoryTreeByUserID(String site_id,int uesr_id)
	{
		return CategoryTreeUtil.getInfoCategoryTreeByUserID(site_id,uesr_id,2);
	}
	
	/**
     * 根据站点ID得到有权限管理的服务目录树
     * @param String site_id
     * @param int user_id
     * @return String
     * */
	public static String getFWCategoryTreeByUserID(String site_id)
	{
		return CategoryTreeUtil.getInfoCategoryTreeByUserID(site_id,2);
	}
	
	/**
     * 根据站点或节点ID获得目录树
     * @param String site_id
     * @return String
     * */
	public static String getCategoryTreeBySiteID(String site_id)
	{
		return CategoryTreeUtil.getCategoryTreeBySiteID(site_id,0);
	}
	
	/**
     * 根据目录ID得目录树节字符串
     * @param int class_id
     * @param String site_id
     * @return String
     * */
	public static String getCategoryTreeByCategoryID(int cat_id,String site_id)
	{
		return CategoryTreeUtil.getCategoryTreeByCategoryID(cat_id,site_id);
	}
	
	/**
     * 根据分类方式ID得目录树节字符串
     * @param int class_id
     * @return String
     * */
	public static String getCategoryTreeByClassID(int class_id)
	{
		return CategoryTreeUtil.getCategoryTreeByClassID(class_id);
	}
	
	/**
     * 根据ID得到它的子级列表(deep+1)
     * @param int cat_id
     * @return List
     * */
	public static List<CategoryBean> getChildCategoryList(int cat_id,String site_id)
	{
		return CategoryManager.getChildCategoryList(cat_id,site_id);
	}
	
	/**
     * 添加目录的时候判断此父节点下是否已有想同的英文名称
     * @param int parent_id
     * @param String cat_ename
     * @param String app_id
     * @param String site_id
     * @return boolean
     * */
	public static boolean categoryIsExist(int parent_id,String cat_ename,String app_id,String site_id)
	{
		String page_path = CategoryUtil.getFoldePathByCategoryID(parent_id,app_id,site_id);
		if(PageManager.pageFileIsExist(page_path, cat_ename, site_id) == true || CategoryManager.categoryIsExist( parent_id, cat_ename,site_id) == true)
			return true;
		else
			return false;
	}
	
	/**
     * 根据cat_ID得到该级目录的保存路径(路径是由父节点的英文名称组合起来的)
     * @param String cat_ids
     * @param String app_id
     * @return String
     * */
	public static String getFoldePathByCategoryID(int cat_id,String app_id,String site_id)
	{
		return CategoryUtil.getFoldePathByCategoryID(cat_id,app_id, site_id);
	}
	
	/**
     * 插入目录信息(用于共享目录添加)
     * @param CategoryBean cgb
     * @param boolean is_share 是否添加的是共享目录
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertCategory(CategoryBean cgb,boolean is_share,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.insertCategory(cgb,is_share,stl);
		}else
			return false;
	}
	
	/**
     * 拷贝基础目录
     * @param int parent_id
     * @param String selected_cat_ids 要拷贝的节点ID
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean copyBasisCategory(int parent_id,int selected_cat_ids,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.copyBasisCategory(parent_id,selected_cat_ids,site_id,stl);
		}else
			return false;
	}
	
	/**
     * 拷贝共享目录
     * @param int parent_id
     * @param String selected_cat_ids 要拷贝的节点ID
     * @param String app_id
     * @param String site_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean copyShareCategory(int parent_id,String selected_cat_ids,String app_id,String site_id,HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.copyShareCategory(parent_id,selected_cat_ids,app_id,site_id,stl);
		}else
			return false;
	}
	
	/**
     * 修改目录信息
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean updateCategory(CategoryBean cgb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.updateCategory(cgb,stl);
		}else
			return false;
	}
	
	/**
     * 批量修改目录信息
     * @param Map
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean batchUpdateCategory(Map<String,String> cat_m,List<CategoryModel> l,String cat_id,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.batchUpdateCategory(cat_m, l, cat_id, site_id, stl);
		}else
			return false;
	}
	
	/**
     * 修改目录首页或列表页模板信息，用于专题工具设计后保存模板关联
     * @param Map
     * @return boolean
     * */
	public static boolean updateCategoryTemplate(Map<String,String> m)
	{
		return CategoryManager.updateCategoryTemplate(m);
	}
	
	/**
     * 修改目录关联列表页信息(主要用于公开指引,指南,年报)
     * @param String gkzy_list
     * @param String gkzn_list
     * @param String gknb_list
     * @return boolean
     * */
	public static boolean updateGKZNCateTemplate(String gkzy_list,String gkzn_list,String gknb_list)
	{
		return CategoryManager.updateGKZNCateTemplate(gkzy_list,gkzn_list,gknb_list);
	}
	
	/**
     * 修改目录归档状态
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean updateCategoryArchiveStatus(String ids,String is_archive,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.updateCategoryArchiveStatus(ids,is_archive,stl);
		}else
			return false;
	}
	
	/**
     * 保存排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortCategory(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.sortCategory(ids,stl);
		}else
			return false;
	}
	
	/**
     * 删除目录信息
     * @param String cat_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean deleteCategory(String cat_ids,String site_id,String app_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.deleteCategory(cat_ids,site_id,app_id,stl);
		}else
			return false;
	}
	
	/**
     * 目录关联分类操作
     * @param String class_id
     * @param String cat_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean releCategoryClass(String class_id,String cat_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.releCategoryClass(class_id,cat_id,stl);
		}else
			return false;
	}
	
	/**
     * 移动目录
     * @param String parent_id
     * @param String cat_ids
     * @return boolean
     * */
	public static boolean moveCategory(String parent_id,String cat_ids,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.moveCategory(parent_id,cat_ids,site_id,stl);
		}else
			return false;
	}
	
	/**
     * 根据分类方式ID得到它所含节点的个数
     * @param int class_id
     * @return int
     * */
	public static int getCategoryCountByClassID(int class_id)
	{
		return CategoryManager.getCategoryCountByClassID(class_id);
	}
	
	/**
     * 根据栏目ID和站点ID得到目录与内容模型对象列表
     * @param 
     * @return List
     * */
	public static List<ModelBean> getCateReleModelBeanList(String cat_id,String site_id)
	{
		return CategoryModelManager.getCateReleModelBeanList(cat_id,site_id);
	}
	
	/**
     * 根据栏目ID,站点ID,内容模型ID 得到模板ID
     * @param 
     * @return 
     * */
	public static String getTemplateID(String cat_id,String site_id,int model_id)
	{
		return CategoryModelManager.getTemplateID(cat_id, site_id, model_id);
	}
	
	/**
     * 删除关联信息
     * @param  String cat_id
     * @param  String site_id
     * @return boolean
     * */
	public static boolean deleteCategoryModel(String cat_id,String site_id)
	{
		return CategoryModelManager.deleteCategoryModel(cat_id, site_id);
	}
	
	
	/********************* 目录管理 结束 **********************************/
	
	/********************* 目录共享管理 开始 **********************************/
	/**
     * 修改基础目录列表页模板
     * @param String template_id
     * @return boolean
     * */
	public static boolean updateBaseCategoryTemplate(String templage_id)
	{
		return CategoryManager.updateBaseCategoryTemplate(templage_id);
	}
	
	/**
     * 得到基础目录列表页模板
     * @param 
     * @return String
     * */
	public static String getBaseCategoryTemplateListID()
	{
		return CategoryManager.getBaseCategoryTemplateListID();
	}
	
	/**
     * 根据站点ID，栏目ID，共享类型得到共享列表
     * @param String site_id
     * @param int cat_id
     * @param int shared_type
     * @return List
     * */
	public static List<CategorySharedBean> getCategorySharedListBySCS(String site_id,int cat_id,int shared_type)
	{
		return CategorySharedManager.getCategorySharedListBySCS(site_id,cat_id,shared_type);
	}
	
	/**
     * 根据站点ID得到所有允许共享给该站点信息的站点集合    
     * @param String t_site_id
     * @return List
     * */
	public static List<SiteBean> getAllowSharedSite(String t_site_id)
	{
		return CategorySharedManager.getAllowSharedSite(t_site_id);
	}
	
	public static String getAllowSharedSiteJSONStr(String t_site_id)
	{
		return CategorySharedManager.getAllowSharedSiteJSONStr(t_site_id);
	}

	public static String getGKBZHSiteJSONStr()
	{
		return CategorySharedManager.getGKBZHSiteJSONStr();
	}
	/**
     * 根据站点ID得到所有可以接收站点信息的站点集合    
     * @param String s_site_id
     * @return List
     * */
	public static List<SiteBean> getAllowReceiveSite(String t_site_id)
	{
		return CategorySharedManager.getAllowReceiveSite(t_site_id);
	}
	
	/**
     * 根据站点ID,得到它能提供给目标站点的栏目树
     * @param String s_site_id 提供共享目标的站点
     * @param String t_site_id 目标站点
     * @return String
     * */
	public static String getSharedCategoryTreeBySiteID(String s_site_id,String t_site_id)
	{
		return CategorySharedManager.getSharedCategoryTreeBySiteID(s_site_id, t_site_id);
	}
	
	/**
     * 根据站点ID,得到它能提供给目标站点的栏目树
     * @param String s_site_id 提供共享目标的站点
     * @param String t_site_id 目标站点,需要获取栏目的站点　
     * @return String
     * */
	public static String getReceiveCategoryTreeBySiteID(String s_site_id,String t_site_id)
	{
		return CategorySharedManager.getReceiveCategoryTreeBySiteID(s_site_id, t_site_id);
	}
	
	/**
     * 插入目录共享信息
     * @param CategorySharedBean csb
     * @return boolean
     * */
	public static boolean insertCategoryShared(CategorySharedBean csb)
	{
		return CategorySharedManager.insertCategoryShared(csb);
	}
	
	/**
     * 修改目录共享信息
     * @param CategorySharedBean csb
     * @return boolean
     * */
	public static boolean updateCategoryShared(CategorySharedBean csb)
	{
		return CategorySharedManager.updateCategoryShared(csb);
	}
	
	/********************* 目录共享管理 结束 **********************************/
	
	
	/********************* 分类方式 开始 **********************************/
	/**
	 * 根据分类英文名简单的栏目树列表
	 * @param String ename
	 * @return List
	 */
	public static SMCategoryBean getSMCategoryList(String ename)
	{
		return CateClassManager.getSMCategoryList(ename);
	}
	
	/**
	 * 得到分类类型为"基础目录"的分类方法列表
	 * @return
	 * 		分类方法列表
	 */
	public static List<CateClassBean> getBasisCateClassList()
	{
		return CateClassManager.getBasisCateClassList();
	}
	
	/**
	 * 得到所有的分类方法类别
	 */
	public static List<CateClassBean> getAllCateClassList()
	{
		return CateClassManager.getAllCateClassList();
	}
	
	/**
	 * 根据应用ID取得共享分类方法列表
	 * @param app_id	应用ID
	 * @return
	 * 		分类方法列表
	 */
	public static List<CateClassBean> getCateClassListByApp(String app_id)
	{
		return CateClassManager.getCateClassListByApp(app_id);
	}
	
	/**
	 * 根据分类方法ID取得分类方法对象
	 * @param id	分类方法ID
	 * @return		分类方法对象
	 */
	public static CateClassBean getCateClassBeanById(String id)
	{
		return CateClassManager.getCateClassBeanById(id);
	}
	
	/**
	 * 插入分类方法信息
	 * @param ccb	分类方法对象
	 * @param request
	 * @return
	 * 		true 成功|false 失败
	 */
	public static boolean insertCateClass(CateClassBean ccb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CateClassManager.insertCateClass(ccb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改分类方法信息
	 * @param ccb	分类方法对象
	 * @param request
	 * @return
	 * 		true 成功|false 失败
	 */
	public static boolean updateCateClass(CateClassBean ccb, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CateClassManager.updateCateClass(ccb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除分类方法信息
	 * @param ids	
	 * 		分类方法ID多个分类方法ID使用","分隔
	 * @param request
	 * @return
	 *  		true 成功|false 失败
	 */
	public static boolean deleteCateClass(String ids, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CateClassManager.deleteCateClass(ids, stl);
		}
		else
		{
			return false;
		}
	}
	
	public static List<AppBean> getAppBeanList()
	{
		return AppRPC.getAppList();
	}
	
	/********************* 分类方式 结束 **********************************/
	
	/********************* 同步管理 开始 **********************************/
	/**
	 * 根据站点ID,cat_id得到推送列表
	 * @param String s_site_id
	 * @param int cat_id
	 * @return	List
	 */
	public static List<SyncBean> getToInfoCategoryList(String s_site_id,int cat_id)
	{
		return SyncManager.getToInfoCategoryList(s_site_id,cat_id);
	}
	
	/**
	 * 根据站点ID得到同步列表
	 * @param String s_site_id
	 * @param int cat_id
	 * @return	List
	 */
	public static List<SyncBean> getSyncListBySiteCatID(String s_site_id,int cat_id)
	{
		return SyncManager.getSyncListBySiteCatID(s_site_id,cat_id);
	}
	
	/**
	 * 插入目录同步信息
	 * @param List<SyncBean> sList
	 * @param String s_cat_id
	 * @param String s_site_id
	 * @return		true 成功| false 失败
	 */
	public static boolean insertSync(List<SyncBean> sList,String s_cat_id,String s_site_id,String orientation)
	{
		return SyncManager.insertSync(sList,s_cat_id,s_site_id,orientation);
	}
	
	/********************* 同步管理 结束 **********************************/
	
	/********************* 同步关联 开始 **********************************/
	/**
	 * 根据目录ID得到关联列表
	 * @param int cat_id
	 * @return List
	 */
	public static List<CategoryReleBean> getCategoryReleUserListByCatID(int cat_id,String site_id)
	{
		return CategoryReleManager.getCategoryReleUserListByCatID(cat_id,site_id);
	}
	
	/**
	 * 插入目录与人员的关联
	 * @param List<CategoryReleBean> list
	 * @return boolean
	 */
	public static boolean insertCategoryReleUser(List<CategoryReleBean> list,int cat_id,String site_id)
	{
		return CategoryReleManager.insertCategoryReleUser(list, cat_id,site_id);
	}
	
	/**
	 * 插入目录与人员的关联(用于在站点用户管理中插入关联)
	 * @param String cat_ids
	 * @param int priv_type 类型0为用户　1为组
	 * @param int prv_id
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean insertCategoryReleUser(String cat_ids,int priv_type,int prv_id,String site_id)
	{
		return CategoryReleManager.insertCategoryReleUser(cat_ids, priv_type,prv_id,site_id);
	}
	
	/**
	 * 根据用户，或用户组得到它所能管理的栏目ID
	 * @param int cat_id
	 * @return List
	 */
	public static String getCategoryIDSByUser(int priv_type,int prv_id,String site_id)
	{
		return CategoryReleManager.getCategoryIDSByUser(priv_type,prv_id,site_id);
	}
	/********************* 同步关联 结束 **********************************/
	
	/********************* 专题分类 开始 **********************************/
	/**
     * 根据站点ID,人员ID得到有权限管理的专题分类树
     * @param String site_id
     * @param int user_id
     * @return String
     * */
	public static String getZTCategoryTreeJsonStr(String site_id,int uesr_id)
	{
		return ZTCategoryManager.getZTCategoryTreeJsonStr(site_id,uesr_id);
	}
	
	/**
     * 根据站点ID得到有权限管理的专题分类树
     * @param String site_id
     * @return String
     * */
	public static String getZTCategoryTreeJsonStr(String site_id)
	{
		return ZTCategoryManager.getZTCategoryTreeJsonStr(site_id);
	}
	
	/**
     * 根据站点或节点ID获得根目录节点列表
     * @param String site_id
     * @return List<CategoryBean>
     * */
	public static List<ZTCategoryBean> getZTCategoryList(String site_id)
	{
		return ZTCategoryManager.getZTCategoryList(site_id);
	}
	
	/**
     * 得到专题分类对象
     * @param int id
     * @return ZTCategoryBean
     * */
	public static ZTCategoryBean getZRCategoryBean(int id)
	{
		return ZTCategoryManager.getZRCategoryBean(id);
	}
	
	/**
     * 添加专题分类
     * @param ZTCategoryBean　zb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertZTCategory(ZTCategoryBean zb,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ZTCategoryManager.insertZTCategory(zb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
     * 修改专题分类
     * @param ZTCategoryBean　zb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateZTCategory(ZTCategoryBean zb,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ZTCategoryManager.updateZTCategory(zb, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
     * 保存专题分类排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean sortZTCategory(String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ZTCategoryManager.sortZTCategory(ids, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
     * 删除专题分类
     * @param int id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteZTCategory(int id,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return ZTCategoryManager.deleteZTCategory(id, stl);
		}
		else
		{
			return false;
		}
	}
	/********************* 专题分类 结束 **********************************/
	
	/********************* 分类与模板关联 开始 **********************************/
	/**
     * 根据栏目ID和站点ID得到目录与内容模型关联列表
     * @param 
     * @return List
     * */
	public static List<CategoryModel> getCategoryReleModelList(String cat_id,String site_id)
	{
		return CategoryModelManager.getCategoryReleModelList(cat_id, site_id);
	}
	
	/**
     * 插入关联信息
     * @param  CategoryModel
     * @param  String cat_id
     * @param  String site_id
     * @return boolean
     * */
	public static boolean insertCategoryModel(List<CategoryModel> l)
	{
		return CategoryModelManager.insertCategoryModel(l);
	}
	
	/**
     * 修改关联信息
     * @param  CategoryModel
     * @param  String cat_id
     * @param  String site_id
     * @return boolean
     * */
	public static boolean updateCategoryModel(List<CategoryModel> l,String cat_id,String site_id)
	{
		return CategoryModelManager.updateCategoryModel(l, cat_id, site_id);
	}
	/********************* 分类与模板关联 结束 **********************************/
	
	/********************* 目录获取规则 结束 **********************************/
	/**
	 * 根据目录节点ID得到所有规则
	 * @param int cata_id
	 * @return List
	 */
	public static List<CategoryGetRegu> getCatagoryReguList(int cat_id,String site_id)
	{
		return CategoryGetReguManager.getCatagoryReguList(cat_id, site_id);
	}
	
	/**
	 * 插入目录节点获取规则
	 * @param List<CategoryGetRegu> l
	 * @param int cat_id
	 * @param String site_id
	 * @return boolean
	 */
	public static String insertCategoryRegu(List<CategoryGetRegu> l,int cat_id,String site_id,String app_id)
	{
		return CategoryGetReguManager.insertCategoryRegu(l, cat_id, site_id, app_id);
	}
	/********************* 目录获取规则 结束 **********************************/
	
	public static void main(String[] args)
	{	
		
		System.out.println(getTemplateID("1879","HIWCMdemo",10));
		//System.out.println(RoleUserManager.isSiteManager(1+"","ggfw","ggfw"));
		
		//System.out.println(str.replaceAll("(.*?)(师市党办发.*[^<]+)(.*?)", "$2").replaceAll("(.*?)(<.*?$)", "$1"));
		
	}
}
