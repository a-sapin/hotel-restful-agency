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
	
	@Override
	public String getHotelNom() {
		return h.getNom();
	}
	
	@Override
	public String getHotelAdresse() {
		return h.getAdresse();
	}
	
	@Override
	public ArrayList<Agence> getHotelPartenaires() {
		return h.getPartenaires();
	}
	
	@Override
	public ArrayList<String> getHotelPartenaireNoms() {
		ArrayList<String> noms = new ArrayList<String>();
		ArrayList<Agence> partenaires = h.getPartenaires();
		for(Agence a : partenaires) {
			noms.add(a.getNom());
		}
		return noms;
	}
	
	@Override
	public ArrayList<String> getHotelPartenaireLogins() {
		ArrayList<String> noms = new ArrayList<String>();
		ArrayList<Agence> partenaires = h.getPartenaires();
		for(Agence a : partenaires) {
			noms.add(a.getLogin());
		}
		return noms;
	}
	
	@Override
	public ArrayList<String> getHotelPartenaireMdps() {
		ArrayList<String> noms = new ArrayList<String>();
		ArrayList<Agence> partenaires = h.getPartenaires();
		for(Agence a : partenaires) {
			noms.add(a.getMdp());
		}
		return noms;
	}
	
	@Override
	public Agence getHotelPartenaireById(int i) {
		return h.getPartenaireById(i);
	}
	
	@Override
	public String getPartenaireNomById(int id) {
		ArrayList<Agence> agences = this.getHotelPartenaires();
		for(int i = 0; i < agences.size(); i++) {
			if(agences.get(i).getId() == id) {
				return agences.get(i).getNom();
			}
		}
		return "";
	}

	@Override
	public int connexionAgenceWS1(String login, String mdp) throws Exception {
		ArrayList<Agence> agences = this.getHotelPartenaires();
		for (int i = 0; i < agences.size(); i++) {
			if(agences.get(i).getLogin().equals(login) && agences.get(i).getMdp().equals(mdp)) {
				return agences.get(i).getId();
			}
		}
		throw new Exception("Connexion à l'agence échouée");
	}
	
	@Override
	public int getOffreIdByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception {
		return this.rechercheParPartenaire(agencyid, fromstring, tostring, nbpersonnes).get(i).getId();
	}
	
	@Override
	public String getOffreChambreToStringByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception {
		return this.rechercheParPartenaire(agencyid, fromstring, tostring, nbpersonnes).get(i).getChambre().toString();
	}
	
	@Override
	public String getOffreArriveeToStringByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception {
		return this.rechercheParPartenaire(agencyid, fromstring, tostring, nbpersonnes).get(i).getDatedispo().toString();
	}
	
	@Override
	public String getOffreDepartToStringByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception {
		return this.rechercheParPartenaire(agencyid, fromstring, tostring, nbpersonnes).get(i).getLimitedispo().toString();
	}
	
	@Override
	public double getOffrePrixByIndex(int agencyid, String fromstring, String tostring, int nbpersonnes, int i) throws Exception {
		return this.rechercheParPartenaire(agencyid, fromstring, tostring, nbpersonnes).get(i).getPrix();
	}

	@Override
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
}
