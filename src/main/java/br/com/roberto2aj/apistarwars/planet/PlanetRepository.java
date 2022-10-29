package br.com.roberto2aj.apistarwars.planet;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Integer>{

	Optional<Planet> findByName(String name);

}
