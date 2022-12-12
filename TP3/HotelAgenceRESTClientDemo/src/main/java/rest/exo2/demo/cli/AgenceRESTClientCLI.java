package rest.exo2.demo.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rest.exo2.demo.example.Employee;
import rest.exo2.demo.models.Agence;

@Component
public class AgenceRESTClientCLI extends AbstractMain implements CommandLineRunner {
	
	@Autowired
	private RestTemplate proxy;
	private IntegerInputProcessor inputProcessor;
	private static String URI_AGENCES; 
	private static String URI_AGENCES_ID;

	@Override
	public void run(String... args) throws Exception {
		BufferedReader inputReader;
		String userInput = "";
		
		try {
			inputReader = new BufferedReader(
					new InputStreamReader(System.in));
			setTestServiceUrl(inputReader);
			URI_AGENCES = SERVICE_URL + "/agences";
			URI_AGENCES_ID = URI_AGENCES + "/{id}"; 
			
			do {
				menu();
				userInput = inputReader.readLine();
				processUserInput(inputReader, userInput, proxy);
				Thread.sleep(3000);
				
			} while(!userInput.equals(QUIT));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected boolean validServiceUrl() {
		return SERVICE_URL.equals(
				"http://localhost:8080/agenceservice/api");
	}

	@Override
	protected void menu() {
		StringBuilder builder = new StringBuilder();
		builder.append(QUIT+". Quit.");
		builder.append("\n1. Count agencies.");
		builder.append("\n2. Display all employees.");
		builder.append("\n3. Get employee by ID");
		builder.append("\n4. Add new employee");
		builder.append("\n5. Remove employee by ID");
		builder.append("\n6. Update existing employee");
		
		System.out.println(builder);
	}
	
	private void processUserInput(BufferedReader reader, 
			String userInput, RestTemplate proxy) {
		Map<String, String> params = new HashMap<>();
		inputProcessor = new IntegerInputProcessor(reader);
		try {
			switch(userInput) {
				case "1":
					String uri = URI_AGENCES;
					Agence[] result = proxy.getForObject(uri, Agence[].class);
					System.out.println("Employees:");
					Arrays.asList(result)
					.forEach(System.out::println);
					break;
				case "2":
					System.out.println("Testificate 2");
					break;
				case "3":
					System.out.println("Testificate 3");
					break;
					
				case "4":
					System.out.println("Testificate 4");
					break;
					
				case "5":
					System.out.println("Testificate 5");
					break;
					
				case "6":
					System.out.println("Testificate 6");
					break;
					
				case QUIT:
					System.out.println("Bye...");
					System.exit(0);
				
				default:
					System.err.println("Sorry, wrong input. Please try again.");
					return;
			} 
		//} catch (IOException e) {
			//e.printStackTrace();
		} catch (HttpClientErrorException e) {
			System.err.println(e.getMessage());
			System.err.println("Please try again with a different ID.");
		}
	}
	
	/*
	Voir les fichiers dans le package rest.exo2.demo.cli pour un exemple plus parlant
	
	Idée de conception pour le client (options de menu):
	1. Se connecter à une agence:
		- faire un get d'Agence selon login et mdp
		- si pas d'Agence trouvée retour au menu
		- sinon on garde l'id de l'Agence dans une variable
	2. Consulter les offres:
		- d'abord se connecter à une agence (via option 1.)
		- lire l'input utilisateur pour plusieurs informations dont: nombre de lits, date de début de séjour, date de fin de séjour
		- faire un get des Chambres correspondantes
		- afficher une liste d'offres (prix: prixnuit Chambre * durée de séjour * réduc Agence), puis demander l'input utilisateur:
		a) effectuer une réservation
			- choisir l'Offre proposée
			- lire l'input utilisateur pour créer un client de la Reservation: nom, prénom, numéro de carte, mois d'expiration, année d'expiration, code de sécurité
			- envoyer la réservation (méthode put)
		b) trier les prix par ordre croissant
			- à coder dans ce fichier: méthode de tri des Offres
			- mettre en avant le prix le moins cher
			- proposer de réserver (via option a))
	*/

}
