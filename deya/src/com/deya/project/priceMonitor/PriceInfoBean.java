package com.deya.project.priceMonitor;

import java.io.Serializable;

public class PriceInfoBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id = 0;
    private int marketId = 0;
    private int typeId = 0;
    private int productId = 0;
    private float price = 0.0F;
    private String unit = "";
    private int isSell = 0;
    private String location = "";
    private String productLevel = "";
    private String landings = "";
    private String tradings = "";
    private String comments = "";
    private String sourceFrom = "";
    private int addUser = 0;
    private String addTime = "";
    private String status = "";
    private String marketName = "";
    private String typeName = "";
    private String productName = "";
    private String username = "";
    private String scName = "";

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

    public int getProductId()
    {
        return this.productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getMarketName()
    {
        return this.marketName;
    }

    public void setMarketName(String marketName)
    {
        this.marketName = marketName;
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

    public float getPrice()
    {
        return this.price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public String getLocation()
    {
        return this.location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLandings()
    {
        return this.landings;
    }

    public void setLandings(String landings)
    {
        this.landings = landings;
    }

    public String getTradings()
    {
        return this.tradings;
    }

    public void setTradings(String tradings)
    {
        this.tradings = tradings;
    }

    public String getComments()
    {
        return this.comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public int getAddUser()
    {
        return this.addUser;
    }

    public void setAddUser(int addUser)
    {
        this.addUser = addUser;
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

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getProductLevel()
    {
        return this.productLevel;
    }

    public void setProductLevel(String productLevel)
    {
        this.productLevel = productLevel;
    }

    public String getSourceFrom()
    {
        return this.sourceFrom;
    }

    public void setSourceFrom(String sourceFrom)
    {
        this.sourceFrom = sourceFrom;
    }

    public String getUnit()
    {
        return this.unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public int getIsSell()
    {
        return this.isSell;
    }

    public void setIsSell(int isSell)
    {
        this.isSell = isSell;
    }

    public String getScName()
    {
        return this.scName;
    }

    public void setScName(String scName)
    {
        this.scName = scName;
    }
}
