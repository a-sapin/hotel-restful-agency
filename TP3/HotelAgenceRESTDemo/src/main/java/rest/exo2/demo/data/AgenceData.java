package rest.exo2.demo.data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import rest.exo2.demo.models.Agence;
import rest.exo2.demo.models.Chambre;
import rest.exo2.demo.models.Hotel;
import rest.exo2.demo.repositories.AgenceRepository;
import rest.exo2.demo.repositories.ChambreRepository;
import rest.exo2.demo.repositories.HotelRepository;

@Configuration
public class AgenceData {
	
	private Logger logger = LoggerFactory.getLogger(AgenceData.class);
	
	private static final String dateFormat = "yyyy-MM-dd";
	
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsonDateFormatter() {
		return builder -> {
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
        };
	}
	
	@Bean
	public CommandLineRunner initDatabase(AgenceRepository repA, HotelRepository repH, ChambreRepository repC) {
		
		return args -> {
			// Création Chambres
			Chambre c1 = new Chambre(101, 2, 45, new Hotel());
			Chambre c2 = new Chambre(102, 3, 65, new Hotel());
			Chambre c3 = new Chambre(103, 2, 55, new Hotel());
			Chambre c4 = new Chambre(120, 1, 45, new Hotel());
			Chambre c5 = new Chambre(121, 2, 80, new Hotel());
			Chambre c6 = new Chambre(122, 1, 60, new Hotel());
			Chambre c7 = new Chambre(1, 3, 135, new Hotel());
			Chambre c8 = new Chambre(2, 2, 100, new Hotel());
			Chambre c9 = new Chambre(3, 3, 150, new Hotel());
			
			// Création Hotels
			Hotel h1 = new Hotel("Hotel Lapeyronie", 3, new ArrayList<>(), new Agence());
			h1.setAdresse(80, "rue des Petetes", "", "Montpellier", "France", 0, 0);
			Hotel h2 = new Hotel("Hotel Paradiso", 4, new ArrayList<>(), new Agence());
			h2.setAdresse(135, "boulevard Diderot", "", "Paris", "France", 0, 0);
			Hotel h3 = new Hotel("Beaumont House", 5, new ArrayList<>(), new Agence());
			h3.setAdresse(56, "Shurdington Road", "", "Cheltenham", "Royaume-Uni", 0, 0);
			
			// Création Agences
			Agence a1 = new Agence("Dorchies Airlines", 0.20, "da_agence", "legoat", new ArrayList<>());
			Agence a2 = new Agence("Mera Summer Camp", 0.10, "msc_agence", "peppibandit", new ArrayList<>());
			
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
			
			// Insertion Chambres
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c1));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c2));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c3));
			h1.setChambres(cal1);
			logger.info("Chambres: "+ h1.getChambreURIs());
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c4));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c5));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c6));
			h2.setChambres(cal2);
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c7));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c8));
			logger.info("Ajout de Chambre à la base de données: " + repC.save(c9));
			h3.setChambres(cal3);
			
			// Insertion Hotels
			logger.info("Ajout d'Hotel à la base de données: " + repH.save(h1));
			logger.info("Ajout d'Hotel à la base de données: " + repH.save(h2));
			a1.setHotels(hal1);
			logger.info("Ajout d'Hotel à la base de données: " + repH.save(h3));
			a2.setHotels(hal2);
			
			// Insertion Agences
			logger.info("Ajout d'Agence à la base de données: " + repA.save(a1));
			logger.info("Ajout d'Agence à la base de données: " + repA.save(a2));
		};
	}
	
}
