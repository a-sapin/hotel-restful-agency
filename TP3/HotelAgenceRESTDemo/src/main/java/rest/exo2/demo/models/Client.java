package rest.exo2.demo.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "clients")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	private String prenom;
	
	// Coordonn�es bancaires
	private String numerocarte;
	private LocalDate moisexpiration;
	private int codesecurite;
	
	private ArrayList<String> reservationuris;
	
	@JsonIgnore
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reservation> reservations;
	
	public Client()
	{
		
	}
	
	public Client(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
		this.numerocarte = "";
		this.moisexpiration = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		this.codesecurite = -1;
		this.reservations = new ArrayList<Reservation>();
		this.reservationuris = new ArrayList<String>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNumerocarte() {
		return numerocarte;
	}

	public void setNumerocarte(String numerocarte) {
		this.numerocarte = numerocarte;
	}

	public LocalDate getMoisexpiration() {
		return moisexpiration;
	}

	public void setMoisexpiration(LocalDate moisexpiration) {
		this.moisexpiration = moisexpiration;
	}

	public int getCodesecurite() {
		return codesecurite;
	}

	public void setCodesecurite(int codesecurite) {
		this.codesecurite = codesecurite;
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
	
	public void setReservationURIs(ArrayList<String> reservation) 
	{
		ArrayList<String> res = new ArrayList<String>();
		for (int i=0; i<reservation.size(); i++)
		{
			res.add(reservation.get(i));
		}
		this.reservationuris = res;
	}

	public void setBankCoords(String numerocarte, int monthexp, int yearexp, int code) {
		this.numerocarte = numerocarte; //format: XXXX-XXXX-XXXX-XXXX
		this.moisexpiration = LocalDate.of(yearexp, monthexp, 1);
		this.codesecurite = code; //format: XXX
		try {
			boolean valid = this.validBankCoords();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validBankCoords() throws Exception {
		if((numerocarte.length() == 19) && (String.valueOf(codesecurite).length() == 3) && moisexpiration.isAfter(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1))) {
			return true;
		}
		else throw new Exception("Coordonn�es bancaires invalides");
	}

	@Override
	public int hashCode() {
		return Objects.hash(codesecurite, id, moisexpiration, nom, numerocarte, prenom, reservations);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return codesecurite == other.codesecurite && Objects.equals(id, other.id)
				&& Objects.equals(moisexpiration, other.moisexpiration) && Objects.equals(nom, other.nom)
				&& Objects.equals(numerocarte, other.numerocarte) && Objects.equals(prenom, other.prenom)
				&& Objects.equals(reservations, other.reservations);
	}
	
	@Override
	public String toString() {
		return id + ": " + nom + " " + prenom + " (coordonnées: " + numerocarte + " " + moisexpiration + " " + codesecurite + ")";
	}
	
}
