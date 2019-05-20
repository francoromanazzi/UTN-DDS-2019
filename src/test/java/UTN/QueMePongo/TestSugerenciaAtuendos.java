package UTN.QueMePongo;

import modelo.atuendo.Atuendo;
import modelo.clima.Clima;
import modelo.clima.ServicioDelClima;
import modelo.clima.Temperatura;
import modelo.evento.Evento;
import modelo.evento.EventoEnInterior;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TestSugerenciaAtuendos {
	Guardarropa guardarropa = new Guardarropa();
	Prenda musculosa = new Prenda(Tipo.MUSCULOSA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda remera1 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda remera2 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.POLIESTER, new Color(50, 0, 0), Optional.of(new Color(0, 0, 0)));
	Prenda remeraMangaLarga = new Prenda(Tipo.REMERA_MANGA_LARGA, Material.POLIESTER, new Color(50, 0, 0), Optional.of(new Color(0, 0, 0)));
	Prenda camisa = new Prenda(Tipo.CAMISA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda buzo = new Prenda(Tipo.BUZO, Material.ALGODON, new Color(0, 0, 0), Optional.empty());
	Prenda campera = new Prenda(Tipo.CAMPERA, Material.ALGODON, new Color(0, 0, 0), Optional.empty());
	Prenda pantalonLargo = new Prenda(Tipo.PANTALON_LARGO, Material.DENIM, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda pantalonCorto = new Prenda(Tipo.PANTALON_CORTO, Material.DENIM, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda zapatos = new Prenda(Tipo.ZAPATOS, Material.CUERO, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda zapatillas = new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda ojotas = new Prenda(Tipo.OJOTAS, Material.GOMA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	Prenda reloj = new Prenda(Tipo.RELOJ, Material.PLATA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));

	@Before
	public void llenarGuardarropa() {
		guardarropa.addPrenda(musculosa);
		guardarropa.addPrenda(remera1);
		guardarropa.addPrenda(remera2);
		guardarropa.addPrenda(buzo);
		guardarropa.addPrenda(campera);

		guardarropa.addPrenda(pantalonLargo);
		guardarropa.addPrenda(pantalonCorto);

		guardarropa.addPrenda(zapatos);
		guardarropa.addPrenda(zapatillas);

		guardarropa.addPrenda(reloj);
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
	public void prendasRequisitoDebeRetornarLasCombinacionesDeRequisitosDeUnaPrenda() {
		System.out.println("Requisitos musculosa " + guardarropa.prendasRequisito(musculosa));
		System.out.println("Requisitos remera manga corta " + guardarropa.prendasRequisito(remera1));
		System.out.println("Requisitos remera manga larga" + guardarropa.prendasRequisito(remeraMangaLarga));
		System.out.println("Requisitos buzo " + guardarropa.prendasRequisito(buzo));
		System.out.println("Requisitos campera " + guardarropa.prendasRequisito(campera));
	}

	@Test
	public void deberiaSugerirRopaAbrigadaSiHaceFrio() {
		ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();
		LocalDateTime fecha = LocalDateTime.of(2019, 5, 3, 1, 0);

		servicioDelClima.setPronosticos(new ArrayList<>(Arrays.asList(new Clima(fecha, new Temperatura(2, "C"), 0.1))));

		Evento evento = new EventoEnInterior(fecha, fecha.plusMinutes(60));

		List<Atuendo> sugerencias = guardarropa.obtenerSugerencias(evento);
		for(int i = 0; i < sugerencias.size(); i++) {
			System.out.println(i + ": " + sugerencias.get(i));
		}
	}

	/*
	@Test
	public void prendasInsuficientesParaSugerencia() {
		guardarropa.addPrenda(remera);
		assertEquals(guardarropa.obtenerSugerencias(new AccuWeather()).size(), 0);
	}

	@Test
	public void prendasSuficientesParaSugerenciaConPosibleAccesorio() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(pantalon);
		guardarropa.addPrenda(zapatos);
		guardarropa.addPrenda(reloj);

		List<Atuendo> sugerencias = guardarropa.obtenerSugerencias(new AccuWeather());

		assertEquals(sugerencias.size(), 2);

		Atuendo atuendoSugeridoConAccesorio = sugerencias.get(0);
		Atuendo atuendoSugeridoSinAccesorio = sugerencias.get(1);

		assertEquals(atuendoSugeridoConAccesorio.getParteSuperior(), remera);
		assertEquals(atuendoSugeridoConAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugeridoConAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugeridoConAccesorio.getAccesorio(), reloj);

		assertEquals(atuendoSugeridoSinAccesorio.getParteSuperior(), remera);
		assertEquals(atuendoSugeridoSinAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugeridoSinAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugeridoSinAccesorio.getAccesorio(), null);
	}

	@Test
	public void prendasSuficientesParaSugerenciaSinAccesorio() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(pantalon);
		guardarropa.addPrenda(zapatos);

		List<Atuendo> sugerencias = guardarropa.obtenerSugerencias(new AccuWeather());

		assertEquals(sugerencias.size(), 1);

		Atuendo atuendoSugerido = sugerencias.get(0);

		assertEquals(atuendoSugerido.getParteSuperior(), remera);
		assertEquals(atuendoSugerido.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido.getCalzado(), zapatos);
		assertEquals(atuendoSugerido.getAccesorio(), null);
	}

	@Test
	public void prendasSuficientesParaMasDeUnaSugerenciaConPosibleAccesorio() {
		guardarropa.addPrenda(remera);
		guardarropa.addPrenda(camisa);
		guardarropa.addPrenda(pantalon);
		guardarropa.addPrenda(zapatos);
		guardarropa.addPrenda(reloj);

		List<Atuendo> sugerencias = guardarropa.obtenerSugerencias(new AccuWeather());

		assertEquals(sugerencias.size(), 4);

		Atuendo atuendoSugerido1ConAccesorio = sugerencias.get(0);
		Atuendo atuendoSugerido2ConAccesorio = sugerencias.get(1);
		Atuendo atuendoSugerido1SinAccesorio = sugerencias.get(2);
		Atuendo atuendoSugerido2SinAccesorio = sugerencias.get(3);

		assertEquals(atuendoSugerido1ConAccesorio.getParteSuperior(), remera);
		assertEquals(atuendoSugerido1ConAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido1ConAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugerido1ConAccesorio.getAccesorio(), reloj);
		
		assertEquals(atuendoSugerido2ConAccesorio.getParteSuperior(), camisa);
		assertEquals(atuendoSugerido2ConAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido2ConAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugerido2ConAccesorio.getAccesorio(), reloj);

		assertEquals(atuendoSugerido1SinAccesorio.getParteSuperior(), remera);
		assertEquals(atuendoSugerido1SinAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido1SinAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugerido1SinAccesorio.getAccesorio(), null);

		assertEquals(atuendoSugerido2SinAccesorio.getParteSuperior(), camisa);
		assertEquals(atuendoSugerido2SinAccesorio.getParteInferior(), pantalon);
		assertEquals(atuendoSugerido2SinAccesorio.getCalzado(), zapatos);
		assertEquals(atuendoSugerido2SinAccesorio.getAccesorio(), null);
	}
	 */
}
