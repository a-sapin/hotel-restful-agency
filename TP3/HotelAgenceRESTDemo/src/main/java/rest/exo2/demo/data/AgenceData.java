package rest.exo2.demo.data;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rest.exo2.demo.models.Agence;
import rest.exo2.demo.models.Hotel;
import rest.exo2.demo.repositories.AgenceRepository;

@Configuration
public class AgenceData {
	
	private Logger logger = LoggerFactory.getLogger(AgenceData.class);

	@Bean
	public CommandLineRunner initDatabase(AgenceRepository rep) {
		return args -> {
			logger.info("Preloading database with " + rep.save(
					new Agence("Dorchies Airlines", 0.20, "da_agence", "legoat", new ArrayList<Hotel>())));
			logger.info("Preloading database with " + rep.save(
					new Agence("Mera Summer Camp", 0.10, "msc_agence", "peppibandit", new ArrayList<Hotel>())));
		};
	}
}
