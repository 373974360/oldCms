package com.deya.wcm.rmi.conf;
 
import java.io.Serializable;

public class RmiServerConfBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String ip = "";
	private String port = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}