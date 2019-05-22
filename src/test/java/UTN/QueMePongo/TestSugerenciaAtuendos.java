package UTN.QueMePongo;

import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.pronosticos_del_clima.Clima;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.Temperatura;
import modelo.evento.Evento;
import modelo.evento.EventoEnInterior;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Premium;
import modelo.usuario.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TestSugerenciaAtuendos {
	private final Usuario userPremium = new Usuario();
	private final Guardarropa guardarropa = new Guardarropa();

	private final Prenda musculosa = new Prenda(Tipo.MUSCULOSA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda remera1 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda remera2 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.POLIESTER, new Color(50, 0, 0), Optional.of(new Color(0, 0, 0)));
	private final Prenda remeraMangaLarga = new Prenda(Tipo.REMERA_MANGA_LARGA, Material.POLIESTER, new Color(50, 0, 0), Optional.of(new Color(0, 0, 0)));
	private final Prenda camisa = new Prenda(Tipo.CAMISA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda buzo = new Prenda(Tipo.BUZO, Material.ALGODON, new Color(0, 0, 0), Optional.empty());
	private final Prenda campera = new Prenda(Tipo.CAMPERA, Material.ALGODON, new Color(0, 0, 0), Optional.empty());
	private final Prenda pantalonLargo = new Prenda(Tipo.PANTALON_LARGO, Material.DENIM, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda pantalonCorto = new Prenda(Tipo.PANTALON_CORTO, Material.DENIM, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda zapatos = new Prenda(Tipo.ZAPATOS, Material.CUERO, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda zapatillas = new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda ojotas = new Prenda(Tipo.OJOTAS, Material.GOMA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda reloj = new Prenda(Tipo.RELOJ, Material.PLATA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda bufanda = new Prenda(Tipo.BUFANDA, Material.LANA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda gorra = new Prenda(Tipo.GORRA, Material.POLIESTER, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));
	private final Prenda gorro = new Prenda(Tipo.GORRO, Material.LANA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)));

	private final ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();
	private final LocalDateTime fecha = LocalDateTime.of(2019, 5, 3, 1, 0);
	private final Evento eventoCorto = new EventoEnInterior(fecha, fecha.plusMinutes(15));
	private final Evento eventoLargo = new EventoEnInterior(fecha, fecha.plusHours(4));


	@Before
	public void asignarPrivilegios() {
		userPremium.setPrivilegio(new Premium());
	}

	@After
	public void limpiarPronosticosDelServicioDelClima() {
		ServicioDelClima.getInstance().setPronosticos(new ArrayList<>());
	}

	@Test
	public void deberiaSugerirRopaAbrigadaSiHaceFrio() {
		guardarropa.addPrenda(musculosa, userPremium);
		guardarropa.addPrenda(remera1, userPremium);
		guardarropa.addPrenda(remera2, userPremium);
		guardarropa.addPrenda(remeraMangaLarga, userPremium);
		guardarropa.addPrenda(buzo, userPremium);
		guardarropa.addPrenda(campera, userPremium);

		guardarropa.addPrenda(pantalonLargo, userPremium);
		guardarropa.addPrenda(pantalonCorto, userPremium);

		guardarropa.addPrenda(zapatos, userPremium);
		guardarropa.addPrenda(zapatillas, userPremium);

		guardarropa.addPrenda(reloj, userPremium);
		guardarropa.addPrenda(bufanda, userPremium);

		servicioDelClima.setPronosticos(new ArrayList<>(Arrays.asList(new Pronostico(fecha.minusMinutes(30), fecha.plusMinutes(30), new Clima(new Temperatura(2, "C"))))));

		List<Sugerencia> sugerencias = guardarropa.obtenerSugerencias(eventoCorto);

		// Todas las sugerencias deberían tener la campera
		assertTrue(sugerencias.stream().allMatch(sugerencia -> sugerencia.getAtuendo().getPartesSuperiores().contains(campera)));

		// No debería sugerir un pantalón corto
		assertTrue(sugerencias.stream().allMatch(sugerencia -> sugerencia.getAtuendo().getParteInferior() != pantalonCorto));

		// Debería poder, en algunas sugerencias, sugerir tanto el reloj como la bufanda
		// Sugiere ambos el 25% de las veces, ya que el accesorio puede ser [], [reloj], [bufanda] o [reloj, bufanda]
		long sugerencias_totales = sugerencias.size();
		long sugerencias_con_reloj_y_bufanda = sugerencias.stream().filter(sug -> sug.getAtuendo().getAccesorios().contains(reloj) && sug.getAtuendo().getAccesorios().contains(bufanda)).count();

		assertTrue(sugerencias.stream().anyMatch(sug -> sug.getAtuendo().getAccesorios().contains(reloj) && sug.getAtuendo().getAccesorios().contains(bufanda)));
		assertEquals(0.25, (double) sugerencias_con_reloj_y_bufanda / (double) sugerencias_totales, 0.001);
	}

	@Test
	public void deberiaSugerirRopaLivianaSiHaceCalor() {
		guardarropa.addPrenda(musculosa, userPremium);
		guardarropa.addPrenda(remera1, userPremium);
		guardarropa.addPrenda(remera2, userPremium);
		guardarropa.addPrenda(remeraMangaLarga, userPremium);
		guardarropa.addPrenda(buzo, userPremium);
		guardarropa.addPrenda(campera, userPremium);

		guardarropa.addPrenda(pantalonLargo, userPremium);
		guardarropa.addPrenda(pantalonCorto, userPremium);

		guardarropa.addPrenda(zapatos, userPremium);
		guardarropa.addPrenda(zapatillas, userPremium);
		guardarropa.addPrenda(ojotas, userPremium);

		guardarropa.addPrenda(reloj, userPremium);
		guardarropa.addPrenda(bufanda, userPremium);

		servicioDelClima.setPronosticos(new ArrayList<>(Arrays.asList(new Pronostico(fecha.minusMinutes(30), fecha.plusMinutes(30), new Clima(new Temperatura(34, "C"))))));

		List<Sugerencia> sugerencias = guardarropa.obtenerSugerencias(eventoCorto);

		// Todas las sugerencias deberían tener pantalon corto
		assertTrue(sugerencias.stream().allMatch(sugerencia -> sugerencia.getAtuendo().getParteInferior() == pantalonCorto));

		// No debería sugerir un buzo
		assertTrue(sugerencias.stream().noneMatch(sugerencia -> sugerencia.getAtuendo().getPartesSuperiores().contains(buzo)));

		// No debería sugerir una campera
		assertTrue(sugerencias.stream().noneMatch(sugerencia -> sugerencia.getAtuendo().getPartesSuperiores().contains(campera)));

		// No debería haber superposicion de prendas en la parte superior
		assertTrue(sugerencias.stream().allMatch(sugerencia -> sugerencia.getAtuendo().getPartesSuperiores().size() == 1));

		// No debería sugerir nunca una bufanda
		assertTrue(sugerencias.stream().noneMatch(sugerencia -> sugerencia.getAtuendo().getAccesorios().contains(bufanda)));

		// Debería sugerir el reloj la mitad de las veces
		long sugerencias_totales = sugerencias.size();
		long sugerencias_con_reloj = sugerencias.stream().filter(sug -> sug.getAtuendo().getAccesorios().contains(reloj)).count();
		assertEquals(0.5, (double) sugerencias_con_reloj / (double) sugerencias_totales, 0.001);
	}

	@Test(expected = SinSugerenciasPosiblesException.class)
	public void deberiaFallarSiNoHayPrendasSuficientesParaSugerencia() {
		guardarropa.addPrenda(remera2, userPremium);
		guardarropa.addPrenda(buzo, userPremium);
		guardarropa.addPrenda(campera, userPremium);

		guardarropa.addPrenda(pantalonLargo, userPremium);

		servicioDelClima.setPronosticos(new ArrayList<>(Arrays.asList(new Pronostico(fecha.minusMinutes(30), fecha.plusMinutes(30), new Clima(new Temperatura(18, "C"))))));

		guardarropa.obtenerSugerencias(eventoCorto); // Le falta calzado => falla
	}

	@Test(expected = PronosticoNoDisponibleException.class)
	public void deberiaFallarSiNoHayPronosticoDisponible() {
		guardarropa.addPrenda(remera2, userPremium);
		guardarropa.addPrenda(buzo, userPremium);
		guardarropa.addPrenda(campera, userPremium);

		guardarropa.addPrenda(pantalonLargo, userPremium);

		guardarropa.addPrenda(zapatos, userPremium);

		guardarropa.obtenerSugerencias(eventoCorto); // No hay pronostico disponible => falla
	}

	@Test
	public void gorraYGorroSonExcluyentes() {
		guardarropa.addPrenda(remera1, userPremium);
		guardarropa.addPrenda(campera, userPremium);

		guardarropa.addPrenda(pantalonLargo, userPremium);
		guardarropa.addPrenda(pantalonCorto, userPremium);

		guardarropa.addPrenda(zapatillas, userPremium);

		guardarropa.addPrenda(gorra, userPremium);
		guardarropa.addPrenda(gorro, userPremium);

		// 1. Si hace frío => tanto gorro como gorra (pero por separado)
		servicioDelClima.setPronosticos(new ArrayList<>(Arrays.asList(new Pronostico(fecha.minusMinutes(30), fecha.plusMinutes(30), new Clima(new Temperatura(2, "C"))))));
		List<Sugerencia> sugerencias = guardarropa.obtenerSugerencias(eventoCorto);

		// No debería tener gorro y gorra al mismo tiempo
		assertFalse(sugerencias.stream().anyMatch(sugerencia -> sugerencia.getAtuendo().getAccesorios().contains(gorra) && sugerencia.getAtuendo().getAccesorios().contains(gorro)));

		// Debería tener gorro y gorra por separado
		assertTrue(sugerencias.stream().anyMatch(sugerencia -> sugerencia.getAtuendo().getAccesorios().contains(gorra)));
		assertTrue(sugerencias.stream().anyMatch(sugerencia -> sugerencia.getAtuendo().getAccesorios().contains(gorro)));

		limpiarPronosticosDelServicioDelClima(); // Reset

		// 2. Si hace calor => solamente gorra
		servicioDelClima.setPronosticos(new ArrayList<>(Arrays.asList(new Pronostico(fecha.minusMinutes(30), fecha.plusMinutes(30), new Clima(new Temperatura(34, "C"))))));
		sugerencias = guardarropa.obtenerSugerencias(eventoCorto);

		// No debería tener gorro
		assertTrue(sugerencias.stream().noneMatch(sugerencia -> sugerencia.getAtuendo().getAccesorios().contains(gorro)));

		// Debería tener gorra la mitad de las veces
		long sugerencias_totales = sugerencias.size();
		long sugerencias_con_gorra = sugerencias.stream().filter(sug -> sug.getAtuendo().getAccesorios().contains(gorra)).count();
		assertEquals(0.5, (double) sugerencias_con_gorra / (double) sugerencias_totales, 0.001);

	}

	@Test(expected = PronosticoNoDisponibleException.class)
	public void deberiaFallarSiNoHayTodosLosPronosticosParaUnEventoLargo() {
		guardarropa.addPrenda(remera2, userPremium);
		guardarropa.addPrenda(buzo, userPremium);
		guardarropa.addPrenda(campera, userPremium);

		guardarropa.addPrenda(pantalonLargo, userPremium);

		guardarropa.addPrenda(zapatos, userPremium);

		servicioDelClima.setPronosticos(new ArrayList<>(Arrays.asList(
				new Pronostico(fecha.minusMinutes(30), fecha.plusMinutes(30), new Clima(new Temperatura(10, "C"))),
				new Pronostico(fecha.plusMinutes(30), fecha.plusMinutes(90), new Clima(new Temperatura(10.7, "C"))),
				new Pronostico(fecha.plusMinutes(90), fecha.plusMinutes(150), new Clima(new Temperatura(10.4, "C")))
				)));

		guardarropa.obtenerSugerencias(eventoLargo); // No están todos los pronosticos disponibles => falla
	}

	@Test
	public void deberiaSugerirSegunElPromedioDelClimaParaUnEventoLargo() {
		guardarropa.addPrenda(musculosa, userPremium);
		guardarropa.addPrenda(remera1, userPremium);
		guardarropa.addPrenda(remera2, userPremium);
		guardarropa.addPrenda(remeraMangaLarga, userPremium);
		guardarropa.addPrenda(buzo, userPremium);
		guardarropa.addPrenda(campera, userPremium);

		guardarropa.addPrenda(pantalonLargo, userPremium);
		guardarropa.addPrenda(pantalonCorto, userPremium);

		guardarropa.addPrenda(zapatos, userPremium);
		guardarropa.addPrenda(zapatillas, userPremium);

		guardarropa.addPrenda(reloj, userPremium);
		guardarropa.addPrenda(bufanda, userPremium);

		servicioDelClima.setPronosticos(new ArrayList<>(Arrays.asList(
				new Pronostico(fecha.minusMinutes(30), fecha.plusMinutes(30), new Clima(new Temperatura(30, "C"))),
				new Pronostico(fecha.plusMinutes(30), fecha.plusMinutes(90), new Clima(new Temperatura(-3, "C"))),
				new Pronostico(fecha.plusMinutes(90), fecha.plusMinutes(150), new Clima(new Temperatura(-5, "C"))),
				new Pronostico(fecha.plusMinutes(150), fecha.plusMinutes(210), new Clima(new Temperatura(-4, "C"))),
				new Pronostico(fecha.plusMinutes(210), fecha.plusMinutes(270), new Clima(new Temperatura(-3, "C")))
		))); // En un intervalo hace calor, pero en el resto hace frío => sugerencias abrigadas

		List<Sugerencia> sugerencias = guardarropa.obtenerSugerencias(eventoLargo);

		// Todas las sugerencias deberían tener la campera
		assertTrue(sugerencias.stream().allMatch(sugerencia -> sugerencia.getAtuendo().getPartesSuperiores().contains(campera)));

		// No debería sugerir un pantalón corto
		assertTrue(sugerencias.stream().allMatch(sugerencia -> sugerencia.getAtuendo().getParteInferior() != pantalonCorto));
	}
}
