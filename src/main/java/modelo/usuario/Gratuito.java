package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;

import java.util.List;

public class Gratuito implements PrivilegiosUsuario {
	private final int capacidadGuardarropa;
	
	public Gratuito(int capMax) {
		this.capacidadGuardarropa = capMax;
	}
	
	@Override
	public void addGuardarropa(List<Guardarropa> guardarropasPrevios, Guardarropa guardarropaNuevo) throws GuardarropaConMayorPrendasQueCapMaxException {
		if(guardarropaNuevo.cantidadPrendas() > this.capacidadGuardarropa)
			throw new GuardarropaConMayorPrendasQueCapMaxException();

		guardarropasPrevios.add(guardarropaNuevo);
	}

	@Override
	public void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws CapacidadExcedidaGuardarropaException {
		if(guardarropa.cantidadPrendas() >= this.capacidadGuardarropa)
			throw new CapacidadExcedidaGuardarropaException();

		guardarropa.addPrenda(prendaNueva);
	}
}
