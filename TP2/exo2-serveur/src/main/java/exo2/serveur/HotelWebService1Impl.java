package exo2.serveur;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.jws.WebService;

@WebService(endpointInterface="exo2.serveur.HotelWebService1")
public class HotelWebService1Impl implements HotelWebService1 {
	
	private Hotel h;
	
	public HotelWebService1Impl(Hotel h) {
		this.h = h;
	}
	
	public double getPrixReduit(Chambre c, LocalDate from, LocalDate to, int loggedagency) {
		return c.getPrixNuit() * (int)ChronoUnit.DAYS.between(from, to) * (1 - h.getPartenaireById(loggedagency).getReduc());
	}
	
	public boolean isEmptyException(ArrayList<Offre> list) throws Exception {
		if (list.size() == 0) {
			throw new Exception("Liste d'offres vide");
		}
		return false;
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
	public ArrayList<Offre> rechercheParPartenaire(String login, String mdp, LocalDate from, LocalDate to, int nbpersonnes) throws Exception {
		try {
			int loggedagency = this.connexionAgence(login, mdp);
			ArrayList<Offre> offres = new ArrayList<Offre>();
			ArrayList<Chambre> chambresdispo = h.getChambresDisponibles(from, to);
			for(int i = 0; i < chambresdispo.size(); i++) {
				Chambre c = chambresdispo.get(i);
				if(c.getNblits() == nbpersonnes) {
					offres.add(new Offre(i, c.getNblits(), h.getPremiereDispo(c, from, to), this.getPrixReduit(c, from, to, loggedagency)));
				}
			}
			boolean isempty = this.isEmptyException(offres);
			return offres;
		} catch (Exception e) {
			throw new Exception("Recherche échouée: " + e.toString());
		}
	}

}
