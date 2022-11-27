package rest.exo2.demo.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.time.temporal.ChronoUnit;

public class Hotel {
	
	private Long id;
	private String nom;
	
	// Adresse
	private String pays;
	private String ville;
	private String rue;
	private int numero;
	private String lieudit;
	private double[] gps; //Taille impos�e: 2
	
	private int etoiles;
	
	private ArrayList<Chambre> chambres;
	
	private ArrayList<Reservation> reservations;
	
	public Hotel() {
		
	}
	
	public Hotel(Long id, String nom, int etoiles, ArrayList<Chambre> chambres, ArrayList<Agence> partenaires) {
		this.id = id;
		this.nom = nom;
		this.etoiles = etoiles;
		this.chambres = chambres;
		this.reservations = new ArrayList<Reservation>();
		
		this.pays = this.ville = this.rue = this.lieudit = "";
		this.numero = -1;
		this.gps = new double[] {0.0, 0.0};
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}
	
	public int getEtoiles() {
		return etoiles;
	}
	
	public ArrayList<Reservation> getReservations() {
		return reservations;
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
		LocalDate datedispo = from;
		boolean founddate = false;
		for (Reservation r1 : this.reservations) {
			if(!founddate) {
				datedispo = LocalDate.of(r1.getArrivee().getYear(), r1.getArrivee().getMonthValue(), r1.getArrivee().getDayOfMonth()+1);
				boolean availabledate = true;
				for (Reservation r2 : this.reservations) {
					if(!r1.equals(r2) && !this.isChambreDispo(c, from, to)) {
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
	
	public LocalDate getLimiteDispo(Chambre c, LocalDate from, LocalDate to) {
		LocalDate premieredispo = this.getPremiereDispo(c, from, to);
		premieredispo = LocalDate.of(premieredispo.getYear(), premieredispo.getMonth(), premieredispo.getDayOfMonth()+1);
		for (Reservation r : reservations) {
			LocalDate arrivee = r.getArrivee();
			if(premieredispo.isBefore(arrivee) && (ChronoUnit.DAYS.between(premieredispo, arrivee) >= 1)) {
				if(to.isBefore(arrivee)) {
					return to;
				}
				else {
					return LocalDate.of(arrivee.getYear(), arrivee.getMonthValue(), arrivee.getDayOfMonth()-1);
				}
			}
		}
		return to;
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
		return Integer.toString(this.numero) + " " + this.rue + ", " + this.lieudit + ", " + this.ville + ", " + this.pays;
	}
	
	public void setAdresse(int numero, String rue, String lieudit, String ville, String pays, double gpsx, double gpsy) {
		this.lieudit = lieudit;
		this.numero = numero;
		this.rue = rue;
		this.ville = ville;
		this.pays = pays;
		this.gps[0] = gpsx;
		this.gps[1] = gpsy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(gps);
		result = prime * result
				+ Objects.hash(chambres, etoiles, id, lieudit, nom, numero, pays, reservations, rue, ville);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		return Objects.equals(chambres, other.chambres) && etoiles == other.etoiles && Arrays.equals(gps, other.gps)
				&& Objects.equals(id, other.id) && Objects.equals(lieudit, other.lieudit)
				&& Objects.equals(nom, other.nom) && numero == other.numero && Objects.equals(pays, other.pays)
				&& Objects.equals(reservations, other.reservations) && Objects.equals(rue, other.rue)
				&& Objects.equals(ville, other.ville);
	}
	
}
