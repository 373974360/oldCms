package com.deya.wcm.services.system.ware;

import java.io.IOException;
import java.util.List;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.dao.system.ware.WareDAO;
import com.deya.wcm.timer.TimerListener;
import com.deya.wcm.timer.TimerTaskJob;

public class WareTimerImpl implements TimerListener{
	static{				
		TimerTaskJob.registerPublishListener(new WareTimerImpl());
	}
	
	public void timingTask()
	{
		List<WareBean> ware_list = WareDAO.getTimerWareList(DateUtil.getCurrentDateTime());
		if(ware_list != null && ware_list.size() > 0)
		{					
			for(int i=0;i<ware_list.size();i++)
			{	
				try{
					WareManager.createWarePage(ware_list.get(i));
				}catch(IOException e)
				{
					e.printStackTrace();
					continue;
				}
			}
		}
	}
}
