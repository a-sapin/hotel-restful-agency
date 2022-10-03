package hai704iTP1serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import hai704iTP1common.Animal;
import hai704iTP1common.Dossier;
import hai704iTP1common.Species;

public class AnimalImpl extends UnicastRemoteObject implements Animal{
	private String name;
	private String ownername;
	private Species species;
	private String race;
	private Dossier file;
	
	public AnimalImpl(String name, String ownername, Species species, String race) throws RemoteException {
		this.name = name;
		this.ownername = ownername;
		this.species = species;
		this.race = race;
		this.file = new DossierImpl();
	}
	
	public String getFullName() throws RemoteException {
		return "L'animal "+name+" appartient à "+ownername;
	}
	
	public String getSpeciesAnimal() throws RemoteException {
		return species.toString();
	}
	
	public String getRace() throws RemoteException {
		return "L'animal est de la race "+race;
	}
	
	public String getDossierAnimal() throws RemoteException {
		return file.getDossier();
	}
	
	public void setDossierAnimal(String dossier) throws RemoteException {
		file.setDossier(dossier);
	}
}
