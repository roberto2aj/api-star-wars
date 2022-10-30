package br.com.roberto2aj.apistarwars.planet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PlanetServiceTest {

	@InjectMocks
	private PlanetService planetService;

	@Mock
	private PlanetRepository repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void findAllPlanets_noPlanet() {
		Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
		assertEquals(planetService.findAllPlanets(), new ArrayList<>());
	}
	

	
}
