package com.deya.wcm.services.browserapi;

import java.util.Comparator;

import com.deya.wcm.bean.cms.count.SiteCountBean;

//按数据量排序 -- 降序排序
public class SiteCountComparator  implements Comparator {
	
	public SiteCountComparator(){
		super();
	}
	
	public int compare(Object o1, Object o2) {    
    	
		SiteCountBean c1 = (SiteCountBean) o1;    
		SiteCountBean c2 = (SiteCountBean) o2;  
    	
    		if (Integer.valueOf(c2.getReleasedCount()) > Integer.valueOf(c1.getReleasedCount())) {    
                return 1;    
            } else {    
                if (Integer.valueOf(c2.getReleasedCount()) == Integer.valueOf(c1.getReleasedCount())) {    
                    return 0;    
                } else {     
                    return -1;    
                }    
            } 
    }   
}
