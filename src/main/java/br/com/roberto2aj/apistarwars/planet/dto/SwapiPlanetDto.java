package br.com.roberto2aj.apistarwars.planet.dto;

import java.util.ArrayList;
import java.util.List;

public class SwapiPlanetDto {

	private String name;

	private String climate;

	private String terrain;

	private List<String> films;

	public SwapiPlanetDto() {
		films = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}

}
