package com.deya.wcm.bean.org.operate;

import java.util.List;

public class MenuBean implements java.io.Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5559805340842906501L;
	private int menu_id;	
	private int parent_id;		
	private String menu_name = "";
	private String menu_url = "";
	private String handls = "";//要触发的方法事件名	
	private int opt_id;
	private String opt_name = "";	
	private String menu_level = "";
	private String menu_position = "";
	private int menu_sort = 999;
	private String menu_memo = "";
	private List<MenuBean> child_menu_list;
	
	public MenuBean clone(){
		MenuBean o = null;
        try{
            o = (MenuBean)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
	}
	
	public String getOpt_name() {
		return opt_name;
	}
	public void setOpt_name(String optName) {
		opt_name = optName;
	}
	public String getHandls() {
		return handls;
	}
	public void setHandls(String handls) {
		this.handls = handls;
	}
	public List<MenuBean> getChild_menu_list() {
		return child_menu_list;
	}
	public void setChild_menu_list(List<MenuBean> child_menu_list) {
		this.child_menu_list = child_menu_list;
	}
	
	public String getMenu_level() {
		return menu_level;
	}
	public void setMenu_level(String menuLevel) {
		menu_level = menuLevel;
	}
	public String getMenu_memo() {
		return menu_memo;
	}
	public void setMenu_memo(String menu_memo) {
		this.menu_memo = menu_memo;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_position() {
		return menu_position;
	}
	public void setMenu_position(String menu_position) {
		this.menu_position = menu_position;
	}
	
	public int getMenu_sort() {
		return menu_sort;
	}
	public void setMenu_sort(int menu_sort) {
		this.menu_sort = menu_sort;
	}
	public int getOpt_id() {
		return opt_id;
	}
	public void setOpt_id(int opt_id) {
		this.opt_id = opt_id;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}	
}
