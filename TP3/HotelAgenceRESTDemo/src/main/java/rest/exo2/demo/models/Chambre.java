package rest.exo2.demo.models;

import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "chambres")
public class Chambre {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "numero")
	private int numero;
	@Column(name = "nblits")
	private int nblits;
	@Column(name = "prixnuit")
	private int prixnuit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotel_id", nullable = false)
	private Hotel hotel;
	
	@OneToMany(mappedBy = "chambre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ArrayList<Reservation> reservations;
	
	public Chambre(int num, int nblits, int prixnuit, Hotel h) {
		this.numero = num;
		this.nblits = nblits;
		this.prixnuit = prixnuit;
		this.hotel = h;
		this.reservations = new ArrayList<Reservation>();
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

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservations = reservations;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hotel, id, nblits, numero, prixnuit);
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
				&& numero == other.numero && prixnuit == other.prixnuit;
	}

	@Override
	public String toString() {
		return "Chambre numéro " + Integer.toString(numero) + ": " + Integer.toString(nblits) + " lits";
	}
	
}
