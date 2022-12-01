package rest.exo2.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rest.exo2.demo.exceptions.HotelException;
import rest.exo2.demo.models.Chambre;
import rest.exo2.demo.models.Hotel;
import rest.exo2.demo.models.Reservation;
import rest.exo2.demo.repositories.HotelRepository;

@RestController
public class HotelController {

	@Autowired
	private HotelRepository rep;
	private static final String uri = "agenceservice/api";
	
	@GetMapping(uri+"/hotels")
	public List<Hotel> getHotels() {
		return rep.findAll();
	}
	
	@GetMapping(uri+"/hotels/{id}")
	public Hotel getHotelById(@PathVariable long id) throws HotelException {
		return rep.findById(id)
				.orElseThrow(() -> new HotelException(
						"Could not find Hotel of ID: " + id));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(uri+"/hotels")
	public Hotel createHotel(@RequestBody Hotel hotel) {
		return rep.save(hotel);
	}
	
	@PutMapping(uri+"hotels/{id}")
	public Hotel updateHotel(@RequestBody Hotel newHotel, @PathVariable long id) {
		return rep.findById(id)
				.map(hotel -> {
					hotel.setNom(newHotel.getNom());
					hotel.setPays(newHotel.getPays());
					hotel.setVille(newHotel.getVille());
					hotel.setRue(newHotel.getRue());
					hotel.setNumero(newHotel.getNumero());
					hotel.setLieudit(newHotel.getLieudit());
					hotel.setGps(newHotel.getGps());
					hotel.setEtoiles(newHotel.getEtoiles());
					hotel.setChambres((ArrayList<Chambre>)newHotel.getChambres());
					hotel.setReservations((ArrayList<Reservation>)newHotel.getReservations());
					hotel.setAgence(newHotel.getAgence());
					return rep.save(hotel);
				})
				.orElseGet(() -> rep.save(newHotel));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(uri+"/hotels/{id}")
	public void deleteHotel(long id) throws HotelException {
		Hotel hotel = rep.findById(id)
				.orElseThrow(() -> new HotelException(
						"Erreur: hotel " + id + " introuvable"));
		rep.delete(hotel);
	}
}
