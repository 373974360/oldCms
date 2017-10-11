package com.deya.project.priceMonitor;

import java.io.Serializable;

public class DayCollectData
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int typeId = 0;
    private int productId = 0;
    private float price = 0.0F;
    private String collectDate = "";
    private int isSell = 0;

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

    public float getPrice()
    {
        return this.price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public String getCollectDate()
    {
        return this.collectDate;
    }

    public void setCollectDate(String collectDate)
    {
        this.collectDate = collectDate;
    }

    public int getIsSell()
    {
        return this.isSell;
    }

    public void setIsSell(int isSell)
    {
        this.isSell = isSell;
    }
}
