package exo2.serveur;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class HotelWebService1Impl implements HotelWebService1 {
	
	private Hotel h;
	
	public HotelWebService1Impl(Hotel h) {
		this.h = h;
	}
	
	public double getPrixReduit(Chambre c, LocalDate from, LocalDate to, int loggedagency) {
		return c.getPrixNuit() * (int)ChronoUnit.DAYS.between(from, to) * h.getPartenaireById(loggedagency).getReduc();
	}

	@Override
	public int connexionAgence(String login, String mdp) {
		ArrayList<Agence> agences = h.getPartenaires();
		for (Agence a : agences) {
			if(a.getLogin().equals(login) && a.getLogin().equals(mdp)) {
				return a.getId();
			}
		}
		return -1;
	}

	@Override
	public ArrayList<Offre> rechercheParPartenaire(String login, String mdp, LocalDate from, LocalDate to, int nbpersonnes) {
		int loggedagency = this.connexionAgence(login, mdp);
		ArrayList<Offre> offres = new ArrayList<Offre>();
		ArrayList<Chambre> chambresdispo = h.getChambresDisponibles(from, to);
		for(int i = 0; i < chambresdispo.size(); i++) {
			Chambre c = chambresdispo.get(i);
			if(c.getNblits() == nbpersonnes) {
				offres.add(new Offre(i, c.getNblits(), h.getPremiereDispo(c, from, to), this.getPrixReduit(c, from, to, loggedagency)));
			}
		}
		return offres;
	}

}
