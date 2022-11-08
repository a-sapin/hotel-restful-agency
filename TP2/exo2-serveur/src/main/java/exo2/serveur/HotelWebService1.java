package exo2.serveur;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HotelWebService1 {
	
	@WebMethod
	int connexionAgence(String id, String mdp) throws Exception;
	
	@WebMethod
	ArrayList<Offre> rechercheParPartenaire(String login, String mdp, LocalDate from, LocalDate to, int nbpersonnes) throws Exception;
}
