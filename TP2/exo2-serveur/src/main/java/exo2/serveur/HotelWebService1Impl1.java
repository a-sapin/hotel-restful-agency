package exo2.serveur;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelWebService1Impl1 implements HotelWebService1 {
	
	private Hotel h1 = new Hotel(); // Déclaration à compléter

	@Override
	public Hotel getHotel() {
		return h1;
	}

	@Override
	public void setHotel(Hotel h) {
		h1 = h;
	}

	@Override
	public int connexionAgence(String id, String mdp) {
		ArrayList<Agence> agences = h1.getPartenaires();
		for (Agence a : agences) {
			if(a.getLogin().equals(id) && a.getLogin().equals(mdp)) {
				return a.getId();
			}
		}
		return -1;
	}

	@Override
	public ArrayList<Offre> rechercheParPartenaire(LocalDate from, LocalDate to, int nbpersonnes) {
		ArrayList<Offre> offres = new ArrayList<Offre>();
		for(Chambre c : h1.getChambresDisponibles()) {
			
		}
		return null;
	}

}
