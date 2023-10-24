package hai704iTP1common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Animal extends Remote {
	String getFullName() throws RemoteException;
	String getSpeciesAnimal() throws RemoteException;
	String getRace() throws RemoteException;
	String getDossierAnimal() throws RemoteException;
	void setDossierAnimal(String dossier) throws RemoteException;
}
