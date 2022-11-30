package rest.exo2.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import rest.exo2.demo.exceptions.ChambreException;
import rest.exo2.demo.models.Chambre;
import rest.exo2.demo.repositories.ChambreRepository;

public class ChambreController {

	private ChambreRepository rep;
	private static final String uri = "agenceservice/api";
	
	@GetMapping(uri+"/chambres")
	public List<Chambre> getChambres() {
		return rep.findAll();
	}
	
	@GetMapping(uri+"/chambres/{id}")
	public Chambre getChambreById(@PathVariable long id) throws ChambreException {
		return rep.findById(id)
				.orElseThrow(() -> new ChambreException(
						"Could not find Chambre of ID: " + id));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(uri+"/chambres")
	public Chambre createChambre(@RequestBody Chambre chambre) {
		return rep.save(chambre);
	}
	
	@PutMapping(uri+"chambres/{id}")
	public Chambre updateHotel(@RequestBody Chambre newChambre, @PathVariable long id) {
		return rep.findById(id)
				.map(chambre -> {
					chambre.setNumero(newChambre.getNumero());
					chambre.setNblits(newChambre.getNblits());
					chambre.setPrixnuit(newChambre.getPrixnuit());
					chambre.setHotel(newChambre.getHotel());
					return rep.save(chambre);
				})
				.orElseGet(() -> rep.save(newChambre));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(uri+"/chambres/{id}")
	public void deleteChambre(long id) throws ChambreException {
		Chambre chambre = rep.findById(id)
				.orElseThrow(() -> new ChambreException(
						"Erreur: Chambre " + id + " introuvable"));
		rep.delete(chambre);
	}
}
