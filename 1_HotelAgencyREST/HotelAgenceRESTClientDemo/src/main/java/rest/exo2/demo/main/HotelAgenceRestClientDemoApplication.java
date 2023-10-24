package rest.exo2.demo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"rest.exo2.demo.models",
		"rest.exo2.demo.client",
		"rest.exo2.demo.cli"
})
public class HotelAgenceRestClientDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelAgenceRestClientDemoApplication.class, args);
	}

}
