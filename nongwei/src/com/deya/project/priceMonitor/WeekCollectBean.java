package com.deya.project.priceMonitor;

import java.io.Serializable;

public class WeekCollectBean implements Serializable
{
	private static final long serialVersionUID = 1L;
    private int productId = 0;  //品种ID
	private String lastPrice = "";     //上期价格
    private String thisPrice = "";    //本期价格
    private String rose = "";    //环比

    private String productName = "";    //品名

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

    public String getThisPrice() {
        return thisPrice;
    }

    public void setThisPrice(String thisPrice) {
        this.thisPrice = thisPrice;
    }

    public String getRose() {
        return rose;
    }

    public void setRose(String rose) {
        this.rose = rose;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}