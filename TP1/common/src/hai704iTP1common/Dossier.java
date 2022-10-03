package hai704iTP1common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Dossier extends Remote {
	String getDossier() throws RemoteException;
	void setDossier(String dossier) throws RemoteException;
}
