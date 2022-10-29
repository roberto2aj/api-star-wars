package br.com.roberto2aj.apistarwars.planet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class PlanetController {

	@Autowired
	private PlanetService service;

	@Operation(summary = "Loads a planet by its Swapi id and saves it into the database."
			+ " If the planet is already loaded into the database, just returns it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the planet.",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = Planet.class)) }),
			@ApiResponse(responseCode = "404", description = "Planet not found",
					content = @Content) })
	@PutMapping("/{id}")
	public Planet loadPlanet(
			@Parameter(description = "id of planet to be loaded")
			@PathVariable Integer id) {
		return service.loadPlanet(id);
	}

	@Operation(summary = "Lists every planet loaded into the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Everything is fine.",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = Planet.class)) })
			})
	@GetMapping("/")
	public List<Planet> listPlanets() {
		return service.findAllPlanets();
	}

	@Operation(summary = "Searches for a planet in the database by its id and returns it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the planet.",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = Planet.class)) }),
			@ApiResponse(responseCode = "404", description = "Planet not found",
					content = @Content) })
	@GetMapping("/{id}")
	public Planet findPlanetById(
			@Parameter(description = "id of planet to be searched for")
			@PathVariable Integer id) {
		return service.findPlanetById(id);
	}

	@Operation(summary = "Searches for a planet in the database by its name and returns it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the planet.",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = Planet.class)) }),
			@ApiResponse(responseCode = "404", description = "Planet not found",
					content = @Content) })
	@GetMapping("/name/{name}")
	public Planet findPlanetByName (
			@Parameter(description = "Name of planet to be searched for")
			@PathVariable String name) {
		return service.findPlanetByName(name);
	}

	@Operation(summary = "Delets a planet from the database by its Swapi id. If the planet does not exist"
			+ ", nothing happens")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Planet does not exist (anymore).")
			})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deletePlanet(
			@Parameter(description = "id of planet to be deleted")
			@PathVariable Integer id) {
		service.deletePlanet(id);
	}

}
