package exo2.serveur;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.jws.WebService;

@WebService(endpointInterface="exo2.serveur.HotelWebService2")
public class HotelWebService2Impl implements HotelWebService2 {
	
	private Hotel h;

	public HotelWebService2Impl(Hotel h) {
		this.h = h;
	}
	
	public double getPrixReduit(Chambre c, LocalDate from, LocalDate to, int loggedagency) {
		return c.getPrixNuit() * (int)ChronoUnit.DAYS.between(from, to) * (1 - h.getPartenaireById(loggedagency).getReduc());
	}
	
	public boolean isEmptyException(ArrayList<Offre> list) throws Exception {
		if (list.isEmpty()) {
			throw new Exception("Liste d'offres vide");
		}
		return false;
	}
	
	public LocalDate isValidException(LocalDate in) throws Exception {
		if(in.isBefore(LocalDate.now())) {
			throw new Exception("Date invalide, située avant aujourd'hui");
		}
		return in;
	}
	
	public ArrayList<Offre> rechercheParPartenaire(int agencyid, String fromstring, String tostring, int nbpersonnes) throws Exception {
		try {
			LocalDate from = LocalDate.parse(fromstring);
			LocalDate to = LocalDate.parse(tostring);
			ArrayList<Offre> offres = new ArrayList<Offre>();
			ArrayList<Chambre> chambresdispo = h.getChambresDisponibles(this.isValidException(from), this.isValidException(to));
			int j = 1;
			for(int i = 0; i < chambresdispo.size(); i++) {
				Chambre c = chambresdispo.get(i);
				if(c.getNblits() == nbpersonnes) {
					offres.add(new Offre(j, c, h.getPremiereDispo(c, from, to), h.getLimiteDispo(c, from, to), this.getPrixReduit(c, from, to, agencyid)));
					j++;
				}
			}
			boolean isempty = this.isEmptyException(offres);
			return offres;
		} catch (Exception e) {
			throw new Exception("Recherche échouée: " + e.toString());
		}
	}
	
	public Client creerClient(String nom, String prenom, String numerocarte, int moisexpiration, int anneeexpiration, int codesecu) throws Exception {
		Client cl = new Client(nom, prenom);
		cl.setBankCoords(numerocarte, moisexpiration, anneeexpiration, codesecu);
		return cl;
	}
	
	Offre getOffreById(ArrayList<Offre> offres, int id) throws Exception {
		for(int i = 0; i < offres.size(); i++) {
			if(offres.get(i).getId() == id) {
				return offres.get(i);
			}
		}
		throw new Exception("Offre inaccessible");
	}

	@Override
	public void reserver(int agencyid, String fromstring, String tostring, int nbpersonnes, int ofrid, String nom, String prenom, String numerocarte, int moisexpiration, int anneeexpiration, int codesecu) throws Exception {
		try {
			Client cl = this.creerClient(nom, prenom, numerocarte, moisexpiration, anneeexpiration, codesecu);
			ArrayList<Offre> offres = this.rechercheParPartenaire(agencyid, fromstring, tostring, nbpersonnes);
			Offre offre = this.getOffreById(offres, ofrid);
			Reservation reserv = new Reservation(offre.getChambre(), cl, offre.getDatedispo(), offre.getLimitedispo());
			h.getReservations().add(reserv);
		} catch (Exception e) {
			throw new Exception("Réservation échouée: " + e.toString());
		}
	}

}
