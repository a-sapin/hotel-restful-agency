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

import rest.exo2.demo.exceptions.AgenceException;
import rest.exo2.demo.models.Agence;
import rest.exo2.demo.repositories.AgenceRepository;

@RestController
public class AgenceController {
	
	@Autowired
	private AgenceRepository rep;
	private static final String uri = "agenceservice/api";
	
	@GetMapping(uri+"/agences")
	public List<Agence> getAllAgences() {
		return rep.findAll();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(uri+"/agences")
	public Agence createAgence(@RequestBody Agence agence) {
		return rep.save(agence);
	}
	
	@PutMapping(uri+"/agences/{id}")
	public Agence updateAgence(@RequestBody Agence newAgence, @PathVariable long id) {
		return rep.findById(id)
				.map(agence -> {
					agence.setNom(newAgence.getNom());
					agence.setReduc(newAgence.getReduc());
					agence.setLogin(newAgence.getLogin());
					agence.setMdp(newAgence.getMdp());
					return rep.save(agence);
				})
				.orElseGet(() -> rep.save(newAgence));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(uri+"/agences/{id}")
	public void deleteAgence(long id) throws AgenceException {
		Agence agence = rep.findById(id)
				.orElseThrow(() -> new AgenceException(
						"Erreur: agence " + id + " introuvable"));
		rep.delete(agence);
	}
	
	// Add methods here but I'm not sure what to get
}
