package rest.exo2.demo.models;

public class Chambre {
	
	private int numero;
	private int nblits;
	private int prixnuit;
	
	public Chambre(int num, int nblits, int prixnuit) {
		this.numero = num;
		this.nblits = nblits;
		this.prixnuit = prixnuit;
	}
	
	public int getNblits() {
		return nblits;
	}
	
	public int getPrixNuit() {
		return prixnuit;
	}
	
	@Override
	public String toString() {
		return "Chambre num�ro " + Integer.toString(numero) + ": " + Integer.toString(nblits) + " lits";
	}
	
}
