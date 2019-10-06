package repositorios;

import modelo.guardarropa.Guardarropa;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioGuardarropas implements WithGlobalEntityManager {
	public Guardarropa buscarPorId(long id) {
		return entityManager().find(Guardarropa.class, id);
	}

	public List<Guardarropa> obtenerTodos() {
		return entityManager().createQuery("FROM Guardarropa", Guardarropa.class).getResultList();
	}
}
