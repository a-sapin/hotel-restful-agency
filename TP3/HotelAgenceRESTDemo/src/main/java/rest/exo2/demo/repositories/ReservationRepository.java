package rest.exo2.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.exo2.demo.models.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	public List<Reservation> findByHotelId(Long id);
	
	public List<Reservation> findByChambreId(Long id);
	
	public List<Reservation> findByClientId(Long id);

}
