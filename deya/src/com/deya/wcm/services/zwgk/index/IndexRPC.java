package com.deya.wcm.services.zwgk.index;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.index.IndexRoleBean;
import com.deya.wcm.bean.zwgk.index.IndexSequenceBean;
import com.deya.wcm.services.Log.LogManager;

public class IndexRPC {

	/**
	 * 生成索引号的方法,索引生成失败返回NULL
	 * @param nodeId	节点ID
	 * @param seqYmd	年份( yyyy || yyyyMM )
	 * @param catId		类目ID
	 * @param seq   	手动设置的流水号，如果为空则自动生成
	 * @param stl	
	 * @return	生成的索引号
	 */
	public static Map<String, String> getIndex(String nodeId, String catId, String ymd, String seq)
	{
		return IndexManager.getIndex(nodeId, catId, ymd, seq);
	}
	
	/**
	 * 取得所有的索引生成规则
	 * @return
	 */
	public static List<IndexRoleBean> getRoleList()
	{
		return IndexManager.getRoleList();
	}
	
	/**
	 * 修改索引生成规则
	 * @param lt	索引生成规则列表
	 * @param request
	 * @return
	 */
	public static boolean updateIndexRole(List<IndexRoleBean> lt, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return IndexManager.updateIndexRole(lt, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 重置索引号生成的流水号信息
	 * @param nodeId 节点ID	
	 * @param catId	类目ID
	 * @param ymd	年份
	 * @param request
	 * @return
	 */
	public static boolean resetSequence(String nodeId, String catId, String ymd, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return IndexManager.resetSequence(nodeId, catId, ymd, stl);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}

}
