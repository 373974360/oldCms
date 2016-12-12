package com.deya.wcm.timer;

import java.io.IOException;
import java.util.List;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.dao.system.ware.WareDAO;
import com.deya.wcm.services.system.ware.WareManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WareTimerImpl implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时生成标签开始执行*****" + DateUtil.getCurrentDateTime());
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
