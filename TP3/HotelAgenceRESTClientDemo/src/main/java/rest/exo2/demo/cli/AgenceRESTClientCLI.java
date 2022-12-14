package rest.exo2.demo.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import rest.exo2.demo.example.Employee;
import rest.exo2.demo.models.Agence;
import rest.exo2.demo.models.Chambre;
import rest.exo2.demo.models.Hotel;
import rest.exo2.demo.models.Reservation;

@Component
public class AgenceRESTClientCLI extends AbstractMain implements CommandLineRunner {
	
	@Autowired
	private RestTemplate proxy;
	private IntegerInputProcessor inputProcessor;
	private static String URI_AGENCES; 
	private static String URI_AGENCES_ID;
	public Agence agency;
	public String agencyID;

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
			
			Boolean agencyFound = false;
			System.out.println("\n[AGENCY WEBSERVICE]\n==============\nWelcome. You must log into your agency.");
			
			while (!agencyFound)
			{

				Scanner scanReader = new Scanner(System.in);
				System.out.println("Please enter your login information:");
				String log = scanReader.nextLine();
				System.out.println("Please enter your password:");
				String pswd = scanReader.nextLine();
				
				Agence[] resultAg = proxy.getForObject(URI_AGENCES, Agence[].class);
	
				
				for (int i=0; i<resultAg.length; i++)
				{
					if (resultAg[i].getLogin().equals(log) && resultAg[i].getMdp().equals(pswd))
					{
						agencyFound = true;
						agency = resultAg[i];
						agencyID = agency.getId().toString();
						System.out.println("Successful connection to Agency "+resultAg[i].getNom()+" #"+agencyID);
						
					}
				}
				if (agencyFound==false) System.out.println("No agency was found, please try again.\n");
			}
			
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
		builder.append("\n2. Fetch all hotels.");
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
					Agence[] resultAg = proxy.getForObject(uri, Agence[].class);
					System.out.println(":");
					Arrays.asList(resultAg)
					.forEach(System.out::println);
					break;
				case "2":
					System.out.println("Fetching all hotels of the agency.\n");
					String AGENCE_CHOISIE = URI_AGENCES + "/" + agencyID + "/hotels";
					Hotel[] hotArray = proxy.getForObject(AGENCE_CHOISIE, Hotel[].class);
					Arrays.asList(hotArray)
					.forEach(System.out::println);
					
					LinkedList<Chambre> roomList = new LinkedList<Chambre>();
					for (int i=0; i<hotArray.length; i++)
					{
						System.out.println("DEBUG : hotel "+hotArray[i].getNom());
						for (int a=0; a<hotArray[i].getChambreURIs().size(); a++)
						{
							String roomURI = "http://localhost:8080/";
							roomURI = roomURI + hotArray[i].getChambreURIs().get(a);
							System.out.println("\tDEBUG : reaching out to GET "+roomURI);
							roomList.add(proxy.getForObject(roomURI, Chambre.class));
						}
					}
					
					System.out.println("DEBUG : Liste de chambres terminée, affinage ensuite");
					Scanner input = new Scanner(System.in);
					
					System.out.println("\nEntrez votre date de début de séjour à l'hôtel:");
					System.out.print("Jour: ");
					int jd = input.nextInt();
					System.out.print("Mois: ");
					int md = input.nextInt();
					System.out.print("Année: ");
					int ad = input.nextInt();
					LocalDate debut = LocalDate.of(ad, md, jd);
					System.out.println("Entrez votre date de fin de séjour à l'hôtel:");
					System.out.print("Jour: ");
					int jf = input.nextInt();
					System.out.print("Mois: ");
					int mf = input.nextInt();
					System.out.print("Année: ");
					int af = input.nextInt();
					LocalDate fin = LocalDate.of(af, mf, jf);
					System.out.print("Entrez le nombre de personnes qu'accueillera la chambre: ");
					int nbpersonnes = input.nextInt();
					String debutstring = debut.toString();
					String finstring = fin.toString();
					//==============
					
					Boolean validChoices = true;
					
					if (!(fin.isAfter(debut)))
					{
						validChoices = false;
						System.err.println("Sorry, it looks like you've entered wrong dates. Try again.");
					}
					
					if (validChoices) 
					{
						LinkedList<Chambre> shortList = new LinkedList<Chambre>();
						for (Chambre r : roomList) {
							System.out.println("DEBUG : looking at room " + r.getNumero());
							String resURI = "http://localhost:8080/";
							LinkedList<Reservation> resList = new LinkedList<Reservation>();
							for (String ru : r.getReservationURIs()) {
								//Add reservation fetched at that address
								System.out.println("\tDEBUG : found reservation " + resURI + ru);
								resList.add(proxy.getForObject(resURI + ru, Reservation.class));
							}
	
							//Look up all the reservations that we added
							Boolean available = true;
							for (Reservation rs : resList) {
								if (!((debut.isBefore(rs.getArrivee()) && fin.isBefore(rs.getArrivee()))
										|| (debut.isAfter(rs.getDepart()) && fin.isAfter(rs.getDepart()))))
									available = false;
							}
							
							if (nbpersonnes>r.getNblits()) available = false;
							if (available)
								shortList.add(r);
						}
						
						if (!(shortList.isEmpty()))
						{
							System.out.println("=RESULT= We found " + shortList.size() + " available rooms for you!");
							for (Chambre f : shortList) 
							{
								System.out.print(f.toString() + " : ");
								double coef = 1 - agency.getReduc();
								double price = f.getPrixnuit() * ChronoUnit.DAYS.between(debut, fin) * coef;
								System.out.println(price + " € for " + ChronoUnit.DAYS.between(debut, fin) + " days, with "
										+ agency.getReduc() * 100 + "% off");
							}
						}
						else System.err.println("We're very sorry, but we didn't find any rooms matching your requirements.");
						
					}
				
				
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
