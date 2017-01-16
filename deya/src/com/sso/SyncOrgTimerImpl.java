package com.sso;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Description:
 * @User: like
 * @Date: 2016/12/8 11:29
 * @Version: 1.0
 * @Created with IntelliJ IDEA.
 */
public class SyncOrgTimerImpl implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SyncOrg.syncOrgDeptOrUser("user",1);
        SyncOrg.syncOrgDeptOrUser("dept",1);
    }
}

