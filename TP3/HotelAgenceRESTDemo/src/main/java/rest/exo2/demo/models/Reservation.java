package rest.exo2.demo.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "reservations")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne//(cascade = CascadeType.ALL)
	@JoinColumn(name = "chambre_id", nullable = false)
	private Chambre chambre;
	
	@ManyToOne//(cascade = CascadeType.ALL)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;
	
	private LocalDate arrivee;
	private LocalDate depart;
	
	@ManyToOne//(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_id", nullable = false)
	private Hotel hotel;
	
	public Reservation() {
		
	}
	
	public Reservation(Chambre ch, Client cl, LocalDate from, LocalDate to, Hotel h) {
		this.chambre = ch;
		this.client = cl;
		this.arrivee = from;
		this.depart = to;
		this.hotel = h;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Chambre getChambre() {
		return chambre;
	}

	public void setChambre(Chambre chambre) {
		this.chambre = chambre;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public LocalDate getArrivee() {
		return arrivee;
	}

	public void setArrivee(LocalDate arrivee) {
		this.arrivee = arrivee;
	}

	public LocalDate getDepart() {
		return depart;
	}

	public void setDepart(LocalDate depart) {
		this.depart = depart;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(arrivee, chambre, client, depart, hotel, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		return Objects.equals(arrivee, other.arrivee) && Objects.equals(chambre, other.chambre)
				&& Objects.equals(client, other.client) && Objects.equals(depart, other.depart)
				&& Objects.equals(hotel, other.hotel) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return id + ": " + chambre + " réservée par " + client.getPrenom() + " " + client.getNom() + ", du " + arrivee + " au " + depart;
	}
	
}
