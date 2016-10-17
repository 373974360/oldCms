package com.deya.wcm.services.system.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.jconfig.JconfigFactory;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.template.TemplateCategoryBean;
import com.deya.wcm.bean.system.template.TemplateEditBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.template.TemplateCategoryDAO;

/**
 * @author 符江波
 * @date   2011-3-24 上午10:19:00
 */
public class TemplateCategoryManager implements ISyncCatch{
	private static String right_main_page_path = JconfigFactory.getJconfigUtilInstance("velocityTemplate").getProperty("path", "", "templatePagePath");

	private static Map<String, TemplateCategoryBean> templateCategory = new HashMap<String, TemplateCategoryBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		templateCategory.clear();
		List<TemplateCategoryBean> tc_list = getAllTemplateCategoryList();
		if(tc_list != null && tc_list.size() > 0)
		{
			for(TemplateCategoryBean tcb : tc_list)
			{
				templateCategory.put(tcb.getTcat_id()+"_"+tcb.getSite_id(),tcb);
			}
		}
	}
	
	public static void reloadTemplateCategory()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.template.TemplateCategoryManager");
	}
	
	public static int getNewID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.TEMPLATE_CATEGORY_TABLE_NAME);
	}
	
	/**
	 * 根据ID得到模板信息
	 * @param int t_id
	 * @return TemplateCategoryBean
	 * */
	public static TemplateCategoryBean getTemplateCategoryBean(int t_id, String site_id, String app_id)
	{
		if(templateCategory.containsKey(t_id+"_"+site_id))
		{
			return templateCategory.get(t_id+"_"+site_id);
		}else
		{
			TemplateCategoryBean tcb = TemplateCategoryDAO.getTemplateCategoryBean(t_id,site_id);
			if(tcb != null)
			{
				templateCategory.put(t_id+"_"+site_id,tcb);
				return tcb;
			}
			else
				return null;
		}
	}

	/**
	 * 得到模板总数
	 * @param 
	 * @return String
	 * */
	public static String getTemplateCategoryCount(Map<String,String> con_map)
	{
		return TemplateCategoryDAO.getTemplateCategoryCount(con_map);
	}

	/**
	 * 修改模板
	 * @param author
	 * @return boolean
	 */
	public static boolean updateTemplateCategory(TemplateCategoryBean template, SettingLogsBean stl){
		if(TemplateCategoryDAO.updateTemplateCategory(template, stl)){
			reloadTemplateCategory();
			return true;
		}
		return false;
	}

	/**
	 * 添加模板
	 * @param template
	 * @return boolean
	 */
	public static boolean addTemplateCategory(TemplateCategoryBean template, SettingLogsBean stl){
		if(template == null){
			return false;
		}
		if(TemplateCategoryDAO.addTemplateCategory(template,stl)){
			reloadTemplateCategory();
			return true;
		}
		return false;
	}
	
	/**
     * 保存排序
     * @param String ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortTemplateCategory(String ids,SettingLogsBean stl)
	{
		if(TemplateCategoryDAO.sortTemplateCategory(ids, stl))
		{
			reloadTemplateCategory();
			return true;
		}else
			return false;
	}

	/**
	 * 所有模板列表
	 * @return List<TemplateCategoryBean>
	 */
	public static List<TemplateCategoryBean> getAllTemplateCategoryList()
	{
		return TemplateCategoryDAO.getAllTemplateCategoryList();
	}

	/**
	 * 得到模板列表
	 * @param con_map
	 * @return List<TemplateCategoryBean>
	 */
	public static List<TemplateCategoryBean> getTemplateCategoryListForDB(Map<String,String> con_map)
	{
		return TemplateCategoryDAO.getTemplateCategoryListForDB(con_map);
	}
	
	

	/**
	 * 删除模板,级联删除他下边的所有模板
	 * @param t_ids
	 * @return boolean
	 */
	public static boolean delTemplateCategoryById(String t_ids, String site_id, SettingLogsBean stl){
		if(t_ids == null || t_ids.equals("")){
			return false;
		}

		t_ids = getAllChildTemplateCategory(t_ids, site_id, "");//得到目录列表及其子列表
		
		if(TemplateCategoryDAO.delTemplateCategory(t_ids,site_id, stl)){
			if(t_ids.indexOf(",") != -1){
				for(String id : t_ids.split(",")){
					templateCategory.remove(id+"_"+site_id);
					List<TemplateEditBean> list = TemplateEditManager.getAllTemplateEditList(id, site_id, "app_id");
					String tids = "";
					for(TemplateEditBean teb : list){
						tids += ","+teb.getT_id();
					}
					TemplateEditManager.delTemplateEditById(tids.substring(1), site_id, stl);
				}
			}else{
				for(String t_id : t_ids.split(",")){
					templateCategory.remove(t_id+"_"+site_id);
					List<TemplateEditBean> list = TemplateEditManager.getAllTemplateEditList(t_id, site_id, "app_id");
					String tids = "";
					for(TemplateEditBean teb : list){
						tids += ","+teb.getT_id();
					}
					if(tids.startsWith(",")){
						tids = tids.substring(1);
					}
					TemplateEditManager.delTemplateEditById(tids, site_id, stl);
				}
			}
			reloadTemplateCategory();
			return true;
		}
		return false;
	}
	
	public static String getAllChildTemplateCategory(String id, String site_id, String appid){		
		List<TemplateCategoryBean> db = getChildList(id,site_id);	
		if(db != null)
		{
			String json_str = id;
			String child_str = getAllChildTemplateCategory2(db,"");
			if(child_str != null && !"".equals(child_str))
				json_str += ","+child_str;
			return json_str;
		}else
			return "";
	}
	
	public static String getAllChildTemplateCategory2(List<TemplateCategoryBean> dl,String loop_type)
	{
		String json_str = "";
		if(dl != null && dl.size() > 0)
		{			
			for(int i=0;i<dl.size();i++)
			{				
				List<TemplateCategoryBean> child_d_list = getChildList(dl.get(i).getTcat_id()+"",dl.get(i).getSite_id());
				
				json_str += dl.get(i).getTcat_id();
				
				if(child_d_list != null && child_d_list.size() > 0)
					json_str += ","+getAllChildTemplateCategory2(child_d_list,"");
				if(i+1 != dl.size())
					json_str += ",";
			}			
		}
		return json_str;
	}
	
	public static List<TemplateCategoryBean> getChildList(String parent_id, String site_id)
	{
		List<TemplateCategoryBean> tc_list = new ArrayList<TemplateCategoryBean>();
		Set<String> s = templateCategory.keySet();
		for(String i : s)
		{
			if(i.substring(i.indexOf("_")+1).equals(site_id))
			{
				TemplateCategoryBean tcb = templateCategory.get(i);
				if(parent_id.equals(tcb.getParent_id()+""))
					tc_list.add(tcb);
			}
		}
		if(tc_list != null && tc_list.size() > 0)
			Collections.sort(tc_list,new TemplateCategoryComparator());
		return tc_list;
	}
	
	
	public static String templateCategoryListToJsonStr(String id, String site_id, String appid) {
			
		List<TemplateCategoryBean> db = getChildList(id,site_id);	
		if(db != null)
		{
			String json_str = "[{\"id\":"+id+",\"iconCls\":\"icon-org\", \"text\":\"模板管理\",\"attributes\":{\"url\":\""
				+right_main_page_path+"?tid="+id+"\"}";
			String child_str = tcListToStrHandl(db,"");
			if(child_str != null && !"".equals(child_str))
				json_str += ",\"children\":["+child_str+"]";
			json_str += "}]";
			return json_str;
		}else
			return "[]";		
	}
	
	public static String tcListToStrHandl(List<TemplateCategoryBean> dl,String loop_type)
	{
		String json_str = "";
		String icon_str = "\"iconCls\":\"icon-user\",";
		String child_state = "";		
		if(dl != null && dl.size() > 0)
		{			
			for(int i=0;i<dl.size();i++)
			{
//				if(dl.get(i).getId() == root_node_id)
//				{
//					icon_str = "\"iconCls\":\"icon-org\"";	
//				}else
					icon_str = "\"iconCls\":\"icon-templateFolder\"";	
				
				json_str += "{";				
				List<TemplateCategoryBean> child_d_list = getChildList(dl.get(i).getTcat_id()+"",dl.get(i).getSite_id());
				if(child_d_list != null && child_d_list.size() > 0 && !"one".equals(loop_type))
					child_state = "\"state\":'closed',";
				else
					child_state = "";
				
				json_str += "\"id\":"+dl.get(i).getTcat_id()+","+icon_str+","+child_state+"\"text\":\""+dl.get(i).getTcat_cname()+"\",\"attributes\":{\"url\":\""
					+right_main_page_path+"?tid="+dl.get(i).getTcat_id()+"\"}";
				
				if(child_d_list != null && child_d_list.size() > 0)
					json_str += ",\"children\":["+tcListToStrHandl(child_d_list,"")+"]";
				json_str += "}";
				if(i+1 != dl.size())
					json_str += ",";
			}			
		}
		return json_str;
	}
	
	static class TemplateCategoryComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			TemplateCategoryBean cgb1 = (TemplateCategoryBean) o1;
			TemplateCategoryBean cgb2 = (TemplateCategoryBean) o2;
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
	
	/**
	 * 新增站点时添加默认专题类的模板分类
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean addDefaultZTCategory(String site_id)
	{
		TemplateCategoryBean cgb = new TemplateCategoryBean();
		cgb.setId(getNewID());
		cgb.setTcat_id(1);
		cgb.setTcat_cname("新闻模版");
		cgb.setTcat_position("/");
		cgb.setApp_id("0");
		cgb.setSite_id(site_id);
		return addTemplateCategory(cgb,null);
		
	}
	
	public static void main(String[] args) {
		String str = templateCategoryListToJsonStr("0","CMSlik","");
		System.out.println(str);
	}
}
