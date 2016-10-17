package com.deya.wcm.services.appeal.sq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.CalculateNumber;
import com.deya.util.DateUtil;
import com.deya.util.RandomStrg;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.appeal.area.AreaBean;
import com.deya.wcm.bean.appeal.count.CountBean;
import com.deya.wcm.bean.appeal.cpDept.CpDeptBean;
import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.wcm.bean.appeal.purpose.PurposeBean;
import com.deya.wcm.bean.appeal.sq.ProcessBean;
import com.deya.wcm.bean.appeal.sq.SQAttachment;
import com.deya.wcm.bean.appeal.sq.SQBean;
import com.deya.wcm.bean.appeal.sq.SQCustom;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.assist.TagsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.sq.SQDAO;
import com.deya.wcm.services.appeal.area.AreaManager;
import com.deya.wcm.services.appeal.cpDept.CpDeptManager;
import com.deya.wcm.services.appeal.cpUser.CpUserManager;
import com.deya.wcm.services.appeal.model.ModelManager;
import com.deya.wcm.services.appeal.purpose.PurposeManager;
import com.deya.wcm.services.appeal.satisfaction.SatisfactionManager;
import com.deya.wcm.services.org.role.RoleUserManager;

/**
 *  诉求信件逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求信件逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */

public class SQManager {
	private static String defalut_randon_str = "A-Z0-9";//默认随机码显示范围
	
	
	/**
	 * 根据会员ID得到该会员所提交的信件列表（前台使用）
	 * @return 
	 * @return List
	 */	
	public static List<SQBean> getBroSQListByMemberID(String me_id)
	{		
		List<SQBean> sl = SQDAO.getBroSQListByMemberID(me_id);
		if(sl != null && sl.size() > 0)
		{
			for(int i=0;i<sl.size();i++)
			{
				SQBean sb = (SQBean)sl.get(i);				
				ModelBean mb = ModelManager.getModelBean(sb.getModel_id());				
				if(mb != null)
					sb.setModel_cname(mb.getModel_cname());
				
				if(sb.getSq_status() == 3)
					sb.setSq_status_name("已办结");
				else
					sb.setSq_status_name("未办结");
			}
		}
		return sl;
	}
	
	/**
	 * 得到信件列表总数（前台使用）
	 * @return 
	 * @return String
	 */
	public static String getBrowserSQCount(String con)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("con", con);
		return SQDAO.getBrowserSQCount(m);
	}
	
	/**
	 * 得到信件列表（前台使用）
	 * @param String con
	 * @param String current_page
	 * @param String page_sizes
	 * @param String order_by
	 * @return List
	 */
	public static List<SQBean> getBrowserSQList(String con,String current_page,String page_sizes,String order_by)
	{
		Map<String,String> m = new HashMap<String,String>();
		int start_page = 1;
		int page_size = 15;
		if(current_page != null && !"".equals(current_page) && !"0".equals(current_page))
		{
			try{
				start_page = Integer.parseInt(current_page);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		if(page_sizes != null && !"".equals(page_sizes))
		{
			try{
				page_size = Integer.parseInt(page_sizes);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}		
		m.put("con", con);
		m.put("start_num", ((start_page-1)*page_size)+"");
		m.put("page_size", page_size+"");		
		m.put("order_by", order_by);
		List<SQBean> sl = SQDAO.getBrowserSQList(m);
		if(sl != null && sl.size() > 0)
		{
			for(int i=0;i<sl.size();i++)
			{
				SQBean sb = (SQBean)sl.get(i);	
				
				if(sb.getSq_status() == 3)
					sb.setSq_status_name("已办结");
				else
					sb.setSq_status_name("未办结");
				
				SetOtherCNameInSQBean(sb);
			}
		}
		return sl;
	}
	
		
	/**
	 * 根据信件编码的查询码得到信件ID和modelid（前台使用）
	 * @param String sq_code
	 * @param String query_code
	 * @return SQBean
	 */
	public static SQBean searchBrowserSQBean(String sq_code,String query_code)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("sq_code", sq_code);
		m.put("query_code", query_code);
		return SQDAO.searchBrowserSQBean(m);
	}
	
	/**
	 * 得到允许的附件大小
	 * @param 
	 * @return int
	 */
	public static long getAppealFileSize()
	{
		try{
			return Long.parseLong(JconfigUtilContainer.bashConfig().getProperty("appeal", "", "file_size"));
		}catch(Exception e)
		{
			e.printStackTrace();
			return 2097152;
		}
			
	}
	
	/**
	 * 根据登录人员ID得到它是否是信件管理员
	 * @param int user_id
	 * @return boolean
	 */
	public static boolean isAppealManager(int user_id)
	{
		String user_ids = ","+RoleUserManager.getRoleIDSByUserAPP(user_id+"","appeal","")+",";
		return user_ids.contains(","+JconfigUtilContainer.systemRole().getProperty("appeal_manager", "", "role_id")+",");
	}
	
	/**
	 * 根据条件得到信件总数
	 * @param Map m
	 * @return String
	 */
	public static String getSqCount(Map<String,String> m,int user_id)
	{
		setTimeCon(m);
		getSqSearchCon(m,user_id);
		return SQDAO.getSqCount(m);
	}
	
	/**
	 * 根据条件得到信件列表
	 * @param  Map m
	 * @param int user_id
	 * @return List
	 */
	public static List<SQBean> getSqList(Map<String,String> m,int user_id)
	{
		setTimeCon(m);
		getSqSearchCon(m,user_id);
		List<SQBean> l = SQDAO.getSqList(m);
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				if(l.get(i).getDo_dept() != 0)
				{
					CpDeptBean cdb = CpDeptManager.getCpDeptBean(l.get(i).getDo_dept());
					if(cdb != null)
						l.get(i).setDo_dept_name(cdb.getDept_name());
				}
			}
		}
		return l;
	}
	
	/**
	 * 得到诉求列表查询条件
	 * @param Map m
	 * @param int user_id
	 * @return List
	 */
	public static void getSqSearchCon(Map<String,String> m,int user_id)
	{
		String model_ids = ModelManager.getModelIDSByUserID(user_id);
		if(!m.containsKey("model_id"))
		{
			//根据登录人员ID得到能管理的业务
			m.put("model_id",model_ids);
		}
		
		//根据用户ID得到相关的查询条件
		if(isAppealManager(user_id))
		{//判断是否是信件管理员
			m.put("is_admin",user_id+"");
		}
		//判断页类型是否是待审件
		if("dsj".equals(m.get("page_type")))
		{
			m.put("dsj_sql", getAutoConSq(model_ids,user_id));
		}else
		{
			m.remove("page_type");			
		}		
		
		//所能管理的部门
		m.put("do_dept",CpUserManager.getSQDeptIDbyUserID(user_id)+"");
	}
	
	/**
	 * 得到已办理的信件总数
	 * @param Map m
	 * @return String
	 */
	public static String getTransactSQCount(Map<String,String> m,int user_id)
	{
		setTimeCon(m);
		
		String model_ids = ModelManager.getModelIDSByUserID(user_id);
		if(!m.containsKey("model_id"))
		{
			//根据登录人员ID得到能管理的业务
			m.put("model_id",model_ids);
		}
		//所能管理的部门
		m.put("do_dept",CpUserManager.getSQDeptIDbyUserID(user_id)+"");
		return SQDAO.getTransactSQCount(m);
	}
	
	
	/**
	 * 根据业务ID，人员ID拼出它能看到的流程审核sql
	 * @param String model_ids
	 * @param int user_id
	 * @return String
	 */
	public static String getAutoConSq(String model_ids,int user_id)
	{
		String sql = "";
		String[] tempA = model_ids.split(",");
		for(int i=0;i<tempA.length;i++)
		{
			if(i > 0)
				sql += " or ";
			sql += "(model_id = "+tempA[i]+" and step_id <= "+ModelManager.getWFIDByMIDUserID(tempA[i], user_id+"")+")";
			
		}
		if(!"".equals(sql))
			sql = " and ("+sql+")";
		return sql;
	}
	
	/**
	 * 得到已办理的信件列表
	 * @param Map m
	 * @return List
	 */
	public static List<SQBean> getTransactSQList(Map<String,String> m,int user_id)
	{
		setTimeCon(m);
		String model_ids = ModelManager.getModelIDSByUserID(user_id);
		if(!m.containsKey("model_id"))
		{
			//根据登录人员ID得到能管理的业务
			m.put("model_id",model_ids);
		}
		//所能管理的部门
		m.put("do_dept",CpUserManager.getSQDeptIDbyUserID(user_id)+"");
		List<SQBean> l = SQDAO.getTransactSQList(m);
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				if(l.get(i).getDo_dept() != 0)
				{
					CpDeptBean cdb = CpDeptManager.getCpDeptBean(l.get(i).getDo_dept());
					if(cdb != null)
						l.get(i).setDo_dept_name(cdb.getDept_name());
				}
			}
		}
		return l;
	}
	
	/**
	 * 设置时间查询条件
	 * @param Map m
	 * @return void
	 */
	public static void setTimeCon(Map<String,String> m)
	{
		if(m.containsKey("search_date"))
		{
			if("day".equals(m.get("search_date")))
			{
				m.put("sq_dtime", DateUtil.getCurrentDate()+" 00:00:00");
			}
			if("week".equals(m.get("search_date")))
			{
				m.put("sq_dtime", DateUtil.getDateBefore(DateUtil.getCurrentDate(),7)+" 00:00:00");
			}
			if("month".equals(m.get("search_date")))
			{
				m.put("sq_dtime", DateUtil.getDateBefore(DateUtil.getCurrentDate(),30)+" 00:00:00");
			}
			if("year".equals(m.get("search_date")))
			{
				m.put("sq_dtime", DateUtil.getDateBefore(DateUtil.getCurrentDate(),365)+" 00:00:00");
			}
		}
	}
	
	/**
	 * 根据条件得到信件信息
	 * @param int sq_id
	 * @return SQBean
	 */
	public static SQBean getSqBean(int sq_id)
	{
		SQBean sb = SQDAO.getSqBean(sq_id);
		SetOtherCNameInSQBean(sb);
		return sb;
	}
	
	/**
	 * 根据条件得到信件信息（前台使用）
	 * @return int sq_id
	 * @return String me_id 会员ID
	 * @return SQBean
	 */
	public static SQBean getBrowserSQBean(int sq_id,String me_id)
	{
		SQBean sb = SQDAO.getBrowserSQBean(sq_id,me_id);
		SetOtherCNameInSQBean(sb);
		return sb;
	}
	
	public static void SetOtherCNameInSQBean(SQBean sb)
	{		
		if(sb != null)
		{
			if(sb.getModel_id() != 0)
			{
				ModelBean mb = ModelManager.getModelBean(sb.getModel_id());
				if(mb != null)
					sb.setModel_cname(mb.getModel_cname());
			}
			if(sb.getPur_id() != 0)
			{
				PurposeBean pb = PurposeManager.getPurposeByID(sb.getPur_id()+"");
				if(pb != null)
					sb.setPur_name(pb.getPur_name());		
			}
			if(sb.getDo_dept() != 0)
			{
				CpDeptBean cb = CpDeptManager.getCpDeptBean(sb.getDo_dept());
				if(cb != null)
					sb.setDo_dept_name(cb.getDept_name());
			}
			
			if(sb.getArea_id() != 0)
			{
				AreaBean ab = AreaManager.getAreaBean(sb.getArea_id());
				if(ab != null)
					sb.setArea_name(ab.getArea_cname());
			}
		}			
	}
	
	/**
	 * 根据条件得到信件扩展字段列表
	 * @return int sq_id
	 * @return List
	 */	
	public static List<SQCustom> getSQCustomList(int sq_id)
	{
		return SQDAO.getSQCustomList(sq_id);
	}
	
	/**
	 * 根据信件ID和扩展字段名称得到内容
	 * @return int sq_id
	 * @return String cu_key
	 * @return String
	 */
	public static String getSQCustomValue(int sq_id,String cu_key)
	{
		return SQDAO.getSQCustomValue(sq_id,cu_key);
	}
	
	/**
     * 根据业务ID得到随机查询码，用于前台页面添加诉求时产生查询码
     * @param 
     * @return List
     * */
	public static String getQueryCode(int model)
	{
		ModelBean mb = ModelManager.getModelBean(model);
		return RandomStrg.getRandomStr(defalut_randon_str, mb.getQuery_num()+"");
	}
	
	/**
     * 根据业务ID得到信件编码
     * @param 
     * @return List
     * */
	public static String getSQCode(int model)
	{
		ModelBean mb = ModelManager.getModelBean(model);
		//编码头＋日期＋随机码
		return mb.getCode_pre()+DateUtil.getCurrentDateTime(mb.getCode_rule())+RandomStrg.getRandomStr(defalut_randon_str, mb.getCode_num()+"");
	}
	
	/**
	 * 插入信件内容 用于前台
	 * @param SQBean sb
	 * @return boolean
	 */
	public static synchronized SQBean insertSQ(SQBean sb,SQAttachment sqa)
	{
		sb.setSubmit_dept_id(sb.getDo_dept());
		int sq_id = PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_SQ_TABLE_NAME);
		sb.setSq_id(sq_id);
		sb.setQuery_code(getQueryCode(sb.getModel_id()));
		ModelBean mb = ModelManager.getModelBean(sb.getModel_id());
		sb.setPublish_status(mb.getIs_auto_publish());
		sb.setSq_code(getSQCode(sb.getModel_id()));
		if("".equals(sb.getSq_dtime()))
			sb.setSq_dtime(DateUtil.getCurrentDateTime());
		sb.setTime_limit(mb.getTime_limit());
		if(mb.getIs_sort() == 1)
		{
			//如果需要分拣，将处理部门置为0
			sb.setDo_dept(0);
		}
		sb.setSq_all_number(SQDAO.getSQYearNum()+1);
		sb.setSq_number(SQDAO.getSQNumber(sb.getModel_id(),sb.getSq_dtime().substring(0,10))+1);
		if(SQDAO.insertSQ(sb))
		{	
			if(sqa != null)
			{
				sqa.setSq_id(sq_id);
				sqa.setRelevance_type(0);
				SQDAO.insertSQAtta(sqa);
			}
			
			if("sms".equals(mb.getRemind_type()) || "email".equals(mb.getRemind_type()))//是否使用短信或mail发送
			{
				SQSMSFactory.sendSMSForAdd(sb,mb);
			}
			return sb;
		}else
			return null;
	}
	
	/**
	 * 插入信件扩展字段 用于前台
	 * @return SQCustom
	 * @return boolean
	 */
	public static boolean insertSQCursom(List<SQCustom> l)
	{
		return SQDAO.insertSQCursom(l);
	}
	
	/**
	 * 修改信件点击次数 
	 * @param int sq_id
	 * @return boolean
	 */
	public static boolean setSQClickCount(int sq_id)
	{
		Map<String,Object> m  = new HashMap<String,Object>();
		m.put("sq_id", sq_id);
		m.put("lasthit_dtime", DateUtil.getCurrentDateTime());
		return SQDAO.setSQClickCount(m);
	}
	
	/**
	 * 修改信件内容 
	 * @param SQBean sb
	 * @return String tag_ids
	 * @return SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSQ(SQBean sb,String tag_ids,SettingLogsBean stl)
	{
		if(SQDAO.updateSQ(sb, stl))
		{
			SQDAO.deleteSQTag(sb.getSq_id()+"");
			SQDAO.insertSQTag(sb.getSq_id()+"", tag_ids);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改信件扩展字段 
	 * @return SQCustom
	 * @return boolean
	 */
	public static boolean updateSQCustom(List<SQCustom> l)
	{
		return SQDAO.updateSQCustom(l);
	}
	
	/**
	 * 修改信件状态
	 * @param Map<String,String> m
	 * @return boolean
	 */
	public static boolean updateStatus(Map<String,String> m)
	{
		if(m.containsKey("accept_dtime"))
		{
			m.put("accept_dtime", DateUtil.getCurrentDateTime());
		}
		//如果Map里有over_dtime，给它加上值

		if(m.containsKey("over_dtime"))
		{
            String over_dtime = m.get("over_dtime");
            if(over_dtime != null && !"".equals(over_dtime))
            {
                m.put("over_dtime",over_dtime);
            }
            else{
                m.put("over_dtime", DateUtil.getCurrentDateTime());
            }
		}

		if(m.containsKey("publish_dtime"))
		{
			if("1".equals(m.get("publish_status")))
				m.put("publish_dtime", DateUtil.getCurrentDateTime());
			else
				m.put("publish_dtime", "");
		}
		if(SQDAO.updateStatus(m))
		{
			String model_id = m.get("model_id");
			
			ModelBean mb = ModelManager.getModelBean(Integer.parseInt(model_id));
			if("sms".equals(mb.getRemind_type()) || "email".equals(mb.getRemind_type()))//是否使用短信或mail发送
			{
				String pro_type = m.get("pro_type");//操作动作标识
				if("1".equals(pro_type) || "100".equals(pro_type))
				{
					//如果是回复或内容审核操作
					//如果信息状态为已办结,发送短信
					String sq_status = m.get("sq_status");
					if("3".equals(sq_status))
					{
						SQSMSFactory.sendSMSForResult(getSqBean(Integer.parseInt(m.get("sq_id"))),mb);
					}
				}
				if("13".equals(pro_type))
				{
					//督办操作
					//如果选择了督办,发送短信
					String supervise_flag = m.get("supervise_flag");//督办
					if("1".equals(supervise_flag))
					{
						SQSMSFactory.sendSMSForSupervise(getSqBean(Integer.parseInt(m.get("sq_id"))),mb);
					}
				}
				if("2".equals(pro_type) || "3".equals(pro_type) || "4".equals(pro_type))
				{
					//转交呈操作
					String prove_type = m.get("prove_type");//转交呈办理
					if("2".equals(prove_type) || "3".equals(prove_type) || "4".equals(prove_type))
					{
						SQSMSFactory.sendSMSForTrans(getSqBean(Integer.parseInt(m.get("sq_id"))),mb);
					}
				}
				if("102".equals(pro_type))
				{
					String publish_status = m.get("publish_status");//转交呈办理
					if("1".equals(publish_status))
					{
						SQSMSFactory.sendSMSForPublish(getSqBean(Integer.parseInt(m.get("sq_id"))),mb);
					}
				}
			}
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除信件
	 * @return String sq_ids
	 * @return SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteSQ(String sq_ids,SettingLogsBean stl)
	{
		return SQDAO.deleteSQ(sq_ids,stl);
	}
	
	/**
	 * 保存信件满意度
	 * @param String sq_id 诉求ID
	 * @param String sat_score_str　满意度分值
	 * @param String raty_score_str　用户满意度
	 * @return boolean
	 */
	public static boolean saveScore(String sq_id,String sat_score_str,String raty_score_str)
	{
		String[] temp_sat = sat_score_str.split(",");
		String[] temp_raty = raty_score_str.split(",");
		int sat_score = 0;
		for(int i=0;i<temp_sat.length;i++)
		{
			sat_score += Integer.parseInt(temp_sat[i]) * Integer.parseInt(temp_raty[i]) / 10;
		}
		Map<String,String> m = new HashMap<String,String>();
		m.put("sq_id", sq_id);		
		m.put("sat_score", sat_score+"");
		if(SQDAO.updateStatus(m))
		{
			SatisfactionManager.insertSatRecord(sq_id,temp_raty);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 得到重复件列表
	 * @param Map m
	 * @return List
	 */	
	public static List<SQBean> getReduplicateSQList(Map<String,String> m)
	{
		return SQDAO.getReduplicateSQList(m);
	}	
	
	/******************** 流程处理 开始 ******************************/
	/**
	 * 修改审核过程内容 
	 * @return Map m
	 * @return SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateProcessNote(Map<String,String> m,SettingLogsBean stl)
	{
		return SQDAO.updateProcessNote(m, stl);
	}
	
	public static List<ProcessBean> getProcessListBySqID(int sq_id)
	{
		return SQDAO.getProcessListBySqID(sq_id);
	}
	
	/**
	 * 插入流程信息
	 * @param ProcessBean pb
	 * @param SQAttachment sqa
	 * @return boolean
	 */
	public static boolean insertProcess(ProcessBean pb,SQAttachment sqa)
	{
		try{
			pb.setDept_name(CpDeptManager.getCpDeptBean(pb.getDo_dept()).getDept_name());
		}catch(Exception e)
		{
			e.printStackTrace();
			//System.out.println("cp_dept id "+pb.getDo_dept()+" is null");
		}
		int pro_id = PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_PROCESS_TABLE_NAME);
		pb.setPro_id(pro_id);	
		pb.setPro_dtime(DateUtil.getCurrentDateTime());
		if(SQDAO.insertProcess(pb))
		{
			if(sqa != null)
			{
				sqa.setSq_id(pro_id);
				sqa.setRelevance_type(1);
				SQDAO.insertSQAtta(sqa);
			}
			return true;
		}else
			return false;
	}
	
	/**
	 * 信件收回
	 * @param String sq_id
	 * @return String true|false|process_is_worked(信件已操作，不允许收回)
	 */
	public static String withdrawSQForProcess(String sq_id,int user_id)
	{
		int detp_id = CpUserManager.getSQDeptIDbyUserID(user_id);//登录人所能管理的部门
		ProcessBean pb = SQDAO.getLastProcessBySqID(sq_id);
		//首先判断该流程的处理人或部门是否为本流程的，如果是才可以进行回退操作
		if(pb.getUser_id() == user_id || pb.getDo_dept() == detp_id)
		{
			//第一步：修改原信件状态
			Map<String,String> m = new HashMap<String,String>();
			m.put("sq_id",sq_id);
			m.put("do_dept",pb.getOld_dept_id()+"");
			m.put("sq_status",pb.getOld_sq_status()+"");
			m.put("prove_type",pb.getOld_prove_type()+"");
			if(SQDAO.updateStatus(m))
			{
				//第二步：删除掉此步流程
				if(SQDAO.deleteProcessByProID(pb.getPro_id()))
				{
					return "true";
				}else
					return "false";
			}
			else
				return "false";
		}else
		{
			//如果不同，表示此信息已被其它部门自理，不能退回
			return "process_is_worked";
		}
	}
	/******************** 流程处理 结束 ******************************/
	
	/******************** 特征标识处理 开始 ******************************/	
	/**
	 * 根据条件得到特征列表
	 * @param int 
	 * @return List
	 */
	public static List<TagsBean> getSQTagList(int sq_id)
	{
		return SQDAO.getSQTagList(sq_id);
	}
	
	/**
	 * 插入特征词关联信息
	 * @param String sq_id 
	 * @param String tag_ids 
	 * @return List
	 */
	public static boolean insertSQTag(String sq_id,String tag_ids)
	{
		if(SQDAO.deleteSQTag(sq_id))
		{
			return SQDAO.insertSQTag(sq_id,tag_ids);
		}else
			return false;
	}
	
	/******************** 特征标识处理 结束 ******************************/
	
	/******************** 附件处理 开始 ******************************/
	/**
	 * 得到附件信息	 
	 * @param String sq_ids
	 * @param String relevance_type
	 * @return List
	 */
	public static List<SQAttachment> getSQAttachmentList(String sq_ids,String relevance_type)
	{
		return SQDAO.getSQAttachmentList(sq_ids, relevance_type);
	}
	
	/**
	 * 插入附件	 
	 * @param SQAttachment sqa
	 * @return bolean
	 */
	public static boolean insertSQAtta(SQAttachment sqa)
	{		
		return SQDAO.insertSQAtta(sqa);
	}
	
	/******************** 附件处理 结束 ******************************/
	
	/**
	 * 根据年份得到信件列表（用于导数据后批量修改流水号）
	 * @return 
	 * @return List
	 */
	public static boolean getAllSQListByYear()
	{
		for(int i=2000;i<2020;i++)
		{
			List<SQBean> l = SQDAO.getAllSQListByYear(i+"");
			if(l != null && l.size() > 0)
			{
				int num=1;
				Map<String,String> m = new HashMap<String,String>();
				//System.out.println(l.size());
				for(int j=0;j<l.size();j++)
				{
					m.put("sq_id", l.get(j).getSq_id()+"");
					m.put("sq_all_number", num+"");
					m.put("sq_number", (SQDAO.getSQNumber(l.get(j).getModel_id(),i+"")+1)+"");
					SQDAO.updateSQNumber(m);
					num++;
				}
			}
		}
		return true;
	}
	
	/**
	 * 按照办结状态统计部门的办结量,并排序
	 * @param String model_ids 业务ID，可为空
	 * @param int row_count
	 * @return List
	 */	
	public static List<CountBean> getSqFinishCountForDept(String model_ids,int row_count)
	{
		if(row_count == 0)
			row_count = 10;
		Map<String,String> m = new HashMap<String,String>();
		m.put("sq_status", "3");
		if(model_ids != null && !"".equals(model_ids))
			m.put("model_ids", model_ids);
		List<CountBean> l = SQDAO.getSqFinishCountForDept(m);	
		List<CountBean> count_l = new ArrayList<CountBean>();
		if(l != null && l.size() > 0)
		{
			if(l.size() < row_count) row_count = l.size();
			int i=1;
			for(CountBean cb : l)
			{
				try{
					if(i>row_count)
						break;
					cb.setDept_name(CpDeptManager.getCpDeptName(cb.getDept_id()));
					count_l.add(cb);
					i++;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return count_l;
	}
	
	/**
	 * 按照办结率对部门进行排行
	 * @param String model_ids 业务ID
	 * @param int row_count
	 * @return List
	 
	public static List<CountBean> getAppealFinishRate(Map<String,String> m,int row_count)
	{
		//第一步按业务对部门信件总数进行排序
		List<CountBean> l = SQDAO.getSqFinishCountForDept(m);
		List<CountBean> count_l = new ArrayList<CountBean>();
		if(l != null && l.size() > 0)
		{
			if(l.size() < row_count) row_count = l.size();
			
			int i=1;
			for(CountBean cb : l)
			{
				try{
					if(i>row_count)
						break;
					cb.setDept_name(CpDeptManager.getCpDeptName(cb.getDept_id()));
					//第二步：根据部门ID得到该部门的办结总数
					m.put("bj_con", "bj_con");
					m.put("do_dept", cb.getDept_id()+"");
					String bj_count = SQDAO.getSQStatistics(m);
					cb.setCountendp(CalculateNumber.getRate(bj_count,cb.getCount()+""));
					cb.setCountwei((Integer.parseInt(cb.getCount()) - Integer.parseInt(bj_count))+"");
					count_l.add(cb);
					i++;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return count_l;
	}
	*/
	public static List<CountBean> getAppealFinishRate(Map<String,String> m,int row_count)
	{
		//第一步按业务对部门信件总数进行排序
		List<CountBean> l = SQDAO.getSqFinishCountForDept(m);
		//得到已办结的部门信件总数列表
		m.put("sq_status", "3");
		List<CountBean> bj_list = SQDAO.getSqFinishCountForDept(m);		
		Map<Integer,CountBean> bj_map = new HashMap<Integer,CountBean>();
		if(bj_list != null && bj_list.size() > 0)
		{
			for(CountBean cb : bj_list)
				bj_map.put(cb.getDept_id(), cb);
		}
		
		if(l != null && l.size() > 0)
		{
			//先循环总数，再根据dept_id匹配找到对应的已办结数
			for(CountBean cb : l)
			{
				cb.setDept_name(CpDeptManager.getCpDeptName(cb.getDept_id()));
				if(bj_map.containsKey(cb.getDept_id()))
				{
					CountBean bj_cb = bj_map.get(cb.getDept_id());							
					cb.setF_temp_count(getRate(bj_cb.getCount(),cb.getCount()));//
					cb.setCountend(bj_cb.getCount());
					cb.setCountendp(cb.getF_temp_count()+"%");
					cb.setCountwei((Integer.parseInt(cb.getCount()) - Integer.parseInt(bj_cb.getCount()))+"");	
				}
			}
			Collections.sort(l,new AppealCountComparatorForFloat());//按办结率排序
			if(l.size() < row_count) 
				row_count = l.size();
			return l.subList(0, row_count);
		}
		else
			return null;
	}
	
	//按红黄牌统计排序
	public static List<CountBean> getAppealFinishRateForAlarm(Map<String,String> m,int row_count)
	{
		//第一步按业务对部门红黄牌信件总数进行排序
		List<CountBean> alarm_list = SQDAO.getSqFinishCountForDept(m);
		//得到按业务对部门信件总数进行排序
		m.remove("alarm_flag");
		List<CountBean> l = SQDAO.getSqFinishCountForDept(m);
		Map<Integer,CountBean> alarm_map = new HashMap<Integer,CountBean>();
		if(alarm_list != null && alarm_list.size() > 0)
		{
			for(CountBean cb : alarm_list)
				alarm_map.put(cb.getDept_id(), cb);
		}
		
		if(l != null && l.size() > 0)
		{
			//先循环总数，再根据dept_id匹配找到对应的红牌数
			for(CountBean cb : l)
			{
				cb.setDept_name(CpDeptManager.getCpDeptName(cb.getDept_id()));
				cb.setCountwei("0");
				cb.setCount_red("0");
				if(alarm_map.containsKey(cb.getDept_id()))
				{
					CountBean alarm_cb = alarm_map.get(cb.getDept_id());
					cb.setCount_red(alarm_cb.getCount());	
					cb.setCountwei(alarm_cb.getCount());//用于排序
				}		
				//System.out.println("cb.getCountwei()------------------"+cb.getCountwei());
			}
			Collections.sort(l,new AppealCountComparatorForDESC());//
			if(l.size() < row_count) 
				row_count = l.size();
			return l.subList(0, row_count);
		}
		else
			return null;
	}
	
	//办结率计算
	public static float getRate(String strUp, String strDown){
		try{
			float floatUp = Float.parseFloat(strUp);
			float floatDown = Float.parseFloat(strDown);
			int intResult = (int)(floatUp/floatDown*10000.0);
			
			return (float)intResult/100;
		}catch(Exception e){
			return 0;
		}
	}
	
	 /**
	 * 查询部门办结率并对未办结数进行正序，倒序排列
	 * @param String model_ids 业务ID
	 * @param int row_count
	 * @param String type 排序方式
	 * @return List
	 */
	public static List<CountBean> getAppealFinishRateForSort(List<CountBean> l,String sort_type,int row_count)
	{
		if("desc".equals(sort_type))
			Collections.sort(l,new AppealCountComparatorForDESC());
		else
			Collections.sort(l,new AppealCountComparatorForASC());
		
		List<CountBean> count_l = new ArrayList<CountBean>();
		if(l != null && l.size() > 0)
		{
			if(l.size() < row_count) row_count = l.size();
			
			int i=1;
			for(CountBean cb : l)
			{
				if(i>row_count)
					break;
				count_l.add(cb);
				i++;				
			}
		}
		return count_l;
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
	
	public static SQBean getSQSimpleBean(int sq_id)
	{
		return SQDAO.getSQSimpleBean(sq_id);
	}
	
	/**
	 * 根据条件统计满意度排行并排序
	 * @param String model_ids
	 * @param int row_count
	 * @return List
	 */
	public static List<CountBean> getSQSatisfaction(String model_id,int row_count)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("model_id", model_id);
		m.put("sat_score", "6");
		//第一步按业务对部门信件满意度进行排序
		List<CountBean> l = SQDAO.getSQSatisfaction(m);
		List<CountBean> count_l = new ArrayList<CountBean>();
		if(l != null && l.size() > 0)
		{
			if(l.size() < row_count) row_count = l.size();
			
			int i=1;
			for(CountBean cb : l)
			{
				try{
					if(i>row_count)
						break;
					cb.setDept_name(CpDeptManager.getCpDeptName(cb.getDept_id()));
					//第二步：根据部门ID得到该部门不满意的信件					
					m.put("do_dept", cb.getDept_id()+"");	
					cb.setCount_normal(SQDAO.getSQSatisfactionCount(m));
					count_l.add(cb);
					i++;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return count_l;
	}
	
	//按业务信件量进行排行
	 public static List<CountBean> getSQStatisticsForModel(Map<String,String> m,int row_count)
    {
		 List<CountBean> l = SQDAO.getSQStatisticsForModel(m);
		 if(l != null && l.size() > 0)
		 {
			 if(l.size() > row_count)
				 l = l.subList(0, row_count);
		 }
		 return l;
    }

	//根据部门id得到所关联的业务
	  public static String getModelIdByDept_id(String dept_id){
		 
		return  SQDAO.getModelIdByDept_id(dept_id);
	  }
	  
	 
	  
	  public static class AppealCountComparatorForASC implements Comparator<Object>{
			public int compare(Object o1, Object o2) {
			    
				CountBean cgb1 = (CountBean) o1;
				CountBean cgb2 = (CountBean) o2;
				if (Integer.parseInt(cgb1.getCountwei()) > Integer.parseInt(cgb2.getCountwei())) {
			     return 1;
			    } else {
			    	if (cgb1.getCountwei() == cgb2.getCountwei()) {
			      return 0;
			     } else {
			      return -1;
			     }
			    }
			}
		}
	  
	  public static class AppealCountComparatorForFloat implements Comparator<Object>{
			public int compare(Object o1, Object o2) {
			    
				CountBean cgb1 = (CountBean) o1;
				CountBean cgb2 = (CountBean) o2;
			    if (cgb2.getF_temp_count() > cgb1.getF_temp_count()) {
			     return 1;
			    } else {
			     if (cgb1.getF_temp_count() == cgb2.getF_temp_count()) {
			      return 0;
			     } else {
			      return -1;
			     }
			    }
			}
		}
	  
	  public static class AppealCountComparatorForDESC implements Comparator<Object>{
			public int compare(Object o1, Object o2) {
			    
				CountBean cgb1 = (CountBean) o1;
				CountBean cgb2 = (CountBean) o2;
			    if (Integer.parseInt(cgb2.getCountwei()) > Integer.parseInt(cgb1.getCountwei())) {
			     return 1;
			    } else {
			     if (cgb1.getCountwei() == cgb2.getCountwei()) {
			      return 0;
			     } else {
			      return -1;
			     }
			    }
			}
		}
	
	public static void main(String[] args)
	{		
		/*
		m.put("start_num", 0);	
		m.put("page_size", 15);
		m.put("sort_name", "sq_id");
		m.put("sort_type", "asc");*/
		List<CountBean> l = getSqFinishCountForDept("30",5);
		//System.out.println(l.get(0).getCount()+"  "+l.get(0).getDept_id()+"  "+l.get(0).getDept_name());
		
	}
}
