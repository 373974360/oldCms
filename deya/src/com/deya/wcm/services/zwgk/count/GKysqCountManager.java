package com.deya.wcm.services.zwgk.count;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.zwgk.count.GKysqCountBean;
import com.deya.wcm.dao.zwgk.count.GKysqCountDAO;

public class GKysqCountManager {

	/**
	 * 取得依申请公开申请单状态统计
	 * @param mp
	 * @return
	 */
	public static List<GKysqCountBean> getYSQStateCnt(Map<String, String> mp) {
		List<GKysqCountBean> retLt = new ArrayList<GKysqCountBean>();
		// 取得依申请公开的申请单信息
		Map<String, GKysqCountBean> retMap = pretreatmentCnt(false, mp);
		for (Iterator<GKysqCountBean> i = retMap.values().iterator(); i
				.hasNext();) {
			GKysqCountBean bean = i.next();
			retLt.add(bean);
		}
		return retLt;
	}

	/**
	 * 取得节点下依申请公开申请单状态统计
	 * @param mp
	 * @return
	 * @throws ParseException 
	 */
	public static List<GKysqCountBean> getYSQStateCntByNode(
			Map<String, String> mp) {
		// 取得节点下依申请公开的信息
		Map<String, GKysqCountBean> retMap = pretreatmentCnt(true, mp);
		List<GKysqCountBean> retLt = sortStateCntByMonth(mp.get("start_day"), mp.get("end_day"), retMap);
		
		return retLt;
	}
	
	/**
	 * 处理按月取得的依申请公开信息
	 * @param s_day 统计开始时间
	 * @param e_day	统计截至时间
	 * @param retMap	已取得的统计值
	 * @return
	 */
	private static List<GKysqCountBean> sortStateCntByMonth(String s_day, String e_day, 
			Map<String, GKysqCountBean> retMap) {
		TreeMap<String, GKysqCountBean> p = new TreeMap<String, GKysqCountBean>();

		for (Iterator<GKysqCountBean> i = retMap.values().iterator(); i
			.hasNext();) {
			GKysqCountBean bean = i.next();
			p.put(bean.getPut_dtime(), bean);
		}
		
		String end_day = e_day.substring(0, 7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar cal = new GregorianCalendar();
		
		try {
			Date start = sdf.parse(s_day);
			cal.setTime(start);
			
			while(end_day.compareTo(sdf.format(cal.getTime())) >= 0){
				if(!p.containsKey(sdf.format(cal.getTime()))) {
					GKysqCountBean gb = new GKysqCountBean();
					gb.setPut_dtime(sdf.format(cal.getTime()));
					p.put(sdf.format(cal.getTime()), gb);
				}
				cal.add(Calendar.MONTH, 1);
			}
		} catch (ParseException e) {
			// DO nothing
		}
		
		List<GKysqCountBean> retLt = new ArrayList<GKysqCountBean>();
		while(p.size() > 0) {
			GKysqCountBean b = p.get(p.lastKey());
			p.remove(p.lastKey());
			retLt.add(b);
		}
		return retLt;
	}
	
	/**
	 * 把数据库取得的数据进行预处理
	 * do_state设置:取得征询第三方时为1000,取得延期时为2000,取得超期时为3000
	 * @param is_node
	 *            True时为节点下申请单状态统计,false时为公开申请单状态统计
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, GKysqCountBean> pretreatmentCnt(boolean is_node,
			Map mp) {
		Map<String, GKysqCountBean> retMap = new HashMap<String, GKysqCountBean>();
		List<GKysqCountBean> cntlt = null; // 数据库取得的列表

		if (is_node) {
			cntlt = GKysqCountDAO.getStateCountByNode(mp);
		} else {
			cntlt = GKysqCountDAO.getStateCount(mp);
		}
		for (GKysqCountBean bean : cntlt) {
			String key = bean.getNode_id();
			if (is_node) { 
				key += bean.getPut_dtime(); // 如果是节点下统计,key需要加上时间
			}
			if (!retMap.containsKey(key)) {
				retMap.put(key, bean); // map中不存在,添加进去
			}
			GKysqCountBean mapBean = retMap.get(key);

			switch (bean.getDo_state()) {
			case -1:
				mapBean.setInvalidCnt(bean.getCount()); // 无效申请
				break;
			case 0:
				mapBean.setUnAcceptCnt(bean.getCount()); // 未受理
				break;
			case 1:
				mapBean.setAcceptedCnt(bean.getCount()); // 已受理
				break;
			case 2:
				mapBean.setReplyCnt(bean.getCount()); // 已回复
				break;
			case 1000:
				mapBean.setThirdCnt(bean.getCount()); // 征询第三方
				break;
			case 2000:
				mapBean.setDelayCnt(bean.getCount()); // 延期
				break;
			case 3000:
				mapBean.setTimeoutCnt(bean.getCount()); // 超期
				break;
			default:
				break;
			}
		}
		return retMap;
	}

	/**
	 * 取得申请单处理方式统计
	 * @param mp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<GKysqCountBean> getYSQTypeCount(Map<String, String> mp) {
		Map<String, GKysqCountBean> retMap = new HashMap<String, GKysqCountBean>();
		List<GKysqCountBean> cntlt = GKysqCountDAO.getTypeCount(mp);
		for (GKysqCountBean bean : cntlt) {
			String key = bean.getNode_id();
			if (!retMap.containsKey(key)) {
				retMap.put(key, bean); // map中不存在,添加进去
			}
			GKysqCountBean mapBean = retMap.get(key);
			switch (bean.getReply_type()) {
			case 1:
				mapBean.setQbgk_cnt(bean.getCount()); // 全部公开
				break;
			case 2:
				mapBean.setBfgk_cnt(bean.getCount()); // 部分公开
				break;
			case 3:
				mapBean.setBygk_cnt(bean.getCount()); // 不予公开
				break;
			case 4:
				mapBean.setFbdwxx_cnt(bean.getCount()); // 非本单位信息
				break;
			case 5:
				mapBean.setXxbcz_cnt(bean.getCount()); // 信息不存在
				break;
			default:
				break;
			}
		}
		List<GKysqCountBean> retLt = new ArrayList<GKysqCountBean>();
		for (Iterator<GKysqCountBean> i = retMap.values().iterator(); i
				.hasNext();) {
			GKysqCountBean bean = i.next();
			retLt.add(bean);
		}
		return retLt;
	}

	@SuppressWarnings("unchecked")
	public static void main(String arg[]) {
		
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("start_day", "2011-01");
		mp.put("end_day","2011-09");
		mp.put("node_id", "GKmzj");
		List lt = getYSQStateCntByNode(mp);
		
		System.out.println(lt.size());
	}

}
