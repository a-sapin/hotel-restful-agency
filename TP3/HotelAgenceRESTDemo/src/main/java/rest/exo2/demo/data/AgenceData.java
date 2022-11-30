package rest.exo2.demo.data;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rest.exo2.demo.models.Agence;
import rest.exo2.demo.models.Chambre;
import rest.exo2.demo.models.Hotel;
import rest.exo2.demo.repositories.AgenceRepository;
import rest.exo2.demo.repositories.ChambreRepository;
import rest.exo2.demo.repositories.HotelRepository;

@Configuration
public class AgenceData {
	
	private Logger logger = LoggerFactory.getLogger(AgenceData.class);
	
	@Bean
	public CommandLineRunner initDatabase(AgenceRepository repA, HotelRepository repH, ChambreRepository repC) {
		
		// Création Chambres
		Chambre c1 = new Chambre(101, 2, 45, null);
		Chambre c2 = new Chambre(102, 3, 65, null);
		Chambre c3 = new Chambre(103, 2, 55, null);
		Chambre c4 = new Chambre(120, 1, 45, null);
		Chambre c5 = new Chambre(121, 2, 80, null);
		Chambre c6 = new Chambre(122, 1, 60, null);
		Chambre c7 = new Chambre(1, 3, 135, null);
		Chambre c8 = new Chambre(2, 2, 100, null);
		Chambre c9 = new Chambre(3, 3, 150, null);
		
		// Création Hotels
		Hotel h1 = new Hotel("Hotel Lapeyronie", 3, null, null);
		h1.setAdresse(80, "rue des Petetes", "", "Montpellier", "France", 0, 0);
		Hotel h2 = new Hotel("Hotel Paradiso", 4, null, null);
		h2.setAdresse(135, "boulevard Diderot", "", "Paris", "France", 0, 0);
		Hotel h3 = new Hotel("Beaumont House", 5, null, null);
		h3.setAdresse(56, "Shurdington Road", "", "Cheltenham", "Royaume-Uni", 0, 0);
		
		// Création Agences
		Agence a1 = new Agence("Dorchies Airlines", 0.20, "da_agence", "legoat", null);
		Agence a2 = new Agence("Mera Summer Camp", 0.10, "msc_agence", "peppibandit", null);
		
		// Lien Chambres-Hotels
		ArrayList<Chambre> cal1 = new ArrayList<Chambre>();
		c1.setHotel(h1); c2.setHotel(h1); c3.setHotel(h1);
		cal1.add(c1); cal1.add(c2); cal1.add(c3);
		h1.setChambres(cal1);
		ArrayList<Chambre> cal2 = new ArrayList<Chambre>();
		c4.setHotel(h2); c5.setHotel(h2); c6.setHotel(h2);
		cal2.add(c4); cal2.add(c5); cal2.add(c6);
		h2.setChambres(cal2);
		ArrayList<Chambre> cal3 = new ArrayList<Chambre>();
		c7.setHotel(h3); c8.setHotel(h3); c9.setHotel(h3);
		cal3.add(c7); cal3.add(c8); cal3.add(c9);
		h3.setChambres(cal3);
		
		// Lien Hotels-Agences
		ArrayList<Hotel> hal1 = new ArrayList<Hotel>();
		h1.setAgence(a1); h2.setAgence(a1);
		hal1.add(h1); hal1.add(h2);
		a1.setHotels(hal1);
		ArrayList<Hotel> hal2 = new ArrayList<Hotel>();
		h3.setAgence(a2);
		hal2.add(h3);
		a2.setHotels(hal2);
		
		return args -> {
			// Insertion Chambres
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c1));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c2));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c3));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c4));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c5));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c6));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c7));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c8));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c9));
			
			// Insertion Hotels
			logger.info("Ajout d'Hotel à la base de données: " + repH.save(h1));
			logger.info("Ajout d'Hotel à la base de données: " + repH.save(h2));
			logger.info("Ajout d'Hotel à la base de données: " + repH.save(h3));
			
			// Insertion Agences
			logger.info("Ajout d'Agence à la base de données: " + repA.save(a1));
			logger.info("Ajout d'Agence à la base de données: " + repA.save(a2));
		};
	}
	
}
