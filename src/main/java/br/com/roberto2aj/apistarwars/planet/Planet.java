package br.com.roberto2aj.apistarwars.planet;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Planet {

	@Id
	private Integer id;
	
	private String name;

	private String climate;

	private String terrain;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

}
