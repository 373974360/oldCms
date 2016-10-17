package com.deya.project.priceMonitor;

import java.io.Serializable;

public class DayCollectBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int typeId = 0; //分类ID
    private int productId = 0;  //品种ID
	private String lastPrice = "";     //昨天批发价格
    private String todayPrice = "";      //今天批发价格
    private String lastSellPrice = "";      //昨天零售价格
    private String todaySellPrice = "";     //今天零售价格
    private String todayRose = "";
    private String todaySellRose = "";
	private String comments = "";   //备注

    private String typeName ="";    //分类名称
    private String productName = "";    //品名

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

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(String todayPrice) {
        this.todayPrice = todayPrice;
    }

    public String getLastSellPrice() {
        return lastSellPrice;
    }

    public void setLastSellPrice(String lastSellPrice) {
        this.lastSellPrice = lastSellPrice;
    }

    public String getTodaySellPrice() {
        return todaySellPrice;
    }

    public void setTodaySellPrice(String todaySellPrice) {
        this.todaySellPrice = todaySellPrice;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTodayRose() {
        return todayRose;
    }

    public void setTodayRose(String todayRose) {
        this.todayRose = todayRose;
    }

    public String getTodaySellRose() {
        return todaySellRose;
    }

    public void setTodaySellRose(String todaySellRose) {
        this.todaySellRose = todaySellRose;
    }
}