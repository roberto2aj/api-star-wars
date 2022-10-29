package br.com.roberto2aj.apistarwars.planet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.roberto2aj.apistarwars.exceptions.PlanetNotFoundException;

@Component
public class PlanetService {

	@Autowired
	private SwapiClient api;

	@Autowired
	private PlanetRepository repository;

	public Planet loadPlanet(Integer id) {
		return repository.findById(id)
				.orElse(loadPlanetFromSwapi(id));
	}

	private Planet loadPlanetFromSwapi(Integer id) {
		var planet = api.loadPlanet(id);
		planet.setId(id);
		repository.save(planet);
		return planet;
	}

	public Planet findPlanet(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new PlanetNotFoundException());
	}

}
