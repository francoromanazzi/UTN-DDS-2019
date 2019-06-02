package modelo.guardarropa;

import com.google.common.collect.Lists;
import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.atuendo.Atuendo;
import modelo.evento.Evento;
import modelo.prenda.Categoria;
import modelo.prenda.NivelDeAbrigo;
import modelo.prenda.Prenda;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.clima.Clima;
import modelo.sugerencia.Sugerencia;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Guardarropa {
	private final List<Prenda> prendas = new ArrayList<>();

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

	private List<Prenda> getPrendasDe(Categoria cat) {
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

	public List<Sugerencia> obtenerSugerencias(Evento evento) throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException {
		Pronostico pronostico = ServicioDelClima.getInstance().obtenerPronosticoPromedioEntre2Fechas(evento.getFechaInicio(), evento.getFechaFin());
		Clima clima = pronostico.getClima();

		List<List<Prenda>> prendasSuperioresValidas = filtrarYSuperponerPrendasPorClima(getPrendasSuperiores(), clima, 1, 3);
		List<List<Prenda>> prendasInferioresValidas = filtrarYSuperponerPrendasPorClima(getPrendasInferiores(), clima, 1, 1);
		List<List<Prenda>> calzadosValidos = filtrarYSuperponerPrendasPorClima(getCalzados(), clima, 1, 1);
		List<List<Prenda>> accesoriosValidos = filtrarYSuperponerPrendasPorClima(getAccesorios(), clima, 0, 5);

		List<Sugerencia> ret = Lists
				.cartesianProduct(prendasSuperioresValidas, prendasInferioresValidas, calzadosValidos, accesoriosValidos)
				.stream()
				.map(result -> new Sugerencia(new Atuendo(result.get(0), result.get(1).get(0), result.get(2).get(0), result.get(3))))
				.collect(Collectors.toList());

		if(ret.isEmpty()) throw new SinSugerenciasPosiblesException();

		return ret;
	}

	private List<List<Prenda>> filtrarYSuperponerPrendasPorClima(List<Prenda> prendas, Clima clima, int min, int max) {
		List<List<Prenda>> ret = new ArrayList<>();

		Collection<NivelDeAbrigo> formasDeAbrigarme = clima.formasDeAbrigarme();

		for(final int i : IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList())) {
			if(i == 0) ret.add(Collections.emptyList()); // Ya que es opcional

			formasDeAbrigarme.forEach(nivelDeAbrigo -> {
				List<Prenda> prendasDeEsteNivelDeAbrigo = prendas.stream().filter(prenda -> prenda.getTipo().getNivelDeAbrigo() == nivelDeAbrigo).collect(Collectors.toList());
				prendasDeEsteNivelDeAbrigo.forEach(prenda -> {

					// Me fijo si puedo satisfacer requisitos de dicha prenda
					List<List<Prenda>> prendasRequisito = prendasRequisito(prenda);
					List<List<Prenda>> prendasRequisitoEnRango = prendasRequisito.stream().filter(listaPrendas -> listaPrendas.size() < i).collect(Collectors.toList());

					List<List<Prenda>> cartesiano = productoCartesiano(prenda, prendasRequisitoEnRango);

					if(!cartesiano.isEmpty())
						ret.addAll(cartesiano);
				});
			});
		}

		return ret.stream().distinct().collect(Collectors.toList());
	}

	private List<List<Prenda>> prendasRequisito(Prenda prendaTarget) {
		List<List<Prenda>> ret = new ArrayList<>();
		prendaTarget.getTipo().getRequisitosParaUsarse().forEach(tipo -> {
			if(tipo == prendaTarget.getTipo()) { // Significa que la prenda podía también usarse por sí misma
				ret.add(new ArrayList<>(Collections.emptyList()));
				return;
			}

			List<Prenda> prendasDelTipoQueEsRequisito = prendas.stream().filter(prenda -> prenda.getTipo() == tipo).collect(Collectors.toList());
			prendasDelTipoQueEsRequisito.forEach(prenda -> {
				List<List<Prenda>> prendasRequisitoDelRequisito = prendasRequisito(prenda);
				List<List<Prenda>> cartesiano = productoCartesiano(prenda, prendasRequisitoDelRequisito);

				if(!cartesiano.isEmpty())
					ret.addAll(cartesiano);
				else
					ret.add(new ArrayList<>(Arrays.asList(prenda)));
			});
		});

		return ret;
	}

	private List<List<Prenda>> productoCartesiano(Prenda prenda, List<List<Prenda>> prendas) {
		List<List<Prenda>> prendaEnvuelta = new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(prenda))));
		List<List<List<Prenda>>> cartesiano = Lists.cartesianProduct(prendaEnvuelta, prendas);
		return cartesiano
				.stream()
				.map(result -> result.stream().flatMap(Collection::stream).collect(Collectors.toList()))
				.collect(Collectors.toList());
	}
}
