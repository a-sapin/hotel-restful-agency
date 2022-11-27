package rest.exo2.demo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
		"rest.exo2.demo.models"
})
@EnableJpaRepositories(basePackages = {
		"rest.exo2.demo.repositories"
})
@SpringBootApplication(scanBasePackages = {
		"rest.exo2.demo.data",
		"rest.exo2.demo.exceptions",
		"rest.exo2.demo.controllers"
})
public class HotelAgenceRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelAgenceRestDemoApplication.class, args);
	}

}
