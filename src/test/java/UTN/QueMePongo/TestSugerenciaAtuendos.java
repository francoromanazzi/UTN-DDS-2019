package UTN.QueMePongo;

import excepciones.*;
import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.clima.Clima;
import modelo.pronosticos_del_clima.clima.temperatura.Celsius;
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
	private final Usuario userPremium = new Usuario("Federico", "fede_123@gmail.com", "1564568766", "", "");
	private final Guardarropa guardarropa = new Guardarropa();

	private final Prenda musculosa = new Prenda(Tipo.MUSCULOSA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda remera1 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda remera2 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.POLIESTER, new Color(50, 0, 0), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda remeraMangaLarga = new Prenda(Tipo.REMERA_MANGA_LARGA, Material.POLIESTER, new Color(50, 0, 0), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda buzo = new Prenda(Tipo.BUZO, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.empty());
	private final Prenda campera = new Prenda(Tipo.CAMPERA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.empty());
	private final Prenda pantalonLargo = new Prenda(Tipo.PANTALON_LARGO, Material.DENIM, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda pantalonCorto = new Prenda(Tipo.PANTALON_CORTO, Material.DENIM, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda zapatos = new Prenda(Tipo.ZAPATOS, Material.CUERO, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda zapatillas = new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda ojotas = new Prenda(Tipo.OJOTAS, Material.GOMA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda reloj = new Prenda(Tipo.RELOJ, Material.PLATA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda bufanda = new Prenda(Tipo.BUFANDA, Material.LANA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda gorra = new Prenda(Tipo.GORRA, Material.POLIESTER, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());
	private final Prenda gorro = new Prenda(Tipo.GORRO, Material.LANA, new Color(50, 50, 50), Optional.of(new Color(0, 0, 0)), Optional.empty());

	private final ServicioDelClima servicioDelClima = ServicioDelClima.getInstance();
	private final LocalDateTime ahora = LocalDateTime.now();
	private final Evento eventoCorto = new Evento("", ahora.plusSeconds(1), ahora.plusSeconds(1).plusMinutes(21), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
	private final Evento eventoLargo = new Evento("", ahora, ahora.plusHours(4), FrecuenciaEvento.UNICA_VEZ, TipoEvento.FORMAL);
	private final Evento eventoDentroDeMuchoTiempo = new Evento("", ahora.plusHours(8), ahora.plusHours(9), FrecuenciaEvento.UNICA_VEZ, TipoEvento.FORMAL);

	@Before
	public void asignarPrivilegios() {
		userPremium.setPrivilegio(new Premium());
	}

	@Before
	public void agregarGuardarropaAUsuario() {
		userPremium.addGuardarropa(guardarropa);
	}

	@After
	public void limpiarPronosticosDelServicioDelClima() {
		ServicioDelClima.getInstance().setPronosticosCache(new ArrayList<>());
	}

	@Test
	public void deberiaSugerirRopaAbrigadaSiHaceFrio() {
		userPremium.addPrenda(musculosa, guardarropa);
		userPremium.addPrenda(remera1, guardarropa);
		userPremium.addPrenda(remera2, guardarropa);
		userPremium.addPrenda(remeraMangaLarga, guardarropa);
		userPremium.addPrenda(buzo, guardarropa);
		userPremium.addPrenda(campera, guardarropa);

		userPremium.addPrenda(pantalonLargo, guardarropa);
		userPremium.addPrenda(pantalonCorto, guardarropa);

		userPremium.addPrenda(zapatos, guardarropa);
		userPremium.addPrenda(zapatillas, guardarropa);

		userPremium.addPrenda(reloj, guardarropa);
		userPremium.addPrenda(bufanda, guardarropa);

		servicioDelClima.setPronosticosCache(new ArrayList<>(Arrays.asList(new Pronostico(ahora.minusMinutes(30), ahora.plusMinutes(30), new Clima(new Celsius(2))))));

		//Lista de sugerencias vacia
		List<Sugerencia> sugerencias = guardarropa.generarSugerencias(eventoCorto, new ArrayList<>());

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
		userPremium.addPrenda(musculosa, guardarropa);
		userPremium.addPrenda(remera1, guardarropa);
		userPremium.addPrenda(remera2, guardarropa);
		userPremium.addPrenda(remeraMangaLarga, guardarropa);
		userPremium.addPrenda(buzo, guardarropa);
		userPremium.addPrenda(campera, guardarropa);

		userPremium.addPrenda(pantalonLargo, guardarropa);
		userPremium.addPrenda(pantalonCorto, guardarropa);

		userPremium.addPrenda(zapatos, guardarropa);
		userPremium.addPrenda(zapatillas, guardarropa);
		userPremium.addPrenda(ojotas, guardarropa);

		userPremium.addPrenda(reloj, guardarropa);
		userPremium.addPrenda(bufanda, guardarropa);

		servicioDelClima.setPronosticosCache(new ArrayList<>(Arrays.asList(new Pronostico(ahora.minusMinutes(30), ahora.plusMinutes(30), new Clima(new Celsius(34))))));

		List<Sugerencia> sugerencias = guardarropa.generarSugerencias(eventoCorto, new ArrayList<>());

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
		userPremium.addPrenda(remera2, guardarropa);
		userPremium.addPrenda(buzo, guardarropa);
		userPremium.addPrenda(campera, guardarropa);

		userPremium.addPrenda(pantalonLargo, guardarropa);

		servicioDelClima.setPronosticosCache(new ArrayList<>(Arrays.asList(new Pronostico(ahora.minusMinutes(30), ahora.plusMinutes(30), new Clima(new Celsius(18))))));

		guardarropa.generarSugerencias(eventoCorto, new ArrayList<>()); // Le falta calzado => falla
	}

	@Test(expected = PronosticoNoDisponibleException.class)
	public void deberiaFallarSiNoHayPronosticoDisponible() {
		userPremium.addPrenda(remera2, guardarropa);
		userPremium.addPrenda(buzo, guardarropa);
		userPremium.addPrenda(campera, guardarropa);

		userPremium.addPrenda(pantalonLargo, guardarropa);

		userPremium.addPrenda(zapatos, guardarropa);

		guardarropa.generarSugerencias(eventoCorto, new ArrayList<>()); // No hay pronostico disponible => falla
	}

	@Test
	public void gorraYGorroSonExcluyentes() {
		userPremium.addPrenda(remera1, guardarropa);
		userPremium.addPrenda(buzo, guardarropa);
		userPremium.addPrenda(campera, guardarropa);

		userPremium.addPrenda(pantalonLargo, guardarropa);
		userPremium.addPrenda(pantalonCorto, guardarropa);

		userPremium.addPrenda(zapatillas, guardarropa);

		userPremium.addPrenda(gorra, guardarropa);
		userPremium.addPrenda(gorro, guardarropa);

		// 1. Si hace frío => tanto gorro como gorra (pero por separado)
		servicioDelClima.setPronosticosCache(new ArrayList<>(Arrays.asList(new Pronostico(ahora.minusMinutes(30), ahora.plusMinutes(30), new Clima(new Celsius(8))))));
		List<Sugerencia> sugerencias = guardarropa.generarSugerencias(eventoCorto, new ArrayList<>());

		// No debería tener gorro y gorra al mismo tiempo
		assertFalse(sugerencias.stream().anyMatch(sugerencia -> sugerencia.getAtuendo().getAccesorios().contains(gorra) && sugerencia.getAtuendo().getAccesorios().contains(gorro)));

		// Debería tener gorro y gorra por separado
		assertTrue(sugerencias.stream().anyMatch(sugerencia -> sugerencia.getAtuendo().getAccesorios().contains(gorra)));
		assertTrue(sugerencias.stream().anyMatch(sugerencia -> sugerencia.getAtuendo().getAccesorios().contains(gorro)));

		//limpiarPronosticosDelServicioDelClima(); // Reset

		// 2. Si hace calor => solamente gorra
		servicioDelClima.setPronosticosCache(new ArrayList<>(Arrays.asList(new Pronostico(ahora.minusMinutes(30), ahora.plusMinutes(30), new Clima(new Celsius(34))))));
		sugerencias = guardarropa.generarSugerencias(eventoCorto, new ArrayList<>());

		// No debería tener gorro
		assertTrue(sugerencias.stream().noneMatch(sugerencia -> sugerencia.getAtuendo().getAccesorios().contains(gorro)));

		// Debería tener gorra la mitad de las veces
		long sugerencias_totales = sugerencias.size();
		long sugerencias_con_gorra = sugerencias.stream().filter(sug -> sug.getAtuendo().getAccesorios().contains(gorra)).count();
		assertEquals(0.5, (double) sugerencias_con_gorra / (double) sugerencias_totales, 0.001);
	}

	@Test(expected = PronosticoNoDisponibleException.class)
	public void deberiaFallarSiNoHayTodosLosPronosticosParaUnEventoLargo() {
		userPremium.addPrenda(remera2, guardarropa);
		userPremium.addPrenda(buzo, guardarropa);
		userPremium.addPrenda(campera, guardarropa);

		userPremium.addPrenda(pantalonLargo, guardarropa);

		userPremium.addPrenda(zapatos, guardarropa);

		servicioDelClima.setPronosticosCache(new ArrayList<>(Arrays.asList(
				new Pronostico(ahora.minusMinutes(30), ahora.plusMinutes(30), new Clima(new Celsius(10))),
				new Pronostico(ahora.plusMinutes(30), ahora.plusMinutes(90), new Clima(new Celsius(10.7))),
				new Pronostico(ahora.plusMinutes(90), ahora.plusMinutes(150), new Clima(new Celsius(10.4)))
		)));

		guardarropa.generarSugerencias(eventoLargo, new ArrayList<>()); // No están todos los pronosticos disponibles => falla
	}

	@Test
	public void deberiaSugerirSegunElPromedioDelClimaParaUnEventoLargo() {
		userPremium.addPrenda(musculosa, guardarropa);
		userPremium.addPrenda(remera1, guardarropa);
		userPremium.addPrenda(remera2, guardarropa);
		userPremium.addPrenda(remeraMangaLarga, guardarropa);
		userPremium.addPrenda(buzo, guardarropa);
		userPremium.addPrenda(campera, guardarropa);

		userPremium.addPrenda(pantalonLargo, guardarropa);
		userPremium.addPrenda(pantalonCorto, guardarropa);

		userPremium.addPrenda(zapatos, guardarropa);
		userPremium.addPrenda(zapatillas, guardarropa);

		userPremium.addPrenda(reloj, guardarropa);
		userPremium.addPrenda(bufanda, guardarropa);

		servicioDelClima.setPronosticosCache(new ArrayList<>(Arrays.asList(
				new Pronostico(ahora.minusMinutes(30), ahora.plusMinutes(30), new Clima(new Celsius(30))),
				new Pronostico(ahora.plusMinutes(30), ahora.plusMinutes(90), new Clima(new Celsius(-3))),
				new Pronostico(ahora.plusMinutes(90), ahora.plusMinutes(150), new Clima(new Celsius(-5))),
				new Pronostico(ahora.plusMinutes(150), ahora.plusMinutes(210), new Clima(new Celsius(-4))),
				new Pronostico(ahora.plusMinutes(210), ahora.plusMinutes(270), new Clima(new Celsius(-3)))
		))); // En un intervalo hace calor, pero en el resto hace frío => sugerencias abrigadas


		List<Sugerencia> sugerencias = guardarropa.generarSugerencias(eventoLargo, new ArrayList<>());

		// Todas las sugerencias deberían tener la campera
		assertTrue(sugerencias.stream().allMatch(sugerencia -> sugerencia.getAtuendo().getPartesSuperiores().contains(campera)));

		// No debería sugerir un pantalón corto
		assertTrue(sugerencias.stream().allMatch(sugerencia -> sugerencia.getAtuendo().getParteInferior() != pantalonCorto));
	}

	@Test(expected = EventoNoFueAgendadoException.class)
	public void deberiaFallarSiUsuarioPideSugerenciasDeEventoNoAgendado() {
		userPremium.obtenerSugerencias(eventoCorto);
	}

	@Test(expected = EventoNoEstaProximoException.class)
	public void deberiaFallarSiUsuarioPideSugerenciaDeEventoNoProximo() {
		userPremium.agendarEventoMockNoPegarleALaDBNiNotificar(eventoDentroDeMuchoTiempo, guardarropa);
		userPremium.obtenerSugerencias(eventoDentroDeMuchoTiempo);
	}

	@Test(expected = EventoYaFueAgendadoException.class)
	public void deberiaFallarSiUsuarioAgregaElMismoEventoDosVeces() {
		userPremium.agendarEventoMockNoPegarleALaDBNiNotificar(eventoDentroDeMuchoTiempo, guardarropa);
		userPremium.agendarEventoMockNoPegarleALaDBNiNotificar(eventoDentroDeMuchoTiempo, guardarropa);
	}

	@Test
	public void usuarioDeberiaPoderPedirSugerenciasDeUnEventoProximo() {
		userPremium.addPrenda(remera1, guardarropa);
		userPremium.addPrenda(pantalonCorto, guardarropa);
		userPremium.addPrenda(zapatillas, guardarropa);

		servicioDelClima.setPronosticosCache(new ArrayList<>(Arrays.asList(new Pronostico(ahora.minusMinutes(30), ahora.plusMinutes(30), new Clima(new Celsius(34))))));

		userPremium.agendarEventoMockNoPegarleALaDBNiNotificar(eventoCorto, guardarropa);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Sugerencia> sugerencias = userPremium.obtenerSugerencias(eventoCorto);
		assertTrue(sugerencias.size() > 0);
	}
}
