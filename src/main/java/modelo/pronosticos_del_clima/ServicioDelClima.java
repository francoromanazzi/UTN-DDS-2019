package modelo.pronosticos_del_clima;

import excepciones.PronosticoNoDisponibleException;
import excepciones.ProveedorDeClimaSeCayoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioDelClima {
	private static final ServicioDelClima ourInstance = new ServicioDelClima();
	private List<Pronostico> pronosticos = new ArrayList<>();
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

	public List<Pronostico> getPronosticos() {
		return pronosticos;
	}

	public void setPronosticos(List<Pronostico> pronosticos) {
		this.pronosticos = pronosticos;
	}

	public void agregarMeteorologo(Meteorologo meteorologo) {
		meteorologos.add(meteorologo);
	}

	public Pronostico obtenerPronostico(LocalDateTime fecha) throws PronosticoNoDisponibleException {
		// Reviso si ya lo tenía cacheado
		// Si no, le pregunto a mis meteorologos hasta que alguno me lo pueda dar
		for (int i = 0; obtenerPronosticosRazonables(fecha).isEmpty() && i < meteorologos.size(); i++) {
			try {
				List<Pronostico> pronosticosNuevos = meteorologos.get(i).obtenerPronosticos();

				// Agrego los pronósticos nuevos a los que ya tenía
				pronosticos.addAll(pronosticosNuevos);

				// Saco los pronosticos viejos que hay en pronosticos
				pronosticos = pronosticos.stream().filter(pronostico -> !pronostico.getFechaFin().isBefore(LocalDateTime.now())).collect(Collectors.toList());
			} catch (ProveedorDeClimaSeCayoException e) {
				System.out.println("El proveedor " + meteorologos.get(i).getClass() + " se cayo");
			}
		}

		if (obtenerPronosticosRazonables(fecha).isEmpty()) {
			throw new PronosticoNoDisponibleException();
		}

		return obtenerPronosticosRazonables(fecha)
				.stream()
				.reduce(obtenerPronosticosRazonables(fecha).get(0), (prono1, prono2) ->
						prono1.minutosHastaValorRepresentativo(fecha) <= prono2.minutosHastaValorRepresentativo(fecha)
								? prono1 : prono2);
	}

	public Pronostico obtenerPronosticoPromedioEntre2Fechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws PronosticoNoDisponibleException {
		List<Pronostico> pronosticos = new ArrayList<>();

		// Obtengo los pronosticos cada media hora, entre las 2 fechas
		for (LocalDateTime fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusMinutes(30)) {
			pronosticos.add(obtenerPronostico(fecha));
		}

		double celsuisPromedio = pronosticos.stream().mapToDouble(pronostico -> pronostico.getClima().getTemperatura().toCelsius()).sum() / (double) pronosticos.size();

		Clima climaPromedio = new Clima(new Temperatura(celsuisPromedio, "C"));
		return new Pronostico(fechaInicio, fechaFin, climaPromedio);
	}

	private List<Pronostico> obtenerPronosticosRazonables(LocalDateTime fecha) {
		return pronosticos.stream().filter(pronostico -> pronostico.intervaloContieneAFecha(fecha)).collect(Collectors.toList());
	}
}
