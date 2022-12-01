package rest.exo2.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.exo2.demo.models.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
