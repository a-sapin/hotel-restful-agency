package exo2.serveur;

import java.util.ArrayList;

public class Hotel {
	
	private String nom;
	
	// Adresse
	private String pays;
	private String ville;
	private String rue;
	private String numero;
	private String lieudit;
	private double[] gps; //Taille imposée: deux
	
	private int etoiles;
	
	private ArrayList<Chambre> chambres;
	
	private ArrayList<Reservation> reservations;
	
}
