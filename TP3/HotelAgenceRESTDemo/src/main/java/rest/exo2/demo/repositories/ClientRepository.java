package rest.exo2.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.exo2.demo.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
