package com.deya.wcm.services.cms.category;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.cms.category.CateClassBean;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.CategoryModel;
import com.deya.wcm.bean.cms.category.SMCategoryBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.cms.category.CategoryDAO;
import com.deya.wcm.dao.cms.category.CategoryGetReguDAO;
import com.deya.wcm.dao.cms.category.CategoryModelDAO;
import com.deya.wcm.dao.system.design.DesignDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.workflow.WorkFlowManager;
import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.role.RoleUserManager;

import java.util.*;

/**
 *  目录管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class CategoryManager implements ISyncCatch{
//public class CategoryManager{
	public static TreeMap<Integer,CategoryBean> category_m = new TreeMap<Integer,CategoryBean>();
	
	protected static int ROOT_ID = 0;
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		category_m.clear();
		try{
			List<CategoryBean> cate_list = CategoryDAO.getAllCategoryList();		
			if(cate_list != null && cate_list.size() > 0)
			{
				for(int i=0;i<cate_list.size();i++)
				{
					category_m.put(cate_list.get(i).getId(), cate_list.get(i));
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		CateClassManager.clearSMCateMap();
		CategoryTreeUtil.reloadMap();
	}
	
	/**
	 * 初始加载目录信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadCategory()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.cms.category.CategoryManager");
		//reloadCatchHandl();
	}
	
	public static int getNewCategoryID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CATEGORY_TABLE_NAME);	
	}
	
	
	
	/**
     * 根据ID返回目录对象
     * @param int id
     * @return CategoryBean
     * */
	public static CategoryBean getCategoryBean(int id)
	{
		if(category_m.containsKey(id))
		{
			return (CategoryBean)category_m.get(id);
		}else
		{
			CategoryBean cgb = CategoryDAO.getCategoryBean(id+"");
			if(cgb != null)
			{
				category_m.put(id, cgb);
				return cgb;
			}
			else
				return null;
		}
	}
	
	/**
	 * 根据多个目录节点得到它的栏目名称
	 * @param int cata_id
	 * @return List
	 */
	public static String getMutilCategoryNames(String cat_ids,String site_id)
	{
		String names = "";
		if(cat_ids != null && !"".equals(cat_ids))
		{
			String[] tempA = cat_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				CategoryBean cb = getCategoryBeanCatID(Integer.parseInt(tempA[i]), site_id);
				if(cb != null)
					names += ","+cb.getCat_cname();
			}
			if(names != null && !"".equals(names))
				names = names.substring(1);
		}
		return names;
	}
	
	/**
     * 根据cat_ID返回目录对象
     * @param int cat_id
     * @param String site_id
     * @return CategoryBean
     * */
	public static CategoryBean getCategoryBeanCatID(int cat_id,String site_id)
	{
		if(cat_id == 0)
		{
			CategoryBean cgb = new CategoryBean();
			cgb.setCat_id(cat_id);
			cgb.setSite_id(site_id);
			cgb.setCat_position("$0$");
			return cgb;
		}
		Set<Integer> set = category_m.keySet();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);				
			if(cgb.getCat_id() == cat_id && site_id.equals(cgb.getSite_id()))
				return cgb;
		}
		return null;
	}

	/**
	 * 根据cat_ID返回目录对象
	 * @param int cat_id
	 * @return CategoryBean
	 * */
	public static CategoryBean getCategoryBeanCatID(int cat_id)
	{
		if(cat_id == 0)
		{
			CategoryBean cgb = new CategoryBean();
			cgb.setCat_id(cat_id);
			cgb.setCat_position("$0$");
			return cgb;
		}
		Set<Integer> set = category_m.keySet();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);
			if(cgb.getCat_id() == cat_id)
				return cgb;
		}
		return null;
	}
	
	/**
     * 根据cat_ID,站点id得到该栏目的工作流程ID
     * @param int cat_id
     * @param String site_id
     * @return int
     * */
	public static int getWFIDByCatID(int cat_id,String site_id)
	{
		CategoryBean cgb = getCategoryBeanCatID(cat_id,site_id);
		if(cgb != null)
			return cgb.getWf_id();
		else
			return 0;
	}
		
	/**
     * 根据站点或节点ID获得根目录节点列表
     * @param String site_id
     * @param String cat_type 节点类型　0普通节点　1专题节点　2服务节点
     * @return List<CategoryBean>
     * */
	public static List<CategoryBean> getCategoryListBySiteID(String site_id,int cat_type)
	{
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		Set<Integer> set = category_m.keySet();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);			
			if(site_id.equals(cgb.getSite_id()) && cgb.getParent_id() == ROOT_ID && cgb.getCat_type() == cat_type && cgb.getIs_archive() == 0)
			{
				list.add(cgb);
			}
		}
		if(list != null && list.size() > 0)
			Collections.sort(list,new CategoryComparator());
		
		return list;
	}
	
	/**
     * 根据站点或节点ID,节点类型 获得根目录节总数
     * @param String Map<String,String> m
     * @return String
     * */
	public static String getCategoryCountBySiteAndType(Map<String,String> m)
	{
		return CategoryDAO.getCategoryCountBySiteAndType(m);
	}
	
	/**
     * 根据站点或节点ID,节点类型 获得根目录节点列表
     * @param String Map<String,String> m
     * @return List<CategoryBean>
     * */
	public static List<CategoryBean> getCategoryListBySiteAndType(Map<String,String> m)
	{
		return CategoryDAO.getCategoryListBySiteAndType(m);
	}
	
	/**
     * 根据专题分类ID和站点ID得到它所包含的目录根节点
     * @param int zt_cat_id 专题分类ID
     * @param String site_id
     * @return List<CategoryBean>
     * */
	public static List<CategoryBean> getZTCategoryListBySiteAndType(int zt_cat_id,String site_id)
	{
		List<CategoryBean> l = new ArrayList<CategoryBean>();
		Set<Integer> s = category_m.keySet();
		for(int i : s)
		{
			CategoryBean cb = category_m.get(i);
			if(site_id.equals(cb.getSite_id()) && cb.getParent_id() == 0 && cb.getZt_cat_id() == zt_cat_id)
			{
				l.add(cb);
			}
		}
		return l;
	}

	/**
     * 判断该人员是否是站点管理员或该级目录的管理权限
     * @param int cat_id
     * @param String site_id
     * @param int user_id
     * @return boolean
     * */
	public static boolean haveCategoryManagementAuthority(int cat_id,String site_id,int user_id)
	{
		if(RoleUserManager.isSiteManager(user_id+"","cms",site_id) || RoleUserManager.isSiteManager(user_id+"","zwgk",site_id) || RoleUserManager.isSiteManager(user_id+"","ggfw",site_id) || CategoryReleManager.isCategoryManagerByUser(user_id,site_id,cat_id))
		{
			return true;
		}else
			return false;
	}	
	
	/**
     * 根据cat_position,将它的所有上级节点也加入到集合中
     * @param Set menu_set
     * @param String cat_position
     * @param String site_id
     * @return 
     * */
	public static void setCategoryByPosition(Set<CategoryBean> set,String cat_position,String site_id)
	{
		String[] tempA = cat_position.split("\\$");
		
		//从第2位开始，如$1$2$3$　第一位$1为根节点，不用取，最后一位	$3为自身，也不用取，取中间的2就可以了	
		
		for(int i=2;i<tempA.length-1;i++)
		{
			if(tempA[i] != null && !"".equals(tempA[i]))
				set.add(getCategoryBeanCatID(Integer.parseInt(tempA[i]),site_id));
		}
	}
		
	/**
     * 根据分类方式ID得目录对象
     * @param int class_id
     * @return CategoryBean
     * */
	public static CategoryBean getCategoryBeanByClassID(int class_id)
	{
		Set<Integer> set = category_m.keySet();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);
			
			if(cgb.getClass_id() == class_id && cgb.getParent_id() == ROOT_ID && "".equals(cgb.getSite_id()))
			{
				return cgb;
			}
		}
		return null;
	}
	
	/**
     * 转换成小对象，内容少一些
     * @param int class_id
     * @return CategoryBean
     * */
	public static SMCategoryBean categoryToSmileBean(CategoryBean cgb)
	{
		SMCategoryBean smb = new SMCategoryBean();
		smb.setCat_cname(cgb.getCat_cname());
		smb.setCat_id(cgb.getCat_id());
		smb.setSite_id(cgb.getSite_id());
		smb.setCat_position(cgb.getCat_position());
		smb.setParent_id(cgb.getParent_id());
		return smb;
	}
	
	/**
     * 根据ID得到它的子级列表(deep+1)
     * @param int cat_id
     * @param String site_id
     * @return List
     * */
	public static List<CategoryBean> getChildCategoryList(int cat_id,String site_id)
	{
		Set<Integer> set = category_m.keySet();
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);
			if(cgb.getParent_id() == cat_id && site_id.equals(cgb.getSite_id()))
			{
				//category_m.get(i).setIs_sub(isHasChildNode(category_m.get(i).getCat_id(), category_m.get(i).getSite_id()));
				//cgb.setIs_sub(isHasChildNode(category_m.get(i).getCat_id(), category_m.get(i).getSite_id()));
				list.add(cgb);
			}
		}
		if(list != null && list.size() > 0)
			Collections.sort(list,new CategoryComparator());
		return list;
	}
	
	/**
     * 根据ID得到它的子级列表(deep+1) 用于前台取子栏目列表，过滤掉不能在前台显示的栏目　is_sow==0的不显示
     * @param int cat_id
     * @param String site_id
     * @return List
     * */
	public static List<CategoryBean> getChildCategoryListForBrowser(int cat_id,String site_id)
	{
		Set<Integer> set = category_m.keySet();
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);
			if(cgb.getParent_id() == cat_id && site_id.equals(cgb.getSite_id()) && cgb.getIs_show() == 1)
			{
				//category_m.get(i).setIs_sub(isHasChildNode(category_m.get(i).getCat_id(), category_m.get(i).getSite_id()));
				cgb.setIs_sub(isHasChildNode(category_m.get(i).getCat_id(), category_m.get(i).getSite_id()));
				list.add(cgb);
			}
		}
		if(list != null && list.size() > 0)
			Collections.sort(list,new CategoryComparator());
		return list;
	}



	/**
	 * 根据ID得到它的子级列表(deep+1) 用于前台取子栏目列表，过滤掉不能在前台显示的栏目　is_sow==0的不显示
	 * @param int cat_id
	 * @param String site_id
	 * @return List
	 * */
	public static List<CategoryBean> getChildCategoryListForBrowser(int cat_id)
	{
		Set<Integer> set = category_m.keySet();
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);
			if(cgb.getParent_id() == cat_id && cgb.getIs_show() == 1)
			{
				//category_m.get(i).setIs_sub(isHasChildNode(category_m.get(i).getCat_id(), category_m.get(i).getSite_id()));
				cgb.setIs_sub(isHasChildNode(category_m.get(i).getCat_id()));
				list.add(cgb);
			}
		}
		if(list != null && list.size() > 0)
			Collections.sort(list,new CategoryComparator());
		return list;
	}
	/**
     * 根据cat_id判断它是否有子级
     * @param int cat_id
     * @param String site_id
     * @return boolean
     * */
	public static boolean isHasChildNode(int cat_id,String site_id)
	{		
		Set<Integer> set = category_m.keySet();		
		for(int i : set){
			CategoryBean cgb = category_m.get(i);
			if(cgb.getParent_id() == cat_id && site_id.equals(cgb.getSite_id()))
				return true;
		}
		return false;
	}

	/**
	 * 根据cat_id判断它是否有子级
	 * @param int cat_id
	 * @param String site_id
	 * @return boolean
	 * */
	public static boolean isHasChildNode(int cat_id)
	{
		Set<Integer> set = category_m.keySet();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);
			if(cgb.getParent_id() == cat_id)
				return true;
		}
		return false;
	}
	/**
	 * 根据栏目对象返回简单的Bean对象，并包含层级的枝叶节点
	 * @param String ename
	 * @return List
	 */
	public static SMCategoryBean getAllChildForSMCategoryBean(CategoryBean cgb)
	{
		if(cgb != null)
		{
			List<SMCategoryBean> sml = new ArrayList<SMCategoryBean>();
			List<CategoryBean> cl = getChildCategoryList(cgb.getCat_id(),cgb.getSite_id());
			if(cl != null && cl.size() > 0)
			{
				for(CategoryBean cb : cl)
				{
					if(cb.getIs_archive() == 0)
						sml.add(getAllChildForSMCategoryBean(cb));					
				}
			}
			SMCategoryBean smb = categoryToSmileBean(cgb);
			smb.setSm_list(sml);
			return smb;
		}
		return null;
	}
	
	
	/**
     * 根据cat_id得到它所有的子级列表(deep+n)
     * @param int cat_id
     * @return List
     * */
	public static List<CategoryBean> getAllChildCategoryList(int cat_id,String site_id)
	{
		CategoryBean cgb = getCategoryBeanCatID(cat_id,site_id);
		if(cgb != null)
		{
			String cat_position = cgb.getCat_position();
			Set<Integer> set = category_m.keySet();
			List<CategoryBean> list = new ArrayList<CategoryBean>();
			for(int i : set){
				cgb = category_m.get(i);
				if(cgb.getCat_position().startsWith(cat_position) && site_id.equals(cgb.getSite_id()))
					list.add(category_m.get(i));
			}			
			return list;
		}else
			return null;
	}
	
	/**
     * 根据cat_id得到它所有的子级cat_id(deep+n)
     * @param int cat_id
     * @return String
     * */
	public static String getAllChildCategoryIDS(int cat_id,String site_id)
	{
		CategoryBean cgb = getCategoryBeanCatID(cat_id,site_id);
		if(cgb != null)
		{
			String cat_position = cgb.getCat_position();
			Set<Integer> set = category_m.keySet();
			String cat_ids = "";
			for(int i : set){
				cgb = category_m.get(i);
				if(cgb.getCat_position().startsWith(cat_position) && !cat_position.equals(cgb.getCat_position()) && site_id.equals(cgb.getSite_id())){
					cat_ids += ","+cgb.getCat_id();
				}
			}
			if(cat_ids.length() > 0){
				cat_ids = cat_ids.substring(1);
			}
			return cat_ids;
		}else
			return null;
	}



	/**
	 * 根据cat_id得到它所有的子级cat_id(deep+n)
	 * @param int cat_id
	 * @return String
	 * */
	public static String getAllChildCategoryIDS(int cat_id)
	{
		CategoryBean cgb = getCategoryBeanCatID(cat_id);
		if(cgb != null)
		{
			String cat_position = cgb.getCat_position();
			Set<Integer> set = category_m.keySet();
			String cat_ids = "";
			for(int i : set){
				cgb = category_m.get(i);
				if(cgb.getCat_position().startsWith(cat_position) && !cat_position.equals(cgb.getCat_position())){
					cat_ids += ","+cgb.getCat_id();
				}
			}
			if(cat_ids.length() > 0){
				cat_ids = cat_ids.substring(1);
			}
			return cat_ids;
		}else
			return null;
	}
	
	/**
     * 根据cat_ID得到它所有的子级ID(deep+n)(可用多个ID)
     * @param String cat_ids
     * @return String
     * */
	public static String getAllChildCategoryIDS(String cat_ids,String site_id)
	{
		String ids = "";
		String[] tempA = cat_ids.split(",");
		for(int i=0;i<tempA.length;i++)
		{
			String c_ids = getAllChildCategoryIDS(Integer.parseInt(tempA[i]),site_id);
			if(c_ids != null && !"".equals(c_ids))
				ids += ","+c_ids;
		}
		if(ids.length() > 0)
			ids = ids.substring(1);
		
		return ids;
	}


	/**
	 * 根据cat_ID得到它所有的子级ID(deep+n)(可用多个ID)
	 * @param String cat_ids
	 * @return String
	 * */
	public static String getAllChildCategoryIDS(String cat_ids)
	{
		String ids = "";
		String[] tempA = cat_ids.split(",");
		for(int i=0;i<tempA.length;i++)
		{
			String c_ids = getAllChildCategoryIDS(Integer.parseInt(tempA[i]));
			if(c_ids != null && !"".equals(c_ids))
				ids += ","+c_ids;
		}
		if(ids.length() > 0)
			ids = ids.substring(1);

		return ids;
	}
	
	/**
     * 根据栏目和站点ID得到栏目内容的保存路径
     * @param int cat_id
     * @param String app_id
     * @param String site_id
     * @return String
     * */
	public static String getCategoryContentSavePath(int cat_id,String app_id,String site_id)
	{
		String save_path = SiteManager.getSitePath(site_id)+CategoryUtil.getFoldePathByCategoryID(cat_id,app_id,site_id);
		return save_path;		
	}
	
	
	
	/**
     * 添加目录的时候判断此父节点下是否已有想同的英文名称
     * @param int parent_id
     * @param String cat_ename
     * @param String site_id
     * @return boolean
     * */
	public static boolean categoryIsExist(int parent_id,String cat_ename,String site_id)
	{
		//List<CategoryBean> l = getAllChildCategoryList(parent_id,site_id);
		List<CategoryBean> l = getChildCategoryList(parent_id,site_id);
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				if(l.get(i).getCat_ename().equals(cat_ename))
					return true;
			}
			return false;
		}
		return false;
	}
	
	/**
     * 根据分类方式对象插入目录信息
     * @param CateClassBean
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertCategoryByClass(CateClassBean ccb,SettingLogsBean stl)
	{
		CategoryBean cgb = new CategoryBean();
		
		cgb.setClass_id(ccb.getClass_id());
		cgb.setCat_cname(ccb.getClass_cname());
		cgb.setCat_ename(ccb.getClass_ename());		
		cgb.setId(getNewCategoryID());
		cgb.setCat_id(cgb.getId());
		cgb.setApp_id("system");
		cgb.setIs_generate_index(0);
		return insertCategory(cgb,true,stl);
	}
	
	
	/**
     * 插入目录信息
     * @param CategoryBean cgb
     * @param boolean is_share 是否添加的是共享目录
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertCategory(CategoryBean cgb,boolean is_share,SettingLogsBean stl)
	{		
		insertCategoryHandl(cgb,is_share);
		if(CategoryDAO.insertCategory(cgb, stl))
		{
			reloadCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 批量修改目录信息
     * @param Map
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean batchUpdateCategory(Map<String,String> cat_m,List<CategoryModel> l,String cat_id,String site_id,SettingLogsBean stl)
	{
		try{
			if(cat_m != null && cat_m.size() > 0)
			{
				cat_m.put("cat_ids", cat_id);
				cat_m.put("site_id", site_id);
				CategoryDAO.batchUpdateCategory(cat_m, stl);
				reloadCategory();
			}			
			if(l != null && l.size() > 0)
			{
				Map<String,String> dete_map = new HashMap<String,String>();
				dete_map.put("cat_id",cat_id);
				dete_map.put("site_id",site_id);
				for(CategoryModel cm : l)
				{
					dete_map.put("model_id",cm.getModel_id()+"");
					DBManager.delete("delete_category_model", dete_map);
					String[] tempA = cat_id.split(",");
					for(int i=0;i<tempA.length;i++)
					{
						cm.setCat_id(Integer.parseInt(tempA[i]));
						cm.setCat_model_id(PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CATEGORY_MODEL_TABLE_NAME));					
						DBManager.insert("insert_category_model", cm);
					}
				}
				CategoryModelManager.reloadCategoryModel();
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 修改目录首页或列表页模板信息，用于专题工具设计后保存模板关联
     * @param Map
     * @return boolean
     * */
	public static boolean updateCategoryTemplate(Map<String,String> m)
	{
		if("template_content".equals(m.get("template_type")))
		{
			//内容页模板需特殊处理
			List<CategoryModel> l = new ArrayList<CategoryModel>();
			CategoryModel pic = new CategoryModel();
			CategoryModel article = new CategoryModel();
			CategoryModel video = new CategoryModel();
			
			String app_id = "cms";
			String site_id = m.get("site_id");
			String cat_id = m.get("cat_id");
			int template_id = Integer.parseInt(m.get("template_id"));
			
			pic.setApp_id(app_id);
			pic.setSite_id(site_id);
			pic.setCat_id(Integer.parseInt(cat_id));
			pic.setTemplate_content(template_id);
			pic.setModel_id(10);
			
			article.setApp_id(app_id);
			article.setSite_id(site_id);
			article.setCat_id(Integer.parseInt(cat_id));
			article.setTemplate_content(template_id);
			article.setModel_id(11);
			
			video.setApp_id(app_id);
			video.setSite_id(site_id);
			video.setCat_id(Integer.parseInt(cat_id));
			video.setTemplate_content(template_id);
			video.setModel_id(13);
			l.add(pic);
			l.add(article);
			l.add(video);
			return CategoryModelManager.updateCategoryModel(l, cat_id, site_id);
		}else
		{		
			if(CategoryDAO.updateCategoryTemplate(m))
			{
				reloadCategory();
				return true;
			}else
				return false;
		}
	}
	
	/**
     * 删除模板时清空模板与栏目的关联
     * @param String template_ids
     * @param String site_id
     * @return boolean
     * */
	public static boolean clearCategoryTemplate(String template_ids,String site_id)
	{
		if(CategoryDAO.clearCategoryTemplate(template_ids,site_id))
		{
			reloadCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 插入目录信息
     * @param CategoryBean cgb
     * @param boolean is_share 是否添加的是共享目录
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertCategoryNoCatch(CategoryBean cgb,boolean is_share,SettingLogsBean stl)
	{		
		insertCategoryHandl(cgb,is_share);
		return CategoryDAO.insertCategory(cgb, stl);
	}
	
	public static void insertCategoryHandl(CategoryBean cgb,boolean is_share)
	{
		if(cgb.getParent_id() == ROOT_ID)
		{
			cgb.setCat_position("$0$"+cgb.getCat_id()+"$");
			cgb.setCat_level(1);
		}
		else
		{
			CategoryBean parentB = getCategoryBeanCatID(cgb.getParent_id(),cgb.getSite_id());
			cgb.setCat_position(parentB.getCat_position()+cgb.getCat_id()+"$");
			cgb.setCat_level(parentB.getCat_level()+1);
		}
		
		if(is_share == true)
			cgb.setCat_source_id(cgb.getCat_id());
	}
	
	/**
     * 拷贝基础目录
     * @param int parent_id
     * @param String selected_cat_ids 要拷贝的节点ID
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean copyBasisCategory(int parent_id,int selected_cat_ids,String site_id,SettingLogsBean stl)
	{		
		//得到它的子节点，并写入到新目录中
		List<CategoryBean> child_list = getChildCategoryList(selected_cat_ids,site_id);
		if(child_list != null && child_list.size() > 0)
		{
			try{
				for(int i=0;i<child_list.size();i++)
				{
					copyCategoryHandl(parent_id,child_list.get(i),"system","",true,stl);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		reloadCategory();
		CategoryModelManager.reloadCategoryModel();
		return true;
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
	public static synchronized boolean copyShareCategory(int parent_id,String selected_cat_ids,String app_id,String site_id,SettingLogsBean stl)
	{
		String[] tempA = selected_cat_ids.split(",");
		for(int i=0;i<tempA.length;i++)
		{
			//此处是从共享目录中选择节点，所以站点ID的参数为空
			CategoryBean cgb = getCategoryBeanCatID(Integer.parseInt(tempA[i]),"");
			if(cgb != null)
			{
				copyCategoryHandl(parent_id,cgb,app_id,site_id,false,stl);
			}
		}
		reloadCategory();
		CategoryModelManager.reloadCategoryModel();
		return true;
	}
	
	/**
     * 拷贝目录
     * @param int parent_id
     * @param CategoryBean cgb
     * @param String app_id
     * @param String site_id
     * @param boolean is_share 是否添加的是共享目录
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static synchronized void copyCategoryHandl(int parent_id,CategoryBean cgb,String app_id,String site_id,boolean is_share,SettingLogsBean stl)
	{
		int source_cat_id = cgb.getCat_id();
		int new_cat_id = getNewCategoryID();	
		cgb.setId(new_cat_id);
		cgb.setParent_id(parent_id);
		cgb.setCat_id(new_cat_id);
		cgb.setCat_class_id(source_cat_id);	
		cgb.setSite_id(site_id);
		cgb.setApp_id(app_id);
		if(parent_id != 0)		
		{
			CategoryBean parent_bean = getCategoryBeanCatID(parent_id,site_id);			
			cgb.setClass_id(parent_bean.getCat_class_id());
		}
			
		if(insertCategoryNoCatch(cgb,is_share,stl))
		{
			//插入节点与模型关系表
			List<CategoryModel> model_l = CategoryModelManager.getCategoryReleModelList(source_cat_id+"","");
			if(model_l != null && model_l.size() > 0)
			{
				for(CategoryModel m : model_l)
				{
					m.setSite_id(site_id);
					m.setApp_id(app_id);
					m.setCat_id(new_cat_id);
				}
				CategoryModelDAO.insertCategoryModel(model_l);
			}
			//插入子节点
			List<CategoryBean> child_list = getChildCategoryList(source_cat_id,"");//此处是从共享目录中选择节点，所以站点ID的参数为空
			if(child_list != null && child_list.size() > 0)
			{
				for(int i=0;i<child_list.size();i++)
				{
					copyCategoryHandl(new_cat_id,child_list.get(i),app_id,site_id,is_share,stl);
				}
			}
		}
	}
	
	/**
     * 修改目录信息
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean updateCategory(CategoryBean cgb,SettingLogsBean stl)
	{
		if(CategoryDAO.updateCategory(cgb, stl))
		{
			reloadCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改目录关联列表页信息(主要用于公开指引,指南,年报)
     * @param String gkzy_list
     * @param String gkzn_list
     * @param String gknb_list
     * @return boolean
     * */
	public static boolean updateGKZNCateTemplate(String gkzy_list,String gkzn_list,String gknb_list)
	{//这3个目录的ID是写死的
		try{
			CategoryDAO.updateCateTemplateListByID(gkzn_list, "10");
			CategoryDAO.updateCateTemplateListByID(gknb_list, "11");
			CategoryDAO.updateCateTemplateListByID(gkzy_list, "12");
			reloadCategory();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 修改目录归档状态
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean updateCategoryArchiveStatus(String ids,String is_archive,SettingLogsBean stl)
	{
		if(CategoryDAO.updateCategoryArchiveStatus(ids,is_archive, stl))
		{
			reloadCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 根据分类修改目录信息
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean updateCategoryByClass(String cat_ename,String cat_cname,int class_id)
	{	
		CategoryBean cgb = getCategoryBeanByClassID(class_id);	
		
		if(cgb != null)
		{
			if(CategoryDAO.updateCategoryByClass(cat_ename,cat_cname,cgb.getCat_id()+""))
			{
				reloadCategory();
				return true;
			}
		}
		return true;
	}
	
	/**
     * 保存排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortCategory(String ids,SettingLogsBean stl)
	{
		if(CategoryDAO.sortCategory(ids, stl))
		{
			reloadCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除目录信息
     * @param String ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean deleteCategory(String cat_ids,String site_id,String app_id,SettingLogsBean stl)
	{
		String[] foldeArr = CategoryUtil.getFoldeArrByCatIDS(cat_ids,site_id,app_id);//根据栏目ID得到目录路径数组
		String all_child_ids = getAllChildCategoryIDS(cat_ids,site_id);
		if(!"".equals(all_child_ids))
			cat_ids = cat_ids+","+all_child_ids;
		
		try{
			//删除与共享表的关联
			CategorySharedManager.deleteCategorySharedByCatID(cat_ids,site_id);
			//删除同步栏目
			SyncManager.deleteSyncForCatID(site_id,cat_ids);
			//删除栏目与内容模型关联
			CategoryModelManager.deleteCategoryModel(cat_ids, site_id);
			//删除目录与人员关联
			CategoryReleManager.deleteCategoryReleUserByCatID(cat_ids,site_id);	
			//删除信息索引
			InfoBaseManager.clearInfoSearchByCatID(site_id, cat_ids);
			//删除栏目下的信息
			InfoBaseManager.deleteInfoBySite(site_id, cat_ids);	
			if(!"zwgk".equals(app_id) && !"ggfw".equals(app_id))
			{
				//删除服务器目录,需要使用rmi 公开和服务的目录不能删
				deleteCategoryFolde(foldeArr,site_id,app_id);	
			}
			//删除栏目获取规则
			CategoryGetReguDAO.deleteCategoryRegu(cat_ids,site_id);
			//删除栏目与专题设计的关联
			DesignDAO.deleteDesignCategory(cat_ids,"",site_id);
			
			if(CategoryDAO.deleteCategory(cat_ids,site_id, stl))
			{	
				reloadCategory();
				return true;
			}else
				return false;
		}catch(Exception e)
		{
			if(CategoryDAO.deleteCategory(cat_ids,site_id, stl))
			{	
				reloadCategory();	
				return true;
			}else
				return false;
		}
		
	}
	
	
	
	/**
     * 删除栏目的服务器目录
     * @param String cat_ids
     * @param String site_id
     * @param String app_id
     * @return 
     * */
	public static void deleteCategoryFolde(String[] foldeArr,String site_id,String app_id)
	{
		String savePath = FormatUtil.formatPath(SiteManager.getSitePath(site_id));
		if(!"cms".equals(app_id))
		{
			savePath = FormatUtil.formatPath(SiteManager.getSitePath(SiteAppRele.getSiteIDByAppID(app_id)));
		}
		if(foldeArr != null && foldeArr.length > 0)
		{
			for(int i=0;i<foldeArr.length;i++)
			{
				try{					
					if(foldeArr[i] != null && !"".equals(foldeArr[i]) && !(savePath+foldeArr[i]).endsWith("ROOT/") && !"/".equals(foldeArr[i]))
					{
						//System.out.println("deleteCategoryFolde-----"+savePath+foldeArr[i]);
						//FileOperation.deleteDir(savePath+foldeArr[i]);	
						FileRmiFactory.delDir(site_id, savePath+foldeArr[i]);
					}
				}catch(Exception e)
				{
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
	/**
     * 根据分类方式ID得到它所含节点的个数(只读取共享目录的)
     * @param int class_id
     * @return int
     * */
	public static int getCategoryCountByClassID(int class_id)
	{
		Set<Integer> set = category_m.keySet();
		int count = 0;
		for(int i : set){
			CategoryBean cgb = category_m.get(i);
			if(cgb.getClass_id() == class_id && "".equals(cgb.getSite_id()))
				count += 1;
		}
		return count;
	}
	
	/**
     * 根据分类方式删除目录
     * @param String class_id
     * @return boolean
     * */
	public static boolean deleteCategoryByClassID(String class_ids)
	{
		if(CategoryDAO.deleteCategoryByClassID(class_ids))
		{
			reloadCategory();			
			return true;
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
	public static boolean releCategoryClass(String class_id,String cat_id,SettingLogsBean stl)
	{
		if(CategoryDAO.releCategoryClass(class_id,cat_id,stl))
		{
			reloadCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 移动目录
     * @param String parent_id
     * @param String cat_ids
     * @return boolean
     * */
	public static boolean moveCategory(String parent_id,String cat_ids,String site_id,SettingLogsBean stl)
	{
		CategoryBean parent_b = getCategoryBeanCatID(Integer.parseInt(parent_id),site_id);
		if(parent_b != null)
		{
			String parent_tree_position = parent_b.getCat_position();
			int cat_level = parent_b.getCat_level();
			if(cat_ids != null && !"".equals(cat_ids))
			{
				try{
					String[] tempA = cat_ids.split(",");
					for(int i=0;i<tempA.length;i++)
					{					
						moveCategoryHandl(tempA[i],parent_id,parent_tree_position,cat_level,site_id,stl);
					}
					reloadCategory();
					return true;
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}else
			return false;
		
	}
	//移动栏目
	public static void moveCategoryHandl(String cat_id,String parent_id,String tree_position,int cat_level,String site_id,SettingLogsBean stl)
	{
		String position = tree_position+cat_id+"$";
		cat_level = cat_level+1;
		Map<String,String> new_m = new HashMap<String,String>();
		new_m.put("cat_id", cat_id);
		new_m.put("parent_id", parent_id);
		new_m.put("cat_position", position);
		new_m.put("cat_level", cat_level+"");
		if(site_id != null && !"".equals(site_id))
			new_m.put("site_id", site_id);
		if(CategoryDAO.moveCategory(new_m,stl)){
			//该节点下的子节点一并转移
			List<CategoryBean> c_list = getChildCategoryList(Integer.parseInt(cat_id),site_id);
			if(c_list != null && c_list.size() > 0)
			{
				for(int i=0;i<c_list.size();i++)
				{
					moveCategoryHandl(c_list.get(i).getCat_id()+"",cat_id,position,cat_level,site_id,stl);
				}
			}
		}
		
	}
	
	/**
     * 根据用户ID和多个栏目ID拼出这个用户所能审核的信息sql
     * @param String user_id
     * @param String category_ids
     * @param String app_id
     * @param site_id
     * @return String
     * */
	public static String getSearchSQLByCategoryIDS(String user_id,String category_ids,String app_id,String site_id)
	{
		String conn = "";
		String conn_temp = "";
		String[] tempA = category_ids.split(",");
		if(tempA != null && !"".equals(tempA))
		{
			for(int i=0;i<tempA.length;i++)
			{
				CategoryBean cb = getCategoryBeanCatID(Integer.parseInt(tempA[i]),site_id);
				if(cb != null)
				{
					if(cb.getWf_id() != 0)
					{
						//如果该栏目有流程，根据用户得到步骤ID
						int step_id = WorkFlowManager.getMaxStepIDByUserID(cb.getWf_id(),user_id,app_id,site_id);
						if(step_id==100){
							conn += "or (ci.cat_id = "+tempA[i]+" and ci.step_id < "+step_id+") ";
						}else{
							conn += "or (ci.cat_id = "+tempA[i]+" and ci.step_id = "+(step_id-1)+") ";
						}
					}else
					{
						conn_temp += ","+tempA[i];
					}
				}
			}
			if(conn != null && !"".equals(conn))
			{
				conn = conn.substring(2);
				conn = "("+conn+")";
			}
			if(conn_temp != "" && !"".equals(conn_temp))
			{
				conn_temp = conn_temp.substring(1);
				conn_temp = " ci.cat_id in ("+conn_temp+")";
				if(conn != null && !"".equals(conn))
				{
					conn = "("+conn+" or "+conn_temp+")";					
				}else
					conn = conn_temp;
			}			
		}
		return " and "+conn;
	}
	
	/**
     * 根据节点ID插入默认的公开目录（公开指南，公开指引，公开年报）
     * @param String class_id
     * @return boolean
     * */
	public static boolean insertGKDefaultCategory(String node_id)
	{
		CategoryBean cgb = new CategoryBean();
		cgb.setSite_id(node_id);
		try
		{
			cgb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CATEGORY_TABLE_NAME));
			cgb.setCat_id(10);
			cgb.setCat_ename("gkzn");
			cgb.setCat_cname("公开指南");
			CategoryDAO.insertGKDefaultCategory(cgb);
			
			cgb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CATEGORY_TABLE_NAME));
			cgb.setCat_id(11);
			cgb.setCat_ename("gknb");
			cgb.setCat_cname("公开年报");
			CategoryDAO.insertGKDefaultCategory(cgb);
			
			cgb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CATEGORY_TABLE_NAME));
			cgb.setCat_id(12);
			cgb.setCat_ename("gkzy");
			cgb.setCat_cname("公开指引");
			CategoryDAO.insertGKDefaultCategory(cgb);
			reloadCategory();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 修改基础目录列表页模板
     * @param String template_id
     * @return boolean
     * */
	public static boolean updateBaseCategoryTemplate(String templage_id)
	{
		if(CategoryDAO.updateBaseCategoryTemplate(templage_id))
		{
			reloadCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 得到基础目录列表页模板
     * @param 
     * @return String
     * */
	public static String getBaseCategoryTemplateListID()
	{
		return CategoryDAO.getBaseCategoryTemplateListID();
	}
		
	public static class CategoryComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			CategoryBean cgb1 = (CategoryBean) o1;
			CategoryBean cgb2 = (CategoryBean) o2;
		    if (cgb1.getCat_sort() > cgb2.getCat_sort()) {
		     return 1;
		    } else {
		     if (cgb1.getCat_sort() == cgb2.getCat_sort()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	
	/*************************************栏目异步树结构**************************************************/
	
	/**
     * 根据站点或节点ID获得根目录节点列表
     * @param String site_id
     * @param String pid 父节点ID
     * @return List<CategoryBean>
     * */
	public static List<CategoryBean> getCategoryListBySiteIDPid(String site_id,int pid)
	{
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		Set<Integer> set = category_m.keySet();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);			
			if(site_id.equals(cgb.getSite_id()) && cgb.getParent_id() == pid)
			{
				list.add(cgb);
			}
		}
		if(list != null && list.size() > 0)
			Collections.sort(list,new CategoryComparator());
		
		return list;
	}
	/**
	 * 根据站点或节点ID获得根目录节点列表
	 * @param String pid 父节点ID
	 * @return List<CategoryBean>
	 * */
	public static List<CategoryBean> getCategoryListByPid(int pid)
	{
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		Set<Integer> set = category_m.keySet();
		for(int i : set){
			CategoryBean cgb = category_m.get(i);
			if(cgb.getParent_id() == pid)
			{
				list.add(cgb);
			}
		}
		if(list != null && list.size() > 0)
			Collections.sort(list,new CategoryComparator());

		return list;
	}
	
	/*************************************栏目异步树结构**************************************************/
	
	
	
	public static void main(String[] args)
	{
		//insertGKDefaultCategory("GKmzj");
		//System.out.println(getCategoryTreeBySiteID("GKmzj",0));		                 
		//System.out.println(deleteCategory("792","11111ddd",new SettingLogsBean()));
		//System.out.println(copyShareCategory(0,"86","11111SITE01",new SettingLogsBean()));
		System.out.println(getCategoryBeanCatID(1883,"HIWCMdemo").getCat_type());
		
		/*
		Set<CategoryBean> s = new HashSet<CategoryBean>(); 
		String ss = "158,160,157";
		String[] tempA = ss.split(",");
		for(int i=0;i<tempA.length;i++)
		{
			s.add(getCategoryBean(Integer.parseInt(tempA[i])));
		}
		
		for(CategoryBean i : s)
		{
			
		}*/
		
	}
}
