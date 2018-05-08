package com.deya.wcm.dao.appeal.satisfaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.satisfaction.*;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class SatisfactionDAO {

	/**
     * 得到满意度指标信息列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getAllSatisfactionList()
	{
		return DBManager.queryFList("getSatisfactionList", "");
	}
	/**
     * 插入满意度指标信息
     * @param SatisfactionBean sfb
     * @return boolean
     * */
	public static boolean insertSatisfaction(SatisfactionBean sfb)
	{
		return DBManager.insert("insert_satisfaction",sfb);
	}
	/**
     * 删除满意度指标信息
     * @param SatisfactionBean sfb
     * @return boolean
     * */
	public static boolean deleteSatisfaction()
	{
		return DBManager.delete("deleteSatisfaction","");
	}
	
	/**
     * 得到满意度指标信息
     * @param SatisfactionBean sfb
     * @return boolean
     * */
	public static SatisfactionBean getSatisfactionBean(int sf_id)
	{
		return (SatisfactionBean)DBManager.queryFObj("get_SatisfactionById",sf_id+"");
	}
	
	/**
     * 插入满意度投票信息
     * @param SatRecordBean srb
     * @return boolean
     * */
	public static boolean insertSatRecord(SatRecordBean srb)
	{
		srb.setRec_id(PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_SATRECORD_TABLE_NAME));
		return DBManager.insert("insert_sat_record", srb);
	}
	
	/**
     * 根据诉求ID得到满意度分值
     * @param String sq_id
     * @return String
     * */
	public static String getSatScoreBySQID(String sq_id)
	{
		return DBManager.getString("getSatScoreBySQID", sq_id);
	}
}
