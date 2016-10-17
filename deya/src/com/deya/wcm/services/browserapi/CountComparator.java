package com.deya.wcm.services.browserapi;

import java.util.Comparator;

import com.deya.wcm.bean.appeal.count.CountBean;

//按数据量排序 -- 降序排序
public class CountComparator  implements Comparator {
	
	public int compare(Object o1, Object o2) {    
    	
		CountBean c1 = (CountBean) o1;    
		CountBean c2 = (CountBean) o2;  
    	
    		if (Double.valueOf(c2.getCountendp().replaceAll("%", "")) > Double.valueOf(c1.getCountendp().replaceAll("%", ""))) {    
                return 1;    
            } else {    
                if (Double.valueOf(c2.getCountendp().replaceAll("%", "")) == Double.valueOf(c1.getCountendp().replaceAll("%", ""))) {    
                    return 0;    
                } else {     
                    return -1;    
                }    
            } 
           
    }   
}
