package br.com.roberto2aj.apistarwars.swapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;

import br.com.roberto2aj.apistarwars.exceptions.CommunicationException;
import br.com.roberto2aj.apistarwars.exceptions.EntityNotFoundException;
import br.com.roberto2aj.apistarwars.film.dto.SwapiFilmDto;
import br.com.roberto2aj.apistarwars.planet.dto.SwapiPlanetDto;

public class SwapiClientTest {

	@InjectMocks
	private SwapiClient swapiClient;

	@Spy
	private HttpClient httpClient;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void loadPlanet_validPlanet() throws IOException, InterruptedException {
		HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(mockResponse.statusCode()).thenReturn(HttpStatus.OK.value());
		Mockito.when(httpClient.send(Mockito.any(), Mockito.eq(BodyHandlers.ofString()))).thenReturn(mockResponse);
		Mockito.when(mockResponse.body()).thenReturn(
				"""
				{
				  "name":"pName",
				  "climate": "pClimate",
				  "terrain": "pTerrain",
				  "films" : ["https://swapi.dev/api/films/1/"]
				}
				""");
		SwapiPlanetDto pDto = new SwapiPlanetDto();
		pDto.setName("pName");
		pDto.setClimate("pClimate");
		pDto.setTerrain("pTerrain");
		pDto.getFilms().add("https://swapi.dev/api/films/1/");

		assertEquals(pDto, swapiClient.loadPlanet(1));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void loadPlanet_invalidPlanet() throws IOException, InterruptedException {
		HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(mockResponse.statusCode()).thenReturn(HttpStatus.NOT_FOUND.value());
		Mockito.when(httpClient.send(Mockito.any(), Mockito.eq(BodyHandlers.ofString()))).thenReturn(mockResponse);

		assertThrows(EntityNotFoundException.class, () -> swapiClient.loadPlanet(100));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void loadFilm_validFilm() throws IOException, InterruptedException {
		HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(mockResponse.statusCode()).thenReturn(HttpStatus.OK.value());
		Mockito.when(mockResponse.body()).thenReturn("""
				{
				  "title":"filmTitle",
				  "director": "directorName",
				  "release_date": "2019-10-18"
				}
				""");
		Mockito.when(httpClient.send(Mockito.any(), Mockito.eq(BodyHandlers.ofString()))).thenReturn(mockResponse);

		SwapiFilmDto swapiDto = new SwapiFilmDto();
		swapiDto.setDirector("directorName");
		swapiDto.setTitle("filmTitle");
		swapiDto.setReleaseDate("2019-10-18");

		assertEquals(swapiDto, swapiClient.loadFilm(1));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void loadFilm_invalidFilm() throws IOException, InterruptedException {
		HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(mockResponse.statusCode()).thenReturn(HttpStatus.NOT_FOUND.value());
		Mockito.when(httpClient.send(Mockito.any(), Mockito.eq(BodyHandlers.ofString()))).thenReturn(mockResponse);

		assertThrows(EntityNotFoundException.class, () -> swapiClient.loadFilm(100));
	}

	@Test
	public void loadFilm_exception() throws IOException, InterruptedException {
		Mockito.when(httpClient.send(Mockito.any(), Mockito.eq(BodyHandlers.ofString())))
				.thenThrow(new InterruptedException());

		assertThrows(CommunicationException.class, () -> swapiClient.loadFilm(100));
	}

}
