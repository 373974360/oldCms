package com.deya.project.dz_szb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyan on 2016/12/13.
 */
public class SzbData {

    /**
     * 查询电子报详细信息
     *
     * @param params map ，包含 id
     * @return 返回电子报信息 bean
     */
    public SzbBean getSzb(Map<String, String> params) {
        return SzbManager.getSzb(params);
    }

    /**
     * 查询已经发布的数字报分页列表
     *
     * @param page     页码
     * @param pageSize 每页条数
     * @return
     */
    public List<SzbBean> getSzbList(int page, int pageSize) {
        if (page < 1) {
            page = 1;
        }
        HashMap<String,Object> params = new HashMap();
        params.put("start_num", (page - 1) * pageSize);
        params.put("page_size", pageSize);
        params.put("status", 1); //已发布

        return SzbManager.getSzbList(params);
    }

    /**
     * 查询数字报的已发布的总数
     * @return
     */
    public Integer getSzbCount() {
        Map<String, Object> params = new HashMap();
        params.put("status", 1); //已发布
        return Integer.valueOf(SzbManager.getSzbCount(params));
    }

    /**
     * 查询最新一起的数字报
     * @return
     */
    public SzbBean getNewestSzb() {
        SzbBean bean = SzbManager.getNewestSzb();
        return bean;
    }

}
