package com.deya.wcm.cmsiresource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cicro.iresource.service.resourceService.dto.TreeDTO;


public class IResourceRMIRPC {

	//得到根节点下面的栏目信息统计
	public static List<IresourceInfoCount> getRootChildTreeCount(String id){
		
		List<IresourceInfoCount> list = new ArrayList<IresourceInfoCount>();
		List<TreeDTO> TreeDTOList = IResourceRMIService.findTreeByPIdAndType(id);
		for(TreeDTO treeDTO : TreeDTOList ){
			Map<String,String> mapInfo = IResourceRMIService.totalByGroupTreeId(treeDTO.getId());
			//System.out.println("mapInfo === " + mapInfo);
			Iterator it = mapInfo.keySet().iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				String value = mapInfo.get(key);
				String treeid = key.split("#")[0];
				String name = key.split("#")[1];
				IresourceInfoCount iresourceInfoCount = new IresourceInfoCount();
				iresourceInfoCount.setName(name);
				iresourceInfoCount.setTreeId(treeid);
				iresourceInfoCount.setCount(Integer.valueOf(value));
				list.add(iresourceInfoCount);
			}
		}
		return list;
		
	}
	
	//通过节点id 得到该节点下面的栏目信息统计
	public static List<IresourceInfoCount> getRootChildTreeCountById(String id){
		
		    List<IresourceInfoCount> list = new ArrayList<IresourceInfoCount>();
			Map<String,String> mapInfo = IResourceRMIService.totalByGroupTreeId(id);
			//System.out.println("mapInfo === " + mapInfo);
			Iterator it = mapInfo.keySet().iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				String value = mapInfo.get(key);
				String treeid = key.split("#")[0];
				String name = key.split("#")[1];
				IresourceInfoCount iresourceInfoCount = new IresourceInfoCount();
				iresourceInfoCount.setName(name);
				iresourceInfoCount.setTreeId(treeid);
				iresourceInfoCount.setCount(Integer.valueOf(value));
				list.add(iresourceInfoCount);
			}
		return list;
		
	}
	
}
