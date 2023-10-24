package rest.exo2.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.exo2.demo.models.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	
	public List<Hotel> findByAgenceId(Long id);

}
