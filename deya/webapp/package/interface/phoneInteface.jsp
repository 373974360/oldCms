<%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java" import="com.deya.util.FormatUtil,com.deya.wcm.bean.appeal.sq.SQBean,com.deya.wcm.bean.cms.category.CategoryBean,com.deya.wcm.bean.cms.info.*,com.deya.wcm.bean.interview.SubjectActor" %>
<%@page import="com.deya.wcm.bean.interview.SubjectBean,com.deya.wcm.bean.search.ResultBean,com.deya.wcm.bean.system.formodel.ModelBean,com.deya.wcm.bean.template.TurnPageBean"%>
<%@page import="com.deya.wcm.services.appeal.sq.SQManager,com.deya.wcm.services.cms.category.CategoryManager,com.deya.wcm.services.cms.info.InfoBaseManager,com.deya.wcm.services.cms.info.ModelUtil"%>
<%@page import="com.deya.wcm.services.model.services.InfoCustomService,com.deya.wcm.services.search.search.SearchManager,com.deya.wcm.services.system.formodel.ModelManager,com.deya.wcm.services.zwgk.info.GKInfoManager,com.deya.wcm.template.velocity.data.AppealData,com.deya.wcm.template.velocity.data.InfoUtilData"%><%@ page import="com.deya.wcm.template.velocity.data.InterViewData"%><%@ page import="org.json.JSONException"%><%@ page import="org.json.JSONObject"%><%@ page import="java.util.*"%>
<%
    String action_type = request.getParameter("action_type");
    String result = "";
    if("news_list".equals(action_type))
    {
        result = getNewsList(request);
    }
    if("news_hits_list".equals(action_type))
    {
        result = getHitsNewsList(request);
    }
    if("cat_count_list".equals(action_type))
    {
        result = getChildCategoryCountList(request);
    }
    if("news_count".equals(action_type))
    {
        result = getNewsListCount(request);
    }
    if("newsPic_count".equals(action_type))
    {
        result = getNewsPicCount(request);
    }
    if("newsPic_list".equals(action_type))
    {
        result = getNewsPicList(request);
    }
    if("cmsLd_list".equals(action_type))
    {
        result = getCmsLdInfoList(request);
    }
    if("news_content".equals(action_type))
    {
        result = getNewsContent(request);
    }
    if("gkShared_list".equals(action_type))
    {
        result = getGkSharedInfoList(request);
    }
    if("gkShared_count".equals(action_type))
    {
        result = getGkSharedInfoCount(request);
    }
    if("gk_list".equals(action_type))
    {
        result = getGkInfoList(request);
    }
    if("ld_list".equals(action_type))
    {
        result = getLdInfoList(request);
    }
    if("gk_count".equals(action_type))
    {
        result = getGkInfoCount(request);
    }
    if("fw_list".equals(action_type))
    {
        result = getFWInfoList(request);
    }
    if("sq_list".equals(action_type))
    {
        result = getSQInfoList(request);
    }
    if("sq_content".equals(action_type))
    {
        result = getSQContent(request);
    }
    if("ft_list".equals(action_type))
    {
        result = getFTInfoList(request);
    }
    if("cat_list".equals(action_type))
    {
        result = getChildCategoryList(request);
    }
    if("custom_info".equals(action_type))
    {
        result = getCustomInfoMap(request);
    }
    if("isToudi".equals(action_type))
    {
        result = isToudi(request);
    }
    if("searchInfo".equals(action_type))
    {
        result = searchInfo(request);
    }

    out.println(result);
%>
<%!

public String getNewsList(HttpServletRequest request)
{
	String json = "";
	String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String title = FormatUtil.formatNullString(request.getParameter("title"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "site_id=" + site_id + ";cat_id="+cat_id+";size="+size+";cur_page="+page+";title="+title+";orderby=ci.released_dtime desc;";
	List<InfoBean> info_list = InfoUtilData.getInfoList(params);
	if(info_list != null && info_list.size() > 0)
	{
		for(InfoBean info : info_list)
		{
			json += ",{\"info_id\":\""+info.getInfo_id()+"\",\"title\":\""+replaceFont(info.getTitle())+"\",\"model_id\":\""+info.getModel_id()+"\",\"content_url\":\""+info.getContent_url()+"\",\"description\":\""+replaceStr(info.getDescription())+"\",\"source\":\""+info.getSource()+"\",\"released_dtime\":\""+info.getReleased_dtime()+"\"}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getHitsNewsList(HttpServletRequest request)
{
	String json = "";
	String params = "site_id=CMScqgjj;size=10;cur_page=1;orderby=ci.hits desc;";
	List<InfoBean> info_list = InfoUtilData.getInfoList(params);
	if(info_list != null && info_list.size() > 0)
	{
		for(InfoBean info : info_list)
		{
			json += ",{\"newstital\":\""+replaceFont(info.getTitle())+"\",\"newsCount\":\""+info.getHits()+"\"}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}
public String getChildCategoryCountList(HttpServletRequest request){
	String json = "";
	String site_id = "CMScqgjj";
	String cat_id = "0";
	List<CategoryBean> info_list = CategoryManager.getChildCategoryList(Integer.parseInt(cat_id),site_id);
	if(info_list != null && info_list.size() > 0)
	{
		for(int i = 0; i < info_list.size(); i++){
			CategoryBean bean = info_list.get(i);
            String params = "site_id=" + site_id + ";cat_id="+bean.getCat_id()+";size=10;cur_page=1;orderby=ci.released_dtime desc;";
            TurnPageBean tpb = InfoUtilData.getInfoCount(params);
            json += ",{\"columntital\":\""+bean.getCat_cname()+"\",\"columnCount\":\""+tpb.getCount()+"\"";
            List<CategoryBean> children_list = CategoryManager.getChildCategoryList(bean.getCat_id(),site_id);
            if(!children_list.isEmpty()){
                json += ",\"children\":[";
                String cjson = "";
                for(CategoryBean cbean:children_list){
                    String cparams = "site_id=" + site_id + ";cat_id="+cbean.getCat_id()+";size=10;cur_page=1;orderby=ci.released_dtime desc;";
                    TurnPageBean ctpb = InfoUtilData.getInfoCount(cparams);
                    cjson += ",{\"columntital\":\""+cbean.getCat_cname()+"\",\"columnCount\":\""+ctpb.getCount()+"\"}";
                }
                cjson = cjson.substring(1);
                json += cjson;
                json +=	""+"]";
            }
            json += "}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getCmsLdInfoList(HttpServletRequest request)
{
    String json = "";
	String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String title = FormatUtil.formatNullString(request.getParameter("title"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "site_id=" + site_id + ";cat_id="+cat_id+";size="+size+";cur_page="+page+";title="+title+";orderby=ci.released_dtime desc;";
	List<InfoBean> info_list = InfoUtilData.getInfoList(params);
	if(info_list != null && info_list.size() > 0)
	{
	    for(InfoBean info : info_list)
		{
		    GKFldcyBean ldcy = (GKFldcyBean)ModelUtil.select(String.valueOf(info.getInfo_id()), info.getSite_id(), ModelManager.getModelEName(info.getModel_id()));
			json += ",{\"info_id\":\""+ldcy.getInfo_id()+"\",\"title\":\""+replaceFont(ldcy.getTitle())+"\",\"model_id\":\""+info.getModel_id()+"\",\"content_url\":\""+info.getContent_url()+"\",\"description\":\""+replaceStr(info.getDescription())+"\",\"source\":\""+ldcy.getSource()+"\",\"pic\":\""+ldcy.getGk_pic()+"\",\"ldzw\":\""+ldcy.getGk_ldzw()+"\",\"gzfg\":\""+ldcy.getGk_gzfg()+"\",\"released_dtime\":\""+info.getReleased_dtime()+"\"}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getNewsListCount(HttpServletRequest request)
{
	String json = "";
	String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "site_id=" + site_id + ";cat_id="+cat_id+";size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
	TurnPageBean tpb = InfoUtilData.getInfoCount(params);
	if(tpb != null)
	{
		json += "{\"count\":\"" + tpb.getCount() + "\"}";
	}
	return json;
}

public String getNewsPicCount(HttpServletRequest request)
{
	String json = "";
	String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "site_id=" + site_id + ";cat_id="+cat_id+";is_show_thumb=true;size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
	TurnPageBean tpb = InfoUtilData.getInfoCount(params);
	if(tpb != null)
	{
		json += "{\"count\":\"" + tpb.getCount() + "\"}";
	}
	return json;
}

public String getNewsPicList(HttpServletRequest request)
{
	String json = "";
	String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "site_id=" + site_id + ";cat_id="+cat_id+";is_show_thumb=true;size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
	List<InfoBean> info_list = InfoUtilData.getInfoList(params);
	if(info_list != null && info_list.size() > 0)
	{
		for(InfoBean info : info_list)
		{
			json += ",{\"info_id\":\""+info.getInfo_id()+"\",\"title\":\""+replaceFont(info.getTitle())+"\",\"model_id\":\""+info.getModel_id()+"\",\"content_url\":\""+info.getContent_url()+"\",\"description\":\""+replaceStr(info.getDescription())+"\",\"source\":\""+info.getSource()+"\",\"thumb_url\":\""+info.getThumb_url()+"\",\"released_dtime\":\""+info.getReleased_dtime()+"\"}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getNewsContent(HttpServletRequest request)
{
	String json = "";
	String id = FormatUtil.formatNullString(request.getParameter("id"));
	InfoBean bean = null;
	if(id != null && !"".equals(id))
	{
		bean = InfoBaseManager.getInfoById(id);
	}
	if(bean != null){
		ModelBean mb = ModelManager.getModelBean(bean.getModel_id());
		Object obj = ModelUtil.select(bean.getInfo_id()+"",bean.getSite_id(),mb.getModel_ename());
		if(obj != null)
		{
			if(mb.getModel_id() == 11)
			{
				ArticleBean article = (ArticleBean)obj;
				json = "{\"type\":\"article\",\"info_id\":\""+article.getInfo_id()+"\",\"title\":\""+replaceFont(article.getTitle())+"\",\"source\":\""+article.getSource()+"\",\"author\":\""+article.getAuthor()+"\",\"hits\":\""+article.getHits()+"\",\"released_dtime\":\""+article.getReleased_dtime()+"\",\"description\":\""+replaceStr(article.getDescription())+"\",\"content\":\""+replaceStr(article.getInfo_content())+"\"}";
			}
			else if(mb.getModel_id() == 10)
			{
				PicBean article = (PicBean)obj;
				List<PicItemBean> picList = article.getItem_list();
				json = "{\"type\":\"pic\",\"info_id\":\""+article.getInfo_id()+"\",\"title\":\""+replaceFont(article.getTitle())+"\",\"source\":\""+article.getSource()+"\",\"author\":\""+article.getAuthor()+"\",\"hits\":\""+article.getHits()+"\",\"released_dtime\":\""+article.getReleased_dtime()+"\",\"description\":\""+replaceStr(article.getDescription())+"\",\"picList\":[";
				for(PicItemBean pib : picList){
					json += "{\"pic_path\":\"" + pib.getPic_path()+"\",\"pic_title\":\"" + replaceFont(pib.getPic_title())+"\",\"pic_note\":\"" + replaceStr(pib.getPic_note())+"\"},";
				}
				json = json.substring(0,json.length() - 1);
				json += "],\"content\":\""+replaceStr(article.getPic_content())+"\"}";
			}
			else if(mb.getModel_id() == 13)
			{
				VideoBean article = (VideoBean)obj;
				json = "{\"type\":\"video\",\"info_id\":\""+article.getInfo_id()+"\",\"title\":\""+replaceFont(article.getTitle())+"\",\"source\":\""+article.getSource()+"\",\"author\":\""+article.getAuthor()+"\",\"hits\":\""+article.getHits()+"\",\"released_dtime\":\""+article.getReleased_dtime()+"\",\"description\":\""+replaceStr(article.getDescription())+"\",\"video_path\":\""+article.getVideo_path()+"\",\"content\":\""+replaceStr(article.getInfo_content())+"\"}";
			}
			else if(mb.getModel_id() == 14)
			{
				GKFtygsBean article = (GKFtygsBean)obj;
				json = "{\"type\":\"tygs\",\"info_id\":\""+article.getInfo_id()+"\",\"title\":\""+replaceFont(article.getTitle())+"\",\"source\":\""+article.getSource()+"\",\"author\":\""+article.getAuthor()+"\",\"hits\":\""+article.getHits()+"\",\"released_dtime\":\""+article.getReleased_dtime()+"\",\"description\":\""+replaceStr(article.getDescription())+"\",\"content\":\""+replaceStr(article.getGk_content())+"\"}";
			}
			else if(mb.getModel_id() == 20)
			{
				GKFbsznBean article = (GKFbsznBean)obj;
				json = "{\"type\":\"b\",\"info_id\":\""+article.getInfo_id()+"\",\"title\":\""+replaceFont(article.getTitle())+"\",\"source\":\""+article.getSource()+"\",\"author\":\""+article.getAuthor()+"\",\"hits\":\""+article.getHits()+"\",\"released_dtime\":\""+article.getReleased_dtime()+"\"";
				json +=",\"gk_bsjg\":\""+replaceStr(article.getGk_bsjg())+"\"";							//受理机构
				json +=",\"gk_sxyj\":\""+replaceStr(article.getGk_sxyj())+"\"";							//事项依据
				json +=",\"gk_bldx\":\""+replaceStr(article.getGk_bldx())+"\"";							//服务对象
				json +=",\"gk_bltj\":\""+replaceStr(article.getGk_bltj())+"\"";							//办理条件
				json +=",\"gk_blsx\":\""+replaceStr(article.getGk_blsx())+"\"";							//办理材料
				json +=",\"gk_bllc\":\""+replaceStr(article.getGk_bllc())+"\"";							//办理流程
				json +=",\"gk_blshixian\":\""+replaceStr(article.getGk_blshixian())+"\"";					//办理时限
				json +=",\"gk_sfbz\":\""+replaceStr(article.getGk_sfbz())+"\"";							//收费标准
				json +=",\"gk_sfyj\":\""+replaceStr(article.getGk_sfyj())+"\"";							//收费依据
				json +=",\"gk_zxqd\":\""+replaceStr(article.getGk_zxqd())+"\"";							//咨询电话
				json +=",\"gk_bgsj\":\""+replaceStr(article.getGk_bgsj())+"\"";							//办公时间及地址
				json +=",\"gk_cclx\":\""+replaceStr(article.getGk_cclx())+"\"";							//乘车路线
				json +=",\"gk_wszx\":\"http://www.weinan.gov.cn/"+replaceStr(article.getGk_wszx())+"\"";	//在线咨询
				List<GKResFileBean> files = GKInfoManager.getGKResFileList(article.getInfo_id() + "");
				json +=",\"gk_bgxz\":[";
				int i = 0;
				for(GKResFileBean gf : files){
					json += "{\"res_url\":\"" + gf.getRes_url()+"\",\"res_name\":\"" + gf.getRes_name()+"\"},";
					i = 1;
				}
				if(i != 0)
				{
					json = json.substring(0,json.length() - 1);
				}
				json += "]}";
			}else{
			    Map custom = (Map)ModelUtil.select(id, "", "article_custom");
			    Set set = custom.keySet();
			    json = "{\"type\":\"b\",\"info_id\":\""+bean.getInfo_id()+"\",\"title\":\""+replaceFont(bean.getTitle())+"\",\"source\":\""+bean.getSource()+"\",\"author\":\""+bean.getAuthor()+"\",\"hits\":\""+bean.getHits()+"\",\"released_dtime\":\""+bean.getReleased_dtime()+"\"";
			    for (Object key : set) {
			        Object value = custom.get(key);
                    json += ",\"" + key.toString() + "\":\""+replaceStr(value.toString())+"\"";
			    }
			    json += "}";
			}
		}
	}
	return "["+json+"]";
}

public String getGkSharedInfoList(HttpServletRequest request)
{
	String json = "";
	String node_id = FormatUtil.formatNullString(request.getParameter("node_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String gk_index = FormatUtil.formatNullString(request.getParameter("gk_index"));
	String title = FormatUtil.formatNullString(request.getParameter("title"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "node_id=" + node_id + ";catalog_id="+cat_id+";gk_index="+gk_index+";title="+title+";size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
	List<GKInfoBean> info_list = InfoUtilData.getGKInfoList(params);
	if(info_list != null && info_list.size() > 0)
	{
		for(GKInfoBean info : info_list)
		{
			json += ",{\"info_id\":\""+info.getInfo_id()+"\",\"title\":\""+replaceFont(info.getTitle())+"\",\"source\":\""+info.getSource()+"\",\"released_dtime\":\""+info.getReleased_dtime()+"\"}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getGkSharedInfoCount(HttpServletRequest request)
{
	String json = "";
	String node_id = FormatUtil.formatNullString(request.getParameter("node_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String gk_index = FormatUtil.formatNullString(request.getParameter("gk_index"));
	String title = FormatUtil.formatNullString(request.getParameter("title"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "node_id=" + node_id + ";catalog_id="+cat_id+";gk_index="+gk_index+";title="+title+";size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
	TurnPageBean tpb = InfoUtilData.getGKInfoCount(params);
	if(tpb != null)
	{
		json += "{\"count\":\"" + tpb.getCount() + "\"}";
	}
	return json;
}

public String getGkInfoList(HttpServletRequest request)
{
	String json = "";
	String node_id = FormatUtil.formatNullString(request.getParameter("node_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String gk_index = FormatUtil.formatNullString(request.getParameter("gk_index"));
	String title = FormatUtil.formatNullString(request.getParameter("title"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "node_id=" + node_id + ";cat_id="+cat_id+";size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
	List<GKInfoBean> info_list = InfoUtilData.getGKInfoList(params);
	if(info_list != null && info_list.size() > 0)
	{
		for(GKInfoBean info : info_list)
		{
			json += ",{\"info_id\":\""+info.getInfo_id()+"\",\"title\":\""+replaceFont(info.getTitle())+"\",\"source\":\""+info.getSource()+"\",\"released_dtime\":\""+info.getReleased_dtime()+"\"}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getLdInfoList(HttpServletRequest request)
{
	String json = "";
	String node_id = FormatUtil.formatNullString(request.getParameter("node_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String gk_index = FormatUtil.formatNullString(request.getParameter("gk_index"));
	String title = FormatUtil.formatNullString(request.getParameter("title"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "node_id=" + node_id + ";cat_id="+cat_id+";size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
	List<GKInfoBean> info_list = InfoUtilData.getGKInfoList(params);
	if(info_list != null && info_list.size() > 0)
	{
		for(GKInfoBean info : info_list)
		{
		    GKFldcyBean ldcy = (GKFldcyBean)ModelUtil.select(String.valueOf(info.getInfo_id()), info.getSite_id(), ModelManager.getModelEName(info.getModel_id()));
			json += ",{\"info_id\":\""+ldcy.getInfo_id()+"\",\"title\":\""+replaceFont(ldcy.getTitle())+"\",\"source\":\""+ldcy.getSource()+"\",\"pic\":\""+ldcy.getGk_pic()+"\",\"ldzw\":\""+ldcy.getGk_ldzw()+"\",\"gzfg\":\""+ldcy.getGk_gzfg()+"\",\"released_dtime\":\""+info.getReleased_dtime()+"\"}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getGkInfoCount(HttpServletRequest request)
{
	String json = "";
	String node_id = FormatUtil.formatNullString(request.getParameter("node_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String gk_index = FormatUtil.formatNullString(request.getParameter("gk_index"));
	String title = FormatUtil.formatNullString(request.getParameter("title"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "node_id=" + node_id + ";cat_id="+cat_id+";size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
	TurnPageBean tpb = InfoUtilData.getGKInfoCount(params);
	if(tpb != null)
	{
		json += "{\"count\":\"" + tpb.getCount() + "\"}";
	}
	return json;
}

public String getFWInfoList(HttpServletRequest request)
{
	String json = "";
	String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "site_id=" + site_id + ";cat_id="+cat_id+";size="+size+";cur_page="+page+";orderby=ci.released_dtime desc;";
	List<InfoBean> info_list = InfoUtilData.getFWInfoList(params);
	if(info_list != null && info_list.size() > 0)
	{
		for(InfoBean info : info_list)
		{
			json += ",{\"info_id\":\""+info.getInfo_id()+"\",\"title\":\""+replaceFont(info.getTitle())+"\",\"source\":\""+info.getSource()+"\",\"released_dtime\":\""+info.getReleased_dtime()+"\"}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getSQInfoList(HttpServletRequest request)
{
	String json = "";
	String model_id = FormatUtil.formatNullString(request.getParameter("model_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String dept_id = FormatUtil.formatNullString(request.getParameter("dept_id"));
	String sq_title = FormatUtil.formatNullString(request.getParameter("sq_title"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "model_id=" + model_id + ";is_open=1;publish_status=1;sq_status=3;cat_id="+cat_id+";dept_id="+dept_id+";sq_title="+sq_title+";size="+size+";cur_page="+page+";orderby=over_dtime desc;";
	List<SQBean> info_list = AppealData.getAppealList(params);
	if(info_list != null && info_list.size() > 0)
	{
		for(SQBean info : info_list)
		{
			json += ",{\"sq_id\":\""+info.getSq_id()+"\",\"sq_title\":\""+replaceFont(info.getSq_title2())+"\",\"add_dtime\":\""+info.getSq_dtime()+"\",\"over_time\":\""+info.getOver_dtime()+"\"}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getSQContent(HttpServletRequest request)
{
	String json = "";
	String id = FormatUtil.formatNullString(request.getParameter("id"));
	SQBean bean = null;
	if(id != null && !"".equals(id))
	{
		bean = SQManager.getSqBean(Integer.parseInt(id));
		json += "{\"sq_id\":\""+bean.getSq_id()+"\",\"sq_name\":\""+bean.getSq_realname()+"\",\"sq_title\":\""+bean.getSq_title2()+"\",\"add_dtime\":\""+bean.getSq_dtime()+"\",\"dept_name\":\""+bean.getDo_dept_name()+"\",\"over_time\":\""+bean.getOver_dtime()+"\",\"sq_content\":\""+replaceStr(bean.getSq_content2())+"\",\"sq_reply\":\""+replaceStr(bean.getSq_reply())+"\"}";
	}
	return "["+json+"]";
}

public String getFTInfoList(HttpServletRequest request)
{
	String json = "";
	String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String params = "site_id=" + site_id + ";size="+size+";cur_page="+page+";orderby=sub.start_time desc;";
	List<SubjectBean> info_list = InterViewData.getInterViewList(params);
	if(info_list != null && info_list.size() > 0)
	{
		for(SubjectBean info : info_list)
		{
			json += ",{\"sub_id\":\""+info.getSub_id()+"\",\"sub_name\":\""+info.getSub_name()+"\",\"sub_pic\":\""+info.getS_for_pic()+"\",\"actor_name\":\""+info.getActor_name()+"\",\"start_time\":\""+info.getStart_time()+"\",\"actor_info\":[";
			List<SubjectActor> saList = InterViewData.getInterViewActorList(info.getSub_id());
			int i = 0;
			for(SubjectActor sj : saList)
			{
				json += "{\"actor_name\":\""+sj.getActor_name()+"\",\"a_post\":\""+sj.getA_post()+"\",\"description\":\""+sj.getDescription()+"\",\"pic_path\":\""+sj.getPic_path()+"\"},";
				i = 1;
			}
			if(i != 0)
			{
				json = json.substring(0,json.length() - 1);
			}
			json += "]}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getChildCategoryList(HttpServletRequest request){
	String json = "";
	String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));
	String cat_id = FormatUtil.formatNullString(request.getParameter("cat_id"));
	String size = FormatUtil.formatNullString(request.getParameter("size"));
	String page = FormatUtil.formatNullString(request.getParameter("page"));
	List<CategoryBean> info_list = CategoryManager.getChildCategoryList(Integer.parseInt(cat_id),site_id);
	if(info_list != null && info_list.size() > 0)
	{
		int index = 0;
		int pageInt = 1;
		int sizeInt = 5;
		int i=0;
		if(page != null && !"".equals(page)){
			pageInt = Integer.parseInt(page);
		}
		if(size != null && !"".equals(size)){
			sizeInt = Integer.parseInt(size);
		}
		//if(page != null && !"".equals(page)){
		//	i = ( - 1 ) * Integer.parseInt(size);
		//}
		for(i = (pageInt - 1) * sizeInt; i <= ((pageInt - 1) * sizeInt + sizeInt) && i < info_list.size(); i++){
			CategoryBean info = info_list.get(i);
			json += ",{\"cat_id\":\""+info.getCat_id()+"\",\"cat_cname\":\""+info.getCat_cname()+"\"";
			json += "}";
		}
		json = json.substring(1);
	}
	return "["+json+"]";
}

public String getCustomInfoMap(HttpServletRequest request){
	String json = "";
	String model_id = FormatUtil.formatNullString(request.getParameter("model_id"));
	String info_id = FormatUtil.formatNullString(request.getParameter("info_id"));
	Map customInfoMap = InfoCustomService.getCustomInfoMap(model_id,info_id);
	JSONObject jsonObject = new JSONObject();
    Set set = customInfoMap.keySet();
    Iterator iterator = set.iterator();
    while (iterator.hasNext()){
        Object next = iterator.next();
        try {
            jsonObject.put((String) next,customInfoMap.get(next));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
	return jsonObject.toString();
}

public String searchInfo(HttpServletRequest request){
	com.deya.wcm.bean.search.SearchResult result = SearchManager.searchGJ(request);
	JSONObject jsonObject = new JSONObject();
    try {
        List<ResultBean> items = result.getItems();
        if(items != null && items.size() > 0){
            List<ResultBean> resultBeanList = new ArrayList<>();
            for (ResultBean item : items) {
                item.setTitle(replaceFont(item.getTitle()));
                resultBeanList.add(item);
            }
            jsonObject.put("maxPage",result.getPageControl().getMaxPage());
            jsonObject.put("maxRowCount",result.getPageControl().getMaxRowCount());
            jsonObject.put("rowsPerPage",result.getPageControl().getRowsPerPage());
            jsonObject.put("items",resultBeanList);
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
	return jsonObject.toString();
}


public String isToudi(HttpServletRequest request){
	String json = "";
	String info_id = FormatUtil.formatNullString(request.getParameter("info_id"));
	//boolean isToudi = com.deya.project.dz_recruit.UserInfoRPC.isToudi(Integer.parseInt(info_id),request);
	JSONObject jsonObject = new JSONObject();
    //jsonObject.put("isToudi",isToudi);
	return jsonObject.toString();
}



public static String replaceStr(String str)
{
	//return str.replaceAll("\"","'").replaceAll("\r|\n|\r\n","").replaceAll("<p.*?[^>]>|</p.*?[^>]>", "<p>");
	return str.replaceAll("\"","'").replaceAll("\r|\n|\r\n","").replaceAll("<p\\+.*?[^>]>|</p\\+.*?[^>]>", "<p>");
}

//替换标题里面的font标签
public static String replaceFont(String str){
    return str.replaceAll("<font([^>]*)>", "").replaceAll("</font([^>]*)>", "");
}

%>

