package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;

import java.util.List;

public class Gratuito implements PrivilegiosUsuario {
	private int capacidadGuardarropa;
	
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
	public void addPrenda(List<Prenda> prendasPrevias, Prenda prendaNueva) throws CapacidadExcedidaGuardarropaException {
		if(prendasPrevias.size() >= this.capacidadGuardarropa)
			throw new CapacidadExcedidaGuardarropaException();

		prendasPrevias.add(prendaNueva);
	}
}
