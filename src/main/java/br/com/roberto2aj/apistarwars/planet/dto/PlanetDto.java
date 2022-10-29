package br.com.roberto2aj.apistarwars.planet.dto;

import java.util.List;

import br.com.roberto2aj.apistarwars.film.dto.FilmDto;

public record PlanetDto(String name, String climate, String terrain, List<FilmDto> films) {}
