package com.deya.wcm.services.cms.countsource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deya.util.OutExcel;
import com.deya.wcm.bean.cms.count.CmsCountBean;
import com.deya.wcm.services.cms.count.CmsCountExcelOut;

public class CmsCountSourceService {

	
	//按照栏目统计站点的发布信息  -  按信息来源分类
	public static List<CmsCountBean> getInfoCountListBySource(Map<String, String> mp){
		List<CmsCountBean> result = new ArrayList<CmsCountBean>();
		try{
			formatDate(mp);
			mp.remove("is_host");
			List<CmsCountBean> list_012 = CmsCountSourceDao.getInfoCountListBySource(mp);
			
			mp.put("is_host","0");
			List<CmsCountBean> list_0 = CmsCountSourceDao.getInfoCountListBySource(mp);
			
			mp.put("is_host","1");
			List<CmsCountBean> list_1 = CmsCountSourceDao.getInfoCountListBySource(mp);
			
			mp.put("is_host","2");
			List<CmsCountBean> list_2 = CmsCountSourceDao.getInfoCountListBySource(mp);
			
			for(CmsCountBean bean012 : list_012){
				String source = bean012.getCat_name();
				//
				//System.out.println("source---" + source);
				int allCount = bean012.getCount(); //全部信息条数
				bean012.setAllCount(allCount);
				
				int hostInfoCount = 0; //主信息条数
				for(CmsCountBean bean0 : list_0){
					if(source.equals(bean0.getCat_name())){
						//System.out.println(bean0.getCount()+"-------------------" + bean0.getCat_name());
						hostInfoCount = bean0.getCount();
						break;
					}
				}
				bean012.setHostInfoCount(hostInfoCount);
				
				int refInfoCount = 0; //引用信息条数
				for(CmsCountBean bean1 : list_1){
					if(source.equals(bean1.getCat_name())){
						refInfoCount = bean1.getCount();
						break;
					}
				}
				bean012.setRefInfoCount(refInfoCount);
				
				int linkInfoCount = 0; //链接信息条数
				for(CmsCountBean bean2 : list_2){
					if(source.equals(bean2.getCat_name())){
						linkInfoCount = bean2.getCount();
						break;
					}
				}
				bean012.setLinkInfoCount(linkInfoCount);
				
				result.add(bean012);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return result;
		}
	}
	
	
	/**
	 * 对参数中的字符型时间进行格式化
	 * 主要是针对时间格式中出现类似 2011-1-1短格式日期
	 * 处理完后,会变成2011-01-01形式的标准格式,然后在对结束时间拼接上
	 * 时间 " 23:59:59"以便包括当日的所有信息
	 * 
	 * exception-如果时间的解析出现问题,开始结束时间回取得当日的开始结束时间
	 * 如果有其他规则请重新处理catch{}中的代码
	 * @param mp
	 */
	private static void formatDate(Map<String, String> mp){
		String s_day = mp.get("start_day");
		String e_day = mp.get("end_day");
		
		// 如果条件中没有时间,则直接退出
		if(e_day == null){
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date date = sdf.parse(e_day);
			Date s_date = sdf.parse(s_day);
			e_day = sdf.format(date) + " 23:59:59";
			s_day = sdf.format(s_date) + " 00:00:01";
			
		}catch(ParseException pex){
			e_day = sdf.format(new Date()) + " 23:59:59";
			s_day = sdf.format(new Date()) + " 00:00:01";
		}
		
		mp.remove("end_day");
		mp.put("end_day", e_day);
		mp.remove("start_day");
		mp.put("start_day", s_day);
	}
	
	
	
	/**
	 * 统计导出Excel文件 -- 按人员统计
	 * @param List listBean
	 * @param List headerList
	 * @return	String 文件路径名字
	 */
	public static String cmsInfoOutExcel(List listBean,List headerList){
		try{
			
			String[] fileStr = CmsCountExcelOut.getFileUrl();
			
			//得到header
			final int size =  headerList.size();
			String[] head = (String[])headerList.toArray(new String[size]);
			
			//填充数据
			String[][] data = new String[listBean.size()][5];
			for(int i=0;i<listBean.size();i++){
				   CmsCountBean countBean = (CmsCountBean)listBean.get(i);
				   data[i][0] = countBean.getCat_name();
				   data[i][1] = String.valueOf(countBean.getAllCount());
				   data[i][2] = String.valueOf(countBean.getHostInfoCount());
				   data[i][3] = String.valueOf(countBean.getRefInfoCount());  
				   data[i][4] = String.valueOf(countBean.getLinkInfoCount());
			 }
			OutExcel oe=new OutExcel("站点信息统计表");
			oe.doOut(fileStr[0],head,data);
			
			return fileStr[1];
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}	
}
