package UTN.QueMePongo.Persistencia;

import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import modelo.usuario.Usuario;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPersistenciaEventos extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void persistirUsuarioConEventosYLosObtengo() {
		Usuario user = new Usuario("lucas", "rosol@gmail.com", "1554675466", "luqui", "asd");
		Evento evento1 = new Evento("Ir al supermercado", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		Evento evento2 = new Evento("Ir al trabajo", LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		user.getEventos().add(evento1);
		user.getEventos().add(evento2);

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
}
