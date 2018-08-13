package com.deya.wcm.dao.cms.count;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.cms.category.CategoryManager;
import org.apache.commons.lang3.StringUtils;

public class CmsCountDAO {

    private static final String HOST = "0";    //主信息
    private static final String REF = "1";        //引用信息
    private static final String LINK = "2";    //链接信息

    /**
     * 根据栏目分类取得统计信息列表
     * (枝节点的统计为空,因为枝节点下没有直接的数据)
     *
     * @param mp 统计参数
     * @param lt 子栏目cat_ids映射
     * @return 统计信息map
     */
    @SuppressWarnings("unchecked")
    public static CmsCountBean getCountListByCat(Map<String, String> mp) {
        List<Map> result = DBManager.queryFList("getInfoCountListByCat", mp);
        CmsCountBean cntBean = new CmsCountBean();
        // 站点统计信息的map
        for (Map bean : result) {
            // 从SQL结果中提取取得的字段
            String is_host = bean.get("IS_HOST").toString();
            String cnt = bean.get("COUNT").toString();

            // 取得站点统计信息bean
            setCmsCountBean(cntBean, is_host, cnt);
        }
        return cntBean;
    }

    /**
     * 根据日期取得统计信息列表
     * (枝节点的统计为空,因为枝节点下没有直接的数据)
     *
     * @param mp
     * @return 统计信息列表
     */
    @SuppressWarnings("unchecked")
    public static Map<String, CmsCountBean> getCountListByDate(Map<String, String> mp) {
        List<Map> result = DBManager.queryFList("getInfoCountListByDate", mp);
        Map<String, CmsCountBean> rs_mp = new TreeMap<String, CmsCountBean>();
        for (Map bean : result) {
            // 从SQL结果中提取取得的字段
            String key = bean.get("RELEASED_DTIME").toString();
            String is_host = bean.get("IS_HOST").toString();
            String cnt = bean.get("COUNT").toString();

            // 取得站点统计信息bean
            CmsCountBean cntBean;
            if (!rs_mp.containsKey(key)) {
                cntBean = new CmsCountBean();
                cntBean.setTime(key);
                rs_mp.put(key, cntBean);
            }

            cntBean = rs_mp.get(key);
            setCmsCountBean(cntBean, is_host, cnt);
        }
        return rs_mp;
    }

    /**
     * 填充cmsCountBean,根据is_host的对应值将统计结果设置到对应的字段
     *
     * @param cntBean 需要设置数值的Bean
     * @param is_host 统计的信息类型HOST为主信息,REF引用信息,LINK为连接信息
     * @param cnt     is_host对应类型的统计结果
     */
    private static void setCmsCountBean(CmsCountBean cntBean, String is_host, String cnt) {
        if (HOST.equals(is_host)) {
            cntBean.setHostInfoCount(Integer.parseInt(cnt));
        } else if (REF.equals(is_host)) {
            cntBean.setRefInfoCount(Integer.parseInt(cnt));
        } else {
            cntBean.setLinkInfoCount(Integer.parseInt(cnt));
        }
    }

    /**
     * 取得站点下所有用户的录入的信息统计列表
     * 信息可以为某一时间段内,可以为"发布","未发布"等,默认取全部信息.
     *
     * @param mp 必须有site_id参数,可选参数start_day,end_day,info_status
     * @return 信息统计列表
     */
    @SuppressWarnings("unchecked")
    public static Map<String, CmsCountBean> getInputCountList(Map<String, String> mp) {

        List<Map> result = DBManager.queryFList("getInputCountList", mp);

        Map<String, CmsCountBean> ret = new HashMap<String, CmsCountBean>();
        for (Map m : result) {
            String count = "0";
            String input_user = "0";
            String user_name = "";
            if (m.get("COUNT") != null) {
                count = m.get("COUNT").toString(); // 取得返回的count列
            }
            if (m.get("USER_ID") != null) {
                input_user = m.get("USER_ID").toString(); // 取得返回的count列
            }
            if (m.get("USER_REALNAME") != null) {
                user_name = m.get("USER_REALNAME").toString(); // 取得返回的count列
            }
            CmsCountBean cmb = new CmsCountBean(input_user, user_name, count);
            ret.put(input_user, cmb);
        }
        return ret;
    }

    /**
     * 取得站点下特定用户的信息统计情况
     * 参数input_user必须添加到map中
     *
     * @param mp
     * @return 取得的信息统计情况
     */
    @SuppressWarnings("unchecked")
    public static Map<String, CmsCountBean> getInputCountListByUserID(Map<String, String> mp) {

        //System.out.println("--dao--getInputCountListByUserID-- mp = " + mp);
        List<Map> result = DBManager.queryFList("getInputCountListByUserID", mp);

        String input_users = (String) mp.get("input_user");
        if (input_users == null) {
            input_users = "0";
        }
        int input_user = Integer.parseInt(input_users);

        Map<String, CmsCountBean> ret = new HashMap<String, CmsCountBean>();
        for (Map m : result) {
            String count = m.get("COUNT").toString(); // 取得返回的count列
            String cat_id = m.get("CAT_ID").toString();
            String cat_name = m.get("CAT_CNAME").toString();

            CmsCountBean cmb = new CmsCountBean();
            cmb.setCat_id(Integer.parseInt(cat_id));
            cmb.setCat_name(cat_name);
            cmb.setCount(Integer.parseInt(count));
            cmb.setInput_user(input_user);
            ret.put(cat_id, cmb);
        }
        return ret;
    }


    /**
     * 取得站点下特定用户的信息统计情况
     * 参数input_user必须添加到map中
     *
     * @param mp
     * @return 取得的信息统计情况
     */
    @SuppressWarnings("unchecked")
    public static Map<String, CmsCountBean> getInputCountListByCate(Map<String, String> mp) {

        mp.remove("cat_ids");
        //System.out.println("--dao--getInputCountListByCate-- mp = " + mp);
        List<Map> result = DBManager.queryFList("getInputCountListByUserID", mp);

        String input_users = (String) mp.get("input_user");
        if (input_users == null) {
            input_users = "0";
        }
        int input_user = Integer.parseInt(input_users);

        Map<String, CmsCountBean> ret = new HashMap<String, CmsCountBean>();
        for (Map m : result) {
            String count = m.get("COUNT").toString(); // 取得返回的count列
            String cat_id = m.get("CAT_ID").toString();
            String cat_name = m.get("CAT_CNAME").toString();

            CmsCountBean cmb = new CmsCountBean();
            cmb.setCat_id(Integer.parseInt(cat_id));
            cmb.setCat_name(cat_name);
            cmb.setCount(Integer.parseInt(count));
            cmb.setInput_user(input_user);
            ret.put(cat_id, cmb);
        }
        return ret;
    }

    /**
     * 取用户工作量统计信息详细列表
     *
     * @param mp
     * @return 用户工作量统计信息详细列表
     */
    public static List<InfoBean> getInfoListByUserIDTimeType(Map<String, String> map) {
        return DBManager.queryFList("info_count.getInfoListByUserIDTimeType", map);
    }

    /**
     * 取得所选栏目下的信息更新情况
     *
     * @param mp
     * @return 取得所选栏目下的信息更新情况
     */
    @SuppressWarnings("unchecked")
    public static List<SiteCountBean> getInfoUpdateListByCate(Map<String, String> mp) {
        List<SiteCountBean> returnList = new ArrayList<SiteCountBean>();
        TreeMap<Integer, CategoryBean> category_m = CategoryManager.category_m;
        //System.out.println("--dao--getInfoUpdateListByCate-- mp = " + mp);
        String group_ids = mp.get("group_ids");
        String cat_ids = mp.get("cat_ids");
        if (group_ids != null) {
            String group_id[] = group_ids.split(",");
            for (int i = 0; i < group_id.length; i++) {
                mp.put("group_id", group_id[i]);
                List<Map> result = DBManager.queryFList("getInfoUpdateListByCate", mp);
                ArrayList<SiteCountBean> arrayList = new ArrayList<SiteCountBean>();
                returnList.add(doOutChild(result, group_id[i]));
            }

        } else if (cat_ids != null) {
            List<Map> result = DBManager.queryFList("getInfoUpdateListByCate", mp);
            String cat_id[] = cat_ids.split(",");
            for (int i = 0; i < cat_id.length; i++) {
                boolean isExist = false;
                if (returnList != null && returnList.size() > 0) {
                    for (SiteCountBean scb : returnList) {
                        CategoryBean cgb = (CategoryBean) category_m.get(Integer.parseInt(cat_id[i]));
                        if (cgb.getCat_position().contains(scb.getCat_id() + "")) {
                            isExist = true;
                        }
                    }
                }
                if (!isExist) {
                    ArrayList<SiteCountBean> arrayList = new ArrayList<SiteCountBean>();
                    returnList.add(doOutChild(result, cat_id[i]));
                }
            }
        }
        return returnList;
    }

    private static SiteCountBean doOutChild(List<Map> map, String cat_id) {
        List<CategoryBean> cat_child = new ArrayList<CategoryBean>();
        TreeMap<Integer, CategoryBean> category_m = CategoryManager.category_m;
        Set set = category_m.keySet();
        for (Iterator localIterator = set.iterator(); localIterator.hasNext(); ) {
            int i = ((Integer) localIterator.next()).intValue();
            CategoryBean cgb = (CategoryBean) category_m.get(Integer.valueOf(i));
            if (Integer.parseInt(cat_id) == cgb.getParent_id()) {
                cat_child.add(cgb);
            }
        }
        SiteCountBean cmb = new SiteCountBean();
        cmb.setCat_id(Integer.parseInt(cat_id));
        cmb.setCat_name(category_m.get(Integer.parseInt(cat_id)).getCat_cname());
        if (cat_child != null && cat_child.size() > 0) {
            cmb.setIs_leaf(false);
            List<SiteCountBean> child = new ArrayList<SiteCountBean>();
            for (int i = 0; i < cat_child.size(); i++) {
                SiteCountBean cmb2 = doOutChild(map, cat_child.get(i).getCat_id() + "");
                child.add(cmb2);
            }
            if (child != null && child.size() > 0)
                Collections.sort(child, new SiteCountBeanComparator());
            cmb.setChild_list(child);
        } else {
            cmb.setIs_leaf(true);
            for (Map m : map) {
                String cat_id2 = m.get("CAT_ID").toString();
                if (Integer.parseInt(cat_id) == Integer.parseInt(cat_id2)) {
                    String lastTime = m.get("LASTTIME").toString(); // 取得返回的count列
                    cmb.setTime(lastTime);
                }
            }
        }
        return cmb;
    }

    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return -1;
            }
            if (dt1.getTime() < dt2.getTime()) {
                return 1;
            }
            return 1;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 1;
    }

    /**
     * 取得站点下所有作者的录入的信息统计列表
     * 信息可以为某一时间段内,可以为"发布","未发布"等,默认取全部信息.
     *
     * @param mp 必须有site_id参数,可选参数start_day,end_day,info_status
     * @return 信息统计列表
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getAuthorCountList(Map<String, String> mp) {

        List<Map> result = DBManager.queryFList("getAuthorCountList", mp);

        Map<String, String> ret = new HashMap<String, String>();
        for (Map m : result) {
            String count = m.get("COUNT").toString(); // 取得返回的count列
            String author = m.get("author").toString();
            ret.put(author, count);
        }
        return ret;
    }

    private static class SiteCountBeanComparator implements Comparator<Object> {
        public int compare(Object o1, Object o2) {

            SiteCountBean cgb1 = (SiteCountBean) o1;
            SiteCountBean cgb2 = (SiteCountBean) o2;
            String time1 = cgb1.getTime();
            String time2 = cgb2.getTime();
            if (time1 != null && !"".equals(time1)) {
                if (time2 != null && !"".equals(time2)) {
                    return compare_date(time1, time2);
                } else {
                    return -1;
                }
            } else {
                return 1;
            }
        }
    }

    public static String getCountBySource(String date) {
        String[] source = {"wt", "mh", "app", "wx", "zzzd"};
        String json = "";
        String chlcode = "";
        String chlname = "";
        String surveySource = "";
        for (String str : source) {
            if (str.equals("wt")) {
                chlname = "网厅";
                chlcode = "02";
                surveySource=str;
            }
            if (str.equals("mh")) {
                chlname = "门户网站";
                chlcode = "04";
                surveySource="pc";
            }
            if (str.equals("app")) {
                chlname = "手机app";
                chlcode = "03";
                surveySource=str;
            }
            if (str.equals("wx")) {
                chlname = "微信";
                chlcode = "05";
                surveySource=str;
            }
            if (str.equals("zzzd")) {
                chlname = "自主终端";
                chlcode = "08";
                surveySource=str;
            }
            Map<String, String> map = new HashMap<>();
            map.put("source", str);
            map.put("date", date);
            String newsCount = DBManager.getString("getCountBySource_count", map);
            String newsHits = DBManager.getString("getCountBySource_hits", map);
            String newsDownloadHits = DBManager.getString("getCountBySource_download", map);
            map.put("surveySource", surveySource);
            String surveyCount = DBManager.getString("getCountBySource_survey", map);
            if(StringUtils.isEmpty(newsCount)){
                newsCount="0";
            }
            if(StringUtils.isEmpty(newsHits)){
                newsHits="0";
            }
            if(StringUtils.isEmpty(newsDownloadHits)){
                newsDownloadHits="0";
            }
            if(StringUtils.isEmpty(surveyCount)){
                surveyCount="0";
            }
            json += ",{\"chlcode\":\"" + chlcode + "\",\"chlname\":\"" + chlname + "\",\"i_date\":\"" + date + "\",\"openinfoquery\":\"" + newsHits + "\",\"openinfopush\":\"" + newsCount + "\",\"newsDownloadHits\":\"" + newsDownloadHits + "\",\"otherinfopush\":\"0\",\"onlinesurvey\":\"" + surveyCount + "\"}";
        }
        json = json.substring(1);
        return json;
    }
}
