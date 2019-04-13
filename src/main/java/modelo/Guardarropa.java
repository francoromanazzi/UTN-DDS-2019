package modelo;

import java.util.List;

public class Guardarropa {
	private List<Prenda> prendas;

	public Guardarropa(List<Prenda> prendas) {
		this.prendas = prendas;
	}

	public List<Prenda> getPrendas() {
		return prendas;
	}

	public void setPrendas(List<Prenda> prendas) {
		this.prendas = prendas;
	}
	
	public void addPrenda(Prenda prenda) {
		this.prendas.add(prenda);
	}
	
	public void removePrenda(Prenda prenda) {
		this.prendas.remove(prenda);
	}
	
	public List<Atuendo> obtenerSugerencia() {
		//aplicar algoritmo de generación de atuendos y devolver las distintas combinaciones de atuendos 
		//para las distintas prendas de un guardarropas
		return null;
	}
}
