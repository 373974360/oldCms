package com.deya.wcm.services.zwgk.index;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.index.IndexRoleBean;
import com.deya.wcm.bean.zwgk.index.IndexSequenceBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.zwgk.index.IndexRoleDAO;
import com.deya.wcm.dao.zwgk.index.IndexSequenceDAO;
import com.deya.wcm.services.cms.category.CategoryManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

public class IndexManager implements ISyncCatch{

	// 信息有效常量
	private final static int VALID_NUM = 1;
	// 序列初始值常量
	private final static int SEQ_START_NUM = 1;
	// 公开节点常量
	private final static String GK_NODE_NUM = "100";
	// Sequence类型--节点类型常量
	private	final static int NOD_NUM = 0;
	// Sequence类型--类目类型常量
	private final static int CAT_NUM = 1;
	// 索引号规则缓存
	private static Map<String, IndexRoleBean> role_mp = new TreeMap<String, IndexRoleBean>();
	
	/**
	 * 初始化加载缓存信息
	 */	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		role_mp.clear();
		List<IndexRoleBean> lt = IndexRoleDAO.getAllIndexRole();
		for(int i=0; i<lt.size(); i++)
		{
			IndexRoleBean irb = lt.get(i);
			role_mp.put(irb.getIr_id(), irb);
		}
	}
	
	/**
	 * 刷新索引码规则缓存信息
	 */
	public static void reLoadRoleMap()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.zwgk.index.IndexManager");
	}

	/**
	 * 取得全部索引生成规则
	 * @return
	 */
	public static List<IndexRoleBean> getRoleList()
	{
		List<IndexRoleBean> lt = new ArrayList<IndexRoleBean>(role_mp.values());
		return lt;
	}
	
	/**
	 * 更新索引规则
	 * @param lt	索引规则列表
	 * @param stl
	 * @return
	 */
	public static boolean updateIndexRole(List<IndexRoleBean> lt, SettingLogsBean stl)
	{
		boolean flg = true;
		for(int i=0; i<lt.size(); i++)
		{
			flg = IndexRoleDAO.updateIndexRole(lt.get(i), stl)? flg : false;
		}
		reLoadRoleMap();
		return flg;
	}
	
	/** 
	 * 生成索引号的方法,索引生成失败返回NULL
	 * @param nodeId 节点ID
	 * @param seqYmd	时间
	 * @param catId	目录ID
	 * @param siteId	站点ID
	 * @param seq	手动设置的序列号，为空时自动设置
	 * @param stl
	 * @return
	 */
	public static Map<String, String> getIndex(String nodeId, String catId, String seqYmd, String seq)
	{
		IndexSequenceBean isb = new IndexSequenceBean();
		isb.setSeq_ymd(seqYmd);
		isb.setSite_id(nodeId);
		//System.out.println("isb----------" + isb);
		return getIndex(nodeId, catId, isb, seq);
	}
	
	/**
	 * 生成索引号的方法,索引生成失败返回NULL
	 * @param nodeId	节点ID
	 * @param isb	流水号生产对象
	 * @param catId	类目ID
	 * @param seq 手动设置的序列号，为空时自动设置
	 * @param stl
	 * @return
	 */
	public static Map<String, String> getIndex(String nodeId, String catId, IndexSequenceBean isb, String seq)
	{
		Map<String, String> retMp = new HashMap<String, String>();
		String ret = "";
		// 流水号生成规则对象 key=F
		IndexRoleBean roleBean = role_mp.get("F");
		// 信息编制年份格式 key=D
		//IndexRoleBean yearRoleBean = role_mp.get("D");
		
		// 添加前段码  key=A
		IndexRoleBean irb = role_mp.get("A");
		//System.out.println(role_mp);
		//System.out.println(VALID_NUM);
		//System.out.println(irb.getIs_valid());
		if(irb.getIs_valid() == VALID_NUM)
		{
			ret += irb.getIr_value() + irb.getIr_space();
		}
		// 添加区域及部门编码 key=B
		irb = role_mp.get("B");
		if(irb.getIs_valid() == VALID_NUM)
		{
			GKNodeBean gkNodeBean = GKNodeManager.getGKNodeBeanByNodeID(nodeId);
			ret += gkNodeBean.getDept_code() + irb.getIr_space();
		}
		// 添加信息分类号 key=C
		irb = role_mp.get("C");
		if(irb.getIs_valid() == VALID_NUM)
		{
			CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(catId), nodeId);
			if(cb == null)
				return retMp;
				
			String[] t_oisition = cb.getCat_position().substring(1,cb.getCat_position().length()-1).split("\\$");
			if("3".equals(irb.getIr_value()))
			{
				String code = cb.getCat_code();
				isb.setSeq_type(CAT_NUM);
				isb.setSeq_item(code);
				ret += code;
			}
			else
			{
				if(t_oisition.length == (Integer.parseInt(irb.getIr_value())+1) || t_oisition.length < (Integer.parseInt(irb.getIr_value())+1))
				{
					String code = cb.getCat_code();
					isb.setSeq_type(CAT_NUM);
					isb.setSeq_item(code);
					ret += code;
				}else
				{	
					String code = CategoryManager.getCategoryBeanCatID(Integer.parseInt(t_oisition[Integer.parseInt(irb.getIr_value())+1]), nodeId).getCat_code();
					isb.setSeq_type(CAT_NUM);
					isb.setSeq_item(code);
					ret += code;
				}
				
			}
			ret += irb.getIr_space();
		}
		
		// 添加编制年份
		irb = role_mp.get("D");
		String date = getSeqYMD(isb.getSeq_ymd(), irb.getIr_value());
		isb.setSeq_ymd(date);
		retMp.put("gk_year", date);
		if(irb.getIs_valid() == VALID_NUM)
		{
			ret += date +irb.getIr_space();
		}
		int sequence = 1;
		// 信息流水号取得失败时返回null
		if("".equals(seq) || seq == null )
		{
			try{
				sequence = getSerialNum(irb.getIr_value(), isb);
			}catch(Exception e)
			{
				return null;
			}
		}
		else
		{
			sequence = Integer.valueOf(seq);
		}
		// 取得信息流水号位数信息 key=E
		IndexRoleBean digitBean = role_mp.get("E");
		int digit = Integer.valueOf(digitBean.getIr_value());
		// 字符串补齐位数后拼接
		ret += FormatUtil.intToString(sequence, digit, "0");
				
		retMp.put("gk_num", sequence+"");
		retMp.put("gk_index", ret);
		//System.out.println("流水号：-----------------" + ret);
		return retMp;
	}
	
	/**
	 * 新建流水号生产信息
	 * @param isb 流水号生产信息对象
	 * @param stl
	 * @return
	 */
	private static boolean insertSeqence(IndexSequenceBean isb)
	{
		if(IndexSequenceDAO.insertSequence(isb))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改流水号生产信息
	 * 这个方法主要是让信息中的Sequence字段每次自增1.
	 * @param isb
	 * @param stl
	 * @return
	 */
	private static boolean updateSequence(IndexSequenceBean isb)
	{
		return IndexSequenceDAO.updateSequence(isb );
//		更新完成后如果不再需要其他操作，则下面的代码就刻意删除
//		if(IndexSequenceDAO.updateSequence(isb, stl))
//		{
//			//TODO other task;
//			return true;
//		}
//		else
//		{
//			return false;
//		}
	}
	
	/**
	 * 重置索引号生成的流水号信息
	 * @param nodeId	节点ID
	 * @param catId	类目ID
	 * @param ymd	年份	
	 * @param stl
	 * @return
	 */
	public static boolean resetSequence(String nodeId, String catId, String ymd, SettingLogsBean stl)
	{
		// 流水号生成规则
		IndexRoleBean roleBean = role_mp.get("F");
		// 信息编制年份规则
		IndexRoleBean irb = role_mp.get("D");
		
		IndexSequenceBean isb = new IndexSequenceBean();
		isb.setSeq_ymd(ymd);
		isb.setSite_id(nodeId);
		
		if(roleBean.getIr_value()== GK_NODE_NUM)
		{
			isb.setSeq_type(NOD_NUM);
			isb.setSeq_item(nodeId);
		}
		// 否则拼接类目ID
		else
		{
			isb.setSeq_type(CAT_NUM);
			isb.setSeq_item(catId);
		}
		
		String date = getSeqYMD(isb.getSeq_ymd(),irb.getIr_value());
		isb.setSeq_ymd(date);
		
		return IndexSequenceDAO.resetSequence(isb, stl);
	}
	
	/**
	 * 取得格式化的日期
	 * @param date	字符串型的时间 (格式必须为yyyy-MM-dd的形式)
	 * @param pattern 字符串格式
	 * @return
	 */
	private static String getSeqYMD(String date, String pattern)
	{
		String ret = "";
		DateFormat df = new SimpleDateFormat(pattern);
		if("".equals(date) || date == null)
		{
			ret = df.format(new Date());
		}
		else
		{
			DateFormat strToDate = new SimpleDateFormat("yyyy-MM");
			Date d;
			try {
				d = strToDate.parse(date);	
			} catch (ParseException e) {
				d = new Date();
			}
			ret = df.format(d);
		}
		return ret;
	}
	
	/**
	 * 取得流水号生产信息
	 * @param isb
	 * @return
	 */
	private static IndexSequenceBean getSequenceBean(IndexSequenceBean isb)
	{
		return IndexSequenceDAO.getSequenceBean(isb);
	}
	
	/**
	 * 流水号取得     根据条件检索不到的流水号，则新建一条信息
	 * @throws Exception 新建流水号失败时返回抛出
	 */
	private static int getSerialNum(String format, IndexSequenceBean isb) throws Exception
	{
		IndexSequenceBean resultBean = getSequenceBean(isb);
		if(resultBean == null)
		{
			isb.setSeq_cur_value(SEQ_START_NUM);
			
			// 新建流水号失败时抛出一个异常
			if(! insertSeqence(isb))
			{
				throw new Exception("新建流水号失败！");
			}
			resultBean = isb;
			return resultBean.getSeq_cur_value();
		}
		else
		{
			// 流水号自动更新，进行自增操作
			int result = resultBean.getSeq_cur_value() + 1;
			resultBean.setSeq_cur_value(result);
			updateSequence(resultBean);
			return result;
		}
	}
	
	/**
	 * @param s
	 */
	/**
	 * @param s
	 */
	public static void main(String[] s)
	{
//		IndexSequenceBean isb = new IndexSequenceBean();
//		isb.setApp_id("qqeee");
//		isb.setSeq_ymd("2011");
//		isb.setSite_id("cms");
//		isb.setSeq_item("www");
//		String ss = getIndex("GKasdg", isb, "catID", new SettingLogsBean());
//		System.out.println(ss);
		
		
		//String strdata = getSeqYMD("1123-12-11", "yyyyMM");
		
		//System.out.println(getIndex("GKrmzf","1835","2011-01",""));
		//System.out.println("eee");
//		System.out.println(p.values().iterator().next());
	}
}