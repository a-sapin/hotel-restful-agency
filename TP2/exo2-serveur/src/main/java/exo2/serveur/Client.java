package exo2.serveur;

import java.time.LocalDate;

public class Client {
	
	private String nom;
	private String prenom;
	
	// Coordonnées bancaires
	private String numerocarte;
	private LocalDate moisexpiration;
	private int codesecurite;
	
	public Client(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
		this.numerocarte = "";
		this.moisexpiration = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		this.codesecurite = -1;
	}
	
	public void setBankCoords(String numerocarte, int monthexp, int yearexp, int code) {
		this.numerocarte = numerocarte;
		this.moisexpiration = LocalDate.of(yearexp, monthexp, 1);
		this.codesecurite = code;
	}
	
}
