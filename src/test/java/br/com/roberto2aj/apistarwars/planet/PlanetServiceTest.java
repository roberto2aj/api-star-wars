package br.com.roberto2aj.apistarwars.planet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.roberto2aj.apistarwars.exceptions.EntityNotFoundException;
import br.com.roberto2aj.apistarwars.film.Film;
import br.com.roberto2aj.apistarwars.film.FilmRepository;
import br.com.roberto2aj.apistarwars.film.dto.FilmDto;
import br.com.roberto2aj.apistarwars.film.dto.SwapiFilmDto;
import br.com.roberto2aj.apistarwars.planet.dto.PlanetDto;
import br.com.roberto2aj.apistarwars.planet.dto.SwapiPlanetDto;
import br.com.roberto2aj.apistarwars.swapi.SwapiClient;

public class PlanetServiceTest {

	@InjectMocks
	private PlanetService planetService;

	@Mock
	private PlanetRepository repository;

	@Mock
	private FilmRepository filmRepository;

	@Mock
	private SwapiClient api;

	private Planet planet;

	private PlanetDto dto;

	private SwapiPlanetDto swapiDto;

	@BeforeEach
	public void setUp() {
		Integer id = 1;
		String name = "n1";
		String terrain = "t1";
		String climate = "c1";

		planet = new Planet();
		planet.setId(id);
		planet.setClimate(climate);
		planet.setTerrain(terrain);
		planet.setName(name);

		dto = new PlanetDto(name, climate, terrain, new ArrayList<>());

		swapiDto = new SwapiPlanetDto();
		swapiDto.setClimate(climate);
		swapiDto.setTerrain(terrain);
		swapiDto.setName(name);

		swapiDto.getFilms().add("url/1");

		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void loadPlanet_planetAlreadyLoaded() {
		Integer id = planet.getId();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(planet));
		assertEquals(planetService.loadPlanet(id), dto);
		Mockito.verify(api, times(0)).loadPlanet(id);
	}

	@Test
	public void loadPlanet_planetNotLoaded_filmNotLoaded() {
		Integer id = planet.getId();
		String title = "t";
		String releaseDate = "r";
		String director = "d";

		Film film = new Film();
		film.setId(id);
		film.setDirector(director);
		film.setReleaseDate(releaseDate);
		film.setTitle(title);
		planet.getFilms().add(film);

		dto = new PlanetDto(planet.getName(), planet.getClimate(), planet.getTerrain(),
				List.of(new FilmDto(title, director, releaseDate)));

		SwapiFilmDto swapiFilmDto = new SwapiFilmDto();
		swapiFilmDto.setDirector(director);
		swapiFilmDto.setTitle(title);
		swapiFilmDto.setReleaseDate(releaseDate);

		Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(null));
		Mockito.when(filmRepository.findById(id)).thenReturn(Optional.ofNullable(null));
		Mockito.when(api.loadPlanet(id)).thenReturn(swapiDto);
		Mockito.when(api.loadFilm(id)).thenReturn(swapiFilmDto);

		assertEquals(dto, planetService.loadPlanet(id));
	}

	@Test
	public void loadPlanet_planetNotLoaded_filmLoaded() {
		Integer id = planet.getId();
		String title = "t";
		String releaseDate = "r";
		String director = "d";

		Film film = new Film();
		film.setId(id);
		film.setDirector(director);
		film.setReleaseDate(releaseDate);
		film.setTitle(title);
		planet.getFilms().add(film);

		dto = new PlanetDto(planet.getName(), planet.getClimate(), planet.getTerrain(),
				List.of(new FilmDto(title, director, releaseDate)));

		SwapiFilmDto swapiFilmDto = new SwapiFilmDto();
		swapiFilmDto.setDirector(director);
		swapiFilmDto.setTitle(title);
		swapiFilmDto.setReleaseDate(releaseDate);

		Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(null));
		Mockito.when(filmRepository.findById(id)).thenReturn(Optional.of(film));
		Mockito.when(api.loadPlanet(id)).thenReturn(swapiDto);
		Mockito.when(api.loadFilm(id)).thenReturn(swapiFilmDto);

		assertEquals(dto, planetService.loadPlanet(id));
	}

	@Test
	public void loadPlanet_planetDoesNotExist() {
		Integer id = 123;
		Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(null));
		Mockito.when(api.loadPlanet(id)).thenThrow(new EntityNotFoundException());

		assertThrows(EntityNotFoundException.class, () -> planetService.loadPlanet(id));
		Mockito.verify(api, times(1)).loadPlanet(id);
	}

	@Test
	public void findById_hasPlanet() {
		Integer id = planet.getId();
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(planet));
		assertEquals(planetService.findPlanetById(id), dto);
	}

	@Test
	public void findById_noPlanet() {
		Integer id = planet.getId();
		Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(null));
		assertThrows(EntityNotFoundException.class, () -> planetService.findPlanetById(id));
	}

	@Test
	public void findByName_hasPlanet() {
		String name = planet.getName();
		Mockito.when(repository.findByName(name)).thenReturn(Optional.of(planet));
		assertEquals(planetService.findPlanetByName(name), dto);
	}

	@Test
	public void findByName_noPlanet() {
		String name = planet.getName();
		Mockito.when(repository.findByName(name)).thenReturn(Optional.ofNullable(null));
		assertThrows(EntityNotFoundException.class, () -> planetService.findPlanetByName(name));
	}

	@Test
	public void findAllPlanets_noPlanet() {
		Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
		assertEquals(planetService.findAllPlanets(), new ArrayList<>());
	}

	@Test
	public void findAllPlanets_hasPlanet() {
		List<Planet> planetList = List.of(planet);
		List<PlanetDto> dtoList = List.of(dto);

		Mockito.when(repository.findAll()).thenReturn(planetList);
		assertEquals(planetService.findAllPlanets(), dtoList);
	}

	@Test
	public void deletePlanet_planetExists() {
		Integer id = 1;
		Mockito.when(repository.existsById(id)).thenReturn(true);
		planetService.deletePlanet(id);
		Mockito.verify(repository, times(1)).deleteById(id);
	}

	@Test
	public void deletePlanet_planetDoesNotExist() {
		Integer id = 1;
		Mockito.when(repository.existsById(id)).thenReturn(false);
		planetService.deletePlanet(id);
		Mockito.verify(repository, times(0)).deleteById(id);
	}

}
