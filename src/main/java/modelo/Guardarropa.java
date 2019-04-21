package modelo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Guardarropa {
	private List<Prenda> prendasSuperiores = new ArrayList<>();
	private List<Prenda> prendasInferiores = new ArrayList<>();
	private List<Prenda> calzados = new ArrayList<>();
	private List<Prenda> accesorios = new ArrayList<>();

	public List<Prenda> getPrendasSuperiores() {
		return prendasSuperiores;
	}

	public List<Prenda> getPrendasInferiores() {
		return prendasInferiores;
	}

	public List<Prenda> getCalzados() {
		return calzados;
	}

	public List<Prenda> getAccesorios() {
		return accesorios;
	}

	public void addPrenda(Prenda prenda) {
		switch (prenda.getCategoria()) {
			case SUPERIOR:
				prendasSuperiores.add(prenda);
				break;
			case INFERIOR:
				prendasInferiores.add(prenda);
				break;
			case CALZADO:
				calzados.add(prenda);
				break;
			case ACCESORIO:
				accesorios.add(prenda);
				break;
		}
	}

	public void removePrenda(Prenda prenda) {
		switch (prenda.getCategoria()) {
			case SUPERIOR:
				prendasSuperiores.remove(prenda);
				break;
			case INFERIOR:
				prendasInferiores.remove(prenda);
				break;
			case CALZADO:
				calzados.remove(prenda);
				break;
			case ACCESORIO:
				accesorios.remove(prenda);
				break;
		}
	}

	public List<Atuendo> obtenerSugerencias() {
		return Lists.cartesianProduct(prendasSuperiores, prendasInferiores, calzados, accesorios).stream()
				.map(result -> new Atuendo(result.get(0), result.get(1), result.get(2), result.get(3))).collect(Collectors.toList());
	}
}
