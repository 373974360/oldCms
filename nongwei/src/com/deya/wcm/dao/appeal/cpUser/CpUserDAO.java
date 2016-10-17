/**
 * 注册用户类
 */
package com.deya.wcm.dao.appeal.cpUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.cpUser.CpUserBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
 
/**
 * @author 王磊
 *
 */
public class CpUserDAO {
	/**
	 * 得到所有用户
	 * @param m
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<CpUserBean> getAllCpUserList(){
		return DBManager.queryFList("getALLCpUserList","");
	}
	
	/**
	 * 新建注册用户
	 * @param user
	 * @return
	 */
	public static boolean insertCpUser(int dept_id,String user_ids,SettingLogsBean stl){		
			if(user_ids != null && !"".equals(user_ids))
			{
				try{
					String[] tempA = user_ids.split(",");
					CpUserBean cub = new CpUserBean();
					for(int i=0;i<tempA.length;i++)
					{
						cub.setDept_id(dept_id);
						cub.setUser_id(Integer.parseInt(tempA[i]));
						DBManager.insert("insert_cp_user", cub);
					}
					PublicTableDAO.insertSettingLogs("添加","诉求系统部门与用户关联",dept_id+"",stl);
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
			return true;
	}
	
	/**
	 * 删除注册用户
	 * @param userIds
	 * @return
	 */
	public static boolean deleteCpUser(String dept_id,String userIds,SettingLogsBean stl){
		Map<String,String> m = new HashMap<String,String>();
		if(userIds != null && !"".equals(userIds))
		m.put("userIds", userIds);
		m.put("dept_id", dept_id);
		if(DBManager.delete("delete_cp_user", m))
		{	
			PublicTableDAO.insertSettingLogs("删除","诉求系统部门与用户关联",dept_id+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 修改注册用户
	 * @param user
	 * @return
	 */
	public static boolean updateCpUser(CpUserBean user){
		return DBManager.update("update_cp_dept", user);
	}
	
	/**
	 * 取得指定ID用户
	 * @param user_id
	 * @return
	 */
	public static CpUserBean getCpUserBean(int user_id){
		return (CpUserBean)DBManager.queryFObj("getCpUserBean",user_id+"");
	}
	
	
	/**
	 * 得到所有注册用户的总数
	 * @param m
	 * @return
	 */
	public static String getCpUserCount(Map<String,String> m){
		return DBManager.getString("getCpUserCount", m);
	}
	
	
}
