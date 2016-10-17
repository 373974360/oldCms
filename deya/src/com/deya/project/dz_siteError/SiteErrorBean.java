package com.deya.project.dz_siteError;
 
import java.io.Serializable;

public class SiteErrorBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private int typeId = 0;
	private int siteId = 0;
	private String submitSiteName = "";
	private String submitUser = "";
	private String submitUserPhone = "";
	private String submitUserEmail = "";
	private String submitDescription = "";
    private String errorTitle = "";
    private String errorCatName = "";
	private String errorUrl = "";
    private String errorDescription = "";
    private String addTime = "";
    private String handleTime = "";
    private String status = "";

    private String typeName = "";
    private String siteName = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public String getSubmitUserPhone() {
        return submitUserPhone;
    }

    public void setSubmitUserPhone(String submitUserPhone) {
        this.submitUserPhone = submitUserPhone;
    }

    public String getSubmitUserEmail() {
        return submitUserEmail;
    }

    public void setSubmitUserEmail(String submitUserEmail) {
        this.submitUserEmail = submitUserEmail;
    }

    public String getSubmitDescription() {
        return submitDescription;
    }

    public void setSubmitDescription(String submitDescription) {
        this.submitDescription = submitDescription;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getErrorCatName() {
        return errorCatName;
    }

    public void setErrorCatName(String errorCatName) {
        this.errorCatName = errorCatName;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSubmitSiteName() {
        return submitSiteName;
    }

    public void setSubmitSiteName(String submitSiteName) {
        this.submitSiteName = submitSiteName;
    }
}