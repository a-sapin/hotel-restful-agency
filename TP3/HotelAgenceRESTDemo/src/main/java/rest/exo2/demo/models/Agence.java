package rest.exo2.demo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "agences")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Agence {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private double reduc;
	private String login;
	private String mdp;
	
	private ArrayList<String> hoteluris;
	
	@JsonIgnore
	@OneToMany(mappedBy = "agence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Hotel> hotels;
	
	public Agence() {
		
	}
	
	public Agence(String nom, double reduc, String login, String mdp, ArrayList<Hotel> hotels) {
		this.nom = nom;
		this.reduc = reduc;
		this.login = login;
		this.mdp = mdp;
		this.hotels = hotels;
		this.hoteluris = hotelsToURI(hotels);
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
	
	public List<Hotel> getHotels() {
		return hotels;
	}
	
	public void setHotels(ArrayList<Hotel> hotels) {
		this.hotels = hotels;
		this.hoteluris = hotelsToURI(hotels);
	}
	
	public Hotel getHotelById(long id) throws Exception {
		for(Hotel h : hotels) {
			if (h.getId() == id) {
				return h;
			}
		}
		throw new Exception("Hotel d'ID " + Long.toString(id) + " introuvable");
	}
	
	public ArrayList<String> hotelsToURI(ArrayList<Hotel> hotelarray) {
		ArrayList<String> uris = new ArrayList<String>();
		for(Hotel h: hotelarray) {
			uris.add("agenceservice/api/hotels/" + h.getId());
		}
		return uris;
	}
	
	public ArrayList<String> getHotelURIs() {
		return hoteluris;
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
	
	public void setHotelURIs(ArrayList<String> uris) {
		this.hoteluris = uris;
	}
	
	@Override
	public String toString() {
		return Long.toString(id) + ": " + nom + ", réduction de " + Double.toString(100*reduc) +"% (login: " + login + "; mdp: " + mdp + ")";
	}
	
}
