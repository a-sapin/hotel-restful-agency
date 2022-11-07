package exo2.serveur;

import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {
	
	private String nom;
	
	// Adresse
	private String pays;
	private String ville;
	private String rue;
	private int numero;
	private String lieudit;
	private double[] gps; //Taille imposée: 2
	
	private int etoiles;
	
	private ArrayList<Chambre> chambres;
	
	private ArrayList<Reservation> reservations;
	
	private ArrayList<Agence> partenaires;
	
	public Hotel(String nom, int etoiles, ArrayList<Chambre> chambres, ArrayList<Agence> partenaires) {
		this.nom = nom;
		this.etoiles = etoiles;
		this.chambres = chambres;
		this.reservations = new ArrayList<Reservation>();
		this.partenaires = partenaires;
		
		this.pays = this.ville = this.rue = this.lieudit = "";
		this.numero = -1;
		this.gps = new double[] {0.0, 0.0};
	}
	
	public Hotel() {
		// TODO Auto-generated constructor stub
	}

	public String getNom() {
		return this.nom;
	}
	
	public boolean isChambreDispo(Chambre c, LocalDate from, LocalDate to) {
		for (Reservation r : this.reservations) {
			if(r.getChambre().equals(c) && !((from.isBefore(r.getArrivee()) && to.isBefore(r.getArrivee())) || (from.isAfter(r.getDepart()) && to.isAfter(r.getDepart())))) {
				return false;
			}
		}
		return true;
	}
	
	public LocalDate getPremiereDispo(Chambre c, LocalDate from, LocalDate to) {
		LocalDate datedispo = LocalDate.now();
		boolean founddate = false;
		for (Reservation r1 : this.reservations) {
			if(!founddate) {
				datedispo = LocalDate.of(r1.getArrivee().getYear(), r1.getArrivee().getMonthValue(), r1.getArrivee().getDayOfMonth()+1);
				boolean availabledate = true;
				for (Reservation r2 : this.reservations) {
					if(!r1.equals(r2) && this.isChambreDispo(c, from, to)) {
						availabledate = false;
					}
				}
				if(availabledate) {
					founddate = true;
				}
			}
		}
		return datedispo;
	}
	
	public ArrayList<Chambre> getChambresDisponibles(LocalDate from, LocalDate to) {
		ArrayList<Chambre> dispo = new ArrayList<Chambre>();
		for (Chambre c : this.chambres) {
			if(this.isChambreDispo(c, from, to)) {
				dispo.add(c);
			}
		}
		return dispo;
	}
	
	
	public String getAdresse() {
		return this.lieudit + ", " + Integer.toString(this.numero) + " " + this.rue + ", " + this.ville + ", " + this.pays;
	}
	
	public void setAdresse(String lieudit, int numero, String rue, String ville, String pays, double gpsx, double gpsy) {
		this.lieudit = lieudit;
		this.numero = numero;
		this.rue = rue;
		this.ville = ville;
		this.pays = pays;
		this.gps[0] = gpsx;
		this.gps[1] = gpsy;
	}

	public ArrayList<Agence> getPartenaires() {
		return partenaires;
	}

	public void setPartenaires(ArrayList<Agence> partenaires) {
		this.partenaires = partenaires;
	}
	
	public Agence getPartenaireById(int id) {
		for (Agence a : this.partenaires) {
			if(a.getId() == id) {
				return a;
			}
		}
		return null;
	}
	
}
