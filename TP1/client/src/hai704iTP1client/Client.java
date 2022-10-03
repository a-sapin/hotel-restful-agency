package hai704iTP1client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import hai704iTP1common.Animal;

public class Client {
	
	public Client() {
		
	}
	
	public static void main(String[] args) {
		String host = (args.length < 1)? null : args[0];
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			Animal stub = (Animal) registry.lookup("Animal");
			System.out.println("Server response: " + stub.getFullName());
			System.out.println("Server response: " + stub.getSpeciesAnimal());
			System.out.println("Server response: " + stub.getRace());
			System.out.println("Server response: " + stub.getDossierAnimal());
			stub.setDossierAnimal("A un rhume");
			System.out.println("Server response: " + stub.getDossierAnimal());
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
}
