package hai704iTP1serveur;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import hai704iTP1common.Hello;

//import hai704iTP1serveur.HelloImpl;

public class Server {

	public Server() {
		
	}
	
	public static void main(String[] args) {
		try {
			Hello obj = new HelloImpl();
			Registry registry = LocateRegistry.createRegistry(1099);
			if  (registry == null) {
				System.err.println("Registry not found");
			}
			else {
				registry.bind("Hello", obj);
				System.out.println("Server is ready");
			}
			
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
