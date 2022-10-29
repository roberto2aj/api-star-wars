package br.com.roberto2aj.apistarwars.planet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class SwapiApi {

	public Planet loadPlanet(Integer id) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					  .uri(new URI("https://swapi.dev/api/planets/" + id))
					  .version(HttpClient.Version.HTTP_2)
					  .GET()
					  .build();
			HttpResponse<String> response = HttpClient.newBuilder()
					  .build().send(request, BodyHandlers.ofString());

			Gson gson = new GsonBuilder().create();
			return gson.fromJson(response.body(), Planet.class);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
