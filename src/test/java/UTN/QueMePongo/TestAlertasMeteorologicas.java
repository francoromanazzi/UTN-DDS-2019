package UTN.QueMePongo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import excepciones.ProveedorDeClimaSeCayoException;
import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.pronosticos_del_clima.Meteorologo;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.proveedores.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestAlertasMeteorologicas {
	private final Meteorologo mockAccuweatherNoHayAlerta = Mockito.mock(AccuWeather.class);
	private final Meteorologo mockAccuweatherHayAlertaLluvia = Mockito.mock(AccuWeather.class);
	private final Meteorologo mockAccuweatherHayAlertaNieve = Mockito.mock(AccuWeather.class);

	private final Meteorologo mockDarkskyNoHayAlerta = Mockito.mock(AccuWeather.class);
	private final Meteorologo mockDarkskyHayAlertaLluvia = Mockito.mock(AccuWeather.class);
	private final Meteorologo mockDarkskyHayAlertaNieve = Mockito.mock(AccuWeather.class);

	private final Meteorologo mockProveedorQueFalla = Mockito.mock(AccuWeather.class);

	ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

	@Before
	public void setupMockito() {
		Mockito.when(mockProveedorQueFalla.obtenerAlertasMeteorologicas()).thenThrow(new ProveedorDeClimaSeCayoException());

		Mockito.when(mockAccuweatherNoHayAlerta.obtenerAlertasMeteorologicas()).thenAnswer(
				(InvocationOnMock invocation) -> {
					List<AccuWeatherAlertasJSON> alertaAccuWeatherEnLista = new Gson().fromJson("[{" +
							"\"EpochTime\":1569097200," +
							"\"HasPrecipitation\":false," +
							"\"PrecipitationType\":null" +
							"}]", new TypeToken<List<AccuWeatherAlertasJSON>>() {
					}.getType());
					AccuWeatherAlertasJSON alertaAccuWeather = alertaAccuWeatherEnLista.get(0);
					Optional<AlertaMeteorologica> alerta = alertaAccuWeather.toAlertaMeteorologica();
					return alerta.map(Collections::singletonList).orElse(Collections.emptyList());
				}
		);

		Mockito.when(mockAccuweatherHayAlertaLluvia.obtenerAlertasMeteorologicas()).thenAnswer(
				(InvocationOnMock invocation) -> {
					List<AccuWeatherAlertasJSON> alertaAccuWeatherEnLista = new Gson().fromJson("[{" +
							"\"EpochTime\":1569097200," +
							"\"HasPrecipitation\":true," +
							"\"PrecipitationType\":\"Rain\"" +
							"}]", new TypeToken<List<AccuWeatherAlertasJSON>>() {
					}.getType());
					AccuWeatherAlertasJSON alertaAccuWeather = alertaAccuWeatherEnLista.get(0);
					Optional<AlertaMeteorologica> alerta = alertaAccuWeather.toAlertaMeteorologica();
					return alerta.map(Collections::singletonList).orElse(Collections.emptyList());
				}
		);

		Mockito.when(mockAccuweatherHayAlertaNieve.obtenerAlertasMeteorologicas()).thenAnswer(
				(InvocationOnMock invocation) -> {
					List<AccuWeatherAlertasJSON> alertaAccuWeatherEnLista = new Gson().fromJson("[{" +
							"\"EpochTime\":1569097200," +
							"\"HasPrecipitation\":true," +
							"\"PrecipitationType\":\"Snow\"" +
							"}]", new TypeToken<List<AccuWeatherAlertasJSON>>() {
					}.getType());
					AccuWeatherAlertasJSON alertaAccuWeather = alertaAccuWeatherEnLista.get(0);
					Optional<AlertaMeteorologica> alerta = alertaAccuWeather.toAlertaMeteorologica();
					return alerta.map(Collections::singletonList).orElse(Collections.emptyList());
				}
		);

		Mockito.when(mockDarkskyNoHayAlerta.obtenerAlertasMeteorologicas()).thenAnswer(
				(InvocationOnMock invocation) -> {
					DarkSkyJSONResponse respuestaParseada = new Gson().fromJson("{" +
							"\"hourly\":{" +
							"\"data\": [" +
							"{\"precipProbability\": 0}," +
							"{\"precipProbability\": 0}," +
							"{\"precipProbability\": 0}" +
							"]}}", DarkSkyJSONResponse.class);
					DarkSkyJSON pronosticoDarkSky = respuestaParseada.getPronosticos().get(1); // El 2do ya que el 1ro puede estar por terminar
					Optional<AlertaMeteorologica> alerta = pronosticoDarkSky.toAlertaMeteorologica();
					return alerta.map(Collections::singletonList).orElse(Collections.emptyList());
				}
		);

		Mockito.when(mockDarkskyHayAlertaLluvia.obtenerAlertasMeteorologicas()).thenAnswer(
				(InvocationOnMock invocation) -> {
					DarkSkyJSONResponse respuestaParseada = new Gson().fromJson("{" +
							"\"hourly\":{" +
							"\"data\": [" +
							"{\"precipProbability\": 0.6,\"precipType\": \"rain\"}," +
							"{\"precipProbability\": 0.6,\"precipType\": \"rain\"}," +
							"{\"precipProbability\": 0.8,\"precipType\": \"rain\"}," +
							"]}}", DarkSkyJSONResponse.class);
					DarkSkyJSON pronosticoDarkSky = respuestaParseada.getPronosticos().get(1); // El 2do ya que el 1ro puede estar por terminar
					Optional<AlertaMeteorologica> alerta = pronosticoDarkSky.toAlertaMeteorologica();
					return alerta.map(Collections::singletonList).orElse(Collections.emptyList());
				}
		);

		Mockito.when(mockDarkskyHayAlertaNieve.obtenerAlertasMeteorologicas()).thenAnswer(
				(InvocationOnMock invocation) -> {
					DarkSkyJSONResponse respuestaParseada = new Gson().fromJson("{" +
							"\"hourly\":{" +
							"\"data\": [" +
							"{\"precipProbability\": 0.7,\"precipType\": \"snow\"}," +
							"{\"precipProbability\": 0.7,\"precipType\": \"snow\"}," +
							"{\"precipProbability\": 0.6,\"precipType\": \"snow\"}," +
							"]}}", DarkSkyJSONResponse.class);
					DarkSkyJSON pronosticoDarkSky = respuestaParseada.getPronosticos().get(1); // El 2do ya que el 1ro puede estar por terminar
					Optional<AlertaMeteorologica> alerta = pronosticoDarkSky.toAlertaMeteorologica();
					return alerta.map(Collections::singletonList).orElse(Collections.emptyList());
				}
		);
	}

	@After
	public void limpiarMeteorologosDelServicioDelClima() {
		servicioDelClima.setMeteorologos(new ArrayList<>());
	}

	@Test
	public void debePoderParsearJsonDeAccuweatherCuandoNoHayAlertaMeteorologica() {
		servicioDelClima.agregarMeteorologo(mockAccuweatherNoHayAlerta);

		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		assertEquals(0, alertas.size());
	}

	@Test
	public void debePoderParsearJsonDeAccuweatherCuandoHayLluvia() {
		servicioDelClima.agregarMeteorologo(mockAccuweatherHayAlertaLluvia);

		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		assertEquals(1, alertas.size());
		assertEquals(AlertaMeteorologica.LLUVIA, alertas.get(0));
	}

	@Test
	public void debePoderParsearJsonDeAccuweatherCuandoHayNieve() {
		servicioDelClima.agregarMeteorologo(mockAccuweatherHayAlertaNieve);

		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		assertEquals(1, alertas.size());
		assertEquals(AlertaMeteorologica.NIEVE, alertas.get(0));
	}

	@Test
	public void debePoderParsearJsonDeDarkskyCuandoNoHayAlertaMeteorologica() {
		servicioDelClima.agregarMeteorologo(mockDarkskyNoHayAlerta);

		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		assertEquals(0, alertas.size());
	}

	@Test
	public void debePoderParsearJsonDeDarkskyCuandoHayLluvia() {
		servicioDelClima.agregarMeteorologo(mockDarkskyHayAlertaLluvia);

		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		assertEquals(1, alertas.size());
		assertEquals(AlertaMeteorologica.LLUVIA, alertas.get(0));
	}

	@Test
	public void debePoderParsearJsonDeDarkskyCuandoHayNieve() {
		servicioDelClima.agregarMeteorologo(mockDarkskyHayAlertaNieve);

		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		assertEquals(1, alertas.size());
		assertEquals(AlertaMeteorologica.NIEVE, alertas.get(0));
	}

	@Test
	public void debeDevolverListaVaciaDeAlertasSiElProveedorSeCaeEnLugarDeFallar() {
		servicioDelClima.agregarMeteorologo(mockProveedorQueFalla);

		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		assertEquals(0, alertas.size());
	}

	@Test
	public void debeConsultarAOtroProveedorsiUnProveedorSeCae() {
		servicioDelClima.agregarMeteorologo(mockProveedorQueFalla); // Este falla
		servicioDelClima.agregarMeteorologo(mockAccuweatherHayAlertaLluvia); // Este tiene la alerta de lluvia

		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		assertEquals(1, alertas.size());
		assertEquals(AlertaMeteorologica.LLUVIA, alertas.get(0));
	}

	@Test
	public void debeConsultarAOtroProveedorHastaEncontrarAlgunaAlerta() {
		servicioDelClima.agregarMeteorologo(mockAccuweatherHayAlertaLluvia); // Este tiene la alerta de lluvia
		servicioDelClima.agregarMeteorologo(mockAccuweatherHayAlertaNieve); // Este tiene la alerta de nieve

		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		assertEquals(1, alertas.size());
		assertEquals(AlertaMeteorologica.LLUVIA, alertas.get(0));
	}

	@Ignore // Poner el @Ignore para cuando no se quiera pegarle a accuweather
	@Test
	public void debePoderPegarleALaAPIDeAccuweatherYParsearlo() {
		servicioDelClima.agregarMeteorologo(new AccuWeather());
		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		System.out.println(alertas);
	}

	@Ignore // Poner el @Ignore para cuando no se quiera pegarle a darksky
	@Test
	public void debePoderPegarleALaAPIDeDarkSkyYParsearlo() {
		servicioDelClima.agregarMeteorologo(new DarkSky());
		List<AlertaMeteorologica> alertas = servicioDelClima.obtenerAlertasMeteorologicas();

		System.out.println(alertas);
	}
}
