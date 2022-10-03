package hai704iTP1serveur;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import hai704iTP1common.Animal;
import hai704iTP1common.Species;

public class Server {

	public Server() {
		
	}
	
	public static void main(String[] args) {
		try {
			Animal obj = new AnimalImpl("El Canado", "Lorenzo Puccio", new Species("Canard", 8), "Colvert");
			obj.setDossierAnimal("En parfaite santé");
			Registry registry = LocateRegistry.createRegistry(1099);
			if  (registry == null) {
				System.err.println("Registry not found");
			}
			else {
				registry.bind("Animal", obj);
				//System.setSecurityManager(new SecurityManager());
				System.setProperty("java.security.policy", "security.policy");
				System.out.println("Server is ready");
			}
			
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
