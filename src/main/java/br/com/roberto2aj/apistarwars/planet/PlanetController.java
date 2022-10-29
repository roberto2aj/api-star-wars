package br.com.roberto2aj.apistarwars.planet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanetController {

	@Autowired
	private PlanetService service;

	@GetMapping("{id}")
	public Planet loadPlanet(@PathVariable Integer id) {
		return service.loadPlanet(id);
	}
}
