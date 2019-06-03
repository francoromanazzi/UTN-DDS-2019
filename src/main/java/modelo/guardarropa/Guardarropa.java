package modelo.guardarropa;

import modelo.prenda.Categoria;
import modelo.prenda.Prenda;

import java.util.*;
import java.util.stream.Collectors;

public class Guardarropa {
	private final List<Prenda> prendas = new ArrayList<>();

	public List<Prenda> getPrendasSuperiores() {
		return getPrendasDeCategoria(Categoria.SUPERIOR);
	}

	public List<Prenda> getPrendasInferiores() {
		return getPrendasDeCategoria(Categoria.INFERIOR);
	}

	public List<Prenda> getCalzados() {
		return getPrendasDeCategoria(Categoria.CALZADO);
	}

	public List<Prenda> getAccesorios() {
		return getPrendasDeCategoria(Categoria.ACCESORIO);
	}

	public List<Prenda> getPrendasDeCategoria(Categoria cat) {
		return prendas.stream().filter(p -> p.getCategoria() == cat).collect(Collectors.toList());
	}

	public int cantidadPrendas() {
		return this.prendas.size();
	}

	public void addPrenda(Prenda prenda) {
		this.prendas.add(prenda);
	}

	public void removePrenda(Prenda prenda) {
		this.prendas.remove(prenda);
	}

	public boolean tienePrenda(Prenda prenda) {
		return this.prendas.contains(prenda);
	}
}
