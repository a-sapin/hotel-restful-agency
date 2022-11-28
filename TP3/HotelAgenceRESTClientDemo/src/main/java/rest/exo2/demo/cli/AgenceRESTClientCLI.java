package rest.exo2.demo.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import rest.exo2.demo.models.Agence;

@Component
public class AgenceRESTClientCLI implements CommandLineRunner {
	
	@Autowired
	private RestTemplate proxy;
	private static String URI_AGENCES; 
	private static String URI_AGENCES_ID;

	@Override
	public void run(String... args) throws Exception {
		BufferedReader inputReader;
		String userInput = "";
		
		try {
			inputReader = new BufferedReader(new InputStreamReader(System.in));
			//setTestServiceUrl(inputReader);
			URI_AGENCES = SERVICE_URL + "/employees";
			URI_AGENCES_ID = URI_AGENCES + "/{id}";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void processUserInput(BufferedReader reader, String userInput, RestTemplate proxy) {
		try {
			switch(userInput) {
			case "1":
				String uri = URI_AGENCES + "/count";
				ObjectMapper mapper = new ObjectMapper();
				break;
			case "2":
				uri = URI_AGENCES;
				Agence[] agences = proxy.getForObject(uri, Agence[].class);
				System.out.println("Agences:");
				Arrays.asList(agences)
				.forEach(System.out::println);
				System.out.println();
				break;
			case "3":
				uri = URI_AGENCES_ID;
				System.out.println("Agence ID:");
				
				break;
			case "4":
				break;
			case "5":
				break;
			case "6":
				break;
			case "QUIT":
				System.out.println("Au revoir!");
				break;
			default:
				System.err.println("Entrée invalide, veuillez réessayer");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
