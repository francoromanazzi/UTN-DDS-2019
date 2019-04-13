package modelo;

import excepciones.PrendaNoPuedeIrAAtuendoException;

public class Atuendo {
	Prenda parteSuperior;
	Prenda parteInferior;
	Prenda calzado;
	Prenda accesorio;
	
	public boolean esDeCategoria(Prenda prenda, Categoria categoria) {
		return prenda.categoria() == categoria;
	}

	public Prenda getParteSuperior() {
		return parteSuperior;
	}

	public void setParteSuperior(Prenda parteSuperior) {
		if( esDeCategoria(parteSuperior, Categoria.SUPERIOR) ) {
			this.parteSuperior = parteSuperior;
			return;
		}
		throw new PrendaNoPuedeIrAAtuendoException();
	}

	public Prenda getParteInferior() {
		return parteInferior;
	}

	public void setParteInferior(Prenda parteInferior) {
		if( esDeCategoria(parteInferior, Categoria.INFERIOR) ) {
			this.parteInferior = parteInferior;
			return;
		}
		throw new PrendaNoPuedeIrAAtuendoException();
	}

	public Prenda getCalzado() {
		return calzado;
	}

	public void setCalzado(Prenda calzado) {
		if( esDeCategoria(calzado, Categoria.CALZADO) ) {
			this.calzado = calzado;
			return;
		}
		throw new PrendaNoPuedeIrAAtuendoException();
	}

	public Prenda getAccesorio() {
		return accesorio;
	}

	public void setAccesorio(Prenda accesorio) {
		if( esDeCategoria(accesorio, Categoria.ACCESORIO) ) {
			this.accesorio = accesorio;
			return;
		}
		throw new PrendaNoPuedeIrAAtuendoException();
	}
}
