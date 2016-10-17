package com.deya.wcm.services.zwgk.ser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.category.CateCurPositionBean;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.CategoryModel;
import com.deya.wcm.bean.control.SiteAppBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.dict.DataDictBean;
import com.deya.wcm.bean.zwgk.ser.SerCategoryBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.ser.SerCategoryDAO;
import com.deya.wcm.dao.zwgk.ser.SerResouceDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.CategoryModelManager;
import com.deya.wcm.services.control.site.SiteAppRele;
import com.deya.wcm.services.system.dict.DataDictionaryManager;

/**
 *  场景式服务主题（导航）逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description:场景式服务主题（导航）逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SerCategoryManager implements ISyncCatch{
	public static Map<Integer,SerCategoryBean> scate_map = new HashMap<Integer,SerCategoryBean>();
	private static int ROOT_ID = 0;
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		scate_map.clear();
		List<SerCategoryBean> l = SerCategoryDAO.getAllSerCategoryList();
		if(l != null && l.size() > 0)
		{
			for(SerCategoryBean s : l)
			{
				scate_map.put(s.getSer_id(), s);
			}
		}
	}
	
	public static void reloadSerCategory()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.zwgk.ser.SerCategoryManager");
	}
	
	/**
	 * 得到已发布的根节点分类树
	 * @return String
	 */
	public static String getSerCategoryRootJSONTree()
	{
		String str = "";
		List<SerCategoryBean> s_list = getSerCategoryRootListForPublish();
		if(s_list != null && s_list.size() > 0)
		{
			for(SerCategoryBean s : s_list)
			{
				str += ",{\"id\":"+s.getSer_id()+",\"text\":\""+s.getCat_name()+"\",\"attributes\":{\"url\":\"/sys/ggfw/ser/serCateList.jsp?ser_id="+s.getSer_id()+"\",\"handl\":\"\"}}";
			}
			str = str.substring(1);
		}
		return "["+str+"]";
	}
	
	/**
	 * 根据主题ID得到节点树
	 * @return String
	 */
	public static String getSerCateJSONTree(int ser_id)
	{
		String str = "";
		SerCategoryBean s = getSerCategoryBean(ser_id);
		if(s != null)
		{
			str += "{\"id\":"+s.getSer_id()+",\"text\":\""+s.getCat_name()+"\"";
			String child_str = getChildSerCateJsonStr(s.getSer_id());
			if(child_str != null && !"".equals(child_str))
			{
				str += ",\"children\":["+child_str+"]";
			}
			str += "}";
		}
		return "["+str+"]";
	}
	
	public static String getChildSerCateJsonStr(int ser_id)
	{
		String str = "";
		List<SerCategoryBean> child_l = getChildSerCategoryList(ser_id);
		if(child_l != null && child_l.size() > 0)
		{
			for(SerCategoryBean scb : child_l)
			{
				String icon_str = "";
				if("leaf".equals(scb.getCat_type()))
					icon_str = ",\"iconCls\":\"icon-ser-resouce\"";	
				str += ",{\"id\":"+scb.getSer_id()+",\"text\":\""+scb.getCat_name()+"\""+icon_str+",\"attributes\":{\"cat_type\":\""+scb.getCat_type()+"\"}";
				String child_str = getChildSerCateJsonStr(scb.getSer_id());
				if(child_str != null && !"".equals(child_str))
				{
					str += ",\"children\":["+child_str+"]";
				}
				str += "}";
			}
			str = str.substring(1);
		}		
		return str;
	}
	
	/**
	 * 根据节点ID得到子节点列表
	 * @return List
	 */
	public static List<SerCategoryBean> getChildSerCategoryList(int ser_id)
	{
		List<SerCategoryBean> s_list = new ArrayList<SerCategoryBean>();
		Set<Integer> set = scate_map.keySet();
		for(int i : set)
		{
			SerCategoryBean scb = scate_map.get(i);
			if(scb.getParent_id() == ser_id)
			{
				s_list.add(scb);
			}
		}
		if(s_list != null && s_list.size() > 0)
			Collections.sort(s_list,new SerCategoryComparator());
		return s_list;
	}
	
	/**
     * 根据ser_id得到它所有的子级ser_id(deep+n)
     * @param int ser_id
     * @return String
     * */
	public static String getAllChildSerCategoryIDS(int ser_id)
	{
		String ids = "";
		SerCategoryBean s = getSerCategoryBean(ser_id);
		if(s != null)
		{
			String tree_position = s.getTree_position();
			Set<Integer> set = scate_map.keySet();
			for(int i : set)
			{
				SerCategoryBean scb = scate_map.get(i);
				if(scb.getTree_position().startsWith(tree_position) && scb.getSer_id() != ser_id)
				{
					ids += ","+scb.getSer_id();
				}
			}
			if(!"".equals(ids))
				ids = ids.substring(1);
		}	
		
		return ids;
	}
	
	/**
     * 根据ser_ids得到它所有的子级ID(deep+n)(可用多个ID)
     * @param String ser_ids
     * @return String
     * */
	public static String getAllChildCategoryIDS(String ser_ids)
	{
		String ids = "";
		String[] tempA = ser_ids.split(",");
		for(int i=0;i<tempA.length;i++)
		{
			String c_ids = getAllChildSerCategoryIDS(Integer.parseInt(tempA[i]));
			if(c_ids != null && !"".equals(c_ids))
				ids += ","+c_ids;
		}
		if(ids.length() > 0)
			ids = ids.substring(1);
		
		return ids;
	}
	
	/**
	 * 得到已发布根节点分类列表
	 * @return List
	 */
	public static List<SerCategoryBean> getSerCategoryRootListForPublish()
	{
		List<SerCategoryBean> s_list = new ArrayList<SerCategoryBean>();
		Set<Integer> set = scate_map.keySet();
		for(int i : set)
		{
			SerCategoryBean scb = scate_map.get(i);
			if(scb.getParent_id() == ROOT_ID && scb.getPublish_status() == 1)
			{
				s_list.add(scb);
			}
		}
		if(s_list != null && s_list.size() > 0)
			Collections.sort(s_list,new SerCategoryComparator());
		return s_list;
	}
	
	/**
	 * 得到根节点分类列表
	 * @return List
	 */
	public static List<SerCategoryBean> getSerCategoryRootList()
	{
		return getChildSerCategoryList(ROOT_ID);		
	}
	
	/**
	 * 根据ID得到资源信息对象
	 * @param res_id
	 * @return SerResouceBean
	 */
	public static SerCategoryBean getSerCategoryBean(int ser_id)
	{
		if(ser_id == ROOT_ID)
		{
			SerCategoryBean scb = new SerCategoryBean();
			scb.setSer_id(ROOT_ID);
			scb.setTree_position("$"+ROOT_ID+"$");
			return scb;
		}
		if(scate_map.containsKey(ser_id))
		{
			return scate_map.get(ser_id);
		}
		else
		{
			SerCategoryBean scb = SerCategoryDAO.getSerCategoryBean(ser_id);
			if(scb != null)
			{
				scate_map.put(ser_id, scb);
				return scb;
			}
			else
				return null;
		}
	}
	
	/**
	 * 根据ID得到它的根分类对象
	 * @param ser_id
	 * @return SerResouceBean
	 */
	public static SerCategoryBean getRootSerCategoryBean(int ser_id)
	{
		SerCategoryBean scb = getSerCategoryBean(ser_id);
		if(scb != null)
		{
			String tree_position = scb.getTree_position();
			String[] tempA = tree_position.split("\\$");
			return getSerCategoryBean(Integer.parseInt(tempA[2]));
		}
		else
			return null;		
	}
	
	/**
	 * 根据ID得到资源分类的ID 如果为空是不需要资源分类
	 * @param ser_id
	 * @return boolean
	 */
	public static String getResouceClassBySerID(int ser_id)
	{
		SerCategoryBean root_b = getRootSerCategoryBean(ser_id);
		if(root_b != null)
		{
			return root_b.getDict_id();
		}else
			return "";
	}
	
	/**
	 * 根据ID资源分类列表,对应数据字典中的
	 * @param ser_id
	 * @return List
	 */
	public static List<DataDictBean> getDataDictList(int ser_id)
	{
		String dict_type = getResouceClassBySerID(ser_id);
		if(dict_type != null && !"".equals(dict_type))
		{
			return DataDictionaryManager.getDataDictionaryListOfCategory(dict_type,"");
		}else
			return null;
	}
	
	/**
	 * 插入分类节点
	 * @param SerCategoryBean scb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertSerCategory(SerCategoryBean scb,SettingLogsBean stl)
	{
		if("root".equals(scb.getCat_type()))
		{
			//如果是根节点,需要在信息栏目表中加入相关信息和常见问题的栏目
			insertPeculiarCategory(scb,stl);
		}
		SerCategoryBean parent_b = getSerCategoryBean(scb.getParent_id());
		scb.setTree_position(parent_b.getTree_position()+scb.getSer_id()+"$");
		if(scb.getPublish_status() == 1)
		{
			scb.setPublish_time(DateUtil.getCurrentDateTime());
		}
		if(SerCategoryDAO.insertSerCategory(scb, stl))
		{
			reloadSerCategory();
			return true;
		}else
			return false;
	}
	/**
	 * 向信息分类表中插入特殊栏目 相关信息和常见问题
	 * @param SerCategoryBean scb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static void insertPeculiarCategory(SerCategoryBean scb,SettingLogsBean stl)
	{
		CategoryModel xgxw_cm = new CategoryModel();
		CategoryModel cjwt_cm = new CategoryModel();
		
		CategoryBean cgb = new CategoryBean();
		int id = CategoryManager.getNewCategoryID();
		cgb.setCat_cname("相关信息");
		cgb.setCat_ename("xgxw");		
		cgb.setId(id);
		cgb.setCat_id(id);
		cgb.setApp_id("ggfw");
		cgb.setSite_id("ggfw");
		cgb.setIs_generate_index(0);
		if(CategoryManager.insertCategory(cgb,true,stl))
		{
			scb.setXgwt_cat_id(id);
			xgxw_cm.setCat_id(id);
		}
		
		cgb = new CategoryBean();
		id = CategoryManager.getNewCategoryID();
		cgb.setCat_cname("常见问题");
		cgb.setCat_ename("cjwt");		
		cgb.setId(id);
		cgb.setCat_id(id);
		cgb.setApp_id("ggfw");
		cgb.setSite_id("ggfw");
		cgb.setIs_generate_index(0);
		if(CategoryManager.insertCategory(cgb,true,stl))
		{
			scb.setCjwt_cat_id(id);
			cjwt_cm.setCat_id(id);
		}
		//插入栏目和模板的关联信息
		List<CategoryModel> cm = new ArrayList<CategoryModel>();
		xgxw_cm.setApp_id("ggfw");		
		xgxw_cm.setModel_id(11);
		xgxw_cm.setSite_id("ggfw");
		xgxw_cm.setTemplate_content(getSerTemplateID("content"));
		
		cjwt_cm.setApp_id("ggfw");		
		cjwt_cm.setModel_id(11);
		cjwt_cm.setSite_id("ggfw");
		cjwt_cm.setTemplate_content(getSerTemplateID("content"));
		cm.add(xgxw_cm);
		cm.add(cjwt_cm);
		CategoryModelManager.insertCategoryModel(cm);
	}
		
	/**
	 * 修改分类节点
	 * @param SerCategoryBean scb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerCategory(SerCategoryBean scb,SettingLogsBean stl)
	{
		if(scb.getPublish_status() == 1 && "".equals(scb.getPublish_time()))
		{
			scb.setPublish_time(DateUtil.getCurrentDateTime());
		}
		if(scb.getPublish_status() == 0)
			scb.setPublish_time("");
		if(SerCategoryDAO.updateSerCategory(scb, stl))
		{
			reloadSerCategory();
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改分类状态
	 * @param String ser_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSerCategoryStatus(String ser_ids,String publish_status,SettingLogsBean stl)
	{
		if(SerCategoryDAO.updateSerCategoryStatus(ser_ids,publish_status, stl))
		{
			reloadSerCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 保存排序
     * @param String ser_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortSerCategory(String ids,SettingLogsBean stl)
	{
		if(SerCategoryDAO.sortSerCategory(ids, stl))
		{
			reloadSerCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除节点信息
     * @param String ser_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean deleteSerCategory(String ser_ids,SettingLogsBean stl)
	{		
		String all_child_ids = getAllChildCategoryIDS(ser_ids);
		if(!"".equals(all_child_ids))
			ser_ids = ser_ids+","+all_child_ids;
		if(SerCategoryDAO.deleteSerCategory(ser_ids, stl))
		{
			reloadSerCategory();
			SerResouceDAO.deleteSerResouceByCategory(ser_ids, stl);
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
	public static boolean moveSerCategory(int parent_id,String ser_ids,SettingLogsBean stl)
	{
		SerCategoryBean parent_scb = getSerCategoryBean(parent_id);
		if(parent_scb != null)
		{
			String parent_tree_position = parent_scb.getTree_position();
			if(ser_ids != null && !"".equals(ser_ids))
			{
				try{
					String[] tempA = ser_ids.split(",");
					for(int i=0;i<tempA.length;i++)
					{					
						moveSerCategoryHandl(tempA[i],parent_id+"",parent_tree_position,stl);
					}
					reloadSerCategory();
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
	public static void moveSerCategoryHandl(String ser_id,String parent_id,String tree_position,SettingLogsBean stl)
	{
		String position = tree_position+ser_id+"$";
		Map<String,String> new_m = new HashMap<String,String>();
		new_m.put("ser_id", ser_id);
		new_m.put("parent_id", parent_id);
		new_m.put("tree_position", position);
		if(SerCategoryDAO.moveSerCategory(new_m, stl))
		{
			List<SerCategoryBean> c_list = getChildSerCategoryList(Integer.parseInt(ser_id));
			if(c_list != null && c_list.size() > 0)
			{
				for(int i=0;i<c_list.size();i++)
				{
					moveSerCategoryHandl(c_list.get(i).getSer_id()+"",ser_id,position,stl);
				}
			}
		}
	}
	
	/**
	 * 得到新的分类节点ID
	 * @return int
	 */		
	public static int getNewID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.SER_CATEGORY_TABLE_NAME);
	}
	
	/**
	 * 得到场景式服务的相关信息或常见问题所关联的模型ID
	 * @param String type
	 * @return int
	 */
	public static int getSerTemplateID(String template_type)
	{
		SiteAppBean sab = SiteAppRele.getSiteAppBean("ggfw");
		if(sab != null)
		{
			if("list".equals(template_type))
			{
				if(sab.getMark1() != null && !"".equals(sab.getMark1()))
					return Integer.parseInt(sab.getMark1());
				else
					return 0;
			}
			if("content".equals(template_type))
			{
				if(sab.getMark2() != null && !"".equals(sab.getMark2()))
					return Integer.parseInt(sab.getMark2());
				else
					return 0;
			}
			return 0;
		}else
			return 0;
	}
	
	//得到当前位置
	public static List<CateCurPositionBean> getSerCategoryTreeposition(int ser_id)
	{
		List<CateCurPositionBean> ccpb_list = new ArrayList<CateCurPositionBean>();
		SerCategoryBean scb = getSerCategoryBean(ser_id);
		String tree_position = scb.getTree_position();
		String[] tempA = tree_position.split("\\$");
		if(tempA != null && tempA.length > 0)
		{
			for(int i=2;i<tempA.length;i++)
			{
				SerCategoryBean s = getSerCategoryBean(Integer.parseInt(tempA[i]));
				if(s != null)
				{
					CateCurPositionBean ccpb = new CateCurPositionBean();
					ccpb.setCat_cname(s.getCat_name());
					ccpb.setCat_id(s.getSer_id());
					if("root".equals(s.getCat_type()))
						ccpb.setUrl("serIndex.jsp?ser_id="+s.getSer_id());
					else
						ccpb.setUrl("serList.jsp?ser_id="+s.getSer_id());
					
					ccpb_list.add(ccpb);
				}
			}
		}
		return ccpb_list;
	}
	
	/**
	 * 修改场景式服务内容页模板关联
	 * @param String template_content_id
	 * @return boolean
	 */
	public static boolean updateSerTemplateContent(String template_content_id)
	{
		return SerCategoryDAO.updateSerTemplateContent(template_content_id);
	}
	
	static class SerCategoryComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			SerCategoryBean scb1 = (SerCategoryBean) o1;
			SerCategoryBean scb2 = (SerCategoryBean) o2;
		    if (scb1.getSort_id() > scb2.getSort_id()) {
		     return 1;
		    } else {
		     if (scb1.getSort_id() == scb2.getSort_id()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	public static void main(String[] args)
	{
		//System.out.println(getSerCateJSONTree(10));
		//deleteGKNodeCategory("3",new SettingLogsBean());
		String ss = "$0$1$";
		String[] a = ss.split("\\$");
		System.out.println(a[2]);
	}
}
