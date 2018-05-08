package com.deya.wcm.services.control.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.control.SiteDomainDAO;
import com.deya.wcm.services.control.site.SiteManager;

public class SiteDomainManager implements ISyncCatch{
    private static Map<String,List<SiteDomainBean>> domain_map = new HashMap<String, List<SiteDomainBean>>();

    /**
     * 初始加载站点域名信息
     *
     * @param
     * @return
     */
    static{
        reloadCatchHandl();
    }

    public void reloadCatch()
    {
        reloadCatchHandl();
    }
    @SuppressWarnings("unchecked")
    public static void reloadCatchHandl()
    {
        domain_map.clear();
        List<SiteDomainBean> l = SiteDomainDAO.getSiteDomainList();
        if(l != null && l.size() > 0)
        {
            for(int i=0;i<l.size();i++)
            {
                if(domain_map.containsKey(l.get(i).getSite_id()))
                {
                    List<SiteDomainBean> sl = domain_map.get(l.get(i).getSite_id());
                    sl.add(l.get(i));
                    domain_map.put(l.get(i).getSite_id()+"",sl);
                }else
                {
                    List<SiteDomainBean> sl = new ArrayList<SiteDomainBean>();
                    sl.add(l.get(i));
                    domain_map.put(l.get(i).getSite_id()+"", sl);
                }

            }
        }
    }

    /**
     * 初始加载站点域名信息
     *
     * @param
     * @return
     */

    public static void reloadSiteDomainList()
    {
        SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.control.domain.SiteDomainManager");
    }

    /**
     * 根据站点ID得到域名列表
     *
     * @param String domain_id
     * @return SiteDomainBean
     */
    public static List<SiteDomainBean> getDomainListBySiteID(String site_id)
    {
        return domain_map.get(site_id);
    }

    /**
     * 根据域名ID得到域名对象
     *
     * @param String domain_id
     * @return SiteDomainBean
     */
    public static SiteDomainBean getSiteDomainBeanByID(String domain_id)
    {
        Set<String> set = domain_map.keySet();
        for(String s : set)
        {
            List<SiteDomainBean> dList = domain_map.get(s);
            for(SiteDomainBean sb : dList)
            {
                if(domain_id.equals(sb.getDomain_id()+""))
                {
                    return sb;
                }
            }
        }
        return null;
    }


    /**
     * 根据域名得到站点ID
     *
     * @param String domain
     * @return String site_id
     */
    public static String getSiteIDByDomain(String site_domain)
    {
        String site_id = "";
        Set<String> set = domain_map.keySet();
        for(String s : set)
        {
            List<SiteDomainBean> dList = domain_map.get(s);
            for(SiteDomainBean sb : dList)
            {
                if(sb.getSite_domain().equals(site_domain))
                {
                    site_id = sb.getSite_id();
                }
            }
        }
        return  site_id;
    }

    /**
     * 根据url得到站点ID
     *
     * @param String url
     * @return String site_id
     */
    public static String getSiteIDByUrl(String url)
    {
        return getSiteIDByDomain(FormatUtil.getDomainForUrl(url));
    }


    /**
     * 根据域名名称得到域名对象
     *
     * @param String site_domain
     * @return SiteDomainBean
     */
    public static SiteDomainBean getSiteDomainBeanByName(String site_domain)
    {
        Set<String> set = domain_map.keySet();
        for(String s : set)
        {
            List<SiteDomainBean> dList = domain_map.get(s);
            for(SiteDomainBean sb : dList)
            {
                if(site_domain.equals(sb.getSite_domain()))
                {
                    return sb;
                }
            }
        }
        return null;
    }

    /**
     * 根据域名得到站点ID
     * @param String site_domain
     * @return String
     */
    public static String getSiteByDomain(String site_domain)
    {
        SiteDomainBean sdb = getSiteDomainBeanByName(site_domain);
        if(sdb != null)
            return sdb.getSite_id();
        else
            return "";
    }

    /**
     * 判断主域名是否存在,用于修改站点信息时判断域名是否存在
     * @param Map
     * @param SettingLogsBean stl
     * @return boolean 返回true:允许修改,返回false:该域名已经存在,不允许修改
     * */
    public static boolean defaultDomainIsExist(String new_site_domain,String site_id)
    {
        //首先根据站点ID得到主域名对象
        String site_domain = getSiteDomainBySiteID(site_id);
        if(new_site_domain.equals(site_domain))
        {
            return true;
        }
        else
        {
            if(siteDomainISExist(new_site_domain))
            {
                return false;
            }
            else
                return true;
        }
    }

    /**
     * 根据站点ID得到它的主域名名称(用于在修改站点信息时,列出它域名名称)
     *
     * @param String site_id
     * @return String
     */
    public static String getSiteDomainBySiteID(String site_id)
    {
        String domain_name = "";
        List<SiteDomainBean> l = getDomainListBySiteID(site_id);
        if(l != null && l.size() > 0)
        {
            for(SiteDomainBean sdb : l)
            {
                if(site_id.equals(sdb.getSite_id()) && sdb.getIs_host() == 1)
                    domain_name = sdb.getSite_domain();
            }
        }
        return domain_name;
    }

    /**
     * 根据站点ID得到它的默认域名名称()
     *
     * @param String site_id
     * @return String
     */
    public static String getDefaultSiteDomainBySiteID(String site_id)
    {
        String domain_name = "";
        List<SiteDomainBean> l = getDomainListBySiteID(site_id);
        if(l != null && l.size() > 0)
        {
            for(SiteDomainBean sdb : l)
            {
                if(site_id.equals(sdb.getSite_id()) && sdb.getIs_default() == 1)
                    domain_name = sdb.getSite_domain();
            }
        }
        if("".equals(domain_name))
        {//如果没有默认的就给主的吧
            return getSiteDomainBySiteID(site_id);
        }
        return domain_name;
    }

    /**
     * 判断该域名是否存在
     *
     * @param String site_domain
     * @return boolean 如果存在返回true 否则返回false
     */
    public static boolean siteDomainISExist(String site_domain)
    {
        Set<String> set = domain_map.keySet();
        for(String s : set)
        {
            List<SiteDomainBean> dList = domain_map.get(s);
            for(SiteDomainBean sb : dList)
            {
                if(site_domain.equals(sb.getSite_domain()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断该域名是否在多域名中存在(不包括主域名)
     *
     * @param String site_domain
     * @return boolean 如果存在返回true 否则返回false
     */
    public static boolean siteDomainISExistNoHost(String site_domain)
    {
        Set<String> set = domain_map.keySet();
        for(String s : set)
        {
            List<SiteDomainBean> dList = domain_map.get(s);
            for(SiteDomainBean sb : dList)
            {
                if(site_domain.equals(sb.getSite_domain()) && "0".equals(sb.getIs_host()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 插入站点域名信息
     * @param SiteDomainBean sdb
     * @param SettingLogsBean stl
     * @return boolean
     * */
    public static boolean insertSiteDomain(SiteDomainBean sdb,SettingLogsBean stl)
    {
        if(!siteDomainISExist(sdb.getSite_domain()))
        {
            if(SiteDomainDAO.insertSiteDomain(sdb, stl))
            {
                reloadSiteDomainList();
                SiteManager.reloadSiteList();
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    /**
     * 修改站点域名信息
     * @param SiteDomainBean sdb
     * @param SettingLogsBean stl
     * @return boolean
     * */
    public static boolean updateSiteDomain(SiteDomainBean sdb,SettingLogsBean stl)
    {
        //首先根据域名得到对象
        SiteDomainBean sdb2 = getSiteDomainBeanByID(sdb.getDomain_id()+"");

        if(!sdb2.getSite_domain().equals(sdb.getSite_domain()))
        {
            if(SiteDomainDAO.updateSiteDomain(sdb, stl))
            {
                reloadSiteDomainList();
                SiteManager.reloadSiteList();
                return true;
            }
            else
                return false;
        }else
            return false;
    }

    /**
     * 修改站点域名名称(根据域名名称修改,用于在修改站点信息时修改域名)
     * @param String new_site_domain
     * @param String site_domain
     * @return int
     * 			返回0:表示域名没有修改过,不做操作
     * 			返回1:表示域名修改过,操作成功	
     * 			返回-1:表示域名修改过,但入库操作失败
     * */
    public static int updateSiteDomainByName(String new_site_domain,String site_id)
    {
        String site_domain = getSiteDomainBySiteID(site_id);
        //首先对比该域名和主域名是否一致,如果不一致,修改
        if(!new_site_domain.equals(site_domain))
        {
            Map<String, String> m = new HashMap<String, String>();
            m.put("new_site_domain", new_site_domain);
            m.put("site_domain", site_domain);
            if(SiteDomainDAO.updateSiteDomainByName(m))
            {
                reloadSiteDomainList();
                SiteManager.reloadSiteList();
                return 1;
            }
            else
                return -1;
        }
        return 0;
    }

    /**
     * 修改站点域名状态
     * @param String 域名ID
     * @param String site_id 站点ID
     * @param SettingLogsBean stl
     * @return boolean
     * */
    public static boolean updateSDomainStatus(String domain_id,String site_id,SettingLogsBean stl)
    {
        if(SiteDomainDAO.updateSDomainStatus(domain_id,site_id, stl))
        {
            reloadSiteDomainList();
            SiteManager.reloadSiteList();
            return true;
        }
        else
            return false;
    }

    /**
     * 根据域名ID删除域名
     * @param String domain_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
    public static boolean deleteSiteDomainByID(String domain_ids,SettingLogsBean stl)
    {
        Map<String, String> m = new HashMap<String, String>();
        m.put("domain_id", domain_ids);
        if(SiteDomainDAO.deleteSiteDomainByID(m, stl))
        {
            reloadSiteDomainList();
            SiteManager.reloadSiteList();
            return true;
        }
        else
            return false;
    }

    /**
     * 根据站点ID删除域名
     * @param String site_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
    public static boolean deleteSiteDomainBySiteID(String site_id,SettingLogsBean stl)
    {
        if(SiteDomainDAO.deleteSiteDomainBySiteID(site_id, stl))
        {
            reloadSiteDomainList();
            SiteManager.reloadSiteList();
            return true;
        }
        else
            return false;
    }

    public static void main(String args[])
    {

        System.out.println(getDomainListBySiteID("HIWCMeeee"));

        //System.out.println(deleteSiteDomainByID("6,7",new SettingLogsBean()));
    }

    public static void testInsertSiteDomain()
    {
        SiteDomainBean sdb = new SiteDomainBean();
        sdb.setDomain_id(16);
        sdb.setIs_default(1);
        sdb.setSite_domain("www.sx.gov.cn");
        sdb.setSite_id("site_id");
        updateSiteDomain(sdb,new SettingLogsBean());
    }
}
