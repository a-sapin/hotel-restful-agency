package exo2.serveur;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HotelWebService2 {
	
	@WebMethod
	int connexionAgence(String login, String mdp) throws Exception;
	
	@WebMethod
	void reserver(ArrayList<Offre> offres, int ofrid, Client cl) throws Exception;

}
