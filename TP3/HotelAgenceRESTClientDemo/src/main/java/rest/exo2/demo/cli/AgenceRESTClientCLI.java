package rest.exo2.demo.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

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
	private static String SERVICE_URL; //Read from inputReader
	private static String URI_AGENCES; 
	private static String URI_AGENCES_ID;

	@Override
	public void run(String... args) throws Exception {
		BufferedReader inputReader;
		String userInput = "";
		
		System.err.println("DEBUG PRINT : initialising BufferedReader");
		
		try {
			inputReader = new BufferedReader(new InputStreamReader(System.in));
			SERVICE_URL = inputReader.readLine(); //Using buffered reader input as value for SERVICE_URL
			
			URI_AGENCES = SERVICE_URL + "/employees";
			URI_AGENCES_ID = URI_AGENCES + "/{id}";
		} catch (IOException e) {
			e.printStackTrace();
		}	//Second catch block is FAULTY 
		/*catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	
	private void processUserInput(BufferedReader reader, String userInput, RestTemplate proxy)
	{
		try {
				//A NE PAS GARDER CT POUR LE CATCH
				if (false) throw new IOException();
				// /!\
			switch(userInput) {
			case "1":
				String uri = URI_AGENCES + "/count";
				String countStr = proxy.getForObject(URI_AGENCES, String.class);
				ObjectMapper mapper = new ObjectMapper();
				mapper.readValue(countStr, Map.class);
				
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
