package repositorios;

import excepciones.GuardarropaNoEncontradoException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioGuardarropas implements WithGlobalEntityManager {
	public Guardarropa buscarPorId(long id) throws GuardarropaNoEncontradoException {
		Guardarropa ret = entityManager().find(Guardarropa.class, id);

		if (ret == null) throw new GuardarropaNoEncontradoException();

		return ret;
	}

	public List<Guardarropa> obtenerTodos() {
		return entityManager().createQuery("FROM Guardarropa", Guardarropa.class).getResultList();
	}

	public List<Guardarropa> obtenerTodosDelUsuario(Long id_user) {
		return entityManager().createQuery("SELECT g FROM Guardarropa g JOIN " +
				"g.usuariosPropietarios u WHERE u.Id = :idUsuario", Guardarropa.class).
				setParameter("idUsuario", id_user).getResultList();
	}

	public void guardar(Guardarropa guardarropa) {
		entityManager().persist(guardarropa);
	}
}
