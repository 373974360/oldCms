package com.deya.project.dz_recruit;
 
import java.io.Serializable;

public class UserInfoRelationBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private int user_id = 0;
    private int info_id = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }
}