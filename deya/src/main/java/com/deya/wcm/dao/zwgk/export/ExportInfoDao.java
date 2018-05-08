package com.deya.wcm.dao.zwgk.export;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.zwgk.export.ExportInfo;
import com.deya.wcm.db.DBManager;

public class ExportInfoDao {

	//通过公开节点和该节点下的栏目得到信息列表
	public static List<ExportInfo> getGkinfoByNodeAndCat(Map map){
		return DBManager.queryFList("exprot_info.getGkinfoByNodeAndCat",map);
	}
	
	//通过公开节点和该节点下的栏目得到信息列表（有文号和简介）
	public static List<ExportInfo> getGkinfoCardByNodeAndCat(Map map){
		return DBManager.queryFList("exprot_info.getGkinfoCardByNodeAndCat",map);
	}
	
	//通过共享栏目ID和公开节点得到所共享的信息列表
	public static List<ExportInfo> getGkGXinfoByNodeAndCat(Map map){
		return DBManager.queryFList("exprot_info.getGkGXinfoByNodeAndCat",map);
	}
	
}
