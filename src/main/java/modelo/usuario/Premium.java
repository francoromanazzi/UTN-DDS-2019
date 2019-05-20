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
	public void addPrenda(List<Prenda> prendasPrevias, Prenda prendaNueva) throws CapacidadExcedidaGuardarropaException {
		prendasPrevias.add(prendaNueva);
	}
}
