package repositorios;

import excepciones.CapacidadExcedidaGuardarropaException;
import excepciones.UsuarioNoEsPropietarioDelGuardarropaException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class RepositorioPrendas implements WithGlobalEntityManager, TransactionalOps {

	public void guardar(Prenda prenda, Guardarropa guardarropa, Usuario usuario) throws UsuarioNoEsPropietarioDelGuardarropaException, CapacidadExcedidaGuardarropaException {
		withTransaction(() -> usuario.addPrenda(prenda, guardarropa));
	}
}
