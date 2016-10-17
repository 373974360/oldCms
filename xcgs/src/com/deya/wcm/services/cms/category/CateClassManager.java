package com.deya.wcm.services.cms.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.cms.category.CateClassBean;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.SMCategoryBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.cms.category.CateClassDao;

/**
 * 分类方式逻辑处理类.
 * <p>
 * Title: CicroDate
 * </p>
 * <p>
 * Description: 分类方式逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: Cicro
 * </p>
 * 
 * @author liqi
 * @version 1.0 *
 */
public class CateClassManager implements ISyncCatch{
	
	// 共享目录
	private static final int BASIS_DIR = 0;
	
	// 分类方法缓存
	private static TreeMap<String, CateClassBean> cateClass_Map = new TreeMap<String, CateClassBean>();
	private static Map<String,SMCategoryBean> sm_category_map = new HashMap<String,SMCategoryBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		cateClass_Map.clear();
		try{
			List<CateClassBean> lt = CateClassDao.getAllCateClassList();		
			sm_category_map.clear();
			if(lt != null && lt.size() > 0)
			{
				for(int i=0; i<lt.size(); i++)
				{
					cateClass_Map.put(lt.get(i).getClass_id()+"", lt.get(i));
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化加载分类方式信息
	 */
	public static void reloadCateClass()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.cms.category.CateClassManager");
	}
	
	public static void clearSMCateMap()
	{
		sm_category_map.clear();
	}
	
	/**
	 * 根据分类英文名简单的栏目树列表
	 * @param String ename
	 * @return List
	 */
	public static SMCategoryBean getSMCategoryList(String ename)
	{
		
		CateClassBean ccb = getCateClassBeanByEName(ename);
		
		if(ccb != null)
		{
			if(sm_category_map.containsKey(ename))
			{
				return sm_category_map.get(ename);
			}else
			{
				CategoryBean cgb = CategoryManager.getCategoryBeanByClassID(ccb.getClass_id());	
				SMCategoryBean smcb = CategoryManager.getAllChildForSMCategoryBean(cgb);
				sm_category_map.put(ename,smcb);
				return smcb;
			}
		}
		return null;
	}
	
	
	/**
	 * 根据分类英文名得到分类对象
	 * @param String ename
	 * @return CateClassBean
	 */
	public static CateClassBean getCateClassBeanByEName(String ename)
	{
		Set<String> s = cateClass_Map.keySet();
		for(String str : s)
		{
			CateClassBean ccb = cateClass_Map.get(str);
			if(ccb.getClass_ename().equals(ename))
			{
				return ccb;
			}
		}
		return null;
	}
	
	/**
	 * 得到所有分类分类方法集合
	 * @return
	 */
	public static Map<String, CateClassBean> getCateClassMap()
	{
		return cateClass_Map;
	}
	
	/**
	 * 得到分类类型为"基础目录"的分类方法列表
	 * @return
	 * 		分类方法列表
	 */
	public static List<CateClassBean> getBasisCateClassList()
	{
		List<CateClassBean> ret = new ArrayList<CateClassBean>();
		Iterator<CateClassBean> it = cateClass_Map.values().iterator();
		while(it.hasNext())
		{
			CateClassBean ccb = it.next();
			if(ccb.getClass_type() == BASIS_DIR)
			{
				ret.add(ccb);
			}
		}
		return ret;
	}
	
	/**
	 * 得到所有分类方法
	 * @return
	 */
	public static List<CateClassBean> getAllCateClassList()
	{
		List<CateClassBean> ret = new ArrayList<CateClassBean>();
		// 临时Map调整元素顺序
		Map<Integer, CateClassBean> mp = new TreeMap<Integer, CateClassBean>();
		Iterator<CateClassBean> val_it = cateClass_Map.values().iterator();
		while(val_it.hasNext())
		{
			CateClassBean ccb = val_it.next();
			mp.put(ccb.getClass_id(), ccb);
		}
		
		Iterator<Integer> it = mp.keySet().iterator();
		while(it.hasNext())
		{
			int key = it.next();
			ret.add(mp.get(key));
		}
		return ret;
	}
	
	/**
	 * 根据应用ID取得共享分类方法列表
	 * @param app_id  应用ID
	 * @return
	 * 		分类方法列表
	 */
	public static List<CateClassBean> getCateClassListByApp(String app_id)
	{
		Set<String> set = cateClass_Map.keySet();
		List<CateClassBean> list = new ArrayList<CateClassBean>();
		for(String i : set){
			CateClassBean ccb = cateClass_Map.get(i);
			if(ccb.getApp_ids().contains(app_id) && ccb.getClass_type() == 1)
				list.add(cateClass_Map.get(i));
		}
		return list;
	}
	
	/**
	 * 根据分类方法ID取得分类方法对象
	 * @param id	分类方法ID
	 * @return		分类方法对象
	 */
	public static CateClassBean getCateClassBeanById(String id)
	{
		if(cateClass_Map.containsKey(id))
			return cateClass_Map.get(id);
		else
		{
			CateClassBean ccb = CateClassDao.getCateClassBeanByID(id);
			if(ccb != null)
			{
				cateClass_Map.put(ccb.getClass_id()+"", ccb);
				return ccb;
			}else
				return null;
		}
	}
	
	/**
	 * 插入分类方法信息
	 * @param ccb	分类方法对象
	 * @param stl
	 * @return
	 * 		true 成功 |false 失败
	 */
	public static boolean insertCateClass(CateClassBean ccb, SettingLogsBean stl)
	{
		int class_id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CLASS_TABLE_NAME);
		ccb.setClass_id(class_id);
		if(CateClassDao.insertCateClass(ccb, stl))
		{
			reloadCateClass();
			CategoryManager.insertCategoryByClass(ccb,stl);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改分类方法信息
	 * @param ccb  分类方法对象
	 * @param stl
	 * @return
	 * 		true 成功 |false 失败
	 */
	public static boolean updateCateClass(CateClassBean ccb, SettingLogsBean stl)
	{
		if(CateClassDao.updateCateClass(ccb, stl))
		{
			reloadCateClass();
			CategoryManager.updateCategoryByClass(ccb.getClass_ename(),ccb.getClass_cname(),ccb.getClass_id());
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除分类方法信息
	 * @param ids
	 * 		分类方法ID,多个ID使用","分隔
	 * @param stl
	 * @return
	 */
	public static boolean deleteCateClass(String ids, SettingLogsBean stl)
	{
		if(CateClassDao.deleteCateClass(ids, stl))
		{
			reloadCateClass();
			CategoryManager.deleteCategoryByClassID(ids);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void main(String arg[])
	{
		System.out.println(getSMCategoryList("ztfl"));
	}
	
}
