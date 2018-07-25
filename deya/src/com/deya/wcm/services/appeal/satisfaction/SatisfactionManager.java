package com.deya.wcm.services.appeal.satisfaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.appeal.satisfaction.*;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.satisfaction.SatisfactionDAO;

public class SatisfactionManager implements ISyncCatch{

	/**
	 *  诉求满意度指标管理逻辑处理类.
	 * <p>Title: CicroDate</p>
	 * <p>Description: 诉求满意度指标角色管理逻辑处理类</p>
	 * <p>Copyright: Copyright (c) 2010</p>
	 * <p>Company: Cicro</p>
	 * @author zhuliang
	 * @version 1.0
	 * * 
	 */
		private static TreeMap<Integer,SatisfactionBean> sf_map = new TreeMap<Integer, SatisfactionBean>();	//诉求满意度指标缓存
		
		static{
			reloadCatchHandl();
		}
		
		public void reloadCatch()
		{
			reloadCatchHandl();
		}
		@SuppressWarnings("unchecked")
		public static void reloadCatchHandl()
		{
			List<SatisfactionBean> sf_list = SatisfactionDAO.getAllSatisfactionList();
			sf_map.clear();
			if(sf_list != null && sf_list.size() > 0)
			{
				for(int i=0;i<sf_list.size();i++)
				{
					sf_map.put(sf_list.get(i).getSat_id(), sf_list.get(i));
				}
			}
		}
		
		/**
		 * 初始加载诉求满意度指标信息
		 * 
		 * @param
		 * @return
		 */		
		public static void reloadSatisfaction()
		{
			SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.satisfaction.SatisfactionManager");
		}
		/**
	     * 获取诉求满意度指标列表信息
	     * @param 
	     * @return List
	     * */
		@SuppressWarnings("unchecked")
		public static List<SatisfactionBean> getSatisfactionList()
		{
			List<SatisfactionBean> sf_list = new ArrayList<SatisfactionBean>();
			if(sf_map != null){
				Iterator iter = sf_map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();	
					sf_list.add((SatisfactionBean)entry.getValue());		
				}	
			}else{
				sf_list = SatisfactionDAO.getAllSatisfactionList();	
			}
			return sf_list;	
		}
		
		/**
	     * 插入诉求满意度指标信息
	     * @param int sf_id
	     * @param List<SatisfactionBean> sf_list
	     * @return boolean
	     * */
		public static boolean insertSatisfaction(List<SatisfactionBean> sf_list,SettingLogsBean stl)
		{
			if(deleteSatisfactionBean(stl))
			{
				if(sf_list != null && sf_list.size() > 0)
				{
					for(int i=0;i<sf_list.size();i++)
					{
						//int sat_id = PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_SATISFATION_TABLE_NAME );
						sf_list.get(i).setSat_id(i+1);
						if(SatisfactionDAO.insertSatisfaction(sf_list.get(i)))
						{
							PublicTableDAO.insertSettingLogs("添加","满意度指标",(i+1)+"",stl);
						}
					}
					reloadSatisfaction();
				}
				return true;
			}else{
				return false;
			}
		}
		/**
	     * 删除诉求满意度指标信息
	     * @param 
	     * @return boolean
	     * */
		public static boolean deleteSatisfactionBean(SettingLogsBean stl)
		{
			if(SatisfactionDAO.deleteSatisfaction())
			{
				PublicTableDAO.insertSettingLogs("删除","满意度指标","",stl);
				//reloadSatisfaction();
				return true;
			}else
				return false;
		}
		
		/**
	     * 根据ID返回诉求满意度指标对象
	     * @param int sf_id
	     * @return SatisfactionBean
	     * */
		public static SatisfactionBean getSatisfactionBean(int sf_id)
		{
			if(sf_map.containsKey(sf_id))	
			{
				return sf_map.get(sf_id);
			}else
			{
				SatisfactionBean sf = SatisfactionDAO.getSatisfactionBean(sf_id);
				if(sf != null){
					sf_map.put(sf_id, sf);
					return sf;
				}else
					return null;
			}
		}
		
		/**
	     * 插入满意度投票信息
	     * @param String temp_raty　用户满意度
	     * @return boolean
	     * */
		public static boolean insertSatRecord(String sq_id,String[] temp_raty)
		{
			List<SatisfactionBean> sf_list = getSatisfactionList();
			if(sf_list != null && sf_list.size() > 0)
			{
				String add_time = DateUtil.getCurrentDateTime();
				for(int i=0;i<sf_list.size();i++)
				{
					try{
						SatRecordBean srb = new SatRecordBean();
						srb.setSq_id(Integer.parseInt(sq_id));
						srb.setSat_id(sf_list.get(i).getSat_id());
						srb.setSat_score(temp_raty[i]);
						srb.setAdd_dtime(add_time);
						SatisfactionDAO.insertSatRecord(srb);
					}catch(Exception e)
					{
						e.printStackTrace();
						return false;
					}
				}
			}
			return true;
		}
		
		/**
	     * 根据诉求ID得到满意度分值
	     * @param String sq_id
	     * @return String
	     * */
		public static String getSatScoreBySQID(String sq_id)
		{
			return SatisfactionDAO.getSatScoreBySQID(sq_id);
		}
		
		public static void main(String[] args)
		{
			System.out.println(getSatScoreBySQID("84"));
		}
}
	