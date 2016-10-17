package com.deya.wcm.services.system.ware;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.ware.WareDAO;
import com.deya.wcm.dao.system.ware.WareInfoDAO;
import com.deya.wcm.rmi.file.FileRmiFactory;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.template.velocity.impl.VelocityWareContextImp;

public class WareManager implements ISyncCatch{
	
	private static Map<String, WareBean> ware_map = new HashMap<String, WareBean>();
	
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
			List<WareBean> lt = WareDAO.getAllWareList();
			ware_map.clear();
			if(lt != null)
			{
				for(int i=0; i<lt.size(); i++)
				{
					ware_map.put(lt.get(i).getId(), lt.get(i));
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化加载缓存
	 */
	public static void reloadMap()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.ware.WareManager");
	}
	
	/**
	 * 根据站点ID和标签类型，得到标签列表
	 * @param site_id	信息标签ID
	 * @param ware_type	信息标签ID
	 * @return	List
	 */
	public static List<WareBean> getWareListByType(String site_id,int ware_type)
	{
		List<WareBean> l = new ArrayList<WareBean>();
		Set<String> s = ware_map.keySet();
		for(String i:s)
		{
			WareBean w = ware_map.get(i);
			if(w.getSite_id().equals(site_id) && w.getWare_type() == ware_type)
			{
				l.add(w);
			}
		}
		return l;
	}
	
	/**
	 * 根据站点ID和分类ID,标签类型，得到标签列表
	 * @param site_id	信息标签ID
	 * @param ware_type	信息标签ID
	 * @return	List
	 */
	public static List<WareBean> getWareListByTypeAndCateID(Map<String, String> mp,int ware_type)
	{		
		String cat_id = WareCategoryManager.getAllChildCateIDS(mp);		
		return getWareListByCateType(cat_id,ware_type,mp);
	}
	
	/**
	 * 通过ID取得信息标签
	 * @param id	信息标签ID
	 * @return	信息标签
	 */
	public static WareBean getWareByID(String id)
	{
		return WareDAO.getWareByID(id);
	}
	
	/**
	 * 根据ware_id，site_id 得到组件对象
	 * @param String ware_id
	 * @param String site_id
	 * @return	信息标签
	 */
	public static WareBean getWareBeanByWareID(String ware_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ware_id", ware_id);
		m.put("site_id", site_id);
		return WareDAO.getWareBeanByWareID(m);
	}
	
	/**
	 * 插入信息标签
	 * @param wb	信息标签
	 * @param stl
	 * @return	true 成功| false 失败
	 * @throws IOException 
	 */	
	public static boolean insertWare(WareBean wb, SettingLogsBean stl) throws IOException
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.WARE_TABLE_NAME);
		wb.setId(id+"");
		wb.setWare_id(id+"");
		if(WareDAO.insertWare(wb, stl))
		{	//如果是手动标签，需要在动标签信息主表中加入相应的行数信息
			if(wb.getWare_type() == 2)
			{
				WareInfoManager.insertWareInfoByRowNum(id,wb.getInfo_num(),wb.getApp_id(),wb.getSite_id());
			}
			reloadMap();
			if((wb.getWare_type() == 0 || wb.getWare_type() == 1) && !"".equals(wb.getWare_content().trim()))
				createWarePage(wb);
			return true;
		}else
		{
			return false;
		}
	}
	
	/**
	 * 修改信息标签内容
	 * @param wb	信息标签对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateWareContent(WareBean wb, SettingLogsBean stl)
	{
		if(WareDAO.updateWareContent(wb, stl))
		{
			try{
				createWarePage(wb);
				return true;
			}catch(IOException e)
			{
				return false;
			}
		}
		else
			return false;
	}
	
	/**
     * 批量生成静态页面　
     * @param List
     * @return boolean
	 * @throws IOException 
     * */
	public static boolean createHtmlPageMutil(String ware_ids,String site_id)
	{
		if(ware_ids != null && !"".equals(ware_ids))
		{
			try{
				String[] arrTemp = ware_ids.split(",");
				for(int i=0;i<arrTemp.length;i++)
				{
					createWarePage(arrTemp[i],site_id);
				}				
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}else
			return true;
	}
	
		
	/**
     * 生成静态页面　
     * @param int id
     * @return boolean
	 * @throws IOException 
     * */
	public static void createWarePage(WareBean wb) throws IOException
	{
		FileRmiFactory.createWarePage(wb);
	}
	
	public static void createWarePageHandl(WareBean wb) throws IOException
	{
		SiteBean sb = SiteManager.getSiteBeanBySiteID(wb.getSite_id());
		if(sb != null)
		{
			String save_path = sb.getSite_path()+"/include";
			save_path = FormatUtil.formatPath(save_path+"/ware_"+wb.getWare_id()+".htm");
			String content = "";
			VelocityWareContextImp vwc = new VelocityWareContextImp(wb);			
			content = vwc.parseTemplate(wb.getWare_content().replaceAll("\\$site_id", wb.getSite_id()));
			
			FileOperation.writeStringToFile(save_path,content,false,"utf-8");
			
			Map<String,String> m = new HashMap<String,String>();
			m.put("update_dtime", DateUtil.getCurrentDateTime());
			m.put("id", wb.getId());
			
			//静态的不记录下次更新时间
			if(wb.getWare_type() > 0)
			{					
				m.put("next_dtime", DateUtil.getDateTimeAfter(DateUtil.getCurrentDateTime(),wb.getWare_interval()));
			}
			WareDAO.updateWareTime(m);
		}	
	}
	
	/**
     * 生成静态页面　
     * @param String ware_id
     * @param String site_id
     * @return boolean
     * */
	public static boolean createWarePage(String ware_id,String site_id)
	{
		WareBean wb = getWareBeanByWareID(ware_id,site_id);
		if(wb == null)
		{
			return false;
		}
		else
		{
			try {
				createWarePage(wb);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}		
	}
	
	/**
	 * 修改信息标签
	 * @param wb	信息标签
	 * @param stl
	 * @return	true 成功| false 失败
	 * @throws IOException 
	 */
	public static boolean updateWare(WareBean wb, SettingLogsBean stl) throws IOException
	{
		if(WareDAO.updateWare(wb, stl))
		{
			reloadMap();
			if(wb.getWare_type() == 0 || wb.getWare_type() == 1 || wb.getWare_type() == 2)
				createWarePage(wb);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 保存信息标签的排序
	 * @param wb	信息标签
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean savaWareSort(String ids, SettingLogsBean stl)
	{
		boolean flg = true;
		String[] arrIDS = ids.split(",");
		for(int i=0; i<arrIDS.length; i++)
		{
			WareBean wb = new WareBean();
			wb.setId(arrIDS[i]);
			wb.setSort_id(i);
			flg = WareDAO.savaWareSort(wb, stl) ? flg : false;
		}
		reloadMap();
		return flg;
	}
	
	/**
	 * 删除标签
	 * @param mp
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteWare(Map<String, String> mp, SettingLogsBean stl)
	{
		if(WareDAO.deleteWare(mp, stl))
		{
			reloadMap();
			WareInfoDAO.deleteWareInfoByWareID(mp, stl);
			WareInfoDAO.deleteWareInfosByWareID(mp, stl);
			//删除标签页面
			deleteWarePage(mp);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 通过信息标签分类ID删除信息标签
	 * @param mp
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteWareByWcatIDS(Map<String, String> mp, SettingLogsBean stl)
	{
		if(WareDAO.deleteWareByWcatIDS(mp, stl))
		{
			mp.put("ware_id", getWareIDSByCat(mp.get("id"),mp));
			deleteWarePage(mp);
			reloadMap();			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 删除标签页面
	 * @param mp	 * 
	 * @return
	 */
	public static void deleteWarePage(Map<String,String> mp)
	{		
		String[] tempA = mp.get("ware_id").split(",");
		SiteBean sb = SiteManager.getSiteBeanBySiteID(mp.get("site_id"));
		String save_path = sb.getSite_path()+"/include";
		if(sb != null)
		{
			for(int i=0;i<tempA.length;i++)
			{						
				save_path = FormatUtil.formatPath(save_path+"/ware_"+tempA[i]+".htm");
				//File f = new File(save_path);
				//f.delete();
				FileRmiFactory.delFile(mp.get("site_id"), save_path);
			}
		}
	}
	
	/**
	 * 根据信息标签分类ID取得信息标签列表
	 * @param mp
	 * @return
	 */
	public static List<WareBean> getWareList(String wcat_id, Map<String, String> mp)
	{
		List<WareBean> retList = new ArrayList<WareBean>();
		Iterator<WareBean> it = ware_map.values().iterator();
		while(it.hasNext())
		{
			WareBean wb = it.next();
			if(wcat_id.equals(wb.getWcat_id()) && isSameAppAndSite(mp, wb))
			{
				retList.add(wb);
			}
		}
		Collections.sort(retList, new WareComparator());
		return retList;
	}
	
	/**
	 * 根据信息标签分类ID,类型取得信息标签列表
	 * @param String wcat_ids 可多个
	 * @param int ware_type
	 * @param Map<String, String> mp
	 * @return
	 */
	public static List<WareBean> getWareListByCateType(String wcat_ids,int ware_type, Map<String, String> mp)
	{
		List<WareBean> retList = new ArrayList<WareBean>();
		Iterator<WareBean> it = ware_map.values().iterator();
		wcat_ids = ","+wcat_ids+",";
		while(it.hasNext())
		{
			WareBean wb = it.next();
			if(wb.getWare_type() == ware_type && wcat_ids.contains(","+wb.getWcat_id()+",") && isSameAppAndSite(mp, wb))
			{
				retList.add(wb);
			}
		}
		Collections.sort(retList, new WareComparator());
		return retList;
	}
	
	/**
	 * 根据信息标签分类ID取得信息标签ID
	 * @param String wcat_id (可多个)
	 * @return
	 */
	public static String getWareIDSByCat(String wcat_ids, Map<String, String> mp)
	{
		String ware_ids = "";
		wcat_ids = ","+wcat_ids+",";
		Iterator<WareBean> it = ware_map.values().iterator();
		while(it.hasNext())
		{
			WareBean wb = it.next();
			if(wcat_ids.contains(","+wb.getWcat_id()+",") && isSameAppAndSite(mp, wb))
			{
				ware_ids += ","+wb.getWare_id();
			}
		}
		if(ware_ids != null && !"".equals(ware_ids) && ware_ids.length() > 0)
			ware_ids = ware_ids.substring(1);
		return ware_ids;
	}
	
	/**
	 * 根据信息标签分类ID取得信息标签列表
	 * @param mp
	 * @return
	 */
	public static List<WareBean> getWareListOfSite(Map<String, String> mp)
	{
		List<WareBean> retList = new ArrayList<WareBean>();
		Iterator<WareBean> it = ware_map.values().iterator();
		while(it.hasNext())
		{
			WareBean wb = it.next();
			if(isSameAppAndSite(mp, wb))
			{
				retList.add(wb);
			}
		}
		Collections.sort(retList, new WareComparator());
		return retList;
	}
	
	public static boolean moveWareToOtherCategory(Map<String, String> mp, SettingLogsBean stl){
		if(WareDAO.moveWareToOtherCategory(mp, stl))
		{
			reloadMap();
			return true;
		}
		else
			return false;
	
	}
	/**
	 * 判断是App_id，Site_id是否相等，如果site_id为“”，则不进行比较
	 * @param site_id	站点ID
	 * @param app_id	应用ID
	 * @return	true 相同| false 不相同
	 */
	private static boolean isSameAppAndSite(Map<String, String> mp, WareBean wb)
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
		else if(site_id.equals(wb.getSite_id()))
		{
			sflg = true;
		}
		
		// 判断应用id，应用ID必须相同
		if(app_id.equals(wb.getApp_id()))
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
	
	static class WareComparator implements Comparator<WareBean>
	{
		@Override
		public int compare(WareBean o1, WareBean o2) 
		{
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
		//System.out.println(getWareBeanByWareID("55","11111ddd"));
		String xml = "site_id=$site_id";
		//xml = xml.replaceAll("url=\"[^\"]", "url=\"http://demo.com");
		System.out.println(xml.replaceAll("\\$site_id", "11111ddd"));
	}

}
