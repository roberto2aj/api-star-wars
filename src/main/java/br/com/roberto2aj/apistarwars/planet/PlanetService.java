package br.com.roberto2aj.apistarwars.planet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanetService {

	@Autowired
	private SwapiApi api;

	@Autowired
	private PlanetRepository repository;

	public Planet loadPlanet(Integer id) {
		var planet = api.loadPlanet(id);
		planet.setId(id);
		repository.save(planet);
		return planet;
	}
}
