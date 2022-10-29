package br.com.roberto2aj.apistarwars.planet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.roberto2aj.apistarwars.exceptions.PlanetNotFoundException;

@Component
public class PlanetService {

	@Autowired
	private SwapiClient api;

	@Autowired
	private PlanetRepository repository;

	public PlanetDto loadPlanet(Integer id) {
		Planet planet = repository.findById(id)
				.orElse(loadPlanetFromSwapi(id));
		return convertToDto(planet);
	}

	private Planet loadPlanetFromSwapi(Integer id) {
		var planet = api.loadPlanet(id);
		planet.setId(id);
		repository.save(planet);
		return planet;
	}

	public PlanetDto findPlanetById(Integer id) {
		return repository.findById(id)
				.map(this::convertToDto)
				.orElseThrow(() -> new PlanetNotFoundException());
	}

	public PlanetDto findPlanetByName(String name) {
		return repository.findByName(name)
				.map(this::convertToDto)
				.orElseThrow(() -> new PlanetNotFoundException());
	}

	public List<PlanetDto> findAllPlanets() {
		return repository.findAll()
				.stream()
				.map(this::convertToDto)
				.toList();
	}

	// I have use 'deleteAllById' instead of 'deleteById'
	// because 'deleteById' throws an exception if the entity doesn't exist.
	// I could check it before deletion, but then I would need to open a transaction
	// which would add uneeded complexity.
	public void deletePlanet(Integer id) {
		repository.deleteAllById(List.of(id));
	}

	private PlanetDto convertToDto(Planet planet) {
		return new PlanetDto(planet.getName(), planet.getClimate(), planet.getTerrain());
	}
}
