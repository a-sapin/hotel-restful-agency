package exo2.serveur;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HotelWebService1 {
	
	@WebMethod
	Hotel getHotel();
	
	@WebMethod
	void setHotel(Hotel h);
	
	@WebMethod
	int connexionAgence(String id, String mdp);
	
	@WebMethod
	ArrayList<Offre> rechercheParPartenaire(LocalDate from, LocalDate to, int nbpersonnes);
}
