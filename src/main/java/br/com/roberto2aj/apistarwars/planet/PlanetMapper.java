package br.com.roberto2aj.apistarwars.planet;

import java.util.List;

import br.com.roberto2aj.apistarwars.film.Film;
import br.com.roberto2aj.apistarwars.film.dto.FilmDto;
import br.com.roberto2aj.apistarwars.film.dto.SwapiFilmDto;
import br.com.roberto2aj.apistarwars.planet.dto.PlanetDto;
import br.com.roberto2aj.apistarwars.planet.dto.SwapiPlanetDto;

public class PlanetMapper {

	public static Planet convertToEntity(SwapiPlanetDto dto, Integer id) {
		Planet planet = new Planet();
		planet.setId(id);
		planet.setName(dto.getName());
		planet.setClimate(dto.getClimate());
		planet.setTerrain(dto.getTerrain());
		return planet;
	}

	public static PlanetDto convertToDto(Planet planet) {
		List<FilmDto> filmDtos = planet.getFilms().stream().map(PlanetMapper::convertToDto).toList();
		return new PlanetDto(planet.getName(), planet.getClimate(), planet.getTerrain(), filmDtos);
	}

	public static FilmDto convertToDto(Film film) {
		return new FilmDto(film.getTitle(), film.getDirector(), film.getReleaseDate());
	}

	public static Film convertToEntity(SwapiFilmDto dto, Integer id) {
		Film film = new Film();
		film.setId(id);
		film.setDirector(dto.getDirector());
		film.setReleaseDate(dto.getReleaseDate());
		film.setTitle(dto.getTitle());
		return film;
	}

}
