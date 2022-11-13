package exo2.serveur;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HotelWebService1 {
	
	@WebMethod
	String getHotelNom();
	
	@WebMethod
	String getHotelAdresse();
	
	@WebMethod
	ArrayList<Agence> getHotelPartenaires();
	
	@WebMethod
	ArrayList<String> getHotelPartenaireNoms();
	
	@WebMethod
	ArrayList<String> getHotelPartenaireLogins();
	
	@WebMethod
	ArrayList<String> getHotelPartenaireMdps();
	
	@WebMethod
	Agence getHotelPartenaireById(int i);
	
	@WebMethod
	String getPartenaireNomById(int i);
	
	@WebMethod
	int connexionAgenceWS1(String id, String mdp) throws Exception;
	
	@WebMethod
	int getOffreIdByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception;
	
	@WebMethod
	String getOffreChambreToStringByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception;
	
	@WebMethod
	String getOffreArriveeToStringByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception;
	
	@WebMethod
	String getOffreDepartToStringByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception;
	
	@WebMethod
	double getOffrePrixByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception;
	
	@WebMethod
	ArrayList<Offre> rechercheParPartenaire(int agencyid, String fromstring, String tostring, int nbpersonnes) throws Exception;
}
