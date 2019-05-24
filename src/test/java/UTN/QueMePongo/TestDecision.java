package UTN.QueMePongo;

import modelo.evento.Evento;
import modelo.evento.EventoEnInterior;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.pronosticos_del_clima.Clima;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.Temperatura;
import modelo.sugerencia.CalificacionSugerencia;
import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class TestDecision {
	private final Usuario usuario = new Usuario();
	private final Guardarropa guardarropa = new Guardarropa();
	private final LocalDateTime fecha = LocalDateTime.now();
	private final Evento eventoCorto = new EventoEnInterior(fecha, fecha.plusMinutes(15));

	@Before
	public void agregarGuardarropaAUsuario() {
		usuario.addGuardarropa(guardarropa);

		usuario.addPrenda(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.empty()), guardarropa);
		usuario.addPrenda(new Prenda(Tipo.PANTALON_CORTO, Material.DENIM, new Color(0, 0, 0), Optional.empty(), Optional.empty()), guardarropa);
		usuario.addPrenda(new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(0, 0, 0), Optional.empty(), Optional.empty()), guardarropa);
	}

	@Before
	public void asignarPronosticosDelSevicioDelClima() {
		ServicioDelClima.getInstance().setPronosticos(new ArrayList<>(Arrays.asList(new Pronostico(fecha.minusMinutes(30), fecha.plusMinutes(30), new Clima(new Temperatura(25, "C"))))));

	}

	@After
	public void limpiarPronosticosDelServicioDelClima() {
		ServicioDelClima.getInstance().setPronosticos(new ArrayList<>());
	}

	@Test
	public void usuarioDeberiaPoderDeshacerUltimaAccion() {
		Sugerencia sugerencia = guardarropa.obtenerSugerencias(eventoCorto).get(0);
		sugerencia.aceptar(usuario);
		sugerencia.calificar(CalificacionSugerencia.CUATRO_ESTRELLAS, usuario);
		sugerencia.calificar(CalificacionSugerencia.UNA_ESTRELLA, usuario);
		usuario.deshacer();

		assertEquals(sugerencia.getEstado(), EstadoSugerencia.CALIFICADO);
		assertEquals(sugerencia.getCalificacion(), CalificacionSugerencia.CUATRO_ESTRELLAS);
	}
}
