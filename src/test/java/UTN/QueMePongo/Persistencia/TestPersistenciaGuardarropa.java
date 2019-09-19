package UTN.QueMePongo.Persistencia;

import modelo.guardarropa.Guardarropa;
import modelo.prenda.Color;
import modelo.prenda.Material;
import modelo.prenda.Prenda;
import modelo.prenda.Tipo;
import modelo.usuario.Usuario;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import servicios.UsuarioService;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPersistenciaGuardarropa extends AbstractPersistenceTest implements WithGlobalEntityManager {
	private final Guardarropa guardarropa1 = new Guardarropa();
	private final Guardarropa guardarropa2 = new Guardarropa();
	private final Guardarropa guardarropa3 = new Guardarropa();
	private final Prenda prenda1 = new Prenda(Tipo.REMERA_MANGA_CORTA, Material.ALGODON, new Color(100, 100, 100), Optional.empty(), Optional.empty());
	private final Prenda prenda2 = new Prenda(Tipo.BOTAS, Material.CUERO, new Color(0, 0, 0), Optional.empty(), Optional.empty());
	private final Usuario user = new Usuario("matias", "mati@gmail.com", "1145734639", "mati543", "1234");
	private final Usuario user2 = new Usuario("juan", "juan@gmail.com", "1145734644", "juan123", "juan111");

	@Test
	public void persistirGuardarropa() {
		guardarropa1.addPrenda(prenda1);
		guardarropa1.addPrenda(prenda2);
		entityManager().persist(guardarropa1);

		List<Guardarropa> guardarropas = entityManager().
				createQuery("from Guardarropa", Guardarropa.class).
				getResultList();

		assertEquals(1, guardarropas.size());
		assertEquals(2, guardarropas.get(0).cantidadPrendas());
	}

	@Test
	public void persistirGuardarropasQueTienenUnUsuarioPropietario() {
		guardarropa1.addUsuario(user);
		guardarropa2.addUsuario(user);
		entityManager().persist(guardarropa1);
		entityManager().persist(guardarropa2);

		List<Guardarropa> guardarropas = entityManager().
				createQuery("from Guardarropa", Guardarropa.class).
				getResultList();

		assertEquals(2, guardarropas.size());

		assertTrue(guardarropas.stream().allMatch(g -> g.getUsuariosPropietarios().size() == 1
				&& g.getUsuariosPropietarios().get(0).getUsername().equals(user.getUsername())));
	}

	@Test
	public void obtenerGuardarropasDeDistintosUsuarios() {
		entityManager().persist(user);
		entityManager().persist(user2);

		guardarropa1.addUsuario(user);
		guardarropa2.addUsuario(user);
		guardarropa3.addUsuario(user2);

		entityManager().persist(guardarropa1);
		entityManager().persist(guardarropa2);
		entityManager().persist(guardarropa3);

		// User 1
		List<Guardarropa> guardarropas = new UsuarioService().getGuardarropasDeUsuarioPorId(user.getId());

		assertEquals(2, guardarropas.size());
		assertTrue(guardarropas.stream().allMatch(g -> g.getUsuariosPropietarios().size() == 1
				&& g.getUsuariosPropietarios().get(0).getUsername().equals(user.getUsername())));

		// User2
		guardarropas = new UsuarioService().getGuardarropasDeUsuarioPorId(user2.getId());

		assertEquals(1, guardarropas.size());
		assertTrue(guardarropas.stream().allMatch(g -> g.getUsuariosPropietarios().size() == 1
				&& g.getUsuariosPropietarios().get(0).getUsername().equals(user2.getUsername())));



	}
}
