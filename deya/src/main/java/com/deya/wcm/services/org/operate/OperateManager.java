package com.deya.wcm.services.org.operate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.bean.org.operate.MenuBean;
import com.deya.wcm.bean.org.operate.OperateBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.org.operate.OperateDAO;
import com.deya.wcm.server.LicenseCheck;

/**
 *  权限注册管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 权限注册管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class OperateManager implements ISyncCatch{
	private static TreeMap<String,OperateBean> opt_map = new TreeMap<String,OperateBean>();
	private static String root_operate_id = "1";
	private static String cms_operate_id = JconfigUtilContainer.systemRole().getProperty("cms_operate", "", "opt_id");//内容操作的权限ID
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<OperateBean> opt_list = OperateDAO.getAllOperateList();
		//System.out.println("OperateManager reloadOperate+++++++++++++++++=="+opt_list);
		opt_map.clear();
		if (opt_list != null && opt_list.size() > 0) {
			for (OperateBean ob : opt_list) {
				if(LicenseCheck.isHaveApp(ob.getApp_id()))
					opt_map.put(ob.getOpt_id() + "", ob);				
			}
		}
	}
	
	/**
	 * 初始加载权限信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadOperate() {
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.operate.OperateManager");
	}
	
	/**
     * 得到权限Map
     * @param
     * @return String
     * */
	public static Map<String,OperateBean> getOptMap()
	{
		return opt_map;
	}
	
	/**
     * 得到权限ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getOperateID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.OPT_TABLE_NAME);
	}
	
	/**
     * 得到所有权限,并组织为json数据
     * @param String app_ids 可多个，用逗号分隔
     * @return String
     * */
	public static String getOperateTreeJsonStr(String app_ids)
	{		
		OperateBean op = getOperateBean(root_operate_id);
		if(op != null)
		{
			String child_str = "";
			String json_str = "[{\"id\":"+root_operate_id+",\"text\":\""+op.getOpt_name()+"\"";
			if("".equals(app_ids) || "system".equals(app_ids))
				child_str = getOperateTreeJsonStrHandl(getChildOptList(root_operate_id));
			else
			{
				if("zwgk".equals(app_ids))
				{
					child_str = getZWGKOpterTreeJsonStrHandl(getChildOptList(root_operate_id,app_ids));
				}else
					child_str = getOperateTreeJsonStrHandl(getChildOptList(root_operate_id,app_ids));
			}
			if(child_str != null && !"".equals(child_str))
				json_str += ",\"children\":["+child_str+"]";
			json_str += "}]";
			return json_str;
		}else
			return "[]";
	}
	
	/**
     * 得到所有权限,并组织为json数据(用于在我的权限中展示权限树)
     * @param String app_ids 可多个，用逗号分隔
     * @return String
     * */
	public static String getOperateTreeJsonStr2(String app_ids)
	{		
		OperateBean op = getOperateBean(root_operate_id);
		if(op != null)
		{
			String child_str = "";
			String json_str = "[";			
			child_str = getOperateTreeJsonStrHandl(getChildOptList(root_operate_id,app_ids));
			if(child_str != null && !"".equals(child_str))
				json_str += child_str;
			json_str += "]";
			return json_str;
		}else
			return "[]";
	}
	
	//得到信息公开的权限树，添加了内容操作，用于角色管理中的权限设置
	public static String getZWGKOpterTreeJsonStrHandl(List<OperateBean> all_oper_list)
	{
		String json_str = "";
		if(all_oper_list != null && all_oper_list.size() > 0)
		{
			for(int i=0;i<all_oper_list.size();i++)
			{
				json_str += "{";
				json_str += "\"id\":"+all_oper_list.get(i).getOpt_id()+",\"text\":\""+all_oper_list.get(i).getOpt_name()+"\"";
				List<OperateBean> child_o_list = getChildOptList(all_oper_list.get(i).getOpt_id()+"");
				if(child_o_list != null && child_o_list.size() > 0)
					json_str += ",\"children\":["+getOperateTreeJsonStrHandl(child_o_list)+","+getCMSOperateTreeJsonStr()+"]";
				json_str += "}";
				if(i+1 != all_oper_list.size())
					json_str += ",";				
			}
			
		}
		return json_str;
	}
	
	//得到内容操作的权限树
	public static String getCMSOperateTreeJsonStr()
	{
		List<OperateBean> l = new ArrayList();
		OperateBean ob = getOperateBean(cms_operate_id);
		l.add(ob);
		return getOperateTreeJsonStrHandl(l);
	}
	
	/**
     * 根据权限集合,组织json字符串
     * @param List<OperateBean> all_oper_list
     * @return String
     * */
	public static String getOperateTreeJsonStrHandl(List<OperateBean> all_oper_list)
	{		
		String json_str = "";
		if(all_oper_list != null && all_oper_list.size() > 0)
		{			
			for(OperateBean ob : all_oper_list)
		{
				json_str += ",{";
				json_str += "\"id\":"+ob.getOpt_id()+",\"text\":\""+ob.getOpt_name()+"\"";
				List<OperateBean> child_o_list = getChildOptList(ob.getOpt_id()+"");
				if(child_o_list != null && child_o_list.size() > 0)
					json_str += ",\"children\":["+getOperateTreeJsonStrHandl(child_o_list)+"]";
				json_str += "}";				
			}
			if(json_str != null && !"".equals(json_str))
				json_str = json_str.substring(1);
		}
		return json_str;
	}
	
	/**
     * 根据权限ID得到对象
     * @param String opt_id
     * @return OperateBean
     * */
	public static OperateBean getOperateBean(String opt_id)
	{
		if("0".equals(opt_id))
			return null;
		if(opt_map.containsKey(opt_id))
		{
			return opt_map.get(opt_id);
		}else
		{
			OperateBean ob = OperateDAO.getOperateBean(opt_id);
			if(ob != null && LicenseCheck.isHaveApp(ob.getApp_id()))
			{
				opt_map.put(opt_id, ob);
				return ob;
			}else
				return null;
		}
	}
	
	/**
     * 根据权限ID得到菜单列表
     * @param String opt_id
     * @return List
     * */
	public static List<MenuBean> getMenuBeanByOptID(String opt_id)
	{
		return MenuManager.getMenuBeanByOptID(opt_id);
	}
	
	/**
     * 根据权限ID得到此节点下的权限节点个数
     * @param String opt_id
     * @return String
     * */
	@SuppressWarnings("unchecked")
	public static String getChildOptCount(String opt_id)
	{
		int count = 0;
		Iterator iter = opt_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();			
			if (opt_id.equals(opt_map.get(key).getParent_id()+"")) {
				count += 1;
			}
		}		
		return count+"";
	}
	
	/**
     * 根据权限ID,应用ID得到此节点下的权限节点列表deep+1
     * @param String opt_id
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<OperateBean> getChildOptList(String opt_id,String app_ids)
	{
		List<OperateBean> oL = new ArrayList<OperateBean>();
		Iterator iter = opt_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			
			if (opt_id.equals(opt_map.get(key).getParent_id()+"") && app_ids.contains(opt_map.get(key).getApp_id())) {
				oL.add((OperateBean)entry.getValue());
			}
		}		
		Collections.sort(oL,new OperateComparator());
		return oL;
	}
			
	/**
     * 根据权限ID得到此节点下的权限节点列表deep+1
     * @param String opt_id
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<OperateBean> getChildOptList(String opt_id)
	{
		List<OperateBean> oL = new ArrayList<OperateBean>();
		Iterator iter = opt_map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			
			if (opt_id.equals(opt_map.get(key).getParent_id()+"")) {
				oL.add((OperateBean)entry.getValue());
			}
		}		
		Collections.sort(oL,new OperateComparator());
		return oL;
	}
	
	/**
	 * 根据权限ID得到此节点下的子节点ID deep+n(可输入多个菜单ID,一般用于删除子节点),
	 * @param String menu_id
	 * @return List
	 */
	public static String getALLChildOptIDSByID(String opt_ids)
	{
		String o_ids = "";
		if(opt_ids != null && !"".equals(opt_ids))
		{
			String[] opt_a = opt_ids.split(",");
			for(int i=0;i<opt_a.length;i++)
			{
				OperateBean ob = getOperateBean(opt_a[i]);
				if(ob != null)
				{
					String tree_position = ob.getTree_position();
					Set<String> set = opt_map.keySet();
					for(String j : set){
						ob = opt_map.get(j);
						if(ob.getTree_position().startsWith(tree_position) && !tree_position.equals(ob.getTree_position()))
							o_ids += ","+ob.getOpt_id();
					}
				}
			}
		}
		return o_ids;
	}
	
	/**
     * 插入权限信息
     * @param OperateBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertOperate(OperateBean ob,SettingLogsBean stl)
	{
		ob.setTree_position(getOperateBean(ob.getParent_id()+"").getTree_position());
		if(OperateDAO.insertOperate(ob, stl))
		{
			reloadOperate();
			return true;
		}else{
			return false;
		}
	}
	

	/**
     * 修改权限信息
     * @param OperateBean ob
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateOperate(OperateBean ob,SettingLogsBean stl)
	{
		if(OperateDAO.updateOperate(ob, stl))
		{
			reloadOperate();
			return true;
		}else{
			return false;
		}
	}
	
	/**
     * 移动权限
     * @param String parent_id
     * @param String opt_ids
     * @return boolean
     * */
	public static boolean moveOperate(String parent_id,String opt_ids,SettingLogsBean stl)
	{
		String parent_tree_position = getOperateBean(parent_id).getTree_position();
		if(opt_ids != null && !"".equals(opt_ids))
		{
			try{
				String[] tempA = opt_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{				
					moveOperateHandl(tempA[i],parent_id,parent_tree_position,stl);
				}
				reloadOperate();
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
     * 根据角色ID得到关联的权限所对应的应用系统ID
     * @param String role_ids
     * @return String
     * */
	public static List<AppBean> getOptAppListbyRoleID(String role_ids)
	{
		return OperateDAO.getOptAppListbyRoleID(role_ids);
	}
	
	public static void moveOperateHandl(String opt_id,String parent_id,String tree_position,SettingLogsBean stl)
	{
		String position = tree_position+opt_id+"$";
		Map<String,String> new_m = new HashMap<String,String>();
		new_m.put("opt_id", opt_id);
		new_m.put("parent_id", parent_id);
		new_m.put("tree_position", position);
		if(OperateDAO.moveOperate(new_m,stl)){
			//该节点下的子节点一并转移
			List<OperateBean> o_list = getChildOptList(opt_id);
			if(o_list != null && o_list.size() > 0)
			{
				for(int i=0;i<o_list.size();i++)
				{
					moveOperateHandl(o_list.get(i).getOpt_id()+"",opt_id,position,stl);
				}
			}
		}
	}
	
	/**
     * 删除权限信息
     * @param String opt_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteOperate(String opt_id,SettingLogsBean stl)
	{
		opt_id = opt_id + getALLChildOptIDSByID(opt_id);
		if(OperateDAO.deleteOperate(opt_id, stl))
		{
			reloadOperate();
			return true;
		}else{
			return false;
		}
	}
	
	static class OperateComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			OperateBean ob1 = (OperateBean) o1;
			OperateBean ob2 = (OperateBean) o2;
		    if (ob1.getOpt_id() > ob2.getOpt_id()) {
		     return 1;
		    } else {
		     if (ob1.getOpt_id() == ob2.getOpt_id()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	public static void main(String args[])
	{
		//insertOperateTest();
		//updateOperateTest();		
		System.out.println(getOperateTreeJsonStr("zwgk"));
		//deleteOperate("101",new SettingLogsBean());
		//System.out.println(getCMSOperateTreeJsonStr());
	}
	
}
