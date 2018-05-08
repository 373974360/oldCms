package com.deya.wcm.timer;

import java.io.IOException;
import java.util.List;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.dao.page.PageDAO;
import com.deya.wcm.services.page.PageManager;
import org.jsoup.helper.DataUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PageTimerImpl implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时生成页面开始执行*****" + DateUtil.getCurrentDateTime());
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
