package com.deya.wcm.services.system.ware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.bean.system.ware.WareInfoBean;
import com.deya.wcm.bean.system.ware.WareInfoRef;
import com.deya.wcm.bean.system.ware.WareInfos;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.ware.WareDAO;
import com.deya.wcm.dao.system.ware.WareInfoDAO;

/**
 *  标签与信息关联逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 标签与信息关联逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class WareInfoManager {
	/*********************** 信息推荐处理　开始 ********************************/
	/**
	 * 根据标签ID，应用ID，站点ID得到它下面的推荐信息总数
	 * @param Map m
	 * @return	List
	 */
	public static String getWareInfoRefCount(Map<String,String> m)
	{
		return WareInfoDAO.getWareInfoRefCount(m);
	}
	
	/**
	 * 根据标签ID，应用ID，站点ID得到它下面的推荐信息
	 * @param Map<String,String> m
	 * @return	List
	 */	
	public static List<InfoBean> getWareInfoRefList(Map<String,String> m)
	{		
		return WareInfoDAO.getWareInfoRefList(m);
	}
	
	/**
	 * 根据信息ID，站点，应用ID得到标签列表
	 * @param String info_id
	 * @param String app_id
	 * @param String site_id
	 * @return	List
	 */	
	public static List<WareBean> getWareListByRefInfo(String info_id,String app_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("info_id", info_id);
		m.put("app_id", app_id);
		m.put("site_id", site_id);
		return WareInfoDAO.getWareListByRefInfo(m);
	}
	
	/**
	 * 添加推荐信息
	 * @param String info_ids
	 * @param int ware_id
	 * @param int user_id
	 * @param String app_id
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean insertWareInfoRef(String info_ids,int ware_id,int user_id,String app_id,String site_id,SettingLogsBean stl)
	{
		try{
			if(info_ids != null && !"".equals(info_ids))
			{
				String[] tempA = info_ids.split(",");
				WareInfoRef wir = new WareInfoRef();
				wir.setWare_id(ware_id);
				wir.setSite_id(site_id);
				wir.setApp_id(app_id);
				wir.setRef_user(user_id);
				wir.setUpdate_dtime(DateUtil.getCurrentDateTime());
				for(int i=0;i<tempA.length;i++)
				{					
					wir.setInfo_id(Integer.parseInt(tempA[i]));
					WareInfoDAO.insertWareInfoRef(wir);
				}
				PublicTableDAO.insertSettingLogs("添加", "推荐信息", info_ids+"", stl);
				return true;
			}
			return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除推荐信息
	 * @param Map m
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfoRef(String info_ids,String ware_ids,String app_id,String site_id,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();		
		m.put("app_id", app_id);
		m.put("site_id", site_id);
		if(info_ids != null && !"".equals(info_ids.trim()))
			m.put("info_ids", info_ids);
		if(ware_ids != null && !"".equals(ware_ids.trim()))
			m.put("ware_ids", ware_ids);
		
		return WareInfoDAO.deleteWareInfoRef(m, stl);
	}	
	
	/*********************** 信息推荐处理　 结束 ********************************/
	
	/*********************** 手动标签信息处理　 开始 ********************************/
	/**
	 * 添加手动标签行数(用于添加标签时添加关联行数)
	 * @param int ware_id
	 * @param int row_num 行数
	 * @param String app_id
	 * @param String site_id
	 * @return	boolean
	 */
	public static boolean insertWareInfoByRowNum(int ware_id,int row_num,String app_id,String site_id)
	{
		try{
			WareInfoBean wib = new WareInfoBean();
			wib.setApp_id(app_id);
			wib.setSite_id(site_id);
			wib.setWare_id(ware_id);
			for(int i=0;i<row_num;i++)
			{
				wib.setSort_id(i+1);
				WareInfoDAO.insertWareInfo(wib);
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加手动标签行数(用于在维护标签内容时添加)
	 * @param int ware_id
	 * @param int sort_id
	 * @param int info_num 目前总行数
	 * @param String app_id
	 * @param String site_id
	 * @return	int
	 */
	public static int insertWareInfo(int ware_id,int sort_id,int info_num,String app_id,String site_id)
	{
		WareInfoBean wib = new WareInfoBean();
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.WARE_INFO_TABLE_NAME);
		wib.setWinfo_id(id);
		wib.setApp_id(app_id);
		wib.setSite_id(site_id);
		wib.setWare_id(ware_id);		
		wib.setSort_id(sort_id);
		if(WareInfoDAO.insertWareInfo(wib))
		{
			Map<String,String> m = new HashMap<String,String>();
			m.put("app_id", app_id);
			m.put("site_id", site_id);
			m.put("ware_id", ware_id+"");
			m.put("info_num", info_num+"");
			WareDAO.updateWareInfoNum(m);
			return id;
		}else
			return 0;		
	}
	
	/**
	 * 保存标签行排序
	 * @param String winfo_ids
	 * @return	boolean
	 */
	public static boolean sortWareInfo(String winfo_ids)
	{
		return WareInfoDAO.sortWareInfo(winfo_ids);
	}
	
	/**
	 * 根据行号ID删除标签关联
	 * @param int ware_id
	 * @param String winfo_id
	 * @param int info_num 目前总行数
	 * @param String app_id
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfoByWInfoID(int ware_id,String winfo_id,int info_num,String app_id,String site_id,SettingLogsBean stl)
	{
		if(WareInfoDAO.deleteWareInfoByWInfoID(winfo_id, stl))
		{
			Map<String,String> m = new HashMap<String,String>();
			m.put("app_id", app_id);
			m.put("site_id", site_id);
			m.put("ware_id", ware_id+"");
			m.put("info_num", info_num+"");
			WareDAO.updateWareInfoNum(m);
			return true;
		}else
			return false;
	}
	
	
	/**
	 * 根据标签ID得到手动标签列表
	 * @param Map<String,String> m
	 * @return	boolean
	 */	
	public static List<WareInfoBean> getWareInfoList(Map<String,String> m)
	{
		List<WareInfoBean> l = WareInfoDAO.getWareInfoList(m);
		if(l != null && l.size() > 0)
		{
			for(WareInfoBean wib : l)
			{
				List<WareInfos> info_list = wib.getInfos_list();
				if(info_list != null && info_list.size() > 0)
				{
					for(WareInfos info : info_list)
					{
						info.setTitle(info.getTitle().replaceAll("<[Bb][Rr]/?>", ""));
					}
				}
			}
		}
		return l;
	}
	
	/**
	 * 根据信息ID得到标签关联的信息内容
	 * @param int id
	 * @return	WareInfos
	 */
	public static WareInfos getWareInfosBean(int id)
	{
		return WareInfoDAO.getWareInfosBean(id);
	}
	
	/**
	 * 得到手动标签行ID	
	 * @return	int
	 */
	public static int getNewWareInfosID()
	{
		return WareInfoDAO.getNewWareInfosID();
	}
	
	/**
	 * 插入标签内容
	 * @param String winfo_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean insertWareInfos(WareInfos wif,SettingLogsBean stl)
	{		
		return WareInfoDAO.insertWareInfos(wif, stl);
	}
	
	/**
	 * 修改标签内容
	 * @param String winfo_id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean updateWareInfos(WareInfos wif,SettingLogsBean stl)
	{
		return WareInfoDAO.updateWareInfos(wif, stl);
	}
	
	/**
	 * 保存标签信息排序
	 * @param String ids
	 * @return	boolean
	 */
	public static boolean sortWInfos(String ids)
	{
		return WareInfoDAO.sortWInfos(ids);
	}
	
	/**
	 * 根据ID删除标签关联信息
	 * @param int id
	 * @param SettingLogsBean stl
	 * @return	boolean
	 */
	public static boolean deleteWareInfosByID(int id,SettingLogsBean stl)
	{
		return WareInfoDAO.deleteWareInfosByID(id, stl);
	}
	/*********************** 手动标签信息处理　 结束 ********************************/
	
	public static void main(String args[])
	{//System.out.println(deleteWareInfoRef("339,346","10","0","11111ddd",new SettingLogsBean()));
		Map m = new HashMap();
		m.put("ware_id", "115");
		m.put("app_id", "cms");
		m.put("site_id", "11111ddd");		
		//System.out.println(getWareInfoList(m).get(5).getInfos_list());
	}
}
