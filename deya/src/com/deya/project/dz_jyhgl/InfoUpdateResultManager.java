package com.deya.project.dz_jyhgl;

public class InfoUpdateResultManager {

    public static boolean insertInfoUpdateResult(InfoUpdateResultBean bean){
        return InfoUpdateResultDAO.insertInfoUpdateResult(bean);
    }
    public static boolean clearInfoUpdateResult(){
        return InfoUpdateResultDAO.clearInfoUpdateResult();
    }
}
