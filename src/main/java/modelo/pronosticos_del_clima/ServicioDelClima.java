package modelo.pronosticos_del_clima;

import excepciones.PronosticoNoDisponibleException;
import excepciones.ProveedorDeClimaSeCayoException;
import modelo.pronosticos_del_clima.clima.Clima;
import modelo.pronosticos_del_clima.clima.temperatura.Celsius;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServicioDelClima {
	private static final ServicioDelClima ourInstance = new ServicioDelClima();
	private List<Pronostico> pronosticosCache = new ArrayList<>();
	private List<Meteorologo> meteorologos = new ArrayList<>();

	public static ServicioDelClima getInstance() {
		return ourInstance;
	}

	private ServicioDelClima() {
	}

	public List<Meteorologo> getMeteorologos() {
		return meteorologos;
	}

	public void setMeteorologos(List<Meteorologo> meteorologos) {
		this.meteorologos = meteorologos;
	}

	public List<Pronostico> getPronosticosCache() {
		return pronosticosCache;
	}

	public void setPronosticosCache(List<Pronostico> pronosticosCache) {
		this.pronosticosCache = pronosticosCache;
	}

	public void agregarMeteorologo(Meteorologo meteorologo) {
		meteorologos.add(meteorologo);
	}

	public Pronostico obtenerPronostico(LocalDateTime fecha) throws PronosticoNoDisponibleException, ProveedorDeClimaSeCayoException {

		Consumer<List<Pronostico>> callbackAlGenerarPronostico = pronosticosNuevos -> {
			// Agrego los pronósticos nuevos a los que ya tenía
			this.pronosticosCache.addAll(pronosticosNuevos);

			// Saco los pronosticosCache viejos que hay en pronosticosCache
			this.pronosticosCache = this.pronosticosCache
					.stream()
					.filter(pronostico -> !pronostico.getFechaFin().isBefore(LocalDateTime.now()))
					.collect(Collectors.toList());
		};

		// Reviso si ya lo tenía cacheado
		// Si no, le pregunto a mis meteorologos hasta que alguno me lo pueda dar
		Stream<Pronostico> pronosticosCacheados = this.pronosticosCache.stream();
		Stream<Pronostico> pronosticosNuevos = this.meteorologos.stream()
				.flatMap(meteorologo -> {
					try {
						return meteorologo.obtenerPronosticos(Optional.of(callbackAlGenerarPronostico)).stream();
					} catch (ProveedorDeClimaSeCayoException e) {
						return Stream.empty();
					}
				});

		Stream<Pronostico> pronosticos = Stream.concat(pronosticosCacheados, pronosticosNuevos);

		return pronosticos
				.filter(pronostico -> pronostico.intervaloContieneAFecha(fecha))
				.min(Comparator.comparingLong(p -> p.minutosHastaValorRepresentativo(fecha)))
				.orElseThrow(PronosticoNoDisponibleException::new);
	}

	public Pronostico obtenerPronosticoPromedioEntre2Fechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PronosticoNoDisponibleException {
		List<Pronostico> pronosticos = new ArrayList<>();

		// Obtengo los pronosticosCache cada media hora, entre las 2 fechas
		for (LocalDateTime fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusMinutes(30)) {
			pronosticos.add(obtenerPronostico(fecha));
		}

		double celsuisPromedio = pronosticos.stream().mapToDouble(pronostico -> pronostico.getClima().getTemperatura().toCelsius().getValor()).sum() / (double) pronosticos.size();

		Clima climaPromedio = new Clima(new Celsius(celsuisPromedio));
		return new Pronostico(fechaInicio, fechaFin, climaPromedio);
	}
}
