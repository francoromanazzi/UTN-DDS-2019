package UTN.QueMePongo.Persistencia;

import modelo.sugerencia.decision.Decision;
import modelo.sugerencia.decision.DecisionVacia;
import modelo.usuario.Usuario;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import servicios.UsuarioService;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestPersistenciaDecision extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void persistirDecisionVaciaAlPersistirUsuarioNuevo() {
		Usuario user = new Usuario("lucas","rosol@gmail.com","1554675466","luqui","asd");

		entityManager().persist(user);

		Usuario userDB = new UsuarioService().getUsuarioByCredentials("luqui","asd");

		List<Decision> decisiones = entityManager().createQuery("from Decision", Decision.class).getResultList();

		assertEquals(1, decisiones.size());
		assertEquals(DecisionVacia.class, userDB.getUltimaDecision().getClass());
	}
}
