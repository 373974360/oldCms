package com.deya.project.priceMonitor;

import java.io.Serializable;

public class WeekCollectBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int productId = 0;
    private String lastPrice = "";
    private String thisPrice = "";
    private String rose = "";
    private String productName = "";

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

    public String getThisPrice()
    {
        return this.thisPrice;
    }

    public void setThisPrice(String thisPrice)
    {
        this.thisPrice = thisPrice;
    }

    public String getRose()
    {
        return this.rose;
    }

    public void setRose(String rose)
    {
        this.rose = rose;
    }

    public String getProductName()
    {
        return this.productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }
}
