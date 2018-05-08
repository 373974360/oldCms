package com.deya.wcm.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISyncCatchRmi extends Remote{
	public void reloadCateh(String class_name)throws RemoteException;
}
