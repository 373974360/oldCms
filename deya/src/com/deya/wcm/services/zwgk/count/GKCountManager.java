package com.deya.wcm.services.zwgk.count;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.zwgk.count.GKCountBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.dao.zwgk.count.GKCountDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.zwgk.node.GKNodeCateManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

/**
 * ZWGK应用栏目统计
 * 
 * @author liqi
 * 
 */
public class GKCountManager {

	/**
	 * 取得指定站点的栏目昨天的栏目工作统计
	 * 
	 * @param app_id
	 *            统计结果所处的应用位置，一般为政务公开
	 * @return
	 */
	public static boolean CateInfoCounting() {
		return CateInfoCounting(null, "zwgk", "");
	}

	/**
	 * 取得指定站点的栏目指定日期的栏目工作统计
	 * 
	 * @param app_id
	 *            统计结果所处的应用位置，一般为政务公开
	 * @param day
	 *            指定日期，格式为YYYY-MM-DD HH:mm:ss, YYYY-MM-DD或者为空
	 * @return
	 */
	public static boolean CateInfoCountingByDate(String day) {
		return CateInfoCounting(null, "zwgk", day);
	}

	/**
	 * 取得指定站点的栏目昨天的栏目工作统计
	 * 
	 * @param site_id
	 * @param app_id
	 *            统计结果所处的应用位置，一般为政务公开
	 * @return
	 */
	public static boolean CateInfoCounting(String site_id) {
		return CateInfoCounting(site_id, "zwgk", "");
	}

	/**
	 * 取得指定日期，指定站点，指定应用的栏目工作统计 指定日期为空时,取前一天的栏目工作统计
	 * 
	 * @param site_id
	 *            站点ID
	 * @param app_id
	 *            应用站点ID
	 * @param time
	 *            指定日期，格式为YYYY-MM-DD HH:mm:ss, YYYY-MM-DD或者为空
	 * @return 是否取得成功
	 */
	@SuppressWarnings("unchecked")
	public static boolean CateInfoCounting(String site_id, String app_id,
			String time) {
		Map<String, String> mp = new HashMap<String, String>();
		if (site_id == null || "".equals(site_id))
			;
		{
			site_id = null;
		}
		mp.put("site_id", site_id);

		if ("".equals(time) || time == null) {
			Calendar c = new GregorianCalendar();
			c.add(Calendar.DAY_OF_MONTH, -1);

			time = DateUtil.getDateString(c.getTime());
			String start_day = time + " 00:00:00";
			String end_day = time + " 59:59:59";
			mp.put("start_day", start_day);
			mp.put("end_day", end_day);
		} else {
			time = time.substring(0, 10);
			String start_day = time + " 00:00:00";
			String end_day = time + " 59:59:59";
			mp.put("start_day", start_day);
			mp.put("end_day", end_day);
		}
		List lt = GKCountDAO.getCateInfoCount(mp);

		mp.put("update_time", time);
		GKCountDAO.deleteGKCount(mp);
		return saveCateInfo(lt, time, app_id, site_id);
	}

	/**
	 * 根据站点ID取得应用栏目工作量统计信息 默认取昨天一天的工作量统计信息
	 * 
	 * @param site_id
	 *            站点ID
	 * @return
	 */
	public static List<GKCountBean> getGKCountList(String site_id) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DAY_OF_MONTH, -1);

		String date = DateUtil.getDateString(c.getTime());
		return getGKCountList(site_id, date, date);
	}

	/**
	 * 根据站点ID和日期取得应用栏目工作量统计信息
	 * 
	 * @param site_id
	 *            站点ID
	 * @param startDate
	 *            日期格式为yyyy-MM-DD
	 * @param endDate
	 *            日期格式为yyyy-MM-DD
	 * @return
	 */
	public static List<GKCountBean> getGKCountList(String site_id,
			String startDate, String endDate) {
		String cat_id = null;
		return getGKCountList(site_id, startDate, endDate, cat_id);
	}

	/**
	 * 取得应用栏目工作量统计信息
	 * 
	 * @param site_id
	 *            站点ID
	 * @param startDate
	 *            日期格式为yyyy-MM-DD 不能为空
	 * @param endDate
	 *            日期格式为yyyy-MM-DD 不能为空
	 * @param cat_id
	 *            目录id, 可以为空
	 * @return
	 */
	public static List<GKCountBean> getGKCountList(String site_id,
			String startDate, String endDate, String cat_id) {
		try{
			Map<String, String> mp = new HashMap<String, String>();
			mp.put("site_id", site_id);
			if ("".equals(startDate)) {  
				startDate = null;
			}
			mp.put("start_day", startDate);
			// if("".equals(endDate)) {
			// endDate = null;
			// }
			mp.put("end_day", endDate);
			// if("".equals(cat_id)) {
			// cat_id = null;
			// }
			mp.put("cat_id", cat_id);
			//List<GKCountBean> lt = GKCountDAO.getGKCount(mp);
			//return addCatInfo(all_list, site_id);
			
			//得到公开节点的信息总数
			List<GKCountBean> all_list = GKCountDAO.getGkInfoCountByStatusANDNode(mp);
			//得到公开节点的图片信息总数
			mp.put("is_pic", "1"); // 取得已发信息 并且有图片
			List<GKCountBean> pic_list = GKCountDAO.getGkInfoCountByStatusANDNode(mp);
			mp.remove("is_pic"); 
			//得到公开节点的信息总数---主动公开
			mp.put("gk_type", "0");
			List<GKCountBean> z_list = GKCountDAO.getGkInfoCountByStatusANDNode(mp);
			//得到公开节点的信息总数---不公开
			mp.put("gk_type", "1");
			List<GKCountBean> b_list = GKCountDAO.getGkInfoCountByStatusANDNode(mp);
			for(GKCountBean gkNodeBeanAll : all_list){
				int cat_ido = gkNodeBeanAll.getCat_id();
				//---主动公开
				if(z_list!=null && z_list.size()>0){
					for(GKCountBean gkCountBean:z_list){
						if(cat_ido==gkCountBean.getCat_id()){
							gkNodeBeanAll.setZ_count(gkCountBean.getInfo_count());
							break;
						}else{
							gkNodeBeanAll.setZ_count(0);
						}
					}
				}else{
					gkNodeBeanAll.setZ_count(0);
				}
				
				//---不公开
				if(b_list!=null && b_list.size()>0){
					for(GKCountBean gkCountBean:b_list){
						if(cat_ido==gkCountBean.getCat_id()){
							gkNodeBeanAll.setB_count(gkCountBean.getInfo_count());
							break;
						}else{
							gkNodeBeanAll.setB_count(0);
						}
					}
				}else{
					gkNodeBeanAll.setB_count(0);
				}
				
				//图片信息
				if(pic_list!=null && pic_list.size()>0){
					for(GKCountBean gkCountBean:pic_list){
						if(cat_ido==gkCountBean.getCat_id()){
							gkNodeBeanAll.setPic_count(gkCountBean.getInfo_count());
							break;
						}else{
							gkNodeBeanAll.setPic_count(0);
						}
					}
				}else{
					gkNodeBeanAll.setPic_count(0);
				}
				
				//---依申请公开
				gkNodeBeanAll.setY_count(gkNodeBeanAll.getInfo_count()-gkNodeBeanAll.getZ_count()-gkNodeBeanAll.getB_count());
	    	}
			
			return addCatInfo(all_list, site_id);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * 添加cat_name字段
	 * 
	 * @param lt
	 *            GKCountBean列表
	 * @param site_id
	 *            站点ID
	 * @return 添加了cat_name字段的GKCountBean列表
	 */
	private static List<GKCountBean> addCatInfo(List<GKCountBean> lt,
			String site_id) {
		// 返回值保存列表
		List<GKCountBean> retlt = new ArrayList<GKCountBean>();
		Map<Integer, GKCountBean> mp = new HashMap<Integer, GKCountBean>();
		for (GKCountBean gkbean : lt) {
			mp.put(gkbean.getCat_id(), gkbean);
		}
		List<CategoryBean> catlt = CategoryManager.getCategoryListBySiteID(
				site_id, 0);// cat_type=0 表示一般类型

		for (CategoryBean cb : catlt) {
			GKCountBean bean = getCateChildList(cb, mp);
			retlt.add(bean);
		}

		return retlt;
	}

	/**
	 * 取得GKCountBean的子列表，这个列表放置在一个 GKCountBean中 GKCountBean中的其他字段对应为子列表中对应字段的统计和
	 * 这个列表仅供前台显示用
	 * 
	 * @param catbean
	 *            栏目对象
	 * @param mp
	 *            取得的统计信息
	 * @return
	 */
	private static GKCountBean getCateChildList(CategoryBean catbean,
			Map<Integer, GKCountBean> mp) {
		GKCountBean retbean = new GKCountBean();
		retbean.setCat_id(catbean.getCat_id());
		retbean.setCat_name(catbean.getCat_cname());

		List<CategoryBean> sublist = CategoryManager.getAllChildCategoryList(
				catbean.getCat_id(), catbean.getSite_id());
		for (int i = 0; i < sublist.size(); i++) {
			if (sublist.get(i).getCat_id() == catbean.getCat_id()) {
				sublist.remove(i);
				break;
			}
		}
		if (sublist.size() != 0) {
			retbean.setIs_leaf(false);
			List<GKCountBean> childList = new ArrayList<GKCountBean>();
			for (CategoryBean cb : sublist) {
				GKCountBean gkbean = getCateChildList(cb, mp);
				retbean.setB_count(retbean.getB_count() + gkbean.getB_count());
				retbean.setZ_count(retbean.getZ_count() + gkbean.getZ_count());
				retbean.setY_count(retbean.getY_count() + gkbean.getY_count());
				retbean.setInfo_count(retbean.getInfo_count()
						+ gkbean.getInfo_count());
				retbean.setPic_count(retbean.getPic_count() + gkbean.getPic_count());

				childList.add(gkbean);
			}
			retbean.setChild_list(childList);
		} else {
			if (mp.containsKey(retbean.getCat_id())) {
				GKCountBean countBean = mp.get(retbean.getCat_id());
				retbean.setB_count(countBean.getB_count());
				retbean.setZ_count(countBean.getZ_count());
				retbean.setY_count(countBean.getY_count());
				retbean.setInfo_count(countBean.getInfo_count());
				retbean.setPic_count(countBean.getPic_count());
			}
		}
		return retbean;
	}

	/**
	 * 将列表中的信息保存到cs_gk_count表中
	 * 
	 * @param lt
	 *            中的Map中有3个字段, cat_id,gk_type,count
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static boolean saveCateInfo(List<Map> lt, String updateTime,
			String appId, String siteId) {
		Map<String, GKCountBean> bean_mp = new HashMap<String, GKCountBean>();
		for (int i = 0; i < lt.size(); i++) {
			fillBean(lt.get(i), bean_mp);
		}

		boolean flg = true;
		for (GKCountBean bean : bean_mp.values()) {
			bean.setUpdate_time(updateTime);
			bean.setApp_id(appId);
			if (siteId != null && !"".equals(siteId))
				bean.setSite_id(siteId);

			int infoCount = bean.getB_count() + bean.getY_count()
					+ bean.getZ_count();
			bean.setInfo_count(infoCount);
			flg = GKCountDAO.insertGKCount(bean) ? flg : false;
		}
		return flg;
	}

	/**
	 * 填充应用栏目统计对象 将ibatis取得的List中的Map信息转换添加到GKBean中
	 * 
	 * @param mp
	 *            存放统计信息
	 * @param bean_mp
	 *            存放填充后的Bean信息
	 */
	@SuppressWarnings("unchecked")
	private static void fillBean(Map mp, Map<String, GKCountBean> bean_mp) {
		String key = mp.get("CAT_ID").toString();
		GKCountBean bean;
		if (bean_mp.containsKey(key)) {
			bean = bean_mp.get(key);
		} else {
			bean = new GKCountBean();
			if (key != null && !"".equals(key))
				bean.setCat_id(Integer.valueOf(key));
			if (mp.get("SITE_ID") != null)
				bean.setSite_id(mp.get("SITE_ID").toString());
			bean_mp.put(key, bean);
		}

		String type = mp.get("GK_TYPE").toString();
		String cnt = mp.get("COUNT").toString();
		if ("0".equals(type)) { // 主动公开信息
			bean.setZ_count(Integer.valueOf(cnt));
		} else if ("1".equals(type)) { // 不公开信息
			bean.setB_count(Integer.valueOf(cnt));
		} else if ("2".equals(type)) { // 依申请公开信息
			bean.setY_count(Integer.valueOf(cnt));
		}
	}

	/**
	 * 根据排序方式和取得位数取得站点公开信息统计情况 排序方式值为Bean中对应字段的变量名,总件数 info_count,依申请公开 y_count
	 * 
	 * @param mp
	 *            参数,包括开始,截至日期,type排序方式,num取得位数等
	 * @return 站点公开信息统计列表
	 */
	public static List<GKCountBean> getAllSiteGKCountList(Map<String, String> mp) {
		List<GKCountBean> ret = new ArrayList<GKCountBean>();

		List<GKCountBean> lt = getAllSiteGKCountList(mp.get("start_day"), mp
				.get("end_day"), null);
		String sort_type = mp.get("type");
		int sort_num = Integer.valueOf(mp.get("num"));

		List<GKCountBean> ascList = sortGKCountBean(sort_type, lt);

		for (int i = ret.size(); i > ret.size() - sort_num && i > 0; i--) {
			ret.add(ascList.get(i - 1));
		}

		return ret;
	}

	/**
	 * 对指定的列表按照Bean中对应的字段排序 取得的列表(升序排列)
	 * 
	 * @param sort_type
	 *            对应字段名称
	 * @param lt
	 *            指定列表
	 * @return 放置排序后的列表(升序排列)
	 */
	private static List<GKCountBean> sortGKCountBean(String sort_type,
			List<GKCountBean> lt) {
		TreeMap<Integer, GKCountBean> beanMap = new TreeMap<Integer, GKCountBean>();
		if ("info_count".equals(sort_type)) {
			for (GKCountBean gkbean : lt) {
				beanMap.put(gkbean.getInfo_count(), gkbean);
			}
		} else if ("z_count".equals(sort_type)) {
			for (GKCountBean gkbean : lt) {
				beanMap.put(gkbean.getZ_count(), gkbean);
			}
		} else if ("y_count".equals(sort_type)) {
			for (GKCountBean gkbean : lt) {
				beanMap.put(gkbean.getY_count(), gkbean);
			}
		} else if ("b_count".equals(sort_type)) {
			for (GKCountBean gkbean : lt) {
				beanMap.put(gkbean.getB_count(), gkbean);
			}
		}

		List<GKCountBean> retList = new ArrayList<GKCountBean>();
		for (GKCountBean bean : beanMap.values()) {
			retList.add(bean);
		}
		return retList;
	}

	/**
	 * 取得所有站点下的工作量统计
	 * 
	 * @param startDate
	 *            统计开始日期
	 * @param endDate
	 *            统计截至日期
	 * @return
	 */
	/*
	public static List<GKCountBean> getAllSiteGKCountList(String startDate,
			String endDate, String node_ids) {
		Set<String> set = new HashSet<String>();
		
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("start_day", startDate);
		mp.put("end_day", endDate);
		if(node_ids != null && node_ids != "" ){
			mp.put("site_ids", node_ids);
			String[] arr = node_ids.replace("'", "").split(",");
			for(String s : arr) {
				set.add(s);
			}
		}

		List<GKCountBean> countList = GKCountDAO.getAllSiteGKCount(mp);
		Map<String, GKCountBean> cnt_mp = new HashMap<String, GKCountBean>();
		for (GKCountBean gkbean : countList) {
			cnt_mp.put(gkbean.getSite_id(), gkbean);
		}

		List<GKCountBean> list = addSiteInfo(cnt_mp,set);
		return list;
	}
*/
	/**
	 * 取得所有站点下的工作量统计
	 * 
	 * @param startDate
	 *            统计开始日期
	 * @param endDate
	 *            统计截至日期
	 * @return
	 */
	public static List<GKCountBean> getAllSiteGKCountList(String startDate,
			String endDate, String node_ids) {
	    try{
	    	Set<String> set = new HashSet<String>();
			
			Map<String, String> mp = new HashMap<String, String>();
			mp.put("start_day", startDate);
			mp.put("end_day", endDate);
			if(node_ids != null && node_ids != "" ){
				mp.put("site_ids", node_ids);
				String[] arr = node_ids.replace("'", "").split(",");
				for(String s : arr) {
					set.add(s);
				}
			}
			
			Map<String, GKCountBean> cnt_mp = new HashMap<String, GKCountBean>();

			//得到所有的公开节点
			List<GKNodeBean> gkNodeList = GKNodeManager.getAllGKNodeList();
			//System.out.println(mp);
			if(gkNodeList.size()==set.size()){
				mp.remove("site_ids");
			}
			//得到公开节点的信息总数---图片信息数
			mp.put("is_pic", "1");
			List<GKCountBean> pic_list = GKCountDAO.getGkInfoCountByStatus(mp);
			//得到公开节点的信息总数---主动公开
			mp.put("gk_type", "0");
			mp.remove("is_pic");
			List<GKCountBean> z_list = GKCountDAO.getGkInfoCountByStatus(mp);
			//得到公开节点的信息总数---不公开
			mp.put("gk_type", "1");
			List<GKCountBean> b_list = GKCountDAO.getGkInfoCountByStatus(mp);
			//得到公开节点的信息总数---依申请公开
			mp.put("gk_type", "2");
			List<GKCountBean> y_list = GKCountDAO.getGkInfoCountByStatus(mp);
			for(GKNodeBean gkNodeBean : gkNodeList){
				String site_id = gkNodeBean.getNode_id();
				GKCountBean countBean = new GKCountBean();
				countBean.setSite_id(site_id);
				
				//---图片信息数
				if(pic_list!=null && pic_list.size()>0){
					for(GKCountBean gkCountBean:pic_list){
						//System.out.println("gkCountBean.getPic_count()=====" + gkCountBean.getPic_count());
						if(site_id.equals(gkCountBean.getSite_id())){
							countBean.setPic_count(gkCountBean.getInfo_count());
							break;
						}else{
							countBean.setPic_count(0);
						}
					}
				}else{
					countBean.setPic_count(0);
				}
				
				//---主动公开
				if(z_list!=null && z_list.size()>0){
					for(GKCountBean gkCountBean:z_list){
						if(site_id.equals(gkCountBean.getSite_id())){
							countBean.setZ_count(gkCountBean.getInfo_count());
							break;
						}else{
							countBean.setZ_count(0);
						}
					}
				}else{
					countBean.setZ_count(0);
				}
				
				//---不公开
				if(b_list!=null && b_list.size()>0){
					for(GKCountBean gkCountBean:b_list){
						if(site_id.equals(gkCountBean.getSite_id())){
							countBean.setB_count(gkCountBean.getInfo_count());
							break;
						}else{
							countBean.setB_count(0);
						}
					}
				}else{
					countBean.setB_count(0);
				}
				
				//---依申请公开
				if(y_list!=null && y_list.size()>0){
					for(GKCountBean gkCountBean:y_list){
						if(site_id.equals(gkCountBean.getSite_id())){
							countBean.setY_count(gkCountBean.getInfo_count());
							break;
						}else{
							countBean.setY_count(0);
						}
					}
				}else{
					countBean.setY_count(0);
				}
				countBean.setInfo_count(countBean.getZ_count()+countBean.getB_count()+countBean.getY_count());
				cnt_mp.put(countBean.getSite_id(), countBean);
			}
			
//			List<GKCountBean> countList = GKCountDAO.getAllSiteGKCount(mp);
//			for (GKCountBean gkbean : countList) {
//				cnt_mp.put(gkbean.getSite_id(), gkbean);
//			}

			List<GKCountBean> list = addSiteInfo(cnt_mp,set);
			
			//按数据量排序 -- 降序排序
			InfoCountComparator infoCountComparator = new InfoCountComparator();
			Collections.sort(list, infoCountComparator);
			
			return list;
	    }catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 按照站点层次处理取得的GKCountBean信息 通过递归遍历取得所有的站点信息，并且添加上工作量统计信息
	 * @param cnt_mp 统计结果
	 * @param set 需要显示的节点ID,为空时为显示全部
	 * @return
	 */
	private static List<GKCountBean> addSiteInfo(Map<String, GKCountBean> cnt_mp, Set<String> set) {
		List<GKCountBean> ret_lt = new ArrayList<GKCountBean>();
		List<GKNodeBean> node_lt = new ArrayList<GKNodeBean>();
		String node_cates = GKNodeCateManager.getAllChildCategoryIDS("0"); // 取得所有节点分类
		String[] arr_node = node_cates.split(",");
		for (int i = 0; i < arr_node.length; i++) {
			List lt = GKNodeManager.getGKNodeListByCateID(Integer
					.valueOf(arr_node[i]));
			node_lt.addAll(lt);
		}
		for (GKNodeBean bean : node_lt) {
			GKCountBean retbean;
			
			// 当set不为空 且 节点ID不包含在set中时break;
			if(set.size() != 0 && !set.contains(bean.getNode_id())) {
				continue;
			}
			
			if (cnt_mp.containsKey(bean.getNode_id())) {
				retbean = cnt_mp.get(bean.getNode_id());
			} else {
				retbean = new GKCountBean();
				retbean.setSite_id(bean.getNode_id());
			}
			retbean.setSite_name(bean.getNode_name());
			ret_lt.add(retbean);
		}

		return ret_lt;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] arg) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("gk_type", "0");
		List<GKCountBean> z_list = GKCountDAO.getGkInfoCountByStatus(mp);
	}
}
