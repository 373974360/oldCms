package com.deya.wcm.services.appeal.sq;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.appeal.sq.ProcessBean;
import com.deya.wcm.bean.appeal.sq.SQAttachment;
import com.deya.wcm.bean.appeal.sq.SQBean;
import com.deya.wcm.bean.appeal.sq.SQCustom;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.bean.system.assist.TagsBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.appeal.count.CountUtil;
import com.deya.wcm.services.appeal.myddc.SqMyddcBean;
import com.deya.wcm.services.appeal.myddc.SqMyddcManager;
import com.deya.wcm.services.org.user.UserLogin;

public class SQRPC {
	public static SQBean getSQSimpleBean(int sq_id)
	{
		return SQManager.getSQSimpleBean(sq_id);
	}
	
	/**
	 * 得到允许的附件大小
	 * @param 
	 * @return int
	 */
	public static long getAppealFileSize()
	{
		return SQManager.getAppealFileSize();
	}
	
	/**
	 * 根据会员ID得到该会员所提交的信件列表（前台使用）
	 * @return 
	 * @return List
	 */	
	public static List<SQBean> getBroSQListByMemberID(String me_id)
	{
		return SQManager.getBroSQListByMemberID(me_id);
	}
	
	/**
	 * 根据登录人员ID得到它是否是信件管理员
	 * @param int user_id
	 * @return boolean
	 */
	public static boolean isAppealManager(int user_id)
	{
		return SQManager.isAppealManager(user_id);
	}
	
	/**
	 * 根据条件得到信件总数
	 * @param Map m
	 * @return String
	 */
	public static String getSqCount(Map<String,String> m,HttpServletRequest request)
	{
		return SQManager.getSqCount(m,UserLogin.getUserBySession(request).getUser_id());
	}
	
	/**
	 * 得到已办理的信件总数
	 * @param Map m
	 * @return String
	 */
	public static String getTransactSQCount(Map<String,String> m,HttpServletRequest request)
	{
		return SQManager.getTransactSQCount(m,UserLogin.getUserBySession(request).getUser_id());
	}
	
	/**
	 * 根据条件得到信件列表
	 * @param Map m
	 * @return List
	 */
	public static List<SQBean> getSqList(Map<String,String> m,HttpServletRequest request)
	{
		return SQManager.getSqList(m,UserLogin.getUserBySession(request).getUser_id());
	}
	
	/**
	 * 得到已办理的信件列表
	 * @param Map m
	 * @return List
	 */
	public static List<SQBean> getTransactSQList(Map<String,String> m,HttpServletRequest request)
	{
		return SQManager.getTransactSQList(m,UserLogin.getUserBySession(request).getUser_id());
	}
	
	/**
	 * 根据条件得到信件信息
	 * @param int sq_id
	 * @return SQBean
	 */
	public static SQBean getSqBean(int sq_id)
	{
		return SQManager.getSqBean(sq_id);
	}
	
	/**
	 * 根据条件得到信件扩展字段列表
	 * @return int sq_id
	 * @return List
	 */	
	public static List<SQCustom> getSQCustomList(int sq_id)
	{
		return SQManager.getSQCustomList(sq_id);
	}
	
	/**
     * 根据业务ID得到随机查询码，用于前台页面添加诉求时产生查询码
     * @param 
     * @return List
     * */
	public static String getQueryCode(int model)
	{		
		return SQManager.getQueryCode(model);
	}
	
	/**
	 * 信件收回
	 * @param String sq_id
	 * @return String true|false|process_is_worked(信件已操作，不允许收回)
	 */
	public static String withdrawSQForProcess(String sq_id,int user_id)
	{
		return SQManager.withdrawSQForProcess(sq_id, user_id);
	}

	/**
	 * 修改信件点击次数 
	 * @param int sq_id
	 * @return boolean
	 */
	public static boolean setSQClickCount(int sq_id)
	{
		return SQManager.setSQClickCount(sq_id);
	}
	
	/**
	 * 修改信件内容 
	 * @param SQBean sb
	 * @param String tag_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSQ(SQBean sb,String tag_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SQManager.updateSQ(sb, tag_ids, stl);
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
		return SQManager.updateSQCustom(l);
	}
	
	/**
	 * 修改信件状态
	 * @param Map<String,String> m
	 * @return boolean
	 */
	public static boolean updateStatus(Map<String,String> m,HttpServletRequest request)
	{
		if("".equals(m.get("do_dept")))
		{
			LoginUserBean lub = UserLogin.getUserBySession(request);
			m.put("do_dept","");//根据登录人找到所在部门
		}
		return SQManager.updateStatus(m);
	}
	
	/**
	 * 删除信件
	 * @return String sq_ids
	 * @return SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteSQ(String sq_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SQManager.deleteSQ(sq_ids, stl);
		}else
			return false;	
	}
	
	/**
	 * 得到重复件列表
	 * @param Map m
	 * @return List
	 */	
	public static List<SQBean> getReduplicateSQList(Map<String,String> m)
	{
		return SQManager.getReduplicateSQList(m);
	}
	
	/**
	 * 得到重复件列表
	 * @param 执行超期计算
	 * @return 
	 
	public static boolean getALlNotEndSqList()
	{
		return SQTaskHandl.getALlNotEndSqList();
	}
	*/
	/******************** 流程处理 开始 ******************************/
	/**
	 * 修改审核过程内容 
	 * @return Map m
	 * @return SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateProcessNote(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SQManager.updateProcessNote(m, stl);
		}else
			return false;
	}
	
	public static List<ProcessBean> getProcessListBySqID(int sq_id)
	{
		return SQManager.getProcessListBySqID(sq_id);
	}
	
	/**
	 * 插入流程信息
	 * @param ProcessBean pb
	 * @param SQAttachment sqa
	 * @return boolean
	 */
	public static boolean insertProcess(ProcessBean pb,SQAttachment sqa,HttpServletRequest request)
	{
		LoginUserBean lub = UserLogin.getUserBySession(request);
		pb.setUser_id(lub.getUser_id());
		pb.setUser_realname(lub.getUser_realname());		
		return SQManager.insertProcess(pb,sqa);
	}
	/******************** 流程处理 结束 ******************************/
	
	/**
	 * 根据条件得到特征列表
	 * @param int 
	 * @return List
	 */
	public static List<TagsBean> getSQTagList(int sq_id)
	{
		return SQManager.getSQTagList(sq_id);
	}
	
	/**
	 * 得到附件信息	 
	 * @param String sq_ids
	 * @param String relevance_type
	 * @return List
	 */
	public static List<SQAttachment> getSQAttachmentList(String sq_ids,String relevance_type)
	{
		return SQManager.getSQAttachmentList(sq_ids, relevance_type);
	}

	/**
	 * 得到附件信息
	 * @param String sq_ids
	 * @param String relevance_type
	 * @return List
	 */
	public static List<SqMyddcBean> getSqMyddcList(Map<String,String> m)
	{
		List<SqMyddcBean> sqMyddcList = SqMyddcManager.getSqMyddcList(m);
		return sqMyddcList;
	}

	/**
	 * 根据选择的行导出Excel
	 * */
	public static String getSqExcelList(Map<String,String> m,HttpServletRequest request){
		List<SQBean> list = SQManager.getSqList(m,UserLogin.getUserBySession(request).getUser_id());
		//删除今天以前的文件夹
		String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
		String path = FormatUtil.formatPath(root_path + "/appeal/sq/");
		CountUtil.deleteFile(path);

		//创建今天的文件夹和xls文件
		String nowDate = CountUtil.getNowDayDate();
		String fileTemp2 = FormatUtil.formatPath(path+ File.separator+nowDate);
		File file2 = new File(fileTemp2);
		if(!file2.exists()){
			file2.mkdir();
		}
		String nowTime = CountUtil.getNowDayDateTime();
		String xls = nowTime + CountUtil.getEnglish(1)+".xls";
		String xlsFile = fileTemp2+File.separator+xls;
		String urlFile = "/sys/appeal/sq/"+nowDate+File.separator+xls;

		String[] head = {"信件编号","留言标题","办理单位","留言内容","来信日期","姓名","电话","邮箱","回复内容"};
		String[][] data = new String[list.size()][9];
		for(int i=0;i<list.size();i++){
			SQBean sqBean = list.get(i);
			data[i][0] = sqBean.getSq_code();//编号
			data[i][1] = sqBean.getSq_title2();//标题
			data[i][2] = sqBean.getDo_dept_name();//办理部门
			data[i][3] = delHtmlTag(sqBean.getSq_content());//来信内容
			data[i][4] = sqBean.getSq_dtime();//来信日期
			data[i][5] = sqBean.getSq_realname();//姓名
			data[i][6] = sqBean.getSq_phone();//电话
			data[i][7] = sqBean.getSq_email();//邮箱
			data[i][8] = delHtmlTag(sqBean.getSq_reply());//回复内容
		}

		OutExcel oe=new OutExcel("来信列表");
		oe.doOut(xlsFile,head,data);
		return urlFile;
	}

	public static String delHtmlTag(String str){
		String newstr = "";
		newstr = str.replaceAll("<[.[^>]]*>","");
		newstr = newstr.replaceAll(" ", "");
		return newstr;
	}
}
