<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager,com.deya.wcm.bean.cms.info.*,com.deya.wcm.bean.cms.category.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.cms.info.InfoBaseManager"%>
<%@page import="com.deya.wcm.services.cms.category.CateClassManager"%>
<%@page import="com.deya.wcm.services.cms.category.CategoryManager"%>
<%!
public class InfoBeanComparator  implements Comparator {
	
	public int compare(Object o1, Object o2) {    
    	
		InfoBean c1 = (InfoBean) o1;    
		InfoBean c2 = (InfoBean) o2;  
    	
    		//if (c2.getReleased_dtime() > c1.getReleased_dtime()) {    
    		if (c2.getReleased_dtime().compareTo(c1.getReleased_dtime())>0) {  
                return 1;    
            } else {    
                if (c2.getReleased_dtime().equals(c1.getReleased_dtime())) {    
                    return 0;    
                } else {     
                    return -1;    
                }    
            } 
           
    }   
}
%>

<%
   
    //是得到感兴趣的信息还是栏目标示  值： 1只取信息  2只取栏目    不写该参数同时得到信息和栏目
	String it = (String)request.getParameter("it");
	if(it==null || "".equals(it)){
		it = "";
	}
    
    //取感兴趣信息的条数
    String ps = (String)request.getParameter("ps");
    if(ps==null || "".equals(ps)){
    	ps = "5";
    }
    
    //取感兴趣栏目的条数
    String pc = (String)request.getParameter("pc");
    if(pc==null || "".equals(pc)){
    	pc = "5";
    }

	Set<String> infoListSet = new HashSet<String>();

    //得到cookies中的信息ID
    String custom_info_ids = java.net.URLDecoder.decode(com.deya.util.CookieUtil.getCookieValue(request,"custom_info_ids"),"utf-8");
    //System.out.println("custom_info_ids------" + custom_info_ids);
    String[] ids = custom_info_ids.split(",");
    for(String id : ids ){
    	if(id!=null && !"".equals(id)){//通过信息id得到相关信息
    		List<RelatedInfoBean> relatedInfoList = com.deya.wcm.template.velocity.data.InfoUtilData.getRelatedInfoList(id);
    		for(RelatedInfoBean relatedInfoBean : relatedInfoList){
    			infoListSet.add(relatedInfoBean.getInfo_id()+"");
    		}
    	}
    }
    
    List<InfoBean> infoBeans = new ArrayList<InfoBean>();
    //通过infoList得到信息对象
    for(String info_id : infoListSet){
    	InfoBean infoBean = InfoBaseManager.getInfoById(info_id+"");
    	infoBeans.add(infoBean);
    }
    
    
    //得到感兴趣的信息
    List<InfoBean> infoBeansResult = new ArrayList<InfoBean>();
    if(it.equals("") || it.equals("1")){
    	//按发布时间倒叙排序
        //按数据量排序 -- 降序排序
    	InfoBeanComparator infoBeanComparator = new InfoBeanComparator();
    	Collections.sort(infoBeans, infoBeanComparator);
    	
		if(infoBeans.size()==0){//没有感兴趣的栏目 取全站最新的信息	
    		String site_id = com.deya.wcm.services.control.domain.SiteDomainManager.getSiteIDByDomain(request.getLocalName());
    		infoBeans = com.deya.wcm.template.velocity.data.InfoUtilData.getInfoList("site_id=$site_id;cur_page=1;size=50;orderby=ci.released_dtime desc");
    	} 
    	
    	int maxint = infoBeans.size()<Integer.parseInt(ps)?infoBeans.size():Integer.parseInt(ps);
    	for(int i=0;i<maxint;i++){
    		infoBeansResult.add(infoBeans.get(i));
    	}
    	
    }
      
    //得到感兴趣的栏目
    List<CategoryBean> categoryBeansResult = new ArrayList<CategoryBean>();
    if(it.equals("") || it.equals("2")){
    	//通过信息id得到信息栏目
    	Set<String> cateListSet = new HashSet<String>();
    	for(InfoBean infoBean : infoBeans){
    		String cate_id = infoBean.getCat_id()+"";
    		cateListSet.add(cate_id);
    	}
    	
    	//通过栏目id得到栏目对象
    	List<CategoryBean> categoryBeansTemp = new ArrayList<CategoryBean>();
    	for(String cate_id : cateListSet){
    		CategoryBean categoryBean = CategoryManager.getCategoryBean(Integer.parseInt(cate_id));
        	categoryBeansTemp.add(categoryBean);
        }
    	//按传回的页面条数 得到栏目列表
    	int maxint = categoryBeansTemp.size()<Integer.parseInt(pc)?categoryBeansTemp.size():Integer.parseInt(pc);
    	for(int i=0;i<maxint;i++){
    		categoryBeansResult.add(categoryBeansTemp.get(i));
    	}
    }
    
    
	String t_id = request.getParameter("t_id");
	if(t_id==null || t_id=="")
	{
		out.println("--------");
		return;
	}
	
	VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
	List<Map> result = new ArrayList<Map>();
	vc.vcontextPut("infoBeans",infoBeansResult);
	vc.vcontextPut("cateBeans",categoryBeansResult);
	//vc.setModelID("",t_id);
	vc.setTemplate_id(t_id); 
	out.println(vc.parseTemplate());
%>