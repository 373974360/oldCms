package com.deya.wcm.timer;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.services.cms.info.InfoPublishManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoTimerImpl implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("定时自动发布信息开始执行*****" + DateUtil.getCurrentDateTime());
        String cancel_ids = "";
        List<InfoBean> info_list = InfoDAO.getAtuoPublishInfoList();
        if (info_list != null && info_list.size() > 0) {
            for (InfoBean info : info_list) {
                if (info.getInfo_status() == 8) {//需要撤消的
                    cancel_ids += "," + info.getInfo_id();
                    InfoPublishManager.cancelAfterEvent(info);
                } else {
                    info.setInfo_status(8);
                    Map<String, String> m = new HashMap<String, String>();
                    m.put("info_id", info.getInfo_id() + "");
                    m.put("info_status", "8");
                    m.put("auto_type", "is_auto_up");
                    m.put("auto_time", "up_dtime");
                    if (info.getReleased_dtime() == null || "".equals(info.getReleased_dtime()))
                        m.put("released_dtime", DateUtil.getCurrentDateTime());
                    else
                        m.put("released_dtime", info.getReleased_dtime());
                    InfoDAO.updateAutoPublishVal(m);
                    InfoPublishManager.publishAfterEvent(info);//这样取出来的数据站点不同，栏目也不同，所以不能使用批量的工具
                }
            }
            if (cancel_ids != null && !"".equals(cancel_ids)) {//批量撤消一下
                cancel_ids = cancel_ids.substring(1);
                Map<String, String> m = new HashMap<String, String>();
                m.put("info_ids", cancel_ids);
                m.put("info_status", "3");
                m.put("auto_type", "is_auto_down");
                m.put("auto_time", "down_dtime");
                m.put("released_dtime", "");
                InfoDAO.updateAutoPublishVal(m);
            }

        }
    }
}
