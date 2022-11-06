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
	
	public String getNom() {
		return this.nom;
	}
	
	public int getEtoiles() {
		return this.etoiles;
	}
	
	public ArrayList<Chambre> getAllChambres() {
		return this.chambres;
	}
	
	public ArrayList<Reservation> getAllReservations() {
		return this.reservations;
	}
	
	public ArrayList<Chambre> getChambresDisponibles(LocalDate from, LocalDate to) {
		ArrayList<Chambre> dispo = new ArrayList<Chambre>();
		for (Chambre c : this.chambres) {
			boolean reservee = false;
			for(Reservation r : this.reservations) {
				(from.isAfter(r.getArrivee()) && to.isBefore(r.getDepart()))
				(from.)
				if()
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
	
}
