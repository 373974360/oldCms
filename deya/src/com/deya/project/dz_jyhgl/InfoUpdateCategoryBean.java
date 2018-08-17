package com.deya.project.dz_jyhgl;

import java.io.Serializable;

public class InfoUpdateCategoryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id = 0;//主键ID
    private int cat_id=0;//栏目ID
    private int gz_id=0;//规则ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getGz_id() {
        return gz_id;
    }

    public void setGz_id(int gz_id) {
        this.gz_id = gz_id;
    }
}
