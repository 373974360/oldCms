package com.deya.wcm.dao.org.dept;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.dept.DeptBean;
import com.deya.wcm.bean.org.dept.DeptLevelBean;
import com.deya.wcm.dao.org.rmi.GetOrgRmi;

public class DeptDAORMIImpl implements IDeptDAO {

    /**
     * 得到所有部门列表
     *
     * @param
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List getAllDeptList() {
        try {
            return GetOrgRmi.getorgRmi().getAllDeptList();
        } catch (RemoteException re) {
            re.printStackTrace();
            return null;
        }
    }

    /**
     * 得到部门ID,用于添加部门时给出ID值
     *
     * @param
     * @return List
     */
    public int getDeptID() {
        try {
            return GetOrgRmi.getorgRmi().getDeptID();
        } catch (RemoteException re) {
            re.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据部门ID及查询条件从库中得到下级部门列表对象deep+1,参数有 dept_id,con_name,con_value
     *
     * @param Map con_m
     * @return List
     */
    public List<DeptBean> getChildDeptListForDB(Map<String, String> con_m) {
        try {
            return GetOrgRmi.getorgRmi().getChildDeptListForDB(con_m);
        } catch (RemoteException re) {
            re.printStackTrace();
            return null;
        }
    }

    /**
     * 根据ID返回部门对象
     *
     * @param String id
     * @return DeptBean
     */
    public DeptBean getDeptBeanByID(String id) {
        try {
            return GetOrgRmi.getorgRmi().getDeptBeanByID(id);
        } catch (RemoteException re) {
            re.printStackTrace();
            return null;
        }
    }

    /**
     * 插入部门信息
     *
     * @param DeptBean db
     * @return boolean
     */
    public boolean insertDept(DeptBean db, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().insertDept(db, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

    /**
     * 修改部门信息
     *
     * @param DeptBean db
     * @return boolean
     */
    public boolean updateDept(DeptBean db, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().updateDept(db, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

    /**
     * 移动部门
     *
     * @param Map<String,String> m
     * @param SettingLogsBean    stl
     * @return boolean
     */
    public boolean moveDept(Map<String, String> m, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().moveDept(m, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

    /**
     * 删除部门信息
     *
     * @param String 　ids
     * @return boolean
     */
    public boolean deleteDept(String ids, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().deleteDept(ids, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

    /**
     * 保存部门排序
     *
     * @param String ids 部门ID
     * @return
     */
    public boolean saveDeptSort(String ids, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().saveDeptSort(ids, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

	/* **********************部门管理员管理　开始******************************** */

    /**
     * 得到部门管理员列表
     *
     * @param
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List getAllDeptManagerList() {
        try {
            return GetOrgRmi.getorgRmi().getAllDeptManagerList();
        } catch (RemoteException re) {
            re.printStackTrace();
            return null;
        }
    }

    /**
     * 添加部门管理员
     *
     * @param String user_ids 所选为管理员的人员ID
     * @param int    dept_id 部门ID
     * @return boolean
     */
    public boolean insertDetpManager(String user_ids, String dept_id, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().insertDetpManager(user_ids, dept_id, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

    /**
     * 修改部门管理员
     *
     * @param String user_ids 所选为管理员的人员ID
     * @param int    dept_id 部门ID
     * @return boolean
     */
    public boolean updateDetpManager(String user_ids, String dept_id, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().updateDetpManager(user_ids, dept_id, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

    /**
     * 删除部门管理员
     *
     * @param int dept_ids 部门ID
     * @return boolean
     */
    public boolean deleteDeptManager(String user_ids, String dept_ids, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().deleteDeptManager(user_ids, dept_ids, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }
	
	/* **********************部门管理员管理　结束******************************** */
	
	/* **********************部门级别管理　开始******************************** */

    /**
     * 得到所有部门级别列表
     *
     * @param
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List getAllDeptLevelList() {
        try {
            return GetOrgRmi.getorgRmi().getAllDeptLevelList();
        } catch (RemoteException re) {
            re.printStackTrace();
            return null;
        }
    }

    /**
     * 根据ID返回部门级别对象
     *
     * @param String id
     * @return DeptBean
     */
    public DeptLevelBean getDeptLevelBeanByID(String id) {
        try {
            return GetOrgRmi.getorgRmi().getDeptLevelBeanByID(id);
        } catch (RemoteException re) {
            re.printStackTrace();
            return null;
        }
    }

    /**
     * 插入部门级别信息
     *
     * @param DeptLevelBean dlb
     * @return boolean
     */
    public boolean insertDeptLevel(DeptLevelBean dlb, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().insertDeptLevel(dlb, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

    /**
     * 修改部门级别信息
     *
     * @param DeptLevelBean dlb
     * @return boolean
     */
    public boolean updateDeptLevel(DeptLevelBean dlb, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().updateDeptLevel(dlb, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

    /**
     * 删除部门级别信息
     *
     * @param String ids
     * @return boolean
     */
    public boolean deleteDeptLevel(String ids, SettingLogsBean stl) {
        try {
            return GetOrgRmi.getorgRmi().deleteDeptLevel(ids, stl);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean inserSynctDept(List<DeptBean> deptList) {
        try {
            return GetOrgRmi.getorgRmi().inserSynctDept(deptList);
        } catch (RemoteException re) {
            re.printStackTrace();
            return false;
        }
    }
	/* **********************部门级别管理　结束******************************** */


}
