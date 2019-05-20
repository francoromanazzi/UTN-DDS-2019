package modelo.usuario;

import excepciones.CapacidadExcedidaGuardarropaException;
import modelo.guardarropa.Guardarropa;
import modelo.atuendo.Decision;
import modelo.atuendo.DecisionVacia;
import modelo.prenda.Prenda;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	protected final List<Guardarropa> guardarropas  = new ArrayList<>();
	private Decision ultimaDecision = new DecisionVacia();
	private PrivilegiosUsuario privilegios = new Gratuito(10);

	public List<Guardarropa> getGuardarropas() {
		return guardarropas;
	}

	public Decision getUltimaDecision() {
		return ultimaDecision;
	}

	public void setUltimaDecision(Decision ultimaDecision) {
		this.ultimaDecision = ultimaDecision;
	}

	public PrivilegiosUsuario getPrivilegios() {
		return privilegios;
	}

	public void setPrivilegios(PrivilegiosUsuario privilegios) {
		this.privilegios = privilegios;
	}

	public void addGuardarropa(Guardarropa guardarropa) {
		privilegios.addGuardarropa(this.guardarropas, guardarropa);
	};

	public void removeGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.remove(guardarropa);
	}
	
	public boolean tieneGuardarropa(Guardarropa g) {
		return this.guardarropas.contains(g);
	}

	public void addPrenda(List<Prenda> prendasPrevias, Prenda prendaNueva) throws CapacidadExcedidaGuardarropaException {
		privilegios.addPrenda(prendasPrevias, prendaNueva);
	}

	public void deshacer() {
		ultimaDecision.deshacer();
		ultimaDecision = new DecisionVacia();
	}
}
