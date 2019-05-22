package UTN.QueMePongo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import excepciones.PronosticoNoDisponibleException;
import excepciones.ProveedorDeClimaSeCayoException;
import modelo.pronosticos_del_clima.Meteorologo;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.proveedores.AccuWeather;
import modelo.pronosticos_del_clima.proveedores.AccuWeatherJSON;
import modelo.pronosticos_del_clima.proveedores.DarkSky;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPronostico {
	private final Meteorologo mockAccuweather2055 = Mockito.mock(AccuWeather.class);
	private final Meteorologo mockAccuweather2056 = Mockito.mock(AccuWeather.class);
	private final Meteorologo mockProveedorQueFalla = Mockito.mock(AccuWeather.class);

	@Before
	public void setupMockito() {
		Mockito.when(mockProveedorQueFalla.obtenerPronosticos()).thenThrow(new ProveedorDeClimaSeCayoException());

		Mockito.when(mockAccuweather2055.obtenerPronosticos()).thenAnswer(
				(InvocationOnMock invocation) -> {
					List<AccuWeatherJSON> pronosticosAccuWeather = new Gson().fromJson("[{" +
							"\"DateTime\":\"2055-11-02T01:00:00-03:00\"," +
							" \"PrecipitationProbability\": 15," +
							"\"Temperature\": {\"Value\": 57, \"Unit\": \"F\"}" +
							"}]", new TypeToken<List<AccuWeatherJSON>>() {
					}.getType());
					return pronosticosAccuWeather.stream().map(AccuWeatherJSON::toPronostico).collect(Collectors.toList());
				}
		);

		Mockito.when(mockAccuweather2056.obtenerPronosticos()).thenAnswer(
				(InvocationOnMock invocation) -> {
					List<AccuWeatherJSON> pronosticosAccuWeather = new Gson().fromJson("[{" +
							"\"DateTime\":\"2056-05-03T01:00:00-03:00\"," +
							" \"PrecipitationProbability\": 15," +
							"\"Temperature\": {\"Value\": 57, \"Unit\": \"F\"}" +
							"}]", new TypeToken<List<AccuWeatherJSON>>() {
					}.getType());
					return pronosticosAccuWeather.stream().map(AccuWeatherJSON::toPronostico).collect(Collectors.toList());
				}
		);
	}

	@After
	public void limpiarMeteorologosDelServicioDelClima() {
		ServicioDelClima.getInstance().setMeteorologos(new ArrayList<>());
	}

	@After
	public void limpiarPronosticosDelServicioDelClima() {
		ServicioDelClima.getInstance().setPronosticos(new ArrayList<>());
	}

	@Test
	public void debePoderParsearJsonDeAccuweatherHaciaPronostico() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(mockAccuweather2056);
		LocalDateTime fechaTarget = LocalDateTime.of(2056, 5, 3, 1, 0);
		Pronostico pronostico = servicioDelClima.obtenerPronostico(fechaTarget);

		assertTrue(pronostico.intervaloContieneAFecha(fechaTarget));
		assertEquals(57, pronostico.getClima().getTemperatura().getValor(), 0.001);
		assertEquals("F", pronostico.getClima().getTemperatura().getUnidad());
	}

	@Test(expected = PronosticoNoDisponibleException.class)
	public void debeFallarSiNoPuedeConseguirElPronostico() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(mockAccuweather2056);
		servicioDelClima.obtenerPronostico(LocalDateTime.of(2088, 5, 3, 1, 0));
	}

	@Test
	public void debeConsultarAOtroProveedorsiUnProveedorNoTienePronostico() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(mockAccuweather2056); // Este no lo tiene
		servicioDelClima.agregarMeteorologo(mockAccuweather2055); // Este sí

		LocalDateTime fechaTarget = LocalDateTime.of(2055, 11, 2, 1, 0);
		Pronostico pronostico = servicioDelClima.obtenerPronostico(fechaTarget);

		assertTrue(pronostico.intervaloContieneAFecha(fechaTarget));
		assertEquals(57, pronostico.getClima().getTemperatura().getValor(), 0.001);
		assertEquals("F", pronostico.getClima().getTemperatura().getUnidad());
	}

	@Test
	public void debeConsultarAOtroProveedorsiUnProveedorSeCae() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(mockProveedorQueFalla); // Este falla
		servicioDelClima.agregarMeteorologo(mockAccuweather2055); // Este tiene el clima

		LocalDateTime fechaTarget = LocalDateTime.of(2055, 11, 2, 1, 0);
		Pronostico pronostico = servicioDelClima.obtenerPronostico(fechaTarget);

		assertTrue(pronostico.intervaloContieneAFecha(fechaTarget));
		assertEquals(57, pronostico.getClima().getTemperatura().getValor(), 0.001);
		assertEquals("F", pronostico.getClima().getTemperatura().getUnidad());
	}

	@Ignore // Poner el @Ignore para cuando no se quiera pegarle a accuweather
	@Test
	public void debePoderPegarleALaAPIDeAccuweatherYParsearlo() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new AccuWeather());
		Pronostico pronostico = servicioDelClima.obtenerPronostico(LocalDateTime.now());

		System.out.println(servicioDelClima.getPronosticos().size());
		System.out.println(pronostico.getFechaInicio());
		System.out.println(pronostico.getFechaFin());
		System.out.println(pronostico.getClima().getTemperatura().getValor());
		System.out.println(pronostico.getClima().getTemperatura().getUnidad());
		System.out.println(pronostico.getClima().getTemperatura().toCelsius());
	}

	@Ignore // Poner el @Ignore para cuando no se quiera pegarle a darksky
	@Test
	public void debePoderPegarleALaAPIDeDarkSkyYParsearlo() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new DarkSky());
		Pronostico pronostico = servicioDelClima.obtenerPronostico(LocalDateTime.now());

		System.out.println(servicioDelClima.getPronosticos().size());
		System.out.println(pronostico.getFechaFin());
		System.out.println(pronostico.getFechaFin());
		System.out.println(pronostico.getClima().getTemperatura().getValor());
		System.out.println(pronostico.getClima().getTemperatura().getUnidad());
		System.out.println(pronostico.getClima().getTemperatura().toCelsius());
	}
}
