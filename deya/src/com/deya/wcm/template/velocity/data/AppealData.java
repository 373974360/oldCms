package com.deya.wcm.template.velocity.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.CalculateNumber;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.appeal.count.CountBean;
import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.wcm.bean.appeal.purpose.PurposeBean;
import com.deya.wcm.bean.appeal.satisfaction.SatisfactionBean;
import com.deya.wcm.bean.appeal.sq.ProcessBean;
import com.deya.wcm.bean.appeal.sq.SQBean;
import com.deya.wcm.bean.appeal.sq.SQCustom;
import com.deya.wcm.bean.member.MemberBean;
import com.deya.wcm.bean.system.assist.TagsBean;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.dao.appeal.sq.SQDAO;
import com.deya.wcm.services.appeal.cpDept.CpDeptManager;
import com.deya.wcm.services.appeal.model.ModelManager;
import com.deya.wcm.services.appeal.purpose.PurposeManager;
import com.deya.wcm.services.appeal.satisfaction.SatisfactionManager;
import com.deya.wcm.services.appeal.sq.SQManager;
import com.deya.wcm.services.member.MemberLogin;
import com.deya.wcm.services.system.assist.TagsManager;

/**
 * @author zhuliang
 * @version 1.0
 * @date   2011-4-27 下午06:00:12
 */
public class AppealData{
	private static int cur_page  = 1;
	private static int page_size  = 10;
	
	public static TurnPageBean getAppealCount(String params)
	{
		TurnPageBean tpb = new TurnPageBean();
		String con = getSQSearchCon(params);
		if(con == null || "".equals(con))
			return tpb;
		tpb.setCount(Integer.parseInt(SQManager.getBrowserSQCount(con)));
		tpb.setCur_page(cur_page);
		tpb.setPage_size(page_size);
		tpb.setPage_count(tpb.getCount()/tpb.getPage_size()+1);
		
		if(tpb.getCount()%tpb.getPage_size() == 0 && tpb.getPage_count() > 1)
			tpb.setPage_count(tpb.getPage_count()-1);
		
		if(cur_page > 1)
			tpb.setPrev_num(cur_page-1);
		
		tpb.setNext_num(tpb.getPage_count());
		if(cur_page < tpb.getPage_count())
			tpb.setNext_num(cur_page+1);
		return tpb;
	}
	
	public static List<SQBean> getAppealHotList(String params)
	{
		String con = "";
		String order_by = "hits desc";
		String page_sizes = JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num");
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{			
			if(tempA[i].toLowerCase().startsWith("weight="))
			{
				String weights = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(weights) && !weights.startsWith("$weight") && FormatUtil.isValiditySQL(weights))
				{					
					if(weights.contains(","))
					{
						String weight = weights.substring(0,weights.indexOf(","));
						String weight_end = weights.substring(weights.indexOf(",")+1);
						String w_cons = "";
						if(weight != null && !"".equals(weight.trim()))
							con += " and weight >= "+weight;
						if(weight_end != null && !"".equals(weight_end.trim()))
							con += " and weight <= "+weight_end;						
					}else
					{
						con += " and weight = "+weights+" ";
					}
				}				
			}
			if(tempA[i].toLowerCase().startsWith("orderby="))
			{
				String ob = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				//判断不为空，且不是替换字符,避免参数没有被替换掉，直接显示到sql
				if(!"".equals(ob) && !ob.startsWith("$orderby"))
					order_by = ob;
			}
			if(tempA[i].toLowerCase().startsWith("model_id="))
			{
				String mid = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(mid) && !mid.startsWith("$model_id") && FormatUtil.isValiditySQL(mid))
				{					
					con += " and model_id in ("+mid+")";
				}
			}
			if(tempA[i].toLowerCase().startsWith("sq_status="))
			{
				String sq_status = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(sq_status) && !sq_status.startsWith("$sq_status") && FormatUtil.isValiditySQL(sq_status))
				{					
					con += " and sq_status in ("+sq_status+")";
				}
			}
			if(tempA[i].toLowerCase().startsWith("hotype="))
			{
				String ob = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ob) && !ob.startsWith("$hotype"))
					order_by = ob;
			}			
			if(tempA[i].toLowerCase().startsWith("size="))
			{
				String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ps)  && !ps.startsWith("$size") && FormatUtil.isNumeric(ps))
					page_sizes = ps;
			}
		}
		order_by += " ,sq_dtime desc";
		return SQManager.getBrowserSQList(con,"1",page_sizes,order_by);
	}
	//得到诉求列表
	public static List<SQBean> getAppealList(String params) {		
		// TODO Auto-generated method stub
		String current_page = "1";
		String page_sizes = JconfigUtilContainer.velocityTemplateConfig().getProperty("num", "15", "show_list_num");
		String order_by = "sq_id desc";
		String con = getSQSearchCon(params);//拼查询条件	
		
		if(con == null || "".equals(con))
			return null;
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{					
			if(tempA[i].toLowerCase().startsWith("orderby="))
			{
				String ob = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				//判断不为空，且不是替换字符,避免参数没有被替换掉，直接显示到sql
				if(!"".equals(ob) && !ob.startsWith("$orderby"))
					order_by = ob;
			}
			if(tempA[i].toLowerCase().startsWith("size="))
			{
				String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ps)  && !ps.startsWith("$size") && FormatUtil.isNumeric(ps))
					page_sizes = ps;
			}
			if(tempA[i].toLowerCase().startsWith("cur_page="))
			{
				String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp))
					current_page = cp;
			}			
		}		
		order_by += " ,sq_dtime desc";
		return SQManager.getBrowserSQList(con,current_page,page_sizes,order_by);
	}
	
	//得到流程列表
	public static List<ProcessBean> getProcessList(int sq_id)
	{
		return SQManager.getProcessListBySqID(sq_id);
	}
	
	//得到部门对象
	public static CpDeptBean getCpDeptObject(int id)
	{
		return CpDeptManager.getCpDeptBean(id);
	}
	
	
	
	public static String getSQSearchCon(String params)
	{		
		String con = "";//拼查询条件
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{				
			if(tempA[i].toLowerCase().startsWith("weight="))
			{
				String weights = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(weights) && !weights.startsWith("$weight") && FormatUtil.isValiditySQL(weights))
				{					
					if(weights.contains(","))
					{
						String weight = weights.substring(0,weights.indexOf(","));
						String weight_end = weights.substring(weights.indexOf(",")+1);
						
						if(weight != null && !"".equals(weight.trim()))
							con += " and weight >= "+weight;
						if(weight_end != null && !"".equals(weight_end.trim()))
							con += " and weight <= "+weight_end;						
					}else
					{
						con += " and weight = "+weights+" ";
					}
				}				
			}
			if(tempA[i].toLowerCase().startsWith("sq_status="))
			{
				String sq_status = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(sq_status) && !sq_status.startsWith("$sq_status") && FormatUtil.isValiditySQL(sq_status))
				{					
					con += " and sq_status in ("+sq_status+")";
				}
			}
			if(tempA[i].toLowerCase().startsWith("dept_id="))
			{
				String dept_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(dept_id) && !dept_id.startsWith("$dept_id") && FormatUtil.isValiditySQL(dept_id))
				{					
					con += " and do_dept in ("+dept_id+")";
				}
			}
			if(tempA[i].toLowerCase().startsWith("sub_dept_id="))
			{
				String sub_dept_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(sub_dept_id) && !sub_dept_id.startsWith("$sub_dept_id") && FormatUtil.isValiditySQL(sub_dept_id))
				{					
					con += " and submit_dept_id in ("+sub_dept_id+")";
				}
			}
			if(tempA[i].toLowerCase().startsWith("related_dept="))
			{
				String related_dept = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(related_dept) && !related_dept.startsWith("$sub_dept_id") && FormatUtil.isValiditySQL(related_dept))
				{					
					con += " and (submit_dept_id in ("+related_dept+") or do_dept in ("+related_dept+"))";
				}
			}
			
			if(tempA[i].toLowerCase().startsWith("model_id="))
			{
				String mid = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(mid) && !mid.startsWith("$model_id") && FormatUtil.isValiditySQL(mid))
				{					
					con += " and model_id in ("+mid+")";
				}
			}
			if(tempA[i].toLowerCase().startsWith("submit_name="))
			{
				String submit_name = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				
				if(!"".equals(submit_name) && !submit_name.startsWith("$submit_name") && FormatUtil.isValiditySQL(submit_name))
				{					
					con += " and submit_name = '"+submit_name+"'";
				}
			}
			if(tempA[i].toLowerCase().startsWith("sq_title="))
			{
				String st = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				//判断是否不为空，且查询条件是正确的，不含特殊字符的				
				if(!"".equals(st) && !st.startsWith("$sq_title") && FormatUtil.isValiditySQL(st))
				{
					con += " and sq_title2 like '%"+st+"%'";
				}
			}
            if(tempA[i].toLowerCase().startsWith("sq_content="))
            {
                String st = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
                //判断是否不为空，且查询条件是正确的，不含特殊字符的
                if(!"".equals(st) && !st.startsWith("$sq_content") && FormatUtil.isValiditySQL(st))
                {
                    con += " and sq_content2 like '%"+st+"%'";
                }
            }
			if(tempA[i].toLowerCase().startsWith("start_time="))
			{
				String sd = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(sd) && !sd.startsWith("$start_time") && FormatUtil.isValiditySQL(sd))
				{					
					con += " and sq_dtime > '"+sd+" 00:00:00' ";
				}
			}
			if(tempA[i].toLowerCase().startsWith("end_time="))
			{
				String ed = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(ed) && !ed.startsWith("$end_time") && FormatUtil.isValiditySQL(ed))
				{					
					con += " and sq_dtime < '"+ed+" 23:59:59' ";
				}
			}
			if(tempA[i].toLowerCase().startsWith("sq_code="))
			{
				String sq = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(sq) && !sq.startsWith("$sq_code") && FormatUtil.isValiditySQL(sq))
				{					
					con += " and sq_code = '"+sq+"' ";
				}
			}
			if(tempA[i].toLowerCase().startsWith("query_code="))
			{
				String qc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(qc) && !qc.startsWith("$query_code") && FormatUtil.isValiditySQL(qc))
				{					
					con += " and query_code = '"+qc+"' ";
				}
			}
			if(tempA[i].toLowerCase().startsWith("pur_id="))
			{
				String pur_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(pur_id) && !pur_id.startsWith("$pur_id") && FormatUtil.isValiditySQL(pur_id))
				{					
					con += " and pur_id in ("+pur_id+") ";
				}
			}
			if(tempA[i].toLowerCase().startsWith("cur_page="))
			{
				String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp) && !"0".equals(cp))
					cur_page = Integer.parseInt(cp);
			}
			if(tempA[i].toLowerCase().startsWith("size="))
			{
				String size = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(size) && !size.startsWith("$size") && FormatUtil.isNumeric(size))
					page_size = Integer.parseInt(size);
			}
			if(tempA[i].toLowerCase().startsWith("tag_id="))
			{
				String tag_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(tag_id) && !tag_id.startsWith("$pur_id") && FormatUtil.isValiditySQL(tag_id))
				{					
					con += " and sq_id in ( select sq_id from cp_sq_tag where tag_id = "+tag_id+" ) ";
				}
			}
			if(tempA[i].toLowerCase().startsWith("showtype="))
			{
									
				con += " and 1=1 ";
				
			}
		}		
		return con;
	}
	
	//根据业务ID获取部门，收信人列表
	public static List<CpDeptBean> getAppealDeptList(String model_id)
	{
		return ModelManager.getModelDeptListByMID(Integer.parseInt(model_id));
	}
	
	//得到诉求目地列表
	public static List<PurposeBean> getPurposeList()
	{
		return PurposeManager.getAllPurposeList();
	}
	//得到诉求内容
	public static SQBean getAppealContent(String sq_id,HttpServletRequest request)
	{//输入未审核的信息内容，用于信件打印
		String sq_code = FormatUtil.formatNullString(request.getParameter("sq_code"));
		String query_code = FormatUtil.formatNullString(request.getParameter("query_code"));
		if("m".equals(request.getParameter("st")) || (sq_code != null && !"".equals(sq_code) && query_code != null && !"".equals(query_code)))
		{
			return SQManager.getSqBean(Integer.parseInt(sq_id));
		}
		else
		{
			String me_id = "";
			MemberBean mb = MemberLogin.getMemberBySession(request);
			
			if(mb != null)
				me_id = mb.getMe_id();
			
			return SQManager.getBrowserSQBean(Integer.parseInt(sq_id),me_id);
		}
	}
	
	/**
	 * 根据信件ID和扩展字段名称得到内容
	 * @return int sq_id
	 * @return String cu_key
	 * @return String
	 */
	public static String getSQCustomValue(int sq_id,String cu_key)
	{
		return SQManager.getSQCustomValue(sq_id,cu_key);
	}
	
	//得到满意度列表
	public static List<SatisfactionBean> getAppealSatisfactionList()
	{
		return SatisfactionManager.getSatisfactionList();
	}
	
	//得到业务列表
	public static List<ModelBean> getAppealModelList()
	{
		return ModelManager.getAllSQModelList();
	}	
	
	//得到业务对象
	public static ModelBean getAppealModelObject(String model_id)
	{
		return ModelManager.getModelBean(Integer.parseInt(model_id));
	}
	
	//得到特征列表
	public static List<TagsBean> getAppealTagList()
	{
		return TagsManager.getTagListByAPPSite("appeal","");
	}
	
	//根据sq_id得到满意度分值
	public static String getAppealScore(String sq_id)
	{
		return SatisfactionManager.getSatScoreBySQID(sq_id);
	}	
	
	/**
	 * 按照办结状态统计部门的办结量,并排序
	 * @param String model_ids 业务ID，可为空
	 * @param int row_count
	 * @return List
	 */	
	public static List<CountBean> getStatisForFinishDept(String model_ids,int row_count)
	{
		return SQManager.getSqFinishCountForDept(model_ids, row_count);
	}
		
	/**
	 * 根据业务ID，信件类型得到总数
	 * @param String model_id
	 * @param String 信件类型 all 所有，sl 受理信件　bj　办结信件（回复件） 
	 * @return String
	 */	
	public static String getAllAppealCount(String model_id,String count_type)
	{
		String c_type = "all";
		if(count_type != null && !"".equals(count_type))
		{
			c_type = count_type;
		}
		Map<String,String> m = new HashMap<String,String>();
		if(model_id != null && !"".equals(model_id))
			m.put("model_ids", model_id);
		if("sl".equals(c_type))
			m.put("sl_con", "sl_con");
		if("bj".equals(c_type))
			m.put("bj_con", "bj_con");
		return SQDAO.getSQStatistics(m);
	}
	
	/**
	 * 根据业务ID，信件类型得到总数
	 * @param String count_type 信件类型 all 所有，sl 受理信件　bj　办结信件（回复件）
	 * 				 count_data yesterday昨天的  ultimo 上月的 instant 本月 cur_data 当天
	 * @return String
	 */
	public static String getAllAppealCount(String params)
	{
		Map<String,String> m = new HashMap<String,String>();
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{	
			if(tempA[i].toLowerCase().startsWith("count_type="))
			{
				String count_type = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(count_type) && !count_type.startsWith("$count_type") && FormatUtil.isValiditySQL(count_type))
				{		
					if("sl".equals(count_type))
						m.put("sl_con", "sl_con");
					if("bj".equals(count_type))
						m.put("bj_con", "bj_con");
					if("wsl".equals(count_type))
						m.put("wsl_con", "wsl_con");
				}				
			}
			if(tempA[i].toLowerCase().startsWith("count_data="))
			{
				String count_data = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(count_data) && !count_data.startsWith("$count_data") && FormatUtil.isValiditySQL(count_data))
				{		
					if("yesterday".equals(count_data))
					{
						m.put("yesterday_con",count_data);
						m.put("start_data", DateUtil.getDateBefore(DateUtil.getCurrentDateTime(),1)+" 00:00:00");
						m.put("end_data", DateUtil.getDateBefore(DateUtil.getCurrentDateTime(),1)+" 23:59:59");
					}
					if("ultimo".equals(count_data))
					{
						String dt = "";
						String d = DateUtil.getCurrentDate();
						String[] dateA = d.split("-");
						int year = Integer.parseInt(dateA[0]);
						int month = Integer.parseInt(dateA[1]);
						if(month == 1)
						{
							year = year-1;
							month = 12;
						}else
							month = month -1;
						if(month < 10)
						{
							dt = year+"-0"+month;
						}else
							dt = year+"-"+month;
						m.put("ultimo",dt);
					}
					if("instant".equals(count_data))
					{
						m.put("instant", DateUtil.getCurrentDateTime("yyyy-MM"));
					}
					if("cur_data".equals(count_data))
					{
						m.put("cur_data",count_data);
						m.put("start_data", DateUtil.getCurrentDate()+" 00:00:00");
					}
				}				
			}		
			if(tempA[i].toLowerCase().startsWith("model_id="))
			{
				String model_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(model_id) && !model_id.startsWith("$model_id") && FormatUtil.isValiditySQL(model_id))
				{				
					m.put("model_ids", model_id);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("do_dept="))
			{
				String do_dept = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(do_dept) && !do_dept.startsWith("$do_dept") && FormatUtil.isValiditySQL(do_dept))
				{				
					m.put("do_dept", do_dept);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("submit_dept_id="))
			{
				String submit_dept_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(submit_dept_id) && !submit_dept_id.startsWith("$submit_dept_id") && FormatUtil.isValiditySQL(submit_dept_id))
				{				
					m.put("submit_dept_id", submit_dept_id);
				}				
			}
		}
		return SQDAO.getSQStatistics(m);
	}
	
	/**
	 * 根据条件统计满意度排行并排序
	 * @param String model_ids
	 * @param int row_count
	 * @return List
	 */
	public static List<CountBean> getAppealSatisfaction(String model_id,int row_count)
	{
		return SQManager.getSQSatisfaction(model_id, row_count);
	}
	
	/**
	 * 按照办结率对部门进行排序
	 * @param String model_ids 业务ID
	 * @param int row_count
	 * @return List
	 */
	public static List<CountBean> getAppealFinishRate(String model_id,int row_count)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_id);
		return SQManager.getAppealFinishRate(m, row_count);
	}
	
	/**
	 * 按照办结率对部门进行排序,传多个参数
	 * @param String params model_id业务ID  row_count 长度 timeout_flag超期标识
	 * @param int row_count
	 * @return List
	 */
	public static List<CountBean> getAppealFinishRate(String params)
	{
		Map<String,String> m = new HashMap<String,String>();
		int row_count = paramsForMap(m,params);
		return SQManager.getAppealFinishRate(m, row_count);
	}
	
	//按红黄牌统计排序
	public static List<CountBean> getAppealFinishRateForAlarm(String params)
	{
		Map<String,String> m = new HashMap<String,String>();
		int row_count = paramsForMap(m,params);
		return SQManager.getAppealFinishRateForAlarm(m, row_count);
	}
	
	public static Integer paramsForMap(Map<String,String> m,String params)
	{
		int row_count = 10;
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{				
			if(tempA[i].toLowerCase().startsWith("model_id="))
			{
				String model_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(model_id) && !model_id.startsWith("$model_id") && FormatUtil.isValiditySQL(model_id))
				{				
					m.put("model_ids", model_id);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("timeout_flag="))
			{
				String timeout_flag = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(timeout_flag) && !timeout_flag.startsWith("$timeout_flag") && FormatUtil.isValiditySQL(timeout_flag))
				{				
					m.put("timeout_flag", timeout_flag);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("sq_status="))
			{
				String sq_status = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(sq_status) && !sq_status.startsWith("$sq_status") && FormatUtil.isValiditySQL(sq_status))
				{				
					m.put("sq_status", sq_status);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("alarm_flag="))
			{
				String alarm_flag = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(alarm_flag) && !alarm_flag.startsWith("$alarm_flag") && FormatUtil.isValiditySQL(alarm_flag))
				{				
					m.put("alarm_flag", alarm_flag);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("not_dept_id="))
			{
				String not_dept_id = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(not_dept_id) && !not_dept_id.startsWith("$not_dept_id") && FormatUtil.isValiditySQL(not_dept_id))
				{				
					m.put("not_dept_id", not_dept_id);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("row_count="))
			{
				String row_counts = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(row_counts) && !row_counts.startsWith("$row_count") && FormatUtil.isValiditySQL(row_counts))
				{				
					row_count = Integer.parseInt(row_counts);
				}				
			}
		}
		return row_count;
	}
	
	
	/**
	 * 查询部门办结率并对未办结数进行正序，倒序排列
	 * @param String model_ids 业务ID
	 * @param int row_count
	 * @param String type 排序方式
	 * @return List
	 */
	public static List<CountBean> getAppealFinishRateForSort(String model_id,int row_count,String sort_type)
	{
		return SQManager.getAppealFinishRateForSort(SQManager.getAppealFinishRate2(model_id), sort_type, row_count);
	}
	
	public static List<CountBean> getAppealFinishRate2(String model_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_ids", model_id);
		//第一步按业务对部门信件总数进行排序
		List<CountBean> l = SQDAO.getSqFinishCountForDept(m);
		List<CountBean> count_l = new ArrayList<CountBean>();
		if(l != null && l.size() > 0)
		{
			for(CountBean cb : l)
			{
				try{
					cb.setDept_name(CpDeptManager.getCpDeptName(cb.getDept_id()));
					//第二步：根据部门ID得到该部门的办结总数
					m.put("bj_con", "bj_con");
					m.put("do_dept", cb.getDept_id()+"");
					String bj_count = SQDAO.getSQStatistics(m);
					cb.setCountendp(CalculateNumber.getRate(bj_count,cb.getCount()+""));
					cb.setCountwei((Integer.parseInt(cb.getCount()) - Integer.parseInt(bj_count))+"");
					count_l.add(cb);					
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return count_l;
	}
	
	
	 //根据部门id得到所关联的业务
    public static String getModelIdByDept_id(String dept_id){
	 
	 return  SQManager.getModelIdByDept_id(dept_id);
    }
    
    //传多个业务ID时候，返回第一个
    public static String getFirstModle_id(String mode_ids){
    	
    	String[] temp=null;
    	//String mid = FormatUtil.formatNullString(mode_ids.substring(mode_ids.indexOf("=")+1));
		if(mode_ids !=""){
				temp= mode_ids.split(",");
		}
    	return temp[0];
    }

    //按业务信件量进行排行
    public static List<CountBean> getSQStatisticsForModel(String params)
    {
    	Map<String,String> m = new HashMap<String,String>();
		int row_count = 10;
		String[] tempA = params.split(";");
		for(int i=0;i<tempA.length;i++)
		{	
			if(tempA[i].toLowerCase().startsWith("timeout_flag="))
			{
				String timeout_flag = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(timeout_flag) && !timeout_flag.startsWith("$timeout_flag") && FormatUtil.isValiditySQL(timeout_flag))
				{				
					m.put("timeout_flag", timeout_flag);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("sq_status="))
			{
				String sq_status = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(sq_status) && !sq_status.startsWith("$sq_status") && FormatUtil.isValiditySQL(sq_status))
				{				
					m.put("sq_status", sq_status);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("sq_flag="))
			{
				String sq_flag = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(sq_flag) && !sq_flag.startsWith("$sq_flag") && FormatUtil.isValiditySQL(sq_flag))
				{				
					m.put("sq_flag", sq_flag);
				}				
			}
			if(tempA[i].toLowerCase().startsWith("row_count="))
			{
				String row_counts = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=")+1));
				if(!"".equals(row_counts) && !row_counts.startsWith("$row_count") && FormatUtil.isValiditySQL(row_counts))
				{				
					row_count = Integer.parseInt(row_counts);
				}				
			}
		}
		return SQManager.getSQStatisticsForModel(m, row_count);
    }
    

	 /**
	 * 根据条件得到信件扩展字段列表
	 * @return int sq_id
	 * @return List
	 */	
    
    public static Map<String,String> getSQCustomForBrower(int sq_id)
	{
	    Map<String,String> m = new HashMap<String,String>();
    	List<SQCustom>  SQCustom = SQManager.getSQCustomList(sq_id);
    	if(SQCustom != null && SQCustom.size() > 0)
    	{
    		for(int i=0;i<SQCustom.size();i++)
    		{ 
    			m.put(SQCustom.get(i).getCu_key(),SQCustom.get(i).getCu_value());
    		}
    	}
    	return m;
	}		

	public static void main(String[] args)
	{
		System.out.println(getAllAppealCount("count_data=instant;count_type=bj"));
		/*model_id=$model_id;size=15;cur_page=$cur_page;submit_name=$!submit_name;pur_id=$!pur_id;start_time=$!start_time;end_time=$!end_time;sq_code=!$sq_code;sq_title=$!sq_title;
		List<CountBean> l = getAppealFinishRate("row_count=100");
		for(CountBean sb : l)
		{
			System.out.println(sb.getDept_name()+"  "+sb.getCountendp());
		}
		yesterday昨天的  ultimo 上月的 instant 本月
		*/
		
	}
}
