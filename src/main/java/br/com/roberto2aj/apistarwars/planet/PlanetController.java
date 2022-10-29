package br.com.roberto2aj.apistarwars.planet;

import java.awt.print.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			+ "If the planet is already loaded into the database, just returns it.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the planet.",
					content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = Book.class)) }),
			@ApiResponse(responseCode = "404", description = "Planet not found",
					content = @Content) })
	@GetMapping("{id}")
	public Planet loadPlanet(
			@Parameter(description = "id of planet to be loaded")
			@PathVariable Integer id) {
		return service.loadPlanet(id);
	}
}
