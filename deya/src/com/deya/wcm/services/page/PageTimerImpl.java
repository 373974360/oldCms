package com.deya.wcm.services.page;

import java.io.IOException;
import java.util.List;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.dao.page.PageDAO;
import com.deya.wcm.timer.TimerListener;
import com.deya.wcm.timer.TimerTaskJob;

public class PageTimerImpl implements TimerListener{
	static{
		TimerTaskJob.registerPublishListener(new PageTimerImpl());
	}
	
	public void timingTask()
	{
		List<PageBean> pb_list = PageDAO.getTimerPageList(DateUtil.getCurrentDateTime());
		if(pb_list != null && pb_list.size() > 0)
		{					
			for(PageBean pb : pb_list)
			{	
				try{
					PageManager.createHtmlPage(pb);
				}catch(IOException e)
				{
					e.printStackTrace();
					continue;
				}
			}
		}
	}
	
}
