package com.deya.project.dz_recruit;
 
import java.io.Serializable;

public class UserInfoBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String tokenId;         //微信token
    private String name;            //姓名
    private String age;             //年龄
    private String gender;          //性别
    private String csny;            //出生年月
    private String sfzh;            //身份证号
    private String phone;           //手机
    private String email;           //电子邮箱
    private String mz;              //民族
    private String zzmm;            //政治面貌
    private String hyzk;            //婚姻状况
    private String hj;              //户籍
    private String xl;              //学历
    private String byyx;            //毕业院校
    private String sxzy;            //所学专业
    private String bysj;            //毕业时间
    private String gzjy;            //工作经验
    private String zwpj;            //自我评价
    private String bz;              //备注
    private String updateTime;
	private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCsny() {
        return csny;
    }

    public void setCsny(String csny) {
        this.csny = csny;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getHyzk() {
        return hyzk;
    }

    public void setHyzk(String hyzk) {
        this.hyzk = hyzk;
    }

    public String getHj() {
        return hj;
    }

    public void setHj(String hj) {
        this.hj = hj;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getByyx() {
        return byyx;
    }

    public void setByyx(String byyx) {
        this.byyx = byyx;
    }

    public String getSxzy() {
        return sxzy;
    }

    public void setSxzy(String sxzy) {
        this.sxzy = sxzy;
    }

    public String getBysj() {
        return bysj;
    }

    public void setBysj(String bysj) {
        this.bysj = bysj;
    }

    public String getGzjy() {
        return gzjy;
    }

    public void setGzjy(String gzjy) {
        this.gzjy = gzjy;
    }

    public String getZwpj() {
        return zwpj;
    }

    public void setZwpj(String zwpj) {
        this.zwpj = zwpj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}