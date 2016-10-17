package com.deya.wcm.services.cms.digg;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.digg.InfoDiggBean;
import com.deya.wcm.bean.cms.digg.InfoDiggLogBean;
import com.deya.wcm.dao.cms.digg.DiggDAO;
import com.deya.wcm.services.cms.category.CategoryManager;

/**
 * 用户对信息的支持/反对操作 的逻辑层处理类
 * <p>Title: CicroDate</p>
 * <p>Description: 用户对信息的支持/反对操作</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 王磊
 * @version 1.0
 * * 
 */
public class DiggManager {
	
	/**
	 * 取得信息digg条数
	 * @param mp	取得条件，包括model_id,release_time,和排序等
	 * @return
	 */
	public static String getInfoDiggListCnt(Map<String, String> mp)
	{
		if(mp.get("start_day") == null || "".equals(mp.get("start_day")))
		{
			Calendar c = new GregorianCalendar ();
			c.add(Calendar.YEAR, -1);
			mp.remove("start_day");
			String start_time = DateUtil.getDateString(c.getTime()) + " 00:00:00";
			mp.put("start_day", start_time);
		}
		String cat_id = mp.get("cat_id");
		if(cat_id != null && !"".equals(cat_id))
		{
			String ids = CategoryManager.getAllChildCategoryIDS(cat_id, mp.get("site_id"));
			if (ids != null && !"".equals(ids))
			{
				cat_id +="," +ids;
			}
			mp.remove("cat_id");
			mp.put("cat_id", cat_id);
		}
		return DiggDAO.getInfoDiggCnt(mp);
	}
	
	/**
	 * 取得信息digg列表
	 * @param mp	取得条件，包括model_id,release_time,和排序等
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<InfoDiggBean> getInfoDiggList(Map<String, String> mp)
	{
		if(mp.get("start_day") == null || "".equals(mp.get("start_day")))
		{
			Calendar c = new GregorianCalendar ();
			c.add(Calendar.YEAR, -1);
			mp.remove("start_day");
			String start_time = DateUtil.getDateString(c.getTime()) + " 00:00:00";
			mp.put("start_day", start_time);
		}
		String cat_id = mp.get("cat_id");
		if(cat_id != null && !"".equals(cat_id))
		{
			String ids = CategoryManager.getAllChildCategoryIDS(cat_id, mp.get("site_id"));
			if (ids != null && !"".equals(ids))
			{
				cat_id +="," +ids;
			}
			mp.remove("cat_id");
			mp.put("cat_id", cat_id);
		}
		return DiggDAO.getInfoDigg(mp);
	}
	
	public static InfoDiggBean getInfoDigg(String info_id)
	{
		return DiggDAO.getInfoDigg(info_id);
	}
	
	/**
	 * 记录   某一信息的支持度
	 * 被支持过的 新增记录，
	 * 否则修改 该信息的 supports 或 againsts 字段
	 * @return
	 */
	public static boolean recordInfoDigg(InfoDiggBean digg)
	{
		String info_id = digg.getInfo_id()+"";
		if(getInfoDigg(info_id) == null )
		{
			return DiggDAO.insertInfoDigg(digg);
		}
		else
		{
			return DiggDAO.updateInfoDigg(digg);
		}
		
	}
	
	
	
	/**
	 * 记录  指定用户对指定信息的支持情况
	 * @param log
	 * @return
	 */
	public static boolean insertInfoDiggLog(InfoDiggLogBean log)
	{
		String diggDtime = DateUtil.getDateTimeString(new Date());
		log.setDigg_dtime(diggDtime);
		return DiggDAO.insertInfoDiggLog(log);
	}

	public static void main(String[] args){
		String cat_id = "354";
		String ids = CategoryManager.getAllChildCategoryIDS("354","11111ddd");
		if (ids != null && !"".equals(ids))
		{
			cat_id +="," +ids;
		}
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("cat_id", cat_id);
		mp.put("site_id", "11111ddd");
		
		mp.put("page_size", 15+"");
		mp.put("start_num", 0+"");
		List lt = getInfoDiggList(mp);
		System.out.println(lt.size());
	}
}
