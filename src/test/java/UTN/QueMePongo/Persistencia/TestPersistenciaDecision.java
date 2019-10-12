package UTN.QueMePongo.Persistencia;

import modelo.atuendo.Atuendo;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.sugerencia.Sugerencia;
import modelo.sugerencia.decision.Decision;
import modelo.sugerencia.decision.DecisionAceptar;
import modelo.sugerencia.decision.DecisionVacia;
import modelo.usuario.Usuario;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import repositorios.RepositorioUsuarios;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

public class TestPersistenciaDecision extends AbstractPersistenceTest implements WithGlobalEntityManager {
	private final Usuario user = new Usuario("lucas", "rosol@gmail.com", "1554675466", "luqui", "asd");

	@Test
	public void persistirDecisionVaciaAlPersistirUsuarioNuevo() {
		entityManager().persist(user);

		Usuario userDB = new RepositorioUsuarios().buscarPorCredenciales("luqui", "asd");

		List<Decision> decisiones = entityManager().createQuery("from Decision", Decision.class).getResultList();

		assertEquals(1, decisiones.size());
		assertEquals(DecisionVacia.class, userDB.getUltimaDecision().getClass());
	}

	@Test
	public void deberiaReemplazarLaUltimaDecisionAlRealizarUnaNuevaDecision() {
		entityManager().persist(user);

		Sugerencia sugerencia = new Sugerencia(
				new Atuendo(Collections.singletonList(new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(0, 0, 0), Optional.empty(), Optional.empty())),
						new Prenda(Tipo.PANTALON_CORTO, Material.DENIM, new Color(0, 0, 0), Optional.empty(), Optional.empty()),
						new Prenda(Tipo.ZAPATILLAS, Material.CUERO, new Color(0, 0, 0), Optional.empty(), Optional.empty()),
						Collections.emptyList()
				)
		);

		entityManager().persist(sugerencia);

		sugerencia.aceptar(user);

		Usuario userDB = new RepositorioUsuarios().buscarPorCredenciales("luqui", "asd");

		assertEquals(DecisionAceptar.class, userDB.getUltimaDecision().getClass());
	}
}
