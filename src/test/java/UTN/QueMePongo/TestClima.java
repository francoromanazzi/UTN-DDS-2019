package UTN.QueMePongo;

import modelo.clima.Clima;
import modelo.clima.MockProveedorClima;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class TestClima {

	@Test
	public void debePoderParsearJsonDeMockProveedorClimaHaciaClima() {
		List<Clima> climas = MockProveedorClima.getInstance().obtenerClimas();
		Clima clima = climas.get(0);
		System.out.println(clima.getFecha());
		System.out.println(clima.getProbabilidadPrecipitacion());
		System.out.println(clima.getTemperatura().getUnidad());
		System.out.println(clima.getTemperatura().getValor());
		assertEquals(1, climas.size());
	}

	/*
	@Test
	public void debePoderPegarleALaAPIDeAccuweather() {
		List<Clima> pronosticos = AccuWheather.getInstance().obtenerClimas();
		assertEquals(12, pronosticos.size());
	} */
}
