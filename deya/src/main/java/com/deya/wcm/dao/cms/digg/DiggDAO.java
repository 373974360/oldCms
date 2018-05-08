package com.deya.wcm.dao.cms.digg;
/**
 * 用户对信息的 支持/反对 操作的    数据层处理类
 * <p>Title: CicroDate</p>
 * <p>Description: 用户对信息的支持/反对操作</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author 王磊
 * @version 1.0
 * * 
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.digg.InfoDiggBean;
import com.deya.wcm.bean.cms.digg.InfoDiggLogBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * @author Administrator
 *
 */
public class DiggDAO {
	
	/**
	 * 根据信息ID取得info_digg对象
	 * @param info_id
	 * @return
	 */
	public static InfoDiggBean getInfoDigg(String info_id)
	{
		return (InfoDiggBean)DBManager.queryFObj("getInfoDigg", info_id);
	}
	
	/**
	 * 取得信息Digg的条数
	 * @param mp	取得条件，包括model_id,release_time,和排序等
	 * @return
	 */
	public static String getInfoDiggCnt(Map<String, String> mp)
	{
		return (String)DBManager.queryFObj("getInfoDiggListCnt", mp);
	}
	
	/**
	 * 取得信息Digg的列表
	 * @param mp	取得条件，包括model_id,release_time,分页信息和排序等
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getInfoDigg(Map<String, String> mp)
	{
		return DBManager.queryFList("getInfoDiggList", mp);
	}

	/**
	 * 新增  信息 的支持度
	 * @param digg
	 * @param log
	 * @return
	 */
	public static boolean insertInfoDigg(InfoDiggBean digg)
	{
		return DBManager.insert("insert_InfoDigg",digg);
	}
	
	
	/**
	 * 修改  信息 的支持度
	 * @param digg
	 * @param log
	 * @return
	 */
	public static boolean updateInfoDigg(InfoDiggBean digg)
	{
		return DBManager.update("update_InfoDigg",digg);
	}
	
	
	
	
	/**
	 * 得到  指定用户 对指定信息的支持情况
	 */
	public static InfoDiggLogBean getDiggLog(String info_id,String user)
	{
		return (InfoDiggLogBean)DBManager.queryFObj("get_InfoDiggLog",info_id+"");
	} 
	
	
	/**
	 * 插入  指定用户 对指定信息的支持日志情况
	 * @param log
	 * @return
	 */
	public static boolean insertInfoDiggLog(InfoDiggLogBean log){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_DIGG_LOG_TABLE_NAME);
		log.setId(id);
		return DBManager.insert("insert_InfoDiggLog",log);
	}
	
}
