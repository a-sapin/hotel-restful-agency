package exo2.serveur;

import java.util.ArrayList;
import javax.xml.ws.Endpoint;

public class Publisher {

	public static void main(String[] args) {
		
		//Création des chambres
		Chambre ch1 = new Chambre(1, 2, 50);
		Chambre ch2 = new Chambre(2, 1, 39);
		Chambre ch3 = new Chambre(3, 3, 69);
		
		ArrayList<Chambre> chambres1 = new ArrayList<Chambre>();
		chambres1.add(ch1);
		chambres1.add(ch2);
		chambres1.add(ch3);
		
		Chambre ch4 = new Chambre(4, 1, 42);
		Chambre ch5 = new Chambre(5, 2, 65);
		Chambre ch6 = new Chambre(6, 5, 420);
		
		ArrayList<Chambre> chambres2 = new ArrayList<Chambre>();
		chambres2.add(ch4);
		chambres2.add(ch5);
		chambres2.add(ch6);
		
		Chambre ch7 = new Chambre(7, 4, 200);
		Chambre ch8 = new Chambre(8, 1, 12);
		Chambre ch9 = new Chambre(9, 2, 900);
		
		ArrayList<Chambre> chambres3 = new ArrayList<Chambre>();
		chambres3.add(ch7);
		chambres3.add(ch8);
		chambres3.add(ch9);
		
		Chambre ch10 = new Chambre(10, 2, 27);
		Chambre ch11 = new Chambre(11, 1, 9);
		Chambre ch12 = new Chambre(12, 4, 69);
		
		ArrayList<Chambre> chambres4 = new ArrayList<Chambre>();
		chambres4.add(ch10);
		chambres4.add(ch11);
		chambres4.add(ch12);
		
		
		//Création des agences
		Agence a1 = new Agence(1, "Dorchies Airlines", 0.35, "da_agence", "legoat");
		Agence a2 = new Agence(2, "Mera Summer Camp", 0.10, "msc_agence", "peppibandit");
		Agence a3 = new Agence(3, "Fazbear Entertainment", 0.1987, "fe_agence", "fnaf_balls");
		
		ArrayList<Agence> part1 = new ArrayList<Agence>();
		part1.add(a1);
		part1.add(a2);
		
		ArrayList<Agence> part2 = new ArrayList<Agence>();
		part2.add(a2);
		part2.add(a3);
		
		ArrayList<Agence> part3 = new ArrayList<Agence>();
		part3.add(a1);
		part3.add(a3);
		
		ArrayList<Agence> part4 = new ArrayList<Agence>();
		part4.add(a1);
		part4.add(a2);
		part4.add(a3);
		
		
		//Création des hotels
		Hotel h1 = new Hotel("Chez Jacky", 2, chambres1, part1);
		h1.setAdresse(4, "Rue du Franchouillard", "Quartier de l'Orangette", "Bussy-Saint-Georges", "France", 0.0, 0.0);
		Hotel h2 = new Hotel("Hotel Memepage", 3, chambres2, part2);
		h2.setAdresse(21, "Reddit Boulevard", "The Misery", "Hell", "Etats-Unis", 0.0, 0.0);
		Hotel h3 = new Hotel("The Basement", 1, chambres3, part3);
		h3.setAdresse(1, "Hill Street", "The Hill", "Detroit", "Etats-Unis", 0.0, 0.0);
		Hotel h4 = new Hotel("Boulevard Jean-Jaures", 0, chambres4, part4);
		h4.setAdresse(1, "Boulevard Jean-Jaures", "Centre-ville", "Nîmes", "France", 0.0, 0.0);
		
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		hotels.add(h1);
		hotels.add(h2);
		hotels.add(h3);
		hotels.add(h4);
		
		for (int i = 0; i < hotels.size(); i++) {
			Endpoint.publish("http://localhost:8080/" + Integer.toString(i) + "/consultation", new HotelWebService1Impl(hotels.get(i)));
			System.out.println("Service de consultation pour " + hotels.get(i).getNom() + " prêt");
			Endpoint.publish("http://localhost:8080/" + Integer.toString(i) + "/reservation", new HotelWebService2Impl(hotels.get(i)));
			System.out.println("Service de reservation pour " + hotels.get(i).getNom() + " prêt");
		}

	}

}
