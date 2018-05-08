package com.deya.wcm.template.velocity.impl;

import java.util.HashMap;
import java.util.Map;

import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.services.system.ware.WareInfoManager;
import com.deya.wcm.template.velocity.VelocityContextAbstract;

public class VelocityWareContextImp extends VelocityContextAbstract{
	public VelocityWareContextImp(WareBean wb)
	{
		if(wb.getWare_type() == 2)
		{
			Map<String,String> m = new HashMap<String,String>();
			m.put("ware_id",wb.getWare_id());
			m.put("app_id",wb.getApp_id());
			m.put("site_id",wb.getSite_id());
			vcontext.put("wareData", WareInfoManager.getWareInfoList(m));
		}		
	}
}
