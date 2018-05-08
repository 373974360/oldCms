/*
 * @(#)SearchResult.java   1.0 2007-2-03
 */
package com.deya.wcm.bean.search;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>Title: 搜索结果包装类</p>
 * <p>Description: 搜索结果包装类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author lisp
 * @version 1.0
 */
public class SearchResult { 
	private String key = ""; //关键字
	private String time = "0.28";//得到搜索所用时间
	private long count = 0 ; //得到搜索结果数
    private PageControl pageControl; //分页信息  
    private List<ResultBean> items = new ArrayList<ResultBean>();//结果条目列表
    
    private String keyE = ""; //转码后的关键字
    
    public SearchResult() { 
    }
    
    /**
     * 往结果列表中增加一个条目
     * @param item SearchResultItem
     */
    public void addItem(ResultBean item) {
        items.add(item);
    }

    /**
     * 从结果列表中删除指定条目
     * @param item SearchResultItem
     */
    public void removeItem(ResultBean item) {
        items.remove(item);
    }

    /**
     * 加载给定列表中的所有条目
     * @param items Set
     */
    public void addAll(Set items) {
        items.addAll(items);
    }

    /**
     * 删除指定列表中的所有条目
     * @param items Set
     */
    public void removeAll(Set items) {
        items.removeAll(items);
    }

    /**
     * 判断结果列表中是否包含某一条目
     * @param item SearchResultItem
     * @return boolean
     */
    public boolean contains(ResultBean item) {
        return items.contains(item);
    }

    public List getItems() {
        return items;
    }

	public PageControl getPageControl() {
		return pageControl;
	}

	public void setPageControl(PageControl pageControl) {
		this.pageControl = pageControl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void setItems(List<ResultBean> items) {
		this.items = items;
	}

	public String getKeyE() {
		try {
			return java.net.URLEncoder.encode(key,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public void setKeyEncode(String keyE) {
		this.keyE = keyE;
	}

}
