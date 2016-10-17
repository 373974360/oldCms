package com.deya.wcm.catchs;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.deya.wcm.rmi.ISyncCatchRmi;

public class ISyncCatchImpl extends UnicastRemoteObject implements ISyncCatchRmi{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ISyncCatchImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void reloadCateh(String class_name)
	{		
		ISyncCatch ic;
		try {
			ic = (ISyncCatch) Class.forName(class_name).newInstance();
			ic.reloadCatch();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
