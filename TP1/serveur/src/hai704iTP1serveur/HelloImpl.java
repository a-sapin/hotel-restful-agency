package hai704iTP1serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import hai704iTP1common.Hello;

public class HelloImpl extends UnicastRemoteObject implements Hello {
	
	public HelloImpl() throws RemoteException {
		
	}
	
	@Override
	public String sayHello() throws RemoteException {
		return "Hello World";
	}
	
	@Override
	public void printHello() throws RemoteException {
		System.out.println("Server says Hello World");
	}
}
