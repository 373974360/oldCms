package com.deya.project.priceMonitor;

import java.io.Serializable;

public class TenCollectBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int typeId = 0;
    private int productId = 0;
    private String maxPrice = "";
    private String minPrice = "";
    private String avgPrice = "";
    private int isSell = 0;
    private String rose = "";
    private String chain = "";
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

    public String getMaxPrice()
    {
        return this.maxPrice;
    }

    public void setMaxPrice(String maxPrice)
    {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice()
    {
        return this.minPrice;
    }

    public void setMinPrice(String minPrice)
    {
        this.minPrice = minPrice;
    }

    public String getAvgPrice()
    {
        return this.avgPrice;
    }

    public void setAvgPrice(String avgPrice)
    {
        this.avgPrice = avgPrice;
    }

    public int getIsSell()
    {
        return this.isSell;
    }

    public void setIsSell(int isSell)
    {
        this.isSell = isSell;
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

    public String getRose()
    {
        return this.rose;
    }

    public void setRose(String rose)
    {
        this.rose = rose;
    }

    public String getChain()
    {
        return this.chain;
    }

    public void setChain(String chain)
    {
        this.chain = chain;
    }
}
