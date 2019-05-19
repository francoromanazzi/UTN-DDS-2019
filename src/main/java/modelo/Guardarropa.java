package modelo;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import modelo.atuendo.Atuendo;
import modelo.clima.Clima;
import modelo.clima.ServicioDelClima;
import modelo.prenda.Prenda;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

	public boolean tienePrenda(Prenda prenda) {
		List<Prenda> listaABuscar = new ArrayList<>();
		switch (prenda.getCategoria()) {
			case SUPERIOR:
				listaABuscar = prendasSuperiores;
				break;
			case INFERIOR:
				listaABuscar = prendasInferiores;
				break;
			case CALZADO:
				listaABuscar = calzados;
				break;
			case ACCESORIO:
				listaABuscar = accesorios;
				break;
		}
		return listaABuscar.contains(prenda);
	}
	
	public List<Atuendo> obtenerSugerencias() {
		Clima clima = ServicioDelClima.getInstance().obtenerClima();
		//Map<Categoria, List<List<NivelDeAbrigo>>> nivelesDeAbrigosPorCategoria = clima.obtenerNivelesDeAbrigoValidos();

		// TODO hacer que sea con N accesorios
		List<Atuendo> sugerenciasConAccesorio = Lists.cartesianProduct(prendasSuperiores, prendasInferiores, calzados, accesorios).stream()
				.map(result -> new Atuendo(null, result.get(1), result.get(2), null)).collect(Collectors.toList());

		List<Atuendo> sugerenciasSinAccesorio= Lists.cartesianProduct(prendasSuperiores, prendasInferiores, calzados).stream()
				.map(result -> new Atuendo(null, result.get(1), result.get(2), null)).collect(Collectors.toList());

		return Lists.newArrayList(Iterables.concat(sugerenciasConAccesorio, sugerenciasSinAccesorio));
	}

	public List<Atuendo> obtenerSugerencias2() {
		/*
		Clima clima = ServicioDelClima.getInstance().obtenerClima();

		List<List<Prenda>> prendasSuperioresValidas = filtrarYSuperponerPrendasPorClima(prendasSuperiores, clima, 1, 3);
		List<List<Prenda>> prendasInferioresValidas = filtrarYSuperponerPrendasPorClima(prendasInferiores, clima, 1, 1);
		List<List<Prenda>> calzadosValidos = filtrarYSuperponerPrendasPorClima(calzados, clima, 1, 1);
		List<List<Prenda>> accesoriosValidos = filtrarYSuperponerPrendasPorClima(accesorios, clima, 0, 5);

		List<Atuendo> todosLosAtuendosPosibles = Lists.cartesianProduct(prendasSuperiores, prendasInferiores, calzados, accesorios).stream()
				.map(result -> new Atuendo(result.get(0), result.get(1), result.get(2), result.get(3))).collect(Collectors.toList());

		List<Atuendo> sugerenciasConAccesorio = Lists.cartesianProduct(prendasSuperiores, prendasInferiores, calzados, accesorios).stream()
				.map(result -> new Atuendo(result.get(0), result.get(1), result.get(2), Optional.of(result.get(3)))).collect(Collectors.toList());

		return Lists.newArrayList(Iterables.concat(sugerenciasConAccesorio, sugerenciasSinAccesorio));
		 */
		return new ArrayList<>(); // TODO
	}

	private List<List<Prenda>> filtrarYSuperponerPrendasPorClima(List<Prenda> prendas, Clima clima, int min, int max) {
		return new ArrayList<>(); // TODO
	}
}
