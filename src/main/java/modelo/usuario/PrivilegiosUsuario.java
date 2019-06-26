package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;

import java.util.List;

public interface PrivilegiosUsuario {
	void addGuardarropa(Guardarropa guardarropaNuevo, Usuario usuarioNuevo) throws GuardarropaConMayorPrendasQueCapMaxException;

	boolean admiteAddPrenda(Prenda prendaNueva, Guardarropa guardarropa);

	void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws CapacidadExcedidaGuardarropaException;
}
