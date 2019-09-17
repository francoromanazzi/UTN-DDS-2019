package UTN.QueMePongo.Persistencia;

import modelo.usuario.Usuario;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
import servicios.UsuarioService;

import static org.junit.Assert.assertEquals;

public class TestPersistenciaUsuario extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void persistirUsuarioYLoObtengo() {
		Usuario user = new Usuario("lucas", "rosol@gmail.com", "1554675466", "luqui", "asd");

		entityManager().persist(user);

		Usuario userDB = new UsuarioService().getUsuarioByCredentials("luqui", "asd");

		assertEquals(user.getUsername(), userDB.getUsername());
		assertEquals(user.getPassword(), userDB.getPassword());
		assertEquals(user.getNombre(), userDB.getNombre());
		assertEquals(user.getMail(), userDB.getMail());
		assertEquals(user.getNumeroTelefono(), userDB.getNumeroTelefono());
	}
}
