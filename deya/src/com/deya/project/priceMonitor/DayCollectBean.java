package com.deya.project.priceMonitor;

import java.io.Serializable;

public class DayCollectBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int typeId = 0;
    private int productId = 0;
    private String lastPrice = "";
    private String todayPrice = "";
    private String lastSellPrice = "";
    private String todaySellPrice = "";
    private String todayRose = "";
    private String todaySellRose = "";
    private String comments = "";
    private String typeName = "";
    private String productName = "";

    public int getTypeId()
    {
        return this.typeId;
    }

    public void setTypeId(int typeId)
    {
        this.typeId = typeId;
    }

    public int getProductId()
    {
        return this.productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getLastPrice()
    {
        return this.lastPrice;
    }

    public void setLastPrice(String lastPrice)
    {
        this.lastPrice = lastPrice;
    }

    public String getTodayPrice()
    {
        return this.todayPrice;
    }

    public void setTodayPrice(String todayPrice)
    {
        this.todayPrice = todayPrice;
    }

    public String getLastSellPrice()
    {
        return this.lastSellPrice;
    }

    public void setLastSellPrice(String lastSellPrice)
    {
        this.lastSellPrice = lastSellPrice;
    }

    public String getTodaySellPrice()
    {
        return this.todaySellPrice;
    }

    public void setTodaySellPrice(String todaySellPrice)
    {
        this.todaySellPrice = todaySellPrice;
    }

    public String getComments()
    {
        return this.comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String getTypeName()
    {
        return this.typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getProductName()
    {
        return this.productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getTodayRose()
    {
        return this.todayRose;
    }

    public void setTodayRose(String todayRose)
    {
        this.todayRose = todayRose;
    }

    public String getTodaySellRose()
    {
        return this.todaySellRose;
    }

    public void setTodaySellRose(String todaySellRose)
    {
        this.todaySellRose = todaySellRose;
    }
}
