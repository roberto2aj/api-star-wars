package br.com.roberto2aj.apistarwars.swapi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.roberto2aj.apistarwars.exceptions.CommunicationException;
import br.com.roberto2aj.apistarwars.exceptions.EntityNotFoundException;
import br.com.roberto2aj.apistarwars.film.dto.SwapiFilmDto;
import br.com.roberto2aj.apistarwars.planet.dto.SwapiPlanetDto;

@Component
public class SwapiClient {

	Logger logger = LoggerFactory.getLogger(SwapiClient.class);

	public SwapiPlanetDto loadPlanet(Integer id) {
		String json = loadEntity("planets", id);
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, SwapiPlanetDto.class);
	}

	public SwapiFilmDto loadFilm(Integer id) {
		String json = loadEntity("films", id);
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, SwapiFilmDto.class);
	}

	public String loadEntity(String suffix, Integer id) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					  .uri(new URI(String.format("https://swapi.dev/api/%s/%d", suffix, id)))
					  .version(HttpClient.Version.HTTP_2)
					  .GET()
					  .build();
			HttpResponse<String> response = HttpClient.newBuilder()
					  .build().send(request, BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				return response.body();
			} else {
				throw new EntityNotFoundException();
			}
		} catch (URISyntaxException | InterruptedException | IOException e) {
			logger.error("Communication error with Swapi");
			logger.error(e.getMessage());
			throw new CommunicationException();
		}
	}

}
