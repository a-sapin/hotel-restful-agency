package rest.exo2.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.exo2.demo.models.Chambre;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {

}
