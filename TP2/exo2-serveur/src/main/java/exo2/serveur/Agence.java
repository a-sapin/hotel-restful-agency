package exo2.serveur;

public class Agence {
	
	private int id;
	private String nom;
	private double reduc;
	private String login;
	private String mdp;
	
	public Agence(int id, String nom, double reduc, String login, String mdp) {
		this.id = id;
		this.nom = nom;
		this.reduc = reduc;
		this.login = login;
		this.mdp = mdp;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public double getReduc() {
		return reduc;
	}

	public String getLogin() {
		return login;
	}

	public String getMdp() {
		return mdp;
	}
	
}
