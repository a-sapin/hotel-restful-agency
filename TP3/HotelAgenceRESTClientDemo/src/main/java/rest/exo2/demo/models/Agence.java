package rest.exo2.demo.models;

import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Agence {
	
	private Long id;
	private String nom;
	private double reduc;
	private String login;
	private String mdp;
	private ArrayList<Hotel> hotels;
	
	public Agence() {
		
	}
	
	public Agence(String nom, double reduc, String login, String mdp, ArrayList<Hotel> hotels) {
		this.nom = nom;
		this.reduc = reduc;
		this.login = login;
		this.mdp = mdp;
		this.hotels = hotels;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getReduc() {
		return reduc;
	}
	
	public void setReduc(double reduc) {
		this.reduc = reduc;
	}

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}
	
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	public ArrayList<Hotel> getAllHotels() {
		return hotels;
	}
	
	public Hotel getHotelById(long id) throws Exception {
		for(Hotel h : hotels) {
			if (h.getId() == id) {
				return h;
			}
		}
		throw new Exception("Hotel d'ID " + Long.toString(id) + " introuvable");
	}

	@Override
	public int hashCode() {
		return Objects.hash(hotels, id, login, mdp, nom, reduc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agence other = (Agence) obj;
		return Objects.equals(hotels, other.hotels) 
				&& id == other.id && Objects.equals(login, other.login)
				&& Objects.equals(mdp, other.mdp) && Objects.equals(nom, other.nom)
				&& Double.doubleToLongBits(reduc) == Double.doubleToLongBits(other.reduc);
	}
	
	@Override
	public String toString() {
		return Long.toString(id) + ": " + nom + ", réduction de " + Double.toString(100*reduc) +"% (login: " + login + "; mdp: " + mdp + ")";
	}
	
}