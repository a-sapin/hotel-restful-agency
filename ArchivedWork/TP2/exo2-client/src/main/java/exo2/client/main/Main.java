package exo2.client.main;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import exo2.client.wsimport.HotelWebService1;
import exo2.client.wsimport.HotelWebService1ImplService;
import exo2.client.wsimport.HotelWebService2;
import exo2.client.wsimport.HotelWebService2ImplService;

public class Main {

	public static void main(String[] args) {
		try {
			ArrayList<HotelWebService1> ws1 = new ArrayList<HotelWebService1>();
			ArrayList<HotelWebService2> ws2 = new ArrayList<HotelWebService2>();
			for(int i = 0; i < 4; i++) {
				URL url1 = new URL("http://localhost:8080/" + Integer.toString(i) + "/consultation");
				HotelWebService1ImplService hws1 = new HotelWebService1ImplService(url1);
				HotelWebService1 proxy1 = hws1.getHotelWebService1ImplPort();
				ws1.add(proxy1);
				URL url2 = new URL("http://localhost:8080/" + Integer.toString(i) + "/reservation");
				HotelWebService2ImplService hws2 = new HotelWebService2ImplService(url2);
				HotelWebService2 proxy2 = hws2.getHotelWebService2ImplPort();
				ws2.add(proxy2);
			}
			Scanner input = new Scanner(System.in);
			System.out.println("Sélectionnez un hotel pour vos vacances:");
			for(int i = 0; i < 4; i++) {
				System.out.println(Integer.toString(i+1) + " - " + ws1.get(i).getHotelNom() + " - " + ws1.get(i).getHotelAdresse());
			}
			System.out.print("Votre choix: ");
			int choix = input.nextInt() - 1;
			System.out.println("\n");
			HotelWebService1 currentproxy1 = ws1.get(choix);
			HotelWebService2 currentproxy2 = ws2.get(choix);
			boolean quit = false;
			String login = "";
			String mdp = "";
			int idagence = -1;
			System.out.println("BIENVENUE A L'HOTEL: " + currentproxy1.getHotelNom().toUpperCase());
			System.out.println(currentproxy1.getHotelAdresse());
			while(!quit) {
				System.out.println("Que souhaitez-vous faire?");
				System.out.println("1 - Me connecter à une agence partenaire");
				System.out.println("2 - Faire une recherche d'offres (et en réserver une par la suite)");
				System.out.println("0 - Quitter");
				System.out.print("Votre choix: ");
				choix = input.nextInt();
				switch(choix) {
				case 1:
					System.out.print("\nLes agences partenaires de l'hôtel sont: ");
					for(int i = 0; i < currentproxy1.getHotelPartenaires().size(); i++) {
						System.out.print(currentproxy1.getHotelPartenaireNoms().get(i));
						if(i < currentproxy1.getHotelPartenaires().size() - 1) {
							System.out.print(", ");
						}
					}
					System.out.println("");
					System.out.println("Connectez-vous avec les identifiants d'une de ces agences.");
					System.out.print("Identifiant: ");
					input.nextLine(); // Fake statement
					login = input.nextLine();
					System.out.print("Mot de passe: ");
					mdp = input.nextLine();
					System.out.println("Tentative de connexion avec les données: " + login + ", " + mdp + "!!!");
					idagence = currentproxy1.connexionAgenceWS1(login, mdp);
					System.out.println("Resultat: "+ Integer.toString(idagence));
					System.out.println("Connecté à l'agence " + currentproxy1.getPartenaireNomById(idagence));
					System.out.println("");
					break;
					
				case 2:
					if(login.isEmpty() || mdp.isEmpty()) {
						System.out.println("\nPour consulter les offres de cet hôtel, connectez-vous à une agence.");
					} else {
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
						for(int i = 0; i < currentproxy1.rechercheParPartenaire(idagence, debutstring, finstring, nbpersonnes).size(); i++) {
							System.out.print(Integer.toString(currentproxy1.getOffreIdByIndex(idagence, debutstring, finstring, nbpersonnes, i)) + " - ");
							System.out.print(currentproxy1.getOffreChambreToStringByIndex(idagence, debutstring, finstring, nbpersonnes, i) + ", ");
							System.out.print("du " + currentproxy1.getOffreArriveeToStringByIndex(idagence, debutstring, finstring, nbpersonnes, i));
							System.out.print(" au " + currentproxy1.getOffreDepartToStringByIndex(idagence, debutstring, finstring, nbpersonnes, i));
							System.out.print(" pour " + Double.toString(currentproxy1.getOffrePrixByIndex(idagence, debutstring, finstring, nbpersonnes, i)) + "€");
							System.out.println("");
						}
						System.out.println("Souhaitez-vous réserver une de ces chambres?");
						System.out.println("Y - OUI   /   N - NON");
						input.nextLine(); // Fake statement, consumes \n from ENTER input
						String choixstring = input.nextLine();
						if(choixstring.equals("Y")) {
							System.out.print("Sélectionnez un numéro d'offre parmi les offres proposées: ");
							choix = input.nextInt();
							System.out.println("Vous avez choisi l'offre " + Integer.toString(choix));
							System.out.println("Renseignez vos informations client:");
							System.out.print("Nom: ");
							input.nextLine(); // Fake statement
							String nomcl = input.nextLine();
							System.out.print("Prénom: ");
							String prenomcl = input.nextLine();
							System.out.print("Numéro de carte bancaire (format XXXX-XXXX-XXXX-XXXX): ");
							String cbcl = input.nextLine();
							System.out.print("Mois d'expiration: ");
							int moiscl = input.nextInt();
							System.out.print("Année d'expiration: ");
							int anneecl = input.nextInt();
							System.out.print("Code de sécurité (format XXX): ");
							int codecl = input.nextInt();
							currentproxy2.reserver(idagence, debutstring, finstring, nbpersonnes, choix, nomcl, prenomcl, cbcl, moiscl, anneecl, codecl);
							System.out.println("Merci d'avoir réservé chez nous!\n");
						} else {
							System.out.println("");
						}
					}
					break;
					
				case 0:
					System.out.println("\nAu plaisir de vous revoir!");
					input.close();
					quit = true;
					break;
				default:
					throw new Exception("Action non reconnue");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
