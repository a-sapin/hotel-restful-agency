package rest.exo2.demo.models;

import java.util.ArrayList;
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

@Entity
@Table(name = "chambres")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Chambre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int numero;
	private int nblits;
	private int prixnuit;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_id", nullable = false)
	private Hotel hotel;
	
	private ArrayList<String> reservationuris;
	
	@JsonIgnore
	@OneToMany(mappedBy = "chambre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reservation> reservations;
	
	public Chambre(int num, int nblits, int prixnuit, Hotel h) {
		this.numero = num;
		this.nblits = nblits;
		this.prixnuit = prixnuit;
		this.hotel = h;
		this.reservations = new ArrayList<Reservation>();
		this.reservationuris = new ArrayList<String>();
	}
	
	public Chambre() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNblits() {
		return nblits;
	}

	public void setNblits(int nblits) {
		this.nblits = nblits;
	}

	public int getPrixnuit() {
		return prixnuit;
	}

	public void setPrixnuit(int prixnuit) {
		this.prixnuit = prixnuit;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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
	
	public void setReservationURIs(ArrayList<String> reservation) {
		ArrayList<String> res = new ArrayList<String>();
		for (int i=0; i<reservation.size(); i++)
		{
			res.add(reservation.get(i));
		}
		this.reservationuris = res;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hotel, id, nblits, numero, prixnuit, reservations);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chambre other = (Chambre) obj;
		return Objects.equals(hotel, other.hotel) && Objects.equals(id, other.id) && nblits == other.nblits
				&& numero == other.numero && prixnuit == other.prixnuit
				&& Objects.equals(reservations, other.reservations);
	}

	@Override
	public String toString() {
		return id + ": Chambre numéro " + Integer.toString(numero) + " de l'hotel " + hotel.getNom() + ": " + Integer.toString(nblits) + " lits";
	}
	
}
