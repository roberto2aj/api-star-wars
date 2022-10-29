package br.com.roberto2aj.apistarwars.film.dto;

import com.google.gson.annotations.SerializedName;

public class SwapiFilmDto {

	private String title;

	private String director;

	@SerializedName(value = "release_date")
	private String releaseDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

}
