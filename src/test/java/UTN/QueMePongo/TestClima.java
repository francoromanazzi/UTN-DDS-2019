package UTN.QueMePongo;

import excepciones.ClimaNoDisponibleException;
import modelo.clima.Clima;
import modelo.clima.ServicioDelClima;
import modelo.clima.proveedores.*;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestClima {

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
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new MockAccuweather());
		Clima clima = servicioDelClima.obtenerClima(LocalDateTime.of(2019, 5, 3, 1, 0));

		assertEquals("2019-05-03T01:00", clima.getFecha().toString());
		assertEquals(0.15, clima.getProbabilidadPrecipitacion(), 0.001);
		assertEquals(57, clima.getTemperatura().getValor(), 0.001);
		assertEquals("F", clima.getTemperatura().getUnidad());
	}

	@Test(expected = ClimaNoDisponibleException.class)
	public void debeFallarSiNoPuedeConseguirElClima() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new MockAccuweather());
		servicioDelClima.obtenerClima(LocalDateTime.of(2055, 5, 3, 1, 0));
	}

	@Test
	public void debeConsultarAOtroProveedorsiUnProveedorNoTieneClima() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new MockAccuweather()); // Este no lo tiene
		servicioDelClima.agregarMeteorologo(new MockAccuweather2()); // Este sí

		Clima clima = servicioDelClima.obtenerClima(LocalDateTime.of(2018, 11, 2, 1, 0));

		assertEquals("2018-11-02T01:00", clima.getFecha().toString());
		assertEquals(0.15, clima.getProbabilidadPrecipitacion(), 0.001);
		assertEquals(57, clima.getTemperatura().getValor(), 0.001);
		assertEquals("F", clima.getTemperatura().getUnidad());
	}

	@Test
	public void debeConsultarAOtroProveedorsiUnProveedorSeCae() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new MockProveedorQueFalla()); // Este falla
		servicioDelClima.agregarMeteorologo(new MockAccuweather2()); // Este tiene el clima

		Clima clima = servicioDelClima.obtenerClima(LocalDateTime.of(2018, 11, 2, 1, 0));

		assertEquals("2018-11-02T01:00", clima.getFecha().toString());
		assertEquals(0.15, clima.getProbabilidadPrecipitacion(), 0.001);
		assertEquals(57, clima.getTemperatura().getValor(), 0.001);
		assertEquals("F", clima.getTemperatura().getUnidad());
	}

	@Ignore // TODO: Poner el @Ignore para cuando no se quiera pegarle a accuweather
	@Test
	public void debePoderPegarleALaAPIDeAccuweatherYParsearlo() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new AccuWeather());
		Clima clima = servicioDelClima.obtenerClima(LocalDateTime.now());

		System.out.println(servicioDelClima.getPronosticos().size());
		System.out.println(clima.getFecha());
		System.out.println(clima.getProbabilidadPrecipitacion());
		System.out.println(clima.getTemperatura().getValor());
		System.out.println(clima.getTemperatura().getUnidad());
		System.out.println(clima.getTemperatura().toCelsius());
	}

	@Ignore // TODO: Poner el @Ignore para cuando no se quiera pegarle a darksky
	@Test
	public void debePoderPegarleALaAPIDeDarkSkyYParsearlo() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();

		servicioDelClima.agregarMeteorologo(new DarkSky());
		Clima clima = servicioDelClima.obtenerClima(LocalDateTime.now());

		System.out.println(servicioDelClima.getPronosticos().size());
		System.out.println(clima.getFecha());
		System.out.println(clima.getProbabilidadPrecipitacion());
		System.out.println(clima.getTemperatura().getValor());
		System.out.println(clima.getTemperatura().getUnidad());
		System.out.println(clima.getTemperatura().toCelsius());
	}
}
