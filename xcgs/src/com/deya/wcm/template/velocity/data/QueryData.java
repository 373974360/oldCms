package com.deya.wcm.template.velocity.data;

import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.query.QueryDicBean;
import com.deya.wcm.bean.query.QueryItemBean;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.services.query.QueryDicManager;
import com.deya.wcm.services.query.QueryItemManager;
import java.io.PrintStream;
import java.util.List;

public class QueryData
{
  private static int cur_page = 1;
  private static int page_size = 15;

  public static List<QueryItemBean> getQueryList(String requet_params, String params, String confid)
  {
    int current_page = 1;
    int page_sizes = Integer.parseInt(JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num"));
    String con = getQuerySearchCon(requet_params, params, confid);
    	//System.out.println(" getQueryList ============" + con);
    String[] tempParam = params.split(";");
    for (int i = 0; i < tempParam.length; ++i)
    {
      if (tempParam[i].toLowerCase().startsWith("size="))
      {
        String ps = FormatUtil.formatNullString(tempParam[i].substring(tempParam[i].indexOf("=") + 1));
        if ((!("".equals(ps))) && (!(ps.startsWith("$size"))) && (FormatUtil.isNumeric(ps)))
          page_sizes = Integer.parseInt(ps);
      }
      if (!(tempParam[i].toLowerCase().startsWith("cur_page=")))
        continue;
      String cp = FormatUtil.formatNullString(tempParam[i].substring(tempParam[i].indexOf("=") + 1));
      if ((!("".equals(cp))) && (!(cp.startsWith("$cur_page"))) && (FormatUtil.isNumeric(cp))) {
        current_page = Integer.parseInt(cp);
      }
    }
    return QueryItemManager.getQueryListBrowser(con, confid, (current_page - 1) * page_sizes, page_sizes);
  }

  public static TurnPageBean getQueryCount(String requet_params, String params, String conf_id)
  {
    TurnPageBean tpb = new TurnPageBean();
    tpb.setCount(Integer.parseInt(QueryItemManager.getQueryListCountBrowser(getQuerySearchCon(requet_params, params, conf_id))));
    tpb.setCur_page(cur_page);
    tpb.setPage_size(page_size);
    tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
    if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
      tpb.setPage_count(tpb.getPage_count() - 1);
    }
    if (cur_page > 1) {
      tpb.setPrev_num(cur_page - 1);
    }
    tpb.setNext_num(tpb.getPage_count());
    if (cur_page < tpb.getPage_count())
      tpb.setNext_num(cur_page + 1);
    return tpb;
  }

  public static String getQuerySearchCon(String requet_params, String param, String conf_id)
  {
    String retult_item = "";
    String search = "";
    String table_view = "";
    if(!(requet_params.trim().endsWith("conf_id=" + conf_id) || !isValiditySQL(requet_params.trim())))
       table_view = "from cs_dz_cx_" + conf_id + " where 1=1";
    else{
       table_view = "from cs_dz_cx_" + conf_id + " where item_id=0";
    }
    String[] temp = requet_params.split("&");
    List list = getTypeDicList("conf_id=" + conf_id + ";is_query=1");
    if (list != null)
    {
      for (int j = 0; j < list.size(); ++j)
      {
        String is_query = "item_" + ((QueryDicBean)list.get(j)).getDic_id();
        for (int i = 0; i < temp.length; ++i)
        {
          if (!(temp[i].toLowerCase().startsWith("item_")))
            continue;
          String[] tempB = temp[i].split("=");
          if (!(is_query.equals(tempB[0])))
            continue;
          String item = FormatUtil.formatNullString(temp[i].substring(temp[i].indexOf("=") + 1));
          if(item == "" || item == null || item == "null"){
        	  continue;
          }else{
              search = search + " and item_" + ((QueryDicBean)list.get(j)).getDic_id() + "='" + item.trim() + "'";
          }
        }
      }
    }

    String[] tempA = param.split(";");
    for (int i = 0; i < tempA.length; ++i)
    {
      if (tempA[i].toLowerCase().startsWith("size="))
      {
        String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
        if ((!("".equals(ps))) && (!(ps.startsWith("$size"))) && (FormatUtil.isNumeric(ps)))
          page_size = Integer.parseInt(ps);
      }
      if (!(tempA[i].toLowerCase().startsWith("cur_page=")))
        continue;
      String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
      if ((!("".equals(cp))) && (!(cp.startsWith("$cur_page"))) && (FormatUtil.isNumeric(cp))) {
        cur_page = Integer.parseInt(cp);
      }
    }
    	System.out.println(" getQuerySearchCon  ============" + table_view + search);
    return table_view + search;
  }

  public static List<QueryDicBean> getTypeDicList(String par)
  {
    String contion = "";
    String[] tempPar = par.split(";");
    for (int i = 0; i < tempPar.length; ++i)
    {
      if (tempPar[i].toLowerCase().startsWith("conf_id="))
      {
        String cid = FormatUtil.formatNullString(tempPar[i].substring(tempPar[i].indexOf("=") + 1));
        if (cid.toLowerCase().startsWith("conf_id="))
        {
          cid = cid.replace("conf_id=", "");
          contion = contion + " and conf_id =" + cid + " ";
        } else {
          contion = contion + " and conf_id =" + cid + " ";
        }
      }
      if (tempPar[i].toLowerCase().startsWith("is_query="))
      {
        String is_query = FormatUtil.formatNullString(tempPar[i].substring(tempPar[i].indexOf("=") + 1));
        if ((!("".equals(is_query))) && (!(is_query.startsWith("$is_query"))) && (FormatUtil.isValiditySQL(is_query)))
        {
          contion = contion + "and is_query='" + is_query + "' ";
        }
      }
      if (tempPar[i].toLowerCase().startsWith("is_result="))
      {
        String is_result = FormatUtil.formatNullString(tempPar[i].substring(tempPar[i].indexOf("=") + 1));
        if ((!("".equals(is_result))) && (!(is_result.startsWith("$is_result"))) && (FormatUtil.isValiditySQL(is_result)))
        {
          contion = contion + "and is_result='" + is_result + "' ";
        }
      }
      if (!(tempPar[i].toLowerCase().startsWith("is_selected=")))
        continue;
      String is_selected = FormatUtil.formatNullString(tempPar[i].substring(tempPar[i].indexOf("=") + 1));
      if (("".equals(is_selected)) || (is_selected.startsWith("$is_selected")) || (!(FormatUtil.isValiditySQL(is_selected))))
        continue;
      contion = contion + "and is_selected='" + is_selected + "' ";
    }
    return QueryDicManager.getTypeDicList(contion);
  }
  
  
  public static boolean isValiditySQL(String str)
  {
      String inj_str ="'|!|@|#|~|$|%|^|*|%|&|(|)|truncate|char|declare| or |+";//这里的东西还可以自己添加
      String[] inj_stra=inj_str.split("\\|");
      for (int i=0 ; i < inj_stra.length ; i++ )
      {
          if (str.indexOf(inj_stra[i])>=0)
          {
          	System.out.println("sql str is error --> "+str);
              return false;
          }
      }
      return true;
  }

  public static void main(String[] args)
  {
	  
    System.out.println(isValiditySQL("conf_id=81&item_4=&btnOK=确 定&item_3=&item_1=*"));
    
  }
}