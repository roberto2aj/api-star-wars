package br.com.roberto2aj.apistarwars.planet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanetService {

	@Autowired
	private SwapiApi api;

	public Planet loadPlanet(Integer id) {
		return api.loadPlanet(id);
	}
}
