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
import br.com.roberto2aj.apistarwars.film.FilmRepository;
import br.com.roberto2aj.apistarwars.planet.dto.PlanetDto;
import br.com.roberto2aj.apistarwars.planet.dto.SwapiPlanetDto;
import br.com.roberto2aj.apistarwars.swapi.SwapiClient;

@Component
public class PlanetService {

	@Autowired
	private SwapiClient api;

	@Autowired
	private PlanetRepository repository;

	@Autowired
	private FilmRepository filmrepository;

	Logger logger = LoggerFactory.getLogger(PlanetService.class);

	public PlanetDto loadPlanet(Integer id) {
		logger.info("Checking if planet with id {} exists in database", id);
		Optional<Planet> planetOpt = repository.findById(id);

		if (planetOpt.isPresent()) {
			logger.info("Planet with id {} already exists in database.", id);
			return PlanetMapper.convertToDto(planetOpt.get());
		}
		logger.info("Loading planet with id {} from Swapi.", id);
		return PlanetMapper.convertToDto(loadPlanetFromSwapi(id));
	}

	private Planet loadPlanetFromSwapi(Integer id) {
		SwapiPlanetDto dto = api.loadPlanet(id);
		Planet planet = PlanetMapper.convertToEntity(dto, id);

		List<Film> films = new ArrayList<>();

		List<Integer> filmIds = dto.getFilms().stream().map(this::getFilmId).toList();
		for (Integer filmId : filmIds) {
			Film f = loadFilm(filmId);
			films.add(f);
		}

		planet.setFilms(films);
		repository.save(planet);
		return planet;
	}

	private Film loadFilm(Integer id) {
		Optional<Film> film = filmrepository.findById(id);
		return film.orElse(
				PlanetMapper.convertToEntity(api.loadFilm(id), id));
	}

	private Integer getFilmId(String s) {
		String[] words = s.split("/");
		return Integer.valueOf(words[words.length -1]);
	}

	public PlanetDto findPlanetById(Integer id) {
		PlanetDto dto = repository.findById(id).map(PlanetMapper::convertToDto).orElse(null);
		if (dto != null) {
			logger.info("Planet with id {} found.");
			return dto;
		}
		logger.info("Planet with id {} not found.");
		throw new EntityNotFoundException();
	}

	public PlanetDto findPlanetByName(String name) {
		PlanetDto dto = repository.findByName(name).map(PlanetMapper::convertToDto).orElse(null);
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
				.map(PlanetMapper::convertToDto)
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

}
