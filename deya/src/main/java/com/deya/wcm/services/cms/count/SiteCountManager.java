package com.deya.wcm.services.cms.count;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.ZTCategoryBean;
import com.deya.wcm.bean.cms.count.SiteCountBean;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.dao.cms.count.SiteCountDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.cms.category.ZTCategoryManager;
import com.deya.wcm.services.control.site.SiteManager;
import com.deya.wcm.services.org.dept.DeptManager;

public class SiteCountManager {
	
	
	//得到人员信息统计
	public static List<SiteCountBean> getSiteCountListByInputUser(Map mp){
		try{
            //得到--全部信息  
			List<SiteCountBean> listAll = SiteCountDAO.getSiteCountListByInput(mp);
			//得到--主信息
			mp.put("is_host","0");
			List<SiteCountBean> listZ = SiteCountDAO.getSiteCountListByInput(mp);
			//得到--引用信息
			mp.put("is_host","1");
			List<SiteCountBean> listY = SiteCountDAO.getSiteCountListByInput(mp);
			//得到--链接信息
			mp.put("is_host","2");
			List<SiteCountBean> listL = SiteCountDAO.getSiteCountListByInput(mp);
			//得到--已发布信息
			mp.put("info_status","8");
			mp.remove("is_host");
			List<SiteCountBean> listP = SiteCountDAO.getSiteCountListByInput(mp);
			
			for(SiteCountBean siteCountBean : listAll){
				int input_user = siteCountBean.getInput_user();
				siteCountBean.setAllCount(siteCountBean.getIcount());
				siteCountBean.setInputCount(siteCountBean.getIcount());
				
				//主信息
				if(listZ!=null && listZ.size()>0){
					for(SiteCountBean siteCountBeanZ : listZ){
						if(input_user==siteCountBeanZ.getInput_user()){
							siteCountBean.setHostInfoCount(siteCountBeanZ.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setHostInfoCount(0);
				}
				
				//引用信息
				if(listY!=null && listY.size()>0){
					for(SiteCountBean siteCountBeanY : listY){
						if(input_user==siteCountBeanY.getInput_user()){
							siteCountBean.setRefInfoCount(siteCountBeanY.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setRefInfoCount(0);
				}
				
				//链接信息
				if(listL!=null && listL.size()>0){
					for(SiteCountBean siteCountBeanL : listL){
						if(input_user==siteCountBeanL.getInput_user()){
							siteCountBean.setLinkInfoCount(siteCountBeanL.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setLinkInfoCount(0);
				}
				
				//已发布信息
				if(listP!=null && listP.size()>0){
					for(SiteCountBean siteCountBeanP : listP){
						if(input_user==siteCountBeanP.getInput_user()){
							siteCountBean.setReleasedCount(siteCountBeanP.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setReleasedCount(0);
				}
				  
				siteCountBean.setReleaseRate();
				
			}
			
			return listAll;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//得到部门信息统计
	public static List<SiteCountBean> getSiteCountListByInput(Map mp){
		List<SiteCountBean> listResult = new ArrayList<SiteCountBean>();
		try{
            
			List<SiteCountBean> listAll = getSiteCountListByInputUser(mp);
			
			List<DeptBean> deptBeans = DeptManager.getChildDeptListByID("0");
			for (DeptBean deptBean : deptBeans) {
				SiteCountBean bean = getCateChildList(deptBean, listAll);
				listResult.add(bean);
			}
			
			return listResult;
		}catch (Exception e) {
			e.printStackTrace();
			return listResult;
		}
	}
	
	//递归用
	private static SiteCountBean getCateChildList(DeptBean deptBean,List<SiteCountBean> list){
		SiteCountBean siteCountBean = new SiteCountBean();
		siteCountBean.setDept_id(deptBean.getDept_id());
		siteCountBean.setDept_name(deptBean.getDept_name());
		siteCountBean.setTree_position(deptBean.getTree_position());
		
		List<DeptBean> deptBeans = DeptManager.getChildDeptListByID(String.valueOf(deptBean.getDept_id()));
		if (deptBeans.size() != 0) {
			siteCountBean.setIs_leaf(false);
			List<SiteCountBean> childList = new ArrayList<SiteCountBean>();
			int inputCount = 0;
			int hostInfoCount = 0;
			int refInfoCount = 0;
			int linkInfoCount = 0;
			int releasedCount = 0; //发布信息条数
			
			for(SiteCountBean bean : list){
				if(siteCountBean.getDept_id()==bean.getDept_id()){
					inputCount += bean.getInputCount();
					hostInfoCount += bean.getHostInfoCount();
					refInfoCount += bean.getRefInfoCount();
					linkInfoCount += bean.getLinkInfoCount();	
					releasedCount += bean.getReleasedCount();
				  }
			}   			
			for (DeptBean beanR : deptBeans) {
				SiteCountBean refBean = getCateChildList(beanR, list);
				childList.add(refBean);
			} 
			siteCountBean.setInputCount(inputCount);
			siteCountBean.setHostInfoCount(hostInfoCount);
			siteCountBean.setRefInfoCount(refInfoCount);
			siteCountBean.setLinkInfoCount(linkInfoCount);
			siteCountBean.setReleasedCount(releasedCount);
			siteCountBean.setReleaseRate();
			siteCountBean.setChild_list(childList); 
		}else {
			int inputCount = 0;
			int hostInfoCount = 0;
			int refInfoCount = 0;
			int linkInfoCount = 0;
			int releasedCount = 0; //发布信息条数
			for(SiteCountBean bean : list){
				if(siteCountBean.getDept_id()==bean.getDept_id()){
					inputCount += bean.getInputCount();
					hostInfoCount += bean.getHostInfoCount();
					refInfoCount += bean.getRefInfoCount();
					linkInfoCount += bean.getLinkInfoCount();	
					releasedCount += bean.getReleasedCount();
				}
			}
			siteCountBean.setInputCount(inputCount);
			siteCountBean.setHostInfoCount(hostInfoCount);
			siteCountBean.setRefInfoCount(refInfoCount);
			siteCountBean.setLinkInfoCount(linkInfoCount);
			siteCountBean.setReleasedCount(releasedCount);
			siteCountBean.setReleaseRate();
		}
		//System.out.println("siteCountBean.getReleaseRate() leaf ====" + siteCountBean.getReleaseRate());
		return siteCountBean;
	}
			
	
	//按站点统计
	public static List<SiteCountBean> getSiteCountListBySite(Map mp){
		try{
            //得到--全部信息  
			List<SiteCountBean> listAll = SiteCountDAO.getSiteCountListBySite(mp);
			//得到--主信息
			mp.put("is_host","0");
			List<SiteCountBean> listZ = SiteCountDAO.getSiteCountListBySite(mp);
			//得到--引用信息
			mp.put("is_host","1");
			List<SiteCountBean> listY = SiteCountDAO.getSiteCountListBySite(mp);
			//得到--链接信息
			mp.put("is_host","2");
			List<SiteCountBean> listL = SiteCountDAO.getSiteCountListBySite(mp);
			//得到--已发布信息
			mp.put("info_status","8");
			mp.remove("is_host");
			List<SiteCountBean> listP = SiteCountDAO.getSiteCountListBySite(mp);
			
			for(SiteCountBean siteCountBean : listAll){
				String site_id = siteCountBean.getSite_id();
				siteCountBean.setAllCount(siteCountBean.getIcount());
				siteCountBean.setInputCount(siteCountBean.getIcount());
				
				//主信息
				if(listZ!=null && listZ.size()>0){
					for(SiteCountBean siteCountBeanZ : listZ){
						if(site_id.equals(siteCountBeanZ.getSite_id())){
							siteCountBean.setHostInfoCount(siteCountBeanZ.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setHostInfoCount(0);
				}
				
				//引用信息
				if(listY!=null && listY.size()>0){
					for(SiteCountBean siteCountBeanY : listY){
						if(site_id.equals(siteCountBeanY.getSite_id())){
							siteCountBean.setRefInfoCount(siteCountBeanY.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setRefInfoCount(0);
				}
				
				//链接信息
				if(listL!=null && listL.size()>0){
					for(SiteCountBean siteCountBeanL : listL){
						if(site_id.equals(siteCountBeanL.getSite_id())){
							siteCountBean.setLinkInfoCount(siteCountBeanL.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setLinkInfoCount(0);
				}
				
				//已发布信息
				if(listP!=null && listP.size()>0){
					for(SiteCountBean siteCountBeanP : listP){
						if(site_id.equals(siteCountBeanP.getSite_id())){
							siteCountBean.setReleasedCount(siteCountBeanP.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setReleasedCount(0);
				}   
				siteCountBean.setReleaseRate();
			}
			
			//按数据量排序 -- 降序排序
			SiteCountComparator countComparator = new SiteCountComparator();
			Collections.sort(listAll, countComparator);
			
			return listAll;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//按某一个站点下面的所有栏目统计
	public static List<SiteCountBean> getSiteCountListBySiteCate(Map mp){
		try{
            //得到--全部信息  
			List<SiteCountBean> listAll = SiteCountDAO.getSiteCountListBySiteCate(mp);
			//得到--主信息
			mp.put("is_host","0");
			List<SiteCountBean> listZ = SiteCountDAO.getSiteCountListBySiteCate(mp);
			//得到--引用信息
			mp.put("is_host","1");
			List<SiteCountBean> listY = SiteCountDAO.getSiteCountListBySiteCate(mp);
			//得到--链接信息
			mp.put("is_host","2");
			List<SiteCountBean> listL = SiteCountDAO.getSiteCountListBySiteCate(mp);
			//得到--已发布信息
			mp.put("info_status","8");
			mp.remove("is_host");
			List<SiteCountBean> listP = SiteCountDAO.getSiteCountListBySiteCate(mp);
			
			for(SiteCountBean siteCountBean : listAll){
				int cat_id = siteCountBean.getCat_id();
				siteCountBean.setAllCount(siteCountBean.getIcount());
				siteCountBean.setInputCount(siteCountBean.getIcount());
				
				//主信息
				if(listZ!=null && listZ.size()>0){
					for(SiteCountBean siteCountBeanZ : listZ){
						if(cat_id==siteCountBeanZ.getCat_id()){
							siteCountBean.setHostInfoCount(siteCountBeanZ.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setHostInfoCount(0);
				}
				
				//引用信息
				if(listY!=null && listY.size()>0){
					for(SiteCountBean siteCountBeanY : listY){
						if(cat_id==siteCountBeanY.getCat_id()){
							siteCountBean.setRefInfoCount(siteCountBeanY.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setRefInfoCount(0);
				}
				
				//链接信息
				if(listL!=null && listL.size()>0){
					for(SiteCountBean siteCountBeanL : listL){
						if(cat_id==siteCountBeanL.getCat_id()){
							siteCountBean.setLinkInfoCount(siteCountBeanL.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setLinkInfoCount(0);
				}
				
				//已发布信息
				if(listP!=null && listP.size()>0){
					for(SiteCountBean siteCountBeanP : listP){
						if(cat_id==siteCountBeanP.getCat_id()){
							siteCountBean.setReleasedCount(siteCountBeanP.getIcount());
							break;
						}
					}
				}else{
					siteCountBean.setReleasedCount(0);
				}   
				siteCountBean.setReleaseRate();
			}
			return listAll;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//按某一个站点下面的所有栏目统计
	public static List<SiteCountBean> getSiteCountListByCate(Map mp){
		List<SiteCountBean> listResult = new ArrayList<SiteCountBean>();
		try{
            
			List<SiteCountBean> listAll = getSiteCountListBySiteCate(mp);
//			for(SiteCountBean bean : listAll){
//				System.out.println("-----------------");
//				System.out.println("bean.getCat_name()=" + bean.getCat_name());
//				System.out.println("bean.getInputCount()=" + bean.getInputCount());
//				System.out.println("bean.getHostInfoCount()=" + bean.getHostInfoCount());
//				System.out.println("bean.getRefInfoCount()=" + bean.getRefInfoCount());
//				System.out.println("bean.getLinkInfoCount()=" + bean.getLinkInfoCount());	
//				System.out.println("bean.getReleasedCount()=" + bean.getReleasedCount());
//			}
			
			
			//得到站点的名称 也就是栏目树的最大节点
			String site_id = (String)mp.get("site_id");
			SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);
			CategoryBean categoryBeanRoot = new CategoryBean();
			categoryBeanRoot.setCat_cname(sb.getSite_name());
			categoryBeanRoot.setCat_id(0);
			categoryBeanRoot.setSite_id(site_id);
			
			//得到网站信息栏目
			SiteCountBean bean = getSiteChildListCate(categoryBeanRoot, listAll);
			listResult.add(bean);
			
			//得到网站专题信息栏目 -------------------------------------------------- start 	
			int mun = -1;
			//得到主题信息的根节点 用于 统计结果 网站信息和专题信息的区分 -- start
			SiteCountBean zt = new SiteCountBean();
			List<SiteCountBean> childListZt = new ArrayList<SiteCountBean>();
			int inputCountZt = 0;
			int hostInfoCountZt = 0;
			int refInfoCountZt = 0;
			int linkInfoCountZt = 0;
			int releasedCountZt = 0; //发布信息条数
			List<ZTCategoryBean> ztCategoryBeans = ZTCategoryManager.getZTCategoryList(site_id);
			if(ztCategoryBeans.size()>0){//如果有网站有专题信息
				zt.setCat_id(mun--);
				zt.setIs_leaf(false);
				zt.setCat_name("专题信息");
				zt.setSite_id(site_id);
			}
			//得到主题信息的根节点 用于 统计结果 网站信息和专题信息的区分 -- end
			
			for(ZTCategoryBean ztCategoryBean : ztCategoryBeans){

				//得到专题根节点
				SiteCountBean beanZt = new SiteCountBean();
				beanZt.setCat_id(mun--);
				beanZt.setIs_leaf(false);
				beanZt.setCat_name(ztCategoryBean.getZt_cat_name());
				beanZt.setSite_id(site_id);
				List<SiteCountBean> childList = new ArrayList<SiteCountBean>();
				int inputCount = 0;
				int hostInfoCount = 0;
				int refInfoCount = 0;
				int linkInfoCount = 0;
				int releasedCount = 0; //发布信息条数
				
				List<CategoryBean> ztBeans = CategoryManager.getZTCategoryListBySiteAndType(ztCategoryBean.getZt_cat_id(),site_id);
				for(CategoryBean bean2 : ztBeans){
					SiteCountBean siteCountBean = getSiteChildListCateZt(bean2, listAll); 
					inputCount += siteCountBean.getInputCount();
					hostInfoCount += siteCountBean.getHostInfoCount();
					refInfoCount += siteCountBean.getRefInfoCount();
					linkInfoCount += siteCountBean.getLinkInfoCount();
					releasedCount += siteCountBean.getReleasedCount();
					childList.add(siteCountBean);
				}
				beanZt.setInputCount(inputCount);
				beanZt.setHostInfoCount(hostInfoCount);
				beanZt.setRefInfoCount(refInfoCount);
				beanZt.setLinkInfoCount(linkInfoCount);
				beanZt.setReleasedCount(releasedCount);
				beanZt.setReleaseRate();
				beanZt.setChild_list(childList); 
				
				inputCountZt += beanZt.getInputCount();
				hostInfoCountZt += beanZt.getHostInfoCount();
				refInfoCountZt += beanZt.getRefInfoCount();
				linkInfoCountZt += beanZt.getLinkInfoCount();
				releasedCountZt += beanZt.getReleasedCount();
				childListZt.add(beanZt);
				
			}
			
			zt.setInputCount(inputCountZt);
			zt.setHostInfoCount(hostInfoCountZt);
			zt.setRefInfoCount(refInfoCountZt);
			zt.setLinkInfoCount(linkInfoCountZt);
			zt.setReleasedCount(releasedCountZt);
			zt.setReleaseRate();
			zt.setChild_list(childListZt);
			if(ztCategoryBeans.size()>0){//如果有网站有专题信息
				listResult.add(zt);
			}
			//得到网站专题信息栏目 -------------------------------------------------- end 
			
			return listResult;
		}catch (Exception e) {
			e.printStackTrace();
			return listResult;
		}
	}
	
	   
	//getSiteCountListByCate 方法 --- 递归用 -- 网站一般信息用
	private static SiteCountBean getSiteChildListCate(CategoryBean categoryBean,List<SiteCountBean> list){
		SiteCountBean siteCountBean = new SiteCountBean();
		siteCountBean.setCat_id(categoryBean.getCat_id());
		siteCountBean.setCat_name(categoryBean.getCat_cname());
		
		List<CategoryBean> cateBeans = CategoryManager.getChildCategoryList(categoryBean.getCat_id(), categoryBean.getSite_id());
		if (cateBeans.size() != 0) {
			siteCountBean.setIs_leaf(false);
			List<SiteCountBean> childList = new ArrayList<SiteCountBean>();
			int inputCount = 0;
			int hostInfoCount = 0;
			int refInfoCount = 0;
			int linkInfoCount = 0;
			int releasedCount = 0; //发布信息条数
			
			for(SiteCountBean bean : list){
				if(siteCountBean.getCat_id()==bean.getCat_id()){
					inputCount += bean.getInputCount();
					hostInfoCount += bean.getHostInfoCount();
					refInfoCount += bean.getRefInfoCount();
					linkInfoCount += bean.getLinkInfoCount();	
					releasedCount += bean.getReleasedCount();
				}
			}
			
			for (CategoryBean beanR : cateBeans) {
				if(beanR.getCat_type()!=0){
					continue;
				}
				SiteCountBean refBean = getSiteChildListCate(beanR, list);			
				inputCount += refBean.getInputCount();
				hostInfoCount += refBean.getHostInfoCount();
				refInfoCount += refBean.getRefInfoCount();
				linkInfoCount += refBean.getLinkInfoCount();
				releasedCount += refBean.getReleasedCount();
				
				childList.add(refBean);
			} 
			siteCountBean.setInputCount(inputCount);
			siteCountBean.setHostInfoCount(hostInfoCount);
			siteCountBean.setRefInfoCount(refInfoCount);
			siteCountBean.setLinkInfoCount(linkInfoCount);
			siteCountBean.setReleasedCount(releasedCount);
			siteCountBean.setReleaseRate();
			siteCountBean.setChild_list(childList); 
		}else {
			int inputCount = 0;
			int hostInfoCount = 0;
			int refInfoCount = 0;
			int linkInfoCount = 0;
			int releasedCount = 0; //发布信息条数
			for(SiteCountBean bean : list){
				if(siteCountBean.getCat_id()==bean.getCat_id()){
					inputCount += bean.getInputCount();
					hostInfoCount += bean.getHostInfoCount();
					refInfoCount += bean.getRefInfoCount();
					linkInfoCount += bean.getLinkInfoCount();	
					releasedCount += bean.getReleasedCount();
				}
			}
			siteCountBean.setInputCount(inputCount);
			siteCountBean.setHostInfoCount(hostInfoCount);
			siteCountBean.setRefInfoCount(refInfoCount);
			siteCountBean.setLinkInfoCount(linkInfoCount);
			siteCountBean.setReleasedCount(releasedCount);
			siteCountBean.setReleaseRate();
		}
		//System.out.println("siteCountBean.getReleaseRate() leaf ====" + siteCountBean.getReleaseRate());
		return siteCountBean;
	}
	
	
	//getSiteCountListByCate 方法 --- 递归用 -- 网站专题信息用
	private static SiteCountBean getSiteChildListCateZt(CategoryBean categoryBean,List<SiteCountBean> list){
		SiteCountBean siteCountBean = new SiteCountBean();
		siteCountBean.setCat_id(categoryBean.getCat_id());
		siteCountBean.setCat_name(categoryBean.getCat_cname());
		
		List<CategoryBean> cateBeans = CategoryManager.getChildCategoryList(categoryBean.getCat_id(), categoryBean.getSite_id());
		if (cateBeans.size() != 0) {
			siteCountBean.setIs_leaf(false);
			List<SiteCountBean> childList = new ArrayList<SiteCountBean>();
			int inputCount = 0;
			int hostInfoCount = 0;
			int refInfoCount = 0;
			int linkInfoCount = 0;
			int releasedCount = 0; //发布信息条数
			
			for(SiteCountBean bean : list){
				if(siteCountBean.getCat_id()==bean.getCat_id()){
					inputCount += bean.getInputCount();
					hostInfoCount += bean.getHostInfoCount();
					refInfoCount += bean.getRefInfoCount();
					linkInfoCount += bean.getLinkInfoCount();	
					releasedCount += bean.getReleasedCount();
				}
			}
			
			for (CategoryBean beanR : cateBeans) {
//				if(beanR.getCat_type()!=0){
//					continue;
//				}
				SiteCountBean refBean = getSiteChildListCate(beanR, list);
				inputCount += refBean.getInputCount();
				hostInfoCount += refBean.getHostInfoCount();
				refInfoCount += refBean.getRefInfoCount();
				linkInfoCount += refBean.getLinkInfoCount();
				releasedCount += refBean.getReleasedCount();
				
				childList.add(refBean);
			} 
			siteCountBean.setInputCount(inputCount);
			siteCountBean.setHostInfoCount(hostInfoCount);
			siteCountBean.setRefInfoCount(refInfoCount);
			siteCountBean.setLinkInfoCount(linkInfoCount);
			siteCountBean.setReleasedCount(releasedCount);
			siteCountBean.setReleaseRate();
			siteCountBean.setChild_list(childList); 
		}else {
			int inputCount = 0;
			int hostInfoCount = 0;
			int refInfoCount = 0;
			int linkInfoCount = 0;
			int releasedCount = 0; //发布信息条数
			for(SiteCountBean bean : list){
				if(siteCountBean.getCat_id()==bean.getCat_id()){
					inputCount += bean.getInputCount();
					hostInfoCount += bean.getHostInfoCount();
					refInfoCount += bean.getRefInfoCount();
					linkInfoCount += bean.getLinkInfoCount();	
					releasedCount += bean.getReleasedCount();
				}
			}
			siteCountBean.setInputCount(inputCount);
			siteCountBean.setHostInfoCount(hostInfoCount);
			siteCountBean.setRefInfoCount(refInfoCount);
			siteCountBean.setLinkInfoCount(linkInfoCount);
			siteCountBean.setReleasedCount(releasedCount);
			siteCountBean.setReleaseRate();
		}
		//System.out.println("siteCountBean.getReleaseRate() leaf ====" + siteCountBean.getReleaseRate());
		return siteCountBean;
	}
	
	
	
	//通过map条件得到组织机构各末级部门 的 “全部信息 主信息 引用信息 链接信息 已发信息 发稿率 ” 的 排行
	public static List<SiteCountBean> getDeptInfoCountBySiteAndNum(Map map){
		List<SiteCountBean> listResult = new ArrayList<SiteCountBean>();
		try{
            
			List<SiteCountBean> listResultAll = new ArrayList<SiteCountBean>();
			int num_size = Integer.valueOf((String)map.get("num_size"));
			List<SiteCountBean> listResultTemp = getSiteCountListByInput(map);
			for(int i=0;i<listResultTemp.size();i++){
				SiteCountBean siteCountBean = listResultTemp.get(i);
				if(siteCountBean.isIs_leaf()){
					listResultAll.add(siteCountBean);
				}
			}
			
			//按数据量排序 -- 降序排序
			DeptSiteCountComparator deptSiteCountComparator = new DeptSiteCountComparator();
			Collections.sort(listResultAll, deptSiteCountComparator);
			
			for(int i=0;i<listResultAll.size();i++){
				if(i>=num_size){
					break;
				}else{
					listResult.add(listResultAll.get(i));
				}
			} 
			
			return listResult;
		}catch (Exception e) {
			e.printStackTrace();
			return listResult;
		}
	}
}

