package modelo.clima.proveedores;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import excepciones.ProveedorDeClimaSeCayoException;
import modelo.clima.Clima;
import modelo.clima.Meteorologo;

import javax.ws.rs.client.ClientBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class AccuWeather extends Meteorologo {
	@Override
	public List<Clima> obtenerPronosticos() throws ProveedorDeClimaSeCayoException {
		String json = pegarleA(ClientBuilder.newClient()
				.target("http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/7894")
				.queryParam("apikey", "a9ZEXA1qgqXcX4sIgxv6OXZWV95jOOXC")
				.queryParam("language", "en-US")
				.queryParam("details", "false")
				.queryParam("metric", "false"));

		List<AccuWeatherJSON> pronosticosAccuWeather = new Gson().fromJson(json, new TypeToken<List<AccuWeatherJSON>>(){}.getType());
		return pronosticosAccuWeather.stream().map(pronostico -> pronostico.toClima()).collect(Collectors.toList());
	}
}
