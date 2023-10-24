package exo2.serveur;

import java.time.LocalDate;

public class Reservation {
	
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

	public Chambre getChambre() {
		return chambre;
	}

	public Client getClient() {
		return client;
	}

	public LocalDate getArrivee() {
		return arrivee;
	}

	public LocalDate getDepart() {
		return depart;
	}
	
}
