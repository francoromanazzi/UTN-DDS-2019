package UTN.QueMePongo;

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
import modelo.sugerencia.CalificacionSugerencia;
import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.SensibilidadTemperatura;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestDecision {
	private final Usuario usuario = new Usuario("","","");
	private final Guardarropa guardarropa = new Guardarropa();
	private final LocalDateTime fecha = LocalDateTime.now();
	private final Evento eventoCorto = new Evento("", fecha, fecha.plusMinutes(15),FrecuenciaEvento.UNICA_VEZ,TipoEvento.INFORMAL);

	@Before
	public void agregarGuardarropaAUsuario() {
		usuario.addGuardarropa(guardarropa);

		usuario.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.empty()), guardarropa);
		usuario.addPrenda(new Prenda(Tipo.PANTALON_CORTO, Material.DENIM, new Color(0, 0, 0), Optional.empty(), Optional.empty()), guardarropa);
		usuario.addPrenda(new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(0, 0, 0), Optional.empty(), Optional.empty()), guardarropa);
	}

	@Before
	public void asignarPronosticosDelSevicioDelClima() {
		ServicioDelClima.getInstance().setPronosticosCache(new ArrayList<>(Arrays.asList(new Pronostico(fecha.minusMinutes(30), fecha.plusMinutes(30), new Clima(new Celsius(25))))));

	}

	@After
	public void limpiarPronosticosDelServicioDelClima() {
		ServicioDelClima.getInstance().setPronosticosCache(new ArrayList<>());
	}

	@Test
	public void usuarioDeberiaPoderDeshacerUltimaAccion() {
		Sugerencia sugerencia = guardarropa.generarSugerencias(eventoCorto,new ArrayList<>()).get(0);
		sugerencia.aceptar(usuario);
		sugerencia.calificar(new CalificacionSugerencia(SensibilidadTemperatura.NORMAL), usuario);
		sugerencia.calificar(new CalificacionSugerencia(SensibilidadTemperatura.NORMAL), usuario);
		usuario.deshacerUltimaDecision();

		assertEquals(sugerencia.getEstado(), EstadoSugerencia.CALIFICADO);
		assertEquals(sugerencia.getCalificacion().getSensibilidadGlobal(), SensibilidadTemperatura.NORMAL);
		assertTrue(sugerencia.getCalificacion().getSensibilidadPorPartesDelCuerpo().isEmpty());
	}
}
