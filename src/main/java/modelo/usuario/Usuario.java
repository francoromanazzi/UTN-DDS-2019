package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import excepciones.GuardarropaConMayorPrendasQueCapMaxException;
import excepciones.UsuarioNoEsDuenioDelGuardarropaException;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Prenda;
import modelo.sugerencia.decision.Decision;
import modelo.sugerencia.decision.DecisionVacia;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private final List<Guardarropa> guardarropas  = new ArrayList<>();
	private Decision ultimaDecision = new DecisionVacia();
	private PrivilegiosUsuario privilegio = new Gratuito(10);

	public List<Guardarropa> getGuardarropas() {
		return guardarropas;
	}

	public Decision getUltimaDecision() {
		return ultimaDecision;
	}

	public void setUltimaDecision(Decision ultimaDecision) {
		this.ultimaDecision = ultimaDecision;
	}

	public PrivilegiosUsuario getPrivilegio() {
		return privilegio;
	}

	public void setPrivilegio(PrivilegiosUsuario privilegio) {
		this.privilegio = privilegio;
	}

	public void addGuardarropa(Guardarropa guardarropa) throws GuardarropaConMayorPrendasQueCapMaxException {
		privilegio.addGuardarropa(this.guardarropas, guardarropa);
	}

	public void removeGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.remove(guardarropa);
	}
	
	public boolean tieneGuardarropa(Guardarropa g) {
		return this.guardarropas.contains(g);
	}

	public void addPrenda(Prenda prendaNueva, Guardarropa guardarropa) throws UsuarioNoEsDuenioDelGuardarropaException, CapacidadExcedidaGuardarropaException {
		if(!tieneGuardarropa(guardarropa))
			throw new UsuarioNoEsDuenioDelGuardarropaException();

		privilegio.addPrenda(prendaNueva, guardarropa);
	}

	public void deshacer() {
		ultimaDecision.deshacer();
		ultimaDecision = new DecisionVacia();
	}
}
