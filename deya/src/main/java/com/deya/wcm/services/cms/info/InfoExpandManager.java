package com.deya.wcm.services.cms.info;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.util.FormatUtil;
import com.deya.util.io.FileOperation;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.cms.info.InfoDAO;
import com.deya.wcm.db.BoneDataSourceFactory;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.CategoryUtil;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.staticpage.VelocityPageContextImp;

public class InfoExpandManager
{
  public static String rs = JconfigUtilContainer.bashConfig().getProperty("on_off", "", "staticPageList");

  public static boolean setInfoTop(String istop, String info_id)
  {
    Map m = new HashMap();
    m.put("istop", istop);
    m.put("info_id", info_id);
    if (InfoDAO.setInfoTop(m))
    {
      InfoBean b = InfoBaseManager.getInfoById(info_id);
      if (b != null)
      {
        if ("on".equals(rs)) {
          CreateListPage(b.getCat_id(), b.getApp_id(), b.getSite_id());
        }
      }
      return true;
    }
    return false;
  }

  public static String upOrDownInfo(int info_id, String site_id, String order)
  {
    List list = new ArrayList();
    String sql = "";
    if ("oracle".equals(BoneDataSourceFactory.getDataTypeName()))
    {
      sql = "select * from (select id,title,content_url from cs_info where info_id > " + info_id + " and info_status=8 and final_status= 0 and site_id='" + site_id + "' ) where rownum = 1 union all " + 
        "select * from (select id,title,content_url from cs_info where info_id < " + info_id + " and info_status=8 and final_status= 0 and site_id='" + site_id + "' order by " + order + ") where rownum=1 ";
    } else if ("mssql".equals(BoneDataSourceFactory.getDataTypeName()))
    {
      sql = "select top 1 * from (select id,title,content_url from cs_info where info_id > " + info_id + " and info_status=8 and final_status= 0 and site_id='" + site_id + "') union all " + 
        "select top 1 * from (select id,title,content_url from cs_info where info_id < " + info_id + " and info_status=8 and final_status= 0 and site_id='" + site_id + "' order by " + order + ")";
    }
    else {
      sql = "select * from (select id,title,content_url from cs_info where info_id > " + info_id + " and info_status=8 and final_status= 0 and site_id='" + site_id + "') limit 0,1 union all " + 
        "select * from (select id,title,content_url from cs_info where info_id < " + info_id + " and info_status=8 and final_status= 0 and site_id='" + site_id + "' order by " + order + ") limit 0,1";
    }

    list = PublicTableDAO.executeSearchSql(sql);
    String str = "";
    String upinfo = "";
    String downinfo = "";
    if (list != null)
    {
      for (int i = 0; i < list.size(); i++)
      {
        Map m = (HashMap)list.get(i);
        String id = (String)m.get("ID");
        String title = (String)m.get("TITLE");
        String content_url = (String)m.get("CONTENT_URL");

        if (Integer.parseInt(id) > info_id)
        {
          upinfo = "<a target='_blank' href='" + content_url + "' title='" + FormatUtil.fiterHtmlTag(title) + "'> 上一篇   </a>&nbsp;&nbsp;";
        }
        else downinfo = "<a target='_blank' href='" + content_url + "' title ='" + FormatUtil.fiterHtmlTag(title) + "'> 下一篇   </a>";

        str = upinfo + downinfo;
      }
    }
    return str;
  }

  public static void CreateListPage(int cat_id, String app_id, String site_id)
  {
    String cur_page = "1";
    String node_id = "";
    String list_size = JconfigUtilContainer.bashConfig().getProperty("list_size", "", "staticPageList");

    String list_Filepath = CategoryUtil.getFoldePathByCategoryID(cat_id, app_id, site_id);
    String savePath = FormatUtil.formatPath(SiteManager.getSitePath(site_id) + list_Filepath);
    int page_num = Integer.parseInt(list_size);
    for (int i = 1; i < page_num; i++)
    {
      cur_page = i+"";
      VelocityPageContextImp velocityPageContextImp = new VelocityPageContextImp();
      velocityPageContextImp.setCcontext("cur_page", cur_page);
      velocityPageContextImp.setCcontext("site_id", site_id);
      velocityPageContextImp.setCcontext("cat_id", Integer.valueOf(cat_id));
      velocityPageContextImp.setTemplateID(cat_id+"", site_id, node_id, "list");
      String content = velocityPageContextImp.parseTemplate();
      try {
        FileOperation.writeStringToFile(savePath + File.separator + cat_id + "_" + cur_page + ".html", content, false, "utf-8");
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void CreateListPage(Set<Integer> cat_ids)
  {
    String cur_page = "1";
    String node_id = "";
    String list_size = JconfigUtilContainer.bashConfig().getProperty("list_size", "", "staticPageList");
    int page_num = Integer.parseInt(list_size) + 1;
    Iterator it = cat_ids.iterator();
    while (it.hasNext())
    {
      String cat_id = (String)it.next();
      CategoryBean catebean = CategoryManager.getCategoryBean(Integer.parseInt(cat_id));
      if (catebean != null)
      {
        String list_Filepath = CategoryUtil.getFoldePathByCategoryID(catebean.getCat_id(), catebean.getApp_id(), catebean.getSite_id());
        String savePath = FormatUtil.formatPath(SiteManager.getSitePath(catebean.getSite_id()) + list_Filepath);
        for (int i = 1; i < page_num; i++)
        {
          cur_page = i+"";
          VelocityPageContextImp velocityPageContextImp = new VelocityPageContextImp();
          velocityPageContextImp.setCcontext("cur_page", cur_page);
          velocityPageContextImp.setCcontext("site_id", catebean.getSite_id());
          velocityPageContextImp.setCcontext("cat_id", cat_id);
          velocityPageContextImp.setTemplateID(cat_id, catebean.getSite_id(), node_id, "list");
          String content = velocityPageContextImp.parseTemplate();
          try {
            FileOperation.writeStringToFile(savePath + File.separator + cat_id + "_" + cur_page + ".html", content, false, "utf-8");
          }
          catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public static String getStaticListPageUrl(String cat_id)
  {
    String list_Filepath = "";
    CategoryBean catebean = CategoryManager.getCategoryBean(Integer.parseInt(cat_id));
    if (catebean != null)
    {
      list_Filepath = CategoryUtil.getFoldePathByCategoryID(catebean.getCat_id(), catebean.getApp_id(), catebean.getSite_id()) + cat_id + "_1.htm";
    }
    return list_Filepath;
  }
}
