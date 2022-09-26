package hai704iTP1client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import hai704iTP1common.Hello;

public class Client {
	
	public Client() {
		
	}
	
	public static void main(String[] args) {
		String host = (args.length < 1)? null : args[0];
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			Hello stub = (Hello) registry.lookup("Hello");
			String response = stub.sayHello();
			System.out.println("Server response: " + response);
			stub.printHello();
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
}
