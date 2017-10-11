package com.deya.project.priceMonitor;

import java.io.Serializable;

public class PriceCollectBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int marketId = 0;
    private int typeId = 0;
    private int productId = 0;
    private int isSell = 0;
    private String avgPrice = "";
    private String dayAvgPrice = "";
    private String dayRose = "";
    private String weekAvgPrice = "";
    private String weekRose = "";
    private String monthAvgPrice = "";
    private String monthRose = "";
    private String chain = "";
    private String typeName = "";
    private String productName = "";
    private String unit = "";

    public int getMarketId()
    {
        return this.marketId;
    }

    public void setMarketId(int marketId)
    {
        this.marketId = marketId;
    }

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

    public int getIsSell()
    {
        return this.isSell;
    }

    public void setIsSell(int isSell)
    {
        this.isSell = isSell;
    }

    public String getAvgPrice()
    {
        return this.avgPrice;
    }

    public void setAvgPrice(String avgPrice)
    {
        this.avgPrice = avgPrice;
    }

    public String getDayAvgPrice()
    {
        return this.dayAvgPrice;
    }

    public void setDayAvgPrice(String dayAvgPrice)
    {
        this.dayAvgPrice = dayAvgPrice;
    }

    public String getDayRose()
    {
        return this.dayRose;
    }

    public void setDayRose(String dayRose)
    {
        this.dayRose = dayRose;
    }

    public String getWeekAvgPrice()
    {
        return this.weekAvgPrice;
    }

    public void setWeekAvgPrice(String weekAvgPrice)
    {
        this.weekAvgPrice = weekAvgPrice;
    }

    public String getWeekRose()
    {
        return this.weekRose;
    }

    public void setWeekRose(String weekRose)
    {
        this.weekRose = weekRose;
    }

    public String getMonthAvgPrice()
    {
        return this.monthAvgPrice;
    }

    public void setMonthAvgPrice(String monthAvgPrice)
    {
        this.monthAvgPrice = monthAvgPrice;
    }

    public String getMonthRose()
    {
        return this.monthRose;
    }

    public void setMonthRose(String monthRose)
    {
        this.monthRose = monthRose;
    }

    public String getChain()
    {
        return this.chain;
    }

    public void setChain(String chain)
    {
        this.chain = chain;
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

    public String getUnit()
    {
        return this.unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }
}
