package modelo.guardarropa;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import excepciones.CapacidadExcedidaGuardarropaException;
import modelo.atuendo.Atuendo;
import modelo.clima.Clima;
import modelo.clima.ServicioDelClima;
import modelo.evento.Evento;
import modelo.prenda.Categoria;
import modelo.prenda.Prenda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Guardarropa {
	private List<Prenda> prendas = new ArrayList<Prenda>();
	private int capacidadMaxima = Integer.MAX_VALUE;
	
	public void setCapacidadMaxima(int capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
	}
	
	private List<Prenda> getPrendasDe(Categoria cat){
		return prendas.stream().filter(p -> p.getCategoria() == cat).collect(Collectors.toList());
	}
	
	public List<Prenda> getPrendasSuperiores() {
		return getPrendasDe(Categoria.SUPERIOR);
	}

	public List<Prenda> getPrendasInferiores() {
		return getPrendasDe(Categoria.INFERIOR);
	}

	public List<Prenda> getCalzados() {
		return getPrendasDe(Categoria.CALZADO);
	}

	public List<Prenda> getAccesorios() {
		return getPrendasDe(Categoria.ACCESORIO);
	}

	public int cantidadPrendas() {
		return this.prendas.size();
	}

	public void addPrenda(Prenda prenda) throws CapacidadExcedidaGuardarropaException{
		if(this.prendas.size() < this.capacidadMaxima)
			this.prendas.add(prenda);
		else throw new CapacidadExcedidaGuardarropaException();
	}

	public void removePrenda(Prenda prenda) {
		this.prendas.remove(prenda);
	}

	public boolean tienePrenda(Prenda prenda) {
		return this.prendas.contains(prenda);
	}
	
	public List<Atuendo> obtenerSugerencias(Evento evento) {
		Clima clima = ServicioDelClima.getInstance().obtenerClima(evento.getFechaInicio());

		// TODO hacer que sea con N accesorios
		List<Atuendo> sugerenciasConAccesorio = Lists.cartesianProduct(getPrendasSuperiores(), getPrendasInferiores(), getCalzados(), getAccesorios()).stream()
				.map(result -> new Atuendo(null, result.get(1), result.get(2), null)).collect(Collectors.toList());

		List<Atuendo> sugerenciasSinAccesorio= Lists.cartesianProduct(getPrendasSuperiores(), getPrendasInferiores(), getCalzados()).stream()
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
