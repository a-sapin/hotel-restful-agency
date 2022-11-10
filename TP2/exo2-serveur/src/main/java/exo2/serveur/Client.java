package exo2.serveur;

import java.time.LocalDate;
//import java.util.regex.*;

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
		this.numerocarte = numerocarte; //format: XXXX-XXXX-XXXX-XXXX
		this.moisexpiration = LocalDate.of(yearexp, monthexp, 1);
		this.codesecurite = code; //format: XXX
		try {
			boolean valid = this.validBankCoords();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validBankCoords() throws Exception {
		//m = Pattern.matches("(\\d{4}-){3}\\d{4}", numerocarte);
		if((numerocarte.length() == 19) && (String.valueOf(codesecurite).length() == 3) && moisexpiration.isAfter(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1))) {
			return true;
		}
		else throw new Exception("Coordonnées bancaires invalides");
	}
	
}
