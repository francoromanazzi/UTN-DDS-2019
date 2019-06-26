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
	public void addGuardarropa(Guardarropa guardarropa, Usuario usuarioNuevo) throws GuardarropaConMayorPrendasQueCapMaxException {
		if (guardarropa.cantidadPrendas() > this.capacidadGuardarropa)
			throw new GuardarropaConMayorPrendasQueCapMaxException();

		guardarropa.addUsuario(usuarioNuevo);
	}

	@Override
	public boolean admiteAddPrenda(Prenda prendaNueva, Guardarropa guardarropa) {
		return guardarropa.cantidadPrendas() < this.capacidadGuardarropa;
	}

	@Override
	public void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws CapacidadExcedidaGuardarropaException {
		if (!admiteAddPrenda(prendaNueva, guardarropa))
			throw new CapacidadExcedidaGuardarropaException();

		guardarropa.addPrenda(prendaNueva);
	}
}
