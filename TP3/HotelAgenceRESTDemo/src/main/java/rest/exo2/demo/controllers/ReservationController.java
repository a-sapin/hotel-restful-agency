package rest.exo2.demo.controllers;

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

import rest.exo2.demo.exceptions.ReservationException;
import rest.exo2.demo.models.Reservation;
import rest.exo2.demo.repositories.ReservationRepository;

@RestController
public class ReservationController {

	@Autowired
	private ReservationRepository rep;
	private static final String uri = "agenceservice/api/reservations";
	
	@GetMapping(uri)
	public List<Reservation> getReservations() {
		return rep.findAll();
	}
	
	@GetMapping(uri+"/{id}")
	public Reservation getReservationById(@PathVariable long id) throws ReservationException {
		return rep.findById(id)
				.orElseThrow(() -> new ReservationException(
						"Could not find Chambre of ID: " + id));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(uri)
	public Reservation createReservation(@RequestBody Reservation reservation) {
		return rep.save(reservation);
	}
	
	@PutMapping(uri+"reservations/{id}")
	public Reservation updateReservation(@RequestBody Reservation newReservation, @PathVariable long id) {
		return rep.findById(id)
				.map(reservation -> {
					reservation.setChambre(newReservation.getChambre());
					reservation.setClient(newReservation.getClient());
					reservation.setArrivee(newReservation.getArrivee());
					reservation.setDepart(newReservation.getDepart());
					reservation.setHotel(newReservation.getHotel());
					return rep.save(reservation);
				})
				.orElseGet(() -> rep.save(newReservation));
	}
	
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	@DeleteMapping(uri+"/{id}")
//	public void deleteReservation(long id) throws ReservationException {
//		Reservation reservation = rep.findById(id)
//				.orElseThrow(() -> new ReservationException(
//						"Erreur: Reservation " + id + " introuvable"));
//		rep.delete(reservation);
//	}
}
