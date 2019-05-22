package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
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

	public void addGuardarropa(Guardarropa guardarropa) {
		privilegio.addGuardarropa(this.guardarropas, guardarropa);
	}

	public void removeGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.remove(guardarropa);
	}
	
	public boolean tieneGuardarropa(Guardarropa g) {
		return this.guardarropas.contains(g);
	}

	public void addPrenda(List<Prenda> prendasPrevias, Prenda prendaNueva) throws CapacidadExcedidaGuardarropaException {
		privilegio.addPrenda(prendasPrevias, prendaNueva);
	}

	public void deshacer() {
		ultimaDecision.deshacer();
		ultimaDecision = new DecisionVacia();
	}
}
