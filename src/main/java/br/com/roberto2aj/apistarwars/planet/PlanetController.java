package br.com.roberto2aj.apistarwars.planet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.roberto2aj.apistarwars.planet.dto.PlanetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("planets")
public class PlanetController {

	@Autowired
	private PlanetService service;

	private Logger logger = LoggerFactory.getLogger(PlanetController.class);

	@Operation(summary = "Loads a planet by its Swapi id and saves it into the database."
			+ " If the planet is already loaded into the database, just returns it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the planet.",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = PlanetDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Planet not found",
					content = @Content) })
	@PutMapping("/{id}")
	public PlanetDto loadPlanet(
			@Parameter(description = "id of planet to be loaded")
			@PathVariable Integer id) {
		logger.info("Loading planet {} into the database.", id);
		return service.loadPlanet(id);
	}

	@Operation(summary = "Lists every planet loaded into the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Everything is fine.",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = PlanetDto.class)) })
			})
	@GetMapping("/")
	public List<PlanetDto> listPlanets() {
		logger.info("Retrieving all planets.");
		return service.findAllPlanets();
	}

	@Operation(summary = "Searches for a planet in the database by its id and returns it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the planet.",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = PlanetDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Planet not found",
					content = @Content) })
	@GetMapping("/{id}")
	public PlanetDto findPlanetById(
			@Parameter(description = "id of planet to be searched for")
			@PathVariable Integer id) {
		logger.info("Searching for planet by id: {}", id);
		return service.findPlanetById(id);
	}

	@Operation(summary = "Searches for a planet in the database by its name and returns it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the planet.",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = PlanetDto.class)) }),
			@ApiResponse(responseCode = "404", description = "Planet not found",
					content = @Content) })
	@GetMapping("/name/{name}")
	public PlanetDto findPlanetByName (
			@Parameter(description = "Name of planet to be searched for")
			@PathVariable String name) {
		logger.info("Searching for planet by name: {}", name);
		return service.findPlanetByName(name);
	}

	@Operation(summary = "Deletes a planet from the database by its Swapi id. If the planet does not exist"
			+ ", nothing happens")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Planet does not exist (anymore).")
			})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void deletePlanet(
			@Parameter(description = "id of planet to be deleted")
			@PathVariable Integer id) {
		logger.info("Deleting planet by id: {}", id);
		service.deletePlanet(id);
	}

}
