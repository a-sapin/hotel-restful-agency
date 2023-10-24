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

import rest.exo2.demo.exceptions.ChambreException;
import rest.exo2.demo.models.Chambre;
import rest.exo2.demo.models.Reservation;
import rest.exo2.demo.repositories.ChambreRepository;
import rest.exo2.demo.repositories.ReservationRepository;

@RestController
public class ChambreController {

	@Autowired
	private ChambreRepository rep;
	@Autowired
	private ReservationRepository repR;
	private static final String uri = "agenceservice/api/chambres";
	
	@GetMapping(uri)
	public List<Chambre> getChambres() {
		return rep.findAll();
	}
	
	@GetMapping(uri+"/{id}")
	public Chambre getChambreById(@PathVariable long id) throws ChambreException {
		return rep.findById(id)
				.orElseThrow(() -> new ChambreException(
						"Could not find Chambre of ID: " + id));
	}
	
//	@ResponseStatus(HttpStatus.CREATED)
//	@PostMapping(uri)
//	public Chambre createChambre(@RequestBody Chambre chambre) {
//		return rep.save(chambre);
//	}
	
	@PutMapping(uri+"/{id}")
	public Chambre updateChambre(@RequestBody Chambre newChambre, @PathVariable long id) {
		return rep.findById(id)
				.map(chambre -> {
					chambre.setNumero(newChambre.getNumero());
					chambre.setNblits(newChambre.getNblits());
					chambre.setPrixnuit(newChambre.getPrixnuit());
					chambre.setHotel(newChambre.getHotel());
					
					chambre.setReservationURIs(newChambre.getReservationURIs());
					return rep.save(chambre);
				})
				.orElseGet(() -> rep.save(newChambre));
	}
	
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	@DeleteMapping(uri+"/{id}")
//	public void deleteChambre(long id) throws ChambreException {
//		Chambre chambre = rep.findById(id)
//				.orElseThrow(() -> new ChambreException(
//						"Erreur: Chambre " + id + " introuvable"));
//		rep.delete(chambre);
//	}
	
	@GetMapping(uri+"/{id}/reservations")
	public List<Reservation> getReservationFromChambreId(@PathVariable Long id) {
		return repR.findByChambreId(id);
	}
}
