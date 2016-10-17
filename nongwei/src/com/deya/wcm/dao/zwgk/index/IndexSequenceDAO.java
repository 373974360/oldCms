package com.deya.wcm.dao.zwgk.index;

import java.util.HashMap;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.index.IndexSequenceBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class IndexSequenceDAO {
	
	/**
	 * 取得流水号生产信息对象
	 * @param isb
	 * @return
	 */
	public static IndexSequenceBean getSequenceBean(IndexSequenceBean isb)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("seq_type", isb.getSeq_type()+"");
		m.put("seq_ymd", isb.getSeq_ymd());
		m.put("site_id", isb.getSite_id());
		if(!"".equals(isb.getSeq_item().trim()))
		{
			m.put("seq_item", isb.getSeq_item());
		}
		try{
			Object temp = DBManager.queryFObj("getIndexSequenceBean", m);
			return (IndexSequenceBean)temp;
		}catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * 添加流水号生产信息
	 * @param isb	流水号生产对象
	 * @param stl
	 * @return
	 */
	public static boolean insertSequence(IndexSequenceBean isb )
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.GK_INDEX_SEQUENCE);
		isb.setId(id);
		if(DBManager.insert("insertIndexSequence", isb)){
		    return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改流水号生产信息
	 * @param isb 流水号生产对象
	 * @param stl
	 * @return
	 */
	public static boolean updateSequence(IndexSequenceBean isb )
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("seq_type", isb.getSeq_type()+"");
		m.put("seq_ymd", isb.getSeq_ymd());
		m.put("site_id", isb.getSite_id());
		if(!"".equals(isb.getSeq_item().trim()))
			m.put("seq_item", isb.getSeq_item());
		m.put("id", isb.getId()+"");
		return DBManager.update("updateIndexSequence", m);
		 
	}
	
	/**
	 * 重置流水号生产信息
	 * @param isb 流水号生产对象
	 * @param stl
	 * @return
	 */
	public static boolean resetSequence(IndexSequenceBean isb, SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("seq_type", isb.getSeq_type()+"");
		m.put("seq_ymd", isb.getSeq_ymd());
		m.put("site_id", isb.getSite_id());
		if(!"".equals(isb.getSeq_item().trim()))
			m.put("seq_item", isb.getSeq_item());
		if(DBManager.update("resetIndexSequence", m)){
			PublicTableDAO.insertSettingLogs("修改","流水号生产信息",isb.getId()+"",stl);
		    return true;
		}else{
			return false;
		}
	}
	
	
	
	public static void main(String[] arg)
	{
		IndexSequenceBean pa = new IndexSequenceBean();
		pa.setSeq_item("");
		pa.setSeq_type(1);
		pa.setApp_id("1");
		pa.setSite_id("1");
		IndexSequenceBean i = getSequenceBean(pa);
		
		i.setSeq_cur_value(999);
		
		if(updateSequence(i))
		{
			System.out.println("OK");
		}
		else
		{
			System.out.println("u_false");
		}
	}

}
