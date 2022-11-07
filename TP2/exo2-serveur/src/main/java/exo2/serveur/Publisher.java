package exo2.serveur;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.ws.Endpoint;

public class Publisher {

	public static void main(String[] args) {
		
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		hotels.addAll(Arrays.asList(
				new Hotel(),
				new Hotel()
		));
		
		for (Hotel hotel : hotels) {
			Endpoint.publish("http://localhost:8080/" + Integer.toString(hotel.hashCode()) + "/reservation", new HotelWebService1Impl(hotel));
			Endpoint.publish("http://localhost:8080/" + Integer.toString(hotel.hashCode()) + "/consultation", new HotelWebService1Impl(hotel));
		}

	}

}
