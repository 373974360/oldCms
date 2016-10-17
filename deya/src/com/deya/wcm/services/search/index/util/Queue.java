package com.deya.wcm.services.search.index.util;

import java.util.LinkedList;

public   class   Queue   { 
	
    private static  LinkedList   list   =   new   LinkedList(); 
    
    public  void   put(Object   v){   
    	list.addFirst(v);  
    } 
    
    public synchronized Object get(){   
        return   list.removeLast();   
    } 
    
    
    public synchronized boolean isEmpty(){   
        return   list.isEmpty();   
    } 
    
    public synchronized boolean isContains(Object o){   
    	if(list.contains(o)){
    		return true;
    	}
        return false;   
    }
    
    public synchronized boolean remove(Object o){   
    	if(list.contains(o)){
    		list.remove(o);
    		return true;
    	}
        return false;   
    }
    
    public static void main(String[] args){ 
        Queue queue = new Queue(); 
        for(int i = 0; i<10;i++){
            queue.put(Integer.toString(i)); 
        }
        
        int i=0;
        while(!queue.isEmpty()) {
        	i++;
            System.out.println(queue.get()); 
            if(i==5){
            	queue.put(Integer.toString(11)); 
            }
        }
        System.out.println("##############################"); 
        while(!queue.isEmpty()) {
            System.out.println(queue.get()); 
            	//queue.put(Integer.toString(11)); 
        }
    } 
} 