package br.com.roberto2aj.apistarwars.planet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanetController {

	@GetMapping
	public String helloWorld() {
		return "Hello World!";
	}
}
