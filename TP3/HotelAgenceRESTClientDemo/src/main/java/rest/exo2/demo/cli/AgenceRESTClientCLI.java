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
public class AgenceRESTClientCLI extends AbstractMain implements CommandLineRunner {
	
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
			
			URI_AGENCES = SERVICE_URL + "/agences";
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
			case "1": // Connexion à une agence
				String uri = URI_AGENCES + "/count";
				String countStr = proxy.getForObject(URI_AGENCES, String.class);
				ObjectMapper mapper = new ObjectMapper();
				mapper.readValue(countStr, Map.class);
				
				break;
			case "2": // 
				uri = URI_AGENCES;
				Agence[] agences = proxy.getForObject(uri, Agence[].class);
				System.out.println("Agences:");
				Arrays.asList(agences)
				.forEach(System.out::println);
				System.out.println();
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

	@Override
	protected boolean validServiceUrl() {
		// TODO Auto-generated method stub
		return SERVICE_URL.equals(
				"http://localhost:8080/agenceservice/api");
	}

	@Override
	protected void menu() {
		// TODO Auto-generated method stub
		
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
