package com.deya.wcm.services.cms.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.cms.category.ZTCategoryBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.cms.category.ZTCategoryDAO;

/**
 *  专题分类管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 专题分类管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class ZTCategoryManager implements ISyncCatch{
	private static Map<Integer,ZTCategoryBean> zt_c_map = new HashMap<Integer,ZTCategoryBean>();
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		zt_c_map.clear();
		List<ZTCategoryBean> l = ZTCategoryDAO.getALlZTCategoryList();
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				zt_c_map.put(l.get(i).getId(), l.get(i));
			}
		}
	}
	
	public static void reloadZTCategory()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.cms.category.ZTCategoryManager");
	}
	
	/**
     * 根据站点ID得到有权限管理的专题分类树
     * @param String site_id
     * @return String
     * */
	public static String getZTCategoryTreeJsonStr(String site_id)
	{
		String json_str = "";
		//首先获取专题分类列表
		List<ZTCategoryBean> l = getZTCategoryList(site_id);
		if(l != null && l.size() > 0)
		{			
			for(int i=0;i<l.size();i++)
			{				
				String cate_str = CategoryTreeUtil.getZTCategoryTreeStr(l.get(i).getZt_cat_id(),site_id);				
				if(cate_str != null && !"".equals(cate_str.trim()))
				{
					json_str += "{\"id\":"+l.get(i).getZt_cat_id()+",\"iconCls\":\"icon-category\",\"text\":\""+l.get(i).getZt_cat_name()+"\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
					json_str += ",\"children\":["+cate_str+"]},";
				}	
			}
			
			if(json_str != null && !"".equals(json_str))
			{
				if(json_str.endsWith(","));
					json_str = json_str.substring(0,json_str.length()-1);
			}
			json_str = "["+json_str+"]";
			return json_str;
		}
		else
			return "[]";
	}
	
	/**
     * 根据站点ID,人员ID得到有权限管理的专题分类树
     * @param String site_id
     * @param int user_id
     * @return String
     * */
	public static String getZTCategoryTreeJsonStr(String site_id,int user_id)
	{
		String json_str = "";
		//首先获取专题分类列表
		List<ZTCategoryBean> l = getZTCategoryList(site_id);
		if(l != null && l.size() > 0)
		{			
			for(int i=0;i<l.size();i++)
			{
				//根据分类ID,用户ID得到它下面的专题树节点
				String cate_str = CategoryTreeUtil.getZTCategoryTreeStr(l.get(i).getZt_cat_id(),site_id,user_id);				
				if(cate_str != null && !"".equals(cate_str.trim()))
				{
					json_str += "{\"id\":"+l.get(i).getZt_cat_id()+",\"iconCls\":\"icon-category\",\"text\":\""+l.get(i).getZt_cat_name()+"\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
					json_str += ",\"children\":["+cate_str+"]},";
				}	
			}
			if(json_str.endsWith(","))
				json_str = json_str.substring(0,json_str.length()-1);
			json_str = "["+json_str+"]";
			return json_str;
		}
		else
			return "[]";
	}
	
	
	/**
     * 根据站点ID得到专题分类列表
     * @param 
     * @return String
     * */
	public static List<ZTCategoryBean> getZTCategoryList(String site_id)
	{
		List<ZTCategoryBean> l = new ArrayList<ZTCategoryBean>();
		Set<Integer> s = zt_c_map.keySet();
		for(int i : s)
		{
			ZTCategoryBean zb = zt_c_map.get(i);
			if(site_id.equals(zb.getSite_id()))
				l.add(zb);
		}
		if(l != null && l.size() > 0)
			Collections.sort(l,new ZTCategoryComparator());
		return l;
	}
		
	/**
     * 得到专题分类对象
     * @param int id
     * @return ZTCategoryBean
     * */
	public static ZTCategoryBean getZRCategoryBean(int id)
	{
		if(zt_c_map.containsKey(id))
		{
			return zt_c_map.get(id);
		}
		else
		{
			ZTCategoryBean zb = ZTCategoryDAO.getZRCategoryBean(id);
			if(zb != null)
			{
				zt_c_map.put(id, zb);
				return zb;
			}else
				return null;
		}
	}
	
	/**
     * 添加专题分类
     * @param ZTCategoryBean　zb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertZTCategory(ZTCategoryBean zb,SettingLogsBean stl)
	{
		if(ZTCategoryDAO.insertZTCategory(zb, stl))
		{
			reloadZTCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改专题分类
     * @param ZTCategoryBean　zb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateZTCategory(ZTCategoryBean zb,SettingLogsBean stl)
	{
		if(ZTCategoryDAO.updateZTCategory(zb, stl))
		{
			reloadZTCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 保存专题分类排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean sortZTCategory(String ids,SettingLogsBean stl)
	{
		if(ZTCategoryDAO.sortZTCategory(ids, stl))
		{
			reloadZTCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除专题分类
     * @param int id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteZTCategory(int id,SettingLogsBean stl)
	{
		if(ZTCategoryDAO.deleteZTCategory(id, stl))
		{
			reloadZTCategory();
			return true;
		}else
			return false;
	}
	
	static class ZTCategoryComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			ZTCategoryBean cgb1 = (ZTCategoryBean) o1;
			ZTCategoryBean cgb2 = (ZTCategoryBean) o2;
		    if (cgb1.getSort_id() > cgb2.getSort_id()) {
		     return 1;
		    } else {
		     if (cgb1.getSort_id() == cgb2.getSort_id()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(getZTCategoryTreeJsonStr("11111ddd"));
	}
}
