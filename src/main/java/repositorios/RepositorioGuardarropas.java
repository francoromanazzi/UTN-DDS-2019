package repositorios;

import excepciones.GuardarropaNoEncontradoException;
import modelo.guardarropa.Guardarropa;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioGuardarropas implements WithGlobalEntityManager {
	public Guardarropa buscarPorId(long id) throws GuardarropaNoEncontradoException{
		Guardarropa ret = entityManager().find(Guardarropa.class, id);

		if (ret == null) throw new GuardarropaNoEncontradoException();

		return ret;
	}

	public List<Guardarropa> obtenerTodos() {
		return entityManager().createQuery("FROM Guardarropa", Guardarropa.class).getResultList();
	}
}
