package modelo.clima.proveedores;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modelo.clima.Clima;
import modelo.clima.Meteorologo;

import java.util.List;
import java.util.stream.Collectors;

public class MockAccuweather2 extends Meteorologo {
	private static final String MOCK_ACCUWEATHER_JSON_CLIMA = "[{" +
				"\"DateTime\":\"2018-11-02T01:00:00-03:00\"," +
				" \"PrecipitationProbability\": 15," +
				"\"Temperature\": {\"Value\": 57, \"Unit\": \"F\"}" +
				"}]";

	public List<Clima> obtenerPronosticos() {
		List<AccuWeatherJSON> pronosticosAccuWeather = new Gson().fromJson(MOCK_ACCUWEATHER_JSON_CLIMA, new TypeToken<List<AccuWeatherJSON>>(){}.getType());
		List<Clima> pronosticosClima = pronosticosAccuWeather.stream().map(pronostico -> pronostico.toClima()).collect(Collectors.toList());
		return pronosticosClima;
	}
}
