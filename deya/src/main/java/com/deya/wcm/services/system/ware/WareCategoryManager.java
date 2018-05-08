package com.deya.wcm.services.system.ware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.bean.system.ware.WareCategoryBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.system.ware.WareCategoryDAO;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.role.RoleUserManager;

public class WareCategoryManager implements ISyncCatch{

	private static Map<String, WareCategoryBean> wareCate_map = new HashMap<String, WareCategoryBean>();
	private static String ware_list_path = JconfigUtilContainer.managerPagePath().getProperty("ware_update", "", "m_org_path"); 
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<WareCategoryBean> lt = WareCategoryDAO.getWareCategoryList();
		wareCate_map.clear();
		if(lt != null)
		{
			for(int i=0; i < lt.size(); i++)
			{
				WareCategoryBean wcb = lt.get(i);
				String key = wcb.getId();
				wareCate_map.put(key, wcb);
			}
		}
	}
	
	/**
	 * 初始化加载信息标签信息
	 */
	public static void reloadMap()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.ware.WareCategoryManager");
	}
	
	/**
	 * 根据站点ID得到所有的分类list	 
	 * @param String site_id
	 * @return list
	 */
	public static List<WareCategoryBean> getWCategoryListBySite(String site_id)
	{
		List<WareCategoryBean> l = new ArrayList<WareCategoryBean>();
		Set<String> s = wareCate_map.keySet();
		for(String i:s)
		{
			WareCategoryBean wcb = wareCate_map.get(i);
			if(site_id.equals(wcb.getSite_id()))
			{
				l.add(wcb);
			}
		}
		return l;
	}
	
	/**
	 * 根据登录用户得到它能管理的标签分类树节点
	 * @param int user_id
	 * @param String site_id
	 * @return String
	 */
	public static String getJSONTreeBySiteUser(int user_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("site_id", site_id);
		m.put("app_id", "cms");
		
		List<WareCategoryBean> wcb_list = new ArrayList<WareCategoryBean>();
		//System.out.println(RoleUserManager.isSiteManager(user_id+"","cms",site_id));
		//判断该人员是否站点管理员，如果是，给出全部的分类
		if(RoleUserManager.isSiteManager(user_id+"","cms",site_id))
			wcb_list = getWCategoryListBySite(site_id);		
		else
			wcb_list = getWCategoryListBySiteUsers(user_id,site_id);		
		SiteBean stb = SiteManager.getSiteBeanBySiteID(site_id);
		
		String rootName = "root";
		if(stb != null)
		{
			rootName = stb.getSite_name();
		}		
		String child_str = "";
		String json_str = "[{\"id\":0,\"text\":\""+rootName+"\"";
		child_str = getChildStrBySpecificList(getChildListBySpecificList("0", wcb_list),wcb_list,m);
		if(child_str != null && !"".equals(child_str))
		{
			json_str += ",\"children\":["+child_str+"]";
		}
		json_str += "}]";
		return json_str;
	}
	
	/**
	 * 从指写的列表中取得Json数据的子目录
	 * @param lt
	 * @param mp
	 * @return
	 */
	private static String getChildStrBySpecificList(List<WareCategoryBean> lt ,List<WareCategoryBean> sp_list,Map<String,String> m)
	{
		String json_str = "";
		if(lt != null && lt.size() > 0)
		{
			for(int i=0;i<lt.size();i++)
			{	
				List<WareCategoryBean> child_o_list = getChildListBySpecificList(lt.get(i).getWcat_id(), sp_list);
				String ware_str = getWareJsonStr(lt.get(i).getWcat_id(), m);//得到标签
				if((child_o_list != null && child_o_list.size() > 0) || (ware_str != null && !"".equals(ware_str)))
				{
					json_str += "{";
					json_str += "\"id\":"+lt.get(i).getWcat_id()+",\"text\":\""+lt.get(i).getWcat_name()+"\"";
					
					String children_str = getChildStrBySpecificList(child_o_list, sp_list,m);
					if(children_str != null && !"".equals(children_str))
					{						
						if(ware_str != null && !"".equals(ware_str))						
							children_str = children_str+","+ware_str;
						
						json_str += ",\"state\":'closed',\"children\":["+children_str+"]";
					}
					else
					{										
						if(ware_str != null && !"".equals(ware_str))
							json_str += ",\"state\":'closed',\"children\":["+ware_str+"]";
					}
					json_str += "}";					
					if(i+1 != lt.size())
						json_str += ",";		
				}
			}
		}
		if(json_str.endsWith(","))
			json_str = json_str.substring(0,json_str.length()-1);
		return json_str;
	}
	
	public static String getWareJsonStr(String wcat_id,Map<String,String> m)
	{
		List<WareBean> wl = WareManager.getWareList(wcat_id,m);
		if(wl != null && wl.size() > 0)
		{
			String str = "";
			for(int i=0;i<wl.size();i++)
			{
				if(wl.get(i).getWare_type() != 1)
				{
					str += ",{";
					str += "\"id\":"+wl.get(i).getWare_id()+",\"text\":\""+wl.get(i).getWare_name()+"\",\"attributes\":{\"url\":\""
					+ware_list_path+"?wareID="+wl.get(i).getWare_id()+"\"}";
					str += "}";		
				}
			}
			if(str != null && !"".equals(str))
				str = str.substring(1);
			return str;
		}
		return null;
	}
	
	/**
	 * 从指写的列表中得它子节点对象
	 * @param String parent_id
	 * @param List<RuleCategoryBean> sp_list
	 * @return	List<RuleCategoryBean>
	 */
	public static List<WareCategoryBean> getChildListBySpecificList(String parent_id,List<WareCategoryBean> sp_list)
	{
		List<WareCategoryBean> child_list = new ArrayList<WareCategoryBean>();
		if(sp_list != null && sp_list.size() > 0)
		{
			for(int i=0;i<sp_list.size();i++)
			{
				if(parent_id.equals(sp_list.get(i).getParent_id()))
					child_list.add(sp_list.get(i));
			}
		}
		Collections.sort(child_list, new WareCateComparator());
		return child_list;
	}
	
	/**
	 * 根据用户ID和站点ID得到它所能管理的分类列表
	 * @param int wcat_id
	 * @param String site_id
	 * @return	RuleCategoryBean
	 */
	public static List<WareCategoryBean> getWCategoryListBySiteUsers(int user_id,String site_id)
	{
		List<WareCategoryBean> wcb_list = new ArrayList<WareCategoryBean>();
		Set<WareCategoryBean> wcat_set = WareReleUserManager.getWCatIDByUser(user_id,site_id);	
		
		Iterator<WareCategoryBean> it = wcat_set.iterator();
		while (it.hasNext()) {
			WareCategoryBean wcb = it.next();
			wcb_list.add(wcb);
		    String position = wcb.getWcat_position();
		    position = position.substring(1,position.length()-1);
		    String[] tempA = position.split("\\$");
		    //截取分类路径，把该分类所有的上级分类写入到list
			for(int i=0;i<tempA.length-1;i++)
			{
				WareCategoryBean w_bean =  getWareCteBeanByWID(tempA[i],site_id);
				if(w_bean != null && !wcb_list.contains(w_bean))
				{
					wcb_list.add(w_bean);
				}
			}
		}
		return wcb_list;
	}
	
	/**
	 * 根据wcat_id和site_id得到分类对象
	 * @param int wcat_id
	 * @param String site_id
	 * @return	RuleCategoryBean
	 */
	public static WareCategoryBean getWareCteBeanByWID(String wcat_id,String site_id)
	{
		Set<String> s = wareCate_map.keySet();
		for(String i : s)
		{
			WareCategoryBean wcb = wareCate_map.get(i);
			if(wcat_id.equals(wcb.getWcat_id()) && site_id.equals(wcb.getSite_id()))
			{
				return wcb;
			}
		}
		return null;
	}
	
	/**
	 * 通过ID，site_id，app_id取得信息标签分类列表
	 * @param id	信息标签ID
	 * @param mp	site_id，app_id
	 * @return	信息标签列表
	 */
	public static List<WareCategoryBean> getWareCateList(String id, Map<String, String> mp)
	{
		return getChildList(id, mp);
	}
	
	/**
	 * 根据ID取得信息标签分类
	 * @param id 信息标签ID
	 * @return	信息标签分类对象
	 */
	public static WareCategoryBean getWareCategoryByID(String id)
	{
		WareCategoryBean wcb = wareCate_map.get(id);
		if(wcb == null)
		{
			reloadMap();
			wcb = wareCate_map.get(id);
		}
		return wcb;
	}
	
	/**
	 * 插入信息标签分类信息
	 * @param wcb	信息标签分类信息
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean insertWareCate(WareCategoryBean wcb, SettingLogsBean stl)
	{
		WareCategoryBean parentBean = wareCate_map.get(wcb.getParent_id());
		if(parentBean != null)
		{
			// 子节点层级为父节点+1，如果父节点不存在，使用默认值
			wcb.setWcat_level(parentBean.getWcat_level()+1);
			// 自身位置在DAO类中添加
			wcb.setWcat_position(parentBean.getWcat_position());
		}
		
		if(WareCategoryDAO.insertWareCate(wcb, stl))
		{
			reloadMap();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改信息标签分类信息
	 * @param wcb	信息标签分类信息
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateWareCategory(WareCategoryBean wcb, SettingLogsBean stl)
	{
		if(WareCategoryDAO.updateWareCate(wcb, stl))
		{
			reloadMap();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 保存信息标签分类排序
	 * @param ids	信息标签分类IDS，多个直接用“,”分隔
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean saveSort(String ids, SettingLogsBean stl)
	{
		boolean flg = true;
		String arrIDS[] = ids.split(",");
		WareCategoryBean wcb = new WareCategoryBean();
		for(int i=0; i<arrIDS.length; i++)
		{
			wcb.setId(arrIDS[i]);
			wcb.setSort_id(i);
			if(!WareCategoryDAO.saveWareCateSort(wcb, stl))
			{
				flg = false;
			}
		}
		reloadMap();
		return flg;
	}
	
	/**
	 * 根据多个分类ID得到它所有的子级ID
	 * @param mp	删除条件
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static String getAllChildCateIDS(Map<String, String> mp)
	{
		String old_ids = mp.get("id");
		String[] arrIDS = old_ids.split(",");
		String ids = "";
		for(int i=0; i<arrIDS.length; i++)
		{
			ids += getAllChildIDS(arrIDS[i], mp);
		}
		// 去掉首位的","号
		if(ids.startsWith(","))
		{
			ids = ids.substring(1);
		}		
		return ids;
	}
	
	
	
	/**
	 * 删除信息标签分类
	 * @param mp	删除条件
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteWareCategory(Map<String, String> mp, SettingLogsBean stl)
	{
		
		String ids = getAllChildCateIDS(mp);		
		mp.put("id", ids);

		if(WareCategoryDAO.deleteWareCate(mp, stl))
		{
			reloadMap();
			// 删除相关联的标签信息
			WareManager.deleteWareByWcatIDS(mp, stl);
			//删除分类与人员关联
			WareReleUserManager.deleteWRUByCat(mp.get("id"),mp.get("site_id"));
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过ID将一个目录移动到另一个下面
	 * @param id	要移动的目录ID
	 * @param parent_id	移动后的父节点ID
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean moveCategory(String id, String parent_id ,SettingLogsBean stl)
	{
		boolean flg = true;
		WareCategoryBean bean = getWareCategoryByID(id);
		WareCategoryBean parentBean = getWareCategoryByID(parent_id);
		
		bean.setParent_id(parent_id);
		bean.setWcat_level(parentBean.getWcat_level()+1);
		bean.setWcat_position(parentBean.getWcat_position()+"$"+bean.getWcat_id());
		
		if( WareCategoryDAO.updateWareCate(bean, stl))
		{
			Map<String, String> mp = new HashMap<String, String>();
			mp.put("site_id", bean.getSite_id());
			mp.put("app_id", bean.getApp_id());
			flg = moveChildList(bean, mp, stl) ? flg: false;
		}
		reloadMap();
		return flg;
	}
	
	/**
	 * 更新指定分类下的所有子分类的信息
	 * @param parentBean	指定分类对象
	 * @param mp
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	private static boolean moveChildList(WareCategoryBean parentBean, Map<String, String> mp, SettingLogsBean stl)
	{
		boolean flg = true;
		List<WareCategoryBean> lt = getChildList(parentBean.getId(), mp);
		if(lt != null && lt.size() > 0)
		{
			for(int i=0; i<lt.size(); i++)
			{
				WareCategoryBean wcb = lt.get(i);
				wcb.setWcat_level(parentBean.getWcat_level() + 1);
				wcb.setWcat_position(parentBean.getWcat_position()+"$"+wcb.getId());
				flg = WareCategoryDAO.updateWareCate(wcb, stl) ? flg : false;
				flg = moveChildList(wcb, mp, stl) ? flg : false;
			}
		}
		return flg;
	}
	
	private static String getAllChildIDS(String id, Map<String, String> mp)
	{
		String ret = ","+id;
		List<WareCategoryBean> lt = getChildList(id, mp);
		if(lt != null && lt.size() > 0)
		{
			for(int i=0; i<lt.size(); i++)
			{
				ret += getAllChildIDS(lt.get(i).getId(), mp);
			}
		}
		return ret;
	}
	
	/**
	 * 取得JsonTree字符串
	 * @return
	 */
	public static String getJSONTreeStr(Map<String, String> mp)
	{
		String site_id = mp.get("site_id")== null ? "" : mp.get("site_id");
		SiteBean stb = SiteManager.getSiteBeanBySiteID(site_id);
		//SiteBean stb = null;
		String rootName = "root";
		if(stb != null)
		{
			rootName = stb.getSite_name();
		}
		
		String child_str = "";
		String json_str = "[{\"id\":0,\"text\":\""+rootName+"\"";
		child_str = getJSONTreeChildStr(getChildList("0", mp), mp);
		if(child_str != null && !"".equals(child_str))
		{
			json_str += ",\"children\":["+child_str+"]";
		}
		json_str += "}]";
		return json_str;
	}
	
	/**
	 * 取得Json数据的子目录
	 * @param lt
	 * @param mp
	 * @return
	 */
	private static String getJSONTreeChildStr(List<WareCategoryBean> lt ,Map<String, String> mp)
	{
		String json_str = "";
		if(lt != null && lt.size() > 0)
		{
			for(int i=0;i<lt.size();i++)
			{				
				json_str += "{";
				json_str += "\"id\":"+lt.get(i).getWcat_id()+",\"text\":\""+lt.get(i).getWcat_name()+"\"";
				List<WareCategoryBean> child_o_list = getChildList(lt.get(i).getWcat_id(), mp);
				if(child_o_list != null && child_o_list.size() > 0)
					json_str += ",\"children\":["+getJSONTreeChildStr(child_o_list, mp)+"]";
				json_str += "}";
				if(i+1 != lt.size())
					json_str += ",";				
			}
		}
		return json_str;
	}

	/**
	 * 根据id取得子分类列表
	 * @param id 信息标签分类ID
	 * @return	自列表ID
	 */
	private static List<WareCategoryBean> getChildList(String id, Map<String, String> mp)
	{		
		List<WareCategoryBean> retList = new ArrayList<WareCategoryBean>();
		Iterator<WareCategoryBean> it = wareCate_map.values().iterator();
		while(it.hasNext())
		{
			WareCategoryBean wcb = it.next();
			if(id.equals(wcb.getParent_id()) && isSameAppAndSite(mp, wcb))
			{
				retList.add(wcb);
			}
		}
		Collections.sort(retList, new WareCateComparator());
		return retList;
	}
	
	/**
	 * 判断是App_id，Site_id是否相等，如果site_id为“”，则不进行比较
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @return	true 相同| false 不相同
	 */
	private static boolean isSameAppAndSite(Map<String, String> mp, WareCategoryBean wcb)
	{
		boolean sflg = false;
		boolean aflg = false;
		String site_id = mp.get("site_id");
		String app_id = mp.get("app_id");
		
		// 判断站点ID，为空直接按相同处理
		if("".equals(site_id))
		{
			sflg = true;
		}
		else if(site_id.equals(wcb.getSite_id()))
		{
			sflg = true;
		}
		
		// 判断应用id，应用ID必须相同
		if(app_id.equals(wcb.getApp_id()))
		{
			aflg = true;
		}
		else
		{
			aflg = false;
		}
		aflg = true;//不分应用，只分站点
		return sflg && aflg;
	}
	
	static class WareCateComparator implements Comparator<WareCategoryBean>{

		@Override
		public int compare(WareCategoryBean o1, WareCategoryBean o2) {
			int flg = 0;
			if(o1.getSort_id() > o2.getSort_id())
			{
				flg = 1;
			}
			else if(o1.getSort_id() == o2.getSort_id())
			{
				flg = 0;
			}
			else
			{
				flg = -1;
			}
			return flg;
		}
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
			System.out.println(getJSONTreeBySiteUser(1,"HIWCM8888"));
		//System.out.println(getJSONTreeStr(1,"11111ddd"));
	}

}
