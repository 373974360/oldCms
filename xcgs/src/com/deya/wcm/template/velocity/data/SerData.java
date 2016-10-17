package com.deya.wcm.template.velocity.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.cms.category.CateCurPositionBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.bean.zwgk.ser.SerCategoryBean;
import com.deya.wcm.bean.zwgk.ser.SerResouceBean;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.services.zwgk.ser.SerCategoryManager;
import com.deya.wcm.services.zwgk.ser.SerResouceManager;

public class SerData {
	/**
	 * 得到已发布的场景式服务列表
	 * @return String
	 */
	public static List<SerCategoryBean> getSerTopicList()
	{
		return SerCategoryManager.getSerCategoryRootListForPublish();
	}
	
	public static SerCategoryBean getSerROOTObject(String ser_id)
	{
		return SerCategoryManager.getRootSerCategoryBean(Integer.parseInt(ser_id));
	}
	
	public static SerCategoryBean getSerObject(String ser_id)
	{
		return SerCategoryManager.getSerCategoryBean(Integer.parseInt(ser_id));
	}
	
	public static List<SerCategoryBean> getChildSerList(String ser_id)
	{
		return SerCategoryManager.getChildSerCategoryList(Integer.parseInt(ser_id));
	}
	
	public static List<SerResouceBean> getSerResouceList(String ser_id)
	{
		return SerResouceManager.getSerResouceListByPublish(ser_id);
	}
	
	/**
	 * 根据条件得到相关信息列表（前台使用）
	 * @param String ser_id
	 * @param String i_type xgxx 相关信息 cjwt常见问题
	 * @return List<UserRegisterBean>
	 */
	public static List<InfoBean> getSerInfoList(String params)
	{			
		return InfoUtilData.getFWInfoList(getSerInfoSqlStr(params));
	}
	
	/**
	 * 根据条件得到相关信息总数，并返回翻页对象
	 * @param String params
	 * @return List<UserRegisterBean>
	 */
	public static TurnPageBean getSerInfoCount(String params)
	{
		return InfoUtilData.getFWInfoCount(getSerInfoSqlStr(params));
	}
	
	public static String getSerInfoSqlStr(String params)
	{
		int cat_id = 0;
		String ser_id = "";
		String info_type = "";
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{	
			if(tempA[i].toLowerCase().startsWith("ser_id="))
			{
				String s_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(s_id) && !s_id.startsWith("$ser_id") && FormatUtil.isValiditySQL(s_id))
				{					
					ser_id = s_id;			
				}				
			}
			if(tempA[i].toLowerCase().startsWith("info_type="))
			{
				String i_t = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(i_t) && !i_t.startsWith("$info_type") && FormatUtil.isValiditySQL(i_t))
				{					
					info_type = i_t;			
				}				
			}
		}
		try{
			SerCategoryBean scb = SerCategoryManager.getRootSerCategoryBean(Integer.parseInt(ser_id));
			if("cjwt".equals(info_type))
				cat_id = scb.getCjwt_cat_id();
			else
				cat_id = scb.getXgwt_cat_id();
			
			return "cat_id="+cat_id+";"+params;
		}
		catch(Exception e)
		{
			System.out.println("getSerInfoSqlStr  ser_id is null");
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 根据栏目ID，页面类型返回栏目的当前位置列表
	 * @param String cat_id
	 * @param String page_type
	 * @return List<CateogryBean>
	 */
	public static List<CateCurPositionBean> getCategoryPosition(String ser_id)
	{
		return SerCategoryManager.getSerCategoryTreeposition(Integer.parseInt(ser_id));
	}
	
	public static void main(String[] args)
	{
		System.out.println(getSerInfoList("ser_id=25;info_type=xgxx;cur_page=1;size=15;orderby=ci.released_dtime desc"));
		System.out.println(getSerInfoCount("ser_id=25;info_type=xgxx;cur_page=1;size=15;orderby=ci.released_dtime desc").getCount());
	}
}
