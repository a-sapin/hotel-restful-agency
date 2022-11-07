package exo2.serveur;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HotelWebService2 {
	
	@WebMethod
	boolean connexionAgence(Agence a, String login, String mdp);
	
	@WebMethod
	void reserver(Offre ofr, Client cl) throws Exception;

}
