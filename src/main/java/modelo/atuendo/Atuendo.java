package modelo.atuendo;

import modelo.prenda.Prenda;

import java.util.List;

public class Atuendo {
	private final Prenda parteInferior, calzado;
	private final List<Prenda> partesSuperiores, accesorios;
	// TODO: Guardar acá la imágen

	public Atuendo(List<Prenda> partesSuperiores, Prenda parteInferior, Prenda calzado, List<Prenda> accesorios) {
		this.partesSuperiores = partesSuperiores;
		this.parteInferior = parteInferior;
		this.calzado = calzado;
		this.accesorios = accesorios;
	}

	public List<Prenda> getPartesSuperiores() {
		return partesSuperiores;
	}

	public Prenda getParteInferior() {
		return parteInferior;
	}

	public Prenda getCalzado() {
		return calzado;
	}

	public List<Prenda> getAccesorios() {
		return accesorios;
	}

	@Override
	public String toString() {
		return "Sup: " + partesSuperiores + ", inf: " + parteInferior + ", calzado: " + calzado + ", acc: " + accesorios;
	}
}
