package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;

import java.util.List;

public interface PrivilegiosUsuario {
	void addGuardarropa(List<Guardarropa> guardarropasPrevios, Guardarropa guardarropaNuevo) throws GuardarropaConMayorPrendasQueCapMaxException;

	void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws CapacidadExcedidaGuardarropaException;
}
