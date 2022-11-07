package exo2.serveur;

import java.time.LocalDate;

public class Offre {
	
	private int id;
	private int nblits;
	private LocalDate datedispo;
	private double prix;
	
	public Offre(int id, int nblits, LocalDate datedispo, double prix) {
		this.id = id;
		this.nblits = nblits;
		this.datedispo = datedispo;
		this.prix = prix;
	}

	public int getId() {
		return id;
	}

	public int getNblits() {
		return nblits;
	}

	public LocalDate getDatedispo() {
		return datedispo;
	}

	public double getPrix() {
		return prix;
	}
	
}
