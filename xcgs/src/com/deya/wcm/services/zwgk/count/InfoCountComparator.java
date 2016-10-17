package com.deya.wcm.services.zwgk.count;

import java.util.Comparator;

import com.deya.wcm.bean.zwgk.count.GKCountBean;

//按数据量排序 -- 降序排序
public class InfoCountComparator  implements Comparator {
	
	public int compare(Object o1, Object o2) {    
    	
		GKCountBean c1 = (GKCountBean) o1;    
		GKCountBean c2 = (GKCountBean) o2;  
    	
    		if (Integer.valueOf(c2.getInfo_count()) > Integer.valueOf(c1.getInfo_count())) {    
                return 1;    
            } else {    
                if (Integer.valueOf(c2.getInfo_count()) == Integer.valueOf(c1.getInfo_count())) {    
                    return 0;    
                } else {     
                    return -1;    
                }    
            } 
           
    }   
}
