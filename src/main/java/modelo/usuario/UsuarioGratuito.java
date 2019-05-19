package modelo.usuario;

import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import modelo.guardarropa.Guardarropa;

public class UsuarioGratuito extends Usuario{
	private int capacidadGuardarropa;
	
	public UsuarioGratuito(int capMax) {
		super();
		this.capacidadGuardarropa = capMax;
	}
	
	@Override
	public void addGuardarropa(Guardarropa guardarropa) throws GuardarropaConMayorPrendasQueCapMaxException {
		if(guardarropa.cantidadPrendas() < this.capacidadGuardarropa) {
			guardarropa.setCapacidadMaxima(this.capacidadGuardarropa);
			this.guardarropas.add(guardarropa);
		}
		else throw new GuardarropaConMayorPrendasQueCapMaxException();
	}
}
