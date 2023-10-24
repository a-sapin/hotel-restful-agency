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
import rest.exo2.demo.models.Client;
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
		builder.append("\n1. Display agencies");
		builder.append("\n2. Make a booking");
		
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
					System.out.println("Our agency is partner of those hotels!\n");
					String AGENCE_CHOISIE = URI_AGENCES + "/" + agencyID + "/hotels";
					Hotel[] hotArray = proxy.getForObject(AGENCE_CHOISIE, Hotel[].class);
					Arrays.asList(hotArray)
					.forEach(System.out::println);
					
					LinkedList<Chambre> roomList = new LinkedList<Chambre>();
					for (int i=0; i<hotArray.length; i++)
					{
						//System.out.println("DEBUG : hotel "+hotArray[i].getNom());
						for (int a=0; a<hotArray[i].getChambreURIs().size(); a++)
						{
							String roomURI = "http://localhost:8080/";
							roomURI = roomURI + hotArray[i].getChambreURIs().get(a);
							//System.out.println("\tDEBUG : reaching out to GET "+roomURI);
							roomList.add(proxy.getForObject(roomURI, Chambre.class));
						}
					}
					
					//System.out.println("DEBUG : Liste de chambres terminée, affinage ensuite");
					Scanner input = new Scanner(System.in);
					
					System.out.println("\nPlease tell us when you're planning to arrive:");
					System.out.print("Day: ");
					int jd = input.nextInt();
					System.out.print("Month: ");
					int md = input.nextInt();
					System.out.print("Year: ");
					int ad = input.nextInt();
					LocalDate debut = LocalDate.of(ad, md, jd);
					System.out.println("When shall you leave?");
					System.out.print("Day: ");
					int jf = input.nextInt();
					System.out.print("Month: ");
					int mf = input.nextInt();
					System.out.print("Year: ");
					int af = input.nextInt();
					LocalDate fin = LocalDate.of(af, mf, jf);
					System.out.print("For how many people are you booking? ");
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
							//System.out.println("DEBUG : looking at room " + r.getNumero());
							String resURI = "http://localhost:8080/";
							LinkedList<Reservation> resList = new LinkedList<Reservation>();
							for (String ru : r.getReservationURIs()) {
								//Add reservation fetched at that address
								//System.out.println("\tDEBUG : found reservation " + resURI + ru);
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
							
							Thread.sleep(2500);
							System.out.println("\nThis list will be sorted by our comparator service to ensure you get the best deals!\n");
							Thread.sleep(2000);
							int OPS = 1;
							while (OPS>0)
							{
								OPS=0;
								for (int u=0; u<shortList.size(); u++)
								{
									if(u>0)
									{
										if (shortList.get(u).getPrixnuit()<shortList.get(u-1).getPrixnuit())
										{
											Chambre remember = shortList.get(u-1);
											shortList.set(u-1, shortList.get(u));
											shortList.set(u, remember);
											OPS=5;
										}
									}
								}
							}
							
							for (Chambre f : shortList) 
							{
								System.out.print("\t"+f.toString() + " : ");
								double coef = 1 - agency.getReduc();
								double price = f.getPrixnuit() * ChronoUnit.DAYS.between(debut, fin) * coef;
								System.out.println(price + " €");
							}
							
							
							System.out.println("\nPlease enter now the ID of the room you would like to book.");
							long choice = input.nextInt();
							Chambre retenue = null;
							for (Chambre ret : shortList)
							{
								if (ret.getId().equals(choice)) retenue = ret;
							}
							if (retenue == null) System.err.println("Sorry, but we couldn't find the room you specified.");
							else
							{
								Scanner textRead = new Scanner(System.in);
								System.out.println("Who's going to stay in that room?");
								System.out.println("Name : ");
								String nc = textRead.nextLine();
								System.out.println("Surname : ");
								String sn = textRead.nextLine();
								Client cl = new Client(nc, sn);
								String createURI = "http://localhost:8080/" + "agenceservice/api/clients";
								proxy.postForObject(createURI, cl, Reservation.class);

								//Fetch the created client online to get its webDB ID
								Client[] arrayCli = proxy.getForObject(createURI, Client[].class);
								for (int z=0; z<arrayCli.length; z++)
								{
									Client cur = arrayCli[z];
									if ((cur.getPrenom().equals(cl.getPrenom()) && (cur.getNom().equals(cl.getNom()))))
									{
										cl = cur;
										z = z + arrayCli.length;
									}
								}
								
								Reservation resFin = new Reservation(retenue, cl, debut, fin, retenue.getHotel());
								createURI = "http://localhost:8080/" + "agenceservice/api/reservations";
								proxy.postForObject(createURI, resFin, Reservation.class);
								
								//Fetch the created reservation online to get its webDB ID
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									e.printStackTrace();
								} //wait for item to be added to server
								
								Reservation[] arrayRes = proxy.getForObject(createURI, Reservation[].class);
								for (int y=0; y<arrayRes.length; y++)
								{
									//System.out.println("\tDEBUG ITERATION "+y);
									Reservation curt = arrayRes[y];
									curt.toString();
									if (resFin.getClient().getNom().equals(curt.getClient().getNom()))
									{
										resFin = curt;
										y = y + arrayRes.length;
									}
								}
								
								//Trying to update the Room
								//retenue.getReservations().add(resFin);
								String resIDURI = "http://localhost:8080/" + "agenceservice/api/chambres/" + retenue.getId();
								String shortndURI = "agenceservice/api/reservations/" + resFin.getId();
								retenue.getReservationURIs().add(shortndURI);
								//System.out.println(retenue.getReservationURIs().isEmpty());
								
								proxy.put(resIDURI, retenue);
								cl.getReservationURIs().add(shortndURI);
								resIDURI = "http://localhost:8080/" + "agenceservice/api/clients/" + cl.getId();
								proxy.put(resIDURI, cl);
								
								Hotel hot = retenue.getHotel();
								hot.getReservationURIs().add(shortndURI);
								resIDURI = "http://localhost:8080/" + "agenceservice/api/hotels/" + hot.getId();
								proxy.put(resIDURI, hot);
								
								
								System.out.println("\nThe booking is confirmed and saved to the database! Have a good stay :)\n");
							}
							
						}
						else System.err.println("We're very sorry, but we didn't find any rooms matching your requirements.");
						
					}
				
				
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
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
