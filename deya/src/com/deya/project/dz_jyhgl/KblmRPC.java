package com.deya.project.dz_jyhgl;

import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.services.cms.count.CmsCountManager;

import java.util.List;
import java.util.Map;

public class KblmRPC {

    /**
     * 取得所选栏目下的工作量统计信息
     *
     * @param mp
     * @return 取得所选栏目下的工作量统计信息列表
     */
    public static List<SiteCountBean> getNullCategoryByCount(Map<String, String> mp) {
        return KblmManager.getNullCategoryByCount(mp);
    }
}
