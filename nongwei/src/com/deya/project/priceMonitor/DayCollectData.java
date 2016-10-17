package com.deya.project.priceMonitor;

import java.io.Serializable;

public class DayCollectData implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int typeId = 0; //分类ID
    private int productId = 0;  //品种ID
	private float price = 0;    //当天平均价格
    private String collectDate = "";   //统计日期
    private int isSell = 0;     //是否零售

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public int getIsSell() {
        return isSell;
    }

    public void setIsSell(int isSell) {
        this.isSell = isSell;
    }
}