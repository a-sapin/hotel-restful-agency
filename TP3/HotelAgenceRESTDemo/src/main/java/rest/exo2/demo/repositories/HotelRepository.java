package rest.exo2.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.exo2.demo.models.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
