package com.deya.wcm.dao.interview;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.deya.wcm.bean.interview.*;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 访谈参与者数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈参与者的数据处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectActorDAO {	
	/**
     * 得到参与者列表总数
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	public static String getActorListALLCount(Map m)
	{		
		return DBManager.getString("getActorListALLCount", m);		
	}
	
	/**
     * 得到所有参与者的名称
     * @param String sub_id
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getAllActorName(String sub_id)
	{		
		List l = DBManager.queryFList("getAllActorName", sub_id);
		return l;
	}
	
	/**
     * 得到参与者列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getActorList(Map m)
	{		
		List l = DBManager.queryFList("getActorList", m);
		return l;
	}
	
	/**
     * 得到参与者列表所有内容
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getActorListALL(Map m)
	{		
		List l = DBManager.queryFList("getActorListALL", m);
		return l;
	}
	
	/**
     * 得到参与者对象
     * @param int id 参与者对象id
     * @return SubjectActor　
     * */
	public static SubjectActor getSubActor(int id)
	{
		return (SubjectActor)DBManager.queryFObj("getSubActor", id);
	}
	
	/**
     * 得到参与者对象
     * @param String id 参与者对象id
     * @return SubjectActor　
     * */
	public static SubjectActor getSubActor(String id)
	{
		return (SubjectActor)DBManager.queryFObj("getSubActorByUUID", id);
	}
	
	/**
     * 添加参与者
     * @param SubjectActor sa 参与者对象
     * @param SettingLogsBean stl
     * @return List　列表
     * */
	public static boolean insertSubActor(SubjectActor sa,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.INTERVIEW_ACTOR_TABLE_NAME);
		sa.setId(id);
		if(DBManager.insert("cp_subActor_insert", sa))
		{
			PublicTableDAO.insertSettingLogs("添加","访谈参与者",id+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改参与者
     * @param SubjectActor sa 参与者对象
     * @return List　列表
     * */
	public static boolean updateSubActor(SubjectActor sa,SettingLogsBean stl)
	{
		if(DBManager.update("cp_subActor_update", sa))
		{
			PublicTableDAO.insertSettingLogs("修改","访谈参与者",sa.getId()+"",stl);
			return true;
		}else
			return false;		
	}
	

	/**
     * 删除参与者
     * @param Map m
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static boolean deleteSubActor(Map m,SettingLogsBean stl)
	{
		if(DBManager.update("cp_subActor_delete", m))
		{
			PublicTableDAO.insertSettingLogs("删除","访谈参与者",m.get("ids")+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 保存参与者排序
     * @param Map m
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static boolean saveSubActorSort(Map m,SettingLogsBean stl)
	{
		String ids = (String)m.get("ids");
		if(ids != null && !"".equals(ids))
		{
			Map<String,String> new_m = new HashMap<String,String>();
			String[] tempA = ids.split(",");
			try{
				for(int i=0;i<tempA.length;i++)
				{
					new_m.put("sort", (i+1)+"");
					new_m.put("id", tempA[i]);
					DBManager.update("cp_subActor_sort", new_m);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;				
			}
			PublicTableDAO.insertSettingLogs("保存排序","访谈参与者",m.get("ids")+"",stl);
		}
		return true;
		
	}
	
	public static void main(String args[])
	{
		SubjectActor s = new SubjectActor();
		s.setId(8);
		s.setActor_id("111");
		s.setSub_id("222");
		s.setActor_name("3333");
		s.setSex("ggg");
		System.out.println(getAllActorName("28f0916c-0b49-4ee5-b8e2-22bb1c72fa26"));
		//System.out.println("互动界面2_2.gif".replaceAll(".*\\.(.*?)","$1"));
	}
}
