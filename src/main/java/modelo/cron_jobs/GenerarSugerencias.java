package modelo.cron_jobs;

import com.google.common.collect.Lists;
import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.atuendo.Atuendo;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.prenda.Categoria;
import modelo.prenda.Prenda;
import modelo.prenda.PrototipoSuperposicion;
import modelo.prenda.Tipo;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.pronosticos_del_clima.clima.Clima;
import modelo.sugerencia.Sugerencia;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class GenerarSugerencias extends TimerTask {
	private final Evento evento;
	private final Guardarropa guardarropaAUtilizar;
	private final Map<Evento, List<Sugerencia>> sugerenciasGeneradasParaEventos;

	public GenerarSugerencias(Evento evento, Guardarropa guardarropaAUtilizar, Map<Evento, List<Sugerencia>> sugerenciasGeneradasParaEventos) {
		this.evento = evento;
		this.guardarropaAUtilizar = guardarropaAUtilizar;
		this.sugerenciasGeneradasParaEventos = sugerenciasGeneradasParaEventos;
	}

	@Override
	public void run() throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException {
		List<Sugerencia> sugerenciasGeneradas = generarSugerencias();
		sugerenciasGeneradasParaEventos.put(evento, sugerenciasGeneradas);
	}

	// Esto es public solo para poder testearlo sin los cron jobs
	public List<Sugerencia> generarSugerencias() throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException {
		Pronostico pronostico = ServicioDelClima.getInstance().obtenerPronosticoPromedioEntre2Fechas(evento.getFechaInicio(), evento.getFechaFin());
		Clima clima = pronostico.getClima();

		List<PrototipoSuperposicion> superposicionesTiposDeSuperiores = obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria.SUPERIOR, clima);
		List<PrototipoSuperposicion> superposicionesTiposDeInferiores = obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria.INFERIOR, clima);
		List<PrototipoSuperposicion> superposicionesTiposDeCalzados = obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria.CALZADO, clima);
		List<PrototipoSuperposicion> superposicionesTiposDeAccesorios = obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria.ACCESORIO, clima);

		List<List<Prenda>> prendasSuperiores = obtenerPrendasQueSatisfacenPrototipo(Categoria.SUPERIOR, superposicionesTiposDeSuperiores);
		List<List<Prenda>> prendasInferiores = obtenerPrendasQueSatisfacenPrototipo(Categoria.INFERIOR, superposicionesTiposDeInferiores);
		List<List<Prenda>> calzados = obtenerPrendasQueSatisfacenPrototipo(Categoria.CALZADO, superposicionesTiposDeCalzados);
		List<List<Prenda>> accesorios = obtenerPrendasQueSatisfacenPrototipo(Categoria.ACCESORIO, superposicionesTiposDeAccesorios);

		List<Sugerencia> ret = Lists
				.cartesianProduct(prendasSuperiores, prendasInferiores, calzados, accesorios)
				.stream()
				.map(result -> new Sugerencia(new Atuendo(result.get(0), result.get(1).get(0), result.get(2).get(0), result.get(3))))
				.collect(Collectors.toList());

		if (ret.isEmpty()) throw new SinSugerenciasPosiblesException();

		return ret;
	}

	private List<PrototipoSuperposicion> obtenerSuperposicionesDeTiposDeCategoriaPorClima(Categoria categoria, Clima clima) {
		double celsius = clima.getTemperatura().toCelsius().getValor();

		return Tipo.obtenerPrototiposSuperposiciones(categoria).stream()
				.filter(
						superposicion -> superposicion.getTemperaturaMinima().getValor() <= celsius && superposicion.getTemperaturaMaxima().getValor() >= celsius
				).collect(Collectors.toList());
	}

	private List<List<Prenda>> obtenerPrendasQueSatisfacenPrototipo(Categoria categoria, List<PrototipoSuperposicion> prototipos) {
		List<Prenda> prendasDeCategoria = guardarropaAUtilizar.getPrendasDeCategoria(categoria);

		List<List<Tipo>> todosLosTipos = prototipos.stream().map(PrototipoSuperposicion::getTipos).collect(Collectors.toList());

		return todosLosTipos.stream().flatMap(tipos -> {
			List<List<Prenda>> prendas = tipos.stream().map(tipo -> prendasDeCategoria.stream().filter(prenda -> prenda.getTipo() == tipo).collect(Collectors.toList())).collect(Collectors.toList());
			List<List<Prenda>> prendasCartesiano = Lists.cartesianProduct(prendas);
			return prendasCartesiano.stream();
		}).collect(Collectors.toList());
	}
}
