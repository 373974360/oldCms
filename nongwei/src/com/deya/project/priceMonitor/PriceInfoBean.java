package com.deya.project.priceMonitor;

import java.io.Serializable;

public class PriceInfoBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
    private int marketId = 0;
	private int typeId = 0; //分类ID
    private int productId = 0;  //品种ID
	private float price = 0;    //价格
    private String unit = "";   //单位
    private int isSell = 0;     //批发or零售

	private String location = "";   //产地
    private String productLevel = "";      //等级
	private String landings = "";   //上市量
	private String tradings = "";   //交易量
	private String comments = "";   //备注
    private String sourceFrom = ""; //信息来源
    private int addUser = 0;        //添加用户
    private String addTime = "";    //添加时间
    private String status = "";     //状态

    private String marketName ="";    //市场名称
    private String typeName ="";    //分类名称
    private String productName = "";    //品名
    private String username = "";
    private String scName = "";     //市场名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLandings() {
        return landings;
    }

    public void setLandings(String landings) {
        this.landings = landings;
    }

    public String getTradings() {
        return tradings;
    }

    public void setTradings(String tradings) {
        this.tradings = tradings;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getAddUser() {
        return addUser;
    }

    public void setAddUser(int addUser) {
        this.addUser = addUser;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(String productLevel) {
        this.productLevel = productLevel;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getIsSell() {
        return isSell;
    }

    public void setIsSell(int isSell) {
        this.isSell = isSell;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }
}