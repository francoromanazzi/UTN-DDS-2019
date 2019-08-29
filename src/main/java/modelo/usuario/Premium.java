package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;

import java.util.List;

public class Premium implements PrivilegiosUsuario {

	@Override
	public void addGuardarropa(Guardarropa guardarropa, Usuario nuevoUsuario) {
		guardarropa.addUsuario(nuevoUsuario);
	}

	@Override
	public boolean admiteAddPrenda(Prenda prendaNueva, Guardarropa guardarropa) {
		return true;
	}

	@Override
	public void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws CapacidadExcedidaGuardarropaException {
		// Si algun usuario del guardarropa era gratuito y no tiene límite para más prendas, excepción
		// Es decir, el usuario premium se ve limitado por los otros usuarios de ese guardarropa
		if (guardarropa
			.getUsuariosPropietarios()
			.stream()
			.anyMatch(usuario -> !usuario.getPrivilegio().admiteAddPrenda(prendaNueva, guardarropa))
		)
			throw new CapacidadExcedidaGuardarropaException();

		guardarropa.addPrenda(prendaNueva);
	}
}
