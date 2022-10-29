package br.com.roberto2aj.apistarwars.planet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.roberto2aj.apistarwars.exceptions.PlanetNotFoundException;
import br.com.roberto2aj.apistarwars.film.Film;
import br.com.roberto2aj.apistarwars.film.dto.FilmDto;
import br.com.roberto2aj.apistarwars.film.dto.SwapiFilmDto;
import br.com.roberto2aj.apistarwars.planet.dto.PlanetDto;
import br.com.roberto2aj.apistarwars.planet.dto.SwapiPlanetDto;
import br.com.roberto2aj.apistarwars.swapi.SwapiClient;

@Component
public class PlanetService {

	@Autowired
	private SwapiClient api;

	@Autowired
	private PlanetRepository repository;

	public PlanetDto loadPlanet(Integer id) {
		Optional<Planet> planetOpt = repository.findById(id);
		if (planetOpt.isPresent()) {
			return convertToDto(planetOpt.get());
		}
		return convertToDto(loadPlanetFromSwapi(id));
	}

	private Planet loadPlanetFromSwapi(Integer id) {
		SwapiPlanetDto dto = api.loadPlanet(id);
		Planet planet = convertToEntity(dto, id);
		
		List<Film> films = new ArrayList<>();
		for (String s : dto.getFilms()) {
			SwapiFilmDto filmDto = api.loadFilm(s);
			Integer filmId = getFilmId(s);
			Film f = convertToEntity(filmDto, filmId);
			films.add(f);
		}

		planet.setFilms(films);
		repository.save(planet);
		return planet;
	}

	private Integer getFilmId(String s) {
		String[] words = s.split("/");
		return Integer.valueOf(words[words.length -1]);
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

	@Transactional
	public void deletePlanet(Integer id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		}
	}

	private PlanetDto convertToDto(Planet planet) {
		List<FilmDto> filmDtos = planet.getFilms().stream().map(this::convertToDto).toList();
		return new PlanetDto(planet.getName(), planet.getClimate(), planet.getTerrain(), filmDtos);
	}

	private FilmDto convertToDto(Film film) {
		return new FilmDto(film.getTitle(), film.getDirector(), film.getReleaseDate());
	}

	
	private Planet convertToEntity(SwapiPlanetDto dto, Integer id) {
		Planet planet = new Planet();
		planet.setId(id);
		planet.setName(dto.getName());
		planet.setClimate(dto.getClimate());
		planet.setTerrain(dto.getTerrain());
		return planet;
	}

	private Film convertToEntity(SwapiFilmDto dto, Integer id) {
		Film film = new Film();
		film.setId(id);
		film.setDirector(dto.getDirector());
		film.setReleaseDate(dto.getReleaseDate());
		film.setTitle(dto.getTitle());
		return film;
	}

}
