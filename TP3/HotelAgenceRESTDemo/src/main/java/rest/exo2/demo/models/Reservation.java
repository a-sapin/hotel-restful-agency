package rest.exo2.demo.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
	
	@Id
	@GeneratedValue
	private Long id;
	private Chambre chambre;
	private Client client;
	private LocalDate arrivee;
	private LocalDate depart;
	
	public Reservation(Chambre ch, Client cl, LocalDate from, LocalDate to) {
		this.chambre = ch;
		this.client = cl;
		this.arrivee = from;
		this.depart = to;
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
	
}
