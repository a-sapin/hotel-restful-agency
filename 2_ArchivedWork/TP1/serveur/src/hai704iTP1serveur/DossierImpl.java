package hai704iTP1serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import hai704iTP1common.Dossier;

public class DossierImpl extends UnicastRemoteObject implements Dossier {
	
	private String dossier;
	
	public DossierImpl() throws RemoteException {
		this.dossier = "";
	}
	
	public String getDossier() throws RemoteException {
		return this.dossier;
	}
	
	public void setDossier(String dossier) throws RemoteException {
		this.dossier = dossier;
	}
}
