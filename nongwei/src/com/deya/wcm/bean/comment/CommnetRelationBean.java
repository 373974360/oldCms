package com.deya.wcm.bean.comment;

import java.io.Serializable;


public class CommnetRelationBean implements Serializable
{
	private int id;
	private int comment_id;
	private String from_list;
	private String from_list1;
	
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getComment_id()
	{
		return comment_id;
	}
	public void setComment_id(int comment_id)
	{
		this.comment_id = comment_id;
	}
	public String getFrom_list()
	{
		return from_list;
	}
	public void setFrom_list(String from_list)
	{
		this.from_list = from_list;
	}
	public String getFrom_list1()
	{
		return from_list1;
	}
	public void setFrom_list1(String from_list1)
	{
		this.from_list1 = from_list1;
	}
}
