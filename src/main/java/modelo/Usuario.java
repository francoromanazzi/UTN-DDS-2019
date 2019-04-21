package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private List<Guardarropa> guardarropas  = new ArrayList<>();

	public Usuario() {
		super();
	}

	public List<Guardarropa> getGuardarropas() {
		return guardarropas;
	}
	
	public void addGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.add(guardarropa);
	}
	
	public void removeGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.remove(guardarropa);
	}

}
