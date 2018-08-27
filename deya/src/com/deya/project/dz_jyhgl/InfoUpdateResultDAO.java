package com.deya.project.dz_jyhgl;

import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.List;
import java.util.Map;

public class InfoUpdateResultDAO {

    public static List<InfoUpdateResultBean> getInfoUpdateResultList(Map<String, String> map){
        return DBManager.queryFList("getInfoUpdateResultList",map);
    }

    public static boolean insertInfoUpdateResult(InfoUpdateResultBean bean){
        try {
            bean.setId(PublicTableDAO.getIDByTableName("cs_info_update_result"));
            DBManager.insert("insertInfoUpdateResult",bean);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clearInfoUpdateResult(){
        try {
            DBManager.delete("clearInfoUpdateResult",null);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
