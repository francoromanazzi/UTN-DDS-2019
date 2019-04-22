package modelo;

public class Atuendo {
	private final Prenda parteSuperior, parteInferior, calzado, accesorio;

	public Atuendo(Prenda parteSuperior, Prenda parteInferior, Prenda calzado, Prenda accesorio) {
		this.parteSuperior = parteSuperior;
		this.parteInferior = parteInferior;
		this.calzado = calzado;
		this.accesorio = accesorio;
	}

	public Prenda getParteSuperior() {
		return parteSuperior;
	}

	public Prenda getParteInferior() {
		return parteInferior;
	}

	public Prenda getCalzado() {
		return calzado;
	}

	public Prenda getAccesorio() {
		return accesorio;
	}
}
