package modelo.pronosticos_del_clima.proveedores;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import excepciones.ProveedorDeClimaSeCayoException;
import modelo.pronosticos_del_clima.Meteorologo;
import modelo.pronosticos_del_clima.Pronostico;

import javax.ws.rs.client.ClientBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class AccuWeather extends Meteorologo {
	@Override
	public List<Pronostico> obtenerPronosticos() throws ProveedorDeClimaSeCayoException {
		String json = pegarleA(ClientBuilder.newClient()
				.target("http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/7894")
				.queryParam("apikey", "a9ZEXA1qgqXcX4sIgxv6OXZWV95jOOXC")
				.queryParam("language", "en-US")
				.queryParam("details", "false")
				.queryParam("metric", "false"));

		List<AccuWeatherJSON> pronosticosAccuWeather = new Gson().fromJson(json, new TypeToken<List<AccuWeatherJSON>>(){}.getType());
		return pronosticosAccuWeather.stream().map(AccuWeatherJSON::toPronostico).collect(Collectors.toList());
	}
}
