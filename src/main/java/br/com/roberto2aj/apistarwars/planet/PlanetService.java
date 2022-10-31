package br.com.roberto2aj.apistarwars.planet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.roberto2aj.apistarwars.exceptions.EntityNotFoundException;
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

	Logger logger = LoggerFactory.getLogger(PlanetService.class);

	public PlanetDto loadPlanet(Integer id) {
		logger.info("Checking if planet with id {} exists in database", id);
		Optional<Planet> planetOpt = repository.findById(id);

		if (planetOpt.isPresent()) {
			logger.info("Planet with id {} already exists in database.", id);
			return convertToDto(planetOpt.get());
		}
		logger.info("Loading planet with id {} from Swapi.", id);
		return convertToDto(loadPlanetFromSwapi(id));
	}

	private Planet loadPlanetFromSwapi(Integer id) {
		SwapiPlanetDto dto = api.loadPlanet(id);
		Planet planet = convertToEntity(dto, id);
		
		List<Film> films = new ArrayList<>();
		for (String s : dto.getFilms()) {
			Integer filmId = getFilmId(s);
			SwapiFilmDto filmDto = api.loadFilm(filmId);
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
		PlanetDto dto = repository.findById(id).map(this::convertToDto).orElse(null);
		if (dto != null) {
			logger.info("Planet with id {} found.");
			return dto;
		}
		logger.info("Planet with id {} not found.");
		throw new EntityNotFoundException();
	}

	public PlanetDto findPlanetByName(String name) {
		PlanetDto dto = repository.findByName(name).map(this::convertToDto).orElse(null);
		if (dto != null) {
			logger.info("Planet with name \"{}\" found.");
			return dto;
		}
		logger.info("Planet with name \"{}\" not found.");
		throw new EntityNotFoundException();
	}

	public List<PlanetDto> findAllPlanets() {
		return repository.findAll()
				.stream()
				.map(this::convertToDto)
				.toList();
	}

	@Transactional
	public void deletePlanet(Integer id) {
		logger.info("Checking if planet with id {} exists in database", id);
		if (repository.existsById(id)) {
			logger.info("Planet with id {} exists, starting deletion", id);	
			repository.deleteById(id);
			logger.info("Planet with id {} deleted.", id);
		} else {
			logger.info("Planet with id {} doesn't exist.", id);
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
