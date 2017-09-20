package com.deya.project.priceMonitor;

import java.io.Serializable;

public class ProductTypeBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id = 0;
    private String marketId = "0";
    private String typeName = "";
    private String comments = "";
    private String addTime = "";
    private String status = "";
    private String marketName = "";

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTypeName()
    {
        return this.typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
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

    public String getComments()
    {
        return this.comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String getMarketId()
    {
        return this.marketId;
    }

    public void setMarketId(String marketId)
    {
        this.marketId = marketId;
    }

    public String getMarketName()
    {
        return this.marketName;
    }

    public void setMarketName(String marketName)
    {
        this.marketName = marketName;
    }
}
