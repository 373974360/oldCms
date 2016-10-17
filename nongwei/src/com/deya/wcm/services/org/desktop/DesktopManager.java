package com.deya.wcm.services.org.desktop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.org.desktop.DeskTopBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.org.desktop.DesktopDAO;

/**
 *  组织机构用户组管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构用户组管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class DesktopManager implements ISyncCatch{
	private static Map<Integer,List<DeskTopBean>> desk_map = new HashMap<Integer,List<DeskTopBean>>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}

	public static void reloadCatchHandl()
	{
		try{
			desk_map.clear();
			List<DeskTopBean> l = DesktopDAO.getUserDesktopList();
			if(l != null && l.size() > 0)
			{
				for(DeskTopBean dtb : l)
				{
					if(desk_map.containsKey(dtb.getUser_id()))
					{
						desk_map.get(dtb.getUser_id()).add(dtb);
					}else
					{
						List<DeskTopBean> d_l = new ArrayList<DeskTopBean>();
						d_l.add(dtb);
						desk_map.put(dtb.getUser_id(), d_l);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void reloadDesktop()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.desktop.DesktopManager");
	}
	
	/**
     * 根据用户ID得到配置列表
     * @param int user_id
     * @return List
     * */
	public static List<DeskTopBean> getUserDesktop(int user_id)
	{
		if(desk_map.containsKey(user_id))
			return desk_map.get(user_id);
		else
			return null;
	}
	
	/**
     * 插入配置信息
     * @param  String user_id
     * @param  List<DeskTopBean> l
     * @return boolean
     * */
	public static boolean insertUserDesktop(String user_id,List<DeskTopBean> l)
	{
		if(DesktopDAO.deleteUserDesktop(user_id))
		{
			try{
				if(l != null && l.size() > 0)
				{
					for(DeskTopBean dtb : l)
					{
						dtb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.DESKTOP_TABLE_NAME));
						DesktopDAO.insertUserDesktop(dtb);
					}
				}				
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			reloadDesktop();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除配置信息
     * @param  user_ids
     * @return boolean
     * */
	public static boolean deleteUserDesktop(String user_ids)
	{
		if(DesktopDAO.deleteUserDesktop(user_ids))
		{
			reloadDesktop();
			return true;
		}else
			return false;
	}
}
