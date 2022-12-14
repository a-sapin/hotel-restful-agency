package rest.exo2.demo.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "hotels")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hotel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	private ArrayList<String> chambreuris;
	
	private ArrayList<String> reservationuris;
	
	@JsonIgnore
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Chambre> chambres;
	
	@JsonIgnore
	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reservation> reservations;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "agence_id", nullable = false)
	private Agence agence;
	
	public Hotel() {
		
	}
	
	public Hotel(String nom, int etoiles, ArrayList<Chambre> chambres, Agence agence) {
		this.nom = nom;
		this.etoiles = etoiles;
		this.chambres = chambres;
		this.chambreuris = this.chambresToURI(chambres);
		this.reservations = new ArrayList<Reservation>();
		this.reservationuris = new ArrayList<String>();
		this.agence = agence;
		
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
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getEtoiles() {
		return etoiles;
	}
	public void setEtoiles(int etoiles) {
		this.etoiles = etoiles;
	}
	
	public Agence getAgence() {
		return agence;
	}
	
	public void setAgence(Agence a) {
		this.agence = a;
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
	
	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getLieudit() {
		return lieudit;
	}

	public void setLieudit(String lieudit) {
		this.lieudit = lieudit;
	}

	public double[] getGps() {
		return gps;
	}

	public void setGps(double[] gps) {
		this.gps = gps;
	}
	
	public List<Chambre> getChambres() {
		return chambres;
	}

	public void setChambres(ArrayList<Chambre> chambres) {
		this.chambres = chambres;
		this.chambreuris = this.chambresToURI(chambres);
	}
	
	public ArrayList<String> chambresToURI(ArrayList<Chambre> chambrearray) {
		ArrayList<String> uris = new ArrayList<String>();
		for(Chambre c: chambrearray) {
			uris.add("agenceservice/api/chambres/" + c.getId());
		}
		return uris;
	}
	
	public ArrayList<String> getChambreURIs() {
		return chambreuris;
	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}
	
	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
		this.reservationuris = this.reservationsToURI(reservations);
	}
	
	public ArrayList<String> reservationsToURI(ArrayList<Reservation> reservationarray) {
		ArrayList<String> uris = new ArrayList<String>();
		for(Reservation r: reservationarray) {
			uris.add("agenceservice/api/reservations/" + r.getId());
		}
		return uris;
	}
	
	public ArrayList<String> getReservationURIs() {
		return reservationuris;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(gps);
		result = prime * result
				+ Objects.hash(agence, chambres, etoiles, id, lieudit, nom, numero, pays, reservations, rue, ville);
		return result;
	}
	
	public void setChambreURIs(ArrayList<String> chambreuris) {
		this.chambreuris = chambreuris;
	}
	
	public void setReservationURIs(ArrayList<String> reservation) {
		ArrayList<String> res = new ArrayList<String>();
		for (int i=0; i<reservation.size(); i++)
		{
			res.add(reservation.get(i));
		}
		this.reservationuris = res;
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
		return Objects.equals(agence, other.agence) && Objects.equals(chambres, other.chambres)
				&& etoiles == other.etoiles && Arrays.equals(gps, other.gps) && Objects.equals(id, other.id)
				&& Objects.equals(lieudit, other.lieudit) && Objects.equals(nom, other.nom) && numero == other.numero
				&& Objects.equals(pays, other.pays) && Objects.equals(reservations, other.reservations)
				&& Objects.equals(rue, other.rue) && Objects.equals(ville, other.ville);
	}

	@Override
	public String toString() {
		return id + ": " + nom + ", " + etoiles + " étoiles via l'agence " + agence.getNom() + " - " + this.getAdresse();
	}

}
