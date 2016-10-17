package com.deya.project.priceMonitor;

import java.io.Serializable;

public class PriceCollectBean implements Serializable
{
	private static final long serialVersionUID = 1L;
    private int marketId = 0; //分类ID
	private int typeId = 0; //分类ID
    private int productId = 0;  //品种ID
    private int isSell = 0;     //零售/批发
    private String avgPrice = "";    //数据库查价
    private String dayAvgPrice = "";    //日均价
    private String dayRose = "";    //日环比
    private String weekAvgPrice = "";    //周均价
    private String weekRose = "";    //周环比
    private String monthAvgPrice = "";    //月均价
    private String monthRose = "";    //月环比
    private String chain = "";    //同比

    private String typeName = "";    //分类名称
    private String productName = "";    //品名

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

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

    public int getIsSell() {
        return isSell;
    }

    public void setIsSell(int isSell) {
        this.isSell = isSell;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getDayAvgPrice() {
        return dayAvgPrice;
    }

    public void setDayAvgPrice(String dayAvgPrice) {
        this.dayAvgPrice = dayAvgPrice;
    }

    public String getDayRose() {
        return dayRose;
    }

    public void setDayRose(String dayRose) {
        this.dayRose = dayRose;
    }

    public String getWeekAvgPrice() {
        return weekAvgPrice;
    }

    public void setWeekAvgPrice(String weekAvgPrice) {
        this.weekAvgPrice = weekAvgPrice;
    }

    public String getWeekRose() {
        return weekRose;
    }

    public void setWeekRose(String weekRose) {
        this.weekRose = weekRose;
    }

    public String getMonthAvgPrice() {
        return monthAvgPrice;
    }

    public void setMonthAvgPrice(String monthAvgPrice) {
        this.monthAvgPrice = monthAvgPrice;
    }

    public String getMonthRose() {
        return monthRose;
    }

    public void setMonthRose(String monthRose) {
        this.monthRose = monthRose;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
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
}