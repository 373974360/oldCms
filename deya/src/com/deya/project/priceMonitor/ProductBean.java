package com.deya.project.priceMonitor;

import java.io.Serializable;

public class ProductBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id = 0;
    private int marketId = 0;
    private int typeId = 0;
    private String productName = "";
    private String addTime = "";
    private String comments = "";
    private String status = "";
    private String typeName = "";
    private String marketName = "";

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

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

    public String getProductName()
    {
        return this.productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getAddTime()
    {
        return this.addTime;
    }

    public void setAddTime(String addTime)
    {
        this.addTime = addTime;
    }

    public String getStatus()
    {
        return this.status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getTypeName()
    {
        return this.typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getMarketName()
    {
        return this.marketName;
    }

    public void setMarketName(String marketName)
    {
        this.marketName = marketName;
    }

    public String getComments()
    {
        return this.comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }
}
