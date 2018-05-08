package com.deya.wcm.services.zwgk.export;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.zwgk.export.ExportDept;

/**
 * 政务公开目录导出处理类
 * <p>Title: ExportInfoService</p>
 * <p>Description: 政务公开目录导出处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class ExportInfoRPC {
    
	//导出政务公开目录   条件：一个公开节点下面的某些栏目
	public static List<ExportDept> exportZwgkInfoByNodeCate(Map map){
        return ExportInfoService.exportZwgkInfoByNodeCate(map);
	} 
}
