package com.deya.wcm.webServices.sendInfo;

public interface ISendInfo {
	public String getReceiveConfigForJSON();
	public String getReceiveConfigForXML();
	public boolean sendInfo(String xml,String user);
	public boolean recordAdoptStatus(String xml);
	
	public boolean sendInfoFormThirdParty(String xml,String username,String password);
}