package exo2.serveur;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HotelWebService2 {
	
	@WebMethod
	void reserver(int agencyid, String fromstring, String tostring, int nbpersonnes, int ofrid, String nom, String prenom, String numerocarte, int moisexpiration, int anneeexpiration, int codesecu) throws Exception;

}
