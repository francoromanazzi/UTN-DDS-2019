package modelo.usuario;

import modelo.guardarropa.Guardarropa;
import modelo.atuendo.Decision;
import modelo.atuendo.DecisionVacia;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
	protected final List<Guardarropa> guardarropas  = new ArrayList<>();
	private Decision ultimaDecision = new DecisionVacia();

	public List<Guardarropa> getGuardarropas() {
		return guardarropas;
	}

	public Decision getUltimaDecision() {
		return ultimaDecision;
	}

	public void setUltimaDecision(Decision ultimaDecision) {
		this.ultimaDecision = ultimaDecision;
	}

	public abstract void addGuardarropa(Guardarropa guardarropa);

	public void removeGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.remove(guardarropa);
	}
	
	public boolean tieneGuardarropa(Guardarropa g) {
		return this.guardarropas.contains(g);
	}

	public void deshacer() {
		ultimaDecision.deshacer();
		ultimaDecision = new DecisionVacia();
	}
}
