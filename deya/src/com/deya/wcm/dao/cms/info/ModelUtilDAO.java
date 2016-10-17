package com.deya.wcm.dao.cms.info;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.info.GKPicBean;
import com.deya.wcm.bean.cms.info.PicBean;
import com.deya.wcm.bean.cms.info.PicItemBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.info.GKInfoDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.cms.info.InfoBaseManager;

/**
 * 内容模型相关处理DAO类，包括文章模型，通用图组新闻，通用视频新闻，用户自定义字段的
 * 添加，修改等操作
 * @author liqi
 *
 */
public class ModelUtilDAO {
	
	/**
	 * 添加相关模型，模型可以是文章模型，通用图组新闻，通用视频新闻，用户自定义字段
	 * 中的任何一个
	 * @param o	具体模型的Bean对象
	 * @param sqlName	模型对应的MyBatis配置文件中的ID
	 * @param String model_name
	 * @param stl
	 * @return	
	 */
	public static boolean addModel(Object o, String sqlName,String model_name, SettingLogsBean stl)
	{
		if(InfoBaseManager.addInfo(o, stl))
		{
			/*
			if(model_name.toLowerCase().contains("gk"))
			{//如果英文名含有gk,表示是公开的内容模型，需要添加内容模型的公用表
				GKInfoDAO.insertGKInfo(o);
			}
			*/
			GKInfoDAO.insertGKInfo(o);
			if(!"".equals(sqlName))
			{
				if(sqlName.equals("insert_info_pic"))
				{
					return insertPicInfo(o,model_name);
				}
				else
				{
					return DBManager.insert(sqlName, o);
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改相关模型，模型可以是文章模型，通用图组新闻，通用视频新闻，用户自定义字段
	 * 中的任何一个
	 * @param o	具体模型的Bean对象
	 * @param sqlName	模型对应的MyBatis配置文件中的ID
	 * @param String model_name
	 * @param stl
	 * @return
	 */
	public static boolean updateModel(Object o, String sqlName,String model_name, SettingLogsBean stl)
	{
		if(InfoBaseManager.updateInfo(o, stl))
		{
			/*
			if(model_name.toLowerCase().contains("gk"))
			{//如果英文名含有gk,表示是公开的内容模型，需要添加内容模型的公用表
				GKInfoDAO.updateGKInfo(o);
			}
			*/
			GKInfoDAO.updateGKInfo(o);
			if(!"".equals(sqlName))
			{
				if(sqlName.equals("update_info_pic"))
				{
					return insertPicInfo(o,model_name);
				}
				else
					return DBManager.update(sqlName, o);
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 根据站点ID，信息ID取得模型信息
	 * @param infoId	信息ID
	 * @param siteId	站点ID
	 * @param sqlName	模型对应的MyBatis配置文件中的ID
	 * @return	取得的Object对象
	 */
	public static Object selectModel(String infoId, String siteId, String sqlName)
	{
		Map<String, String> map = new HashMap<String, String>();		
		map.put("info_id", infoId);
		return DBManager.queryFObj(sqlName, map);
	}
	
	/**
	 * 组图内容模型添加特殊处理
	 * @param Object o
	 * @return	boolean
	 */
	public static boolean insertPicInfo(Object o,String model_name)
	{		
		List<PicItemBean> list = null;
		int info_id = 0;
		if("gkpic".equals(model_name))
		{
			GKPicBean pb = (GKPicBean)o;
			list = pb.getItem_list();
			info_id = pb.getInfo_id();
		}
		if("pic".equals(model_name))
		{
			PicBean pb = (PicBean)o;
			list = pb.getItem_list();
			info_id = pb.getInfo_id();
		}
		//先删除
		if(deletePicInfo(info_id))
		{
			if(list != null && list.size() > 0)
			{
				try{
					for(PicItemBean pib : list)
					{
						pib.setPic_id(PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_PIC_TABLE_NAME));
						pib.setInfo_id(info_id);
						DBManager.insert("insert_info_pic", pib);
					}
					return true;
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
			else			
				return false;
		}else			
			return false;
	}
	
	/**
	 * 删除组图内容模型数据
	 * @param int id
	 * @return	boolean
	 */
	public static boolean deletePicInfo(int id)
	{
		return DBManager.delete("delete_info_pic", id);
	}
	
	public static void main(String[] args)
	{
		System.out.println(selectModel("10","dd","getPicInfoBean"));
	}
	
}
