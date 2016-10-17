package com.deya.wcm.services.cms.count;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.count.SiteInfoTuisongBean;
import com.deya.wcm.bean.cms.count.TuisongCountBean;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.dao.cms.count.TuisongCountDao;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.appeal.count.CountUtil;
import com.deya.wcm.services.browserapi.BrowserAPIService;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

public class TuisongCountManager
{
		/**
		 * 按栏目分类取得信推送信息统计情况
		 * 如果栏目是叶节点取得叶节点的统计信息,如果栏目是枝节点,
		 * 则取得节点下一级栏目的统计情况.
		 * @param mp	site_id和cat_id这两个参数不可或缺.
		 * @return	统计结果列表,如果为空返回空列表
		 */
		public static List<TuisongCountBean> getTuisongInfoCountByCat(Map<String, String> mp){
			int cat_id = Integer.parseInt(mp.get("cat_id"));
			String site_id = mp.get("site_id");
			
			// 判断cat_id对应的分类是否是枝节点
			boolean isMinorNode = CategoryManager.isHasChildNode(cat_id, site_id);
			List<TuisongCountBean> ret = new ArrayList<TuisongCountBean>();
			
			// 如果是叶节点直接取统计结果
			if(!isMinorNode){
				TuisongCountBean bean = TuisongCountDao.getCountListByCat(mp);
				bean.setCat_id(cat_id);
				bean.setCat_name(CategoryManager.getCategoryBean(cat_id).getCat_cname());
				ret.add(bean);	
			}else {
				List<CategoryBean> directSubNode;
				//cat_id为零时,表示取得站点下的所有内容管理目录
				if(cat_id == 0){
					directSubNode = CategoryManager.getCategoryListBySiteID(site_id,0);
				}else{
					directSubNode = CategoryManager.getChildCategoryList(cat_id,site_id);
				}
				for(CategoryBean bean : directSubNode) {
					mp.remove("cat_id");
					String ids = CategoryManager.getAllChildCategoryIDS(bean.getCat_id(),site_id);
					if(ids== null || "".equals(ids)){
						ids = bean.getCat_id() + "";
					}else {
						ids += "," + bean.getCat_id();
					}
					mp.put("cat_id",ids);
					TuisongCountBean retBean = TuisongCountDao.getCountListByCat(mp);
					retBean.setCat_id(bean.getCat_id());
					retBean.setCat_name(bean.getCat_cname());
					ret.add(retBean);
				}
			}
			return ret;
		}
		
		/**
		 * 统计导出Excel文件 -- 按栏目统计
		 * @param List listBean
		 * @param List headerList
		 * @return	String 文件路径名字
		 */
		public static String tuiSongInfoOutExcel(List listBean,List headerList){
			try{
				String[] fileStr = getFileUrl();
				//得到header
				final int size =  headerList.size();
				String[] head = (String[])headerList.toArray(new String[size]);
				//填充数据
				String[][] data = new String[listBean.size()][5];
				for(int i=0;i<listBean.size();i++)
				{
					TuisongCountBean countBean = (TuisongCountBean)listBean.get(i);
					
					data[i][0] = countBean.getCat_name();
					data[i][1] = String.valueOf(countBean.getIs_host());
					data[i][2] = String.valueOf(countBean.getIsNot_host());	 
				 }
				OutExcel oe=new OutExcel("站点栏目推送信息统计表");
				oe.doOut(fileStr[0],head,data);
				return fileStr[1];
			}catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		
		//删除今天以前的文件夹  并 创建今天的文件夹和xls文件
		public static String[] getFileUrl(){
			//删除今天以前的文件夹
			String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
			String path = FormatUtil.formatPath(root_path + "/cms/cmsCount/file/");  
			CountUtil.deleteFile(path);
			
			//创建今天的文件夹和xls文件
			String nowDate = CountUtil.getNowDayDate();
			String fileTemp2 = FormatUtil.formatPath(path+File.separator+nowDate);
			File file2 = new File(fileTemp2);
			if(!file2.exists()){
				file2.mkdir();
			}
			String nowTime = CountUtil.getNowDayDateTime();
			String xls = nowTime + CountUtil.getEnglish(1)+".xls";
			String xlsFile = fileTemp2+File.separator+xls;
			String urlFile = "/sys/cms/cmsCount/file/"+nowDate+File.separator+xls;
			String[] str = {xlsFile,urlFile};
			return str;
		}
		
		public static List<SiteInfoTuisongBean> getOneSiteTuisCounts(Map<String, String> mp)
		{
			
			//Map mapt = map;
			String start_day = (String)mp.get("start_day");
			if(start_day!=null && !"".equals(start_day)){
				if(!start_day.contains(":")){
					start_day =  start_day+" 00:00:01";
				}
				mp.put("start_time",start_day);
			}else{
				mp.put("start_time", "2000-01-01 00:00:01");
			}
			
			String end_day = (String)mp.get("end_day");
			if(end_day!=null && !"".equals(end_day)){
				if(!end_day.contains(":")){
					end_day =  start_day+" 23:59:59";
				}
				mp.put("end_time",end_day);
			}else{
				mp.put("end_time", DateUtil.getCurrentDateTime());
			}
			String atype = (String)mp.get("atype");
			if(atype==null){
				atype = "";
			}
			if("lastmonth".equals(atype)){//全部的统计
				//得到上个月的时间 开始时间
				mp.put("start_time",BrowserAPIService.getFirstDayByLastMonth());
				mp.put("end_time",BrowserAPIService.getLastDayByLastMonth());
			}else if("currmonth".equals(atype)){
				mp.put("start_time",BrowserAPIService.getFirstDayByCurrMonth());
				mp.put("end_time",BrowserAPIService.getLastDayByCurrMonth());
			}
			
			String site_id = mp.get("site_id");
			List<SiteInfoTuisongBean> stsList = new ArrayList<SiteInfoTuisongBean>();
			if(mp.get("app_id").equals("cms"))
			{
				List<SiteBean> sbl = SiteManager.getSiteChildListByID("HIWCMcgroup");
				for(int j=0;j<sbl.size();j++)
				{   
					SiteInfoTuisongBean sitb = new SiteInfoTuisongBean();
						System.out.println("site_id=="+sbl.get(j).getSite_id());
					int icount = 0;
					if(!site_id.equals(sbl.get(j).getSite_id()))
					{
						List<InfoBean> InfoList = DBManager.queryFList("info_count.getOneSiteInfoLists",mp);
						for(int i=0;i<InfoList.size();i++)
						{ 
							InfoBean mm = InfoBaseManager.getInfoById(InfoList.get(i).getFrom_id()+"");
							if(mm != null)
							{
								if(sbl.get(j).getSite_id().equals(mm.getSite_id()))
								{
									//System.out.println("sbl.get(j).getSite_id()=="+sbl.get(j).getSite_id()+"==mm.getSite_id()=="+mm.getSite_id());
									icount ++;
								}
							}
							sitb.setSite_id(sbl.get(j).getSite_id());
							sitb.setSite_name(sbl.get(j).getSite_name());
						}
						sitb.setIcount(icount);	
				   }
				   stsList.add(sitb);
				}
			}else{
				//统计公开节点推送信息
					System.out.println(" 	run in tongji gongkai ");
				List<GKNodeBean> gkl =  GKNodeManager.getAllGKNodeList();
				for(int j=0;j<gkl.size();j++)
				{   
					    int icount1 = 0;
					    SiteInfoTuisongBean sitb = new SiteInfoTuisongBean();
						List<InfoBean> InfoList = DBManager.queryFList("info_count.getOneSiteInfoLists",mp);
						for(int i=0;i<InfoList.size();i++)
						{ 
							InfoBean infobean = InfoBaseManager.getInfoById(InfoList.get(i).getFrom_id()+"");
							if(infobean != null)
							{
								System.out.println("node_id==="+gkl.get(j).getNode_id());
								if(infobean.getSite_id().equals(gkl.get(j).getNode_id()))
								{
									icount1 ++;
								}
							}
							sitb.setSite_id(gkl.get(j).getNode_id());
							sitb.setSite_name(gkl.get(j).getNode_name());
							sitb.setIcount(icount1);
						}
						stsList.add(sitb);
				}
			}
			if(stsList != null && stsList.size() > 0)
				Collections.sort(stsList,new countComparator());
			return stsList;
		}
		
		/***	排序		****/
		static class countComparator implements Comparator<Object>{
			public int compare(Object o1, Object o2) {
			    
				SiteInfoTuisongBean cgb1 = (SiteInfoTuisongBean) o1;
				SiteInfoTuisongBean cgb2 = (SiteInfoTuisongBean) o2;
			    if (cgb1.getIcount() < cgb2.getIcount()) {
			     return 1;
			    } else {
			     if (cgb1.getIcount() == cgb2.getIcount()) {
			      return 0;
			     } else {
			      return -1;
			     }
			    }
			}
		}
		
		public static void main(String[] args)
		{
			Map<String, String> mp = new HashMap<String, String>();
			
			mp.put("site_id","HIWCMdemo");
			mp.put("app_id", "zwgk");
			
			mp.put("start_time", "2010-01-01");
			mp.put("end_time", "2012-12-12 12:59:59");
			
			List<SiteInfoTuisongBean> l = getOneSiteTuisCounts(mp);
			for(int n=0;n<l.size();n++)
			{
				System.out.println(l.get(n).getSite_name()+"=====count=="+l.get(n).getIcount());
			}
		}
}