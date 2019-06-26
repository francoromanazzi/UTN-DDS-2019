package modelo.atuendo;

import modelo.prenda.Prenda;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Atuendo {
	private final Prenda parteInferior, calzado;
	private final List<Prenda> partesSuperiores, accesorios;

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

	public List<Prenda> obtenerTodasLasPrendas() {
		return Stream.of(partesSuperiores, Collections.singletonList(parteInferior), Collections.singletonList(calzado), accesorios)
				.flatMap(Collection::stream).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "Sup: " + partesSuperiores + ", inf: " + parteInferior + ", calzado: " + calzado + ", acc: " + accesorios;
	}
}
