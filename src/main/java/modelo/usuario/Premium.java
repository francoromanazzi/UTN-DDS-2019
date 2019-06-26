package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;

import java.util.List;

public class Premium implements PrivilegiosUsuario {

	@Override
	public void addGuardarropa(List<Guardarropa> guardarropasPrevios, Guardarropa guardarropaNuevo) {
		guardarropasPrevios.add(guardarropaNuevo);
	}

	@Override
	public void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws CapacidadExcedidaGuardarropaException {
		guardarropa.addPrenda(prendaNueva);
	}
}
