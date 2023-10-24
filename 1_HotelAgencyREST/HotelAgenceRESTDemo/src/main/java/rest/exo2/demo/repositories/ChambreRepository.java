package rest.exo2.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.exo2.demo.models.Chambre;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
	
	public List<Chambre> findByHotelId(Long id);

}
