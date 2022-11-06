package exo2.serveur;

public class Chambre {
	
	private int numero;
	private int nblits;
	private boolean disponible; //Je sais pas si on obtient la disponibilité par l'absence de Reservation à une date donnée. On verra.
	private int prixnuit;
	
	public Chambre(int num, int nblits, int prixnuit) {
		this.numero = num;
		this.nblits = nblits;
		this.disponible = true;
		this.prixnuit = prixnuit;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	public int getPrixNuit() {
		return prixnuit;
	}
	
}
