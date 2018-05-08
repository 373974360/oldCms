package com.deya.wcm.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.cicro.iresource.service.resourceService.dto.DataElementCollectionDTO;

   
public interface ICustomFormRMI  extends Remote{

	//添加自定义表单
	public boolean addCustomForm(DataElementCollectionDTO dataElementCollection)throws RemoteException;
	
	//修改自定义表单
	public boolean updateCustomForm(DataElementCollectionDTO dataElementCollection)throws RemoteException;
	
	//添加自定义表单
	public boolean addCustomForm(String info)throws RemoteException;
	
	//修改自定义表单
	public boolean updateCustomForm(String info)throws RemoteException;
	
	//删除自定义表
	public boolean deleteCustomForm(String ids,String enames)throws RemoteException;
	
	
	//添加自定义表信息
	public boolean addCustomInfo(String info)throws RemoteException;
	
	//修改自定义表信息
	public boolean updateCustomInfo(String info)throws RemoteException;
	
	//删除自定义表信息
	public boolean deleteCustomInfo(String ids)throws RemoteException;
	
	
	//在资源库中添加信息直接到 wcm 中的对应的数据模型中 
	public boolean insertForm(Object ob, String model_name,String from_id) throws RemoteException;
	
	//在资源库中修改信息直接到 wcm 中的对应的数据模型中 
	public boolean updateForm(Object ob, String model_name,String from_id) throws RemoteException;
	
	//在资源库中删除信息直接到 wcm 中的对应的数据模型中
	public boolean deleteForm(List<String> list,String site_id) throws RemoteException;
}
