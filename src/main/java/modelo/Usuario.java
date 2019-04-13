package modelo;

import java.util.List;

public class Usuario {
	private List<Guardarropa> guardarropas;

	public Usuario(List<Guardarropa> guardarropas) {
		super();
		this.guardarropas = guardarropas;
	}

	public List<Guardarropa> getGuardarropas() {
		return guardarropas;
	}

	public void setGuardarropas(List<Guardarropa> guardarropas) {
		this.guardarropas = guardarropas;
	}
	
	public void addGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.add(guardarropa);
	}
	
	public void removeGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.remove(guardarropa);
	}
}
