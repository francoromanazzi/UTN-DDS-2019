package UTN.QueMePongo;

import com.google.gson.Gson;
import modelo.clima.proveedores.AccuWeather;
import modelo.clima.proveedores.AccuWeatherJSON;
import modelo.clima.Clima;
import modelo.clima.ServicioDelClima;
import modelo.clima.proveedores.DarkSky;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestClima {

	private final Gson gson = new Gson();
	private final String MOCK_ACCUWEATHER_JSON_CLIMA = "{" +
			"\"DateTime\":\"2019-05-03T01:00:00-03:00\"," +
			" \"PrecipitationProbability\": 15," +
			"\"Temperature\": {\"Value\": 57, \"Unit\": \"F\"}" +
			"}";

	@After
	public void limpiarMeteorologosDelServicioDelClima() {
		ServicioDelClima.getInstance().setMeteorologos(new ArrayList<>());
	}

	@After
	public void limpiarPronosticosDelServicioDelClima() {
		ServicioDelClima.getInstance().setPronosticos(new ArrayList<>());
	}

	@Test
	public void debePoderParsearJsonDeAccuweatherHaciaClima() {
		Clima clima = gson.fromJson(MOCK_ACCUWEATHER_JSON_CLIMA, AccuWeatherJSON.class).toClima();
		assertEquals("2019-05-03T01:00", clima.getFecha().toString());
		assertEquals(0.15, clima.getProbabilidadPrecipitacion(), 0.001);
		assertEquals(57, clima.getTemperatura().getValor(), 0.001);
		assertEquals("F", clima.getTemperatura().getUnidad());
	}

	//@Ignore // TODO: Poner el @Ignore para cuando no se quiera pegarle a accuweather
	@Test
	public void debePoderPegarleALaAPIDeAccuweatherYParsearlo() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new AccuWeather());
		Clima clima = servicioDelClima.obtenerClima();

		System.out.println(servicioDelClima.getPronosticos().size());

		System.out.println(clima.getFecha());
		System.out.println(clima.getProbabilidadPrecipitacion());
		System.out.println(clima.getTemperatura().getValor());
		System.out.println(clima.getTemperatura().getUnidad());
		System.out.println(clima.getTemperatura().toCelsius());
	}

	//@Ignore // TODO: Poner el @Ignore para cuando no se quiera pegarle a darksky
	@Test
	public void debePoderPegarleALaAPIDeDarkSkyYParsearlo() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new DarkSky());
		Clima clima = servicioDelClima.obtenerClima();

		System.out.println(servicioDelClima.getPronosticos().size());

		System.out.println(clima.getFecha());
		System.out.println(clima.getProbabilidadPrecipitacion());
		System.out.println(clima.getTemperatura().getValor());
		System.out.println(clima.getTemperatura().getUnidad());
		System.out.println(clima.getTemperatura().toCelsius());
	}
}
