package com.deya.wcm.dao.zwgk.info;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.deya.wcm.bean.cms.info.GKFbsznBean;
import com.deya.wcm.bean.cms.info.GKInfoBean;
import com.deya.wcm.bean.cms.info.GKResFileBean;
import com.deya.wcm.bean.cms.info.RelatedInfoBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  公开信息数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 公开信息数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class GKInfoDAO {
	/**
	 * 根据共享目录得到公开信息总数（前台使用）
	 * @param Map<String, String> m
	 * @return String
	 */
	public static String getBroGKInfoCountForSharedCategory(Map<String, String> m)
	{
		return DBManager.getString("getBroGKInfoCountForSharedCategory", m);
	}
	
	/**
	 * 根据共享目录得到公开信息列表（前台使用）
	 * @param Map<String, String> m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<GKInfoBean> getBroGKInfoListForSharedCategory(Map<String, String> m)
	{
		return DBManager.queryFList("getBroGKInfoListForSharedCategory", m);
	}
	
	/**
	 * 得到公开信息列表（前台使用）
	 * @param Map<String, String> m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<GKInfoBean> getBroGKInfoList(Map<String, String> m)
	{
		return DBManager.queryFList("getBroGKInfoList", m);
	}
	
	/**
	 * 得到公开信息列表（前台使用）
	 * @param Map<String, String> m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<GKFbsznBean> getBroGKBSZNInfoList(Map<String, String> m)
	{
		return DBManager.queryFList("getBroGKBSZNInfoList", m);
	}
	
	/**
	 * 得到公开信息总数（前台使用）
	 * @return String
	 */
	public static String getBroGKInfoCount(Map<String, String> m)
	{
		return DBManager.getString("getBroGKInfoCount", m);
	}
	
	public static GKInfoBean getGKInfoBean(String info_id)
	{
		return (GKInfoBean)DBManager.queryFObj("getGKInfoBean", info_id);
	}
	
	//根据关键词自动找到相关信息
	@SuppressWarnings("unchecked")
	public static List<RelatedInfoBean> getReleGKInfoListForAuto(Map<String, String> map){
		return DBManager.queryFList("getReleGKInfoListForAuto", map);
	}
	
	/**
	 * 得到所有公开信息列表
	 * @param Map<String, String> m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<GKInfoBean> getGKInfoList(Map<String, String> m)
	{
		return DBManager.queryFList("getGKInfoList", m);
	}
	
	/**
	 * 判断索引号是否已存在，返回info_id
	 * @param String
	 * @return String
	 */
	public static String getInfoIdForGKIndex(String gk_index)
	{
		return DBManager.getString("getInfoIdForGKIndex", gk_index);
	}
	
	/**
	 * 得到所有公开信息总数
	 * @return String
	 */
	public static String getGKInfoCount(Map<String, String> m)
	{
		return DBManager.getString("getGKInfoCount", m);
	}
	
	/**
	 * 插入公开公用表
	 * @param Object ob
	 * @return boolean
	 */
	public static boolean insertGKInfo(Object ob)
	{
		if(DBManager.insert("insert_gk_info", ob))
		{
			GKInfoBean gif = (GKInfoBean)ob;
			insertGKResFile(gif.getInfo_id(),gif.getFile_list());
			return true;
		}else
			return false;
	}
	
	
	/**
	 * 修改公开公用表
	 * @param Object ob
	 * @return boolean
	 */
	public static boolean updateGKInfo(Object ob)
	{
		if(DBManager.update("update_gk_info", ob))
		{
			try {
				String from_id  = BeanUtils.getProperty(ob, "from_id");
				//判断一下，如果是引用的信息，不处理附件
				if("0".equals(from_id))
				{
					GKInfoBean gif = (GKInfoBean)ob;
					insertGKResFile(gif.getInfo_id(),gif.getFile_list());
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除公开公用表
	 * @param String info_ids
	 * @return boolean
	 */
	public static boolean deleteGKInfo(String info_ids)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("info_ids", info_ids);
		if(DBManager.delete("delete_gk_info", m))
		{
			deleteGKResFile(info_ids);
			return true;
		}else
			return false;
	}
	
	/**
	 * 清空回收站
	 * @param String info_ids
	 * @return boolean
	 */
	public static boolean clearGKInfo(Map<String,String> m)
	{
		if(DBManager.delete("clear_gk_info", m))
		{
			clearGKResFile(m);
			return true;
		}else
			return false;
	}
	
	/**
	 * 插入公开附件公用表
	 * @param int info_id
	 * @param List<GKResFileBean> gf_list
	 * @return boolean
	 */
	public static boolean insertGKResFile(int info_id,List<GKResFileBean> gf_list)
	{
		try{
			if(deleteGKResFile(info_id+""))
			{
				if(gf_list != null && gf_list.size() > 0)
				{
					for(GKResFileBean gfb : gf_list)
					{
						gfb.setRes_id(PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_RESFILE_TABLE_NAME));
						DBManager.insert("insert_gk_resFile", gfb);
					}
				}
				return true;
			}else
				return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * 删除公开附件公用表
	 * @param String info_id
	 * @return boolean
	 */
	public static boolean deleteGKResFile(String info_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		
		if(info_id.contains(","))
		{
			m.put("info_ids", info_id);
		}else
		{
			m.put("info_id", info_id);
		}
		return deleteGKResFile(m);
	}
	
	public static boolean deleteGKResFile(Map<String,String> m)
	{
		return DBManager.delete("delete_gk_resFile", m);
	}
	
	/**
	 * 得到公开附件公用表
	 * @param String info_id
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static List<GKResFileBean> getGKResFileList(String info_id)
	{
		return DBManager.queryFList("getGKResFileList", info_id);
	}
	
	/**
	 * 清空附件公用表
	 * @param String info_ids
	 * @return boolean
	 */
	public static boolean clearGKResFile(Map<String,String> m)
	{
		return DBManager.delete("clear_gk_resFile", m);
	}
	
	/**
	 * 得到gk_year内容，用于移动时重新生成索引号用
	 * @param String info_id
	 * @return boolean
	 */
	public static String getGKYearStr(String info_id)
	{
		return DBManager.getString("getGKYearStr", info_id);
	}
	
	/**
	 * 获取公开所有信息,用于重新生成索引号
	 * @param Map<String, String> m
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<GKInfoBean> getAllGKInfoList(Map<String, String> m)
	{		
		return DBManager.queryFList("getAllGKInfoList", m);
	}
	
	/**
	 * 修改索引号
	 * @param Map<String, String> m
	 * @return List
	 */
	public static boolean updateGKIndex(String info_id,String gk_index,String gk_num)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("gk_index", gk_index);
		m.put("gk_num", gk_num);
		m.put("info_id", info_id);
		return DBManager.update("update_gkinfo_index", m);
	}
	
	/**
	 * 删除索引号序号规则表
	 * @param Map<String, String> m
	 * @return boolean
	 */
	public static boolean deleteGKIndexSequence(String node_id)
	{
		Map<String, String> m = new HashMap<String, String>();
		if(node_id != null && !"".equals(node_id))
		{
			m.put("site_id", node_id);
		}
		return DBManager.delete("delete_gk_sequence", m);
	}
	
	/**
	 * 按年度或季度统计公开的发布量
	 * @param String type
	 * @return String
	 */
	public static String getGKPublishStatistics(Map<String,String> m)
	{
		return DBManager.getString("getGKPublishStatistics", m);
	}
}
