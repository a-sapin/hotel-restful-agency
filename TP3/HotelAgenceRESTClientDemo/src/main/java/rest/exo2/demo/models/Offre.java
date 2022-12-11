package rest.exo2.demo.models;

import java.time.LocalDate;

public class Offre {
	
	private int id;
	private Chambre chambre;
	private LocalDate datedispo;
	private LocalDate limitedispo;
	private double prix;
	
	public Offre(int id, Chambre c, LocalDate datedispo, LocalDate limitedispo, double prix) {
		this.id = id;
		this.chambre = c;
		this.datedispo = datedispo;
		this.limitedispo = limitedispo;
		this.prix = prix;
	}

	public int getId() {
		return id;
	}

	public Chambre getChambre() {
		return chambre;
	}

	public LocalDate getDatedispo() {
		return datedispo;
	}
	
	public LocalDate getLimitedispo() {
		return limitedispo;
	}

	public double getPrix() {
		return prix;
	}
	
}
