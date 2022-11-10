package exo2.serveur;

import java.util.ArrayList;

import javax.jws.WebService;

@WebService(endpointInterface="exo2.serveur.HotelWebService2")
public class HotelWebService2Impl implements HotelWebService2 {
	
	private Hotel h;

	public HotelWebService2Impl(Hotel h) {
		this.h = h;
	}

	@Override
	public int connexionAgence(String login, String mdp) throws Exception {
		ArrayList<Agence> agences = h.getPartenaires();
		for (Agence a : agences) {
			if(a.getLogin().equals(login) && a.getLogin().equals(mdp)) {
				return a.getId();
			}
		}
		throw new Exception("Connexion à l'agence échouée");
	}

	@Override
	public void reserver(ArrayList<Offre> offres, int ofrid, Client cl) throws Exception {
		try {
			Offre offre = offres.get(ofrid);
			boolean valid = cl.validBankCoords();
			Reservation reserv = new Reservation(offre.getChambre(), cl, offre.getDatedispo(), offre.getLimitedispo());
			h.getReservations().add(reserv);
		} catch (Exception e) {
			throw new Exception("Réservation échouée: " + e.toString());
		}
	}

}
