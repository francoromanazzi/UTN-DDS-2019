package UTN.QueMePongo.Persistencia;

import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import modelo.guardarropa.Guardarropa;
import modelo.usuario.Usuario;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPersistenciaEventos extends AbstractPersistenceTest implements WithGlobalEntityManager {
	private final Usuario user = new Usuario("lucas", "rosol@gmail.com", "1554675466", "luqui", "asd");
	private final Evento evento1 = new Evento("Ir al supermercado", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
	private final Evento evento2 = new Evento("Ir al trabajo", LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
	private final Guardarropa guardarropa = new Guardarropa();

	@Test
	public void persistirUsuarioConEventosYLosObtengo() {
		user.agendarEventoMockNoGenerarSugerencias(evento1, guardarropa);
		user.agendarEventoMockNoGenerarSugerencias(evento2, guardarropa);

		entityManager().persist(user);

		List<Evento> eventosDeMiUserDB = entityManager().createQuery
				("SELECT e FROM Evento e, Usuario u WHERE u.username=:username", Evento.class)
				.setParameter("username", user.getUsername())
				.getResultList();

		assertEquals(2, eventosDeMiUserDB.size());
		assertTrue(
				eventosDeMiUserDB.stream().anyMatch(ev -> ev.equals(evento1))
						&& eventosDeMiUserDB.stream().anyMatch(ev -> ev.equals(evento2))
		);
	}

	@Test
	public void deberiaPoderEliminarEventoYCuandoVuelvoATraerElUsuarioNoLoTiene() {
		user.agendarEventoMockNoGenerarSugerencias(evento1, guardarropa);

		entityManager().persist(user);

		user.eliminarEvento(evento1);

		Usuario usuarioDB = entityManager().createQuery("FROM Usuario u", Usuario.class).getSingleResult();
		assertEquals(0, usuarioDB.getEventos().size());

		// El evento sigue existiendo pero no tiene duenio
		List<Evento> eventosDB = entityManager().createQuery
				("FROM Evento e", Evento.class)
				.getResultList();
		assertEquals(1, eventosDB.size());
	}
}
