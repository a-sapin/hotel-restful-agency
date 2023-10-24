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

import rest.exo2.demo.exceptions.AgenceException;
import rest.exo2.demo.models.Agence;
import rest.exo2.demo.models.Hotel;
import rest.exo2.demo.repositories.AgenceRepository;
import rest.exo2.demo.repositories.HotelRepository;

@RestController
public class AgenceController {
	
	@Autowired
	private AgenceRepository rep;
	@Autowired
	private HotelRepository repH;
	private static final String uri = "agenceservice/api/agences";
	
	@GetMapping(uri)
	public List<Agence> getAllAgences() {
		return rep.findAll();
	}
	
	@GetMapping(uri+"/{id}")
	public Agence getAgenceById(@PathVariable long id) throws AgenceException
	{
		return rep.findById(id)
				.orElseThrow(() -> new AgenceException(
						"ERR : Could not find Agency with that ID :"+id));
	}
	
//	@ResponseStatus(HttpStatus.CREATED)
//	@PostMapping(uri)
//	public Agence createAgence(@RequestBody Agence agence) {
//		return rep.save(agence);
//	}
	
	@PutMapping(uri+"/{id}")
	public Agence updateAgence(@RequestBody Agence newAgence, @PathVariable long id) {
		return rep.findById(id)
				.map(agence -> {
					agence.setNom(newAgence.getNom());
					agence.setReduc(newAgence.getReduc());
					agence.setLogin(newAgence.getLogin());
					agence.setMdp(newAgence.getMdp());
					agence.setHotels((ArrayList<Hotel>)newAgence.getHotels());
					return rep.save(agence);
				})
				.orElseGet(() -> rep.save(newAgence));
	}
	
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	@DeleteMapping(uri+"/{id}")
//	public void deleteAgence(@PathVariable long id) throws AgenceException {
//		Agence agence = rep.findById(id)
//				.orElseThrow(() -> new AgenceException(
//						"Erreur: agence " + id + " introuvable"));
//		rep.delete(agence);
//	}
	
	@GetMapping(uri+"/{id}/hotels")
	public List<Hotel> getHotelFromAgencyId(@PathVariable Long id) {
		return repH.findByAgenceId(id);
	}
	
}
