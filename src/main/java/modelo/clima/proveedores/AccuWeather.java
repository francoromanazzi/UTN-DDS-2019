package modelo.clima.proveedores;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modelo.clima.Clima;
import modelo.clima.Meteorologo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

public class AccuWeather implements Meteorologo {
	public List<Clima> obtenerPronosticos() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/7894")
				.queryParam("apikey", "a9ZEXA1qgqXcX4sIgxv6OXZWV95jOOXC")
				.queryParam("language", "en-US")
				.queryParam("details", "false")
				.queryParam("metric", "false");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get(Response.class);

		List<AccuWeatherJSON> pronosticosAccuWeather = new Gson().fromJson(response.readEntity(String.class), new TypeToken<List<AccuWeatherJSON>>(){}.getType());
		List<Clima> pronosticosClima = pronosticosAccuWeather.stream().map(pronostico -> pronostico.toClima()).collect(Collectors.toList());
		return pronosticosClima;
	}
}
